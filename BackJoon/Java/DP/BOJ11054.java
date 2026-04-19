package DP;/*
가장 긴 바이토닉 부분 수열 LIS

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	38118	19454	15199	50.819%
문제
수열 S가 어떤 수 Sk를 기준으로 S1 < S2 < ... Sk-1 < Sk > Sk+1 > ... SN-1 > SN을 만족한다면, 그 수열을 바이토닉 수열이라고 한다.

예를 들어, {10, 20, 30, 25, 20}과 {10, 20, 30, 40}, {50, 40, 25, 10} 은 바이토닉 수열이지만,  {1, 2, 3, 2, 1, 2, 3, 2, 1}과 {10, 20, 30, 40, 20, 30} 은 바이토닉 수열이 아니다.

수열 A가 주어졌을 때, 그 수열의 부분 수열 중 바이토닉 수열이면서 가장 긴 수열의 길이를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 수열 A의 크기 N이 주어지고, 둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (1 ≤ N ≤ 1,000, 1 ≤ Ai ≤ 1,000)

출력
첫째 줄에 수열 A의 부분 수열 중에서 가장 긴 바이토닉 수열의 길이를 출력한다.

예제 입력 1
10
1 5 2 1 4 3 4 5 2 1
예제 출력 1
7
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class BOJ11054 {
    static int N;
    static int[] S;
    static int[] mem_up;
    static int[] mem_down;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        S = Arrays.asList(br.readLine().split(" "))
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        mem_up = new int[N+1];
        mem_down = new int[N+1];
        Arrays.fill(mem_up,1);
        Arrays.fill(mem_down,1);

        dp(N);

        System.out.println(max);

        for(int i=0;i<N;i++) {
            dp_td_up(i);
            //dp_td_down((N-1)-i);
            dp_td_down(i);
        }

        int result = 0;
        for(int i=0;i<N;i++) {
            result = Math.max(result, mem_up[i] + mem_down[N-i] - 1);
        }
        System.out.println(result);


    }

    //index = k일 때, k까지의 가장 긴 증가 부분수열 + k부터 가장 긴 감소 부분수열을 각각 구하는 dp
    //1. start = 0에서 긴 증가 부분수열 + start = N-1에서 긴 증가 부분수열
    static void dp(int n) {
        for(int i=1;i<n;i++) {
            for(int j=i-1;j>=0;j--) {
                //증가 부분수열
                if(S[i] > S[j]) {
                    mem_up[i] = Math.max(mem_up[i], mem_up[j] + 1);
                }
                //감소 부분수열 = 뒤에서부터 앞으로 수행하는 증가부분수열
                if(S[n-(i+1)] > S[n-(j+1)]) {
                    mem_down[n-(i+1)] = Math.max(mem_down[n-(i+1)], mem_down[n-(j+1)] + 1);
                }
            }
//            //감소 부분수열 = index = n-1 ~ 1 부터 시작하는 증가 부분수열
//            for(int k=n-i;k<n;k++) {
//                if(S[n-(i+1)] > S[k]) {
//                    mem_down[n-(i+1)] = Math.max(mem_down[n-(i+1)], mem_down[k] + 1);
//                }
//            }

        }

        for(int i=0;i<n;i++) {
            max = Math.max(max, mem_up[i] + mem_down[i] - 1);
        }
    }

    static int dp_td_up(int n) {
        if(n == 0) {
            return 1;
        }

        if(mem_up[n] != 1) return mem_up[n];

        for(int i=n-1;i>=0;i--) {
            if(S[i] < S[n]) {
                mem_up[n] = Math.max(mem_up[n], dp_td_up(i) + 1);
            }
        }

        return mem_up[n];
    }
    // n을 기준으로 <-방향으로 감소하는 부분수열 : for(int i=n-1;i>=0;i--)
//    static int dp_td_down(int n) {
//        if(n == 0) {
//            return 1;
//        }
//
//        if(mem_down[n] != 1) return mem_down[n];
//
//        for(int i=n-1;i>=0;i--) {
//            if(S[i] < S[n]) {
//                mem_down[n] = Math.max(mem_down[n], dp_td_down(i) + 1);
//            }
//        }
//
//        return mem_down[n];
//    }
    // n을 기준으로 ->방향으로 감소하는 부분수열 : for(int i=n-1;i>=0;i--)
    static int dp_td_down(int n) {
        if(n == 0) {
            return 1;
        }

        if(mem_down[n] != 1) return mem_down[n];

        for(int i=n+1;i<N;i++) {
            if(S[i] < S[n]) {
                mem_down[n] = Math.max(mem_down[n], dp_td_down(i) + 1);
            }
        }

        return mem_down[n];
    }
}
