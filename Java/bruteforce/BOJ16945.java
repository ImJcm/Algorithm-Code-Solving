package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

/*
매직 스퀘어로 변경하기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	1544	859	610	52.003%
문제
1부터 N2까지의 수가 하나씩 채워져 있는 크기가 N×N인 배열이 있고, 이 배열의 모든 행, 열, 길이가 N인 대각선의 합이 모두 같을 때, 매직 스퀘어라고 한다.

크기가 3×3인 배열 A가 주어졌을 때, 이 배열을 매직 스퀘어로 변경하려고 한다. 한 칸에 있는 수 a를 b로 변경하는 비용은 |a - b| 이다. 예를 들어, 아래와 같은 경우를 살펴보자.

5 3 4
1 5 8
6 4 2
이 배열의 수를 아래와 같이 변경하면 매직 스퀘어가 되고, 비용은 |5 - 8| + |8 - 9| + |4 - 7| = 7 이다.

8 3 4
1 5 9
6 7 2
3×3 크기의 배열 A가 주어졌을 때, 이 배열을 매직 스퀘어로 변경하는 비용의 최솟값을 구해보자. 배열 A는 1부터 9까지의 수로만 채워져 있고, 매직 스퀘어로 변경한 배열도 1부터 9까지의 수로만 채워져 있어야 한다.

입력
총 세 개의 줄에 걸쳐서 배열 A의 원소가 주어진다.

출력
첫째 줄에 배열 A를 매직 스퀘어로 변경하는 비용의 최솟값을 출력한다.

예제 입력 1
4 9 2
3 5 7
8 1 5
예제 출력 1
1
가장 오른쪽 아랫칸의 수를 6으로 변경하면 매직 스퀘어가 되고, 비용은 |5 - 6| = 1이다.

예제 입력 2
4 8 2
4 5 7
6 1 6
예제 출력 2
4
4 9 2
3 5 7
8 1 6
으로 변경할 수 있고, 비용은 |8 - 9| + |4 - 3| + |6 - 8| = 4이다.

출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: jh05013, kyo20111
알고리즘 분류
브루트포스 알고리즘
백트래킹
 */
/*
알고리즘 핵심
1. 매직스퀘어의 개념 - 3x3 배열판에서 1~9까지 중복없는 숫자로 각 행,열,대각의 합이 같은 배열을 의미한다.
2. 각 (r,c) 좌표의 값을 1~9까지의 값으로 바꾸면서 중복없는 배열을 만든 후, 기저사례에서 각 행,열,대각의 합이 같은지를 비교한 후,
최소 비용을 업데이트한다.
 */
public class BOJ16945 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[][] arr;
    static int ans;
    static boolean[] isUsed;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        dfs(0,0);

        System.out.println(ans);
    }

    static void dfs(int depth, int cost) {
        if(depth == 9) {
            if(check_magic_square()) {
                ans = Math.min(ans, cost);
            }
            return;
        }

        for(int i = 1; i <= 9; i++) {
            if(isUsed[i]) continue;
            int r = depth / 3;
            int c = depth % 3;

            int o_v = arr[r][c];

            isUsed[i] = true;
            arr[r][c] = i;
            dfs(depth + 1, cost + Math.abs(o_v - i));
            isUsed[i] = false;
            arr[r][c] = o_v;
        }
    }

    static boolean check_magic_square() {
        HashSet<Integer> set = new HashSet<>();

        int row_1 = arr[0][0] + arr[0][1] + arr[0][2];
        int row_2 = arr[1][0] + arr[1][1] + arr[1][2];
        int row_3 = arr[2][0] + arr[2][1] + arr[2][2];

        int col_1 = arr[0][0] + arr[1][0] + arr[2][0];
        int col_2 = arr[0][1] + arr[1][1] + arr[2][1];
        int col_3 = arr[0][2] + arr[1][2] + arr[2][2];

        int diagonal_1 = arr[0][0] + arr[1][1] + arr[2][2];
        int diagonal_2 = arr[2][0] + arr[1][1] + arr[0][2];

        set.add(row_1);
        set.add(row_2);
        set.add(row_3);

        set.add(col_1);
        set.add(col_2);
        set.add(col_3);

        set.add(diagonal_1);
        set.add(diagonal_2);

        if(set.size() == 1) {
            return true;
        }

        return false;
    }

    static void init_setting() throws IOException {
        arr = new int[3][3];
        isUsed = new boolean[10];

        ans = Integer.MAX_VALUE;

        for(int i = 0; i < 3; i++) {
            String[] input = br.readLine().split(" ");
            for(int j = 0; j < 3; j++) {
                arr[i][j] = Integer.parseInt(input[j]);
            }
        }
    }
}
