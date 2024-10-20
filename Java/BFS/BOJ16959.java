package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
체스판 여행 1

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	1559	710	464	42.336%
문제
크기가 N×N인 체스판이 있고, 체스판의 각 칸에는 1부터 N2까지의 정수가 한 번씩 적혀있다. 지학이는 이 체스판을 이용해서 재미있는 게임을 해보려고 한다.

지학이가 가지고 있는 말은 나이트, 비숍, 룩이다. 가장 먼저 1이 적혀있는 칸에 말 하나를 놓는다. 그 다음, 1, 2, ..., N2 순서로 이동시키려고 한다.

먼저, 1에 나이트, 비숍, 룩 중 하나를 놓는다. 그 다음, 말을 이동시켜서 2가 적힌 칸으로 이동시킨다. 1에서 2로 이동시킬 때, 다른 수가 적힌 칸을 방문할 수도 있다. 그 다음에는 3이 적힌 칸으로 이동시키고, ..., N2이 적힌 칸으로 이동시킨다. 같은 칸을 여러 번 방문하는 것도 가능하다.

지학이가 1초 동안 할 수 있는 행동은 체스판 위에 놓인 말을 이동시키거나, 다른 말로 바꾸는 것이다.

1에서 출발해서, 2, 3, ..., N2-1을 방문하고, N2까지 도착하는데 걸리는 시간의 최솟값을 구해보자.

입력
첫째 줄에 체스판의 크기 N(3 ≤ N ≤ 10)이 주어진다.

둘째 줄부터 N개의 줄에 체스판에 적힌 수가 주어진다.

출력
첫째 줄에 문제에 주어진 대로 방문하는데 필요한 시간의 최솟값을 출력한다.

예제 입력 1
3
1 9 3
8 6 7
4 2 5
예제 출력 1
12
예제 입력 2
3
1 5 8
9 2 4
3 6 7
예제 출력 2
12
예제 입력 3
4
5 4 1 13
8 3 6 16
15 9 14 12
11 2 7 10
예제 출력 3
23
예제 입력 4
5
21 14 2 3 12
19 8 16 18 7
9 17 10 15 4
24 5 1 23 11
25 13 22 6 20
예제 출력 4
38
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
순차적으로 1~N^2을 거쳐서 이동하는데 걸리는 시간을 요구하고 이미 도착했던 위치를 중복으로 거칠 수 있기 때문에 좌표 정보만을 가지고 방문여부를 체크하면
안된다.

따라서, 방문 여부에 추가될 변수는 N -> N+1로 넘어갈 때 방문한 여부를 체크하는 3차원 배열과 이동에 사용된 체스의 종류에 따라 방문 여부를 체크하는 4차원 배열로 검사한다.

알고리즘 핵심
BFS
1. 체스말의 종류, r,c의 좌표, 현재 위치의 정수값, 다음 이동할 정수값을 저장한 클래스를 bfs 알고리즘에 사용한다.
2. 초기에 knight, rook, bishop 세 개의 말을 추가하여 bfs를 수행한다.
3. 각 말의 위치를 시작으로 각 말의 다음 위치를 계산하여 이동한 위치의 정수값이 target와 같은지 여부를 검사한다.
4. 다음 목표 정수값과 같은 경우 visited[nr][nc][다음 목표 정수값 + 1][체스 종류]를 방문 체크하고 이동 정보를 queue에 저장한다.
5. 다음 목표 정수값과 다른 경우 현재 위치를 이동한 위치로 저장한 후 이동 정보를 queue에 저장한다.
6. 현재 위치가 N^2이고 다음 목표 정수값이 N^2 + 1에 해당하는 chess 정보가 존재할 경우 total_time을 ans에 업데이트한다.

visited의 3차원 변수값으로 다음 목표값으로 설정하였지만 현재 위치로 설정하여도 무방하다고 생각한다.
 */
public class BOJ16959 {
    static class BOJ16959_chess {
        int chess;
        int r,c;
        int cur_pos,target_pos;
        int total_time;

        BOJ16959_chess(int chess, int r, int c, int c_p, int t_p, int t_t) {
            this.chess = chess;
            this.r = r;
            this.c = c;
            this.cur_pos = c_p;
            this.target_pos = t_p;
            this.total_time = t_t;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
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
    static BOJ16959_chess start;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        bfs();

        System.out.println(ans);
    }

    private static void bfs() {
        Queue<BOJ16959_chess> q = new LinkedList<>();
        boolean[][][][] visited = new boolean[N][N][N*N + 2][3];

        for(int i = 0; i < 3; i++) {
            q.add(new BOJ16959_chess(i,start.r, start.c, start.cur_pos, start.target_pos, start.total_time));
            visited[start.r][start.c][start.target_pos][i] = true;
        }

        while(!q.isEmpty()) {
            BOJ16959_chess now = q.poll();

            if((now.cur_pos == (int) Math.pow(N,2)) && (now.target_pos == now.cur_pos + 1)) {
                ans = now.total_time;
                break;
            }

            int nr,nc;

            switch (now.chess) {
                case 0:
                    for(int[] d : chess_directions[0]) {
                        nr = now.r + d[0];
                        nc = now.c + d[1];

                        if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                        if(visited[nr][nc][now.target_pos][now.chess]) continue;

                        if(board[nr][nc] == now.target_pos) {
                            visited[nr][nc][now.target_pos + 1][now.chess] = true;
                            q.add(new BOJ16959_chess(0,nr,nc,board[nr][nc],now.target_pos + 1,now.total_time + 1));
                        } else {
                            visited[nr][nc][now.target_pos][now.chess] = true;
                            q.add(new BOJ16959_chess(0,nr,nc,board[nr][nc],now.target_pos, now.total_time + 1));
                        }
                    }

                    if(!visited[now.r][now.c][now.target_pos][1]) {
                        visited[now.r][now.c][now.target_pos][1] = true;
                        q.add(new BOJ16959_chess(1, now.r, now.c, now.cur_pos, now.target_pos, now.total_time + 1));
                    }

                    if(!visited[now.r][now.c][now.target_pos][2]) {
                        visited[now.r][now.c][now.target_pos][2] = true;
                        q.add(new BOJ16959_chess(2, now.r, now.c, now.cur_pos, now.target_pos, now.total_time + 1));
                    }
                    break;
                case 1:
                    for(int i = 1; i < N; i++) {
                        for(int[] d : chess_directions[1]) {
                            nr = now.r + i * d[0];
                            nc = now.c + i * d[1];

                            if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                            if(visited[nr][nc][now.target_pos][now.chess]) continue;

                            if(board[nr][nc] == now.target_pos) {
                                visited[nr][nc][now.target_pos + 1][now.chess] = true;
                                q.add(new BOJ16959_chess(1,nr,nc,board[nr][nc],now.target_pos + 1,now.total_time + 1));
                            } else {
                                visited[nr][nc][now.target_pos][now.chess] = true;
                                q.add(new BOJ16959_chess(1,nr,nc,board[nr][nc],now.target_pos,now.total_time + 1));
                            }
                        }
                    }

                    if(!visited[now.r][now.c][now.target_pos][0]) {
                        visited[now.r][now.c][now.target_pos][0] = true;
                        q.add(new BOJ16959_chess(0, now.r, now.c, now.cur_pos, now.target_pos, now.total_time + 1));
                    }

                    if(!visited[now.r][now.c][now.target_pos][2]) {
                        visited[now.r][now.c][now.target_pos][2] = true;
                        q.add(new BOJ16959_chess(2, now.r, now.c, now.cur_pos, now.target_pos, now.total_time + 1));
                    }
                    break;
                case 2:
                    for(int i = 1; i < N; i++) {
                        for(int[] d : chess_directions[2]) {
                            nr = now.r + i * d[0];
                            nc = now.c + i * d[1];

                            if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                            if(visited[nr][nc][now.target_pos][now.chess]) continue;

                            visited[nr][nc][now.target_pos][now.chess] = true;
                            if(board[nr][nc] == now.target_pos) {
                                q.add(new BOJ16959_chess(2,nr,nc,board[nr][nc],now.target_pos + 1,now.total_time + 1));
                            } else {
                                q.add(new BOJ16959_chess(2,nr,nc,board[nr][nc],now.target_pos,now.total_time + 1));
                            }
                        }
                    }
                    if(!visited[now.r][now.c][now.target_pos][0]) {
                        visited[now.r][now.c][now.target_pos][0] = true;
                        q.add(new BOJ16959_chess(0, now.r, now.c, now.cur_pos, now.target_pos, now.total_time + 1));
                    }

                    if(!visited[now.r][now.c][now.target_pos][1]) {
                        visited[now.r][now.c][now.target_pos][1] = true;
                        q.add(new BOJ16959_chess(1, now.r, now.c, now.cur_pos, now.target_pos, now.total_time + 1));
                    }
                    break;
            }
        }
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        board = new int[N][N];

        ans = Integer.MAX_VALUE;

        for(int r = 0; r < N; r++) {
            String[] input = br.readLine().split(" ");
            for(int c = 0; c < N; c++) {
                board[r][c] = Integer.parseInt(input[c]);

                if(board[r][c] == 1) {
                    start = new BOJ16959_chess(-1,r,c,1,2,0);
                }
            }
        }
    }
}
