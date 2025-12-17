package DP;/*
오르막 수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	41645	20309	15675	47.553%
문제
오르막 수는 수의 자리가 오름차순을 이루는 수를 말한다. 이때, 인접한 수가 같아도 오름차순으로 친다.

예를 들어, 2234와 3678, 11119는 오르막 수이지만, 2232, 3676, 91111은 오르막 수가 아니다.

수의 길이 N이 주어졌을 때, 오르막 수의 개수를 구하는 프로그램을 작성하시오. 수는 0으로 시작할 수 있다.

입력
첫째 줄에 N (1 ≤ N ≤ 1,000)이 주어진다.

출력
첫째 줄에 길이가 N인 오르막 수의 개수를 10,007로 나눈 나머지를 출력한다.

예제 입력 1
1
예제 출력 1
10
예제 입력 2
2
예제 출력 2
55
예제 입력 3
3
예제 출력 3
220
 */
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ11057 {
    static int N;
    static long[][] mem;
    static int mod = 10007;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        mem = new long[N+1][10];

        for(int i=0;i<=N;i++) {
            Arrays.fill(mem[i],0);
        }
        for(int i=0;i<10;i++) {
            mem[1][i] = 1;
        }

        System.out.println(dp(N));

        long result = 0;
        for(int i=0;i<10;i++) {
            result += dp_topdown(N,i);
        }
        System.out.println(result % mod);
    }

    static long dp(int n) {
        for(int i=2;i<=n;i++) {
            for(int j=0;j<10;j++) {
                for(int k=j;k<10;k++) {
                    mem[i][j] += mem[i-1][k];
                }
                mem[i][j] %= mod;
            }
        }

        long result = 0;
        for(int i=0;i<10;i++) {
            result += mem[n][i];
        }
        return result % mod;
    }

    static long dp_topdown(int n,int idx) {
        if(n == 0) {
            return 0;
        }

        if(mem[n][idx] != 0) return mem[n][idx];

        for(int i=0;i<10;i++) {
            if(i < idx) continue;
            mem[n][idx] += dp_topdown(n-1,i);
        }
        mem[n][idx] %= mod;

        return mem[n][idx];
    }
}
