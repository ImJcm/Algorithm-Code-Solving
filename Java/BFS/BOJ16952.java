package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/*
체스판 여행 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	666	260	165	41.250%
문제
크기가 N×N인 체스판이 있고, 체스판의 각 칸에는 1부터 N2까지의 정수가 한 번씩 적혀있다. 지학이는 이 체스판을 이용해서 재미있는 게임을 해보려고 한다.

지학이가 가지고 있는 말은 나이트, 비숍, 룩이다. 가장 먼저 1이 적혀있는 칸에 말 하나를 놓는다. 그 다음, 1, 2, ..., N2 순서로 이동시키려고 한다.

먼저, 1에 나이트, 비숍, 룩 중 하나를 놓는다. 그 다음, 말을 이동시켜서 2가 적힌 칸으로 이동시킨다. 1에서 2로 이동시킬 때, 다른 수가 적힌 칸을 방문할 수도 있다. 그 다음에는 3이 적힌 칸으로 이동시키고, ..., N2이 적힌 칸으로 이동시킨다. 같은 칸을 여러 번 방문하는 것도 가능하다.

지학이가 1초 동안 할 수 있는 행동은 체스판 위에 놓인 말을 이동시키거나, 다른 말로 바꾸는 것이다.

1에서 출발해서, 2, 3, ..., N2-1을 방문하고, N2까지 도착하는데 걸리는 시간의 최솟값을 구해보자. 최소 시간이 나오는 방법이 여러가지인 경우에는 말을 바꾸는 횟수를 최소로 하자.

입력
첫째 줄에 체스판의 크기 N(3 ≤ N ≤ 10)이 주어진다.

둘째 줄부터 N개의 줄에 체스판에 적힌 수가 주어진다.

출력
첫째 줄에 문제에 주어진 대로 방문하는데 필요한 시간의 최솟값과 말을 교체한 횟수를 출력한다.

예제 입력 1
3
1 9 3
8 6 7
4 2 5
예제 출력 1
12 1
나이트를 놓고 시작한다.

(3, 2)로 이동한다. (2 방문)
(1, 3)으로 이동한다. (3 방문)
(3, 2)로 이동한다.
나이트를 룩으로 바꾼다.
(3, 1)으로 이동한다. (4 방문)
(3, 3)으로 이동한다. (5 방문)
(3, 2)로 이동한다.
(2, 2)로 이동한다. (6 방문)
(2, 3)으로 이동한다. (7 방문)
(2, 1)로 이동한다. (8 방문)
(1, 1)로 이동한다.
(1, 2)로 이동한다. (9 방문)
예제 입력 2
3
1 5 8
9 2 4
3 6 7
예제 출력 2
12 1
예제 입력 3
4
5 4 1 13
8 3 6 16
15 9 14 12
11 2 7 10
예제 출력 3
23 0
예제 입력 4
5
21 14 2 3 12
19 8 16 18 7
9 17 10 15 4
24 5 1 23 11
25 13 22 6 20
예제 출력 4
38 2
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
최단 경로
플로이드–워셜
 */
public class BOJ16952 {
    static class BOJ16952_chess {
        int chess;
        int r,c;
        int cur_pos,target_pos;
        int total_time;
        int move_cnt;

        BOJ16952_chess(int chess, int r, int c, int c_p, int t_p, int t_t, int m_c) {
            this.chess = chess;
            this.r = r;
            this.c = c;
            this.cur_pos = c_p;
            this.target_pos = t_p;
            this.total_time = t_t;
            this.move_cnt = m_c;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans_time,ans_change_cnt;
    static int[][] board;
    static int[][][] chess_directions = {
            {   // knight
                {-1,-2}, {1,-2}, {-2,-1}, {-2,1}, {-1,2}, {1,2}, {2,-1}, {2,1}
            }, {    // rook
                {-1,0}, {1,0}, {0,-1}, {0,1}
            }, {    // bishop
                {-1,-1}, {-1,1}, {1,-1}, {1,1}
            }
    };
    static BOJ16952_chess start;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        bfs();

        System.out.println(ans_time + " " + ans_change_cnt);
    }

    private static void bfs() {
        Queue<BOJ16952_chess> q = new LinkedList<>();
        int[][][][] visited = new int[N][N][N*N + 2][3];

        for(int r = 0; r < N; r++) {
            for(int c = 0; c < N; c++) {
                for(int n = 1; n <= N*N + 1; n++) {
                    for(int k = 0; k < 3; k++) {
                        visited[r][c][n][k] = Integer.MAX_VALUE;
                    }
                }
            }
        }

        for(int i = 0; i < 3; i++) {
            q.add(new BOJ16952_chess(i,start.r, start.c, start.cur_pos, start.target_pos, start.total_time,start.move_cnt));
            visited[start.r][start.c][start.target_pos][i] = start.move_cnt;
        }

        while(!q.isEmpty()) {
            BOJ16952_chess now = q.poll();

            if((now.cur_pos == (int) Math.pow(N,2)) && (now.target_pos == now.cur_pos + 1)) {
                if(ans_time > now.total_time) {
                    ans_time = now.total_time;
                    ans_change_cnt = now.move_cnt;
                } else if(ans_time == now.total_time) {
                    if(ans_change_cnt > now.move_cnt) {
                        ans_change_cnt = now.move_cnt;
                    }
                }
                continue;
            }

            int nr,nc;

            for(int c = 0; c < 3; c++) {
                if(c == now.chess) continue;
                if(visited[now.r][now.c][now.target_pos][now.chess] == Integer.MAX_VALUE || visited[now.r][now.c][now.target_pos][now.chess] > now.move_cnt) {
                    visited[now.r][now.c][now.target_pos][now.chess] = now.move_cnt + 1;
                    q.add(new BOJ16952_chess(c,now.r,now.c,now.cur_pos,now.target_pos,now.total_time + 1,now.move_cnt + 1));
                }
            }

            switch (now.chess) {
                case 0:
                    for(int[] d : chess_directions[0]) {
                        nr = now.r + d[0];
                        nc = now.c + d[1];

                        if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;

                        int next_target = now.target_pos;

                        if(board[nr][nc] == now.target_pos) {
                            next_target = now.target_pos + 1;
                        }

                        if(visited[nr][nc][next_target][now.chess] == Integer.MAX_VALUE || visited[nr][nc][next_target][now.chess] > now.move_cnt) {
                            visited[nr][nc][next_target][now.chess] = now.move_cnt;
                            q.add(new BOJ16952_chess(now.chess,nr,nc,board[nr][nc],next_target,now.total_time + 1,now.move_cnt));
                        }
                    }
                    break;
                case 1:
                    for(int i = 1; i < N; i++) {
                        for(int[] d : chess_directions[1]) {
                            nr = now.r + i * d[0];
                            nc = now.c + i * d[1];

                            if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;

                            int next_target = now.target_pos;

                            if(board[nr][nc] == now.target_pos) {
                                next_target = now.target_pos + 1;
                            }

                            if(visited[nr][nc][next_target][now.chess] == Integer.MAX_VALUE || visited[nr][nc][next_target][now.chess] > now.move_cnt) {
                                visited[nr][nc][next_target][now.chess] = now.move_cnt;
                                q.add(new BOJ16952_chess(now.chess,nr,nc,board[nr][nc],next_target,now.total_time + 1,now.move_cnt));
                            }
                        }
                    }
                    break;
                case 2:
                    for(int i = 1; i < N; i++) {
                        for(int[] d : chess_directions[2]) {
                            nr = now.r + i * d[0];
                            nc = now.c + i * d[1];

                            if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;

                            int next_target = now.target_pos;

                            if(board[nr][nc] == now.target_pos) {
                                next_target = now.target_pos + 1;
                            }

                            if(visited[nr][nc][next_target][now.chess] == Integer.MAX_VALUE || visited[nr][nc][next_target][now.chess] > now.move_cnt) {
                                visited[nr][nc][next_target][now.chess] = now.move_cnt;
                                q.add(new BOJ16952_chess(now.chess,nr,nc,board[nr][nc],next_target,now.total_time + 1,now.move_cnt));
                            }
                        }
                    }
                    break;
            }
        }
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        board = new int[N][N];

        ans_time = Integer.MAX_VALUE;
        ans_change_cnt = Integer.MAX_VALUE;

        for(int r = 0; r < N; r++) {
            String[] input = br.readLine().split(" ");
            for(int c = 0; c < N; c++) {
                board[r][c] = Integer.parseInt(input[c]);

                if(board[r][c] == 1) {
                    start = new BOJ16952_chess(-1,r,c,1,2,0,0);
                }
            }
        }
    }
}