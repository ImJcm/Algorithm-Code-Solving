package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
다리 만들기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	192 MB	51119	19673	12370	35.264%
문제
여러 섬으로 이루어진 나라가 있다. 이 나라의 대통령은 섬을 잇는 다리를 만들겠다는 공약으로 인기몰이를 해 당선될 수 있었다. 하지만 막상 대통령에 취임하자, 다리를 놓는다는 것이 아깝다는 생각을 하게 되었다. 그래서 그는, 생색내는 식으로 한 섬과 다른 섬을 잇는 다리 하나만을 만들기로 하였고, 그 또한 다리를 가장 짧게 하여 돈을 아끼려 하였다.

이 나라는 N×N크기의 이차원 평면상에 존재한다. 이 나라는 여러 섬으로 이루어져 있으며, 섬이란 동서남북으로 육지가 붙어있는 덩어리를 말한다. 다음은 세 개의 섬으로 이루어진 나라의 지도이다.



위의 그림에서 색이 있는 부분이 육지이고, 색이 없는 부분이 바다이다. 이 바다에 가장 짧은 다리를 놓아 두 대륙을 연결하고자 한다. 가장 짧은 다리란, 다리가 격자에서 차지하는 칸의 수가 가장 작은 다리를 말한다. 다음 그림에서 두 대륙을 연결하는 다리를 볼 수 있다.



물론 위의 방법 외에도 다리를 놓는 방법이 여러 가지 있으나, 위의 경우가 놓는 다리의 길이가 3으로 가장 짧다(물론 길이가 3인 다른 다리를 놓을 수 있는 방법도 몇 가지 있다).

지도가 주어질 때, 가장 짧은 다리 하나를 놓아 두 대륙을 연결하는 방법을 찾으시오.

입력
첫 줄에는 지도의 크기 N(100이하의 자연수)가 주어진다. 그 다음 N줄에는 N개의 숫자가 빈칸을 사이에 두고 주어지며, 0은 바다, 1은 육지를 나타낸다. 항상 두 개 이상의 섬이 있는 데이터만 입력으로 주어진다.

출력
첫째 줄에 가장 짧은 다리의 길이를 출력한다.

예제 입력 1
10
1 1 1 0 0 0 0 1 1 1
1 1 1 1 0 0 0 0 1 1
1 0 1 1 0 0 0 0 1 1
0 0 1 1 1 0 0 0 0 1
0 0 0 1 0 0 0 0 0 1
0 0 0 0 0 0 0 0 0 1
0 0 0 0 0 0 0 0 0 0
0 0 0 0 1 1 0 0 0 0
0 0 0 0 1 1 1 0 0 0
0 0 0 0 0 0 0 0 0 0
예제 출력 1
3
출처
빠진 조건을 찾은 사람: choiking10
데이터를 추가한 사람: hello70825, ohyuni
잘못된 데이터를 찾은 사람: tncks0121
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
격자 그래프
 */
/*
알고리즘 핵심
BFS
1. 입력으로 주어진 섬의 위치에서 각각의 섬을 구분하기 위한 BFS를 수행한다. (search_islands())
2. 1번의 결과로 섬을 구분하여 인덱싱한 결과를 저장한 배열을 만든다. (indexed_map)
3. (r,c)의 모든 좌표를 기준으로 해당 좌표가 섬이고, 외곽에 해당하여 바다로 다리를 건설할 수 있는 경우 바다를 통해 다리를 잇는
BFS를 기준이 되는 좌표의 섬 번호가 아닌 다른 섬에 도달할 때까지 수행한다.
이때, BFS의 경우 다른 섬의 번호에 최초로 도달할 경우가 최소 다리 개수를 의미하므로 ans에 최소 다리 개수를 업데이트한다.
(다른 섬에 도달 시, 다리 개수가 하나 더 추가되므로 cnt - 1개가 실제 필요한 다리 개수이다.)
4. ans를 출력한다.
 */
public class BOJ2146 {
    static class BOJ2146_pos {
        int r,c;

        BOJ2146_pos(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static class BOJ2146_bridge {
        int r,c;
        int cnt;

        BOJ2146_bridge(int r, int c, int cnt) {
            this.r = r;
            this.c = c;
            this.cnt = cnt;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static int[][] map, indexed_map, direction = {{-1,0},{1,0},{0,-1},{0,1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        search_islands();

        connect_bridge_between_islands();

        System.out.println(ans);
    }

    private static void search_islands() {
        int idx = 1;
        for(int r = 0; r < N; r++) {
            for(int c = 0; c < N; c++) {
                if(map[r][c] == 0 || indexed_map[r][c] > 0) continue;
                bfs_search_islands(new BOJ2146_pos(r,c), idx++);
            }
        }
    }

    private static void bfs_search_islands(BOJ2146_pos p, int idx) {
        Queue<BOJ2146_pos> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];

        q.add(p);
        visited[p.r][p.c] = true;
        indexed_map[p.r][p.c] = idx;

        while(!q.isEmpty()) {
            BOJ2146_pos now = q.poll();

            int nr,nc;

            for(int[] d : direction) {
                nr = now.r + d[0];
                nc = now.c + d[1];

                if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                if(visited[nr][nc] || map[nr][nc] == 0) continue;

                visited[nr][nc] = true;
                indexed_map[nr][nc] = idx;
                q.add(new BOJ2146_pos(nr,nc));
            }
        }
    }

    private static void connect_bridge_between_islands() {
        for(int r = 0; r < N; r++) {
            for(int c = 0; c < N; c++) {
                if(indexed_map[r][c] == 0) continue;
                bfs_connect_bridge_between_islands(new BOJ2146_bridge(r,c,0),indexed_map[r][c]);
            }
        }
    }

    private static void bfs_connect_bridge_between_islands(BOJ2146_bridge b, int idx) {
        Queue<BOJ2146_bridge> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];

        q.add(b);
        visited[b.r][b.c] = true;

        while(!q.isEmpty()) {
            BOJ2146_bridge now = q.poll();

            if(indexed_map[now.r][now.c] != idx && indexed_map[now.r][now.c] != 0) {
                ans = Math.min(ans, now.cnt - 1);
                return;
            }

            int nr,nc;

            for(int[] d : direction) {
                nr = now.r + d[0];
                nc = now.c + d[1];

                if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                if(visited[nr][nc] || indexed_map[nr][nc] == idx) continue;

                visited[nr][nc] = true;
                q.add(new BOJ2146_bridge(nr,nc,now.cnt + 1));
            }
        }
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        map = new int[N][N];

        for(int i = 0; i < N; i++) {
            map[i] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        indexed_map = new int[N][N];

        ans = Integer.MAX_VALUE;
    }
}
