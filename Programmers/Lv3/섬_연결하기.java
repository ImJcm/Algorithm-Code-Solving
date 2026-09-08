package Lv3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/*
섬 연결하기
제출 내역
문제 설명
n개의 섬 사이에 다리를 건설하는 비용(costs)이 주어질 때, 최소의 비용으로 모든 섬이 서로 통행 가능하도록 만들 때 필요한 최소 비용을 return 하도록 solution을 완성하세요.

다리를 여러 번 건너더라도, 도달할 수만 있으면 통행 가능하다고 봅니다. 예를 들어 A 섬과 B 섬 사이에 다리가 있고, B 섬과 C 섬 사이에 다리가 있으면 A 섬과 C 섬은 서로 통행 가능합니다.

제한사항

섬의 개수 n은 1 이상 100 이하입니다.
costs의 길이는 ((n-1) * n) / 2이하입니다.
임의의 i에 대해, costs[i][0] 와 costs[i] [1]에는 다리가 연결되는 두 섬의 번호가 들어있고, costs[i] [2]에는 이 두 섬을 연결하는 다리를 건설할 때 드는 비용입니다.
같은 연결은 두 번 주어지지 않습니다. 또한 순서가 바뀌더라도 같은 연결로 봅니다. 즉 0과 1 사이를 연결하는 비용이 주어졌을 때, 1과 0의 비용이 주어지지 않습니다.
모든 섬 사이의 다리 건설 비용이 주어지지 않습니다. 이 경우, 두 섬 사이의 건설이 불가능한 것으로 봅니다.
연결할 수 없는 섬은 주어지지 않습니다.
입출력 예

n	costs	return
4	[[0,1,1],[0,2,2],[1,2,5],[1,3,1],[2,3,8]]	4
입출력 예 설명

costs를 그림으로 표현하면 다음과 같으며, 이때 초록색 경로로 연결하는 것이 가장 적은 비용으로 모두를 통행할 수 있도록 만드는 방법입니다.

image.png
 */
/*

 */
public class 섬_연결하기 {
    static void main() {
        int n = 4;
        int[][] costs = new int[][] {
                {0,1,1},{0,2,2},{1,2,5},{1,3,1},{2,3,8}
        };

        Solve task = new Solve();
        System.out.println(task.solution(n, costs));
    }

    /*
        design : 최소 비용을 갖는 경로를 시작으로 섬에서 연결된 다리들을 우선순위 큐에 올려 그때마다 최소 경로를
        선택하고, 섬들간의 인접 여부를 만족시킨다.
     */
    private static class Solve {
        private class Island {
            int island;
            ArrayList<Bridge> bridges = new ArrayList<>();

            public Island(int island) {
                this.island = island;
            }

            public void addBridge(Bridge bridge) {
                this.adj.add(bridge));
            }
        }
        private class Bridge implements Comparable<Bridge> {
            int from, to;
            int cost;

            public Bridge(int from, int to, int cost) {
                this.from = from;
                this.to = to;
                this.cost = cost;
            }

            @Override
            public int compareTo(Bridge o) {
                return this.cost - o.cost;
                /*if (this.cost == o.cost) {
                    int adj_size1 = this.from.adj.size() + this.to.adj.size();
                    int adj_size2 = o.from.adj.size() + o.to.adj.size();

                    return adj_size2 - adj_size1;
                } else {
                    return this.cost - o.cost;
                }*/
            }
        }
        private int ans;
        private Island[] islands;

        public int solution(int n, int[][] costs) {
            init_setting(n, costs);

            connect_island(n, islands);

            return ans;
        }

        private void connect_island(int n, Island[] islands) {
            PriorityQueue<Bridge> pq = new PriorityQueue<>();
            pq.addAll(islands[0].bridges);
            boolean[] visited = new boolean[n];
            visited[islands[0].island] = true;

            while(!pq.isEmpty()) {
                Bridge current = pq.poll();


            }
        }

        private void init_setting(int n, int[][] costs) {
            ans = 0;

            islands = new Island[n];

            for(int i = 0; i < n; i++) {
                islands[i] = new Island(i);
            }

            for(int i = 0; i < costs.length; i++) {
                int fi = costs[i][0];
                int ti = costs[i][1];
                int c = costs[i][2];

                islands[fi].bridges.add(new Bridge(fi, ti, c));
                islands[ti].bridges.add(new Bridge(ti, fi, c));
            }
        }
    }

    /*
        failure solve
        => 최소 비용의 경로를 선택하지만, 모든 섬이 연결되었다고 보장할 수 없다.
     */
    private static class WrongSolve {
        private class Island {
            int island;
            ArrayList<Island> adj = new ArrayList<>();

            public Island(int island) {
                this.island = island;
            }

            public void addIsland(int island) {
                this.adj.add(new Island(island));
            }
        }
        private class Bridge implements Comparable<Bridge> {
            Island from, to;
            int cost;

            public Bridge(Island from, Island to, int cost) {
                this.from = from;
                this.to = to;
                this.cost = cost;
            }
            @Override
            public int compareTo(Bridge o) {
                if(this.cost == o.cost) {
                    int adj_size1 = this.from.adj.size() + this.to.adj.size();
                    int adj_size2 = o.from.adj.size() + o.to.adj.size();

                    return adj_size2 - adj_size1;
                } else {
                    return this.cost - o.cost;
                }

            }
        }
        private int ans;
        private boolean[] visited;
        private Island[] islands;
        private Bridge[] bridges;

        public int solution(int n, int[][] costs) {
            init_setting(n, costs);

            connect_island(n, bridges, visited);

            return ans;
        }

        private void connect_island(int n, Bridge[] bridges, boolean[] visited) {
            PriorityQueue<Bridge> pq = new PriorityQueue<>();
            pq.addAll(Arrays.asList(bridges));

            int c = 0;
            int cost = 0;

            while(!pq.isEmpty()) {
                Bridge bridge = pq.poll();

                int fi = bridge.from.island;
                int ti = bridge.to.island;

                if(visited[fi] && visited[ti]) continue;

                if(!visited[fi]) {
                    visited[fi] = true;
                    c++;
                }
                if(!visited[ti]) {
                    visited[ti] = true;
                    c++;
                }

                cost += bridge.cost;

                if(c == n) {
                    ans = cost;
                    return;
                }
            }
        }

        private void init_setting(int n, int[][] costs) {
            ans = 0;

            visited = new boolean[n];
            islands = new Island[n];
            bridges = new Bridge[costs.length];

            for(int i = 0; i < n; i++) {
                islands[i] = new Island(i);
            }

            for(int i = 0; i < costs.length; i++) {
                int f = costs[i][0];
                int t = costs[i][1];
                int c = costs[i][2];

                islands[f].addIsland(t);
                islands[t].addIsland(f);
                bridges[i] = new Bridge(islands[f], islands[t], c);
            }
        }
    }
}
