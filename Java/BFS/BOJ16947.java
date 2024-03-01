package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
서울 지하철 2호선

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	7756	3525	2293	43.560%
문제
서울 지하철 2호선은 다음과 같이 생겼다.



지하철 2호선에는 51개의 역이 있고, 역과 역 사이를 연결하는 구간이 51개 있다. 즉, 정점이 51개이고, 양방향 간선이 51개인 그래프로 나타낼 수 있다. 2호선은 순환선 1개와 2개의 지선으로 이루어져 있다. 한 역에서 출발해서 계속 가면 다시 출발한 역으로 돌아올 수 있는 노선을 순환선이라고 한다. 지선은 순환선에 속하는 한 역에서 시작하는 트리 형태의 노선이다.

두 역(정점) 사이의 거리는 지나야 하는 구간(간선)의 개수이다. 역 A와 순환선 사이의 거리는 A와 순환선에 속하는 역 사이의 거리 중 최솟값이다.

지하철 2호선과 같은 형태의 노선도가 주어졌을 때, 각 역과 순환선 사이의 거리를 구해보자.

입력
첫째 줄에 역의 개수 N(3 ≤ N ≤ 3,000)이 주어진다. 둘째 줄부터 N개의 줄에는 역과 역을 연결하는 구간의 정보가 주어진다. 같은 구간이 여러 번 주어지는 경우는 없고, 역은 1번부터 N번까지 번호가 매겨져 있다. 임의의 두 역 사이에 경로가 항상 존재하는 노선만 입력으로 주어진다.

출력
총 N개의 정수를 출력한다. 1번 역과 순환선 사이의 거리, 2번 역과 순환선 사이의 거리, ..., N번 역과 순환선 사이의 거리를 공백으로 구분해 출력한다.

예제 입력 1
4
1 3
4 3
4 2
1 2
예제 출력 1
0 0 0 0
예제 입력 2
6
1 2
3 4
6 4
2 3
1 3
3 5
예제 출력 2
0 0 0 1 1 2
예제 입력 3
51
1 2
2 3
3 4
4 5
5 6
6 7
7 8
8 9
9 10
10 11
11 12
12 13
13 14
14 15
15 16
16 17
17 18
18 19
19 20
20 21
21 22
22 23
23 24
24 25
25 26
26 27
27 28
28 29
29 30
30 31
31 32
32 33
33 34
34 35
35 36
36 37
37 38
38 39
39 40
40 41
41 42
42 43
43 1
11 44
44 45
45 46
46 47
34 48
48 49
49 50
50 51
예제 출력 3
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 2 3 4 1 2 3 4
서울 지하철 2호선 노선이다.

1번부터 43번까지는 역 번호와 일치하며, 성수역은 11번, 신도림역은 34번이다.

예제 입력 4
38
1 2
2 3
3 4
4 5
5 6
6 1
1 7
7 8
8 9
9 10
10 11
11 12
12 13
13 14
14 15
15 16
16 17
17 18
18 19
19 20
20 21
21 22
22 23
23 24
24 25
25 26
26 27
27 28
28 29
29 30
30 31
31 32
32 33
33 34
34 35
35 36
36 37
37 38
예제 출력 4
0 0 0 0 0 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32
서울 지하철 6호선이다. 실제로는 일부 구간이 양방향이 아니다.

예제 입력 5
12
1 3
3 4
4 5
5 6
6 7
7 8
8 4
2 3
7 9
9 12
7 10
10 11
예제 출력 5
2 2 1 0 0 0 0 0 1 1 2 2
2489번: 응급센터 문제에 나와있는 예제 그래프이다.

출처
문제를 번역한 사람: baekjoon
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
깊이 우선 탐색
 */
/*
1. 순환선 구간을 찾는다. - DFS
ㄴ flag를 통해 순환선이 있는지 검사하고 순환선 역을 ringRoad에 저장한다.
ㄴ 순환선을 구하는 ringRoad_search()는 모든 순환선을 구해야 한다.
    ㄴ 겹치는 순환선 역이 존재할 수 있으므로 중복되는 순환선 역을 제거하기 위해 LinkedHashSet 사용

2. 순환선 구간을 기준으로 순환선 구간을 제외한 역을 대상으로 BFS 진행
ㄴ 모든 순환선이 구해지면 순환선으로 판단된 역에서 다른 역 사이의 최소 거리를 BFS로 최단 거리 값을 업데이트한다.

문제를 읽고 순환선이 단순하게 1개만 있을리가 없기 때문에 어떤씩으로 모든 순환선을 구할 수 있는지와 어떤방식으로 코드를 작성해야하는지 생각하느라 조금 오래 걸렸다.
많은 문제를 경험하는 것을 통해 극복할 수 있다고 생각한다.
 */
public class BOJ16947 {
    static class BOJ16947_station {
        int min_distance_from_ringRoad = Integer.MAX_VALUE;
        ArrayList<Integer> adjacency_station;

        public BOJ16947_station(int md) {
            this.min_distance_from_ringRoad = md;
            this.adjacency_station = new ArrayList<>();
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static boolean flag = false;
    static boolean[] visited,visited2;
    static ArrayList<BOJ16947_station> stations;
    static LinkedHashSet<Integer> ringRoad;

    public static void main(String[] args) throws IOException {
        init_setting();

        search_ringRoad();

        measure_distance();

        print();
    }

    static void search_ringRoad() {
        for(int i=0;i<N;i++) {
            if(stations.get(i).adjacency_station.size() > 1) {
                visited[i] = true;
                dfs(0,-1,i,i);
                visited[i] = false;
                flag = false;
            }
        }
    }

    static void dfs(int depth,int pre, int start, int dest) {
        if(depth >= 2 && flag) {
            return;
        }

        for(int s : stations.get(start).adjacency_station) {
            if(visited[s]) {
                if(dest == s && pre != s) {
                    for(int i=0;i<visited.length;i++) {
                        if(visited[i]) {
                            ringRoad.add(i);
                            stations.get(i).min_distance_from_ringRoad = 0;
                        }
                    }
                    flag = true;
                    return;
                }
                continue;
            }
            visited[s] = true;
            dfs(depth+1, start, s, dest);
            visited[s] = false;
        }
    }

    static void measure_distance() {
        for(int s : ringRoad) {
            bfs(s);
        }
    }

    static void bfs(int start) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);

        visited2[start] = true;

        while(!q.isEmpty()) {
            int adj_station = q.poll();
            for(int s : stations.get(adj_station).adjacency_station) {
                if(ringRoad.contains(s) || visited2[s]) {
                    continue;
                }
                visited2[s] = true;

                stations.get(s).min_distance_from_ringRoad =
                        Math.min(stations.get(s).min_distance_from_ringRoad, stations.get(adj_station).min_distance_from_ringRoad + 1);
                q.offer(s);
            }
        }
    }

    static void print() {
        for(BOJ16947_station s : stations) {
            System.out.print(s.min_distance_from_ringRoad + " ");
        }
    }

    static void init_setting() throws IOException{
        stations = new ArrayList<>();
        ringRoad = new LinkedHashSet<>();

        N = Integer.parseInt(br.readLine());

        visited = new boolean[N];
        visited2 = new boolean[N];

        for(int i=0;i<N;i++) {
            stations.add(new BOJ16947_station(Integer.MAX_VALUE));
            visited[i] = false;
            visited2[i] = false;
        }

        for(int i=0;i<N;i++) {
            String[] edges = br.readLine().split(" ");
            int start_station = Integer.parseInt(edges[0]);
            int destination_station = Integer.parseInt(edges[1]);
            stations.get(start_station - 1).adjacency_station.add(destination_station - 1);
            stations.get(destination_station - 1).adjacency_station.add(start_station - 1);
        }
    }
}
