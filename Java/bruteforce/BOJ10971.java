package BOJ.bruteforce;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
외판원 순회 2(silver 2)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	35706	12880	7597	33.776%
문제
외판원 순회 문제는 영어로 Traveling Salesman problem (TSP) 라고 불리는 문제로 computer science 분야에서 가장 중요하게 취급되는 문제 중 하나이다. 여러 가지 변종 문제가 있으나, 여기서는 가장 일반적인 형태의 문제를 살펴보자.

1번부터 N번까지 번호가 매겨져 있는 도시들이 있고, 도시들 사이에는 길이 있다. (길이 없을 수도 있다) 이제 한 외판원이 어느 한 도시에서 출발해 N개의 도시를 모두 거쳐 다시 원래의 도시로 돌아오는 순회 여행 경로를 계획하려고 한다. 단, 한 번 갔던 도시로는 다시 갈 수 없다. (맨 마지막에 여행을 출발했던 도시로 돌아오는 것은 예외) 이런 여행 경로는 여러 가지가 있을 수 있는데, 가장 적은 비용을 들이는 여행 계획을 세우고자 한다.

각 도시간에 이동하는데 드는 비용은 행렬 W[i][j]형태로 주어진다. W[i][j]는 도시 i에서 도시 j로 가기 위한 비용을 나타낸다. 비용은 대칭적이지 않다. 즉, W[i][j] 는 W[j][i]와 다를 수 있다. 모든 도시간의 비용은 양의 정수이다. W[i][i]는 항상 0이다. 경우에 따라서 도시 i에서 도시 j로 갈 수 없는 경우도 있으며 이럴 경우 W[i][j]=0이라고 하자.

N과 비용 행렬이 주어졌을 때, 가장 적은 비용을 들이는 외판원의 순회 여행 경로를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 도시의 수 N이 주어진다. (2 ≤ N ≤ 10) 다음 N개의 줄에는 비용 행렬이 주어진다. 각 행렬의 성분은 1,000,000 이하의 양의 정수이며, 갈 수 없는 경우는 0이 주어진다. W[i][j]는 도시 i에서 j로 가기 위한 비용을 나타낸다.

항상 순회할 수 있는 경우만 입력으로 주어진다.

출력
첫째 줄에 외판원의 순회에 필요한 최소 비용을 출력한다.

예제 입력 1
4
0 10 15 20
5 0 9 10
6 13 0 12
8 8 9 0
예제 출력 1
35
 */
/*
방법 : 1. bruteforce, 2. recursive
처음 제출은 처음node로 돌아오는 것을 생각하지 않아서 틀린 것 같다.
 */
public class BOJ10971 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int path[][] = new int[10][10];
    static boolean[] visited = new boolean[10];
    static int min_cost = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        for(int i=0;i<N;i++) {
            path[i] = Arrays.asList(br.readLine().split(" "))
                    .stream()
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        //시작node와 출발node를 지정하여 DFS수행
        for(int i=0;i<N;i++) {
            Arrays.fill(visited,false);
            visited[i] = true;
            shortestpath(0,i,0,i);

        }


        System.out.println(min_cost);
    }

    static void shortestpath(int depth, int here_node,int current_cost,int start_node) {
        //시작node를 main에서 정했기 때문에, depth = N - 1
        //depth = N-1이면, 현재 node에서 start node로 갈 수 있는지 여부를 판단하고 가능하면, 경로의 최소값을 비교하고 업데이트
        if(depth == N-1) {
            if(path[here_node][start_node] != 0) {
                min_cost = Math.min(current_cost + path[here_node][start_node], min_cost);
            }
            return;
        }

        for(int next=0;next<N;next++) {
            if(visited[next] || path[here_node][next] == 0) {
                continue;
            }
            visited[next] = true;
            shortestpath(depth+1, next,current_cost + path[here_node][next],start_node);
            visited[next] = false;
        }
    }
}
