package DP;/*
수확

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	508	230	169	47.207%
문제
1 × N 크기의 긴 밭에 벼가 심어져 있다. 준희는 이 벼를 수확 하려고 한다. 그런데 가운데 있는 벼를 수확을 하려면 끝에서 가운데까지 헤집고 들어가야 하므로 양 끝에 있는 벼만 수확을 할 수 있다. 처음에는 첫 번째와 N번째 벼를 수확을 할 수 있을 것이며 만약에 첫 번째 벼를 수확을 하였다면 두 번째 벼와 N번째 벼를 수확을 할 수 있다.

수확을 하였을 때 얻을 수 있는 이익은 다음과 같다. 만약에 그 벼의 가치가 v(i)라고 하고 그 벼를 k번째로 수확을 한다고 하면 v(i) × k 만큼의 이익을 얻게 된다.

만약에 벼의 가치가 차례로 1 3 1 5 2 라고 하고 첫 번째, 다섯 번째, 두 번째, 세 번째, 네 번째의 순서대로 수확을 한다고 하면 1×1 + 2×2 + 3×3 + 4×1 + 5×5 = 43 이 되어 43 만큼의 이익을 얻게 된다. 그리고 이 값이 저 벼로 얻을 수 있는 최대 이익이 된다.

우리가 구해야 할 값은 다음과 같다. 벼의 개수 N과 벼의 가치가 주어져 있을 때, 얻을 수 있는 최대 이익을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 벼의 개수 N(1 ≤ N ≤ 2,000)이 주어지고 두 번째 줄부터 N+1번쨰 줄까지 벼의 가치 v(i) (1 ≤ v(i) ≤ 1,000) 가 주어진다.

출력
첫째 줄에 얻을 수 있는 최대 이익을 출력한다.

예제 입력 1
5
1
3
1
5
2
예제 출력 1
43
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class BOJ1823 {
    static int N;
    static int[] v;
//    static long[] dp_t;
//    static long[] dp_b;
    static int[][] dp_bot;
    static int[][] dp;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        v = new int[N+1];
        dp = new int[N+1][N+1];
        dp_bot = new int[N+1][N+1];
//        dp_t = new long[N+1];
//        dp_b = new long[N+1];
//        Arrays.fill(dp_t,0);
//        Arrays.fill(dp_b,0);
        for(int i=1;i<=N;i++) {
            v[i] = Integer.parseInt(br.readLine());
            Arrays.fill(dp[i],0);
            Arrays.fill(dp_bot[i],0);
        }

        System.out.println(dp_func(1,N));
//        System.out.println(dp_bottomup(N));
//        양 끝에서 작은 값더하는 방법 - 틀림
//        long cost = solve(N);
//        System.out.println(cost);

//        dfs - 시간초과
//        dfs(0,0,N-1,0);
//        System.out.println(max);
    }

    //dp_topdown
    static int dp_func(int s,int e) {
       if(s > e) return 0;

       if(dp[s][e] != 0) return dp[s][e];

       int cost_idx = N - (e - s);
       dp[s][e] = Math.max(dp_func(s+1,e) + cost_idx * v[s],dp_func(s,e-1) + cost_idx * v[e]);

       return dp[s][e];
    }
    static int dp_bottomup(int n) {
        for(int i=1;i<=n;i++) {
            dp_bot[i][i] = n * v[i];
        }

        for(int i=1;i<=n;i++) {
            for(int j=i-1;j>=0;j--) {
                dp_bot[j][i] = Math.max(dp_bot[j+1][i] + v[j] * (n-i+j), dp_bot[j][i-1] + v[i] * (n-i+j));
            }
        }
        return dp_bot[1][n];
    }
    //dfs - 시간초과
   /* static void dfs(int n,int lidx, int ridx, int cost) {
        if(n == N) {
            max = Math.max(cost, max);
            return;
        }

        dfs(n+1,lidx+1,ridx,cost + (n+1) * v[lidx]);
        dfs(n+1,lidx,ridx-1,cost + (n+1) * v[ridx]);
    }*/

    //양 끝에서 작은 값으로 더해가는 방법 - 틀림
   /* static long solve(int n) {
        int lidx1=0,lidx2=0, ridx1=n-1,ridx2=n-1;
        if(v[0] < v[n-1]) {
            dp_t[1] = v[0];
            dp_b[1] = v[0];
            lidx1++;
            lidx2++;
        } else if(v[0] > v[n-1]){
            dp_t[1] = v[n-1];
            dp_b[1] = v[n-1];
            ridx1--;
            ridx2--;
        } else {
            dp_t[1] = v[0];
            lidx1++;
            dp_b[1] = v[n-1];
            ridx2--;
        }

        for(int i=1;i<n;i++) {
            if(lidx1 < ridx1 && v[lidx1] <= v[ridx1]) {
                dp_t[i+1] = (i+1) * v[lidx1++] + dp_t[i];
            } else {
                dp_t[i+1] = (i+1) * v[ridx1--] + dp_t[i];
            }

            if(lidx2 < ridx2 && v[lidx2] < v[ridx2]) {
                dp_b[i+1] = (i+1) * v[lidx2++] + dp_b[i];
            } else {
                dp_b[i+1] = (i+1) * v[ridx2--] + dp_b[i];
            }

        }

        return Math.max(dp_t[n],dp_b[n]);
    }*/
}
