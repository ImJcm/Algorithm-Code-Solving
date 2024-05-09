package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
기타리스트

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	21024	8123	6354	38.068%
문제
Day Of Mourning의 기타리스트 강토는 다가오는 공연에서 연주할 N개의 곡을 연주하고 있다. 지금까지 공연과는 다른 공연을 보여주기 위해서 이번 공연에서는 매번 곡이 시작하기 전에 볼륨을 바꾸고 연주하려고 한다.

먼저, 공연이 시작하기 전에 각각의 곡이 시작하기 전에 바꿀 수 있는 볼륨의 리스트를 만들었다. 이 리스트를 V라고 했을 때, V[i]는 i번째 곡을 연주하기 전에 바꿀 수 있는 볼륨을 의미한다. 항상 리스트에 적힌 차이로만 볼륨을 바꿀 수 있다. 즉, 현재 볼륨이 P이고 지금 i번째 곡을 연주하기 전이라면, i번 곡은 P+V[i]나 P-V[i] 로 연주해야 한다. 하지만, 0보다 작은 값으로 볼륨을 바꾸거나, M보다 큰 값으로 볼륨을 바꿀 수 없다.

곡의 개수 N과 시작 볼륨 S, 그리고 M이 주어졌을 때, 마지막 곡을 연주할 수 있는 볼륨 중 최댓값을 구하는 프로그램을 작성하시오. 모든 곡은 리스트에 적힌 순서대로 연주해야 한다.

입력
첫째 줄에 N, S, M이 주어진다. (1 ≤ N ≤ 50, 1 ≤ M ≤ 1,000, 0 ≤ S ≤ M) 둘째 줄에는 각 곡이 시작하기 전에 줄 수 있는 볼륨의 차이가 주어진다. 이 값은 1보다 크거나 같고, M보다 작거나 같다.

출력
첫째 줄에 가능한 마지막 곡의 볼륨 중 최댓값을 출력한다. 만약 마지막 곡을 연주할 수 없다면 (중간에 볼륨 조절을 할 수 없다면) -1을 출력한다.

예제 입력 1
3 5 10
5 3 7
예제 출력 1
10
예제 입력 2
4 8 20
15 2 9 10
예제 출력 2
-1
예제 입력 3
14 40 243
74 39 127 95 63 140 99 96 154 18 137 162 14 88
예제 출력 3
238
힌트


출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: thak00
알고리즘 분류
다이나믹 프로그래밍
 */
/*
dfs로 구하는 코드는 시간초과 발생한다. - O(2^N)

s를 시작으로 마지막 곡까지 도달할 때의 볼륨은 p + (v[0] + v[1] + ... + v[N-1])의 최대값을 의미하게 된다.
그래서, 최종적인 볼륨의 최대값이 순차적으로 각 순서에서의 볼륨이 최대값을 만족해야 최대값인 볼륨이 나올 것이라고 예상했는데
잘못된 생각이였다.
예시, V[] = {5,3,7}, S = 5, M = 10
5 -> 10 -> 7 -> 14 (x)
             -> 0  (o)
5 -> 0 -> 3 -> 10 (o)
            -> -4 (x)
위 예시처럼 각 순서에서 볼륨의 최대값을 만족하는 것이 최종적인 볼륨이 최대값인 것을 만족하지 못한다.
즉, i번째 볼륨 값 = i-1 볼륨 (+ V[i] or  - V[i]) 를 만족하지 못한다.

도저히 규칙을 점화식을 찾기가 어려워 정답 코드를 참고하였다. - https://lotuslee.tistory.com/131

정답 코드 접근 방법
1. i번째에서 가능한 볼륨의 값을 배열에 저장하는 방법
즉, V = {5,3,7}을 기준으로 1번째에서 가능한 볼륨은 5 + 5, 5 - 5인 10,0 -> dp[0] = dp[10] = 1
2번째에서 가능한 볼륨은 10 + 3 = 13, 0 - 3 = -3은 불가, dp[7] = dp[3] = 2;
3번째에서 가능한 볼륨은 7 + 7 = 14, 3 - 7 = -4는 불가, dp[10] = dp[0] = 3;로 설정하여
N번째 최대값인 볼륨의 값은 N값을 갖는 인덱스 중 큰 값으로 나타낼 수 있다.
 */
public class BOJ1495 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,S,M,max_vol = -1;
    static int[] V,dp,play = {1,-1};


    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        volume_check();
        //dfs(0,S);
        //System.out.println(max_vol);
    }

    /*
        2번째 for문을 진행하면서 내부에서 dp[plus or minus]에서 값의 변형이 이루어질 수 있으므로 2번째 for문에서
        i번째에서 가능한 볼륨을 모두 구하기 전에 dp의 값을 업데이트하지 않고 끝난 후에 업데이트를 진행하도록 한다.
     */
    static void volume_check() {
        dp[S] = 0;

        for(int i=1;i<=N;i++) {
            int cur_v = V[i];
            ArrayList<Integer> possible_volume = new ArrayList<>();

            for(int j=0;j<=M;j++) {
                if(dp[j] == i - 1) {
                    int plus = j + cur_v;
                    int minus = j - cur_v;

                    if(0 <= plus && plus <= M) {
                        possible_volume.add(plus);
                    }

                    if(0 <= minus && minus <= M) {
                        possible_volume.add(minus);
                    }
                }
            }

            /*for(int j=0;j<=M;j++) {
                if(dp[j] == i - 1) {
                    int plus = j + cur_v;
                    int minus = j - cur_v;

                    if(0 <= plus && plus <= M) {
                        dp[plus] = i;
                    }

                    if(0 <= minus && minus <= M) {
                        dp[minus] = i;
                    }
                }
            }*/

            for(int v : possible_volume) {
                dp[v] = i;
            }
        }

        for(int i=0;i<=M;i++) {
            if(dp[i] == N) {
                max_vol = Math.max(max_vol, i);
            }
        }

        System.out.println(max_vol);
    }

    // memory dump
    static void dfs(int depth, int volume) {
        if(depth == N + 1) {
            max_vol = Math.max(max_vol, volume);
            return;
        }

        for(int i=0;i<2;i++) {
            int cur_vol = volume + (play[i] * V[depth]);
            if(cur_vol < 0 || cur_vol > M) continue;
            dfs(depth + 1, cur_vol);
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        S = Integer.parseInt(input[1]);
        M = Integer.parseInt(input[2]);

        V = new int[N+1];
        dp = new int[M+1];

        input = br.readLine().split(" ");

        for(int i=1;i<=input.length;i++) {
            V[i] = Integer.parseInt(input[i-1]);
        }

        for(int i=0;i<=M;i++) {
            dp[i] = -1;
        }
    }
}
