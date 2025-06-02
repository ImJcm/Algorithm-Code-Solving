package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
섬의 개수 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	80324	41256	29622	50.242%
문제
정사각형으로 이루어져 있는 섬과 바다 지도가 주어진다. 섬의 개수를 세는 프로그램을 작성하시오.



한 정사각형과 가로, 세로 또는 대각선으로 연결되어 있는 사각형은 걸어갈 수 있는 사각형이다.

두 정사각형이 같은 섬에 있으려면, 한 정사각형에서 다른 정사각형으로 걸어서 갈 수 있는 경로가 있어야 한다. 지도는 바다로 둘러싸여 있으며, 지도 밖으로 나갈 수 없다.

입력
입력은 여러 개의 테스트 케이스로 이루어져 있다. 각 테스트 케이스의 첫째 줄에는 지도의 너비 w와 높이 h가 주어진다. w와 h는 50보다 작거나 같은 양의 정수이다.

둘째 줄부터 h개 줄에는 지도가 주어진다. 1은 땅, 0은 바다이다.

입력의 마지막 줄에는 0이 두 개 주어진다.

출력
각 테스트 케이스에 대해서, 섬의 개수를 출력한다.

예제 입력 1
1 1
0
2 2
0 1
1 0
3 2
1 1 1
1 1 1
5 4
1 0 1 0 0
1 0 0 0 0
1 0 1 0 1
1 0 0 1 0
5 4
1 1 1 0 1
1 0 1 0 1
1 0 1 0 1
1 0 1 1 1
5 5
1 0 1 0 1
0 0 0 0 0
1 0 1 0 1
0 0 0 0 0
1 0 1 0 1
0 0
예제 출력 1
0
1
1
3
1
9
출처
ICPC > Regionals > Asia Pacific > Japan > Japan Domestic Contest > 2009 Japan Domestic Contest B번

문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: j4bez
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
깊이 우선 탐색
격자 그래프
플러드 필
 */
/*
알고리즘 핵심
그래프 + dfs (+bfs, FloodFill)
1. 모든 (h,w)를 기준으로 상,하,좌,우,대각 방향으로 인접한 섬을 탐색한다.
2. dfs,bfs - 시작지점이 섬인 경우 ans를 1 증가시킨 상태로 인접한 섬을 방문상태를 업데이트한다. / 섬이 아닌 경우 다음 좌표로 넘어간다.
floodFill - 시작지점이 섬인 경우 idx를 1 증가시킨 상태로 인접한 섬을 idx값으로 지정한다. / 섬이 아닌 경우 다음 좌표로 넘어간다.
3. dfs,bfs = ans, floodFill = idx를 출력한다.
 */
public class BOJ4963 {
    static class BOJ4963_pos {
        int h,w;

        BOJ4963_pos(int h, int w) {
            this.h = h;
            this.w = w;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int W,H,ans,idx;
    static int[][] direction = {{-1,0},{1,0},{0,1},{0,-1},{-1,-1},{-1,1},{1,-1},{1,1}};
    static boolean[][] visited;
    static int[][] map, indexedMap;

    public static void main(String[] args) throws IOException {
        while(true) {
            String[] input = br.readLine().split(" ");

            W = Integer.parseInt(input[0]);
            H = Integer.parseInt(input[1]);

            if(W == 0 && H == 0) return;

            init_setting();

            solve();
        }
    }

    private static void solve() {
        for(int h = 0; h < H; h++) {
            for(int w = 0; w < W; w++) {
                if(visited[h][w] || map[h][w] == 0) continue;

                if(map[h][w] == 1) ans += 1;
                visited[h][w] = true;
                dfs(new BOJ4963_pos(h,w));
                bfs(new BOJ4963_pos(h,w));
                FloodFill(new BOJ4963_pos(h,w),++idx);
            }
        }

        System.out.println(ans);
        System.out.println(idx);
    }

    private static void dfs(BOJ4963_pos m) {
        for(int[] d : direction) {
            int nh = m.h + d[0];
            int nw = m.w + d[1];

            if(nw < 0 || nw >= W || nh < 0 || nh >= H) continue;
            if(visited[nh][nw] || map[nh][nw] == 0) continue;

            visited[nh][nw] = true;
            dfs(new BOJ4963_pos(nh,nw));
        }
    }

    private static void bfs(BOJ4963_pos m) {
        Queue<BOJ4963_pos> q = new LinkedList<>();

        q.add(m);
        visited[m.h][m.w] = true;

        while(!q.isEmpty()) {
            BOJ4963_pos now = q.poll();

            for(int[] d : direction) {
                int nh = now.h + d[0];
                int nw = now.w + d[1];

                if(nw < 0 || nw >= W || nh < 0 || nh >= H) continue;
                if(visited[nh][nw] || map[nh][nw] == 0) continue;

                visited[nh][nw] = true;
                q.add(new BOJ4963_pos(nh,nw));
            }
        }
    }

    private static void FloodFill(BOJ4963_pos m, int idx) {
        Queue<BOJ4963_pos> q = new LinkedList<>();

        q.add(m);
        visited[m.h][m.w] = true;
        indexedMap[m.h][m.w] = idx;

        while(!q.isEmpty()) {
            BOJ4963_pos now = q.poll();

            for(int[] d : direction) {
                int nh = now.h + d[0];
                int nw = now.w + d[1];

                if(nw < 0 || nw >= W || nh < 0 || nh >= H) continue;
                if(visited[nh][nw] || map[nh][nw] == 0) continue;

                visited[nh][nw] = true;
                indexedMap[nh][nw] = idx;
                q.add(new BOJ4963_pos(nh,nw));
            }
        }
    }

    private static void init_setting() throws IOException {
        map = new int[H][W];

        visited = new boolean[H][W];

        for(int h = 0; h < H; h++) {
            map[h] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        ans = 0;

        indexedMap = new int[H][W];
        idx = 0;
    }
}
