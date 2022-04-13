import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.util.Arrays;
/*
문제
두 자연수 A와 B가 있을 때, A = BC를 만족하는 자연수 C를 A의 약수라고 한다. 예를 들어, 2의 약수는 1, 2가 있고, 24의 약수는 1, 2, 3, 4, 6, 8, 12, 24가 있다. 자연수 A의 약수의 합은 A의 모든 약수를 더한 값이고, f(A)로 표현한다. x보다 작거나 같은 모든 자연수 y의 f(y)값을 더한 값은 g(x)로 표현한다.

자연수 N이 주어졌을 때, g(N)을 구해보자.

입력
첫째 줄에 테스트 케이스의 개수 T(1 ≤ T ≤ 100,000)가 주어진다. 둘째 줄부터 테스트 케이스가 한 줄에 하나씩 주어지며 자연수 N(1 ≤ N ≤ 1,000,000)이 주어진다.

출력
각각의 테스트 케이스마다, 한 줄에 하나씩 g(N)를 출력한다.

예제 입력 1
5
1
2
10
70
10000
예제 출력 1
1
4
87
4065
82256014
 */

public class BOJ17425 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static long[] f;     //약수의 합 저장
    static long[] g;     //N 이하 값의 약수의 합
    public static void main(String[] args) throws NumberFormatException, IOException{
        int N = Integer.parseInt(br.readLine());
        f = new long[1000001];
        g = new long[1000001];
        Arrays.fill(f,1);

        //div_sum();
        //solve();

        /*  시간초과로 "https://bbangson.tistory.com/53"의 알고리즘을 참고함.
            !배수 알고리즘! { i*1, i*2, i*3, ... }
            i -> 2~1000000
            j -> i*j <= 1000000 이므로, O(NlogN)
         */
        for(int i=2;i<f.length;i++) {
            for(int j=1; i*j<f.length;j++) {
                f[i*j] += i;
            }
        }

        for(int i=1;i<g.length;i++) {
            g[i] = g[i-1] + f[i];
        }

        for(int i=0;i<N;i++) {
            int input = Integer.parseInt(br.readLine());
            sb.append(g[input]).append("\n");
        }

        System.out.println(sb);
    }
    /*
    //아래 코드로 출력을 하게되면, 시간초과가 발생한다.
    //출력형태도 신경써야 시간을 아낄 수 있을 것 같다.
     int[] input = new int[N];
     for(int i=0;i<N;i++) {
        input[i] = Integer.parseInt(br.readline());
     }

     for(int i=0;i<N;i++) {
        System.out.println(g[input[i]]);
     }
     */
    /*
    static void solve() {
        for(int i=1;i<limit_n;i++) {
            g[i] = g[i-1] + f[i];
        }
    }

     */
    /*
    //2. ----- 재귀방식으로 시간초과가 발생하는 것 같다.------
    static long solve(int n){
        if(n < 2) return 1;
        if(g[n] == 0) g[n] = solve(n-1) + f[n];
        return g[n];
    }
     */
    /*
    static void div_sum() {
        for(int i=2;i<limit_n;i++) {
            for(int j=1; i*j<limit_n;j++) {
                f[i*j] += i;
            }
        }
    }

     */
    /*
    //1. ---- 아래 코드는 div_sum과정에서 매 수마다 모든 약수를 도출하여 시간초과를 발생하는 것 같다.----
    static int div_sum(int n) {
        if(f[n] != 0) return f[n];
        int f_n = n;
        for(int i=n/2;i>=1;i--) {
            if(n%i==0) f_n += i;
        }
        f[n] = f_n;
        return f[n];
    }
    */
}
