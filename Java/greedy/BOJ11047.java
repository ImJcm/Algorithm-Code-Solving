package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
동전 0

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	175100	95621	72649	53.482%
문제
준규가 가지고 있는 동전은 총 N종류이고, 각각의 동전을 매우 많이 가지고 있다.

동전을 적절히 사용해서 그 가치의 합을 K로 만들려고 한다. 이때 필요한 동전 개수의 최솟값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N과 K가 주어진다. (1 ≤ N ≤ 10, 1 ≤ K ≤ 100,000,000)

둘째 줄부터 N개의 줄에 동전의 가치 Ai가 오름차순으로 주어진다. (1 ≤ Ai ≤ 1,000,000, A1 = 1, i ≥ 2인 경우에 Ai는 Ai-1의 배수)

출력
첫째 줄에 K원을 만드는데 필요한 동전 개수의 최솟값을 출력한다.

예제 입력 1
10 4200
1
5
10
50
100
500
1000
5000
10000
50000
예제 출력 1
6
예제 입력 2
10 4790
1
5
10
50
100
500
1000
5000
10000
50000
예제 출력 2
12
출처
데이터를 추가한 사람: ai4youej
문제를 만든 사람: baekjoon
알고리즘 분류
그리디 알고리즘
 */
/*
알고리즘 핵심
그리디 알고리즘
1. 오름차순 정렬된 코인을 가장 큰 코인부터 작은 순으로 총합 K에 coin을 차감하면서 코인의 사용 개수를 업데이트한다.
 */
public class BOJ11047 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,K,ans;
    static int[] coins;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        for(int i = N - 1; i >= 0; i--) {
            int coin_cnt = 0;

            while(K >= coins[i]) {
                K -= coins[i];
                coin_cnt++;
            }

            ans += coin_cnt;
        }

        System.out.println(ans);
    }

    private static void init_setting() throws IOException{
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);

        coins = new int[N];

        for(int i = 0; i < N; i++) {
            coins[i] = Integer.parseInt(br.readLine());
        }
    }
}
