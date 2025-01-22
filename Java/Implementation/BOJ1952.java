package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
달팽이2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	6185	3113	2613	53.327%
문제
M줄 N칸으로 되어 있는 표 위에, 달팽이 모양으로 선을 그리려고 한다.

ㅇ




위의 그림은 M=5, N=3의 예이다. 이제 표의 왼쪽 위 칸(ㅇ)에서 시작하여, 오른쪽으로 선을 그려 나간다. 표의 바깥 또는 이미 그려진 칸에 닿아서 더 이상 이동할 수 없게 되면, 시계방향으로 선을 꺾어서 그려나간다.

ㅇ	→	↘
↗	↘	↓
↑	↓	↓
↑	끝	↓
↖	←	↙
위의 표는 선을 그려 나간 모양을 나타낸 것이다. 선이 꺾어진 부분은 대각선으로 나타내었다. 표의 모든 칸이 채워질 때까지, 선을 몇 번 꺾게 될까?

입력
첫째 줄에 M과 N이 빈 칸을 사이에 두고 주어진다. (2 ≤ M, N ≤ 100)

출력
첫째 줄에 표의 모든 칸이 채워질 때까지 선이 꺾어지는 횟수를 출력한다.

예제 입력 1
5 3
예제 출력 1
5
알고리즘 분류
수학
구현
시뮬레이션
메모
 */
/*
알고리즘 핵심
구현 + 수학
(M,N)의 여러 케이스를 직접 값을 구했을 때 규칙성을 발견하였다.
(5,3)
o   ㅡ  \
/   \   |
|   |   |
|   #   |
\   ㅡ  /
에서 상단 M = 1부분을 제거하면 (4,3) + (1,3)으로 나눌 수 있다.
이때, (4,3) 내부에서 선이 꺽이는 횟수는 90도 회전시켰을 때 (3,4)와 같아진다는 것을 알 수 있다.
o   ㅡ   ㅡ  \
/   ㅡ   #   |
\   ㅡ   ㅡ  /
이를 다시 적용하면 (3,4) = (4,2) + 1임을 알 수 있다.

즉, 점화식은 다음과 같다. (M,N) = (N, M - 1) + 1 (if m or n = 1, ret 1);
 */
public class BOJ1952 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int M,N,ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        ans = dfs(M,N);

        System.out.println(ans);
    }

    private static int dfs(int m, int n) {
        if(m == 1 || n == 1) return 1;
        return dfs(n, m - 1) + 1;
    }


    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        M = Integer.parseInt(input[0]);
        N = Integer.parseInt(input[1]);

    }
}
