package Lv3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
이중우선순위큐
제출 내역
문제 설명
이중 우선순위 큐는 다음 연산을 할 수 있는 자료구조를 말합니다.

명령어	수신 탑(높이)
I 숫자	큐에 주어진 숫자를 삽입합니다.
D 1	큐에서 최댓값을 삭제합니다.
D -1	큐에서 최솟값을 삭제합니다.
이중 우선순위 큐가 할 연산 operations가 매개변수로 주어질 때, 모든 연산을 처리한 후 큐가 비어있으면 [0,0] 비어있지 않으면 [최댓값, 최솟값]을 return 하도록 solution 함수를 구현해주세요.

제한사항
operations는 길이가 1 이상 1,000,000 이하인 문자열 배열입니다.
operations의 원소는 큐가 수행할 연산을 나타냅니다.
원소는 “명령어 데이터” 형식으로 주어집니다.- 최댓값/최솟값을 삭제하는 연산에서 최댓값/최솟값이 둘 이상인 경우, 하나만 삭제합니다.
빈 큐에 데이터를 삭제하라는 연산이 주어질 경우, 해당 연산은 무시합니다.
입출력 예
operations	return
["I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"]	[0,0]
["I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"]	[333, -45]
입출력 예 설명
입출력 예 #1

16과 -5643을 삽입합니다.
최솟값을 삭제합니다. -5643이 삭제되고 16이 남아있습니다.
최댓값을 삭제합니다. 16이 삭제되고 이중 우선순위 큐는 비어있습니다.
우선순위 큐가 비어있으므로 최댓값 삭제 연산이 무시됩니다.
123을 삽입합니다.
최솟값을 삭제합니다. 123이 삭제되고 이중 우선순위 큐는 비어있습니다.
따라서 [0, 0]을 반환합니다.

입출력 예 #2

-45와 653을 삽입후 최댓값(653)을 삭제합니다. -45가 남아있습니다.
-642, 45, 97을 삽입 후 최댓값(97), 최솟값(-642)을 삭제합니다. -45와 45가 남아있습니다.
333을 삽입합니다.
이중 우선순위 큐에 -45, 45, 333이 남아있으므로, [333, -45]를 반환합니다.

※ 공지 - 2024년 7월 22일 테스트케이스가 추가되었습니다. 기존에 제출한 코드가 통과하지 못할 수도 있습니다.
 */
/*
알고리즘 핵심
자료구조(우선순위 큐)
1. 하나의 우선순위 큐로는 동시에 최댓값, 최소값을 구할 수 없으므로 2개를 사용한다.
2. 각각의 우선순위 큐를 오름차순, 내림차순을 기준으로 설정한 후, I의 명령어에는 모두 데이터를 넣고,
D 명령어는 1 or -1에 따라 각 우선순위 큐에서 데이터를 삭제한다.
이때, 삭제하는 과정에서 remove를 사용하여 반대 큐도 삭제를 진행한다.
remove(Object o) = O(nlogn)
3. 최종적으로 남은 오름차순,내림차순의 큐에서 각각의 head값을 ans에 저장한다.
 */
public class 이중우선순위큐 {
    public static void main(String[] args) {
        String[] operations = new String[] {
                "I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"
        };

        String[] operations2 = new String[] {
                "I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"
        };

        String[] custom = new String[] {
                "I 10", "I 20", "D 1", "I 30", "I 40", "D -1", "D -1"
        };

        Solve task = new Solve();
        System.out.println(Arrays.toString(task.solution(operations2)));
        //System.out.println(Arrays.toString(task.solution(custom)));
    }

    private static class Solve {
        private int[] ans;
        private PriorityQueue<Integer> pq_up,pq_down;

        public int[] solution(String[] operations) {
            init_setting(operations);

            dual_priority_queue(operations);

            return ans;
        }

        private void dual_priority_queue(String[] operations) {
            for(String ops : operations) {
                String[] op = ops.split(" ");

                String command = op[0];
                Integer num = Integer.valueOf(op[1]);

                switch (command) {
                    case "I":
                        pq_up.add(num);
                        pq_down.add(num);
                        break;
                    case "D": {
                        if(num == 1) {
                            pq_up.remove(pq_down.poll());
                        } else {
                            pq_down.remove(pq_up.poll());
                        }
                        break;
                    }
                }
            }

            ans[0] = pq_down.isEmpty() ? 0 : pq_down.poll();
            ans[1] = pq_up.isEmpty() ? 0 : pq_up.poll();
        }

        private void init_setting(String[] operations) {
            ans = new int[2];

            pq_up = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });

            pq_down =  new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.compareTo(o1);
                }
            });

            ans[0] = ans[1] = 0;
        }
    }
}
