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
public class BOJ10564 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T,N,M;
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

        dp = new int[N + 1][100];

        for(int i = 0; i < N + 1; i++) Arrays.fill(dp[i], -1);

        int ans = dfs(N,0);

        System.out.println(ans);
    }

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
