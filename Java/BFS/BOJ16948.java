package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

/*
데스 나이트

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	7032	4804	4036	69.288%
문제
게임을 좋아하는 큐브러버는 체스에서 사용할 새로운 말 "데스 나이트"를 만들었다. 데스 나이트가 있는 곳이 (r, c)라면, (r-2, c-1), (r-2, c+1), (r, c-2), (r, c+2), (r+2, c-1), (r+2, c+1)로 이동할 수 있다.

크기가 N×N인 체스판과 두 칸 (r1, c1), (r2, c2)가 주어진다. 데스 나이트가 (r1, c1)에서 (r2, c2)로 이동하는 최소 이동 횟수를 구해보자. 체스판의 행과 열은 0번부터 시작한다.

데스 나이트는 체스판 밖으로 벗어날 수 없다.

입력
첫째 줄에 체스판의 크기 N(5 ≤ N ≤ 200)이 주어진다. 둘째 줄에 r1, c1, r2, c2가 주어진다.

출력
첫째 줄에 데스 나이트가 (r1, c1)에서 (r2, c2)로 이동하는 최소 이동 횟수를 출력한다. 이동할 수 없는 경우에는 -1을 출력한다.

예제 입력 1
7
6 6 0 1
예제 출력 1
4
예제 입력 2
6
5 1 0 5
예제 출력 2
-1
예제 입력 3
7
0 3 4 3
예제 출력 3
2
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
board 그래프 내부에 row, col 정보와 해당 위치로 가기 까지의 최소한의 움직인 cnt를 저장하고, BFS로 해당 위치로의 최소 움직임 값만
업데이트하고 q가 모두 비워지면 r2,c2의 min_move_cnt 값을 출력한다. 이때, Integer.MAX_VALUE이면 -1을 출력한다.
 */
public class BOJ16948 {
    public static class BOJ16948_board {
        int row, col;
        int min_move_cnt;

        public BOJ16948_board(int r, int c) {
            this.row = r;
            this.col = c;
            this.min_move_cnt = Integer.MAX_VALUE;
        }

        void setMin_Move_Cnt(int cnt) {
            this.min_move_cnt = cnt;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, r1, r2, c1 ,c2;
    static BOJ16948_board[][] boards;
    static int[][] direction = {{-2,-1}, {-2,1}, {0,-2}, {0,2}, {2,-1}, {2,1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        bfs();

        System.out.println(boards[r2][c2].min_move_cnt == Integer.MAX_VALUE ? -1 : boards[r2][c2].min_move_cnt);
    }

    static void bfs() {
        Queue<BOJ16948_board> q = new LinkedList<>();

        q.offer(boards[r1][c1]);
        boards[r1][c1].min_move_cnt = 0;

        while(!q.isEmpty()) {
            BOJ16948_board now = q.poll();

            for(int[] d : direction) {
                int next_row = now.row + d[0];
                int next_col = now.col + d[1];
                if(next_row < 0 || next_row >= N || next_col < 0 || next_col >= N) {
                    continue;
                }

                if(boards[next_row][next_col].min_move_cnt > boards[now.row][now.col].min_move_cnt + 1) {
                    boards[next_row][next_col].min_move_cnt = boards[now.row][now.col].min_move_cnt + 1;
                    q.offer(boards[next_row][next_col]);
                }
            }
        }
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        String[] input = br.readLine().split(" ");

        r1 = Integer.parseInt(input[0]);
        c1 = Integer.parseInt(input[1]);
        r2 = Integer.parseInt(input[2]);
        c2 = Integer.parseInt(input[3]);

        boards = new BOJ16948_board[N][N];

        IntStream.range(0, N)
                .forEach(i -> IntStream.range(0, N)
                        .forEach(j -> boards[i][j] = new BOJ16948_board(i, j)));
    }
}
