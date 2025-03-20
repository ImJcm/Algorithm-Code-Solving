package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
피보나치 함수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.25 초 (추가 시간 없음)	128 MB	244056	78219	61914	34.017%
문제
다음 소스는 N번째 피보나치 수를 구하는 C++ 함수이다.

int fibonacci(int n) {
    if (n == 0) {
        printf("0");
        return 0;
    } else if (n == 1) {
        printf("1");
        return 1;
    } else {
        return fibonacci(n‐1) + fibonacci(n‐2);
    }
}
fibonacci(3)을 호출하면 다음과 같은 일이 일어난다.

fibonacci(3)은 fibonacci(2)와 fibonacci(1) (첫 번째 호출)을 호출한다.
fibonacci(2)는 fibonacci(1) (두 번째 호출)과 fibonacci(0)을 호출한다.
두 번째 호출한 fibonacci(1)은 1을 출력하고 1을 리턴한다.
fibonacci(0)은 0을 출력하고, 0을 리턴한다.
fibonacci(2)는 fibonacci(1)과 fibonacci(0)의 결과를 얻고, 1을 리턴한다.
첫 번째 호출한 fibonacci(1)은 1을 출력하고, 1을 리턴한다.
fibonacci(3)은 fibonacci(2)와 fibonacci(1)의 결과를 얻고, 2를 리턴한다.
1은 2번 출력되고, 0은 1번 출력된다. N이 주어졌을 때, fibonacci(N)을 호출했을 때, 0과 1이 각각 몇 번 출력되는지 구하는 프로그램을 작성하시오.

입력
첫째 줄에 테스트 케이스의 개수 T가 주어진다.

각 테스트 케이스는 한 줄로 이루어져 있고, N이 주어진다. N은 40보다 작거나 같은 자연수 또는 0이다.

출력
각 테스트 케이스마다 0이 출력되는 횟수와 1이 출력되는 횟수를 공백으로 구분해서 출력한다.

예제 입력 1
3
0
1
3
예제 출력 1
1 0
0 1
1 2
예제 입력 2
2
6
22
예제 출력 2
5 8
10946 17711
출처
문제를 번역한 사람: baekjoon
데이터를 추가한 사람: connotation, doju, wonrok97
어색한 표현을 찾은 사람: cyj101366
알고리즘 분류
다이나믹 프로그래밍
 */
public class BOJ1003 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T;
    static int[] dp_zero,dp_one;

    public static void main(String[] args) throws IOException{
        init_setting();

        solve();
    }

    private static int fibonacci(int n) {
        if(n == 0) return 0;
        else if(n == 1) return 1;
        else return fibonacci(n - 1) + fibonacci(n - 2);
    }

    private static void solve() throws IOException{
        for(int i = 3; i <= 40; i++) {
            dp_zero[i] = dp_zero[i - 1] + dp_zero[i - 2];
            dp_one[i] = dp_one[i - 1] + dp_one[i - 2];
        }

        for(int i = 0; i < T; i++) {
            int t = Integer.parseInt(br.readLine());

            System.out.println(dp_zero[t] + " " + dp_one[t]);
        }
    }

    private static void init_setting() throws IOException {
        T = Integer.parseInt(br.readLine());

        dp_zero = new int[41];
        dp_one = new int[41];

        dp_zero[0] = 1;
        dp_one[0] = 0;

        dp_zero[1] = 0;
        dp_one[1] = 1;

        dp_zero[2] = 1;
        dp_one[2] = 1;
    }
}
