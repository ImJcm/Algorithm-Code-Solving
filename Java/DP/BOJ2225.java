import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
합분해

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	33803	14916	10887	42.552%
문제
0부터 N까지의 정수 K개를 더해서 그 합이 N이 되는 경우의 수를 구하는 프로그램을 작성하시오.

덧셈의 순서가 바뀐 경우는 다른 경우로 센다(1+2와 2+1은 서로 다른 경우). 또한 한 개의 수를 여러 번 쓸 수도 있다.

입력
첫째 줄에 두 정수 N(1 ≤ N ≤ 200), K(1 ≤ K ≤ 200)가 주어진다.

출력
첫째 줄에 답을 1,000,000,000으로 나눈 나머지를 출력한다.

예제 입력 1
20 2
예제 출력 1
21
예제 입력 2
6 4
예제 출력 2
84
 */
public class BOJ2225 {
    static int N, K;
    static long[][] mem;
    static int mod = 1000000000;
    static int cnt = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        K = Integer.parseInt(str[1]);

        mem = new long[N+1][K+1];

        for(int i=0;i<=N;i++) {
            Arrays.fill(mem[i],0);
            mem[i][1] = 1;
        }

        System.out.println(dp(N,K));

        //dfs
//        dfs(N,K);
//        System.out.println(cnt % mod);
    }

    //DP
    static long dp(int n,int k) {
        if(n == 0 && k == 0) {
            return 0;
        }

        if(mem[n][k] != 0) return mem[n][k];

        for(int i=0;i<=N;i++) {
            if(n >= i && k >= 0) {
                mem[n][k] += dp(n-i,k-1);
            }
        }
        mem[n][k] %= mod;

        return mem[n][k];
    }

    static void dfs(int n, int k) {
        if(n == 0 && k == 0) {
            cnt += 1;
            return;
        }

        for(int i=0;i<=N;i++) {
            if(n >= i && k >= 0) {
                dfs(n - i, k-1);
            }
        }

    }
}
