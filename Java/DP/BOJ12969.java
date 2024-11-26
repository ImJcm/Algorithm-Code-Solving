package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
ABC 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	2195	1092	780	48.267%
문제
정수 N과 K가 주어졌을 때, 다음 두 조건을 만족하는 문자열 S를 찾는 프로그램을 작성하시오.

문자열 S의 길이는 N이고, 'A', 'B', 'C'로 이루어져 있다.
문자열 S에는 0 ≤ i < j < N 이면서 S[i] < S[j]를 만족하는 (i, j) 쌍이 K개가 있다.
입력
첫째 줄에 N과 K가 주어진다. (3 ≤ N ≤ 30, 0 ≤ K ≤ N(N-1)/2)

출력
첫째 줄에 문제의 조건을 만족하는 문자열 S를 출력한다. 가능한 S가 여러 가지라면, 아무거나 출력한다. 만약, 그러한 S가 존재하지 않는 경우에는 -1을 출력한다.

예제 입력 1
3 3
예제 출력 1
ABC
예제 입력 2
3 0
예제 출력 2
CBA
예제 입력 3
5 10
예제 출력 3
-1
예제 입력 4
15 36
예제 출력 4
CABBACCBAABCBBB
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
다이나믹 프로그래밍
 */
public class BOJ12969 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,K;
    static char[] alphabets = {'A','B','C'};
    static boolean flag;
    //static int[][][][] dp;
    //static boolean[] visited;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve_brute();

        //solve();
    }

    /*private static void solve() {
        dfs(0,0,0,0,0);


    }

    private static void dfs(int n, int k, int a, int b, int c) {
        if(n == N) {
            return;
        }

        for(int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    if(visited[k]) continue;
                    dp[k][a + 1][b][c] = dp[k][a][b][c];
                    visited[k] = true;
                    dfs(n + 1, k,a + 1, b, c);
                    break;
                case 1:
                    if(visited[k + a]) continue;
                    dp[k + a][a][b + 1][c] = dp[k][a][b][c] + a;
                    visited[k + a] = true;
                    dfs(n + 1, k + a, a, b + 1, c);
                    break;
                case 2:
                    if(visited[k + a + b]) continue;
                    dp[k + a + b][a][b][c + 1] = dp[k][a][b][c] + a + b;
                    visited[k + a + b] = true;
                    dfs(n + 1, k + a + b, a, b, c + 1);
                    break;
            }
        }
    }*/

    private static void solve_brute() {
        dfs_2(0,new char[N]);

        if(flag) System.out.print("-1");
    }

    private static void dfs_2(int depth, char[] arr) {
        if(!flag)  return;
        if(depth == N) {
            int cnt = 0;

            for(int i = N - 1; i > 0 ; i--) {
                for(int j = i - 1; j >= 0; j--) {
                    if(Character.compare(arr[i],arr[j]) > 0) cnt++;
                }
            }

            if(cnt == K) {
                flag = false;
                for(char i : arr) {
                    System.out.print(i);
                }
            }

            return;
        }

        for(int i = 0; i < 3; i++) {
            arr[depth] = alphabets[i];
            dfs_2(depth + 1, arr);
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);

        flag = true;

        //visited = new boolean[K + 1];
        //dp = new int[K + 1][N][N][N];
    }
}
