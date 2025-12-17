package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
네 수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	33831	15497	13596	46.201%
문제
네 자연수 A, B, C, D가 주어진다. 이때, A와 B를 붙인 수와 C와 D를 붙인 수의 합을 구하는 프로그램을 작성하시오.

두 수 A와 B를 합치는 것은 A의 뒤에 B를 붙이는 것을 의미한다. 즉, 20과 30을 붙이면 2030이 된다.

입력
첫째 줄에 네 자연수 A, B, C, D가 주어진다. (1 ≤ A, B, C, D ≤ 1,000,000)

출력
A와 B를 붙인 수와 C와 D를 붙인 수의 합을 출력한다.

예제 입력 1
10 20 30 40
예제 출력 1
4060
출처
문제를 만든 사람: baekjoon
문제의 오타를 찾은 사람: wurikiji
알고리즘 분류
수학
구현
문자열
사칙연산
 */
/*
알고리즘 핵심
자료구조 (문자열)
1. 입력으로 주어진 자연수 값을 String 배열에 담는다.
2. 두 문자열을 A + B, C + D로 합쳐서 Long 타입의 값으로 변환한다. - Long.parseLong()
-> numberFormatException() : A,B,C,D의 범위가 1,000,000이므로 두 변수가 합쳐졌을 때 타입의 범위를 넘어갈 수 있으므로
Integer -> Long을 고려해야 한다.
 */
public class BOJ10824 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String[] num;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        String AB = num[0] + num[1];
        String CD = num[2] + num[3];

        Long ab = Long.parseLong(AB);
        Long cd = Long.parseLong(CD);

        System.out.println(ab + cd);
    }

    private static void init_setting() throws IOException{
        num = br.readLine().split(" ");
    }
}
