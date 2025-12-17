package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
팰린드롬 만들기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	7189	2555	1850	36.382%
문제
앞에서 뒤로 보나, 뒤에서 앞으로 보나 같은 수열을 팰린드롬 이라고 한다. 예를 들어 {1}, {1, 2, 1}, {1, 2, 2, 1}과 같은 수열은 팰린드롬 이지만, {1, 2, 3}, {1, 2, 3, 2} 등은 팰린드롬이 아니다.

한 수열이 주어졌을 때, 이 수열에 최소 개수의 수를 끼워 넣어 팰린드롬을 만들려고 한다. 최소 몇 개의 수를 끼워 넣으면 되는지를 알아내는 프로그램을 작성하시오.

입력
첫째 줄에 수열의 길이 N(1≤N≤5,000)이 주어진다. 다음 줄에는 N개의 수열을 이루는 수들이 주어진다. 각 수들은 int 범위이다.

출력
첫째 줄에 끼워 넣을 수들의 최소 개수를 출력한다.

예제 입력 1
5
1 2 3 4 2
예제 출력 1
2
출처
문제를 번역한 사람: baekjoon
잘못된 데이터를 찾은 사람: tncks0121
알고리즘 분류
다이나믹 프로그래밍
 */
/*
알고리즘 핵심
DP
1. left,right로 구간을 나누어 팰린드롬을 구성하기 위해 최소한의 추가할 수를 dp에 저장한다.
2. 기저사례로 left가 right를 넘어가는 경우 0을 반환한다.
3. dp에 중복되는 구간의 최소 팰린드럼 수가 0이 아니면 반환한다.
4. left,right의 수가 같으면 left + 1, right - 1로 구간을 좁힌 dfs를 수행한다.
5. left,right의 수가 같지 않으면, left + 1, right 또는 left, right - 1 중 작은 값에 + 1한 값을 dp에 저장한다.
6. dfs(left, right) dp[left][right]를 반환한다.
 */
public class BOJ1695 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static int[] numbers;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        ans = dfs(0,N-1);

        System.out.println(ans);
    }

    private static int dfs(int left, int right) {
        if(left > right) return 0;

        if(dp[left][right] != 0) return dp[left][right];

        if(numbers[left] == numbers[right]) {
            dp[left][right] = dfs(left + 1, right - 1);
        } else {
            dp[left][right] = Math.min(dfs(left + 1,right), dfs(left,right - 1)) + 1;
        }

        return dp[left][right];
    }

    private static void solve_dfs() {
        for(int i = 0; i < N; i++) {
            dfs_2(i-1,i+1,0);
        }

        System.out.println(ans);
    }

    private static void dfs_2(int l, int r, int a) {
        if(l < 0 || r >= N) {
            int remain_left = l + 1;
            int remain_right = N - r;

            ans = Math.min(ans,a + remain_left + remain_right);
            return;
        }

        if(numbers[l] == numbers[r]) {
            dfs_2(l-1,r+1,a);
        } else {
            dfs_2(l-1,r,a+1);
            dfs_2(l,r+1,a+1);
        }
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        ans = Integer.MAX_VALUE;

        numbers = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        dp = new int[N][N];
    }
}
