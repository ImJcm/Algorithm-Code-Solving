package MST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
다리 만들기 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	29629	10974	6972	33.444%
문제
섬으로 이루어진 나라가 있고, 모든 섬을 다리로 연결하려고 한다. 이 나라의 지도는 N×M 크기의 이차원 격자로 나타낼 수 있고, 격자의 각 칸은 땅이거나 바다이다.

섬은 연결된 땅이 상하좌우로 붙어있는 덩어리를 말하고, 아래 그림은 네 개의 섬으로 이루어진 나라이다. 색칠되어있는 칸은 땅이다.



다리는 바다에만 건설할 수 있고, 다리의 길이는 다리가 격자에서 차지하는 칸의 수이다. 다리를 연결해서 모든 섬을 연결하려고 한다. 섬 A에서 다리를 통해 섬 B로 갈 수 있을 때, 섬 A와 B를 연결되었다고 한다. 다리의 양 끝은 섬과 인접한 바다 위에 있어야 하고, 한 다리의 방향이 중간에 바뀌면 안된다. 또, 다리의 길이는 2 이상이어야 한다.

다리의 방향이 중간에 바뀌면 안되기 때문에, 다리의 방향은 가로 또는 세로가 될 수 밖에 없다. 방향이 가로인 다리는 다리의 양 끝이 가로 방향으로 섬과 인접해야 하고, 방향이 세로인 다리는 다리의 양 끝이 세로 방향으로 섬과 인접해야 한다.

섬 A와 B를 연결하는 다리가 중간에 섬 C와 인접한 바다를 지나가는 경우에 섬 C는 A, B와 연결되어있는 것이 아니다.

아래 그림은 섬을 모두 연결하는 올바른 2가지 방법이고, 다리는 회색으로 색칠되어 있다. 섬은 정수, 다리는 알파벳 대문자로 구분했다.


다리의 총 길이: 13

D는 2와 4를 연결하는 다리이고, 3과는 연결되어 있지 않다.

다리의 총 길이: 9 (최소)

다음은 올바르지 않은 3가지 방법이다


C의 방향이 중간에 바뀌었다	D의 길이가 1이다.	가로 다리인 A가 1과 가로로 연결되어 있지 않다.
다리가 교차하는 경우가 있을 수도 있다. 교차하는 다리의 길이를 계산할 때는 각 칸이 각 다리의 길이에 모두 포함되어야 한다. 아래는 다리가 교차하는 경우와 기타 다른 경우에 대한 2가지 예시이다.


A의 길이는 4이고, B의 길이도 4이다.

총 다리의 총 길이: 4 + 4 + 2 = 10

다리 A: 2와 3을 연결 (길이 2)

다리 B: 3과 4를 연결 (길이 3)

다리 C: 2와 5를 연결 (길이 5)

다리 D: 1과 2를 연결 (길이 2)

총 길이: 12

나라의 정보가 주어졌을 때, 모든 섬을 연결하는 다리 길이의 최솟값을 구해보자.

입력
첫째 줄에 지도의 세로 크기 N과 가로 크기 M이 주어진다. 둘째 줄부터 N개의 줄에 지도의 정보가 주어진다. 각 줄은 M개의 수로 이루어져 있으며, 수는 0 또는 1이다. 0은 바다, 1은 땅을 의미한다.

출력
모든 섬을 연결하는 다리 길이의 최솟값을 출력한다. 모든 섬을 연결하는 것이 불가능하면 -1을 출력한다.

제한
1 ≤ N, M ≤ 10
3 ≤ N×M ≤ 100
2 ≤ 섬의 개수 ≤ 6
예제 입력 1
7 8
0 0 0 0 0 0 1 1
1 1 0 0 0 0 1 1
1 1 0 0 0 0 0 0
1 1 0 0 0 1 1 0
0 0 0 0 0 1 1 0
0 0 0 0 0 0 0 0
1 1 1 1 1 1 1 1
예제 출력 1
9
예제 입력 2
7 8
0 0 0 1 1 0 0 0
0 0 0 1 1 0 0 0
1 1 0 0 0 0 1 1
1 1 0 0 0 0 1 1
1 1 0 0 0 0 0 0
0 0 0 0 0 0 0 0
1 1 1 1 1 1 1 1
예제 출력 2
10
예제 입력 3
7 8
1 0 0 1 1 1 0 0
0 0 1 0 0 0 1 1
0 0 1 0 0 0 1 1
0 0 1 1 1 0 0 0
0 0 0 0 0 0 0 0
0 1 1 1 0 0 0 0
1 1 1 1 1 1 0 0
예제 출력 3
9
예제 입력 4
7 7
1 1 1 0 1 1 1
1 1 1 0 1 1 1
1 1 1 0 1 1 1
0 0 0 0 0 0 0
1 1 1 0 1 1 1
1 1 1 0 1 1 1
1 1 1 0 1 1 1
예제 출력 4
-1
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: djm03178
알고리즘 분류
구현
그래프 이론
브루트포스 알고리즘
그래프 탐색
너비 우선 탐색
깊이 우선 탐색
최소 스패닝 트리
 */
/*
알고리즘 핵심
1. 입력으로 주어진 바다와 섬의 정보에서 각 섬을 구분하는 과정이 필요하다 - BFS or DFS
2. 나누어진 섬을 기준으로 각 섬 간의 최소 비용 이동 경로 정보가 필요하다. - DFS
3. 최소 이동 비용 그래프 = Shortest_path를 통해 간선-비용 구조체 생성
4. MST - Kruskal 알고리즘을 사용하여 최소 비용 신장 그래프를 구한다.
4-a. 신장 그래프를 구성하는 과정에서 간선의 갯수가 Vertex - 1 개수를 만족하지 못하는 경우, 모든 섬을 연결할 수 없는 경우로 판단이 가능하다.
4-b. 신장 그래프를 구성하는 간선의 갯수가 Vertex - 1 개수를 만족하면, 구성하는 간선들의 비용을 더하여 ans에 업데이트한다.
 */
public class BOJ17472 {
    static class BOJ17472_island {
        int n,m,l,i;
        BOJ17472_island(int n, int m, int l, int i) {
            this.n = n;
            this.m = m;
            this.l = l;
            this.i = i;
        }
    }

    static class BOJ17472_edge {
        int from, to, weight;

        BOJ17472_edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,num,ans;
    static int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
    static BOJ17472_island[][] map;
    static ArrayList<BOJ17472_island>[] islands;
    static int[][] shortest_path;
    static ArrayList<BOJ17472_edge> edges;
    static ArrayList<Integer> link;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        divide_island();

        build_bridge();

        check_bridge_cnt();

        //print();

        System.out.println(ans);
    }

    private static void check_bridge_cnt() {
        for(int i = 1; i < num; i++) {
            for(int j = i; j < num; j++) {
                if(shortest_path[i][j] == 0) continue;
                edges.add(new BOJ17472_edge(i,j,shortest_path[i][j]));
            }
        }

        link.add(0);
        for(int i = 1; i < num; i++) {
            link.add(i);
        }

        edges.sort(new Comparator<BOJ17472_edge>() {
            @Override
            public int compare(BOJ17472_edge o1, BOJ17472_edge o2) {
                return o1.weight - o2.weight;
            }
        });

        int cost = 0;
        int edge_num = 0;
        boolean flag = false;
        for(BOJ17472_edge e : edges) {
            if(same(e.from, e.to)) continue;
            cost += e.weight;
            union(e.from, e.to);
            edge_num++;

            if(edge_num == num - 2) {
                flag = true;
                break;
            }
        }

        if(flag) {
            ans = Math.min(ans, cost);
        } else {
            ans = -1;
        }
    }

    private static int find(int x) {
        if(link.get(x) == x) return x;
        else return find(link.get(x));
    }

    private static boolean same(int a, int b) {
        return find(a) == find(b);
    }

    private static void union(int a, int b) {
        a = find(a);
        b = find(b);

        if(a < b) link.set(b,a);
        else link.set(a,b);
    }

    private static void build_bridge() {
        shortest_path = new int[num][num];

        for(int l = 1; l < num; l++) {
            for(BOJ17472_island island : islands[l]) {
                for(int[] d : directions) {
                    dfs(island, l, d,0);
                }
            }
        }
    }

    private static void dfs(BOJ17472_island island, int l, int[] direction, int move) {
        if(island.i != 0 && move != 0) {
            if(move > 2) {
                if(shortest_path[l][island.l] == 0) {
                    shortest_path[l][island.l] = move - 1;
                } else {
                    shortest_path[l][island.l] = Math.min(shortest_path[l][island.l],move - 1);
                }
            }
            return;
        }

        int nn = island.n + direction[0];
        int nm = island.m + direction[1];
        if(out_check(island, direction)) return;
        island = map[nn][nm];
        if(island.l == l) return;
        dfs(island, l, direction, move + 1);
    }

    private static boolean out_check(BOJ17472_island island, int[] d) {
        int next_n = island.n + d[0];
        int next_m = island.m + d[1];

        if(next_n < 1 || next_m < 1 || next_n > N || next_m > M) return true;
        else return false;
    }

    private static void divide_island() {
        num = 1;
        boolean[][] visited = new boolean[N+1][M+1];

        for(int n = 1; n <= N; n++) {
            for(int m = 1; m <= M; m++) {
                if(map[n][m].i == 0) continue;
                if(visited[n][m]) continue;
                map[n][m].l = num++;
                bfs(map[n][m],visited);
            }
        }
    }

    private static void bfs(BOJ17472_island i, boolean[][] visited) {
        Queue<BOJ17472_island> q = new LinkedList<>();

        islands[i.l] = new ArrayList<>();
        q.offer(i);
        visited[i.n][i.m] = true;

        while(!q.isEmpty()) {
            BOJ17472_island now = q.poll();

            islands[i.l].add(now);

            for(int[] d : directions) {
                int nn = now.n + d[0];
                int nm = now.m + d[1];

                if(nn < 1 || nm < 1 || nn > N || nm > M) continue;
                if(visited[nn][nm] || map[nn][nm].i == 0) continue;

                visited[nn][nm] = true;
                map[nn][nm].l = now.l;
                q.offer(map[nn][nm]);
            }
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        map = new BOJ17472_island[N+1][M+1];
        islands = new ArrayList[7];
        edges = new ArrayList<>();
        link = new ArrayList<>();

        ans = Integer.MAX_VALUE;

        for(int n = 1; n <= N; n++) {
            input = br.readLine().split(" ");
            for(int m = 1; m <= M; m++) {
                map[n][m] = new BOJ17472_island(n,m,0,Integer.parseInt(input[m-1]));
            }
        }
    }

    private static void print() {
        for(int n = 1; n <= N; n++) {
            for(int m = 1; m <= M; m++) {
                System.out.print(map[n][m].l + " ");
            }
            System.out.println();
        }
    }
}
