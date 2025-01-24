package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
달팽이3

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	2248	501	376	26.147%
문제
M줄 N칸으로 되어 있는 표 위에, 달팽이 모양으로 선을 그리려고 한다.

ㅇ




위의 그림은 M=5, N=3의 예이다. 이제 표의 왼쪽 위 칸(○)에서 시작하여, 오른쪽으로 선을 그려 나간다. 표의 바깥 또는 이미 그려진 칸에 닿아서 더 이상 이동할 수 없게 되면, 시계방향으로 선을 꺾어서 그려나간다.

ㅇ	→	↘
↗	↘	↓
↑	↓	↓
↑	끝	↓
↖	←	↙
위의 표는 선을 그려 나간 모양을 나타낸 것이다. 선이 꺾어진 부분은 대각선으로 나타내었다.  표의 모든 칸이 채워질 때까지 선을 몇 번 꺾게 될까? 또, 어디에서 끝나게 될까?

입력
첫째 줄에 M과 N이 빈 칸을 사이에 두고 주어진다. (2 ≤ M, N ≤ 2,100,000,000)

출력
첫째 줄에 표의 모든 칸이 채워질 때까지 선이 꺾어지는 횟수를 출력한다. 둘째 줄에 끝나는 점의 좌표를 출력한다. 왼쪽 위 칸의 좌표를 (1, 1), 오른쪽 아래 칸의 좌표를 (M, N)이라고 하자.

예제 입력 1
5 3
예제 출력 1
5
4 2
알고리즘 분류
수학
많은 조건 분기
 */
/*
알고리즘 핵심
구현 + 수학
첫 접근 시도 : (M,N) = (N, M-1) + 1을 이용하여 M,N의 모든 수의 곡선값을 저장한 배열을 이용하여 답을 구하려고 하였으나 Heap overflow 발생
원인 : M,N의 크키가 2_100_000_000으로 큰 값이므로 발생한다.

따라서, 배열을 사용하지 않고 답을 구해야한다.

1. 곡선의 개수 -> (M,N) = (N, M-1) + 1을 응용하여 (M,N) = (M-1,N-1) + 2, .... M or N == 1일 때까지 수행하고
N = 1, M != 1일 때, 하나의 곡선을 추가할 수 있으므로 해당 조건까지 수행한다.
즉, 사용된 곡선의 개수는 (min(M,N) - 1) * 2 + (M == 1 ? 0 : 1) 이다.

2. 도착 지점의 좌표를 구하기 위해 생각한 점은 곡선의 개수와 같이 좌표또한 규칙성이 있다고 생각하여 규칙성을 찾으려고 여러 테스트케이스를 나열하였다.
(M,N) - total curve count / end point(e_m,e_n)
2,2 - 2 / 2,1   3,2 - 3 / 2,1   4,2 - 3 / 2,1   5,2 - 3 / 2,1   6,2 - 3 / 2,1
2,3 - 2 / 2,1   3,3 - 4 / 2,2   4,3 - 5 / 3,2   5,3 - 5 / 4,2   6,3 - 5 / 5,2
2,4 - 2 / 2,1   3,4 - 4 / 2,3   4,4 - 6 / 3,2   5,4 - 5 / 3,2   6,4 - 7 / 3,2
2,5 - 2 / 2,1   3,5 - 4 / 2,4   4,5 - 6 / 3,2   5,5 - 8 / 3,2   6,5 - 9 / 4,3

a. M or N == 2일 때, 시작 지점에서 (1,0)을 더한 값이 도착지점임을 알 수 있다.

b. a의 규칙과 1번에서 외각을 줄여나가는 과정에서 만들어지는 도형에서 M,N의 수에 따라 도착지점을 예상할 수 있다.
(M,N)의 도형에서 외곽에 2개의 곡선을 사용하였을 때, 남은 도형에서의 시작지점은 이전 도형의 시작 지점(m,n)에서 (1,1)을 더한 값이 된다.

따라서, 외곽에서 곡선이 사용된 횟수 l를 시작지점에 더하여 마지막 도형의 시작지점인 (1+l,1+l)에서 남은 도형의 m,n의 값에 따라
도착 지점을 예상할 수 있다. (추가로, m의 값이 n의 값보다 우선순위가 높기 때문에 조건을 각각 분리하였다.)

m = 2면, (시작지점_x + 1, 시작지점_y)
m = 1면, (시작지점_x, 시작지점_y + n - 1) (n = 남은도형의 y축 길이)
n = 2면, (시작지점_x + 1, 시작지점_y)
n = 1면, (시작지점_x, 시작지점_y + m - 1) (m = 남은도형의 x축 길이)

(좌표의 정보 : | = x, ㅡ = y)
(1,2)인 도형 => ㅁㅁ
(2,1) => ㅁ
         ㅁ
 */
public class BOJ1959 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int M,N;
    static long ans;

    //static int[][] snail;
    //static final int MAX = 2_100_000_000;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        int c = Math.min(M,N) - 1;

        ans = (M - c == 1 ? 0 : 1) + (c * 2L);

        int l = Math.min(M / 2 + (M % 2 == 0 ? -1 : 0), N / 2 + (N % 2 == 0 ? -1 : 0));

        int m = M - (l * 2);
        int n = N - (l * 2);

        int e_x,e_y;

        e_x = e_y = 1 + l;

        if(m == 2) e_x++;
        else if(m == 1) e_y += (n - 1);
        else if(n == 2) e_x++;
        else if(n == 1) e_x += (m - 1);

        System.out.println(ans);
        System.out.println(e_x + " " + e_y);
    }

    // heap overflow
    /*private static void solve_fail() {
        for(int i = 2; i <= MAX; i++) {
            for(int j = 2; j <= MAX; j++) {
                snail[i][j] = snail[i][j] != 1 ? snail[i][j] : snail[j][i - 1] + 1;
                snail[j][i] = snail[j][i] != 1 ? snail[j][i] : snail[i][j - 1] + 1;
            }
        }

        System.out.println(snail[M][N]);
    }*/

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        M = Integer.parseInt(input[0]);
        N = Integer.parseInt(input[1]);

        // heap overflow
        //snail = new int[MAX + 1][MAX + 1];

        //for(int i = 1; i <= MAX; i++) Arrays.fill(snail[i], 1);
    }
}
