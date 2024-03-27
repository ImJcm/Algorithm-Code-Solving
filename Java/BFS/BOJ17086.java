package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
아기 상어 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	11298	5551	4166	47.319%
문제
N×M 크기의 공간에 아기 상어 여러 마리가 있다. 공간은 1×1 크기의 정사각형 칸으로 나누어져 있다. 한 칸에는 아기 상어가 최대 1마리 존재한다.

어떤 칸의 안전 거리는 그 칸과 가장 거리가 가까운 아기 상어와의 거리이다. 두 칸의 거리는 하나의 칸에서 다른 칸으로 가기 위해서 지나야 하는 칸의 수이고, 이동은 인접한 8방향(대각선 포함)이 가능하다.

안전 거리가 가장 큰 칸을 구해보자.

입력
첫째 줄에 공간의 크기 N과 M(2 ≤ N, M ≤ 50)이 주어진다. 둘째 줄부터 N개의 줄에 공간의 상태가 주어지며, 0은 빈 칸, 1은 아기 상어가 있는 칸이다. 빈 칸과 상어의 수가 각각 한 개 이상인 입력만 주어진다.

출력
첫째 줄에 안전 거리의 최댓값을 출력한다.

예제 입력 1
5 4
0 0 1 0
0 0 0 0
1 0 0 0
0 0 0 0
0 0 0 1
예제 출력 1
2
예제 입력 2
7 4
0 0 0 1
0 1 0 0
0 0 0 0
0 0 0 1
0 0 0 0
0 1 0 0
0 0 0 1
예제 출력 2
2
출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: jh05013
데이터를 추가한 사람: jyn47
알고리즘 분류
그래프 이론
브루트포스 알고리즘
그래프 탐색
너비 우선 탐색
 */
/*
처음 문제를 읽고 상어가 위치한 곳에서 BFS를 돌려보고 space에 업데이트된 safe_distance 중 최대값을 반환하는 방법을 생각했다.
하지만, 메모리 초과가 발생하였다.

다시 생각해보니 굳이 모든 상어의 위치에서 bfs를 수행할 필요없이 한번의 bfs로 queue에 상어들의 위치를 처음부터 넣은 상태로 bfs를 돌리고 visited
를 적용하면 메모리초과문제를 해결할 수 있을 것 같았다.

예상대로 정답!

중요한 점: 상어가 위치한 곳을 기점으로 bfs를 수행하면 해당 위치는 가장 최소 값을 갖는 위치를 보장하기 때문에 visited를 추가하여 방문여부를 검사하여
중복된 위치를 재방문하지 않도록하는 것과 상어가 위치한 곳을 한번의 BFS Queue에 저장하여 bfs 과정을 수행하는 것이다.
 */

public class BOJ17086 {
    static class BOJ17086_space {
        int r,c;
        int shark;
        int safe_distance;

        BOJ17086_space(int r, int c, int s_d, int s) {
            this.r = r;
            this.c = c;
            this.safe_distance = s_d;
            this.shark = s;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,max_safe_distance = 0;
    static boolean[][] visited;
    static int[][] direction = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
    static ArrayList<BOJ17086_space> shark_spaces;
    static BOJ17086_space[][] spaces;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        bfs();

        check_max_safe_distance();
    }

    static void check_max_safe_distance() {
        for(int i=1;i<=N;i++) {
            for(int j=1;j<=M;j++) {
                max_safe_distance = Math.max(spaces[i][j].safe_distance, max_safe_distance);
            }
        }

        System.out.println(max_safe_distance);
    }

    static void bfs() {
        Queue<BOJ17086_space> q = new LinkedList<>();

        for(BOJ17086_space s_s : shark_spaces) {
            q.offer(s_s);
        }

        while(!q.isEmpty()) {
            BOJ17086_space now = q.poll();

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 1 || nr > N || nc < 1 || nc > M) continue;
                if(spaces[nr][nc].shark == 1 || visited[nr][nc]) continue;

                spaces[nr][nc].safe_distance = now.safe_distance + 1;
                visited[nr][nc] = true;
                q.offer(spaces[nr][nc]);
            }
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");
        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        spaces = new BOJ17086_space[N+1][M+1];
        visited = new boolean[N+1][M+1];
        shark_spaces = new ArrayList<>();

        for(int i=1;i<=N;i++) {
            input = br.readLine().split(" ");
            for(int j=1;j<=M;j++) {
                spaces[i][j] = new BOJ17086_space(i,j,Integer.MAX_VALUE,0);
                visited[i][j] = false;
                if(input[j-1].equals("1")) {
                    visited[i][j] = true;
                    spaces[i][j].shark = 1;
                    spaces[i][j].safe_distance = 0;
                    shark_spaces.add(spaces[i][j]);
                }
            }
        }
    }
}

//메모리 초과 코드
class BOJ17086_memory_over {
    static class BOJ17086_space {
        int r,c;
        int shark;
        int safe_distance;

        BOJ17086_space(int r, int c, int s_d, int s) {
            this.r = r;
            this.c = c;
            this.safe_distance = s_d;
            this.shark = s;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,max_safe_distance = 0;
    static int[][] direction = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
    static ArrayList<BOJ17086_space> shark_spaces;
    static BOJ17086_space[][] spaces;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(BOJ17086_space shark_space : shark_spaces) {
            bfs(shark_space);
        }

        check_max_safe_distance();
    }

    static void check_max_safe_distance() {
        for(int i=1;i<=N;i++) {
            for(int j=1;j<=M;j++) {
                max_safe_distance = Math.max(spaces[i][j].safe_distance, max_safe_distance);
            }
        }

        System.out.println(max_safe_distance);
    }

    static void bfs(BOJ17086_space s_s) {
        Queue<BOJ17086_space> q = new LinkedList<>();

        q.offer(s_s);

        while(!q.isEmpty()) {
            BOJ17086_space now = q.poll();

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 1 || nr > N || nc < 1 || nc > M) continue;
                if(spaces[nr][nc].shark == 1) continue;
                if(spaces[nr][nc].safe_distance < now.safe_distance + 1) continue;

                spaces[nr][nc].safe_distance = Math.min(spaces[nr][nc].safe_distance, now.safe_distance + 1);
                q.offer(spaces[nr][nc]);
            }
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");
        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        spaces = new BOJ17086_space[N+1][M+1];
        shark_spaces = new ArrayList<>();

        for(int i=1;i<=N;i++) {
            input = br.readLine().split(" ");
            for(int j=1;j<=M;j++) {
                spaces[i][j] = new BOJ17086_space(i,j,Integer.MAX_VALUE,0);
                if(input[j-1].equals("1")) {
                    spaces[i][j].shark = 1;
                    spaces[i][j].safe_distance = 0;
                    shark_spaces.add(spaces[i][j]);
                }
            }
        }
    }
}
