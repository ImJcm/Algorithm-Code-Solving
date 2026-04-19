package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
괄호 추가하기 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (추가 시간 없음)	512 MB	1536	881	690	57.837%
문제
길이가 N인 수식이 있다. 수식은 0보다 크거나 같고, 9보다 작거나 같은 정수와 연산자(+, -, ×)로 이루어져 있다. 곱하기의 연산자 우선순위가 더하기와 빼기보다 높기 때문에, 곱하기를 먼저 계산 해야 한다. 수식을 계산할 때는 왼쪽에서부터 순서대로 계산해야 한다. 예를 들어, 3+8×7-9×2의 결과는 41이다.

수식에 괄호를 추가하면, 괄호 안에 들어있는 식은 먼저 계산해야 한다. 단, 괄호 안에는 연산자가 하나만 들어 있어야 한다. 예를 들어, 3+8×7-9×2에 괄호를 (3+8)×7-(9×2)와 같이 추가했으면, 식의 결과는 59가 된다. 하지만, 중첩된 괄호는 사용할 수 없다. 즉, 3+((8×7)-9)×2, 3+((8×7)-(9×2))은 모두 괄호 안에 괄호가 있기 때문에, 올바른 식이 아니다.

수식이 주어졌을 때, 괄호를 적절히 추가해 만들 수 있는 식의 결과의 최댓값을 구하는 프로그램을 작성하시오. 추가하는 괄호 개수의 제한은 없으며, 추가하지 않아도 된다.

입력
첫째 줄에 수식의 길이 N(1 ≤ N ≤ 19)가 주어진다. 둘째 줄에는 수식이 주어진다. 수식에 포함된 정수는 모두 0보다 크거나 같고, 9보다 작거나 같다. 문자열은 정수로 시작하고, 연산자와 정수가 번갈아가면서 나온다. 연산자는 +, -, * 중 하나이다. 여기서 *는 곱하기 연산을 나타내는 × 연산이다. 항상 올바른 수식만 주어지기 때문에, N은 홀수이다.

출력
첫째 줄에 괄호를 적절히 추가해서 얻을 수 있는 결과의 최댓값을 출력한다. 정답은 231보다 작고, -231보다 크다.

예제 입력 1
9
3+8*7-9*2
예제 출력 1
59
예제 입력 2
5
8*3+5
예제 출력 2
64
예제 입력 3
7
8*3+5+2
예제 출력 3
66
예제 입력 4
19
1*2+3*4*5-6*7*8*9*0
예제 출력 4
100
예제 입력 5
19
1*2+3*4*5-6*7*8*9*9
예제 출력 5
-27116
예제 입력 6
19
1-9-1-9-1-9-1-9-1-9
예제 출력 6
24
출처
문제를 만든 사람: baekjoon
비슷한 문제
16637번. 괄호 추가하기
16639번. 괄호 추가하기 3
알고리즘 분류
구현
브루트포스 알고리즘
백트래킹
 */
/*
알고리즘 핵심
1.backtracking()
1-1. 백트랙킹으로 모든 괄호를 포함하거나 포함하지 않는 연산식을 만든다.
2. calculate()
2-1. 1번 과정을 거쳐 만들어진 연산식을 대상으로 괄호를 우선 계산하여 새로운 연산식을 만든다.
2-2. 2번 과정을 거쳐 만들어진 연산식을 대상으로 * 연산을 우선 계산한 새로운 연산식을 만든다.
2-3. +,-의 연산 우선순위는 같으므로 왼쪽부터 최종 연산을 수행하여 ans에 최대값을 업데이트한다.
 */
public class BOJ16638 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static String operands;
    static boolean[] bracket;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        backtracking(0);

        System.out.println(ans);
    }

    static void backtracking(int s) {
        if(s == N-1) {
            calculate();
            return;
        }

        backtracking(s+2);
        if(!bracket[s]) {
            bracket[s] = bracket[s+2] = true;
            backtracking(s+2);
            bracket[s] = bracket[s+2] = false;
        }
    }

    static void calculate() {
        ArrayList<Character> bracket_calculate_formula_operator = new ArrayList<>();
        ArrayList<Integer> bracket_calculate_formula_operand = new ArrayList<>();

        for(int i=0;i<N;) {
            if(bracket[i]) {
                int result = 0;
                int prev_operand = Character.getNumericValue(operands.charAt(i));
                int next_operand = Character.getNumericValue(operands.charAt(i+2));

                switch (operands.charAt(i+1)) {
                    case '+':
                        result = prev_operand + next_operand;
                        break;
                    case '-':
                        result = prev_operand - next_operand;
                        break;
                    case '*':
                        result = prev_operand * next_operand;
                        break;
                }
                bracket_calculate_formula_operand.add(result);
                i += 3;
            } else {
                if(Character.isDigit(operands.charAt(i))) {
                    bracket_calculate_formula_operand.add(Character.getNumericValue(operands.charAt(i)));
                } else {
                    bracket_calculate_formula_operator.add(operands.charAt(i));
                }
                i += 1;
            }
        }

        ArrayList<Character> multiple_calculate_formula_operator = new ArrayList<>();
        ArrayList<Integer> multiple_calculate_formula_operand = new ArrayList<>();

        multiple_calculate_formula_operand.add(bracket_calculate_formula_operand.get(0));

        for(int i=0;i<bracket_calculate_formula_operator.size();i++) {
            multiple_calculate_formula_operand.add(bracket_calculate_formula_operand.get(i+1));

            if(bracket_calculate_formula_operator.get(i).equals('*')) {
                int last_idx = multiple_calculate_formula_operand.size();
                int next_operand = multiple_calculate_formula_operand.remove(last_idx-1);
                int prev_operand = multiple_calculate_formula_operand.remove(last_idx-2);
                int result =  next_operand * prev_operand;
                multiple_calculate_formula_operand.add(result);
            } else {
                multiple_calculate_formula_operator.add(bracket_calculate_formula_operator.get(i));
            }
        }

        int sum = multiple_calculate_formula_operand.get(0);

        for(int i=0;i<multiple_calculate_formula_operator.size();i++) {
            int operand = multiple_calculate_formula_operand.get(i+1);
            if(multiple_calculate_formula_operator.get(i).equals('+')) {
                sum += operand;
            } else {
                sum -= operand;
            }
        }

        ans = Math.max(ans, sum);
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        operands = br.readLine();

        bracket = new boolean[N];

        ans = Integer.MIN_VALUE;
    }
}
