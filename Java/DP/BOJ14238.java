package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
출근 기록 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	3776	1209	884	32.789%
문제
스타트링크에는 세명의 직원이 일을 하고 있다. 세 직원의 이름은 강호(A), 준규(B), 수빈(C) 이다.

이 회사의 직원은 특별한 룰을 가지고 있는데, 바로 하루에 한 명만 출근한다는 것이다. 3일간의 출근 기록이 "AAC"라는 것은 처음 이틀은 A가 출근했고, 셋째 날엔 C만 출근했다는 뜻이다.

A는 매일 매일 출근할 수 있다. B는 출근한 다음날은 반드시 쉬어야 한다. C는 출근한 다음날과 다다음날을 반드시 쉬어야 한다. 따라서, 모든 출근 기록이 올바른 것은 아니다. 예를 들어, B는 출근한 다음날 쉬어야 하기 때문에, "BB"는 절대로 나올 수 없는 출근 기록이다.

출근 기록 S가 주어졌을 때, S의 모든 순열 중에서 올바른 출근 기록인 것 아무거나 출력하는 프로그램을 작성하시오.

입력
첫째 줄에 출근 기록 S가 주어진다. S의 길이는 50을 넘지 않는다.

출력
S의 모든 순열 중에서 올바른 출근 기록인 것을 하나만 출력한다. 만약, 올바른 출근 기록이 없는 경우에는 -1을 출력한다.

예제 입력 1
CAB
예제 출력 1
BCA
예제 입력 2
CBB
예제 출력 2
BCB
예제 입력 3
BB
예제 출력 3
-1
예제 입력 4
BBA
예제 출력 4
BAB
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
다이나믹 프로그래밍
그래프 이론
그래프 탐색
깊이 우선 탐색
 */
public class BOJ14238 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int A,B,C,L;
    static char[] ans;
    static boolean[][][][] dp;
    static boolean flag;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        dfs(0,A,B,C);

        if(flag) {
            for(char c : ans) {
                System.out.print(c);
            }
        } else {
            System.out.println("-1");
        }
    }

    private static void dfs(int len, int a, int b, int c) {
        if(flag) return;
        if(len == L) {

        }

        if(dp[len][a][b][c]) return;

        dp[len][a][b][c] = true;

        if(a != 0) {
            ans[len] = 'A';
            dfs(len + 1, a - 1, b, c);
        }

        if(len != 0)
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split("");

        A = B = C = 0;
        flag = false;

        L = input.length;

        ans = new char[L];

        for(String s : input) {
            if(s.equals("A")) {
                A++;
            } else if(s.equals("B")) {
                B++;
            } else if(s.equals("C")) {
                C++;
            }
        }

        dp = new boolean[L][A][B][C];
    }
}
