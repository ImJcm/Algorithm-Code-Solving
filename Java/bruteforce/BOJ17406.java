package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
배열 돌리기 4

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	22941	9728	6698	39.444%
문제
크기가 N×M 크기인 배열 A가 있을때, 배열 A의 값은 각 행에 있는 모든 수의 합 중 최솟값을 의미한다. 배열 A가 아래와 같은 경우 1행의 합은 6, 2행의 합은 4, 3행의 합은 15이다. 따라서, 배열 A의 값은 4이다.

1 2 3
2 1 1
4 5 6
배열은 회전 연산을 수행할 수 있다. 회전 연산은 세 정수 (r, c, s)로 이루어져 있고, 가장 왼쪽 윗 칸이 (r-s, c-s), 가장 오른쪽 아랫 칸이 (r+s, c+s)인 정사각형을 시계 방향으로 한 칸씩 돌린다는 의미이다. 배열의 칸 (r, c)는 r행 c열을 의미한다.

예를 들어, 배열 A의 크기가 6×6이고, 회전 연산이 (3, 4, 2)인 경우에는 아래 그림과 같이 회전하게 된다.

A[1][1]   A[1][2] → A[1][3] → A[1][4] → A[1][5] → A[1][6]
             ↑                                       ↓
A[2][1]   A[2][2]   A[2][3] → A[2][4] → A[2][5]   A[2][6]
             ↑         ↑                   ↓         ↓
A[3][1]   A[3][2]   A[3][3]   A[3][4]   A[3][5]   A[3][6]
             ↑         ↑                   ↓         ↓
A[4][1]   A[4][2]   A[4][3] ← A[4][4] ← A[4][5]   A[4][6]
             ↑                                       ↓
A[5][1]   A[5][2] ← A[5][3] ← A[5][4] ← A[5][5] ← A[5][6]

A[6][1]   A[6][2]   A[6][3]   A[6][4]   A[6][5]   A[6][6]
회전 연산이 두 개 이상이면, 연산을 수행한 순서에 따라 최종 배열이 다르다.

다음은 배열 A의 크기가 5×6이고, 회전 연산이 (3, 4, 2), (4, 2, 1)인 경우의 예시이다.

1 2 3 2 5 6
3 8 7 2 1 3
8 2 3 1 4 5
3 4 5 1 1 1
9 3 2 1 4 3
1 8 2 3 2 5
3 2 3 7 2 6
8 4 5 1 1 3
3 3 1 1 4 5
9 2 1 4 3 1
1 8 2 3 2 5
3 2 3 7 2 6
3 8 4 1 1 3
9 3 5 1 4 5
2 1 1 4 3 1
배열 A	(3, 4, 2) 연산 수행 후	(4, 2, 1) 연산 수행 후
1 2 3 2 5 6
3 8 7 2 1 3
8 2 3 1 4 5
3 4 5 1 1 1
9 3 2 1 4 3
1 2 3 2 5 6
3 8 7 2 1 3
3 8 2 1 4 5
9 4 3 1 1 1
3 2 5 1 4 3
1 8 2 3 2 5
3 8 2 7 2 6
3 4 3 1 1 3
9 2 1 1 4 5
3 5 1 4 3 1
배열 A	(4, 2, 1) 연산 수행 후	(3, 4, 2) 연산 수행 후
배열 A에 (3, 4, 2), (4, 2, 1) 순서로 연산을 수행하면 배열 A의 값은 12, (4, 2, 1), (3, 4, 2) 순서로 연산을 수행하면 15 이다.

배열 A와 사용 가능한 회전 연산이 주어졌을 때, 배열 A의 값의 최솟값을 구해보자. 회전 연산은 모두 한 번씩 사용해야 하며, 순서는 임의로 정해도 된다.

입력
첫째 줄에 배열 A의 크기 N, M, 회전 연산의 개수 K가 주어진다.

둘째 줄부터 N개의 줄에 배열 A에 들어있는 수 A[i][j]가 주어지고, 다음 K개의 줄에 회전 연산의 정보 r, c, s가 주어진다.

출력
배열 A의 값의 최솟값을 출력한다.

제한
3 ≤ N, M ≤ 50
1 ≤ K ≤ 6
1 ≤ A[i][j] ≤ 100
1 ≤ s
1 ≤ r-s < r < r+s ≤ N
1 ≤ c-s < c < c+s ≤ M
예제 입력 1
5 6 2
1 2 3 2 5 6
3 8 7 2 1 3
8 2 3 1 4 5
3 4 5 1 1 1
9 3 2 1 4 3
3 4 2
4 2 1
예제 출력 1
12
출처
문제를 만든 사람: baekjoon
알고리즘 분류
구현
브루트포스 알고리즘
백트래킹
 */
/*
알고리즘 핵심
1. 회전 기준 (r,c)에서 s~1까지 시계방향 회전과 반시계 방향 회전을 수행하는 것을 dfs로 모든 경우를 검사하여 최소 행의 값을 업데이트한다.
 */
public class BOJ17406 {
    static class BOJ17406_rotate {
        int r,c,s;
        boolean visited;

        BOJ17406_rotate(int r, int c, int s) {
            this.r = r;
            this.c = c;
            this.s = s;
            this.visited = false;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,K,ans;
    static int[][] arr;
    static ArrayList<BOJ17406_rotate> rotate_orders;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        dfs(0);

        System.out.println(ans);
    }

    static void dfs(int depth) {
        if(depth == rotate_orders.size()) {
            check_each_row_min_value();
            return;
        }

        for(BOJ17406_rotate r : rotate_orders) {
            if(r.visited) continue;
            r.visited = true;
            rotate_arr(r);
            dfs(depth+1);
            r.visited = false;
            back_rotate_arr(r);
        }
    }

    static void rotate_arr(BOJ17406_rotate rot) {
        for(int s=rot.s;s>0;s--) {
            int low_r = (rot.r - s);
            int high_r = (rot.r + s);
            int low_c = (rot.c - s);
            int high_c = (rot.c + s);

            int left_top = arr[low_r][low_c];
            //int left_bottom = arr[high_r][low_c];
            //int right_top = arr[low_r][high_c];
            //int right_bottom = arr[high_r][high_c];

            // ↑
            for(int r=low_r;r<high_r;r++) {
                arr[r][low_c] = arr[r+1][low_c];
            }

            // ←
            for(int c=low_c;c<high_c;c++) {
                arr[high_r][c] = arr[high_r][c+1];
            }

            // ↓
            for(int r=high_r;r>low_r;r--) {
                arr[r][high_c] = arr[r-1][high_c];
            }

            // →
            for(int c=high_c;c>low_c;c--) {
                arr[low_r][c] = arr[low_r][c-1];
            }

            arr[low_r][low_c+1] = left_top;
        }
    }

    static void back_rotate_arr(BOJ17406_rotate rot) {
        for(int s=rot.s;s>0;s--) {
            int low_r = (rot.r - s);
            int high_r = (rot.r + s);
            int low_c = (rot.c - s);
            int high_c = (rot.c + s);

            //int left_top = arr[low_r][low_c];
            int left_bottom = arr[high_r][low_c];
            //int right_top = arr[low_r][high_c];
            //int right_bottom = arr[high_r][high_c];

            // ↓
            for(int r=high_r;r>low_r;r--) {
                arr[r][low_c] = arr[r-1][low_c];
            }

            // ←
            for(int c=low_c;c<high_c;c++) {
                arr[low_r][c] = arr[low_r][c+1];
            }

            // ↑
            for(int r=low_r;r<high_r;r++) {
                arr[r][high_c] = arr[r+1][high_c];
            }

            // →
            for(int c=high_c;c>low_c;c--) {
                arr[high_r][c] = arr[high_r][c-1];
            }

            arr[high_r][low_c+1] = left_bottom;
        }
    }

    static void check_each_row_min_value() {
        for(int r=1;r<=N;r++) {
            int sum = 0;

            for(int c=1;c<=M;c++) {
                sum += arr[r][c];
            }

            ans = Math.min(ans, sum);
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        K = Integer.parseInt(input[2]);

        arr = new int[N+1][M+1];
        rotate_orders = new ArrayList<>();

        ans = Integer.MAX_VALUE;

        for(int i=1;i<=N;i++) {
            input = br.readLine().split(" ");
            for(int j=1;j<=M;j++) {
                arr[i][j] = Integer.parseInt(input[j-1]);
            }
        }

        for(int k=0;k<K;k++) {
            input = br.readLine().split(" ");
            int r = Integer.parseInt(input[0]);
            int c = Integer.parseInt(input[1]);
            int s = Integer.parseInt(input[2]);

            rotate_orders.add(new BOJ17406_rotate(r,c,s));
        }
    }
}
