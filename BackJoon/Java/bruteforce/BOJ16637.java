package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
괄호 추가하기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (추가 시간 없음)	512 MB	21566	9131	6673	42.191%
문제
길이가 N인 수식이 있다. 수식은 0보다 크거나 같고, 9보다 작거나 같은 정수와 연산자(+, -, ×)로 이루어져 있다. 연산자 우선순위는 모두 동일하기 때문에, 수식을 계산할 때는 왼쪽에서부터 순서대로 계산해야 한다. 예를 들어, 3+8×7-9×2의 결과는 136이다.

수식에 괄호를 추가하면, 괄호 안에 들어있는 식은 먼저 계산해야 한다. 단, 괄호 안에는 연산자가 하나만 들어 있어야 한다. 예를 들어, 3+8×7-9×2에 괄호를 3+(8×7)-(9×2)와 같이 추가했으면, 식의 결과는 41이 된다. 하지만, 중첩된 괄호는 사용할 수 없다. 즉, 3+((8×7)-9)×2, 3+((8×7)-(9×2))은 모두 괄호 안에 괄호가 있기 때문에, 올바른 식이 아니다.

수식이 주어졌을 때, 괄호를 적절히 추가해 만들 수 있는 식의 결과의 최댓값을 구하는 프로그램을 작성하시오. 추가하는 괄호 개수의 제한은 없으며, 추가하지 않아도 된다.

입력
첫째 줄에 수식의 길이 N(1 ≤ N ≤ 19)가 주어진다. 둘째 줄에는 수식이 주어진다. 수식에 포함된 정수는 모두 0보다 크거나 같고, 9보다 작거나 같다. 문자열은 정수로 시작하고, 연산자와 정수가 번갈아가면서 나온다. 연산자는 +, -, * 중 하나이다. 여기서 *는 곱하기 연산을 나타내는 × 연산이다. 항상 올바른 수식만 주어지기 때문에, N은 홀수이다.

출력
첫째 줄에 괄호를 적절히 추가해서 얻을 수 있는 결과의 최댓값을 출력한다. 정답은 231보다 작고, -231보다 크다.

예제 입력 1
9
3+8*7-9*2
예제 출력 1
136
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
0
예제 입력 5
19
1*2+3*4*5-6*7*8*9*9
예제 출력 5
426384
예제 입력 6
19
1-9-1-9-1-9-1-9-1-9
예제 출력 6
24
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: rlgns9999
비슷한 문제
16638번. 괄호 추가하기 2
16639번. 괄호 추가하기 3
알고리즘 분류
구현
브루트포스 알고리즘
 */
/*
알고리즘 핵심
1. dfs로 숫자에 위치하는 곳에서 다음 숫자를 괄호를 설정할 것인지 안할 것인지 모든 가능한 경우의 수를 만든다.
2. 만들어진 연산식은 isBracketed[]에 의해 괄호가 존재 여부를 통해 해당 계산식의 결과를 도출하고 최댓값으로 업데이트한다.

개인적으로 이 문제는 기존의 dfs의 기저사례와는 다르다고 생각한다.
기존의 dfs는 특정 로직을 수행하였을 때, dfs를 재귀호출하여 depth를 늘려가는 식으로 기저사례를 지정할 수 있었지만,
이 문제는 기저사례가 dfs 내부의 괄호 설정 여부를 연산식에서의 모든 숫자를 거치고 나서야 기저사례로서 작동한다고 생각한다.

위와 같이 생각한 이유가 1+2+3+4를 예시로 1에서 괄호를 설정한다고 하면, (1,2)에 해당하는 위치의 isBracked를 true로 설정할 것이고,
dfs를 호출할 것이다. 그런데 depth를 어떻게 늘려가고 기저사례로 괄호는 없거나 하나 이상일 수 있기 때문에 depth = ? 무엇으로 설정해야 종료할 것인지가 모호했다.
그래서 연산식에 괄호가 설정되는 모든 경우의 수는 dfs 함수의 내부 반복문이 종료될 때 설정된 값으로 기저사례를 잡은 것이다.

아니면 다음과 같은 방식으로 작동할 수도 있다고 생각한다.
void func(int depth) {
    if(depth == formula.length() - 2) { // 마지막 숫자는 괄호 설정이 불가능하기 때문에 이전 숫자까지가 기저사례
       //연산 식 계산
       return;
    }

    if(!isBracked[depth] && Character.isDigit(formula.charAt(depth))) {
        isBracked[depth] = isBracked[depth+2] = true;
        func(depth+1);
        isBracked[depth] = isBracked[depth+2] = false;
    }
    func(depth+1);  // 괄호를 설정하지 않고 다음으로 이동
}
 */
public class BOJ16637 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static String formula;
    static boolean[] isBracketed;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        dfs(0);

        System.out.println(ans);
    }

    static void dfs(int depth) {
        for(int i=depth;i<formula.length() - 2;i++) {
            if(isBracketed[i] || (i % 2) != 0) continue;
            if(!isBracketed[i] && !isBracketed[i+2]) {
                isBracketed[i] = isBracketed[i+2] = true;
                dfs(i);
                isBracketed[i] = isBracketed[i+2] = false;
            }
        }

        calculate_operations();
    }

    static void calculate_operations() {
        int sum = 0, sub_operand = 0;
        char operator = '+';
        for(int i=0;i<formula.length();i++) {
            if(Character.isDigit(formula.charAt(i))) {
                if(isBracketed[i]) {
                    int bracket_operand_a = Character.getNumericValue(formula.charAt(i));
                    int bracket_operand_b = Character.getNumericValue(formula.charAt(i+2));
                    char bracket_operator = formula.charAt(i+1);

                    switch (bracket_operator) {
                        case '+':
                            sub_operand = (bracket_operand_a + bracket_operand_b);
                            break;
                        case '-':
                            sub_operand = (bracket_operand_a - bracket_operand_b);
                            break;
                        case '*':
                            sub_operand = (bracket_operand_a * bracket_operand_b);
                            break;
                    }
                    i += 2;
                } else {
                    sub_operand = Character.getNumericValue(formula.charAt(i));
                }

                switch (operator) {
                    case '+':
                        sum += sub_operand;
                        break;
                    case '-':
                        sum -= sub_operand;
                        break;
                    case '*':
                        sum *= sub_operand;
                        break;
                }
            } else {
                operator = formula.charAt(i);
            }
        }

        ans = Math.max(sum, ans);
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        formula = br.readLine();

        isBracketed = new boolean[formula.length()];
        ans = Integer.MIN_VALUE;

        Arrays.fill(isBracketed, false);
    }
}
