package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
트리의 부모 찾기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	107367	49451	34562	43.617%
문제
루트 없는 트리가 주어진다. 이때, 트리의 루트를 1이라고 정했을 때, 각 노드의 부모를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 노드의 개수 N (2 ≤ N ≤ 100,000)이 주어진다. 둘째 줄부터 N-1개의 줄에 트리 상에서 연결된 두 정점이 주어진다.

출력
첫째 줄부터 N-1개의 줄에 각 노드의 부모 노드 번호를 2번 노드부터 순서대로 출력한다.

예제 입력 1
7
1 6
6 3
3 5
4 1
2 4
4 7
예제 출력 1
4
6
1
3
1
4
예제 입력 2
12
1 2
1 3
2 4
3 5
3 6
4 7
4 8
5 9
5 10
6 11
6 12
예제 출력 2
1
1
2
3
3
4
4
5
5
6
6
출처
문제를 만든 사람: baekjoon
잘못된 조건을 찾은 사람: jh05013
알고리즘 분류
그래프 이론
그래프 탐색
트리
너비 우선 탐색
깊이 우선 탐색
 */
/*
알고리즘 핵심
자료구조(트리) + bfs (or dfs)
1. 1~N 까지의 node를 생성하고, 해당 노드는 자기 자신의 번호와 인접한 노드를 배열로 갖는다.
2. 루트 노드를 1로 설정하였으므로, 1번 노드부터 인접한 노드를 queue에 넣고 가장 인접한 노드부터 bfs 탐색한다.
3. 노드를 탐색하면서 이미 탐색한 노드를 visited 배열에 체크하여 부모 노드와 자식 노드를 구분한다.
4. queue에 node를 넣을 때, 현재 노드의 인접한 노드를 탐색하는 것이므로 queue에 넣어지는 node의 부모 노드는 현재 노드로 설정한다.
 */
public class BOJ11725 {
    static class BOJ11725_node {
        int idx,parent;
        ArrayList<BOJ11725_node> adj;

        BOJ11725_node(int idx) {
            this.idx = idx;
            this.parent = 0;
            this.adj = new ArrayList<>();
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static BOJ11725_node[] nodes;
    static boolean[] visited;
    static StringBuilder ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        search_node(nodes[1]);

        print_parent_node();
    }

    private static void print_parent_node() {
        for(int i = 1; i <= N; i++) {
            ans.append(nodes[i].parent).append("\n");
        }

        System.out.println(ans.substring(2));
    }

    private static void search_node(BOJ11725_node node) {
        Queue<BOJ11725_node> q = new LinkedList<>();

        q.add(node);
        visited[node.idx] = true;

        while(!q.isEmpty()) {
            BOJ11725_node now = q.poll();

            for(BOJ11725_node n : now.adj) {
                if(visited[n.idx]) continue;

                visited[n.idx] = true;
                n.parent = now.idx;
                q.add(nodes[n.idx]);
            }
        }
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        visited = new boolean[N + 1];

        nodes = new BOJ11725_node[N + 1];

        for(int i = 1; i <= N; i++) {
            nodes[i] = new BOJ11725_node(i);
        }

        for(int i = 1; i < N; i++) {
            String[] input = br.readLine().split(" ");

            int n1 = Integer.parseInt(input[0]);
            int n2 = Integer.parseInt(input[1]);

            nodes[n1].adj.add(nodes[n2]);
            nodes[n2].adj.add(nodes[n1]);
        }

        ans = new StringBuilder();
    }
}
