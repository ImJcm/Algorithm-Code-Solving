package Programmers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/*
더 맵게
제출 내역
문제 설명
매운 것을 좋아하는 Leo는 모든 음식의 스코빌 지수를 K 이상으로 만들고 싶습니다. 모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 Leo는 스코빌 지수가 가장 낮은 두 개의 음식을 아래와 같이 특별한 방법으로 섞어 새로운 음식을 만듭니다.

섞은 음식의 스코빌 지수 = 가장 맵지 않은 음식의 스코빌 지수 + (두 번째로 맵지 않은 음식의 스코빌 지수 * 2)
Leo는 모든 음식의 스코빌 지수가 K 이상이 될 때까지 반복하여 섞습니다.
Leo가 가진 음식의 스코빌 지수를 담은 배열 scoville과 원하는 스코빌 지수 K가 주어질 때, 모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 섞어야 하는 최소 횟수를 return 하도록 solution 함수를 작성해주세요.

제한 사항
scoville의 길이는 2 이상 1,000,000 이하입니다.
K는 0 이상 1,000,000,000 이하입니다.
scoville의 원소는 각각 0 이상 1,000,000 이하입니다.
모든 음식의 스코빌 지수를 K 이상으로 만들 수 없는 경우에는 -1을 return 합니다.
입출력 예
scoville	K	return
[1, 2, 3, 9, 10, 12]	7	2
입출력 예 설명
스코빌 지수가 1인 음식과 2인 음식을 섞으면 음식의 스코빌 지수가 아래와 같이 됩니다.
새로운 음식의 스코빌 지수 = 1 + (2 * 2) = 5
가진 음식의 스코빌 지수 = [5, 3, 9, 10, 12]

스코빌 지수가 3인 음식과 5인 음식을 섞으면 음식의 스코빌 지수가 아래와 같이 됩니다.
새로운 음식의 스코빌 지수 = 3 + (5 * 2) = 13
가진 음식의 스코빌 지수 = [13, 9, 10, 12]

모든 음식의 스코빌 지수가 7 이상이 되었고 이때 섞은 횟수는 2회입니다.

※ 공지 - 2022년 12월 23일 테스트 케이스가 추가되었습니다. 기존에 제출한 코드가 통과하지 못할 수도 있습니다.
※ 공지 - 2023년 03월 23일 테스트 케이스가 추가되었습니다. 기존에 제출한 코드가 통과하지 못할 수도 있습니다.
 */
/*
알고리즘 핵심
자료구조(우선순위 큐)
1. 스코빌 수치에 따른 오름차순으로 정렬된 우선순위 큐를 사용한다.
2. 우선순위 큐의 헤드 값이 K보다 작은 경우, 스코빌을 섞는 과정을 거친다.
3. 2번 과정에서 헤드의 값이 K보다 이상의 수치가 나오는 경우, 횟수를 출력한다.
이때, 큐의 데이터의 크기가 1인 경우, 헤드의 값에 따라 -1 or 횟수를 출력한다.
 */
public class 더_맵게 {
    public static void main() {
        int[] scoville = {
                1,2,3,9,10,12
        };
        int K = 7;
        Solve task = new Solve();
        System.out.println(task.solution(scoville,K));
    }

    private static class Solve {
        private int ans;
        private PriorityQueue<Integer> pq;

        public int solution(int[] scoville, int k) {
            init_setting(scoville);

            mix_scoville(k);

            return ans;
        }

        private void mix_scoville(int k) {
            while(true) {
                if (pq.peek() >= k) return;
                if (pq.size() == 1 && pq.peek() < k) {
                    ans = -1;
                    return;
                }

                long k1 = pq.poll();
                long k2 = pq.poll();
                long mixed = k1 + (k2 * 2);

                ans++;
                pq.offer(mixed >= Integer.MAX_VALUE ?
                        Integer.MAX_VALUE : (int) mixed);
            }
        }

        private void init_setting(int[] scoville) {
            pq = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });

            Arrays.stream(scoville).forEach(x -> pq.offer(x));

            ans = 0;
        }
    }

    /*
        스코빌 지수를 섞다보면 int의 범위를 넘어가는 경우의 수가 생길 것이라고 판단하여 Long타입으로 작성하였지만,
        효율성 테스트가 적합하지 않았다.

        따라서, 계산은 Long으로 하지만, 문제의 요구되는 K의 범위가 1,000,000,000이므로 스코빌을 합친 수치가 int의
        범위를 넘어가는 경우 long타입의 값을 넣는 것이 아닌 int의 MAX값을 넣어 int의 범위내로 타입을 조정하도록 한다.

        K의 범위보다 int의 범위가 더 크기때문에 MAX값을 넣는 것으로 대체할 수 있다.
     */
    private static class Not_Efficiency_Solve {
        private int ans;
        private PriorityQueue<Long> pq;

        public int solution(int[] scoville, int k) {
            init_setting(scoville);

            mix_scoville(k);

            return ans;
        }

        private void mix_scoville(int k) {
            while(true) {
                if(pq.peek() >= k) return;
                if(pq.size() == 1 && pq.peek() < k) {
                    ans = -1;
                    return;
                }

                long k1 = pq.poll();
                long k2 = pq.poll();

                ans++;
                pq.offer(k1 + (k2 * 2));
            }
        }

        private void init_setting(int[] scoville) {
            pq = new PriorityQueue<>(new Comparator<Long>() {
                @Override
                public int compare(Long o1, Long o2) {
                    return o1.compareTo(o2);
                }
            });

            // JDK 16 버전 이상 호환
            /*pq.addAll(Arrays.stream(scoville)
                    .mapToObj(Long::valueOf)
                    .toList());*/

            Arrays.stream(scoville)
                    .mapToLong(i -> i)
                    .forEach(pq::offer);

            // JDK16 아래 버전 호환
            /*pq.addAll(Arrays.stream(scoville)
                    .mapToObj(Long::valueOf)
                    .collect(Collectors.toList()));*/

            // 비효율 버전
            /*for(Integer s : scoville) {
                pq.offer(Long.valueOf(s));
            }*/

            ans = 0;
        }
    }
}