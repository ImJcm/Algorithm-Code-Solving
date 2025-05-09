package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
-2진수 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	13094	6187	5124	46.552%
문제
-2진법은 부호 없는 2진수로 표현이 된다. 2진법에서는 20, 21, 22, 23이 표현 되지만 -2진법에서는 (-2)0 = 1, (-2)1 = -2, (-2)2 = 4, (-2)3 = -8을 표현한다. 10진수로 1부터 표현하자면 1, 110, 111, 100, 101, 11010, 11011, 11000, 11001 등이다.

10진법의 수를 입력 받아서 -2진수를 출력하는 프로그램을 작성하시오.

입력
첫 줄에 10진법으로 표현된 수 N이 주어진다.

출력
-2진법 수를 출력한다.

제한
-2,000,000,000 ≤ N ≤ 2,000,000,000
예제 입력 1
-13
예제 출력 1
110111
출처
Olympiad > USA Computing Olympiad > 2005-2006 Season > USACO February 2006 Contest > Bronze ?번

알고리즘 분류
수학
정수론
 */
/*
알고리즘 핵심
수학 + 정수론 (규칙성)
1. 기존의 2진법으로 이진수표현을 하는 방법을 응용해야한다.
1 = 1
2 = 110
3 = 111
4 = 100
5 = 101
6 = 11010
7 = 11011
8 = 11000
9 = 11001
10 = 11110
11 = 11111
12 = 11100
...
위 나열된 값에서 1씩 더했을 때, 짝수 차수의 값의 변화는 없고, 홀수 차수의 값에서는 다음 차수의 값을 감소시킨다.
ex) 11 -> 12 => 11111 + 1 -> 11110 + 10 -> 11100 + 100 - 100 = 11100
이를 응용하면 N % 2 -> (1 or 0, 홀수 지수 자리인 경우) -> N / 2 or N / 2 + 1로 나타낼 수 있다.
10 = 11110 -> 10 % 2 = 0, 10 / 2 -> 5 % 2 = 1, 5 / 2 + 1 -> 3 % 2 = 1, 3 / 2 -> 1
1110 -> 앞자리의 수가 1이고, 홀수차수인 3이므로 1 -> 11
(1 -> 11인 이유는 (-2)^3 = -8이므로 1000인 8의 값을 얻으려면 (-2)^4 + (-2)^3 = 16 - 8 = 8로 변환해야 한다.)

음수의 경우, 양수일 때의 경우에서 차수가 짝수차수에서 위와 같은 과정을 거치면 된다.
ex) -6 = 1110 -> 6 % 2 = 0, 6 / 2 -> 3 % 2 = 1, 3 / 2 -> 1 -> 짝수 차수이면서 1이므로, 1 -> 11 => 1110
 */
public class BOJ2089 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static StringBuilder binary_str;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        System.out.println(N >= 0 ? convert_binary_str(N,true) : convert_binary_str(N,false));
    }

    private static String convert_binary_str(int N, boolean positive) {
        boolean odd_and_even = false;
        int result = 0;

        if(N == 0) {
            binary_str.append(0);
        } else {
            if(positive) {
                while(true) {
                    if(N == 1) {
                        binary_str.append(odd_and_even ? 11 : 1);
                        break;
                    }
                    result = N % 2;
                    binary_str.append(result);
                    N = odd_and_even ?
                            result == 1 ? N / 2 + 1 : N / 2
                            : N / 2;
                    odd_and_even = !odd_and_even;
                }
            } else {
                N *= -1;
                while(true) {
                    if(N == 1) {
                        binary_str.append(odd_and_even ? 1 : 11);
                        break;
                    }

                    result = N % 2;
                    binary_str.append(result);
                    N = odd_and_even ? N / 2
                            : result == 1 ? N / 2 + 1 : N / 2;
                    odd_and_even = !odd_and_even;
                }
            }
        }

        return binary_str.reverse().toString();
    }


    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        binary_str = new StringBuilder();
    }
}
