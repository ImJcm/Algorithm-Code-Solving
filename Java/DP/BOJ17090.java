package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/*
미로 탈출하기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	6065	2108	1531	33.767%
문제
크기가 N×M인 미로가 있고, 미로는 크기가 1×1인 칸으로 나누어져 있다. 미로의 각 칸에는 문자가 하나 적혀있는데, 적혀있는 문자에 따라서 다른 칸으로 이동할 수 있다.

어떤 칸(r, c)에 적힌 문자가

U인 경우에는 (r-1, c)로 이동해야 한다.
R인 경우에는 (r, c+1)로 이동해야 한다.
D인 경우에는 (r+1, c)로 이동해야 한다.
L인 경우에는 (r, c-1)로 이동해야 한다.
미로에서 탈출 가능한 칸의 수를 계산해보자. 탈출 가능한 칸이란, 그 칸에서 이동을 시작해서 칸에 적힌대로 이동했을 때, 미로의 경계 밖으로 이동하게 되는 칸을 의미한다.

입력
첫째 줄에 미로의 크기 N, M(3 ≤ N, M ≤ 500)이 주어진다. 둘째 줄부터 N개의 줄에는 미로의 각 칸에 적힌 문자가 주어진다.

출력
첫째 줄에 탈출 가능한 칸의 수를 출력한다.

예제 입력 1
3 3
DDD
DDD
DDD
예제 출력 1
9
예제 입력 2
3 3
DDR
DLU
LLL
예제 출력 2
9
예제 입력 3
3 3
RRD
RDD
ULL
예제 출력 3
0
예제 입력 4
3 4
RRDD
RRDR
DULU
예제 출력 4
4
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
다이나믹 프로그래밍
그래프 이론
그래프 탐색
깊이 우선 탐색
 */
/*
timeOut_1, timeOut_2 코드를 작성하고도 도저히 시간초과를 해결할 방법이 떠오르지 않아 정답코드 참고하였다. - https://velog.io/@solser12/%EB%B0%B1%EC%A4%80-17090-%EB%AF%B8%EB%A1%9C-%ED%83%88%EC%B6%9C%ED%95%98%EA%B8%B0-JAVA

기존에 작성한 알고리즘에서 벗어나지 않으나 한 지점에서 dfs를 돌린 후, 외곽으로 이동이 가능한지에 따라 ans를 증가시켜주는 알고리즘에서 차이가 난다.
또한, 이동을 하는 square마다 별도의 number를 지정하여 visited와 함께 메모리제이션 기능을 수행한다.

이 코드를 통해 왜 시간초과가 발생했는지 원인을 알 수 있었다.
(원인 : 모든 칸마다 dfs를 수행하기 때문이다. dfs 내부에서 메모리제이션을 수행한다고 하더라도 모든 칸을 dfs를 수행하려고 하는 점에서 시간초과가 발생하지 않았나 생각한다.)

알고리즘 핵심
1. miro의 크기를 N+2, M+2로 외곽을 추가한 맵 배열을 생성
2. 실제 미로에 해당하는 칸을 순차적으로 dfs를 수행한다.
3. 이동하는 칸마다 sqaure_section_num와 방문여부를 설정하고, 외곽으로 탈출이 가능한지 여부에 따라 enable_out_num을 업데이트한다.
4. 3의 과정을 반복하면서 이미 방문한 square에 도달 시, 해당 칸의 square_num이 enable_out_num에 true/false 여부를 확인하고
true면, 해당 칸을 시작으로 모든 행위가 외곽으로 가능하다는 것을 의미하므로 이미 지나온 칸을 업데이트한 move_cnt를 ans에 업데이트한다.
false면, 외곽으로의 탈출이 불가능하다는 것을 의미하므로 return을 통해 이미 반복한 동작을 멈추게된다. (dp - memorization)
 */
public class BOJ17090 {
    static class BOJ17090_square {
        int n,m;
        String str;
        int square_num;

        BOJ17090_square(int n, int m, String str) {
            this.n = n;
            this.m = m;
            this.str = str;
            this.square_num = 0;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,ans,square_section_num;
    static boolean[] enable_out_num;
    static BOJ17090_square[][] miro;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= M; j++) {
                if(miro[i][j].square_num != 0) continue;
                dfs(miro[i][j],0);
                square_section_num++;
            }
        }
        System.out.println(ans);
    }

    static void dfs(BOJ17090_square s, int move_cnt) {
        if(check_escape(s)) {
            enable_out_num[square_section_num] = true;
            ans += move_cnt;
            return;
        } else if(s.square_num != 0) {
            if(enable_out_num[s.square_num]) {
                enable_out_num[square_section_num] = true;
                ans += move_cnt;
            }
            return;
        }

        s.square_num = square_section_num;

        switch (s.str) {
            case "U":
                dfs(miro[s.n-1][s.m], move_cnt + 1);
                break;
            case "R":
                dfs(miro[s.n][s.m+1], move_cnt + 1);
                break;
            case "D":
                dfs(miro[s.n+1][s.m], move_cnt + 1);
                break;
            case "L":
                dfs(miro[s.n][s.m-1], move_cnt + 1);
                break;
        }
    }

    static boolean check_escape(BOJ17090_square s) {
        return s == null;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        ans = 0;
        square_section_num = 1;

        enable_out_num = new boolean[N * M + 1];
        miro = new BOJ17090_square[N+2][M+2];

        for(int i = 1; i <= N; i++) {
            input = br.readLine().split("");
            for(int j = 1; j <= M; j++) {
                miro[i][j] = new BOJ17090_square(i,j,input[j-1]);
            }
        }
    }
}

class BOJ17090_timeOut_2 {
    static class BOJ17090_square {
        int n,m;
        String str;

        BOJ17090_square(int n, int m, String str) {
            this.n = n;
            this.m = m;
            this.str = str;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,ans;
    static boolean mem_flag;
    static boolean[][] visited;
    static BOJ17090_square[][] miro;
    static int[][] memorization;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= M; j++) {
                mem_flag = false;
                if(memorization[i][j] != 0) ans++;
                else dfs(miro[i][j]);
            }
        }
        System.out.println(ans);
    }

    static void dfs(BOJ17090_square m) {
        if(check_escape(m)) {
            ans++;
            mem_flag = true;
            return;
        }

        if(memorization[m.n][m.m] != 0) {
            ans++;
            mem_flag = true;
            return;
        }

        if(visited[m.n][m.m]) return;

        visited[m.n][m.m] = true;
        memorization[m.n][m.m] = 1;

        switch (m.str) {
            case "U":
                dfs(miro[m.n - 1][m.m]);
                break;
            case "R":
                dfs(miro[m.n][m.m + 1]);
                break;
            case "D":
                dfs(miro[m.n + 1][m.m]);
                break;
            case "L":
                dfs(miro[m.n][m.m - 1]);
                break;
        }

        if(!mem_flag) {
            memorization[m.n][m.m] = 0;
        }
    }

    static boolean check_escape(BOJ17090_square square) {
        //return square == null || square.enable_out;
        return square == null;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        ans = 0;

        memorization = new int[N+2][M+2];
        miro = new BOJ17090_square[N+2][M+2];
        visited = new boolean[N+2][M+2];

        for(int i = 1; i <= N; i++) {
            input = br.readLine().split("");
            for(int j = 1; j <= M; j++) {
                miro[i][j] = new BOJ17090_square(i,j,input[j-1]);
            }
        }
    }
}

class BOJ17090_timeOut_1{
    static class BOJ17090_square {
        int n,m;
        String str;
        boolean enable_out;

        BOJ17090_square(int n, int m, String str) {
            this.n = n;
            this.m = m;
            this.str = str;
            this.enable_out = false;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,ans;
    static BOJ17090_square[][] miro;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= M; j++) {
                dfs(miro[i][j], new ArrayList<BOJ17090_square>(), new boolean[N+2][M+2]);
            }
        }
        System.out.println(ans);
    }

    static void dfs(BOJ17090_square m, ArrayList<BOJ17090_square> path, boolean[][] visited) {
        if(check_escape(m)) {
            ans++;
            path.forEach(s -> s.enable_out = true);
            return;
        }

        if(visited[m.n][m.m]) return;

        visited[m.n][m.m] = true;
        path.add(m);

        switch (m.str) {
            case "U":
                dfs(miro[m.n - 1][m.m], path, visited);
                break;
            case "R":
                dfs(miro[m.n][m.m + 1], path, visited);
                break;
            case "D":
                dfs(miro[m.n + 1][m.m], path, visited);
                break;
            case "L":
                dfs(miro[m.n][m.m - 1], path, visited);
                break;
        }
    }

    static boolean check_escape(BOJ17090_square square) {
        return square == null || square.enable_out;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        ans = 0;

        miro = new BOJ17090_square[N+2][M+2];

        for(int i = 1; i <= N; i++) {
            input = br.readLine().split("");
            for(int j = 1; j <= M; j++) {
                miro[i][j] = new BOJ17090_square(i,j,input[j-1]);
            }
        }
    }
}
