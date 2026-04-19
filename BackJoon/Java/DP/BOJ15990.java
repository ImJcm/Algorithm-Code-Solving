package DP;/*
1, 2, 3 더하기 5

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초 (추가 시간 없음)	512 MB	19619	6670	4621	31.032%
문제
정수 4를 1, 2, 3의 합으로 나타내는 방법은 총 3가지가 있다. 합을 나타낼 때는 수를 1개 이상 사용해야 한다. 단, 같은 수를 두 번 이상 연속해서 사용하면 안 된다.

1+2+1
1+3
3+1
정수 n이 주어졌을 때, n을 1, 2, 3의 합으로 나타내는 방법의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 테스트 케이스의 개수 T가 주어진다. 각 테스트 케이스는 한 줄로 이루어져 있고, 정수 n이 주어진다. n은 양수이며 100,000보다 작거나 같다.

출력
각 테스트 케이스마다, n을 1, 2, 3의 합으로 나타내는 방법의 수를 1,000,000,009로 나눈 나머지를 출력한다.

예제 입력 1
3
4
7
10
예제 출력 1
3
9
27
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class BOJ15990 {
    static int T,N;
    static int[][] mem;
    static int[][] mem2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        T = Integer.parseInt(br.readLine());

        mem = new int[100001][4];

        for(int i=0;i<100001;i++) {
            Arrays.fill(mem[i],0);
        }
        mem2 = new int[100001][4];

        for(int i=0;i<100001;i++) {
            Arrays.fill(mem2[i],0);
        }
        mem2[1][1] = 1;
        mem2[2][2] = 1;
        mem2[3][1] = 1;
        mem2[3][2] = 1;
        mem2[3][3] = 1;

        for (int i = 0; i < T; i++) {
            N = Integer.parseInt(br.readLine());

            System.out.println(dp(N,0));
        }

        for(int j=0;j<T;j++) {
            System.out.println(botup_dp(N));
        }
    }

    /*
        mem[n] 으로만 했을 때, 같은 수를 두 번 이상 연속해서 사용하는 경우의 수 까지 ret에 더해져 답이 나오지 않는다
        따라서, mem[n][pre_idx]로 이전 값에 따라서 경우의 수를 나누어 ret에 적용해야 한다.
    */
    //topdown - dp
    static int dp(int n, int pre_idx) {
        if (n == 0) return 1;
        if (n < 0) return 0;
        if (mem[n][pre_idx] != 0) return mem[n][pre_idx];

        int ret = 0;
        for(int i=1;i<=3;i++) {
            if(pre_idx == i) continue;
            ret = dp(n-i,i) + ret;
            ret %= 1000000009;
        }
        mem[n][pre_idx] += ret;

        return mem[n][pre_idx];
    }

    //bottomup - dp
    static int botup_dp(int n) {
        int ret = 0;
        for(int i=4;i<=n;i++) {
            mem2[i][1] = (mem2[i-1][2] + mem2[i-1][3]) % 1000000009;
            mem2[i][2] = (mem2[i-2][1] + mem2[i-2][3]) % 1000000009;
            mem2[i][3] = (mem2[i-3][1] + mem2[i-3][2]) % 1000000009;
        }

        return (mem2[n][1] + mem2[n][2] + mem2[n][3]) % 1000000009;
    }
}
