package Lv3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
가장 먼 노드
제출 내역
문제 설명
n개의 노드가 있는 그래프가 있습니다. 각 노드는 1부터 n까지 번호가 적혀있습니다. 1번 노드에서 가장 멀리 떨어진 노드의 갯수를 구하려고 합니다. 가장 멀리 떨어진 노드란 최단경로로 이동했을 때 간선의 개수가 가장 많은 노드들을 의미합니다.

노드의 개수 n, 간선에 대한 정보가 담긴 2차원 배열 vertex가 매개변수로 주어질 때, 1번 노드로부터 가장 멀리 떨어진 노드가 몇 개인지를 return 하도록 solution 함수를 작성해주세요.

제한사항
노드의 개수 n은 2 이상 20,000 이하입니다.
간선은 양방향이며 총 1개 이상 50,000개 이하의 간선이 있습니다.
vertex 배열 각 행 [a, b]는 a번 노드와 b번 노드 사이에 간선이 있다는 의미입니다.
입출력 예
n	vertex	return
6	[[3, 6], [4, 3], [3, 2], [1, 3], [1, 2], [2, 4], [5, 2]]	3
입출력 예 설명
예제의 그래프를 표현하면 아래 그림과 같고, 1번 노드에서 가장 멀리 떨어진 노드는 4,5,6번 노드입니다.

image.png
 */
/*
알고리즘 핵심
BFS + 그래프
1. 노드의 번호와 인접한 노드, 1번 노드로부터 떨어진 거리를 저장한 구조체를 생성하여 BFS를 수행하여 최단 거리를 각각 갱신한다.
2. BFS 수행과정에서 1번 노드로부터 가장 먼거리를 찾고, 노드들 중 가장 먼 거리에 해당하는 노드의 개수를 구한다.
 */
public class 가장_먼_노드 {
    static void main() {
        int n = 6;
        int[][] vertex = new int[][] {
                {3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}
        };

        Solve task = new Solve();
        System.out.println(task.solution(n, vertex));
    }

    private static class Solve {
        private class Node {
            int node;
            int dist_from_node_1;
            ArrayList<Integer> adj;

            public Node(int node) {
                this.node = node;
                this.dist_from_node_1 = -1;
                this.adj = new ArrayList<>();
            }

            public void addEdge(int v) {
                this.adj.add(v);
            }
        }
        private int ans;
        private int max_dist_from_node_1;
        private Node[] nodes;


        public int solution(int n, int[][] vertex) {
            init_setting(n,vertex);

            move_nodes(n,nodes);

            search_faraway_nodes(max_dist_from_node_1, nodes);

            return ans;
        }

        private void move_nodes(int n, Node[] nodes) {
            Queue<Node> q = new LinkedList<>();
            boolean[] visited = new boolean[n + 1];

            q.add(nodes[1]);
            visited[nodes[1].node] = true;

            while(!q.isEmpty()) {
                Node cur = q.poll();

                max_dist_from_node_1 = Math.max(max_dist_from_node_1, cur.dist_from_node_1);

                for (Integer v : cur.adj) {
                    if (!visited[v]) {
                        visited[v] = true;
                        nodes[v].dist_from_node_1 = cur.dist_from_node_1 + 1;
                        q.add(nodes[v]);
                    }
                }
            }
        }

        private void search_faraway_nodes(int max_dist, Node[] nodes) {
            int cnt = 0;

            for(int i = 1; i < nodes.length; i++) {
                if(nodes[i].dist_from_node_1 == max_dist) {
                    cnt++;
                }
            }

            ans = cnt;
        }

        private void init_setting(int n, int[][] vertex) {
            ans = 0;
            max_dist_from_node_1 = -1;
            nodes = new Node[n + 1];

            for(int i = 1; i <= n; i++) {
                nodes[i] = new Node(i);
            }

            for(int[] v : vertex) {
                int f = v[0];
                int t = v[1];

                nodes[f].adj.add(t);
                nodes[t].adj.add(f);
            }

            nodes[1].dist_from_node_1 = 0;
        }
    }
}
