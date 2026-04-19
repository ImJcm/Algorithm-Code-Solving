package DP;/*
동물원

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	23854	11902	9368	47.977%
문제
어떤 동물원에 가로로 두칸 세로로 N칸인 아래와 같은 우리가 있다.



이 동물원에는 사자들이 살고 있는데 사자들을 우리에 가둘 때, 가로로도 세로로도 붙어 있게 배치할 수는 없다. 이 동물원 조련사는 사자들의 배치 문제 때문에 골머리를 앓고 있다.

동물원 조련사의 머리가 아프지 않도록 우리가 2*N 배열에 사자를 배치하는 경우의 수가 몇 가지인지를 알아내는 프로그램을 작성해 주도록 하자. 사자를 한 마리도 배치하지 않는 경우도 하나의 경우의 수로 친다고 가정한다.

입력
첫째 줄에 우리의 크기 N(1≤N≤100,000)이 주어진다.

출력
첫째 줄에 사자를 배치하는 경우의 수를 9901로 나눈 나머지를 출력하여라.

예제 입력 1
4
예제 출력 1
41
 */
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ1309 {
    static int N;
    static long[][] mem;
    static int mod = 9901;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        //[n][0] : n줄에 사자를 배치안할 경우, [1] : 왼쪽에 배치하는 경우, [2] : 오른쪽에 배치하는 경우
        mem = new long[N+1][3];
        for(int i=1;i<=N;i++) {
            Arrays.fill(mem[i], 1);
        }

        System.out.println(dp(N));

        System.out.println((dp_topdown(N,0) + dp_topdown(N,1) + dp_topdown(N,2)) % mod);
    }

    static long dp(int n) {
        for(int i=2;i<=n;i++) {
            mem[i][0] = (mem[i-1][0] + mem[i-1][1] + mem[i-1][2]);
            mem[i][1] = (mem[i-1][0] + mem[i-1][2]);
            mem[i][2] = (mem[i-1][0] + mem[i-1][1]);

            mem[i][0] %= mod;
            mem[i][1] %= mod;
            mem[i][2] %= mod;
        }

        return (mem[n][0] + mem[n][1] + mem[n][2]) % mod;
    }

    static long dp_topdown(int n, int idx) {
        if(n == 1) {
            return 1;
        }

        if(mem[n][idx] != 1) return mem[n][idx];

        if(idx == 0) {
            mem[n][idx] = dp_topdown(n-1,idx)
                    + dp_topdown(n-1,idx+1)
                    + dp_topdown(n-1,idx+2);
        } else if(idx == 1) {
            mem[n][idx] = dp_topdown(n-1,idx-1) + dp_topdown(n-1,idx+1);
        } else {
            mem[n][idx] = dp_topdown(n-1,idx-2) + dp_topdown(n-1,idx-1);
        }
        mem[n][idx] %= mod;

        return mem[n][idx];
    }
}
