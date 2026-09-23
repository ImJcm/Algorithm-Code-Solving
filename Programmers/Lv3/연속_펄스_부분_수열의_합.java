package Lv3;

/*
연속 펄스 부분 수열의 합
제출 내역
문제 설명
어떤 수열의 연속 부분 수열에 같은 길이의 펄스 수열을 각 원소끼리 곱하여 연속 펄스 부분 수열을 만들려 합니다. 펄스 수열이란 [1, -1, 1, -1 …] 또는 [-1, 1, -1, 1 …] 과 같이 1 또는 -1로 시작하면서 1과 -1이 번갈아 나오는 수열입니다.
예를 들어 수열 [2, 3, -6, 1, 3, -1, 2, 4]의 연속 부분 수열 [3, -6, 1]에 펄스 수열 [1, -1, 1]을 곱하면 연속 펄스 부분수열은 [3, 6, 1]이 됩니다. 또 다른 예시로 연속 부분 수열 [3, -1, 2, 4]에 펄스 수열 [-1, 1, -1, 1]을 곱하면 연속 펄스 부분수열은 [-3, -1, -2, 4]이 됩니다.
정수 수열 sequence가 매개변수로 주어질 때, 연속 펄스 부분 수열의 합 중 가장 큰 것을 return 하도록 solution 함수를 완성해주세요.

제한 사항
1 ≤ sequence의 길이 ≤ 500,000
-100,000 ≤ sequence의 원소 ≤ 100,000
sequence의 원소는 정수입니다.
입출력 예
sequence	result
[2, 3, -6, 1, 3, -1, 2, 4]	10
입출력 예 설명
주어진 수열의 연속 부분 수열 [3, -6, 1]에 펄스 수열 [1, -1, 1]을 곱하여 연속 펄스 부분 수열 [3, 6, 1]을 얻을 수 있고 그 합은 10으로서 가장 큽니다.
 */
public class 연속_펄스_부분_수열의_합 {
    static void main() {
        int[] sequence = new int[] {
                2, 3, -6, 1, 3, -1, 2, 4
        };

        Solve task = new Solve();
        System.out.println(task.solution(sequence));
    }

    private static class Solve {
        private long ans;
        private int[] plus_pulse_sequence, minus_pulse_sequence;

        public long solution(int[] sequence) {
            init_setting(sequence);

            partial_sum_of_consecutive_pulses(plus_pulse_sequence);
            partial_sum_of_consecutive_pulses(minus_pulse_sequence);

            return ans;
        }

        private void partial_sum_of_consecutive_pulses(int[] Sequence) {
            int left = 0, right = 1;
            int max_sum = Sequence[left];

            while(right < Sequence.length) {
                int a1 = Sequence[right] - Sequence[left];
                int a2 = Sequence[right] - Sequence[right - 1];

                if(a1 >= a2) {
                    max_sum = Math.max(max_sum, a1);
                } else {
                    left = right;
                    max_sum = Math.max(max_sum, a2);
                }

                right++;
            }

            ans = Math.max(ans, max_sum);
        }

        private void init_setting(int[] sequence) {
            ans = 0;

            plus_pulse_sequence = new int[sequence.length];
            minus_pulse_sequence = new int[sequence.length];

            int pulse = -1;

            plus_pulse_sequence[0] = sequence[0];
            minus_pulse_sequence[0] = sequence[0] * pulse;

            for(int i = 1; i < sequence.length; i++) {
                plus_pulse_sequence[i] = plus_pulse_sequence[i - 1] + sequence[i] * pulse;
                pulse *= -1;
                minus_pulse_sequence[i] = minus_pulse_sequence[i - 1] + sequence[i] * pulse;
            }
        }
    }
}
