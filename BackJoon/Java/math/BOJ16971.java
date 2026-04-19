package math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
배열 B의 값

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	1073	414	287	37.128%
문제
크기가 N×M인 배열 A가 있을 때, 다음과 같은 방법을 이용해서 크기가 (N-1)×(M-1)인 배열 B를 만들 수 있다.

B[i][j] = A[i][j] + A[i+1][j] + A[i+1][j+1] + A[i][j+1] (1 ≤ i < N, 1 ≤ j < M)
배열의 값은 배열의 모든 원소를 합한 값이다.

배열 A에서 임의의 두 행이나 임의의 두 열의 위치를 교환할 수 있다. 배열 A에서 교환을 최대 1번 수행해서 만들 수 있는 배열 B의 값의 최댓값을 구해보자.

입력
첫째 줄에 배열 A의 크기 N, M이 주어진다. 둘째 줄부터 N개의 줄에 배열의 원소가 주어진다. 배열은 정수로만 이루어져 있다.

출력
만들 수 있는 배열 B의 값 중 최댓값을 출력한다.

제한
2 ≤ N, M ≤ 1,000
-1,000 ≤ Ai,j ≤ 1,000
예제 입력 1
3 3
9 8 7
3 2 1
6 5 4
예제 출력 1
92
1번 행과 2번 행을 교환하는 것이 최대이다.

예제 입력 2
3 4
1 2 1 1
2 1 1 2
2 1 1 1
예제 출력 2
34
1번 열과 3번 열을 교환하는 것이 최대이다.

예제 입력 3
3 3
1 1 1
1 2 1
1 1 1
예제 출력 3
20
교환을 하지 않는 것이 최대이다.

출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: jh05013
데이터를 추가한 사람: kimmireu
알고리즘 분류
수학
그리디 알고리즘
누적 합
 */
/*
첫 접근으로 원본 상태의 B배열의 값과 중복없는 행 교환, 열 교환 이후 B배열의 값중 최댓값을 구하도록 코드를 구현하였다.
결과는 시간초과가 발생하였다. 행,열 교환에서 O(N^3), B배열 계산에서 O(N^2)으, 로 총 O(N^5)로 시간초과가 발생한 것같다.

이를 해결하기 위해 두번쨰 방법은 연산과정을 단축하기 위해 스왑과정을 없애는 코드를 구현하여도 여전히 시간복잡도가 컸다.
O(N^4)

어떻게 풀이를 할 수 있을까 고민하다가 규칙성을 발견하고자 생각한 아이디어로
B배열의 값을 계산할 때, 꼭짓점의 수는 1번만 더해지고, 양 사이드는 2번씩 내부는 4번씩 더해진다는 것을 알게되었는데
이때, N이 커지고 내부로 갈수록 2^n만큼 더해지는 것으로 예상하고 자세히 시뮬레이션을 돌려보지 않았다.

결국은 아이디어를 생각하지 못하고 정답 코드의 도움을 빌려 코드를 구현하게 되었다.

알고리즘 핵심
1. 수학적으로 규칙이 존재한다.
2. 양 끝 행,열은 양끝은 1번씩 더하고 나머지는 2번씩 더하고, 중간 행,열은 양 끝은 2번씩 더하고, 나머지는 4번씩 더해진다.
3. 그리드 알고리즘을 적용할 수 있는 부분으로 행,열 교환에 적용할 대상은 양 끝의 행,열 중 합이 큰 행과 양끝을 제외한 행,열 중 합이 적은 라인과 교환하는 것이 B배열이 최댓값을 보장한다.
4. 행,열의 합을 계산하기 위해 별도의 인자를 적용하여 해당 행,열이 양 끝라인의 행,열로 적용할지 적용하지 않을지 결정할 수 있어야 한다.

 */
public class BOJ16971 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,ans;
    static int[][] A;
    static int[][] cumulativeSum, cumulativeRowSum, cumulativeColSum;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        int sum = 0;
        for(int n = 1; n <= N; n++) {
            if(n == 1 || n == N) sum += sumRow(true, n);
            else sum += sumRow(false, n);
        }

        ans = Math.max(ans, sum);

        int maxSumRow = sumRow(true, 1) > sumRow(true, N) ? 1 : N;
        int minSumRow = findMinRowSum();

        sum = 0;

        if(minSumRow != 0) {
            for(int n = 1; n <= N; n++) {
                if(n == 1 || n == N) {
                    if(n == maxSumRow) sum += sumRow(false, n);
                    else sum += sumRow(true, n);
                } else {
                    if(n == minSumRow) sum += sumRow(true, n);
                    else sum += sumRow(false, n);
                }
            }

            ans = Math.max(ans, sum);
        }


        int maxSumCol = sumCol(true, 1) > sumCol(true, M) ? 1 : M;
        int minSumCol = findMinColSum();

        sum = 0;

        if(minSumCol != 0) {
            for(int m = 1; m <= M; m++) {
                if(m == 1 || m == M) {
                    if(m == maxSumCol) sum += sumCol(false, m);
                    else sum += sumCol(true, m);
                } else {
                    if(m == minSumCol) sum += sumCol(true, m);
                    else sum += sumCol(false, m);
                }
            }

            ans = Math.max(ans, sum);
        }

        System.out.println(ans);

    }

    private static int findMinRowSum() {
        int row = 0, sum = 0, minSum = Integer.MAX_VALUE;
        for(int n = 2; n <= N - 1; n++) {
            sum = sumRow(false, n);

            if(sum < minSum) {
                row = n;
                minSum = sum;
            }
        }

        return row;
    }

    private static int findMinColSum() {
        int col = 0, sum = 0, minSum = Integer.MAX_VALUE;
        for(int m = 2; m <= M - 1; m++) {
            sum = sumCol(false, m);

            if(sum < minSum) {
                col = m;
                minSum = sum;
            }
        }

        return col;
    }

    private static int sumRow(boolean isside, int n) {
        int sum = 0;

        if(isside) {
            for(int m = 1; m <= M; m++) {
                if(m == 1 || m == M) {
                    sum += A[n][m];
                } else {
                    sum += (2 * A[n][m]);
                }
            }
        } else {
            for(int m = 1; m <= M; m++) {
                if(m == 1 || m == M) {
                    sum += (2 * A[n][m]);
                } else {
                    sum += (4 * A[n][m]);
                }
            }
        }

        return sum;
    }

    private static int sumCol(boolean isside, int m) {
        int sum = 0;

        if(isside) {
            for(int n = 1; n <= N; n++) {
                if(n == 1 || n == N) {
                    sum += A[n][m];
                } else {
                    sum += (2 * A[n][m]);
                }
            }
        } else {
            for(int n = 1; n <= N; n++) {
                if(n == 1 || n == N) {
                    sum += (2 * A[n][m]);
                } else {
                    sum += (4 * A[n][m]);
                }
            }
        }

        return sum;
    }

    private static void solve_timeOut2() {
        ArrayCheck2(0,0,0);

        for(int n = 1; n < N; n++) {
            for(int n2 = n + 1; n2 <= N; n2++) {
                ArrayCheck2(1,n,n2);
            }
        }

        for(int m = 1; m < M; m++) {
            for(int m2 = m + 1; m2 <= M; m2++) {
                ArrayCheck2(2,m,m2);
            }
        }

        System.out.println(ans);
    }

    private static void ArrayCheck2(int rowcol, int l, int l2) {
        cumulativeSum = new int[N+1][M+1];

        if(rowcol == 0) {
            for(int n = 1; n < N; n++) {

                for(int m = 1; m < M; m++) {
                    cumulativeSum[n][m] = (cumulativeRowSum[n][m+1] - cumulativeRowSum[n][m-1] + cumulativeRowSum[n+1][m+1] - cumulativeRowSum[n+1][m-1]
                            + cumulativeSum[n-1][m] + cumulativeSum[n][m-1] - cumulativeSum[n-1][m-1]);
                }
            }
        } else if(rowcol == 1) {
            for(int n = 1; n < N; n++) {
                int n2 = n == l ? l2 : n == l2 ? l : n;
                int n3 = n + 1 == l ? l2 : n + 1 == l2 ? l : n + 1;
                for(int m = 1; m < M; m++) {
                    cumulativeSum[n][m] = (cumulativeRowSum[n2][m+1] - cumulativeRowSum[n2][m-1] + cumulativeRowSum[n3][m+1] - cumulativeRowSum[n3][m-1]
                            + cumulativeSum[n-1][m] + cumulativeSum[n][m-1] - cumulativeSum[n-1][m-1]);
                }
            }
        } else if(rowcol == 2){
            for(int m = 1; m < M; m++) {
                int m2 = m == l ? l2 : m == l2 ? l : m;
                int m3 = m + 1 == l ? l2 : m + 1 == l2 ? l : m + 1;
                for(int n = 1; n < N; n++) {
                    cumulativeSum[n][m] = (cumulativeColSum[n+1][m2] - cumulativeColSum[n-1][m2] + cumulativeColSum[n+1][m3] - cumulativeColSum[n-1][m3]
                            + cumulativeSum[n-1][m] + cumulativeSum[n][m-1] - cumulativeSum[n-1][m-1]);
                }
            }
        }

        ans = Math.max(ans, cumulativeSum[N-1][M-1]);
    }

    private static void solve_timeOut() {
        ArrayCheck();

        for(int n = 1; n < N; n++) {
            for(int n2 = n + 1; n2 <= N; n2++) {
                swapLine(0,n,n2);
                ArrayCheck();
                swapLine(0,n2,n);
            }
        }

        for(int m = 1; m < M; m++) {
            for(int m2 = m + 1; m2 <= M; m2++) {
                swapLine(1,m,m2);
                ArrayCheck();
                swapLine(1,m2,m);
            }
        }

        System.out.println(ans);
    }

    private static void swapLine(int RowOrCol, int l, int l2) {
        switch (RowOrCol) {
            case 0:
                for(int m = 1; m <= M; m++) {
                    int temp = A[l][m];
                    A[l][m] = A[l2][m];
                    A[l2][m] = temp;
                }
                break;
            case 1:
                for(int n = 1; n <= N; n++) {
                    int temp = A[n][l];
                    A[n][l] = A[n][l2];
                    A[n][l2] = temp;
                }
                break;
        }
    }
    private static void ArrayCheck() {
        cumulativeSum = new int[N+1][M+1];

        for(int n = 1; n < N; n++) {
            for(int m = 1; m < M; m++) {
                cumulativeSum[n][m] = (A[n][m] + A[n+1][m] + A[n][m+1] + A[n+1][m+1]
                        + cumulativeSum[n-1][m] + cumulativeSum[n][m-1] - cumulativeSum[n-1][m-1]);
            }
        }
        ans = Math.max(ans, cumulativeSum[N-1][M-1]);
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        ans = Integer.MIN_VALUE;

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        A = new int[N+1][M+1];
        cumulativeRowSum = new int[N+1][M+1];
        cumulativeColSum = new int[N+1][M+1];

        for(int n = 1; n <= N; n++) {
            input = br.readLine().split(" ");
            for(int m = 1; m <= M; m++) {
                A[n][m] = Integer.parseInt(input[m-1]);

                cumulativeRowSum[n][m] = A[n][m] + cumulativeRowSum[n][m-1];
                cumulativeColSum[n][m] = A[n][m] + cumulativeColSum[n-1][m];
            }
        }
    }
}
