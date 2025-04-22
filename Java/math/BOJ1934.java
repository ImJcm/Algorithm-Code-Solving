package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
최소공배수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	93223	50526	42883	55.013%
문제
두 자연수 A와 B에 대해서, A의 배수이면서 B의 배수인 자연수를 A와 B의 공배수라고 한다. 이런 공배수 중에서 가장 작은 수를 최소공배수라고 한다. 예를 들어, 6과 15의 공배수는 30, 60, 90등이 있으며, 최소 공배수는 30이다.

두 자연수 A와 B가 주어졌을 때, A와 B의 최소공배수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 테스트 케이스의 개수 T(1 ≤ T ≤ 1,000)가 주어진다. 둘째 줄부터 T개의 줄에 걸쳐서 A와 B가 주어진다. (1 ≤ A, B ≤ 45,000)

출력
첫째 줄부터 T개의 줄에 A와 B의 최소공배수를 입력받은 순서대로 한 줄에 하나씩 출력한다.

예제 입력 1
3
1 45000
6 10
13 17
예제 출력 1
45000
30
221
출처
문제의 오타를 찾은 사람: jason9319, kyo20111
알고리즘 분류
수학
정수론
유클리드 호제법
 */
/*
알고리즘 핵심
수학 (유클리드 호제법 - 최소공약수)
1. 유클리드 호제법을 사용하여 두 수 A,B의 최대공약수를 구한다.
2. gcd x (A / gcd) x (B / gcd) = lcm 이므로, 수식을 최적화하여 A x B / gcd인 최소공배수를 출력한다.
 */
public class BOJ1934 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T;
    static int[][] AB;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        for(int[] ab : AB) {
            //System.out.println(bruteforce_lcm(ab[0], ab[1]));
            int gcd = Euclidean(ab[0],ab[1]);
            System.out.println(ab[0] * ab[1] / gcd); // gcd * (ab[0] / gcd) * (ab[1] / gcd)
        }
    }

    private static int Euclidean(int a, int b) {
        if(b == 0) {
            return a;
        }
        return Euclidean(b, a % b);
    }

    private static int bruteforce_lcm(int a, int b) {
        int c = 2;
        int l = Math.min(a,b);
        int lsm = 1;

        while(c <= l) {
            if(a % c == 0 && b % c == 0) {
                lsm *= c;
                a /= c;
                b /= c;
                c = 2;
                l = Math.min(a,b);
                continue;
            }
            c++;
        }

        lsm *= (a * b);

        return lsm;
    }

    private static void init_setting() throws IOException {
        T = Integer.parseInt(br.readLine());

        AB = new int[T][2];

        for(int i = 0; i < T; i++) {
            String[] input = br.readLine().split(" ");

            AB[i][0] = Integer.parseInt(input[0]);
            AB[i][1] = Integer.parseInt(input[1]);
        }
    }

}
