package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
DFS 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	6092	2051	1489	35.836%
문제
BOJ에서 정답이 여러가지인 경우에는 스페셜 저지를 사용한다. 스페셜 저지는 유저가 출력한 답을 검증하는 코드를 통해서 정답 유무를 결정하는 방식이다. 오늘은 스페셜 저지 코드를 하나 만들어보려고 한다.

정점의 개수가 N이고, 정점에 1부터 N까지 번호가 매겨져있는 양방향 그래프가 있을 때, DFS 알고리즘은 다음과 같은 형태로 이루어져 있다.

1
2
3
4
5
6
7
8
9
10
11
12
void dfs(int x) {
    if (check[x] == true) {
        return;
    }
    check[x] = true;
    // x를 방문
    for (int y : x와 인접한 정점) {
        if (check[y] == false) {
            dfs(y);
        }
    }
}
이 문제에서 시작 정점은 1이기 때문에 가장 처음에 호출하는 함수는 dfs(1)이다. DFS 방문 순서는 dfs함수에서 // x를 방문 이라고 적힌 곳에 도착한 정점 번호를 순서대로 나열한 것이다.

트리가 주어졌을 때, 올바른 DFS 방문 순서인지 구해보자.

입력
첫째 줄에 정점의 수 N(2 ≤ N ≤ 100,000)이 주어진다. 둘째 줄부터 N-1개의 줄에는 트리의 간선 정보가 주어진다. 마지막 줄에는 DFS 방문 순서가 주어진다. DFS 방문 순서는 항상 N개의 정수로 이루어져 있으며, 1부터 N까지 자연수가 한 번씩 등장한다.

출력
입력으로 주어진 DFS 방문 순서가 올바른 순서면 1, 아니면 0을 출력한다.

예제 입력 1
4
1 2
1 3
2 4
1 2 3 4
예제 출력 1
0
예제 입력 2
4
1 2
1 3
2 4
1 2 4 3
예제 출력 2
1
예제 입력 3
4
1 2
1 3
2 4
1 3 2 4
예제 출력 3
1
출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: jh05013
데이터를 추가한 사람: kritias
알고리즘 분류
자료 구조
그래프 이론
그래프 탐색
정렬
깊이 우선 탐색
 */
/*
시간초과 이후, 반복 연산을 줄이는 코드로 변경해보아도 그대로 시간초과 발생하여 다른 코드를 참고하였다.
https://nahwasa.com/entry/%EB%B0%B1%EC%A4%80-16964-%EC%9E%90%EB%B0%94-DFS-%EC%8A%A4%ED%8E%98%EC%85%9C-%EC%A0%80%EC%A7%80-BOJ-16964-JAVA

renumbering 방식 : 주변 인접 간선들을 완전탐색하는 것이 아닌 찾아야되는 간선으로 먼저 탐색을 할 수 있도록 간선들을 정렬하는 방법

renumbering을 통해 인접한 간선을 정렬시키면, 각 정점에 대해 입력으로 제시된 순서와 동일한 순서로 방문하며 확인할 수 있기 때문에 연산
속도를 높일 수 있을 것이다.

renumbering 방법을 알아두면 유용할 것 같다.
 */
public class BOJ16964 {
    public static class BOJ16964_Node {
        ArrayList<Integer> adj_nodes;

        public BOJ16964_Node() {
            adj_nodes = new ArrayList<>();
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, start_node = 0, cur_order = 0;
    static boolean flag = true;
    static boolean[] visited;
    static BOJ16964_Node[] nodes;
    static ArrayList<Integer> visit_orders,search_orders;

    public static void main(String[] args) throws IOException {
        init_setting();

        renumbering();

        verification();
    }

    static void verification() {
        if(start_node != 1 || nodes[start_node].adj_nodes.size() == 0) {
            //flag = false;
            System.out.println(0);
        } else {
            visited[start_node] = true;
            //dfs_a(start_node);

            System.out.println(dfs(start_node) ? 1 : 0);

            /*
            visited[start_node] = true;
            dfs_AllSearch(start_node);
            */
        }

        //System.out.println(flag ? 1 : 0);
    }

    static boolean dfs(int cur_node) {
        if(visit_orders.get(cur_order++) != cur_node) {
            return false;
        }

        for(int next_node : nodes[cur_node].adj_nodes) {
            if(visited[next_node]) continue;
            visited[next_node] = true;
            return dfs(next_node);
        }
        return true;
    }

    static void dfs_a(int cur_node) {
        if(visit_orders.get(cur_order++) != cur_node) {
            flag = false;
            return;
        }

        for(int next_node : nodes[cur_node].adj_nodes) {
            if(visited[next_node]) continue;
            visited[next_node] = true;
            dfs(next_node);
        }
    }

    static void dfs_AllSearch(int cur_node) {
        if(cur_order == visit_orders.size()) {
            flag = true;
            return;
        }

        //73% - 시간초과
        /*HashSet<Integer> unvisited_nodes = new HashSet<>();

        for(int adj_node : nodes[cur_node].adj_nodes) {
            if(visited[adj_node]) continue;
            unvisited_nodes.add(adj_node);
        }

        for(int i=0;i<unvisited_nodes.size() && flag;i++) {
            int visit_order_node = visit_orders.get(cur_order);
            if(nodes[cur_node].adj_nodes.contains(visit_order_node)) {
                cur_order++;
                visited[visit_order_node] = true;
                dfs(visit_order_node);
            } else {
                flag = false;
                return;
            }
        }*/

        //73% - 시간초과
        /*for(int node : nodes[cur_node].adj_nodes) {
            if(visited[node]) continue;
            int visit_order_node = visit_orders.get(cur_order);
            if(nodes[cur_node].adj_nodes.contains(visit_order_node)) {
                if(!visited[visit_order_node]) {
                    cur_order++;
                    visited[visit_order_node] = true;
                    dfs(visit_order_node);
                } else {
                    flag = false;
                    return;
                }
            }
        }*/
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        visited = new boolean[N+1];
        nodes = new BOJ16964_Node[N+1];
        visit_orders = new ArrayList<>();
        search_orders = new ArrayList<>();

        for(int i = 1; i < N; i++) {
            visited[i] = false;
            String[] input = br.readLine().split(" ");
            int node_1 = Integer.parseInt(input[0]);
            int node_2 = Integer.parseInt(input[1]);

            if(nodes[node_1] == null) {
                nodes[node_1] = new BOJ16964_Node();
            }
            if(nodes[node_2] == null) {
                nodes[node_2] = new BOJ16964_Node();
            }

            nodes[node_1].adj_nodes.add(node_2);
            nodes[node_2].adj_nodes.add(node_1);
        }
        visited[N] = false;

        /*
        visit_orders = new ArrayList<>(Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList()));

        //0~N-1 까지의 값인 stream을 생성하고, boxed를 통해 Stream<Integer>로 만든 후, visit_orders(index)에 해당하는 값으로 비교하여 오름차순 정렬
        search_orders = IntStream.range(0, visit_orders.size())
                .boxed()
                .sorted((i, j) -> Integer.compare(visit_orders.get(i), visit_orders.get(j)))
                .map(i -> i + 1)
                .collect(Collectors.toCollection(ArrayList::new));

        //for(int i=0;i<visit_orders.size();i++) {
        //    int pos = visit_orders[i];
        //    search_orders[pos] = i+1;
        //}
        */

        /*
        for(int i=0;i<N;i++) {
            visit_orders.add(0);
            search_orders.add(0);
        }

        visit_orders = IntStream.range(0,100)
                .mapToObj(i -> 0)
                .collect(Collectors.toCollection(ArrayList::new));
        */

        for(int i=0;i<=N;i++) {
            visit_orders.add(0);
            search_orders.add(0);
        }

        String[] input_orders = br.readLine().split(" ");
        for(int i=0;i<N;i++) {
            int n = Integer.parseInt(input_orders[i]);
            visit_orders.set(i,n);
            search_orders.set(n,i);
        }


        //start_node = visit_orders.get(cur_order++);
        start_node = visit_orders.get(cur_order);
    }

    static void renumbering() {
        /*
        Arrays.stream(nodes)
                .filter(Objects::nonNull)
                .forEach(node -> Collections.sort(node.adj_nodes, Comparator.comparingInt(search_orders::indexOf)));
        */

        for(int i=1;i<nodes.length;i++) {
            Collections.sort(nodes[i].adj_nodes, (idx1, idx2) -> {
                return search_orders.get(idx1) - search_orders.get(idx2);
            });
        }

        /*for(int i=1;i<nodes.length;i++) {
            Collections.sort(nodes[i].adj_nodes, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return search_orders.get(o1) - search_orders.get(o2);
                }
            });
        }*/
    }
}
