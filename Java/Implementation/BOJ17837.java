package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/*
새로운 게임 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초	512 MB	14562	7220	4553	47.780%
문제
재현이는 주변을 살펴보던 중 체스판과 말을 이용해서 새로운 게임을 만들기로 했다. 새로운 게임은 크기가 N×N인 체스판에서 진행되고, 사용하는 말의 개수는 K개이다. 말은 원판모양이고, 하나의 말 위에 다른 말을 올릴 수 있다. 체스판의 각 칸은 흰색, 빨간색, 파란색 중 하나로 색칠되어있다.

게임은 체스판 위에 말 K개를 놓고 시작한다. 말은 1번부터 K번까지 번호가 매겨져 있고, 이동 방향도 미리 정해져 있다. 이동 방향은 위, 아래, 왼쪽, 오른쪽 4가지 중 하나이다.

턴 한 번은 1번 말부터 K번 말까지 순서대로 이동시키는 것이다. 한 말이 이동할 때 위에 올려져 있는 말도 함께 이동한다. 말의 이동 방향에 있는 칸에 따라서 말의 이동이 다르며 아래와 같다. 턴이 진행되던 중에 말이 4개 이상 쌓이는 순간 게임이 종료된다.

A번 말이 이동하려는 칸이
흰색인 경우에는 그 칸으로 이동한다. 이동하려는 칸에 말이 이미 있는 경우에는 가장 위에 A번 말을 올려놓는다.
A번 말의 위에 다른 말이 있는 경우에는 A번 말과 위에 있는 모든 말이 이동한다.
예를 들어, A, B, C로 쌓여있고, 이동하려는 칸에 D, E가 있는 경우에는 A번 말이 이동한 후에는 D, E, A, B, C가 된다.
빨간색인 경우에는 이동한 후에 A번 말과 그 위에 있는 모든 말의 쌓여있는 순서를 반대로 바꾼다.
A, B, C가 이동하고, 이동하려는 칸에 말이 없는 경우에는 C, B, A가 된다.
A, D, F, G가 이동하고, 이동하려는 칸에 말이 E, C, B로 있는 경우에는 E, C, B, G, F, D, A가 된다.
파란색인 경우에는 A번 말의 이동 방향을 반대로 하고 한 칸 이동한다. 방향을 반대로 바꾼 후에 이동하려는 칸이 파란색인 경우에는 이동하지 않고 가만히 있는다.
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
예제 출력 5
7
출처
문제를 만든 사람: baekjoon
알고리즘 분류
구현
시뮬레이션
 */
/*
BOJ17780(새로운 게임)의 알고리즘에서 응용된 문제로 차이나는 로직으로 다음과 같다.
1. 각 체스말들은 엎은 말과 함께 움직인다.
2. 현재 움직이는 체스말이 엎은 말들을 기준으로 흰색, 빨간색, 파란색 로직을 적용해야 한다.
2-ex) 2 1 3 순으로 체스말이 존재하는 경우, 1번 체스말이 움직이는 상황
2-1. 흰색인 경우, 다음 칸으로 1 3이 움직인다.
2-2. 빨간색인 경우, 1 3 -> 3 1로 역순이 적용된 상태가 다음칸으로 움직인다.
2-3. 파란색인 경우, 방향을 바꾸고 1 3이 이동 후, 이동한 칸의 색상에 따라 해당하는 로직 수행
3. 움직이려는 곳이 파란색칸이고 또 움직이려는 칸이 파란색인 경우 방향을 바꾸지 않고 멈춘다.

이 문제의 알고리즘의 핵심은 움직이는 말이 엎은 말을 대상으로 부분 배열을 구성하고 해당하는 부분 배열을 대상으로 움직임을 수행해야하는 것
 */
public class BOJ17837 {
    static class BOJ17837_chess {
        int r,c,d;

        BOJ17837_chess(int r,int c,int d) {
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,K,turns;
    static boolean flag = false;
    static int[][] boards_color, directions = {{0,0},{0,1},{0,-1},{-1,0},{1,0}};
    static ArrayList<BOJ17837_chess>[][] boards;
    static ArrayList<BOJ17837_chess> orders,sub_arr;

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

    static void move_chess() {
        for(BOJ17837_chess c : orders) {
            int n_r = c.r + directions[c.d][0];
            int n_c = c.c + directions[c.d][1];
            int color = boards_color[n_r][n_c];

            switch (color) {
                case 0:
                    white_operation(c,n_r,n_c);
                    break;
                case 1:
                    red_operation(c,n_r,n_c);
                    break;
                case 2:
                    blue_operation(c);
                    break;
            }
        }
    }

    static void common_operation(int start_idx, int o_r, int o_c, int n_r, int n_c) {
        ArrayList<BOJ17837_chess> not_use_chess = new ArrayList<>();

        for(int s=start_idx;s<boards[o_r][o_c].size();s++) {
            BOJ17837_chess c = boards[o_r][o_c].get(s);

            boards[n_r][n_c].add(c);
            c.r = n_r;
            c.c = n_c;
            not_use_chess.add(c);
        }

        for(BOJ17837_chess c : not_use_chess) {
            boards[o_r][o_c].remove(c);
        }

        if(boards[n_r][n_c].size() >= 4) {
            flag = true;
        }
    }

    static int find_start_idx(BOJ17837_chess c) {
        return boards[c.r][c.c].indexOf(c);
    }

    static void sub_reverse_arr(int s, int r, int c) {
       sub_arr = new ArrayList<>();

        for(int i=s;i<boards[r][c].size();i++) {
            sub_arr.add(boards[r][c].get(i));
        }

        boards[r][c].removeAll(sub_arr);

        Collections.reverse(sub_arr);

        for(BOJ17837_chess cc : sub_arr) {
            boards[r][c].add(cc);
        }
    }

    static void white_operation(BOJ17837_chess c, int n_r, int n_c) {
        int s = find_start_idx(c);
        common_operation(s,c.r,c.c,n_r,n_c);
    }

    static void red_operation(BOJ17837_chess c, int n_r, int n_c) {
        int s = find_start_idx(c);

        sub_reverse_arr(s,c.r,c.c);

        common_operation(s,c.r,c.c,n_r,n_c);
    }

    static void blue_operation(BOJ17837_chess c) {
        c.d = (c.d == 1 || c.d == 2) ?
                (c.d == 1 ? 2 : 1) : (c.d == 3 ? 4 : 3);

        int n_r = c.r + directions[c.d][0];
        int n_c = c.c + directions[c.d][1];
        int n_color = boards_color[n_r][n_c];

        switch (n_color) {
            case 0:
                white_operation(c,n_r,n_c);
                break;
            case 1:
                red_operation(c,n_r,n_c);
                break;
            case 2:
                break;
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);
        turns = 1;

        boards = new ArrayList[N+2][N+2];   //N+1까지만 해도 무방
        boards_color = new int[N+2][N+2];
        orders = new ArrayList<>();

        for(int i=1;i<N+1;i++) {
            input = br.readLine().split(" ");
            for(int j=1;j<N+1;j++) {
                boards[i][j] = new ArrayList<>();
                boards_color[i][j] = Integer.parseInt(input[j-1]);
            }
        }

        for(int i=0;i<N+2;i++) {
            boards_color[i][N+1] = boards_color[N+1][i] = boards_color[i][0] = boards_color[0][i] = 2;
        }

        for(int i=0;i<K;i++) {
            input = br.readLine().split(" ");

            int r = Integer.parseInt(input[0]);
            int c = Integer.parseInt(input[1]);
            int d = Integer.parseInt(input[2]);

            BOJ17837_chess chess = new BOJ17837_chess(r,c,d);

            boards[r][c].add(chess);
            orders.add(chess);
        }
    }
}
