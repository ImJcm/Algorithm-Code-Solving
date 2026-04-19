package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
환승 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	7934	2051	1410	26.474%
문제
아주 먼 미래에 사람들이 가장 많이 사용하는 대중교통은 하이퍼튜브이다. 하이퍼튜브 하나는 역 K개를 서로 연결한다. 1번역에서 N번역으로 가는데 방문하는 최소 역의 수는 몇 개일까?

입력
첫째 줄에 역의 수 N과 한 하이퍼튜브가 서로 연결하는 역의 개수 K, 하이퍼튜브의 개수 M이 주어진다. (1 ≤ N ≤ 100,000, 1 ≤ K, M ≤ 1000)

다음 M개 줄에는 하이퍼튜브의 정보가 한 줄에 하나씩 주어진다. 총 K개 숫자가 주어지며, 이 숫자는 그 하이퍼튜브가 서로 연결하는 역의 번호이다.

출력
첫째 줄에 1번역에서 N번역으로 가는데 방문하는 역의 개수의 최솟값을 출력한다. 만약, 갈 수 없다면 -1을 출력한다.

예제 입력 1
9 3 5
1 2 3
1 4 5
3 6 7
5 6 7
6 8 9
예제 출력 1
4
1-3-6-9나 1-5-6-9로 이동하면 된다.

예제 입력 2
15 8 4
11 12 8 14 13 6 10 7
1 5 8 12 13 6 2 4
10 15 4 5 9 8 14 12
11 12 14 3 5 6 1 13
예제 출력 2
3
 */
/*
    3개의 노드를 연결해주는 하이퍼튜브 노드를 추가하고 새로운 그래프를 짠 후, BFS를 수행하여,
    출발점에서 도착점까지의 역의 개수를 출력
    이때, 방문하는 과정에서 노드에 최단 거리 값을 업데이트하면서 도착점까지 도달하면 된다.
    즉, return dist[N]
    
    핵심 : BFS
 */
public class BOJ5214 {
    static int N,K,M;
    static ArrayList<Station>[] hyperTube;
    static boolean[] visited;
    static ArrayList<Integer> End_point = new ArrayList<>();
    static int minCost = Integer.MAX_VALUE;

    static class Station {
        int station;
        int dist;
        int transfer = 0;

        Station(int s,int d) {
            this.station = s;
            this.dist = d;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        visited = new boolean[N+M+1];
        Arrays.fill(visited,false);

        hyperTube = new ArrayList[N+M+1];
        for(int i=0;i<=(N+M);i++) {
            hyperTube[i] = new ArrayList<>();
        }

        for(int i=1;i<=M;i++) {
            st = new StringTokenizer(br.readLine());

            for(int j=0;j<K;j++) {
                int n = Integer.parseInt(st.nextToken());
                hyperTube[n].add(new Station(N+i,101001));
                hyperTube[N+i].add(new Station(n,101001));
            }
        }

        BFS();

        //Case_1 - output
        //N=1, K=1, M=1일 경우, 하이퍼튜브를 거쳐서 들어가면 안되기 때문에,
        //1 -> 2 -> 1이 아닌, 1 -> 1이므로, "1" 출력
        if(N==1 && K == 1) System.out.println("1");
        else {
            if (End_point.size() != 0) {
                //System.out.println(hyperTube[N].get(0).dist - hyperTube[N].get(0).transfer);
                int min_cost = Integer.MAX_VALUE;
                for (int sidx : End_point) {
                    for (Station s : hyperTube[sidx]) {
                        if (s.station == N) {
                            min_cost = Math.min(min_cost, (s.dist - s.transfer));
                        }
                    }
                }
                System.out.println(min_cost);
            } else {
                System.out.println("-1");
            }
        }

        //Case_2
//        if(N==1 && K == 1) System.out.println("1");
//        else System.out.println(minCost);
    }

    static void BFS() {
        Queue<Station> q = new LinkedList<>();
        q.offer(new Station(1,1));
        visited[1] = true;

        while(!q.isEmpty()) {
            Station s = q.poll();

            for(Station NextStation : hyperTube[s.station]) {
                if(!visited[NextStation.station] && NextStation.dist > s.dist + 1) {
                    if(NextStation.station > N) {
                        NextStation.transfer = s.transfer + 1;
                    } else {
                        NextStation.transfer = s.transfer;
                    }
                    NextStation.dist = s.dist + 1;
                    q.offer(NextStation);
                    visited[NextStation.station] = true;
                }

                if(NextStation.station == N) {
                    //Case_1
                    End_point.add(s.station);

                    //Case_2
                    //minCost = Math.min(minCost, s.dist - s.transfer);
                }
            }
        }
    }
}
