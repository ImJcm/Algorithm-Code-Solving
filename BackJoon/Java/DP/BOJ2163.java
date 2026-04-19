package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
초콜릿 자르기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	46604	32428	28801	71.161%
문제
정화는 N×M 크기의 초콜릿을 하나 가지고 있다. 초콜릿은 금이 가 있는 모양을 하고 있으며, 그 금에 의해 N×M개의 조각으로 나눠질 수 있다.

초콜릿의 크기가 너무 크다고 생각한 그녀는 초콜릿을 친구들과 나눠 먹기로 했다. 이를 위해서 정화는 초콜릿을 계속 쪼개서 총 N×M개의 조각으로 쪼개려고 한다. 초콜릿을 쪼갤 때에는 초콜릿 조각을 하나 들고, 적당한 위치에서 초콜릿을 쪼갠다. 초콜릿을 쪼갤 때에는 금이 가 있는 위치에서만 쪼갤 수 있다. 이와 같이 초콜릿을 쪼개면 초콜릿은 두 개의 조각으로 나눠지게 된다. 이제 다시 이 중에서 초콜릿 조각을 하나 들고, 쪼개는 과정을 반복하면 된다.

초콜릿을 쪼개다보면 초콜릿이 녹을 수 있기 때문에, 정화는 가급적이면 초콜릿을 쪼개는 횟수를 최소로 하려 한다. 초콜릿의 크기가 주어졌을 때, 이를 1×1 크기의 초콜릿으로 쪼개기 위한 최소 쪼개기 횟수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 두 정수 N, M(1 ≤ N, M ≤ 300)이 주어진다.

출력
첫째 줄에 답을 출력한다.

예제 입력 1
2 2
예제 출력 1
3
예제 입력 2
1 1
예제 출력 2
0
출처
문제의 오타를 찾은 사람: gusqja8753
알고리즘 분류
수학
사칙연산
 */
/*
알고리즘 핵심
DP
1. 가로, 세로의 정보를 갖는 메모리제이션을 이용하고, [N][M]의 크기를 갖는 초콜릿이 있을 때 최소 횟수의 자르기 수를 저장한다.
2. 1x1 크기일 때, 0을 반환한다.
3. NxM 크기일 때, min(N-1 x M + (M - 1) + 1, N x M-1 + (N - 1) + 1)로 선택한다.
이때, N or M이 1일 때는 초콜릿 자르기의 횟수는 M - 1 or N - 1로 정해지기 때문이고, + 1은 두 초콜릿을 나누는 과정에서 자르는 횟수를 의미한다.
 */
public class BOJ2163 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,ans;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
        solve2();
    }

    // 상향식
    private static void solve2() {
        for(int i = 0; i < N + 1; i++) Arrays.fill(dp[i], 0);

        for(int n = 1; n < N + 1; n++) {
            for(int m = 1; m < M + 1; m++) {
                if(n == 1 && m == 1) continue;
                dp[n][m] = Math.min(dp[n - 1][m] + m, dp[n][m - 1] + n);
            }
        }

        System.out.println(dp[N][M]);
    }

    private static void solve() {
        //ans = dfs_dp(N,M);
        ans = dfs_dp2(N,M);

        System.out.println(ans);
    }

    // 하향식_1
    private static int dfs_dp(int n, int m) {
        if(n == 1 && m == 1) return 0;

        int nn = Math.max(n,m);
        int nm = Math.min(n,m);

        if(dp[nn][nm] != Integer.MAX_VALUE) return dp[nn][nm];

        int r = dp[nn][nm];

        for(int i = 1; i < nn; i++) {
           r = Math.min(r, dfs_dp(i, nm) + dfs_dp(nn - i, nm) + 1);
        }

        dp[nn][nm] = r;

        return dp[nn][nm];
    }

    // 하향식_2 - 1에서 단축
    private static int dfs_dp2(int n, int m) {
        if(n == 1) return m - 1;
        if(m == 1) return n - 1;

        if(dp[n][m] != Integer.MAX_VALUE) return dp[n][m];

        dp[n][m] = Math.min(dfs_dp2(n - 1, m) + dfs_dp2(1, m) + 1, dfs_dp2(n, m - 1) + dfs_dp2(n, 1) + 1);

        return dp[n][m];
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        ans = 0;

        int max_length = Math.max(N,M);
        dp = new int[max_length + 1][max_length + 1];

        for(int n = 0; n < max_length + 1; n++) Arrays.fill(dp[n], Integer.MAX_VALUE);
    }
}
