package DP;

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
/*
알고리즘 핵심
DP
1. dp : 1-문자열 길이, 2-S[i] < S[j]를 만족하는 쌍의 개수, 3-'A'의 개수, 4-'B'의 개수 를 나타내는 배열로 메모리제이션 배열을 사용한다.
2. 이전 문자열의 순서와 상관없이 앞에서 오는 문자에 따라 문자열의 쌍의 갯수가 정해진다.
    ... A가 오는 경우, pair = pair
    ... B가 오는 경우, pair = pair + A의 개수
    ... C가 오는 경우, pair = pair + A + B의 개수
3. 문자열의 길이와 pair의 개수, A의 개수, B의 개수를 이미 도달한 경우는 최초로 도달하여 최대 pair 개수를 만족하는 경우를 수행한 경우이므로 재방문 시 return하여
중복 과정을 피한다.
4. dp 업데이트를 dfs 재귀호출 뒤에 위치하여 이전에 추가된 문자열이후 새로 A-B-C 순으로 문자열을 추가하는 dfs를 호출하여 N개의 문자열을 만족하는지와 pair의 개수가 K개를 만족해야 한다.

처음 문제를 읽고 bruteforce 방식으로 코드를 구현하여 중복되는 과정이 무엇인지 확인하려고 하였다.
그 과정에서 A,B,C를 추가하였을 때 이전에 추가된 문자를 확인하는 과정에서 '순서'와는 상관없이 A,B 문자의 개수만이 영향이 있는 것을 확인하였고
이를 이용하여 dp[문자열의 길이][S[i] < S[j]를 만족하는 쌍의 개수][A의 개수][B의 개수][C의 개수]인 5차원 배열을 사용하였지만 해당 방법은
메모리 초과가 발생하였다.

원인을 생각해봐도 알 수 없어서 다른 정답 코드를 참고하여 원인을 알게되었다.

원인으로 pair를 구성하는 과정에서 C의 개수는 불필요하다는 것으로 즉, dp의 5차원이 원인이였다.

따라서, 5차원 배열에서 C의 개수를 저장하는 차원을 줄여 4차원 배열로 만들어 해결할 수 있었다.

접근 방법은 맞았지만 구현하는 과정에서 어려움이 있었다.

참고 - https://kangminjun.tistory.com/entry/BOJ-12969%EB%B2%88-ABC

 */
public class BOJ12969 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,K;
    static char[] alphabets = {'A','B','C'}, ans;
    static boolean flag;
    static int[][][][] dp;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        init_setting();

        // 모든 경우의 문자열에서 조건을 만족하는 경우에서 가장 큰 결과를 반환하므로 옳은 방법이지만 시간초과 발생 가능 = 3^30
        //solve_brute();

        solve();
    }

    private static void solve() {
        dfs(0,0,0,0);

        if(flag) {
            System.out.println("-1");
        } else {
            System.out.println(ans);
        }
    }

    private static void dfs(int n, int k, int a, int b) {
        if(n == N) {
            if(k == K) {
                flag = false;
            }
            return;
        }

        if(dp[n][k][a][b] != 0) return;

        for(int i = 0; i < 3 && flag; i++) {
            ans[n] = alphabets[i];
            switch (i) {
                case 0:
                    dfs(n + 1, k,a + 1, b);
                    dp[n + 1][k][a + 1][b] = k + dp[n][k][a][b];
                    break;
                case 1:
                    dfs(n + 1, k + a, a, b + 1);
                    dp[n + 1][k + a][a][b + 1] = k + dp[n][k][a][b] + a;
                    break;
                case 2:
                    dfs(n + 1, k + a + b, a, b);
                    dp[n + 1][k + a + b][a][b] = k + dp[n][k][a][b] + a + b;
                    break;
            }
        }
    }

    private static void solve_brute() {
        dfs_brute(0,new char[N]);

        if(flag) System.out.print("-1");
    }

    private static void dfs_brute(int depth, char[] arr) {
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
            dfs_brute(depth + 1, arr);
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);

        flag = true;

        ans = new char[N];
        visited = new boolean[N*(N-1)/2 + 1];
        dp = new int[N + 1][N*(N-1)/2 + 1][N + 1][N + 1];
    }
}
