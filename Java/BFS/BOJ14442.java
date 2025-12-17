package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
벽 부수고 이동하기 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	29332	8067	5494	27.029%
문제
N×M의 행렬로 표현되는 맵이 있다. 맵에서 0은 이동할 수 있는 곳을 나타내고, 1은 이동할 수 없는 벽이 있는 곳을 나타낸다. 당신은 (1, 1)에서 (N, M)의 위치까지 이동하려 하는데, 이때 최단 경로로 이동하려 한다. 최단경로는 맵에서 가장 적은 개수의 칸을 지나는 경로를 말하는데, 이때 시작하는 칸과 끝나는 칸도 포함해서 센다.

만약에 이동하는 도중에 벽을 부수고 이동하는 것이 좀 더 경로가 짧아진다면, 벽을 K개 까지 부수고 이동하여도 된다.

한 칸에서 이동할 수 있는 칸은 상하좌우로 인접한 칸이다.

맵이 주어졌을 때, 최단 경로를 구해 내는 프로그램을 작성하시오.

입력
첫째 줄에 N(1 ≤ N ≤ 1,000), M(1 ≤ M ≤ 1,000), K(1 ≤ K ≤ 10)이 주어진다. 다음 N개의 줄에 M개의 숫자로 맵이 주어진다. (1, 1)과 (N, M)은 항상 0이라고 가정하자.

출력
첫째 줄에 최단 거리를 출력한다. 불가능할 때는 -1을 출력한다.

예제 입력 1
6 4 1
0100
1110
1000
0000
0111
0000
예제 출력 1
15
예제 입력 2
6 4 2
0100
1110
1000
0000
0111
0000
예제 출력 2
9
예제 입력 3
4 4 3
0111
1111
1111
1110
예제 출력 3
-1
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: jh05013, sait2000, YunGoon
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
boj2206을 응용하는 문제로 1번의 벽을 부술 수 있는 로직에서 K개 까지 벽을 부술 수 있으므로 visited[N+1][M+1][K+1]로 방문여부를 체크한다.
 */
public class BOJ14442 {
    static class BOJ14442_map {
        int r,c;
        int wall;
        int move;
        int broken_wall_cnt;

        BOJ14442_map(int r, int c, int wall) {
            this.r = r;
            this.c = c;
            this.wall = wall;
        }

        BOJ14442_map(int r, int c, int move, int broken_wall_cnt) {
            this.r = r;
            this.c = c;
            this.move = move;
            this.broken_wall_cnt = broken_wall_cnt;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,K;
    static boolean[][][] visited;
    static BOJ14442_map[][] maps;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        System.out.println(bfs());
    }

    static int bfs() {
        Queue<BOJ14442_map> q = new LinkedList<>();

        q.offer(maps[1][1]);
        visited[1][1][0] = true;
        maps[1][1].move = 1;
        maps[1][1].broken_wall_cnt = 0;

        while(!q.isEmpty()) {
            BOJ14442_map now = q.poll();

            if(now.r == N && now.c == M) {
                return now.move;
            }

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 1 || nr > N || nc < 1 || nc > M) continue;

                /*if(now.broken_wall_cnt == 0) {
                    *//*if(maps[nr][nc].wall == 0) {
                        if(visited[nr][nc][0]) continue;
                        visited[nr][nc][0] = true;
                        q.offer(new BOJ14442_map(nr,nc,now.move + 1, now.broken_wall_cnt));
                    } else {
                        if(visited[nr][nc][1]) continue;
                        visited[nr][nc][1] = true;
                        q.offer(new BOJ14442_map(nr,nc,now.move + 1, now.broken_wall_cnt + 1));
                    }*//*
                    int broke_status = maps[nr][nc].wall == 1 ? 1 : 0;
                    if(visited[nr][nc][0]) continue;
                    visited[nr][nc][broke_status] = true;
                    q.offer(new BOJ14442_map(nr,nc,now.move + 1, broke_status));
                } else if(now.broken_wall_cnt != 0 && now.broken_wall_cnt <= K) {
                    int broke_status = maps[nr][nc].wall == 1 ? 1 : 0;
                    if(now.broken_wall_cnt + broke_status > K || visited[nr][nc][now.broken_wall_cnt + broke_status]) continue;
                    visited[nr][nc][now.broken_wall_cnt + broke_status] = true;
                    q.offer(new BOJ14442_map(nr,nc,now.move + 1, now.broken_wall_cnt + broke_status));
                }*/
                //다음 이동 구역이 벽인지 빈공간인지 체크
                int broke_status = maps[nr][nc].wall == 1 ? 1 : 0;
                //벽을 부순 횟수와 다음 공간이 벽인지 빈공간인지 값을 더한 것이 K를 넘어가거나 방문한 곳이라면 continue
                if(now.broken_wall_cnt + broke_status > K || visited[nr][nc][now.broken_wall_cnt + broke_status]) continue;
                //벽을 부숴서 이동하거나 벽이 아닌 곳이 방문하지 않은 상태에서 이동하는 경우 해당 구역을 방문처리한다.
                visited[nr][nc][now.broken_wall_cnt + broke_status] = true;
                //이동거리를 +1하고 벽을 부순 여부에 따른 값으로 설정하여 다음 queue에 새로운 BOJ14442_map 객체를 삽입한다.
                q.offer(new BOJ14442_map(nr,nc,now.move + 1, now.broken_wall_cnt + broke_status));
            }
        }
        return -1;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        K = Integer.parseInt(input[2]);

        visited = new boolean[N+1][M+1][K+1];
        maps = new BOJ14442_map[N+1][M+1];

        for(int i=1;i<=N;i++) {
            input = br.readLine().split("");
            for(int j=1;j<=M;j++) {
                maps[i][j] = new BOJ14442_map(i,j,Integer.parseInt(input[j-1]));
                for(int k=0;k<=K;k++) {
                    visited[i][j][k] = false;
                }
            }
        }

    }
}
