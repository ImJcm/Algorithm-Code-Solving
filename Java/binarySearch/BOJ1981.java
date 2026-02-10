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
알고리즘 핵심
이분탐색 + BFS
1. 입력으로 주어지는 값중 최대,최소의 뺀값으로 만들 수 있는 결과값을 이분탐색을 진행하고, 최소값을 지정(0~MAX_VALUE)하여 BFS를 수행한다.
2. BFS의 수행결과가 true인 경우, 최대-최소 + 최소값을 만족하는 경로가 존재하므로 최대-최소값을 줄이기 위해 r = m - 1로 설정한다.
BFS의 수행결과가 false인 경우, 만족하는 경로가 없으므로 l = m + 1로 설정한다.
3. 2번의 과정을 수행하는데 최소값을 지정하기 때문에 0~MAX_VALUE 횟수의 반복문으로 수행해야 한다.
 */
public class BOJ1981 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    /*
        정답 코드 : 입력으로 주어지는 값중 최소값과 최대값을 기준으로 두 값의 합 / 2를 이분 탐색하여 값을 정하고, 경로를 탐색하는 과정에서 최소값을 지정하여
        max-min이 이분탐색한 결과와 같은지 여부를 통해 l,r을 조정한다.
        bfs 과정에서 현시점에서 다음으로 나아가는 위치의 최소값과 최대값의 차이를 비교하는 것외에 현재 경로를 탐색하는 일련의 과정에서 최소값을 정하는 가정이 필요하다.
        결국 최소값을 정하여 bfs를 수행하기 때문에 max의 값과 정해진 min값과의 차이를 이분탐색의 결과값과 비교하여 (N,N)에 도달할 수 있는지를 검사한다.
        최소값을 정하는 이유는 입력으로 주어지는 값중 최대-최소인 값으로 이분탐색한 결과값만으로 최소결과를 도출할 수 없다.
        => (한번의 bfs로 해당하는 경로의 최대-최소인 값이 최소임을 만족하지 못하기 때문에 이미 탐색한 곳을 중복으로 탐색하여 경로를 만들어야하지만 중복 탐색 경로를
        정하는 기준이 애매하다. + 최소값을 결정함으로써 가능한 경로가 나뉘어지고 최대-최소값이 달라질 수 있다.)
        힌트 참고 : https://www.acmicpc.net/board/view/5577
     */
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
        int N,ans,l,r,max_r;
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
            boolean flag = false;

            for(int s = 0; s <= max_r; s++) {
                if(bfs(arr[0][0], m, s)) {
                    flag = true;
                    ans = m;
                }
            }

            if(flag) r = m - 1;
            else l = m + 1;

            binary_search();
        }

        private boolean bfs(Pos s, int m, int min_n) {
            PriorityQueue<Pos> q = new PriorityQueue<>(new Comparator<Pos>() {
                @Override
                public int compare(Pos o1, Pos o2) {
                    return o1.min_n - o2.min_n;
                }
            });
            boolean[][] visited = new boolean[N][N];

            q.add(s);
            visited[s.x][s.y] = true;

            while(!q.isEmpty()) {
                Pos np = q.poll();

                if(np.x == N - 1 && np.y == N - 1) {
                    return true;
                }

                for(int[] d : direction) {
                    int nx = np.x + d[0];
                    int ny = np.y + d[1];

                    if(nx < 0 || nx >= N || ny < 0 || ny >= N) continue;

                    int nmin_n = Math.min(np.min_n, arr[nx][ny].n);
                    int nmax_n = Math.max(np.max_n, arr[nx][ny].n);
                    int diff = nmax_n - min_n;

                    if(nmin_n < min_n || diff > m || visited[nx][ny]) continue;

                    visited[nx][ny] = true;
                    q.add(new Pos(nx,ny,arr[nx][ny].n,nmin_n,nmax_n));
                }
            }
            return false;
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            arr = new Pos[N][N];

            l = 0;
            r = 0;
            ans = 201;

            for(int x = 0; x < N; x++) {
                String[] input = br.readLine().split(" ");
                for(int y = 0; y < N; y++) {
                    int n = Integer.parseInt(input[y]);
                    arr[x][y] = new Pos(x,y,n,n,n);
                    r = max_r = Math.max(r,n);
                }
            }
        }
    }

    /*
        틀린 코드(메모리 초과) : visited를 min,max,x,y의 4차원으로 설정하여 방문 여부를 체크하는 것으로 메모리 초과가 발생한다.
     */
    public static class WrongSolve4 {
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
            }
            else l = m + 1;

            binary_search();
        }

        private boolean bfs(Pos s, int m) {
            PriorityQueue<Pos> q = new PriorityQueue<>(new Comparator<Pos>() {
                @Override
                public int compare(Pos o1, Pos o2) {
                    int o1_diff = o1.max_n - o1.min_n;
                    int o2_diff = o2.max_n - o2.min_n;

                    return o1_diff - o2_diff;
                }
            });
            boolean[][][][] visited = new boolean[201][201][N][N];

            q.add(s);
            visited[s.n][s.n][s.x][s.y] = true;

            while(!q.isEmpty()) {
                Pos np = q.poll();

                if(np.x == N - 1 && np.y == N - 1) {
                    return true;
                }

                for(int[] d : direction) {
                    int nx = np.x + d[0];
                    int ny = np.y + d[1];

                    if(nx < 0 || nx >= N || ny < 0 || ny >= N) continue;

                    int nmin_n = Math.min(np.min_n, arr[nx][ny].n);
                    int nmax_n = Math.max(np.max_n, arr[nx][ny].n);
                    int diff = nmax_n - nmin_n;

                    if(diff > m || visited[nmin_n][nmax_n][nx][ny]) continue;

                    visited[nmin_n][nmax_n][nx][ny] = true;
                    q.add(new Pos(nx,ny,arr[nx][ny].n,nmin_n,nmax_n));
                }
            }
            return false;
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            arr = new Pos[N][N];

            l = 0;
            r = 0;
            ans = 201;

            for(int x = 0; x < N; x++) {
                String[] input = br.readLine().split(" ");
                for(int y = 0; y < N; y++) {
                    int n = Integer.parseInt(input[y]);
                    arr[x][y] = new Pos(x,y,n,n,n);
                    r = Math.max(r,n);
                }
            }
        }
    }

    /*
        틀린 코드 : max-min의 값을 이진탐색하여 값을 정하고, BFS로 우선순위 큐를 사용하여 max-min의 값이 차이가 적은 순으로 이동 여부를 검사한다.
        이때, max-min의 값과 x,y의 3차원의 방문 여부 배열을 사용하여 재방문을 조정한다.
        하지만 해당 로직은 max-min의 값이 다르지만 결과값이 같은 경우는 구분하지 못하므로 최종적인 max-min의 값이 오류가 생길 수 있다.
     */
    public static class WrongSolve3 {
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
            }
            else l = m + 1;

            binary_search();
        }

        private boolean bfs(Pos s, int m) {
            PriorityQueue<Pos> q = new PriorityQueue<>(new Comparator<Pos>() {
                @Override
                public int compare(Pos o1, Pos o2) {
                    int o1_diff = o1.max_n - o1.min_n;
                    int o2_diff = o2.max_n - o2.min_n;

                    int diff = o1_diff - o2_diff;

                    if(diff == 0) return o1.max_n - o2.max_n;
                    else return diff;
                }
            });
            boolean[][][] visited = new boolean[201][N][N];

            q.add(s);
            visited[0][s.x][s.y] = true;

            while(!q.isEmpty()) {
                Pos np = q.poll();

                if(np.x == N - 1 && np.y == N - 1) {
                    //ans = Math.min(ans, np.max_n - np.min_n);
                    return true;
                }

                for(int[] d : direction) {
                    int nx = np.x + d[0];
                    int ny = np.y + d[1];

                    if(nx < 0 || nx >= N || ny < 0 || ny >= N) continue;

                    int nmin_n = Math.min(np.min_n, arr[nx][ny].n);
                    int nmax_n = Math.max(np.max_n, arr[nx][ny].n);
                    int diff = nmax_n - nmin_n;

                    if(diff > m || visited[diff][nx][ny]) continue;

                    visited[diff][nx][ny] = true;
                    q.add(new Pos(nx,ny,arr[nx][ny].n,nmin_n,nmax_n));
                }
            }
            return false;
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            arr = new Pos[N][N];

            l = 0;
            r = 0;
            ans = 201;

            for(int x = 0; x < N; x++) {
                String[] input = br.readLine().split(" ");
                for(int y = 0; y < N; y++) {
                    int n = Integer.parseInt(input[y]);
                    arr[x][y] = new Pos(x,y,n,n,n);
                    r = Math.max(r,n);
                }
            }
        }
    }

    /*
        틀린 코드 : 이분 탐색의 대상을 max-min값으로 고정한 상태, visited를 int 타입으로 설정하여 해당 경로에 도달한 경우 max-min 값을 채워 기존의 값보다 작은 경로는 재방문이 가능하도록 하였지만
        틀린 로직이다.
     */
    public static class WrongSolve2 {
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

    /*
        틀린 코드 : 이분 탐색의 대상을 max-min값으로 고정한 상태, BFS에서 우선순위 큐를 통해 max-min값이 작은 값을 우선으로
        (1,1) -> (N,N)으로의 이동 여부를 검사한다.
        하지만, 해동 로직은 올바른 최소값을 보장하지 못한다.
        한번의 이동이 최소값을 만족하지 못할 경우 다른 경로의 이동으로는 가능하지만 visited로 인해 재방문이 불가능하다.
     */
    private static class WrongSolve {
        private class Pos {
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

        void solve() throws IOException {
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
