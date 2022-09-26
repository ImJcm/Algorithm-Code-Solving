/*
가장 긴 감소하는 부분 수열

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	24742	15647	12732	64.190%
문제
수열 A가 주어졌을 때, 가장 긴 감소하는 부분 수열을 구하는 프로그램을 작성하시오.

예를 들어, 수열 A = {10, 30, 10, 20, 20, 10} 인 경우에 가장 긴 감소하는 부분 수열은 A = {10, 30, 10, 20, 20, 10}  이고, 길이는 3이다.

입력
첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000)이 주어진다.

둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (1 ≤ Ai ≤ 1,000)

출력
첫째 줄에 수열 A의 가장 긴 감소하는 부분 수열의 길이를 출력한다.

예제 입력 1
6
10 30 10 20 20 10
예제 출력 1
3
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class BOJ11722 {
    static int N;
    static int[] A;
    static int[] mem;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        A = Arrays.asList(br.readLine().split(" "))
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        mem = new int[1001];
        Arrays.fill(mem, 1);

//        for(int i=0;i<N;i++) {
//            dp_td(i);
//        }
//        System.out.println(max);

        dp_bt(N);
    }
    //A : 0~n-1 , mem : 1 ~ n
    static void dp_bt(int n) {
        for(int i=1;i<=n;i++) {
            for(int j=0;j<i;j++) {
                if(A[i-1] < A[j]) {
                    mem[i] = Math.max(mem[i], mem[j+1] + 1);
                }
            }
            max = Math.max(max, mem[i]);
        }
        System.out.println(max);
    }
    static int dp_td(int n) {
        if(n == 0) {
            return 1;
        }

        if(mem[n] != 1) return mem[n];

        for(int i=n;i>=0;i--) {
            if(A[n] < A[i]) {
                mem[n] = Math.max(mem[n], dp_td(i) + 1);
            }
        }
        max = Math.max(mem[n], max);

        return mem[n];
    }
}
