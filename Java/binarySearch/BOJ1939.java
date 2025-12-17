package binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
중량제한

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	45150	13070	8081	26.866%
문제
N(2 ≤ N ≤ 10,000)개의 섬으로 이루어진 나라가 있다. 이들 중 몇 개의 섬 사이에는 다리가 설치되어 있어서 차들이 다닐 수 있다.

영식 중공업에서는 두 개의 섬에 공장을 세워 두고 물품을 생산하는 일을 하고 있다. 물품을 생산하다 보면 공장에서 다른 공장으로 생산 중이던 물품을 수송해야 할 일이 생기곤 한다. 그런데 각각의 다리마다 중량제한이 있기 때문에 무턱대고 물품을 옮길 순 없다. 만약 중량제한을 초과하는 양의 물품이 다리를 지나게 되면 다리가 무너지게 된다.

한 번의 이동에서 옮길 수 있는 물품들의 중량의 최댓값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N, M(1 ≤ M ≤ 100,000)이 주어진다. 다음 M개의 줄에는 다리에 대한 정보를 나타내는 세 정수 A, B(1 ≤ A, B ≤ N), C(1 ≤ C ≤ 1,000,000,000)가 주어진다. 이는 A번 섬과 B번 섬 사이에 중량제한이 C인 다리가 존재한다는 의미이다. 서로 같은 두 섬 사이에 여러 개의 다리가 있을 수도 있으며, 모든 다리는 양방향이다. 마지막 줄에는 공장이 위치해 있는 섬의 번호를 나타내는 서로 다른 두 정수가 주어진다. 공장이 있는 두 섬을 연결하는 경로는 항상 존재하는 데이터만 입력으로 주어진다.

출력
첫째 줄에 답을 출력한다.

예제 입력 1
3 3
1 2 2
3 1 3
2 3 2
1 3
예제 출력 1
3
출처
문제의 오타를 찾은 사람: lg8375
데이터를 추가한 사람: djm03178, oud3noida
빠진 조건을 찾은 사람: TAMREF
알고리즘 분류
자료 구조
그래프 이론
그래프 탐색
이분 탐색
너비 우선 탐색
최단 경로
데이크스트라
분리 집합
 */
/*
알고리즘 핵심
BFS + 이분 탐색
1. 섬과 다리 정보를 저장히기 위해 Bridge 구조체와 배열을 사용하여 형태를 구성한다.
2. 출발하는 섬과 도착하는 섬의 다리 중 최대 무게 중 최소값을 r로 설정하고 l을 1로 설정하여 이분 탐색을 진행하여 무게를 지정한다.
3. 해당 무게로 S,E를 이동하는 경로가 있는지 BFS를 수행한다.
4. 섬의 이동이 가능하면, 무게를 늘려 이동이 가능한지 중간값을 재설정하고, 불가능하면 중간값을 낮추어 재설정하여 최대 무게를 구한다.

처음 구현한 방식의 경우 섬과 다리의 구조체를 만들어 한번의 코드로 영속성 전이와 같은 개념을 이용하여 간편하게 양방향 다리 설정을 하려고했으나
해당 방법으로 시간 초과를 받았다고 판단하여 정보를 구성하는 과정을 수정하였다.

구현은 단순하게 하는 것이 좋다는 결론이다.
 */
public class BOJ1939 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        public class Bridge {
            int dest,weight;

            public Bridge(int d, int w) {
                this.dest = d;
                this.weight = w;
            }
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,M,S,E,l,r,ans;
        ArrayList<ArrayList<Bridge>> bridges;

        private void solve() throws IOException {
            init_setting();

            binary_search();

            System.out.println(ans);
        }

        private void binary_search() {
            if(l > r) return;

            int m = (l + r) / 2;

            if(BFS(m)) l = m + 1;
            else r = m - 1;

            binary_search();
        }

        private boolean BFS(int w) {
            Queue<Integer> q = new LinkedList<>();
            boolean[] visited = new boolean[N + 1];

            q.add(S);
            visited[S] = true;

            while(!q.isEmpty()) {
                Integer i = q.poll();

                if(i == E) {
                    ans = w;
                    return true;
                }

                for(Bridge b : bridges.get(i)) {
                    if(visited[b.dest] || b.weight < w) continue;

                    visited[b.dest] = true;
                    q.add(b.dest);
                }
            }

            return false;
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            M = Integer.parseInt(input[1]);

            bridges = new ArrayList<>();

            for(int i = 0; i <= N; i++) {
                bridges.add(new ArrayList<>());
            }

            for(int i = 0; i < M; i++) {
                input = br.readLine().split(" ");

                int A = Integer.parseInt(input[0]);
                int B = Integer.parseInt(input[1]);
                int C = Integer.parseInt(input[2]);

                bridges.get(A).add(new Bridge(B,C));
                bridges.get(B).add(new Bridge(A,C));
            }

            input = br.readLine().split(" ");

            S = Integer.parseInt(input[0]);
            E = Integer.parseInt(input[1]);

            l = 1;
            r = Math.min(bridges.get(S).stream()
                    .map(o -> o.weight)
                    .max(Integer::compareTo)
                    .get()
                ,bridges.get(E).stream()
                            .map(o -> o.weight)
                            .max(Integer::compareTo)
                            .get());

            ans = 0;
        }
    }

    /*
        53% time out
        reason : 2개의 구조체가 서로 참조하는 형태로 섬과 다리로 분리되어 정보를 저장하기 때문에 시간 초과가 발생한 것 같다.
        섬과 다리에 대한 정보를 다음과 같이 분리한다.
        1. 섬에 대한 정보는 배열을 생성하여 인덱스를 통해 섬을 구분하고, 각 배열의 요소에는 다리의 정보들을 저장하기 위해 배열을 만든다.
        2. 1번에서 생성한 배열에 저장할 정보로 (목적지, 중량) 정보를 저장하는 다리 구조체를 만든다.
        3. 1,2번을 통해 arr[A] = new ArrayList<>() => 해당 배열에 Bridge 구조체를 저장한다.
            (arr[A].add(new Bridge(B,C)); => A 섬에서 B 섬으로 C의 무게를 견디는 다리가 있다.
            arr[B].add(new Bridge(A,C)) => 양방향을 만족하기 위해 추가한다.

     */
    public static class TimeOut_Wrong_Solve {
        public class Bridge {
            int weight;
            Island island;

            public Bridge(Island i, int w) {
                this.island = i;
                this.weight = w;
            }

            int getWeight() {
                return this.weight;
            }
        }
        public class Island {
            int n;
            ArrayList<Bridge> bridges;

            public Island(int n) {
                this.n = n;
                this.bridges = new ArrayList<>();
            }

            ArrayList<Bridge> getConnected() {
                return this.bridges;
            }

            void addConnected(Bridge b, Bridge d) {
                this.bridges.add(b);
                if(b.island.getConnected().contains(d)) return;
                b.island.addConnected(new Bridge(this,b.getWeight()),b);
            }
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,M,l,r,ans;
        Island S,E;
        ArrayList<Island> islands;

        private void solve() throws IOException {
            init_setting();

            binary_search();    // search max_weight

            System.out.println(ans);
        }

        // S -> E로 이동하는 동안의 최대 무게 지정 : 이분 탐색
        private void binary_search() {
            if(l > r) return;

            int m = (l + r) / 2;

            if(BFS(m)) l = m + 1;
            else r = m - 1;

            binary_search();
        }

        // 이분 탐색을 통해 나온 최대 무게로 BFS 수행하여 도달하는지 여부 확인
        private boolean BFS(int w) {
            Queue<Island> q = new LinkedList<>();
            boolean[] visited = new boolean[N + 1];

            visited[S.n] = true;
            q.add(S);

            while(!q.isEmpty()) {
                Island cur = q.poll();

                if(cur.n == E.n) {
                    ans = Math.max(ans, w);
                    return true;
                }

                for(Bridge b : cur.bridges) {
                    if(b.weight < w || visited[b.island.n]) continue;

                    q.add(islands.get(b.island.n));
                    visited[b.island.n] = true;
                }
            }

            return false;
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            M = Integer.parseInt(input[1]);

            islands = new ArrayList<>();

            for(int i = 0; i <= N; i++) {
                islands.add(new Island(i));
            }

            for(int i = 0; i < M; i++) {
                input = br.readLine().split(" ");

                int A = Integer.parseInt(input[0]);
                int B = Integer.parseInt(input[1]);
                int C = Integer.parseInt(input[2]);

                islands.get(A).addConnected(new Bridge(islands.get(B),C),null);
            }

            input = br.readLine().split(" ");

            S = islands.get(Integer.parseInt(input[0]));
            E = islands.get(Integer.parseInt(input[1]));

            l = 1;
            r = 1000000000;
            /*r = Math.min(S.getConnected().stream()
                    .map(Bridge::getWeight)
                    .max(Integer::compareTo)
                    .get()
                ,E.getConnected().stream()
                        .map(Bridge::getWeight)
                        .max(Integer::compareTo)
                        .get());*/

            ans = 0;
        }
    }
}
