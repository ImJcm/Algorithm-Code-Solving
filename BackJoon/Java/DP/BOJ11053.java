package DP;/*
가장 긴 증가하는 부분 수열

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	114414	45021	29682	37.344%
문제
수열 A가 주어졌을 때, 가장 긴 증가하는 부분 수열을 구하는 프로그램을 작성하시오.

예를 들어, 수열 A = {10, 20, 10, 30, 20, 50} 인 경우에 가장 긴 증가하는 부분 수열은 A = {10, 20, 10, 30, 20, 50} 이고, 길이는 4이다.

입력
첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000)이 주어진다.

둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (1 ≤ Ai ≤ 1,000)

출력
첫째 줄에 수열 A의 가장 긴 증가하는 부분 수열의 길이를 출력한다.

예제 입력 1
6
10 20 10 30 20 50
예제 출력 1
4
 */
/*
    dfs - 완전 탐색 - 시간초과 N <= 1000,
 */
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Arrays;

public class BOJ11053 {
    static int N;
    static int[] A;
    static int[] mem;
    static int max = 1;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        A = Arrays.asList(br.readLine().split(" "))
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        mem = new int[N];
        Arrays.fill(mem,1);

        System.out.println(dp(N));
    }

    static int dp(int n) {
        for(int i=1;i<n;i++) {
            int tmp = 1;
            for(int j=i-1;j>=0;j--) {
                if(A[i] > A[j]) {
                    tmp = Math.max(tmp, mem[j] + 1);
                }
            }
            //min[n-1]의 값을 출력하는 것이 아닌, 1~n일 때, 가장 긴 부분수열의 길이를 구하는 것이므로
            //i가 증가할 때마다, 최대값을 업데이트 해야한다.
            mem[i] = tmp;
            max = Math.max(mem[i], max);
        }

        return max;
    }
}
