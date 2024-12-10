package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
BOJ 거리

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	3818	2300	1916	62.067%
문제
BOJ 거리는 보도블록 N개가 일렬로 놓여진 형태의 도로이다. 도로의 보도블록은 1번부터 N번까지 번호가 매겨져 있다.

스타트의 집은 1번에 있고, 링크의 집은 N번에 있다. 스타트는 링크를 만나기 위해서 점프해가려고 한다.

BOJ거리의 각 보도블록에는 B, O, J 중에 하나가 쓰여 있다. 1번은 반드시 B이다.

스타트는 점프를 통해서 다른 보도블록으로 이동할 수 있다. 이때, 항상 번호가 증가하는 방향으로 점프를 해야 한다. 만약, 스타트가 현재 있는 곳이 i번이라면, i+1번부터 N번까지로 점프를 할 수 있다. 한 번 k칸 만큼 점프를 하는데 필요한 에너지의 양은 k*k이다.

스타트는 BOJ를 외치면서 링크를 만나러 가려고 한다. 따라서, 스타트는 B, O, J, B, O, J, B, O, J, ... 순서로 보도블록을 밟으면서 점프를 할 것이다.

스타트가 링크를 만나는데 필요한 에너지 양의 최솟값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 1 ≤ N ≤ 1,000이 주어진다.

둘째 줄에는 보도블록에 쓰여 있는 글자가 1번부터 순서대로 주어진다.

출력
스타트가 링크를 만나는데 필요한 에너지 양의 최솟값을 출력한다. 만약, 스타트가 링크를 만날 수 없는 경우에는 -1을 출력한다.

예제 입력 1
9
BOJBOJBOJ
예제 출력 1
8
예제 입력 2
9
BOJBOJBOJ
예제 출력 2
8
예제 입력 3
8
BJJOOOBB
예제 출력 3
-1
예제 입력 4
13
BJBBJOOOJJJJB
예제 출력 4
50
예제 입력 5
2
BO
예제 출력 5
1
예제 입력 6
15
BJBOJOJOOJOBOOO
예제 출력 6
52
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: djm03178
문제의 오타를 찾은 사람: doju, jh05013
알고리즘 분류
다이나믹 프로그래밍
 */
/*
알고리즘 핵심
DP
1. 시작 지점과 끝 지점을 나타내는 2차원 배열의 DP 배열을 사용한다.
2. start - end 지점 사이에 점프가 불가능한 것을 표현하기 위해 e 변수에 Integer.MAX_VALUE를 설정하여 여부를 결정하였다.
3. start 지점에서의 문자를 기준으로 N개의 문자열까지 다음 점프 지점으로 가능한 위치를 모두 고른 후, dfs_dp의 시작지점을 업데이트하여 호출한다.
4. dfs_dp의 호출결과 Integer.MAX_VALUE의 경우 start에서 end까지 점프 가능한 구간이 없는 것으로 판단하여 다른 점프 구간으로 넘어간다.
5. start == end 의 경우 더 이상 점프할 구간이 없으므로 return 0을 호출한다.
6. start - end 구간에서 점프하는 구간이 존재하는 경우 e, r(= dfs_dp(start,end)) + (i - start)^2인 값에서 최소인 값으로 dp[start][end]을 업데이트한다.
7. dfs_dp(0,N - 1) 구간을 호출하여 반환된 값을 구분하여 값을 출력한다.
 */
public class BOJ12026 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static int[][] dp;
    static char[] blocks;


    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        //dfs(0,0,0);

        //System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);

        ans = dfs_dp(0,N - 1);

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    private static int dfs_dp(int start, int end) {
        if(start == end) {
            return 0;
        }

        if(dp[start][end] != 0) return dp[start][end];

        int next_target = blocks[start] == 'B' ? 1 : blocks[start] == 'O' ? 2 : 0;
        int e = Integer.MAX_VALUE;

        for(int i = start + 1; i < N; i++) {
            int now = blocks[i] == 'B' ? 0 : blocks[i] == 'O' ? 1 : 2;

            if(now != next_target) continue;

            int r = dfs_dp(i,end);

            if(r == Integer.MAX_VALUE) continue;

            e = Math.min(e, r + (int) Math.pow(i - start,2));
        }

        dp[start][end] = e;

        return dp[start][end];
    }

    private static void dfs(int depth, int now_target, int total_energy) {
        if(depth == N - 1) {
            ans = Math.min(ans, total_energy);
            return;
        }

        int next_target = (now_target + 1) % 3; // B = 0, O = 1, J = 2

        for(int i = depth + 1; i < N; i++) {
            int now = blocks[i] == 'B' ? 0 : blocks[i] == 'O' ? 1 : 2;

            if(now != next_target) continue;

            int e = (int) Math.pow(i - depth,2);
            dfs(i,next_target,total_energy + e);
        }
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        blocks = br.readLine().toCharArray();

        ans = Integer.MAX_VALUE;

        dp = new int[N][N];
    }
}
