package math;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
/*
약수의 합 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (추가 시간 없음)	512 MB	2814	1156	972	41.030%
문제
두 자연수 A와 B가 있을 때, A = BC를 만족하는 자연수 C를 A의 약수라고 한다. 예를 들어, 2의 약수는 1, 2가 있고, 24의 약수는 1, 2, 3, 4, 6, 8, 12, 24가 있다. 자연수 A의 약수의 합은 A의 모든 약수를 더한 값이고, f(A)로 표현한다. x보다 작거나 같은 모든 자연수 y의 f(y)값을 더한 값은 g(x)로 표현한다.

자연수 N이 주어졌을 때, g(N)을 구해보자.

입력
첫째 줄에 자연수 N(1 ≤ N ≤ 1,000,000)이 주어진다.

출력
첫째 줄에 g(N)를 출력한다.

예제 입력 1
1
예제 출력 1
1
예제 입력 2
2
예제 출력 2
4
예제 입력 3
10
예제 출력 3
87
예제 입력 4
70
예제 출력 4
4065
예제 입력 5
10000
예제 출력 5
82256014
 */

//int자료형으로 할 경우, 정수값이 범위를 벗어나기때문에, long타입을 사용
public class BOJ17427 {
    static long[] f = new long[1000001];
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static long result = 0;

    public static void main(String[] args) throws Exception {
        int n = Integer.parseInt(br.readLine());

        Arrays.fill(f,1);
        //약수의 합을 구하는 알고리즘 O(NlgN)
        for(int i=2;i<=n;i++) {
            for(int j=1;i*j<=n;j++) {
                f[i*j] += (long)i;
            }
        }

        for(int i=1;i<=n;i++) {
            result += f[i];
        }
        System.out.println(result);
    }
}
