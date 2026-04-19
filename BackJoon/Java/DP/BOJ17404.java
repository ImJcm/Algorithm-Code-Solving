package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
RGB거리 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (추가 시간 없음)	128 MB	20580	12292	10164	59.933%
문제
RGB거리에는 집이 N개 있다. 거리는 선분으로 나타낼 수 있고, 1번 집부터 N번 집이 순서대로 있다.

집은 빨강, 초록, 파랑 중 하나의 색으로 칠해야 한다. 각각의 집을 빨강, 초록, 파랑으로 칠하는 비용이 주어졌을 때, 아래 규칙을 만족하면서 모든 집을 칠하는 비용의 최솟값을 구해보자.

1번 집의 색은 2번, N번 집의 색과 같지 않아야 한다.
N번 집의 색은 N-1번, 1번 집의 색과 같지 않아야 한다.
i(2 ≤ i ≤ N-1)번 집의 색은 i-1, i+1번 집의 색과 같지 않아야 한다.
입력
첫째 줄에 집의 수 N(2 ≤ N ≤ 1,000)이 주어진다. 둘째 줄부터 N개의 줄에는 각 집을 빨강, 초록, 파랑으로 칠하는 비용이 1번 집부터 한 줄에 하나씩 주어진다. 집을 칠하는 비용은 1,000보다 작거나 같은 자연수이다.

출력
첫째 줄에 모든 집을 칠하는 비용의 최솟값을 출력한다.

예제 입력 1
3
26 40 83
49 60 57
13 89 99
예제 출력 1
110
예제 입력 2
3
1 100 100
100 1 100
100 100 1
예제 출력 2
3
예제 입력 3
3
1 100 100
100 100 100
1 100 100
예제 출력 3
201
예제 입력 4
6
30 19 5
64 77 64
15 19 97
4 71 57
90 86 84
93 32 91
예제 출력 4
208
예제 입력 5
8
71 39 44
32 83 55
51 37 63
89 29 100
83 58 11
65 13 15
47 25 29
60 66 19
예제 출력 5
253
출처
문제를 만든 사람: baekjoon
알고리즘 분류
다이나믹 프로그래밍
 */
/*
알고리즘 핵심
DP
1. n번째 집 위치, n번째 집들 중 i(빨강,초록,파랑)집 위치, 0번째 집의 색을 칠한 위치인 3개의 정보를 담은 3차원 dp를 사용한다.
3차원으로 정한 이유
[n번째 집 위치][n번째 집들 중 i집 위치]인 2차원 dp를 사용할 경우, 처음 집을 색칠한 색과 마지막에 집에 색칠한 색이 최소 비용을 만족하더라도
처음 집과 마지막 집의 색이 다른 색임을 보장하지 못한다.
ex)
1 2 3
6 5 4
2 3 1
6 4 5
시작을 0번째에서 2번째 집을 색칠한 경우 2 -> 4 -> 2 -> 4가 최소 비용을 나타내지만 0번째의 집과 N-1번째 집의 색상이 같으므로 불가능하다.
하지만 2차원 배열로는 아래 3개의 조건을 만족하지 못한다.

- 1번 집의 색은 2번, N번 집의 색과 같지 않아야 한다.
- N번 집의 색은 N-1번, 1번 집의 색과 같지 않아야 한다.
- i(2 ≤ i ≤ N-1)번 집의 색은 i-1, i+1번 집의 색과 같지 않아야 한다.

따라서, 시작과 끝 색상의 조건을 만족하기 위한 "처음 집의 색을 칠한 위치" 정보를 추가하여 n번째의 집 위치에서 i번째 집의 색상을 고를 때
처음 집의 색상을 칠한 위치에서 분기된 최소 비용만을 고려할 수 있다.
2. n번째 집에서 i번째를 색상을 고를 경우 i번째를 제외한 나머지 n-1번째 집에서의 최소 비용을 더한 값의 최소값을 누적한다.
3. n-1번째 도달할 경우, 처음 위치의 집에서 색을 칠한 위치의 집의 번호와 다음 칠할 집의 위치가 같은 경우 IMPOSSIBLE을 반환하여 불가능한 경우로 나타내고,
같지 않은 경우 해당 집 위치의 색상 비용을 반환하여 기저 사례로 반환하여 최소 비용을 업데이트한다.
4. n번째 집 위치 + i번째 집을 칠할 경우 + 처음 집의 칠한 집의 위치 정보를 담는 함수 = print_paint(n,i,i)를
0번째를 시작으로 0~2 집에 칠한 경우 + 처음 칠한 집의 위치를 각각 호출하여 최소 비용을 ans에 업데이트한다.

# 문제에서 제시하는 색을 칠하는 조건이 없는 경우 2차원의 dp로 구성 가능하다.
 */
public class BOJ17404 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static int[][] cost;
    static int[][][] dp;
    final static int IMPOSSIBLE = 1_000_001;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    // 하향식 - bottom-up
    private static void print_paint_2() {
        dp[0][0][0] = cost[0][0];
        dp[0][1][1] = cost[0][1];
        dp[0][2][2] = cost[0][2];

        for(int n = 1; n < N; n++) {
            for(int i = 0; i < 3; i++) {
                for(int s = 0; s < 3; s++) {
                    int res_1 = dp[n - 1][(i + 1) % 3][s] == 0 ? IMPOSSIBLE : dp[n - 1][(i + 1) % 3][s];
                    int res_2 = dp[n - 1][(i + 2) % 3][s] == 0 ? IMPOSSIBLE : dp[n - 1][(i + 2) % 3][s];
                    dp[n][i][s] = cost[n][i] + Math.min(res_1, res_2);
                }
            }
        }

        ans = IMPOSSIBLE;

        for(int i = 0; i < 3; i++) {
            for(int s = 0; s < 3; s++) {
                if(i == s) continue;
                ans = Math.min(ans, dp[N - 1][i][s]);
            }
        }
    }

    // 상향식 - top-down
    private static int print_paint(int n, int i, int s) {
        if(n == N - 1) {
            if(i == s) return IMPOSSIBLE;
            else return cost[n][i];
        }

        if(dp[n][i][s] != 0) return dp[n][i][s];

        dp[n][i][s] = cost[n][i] + Math.min(print_paint(n + 1, (i + 1) % 3, s), print_paint(n + 1, (i + 2) % 3, s));

        return dp[n][i][s];
    }

    private static void solve() {
        print_paint_2();

        int ans_0 = print_paint(0,0,0);
        int ans_1 = print_paint(0,1,1);
        int ans_2 = print_paint(0,2,2);

        ans = Math.min(ans_0,
                Math.min(ans_1,ans_2));

        System.out.println(ans);
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        cost = new int[N][3];
        dp = new int[N][3][3];

        for(int i = 0; i < N; i++) {
            cost[i] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
    }
}
