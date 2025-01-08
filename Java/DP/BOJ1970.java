package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
건배 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	1051	407	286	40.683%
문제
N명의 사람이 원판 테이블에 앉아서 콜라를 마시고 있다. 그 상황에서 두 사람이 짝을 지어서 건배를 하려 한다. 그런데 이들은 건배를 할 때, 보기 좋게 하기 위하여 마시고 있는 콜라의 브랜드가 같은 경우에만 건배를 할 수 있다고 한다. 그리고 사람들이 동시에 건배를 할 때, 사람들의 팔이 서로 엇갈리는 경우에는 건배를 할 수 없다고 한다.



예를 들어 왼쪽 그림과 같은 경우는 겹치는 경우가 없어 건배를 할 수 있으나 오른쪽과 같은 경우에는 건배를 할 수 없다. 사람의 수 N과 각각의 사람이 마시는 콜라의 브랜드가 주어져 있을 때, 동시에 건배를 할 수 있는 가장 많은 쌍의 개수를 출력하시오.

입력
첫 줄에 사람의 수 (1 ≤ N ≤ 1000) 이 주어진다. 그리고 둘째 줄에 N개의 정수(1 이상 100 이하)가 공백을 사이에 두고 주어지는데 이는 각각의 사람이 마시는 콜라의 브랜드이다. (시계방향순서대로 주어진다)

출력
동시에 건배를 할 수 있는 가장 많은 쌍의 개수를 출력한다.

예제 입력 1
22
1 7 1 2 4 2 4 9 1 1 9 4 5 9 4 5 6 9 2 1 2 9
예제 출력 1
8
예제 입력 2
6
1 2 2 1 3 3
예제 출력 2
3
출처
ICPC > Regionals > Europe > Northwestern European Regional Contest > Benelux Algorithm Programming Contest > BAPC 2006 E번

잘못된 조건을 찾은 사람: Apple_Cplus
데이터를 추가한 사람: cgiosy
알고리즘 분류
다이나믹 프로그래밍
 */
public class BOJ1970 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] brands;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        /*for(int i = 0; i < N - 1; i++) {
            for(int j = i + 1; j < N; j++) {
                int e = 0;
                if(brands[i] == brands[j]) e = 1;
                int inner_i = i + 1;
                int outer_i = j + 1 == N ? 0 : j + 1;
                int inner_j = j - 1;
                int outer_j = i - 1 == -1 ? N - 1 : i - 1;

                dp[i][j] = Math.max(dp[i][j], dp[inner_i][inner_j] + dp[outer_i][outer_j] + e);
            }
        }*/
        /*for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                int e = 0;
                if((i != j) && (brands[i] == brands[j])) e = 1;
                int inner_i = i + 1 == N ? 0 : i + 1;
                int outer_i = i - 1 == -1 ? N - 1 : i - 1;
                int inner_j = j - 1 == -1 ? N - 1 : j - 1;
                int outer_j = j + 1 == N ? 0 : j + 1;

                //dp[i][j] = Math.max(dp[i][j], dp[inner_i][inner_j] + dp[outer_i][outer_j] + e);
                dp[i][j] = Math.max(dp[i][j],
                        Math.max(dp[j][i], dp[inner_i][inner_j] + dp[outer_i][outer_j] + e));

                dp[j][i] = dp[i][j];
            }
        }*/

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                int e = 0;
                if(i != j && brands[i] == brands[j]) e = 1;
                int next_i = i;
                int next_j = j;


                int r = 0;
                if(next_i < next_j) r = dp[next_i][next_j] + e;
                else r = dp[next_j][next_i] + e;

                dp[i][j] = Math.max(dp[i][j], r);
            }
        }

        System.out.println("break");
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        brands = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        dp = new int[N][N];

        //for(int i = 0; i < N; i++) Arrays.fill(dp[i], -1);
    }
}
