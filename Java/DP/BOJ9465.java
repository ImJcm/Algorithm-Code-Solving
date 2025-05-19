package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
스티커 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	86404	41332	29617	47.253%
문제
상근이의 여동생 상냥이는 문방구에서 스티커 2n개를 구매했다. 스티커는 그림 (a)와 같이 2행 n열로 배치되어 있다. 상냥이는 스티커를 이용해 책상을 꾸미려고 한다.

상냥이가 구매한 스티커의 품질은 매우 좋지 않다. 스티커 한 장을 떼면, 그 스티커와 변을 공유하는 스티커는 모두 찢어져서 사용할 수 없게 된다. 즉, 뗀 스티커의 왼쪽, 오른쪽, 위, 아래에 있는 스티커는 사용할 수 없게 된다.



모든 스티커를 붙일 수 없게된 상냥이는 각 스티커에 점수를 매기고, 점수의 합이 최대가 되게 스티커를 떼어내려고 한다. 먼저, 그림 (b)와 같이 각 스티커에 점수를 매겼다. 상냥이가 뗄 수 있는 스티커의 점수의 최댓값을 구하는 프로그램을 작성하시오. 즉, 2n개의 스티커 중에서 점수의 합이 최대가 되면서 서로 변을 공유 하지 않는 스티커 집합을 구해야 한다.

위의 그림의 경우에 점수가 50, 50, 100, 60인 스티커를 고르면, 점수는 260이 되고 이 것이 최대 점수이다. 가장 높은 점수를 가지는 두 스티커 (100과 70)은 변을 공유하기 때문에, 동시에 뗄 수 없다.

입력
첫째 줄에 테스트 케이스의 개수 T가 주어진다. 각 테스트 케이스의 첫째 줄에는 n (1 ≤ n ≤ 100,000)이 주어진다. 다음 두 줄에는 n개의 정수가 주어지며, 각 정수는 그 위치에 해당하는 스티커의 점수이다. 연속하는 두 정수 사이에는 빈 칸이 하나 있다. 점수는 0보다 크거나 같고, 100보다 작거나 같은 정수이다.

출력
각 테스트 케이스 마다, 2n개의 스티커 중에서 두 변을 공유하지 않는 스티커 점수의 최댓값을 출력한다.

예제 입력 1
2
5
50 10 100 20 40
30 50 70 10 60
7
10 30 10 50 100 20 40
20 40 30 50 60 20 80
예제 출력 1
260
290
출처
ICPC > Regionals > Asia Pacific > Korea > Asia Regional - Daejeon 2013 K번

문제를 번역한 사람: baekjoon
데이터를 추가한 사람: jh05013, seoyeon1018
알고리즘 분류
다이나믹 프로그래밍
 */
/*
알고리즘 핵심
DP
1. 하나의 스티커를 고를 경우 상,하,좌,우의 스티커는 고를 수 없기 때문에 현재 스티커를 기준으로 최대 스코어를 가지는 경우는
n - 2에서 최대 스코어를 가지는 누적값과 n - 1에서 고를 수 있는 스티커에서 누적된 점수의 최대값을 더한 경우이다.
이를 정규식으로 나타내면 다음과 같다.
dp(r,n) = score(r,n) + max(dp(!r,n-1), dp(r,n-2), dp(!r,n-2)) #(r = 0 or 1)
2. ans = dp(r,N-1), dp(!r,N-1) 중 최대값을 저장하고 출력한다.

#위 정규식을 바탕으로 상향식, 하향식 dp를 모두 구현하였다.
 */
public class BOJ9465 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T,N,ans;
    static int[][] sticker_point,dp;

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        while(T-- > 0) {
            init_setting();

            ans = Math.max(solve_A(0,N - 1), solve_A(1,N - 1));
            //solve();

            System.out.println(ans);
        }
    }

    private static void solve() {
        int n1,n2,d1,d2,d3,d4;

        for(int i = 0; i < N; i++) {
            n1 = i - 2;
            n2 = i - 1;

            d1 = i - 2 < 0 ? 0 : dp[0][n1];
            d2 = i - 2 < 0 ? 0 : dp[1][n1];
            d3 = i - 1 < 0 ? 0 : dp[0][n2];
            d4 = i - 1 < 0 ? 0 : dp[1][n2];

            dp[0][i] = sticker_point[0][i] + Math.max(d1,
                    Math.max(d2,d4));

            dp[1][i] = sticker_point[1][i] + Math.max(d2,
                    Math.max(d1,d3));
        }

        ans = Math.max(dp[0][N - 1], dp[1][N - 1]);
    }

    private static int solve_A(int r, int n) {
        if(n < 0) {
            return 0;
        }

        if(dp[r][n] != 0) return dp[r][n];

        dp[r][n] = sticker_point[r][n] + Math.max(solve_A((r + 1) % 2, n - 1),
                Math.max(solve_A(r, n - 2), solve_A((r + 1) % 2, n - 2)));

        return dp[r][n];
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        dp = new int[2][N];
        sticker_point = new int[2][];

        sticker_point[0] = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        sticker_point[1] = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
