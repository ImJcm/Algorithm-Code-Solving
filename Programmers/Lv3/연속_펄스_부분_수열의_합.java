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
/*
알고리즘 핵심
Prefix_Sum(누적합)
1. [1,-1,...], [-1,1,...]의 각 펄스로 sequence를 곱한 값으로 각각의 누적합 배열을 만들다.
2. 각 누적합 배열을 기준으로 구간에서의 최대값을 구한다.
이때, 초반 최대값은 seq의 배열의 길이가 1인 경우를 고려하여 seq[0]으로 설정하고, 최대값이 처음 요소를 구성하는 경우를
고려하여 이전까지의 최소구간을 min(seq[0], 0)로 구성한다.
 */
public class 연속_펄스_부분_수열의_합 {
    static void main() {
        int[] sequence = new int[] {
                2, 3, -6, 1, 3, -1, 2, 4
                //1
                //6, -7, 16, 3, -4
        };

        Solve task = new Solve();
        System.out.println(task.solution(sequence));
    }

    private static class Solve {
        private long ans;
        private long[] plus_pulse_sequence, minus_pulse_sequence;

        public long solution(int[] sequence) {
            init_setting(sequence);

            partial_sum_of_consecutive_pulses(plus_pulse_sequence);
            partial_sum_of_consecutive_pulses(minus_pulse_sequence);

            return ans;
        }

        private void partial_sum_of_consecutive_pulses(long[] Sequence) {
            long max_sum = Sequence[0];
            long min_prefix = Math.min(Sequence[0], 0);

            for(int i = 1; i < Sequence.length; i++) {
                max_sum = Math.max(max_sum, Sequence[i] - min_prefix);

                min_prefix = Math.min(min_prefix, Sequence[i]);
            }

            ans = Math.max(ans, max_sum);
        }

        private void init_setting(int[] sequence) {
            ans = 0;

            plus_pulse_sequence = new long[sequence.length];
            minus_pulse_sequence = new long[sequence.length];

            int pulse = -1;

            plus_pulse_sequence[0] = sequence[0];
            minus_pulse_sequence[0] = sequence[0] * pulse;

            for(int i = 1; i < sequence.length; i++) {
                plus_pulse_sequence[i] = plus_pulse_sequence[i - 1] + (long) sequence[i] * pulse;
                pulse *= -1;
                minus_pulse_sequence[i] = minus_pulse_sequence[i - 1] + (long) sequence[i] * pulse;
            }
        }
    }
}
