package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
벽 부수고 이동하기 3

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	16265	3950	2615	23.461%
문제
N×M의 행렬로 표현되는 맵이 있다. 맵에서 0은 이동할 수 있는 곳을 나타내고, 1은 이동할 수 없는 벽이 있는 곳을 나타낸다. 당신은 (1, 1)에서 (N, M)의 위치까지 이동하려 하는데, 이때 최단 경로로 이동하려 한다. 최단경로는 맵에서 가장 적은 개수의 칸을 지나는 경로를 말하는데, 이때 시작하는 칸과 끝나는 칸도 포함해서 센다. 이동하지 않고 같은 칸에 머물러있는 경우도 가능하다. 이 경우도 방문한 칸의 개수가 하나 늘어나는 것으로 생각해야 한다.

이번 문제에서는 낮과 밤이 번갈아가면서 등장한다. 가장 처음에 이동할 때는 낮이고, 한 번 이동할 때마다 낮과 밤이 바뀌게 된다. 이동하지 않고 같은 칸에 머무르는 경우에도 낮과 밤이 바뀌게 된다.

만약에 이동하는 도중에 벽을 부수고 이동하는 것이 좀 더 경로가 짧아진다면, 벽을 K개 까지 부수고 이동하여도 된다. 단, 벽은 낮에만 부술 수 있다.

한 칸에서 이동할 수 있는 칸은 상하좌우로 인접한 칸이다.

맵이 주어졌을 때, 최단 경로를 구해 내는 프로그램을 작성하시오.

입력
첫째 줄에 N(1 ≤ N ≤ 1,000), M(1 ≤ M ≤ 1,000), K(1 ≤ K ≤ 10)이 주어진다. 다음 N개의 줄에 M개의 숫자로 맵이 주어진다. (1, 1)과 (N, M)은 항상 0이라고 가정하자.

출력
첫째 줄에 최단 거리를 출력한다. 불가능할 때는 -1을 출력한다.

예제 입력 1
1 4 1
0010
예제 출력 1
5
예제 입력 2
1 4 1
0100
예제 출력 2
4
예제 입력 3
6 4 1
0100
1110
1000
0000
0111
0000
예제 출력 3
15
예제 입력 4
6 4 2
0100
1110
1000
0000
0111
0000
예제 출력 4
9
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: jh05013
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
BOJ14442를 응용한 코드에서 낮/밤을 설정하는 값을 BOJ16933_map 객체에 추가하여 BFS를 수행한다.

추가로 밤인 경우, 다음 이동 공간이 벽인지 빈공간인지에 따라 제자리 이동할지 다음 공간으로 이동할지 new BOJ16933 객체의 (now.r,now.c,...) or (nr,nc,...)로 queue 삽입할 객체가 결정된다.
 */
public class BOJ16933 {
    static class BOJ16933_map {
        int r,c;
        int wall;
        int broken_wall;
        int move;
        boolean day_and_night;

        BOJ16933_map(int r, int c, int move, int b_w, boolean d_a_n) {
            this.r = r;
            this.c = c;
            this.move = move;
            this.broken_wall = b_w;
            this.day_and_night =d_a_n;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,K;
    static boolean[][][] visited;
    static BOJ16933_map[][] maps;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        System.out.println(bfs());
    }

    static int bfs() {
        Queue<BOJ16933_map> q = new LinkedList<>();

        maps[1][1].move = 1;
        visited[1][1][0] = true;
        q.offer(maps[1][1]);

        while(!q.isEmpty()) {
            BOJ16933_map now = q.poll();

            if(now.r == N && now.c == M) {
                return now.move;
            }

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 1 || nr > N || nc < 1 || nc > M) continue;

                //낮인 경우
                if(now.day_and_night) {
                    //다음 이동 공간이 벽인지 아닌지 검사
                    int broken_status = maps[nr][nc].wall == 1 ? 1 : 0;
                    //벽인지 빈공간인지 검사 결과 값 + 이전 공간의 벽을 부순 횟수가 K가 넘을 경우와 벽인지 빈공간인지 여부에 따라 다음 이동 공간이 방문한 경우 continue
                    if(now.broken_wall + broken_status > K || visited[nr][nc][now.broken_wall + broken_status]) continue;
                    visited[nr][nc][now.broken_wall + broken_status] = true;
                    q.offer(new BOJ16933_map(nr,nc, now.move + 1, now.broken_wall + broken_status, !now.day_and_night));
                } else {
                    //밤인 경우
                    //다음으로 이동할 구역이 벽이면
                    if(maps[nr][nc].wall == 1) {
                        //방문여부 상관없이 밤의 경우 벽을 부술 수 없으므로, 제자리 이동
                        q.offer(new BOJ16933_map(now.r, now.c, now.move + 1, now.broken_wall, !now.day_and_night));
                    } else {
                        //다음으로 이동할 구역이 빈공간이면
                        //벽을 부순횟수에서 이미 방문한 구역이면 continue
                        if(visited[nr][nc][now.broken_wall]) continue;
                        visited[nr][nc][now.broken_wall] = true;
                        q.offer(new BOJ16933_map(nr,nc, now.move + 1, now.broken_wall, !now.day_and_night));
                    }
                }
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
        maps = new BOJ16933_map[N+1][M+1];

        for(int i=1;i<=N;i++) {
            input = br.readLine().split("");
            for(int j=1;j<=M;j++) {
                maps[i][j] = new BOJ16933_map(i,j,0,0,true);
                maps[i][j].wall = Integer.parseInt(input[j-1]);
                for(int k=0;k<=K;k++) {
                    visited[i][j][k] = false;
                }
            }
        }
    }
}
