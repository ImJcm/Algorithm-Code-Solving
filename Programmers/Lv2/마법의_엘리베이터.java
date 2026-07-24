package Lv2;

import java.util.LinkedList;
import java.util.Queue;

/*
마법의 엘리베이터
제출 내역
문제 설명
마법의 세계에 사는 민수는 아주 높은 탑에 살고 있습니다. 탑이 너무 높아서 걸어 다니기 힘든 민수는 마법의 엘리베이터를 만들었습니다. 마법의 엘리베이터의 버튼은 특별합니다. 마법의 엘리베이터에는 -1, +1, -10, +10, -100, +100 등과 같이 절댓값이 10c (c ≥ 0 인 정수) 형태인 정수들이 적힌 버튼이 있습니다. 마법의 엘리베이터의 버튼을 누르면 현재 층 수에 버튼에 적혀 있는 값을 더한 층으로 이동하게 됩니다. 단, 엘리베이터가 위치해 있는 층과 버튼의 값을 더한 결과가 0보다 작으면 엘리베이터는 움직이지 않습니다. 민수의 세계에서는 0층이 가장 아래층이며 엘리베이터는 현재 민수가 있는 층에 있습니다.

마법의 엘리베이터를 움직이기 위해서 버튼 한 번당 마법의 돌 한 개를 사용하게 됩니다.예를 들어, 16층에 있는 민수가 0층으로 가려면 -1이 적힌 버튼을 6번, -10이 적힌 버튼을 1번 눌러 마법의 돌 7개를 소모하여 0층으로 갈 수 있습니다. 하지만, +1이 적힌 버튼을 4번, -10이 적힌 버튼 2번을 누르면 마법의 돌 6개를 소모하여 0층으로 갈 수 있습니다.

마법의 돌을 아끼기 위해 민수는 항상 최소한의 버튼을 눌러서 이동하려고 합니다. 민수가 어떤 층에서 엘리베이터를 타고 0층으로 내려가는데 필요한 마법의 돌의 최소 개수를 알고 싶습니다. 민수와 마법의 엘리베이터가 있는 층을 나타내는 정수 storey 가 주어졌을 때, 0층으로 가기 위해 필요한 마법의 돌의 최소값을 return 하도록 solution 함수를 완성하세요.

제한사항
1 ≤ storey ≤ 100,000,000
입출력 예
storey	result
16	6
2554	16
입출력 예 설명
입출력 예 #1

문제 예시와 같습니다.
입출력 예 #2

-1, +100이 적힌 버튼을 4번, +10이 적힌 버튼을 5번, -1000이 적힌 버튼을 3번 누르면 0층에 도착 할 수 있습니다. 그러므로 16을 return 합니다.
 */
/*
알고리즘 핵심
재귀 + 구현
1. storey의 자릿수마다 올림과 내림의 카운트를 통해 엘리베이터 이동 횟수를 업데이트한다.
2. 올림 또는 내림을 통해 만들어진 수가 9자리를 넘어가거나, 0이 되는 경우 기저사례로 종료하고, 이때 이동한 횟수를 ans로 업데이트한다.

첫 접근을 bfs로 엘리베이터가 갈 수 있는 층수를 방문여부로 검사하여 최소 횟수를 구하려고 했으나, 엘리베이터를 통해 만들어지는 층수가
1개의 층수에서 +-로 1,10,100,...,100_000_000만큼 늘어나기 때문에 시간초과가 발생할 가능성이 보였다.

그래서, 자릿수마다 수를 통해 횟수를 만들어 낼 수 있고, 자릿수의 올림을 통해 다음 자릿수에 영향이 있으므로 해당 값을 업데이트하여
최소 횟수를 구하려고 하였다.
 */
public class 마법의_엘리베이터 {
    static void main() {
        int storey =
                //100_000_000;
                //16;
                2554;

        Solve task = new Solve();
        System.out.println(task.solution(storey));
    }

    private static class Solve {
        private int ans;

        public int solution(int storey) {
            init_setting();

            magic_elevator(0, 0, storey);

            return ans;
        }

        private void magic_elevator(int idx, int cnt, int storey) {
            if(idx > 8 || idx >= Integer.toString(storey).length()) {
                ans = Math.min(ans, cnt);
                return;
            }

            int w = (int) Math.pow(10, idx);

            int num = (storey % (w * 10)) / w;

            int up = 10 - num;
            int down = num;

            magic_elevator(idx + 1, cnt + up, storey + w * 10);
            magic_elevator(idx + 1, cnt + down, storey);
        }

        private void init_setting() {
            ans = Integer.MAX_VALUE;
        }
    }

    /*
        시간초과 발생 예상 : bfs
        10^n까지 늘어나기 때문에 시간초과가 발생할 가능성이 높다.
     */
    private static class WrongSolve {
        private class Floor {
            int f;
            int cnt;

            public Floor(int f, int cnt) {
                this.f = f;
                this.cnt = cnt;
            }
        }
        private int ans;
        private final int MAX_FLOOR = 100_000_000;

        public int solution(int storey) {
            init_setting(storey);

            magic_elevator(storey);

            return ans;
        }


        private void magic_elevator(int storey) {
            Queue<Floor> q = new LinkedList<>();
            q.add(new Floor(storey, 0));
            boolean[] visited = new boolean[MAX_FLOOR + 1];
            visited[storey] = true;

            while(!q.isEmpty()) {
                Floor nfloor = q.poll();

                if(nfloor.f == 0) {
                    ans = nfloor.cnt;
                    return;
                }

                for(int i = 0; i < 9; i++) {
                    int n = (int) Math.pow(10,i);

                    int nf_up = nfloor.f + n;
                    int nf_down = nfloor.f - n;

                    if(nf_up > 0 && nf_up <= MAX_FLOOR && !visited[nf_up]) {
                        visited[nf_up] = true;
                        q.add(new Floor(nf_up, nfloor.cnt + 1));
                    }
                    if(nf_down > 0 && nf_down <= MAX_FLOOR && !visited[nf_down]) {
                        visited[nf_down] = true;
                        q.add(new Floor(nf_down, nfloor.cnt + 1));
                    }
                }
            }
        }

        private void init_setting(int storey) {
            ans = Integer.MAX_VALUE;
        }
    }
}
