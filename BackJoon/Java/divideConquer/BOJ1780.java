package divideConquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
종이의 개수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	53860	33184	24955	60.758%
문제
N×N크기의 행렬로 표현되는 종이가 있다. 종이의 각 칸에는 -1, 0, 1 중 하나가 저장되어 있다. 우리는 이 행렬을 다음과 같은 규칙에 따라 적절한 크기로 자르려고 한다.

만약 종이가 모두 같은 수로 되어 있다면 이 종이를 그대로 사용한다.
(1)이 아닌 경우에는 종이를 같은 크기의 종이 9개로 자르고, 각각의 잘린 종이에 대해서 (1)의 과정을 반복한다.
이와 같이 종이를 잘랐을 때, -1로만 채워진 종이의 개수, 0으로만 채워진 종이의 개수, 1로만 채워진 종이의 개수를 구해내는 프로그램을 작성하시오.

입력
첫째 줄에 N(1 ≤ N ≤ 37, N은 3k 꼴)이 주어진다. 다음 N개의 줄에는 N개의 정수로 행렬이 주어진다.

출력
첫째 줄에 -1로만 채워진 종이의 개수를, 둘째 줄에 0으로만 채워진 종이의 개수를, 셋째 줄에 1로만 채워진 종이의 개수를 출력한다.

예제 입력 1
9
0 0 0 1 1 1 -1 -1 -1
0 0 0 1 1 1 -1 -1 -1
0 0 0 1 1 1 -1 -1 -1
1 1 1 0 0 0 0 0 0
1 1 1 0 0 0 0 0 0
1 1 1 0 0 0 0 0 0
0 1 -1 0 1 -1 0 1 -1
0 -1 1 0 1 -1 0 1 -1
0 1 -1 1 0 -1 0 1 -1
예제 출력 1
10
12
11
출처
문제를 만든 사람: author5
잘못된 데이터를 찾은 사람: tncks0121
데이터를 추가한 사람: djm03178, hwankim123, upsk1
알고리즘 분류
분할 정복
재귀
 */
/*
알고리즘 핵심
분할 정복 + 재귀
1. N과 시작 좌표 r,c을 시작으로 (r ~ r + N, c ~ c + N)의 수가 모두 같은지 확인한다.
2. 같다면 (r,c)의 수인 종이를 +1한다
3. 같지 않다면, N = N / 3으로 나눈 후, (r,c)를 각각 9개의 ((r + N/3 * [1,2,3]),(c + N/3 * [1,2,3])) 종이를 만들고
1번 과정을 반복한다.
4. 2번 과정에서 조건을 만족한다면 해당 종이는 1번 과정을 거치지 않는다. 이후, N = 1이 될때까지 반복하여 ans를 업데이트한다.
 */
public class BOJ1780 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] ans;
    static int[][] papers;
    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static boolean is_same_value_paper(int n, int r, int c) {
        int num = papers[r][c];

        for(int i = r; i < r + n; i++) {
            for(int j = c; j < c + n; j++) {
                if(num != papers[i][j]) return false;
            }
        }

        ans[num + 1] += 1;
        return true;
    }

    private static void divide_conquer(int n, int r, int c) {
        if(is_same_value_paper(n,r,c)) return;
        else {
            int nn = n / 3;
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    divide_conquer(nn, r + (nn * i), c + (nn * j));
                }
            }
        }
    }

    private static void solve() {
        divide_conquer(N,0,0);

        print();
    }

    private static void print() {
        for(int i = 0; i < ans.length; i++) {
            System.out.println(ans[i]);
        }
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        papers = new int[N][];

        for(int i = 0; i < N; i++) {
            papers[i] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        ans = new int[3];
    }
}
