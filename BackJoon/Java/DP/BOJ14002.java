package DP;/*
가장 긴 증가하는 부분 수열 4 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	26121	10241	7760	39.371%
문제
수열 A가 주어졌을 때, 가장 긴 증가하는 부분 수열을 구하는 프로그램을 작성하시오.

예를 들어, 수열 A = {10, 20, 10, 30, 20, 50} 인 경우에 가장 긴 증가하는 부분 수열은 A = {10, 20, 10, 30, 20, 50} 이고, 길이는 4이다.

입력
첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000)이 주어진다.

둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (1 ≤ Ai ≤ 1,000)

출력
첫째 줄에 수열 A의 가장 긴 증가하는 부분 수열의 길이를 출력한다.

둘째 줄에는 가장 긴 증가하는 부분 수열을 출력한다. 그러한 수열이 여러가지인 경우 아무거나 출력한다.

예제 입력 1
6
10 20 10 30 20 50
예제 출력 1
4
10 20 30 50
 */
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Arrays;

public class BOJ14002 {
    static int N;
    static int[] A;
    static int max = 1;
    static int[] mem;
    //최대길이 부분수열을 구성하는 이전 idx
    static int maxpreidx;
    static int[] preidx_mem;
    static String s = "";

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        A = Arrays.asList(br.readLine().split(" "))
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        mem = new int[N];
        Arrays.fill(mem,1);

        preidx_mem = new int[N];
        Arrays.fill(preidx_mem,0);

        System.out.println(dp(N));

        s = A[maxpreidx] + s;
        for(int i=0;i<max-1;i++) {
            s = " " + s;    //s += " "; -> s = s + " ";
            s = Integer.toString(A[preidx_mem[maxpreidx]]) + s;
            maxpreidx = preidx_mem[maxpreidx];
        }

        System.out.println(s);
    }

    static int dp(int n) {
        for(int i=1;i<n;i++) {
//            j = i-1부터 시작하는 반복문은 최대길이 부분수열의 이전 idx를 저장하지 못한다.
//            따라서, j = 0 부터 시작하도록하여, i와 가깝고, 이전 부분수열의 최대값을 가지는 idx를 저장하게한다.
//            for(int j=i-1;j>=0;j--) {
            for(int j=0;j<i;j++) {
                if(A[i] > A[j] && mem[i] < mem[j] + 1) {
                    mem[i] = mem[j] + 1;
                    preidx_mem[i] = j;
                }
//                if(A[i] > A[j]) {
//                    if(mem[i] < mem[j] + 1) {
//                    mem[i] = Math.max(mem[i], mem[j] + 1);
//                    preidx_mem[i] = j;
//                    }
//                }
            }
            //
            if(max < mem[i]) {
                max = mem[i];
                maxpreidx = i;
            }
        }
        return max;
    }
}
