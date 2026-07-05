package Lv2;

import java.util.Arrays;

/*
2개 이하로 다른 비트
제출 내역
문제 설명
양의 정수 x에 대한 함수 f(x)를 다음과 같이 정의합니다.

x보다 크고 x와 비트가 1~2개 다른 수들 중에서 제일 작은 수
예를 들어,

f(2) = 3 입니다. 다음 표와 같이 2보다 큰 수들 중에서 비트가 다른 지점이 2개 이하이면서 제일 작은 수가 3이기 때문입니다.
수	비트	다른 비트의 개수
2	000...0010
3	000...0011	1
f(7) = 11 입니다. 다음 표와 같이 7보다 큰 수들 중에서 비트가 다른 지점이 2개 이하이면서 제일 작은 수가 11이기 때문입니다.
수	비트	다른 비트의 개수
7	000...0111
8	000...1000	4
9	000...1001	3
10	000...1010	3
11	000...1011	2
정수들이 담긴 배열 numbers가 매개변수로 주어집니다. numbers의 모든 수들에 대하여 각 수의 f 값을 배열에 차례대로 담아 return 하도록 solution 함수를 완성해주세요.

제한사항
1 ≤ numbers의 길이 ≤ 100,000
0 ≤ numbers의 모든 수 ≤ 1015
입출력 예
numbers	result
[2,7]	[3,11]
입출력 예 설명
입출력 예 #1

문제 예시와 같습니다.
 */
/*
알고리즘 핵심
비트 + 구현
1. 입력으로 주어진 number를 long -> String 타입의 binaryString 형태로 만든다.
2. 이떄 문제의 조건으로 주어지는 1~2개의 다른 비트이면서 x보다 큰 수는 가장 우측의 비트부터 1씩 증가시키는 것이 옳다.
3. 이때, 우측에서부터 비트의 값에 따라 다음 로직을 수행한다.
3-1. 0인 경우, 해당 비트의 값을 1로 증가시킨다. (= 해당 비트 위치에 따른 값을 더해준다.)
3-2. 1인 경우, 다음 비트의 값이 0인 경우, 해당 비트의 값을 1증가시키는 것이 조건을 만족하는 값이다. (= 앞선 비트와 현재비트의 값을 서로 바꾼다.)
 */
// 실제 문제 제목 : 2개 이하로 다른 비트
public class 두개_이하로_다른_비트 {
    static void main() {
        long[] numbers = new long[] {
                2,7
        };

        Solve task = new Solve();
        System.out.println(Arrays.toString(task.solution(numbers)));
    }

    private static class Solve {
        private long[] ans;

        public long[] solution(long[] numbers) {
            init_setting(numbers);

            for(int i = 0; i < numbers.length; i++) {
                ans[i] = f(numbers[i]);
            }

            return ans;
        }

        private long f(long num) {
            long res = num;

            String bs = "0" + Long.toBinaryString(res);

            for(int i = bs.length() - 1; i > 0; i--) {
                if(bs.charAt(i) == '0' || (bs.charAt(i) == '1' && bs.charAt(i - 1) == '0')) {
                    res += (long) Math.pow(2, bs.length() - i - 1);
                    break;
                }
            }
            return res;
        }

        private void init_setting(long[] numbers) {
            ans = new  long[numbers.length];
        }
    }
}
