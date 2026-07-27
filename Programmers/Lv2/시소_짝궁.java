package Lv2;

/*
시소 짝꿍
제출 내역
문제 설명
어느 공원 놀이터에는 시소가 하나 설치되어 있습니다. 이 시소는 중심으로부터 2(m), 3(m), 4(m) 거리의 지점에 좌석이 하나씩 있습니다.
이 시소를 두 명이 마주 보고 탄다고 할 때, 시소가 평형인 상태에서 각각에 의해 시소에 걸리는 토크의 크기가 서로 상쇄되어 완전한 균형을 이룰 수 있다면 그 두 사람을 시소 짝꿍이라고 합니다. 즉, 탑승한 사람의 무게와 시소 축과 좌석 간의 거리의 곱이 양쪽 다 같다면 시소 짝꿍이라고 할 수 있습니다.
사람들의 몸무게 목록 weights이 주어질 때, 시소 짝꿍이 몇 쌍 존재하는지 구하여 return 하도록 solution 함수를 완성해주세요.

제한 사항
2 ≤ weights의 길이 ≤ 100,000
100 ≤ weights[i] ≤ 1,000
몸무게 단위는 N(뉴턴)으로 주어집니다.
몸무게는 모두 정수입니다.
입출력 예
weights	result
[100,180,360,100,270]	4
입출력 예 설명
{100, 100} 은 서로 같은 거리에 마주보고 앉으면 균형을 이룹니다.
{180, 360} 은 각각 4(m), 2(m) 거리에 마주보고 앉으면 균형을 이룹니다.
{180, 270} 은 각각 3(m), 2(m) 거리에 마주보고 앉으면 균형을 이룹니다.
{270, 360} 은 각각 4(m), 3(m) 거리에 마주보고 앉으면 균형을 이룹니다.
 */
public class 시소_짝궁 {
    static void main() {
        int[] weights = new int[] {
                100,180,360,100,270
        };

        Solve task = new Solve();
        System.out.println(task.solution(weights));
    }

    private static class Solve {
        private long ans;
        private int[] duplicate_weights,avail_weights;

        /*
            Failure Solve : TestCase #4 ~ #11
            Logic error
         */
        public long solution(int[] weights) {
            init_setting(weights);

            check_pair(duplicate_weights, avail_weights);

            return ans;
        }

        private void check_pair(int[] duplicate_weights, int[] avail_weights) {
            for(int i = 0; i < duplicate_weights.length; i++) {
                if(duplicate_weights[i] > 1) {
                    ans += ((long) duplicate_weights[i] * (duplicate_weights[i] - 1)) / 2;
                }
            }

            for(int i = 200; i < avail_weights.length; i++) {
                if(avail_weights[i] > 1) {
                    int w2 = i / 2;
                    int w3 = i / 3;
                    int w4 = i / 4;

                    long d2,d3,d4;
                    d2 = d3 = d4 = 0;

                    if(100 <= w2 && w2 <= 1000) d2 = duplicate_weights[w2];
                    if(100 <= w3 && w3 <= 1000) d3 = duplicate_weights[w3];
                    if(100 <= w4 && w4 <= 1000) d4 = duplicate_weights[w4];

                    ans += (((long) d2 * d3) + ((long) d2 * d4) + ((long) d3 * d4));
                }
            }
        }

        private void init_setting(int[] weights) {
            ans = 0;
            duplicate_weights = new int[1001]; // 100 <= w <= 1000
            avail_weights = new int[4001]; // 200 <= w <= 4000

            for(int i = 0; i < weights.length; i++) {
                int x1 = weights[i];
                int x2 = weights[i] * 2;
                int x3 = weights[i] * 3;
                int x4 = weights[i] * 4;

                duplicate_weights[x1]++;
                avail_weights[x2]++;
                avail_weights[x3]++;
                avail_weights[x4]++;
            }
        }
    }

    /*
        Failure Solve : #7~ 부터 시간초과 나머지도 TC도 시간초과가 발생할 것이라고 예상
        O(n2)
     */
    private static class WrongSolve {
        private long ans;

        public long solution(int[] weights) {
            init_setting(weights);

            for(int i = 0; i < weights.length - 1; i++) {
                for(int j = i + 1; j < weights.length; j++) {
                    for(int k = 2; k <= 4; k++) {
                        int res = weights[i] * k;
                        int meter = res / weights[j];
                        if(2 <= meter && meter <= 4 && res % weights[j] == 0) {
                            ans++;
                            break;
                        }
                    }
                }
            }

            return ans;
        }

        private void init_setting(int[] weights) {
            ans = 0;
        }
    }
}
