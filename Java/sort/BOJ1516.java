import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/*
게임 개발

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	14974	7207	5610	48.580%
문제
숌 회사에서 이번에 새로운 전략 시뮬레이션 게임 세준 크래프트를 개발하기로 하였다. 핵심적인 부분은 개발이 끝난 상태고, 종족별 균형과 전체 게임 시간 등을 조절하는 부분만 남아 있었다.

게임 플레이에 들어가는 시간은 상황에 따라 다를 수 있기 때문에, 모든 건물을 짓는데 걸리는 최소의 시간을 이용하여 근사하기로 하였다. 물론, 어떤 건물을 짓기 위해서 다른 건물을 먼저 지어야 할 수도 있기 때문에 문제가 단순하지만은 않을 수도 있다. 예를 들면 스타크래프트에서 벙커를 짓기 위해서는 배럭을 먼저 지어야 하기 때문에, 배럭을 먼저 지은 뒤 벙커를 지어야 한다. 여러 개의 건물을 동시에 지을 수 있다.

편의상 자원은 무한히 많이 가지고 있고, 건물을 짓는 명령을 내리기까지는 시간이 걸리지 않는다고 가정하자.

입력
첫째 줄에 건물의 종류 수 N(1 ≤ N ≤ 500)이 주어진다. 다음 N개의 줄에는 각 건물을 짓는데 걸리는 시간과 그 건물을 짓기 위해 먼저 지어져야 하는 건물들의 번호가 주어진다. 건물의 번호는 1부터 N까지로 하고, 각 줄은 -1로 끝난다고 하자. 각 건물을 짓는데 걸리는 시간은 100,000보다 작거나 같은 자연수이다. 모든 건물을 짓는 것이 가능한 입력만 주어진다.

출력
N개의 각 건물이 완성되기까지 걸리는 최소 시간을 출력한다.

예제 입력 1
5
10 -1
10 1 -1
4 1 -1
4 3 1 -1
3 3 -1
예제 출력 1
10
20
14
18
17
 */
/*
    그래프 문제에서 각 노드간에 순서가 필요한 그래프 즉, 사이클이 없고 방향그래프인 DAG에서
    순서조건에 맞는 노드를 탐색하는 방법으로 topological_sort(위상정렬)을 사용하여 탐색을 진행할 수 있다.
    우선순위 큐를 사용하여 큐에 처음에 적용될 노드를 선택하기 위해 compare함수를 통해 오름차순 또는 내림차순으로 정의하여
    조건에 맞게 동작과정을 수행한다
    핵심 : 위상정렬 + BFS + 그래프
 */
public class BOJ1516 {
    static int N;
    static int[] dist;
    static int[] indegree;
    static ArrayList<Building>[] order;
    static PriorityQueue<Building> pq = new PriorityQueue<>((b1,b2) -> b2.time - b1.time);

    static class Building {
        int time;
        int nowBuilding;

        Building(int t,int nb) {
            this.time = t;
            this.nowBuilding = nb;
        }
    }

    public static void main(String[] args) throws IOException, NumberFormatException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        dist = new int[N+1];
        Arrays.fill(dist,-1);
        indegree = new int[N+1];
        Arrays.fill(indegree,0);

        order = new ArrayList[N+1];
        order[0] = new ArrayList<Building>();
        order[0].add(new Building(0,0));

        for(int i=0;i<=N;i++) {
            order[i] = new ArrayList<Building>();
        }
        for(int i=1;i<=N;i++) {
            String[] str = br.readLine().split(" ");

            //처음으로 시작하는 건물
            if(str.length == 2) {
                indegree[i] = 0;
                dist[i] = Integer.parseInt(str[0]);
                pq.offer(new Building(Integer.parseInt(str[0]),i));
                continue;
            }
            //건물번호 1~N
            for(int j=1;j<str.length-1;j++) {
                order[Integer.parseInt(str[j])].add(new Building(Integer.parseInt(str[0]),i));
                indegree[i]++;
            }
        }

        topological_sorting();

        for(int i=1;i<dist.length;i++) {
            System.out.println(dist[i]);
        }
    }

    //위상정렬 + 우선순위 큐
    static void topological_sorting() {
        //b2 - b1 = 내림차순, b1 - b2 = 오름차순
        //PriorityQueue<Building> pq = new PriorityQueue<Building>((b1, b2) -> b2.time - b1.time);

        while(!pq.isEmpty()) {
            Building b = pq.poll();

            for(Building nb : order[b.nowBuilding]) {
                indegree[nb.nowBuilding]--;

                if(dist[b.nowBuilding] + nb.time > dist[nb.nowBuilding]) {
                    dist[nb.nowBuilding] = dist[b.nowBuilding] + nb.time;
                }

                if(indegree[nb.nowBuilding] == 0) {
                    pq.offer(nb);
                }
            }
        }
    }
}
