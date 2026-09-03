package Lv2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
디펜스 게임
제출 내역
문제 설명
준호는 요즘 디펜스 게임에 푹 빠져 있습니다. 디펜스 게임은 준호가 보유한 병사 n명으로 연속되는 적의 공격을 순서대로 막는 게임입니다. 디펜스 게임은 다음과 같은 규칙으로 진행됩니다.

준호는 처음에 병사 n명을 가지고 있습니다.
매 라운드마다 enemy[i]마리의 적이 등장합니다.
남은 병사 중 enemy[i]명 만큼 소모하여 enemy[i]마리의 적을 막을 수 있습니다.
예를 들어 남은 병사가 7명이고, 적의 수가 2마리인 경우, 현재 라운드를 막으면 7 - 2 = 5명의 병사가 남습니다.
남은 병사의 수보다 현재 라운드의 적의 수가 더 많으면 게임이 종료됩니다.
게임에는 무적권이라는 스킬이 있으며, 무적권을 사용하면 병사의 소모없이 한 라운드의 공격을 막을 수 있습니다.
무적권은 최대 k번 사용할 수 있습니다.
준호는 무적권을 적절한 시기에 사용하여 최대한 많은 라운드를 진행하고 싶습니다.

준호가 처음 가지고 있는 병사의 수 n, 사용 가능한 무적권의 횟수 k, 매 라운드마다 공격해오는 적의 수가 순서대로 담긴 정수 배열 enemy가 매개변수로 주어집니다. 준호가 몇 라운드까지 막을 수 있는지 return 하도록 solution 함수를 완성해주세요.

제한사항
1 ≤ n ≤ 1,000,000,000
1 ≤ k ≤ 500,000
1 ≤ enemy의 길이 ≤ 1,000,000
1 ≤ enemy[i] ≤ 1,000,000
enemy[i]에는 i + 1 라운드에서 공격해오는 적의 수가 담겨있습니다.
모든 라운드를 막을 수 있는 경우에는 enemy[i]의 길이를 return 해주세요.
입출력 예
n	k	enemy	result
7	3	[4, 2, 4, 5, 3, 3, 1]	5
2	4	[3, 3, 3, 3]	4
입출력 예 설명
입출력 예#1

1, 3, 5 라운드의 공격을 무적권으로 막아내고, 2, 4 라운드에 각각 병사를 2명, 5명 소모하면 5라운드까지 공격을 막을 수 있습니다. 또, 1, 3, 4번째 공격을 무적권으로 막아내고, 2, 5 번째 공격에 각각 병사를 2명, 3명 소모하여 5라운드까지 공격을 막을 수 있습니다. 그보다 많은 라운드를 막는 방법은 없으므로 5를 return 합니다.
입출력 예#2

준호는 모든 공격에 무적권을 사용하여 4라운드까지 막을 수 있습니다.
 */
/*
알고리즘 핵심
우선순위 큐
1. 앞쪽부터 순차적으로 적을 제거하기 때문에 k개 만큼 우선순위 큐를 채운다.
2. k개를 채운 후, enemy[i]와 가장 적은 수의 적을 비교하여 소탕권을 사용할 라운드를 결정한다.
3. 소탕권을 모두 사용하고, 남은 병사의 수와 현재 소탕가능한 적의 수를 비교하여 소탕 가능한 라운드 수를 갱신한다.

첫 접근으로 구간을 구하는 문제로 누적합을 통해 뒷 라운드부터 [0,i]구간의 내림차순의 k개를 prefix[i]에서 뺀 값을 n과 비교하여
소탕 가능한 라운드를 구하려고 하였지만, 시간초과 및 틀린 로직이였다.

고민하는 과정에서 문득 앞에서 순차적으로 진행하기 때문에 결국 현재 라운드의 적의 수와 현재까지 사용했던 가장 적은 적의 수를 가진
라운드를 찾는 것이기 때문에 소탕권을 사용한 적의 수를 저장해두면 된다고 생각이 들었다.

저장해둔 적의 수와 현재 라운드의 적 수를 비교하여 교체하는 것이 핵심이였다.

따라서, 우선순위 큐를 사용하여 적의 수를 저장해두고, k개의 소탕권을 다 사용하였다면 가장 적은 라운드의 적수와 현재 적수를 비교하여
교체작업을 하여 현재까지 소탕할 수 있는 적의 수를 누적한 값과 n을 비교하여 소탕가능한 라운드를 결정한다.
 */
public class 디펜스_게임 {
    static void main() {
        /*int n = 7;
        int k = 3;
        int[] enemy = new int[] {
                4, 2, 4, 5, 3, 3, 1
        };*/
        int n = 10;
        int k = 1;
        int[] enemy = new int[] {
                10,1,2,11,12
        };

        Solve task = new Solve();
        System.out.println(task.solution(n,k,enemy));
    }

    private static class Solve {
        private int ans;
        private PriorityQueue<Integer> pq;

        public int solution(int n, int k, int[] enemy) {
            init_setting();

            defense_game(n,k,enemy,pq);

            return ans;
        }

        private void defense_game(int n, int k, int[] enemy, PriorityQueue<Integer> pq) {
            int cur_n = 0;

            for (int i = 0; i < enemy.length; i++) {
                if (pq.size() < k) {
                    pq.add(enemy[i]);
                    continue;
                }

                int weakest = pq.peek();
                if (enemy[i] > weakest) {
                    cur_n += pq.poll();
                    pq.add(enemy[i]);
                } else {
                    cur_n += enemy[i];
                }

                if (cur_n > n) {
                    ans = i;
                    break;
                }
            }

            if (ans == 0) {
                ans = enemy.length;
            }
        }

        private void init_setting() {
            ans = 0;

            pq = new PriorityQueue<>();

            /*pq = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            });*/
        }
    }

    /*
        Failure Solve : time out + logic error
     */
    private static class WrongSolve_timeout2 {
        private class Enemy_Node {
            int enemy,idx;

            public Enemy_Node(int enemy, int idx) {
                this.enemy = enemy;
                this.idx = idx;
            }
        }
        private int ans;
        private long[] prefix_sum;
        private ArrayList<Enemy_Node> sorted_enemies;

        public int solution(int n, int k, int[] enemy) {
            init_setting(n,k,enemy);

            defense_game(n,k,enemy,prefix_sum);

            return ans;
        }

        private void defense_game(int n, int k, int[] enemy, long[] prefix_sum) {
            boolean flag = true;
            int idx = prefix_sum.length;
            sorted_enemies = IntStream.range(0, enemy.length)
                    .mapToObj(i -> new Enemy_Node(enemy[i],i))
                    .sorted((o1, o2) -> o2.enemy - o1.enemy)
                    .collect(Collectors.toCollection(ArrayList::new));

            while(flag) {
                long sum = 0;

                int i = 0;
                while(i < sorted_enemies.size() && i < k) {
                    if(idx < sorted_enemies.get(i).idx) {
                        sorted_enemies.remove(i);
                        continue;
                    }
                    sum += sorted_enemies.get(i).enemy;
                    i++;
                }

                if(prefix_sum[idx - 1] - sum <= n) {
                    ans = idx;
                    flag = false;
                }

                idx--;
            }
        }

        private void init_setting(int n, int k, int[] enemy) {
            ans = 0;

            prefix_sum = new long[enemy.length];

            prefix_sum[0] = enemy[0];

            for(int i = 1; i < enemy.length; i++) {
                prefix_sum[i] = prefix_sum[i - 1] + enemy[i];
            }
        }
    }

    /*
        Failure Solve : timeout
     */
    private static class WrongSolve_timeout {
        private int ans;
        private long[] prefix_sum;

        public int solution(int n, int k, int[] enemy) {
            init_setting(n,k,enemy);

            defense_game(n,k,enemy,prefix_sum);

            return ans;
        }

        private void defense_game(int n, int k, int[] enemy, long[] prefix_sum) {
            boolean flag = true;
            int idx = prefix_sum.length;

            while(flag) {
                ArrayList<Integer> sorted_enemy = Arrays.stream(enemy)
                        .limit(idx)
                        .boxed()
                        .sorted(Comparator.reverseOrder())
                        .collect(Collectors.toCollection(ArrayList::new));

                long sum = 0;

                for(int i = 0; i < k; i++) {
                    sum += sorted_enemy.get(i);
                }

                if(prefix_sum[idx - 1] - sum <= n) {
                    ans = idx;
                    flag = false;
                }

                idx--;
            }
        }

        private void init_setting(int n, int k, int[] enemy) {
            ans = 0;

            prefix_sum = new long[enemy.length];

            prefix_sum[0] = enemy[0];

            for(int i = 1; i < enemy.length; i++) {
                prefix_sum[i] = prefix_sum[i - 1] + enemy[i];
            }
        }
    }
}
