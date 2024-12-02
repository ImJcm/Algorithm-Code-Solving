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
/*
알고리즘 핵심
DP
1. A,B,C로 구성되는 문자열의 순서에 따라 중복 연산의 여부가 결정되므로 dp는 A,B,C의 개수 + 어제 출근 사원 + 그제 출근 사원을 나타내는
5차원 배열을 사용한다.
2. 각 dfs의 len마다 A,B,C 사원의 출근이 가능한지 조건을 검사하여 다음 재귀호출이 이루어져야 한다.
3. dfs의 재귀 호출 이후, dp를 업데이트한다.
4. dp에 A,B,C,y_d,yy_d에 출근한 사원을 검사하여 중복 여부를 확인하여 수행 여부를 결정한다.
 */
public class BOJ14238 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int A,B,C,L;
    static char[] ans;
    static boolean[][][][][] dp;
    static boolean flag;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        dfs(0,0,0,0,0,0);

        if(flag) {
            for(int i = 0; i < ans.length; i++) {
                System.out.print(ans[i]);
            }
        } else {
            System.out.println("-1");
        }
    }

    private static void dfs(int len, int a, int b, int c,int y_d, int yy_d) {
        if(len == L) {
            flag = true;
            return;
        }

        if(dp[a][b][c][y_d][yy_d]) return;

        if(a + 1 <= A && !flag) {
            ans[len] = 'A';
            dfs(len + 1, a + 1, b, c,0,y_d);
            dp[a + 1][b][c][0][y_d] = true;
        }

        if(b + 1 <= B && y_d != 1 && !flag) {
            ans[len] = 'B';
            dfs(len + 1, a, b + 1, c,1,y_d);
            dp[a][b + 1][c][1][y_d] = true;
        }

        if(c + 1 <= C && y_d != 2 && yy_d != 2 && !flag) {
            ans[len] = 'C';
            dfs(len + 1, a, b, c + 1,2,y_d);
            dp[a][b][c + 1][2][y_d] = true;
        }
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

        dp = new boolean[A + 1][B + 1][C + 1][3][3];
    }
}

/*
// dfs의 반환 타입을 boolean으로 적용하는 방법
public class Algor_test2 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int A,B,C,L;
    static char[] ans;
    static boolean[][][][][] dp;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        if(dfs(0,0,0,0,0,0)) {
            for(int i = 0; i < ans.length; i++) {
                System.out.print(ans[i]);
            }
        } else {
            System.out.println("-1");
        }
    }

    private static boolean dfs(int len, int a, int b, int c, int y_d, int yy_d) {
        if(len == L) {
            return true;
        }

        if(dp[a][b][c][y_d][yy_d]) return false;

        dp[a][b][c][y_d][yy_d] = true;

        ans[len] = 'A';
        if(a < A) {
            if(dfs(len + 1, a + 1, b, c,0,y_d)) return true;
        }

        ans[len] = 'B';
        if(b < B && y_d != 1) {
            if(dfs(len + 1, a, b + 1, c,1,y_d)) return true;
        }

        ans[len] = 'C';
        if(c < C && y_d != 2 && yy_d != 2) {
            if(dfs(len + 1, a, b, c + 1,2,y_d)) return true;
        }

        return false;
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split("");

        A = B = C = 0;

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

        dp = new boolean[A + 1][B + 1][C + 1][3][3];
    }
}
 */
