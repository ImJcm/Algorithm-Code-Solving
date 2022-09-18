/*
연속합

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초 (추가 시간 없음)	128 MB	109115	38630	27120	34.128%
문제
n개의 정수로 이루어진 임의의 수열이 주어진다. 우리는 이 중 연속된 몇 개의 수를 선택해서 구할 수 있는 합 중 가장 큰 합을 구하려고 한다. 단, 수는 한 개 이상 선택해야 한다.

예를 들어서 10, -4, 3, 1, 5, 6, -35, 12, 21, -1 이라는 수열이 주어졌다고 하자. 여기서 정답은 12+21인 33이 정답이 된다.

입력
첫째 줄에 정수 n(1 ≤ n ≤ 100,000)이 주어지고 둘째 줄에는 n개의 정수로 이루어진 수열이 주어진다. 수는 -1,000보다 크거나 같고, 1,000보다 작거나 같은 정수이다.

출력
첫째 줄에 답을 출력한다.

예제 입력 1
10
10 -4 3 1 5 6 -35 12 21 -1
예제 출력 1
33
예제 입력 2
10
2 1 -4 3 4 -4 6 5 -5 1
예제 출력 2
14
예제 입력 3
5
-1 -2 -3 -4 -5
예제 출력 3
-1
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ1912 {
    static int N;
    static int[] A;
    static int[] mem;
    static int[] mem2;
    static int max = Integer.MIN_VALUE;
    static int max2 = Integer.MIN_VALUE;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        A = Arrays.asList(br.readLine().split(" "))
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        int sum = 0;
        int best = Integer.MIN_VALUE;

        //O(N)
        for(int i=0;i<N;i++) {
            sum = Math.max(A[i], sum + A[i]);
            best = Math.max(best,sum);
        }
        System.out.println(best);

        //O(N^2) - 시간초과
        int sum2 = 0;
        int best2 = Integer.MIN_VALUE;
        for(int i=0;i<N;i++) {
            sum2 = 0;
            for(int j=i;j<N;j++) {
                sum2 += A[j];
                best2 = Math.max(sum2, best2);
            }
        }
        System.out.println(best2);


        //O(N^3) - 시간초과
        int best3 = Integer.MIN_VALUE;
        int sum3 = 0;
        for(int i=0;i<N;i++) {
            for(int j=i;j<N;j++) {
                sum3 = 0;
                for(int k=i;k<=j;k++) {
                    sum3 += A[k];
                }
                best3 = Math.max(best3, sum3);
            }
        }
        System.out.println(best3);

        //dp - topdown
        mem = new int[N];
        mem[0] = max = A[0];

        dp(1);

        //dp - bottomup
        mem2 = new int[N];
        mem2[0] = max2 = A[0];

        bt_dp(N);

    }

    //topdown
    static void dp(int n) {
        if(n == N) {
            System.out.println(max);
            return;
        }

        //if(mem[n-1] + A[n] < A[n]) {
        if(mem[n-1]  < 0) {
            mem[n] = A[n];
        } else {
            mem[n] = A[n] + mem[n-1];
        }

        max = Math.max(max, mem[n]);
        dp(n+1);
    }

    //bottomup
    static void bt_dp(int n) {
        for(int i=1;i<n;i++) {
            mem2[i] = Math.max(mem2[i-1] + A[i], A[i]);
            max2 = Math.max(max2, mem2[i]);
        }

        System.out.println(max2);
    }

}
