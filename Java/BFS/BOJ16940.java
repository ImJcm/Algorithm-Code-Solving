package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
BFS 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	9926	2692	1652	24.816%
문제
BOJ에서 정답이 여러가지인 경우에는 스페셜 저지를 사용한다. 스페셜 저지는 유저가 출력한 답을 검증하는 코드를 통해서 정답 유무를 결정하는 방식이다. 오늘은 스페셜 저지 코드를 하나 만들어보려고 한다.

정점의 개수가 N이고, 정점에 1부터 N까지 번호가 매겨져있는 양방향 그래프가 있을 때, BFS 알고리즘은 다음과 같은 형태로 이루어져 있다.

큐에 시작 정점을 넣는다. 이 문제에서 시작 정점은 1이다. 1을 방문했다고 처리한다.
큐가 비어 있지 않은 동안 다음을 반복한다.
큐에 들어있는 첫 정점을 큐에서 꺼낸다. 이 정점을 x라고 하자.
x와 연결되어 있으면, 아직 방문하지 않은 정점 y를 모두 큐에 넣는다. 모든 y를 방문했다고 처리한다.
2-2 단계에서 방문하지 않은 정점을 방문하는 순서는 중요하지 않다. 따라서, BFS의 결과는 여러가지가 나올 수 있다.

트리가 주어졌을 때, 올바른 BFS 방문 순서인지 구해보자.

입력
첫째 줄에 정점의 수 N(2 ≤ N ≤ 100,000)이 주어진다. 둘째 줄부터 N-1개의 줄에는 트리의 간선 정보가 주어진다. 마지막 줄에는 BFS 방문 순서가 주어진다. BFS 방문 순서는 항상 N개의 정수로 이루어져 있으며, 1부터 N까지 자연수가 한 번씩 등장한다.

출력
입력으로 주어진 BFS 방문 순서가 올바른 순서면 1, 아니면 0을 출력한다.

예제 입력 1
4
1 2
1 3
2 4
1 2 3 4
예제 출력 1
1
올바른 순서는 1, 2, 3, 4와  1, 3, 2, 4가 있다.

예제 입력 2
4
1 2
1 3
2 4
1 2 4 3
예제 출력 2
0
출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: jh05013
빠진 조건을 찾은 사람: njw1204
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
올바른 순서인지 알 수 있는방법은 해당 Node의 Level이라는 변수를 통해 접근 순서를 정하는 것으로 생각된다.
Node.1 -> start, level = 0
Node.2 -> level = 1
Node.3 -> level = 1
Node.4 -> level = 2
현재 노드에서 인접한 노드를 queue에 삽입할 때, 현재 노드의 level + 1을 인접한 Node에 level로 부여한다.

이때, 위의 개념에서 출발 Node가 1이 아닌 경우에는 모든 출발노드에서 BFS를 구하는 것은 불필요한 작업인 것으로 생각한다.
따라서, 입력으로 주어진 순서의 첫번째 번호를 출발 Node로 BFS를 돌려 Level 작업을 마치고, 올바른 순서인지 검사하면 될 것같다.

verification_fault() - 오답 발생 / 이유 : level만 고려하였기 때문에 이전 연결된 부모노드가 맞는지 여부를 확인할 수 없었다.

verification() - BFS 방식으로 주어진 경로를 진행하였을 때, 현재 노드에서 인접노드에 다음 순서의 노드가 없는 경우는 올바른 순서가 아님을 검증이 가능하다.
이는 BFS 과정을 수행하면서 검증이 가능하기 때문에 level을 나눌 필요도 없고, 부모노드인지 확인할 과정 또한 필요가 없다.

Node class 내부에 인접노드의 정보를 포함하기 때문에 다음 순서가 인접노드에 포함하는지 중간에 조건을 검사하면 확인이 가능했다.

중요 : 검증이 필요한 입력된 순서를 검증하려면 인접한 노드의 방문여부를 검사한 후, 미방문된 인접 노드들을 기준으로 포함되는지 검사해야한다.
 */
public class BOJ16940 {
    public static class BOJ16940_Node {
        int level;
        int parent_node;
        ArrayList<Integer> adj_nodes;


        public BOJ16940_Node() {
            adj_nodes = new ArrayList<>();
        }
        public BOJ16940_Node(int level, int parent) {
            this.level = level;
            this.parent_node = parent;
            adj_nodes = new ArrayList<>();
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, start_node = 0;
    static String[] visit_orders;
    static boolean check_1 = true, check_2 = true;
    static boolean[] visited;
    static BOJ16940_Node[] nodes;

    public static void main(String[] args) throws IOException {
        init_setting();

        //search_BFS();

        verification();
    }

    static void verification() {
        if(start_node != 1 || nodes[start_node].adj_nodes.size() == 0) {
            System.out.println("0");
        } else {
            bfs();
        }
    }

    static void bfs() {
        int cur_order = 1;
        Queue<Integer> q = new LinkedList<>();

        visited[start_node] = true;
        q.offer(start_node);

        while(!q.isEmpty()) {
            int cur_node = q.poll();

            HashSet<Integer> unvisited_adj = new HashSet<>();

            for(int node : nodes[cur_node].adj_nodes) {
                if(visited[node]) continue;
                visited[node] = true;
                unvisited_adj.add(node);
            }

            for(int i=0;i<unvisited_adj.size();i++) {
                int next_order_node = Integer.parseInt(visit_orders[cur_order]);
                if(!unvisited_adj.contains(next_order_node)) {
                    System.out.println("0");
                    return;
                }
                q.offer(next_order_node);
                cur_order++;
            }

            //아래 코드는 visit_order를 순차적으로 cur_node에서 인접한 노드에 포함하는지 검사하는데 이때, 잘못된 점은
            //cur_node를 기준으로 방문하지 않은 인접한 노드들만을 기준으로 visit_order[cur_order]가 포함하는지를 검사해야한다.
            //즉, visit_order[cur_order]에 해당하는 노드가 방문했는지 방문안했는지 여부가 불분명하기 때문에 오답처리되는 코드이다.
            /*for(int node : nodes[cur_node].adj_nodes) {
                if(visited[node]) continue;
                visited[node] = true;
                q.offer(node);

                if(cur_order == visit_orders.length) {
                    System.out.println("0");
                    return;
                }

                int next_order_node = Integer.parseInt(visit_orders[cur_order++]);

                if(!nodes[cur_node].adj_nodes.contains(next_order_node)) {
                    System.out.println("0");
                    return;
                }
            }*/
        }
        System.out.println("1");
    }

    static void search_BFS() {
        bfs_fault();
    }

    static void bfs_fault() {
        Queue<Integer> q = new LinkedList<>();

        q.offer(start_node);
        visited[start_node] = true;

        while(!q.isEmpty()) {
            int cur_node = q.poll();

            for(int next_node : nodes[cur_node].adj_nodes) {
                if(visited[next_node]) continue;
                visited[next_node] = true;
                q.offer(next_node);

                //노드의 순서인 level 업데이트
                nodes[next_node].level = nodes[cur_node].level + 1;
                nodes[next_node].parent_node = cur_node;
            }
        }
    }

    //잘못된 검증 - 부모노드를 고려하지 못함
    static void verification_fault() {
        for(int i=1;i<visit_orders.length;i++) {
            int pre_node = Integer.parseInt(visit_orders[i-1]);
            int cur_node = Integer.parseInt(visit_orders[i]);

            if(nodes[cur_node].level < nodes[pre_node].level) {
                check_1 = false;
                break;
            }
        }
        System.out.println(check_1 ? 1 : 0);
    }

    static void init_setting() throws IOException{
        N = Integer.parseInt(br.readLine());

        visited = new boolean[N + 1];
        nodes = new BOJ16940_Node[N + 1];

        // 1~N-1 줄까지 입력으로 노드 간 인접 정보
        for(int i=1;i<N;i++) {
            visited[i] = false;
            String[] input = br.readLine().split(" ");
            int start_node = Integer.parseInt(input[0]);
            int destination_node = Integer.parseInt(input[1]);
            if(nodes[start_node] == null) {
                //nodes[start_node] = new BOJ16940_Node(0,0);
                nodes[start_node] = new BOJ16940_Node();
            }

            if(nodes[destination_node] == null) {
                //nodes[destination_node] = new BOJ16940_Node(0,0);
                nodes[destination_node] = new BOJ16940_Node();
            }
            nodes[start_node].adj_nodes.add(destination_node);
            nodes[destination_node].adj_nodes.add(start_node);
        }

        //입력으로 주어지는 BFS 방문 순서 정보
        visit_orders = br.readLine().split(" ");
        visited[N] = false;

        //방문 순서 검증을 위한 출발 노드 확인
        start_node = Integer.parseInt(visit_orders[0]);
        //nodes[start_node].level = 1;
    }
}
