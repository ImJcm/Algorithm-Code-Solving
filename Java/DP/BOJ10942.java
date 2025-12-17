package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
팰린드롬?

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (하단 참고)	256 MB	57218	17064	11819	29.902%
문제
명우는 홍준이와 함께 팰린드롬 놀이를 해보려고 한다.

먼저, 홍준이는 자연수 N개를 칠판에 적는다. 그 다음, 명우에게 질문을 총 M번 한다.

각 질문은 두 정수 S와 E(1 ≤ S ≤ E ≤ N)로 나타낼 수 있으며, S번째 수부터 E번째 까지 수가 팰린드롬을 이루는지를 물어보며, 명우는 각 질문에 대해 팰린드롬이다 또는 아니다를 말해야 한다.

예를 들어, 홍준이가 칠판에 적은 수가 1, 2, 1, 3, 1, 2, 1라고 하자.

S = 1, E = 3인 경우 1, 2, 1은 팰린드롬이다.
S = 2, E = 5인 경우 2, 1, 3, 1은 팰린드롬이 아니다.
S = 3, E = 3인 경우 1은 팰린드롬이다.
S = 5, E = 7인 경우 1, 2, 1은 팰린드롬이다.
자연수 N개와 질문 M개가 모두 주어졌을 때, 명우의 대답을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 수열의 크기 N (1 ≤ N ≤ 2,000)이 주어진다.

둘째 줄에는 홍준이가 칠판에 적은 수 N개가 순서대로 주어진다. 칠판에 적은 수는 100,000보다 작거나 같은 자연수이다.

셋째 줄에는 홍준이가 한 질문의 개수 M (1 ≤ M ≤ 1,000,000)이 주어진다.

넷째 줄부터 M개의 줄에는 홍준이가 명우에게 한 질문 S와 E가 한 줄에 하나씩 주어진다.

출력
총 M개의 줄에 걸쳐 홍준이의 질문에 대한 명우의 답을 입력으로 주어진 순서에 따라서 출력한다. 팰린드롬인 경우에는 1, 아닌 경우에는 0을 출력한다.

예제 입력 1
7
1 2 1 3 1 2 1
4
1 3
2 5
3 3
5 7
예제 출력 1
1
0
1
1
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: bliss08, deneb2016, eric00513, tlsgkdns
알고리즘 분류
다이나믹 프로그래밍
시간 제한
Java 8: 2.5 초
PyPy3: 1.5 초
Java 8 (OpenJDK): 2.5 초
Java 11: 2.5 초
PyPy2: 1.5 초
Kotlin (JVM): 2.5 초
 */
/*
문제의 접근 방법은 예시로 나온 [1,2,1,3,1,2,1]을 구간별로 회문여부를 나열하였을 때 규칙을 발견하였다.
- 팰린드롬 가능 = 1, 불가능 = 0
(1,1) = 1   (2,2) = 1   (3,3) = 1   (4,4) = 1   (5,5) = 1   (6,6) = 1   (7,7) = 1
(1,2) = 0   (2,3) = 0   (3,4) = 0   (4,5) = 0   (5,6) = 0   (6,7) = 0
(1,3) = 1   (2,4) = 0   (3,5) = 1   (4,6) = 0   (5,7) = 1
(1,4) = 0   (2,5) = 0   (3,6) = 0   (4,7) = 0
(1,5) = 0   (2,6) = 1   (3,7) = 0
(1,6) = 0   (2,7) = 0
(1,7) = 1

(1,7)이 팰린드롬인지 여부는 (2,6)의 팰린드롬 여부와 (1,1), (7,7)이 같은지 여부에 따라 결정된다.
(1,3)이 팰린드롬인지 여부는 (2,2)의 팰린드롬 여부와 (1,1), (3,3)이 같은지 여부에 따라 결정된다.
...

이때, 두 구간의 길이가 짝수/홀수 여부에 따라 결과가 달라지는지 고민하다가 (i,j)의 팰린드롬 여부는 우선적으로 (i+1, j-1)이 팰린드롬인지가
중요하기 때문에 구간의 길이는 영향을 미치지 않다고 생각하였다. 하지만, 두 구간의 길이가 2인 경우 i+1, j-1이 값이 교차되므로 해당 경우는
(i,i), (j,j)의 값이 같은지를 검사하여 결정한다.

또한, i의 출발 인덱스를 N-1부터 1로 수행하여 (1,7)을 구하기 위해 (2,6)의 여부 검사가 선행될 수 있도록 하였다.
(i=1->N-1로 수행하는 경우, 올바른 결과를 만들지 못한다.)

따라서, (i,j)의 팰린드롬 여부는 아래와 같은 코드로 작성된다. -> palindrome()
 */
public class BOJ10942 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static int[] numbers;
    static int[][] dp;
    static StringBuilder sb = new StringBuilder();


    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() throws IOException {
        palindrome();

        for(int i=0;i<M;i++) {
            String[] input = br.readLine().split(" ");

            int start = Integer.parseInt(input[0]);
            int end = Integer.parseInt(input[1]);

            sb.append(dp[start][end]);
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    static void palindrome() {
        for(int i=N-1;i>=1;i--) {
            for(int j=i+1;j<=N;j++) {
                int value = 0;
                if(j - i > 1) {
                    if(dp[i+1][j-1] == 1) {
                        if(numbers[i] == numbers[j]) {
                            value = 1;      // value = dp[i+1][j-1];
                        } else {
                            value = 0;
                        }
                    } else {
                        value = 0;
                    }
                } else {
                    if(numbers[i] == numbers[j]) {
                        value = 1;          // value = dp[i][i] or dp[j][j];
                    } else {
                        value = 0;
                    }
                }
                dp[i][j] = value;
            }
        }
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        numbers = new int[N + 1];
        dp = new int[N + 1][N + 1];

        String[] input = br.readLine().split(" ");
        for(int i=1;i<=N;i++) {
            numbers[i] = Integer.parseInt(input[i-1]);
            Arrays.fill(dp[i], 0);
            dp[i][i] = 1;
        }

        M = Integer.parseInt(br.readLine());
    }
}
