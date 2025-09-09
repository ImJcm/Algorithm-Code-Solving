package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
A와 B 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	22059	7143	5673	31.141%
문제
수빈이는 A와 B로만 이루어진 영어 단어 존재한다는 사실에 놀랐다. 대표적인 예로 AB (Abdominal의 약자), BAA (양의 울음 소리), AA (용암의 종류), ABBA (스웨덴 팝 그룹)이 있다.

이런 사실에 놀란 수빈이는 간단한 게임을 만들기로 했다. 두 문자열 S와 T가 주어졌을 때, S를 T로 바꾸는 게임이다. 문자열을 바꿀 때는 다음과 같은 두 가지 연산만 가능하다.

문자열의 뒤에 A를 추가한다.
문자열의 뒤에 B를 추가하고 문자열을 뒤집는다.
주어진 조건을 이용해서 S를 T로 만들 수 있는지 없는지 알아내는 프로그램을 작성하시오.

입력
첫째 줄에 S가 둘째 줄에 T가 주어진다. (1 ≤ S의 길이 ≤ 49, 2 ≤ T의 길이 ≤ 50, S의 길이 < T의 길이)

출력
S를 T로 바꿀 수 있으면 1을 없으면 0을 출력한다.

예제 입력 1
A
BABA
예제 출력 1
1
예제 입력 2
BAAAAABAA
BAABAAAAAB
예제 출력 2
1
예제 입력 3
A
ABBA
예제 출력 3
0
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
문자열
브루트포스 알고리즘
재귀
 */
/*
알고리즘 핵심
그리디 알고리즘 + bruteforce + String
1. S,T의 길이가 49,50으로 작기 때문에 bruteforce로 충분히 S -> T로의 문자열을 만들어가는 과정이 가능하다.
2. S에서 A 또는 B를 추가하는 조건을 만족하면서 문자열을 만들면서 T가 되는 경우 ans를 1로 설정하고 그 외에는 return하여 모든 경우의 수를 살핀다.
 */
public class BOJ12919 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String S,T;
    static StringBuilder sb;
    static int ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        dfs(S.length());

        System.out.println(ans);
    }

    private static void dfs(int n) {
        if(n == T.length()) {
            if(sb.toString().equals(T)) ans = 1;
            return;
        }

        sb.append("A");
        dfs(n + 1);
        sb.deleteCharAt(n);

        sb.append("B");
        sb.reverse();
        dfs(n + 1);
        sb.reverse();
        sb.deleteCharAt(n);
    }

    private static void init_setting() throws IOException {
        S = br.readLine();
        T = br.readLine();

        ans = 0;

        sb = new StringBuilder(S);
    }
}
