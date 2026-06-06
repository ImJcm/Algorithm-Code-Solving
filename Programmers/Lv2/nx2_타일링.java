package Lv2;

/*
2 x n 타일링
제출 내역
문제 설명
가로 길이가 2이고 세로의 길이가 1인 직사각형모양의 타일이 있습니다. 이 직사각형 타일을 이용하여 세로의 길이가 2이고 가로의 길이가 n인 바닥을 가득 채우려고 합니다. 타일을 채울 때는 다음과 같이 2가지 방법이 있습니다.

타일을 가로로 배치 하는 경우
타일을 세로로 배치 하는 경우
예를들어서 n이 7인 직사각형은 다음과 같이 채울 수 있습니다.

Imgur

직사각형의 가로의 길이 n이 매개변수로 주어질 때, 이 직사각형을 채우는 방법의 수를 return 하는 solution 함수를 완성해주세요.

제한사항
가로의 길이 n은 60,000이하의 자연수 입니다.
경우의 수가 많아 질 수 있으므로, 경우의 수를 1,000,000,007으로 나눈 나머지를 return해주세요.
입출력 예
n	result
4	5
입출력 예 설명
입출력 예 #1
다음과 같이 5가지 방법이 있다.

https://i.imgur.com/keiKrD3.png

https://i.imgur.com/O9GdTE0.png

https://i.imgur.com/IZBmc6M.png

https://i.imgur.com/29LWVzK.png

https://i.imgur.com/z64JbNf.png
 */
/*
알고리즘 핵심
DP
1. n번째 타일링으로 구성할 수 있는 경우는 n-1번째, n-2번째 경우의 수를 조합하여 만들 수 있다.
(f(1) = 1, f(2) = 2, f(3) = 3, f(4) = 5, f(5) = 8, ... 정규식 => f(n) = f(n-1) + f(n-2) (n>=2, f(0) = 1)

가능한 모든 경우의 수를 찾는 경우 시간초과 발생 예상된다.
 */
public class nx2_타일링 {
    static void main() {
        int n = 4;

        Solve task = new Solve();
        System.out.println(task.solution(n));
    }

    private static class Solve {
        private int ans;
        private int[] dp;
        private final int limit = 1_000_000_007;

        public int solution(int n) {
            init_setting(n);

            tiling(n,dp);

            return ans;
        }

        private void tiling(int n, int[] dp) {
            for(int i = 2; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
                dp[i] %= limit;
            }

            ans = dp[n];
        }

        private void init_setting(int n) {
            ans = 0;

            dp = new int[n + 1];

            dp[0] = dp[1] = 1;
        }
    }
}
