package bruteforce;/*
이분 그래프

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	70526	18554	11136	23.490%
문제
그래프의 정점의 집합을 둘로 분할하여, 각 집합에 속한 정점끼리는 서로 인접하지 않도록 분할할 수 있을 때, 그러한 그래프를 특별히 이분 그래프 (Bipartite Graph) 라 부른다.

그래프가 입력으로 주어졌을 때, 이 그래프가 이분 그래프인지 아닌지 판별하는 프로그램을 작성하시오.

입력
입력은 여러 개의 테스트 케이스로 구성되어 있는데, 첫째 줄에 테스트 케이스의 개수 K가 주어진다. 각 테스트 케이스의 첫째 줄에는 그래프의 정점의 개수 V와 간선의 개수 E가 빈 칸을 사이에 두고 순서대로 주어진다. 각 정점에는 1부터 V까지 차례로 번호가 붙어 있다. 이어서 둘째 줄부터 E개의 줄에 걸쳐 간선에 대한 정보가 주어지는데, 각 줄에 인접한 두 정점의 번호 u, v (u ≠ v)가 빈 칸을 사이에 두고 주어진다.

출력
K개의 줄에 걸쳐 입력으로 주어진 그래프가 이분 그래프이면 YES, 아니면 NO를 순서대로 출력한다.

제한
2 ≤ K ≤ 5
1 ≤ V ≤ 20,000
1 ≤ E ≤ 200,000
예제 입력 1
2
3 2
1 3
2 3
4 4
1 2
2 3
3 4
4 2
예제 출력 1
YES
NO
 */
/*
    이분그래프(bipartite graph) 알고리즘
    인접한 정점끼리 서로 다른 색으로 칠해서 모든 정점을 두 가지 색으로만 칠할 수 있는 그래프를 의미힌다
    즉, 그래프의 모든 정점이 두 그룹으로 나눠지고 서로 다른 그룹의 정점이 간선으로 연결되어져 있는 그래프를 이분 그래프라고 한다

    이분 그래프의 특징
    1. 이분 그래프인지 확인하는 방법은 DFS, BFS탐색을 이용
    2. 이분 그래프는 BFS할 때 같은 레벨의 정점끼리는 무조건 같은 색으로 칠해진다
    3. 연결 성분의 개수(Connected Component)를 구하는 방법과 유사
    4. 모든 정점을 방문하며 간선을 검사하기 때문에 시간복잡도는 O(E+V)로 그래프 탐색 알고리즘과 같다
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ1707 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static ArrayList<ArrayList<Integer>> node;

    static final int Red = 1;
    static final int Black = -1;
    static int[] colors;
    static boolean checkBipartite;

    public static void main(String[] args) throws IOException {
        int N = Integer.parseInt(br.readLine());

        while(N-- > 0) {
            st = new StringTokenizer(br.readLine());
            int V = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());

            node = new ArrayList<>();
            colors = new int[V+1];
            checkBipartite = true;

            for(int i=0;i<V+1;i++) {
                node.add(new ArrayList<Integer>());
                colors[i] = 0;
            }

            while(E-- > 0) {
                st = new StringTokenizer(br.readLine());
                int v1 = Integer.parseInt(st.nextToken());
                int v2 = Integer.parseInt(st.nextToken());

                node.get(v1).add(v2);
                node.get(v2).add(v1);
            }

            for(int i=1;i<V+1;i++) {
                if(!checkBipartite) {
                    break;
                }

                if(colors[i] == 0) {
                    dfs(i,Black);
                    bfs(i,Black);
                }
            }
            System.out.println(checkBipartite ? "YES":"NO");
        }
    }

    static void dfs(int start, int color) {
        colors[start] = color;

        for(int adj : node.get(start)) {
            if(colors[adj] == color) {
                checkBipartite = false;
                return;
            }

            if(colors[adj] == 0) {
                dfs(adj,-color);
            }
        }
    }

    static void bfs(int start,int color) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        colors[start] = color;

        while(!queue.isEmpty()) {
            int v = queue.poll();

            for(int adj : node.get(v)) {
                if(colors[adj] == 0) {
                    queue.offer(adj);
                    colors[adj] = colors[v] * -1;
                } else if(colors[v] + colors[adj] != 0) {
                    checkBipartite = false;
                    return;
                }
            }
        }


    }
}
