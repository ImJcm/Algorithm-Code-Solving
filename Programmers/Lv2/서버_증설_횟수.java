package Lv2;

import java.util.LinkedList;
import java.util.Queue;

/*
서버 증설 횟수
제출 내역
문제 설명
당신은 온라인 게임을 운영하고 있습니다. 같은 시간대에 게임을 이용하는 사람이 m명 늘어날 때마다 서버 1대가 추가로 필요합니다. 어느 시간대의 이용자가 m명 미만이라면, 서버 증설이 필요하지 않습니다. 어느 시간대의 이용자가 n x m명 이상 (n + 1) x m명 미만이라면 최소 n대의 증설된 서버가 운영 중이어야 합니다. 한 번 증설한 서버는 k시간 동안 운영하고 그 이후에는 반납합니다. 예를 들어, k = 5 일 때 10시에 증설한 서버는 10 ~ 15시에만 운영됩니다.

하루 동안 모든 게임 이용자가 게임을 하기 위해 서버를 최소 몇 번 증설해야 하는지 알고 싶습니다. 같은 시간대에 서버를 x대 증설했다면 해당 시간대의 증설 횟수는 x회입니다.

다음은 m = 3, k = 5 일 때의 시간대별 증설된 서버의 수와 증설 횟수 예시입니다.

시각	게임 이용자의 수	증설된 서버의 수	증설 횟수
0 ~ 1	0	0	0
1 ~ 2	2	0	0
2 ~ 3	3	1	1
3 ~ 4	3	1	0
4 ~ 5	1	1	0
5 ~ 6	2	1	0
6 ~ 7	0	1	0
7 ~ 8	0	0	0
8 ~ 9	0	0	0
9 ~ 10	0	0	0
10 ~ 11	4	1	1
11 ~ 12	2	1	0
12 ~ 13	0	1	0
13 ~ 14	6	2	1
14 ~ 15	0	2	0
15 ~ 16	4	1	0
16 ~ 17	2	1	0
17 ~ 18	13	4	3
18 ~ 19	3	3	0
19 ~ 20	5	3	0
20 ~ 21	10	3	0
21 ~ 22	0	3	0
22 ~ 23	1	0	0
23 ~ 24	5	1	1
모든 게임 이용자를 감당하기 위해 최소 7번 서버를 증설해야 하며, 이보다 적은 수의 서버 증설로는 모든 게임 이용자를 감당할 수 없습니다.

0시에서 23시까지의 시간대별 게임 이용자의 수를 나타내는 1차원 정수 배열 players, 서버 한 대로 감당할 수 있는 최대 이용자의 수를 나타내는 정수 m, 서버 한 대가 운영 가능한 시간을 나타내는 정수 k가 주어집니다. 이때, 모든 게임 이용자를 감당하기 위한 최소 서버 증설 횟수를 return 하도록 solution을 완성해 주세요.

제한사항
players의 길이 = 24
0 ≤ players의 원소 ≤ 1,000
players[i]는 i시 ~ i+1시 사이의 게임 이용자의 수를 나타냅니다.
1 ≤ m ≤ 1,000
1 ≤ k ≤ 24
테스트 케이스 구성 안내
아래는 테스트 케이스 구성을 나타냅니다. 각 그룹 내의 테스트 케이스를 모두 통과하면 해당 그룹에 할당된 점수를 획득할 수 있습니다.

그룹	총점	추가 제한 사항
#1	5%	m = 1, k = 1
#2	7%	k = 1
#3	88%	추가 제한 사항 없음
입출력 예
players	m	k	result
[0, 2, 3, 3, 1, 2, 0, 0, 0, 0, 4, 2, 0, 6, 0, 4, 2, 13, 3, 5, 10, 0, 1, 5]	3	5	7
[0, 0, 0, 10, 0, 12, 0, 15, 0, 1, 0, 1, 0, 0, 0, 5, 0, 0, 11, 0, 8, 0, 0, 0]	5	1	11
[0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 5, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1]	1	1	12
입출력 예 설명
입출력 예 #1

문제의 예시와 같습니다.
입출력 예 #2

총 11번 서버를 증설해야 합니다.
3 ~ 4시: 2번
5 ~ 6시: 2번
7 ~ 8시: 3번
15 ~ 16시: 1번
18 ~ 19시: 2번
20 ~ 21시: 1번
입출력 예 #3

총 12번 서버를 증설해야 합니다.
5 ~ 6시: 2번
9 ~ 10시: 1번
11 ~ 12시: 5번
13 ~ 14시: 2번
15 ~ 16시: 1번
23 ~ 24시: 1번
 */
/*
알고리즘 핵심
Queue
1. 서버의 증설은 시간대 별로 사용자 수와 현재 상태의 가용 서버의 수에 의해 정해진다.
2. 서버를 추가한 후, 일정 시간 후에 제거해야 하므로, 시간대별로 추가와 삭제가 들어온 시간대에 결정되므로 queue를 사용하여 서버를 증설한다.
3. 현재 시간대의 이용자 수와 현재 가용중인 서버의 개수를 이용하여 추가할 서버를 결정하고 추가한다.
(서버를 각 단일 객체로 보고 queue에 추가할 수 있지만, 같은 시간대에 추가되는 서버를 하나의 정보로 보고 클래스로 작성하여 정보를 업데이트하였다.)
 */
public class 서버_증설_횟수 {
    static void main() {
        int[] players = new int[] {
                0, 2, 3, 3, 1, 2, 0, 0, 0, 0, 4, 2, 0, 6, 0, 4, 2, 13, 3, 5, 10, 0, 1, 5
        };
        int m = 3;
        int k = 5;

        Solve task = new Solve();
        System.out.println(task.solution(players,m,k));
    }

    private static class Solve {
        private class Server {
            int opening_time;
            int cnt;

            public Server(int opening_time, int cnt) {
                this.opening_time = opening_time;
                this.cnt = cnt;
            }
        }
        private int ans;
        private Queue<Server> q;

        public int solution(int[] players, int m, int k) {
            init_setting();

            server_expansion(players,m,k,q);

            return ans;
        }

        private void server_expansion(int[] players, int m, int k, Queue<Server> q) {
            int cur_server_cnt = 0;

            for(int i = 0; i < 24; i++) {
                cur_server_cnt -= update(i,k,q);

                int must_available_server = Math.max(players[i] / m - cur_server_cnt, 0);

                if(must_available_server > 0) {
                    q.add(new Server(i, must_available_server));
                }

                ans += must_available_server;
                cur_server_cnt += must_available_server;
            }
        }

        private int update(int i, int k,Queue<Server> q) {
            int cnt = 0;

            while(!q.isEmpty()) {
                Server s = q.peek();

                if(s.opening_time + k <= i) {
                    cnt += q.poll().cnt;
                } else {
                    break;
                }
            }
            return cnt;
        }

        private void init_setting() {
            ans = 0;

            q = new LinkedList<>();
        }
    }
}
