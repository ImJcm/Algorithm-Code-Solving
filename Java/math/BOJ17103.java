package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
골드바흐 파티션

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초	512 MB	40619	15005	11584	34.767%
문제
골드바흐의 추측: 2보다 큰 짝수는 두 소수의 합으로 나타낼 수 있다.
짝수 N을 두 소수의 합으로 나타내는 표현을 골드바흐 파티션이라고 한다. 짝수 N이 주어졌을 때, 골드바흐 파티션의 개수를 구해보자. 두 소수의 순서만 다른 것은 같은 파티션이다.

입력
첫째 줄에 테스트 케이스의 개수 T (1 ≤ T ≤ 100)가 주어진다. 각 테스트 케이스는 한 줄로 이루어져 있고, 정수 N은 짝수이고, 2 < N ≤ 1,000,000을 만족한다.

출력
각각의 테스트 케이스마다 골드바흐 파티션의 수를 출력한다.

예제 입력 1
5
6
8
10
12
100
예제 출력 1
1
1
2
1
6
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: djm03178
문제의 오타를 찾은 사람: jh05013
알고리즘 분류
수학
정수론
소수 판정
에라토스테네스의 체
 */
public class BOJ17103 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T,N;
    static int[] sieve;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void Eratosthenes(int n) {
        sieve = new int[n+1];

        Arrays.fill(sieve, 0);

        for(int x = 2; x <= n; x++) {
            if(sieve[x] == 1) continue;
            for(int u = 2 * x; u <= n; u += x) {
                sieve[u] = 1;
            }
        }
    }

    private static void solve() {

    }

    private static void init_setting() throws IOException {
        T = Integer.parseInt(br.readLine());


    }


}
