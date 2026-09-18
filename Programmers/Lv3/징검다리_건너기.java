package Lv3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.IntStream;

/*
징검다리 건너기
제출 내역
문제 설명
[본 문제는 정확성과 효율성 테스트 각각 점수가 있는 문제입니다.]

카카오 초등학교의 "니니즈 친구들"이 "라이언" 선생님과 함께 가을 소풍을 가는 중에 징검다리가 있는 개울을 만나서 건너편으로 건너려고 합니다. "라이언" 선생님은 "니니즈 친구들"이 무사히 징검다리를 건널 수 있도록 다음과 같이 규칙을 만들었습니다.

징검다리는 일렬로 놓여 있고 각 징검다리의 디딤돌에는 모두 숫자가 적혀 있으며 디딤돌의 숫자는 한 번 밟을 때마다 1씩 줄어듭니다.
디딤돌의 숫자가 0이 되면 더 이상 밟을 수 없으며 이때는 그 다음 디딤돌로 한번에 여러 칸을 건너 뛸 수 있습니다.
단, 다음으로 밟을 수 있는 디딤돌이 여러 개인 경우 무조건 가장 가까운 디딤돌로만 건너뛸 수 있습니다.
"니니즈 친구들"은 개울의 왼쪽에 있으며, 개울의 오른쪽 건너편에 도착해야 징검다리를 건넌 것으로 인정합니다.
"니니즈 친구들"은 한 번에 한 명씩 징검다리를 건너야 하며, 한 친구가 징검다리를 모두 건넌 후에 그 다음 친구가 건너기 시작합니다.

디딤돌에 적힌 숫자가 순서대로 담긴 배열 stones와 한 번에 건너뛸 수 있는 디딤돌의 최대 칸수 k가 매개변수로 주어질 때, 최대 몇 명까지 징검다리를 건널 수 있는지 return 하도록 solution 함수를 완성해주세요.

[제한사항]
징검다리를 건너야 하는 니니즈 친구들의 수는 무제한 이라고 간주합니다.
stones 배열의 크기는 1 이상 200,000 이하입니다.
stones 배열 각 원소들의 값은 1 이상 200,000,000 이하인 자연수입니다.
k는 1 이상 stones의 길이 이하인 자연수입니다.
[입출력 예]
stones	k	result
[2, 4, 5, 3, 2, 1, 4, 2, 5, 1]	3	3
입출력 예에 대한 설명
입출력 예 #1

첫 번째 친구는 다음과 같이 징검다리를 건널 수 있습니다.
step_stones_104.png

첫 번째 친구가 징검다리를 건넌 후 디딤돌에 적힌 숫자는 아래 그림과 같습니다.
두 번째 친구도 아래 그림과 같이 징검다리를 건널 수 있습니다.
step_stones_101.png

두 번째 친구가 징검다리를 건넌 후 디딤돌에 적힌 숫자는 아래 그림과 같습니다.
세 번째 친구도 아래 그림과 같이 징검다리를 건널 수 있습니다.
step_stones_102.png

세 번째 친구가 징검다리를 건넌 후 디딤돌에 적힌 숫자는 아래 그림과 같습니다.
네 번째 친구가 징검다리를 건너려면, 세 번째 디딤돌에서 일곱 번째 디딤돌로 네 칸을 건너뛰어야 합니다. 하지만 k = 3 이므로 건너뛸 수 없습니다.
step_stones_103.png

따라서 최대 3명이 디딤돌을 모두 건널 수 있습니다.
 */
/*
알고리즘 핵심
Sliding Window Maximum + Monotonic Queue(Deque, 단조 큐)
1. 크기가 k인 슬라이딩 윈도우를 배열의 왼쪽에서 오른쪽으로 이동시키면서, 각 윈도우마다 최대값을 이전까지 누적된 최대값과 비교하여
최소값을 저장하여 통과할 수 있는 인원수를 갱신한다

처음 접근 방식으로 0부터 시작하여 [i, i + k] 구간에서 최대값이 건너갈 수 있는 최대 인원 수로 판단하고,
해당 구간에서 최대 인원을 만족하는 인덱스를 i로 갱신하고, 위 방법을 반복하여 모든 구간을 검사하도록 했지만,
로직 에러 + 시간초과가 발생하였다.
이때, 로직 에러는 stnoes의 크기가 1인 경우를 고려하지 못해서 발생한 것으로 모든 구간이 검사가 가능하도록 변경하였다.
하지만, 효율성 테스트에서 모두 불합격이 나와 결국은 0~n까지 반복하면서 k번째 구간을 검사하기 때문에 O(n^2)으로 효율성을 만족하지
못하는 것으로 생각한다.
그래서 이 문제의 힌트를 통해 알게된 점은 이분 탐색과 sliding window maximum 방법을 알게되었다.
이분 탐색 방법은 건너갈 수 있는 인원 수를 돌의 제한 수의 사이의 값으로 결정하여 순차적으로 돌들을 건너면서
k번째로 점프가 가능하지 않는 경우에 인원수를 이분탐색으로 줄이고, 그렇지 않은 경우 인원수를 늘려 최대 인원수를 구한다.

sliding window maximum방식은 위의 설명과 같이 deque를 사용하여 순차적으로 윈도우 내부에서 최대값을 유지(내림차순 정렬)하는 방법이다.
이때, 내부의 최대값을 유지하는 방법은 다음과 같다.
1. k 범위를 벗어나는 요소 제거 : 윈도우 크기를 벗어난 왼쪽 범위의 인덱스를 큐에서 제거한다.
2. 작은 값 제거 : 새로 들어올 값보다 작은 기존 값들을 인덱스의 뒤에서부터 제거한다.
3. 인덱스 추가 : 현재 인덱스를 큐의 뒷부분에 추가한다.
4. 결과 저장 : 윈도우 크기가 k가 완성되는 시점부터 큐의 첫 번째 인덱스 값을 저장한다.

이 방법을 활용하여, 4번 결과 저장부분에서 이전 윈도우들의 최대값과 현재 윈도우의 최대값을 비교하여 최소값을 갱신하여
최대로 건널 수있는 인원수를 구할 수 있다.

 */
public class 징검다리_건너기 {
    static void main() {
        int[] stones = new int[] {
                2, 4, 5, 3, 2, 1, 4, 2, 5, 1
                //7,2,8,7,2,5,9
                //1
        };

        int k = 3;

        Solve task = new Solve();
        System.out.println(task.solution(stones, k));
    }

    private static class Solve {
        private int ans;

        public int solution(int[] stones, int k) {
            init_setting(stones, k);

            crossing_stepping_stones(stones, k);

            return ans;
        }

        private void crossing_stepping_stones(int[] stones, int k) {
            Deque<Integer> dq = new ArrayDeque<>();
            int max_crossing = 200_000_001;

            for(int i = 0; i < stones.length; i++) {
                while(!dq.isEmpty() && i - k + 1 > dq.peekFirst()) dq.pollFirst();
                while(!dq.isEmpty() && stones[i] > stones[dq.peekLast()]) dq.pollLast();

                dq.addLast(i);

                if(i >= k - 1) max_crossing = Math.min(max_crossing, stones[dq.peekFirst()]);
            }

            ans = max_crossing;
        }

        /*
            Efficiency Test : ETC#1~14 - timeout
         */
        private void timeout_crossing_stepping_stones(int[] stones, int k) {
            int max_crossing = 200_000_001;

            for(int i = 0;; i++) {
                int e = Math.min(i + k, stones.length);
                int maxIndex = IntStream.range(i, e)
                        .boxed()
                        .max((a,b) -> Integer.compare(stones[a], stones[b]))
                        .orElse(-1);

                max_crossing = Math.min(max_crossing, stones[maxIndex]);

                if(i + k >= stones.length) break;
            }

            ans = max_crossing;
        }



        /*
            Wrong Solve : TC#1,3 - logic error, Efficiency#3 - time out
         */
        private void wrong_logic_crossing_stepping_stones(int[] stones, int k) {
            int i = 0, max_crossing = 200_000_001;

            while(i + k < stones.length) {
                int maxIndex = IntStream.range(i, i + k)
                        .boxed()
                        .max((a,b) -> Integer.compare(stones[a], stones[b]))
                        .orElse(-1);

                i = maxIndex + 1;
                max_crossing = Math.min(max_crossing, stones[maxIndex]);
            }

            ans = max_crossing;
        }

        private void init_setting(int[] stones, int k) {
            ans = 0;
        }
    }
}
