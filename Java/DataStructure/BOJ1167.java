package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/*
트리의 지름

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	70509	24445	17801	34.292%
문제
트리의 지름이란, 트리에서 임의의 두 점 사이의 거리 중 가장 긴 것을 말한다. 트리의 지름을 구하는 프로그램을 작성하시오.

입력
트리가 입력으로 주어진다. 먼저 첫 번째 줄에서는 트리의 정점의 개수 V가 주어지고 (2 ≤ V ≤ 100,000)둘째 줄부터 V개의 줄에 걸쳐 간선의 정보가 다음과 같이 주어진다. 정점 번호는 1부터 V까지 매겨져 있다.

먼저 정점 번호가 주어지고, 이어서 연결된 간선의 정보를 의미하는 정수가 두 개씩 주어지는데, 하나는 정점번호, 다른 하나는 그 정점까지의 거리이다. 예를 들어 네 번째 줄의 경우 정점 3은 정점 1과 거리가 2인 간선으로 연결되어 있고, 정점 4와는 거리가 3인 간선으로 연결되어 있는 것을 보여준다. 각 줄의 마지막에는 -1이 입력으로 주어진다. 주어지는 거리는 모두 10,000 이하의 자연수이다.

출력
첫째 줄에 트리의 지름을 출력한다.

예제 입력 1
5
1 3 2 -1
2 4 4 -1
3 1 2 4 3 -1
4 2 4 3 3 5 6 -1
5 4 6 -1
예제 출력 1
11
출처
문제의 오타를 찾은 사람: ababc1005, cfghj101, WeissBlume
문제를 만든 사람: author5
데이터를 추가한 사람: djm03178, mythofys
알고리즘 분류
그래프 이론
그래프 탐색
트리
깊이 우선 탐색
트리의 지름
 */
/*
알고리즘 핵심
자료구조(트리) + dfs + 트리의 지름 개념
1. 트리의 지름 알고리즘을 이용하여 임의의 노드에서 가장 거리가 먼 노드를 찾는다.
2. 찾은 노드를 기준으로 가장 거리가 먼 노드와의 거리를 ans에 업데이트하고 출력한다.

루트 노드의 처리 - https://www.acmicpc.net/board/view/56965

문제를 읽고 하나의 노드는 여러 개의 자식 노드를 가질 수 있고, 사이클이 있을 수 있다고 생각하여 모든 각 노드에서 다른 노드간의 거리의 최대값을 구하려고
bruteforece 방식을 생각했지만 노드의 개수가 100,000개로 많기 때문에 코드를 작성하기 이전에 시간초과 발생할 것이라고 예상은 했다.

그래도 문제의 접근방법을 몰랐기에 bruteforce 방식으로 코드를 짠 이후, 트리의 정의와 트리의 지름 알고리즘 개념을 검색하여 풀게되었다.

트리의 정의
1. 정점의 개수가 N일 때, 간선의 개수가 N - 1일 것
2. 사이클이 존재하지 않을 것
3. 어떤 정점에서 다른 정점으로 가는 방법이 오직 한 가지일 것 (방향을 고려하지 않았을 때)
(트리가 여러개인 경우, 포레스트라고 한다.)

트리의 지름 알고리즘
1. 임의의 노드를 기준으로 가장 거리가 먼 노드를 찾는다. (이때, 거리가 멀다는 의미는 가중치가 존재할 때 길이가 긴 것을 의미)
2. 1번에서 찾은 노드를 기준으로 가장 거리가 먼 노드를 찾는다.
3. 2번에서 찾은 노드와 1번에서 찾은 노드의 거리가 트리의 지름이다.

임의의 노드를 잡고 가장 거리가 먼 노드를 찾고, 찾은 노드를 기준으로 다시 한번 가장 거리가 먼 노드를 찾는 것이 트리의 지름을 보장하는 이유
트리의 경우 각 노드는 다른 노드로의 경로가 하나임을 만족하기 때문에 선택한 노드를 기준으로 가장 거리가 먼 노드를 찾고,
찾은 노드에서 거리가 먼 노드를 찾을 때, 처음 선택한 노드에서 반대 방향의 거리가 먼 노드의 경로를 추가하면 해당 트리에서 가장 먼 거리의 두 노드임을 만족한다.
 */
public class BOJ1167 {
    static class BOJ1167_node {
        int v;
        ArrayList<Integer> adj;
        Map<Integer,Integer> edge;

        BOJ1167_node(int v) {
            this.v = v;
            this.adj = new ArrayList<>();
            this.edge = new HashMap<>();
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int V,ans;
    static boolean[] visited;
    static int[] dist;
    static BOJ1167_node[] nodes;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        visited = new boolean[V + 1];
        dist = new int[V + 1];
        dfs(nodes[1]);

        int max_dist_node = IntStream.range(1,dist.length)
                .reduce((i, j) -> dist[i] >= dist[j] ? i : j)
                .orElse(0);

        Arrays.fill(visited, false);
        Arrays.fill(dist, 0);
        dfs(nodes[max_dist_node]);

        ans = Arrays.stream(dist).max().orElse(-1);

        System.out.println(ans);
    }


    private static void dfs(BOJ1167_node node) {
        visited[node.v] = true;

        for(Integer adj_n : node.adj) {
            if(visited[adj_n]) continue;

            dist[adj_n] = dist[node.v] + node.edge.get(adj_n);
            dfs(nodes[adj_n]);
        }
    }

    /*
        시간초과
        트리의 정의를 몰랐던 것이 실패 코드의 주 원인이라고 생각한다.
        각 노드끼리의 거리 중 최대값을 갱신하는 방법으로 bruteforce에 해당한다.
     */
    private static void solve_timeOut() {
        for(int v = 1; v <= V; v++) {
            visited = new boolean[V + 1];
            visited[v] = true;
            ans = Math.max(ans, dfs_timeOut(nodes[v], visited));
        }

        System.out.println(ans);
    }

    private static int dfs_timeOut(BOJ1167_node node, boolean[] visited) {
        if(node.adj.isEmpty()) return 0;

        int res = 0;

        for(Integer n : node.adj) {
            if(visited[n]) continue;

            visited[n] = true;
            res = Math.max(res, nodes[node.v].edge.get(n) + dfs_timeOut(nodes[n],visited));
            visited[n] = false;
        }

        return res;
    }

    private static void init_setting() throws IOException {
        V = Integer.parseInt(br.readLine());

        nodes = new BOJ1167_node[V + 1];

        for(int i = 1; i <= V; i++) {
            nodes[i] = new BOJ1167_node(i);
        }

        for(int i = 1; i <= V; i++) {
            String[] input = br.readLine().split(" ");

            int n1 = Integer.parseInt(input[0]);
            int n2,d;

            for(int j = 1; j < input.length - 2; j += 2) {
                n2 = Integer.parseInt(input[j]);
                d = Integer.parseInt(input[j + 1]);

                nodes[n1].adj.add(n2);
                nodes[n1].edge.put(n2,d);
            }
        }

        ans = Integer.MIN_VALUE;
    }
}
