package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
동전 1

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (추가 시간 없음)	4 MB	65186	30821	23501	47.310%
문제
n가지 종류의 동전이 있다. 각각의 동전이 나타내는 가치는 다르다. 이 동전을 적당히 사용해서, 그 가치의 합이 k원이 되도록 하고 싶다. 그 경우의 수를 구하시오. 각각의 동전은 몇 개라도 사용할 수 있다.

사용한 동전의 구성이 같은데, 순서만 다른 것은 같은 경우이다.

입력
첫째 줄에 n, k가 주어진다. (1 ≤ n ≤ 100, 1 ≤ k ≤ 10,000) 다음 n개의 줄에는 각각의 동전의 가치가 주어진다. 동전의 가치는 100,000보다 작거나 같은 자연수이다.

출력
첫째 줄에 경우의 수를 출력한다. 경우의 수는 231보다 작다.

예제 입력 1
3 10
1
2
5
예제 출력 1
10
출처
어색한 표현을 찾은 사람: dbfldkfdbgml
빠진 조건을 찾은 사람: goodcrane3
데이터를 추가한 사람: jh05013
알고리즘 분류
다이나믹 프로그래밍
 */
/*
아래와 같이 실제 답을 나열하여 규칙을 찾아보려고 했고, 그 결과 k의 가치를 만들기 위해 동전의 가치를 뺀 시점의 경우의 수를 이용하는 것이라 생각하여
dp[k] = dp[k-coin[i]] (1 <= i < N) 이라는 점화식을 세울 수 있었는데 점화식의 규칙이 성립하지 않았다.

values = 1,2,5 | K = 10     values = 1,3,5 | K = 10
dp[1] = 1                   dp[1] = 1
dp[2] = 2                   dp[2] = 1
dp[3] = 2                   dp[3] = 2
dp[4] = 3                   dp[4] = 2
dp[5] = 4                   dp[5] = 3
dp[6] = 5                   dp[6] = 4
dp[7] = 6                   dp[7] = 4
dp[8] = 7                   dp[8] = 5
dp[9] = 8                   dp[9] = 6
dp[10] = 10                 dp[10] = 6

위의 점화식에서의 개념은 맞다고 생각하는데 답이 성립하지 않아서 잘못된 점화식인가 생각하고 다른 점화식도 만들어 봤지만 잘못된 방식이였다.

문제가 짐 싸기 문제(knapsack)와 접근방법이 비슷하다고 생각은 했는데 응용할 방법이 생각나지는 않았다.

그래서 dfs로 완전 탐색을 이용하여 풀 생각도 해봤지만 "순서가 다르지만 구성이 같은 경우 같은 경우의 수로 판단"이라는 조건과 완전 탐색을
수행 시 시간초과가 발생할 것이라는 확신때문에 dp 풀이방식에 집중하였지만 도저히 모르겠어서 정답 코드와 개념을 참고하였다.

풀이 개념
1. k원이 되는 조합을 dp[k]라고 두고, dp[k] = dp[k-coin[1]] + dp[k-coin[2]] + ... + dp[k-coin[i]] (1<= i < N) 이라는 점화식은
    순서만 다르고 조합이 같은 경우도 중복하여 경우의 수로 인식한다는 문제가 있다.
2. 순서가 다르고 조합이 같은 경우를 중복시키지 않는 방법
    a. 동전을 한가지만 사용할 때의 경우의 수, 두가지 사용할 때의 경우의 수, 세가지 사용할 때의 경우의 수, ... 와 같은 방식으로 동전의 가지 수를 늘려가면서 메모리제이션을 수행한다.
3. values = 1,2,5 | k = 10을 기준으로
K/coins  1  2  3  4  5  6  7  8  9  10
coin = 1 1  1  1  1  1  1  1  1  1  1
coin = 2 1  2  2  3  3  4  4  5  5  6
coin = 5 1  2  2  3  4  5  6  7  8  10
k = 1의 경우, 동전 1로만 나타낼 수 있고 동전 2로는 나타내기가 불가능하다.
k = 2의 경우, 동전 1과 동전 2로 나타낼 수 있기 때문에 기존의 dp[2] = dp[2] + 1이 된다.
k = 3의 경우, 동전 1과 동전 2로 나타낼 수 있는데, (1,1,1), (1,2), (2,1)에서 (1,2) = (2,1)이기 때문에
동전 1로 3을 구성한 (1,1,1)에서 동전 2를 사용해서 3을 만드는 dp[3-2] = dp[1]을 더한 값이 된다.
따라서, dp[3] = dp[3] + dp[1] => 동전 1,2로 표현할 수 있는 경우의 수는 = 동전 1로 표현할 수 있는 경우의 수 + 동전 2가 추가된 경우의 수
즉, dp[k] = dp[k] + dp[k-coin[i]]라는 점화식을 나타낼 수 있다.
 */
public class BOJ2293 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int n,k;
    static int[] values,dp;

    public static void main(String[] args) throws IOException{
        init_setting();

        solve();
    }

    static void solve() {
        dp[0] = 1;  // 어떠한 동전도 사용하지 않는 경우

        // 사용할 동전의 개수, 사용하는 동전의 가치를 나타내는 변수 = i
        for(int i=0;i<n;i++) {
            // 나타낼 수 있는 동전들의 가치의 합 = j
            for(int j=1;j<=k;j++) {
                if(j >= values[i]) {
                    dp[j] += dp[j-values[i]];
                }
            }
        }

        System.out.println(dp[k]);
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        n = Integer.parseInt(input[0]);
        k = Integer.parseInt(input[1]);

        values = new int[n];
        dp = new int[k+1];

        for(int i=0;i<n;i++) {
            int v = Integer.parseInt(br.readLine());

            values[i] = v;
        }
    }
}
