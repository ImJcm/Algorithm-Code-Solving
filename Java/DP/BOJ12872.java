package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
플레이리스트

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	1470	700	512	48.531%
문제
수빈이는 BOJ 알고리즘 캠프에서 음악을 들으면서 문제를 풀고 있다. 지금 수빈이의 스마트폰에는 N개의 노래가 저장되어 있다. 오늘 수빈이는 P개의 노래를 들으려고 한다. 수빈이는 다음과 같은 조건을 만족하는 플레이리스트를 만들려고 한다. 플레이리스트에는 같은 노래를 여러 번 추가해도 된다.

모든 노래를 플레이리스트에 추가해야 한다.
같은 노래를 추가하려면, 플레이리스트에서 두 노래 사이에 적어도 M개의 곡이 있어야 한다.
수빈이는 플레이리스트를 만들 수 있는 경우의 수가 궁금해졌다. N, M, P가 주어졌을 때, 수빈이가 만들 수 있는 플레이리스트의 경우의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N, M, P가 주어진다. (1 ≤ N ≤ 100, 0 ≤ M ≤ N, N ≤ P ≤ 100)

출력
첫째 줄에 수빈이가 만들 수 있는 플레이리스트의 경우의 수를 출력한다. 경우의 수가 매우 커질 수 있기 때문에, 1,000,000,007로 나눈 나머지를 출력한다.

예제 입력 1
1 0 3
예제 출력 1
1
예제 입력 2
1 1 3
예제 출력 2
0
예제 입력 3
2 0 3
예제 출력 3
6
예제 입력 4
2 1 4
예제 출력 4
2
힌트
예제 1의 경우에 가능한 플레이리스트는 (노래1, 노래1, 노래1) 이다.

예제 2의 경우에는 가능한 플레이리스트가 없다.

예제 3의 경우에 (노래1, 노래1, 노래1), (노래2, 노래2, 노래2)는 불가능한 경우이다.

예제 4의 경우에 가능한 플레이리스트는 (노래1, 노래2, 노래1, 노래2), (노래2, 노래1, 노래2, 노래1) 이다.

출처
문제를 번역한 사람: baekjoon
알고리즘 분류
수학
다이나믹 프로그래밍
 */
/*
알고리즘 핵심
DP
문제의 접근으로 점화식을 찾기위해 bruteforce를 구현하였지만, DP에 사용될 차원들의 정보를 생각하는데 어려움이 있었다.
다양한 예시를 직접 작성하여 중복되는 과정을 통해 과정을 진행하면서 현재 플레이리스트에 들어갈 곡의 현재 위치와 사용된 곡의 수를 이용하는
2차원 배열의 메모리제이션까지는 생각하였지만 dp[P][N] = dp[P - 1][N] => 이전에 추가한 곡을 중복하여 추가한 경우 / dp[P - 1][N + 1] => 이전에 추가하지 않은 곡을 새로 추가한 경우
임을 생각하였지만 위 두 과정을 동시에 만족하는 조건을 생각하기 어려웠다.

그래서, 정답 코드를 참고하여 코드를 작성하였다. - https://grape-world-dev.tistory.com/8

코드 분석
1. 길이가 p인 플레이리스트를 만드는 경우의 수를 나타내는 메모리제이션 배열 dp
2. 현재 플레이리스트가 나타내는 위치 : p, 현재 플레이리스트에 추가한 곡의 수 : s를 dp의 차원 정보로 사용한다.
3. 현재 플레이스트에 담기지 않은 곡의 수 : remain_appendable_songs = N - s를 만족한다.

현재 플레이리스트에 곡을 추가할 위치에 도달할 때, 추가하지 못한 남은 곡의 수가 0인 경우 모든 곡을 추가할 수 있기 때문에 return 1
반면에, 추가히지 못한 남은 곡의 수가 0보다 큰 경우 문제에서 제시한 조건 1에 부적합하므로 return 0을 수행한다.

dp[p][s]의 의미는 플레이리스트에 추가할 곡의 위치 P와 현재 플레이리스트에 추가한 곡의 수 S일 때, 플레이리스트에 곡을 담을 경우의 수이다.

플레이리스트에 추가하지 않은 남은 곡의 수가 0보다 큰 경우, (남은 곡의 수 x 플레이리스트에 남은 곡을 추가한 형태로 P - 1로 위치와 추가한 곡을 늘린 형태로 S + 1에 해당하는 함수를 재귀호출한다.
해당 결과로 나온 값을 r에 누적하여 더한다.

문제에서 제시한 조건 2를 만족하는 경우의 수를 구하기 위해 현재까지 추가된 곡 s가 중복으로 추가할 수 있는 곡과의 간격을 의미하는 M 값보다 큰 경우
현재 위치에서 이미 추가한 곡을 추가할 수 있으므로 ((현재까지 추가한 곡의 수 - 중복추가할 경우 두 곡의 간격) x 플레이리스트에 이미 추가한 곡을 추가한 형태로 P - 1만 설정한 함수를 재귀호출한다.

모든 경우의 수가 누적된 합 결과를 r에 저장되고 해당 값을 mod (=1_000_000_007)로 나눈 나머지 값을 dp[p][s]에 저장한다.

조건 1,2를 모두 만족하는 경우를 만들기 위해 추가 조건으로 반복문을 사용해야 한다고 생각했지만 필요가 없었다.

다시 한번 느끼지만, DP에 사용되는 메모리제이션 배열의 정보와 조건을 만족하는 과정을 만드는 것은 정말 어렵다고 생각한다.

+ 상향식 버전 추가
하향식 버전의 코드를 참고하여 상향식 DP코드를 작성하는 과정에서 remain_appendable_songs의 값에 따라 이전 위치와 사용된 곡 - 1 상태에서 추가되지 않은 남은 곡 수를 곱한 값과
이미 추가된 곡 중 간격 이상인 경우 추가한 경우만은 고려하였는데 원하는 결과를 내지 못했다.

이유가 무엇인지 계속 디버깅을 수행한 결과 remain_appendable_songs가 0이 될 때는 이미 추가된 곡이 모두 사용될 수 있음을 고려하지 못해서 였다.

따라서, 해당 조건을 추가한 코드에서는 원하는 결과값을 낼 수 있었다.
 */
public class BOJ12872 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,P,ans;
    static long[][] dp;
    final static int mod = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
        solve2();
    }

    // 상향식
    private static void solve2() {
        for(int i = 0; i < P + 1; i++) Arrays.fill(dp[i], 0);

        dp[0][0] = 1;

        for(int p = 1; p < P + 1; p++) {
            for(int s = 1; s <= N; s++) {
                int remain_appendable_songs = N - s;
                long r = 0;

                if(remain_appendable_songs > 0) {
                    r += dp[p - 1][s - 1] * remain_appendable_songs;
                }

                if(remain_appendable_songs == 0) {
                    r += dp[p - 1][s - 1] * s;
                }

                if(s > M) {
                    r += dp[p - 1][s] * (s - M);
                }

                dp[p][s] = r % mod;
            }
        }

        System.out.println(dp[P][N]);
    }

    private static void solve() {
        //dfs_bruteforce(P, new int[P + 1], new int[P + 1]);
        ans = (int) dfs_dp(P,0);

        System.out.println(ans);
    }

    // 하향식
    private static long dfs_dp(int p, int s) {
        int remain_appendable_songs = N - s;
        if(p == 0) {
            if(remain_appendable_songs == 0) return 1;
            else return 0;
        }

        if(dp[p][s] != -1) return dp[p][s];

        long r = dp[p][s];
        r = 0;

        if(remain_appendable_songs > 0) {
            r += dfs_dp(p - 1, s + 1) * remain_appendable_songs;
        }

        if(s > M) {
            r += dfs_dp(p - 1, s) * (s - M);
        }

        r %= mod;

        dp[p][s] = r;
        return dp[p][s];
    }

    // DP 점화식 도출용 bruteforce - 시간초과 예상
    private static void dfs_bruteforce(int p, int[] playlist, int[] used) {
        if(p == 0) {
            boolean all_use = true;

            // 조건 1 - 모든 곡이 담겨야 한다.
            for(int i = 1; i <= N; i++) {
                if(used[i] == 0) {
                    all_use = false;
                    break;
                }
            }

            if(all_use) {
                ans = (ans + 1) % mod;
            }

            return;
        }

        for(int s = 1; s <= N; s++) {
            boolean possible = true;

            // 조건 2 - 같은 곡을 담을 경우 두 곡 사이에는 M개의 다른 곡이 있어야 한다.
            if(p + M <= P) {
                for(int l = p + 1; l < p + M + 1; l++) {
                    if(playlist[l] == s) {
                        possible = false;
                        break;
                    }
                }
            }

            if(possible) {
                playlist[p] = s;
                used[s]++;
                dfs_bruteforce(p - 1, playlist, used);
                playlist[p] = 0;
                used[s]--;
            }
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        P = Integer.parseInt(input[2]);

        ans = 0;

        dp = new long[P + 1][N + 1];

        for(int i = 0; i < P + 1; i++) Arrays.fill(dp[i],-1);
    }
}
