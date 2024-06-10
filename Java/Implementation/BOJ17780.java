package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/*
새로운 게임

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초	512 MB	4016	2001	1514	50.805%
문제
재현이는 주변을 살펴보던 중 체스판과 말을 이용해서 새로운 게임을 만들기로 했다. 새로운 게임은 크기가 N×N인 체스판에서 진행되고, 사용하는 말의 개수는 K개이다. 말은 원판모양이고, 하나의 말 위에 다른 말을 올릴 수 있다. 체스판의 각 칸은 흰색, 빨간색, 파란색 중 하나로 색칠되어있다.

게임은 체스판 위에 말 K개를 놓고 시작한다. 말은 1번부터 K번까지 번호가 매겨져 있고, 이동 방향도 미리 정해져 있다. 이동 방향은 위, 아래, 왼쪽, 오른쪽 4가지 중 하나이다.

턴 한 번은 1번 말부터 K번 말까지 순서대로 이동시키는 것이다. 한 말이 이동할 때 위에 올려져 있는 말도 함께 이동하며, 가장 아래에 있는 말만 이동할 수 있다. 말의 이동 방향에 있는 칸에 따라서 말의 이동이 다르며 아래와 같다. 턴이 진행되던 중에 말이 4개 이상 쌓이는 순간 게임이 종료된다.

A번 말이 이동하려는 칸이
흰색인 경우에는 그 칸으로 이동한다. 이동하려는 칸에 말이 이미 있는 경우에는 가장 위에 A번 말을 올려놓는다.
A번 말의 위에 다른 말이 있는 경우에는 A번 말과 위에 있는 모든 말이 이동한다.
예를 들어, A, B, C로 쌓여있고, 이동하려는 칸에 D, E가 있는 경우에는 A번 말이 이동한 후에는 D, E, A, B, C가 된다.
빨간색인 경우에는 이동한 후에 A번 말과 그 위에 있는 모든 말의 쌓여있는 순서를 반대로 바꾼다.
A, B, C가 이동하고, 이동하려는 칸에 말이 없는 경우에는 C, B, A가 된다.
A, D, F, G가 이동하고, 이동하려는 칸에 말이 E, C, B로 있는 경우에는 E, C, B, G, F, D, A가 된다.
파란색인 경우에는 A번 말의 이동 방향을 반대로 하고 한 칸 이동한다. 방향을 반대로 한 후에 이동하려는 칸이 파란색인 경우에는 이동하지 않고 방향만 반대로 바꾼다.
체스판을 벗어나는 경우에는 파란색과 같은 경우이다.
다음은 크기가 4×4인 체스판 위에 말이 4개 있는 경우이다.



첫 번째 턴은 다음과 같이 진행된다.


두 번째 턴은 다음과 같이 진행된다.


체스판의 크기와 말의 위치, 이동 방향이 모두 주어졌을 때, 게임이 종료되는 턴의 번호를 구해보자.

입력
첫째 줄에 체스판의 크기 N, 말의 개수 K가 주어진다. 둘째 줄부터 N개의 줄에 체스판의 정보가 주어진다. 체스판의 정보는 정수로 이루어져 있고, 각 정수는 칸의 색을 의미한다. 0은 흰색, 1은 빨간색, 2는 파란색이다.

다음 K개의 줄에 말의 정보가 1번 말부터 순서대로 주어진다. 말의 정보는 세 개의 정수로 이루어져 있고, 순서대로 행, 열의 번호, 이동 방향이다. 행과 열의 번호는 1부터 시작하고, 이동 방향은 4보다 작거나 같은 자연수이고 1부터 순서대로 →, ←, ↑, ↓의 의미를 갖는다.

같은 칸에 말이 두 개 이상 있는 경우는 입력으로 주어지지 않는다.

출력
게임이 종료되는 턴의 번호를 출력한다. 그 값이 1,000보다 크거나 절대로 게임이 종료되지 않는 경우에는 -1을 출력한다.

제한
4 ≤ N ≤ 12
4 ≤ K ≤ 10
예제 입력 1
4 4
0 0 2 0
0 0 1 0
0 0 1 2
0 2 0 0
2 1 1
3 2 3
2 2 1
4 1 2
예제 출력 1
-1
예제 입력 2
4 4
0 0 0 0
0 0 0 0
0 0 0 0
0 0 0 0
1 1 1
1 2 1
1 3 1
1 4 1
예제 출력 2
1
예제 입력 3
4 4
0 0 0 0
0 0 0 0
0 0 0 0
0 0 0 0
1 1 1
1 2 1
1 3 1
2 4 3
예제 출력 3
1
예제 입력 4
4 4
0 0 0 0
0 0 0 0
0 0 0 0
0 0 0 0
1 1 1
1 2 1
1 3 1
3 3 3
예제 출력 4
2
예제 입력 5
4 4
0 0 2 0
0 0 1 0
0 0 1 2
0 2 0 0
2 1 1
3 2 3
2 2 1
4 1 3
예제 출력 5
8
예제 입력 6
6 10
0 1 2 0 1 1
1 2 0 1 1 0
2 1 0 1 1 0
1 0 1 1 0 2
2 0 1 2 0 1
0 2 1 0 2 1
1 1 1
2 2 2
3 3 4
4 4 1
5 5 3
6 6 2
1 6 3
6 1 2
2 4 3
4 2 1
예제 출력 6
9
출처
문제를 만든 사람: baekjoon
알고리즘 분류
구현
시뮬레이션
 */
/*
정답 알고리즘 핵심
1. (N+1) x (N+1) 칸으로 체스판을 구성하여 외곽을 파란색으로 지정하는 것
2. 흰색, 빨간색, 파란색 로직은 문제에서 주어진 요구사항에 맞춰 구현
3. 모든 체스는 각 턴마다 순서대로 로직을 수행하므로 순서를 저장하는 별도의 ArrayList를 이용한다. (java 특성상 객체의 주소값을 보관하기 때문에 적합)
4. 체스말은 같은 칸에 겹칠 수 있고, 가장 아래에 있는 체스말만 움직일 수 있으므로 해당 체스말이 움직일 수 있는 여부를 확인할 수 있는 BOJ17780_chess.move를 통해 움직일 수 있는 체스 구별
5. 각 흰색, 빨간색, 파란색에서 겹쳐지는 코드가 있었기 때문에 별도의 함수를 호출하여 동작하도록 분리하였다.
6. common_operation과 uncommon_operation을 나눈 이유는 이동하는 체스말이 다음 이동하는 구역의 색상이 파란색인 경우, 방향을 반대로 한칸 움직이는데 움직인 칸의 색상이 파란색인 경우 방향만 바꿔주는 로직을 수행하고,
파란색이 아닌 흰색, 빨간색인 경우, 해당하는 색에 함수를 호출하여 로직 수행
7. 체스말이 4개 이상이 되는 시점에서 종료하므로 체스말이 추가될 수 있는 common_operation 내부에 기저사례로 flag 변수를 설정
 */
public class BOJ17780 {
    static class BOJ17780_chess {
        int r,c;
        int d;
        boolean move;

        BOJ17780_chess(int r,int c,int d, boolean move) {
            this.r = r;
            this.c = c;
            this.d = d;
            this.move = move;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,K,turns;
    static boolean flag;
    static int[][] boards_color,directions = {{0,0},{0,1},{0,-1},{-1,0},{1,0}}; // (1,→), (2,←), (3,↑), (4,↓)
    static ArrayList<BOJ17780_chess>[][] boards;
    static ArrayList<BOJ17780_chess> orders;


    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        while(turns++ <= 1000) {
            move_chess();

            if(flag) {
                System.out.println(turns - 1);
                break;
            }
        }

        if(turns == 1002) {
            System.out.println(-1);
        }
    }

    static void common_operation(BOJ17780_chess c, int n_r, int n_c) {
        int o_r = c.r;
        int o_c = c.c;

        for(BOJ17780_chess cc : boards[c.r][c.c]) {
            boards[n_r][n_c].add(cc);
            cc.r = n_r;
            cc.c = n_c;
        }

        if(boards[n_r][n_c].size() >= 4) {
            flag = true;
        }

        boards[o_r][o_c].clear();
    }

    static void uncommon_operation(BOJ17780_chess c) {
        for(BOJ17780_chess cc : boards[c.r][c.c]) {
            cc.d = c.d;
        }
    }

    static void white_operation(BOJ17780_chess c, int n_r, int n_c) {
        if(!boards[n_r][n_c].isEmpty()) {
            c.move = false;
        }

        common_operation(c,n_r,n_c);
    }

    static void red_operation(BOJ17780_chess c, int n_r, int n_c) {
        c.move = false;
        Collections.reverse(boards[c.r][c.c]);

        if(boards[n_r][n_c].isEmpty()) {
            boards[c.r][c.c].get(0).move = true;
        }

        common_operation(c,n_r,n_c);
    }

    static void blue_operation(BOJ17780_chess c) {
        c.d = (c.d == 1 || c.d == 2) ?
                (c.d == 1 ? 2 : 1) : (c.d == 3 ? 4 : 3);

        int n_r = c.r + directions[c.d][0];
        int n_c = c.c + directions[c.d][1];

        if(boards_color[n_r][n_c] != 2) {
            if(boards_color[n_r][n_c] == 1) {
                red_operation(c,n_r,n_c);
            } else {
                white_operation(c,n_r,n_c);
            }
        } else {
            c.d = (c.d == 1 || c.d == 2) ?
                    (c.d == 1 ? 2 : 1) : (c.d == 3 ? 4 : 3);
            uncommon_operation(c);
        }
    }

    static void move_chess() {
        for(BOJ17780_chess c : orders) {
            if (!c.move) continue;

            int n_r = c.r + directions[c.d][0];
            int n_c = c.c + directions[c.d][1];

            int color = boards_color[n_r][n_c];

            switch (color) {
                case 0: // white
                    white_operation(c,n_r,n_c);
                    break;
                case 1: // red
                    red_operation(c,n_r,n_c);
                    break;
                case 2: // blue
                    blue_operation(c);
                    break;
            }
        }
    }

    /*
        TC#6 올바르지 않은 결과 도출
        이유 : 이동하는 칸이 파랑인 경우, 반대방향으로 한칸 이동하는데 이동한 칸의 색을 파란색인 경우만 고려하고 빨간색인 경우를 고려하지 못해서 오답이 나오는 것으로 예상한다.
        따라서, 흰색, 빨간색, 파란색의 경우에 해당하는 동작을 수행하는 함수를 나누어 호출하는 형태로 바꾸는 것이 좋아보인다.
     */
    static void move_chess_WA() {
        for(BOJ17780_chess c : orders) {
            if(!c.move) continue;

            int origin_r = c.r;
            int origin_c = c.c;
            int nr = c.r + directions[c.d][0];
            int nc = c.c + directions[c.d][1];

            int color = boards_color[nr][nc];
            boolean dup = true;

            c.r = nr;
            c.c = nc;

            switch (color) {
                case 0: // white
                    if(!boards[nr][nc].isEmpty()) {
                        c.move = false;
                    }
                    break;
                case 1: // red
                    c.move = false;
                    Collections.reverse(boards[origin_r][origin_c]);

                    if(boards[nr][nc].isEmpty()) {
                        boards[origin_r][origin_c].get(0).move = true;
                    }
                    break;
                case 2: // blue
                    c.r = origin_r;
                    c.c = origin_c;

                    c.d = (c.d == 1 || c.d == 2) ?
                            (c.d == 1 ? 2 : 1) : (c.d == 3 ? 4 : 3);

                    nr = c.r + directions[c.d][0];
                    nc = c.c + directions[c.d][1];

                    if(boards_color[nr][nc] != 2) {
                        c.r = nr;
                        c.c = nc;
                    } else {
                        dup = false;
                        c.d = (c.d == 1 || c.d == 2) ?
                                (c.d == 1 ? 2 : 1) : (c.d == 3 ? 4 : 3);
                    }
                    break;
            }

            if(dup) {
                for(BOJ17780_chess cc : boards[origin_r][origin_c]) {
                    boards[nr][nc].add(cc);
                    cc.r = nr;
                    cc.c = nc;
                }

                if(boards[nr][nc].size() >= 4) {
                    flag = true;
                }

                boards[origin_r][origin_c].clear();
            } else {
                for(BOJ17780_chess cc : boards[origin_r][origin_c]) {
                    cc.d = c.d;
                }
            }

            // 문제에서 주어진 체스판의 크기 = N * N에서 (N+1) * (N+1)로 설정하여 외곽을 모두 파란색 타일로 지정하여 외곽 체크를 할 필요가 없어진다.
            /*
                1 : 체스판을 내부인 경우
                2 : 체스판을 벗어나는 경우
             */
            /*int op = 1;

            if(nr < 1 || nr > N || nc < 1 || nc > N) {
                op = 2;
            }

            switch (op) {
                case 1:
                    int color = boards_color[nr][nc];
                    boolean dup = true;

                    c.r = nr;
                    c.c = nc;

                    switch (color) {
                        case 0: // white
                            if(!boards[nr][nc].isEmpty()) {
                                c.move = false;
                            }
                            break;
                        case 1: // red
                            c.move = false;
                            Collections.reverse(boards[origin_r][origin_c]);

                            if(boards[nr][nc].isEmpty()) {
                                boards[origin_r][origin_c].get(0).move = true;
                            }
                            break;
                        case 2: // blue
                            c.r = origin_r;
                            c.c = origin_c;

                            c.d = (c.d == 1 || c.d == 2) ?
                                    (c.d == 1 ? 2 : 1) : (c.d == 3 ? 4 : 3);

                            nr = c.r + directions[c.d][0];
                            nc = c.c + directions[c.d][1];

                            if(boards_color[nr][nc] != 2) {
                               c.r = nr;
                               c.c = nc;
                            } else {
                                dup = false;
                                c.d = (c.d == 1 || c.d == 2) ?
                                        (c.d == 1 ? 2 : 1) : (c.d == 3 ? 4 : 3);
                            }
                            break;
                    }

                    if(dup) {
                        for(BOJ17780_chess cc : boards[origin_r][origin_c]) {
                            boards[nr][nc].add(cc);
                            cc.r = nr;
                            cc.c = nc;
                        }

                        if(boards[nr][nc].size() >= 4) {
                            flag = true;
                        }

                        boards[origin_r][origin_c].clear();
                    } else {
                        for(BOJ17780_chess cc : boards[origin_r][origin_c]) {
                            cc.d = c.d;
                        }
                    }
                    break;
                case 2:
                    c.d = (c.d == 1 || c.d == 2) ?
                            (c.d == 1 ? 2 : 1) : (c.d == 3 ? 4 : 3);
                    break;
            }*/
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);
        turns = 1;
        flag = false;

        boards = new ArrayList[N+2][N+2];
        boards_color = new int[N+2][N+2];
        orders = new ArrayList<>();

        for(int i=1;i<=N;i++) {
            input = br.readLine().split(" ");
            for(int j=1;j<=N;j++) {
                boards[i][j] = new ArrayList<>();
                boards_color[i][j] = Integer.parseInt(input[j-1]);
            }
        }

        // 외곽 파란색 칸 처리
        for(int i=0;i<=N+1;i++) {
            boards_color[0][i] = 2;
            boards_color[i][0] = 2;
            boards_color[N+1][i] = 2;
            boards_color[i][N+1] = 2;
        }

        for(int i=1;i<=K;i++) {
            input = br.readLine().split(" ");

            int r = Integer.parseInt(input[0]);
            int c = Integer.parseInt(input[1]);
            int d = Integer.parseInt(input[2]);

            BOJ17780_chess chess = new BOJ17780_chess(r,c,d,true);

            boards[r][c].add(chess);
            orders.add(chess);
        }

    }
}
