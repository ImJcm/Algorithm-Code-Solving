package binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
배열에서 이동 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	11687	3213	2133	25.429%
문제
n×n짜리의 배열이 하나 있다. 이 배열의 (1, 1)에서 (n, n)까지 이동하려고 한다. 이동할 때는 상, 하, 좌, 우의 네 인접한 칸으로만 이동할 수 있다.

이와 같이 이동하다 보면, 배열에서 몇 개의 수를 거쳐서 이동하게 된다. 이동하기 위해 거쳐 간 수들 중 최댓값과 최솟값의 차이가 가장 작아지는 경우를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 n(2 ≤ n ≤ 100)이 주어진다. 다음 n개의 줄에는 배열이 주어진다. 배열의 각 수는 0보다 크거나 같고, 200보다 작거나 같은 정수이다.

출력
첫째 줄에 (최대 - 최소)가 가장 작아질 때의 그 값을 출력한다.

예제 입력 1
5
1 1 3 6 8
1 2 2 5 5
4 4 0 3 3
8 0 2 3 4
4 3 0 2 1
예제 출력 1
2
출처
University > Tu-Darmstadt Programming Contest > TUD Contest 2006 6번

Olympiad > USA Computing Olympiad > 2002-2003 Season > USACO US Open 2003 Contest > Green 1번

문제를 번역한 사람: author5
데이터를 추가한 사람: kdk8361, yclock
알고리즘 분류
그래프 이론
그래프 탐색
이분 탐색
너비 우선 탐색
 */
/*

 */
public class BOJ1981 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        public class Pos {
            int x,y,n,min_n,max_n;

            Pos(int x, int y, int n, int min, int max) {
                this.x = x;
                this.y = y;
                this.n = n;
                this.min_n = min;
                this.max_n = max;
            }
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,ans,l,r;
        int[][] direction = {{0,-1},{-1,0},{0,1},{1,0}};
        Pos[][] arr;

        private void solve() throws IOException {
            init_setting();

            binary_search();

            System.out.println(ans);
        }

        private void binary_search() {
            if(l > r) return;

            int m = (l + r) / 2;

            if(bfs(arr[0][0], m)) {
                r = m - 1;
                ans = m;
            } else l = m + 1;

            binary_search();
        }

        private boolean bfs(Pos s, int m) {
            Queue<Pos> q = new LinkedList<>();
            int[][] visited = new int[N][N];

            for(int i = 0; i < N; i++) {
                Arrays.fill(visited[i],201);
            }

            q.add(s);
            visited[s.x][s.y] = s.max_n - s.min_n;

            while(!q.isEmpty()) {
                Pos np = q.poll();

                if(np.x == N - 1 && np.y == N - 1) {
                    //q.clear();
                    return true;
                }

                for(int[] d : direction) {
                    int nx = np.x + d[0];
                    int ny = np.y + d[1];

                    if(nx < 0 || nx >= N || ny < 0 || ny >= N) continue;

                    int nmin_n = Math.min(np.min_n,arr[nx][ny].n);
                    int nmax_n = Math.max(np.max_n,arr[nx][ny].n);
                    int diff = nmax_n - nmin_n;

                    if(visited[nx][ny] <= diff || diff > m) continue;

                    visited[nx][ny] = Math.min(visited[nx][ny],diff);
                    q.add(new Pos(nx,ny,arr[nx][ny].n,nmin_n,nmax_n));
                }
            }
            return false;
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            arr = new Pos[N][N];

            l = 201;
            r = 0;
            ans = 201;

            for(int x = 0; x < N; x++) {
                String[] input = br.readLine().split(" ");
                for(int y = 0; y < N; y++) {
                    int n = Integer.parseInt(input[y]);
                    arr[x][y] = new Pos(x,y,n,n,n);
                    l = Math.min(l,n);
                    r = Math.max(r,n);
                }
            }

            arr[0][0].min_n = Math.min(arr[0][0].n,arr[N - 1][N - 1].n);
            arr[0][0].max_n = Math.max(arr[0][0].n,arr[N - 1][N - 1].n);

            r -= l;
        }
    }

    public static class WrongSolve {
        public class Pos {
            int x,y,n,min_n,max_n;

            Pos(int x, int y, int n, int min, int max) {
                this.x = x;
                this.y = y;
                this.n = n;
                this.min_n = min;
                this.max_n = max;
            }
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,ans,l,r;
        int[][] direction = {{0,-1},{-1,0},{0,1},{1,0}};
        Pos[][] arr;

        private void solve() throws IOException {
            init_setting();

            binary_search();

            System.out.println(ans);
        }

        private void binary_search() {
            if(l > r) return;

            int m = (l + r) / 2;

            if(bfs(arr[0][0], m)) {
                r = m - 1;
                ans = m;
            } else l = m + 1;

            binary_search();
        }

        private boolean bfs(Pos s, int m) {
            PriorityQueue<Pos> pq = new PriorityQueue<>(new Comparator<Pos>() {
                @Override
                public int compare(Pos o1, Pos o2) {
                    //return o2.min_n - o1.min_n; // wrong solve

                    /* // wrong solve2
                    int o1_diff = o1.max_n - o1.min_n;
                    int o2_diff = o2.max_n - o2.min_n;
                    return o1_diff - o2_diff;*/

                    // wrong solve3
                    int min_diff = o2.min_n - o1.min_n;
                    int max_diff = o1.max_n - o2.max_n;

                    if(min_diff > 0) return 1;
                    else if(min_diff < 0) return -1;
                    else return max_diff;
                }
            });
            boolean[][] visited = new boolean[N][N];

            pq.add(s);
            visited[s.x][s.y] = true;

            while(!pq.isEmpty()) {
                Pos np = pq.poll();

                if(np.x == N - 1 && np.y == N - 1) {
                    return true;
                }

                for(int[] d : direction) {
                    int nx = np.x + d[0];
                    int ny = np.y + d[1];

                    if(nx < 0 || nx >= N || ny < 0 || ny >= N || visited[nx][ny]) continue;

                    int nmin_n = Math.min(np.min_n,arr[nx][ny].n);
                    int nmax_n = Math.max(np.max_n,arr[nx][ny].n);

                    if(nmax_n - nmin_n <= m) {
                        visited[nx][ny] = true;
                        pq.add(new Pos(nx,ny,arr[nx][ny].n,nmin_n,nmax_n));
                    }
                }
            }
            return false;
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            arr = new Pos[N][N];

            l = 201;
            r = 0;
            ans = 201;

            for(int x = 0; x < N; x++) {
                String[] input = br.readLine().split(" ");
                for(int y = 0; y < N; y++) {
                    int n = Integer.parseInt(input[y]);
                    arr[x][y] = new Pos(x,y,n,n,n);
                    l = Math.min(l,n);
                    r = Math.max(r,n);
                }
            }

            arr[0][0].min_n = Math.min(arr[0][0].n,arr[N - 1][N - 1].n);
            arr[0][0].max_n = Math.max(arr[0][0].n,arr[N - 1][N - 1].n);

            r -= l;
        }
    }
}
