package BFS;/*
연결 요소의 개수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
3 초	512 MB	79701	36397	23990	42.794%
문제
방향 없는 그래프가 주어졌을 때, 연결 요소 (Connected Component)의 개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 정점의 개수 N과 간선의 개수 M이 주어진다. (1 ≤ N ≤ 1,000, 0 ≤ M ≤ N×(N-1)/2) 둘째 줄부터 M개의 줄에 간선의 양 끝점 u와 v가 주어진다. (1 ≤ u, v ≤ N, u ≠ v) 같은 간선은 한 번만 주어진다.

출력
첫째 줄에 연결 요소의 개수를 출력한다.

예제 입력 1
6 5
1 2
2 5
5 1
3 4
4 6
예제 출력 1
2
예제 입력 2
6 8
1 2
2 5
5 1
3 4
4 6
5 4
2 4
2 3
예제 출력 2
1
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ11724 {
    static int N, M;
    static int[][] node;
    static boolean[] visited;
    static int connected_component = 0;
    static Queue<Integer> queue;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        node = new int[N+1][N+1];
        visited = new boolean[N+1];

        Arrays.fill(visited,false);
        for(int i=0;i<N;i++) {
            Arrays.fill(node[i+1],0);
        }
        for(int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            node[from][to] = node[to][from] = 1;
        }

        //dfs
        for(int i=1;i<=N;i++) {
            if(visited[i]) continue;
            visited[i] = true;
            dfs(i);
            connected_component++;
        }
        System.out.println(connected_component);

        connected_component = 0;
        Arrays.fill(visited,false);
        //bfs
        for(int i=1;i<=N;i++) {
            if(visited[i]) continue;
            visited[i] = true;
            bfs(i);
            connected_component++;
        }
        System.out.println(connected_component);
    }

    /*
        start 노드에서 시작하여 모든 노드와 연결상태와 방문여부를 확인하고, 방문하지 않았고, 연결된 상태의 노드가 있다면,
        해당 노드를 방문 체크 후 위의 과정을 dfs 재귀호출을 통해 가능할 때까지 수행한다.
        이때, dfs가 불가능하다면, 연결 상태 수를 증가시킨다.
        => i(1)부터 시작하여 연결된 노드들을 모두 검사하고 dfs가 종료될 때는 더이상의 연결이 가능한 노드들이 없다는 의미이기 때문에
            i+1 ~ N까지 dfs()를 호출하여 다른 연결상태의 노드들을 찾는 과정을 거쳐서 모든 노드가 방문될 때까지 연결상태 수를 +1 수행한다.
     */
    static void dfs(int start) {
        for(int i=1;i<node[start].length;i++) {
            if(visited[i]) continue;
            if(node[start][i] == 0) continue;
            visited[i] = true;
            dfs(i);
        }
    }

    /*
        bfs형태로 start노드와 방문하지 않고 연결된 노드들을 queue에 담고 방문여부를 체크해준다. 이 과정을
        queue가 비워질 때 까지 수행한다. 위 과정이 끝난다면 하나의 연결 상태를 만든 것이므로 연결상태 수를 +1
        그리고, 방문하지 않는 노드가 아직 존재한다면 bfs(index)를 호출하여 다음 연결 상태를 검사하는 과정을 수행한다.
     */
    static void bfs(int start) {
        queue = new LinkedList<>();
        queue.offer(start);

        while(!queue.isEmpty()) {
            start = queue.poll();
            for(int i=1;i<=N;i++) {
                if(!visited[i] && node[start][i] == 1) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
        }

    }

}
