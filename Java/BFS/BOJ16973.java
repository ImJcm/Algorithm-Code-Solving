package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
직사각형 탈출

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	7670	2282	1544	26.894%
문제
크기가 N×M인 격자판에 크기가 H×W인 직사각형이 놓여 있다. 격자판은 크기가 1×1인 칸으로 나누어져 있다. 격자판의 가장 왼쪽 위 칸은 (1, 1), 가장 오른쪽 아래 칸은 (N, M)이다. 직사각형의 가장 왼쪽 위칸은 (Sr, Sc)에 있을 때, 이 직사각형의 가장 왼쪽 위칸을 (Fr, Fc)로 이동시키기 위한 최소 이동 횟수를 구해보자.

격자판의 각 칸에는 빈 칸 또는 벽이 있다. 직사각형은 벽이 있는 칸에 있을 수 없다. 또한, 직사각형은 격자판을 벗어날 수 없다.

직사각형은 한 번에 왼쪽, 오른쪽, 위, 아래 중 한 방향으로 한 칸 이동시킬 수 있다.

입력
첫째 줄에 격자판의 크기 N, M이 주어진다. 둘째 줄부터 N개의 줄에 격자판의 각 칸의 정보가 주어진다. 0은 빈 칸, 1은 벽이다.

마지막 줄에는 직사각형의 크기 H, W, 시작 좌표 Sr, Sc, 도착 좌표 Fr, Fc가 주어진다.

격자판의 좌표는 (r, c) 형태이고, r은 행, c는 열이다. 1 ≤ r ≤ N, 1 ≤ c ≤ M을 만족한다.

출력
첫째 줄에 최소 이동 횟수를 출력한다. 이동할 수 없는 경우에는 -1을 출력한다.

제한
2 ≤ N, M ≤ 1,000
1 ≤ H ≤ N
1 ≤ W ≤ M
1 ≤ Sr ≤ N-H+1
1 ≤ Sc ≤ M-W+1
1 ≤ Fr ≤ N-H+1
1 ≤ Fc ≤ M-W+1
입력으로 주어진 직사각형은 격자판을 벗어나지 않고, 직사각형이 놓여 있는 칸에는 벽이 없다.
예제 입력 1
4 5
0 0 0 0 0
0 0 1 0 0
0 0 0 0 0
0 0 0 0 0
2 2 1 1 1 4
예제 출력 1
7
아래, 아래, 오른쪽, 오른쪽, 오른쪽, 위, 위

예제 입력 2
6 7
0 0 0 0 0 0 0
0 0 0 1 0 0 0
0 0 0 0 0 0 0
0 0 0 0 0 0 1
0 0 1 0 0 0 0
0 0 0 0 0 0 0
2 3 1 1 5 5
예제 출력 2
8
아래, 아래, 오른쪽, 오른쪽, 오른쪽, 아래, 아래, 오른쪽

출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: cheetose, jh05013, mwy3055
알고리즘 분류
그래프 이론
그래프 탐색
누적 합
너비 우선 탐색
 */
/*
알고리즘 핵심
BFS
1. s_r, s_c를 기준으로 상하좌우로 움직이는 BFS를 기본으로 한다.
2. 추가된 로직은 s_r, s_c, f_r, f_c로 구성되는 직사각형의 내부에 1이 존재하거나 보드판을 넘어가는 좌표의 경우를 검사하는 것이다.
+ 움직인 직사각형에서 2번 과정을 수행하는 방법으로 2차원 배열로 내부를 검사하는 방법 or 1차원 배열로 외곽 라인이 벽에 겹치는지 검사하는 방법
or 누적합을 이용하여 입력으로 주어진 보드판을 사전에 계산해놓고 사용하는 방법
가장 빠른 방법은 누적합을 이용하여 움직인 직사각형이 벽에 닿는지를 prefix[f_r] - prefix[s_r]로 0이 아니면 벽에 닿는 것으로 판단하여 조건과
board[s_r][s_c], board[f_r][f_c]가 1이 아닌 두 조건을 검사하여 벽에 닿는지를 판단한다.

1 2 3 4 5 6 - index
0 1 0 0 0 1
이라고 가정할 때, prefix[5] - prefix[2] = 0 이지만, board[s_r][2] = 1 이므로 x
prefix[5] - prefix[3] = 0, board[s_r][5] = board[s_r][3] = 0 이므로 o

1차원 배열로 row,col로 저장하는 방법 or 2차원 배열로 저장하는 방법 모두 존재한다.
 */
public class BOJ16973 {
    static class BOJ16973_rectangle {
        int s_r,s_c,f_r,f_c,move;

        BOJ16973_rectangle(int s_r, int s_c, int f_r, int f_c, int move) {
            this.s_r = s_r;
            this.s_c = s_c;
            this.f_r = f_r;
            this.f_c = f_c;
            this.move = move;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,H,W,S_R,S_C,F_R,F_C,ans;
    static int[][] board,directions = {{-1,0},{1,0},{0,-1},{0,1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        bfs(new BOJ16973_rectangle(S_R,S_C,S_R + (H - 1), S_C + (W - 1),0));

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    private static void bfs(BOJ16973_rectangle r) {
        Queue<BOJ16973_rectangle> q = new LinkedList<>();
        boolean[][] visited = new boolean[N+1][M+1];

        q.add(r);
        visited[r.s_r][r.s_c] = true;

        while(!q.isEmpty()) {
            BOJ16973_rectangle now = q.poll();

            if(now.s_r == F_R && now.s_c == F_C) {
                ans = now.move;
                break;
            }

            for(int[] d : directions) {
                int n_s_r = now.s_r + d[0];
                int n_s_c = now.s_c + d[1];
                int n_f_r = now.f_r + d[0];
                int n_f_c = now.f_c + d[1];

                if(!rectangle_range_check(n_s_r, n_s_c, n_f_r, n_f_c) || visited[n_s_r][n_s_c]) continue;

                visited[n_s_r][n_s_c] = true;
                q.add(new BOJ16973_rectangle(n_s_r, n_s_c, n_f_r, n_f_c, now.move + 1));
            }
        }
    }

    private static boolean rectangle_range_check(int n_s_r, int n_s_c, int n_f_r, int n_f_c) {
        boolean flag = true;

        for(int r = 0; r < H; r++) {
            int nsr = n_s_r + r;
            int nfr = n_f_r - r;

            if((nsr < 1 || nsr > N || nfr < 1 || nfr > N) || (board[nsr][n_s_c] == 1 || board[nfr][n_f_c] == 1)) {
                return !flag;
            }
        }

        for(int c = 0; c < W; c++) {
            int nsc = n_s_c + c;
            int nfc = n_f_c - c;

            if((nsc < 1 || nsc > M || nfc < 1 || nfc > M) || (board[n_s_r][nsc] == 1 || board[n_f_r][nfc] == 1)) {
                return !flag;
            }
        }

        return flag;
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        ans = Integer.MAX_VALUE;

        board = new int[N+2][M+2];

        for(int r = 1; r <= N; r++) {
            input = br.readLine().split(" ");
            for(int c = 1; c <= M; c++) {
                board[r][c] = Integer.parseInt(input[c-1]);
            }
        }

        input = br.readLine().split(" ");

        H = Integer.parseInt(input[0]);
        W = Integer.parseInt(input[1]);
        S_R = Integer.parseInt(input[2]);
        S_C = Integer.parseInt(input[3]);
        F_R = Integer.parseInt(input[4]);
        F_C = Integer.parseInt(input[5]);
    }
}
