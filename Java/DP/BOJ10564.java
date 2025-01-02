package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
팔굽혀펴기 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	1770	538	365	30.215%
문제


동혁이는 미식축구를 보면서 팔굽혀펴기를 하고 있다. 동혁이는 자신이 응원하는 팀이 득점을 성공할 때 마다 팀이 득점한 점수만큼 팔굽혀펴기를 한다.

만약, 팀이 터치 다운(7점)을 했다면 총 7번 팔굽혀펴기를 해야 하고, 그 다음에 필드 골(3점)을 했다면, 팔굽혀펴기를 10번 해야 한다. 그 다음 세이프티(2점)을 했다면, 이제 팔굽혀펴기를 12번 해야 한다. 만약, 이 상태에서 게임이 끝났다면, 동혁이는 게임을 보는 동안 7+10+12=29번 팔굽혀펴기를 한 것이다.

경기가 끝난 후에 친구를 만난 동혁이는 "나 오늘 총 N번 팔굽혀펴기를 했어!" 라고 자랑했다. N이 주어졌을 때, 동혁이가 응원하는 팀이 득점한 점수를 구하는 프로그램을 작성하시오. 가능한 점수가 여러 가지라면, 가장 큰 값을 구한다. 예를 들어, 동혁이가 팔굽혀펴기를 29번 했다면, 팀은 3, 2, 2, 7점 총 14점을 득점한 것이 가능한 점수 중에서 최댓값이다.

입력
첫째 줄에 테스트 케이스의 개수가 주어지며, 이 값은 1과 20을 포함하는 그 사이의 값이다.

각 테스트 케이스는 두 정수 N과 M (1 ≤ N ≤ 5,000, 1 ≤ M ≤ 10) 으로 시작하며, N은 동혁이가 한 팔굽혀펴기의 횟수, M은 그 경기에서 나올 수 있는 득점의 종류이다.

다음 M개의 줄에는 Si (1 ≤ Si ≤ 20)가 주어지며, 각각의 값은 그 경기에서 팀이 한 번에 득점할 수 있는 점수이다. 모든 점수는 서로 다르다.

출력
각각의 테스트 케이스에 대해서, 동혁이가 응원하는 팀이 득점한 점수의 최댓값을 출력한다. 만약, 불가능한 경우에는 -1을 출력한다.

예제 입력 1
4
29 3
7 3 2
15 1
1
16 1
1
6 2
3 1
예제 출력 1
14
5
-1
3
출처
ICPC > Regionals > North America > Pacific Northwest Regional > 2014 Pacific Northwest Region Programming Contest > Division 1 H번

문제를 번역한 사람: baekjoon
알고리즘 분류
다이나믹 프로그래밍
 */
/*
알고리즘 핵심
DP
1. 팔굽혀펴기의 누적된 개수와 경기가 진행되는 라운드 정보를 갖는 2차원 배열 DP
2. 첫 경기에서 7점을 득점 후 2번째 경기에서 2점을 득점하는 경우, 누적된 팔굽혀펴기의 개수는 다음 예시로 누적됨을 알 수 있다.
득점 : 7 2 2 3 인 경우, 7 + (7 + 2) + (7 + 2 + 2) + (7 + 2 + 2 + 3)
득점 : 5 4 3 인 경우, 5 + (5 + 4) + (5 + 4 + 3)
위 예시에서 총 4경기를 진행한 경우 1번째 경기의 득점 수는 곱하기 4가 적용되고, 2번째 경이의 득점 수는 곱하기 3, 3번째 경기는 곱하기 2, 4번째 경기는 곱하기 1을
적용하여 누적된 팔굽혀펴기의 개수를 구할 수 있다.
따라서, 누적된 팔굽혀펴기의 개수와 현재 진행중인 경기의 횟수를 메모리제이션 차원으로 사용한다.

팔굽혀펴기의 누적된 개수는 득점한 점수 값을 더하기 전 팔굽혀펴기의 수에 득점만큼 더해진 값이므로 메모리제이션을 사용하여 총 득점 수를 저장하여 메모리제이션으로 사용한다.

진행되는 경기의 횟수를 i, 팔굽혀펴기의 총 횟수를 j라고 할 때,
점화식은 dp[j][i] = max(dp[j][i], dp[j - i * score][i - 1] + score)이다.
 */
public class BOJ10564 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T,N,M,push_ups,matches;
    static int[] scores;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        init_setting();

        while(T-- > 0) {
            solve();
        }
    }

    private static void solve() throws IOException{
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        scores = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int max_score = Arrays.stream(scores).max().getAsInt();

        //matches = 100;
        matches = 5;
        push_ups = Math.max(N, max_score);

        dp = new int[push_ups + 1][matches + 1];

        for(int i = 0; i < push_ups + 1; i++) Arrays.fill(dp[i], -1);

        // int ans = dfs(N,0);

        // int ans = dfs_2();

        int ans = dfs_3(N,0);

        ans = ans == -2 ? -1 : ans;

        System.out.println(ans);
    }

    // 정답 - 상향식 DP
    private static int dfs_2() {
        for(int s : scores) dp[s][1] = s;

        int ans = -1;

        for(int i = 1; i < matches + 1; i++) {
            for(int j = 1; j < push_ups + 1; j++) {
                for(int s : scores) {
                    if(j <= i * s) continue;
                    if(dp[j - i * s][i - 1] == -1) continue;
                    dp[j][i] = Math.max(dp[j][i], dp[j - i * s][i - 1] + s);
                }
            }
            ans = Math.max(ans, dp[N][i]);
        }

        return ans;
    }

    // 정답 - 하향식 DP
    private static int dfs_3(int n, int r) {
        if(n == 0) return 0;

        if(dp[n][r] != -1) return dp[n][r];

        int result = -2;

        for(int s : scores) {
            if(n - (r + 1) * s < 0) continue;

            int semi_result = dfs_3(n - (r + 1) * s, r + 1);

            if(semi_result == -2) continue;

            result = Math.max(result,semi_result + s);
        }

        dp[n][r] = result;

        return dp[n][r];
    }

    // 실패 - 시간초과
    // 원인 : 올바른 메모리제이션이 적용되지 않음
    private static int dfs(int n, int r) {
        if(n == 0) return 0;

        if(dp[n][r] != -1) return dp[n][r];

        int result = -1;

        for(int s : scores) {
            if(n - (r + 1) * s < 0) continue;

            int semi_result = dfs(n - (r + 1) * s, r + 1);

            if(semi_result == -1) continue;

            result = Math.max(result,semi_result + s);
        }

        dp[n][r] = result;

        return dp[n][r];
    }

    private static void init_setting() throws IOException {
        T = Integer.parseInt(br.readLine());
    }
}
