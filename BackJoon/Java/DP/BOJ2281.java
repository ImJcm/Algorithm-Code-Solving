package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
데스노트

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	4173	1669	1175	42.511%
문제
사악한 라이토는 기발한 방법을 이용하여 L(애칭 섊)을 살해한 뒤 데스노트를 다시 손에 넣었다. 라이토는 이제 이 노트에 n명의 이름을 적어 넣으려고 한다. 이때 다음과 같은 조건을 만족시키면서 이름을 적어 넣으려 한다.

우선, 이름을 적어 넣을 때 이미 정해진 순서대로 n명의 이름을 적어 넣어야 한다. 이름을 적을 때도, 노트를 위에서 아래로, 같은 줄에서는 왼쪽 맨 끝에서 오른쪽으로 차례로 적는다고 하자. 또한 이름을 적을 때 각 사람의 이름 사이에 빈 칸을 하나씩 두려고 한다. 한 줄을 적다가 그 줄의 끝에 한 사람의 이름이 다 들어가지 않고 잘리게 되면 반드시 새로운 줄에 이름을 써야 한다. 그렇지 않으면 이름이 중간에 잘려서 자칫하면 두 명의 사람이 죽게 된다. 이때, 각 줄의 끝에 사용하지 않고 남게 되는 칸의 수의 제곱의 합이 최소가 되도록 하려 한다. 이를 계산할 때 제일 마지막 줄은 앞으로 이름을 적을 기회가 있으므로 계산하지 않는다. 예를 들어 노트의 폭(너비)이 20인 다음의 경우를 보자.



각 사람의 이름의 길이가 차례로 7, 4, 2, 3, 2, 5, 1, 12, 7, 5, 6 인 경우이다. 위와 같이 적으면 차례로 1, 10, 0칸이 남아서 제곱의 합이 101이 된다. 반면에 아래의 경우에는 5, 6, 0칸이 남아서 제곱의 합이 61이 된다.

입력
첫째 줄에 n(1 ≤ n ≤ 1,000), m(1 ≤ m ≤ 1,000)이 주어진다. m은 노트의 가로 칸의 개수(폭, 너비)이다. 다음 n개의 줄에는 각 사람의 이름의 길이가 노트에 적어야 할 순서대로 주어진다. 각 길이는 m을 넘지 않는 자연수이다.

출력
첫째 줄에 남게 되는 칸 수의 제곱의 합의 최솟값을 출력한다.

예제 입력 1
11 20
7
4
2
3
2
5
1
12
7
5
6
예제 출력 1
61
출처
데이터를 추가한 사람: djm03178
문제의 오타를 찾은 사람: gumgood
알고리즘 분류
다이나믹 프로그래밍
 */
/*
알고리즘 핵심
DP
첫번째 접근 dp로 [이름 순서 값][이름이 작성된 줄 번호][남은 폭 너비]의 3차원 배열을 사용하여 메모리제이션 배열을 구성하였지만 메모리 초과 발생
두번째 접근 dp로 [이름 순서 값][남은 폭 너비]로 구성하여 60%의 성공 이후 시간초과 발생까지 도달 - 여기서 도저히 해결방법이 생각나지 않아서 다른 정답 코드를 참고하였다.
세번째 접근 두번째 접근에서 dp의 값이 0이 될 수 있다는 것을 생각하지 못하여 실패한 것을 인지하고 초기값을 -1로 설정한 후 성공

dp를 사용하여 논리적으로 기능은 정상적이지만 60%에서 시간초과가 발생한 이유로 메모리제이션이 작동하지 않는다는 것은 짐작했지만 왜 그런지 이유를 몰랐다.
해결
-> 메모리제이션의 결과값이 0이 될 수 있다는 점을 간과하여 메모리제이션의 값이 0으로 저장될 경우 dp[n][m]을 반환하지 못해 시간초과가 발생한 것이다.
따라서 ,dp[n][m]의 초기값을 -1로 초기화한 후, 다음 개행부터 이름 적기를 시작하는 dfs_dp() 값을 초기값으로 저장한 후 이름 작성이 같은 줄에 가능한 경우와 값을 비교하여
최소값을 dp[n][m]에 저장한다.
 */
public class BOJ2281 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,ans;
    static int[] names;
    //static int[][][] dp;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        ans = dfs_dp(0,M + 1); // time out - 60%
        // ans = dfs_dp(0,0,M + 1); // memory dump

        System.out.println(ans);
    }

    private static int dfs_dp(int n, int m) {
        if (n == N) return 0;

        if (dp[n][m] != -1) return dp[n][m];

        dp[n][m] = dfs_dp(n + 1,(M + 1) - (names[n] + 1)) + (m * m);

        if(m >= names[n] + 1) {
            dp[n][m] = Math.min(dp[n][m], dfs_dp(n + 1, m - (names[n] + 1)));
        }

        return dp[n][m];
    }

    // Failure - time out (60%)
    /*private static int dfs_dp(int n, int m) {
        if (n == N) {
            return 0;
        }

        if (dp[n][m] != 0) return dp[n][m];

        int append_name = Integer.MAX_VALUE;
        int new_line_name = Integer.MAX_VALUE;

        if(m >= names[n] + 1) {
            append_name = dfs_dp(n + 1, m - (names[n] + 1));
        }

        new_line_name = dfs_dp(n + 1,(M + 1) - (names[n] + 1)) + (int) Math.pow(m,2);

        dp[n][m] = Math.min(append_name, new_line_name);

        return dp[n][m];
    }*/

    /*
    // Failure - Memory Dump
    private static int dfs_dp(int n, int l, int m) {
        if (n == N) {
            return 0;
        }

        if (dp[n][l][m] != 0) return dp[n][l][m];

        int append_name = Integer.MAX_VALUE;
        int new_line_name = Integer.MAX_VALUE;

        if(m >= names[n] + 1) {
            append_name = dfs_dp(n + 1, l, m - (names[n] + 1));
        }

        new_line_name = dfs_dp(n + 1, l + 1, (M + 1) - (names[n] + 1)) + (int) Math.pow(m,2);

        dp[n][l][m] = Math.min(append_name, new_line_name);

        return dp[n][l][m];
    }*/

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        names = new int[N];

        for(int i = 0; i < N; i++) {
            names[i] = Integer.parseInt(br.readLine());
        }

        //dp = new int[N + 1][N + 1][M + 2];
        dp = new int[N + 1][M + 2];

        for(int i = 0; i < N + 1; i++) {
            Arrays.fill(dp[i], - 1);
        }
    }
}
