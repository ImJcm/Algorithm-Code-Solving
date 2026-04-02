package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
바이러스

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	238389	116216	76221	47.067%
문제
신종 바이러스인 웜 바이러스는 네트워크를 통해 전파된다. 한 컴퓨터가 웜 바이러스에 걸리면 그 컴퓨터와 네트워크 상에서 연결되어 있는 모든 컴퓨터는 웜 바이러스에 걸리게 된다.

예를 들어 7대의 컴퓨터가 <그림 1>과 같이 네트워크 상에서 연결되어 있다고 하자. 1번 컴퓨터가 웜 바이러스에 걸리면 웜 바이러스는 2번과 5번 컴퓨터를 거쳐 3번과 6번 컴퓨터까지 전파되어 2, 3, 5, 6 네 대의 컴퓨터는 웜 바이러스에 걸리게 된다. 하지만 4번과 7번 컴퓨터는 1번 컴퓨터와 네트워크상에서 연결되어 있지 않기 때문에 영향을 받지 않는다.



어느 날 1번 컴퓨터가 웜 바이러스에 걸렸다. 컴퓨터의 수와 네트워크 상에서 서로 연결되어 있는 정보가 주어질 때, 1번 컴퓨터를 통해 웜 바이러스에 걸리게 되는 컴퓨터의 수를 출력하는 프로그램을 작성하시오.

입력
첫째 줄에는 컴퓨터의 수가 주어진다. 컴퓨터의 수는 100 이하인 양의 정수이고 각 컴퓨터에는 1번 부터 차례대로 번호가 매겨진다. 둘째 줄에는 네트워크 상에서 직접 연결되어 있는 컴퓨터 쌍의 수가 주어진다. 이어서 그 수만큼 한 줄에 한 쌍씩 네트워크 상에서 직접 연결되어 있는 컴퓨터의 번호 쌍이 주어진다.

출력
1번 컴퓨터가 웜 바이러스에 걸렸을 때, 1번 컴퓨터를 통해 웜 바이러스에 걸리게 되는 컴퓨터의 수를 첫째 줄에 출력한다.

예제 입력 1
7
6
1 2
2 3
1 5
5 2
5 6
4 7
예제 출력 1
4
출처
Olympiad > 한국정보올림피아드 > 한국정보올림피아드시․도지역본선 > 지역본선 2004 > 초등부 3번

잘못된 데이터를 찾은 사람: djm03178, jsa3824
데이터를 추가한 사람: chansol, jh05013
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
깊이 우선 탐색
 */
public class BOJ2606 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    // BFS
    private static class Solve {
        private class Com {
            private ArrayList<Integer> adj;

            public Com() {
                this.adj = new ArrayList<>();
            }
        }
        private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        private int c,p,ans;
        private boolean[] visited;
        private Com[] coms;

        void solve() throws IOException {
            init_setting();

            spread_virus();

            System.out.println(ans);
        }

        private void spread_virus() {
            Queue<Com> q = new LinkedList<>();
            q.add(coms[1]);
            visited[1] = true;

            while(!q.isEmpty()) {
                Com now = q.poll();

                for(Integer v : now.adj) {
                    if(visited[v]) continue;

                    visited[v] = true;
                    ans++;
                    q.add(coms[v]);
                }
            }
        }

        private void init_setting() throws IOException {
            c = Integer.parseInt(br.readLine());
            p = Integer.parseInt(br.readLine());

            coms = new Com[c + 1];
            visited = new  boolean[c + 1];

            for(int i = 1; i <= c; i++) {
                coms[i] = new Com();
            }

            for(int i = 0; i < p; i++) {
                String[] input = br.readLine().split(" ");

                int c1 = Integer.parseInt(input[0]);
                int c2 = Integer.parseInt(input[1]);

                coms[c1].adj.add(c2);
                coms[c2].adj.add(c1);
            }

            ans = 0;
        }
    }
}
