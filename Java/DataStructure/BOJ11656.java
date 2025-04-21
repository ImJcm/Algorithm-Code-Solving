package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
접미사 배열

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	29299	20691	17277	71.455%
문제
접미사 배열은 문자열 S의 모든 접미사를 사전순으로 정렬해 놓은 배열이다.

baekjoon의 접미사는 baekjoon, aekjoon, ekjoon, kjoon, joon, oon, on, n 으로 총 8가지가 있고, 이를 사전순으로 정렬하면, aekjoon, baekjoon, ekjoon, joon, kjoon, n, on, oon이 된다.

문자열 S가 주어졌을 때, 모든 접미사를 사전순으로 정렬한 다음 출력하는 프로그램을 작성하시오.

입력
첫째 줄에 문자열 S가 주어진다. S는 알파벳 소문자로만 이루어져 있고, 길이는 1,000보다 작거나 같다.

출력
첫째 줄부터 S의 접미사를 사전순으로 한 줄에 하나씩 출력한다.

예제 입력 1
baekjoon
예제 출력 1
aekjoon
baekjoon
ekjoon
joon
kjoon
n
on
oon
출처
문제를 만든 사람: baekjoon
알고리즘 분류
문자열
정렬
 */
/*
알고리즘 핵심
자료구조 (문자열 + 정렬)
1. 입력된 문자열을 String.substring()을 사용하여 접미사 배열을 만든다.
2. Arrays.sort()를 사용하여 사전상 오름차순의 정렬을 접미사 배열에 적용하고 출력한다.
 */
public class BOJ11656 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String S;
    static String[] suffix;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        Arrays.sort(suffix);

        for(String s : suffix) {
            System.out.println(s);
        }
    }

    private static void init_setting() throws IOException{
        S = br.readLine();

        suffix = new String[S.length()];

        for(int i = 0; i < S.length(); i++) {
            suffix[i] = S.substring(i);
        }
    }
}
