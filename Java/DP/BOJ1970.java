package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
건배 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	1051	407	286	40.683%
문제
N명의 사람이 원판 테이블에 앉아서 콜라를 마시고 있다. 그 상황에서 두 사람이 짝을 지어서 건배를 하려 한다. 그런데 이들은 건배를 할 때, 보기 좋게 하기 위하여 마시고 있는 콜라의 브랜드가 같은 경우에만 건배를 할 수 있다고 한다. 그리고 사람들이 동시에 건배를 할 때, 사람들의 팔이 서로 엇갈리는 경우에는 건배를 할 수 없다고 한다.



예를 들어 왼쪽 그림과 같은 경우는 겹치는 경우가 없어 건배를 할 수 있으나 오른쪽과 같은 경우에는 건배를 할 수 없다. 사람의 수 N과 각각의 사람이 마시는 콜라의 브랜드가 주어져 있을 때, 동시에 건배를 할 수 있는 가장 많은 쌍의 개수를 출력하시오.

입력
첫 줄에 사람의 수 (1 ≤ N ≤ 1000) 이 주어진다. 그리고 둘째 줄에 N개의 정수(1 이상 100 이하)가 공백을 사이에 두고 주어지는데 이는 각각의 사람이 마시는 콜라의 브랜드이다. (시계방향순서대로 주어진다)

출력
동시에 건배를 할 수 있는 가장 많은 쌍의 개수를 출력한다.

예제 입력 1
22
1 7 1 2 4 2 4 9 1 1 9 4 5 9 4 5 6 9 2 1 2 9
예제 출력 1
8
예제 입력 2
6
1 2 2 1 3 3
예제 출력 2
3
출처
ICPC > Regionals > Europe > Northwestern European Regional Contest > Benelux Algorithm Programming Contest > BAPC 2006 E번

잘못된 조건을 찾은 사람: Apple_Cplus
데이터를 추가한 사람: cgiosy
알고리즘 분류
다이나믹 프로그래밍
 */
/*
알고리즘 핵심
DP
처음 접근으로 시작지점과 끝지점 간의 최대 쌍의 개수를 구하는 것이기 때문에 2차원 DP를 생각하였고,
상향식을 생각하여 0부터 시작하여 N-1까지 끝 지점을 정하는 반복문과 0부터 끝 지점을 구간에서 한 지점을 정하는 반복문으로 DP를 채울 수 있다고 생각하였지만 실패하였다.

도저히 해결 방법이 떠오르지 않아 정답 코드 참고
https://github.com/junhaa/JAVA/commit/773f7653d450fd873c65be7b10a2b56e0fc5132d - 하향식
https://github.com/yunjaena/CSE_Study/commit/5ea121f8435ad3fca3ffef8f69fa106191b36a1d - 상향식

상향식 방법을 2차원의 반복문으로 사용하는 것까지는 생각했으나 0부터 끝 지점까지의 반복문과 끝 지점을 결정하는 반복문 + 시작 지점과 끝 지점사이에서 최대 쌍 개수를 구하는
반복문을 고려하지 못했다고 생각한다.
 */
public class BOJ1970 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, ans;
    static int[] brands;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
        solve2();
    }

    /*
        상향식 DP
        i : 시작 지점과 끝 지점 사이의 구간 길이
        s : 시작 지점
        e : 끝 지점 (상대적 위치)
        e_in_round : 원형 테이블에서의 인덱스 번호 (절대적 위치)
     */
    private static void solve2() {
        for(int i = 0; i < N; i++) Arrays.fill(dp[i], 0);

        for(int i = 2; i <= N; i++) {
            for(int s = 0; s < N; s++) {
                int e = s + i - 1;
                int e_in_round = e % N;

                // 시작 지점과 끝 지점의 콜라 브랜드가 같으면 (시작 지점 + 1, 끝 지점 - 1)의 dp + 1과 큰 값을 업데이트
                if(brands[s] == brands[e_in_round]) {
                    dp[s][e_in_round] = Math.max(dp[s][e_in_round], dp[(s + 1) % N][(e_in_round + N - 1) % N] + 1);
                }

                //시작 지점과 끝 지점 사이에서 한 점을 기준으로 (s,e) / (s, m) + (m + 1, e_in_round) 값 중 큰 값으로 업데이트
                for(int m = s; m < e; m++) {
                    dp[s][e_in_round] = Math.max(dp[s][e_in_round], dp[s][m % N] + dp[(m + 1) % N][e_in_round]);
                }
            }
        }

        System.out.println(dp[0][N - 1]);
    }

    /*
        하향식 DP
     */
    private static void solve() {
        ans = dfs_dp(0,N - 1);

        System.out.println(ans);
    }

    /*
        s : 시작 지점
        e : 끝 지점
        끝 지점을 시작으로 시작 지점을 +1 씩 늘려가며 s와 e 내부에서 i값을 지정하여 (s,e)의 값을 s와 i의 콜라 브랜드가 같은 경우
        (s,e) / (s + 1, i - 1) + (i + 1, e) + 1 중 큰 값으로 업데이트
     */
    private static int dfs_dp(int s, int e) {
        if(s >= e) return 0;
        if(dp[s][e] != -1) return dp[s][e];

        int r = dfs_dp(s + 1, e);

        for(int i = s + 1; i <= e; i++) {
            if(brands[s] == brands[i]) {
                r = Math.max(r, dfs_dp(s + 1, i - 1) + dfs_dp(i + 1,e) + 1);
            }
        }
        dp[s][e] = r;

        return dp[s][e];
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        brands = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        dp = new int[N][N];

        for(int i = 0; i < N; i++) Arrays.fill(dp[i], -1);
    }
}
