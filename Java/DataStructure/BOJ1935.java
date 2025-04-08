package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
후위 표기식2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	29844	14653	11801	48.898%
문제
후위 표기식과 각 피연산자에 대응하는 값들이 주어져 있을 때, 그 식을 계산하는 프로그램을 작성하시오.

입력
첫째 줄에 피연산자의 개수(1 ≤ N ≤ 26) 가 주어진다. 그리고 둘째 줄에는 후위 표기식이 주어진다. (여기서 피연산자는 A~Z의 영대문자이며, A부터 순서대로 N개의 영대문자만이 사용되며, 길이는 100을 넘지 않는다) 그리고 셋째 줄부터 N+2번째 줄까지는 각 피연산자에 대응하는 값이 주어진다. 3번째 줄에는 A에 해당하는 값, 4번째 줄에는 B에 해당하는값 , 5번째 줄에는 C ...이 주어진다, 그리고 피연산자에 대응 하는 값은 100보다 작거나 같은 자연수이다.

후위 표기식을 앞에서부터 계산했을 때, 식의 결과와 중간 결과가 -20억보다 크거나 같고, 20억보다 작거나 같은 입력만 주어진다.

출력
계산 결과를 소숫점 둘째 자리까지 출력한다.

예제 입력 1
5
ABC*+DE/-
1
2
3
4
5
예제 출력 1
6.20
예제 입력 2
1
AA+A+
1
예제 출력 2
3.00
출처
데이터를 추가한 사람: arine
빠진 조건을 찾은 사람: ljk0411jg
문제의 오타를 찾은 사람: masioka, whtjddjs0723
알고리즘 분류
자료 구조
스택
 */
/*
알고리즘 핵심
자료구조 (Stack)
1. A-Z까지 대응되는 값을 Map에 저장하고, 후위 연산을 위해 stack을 사용한다.
2. 입력으로 주어진 후위 표시식을 순차적으로 조회하고, 해당하는 값이 A-Z인 경우, map에 대응되는 값을 double 타입으로 stack에 저장한다.
해당하는 값이 "+-/*"인 경우, 두개의 피연산자를 pop하여 해당 연산을 수행한 값을 stack에 저장한다.
3. 최종적으로 나온 결과값을 소수점 두자리만 출력한다. - printf("%.2f", result);
 */
public class BOJ1935 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static String[] postfix_notation;
    static Stack<Double> stack;
    static Map<String, Double> map;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        Double i1,i2;

        for(String s : postfix_notation) {
            switch (s) {
                case "+":
                    i1 = stack.pop();
                    i2 = stack.pop();

                    stack.push(i2 + i1);
                    break;
                case "-":
                    i1 = stack.pop();
                    i2 = stack.pop();

                    stack.push(i2 - i1);
                    break;
                case "*":
                    i1 = stack.pop();
                    i2 = stack.pop();

                    stack.push(i2 * i1);
                    break;
                case "/":
                    i1 = stack.pop();
                    i2 = stack.pop();

                    stack.push(i2 / i1);
                    break;
                default:
                    stack.push(map.get(s));
                    break;
            }
        }

        System.out.printf("%.2f",stack.pop());
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        postfix_notation = br.readLine().split("");

        char ch = 'A';

        stack = new Stack<>();

        map = new HashMap<>();

        for(int i = 0; i < N; i++) {
            map.put(String.valueOf(ch), Double.parseDouble(br.readLine()));
            ch = (char) (ch + 1);
        }
    }
}
