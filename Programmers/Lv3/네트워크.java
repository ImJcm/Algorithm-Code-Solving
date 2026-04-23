package Lv3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
네트워크
제출 내역
문제 설명
네트워크란 컴퓨터 상호 간에 정보를 교환할 수 있도록 연결된 형태를 의미합니다. 예를 들어, 컴퓨터 A와 컴퓨터 B가 직접적으로 연결되어있고, 컴퓨터 B와 컴퓨터 C가 직접적으로 연결되어 있을 때 컴퓨터 A와 컴퓨터 C도 간접적으로 연결되어 정보를 교환할 수 있습니다. 따라서 컴퓨터 A, B, C는 모두 같은 네트워크 상에 있다고 할 수 있습니다.

컴퓨터의 개수 n, 연결에 대한 정보가 담긴 2차원 배열 computers가 매개변수로 주어질 때, 네트워크의 개수를 return 하도록 solution 함수를 작성하시오.

제한사항
컴퓨터의 개수 n은 1 이상 200 이하인 자연수입니다.
각 컴퓨터는 0부터 n-1인 정수로 표현합니다.
i번 컴퓨터와 j번 컴퓨터가 연결되어 있으면 computers[i][j]를 1로 표현합니다.
computer[i][i]는 항상 1입니다.
입출력 예
n	computers	return
3	[[1, 1, 0], [1, 1, 0], [0, 0, 1]]	2
3	[[1, 1, 0], [1, 1, 1], [0, 1, 1]]	1
입출력 예 설명
예제 #1
아래와 같이 2개의 네트워크가 있습니다.
    1
  /
2       3

예제 #2
아래와 같이 1개의 네트워크가 있습니다.
    1
  /
2 ㅡㅡㅡ 3
 */
/*
알고리즘 핵심
DFS/BFS
1. 네트워크를 구성하여, DFS or BFS를 사용하여 연결된 컴퓨터를 방문여부를 업데이트한다.
2. 방문여부를 통해 중복되는 네트워크를 방지하고 개수를 구한다.
 */
public class 네트워크 {
    public static void main() {
        int n = 3;
        int[][] computers = {
                {1,1,0},{1,1,0},{0,0,1}
        };

        int[][] computers2 = {
                {1,1,0},{1,1,1},{0,1,1}
        };

        Solve task = new Solve();
        System.out.println(task.solution(n,computers2));
    }

    private static class Solve {
        private class Com {
            int n;
            private ArrayList<Integer> adj;

            public Com(int n) {
                this.n = n;
                this.adj = new ArrayList<>();
            }

            public void connect(int n) {
                this.adj.add(n);
            }
        }
        private Com[] Coms;
        private boolean[] visited;
        private int ans;

        int solution(int n, int[][] computers) {
            init_setting(n,computers);

            for(int i = 0; i < n; i++) {
                if(visited[i]) continue;
                network_tracking(Coms[i]);
                ans++;
            }

            return ans;
        }

        // BFS
        private void network_tracking(Com com) {
            Queue<Com> q = new LinkedList<>();
            q.add(com);
            visited[com.n] = true;

            while(!q.isEmpty()) {
                Com c = q.poll();

                for(int adj_n : c.adj) {
                    if(visited[adj_n]) continue;
                    visited[adj_n] = true;
                    q.add(Coms[adj_n]);
                }
            }
        }

        // DFS
        private void network_tracking2(Com com) {
            visited[com.n] = true;

            for(int adj_n : com.adj) {
                if(visited[adj_n]) continue;
                network_tracking2(Coms[adj_n]);
            }
        }

        private void init_setting(int n, int[][] computers) {
            Coms = new Com[n];
            visited = new boolean[n];

            for(int i = 0; i < n; i++) {
                Coms[i] = new Com(i);
            }

            for(int i = 0; i < computers.length; i++) {
                for(int j = 0; j < computers[i].length; j++) {
                    if(computers[i][j] == 1) Coms[i].connect(j);
                }
            }

            ans = 0;
        }
    }
}
