/*
DFS와 BFS

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	200648	73815	43808	35.825%
문제
그래프를 DFS로 탐색한 결과와 BFS로 탐색한 결과를 출력하는 프로그램을 작성하시오. 단, 방문할 수 있는 정점이 여러 개인 경우에는 정점 번호가 작은 것을 먼저 방문하고, 더 이상 방문할 수 있는 점이 없는 경우 종료한다. 정점 번호는 1번부터 N번까지이다.

입력
첫째 줄에 정점의 개수 N(1 ≤ N ≤ 1,000), 간선의 개수 M(1 ≤ M ≤ 10,000), 탐색을 시작할 정점의 번호 V가 주어진다. 다음 M개의 줄에는 간선이 연결하는 두 정점의 번호가 주어진다. 어떤 두 정점 사이에 여러 개의 간선이 있을 수 있다. 입력으로 주어지는 간선은 양방향이다.

출력
첫째 줄에 DFS를 수행한 결과를, 그 다음 줄에는 BFS를 수행한 결과를 출력한다. V부터 방문된 점을 순서대로 출력하면 된다.

예제 입력 1
4 5 1
1 2
1 3
1 4
2 4
3 4
예제 출력 1
1 2 4 3
1 2 3 4
예제 입력 2
5 5 3
5 4
5 2
1 2
3 4
3 1
예제 출력 2
3 1 2 5 4
3 1 4 2 5
예제 입력 3
1000 1 1000
999 1000
예제 출력 3
1000 999
1000 999
 */
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.*;

public class BOJ1260 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int V = Integer.parseInt(st.nextToken());

        int[][] node = new int[N+1][N+1];
        Boolean[] visited = new Boolean[N+1];
        Arrays.fill(visited,false);


        for(int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            node[from][to] = node[to][from] = 1;
        }

        DFS(V,node,visited);
        Arrays.fill(visited,false);
        System.out.println();
        BFS(V,node,visited);
    }

    static void DFS(int v, int[][] node,Boolean[] visit) {
        if(visit[v]) return;

        visit[v] = true;
        System.out.print(v + " ");

        for(int i=1;i<node.length;i++) {
            if(!visit[i] && node[v][i] == 1) {
                DFS(i,node,visit);
            }
        }
    }

    static void BFS(int v, int[][] node, Boolean[] visit) {
        StringBuilder sb = new StringBuilder();

        Queue<Integer> q = new LinkedList<>();
        q.offer(v);
        visit[v] = true;

        while(!q.isEmpty()) {
            v = q.poll();
            sb.append(v + " ");
            for(int i=1;i<node.length;i++) {
                if(!visit[i] && node[v][i] == 1) {
                    q.offer(i);
                    visit[i] = true;
                }
            }
        }
        System.out.println(sb.toString());
    }
}
