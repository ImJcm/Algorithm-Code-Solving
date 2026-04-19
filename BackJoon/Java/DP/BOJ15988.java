package DP;/*
1, 2, 3 더하기 3

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초 (추가 시간 없음)	512 MB	21380	7823	5926	34.935%
문제
정수 4를 1, 2, 3의 합으로 나타내는 방법은 총 7가지가 있다. 합을 나타낼 때는 수를 1개 이상 사용해야 한다.

1+1+1+1
1+1+2
1+2+1
2+1+1
2+2
1+3
3+1
정수 n이 주어졌을 때, n을 1, 2, 3의 합으로 나타내는 방법의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 테스트 케이스의 개수 T가 주어진다. 각 테스트 케이스는 한 줄로 이루어져 있고, 정수 n이 주어진다. n은 양수이며 1,000,000보다 작거나 같다.

출력
각 테스트 케이스마다, n을 1, 2, 3의 합으로 나타내는 방법의 수를 1,000,000,009로 나눈 나머지를 출력한다.

예제 입력 1
3
4
7
10
예제 출력 1
7
44
274
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class BOJ15988 {
    static int N;
    static long[] mem;
    static int mod = 1000000009;
    static int[] list;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());

        mem = new long[1000001];
        Arrays.fill(mem,0);
        mem[1] = 1; //1
        mem[2] = 2; //1+1, 2
        mem[3] = 4; //1+1+1, 1+2, 2+1, 3

        //dp - timeover
//        for(int i=0;i<N;i++) {
//            int subN = Integer.parseInt(br.readLine());
//            long tmp = dp(subN);
//            sb.append(tmp).append("\n");
//        }
//        System.out.println(sb.toString());

        //dp - 큰값을 한번만 계산하고, mem[n]값들을 출력하는 형태도 시간초과
//        int maxidx = Integer.MIN_VALUE;
//        list = new int[N];
//        for(int i=0;i<N;i++) {
//            int subN = Integer.parseInt(br.readLine());
//            maxidx = Math.max(maxidx, subN);
//            list[i] = subN;
//        }
//
//        dp(maxidx);
//
//        for(int i=0;i<N;i++) {
//            sb.append(mem[list[i]]).append("\n");
//        }
//        System.out.println(sb.toString());

        //dp_bt - Success
        int maxidx = Integer.MIN_VALUE;
        list = new int[N];
        for(int i=0;i<N;i++) {
            int subN = Integer.parseInt(br.readLine());
            maxidx = Math.max(maxidx, subN);
            list[i] = subN;
        }

        dp_bt(maxidx);

        for(int i=0;i<N;i++) {
            sb.append(mem[list[i]]).append("\n");
        }
        System.out.println(sb.toString());
    }

    //dp - bottomup
    static void dp_bt(int n) {
        for(int i=4;i<=n;i++) {
            mem[i] = mem[i-1] + mem[i-2] + mem[i-3];
            mem[i] %= mod;
        }
    }

    //dp - topdown
    static long dp(int n) {
        if(n == 0) {
            return 0;
        }

        if(mem[n] != 0) return mem[n];

        for(int i=1;i<=3;i++) {
            if(n - i >= 0) {
                mem[n] += dp(n-i);
            }
        }
        mem[n] %= mod;

        return mem[n];
    }
}
