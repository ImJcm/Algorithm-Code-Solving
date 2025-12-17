package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
단어 뒤집기 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	43677	23531	17814	54.794%
문제
문장이 주어졌을 때, 단어를 모두 뒤집어서 출력하는 프로그램을 작성하시오. 단, 단어의 순서는 바꿀 수 없다. 단어는 영어 알파벳으로만 이루어져 있다.

입력
첫째 줄에 테스트 케이스의 개수 T가 주어진다. 각 테스트 케이스는 한 줄로 이루어져 있으며, 문장이 하나 주어진다. 단어의 길이는 최대 20, 문장의 길이는 최대 1000이다. 단어와 단어 사이에는 공백이 하나 있다.

출력
각 테스트 케이스에 대해서, 입력으로 주어진 문장의 단어를 모두 뒤집어 출력한다.

예제 입력 1
2
I am happy today
We want to win the first prize
예제 출력 1
I ma yppah yadot
eW tnaw ot niw eht tsrif ezirp
출처
ICPC > Regionals > Asia Pacific > Korea > Asia Regional - Taejon 2001 연습 세션 PA번

문제를 번역한 사람: baekjoon
알고리즘 분류
구현
문자열
 */
/*
알고리즘 핵심
자료구조
입력으로 주어진 문자열(string)을 공백으로 문장을 나눈 후, StringBuffer로 객체를 생성하여 reverse 내장함수를 사용하여 뒤집어진 상태의 문자열을 만든다.
이후, StringBuilder를 통해 뒤집어진 문자열과 공백을 추가(append)하여 입력으로 주어진 문장마다 출력을 수행한다.
 */
public class BOJ9093 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T;
    static String[] strs;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    public static void solve() {
        StringBuilder sb;

        for(String s : strs) {
            String[] tmp = s.split(" ");
            sb = new StringBuilder();

            for(String ss : tmp) {
                StringBuffer sbf = new StringBuffer(ss);

                sb.append(sbf.reverse().toString()).append(" ");
            }

            System.out.println(sb);
        }
    }

    private static void init_setting() throws IOException {
        T = Integer.parseInt(br.readLine());

        strs = new String[T];

        for(int i = 0; i < T; i++) strs[i] = br.readLine();
    }
}
