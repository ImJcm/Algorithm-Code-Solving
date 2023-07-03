package BackJoon;/*
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

        /*connected_component = 0;
        Arrays.fill(visited,false);
        //bfs
        for(int i=1;i<=N;i++) {
            if(visited[i]) continue;
            visited[i] = true;
            bfs(i);
            connected_component++;
        }
        System.out.println(connected_component);*/
    }

    static void dfs(int start) {
        for(int i=1;i<node[start].length;i++) {
            if(visited[i]) continue;
            if(node[start][i] == 0) continue;
            visited[i] = true;
            dfs(i);
        }
    }

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
