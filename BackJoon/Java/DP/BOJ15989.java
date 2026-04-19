package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

/*
1, 2, 3 더하기 4

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초 (추가 시간 없음)	512 MB	9285	5923	4739	64.494%
문제
정수 4를 1, 2, 3의 합으로 나타내는 방법은 총 4가지가 있다. 합을 나타낼 때는 수를 1개 이상 사용해야 한다. 합을 이루고 있는 수의 순서만 다른 것은 같은 것으로 친다.

1+1+1+1
2+1+1 (1+1+2, 1+2+1)
2+2
1+3 (3+1)
정수 n이 주어졌을 때, n을 1, 2, 3의 합으로 나타내는 방법의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 테스트 케이스의 개수 T가 주어진다. 각 테스트 케이스는 한 줄로 이루어져 있고, 정수 n이 주어진다. n은 양수이며 10,000보다 작거나 같다.

출력
각 테스트 케이스마다, n을 1, 2, 3의 합으로 나타내는 방법의 수를 출력한다.

예제 입력 1
3
4
7
10
예제 출력 1
4
8
14
출처
문제를 만든 사람: baekjoon
알고리즘 분류
다이나믹 프로그래밍
 */
/*
점화식을 찾기 위해 일정 수까지 1,2,3으로 구성될 수 있는 경우의 수를 구하였다.
dp[1] = 1;
dp[2] = 2;
dp[3] = 3;
dp[4] = 4;    // 1 + 1 + 1 + 1, 1 + 1 + 2, 1 + 3, 2 + 2
dp[5] = 5;    // 1 + 1 + 1 + 1 + 1, 1 + 1 + 1 + 2, 1 + 1 + 3, 2 + 2 + 1, 2 + 3
dp[6] = 7;    // 1 + 1 + 1 + 1 + 1 + 1, 1 + 1 + 1 + 1 + 2, 1 + 1 + 1 + 3, 1 + 1 + 2 + 2, 1 + 2 + 3, 2 + 2 + 2, 3 + 3
dp[7] = 8;
dp[8] = 10;
dp[9] = 12;
dp[10] = 14;
dp[11] = 16;
dp[12] = 19;
dp[13] = 21;
dp[14] = 24;
dp[15] = 27;
dp[16] = 30;
...

아래 값에서 규칙성을 발견할 수 있었는데, dp[n] = dp[n-1] + dp[n-2] - dp[n-3]을 볼 수 있는데
예외적으로, 3의 배수인 경우, dp[n] = dp[n-1] + dp[n-2] - dp[n-3] + 1;이 적용된다.

재귀형태의 dp는 함수 호출이 많기 때문에 bottom-up 반복문 호출형태로 dp를 계산하는 것이 좋다고 생각한다.
 */
public class BOJ15989 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T,N,count;
    static int[] dp;
    static HashSet<String> visited;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() throws IOException {
        //dp_recursive(10000);
        dp_repeat();

        for(int i=0;i<T;i++) {
            N = Integer.parseInt(br.readLine());
            System.out.println(dp[N]);
        }
    }

    static void dp_repeat() {
        for(int i=4;i<=10000;i++) {
            if(i % 3 == 0) {
                dp[i] = dp[i-1] + dp[i-2] - dp[i-3] + 1;
            } else {
                dp[i] = dp[i-1] + dp[i-2] - dp[i-3];
            }
        }
    }

    //recursive 형태로 dp를 구하게 되면 많은 수의 재귀함수 호출로 시간초과 또는 메모리 초과 발생할 수 있다.
    static int dp_recursive(int sum) {
        if(sum < 4) {
            return dp[sum];
        }

        if(dp[sum] > 0) return dp[sum];

        if(sum % 3 == 0) {
            dp[sum] = dp_recursive(sum-1) + dp_recursive(sum-2) - dp_recursive(sum-3) + 1;
        } else {
            dp[sum] = dp_recursive(sum-1) + dp_recursive(sum-2) - dp_recursive(sum-3);
        }

        return dp[sum];
    }

    static void solve_dfs() throws IOException {
        //1,2,3 구성 가능한 경우의 수 구하기
        for(int i=0;i<T;i++) {
            count = 0;
            N = Integer.parseInt(br.readLine());
            dfs(0,0,0,0, N);
            dp[N] = count;
        }
    }

    static void dfs(int sum, int one, int two, int three, int target) {
        if(sum > target) {
            return;
        }

        if(sum == target) {
            String v = one + "/" + two + "/" + three;

            if(!visited.contains(v)) {
                visited.add(v);
                count++;
            }
            return;
        }

        dfs(sum + 1, one + 1, two, three, target);
        dfs(sum + 2, one, two + 1, three, target);
        dfs(sum + 3, one, two, three + 1, target);
    }

    static void init_setting() throws IOException {
        T = Integer.parseInt(br.readLine());

        dp = new int[10001];
        //visited = new HashSet<>();

        Arrays.fill(dp, 0);

        dp[1] = 1;      // 1
        dp[2] = 2;      // 1 + 1, 2
        dp[3] = 3;      // 1 + 1 + 1, 1 + 2, 3

        /*visited.add("1/0/0");
        visited.add("0/1/0");
        visited.add("0/0/1");*/
    }
}
