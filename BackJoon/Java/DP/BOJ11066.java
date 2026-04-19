package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
파일 합치기 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	32046	16023	10841	48.877%
문제
소설가인 김대전은 소설을 여러 장(chapter)으로 나누어 쓰는데, 각 장은 각각 다른 파일에 저장하곤 한다. 소설의 모든 장을 쓰고 나서는 각 장이 쓰여진 파일을 합쳐서 최종적으로 소설의 완성본이 들어있는 한 개의 파일을 만든다. 이 과정에서 두 개의 파일을 합쳐서 하나의 임시파일을 만들고, 이 임시파일이나 원래의 파일을 계속 두 개씩 합쳐서 소설의 여러 장들이 연속이 되도록 파일을 합쳐나가고, 최종적으로는 하나의 파일로 합친다. 두 개의 파일을 합칠 때 필요한 비용(시간 등)이 두 파일 크기의 합이라고 가정할 때, 최종적인 한 개의 파일을 완성하는데 필요한 비용의 총 합을 계산하시오.

예를 들어, C1, C2, C3, C4가 연속적인 네 개의 장을 수록하고 있는 파일이고, 파일 크기가 각각 40, 30, 30, 50 이라고 하자. 이 파일들을 합치는 과정에서, 먼저 C2와 C3를 합쳐서 임시파일 X1을 만든다. 이때 비용 60이 필요하다. 그 다음으로 C1과 X1을 합쳐 임시파일 X2를 만들면 비용 100이 필요하다. 최종적으로 X2와 C4를 합쳐 최종파일을 만들면 비용 150이 필요하다. 따라서, 최종의 한 파일을 만드는데 필요한 비용의 합은 60+100+150=310 이다. 다른 방법으로 파일을 합치면 비용을 줄일 수 있다. 먼저 C1과 C2를 합쳐 임시파일 Y1을 만들고, C3와 C4를 합쳐 임시파일 Y2를 만들고, 최종적으로 Y1과 Y2를 합쳐 최종파일을 만들 수 있다. 이때 필요한 총 비용은 70+80+150=300 이다.

소설의 각 장들이 수록되어 있는 파일의 크기가 주어졌을 때, 이 파일들을 하나의 파일로 합칠 때 필요한 최소비용을 계산하는 프로그램을 작성하시오.

입력
프로그램은 표준 입력에서 입력 데이터를 받는다. 프로그램의 입력은 T개의 테스트 데이터로 이루어져 있는데, T는 입력의 맨 첫 줄에 주어진다.각 테스트 데이터는 두 개의 행으로 주어지는데, 첫 행에는 소설을 구성하는 장의 수를 나타내는 양의 정수 K (3 ≤ K ≤ 500)가 주어진다. 두 번째 행에는 1장부터 K장까지 수록한 파일의 크기를 나타내는 양의 정수 K개가 주어진다. 파일의 크기는 10,000을 초과하지 않는다.

출력
프로그램은 표준 출력에 출력한다. 각 테스트 데이터마다 정확히 한 행에 출력하는데, 모든 장을 합치는데 필요한 최소비용을 출력한다.

예제 입력 1
2
4
40 30 30 50
15
1 21 3 4 5 35 5 4 3 5 98 21 14 17 32
예제 출력 1
300
864
출처
ICPC > Regionals > Asia Pacific > Korea > Nationwide Internet Competition > Daejeon Nationalwide Internet Competition 2015 F번

알고리즘 분류
다이나믹 프로그래밍
 */
/*
문제를 읽고 dp의 활용과 규칙성을 찾기 위해 1~5개의 수를 예시로 들고 최소 비용을 갖는 경우를 시뮬레이션 해봤을 때, 다음과 같은 규칙을 얻을 수 있었다.
example = {1};
min_cost = 1;

example = {1,2};
min_cost = 3;

example = {1,2,3};
min_cost = (1+2) + (1+2+3) = 9;

example = {1,2,3,4};
min_cost = (1+2) + (1+2+3) + (1+2+3+4) = 19;

example = {1,2,3,4,5};
min_cost = (1+2) + (1+2+3) + (4+5) + (1+2+3+4+5) = 3 + 6 + 9 + 15 = 33;

즉, 배열에서 최소 값에 해당하는 2개의 값을 누적하여 더하는 것으로 예상했다. <- (파일의 순서를 조정할 수 있다고 생각하였다. = 잘못된 생각)

하지만, 이러한 방법은 dp의 방식이 아닌 단순 반복을 통한 답 구하기라고 생각하였고 도저히 접근방법이 생각이 나지않아 정답 코드를 참고하였다.

이 문제의 핵심은 DP + 누적합이고, 파일의 순서를 조정할 수 없는 것이다. (파일을 합칠 경우, 연속된 파일에 한에서 가능하다.)

즉,
i. 소설의 각 챕터이므로 무조건 인접한 것끼리 묶어야 한다.
ii. 규칙상 마지막 연산은 전체합을 더해야한다.

핵심 과정
dp[i][j] = file[i]부터 file[j]까지의 최소 가치를 저장한다.
ex) {40,30,30,50}
dp[1][2] = 40 + 30 = 70; dp[2][3] = 30 + 30 = 60; dp[3][4] = 30 + 50 = 80;
dp[1][3] = dp[1][2] + dp[3][3] or dp[1][1] + dp[2][3]; dp[2][4] = dp[2][2] + dp[3][4] or dp[2][3] + dp[4][4]
dp[1][4] = dp[1][1] + dp[2][4] or dp[1][3] + dp[4][4] or dp[1][2] + dp[3][4]
각 dp의 i부터 j까지의 최소값을 만족하는 것이다.

즉, 이를 수식화하면
i.      dp[0][0] = 0;
ii.     dp[i][i+1] = file[i] + file[i+1];
iii.    dp[i][j] = min(dp[i][k] + dp[k+1][j] + sum(file[i]~file[j]) ( i < k < j )

gap : 몇 개씩 묶어서 계산할 범위를 결정할 것인지 결정하는 값
s   : start 값
e   : end 값
m   : mid 값

개인적인 생각으로 코드를 해석하면, gap은 bottom-up으로 dp[0][K]값을 구하기 위해 아래 부분 dp들을 구하기 위한 작업이라고 생각한다.
즉, gap의 값을 통해 묶을 파일의 갯수와 gap 값만큼의 구간길이를 갖는 start, end를 정하고 해당 구간에서의 중간값을 설정하여 최소 비용을
구함으로써 짧은 구간에서 구한 최소 비용 값으로 긴 구간에서 최소비용을 구한다고 생각한다.

gap = 1에서 구할 수 있는 dp의 값들은 dp[0][1], dp[1][2], ... dp[K-2][K-1]
gap = 2에서 구할 수 있는 dp의 값들은 dp[0][2], dp[1][3], ... dp[K-3][K-1]
...
gap = K-1에서 구할 수 있는 dp의 값들은 dp[0][K-1]

이때, 각 dp[s][e]는 중간값으로 나뉘어 최소 비용을 업데이트한다.
min(dp[s][m],dp[m+1][e] + subtotal(s,e));

ex)
gap = 1
dp[0][1]을 기준으로 보면 dp[0][1] = dp[0][0] + dp[1][1] + sum[0] - 0;

gap = 2
dp[0][2]을 기준으로 보면 dp[0][2] = Min(dp[0][0] + dp[1][2] + subtotal(0,2), dp[0][1] + dp[2][2] + subtotal(0,2))

gap = 3
dp[0][3]을 기준으로 보면 dp[0][3] = Min(dp[0][0] + do[1][3] + subtotal(0,3), dp[0][1] + dp[2][3] + subtotal(0,3), dp[0][2] + dp[3][3] + subtotal(0,3))

gap을 늘려가며 최소비용을 만족하는 값을 이전 dp 값을 통해 최소비용 값을 만족한다.

참고 - https://mierminusone.tistory.com/9, https://m.blog.naver.com/tjdwns0920/221135677693
 */
public class BOJ11066 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() throws IOException {
        while(T-- > 0) {
            int K = Integer.parseInt(br.readLine());

            int[] file = new int[K];
            int[] sum = new int[K];
            int[][] dp = new int[K][K];

            String[] input = br.readLine().split(" ");
            file[0] = sum[0] = Integer.parseInt(input[0]);
            dp[0][0] = 0;
            for(int i=1;i<K;i++) {
                file[i] = Integer.parseInt(input[i]);
                sum[i] = file[i] + sum[i-1];
                dp[i][i] = 0;
            }

            for(int i=0;i<K-1;i++) {
                dp[i][i+1] = file[i] + file[i+1];
            }

            for(int gap = 1;gap < K;gap++) {
                for(int s = 0;s + gap < K;s++) {
                    int e = s + gap;
                    dp[s][e] = Integer.MAX_VALUE;

                    for(int m = s;m < e;m++) {
                        dp[s][e] = Math.min(dp[s][e], dp[s][m] + dp[m+1][e] + subtotal(sum,s,e));
                    }
                }
            }
            System.out.println(dp[0][K-1]);
        }
    }

    static int subtotal(int[] sum, int start, int end) {
        if(start == 0) return sum[end];
        else return sum[end] - sum[start-1];
    }

    static void init_setting() throws IOException {
        T = Integer.parseInt(br.readLine());
    }
}
