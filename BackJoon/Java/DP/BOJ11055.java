package DP;/*
가장 큰 증가 부분 수열

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	37725	17139	13622	45.107%
문제
수열 A가 주어졌을 때, 그 수열의 증가 부분 수열 중에서 합이 가장 큰 것을 구하는 프로그램을 작성하시오.

예를 들어, 수열 A = {1, 100, 2, 50, 60, 3, 5, 6, 7, 8} 인 경우에 합이 가장 큰 증가 부분 수열은 A = {1, 100, 2, 50, 60, 3, 5, 6, 7, 8} 이고, 합은 113이다.

입력
첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000)이 주어진다.

둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (1 ≤ Ai ≤ 1,000)

출력
첫째 줄에 수열 A의 합이 가장 큰 증가 부분 수열의 합을 출력한다.

예제 입력 1
10
1 100 2 50 60 3 5 6 7 8
예제 출력 1
113
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ11055 {
    static int N;
    static int[] mem;
    static int[] A;
    static int max = Integer.MIN_VALUE;
    static int[] tmp;
    //length : LIS
    static int[] length;
    static int[] LIS_max;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        A = Arrays.asList(br.readLine().split(" "))
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        mem = new int[N];
        Arrays.fill(mem,0);

        tmp = new int[N];
        //dfs(0);

        length = new int[N];
        LIS_max = new int[N];

        dp(N);
    }

    /*
        LIS code와 logic은 같다.
     */
    static void dp(int n) {
        for(int i=0;i<n;i++) {
            LIS_max[i] = A[i];
        }
        //n == 1
        max = LIS_max[0];

        for(int i=1;i<n;i++) {
            for(int j=i-1;j>=0;j--) {
                if(A[i] > A[j]) {
                    LIS_max[i] = Math.max(LIS_max[i], LIS_max[j] + A[i]);
                }
            }
            max = Math.max(LIS_max[i],max);
        }
        System.out.println(max);
    }

    static void dfs_2(int n, String str) {
        if(n == N) {
            if(!str.equals("")) {
                System.out.println(str);
                return;
            }
        }
        dfs_2(n+1,str+Integer.toString(A[n]));
        dfs_2(n+1,str);


    }
    static void dfs(int n) {
        if(n == N) {
            int m = 0;
            for(int i=0;i<tmp.length;i++) {
                if(tmp[i] != 0) {
                    System.out.print(tmp[i] + " ");
                }
            }
            System.out.println();
            return;
        }
        tmp[n] = A[n];
        dfs(n+1);
        tmp[n] = 0;
        dfs(n+1);

    }
}
