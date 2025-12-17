package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
이동하기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	32653	19496	14065	58.798%
문제
준규는 N×M 크기의 미로에 갇혀있다. 미로는 1×1크기의 방으로 나누어져 있고, 각 방에는 사탕이 놓여져 있다. 미로의 가장 왼쪽 윗 방은 (1, 1)이고, 가장 오른쪽 아랫 방은 (N, M)이다.

준규는 현재 (1, 1)에 있고, (N, M)으로 이동하려고 한다. 준규가 (r, c)에 있으면, (r+1, c), (r, c+1), (r+1, c+1)로 이동할 수 있고, 각 방을 방문할 때마다 방에 놓여져있는 사탕을 모두 가져갈 수 있다. 또, 미로 밖으로 나갈 수는 없다.

준규가 (N, M)으로 이동할 때, 가져올 수 있는 사탕 개수의 최댓값을 구하시오.

입력
첫째 줄에 미로의 크기 N, M이 주어진다. (1 ≤ N, M ≤ 1,000)

둘째 줄부터 N개 줄에는 총 M개의 숫자가 주어지며, r번째 줄의 c번째 수는 (r, c)에 놓여져 있는 사탕의 개수이다. 사탕의 개수는 0보다 크거나 같고, 100보다 작거나 같다.

출력
첫째 줄에 준규가 (N, M)으로 이동할 때, 가져올 수 있는 사탕 개수를 출력한다.

예제 입력 1
3 4
1 2 3 4
0 0 0 5
9 8 7 6
예제 출력 1
31
예제 입력 2
3 3
1 0 0
0 1 0
0 0 1
예제 출력 2
3
예제 입력 3
4 3
1 2 3
6 5 4
7 8 9
12 11 10
예제 출력 3
47
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: tony9402
알고리즘 분류
다이나믹 프로그래밍
 */
/*
top-down 방식으로 dp를 활용하여 문제를 해결하였다.
(N,M)에서 가질 수 있는 최대 사탕의 개수는 dp[n][m] = miro[n][m] + Max(dp[n-1][m],dp[n][m-1],dp[n-1][m-1])이다.
따라서, dp[n-1][m] = dfs(n-1,m) | dp[n][m-1] = dfs(n,m-1) | dp[n-1][m-1] = dfs(n-1,m-1)이므로 재귀형태로 (1,1)에 도달 시,
최종적으로 가져올 수 있는 사탕의 개수를 반환한다.

90% 시간초과 발생 이유 : dp[r][c]의 값이 0이 될 수도 있으므로, default 값을 -1로 설정하고, if(dp[room.r][room.c] >= 0) return dp[room.r][room.c];
코드를 수정했다.
 */
public class BOJ11048 {
    static class BOJ11048_room {
        int r,c;

        BOJ11048_room(int r,int c) {
            this.r = r;
            this.c = c;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static int[][] miro,dp;
    static int[][] direction = {{1,0},{0,1},{1,1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        System.out.println(dfs(new BOJ11048_room(N,M)));
    }

    static int dfs_2(BOJ11048_room room) {
        if(room.r == 1 && room.c == 1) {
            return miro[1][1];
        }

        if(room.r < 1 || room.c < 1) return 0;

        if(dp[room.r][room.c] >= 0) return dp[room.r][room.c];

        dp[room.r][room.c] = miro[room.r][room.c] + Math.max(Math.max(
                dfs(new BOJ11048_room(room.r - 1, room.c)),
                dfs(new BOJ11048_room(room.r, room.c - 1)))
                ,dfs(new BOJ11048_room(room.r - 1, room.c - 1)));

        return dp[room.r][room.c];
    }

    static int dfs(BOJ11048_room room) {
        if(room.r == 1 && room.c == 1) {
            return miro[1][1];
        }

        if(dp[room.r][room.c] >= 0) return dp[room.r][room.c];

        int candy = 0;
        for(int[] d : direction) {
            int nr = room.r - d[0];
            int nc = room.c - d[1];

            if(nr < 1 || nr > N || nc < 1 || nc > M) continue;
            candy = Math.max(candy, dfs(new BOJ11048_room(nr,nc)));
        }
        dp[room.r][room.c] = miro[room.r][room.c] + candy;

        return dp[room.r][room.c];
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        miro = new int[N+1][M+1];
        dp = new int[N+1][M+1];

        for(int r=1;r<=N;r++) {
            input = br.readLine().split(" ");
            for(int c=1;c<=M;c++) {
                miro[r][c] = Integer.parseInt(input[c-1]);
                dp[r][c] = -1;
            }
        }
    }
}
