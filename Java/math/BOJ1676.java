package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
팩토리얼 0의 개수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	100617	46993	39580	46.319%
문제
N!에서 뒤에서부터 처음 0이 아닌 숫자가 나올 때까지 0의 개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N이 주어진다. (0 ≤ N ≤ 500)

출력
첫째 줄에 구한 0의 개수를 출력한다.

예제 입력 1
10
예제 출력 1
2
예제 입력 2
3
예제 출력 2
0
출처
데이터를 추가한 사람: 111111111111, his130
문제를 만든 사람: author6
알고리즘 분류
수학
 */
/*
알고리즘 핵심
수학 (팩토리얼, 규칙성)
팩토리얼 계산기 - https://ko.numberempire.com/factorialcalculator.php
0! = 1
1! = 1
2! = 2
3! = 6
4! = 24
5! = 120
6! = 720
7! = 5040
8! = 40320
9! = 362880
10! = 3628800
11! = 39916800
    ...
15! = 1307...68000
    ...
25! = 1551...84000000
    ...
50! = 3041...512000000000000
    ...
75! = 2480...24000000000000000000
    ...
100! = 9332...64000000000000000000000000
125! = 1882...80000000000000000000000000000000
    ...
150! = 5713...520000000000000000000000000000000000000

1. 5배수 차이로 1개씩 값이 늘어남을 볼 수 있다.
2. 25배수 차이로 1개씩 값이 늘어남을 볼 수 있다.
3. 125배수 차이로 1개씩 값이 늘어남을 볼 수 있다.

즉, 5^n배수에서 1개씩 값이 늘어난다.
해당 규칙으로 다음과 같은 정규식을 낼 수 있다.
N! = (N / 5) + (N / 25) + (N / 125) + ...
(0 <= N <= 450)이므로, 5^4 = 625보다 적으므로 고려하지 않는다.
 */
public class BOJ1676 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    /*
     N = 21부터 Long타입으로 값을 저장하는 범위를 초과한다.
     따라서, 모든 값을 저장하여 0이 아닌 처음 위치를 구하는 방법은 잘못된 방법이다.
     */
    private static Long Factorial(int n) {
        long result = 1;

        for(int i = 2; i <= n;
            i++) {
            result *= i;
        }

        return result;
    }

    private static void wrong_solve() {
        String result = String.valueOf(Factorial(N));

        for(int i = result.length() - 1; i >= 0; i--) {
            char ch = result.charAt(i);

            if('1' <= ch && ch <= '9') {
                break;
            } else {
                ans++;
            }
        }

        System.out.println(ans);
    }

    private static void solve() {
        ans = (N / (int) Math.pow(5,1)) + (N / (int) Math.pow(5,2)) + (N / (int) Math.pow(5,3));
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        ans = 0;
    }
}
