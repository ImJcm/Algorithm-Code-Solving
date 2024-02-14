package BackJoon;

/*
구슬 탈출 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	87903	26520	15134	27.798%
문제
스타트링크에서 판매하는 어린이용 장난감 중에서 가장 인기가 많은 제품은 구슬 탈출이다. 구슬 탈출은 직사각형 보드에 빨간 구슬과 파란 구슬을 하나씩 넣은 다음, 빨간 구슬을 구멍을 통해 빼내는 게임이다.

보드의 세로 크기는 N, 가로 크기는 M이고, 편의상 1×1크기의 칸으로 나누어져 있다. 가장 바깥 행과 열은 모두 막혀져 있고, 보드에는 구멍이 하나 있다. 빨간 구슬과 파란 구슬의 크기는 보드에서 1×1크기의 칸을 가득 채우는 사이즈이고, 각각 하나씩 들어가 있다. 게임의 목표는 빨간 구슬을 구멍을 통해서 빼내는 것이다. 이때, 파란 구슬이 구멍에 들어가면 안 된다.

이때, 구슬을 손으로 건드릴 수는 없고, 중력을 이용해서 이리 저리 굴려야 한다. 왼쪽으로 기울이기, 오른쪽으로 기울이기, 위쪽으로 기울이기, 아래쪽으로 기울이기와 같은 네 가지 동작이 가능하다.

각각의 동작에서 공은 동시에 움직인다. 빨간 구슬이 구멍에 빠지면 성공이지만, 파란 구슬이 구멍에 빠지면 실패이다. 빨간 구슬과 파란 구슬이 동시에 구멍에 빠져도 실패이다. 빨간 구슬과 파란 구슬은 동시에 같은 칸에 있을 수 없다. 또, 빨간 구슬과 파란 구슬의 크기는 한 칸을 모두 차지한다. 기울이는 동작을 그만하는 것은 더 이상 구슬이 움직이지 않을 때 까지이다.

보드의 상태가 주어졌을 때, 최소 몇 번 만에 빨간 구슬을 구멍을 통해 빼낼 수 있는지 구하는 프로그램을 작성하시오.

입력
첫 번째 줄에는 보드의 세로, 가로 크기를 의미하는 두 정수 N, M (3 ≤ N, M ≤ 10)이 주어진다. 다음 N개의 줄에 보드의 모양을 나타내는 길이 M의 문자열이 주어진다. 이 문자열은 '.', '#', 'O', 'R', 'B' 로 이루어져 있다. '.'은 빈 칸을 의미하고, '#'은 공이 이동할 수 없는 장애물 또는 벽을 의미하며, 'O'는 구멍의 위치를 의미한다. 'R'은 빨간 구슬의 위치, 'B'는 파란 구슬의 위치이다.

입력되는 모든 보드의 가장자리에는 모두 '#'이 있다. 구멍의 개수는 한 개 이며, 빨간 구슬과 파란 구슬은 항상 1개가 주어진다.

출력
최소 몇 번 만에 빨간 구슬을 구멍을 통해 빼낼 수 있는지 출력한다. 만약, 10번 이하로 움직여서 빨간 구슬을 구멍을 통해 빼낼 수 없으면 -1을 출력한다.

예제 입력 1
5 5
#####
#..B#
#.#.#
#RO.#
#####
예제 출력 1
1
예제 입력 2
7 7
#######
#...RB#
#.#####
#.....#
#####.#
#O....#
#######
예제 출력 2
5
예제 입력 3
7 7
#######
#..R#B#
#.#####
#.....#
#####.#
#O....#
#######
예제 출력 3
5
예제 입력 4
10 10
##########
#R#...##B#
#...#.##.#
#####.##.#
#......#.#
#.######.#
#.#....#.#
#.#.#.#..#
#...#.O#.#
##########
예제 출력 4
-1
예제 입력 5
3 7
#######
#R.O.B#
#######
예제 출력 5
1
예제 입력 6
10 10
##########
#R#...##B#
#...#.##.#
#####.##.#
#......#.#
#.######.#
#.#....#.#
#.#.##...#
#O..#....#
##########
예제 출력 6
7
예제 입력 7
3 10
##########
#.O....RB#
##########
예제 출력 7
-1
출처
문제를 만든 사람: baekjoon
잘못된 데이터를 찾은 사람: jason9319, tncks0121
데이터를 추가한 사람: kkw564
문제의 오타를 찾은 사람: sky1357, welchsgrape, wurikiji
어색한 표현을 찾은 사람: toysmars
알고리즘 분류
구현
그래프 이론
그래프 탐색
시뮬레이션
너비 우선 탐색
 */
/*
반례 모음)
9 7
#######
#.....#
#.....#
#R....#
#.....#
##...B#
#.....#
#..O..#
#######
correct = -1

9 6
######
#...##
##..O#
##R.B#
#.#.##
#...##
###..#
#.#..#
######
correct = 4

8 7
#######
#.....#
#.....#
####.O#
##..R##
###..##
#...B.#
#######
correct = 4

9 6
######
##..B#
#.#..#
#....#
#...O#
#....#
#.#..#
#R.#.#
######
correct = -1

10 6
######
#..#.#
#.R..#
#.B..#
#.O..#
#...##
#....#
##...#
#....#
######
correct = 3

6 6
######
#B#..#
#.####
#.#..#
#OR.##
######
correct = 1

10 8
########
#...#.##
##.##.##
##..##.#
#...##.#
#O..#.##
#.#.#..#
#R.###.#
#B.#...#
########
correct = -1

8 9
#########
#..B....#
#.......#
#.......#
#.....O.#
#..#....#
#...#R..#
#########
correct = -1

6 8
########
##..#O.#
#.##.B.#
#.#...R#
#...##.#
########
correct = 4

 */
/*
    R, B 공이 옮겨지는 순서에 따라서 결과값이 달라지는 문제가 발생한다.
    ex) 반례 1)
    3 10 -> -1 (R 먼저 이동 후, B가 이동할 때), 1 (B 먼저 이동 후, R가 이동할 때)
    ##########
    #.O....RB#
    ##########

    반례 2)
    10 7 -> -1(R 이동 후, B가 이동할 때), 3(B 이동 후, R가 이동할 때)
    #######
    #.....#
    #.B...#
    #R....#
    #.....#
    #.....#
    #...O.#
    #.....#
    #.....#
    #######

    따라서, 두 공이 일직선 상에 존재할 경우를 고려해야 한다. 직선상 R,B 공의 위치를 고려하여 상하좌우로 이동할 때 앞서서 이동할 수 있는
    구슬을 선별하여 해당 구슬이 이동하는 for문을 선택하는 로직을 구현하면 될 것 같다 - R,B가 겹치는 경우를 고려

    또한, 기존 코드는 visited를 통해 이미 지나온 경로를 또 지나가는 것을 방지하는 코드를 작성하는 과정에서 중간에 겹치는 경로가 생기는 경우
    오류가 발생할 수 있기 때문에 한 방향에서 반대방향으로 가는 것을 방지하는 방법을 사용해야 될 것 같다. = dir 매개변수 추가

    문제를 읽고 DFS를 통해서 풀 수 있을 것 같아서 DFS를 이용하여 코드를 작성하였다. 이 과정에서 힌트에는 BFS, Graph를 이용하여 풀 수 있다고
    나와있어서 생각해보면 BFS를 통해 Queue를 이용하여 '#' 또는 'R','B'와 만날 때 Queue에 이동한 위치를 저장하여 depth를 늘려 답을 도출할 수 있을 것 같다.

    최종적으로 코드를 완성시키는 과정에서 TC가 다 맞아서 제출해보고 틀리고, 반례를 게시판을 통해 찾아서 적용해보고 어느 부분이 틀렸는지
    디버깅을 통해 고생을 좀 했다.

    좀더 잘해서 실수없이 완벽하게 풀 수 있도록 노력해야겠다.

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ13460 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,RED_X,RED_Y,BLUE_X,BLUE_Y,minCnt = Integer.MAX_VALUE;
    static char[][] board;

    public static void main(String[] args) throws IOException {
        board_setting();

        solve(0,-1,1,1,RED_X,RED_Y,BLUE_X,BLUE_Y);

        System.out.println(minCnt == Integer.MAX_VALUE ? -1 : minCnt);
    }

    static void solve(int depth, int dir, int red, int blue, int r_x, int r_y, int b_x, int b_y) {
        if(depth > 10) {
            return;
        }

        if(red == 0 && blue != 0) {
            minCnt = minCnt > depth ? depth : minCnt;
            return;
        }

        for(int i=0;i<4;i++) {
            boolean red_check = false;
            boolean blue_check = false;
            int next_r_x = r_x;
            int next_r_y = r_y;
            int next_b_x = b_x;
            int next_b_y = b_y;
            boolean select_direction = true;        //R,B 중 우선 이동할 구슬 방향 선택, R이동 = default
            if(dir != -1 && (dir + 2 == i || dir - 2 == i)) {      //0 - 왼쪽이동 | 1 - 아래쪽이동 | 2 - 오른쪽이동 | 3- 위쪽이동
                continue;
            }
            switch (i) {
                case 0:
                    //i = 0, 왼쪽으로 기울이기
                    if(next_r_x == next_b_x && next_r_y > next_b_y) {
                        select_direction = false;
                    }

                    if(select_direction) {
                        for(int y=r_y-1;y>0;y--) {
                            if(board[r_x][y] == '#' || board[r_x][y] == 'O' || (r_x == next_b_x && y == next_b_y)) {
                                next_r_y = y+1;
                                if(board[r_x][y] == 'O') {
                                    next_r_y = y;
                                    red_check = true;
                                }
                                break;
                            }
                            next_r_y = y;
                        }

                        for(int y=b_y-1;y>0;y--) {
                            if(board[b_x][y] == '#' || board[b_x][y] == 'O' || (b_x == next_r_x && y == next_r_y)) {
                                next_b_y = y+1;
                                if(board[b_x][y] == 'O') {
                                    next_b_y = y;
                                    blue_check = true;
                                }
                                break;
                            }
                            next_b_y = y;
                        }
                    } else {
                        for(int y=b_y-1;y>0;y--) {
                            if(board[b_x][y] == '#' || board[b_x][y] == 'O' || (b_x == next_r_x && y == next_r_y)) {
                                next_b_y = y+1;
                                if(board[b_x][y] == 'O') {
                                    next_b_y = y;
                                    blue_check = true;
                                }
                                break;
                            }
                            next_b_y = y;
                        }

                        for(int y=r_y-1;y>0;y--) {
                            if (board[r_x][y] == '#' || board[r_x][y] == 'O' || (r_x == next_b_x && y == next_b_y)) {
                                next_r_y = y + 1;
                                if (board[r_x][y] == 'O') {
                                    next_r_y = y;
                                    red_check = true;
                                }
                                break;
                            }
                            next_r_y = y;
                        }
                    }

                    if(red_check && !blue_check) {
                        solve(depth + 1, 0,red-1, blue, next_r_x, next_r_y, next_b_x, next_b_y);
                    } else if(!red_check && !blue_check){
                        if(!(r_x == next_r_x && r_y == next_r_y && b_x == next_b_x && b_y == next_b_y)) {
                            solve(depth + 1, 0, red, blue, next_r_x, next_r_y, next_b_x, next_b_y);
                        }
                    }
                    break;
                case 1:
                    //i = 1, 아래쪽으로 기울이기
                    if(next_r_y == next_b_y && next_r_x < next_b_x) {
                        select_direction = false;
                    }

                    if(select_direction) {
                        for(int x=r_x+1;x<N-1;x++) {
                            if(board[x][r_y] == '#' || board[x][r_y] == 'O' || (x == next_b_x && r_y == next_b_y)) {
                                next_r_x = x-1;
                                if(board[x][r_y] == 'O') {
                                    next_r_x = x;
                                    red_check = true;
                                }
                                break;
                            }
                            next_r_x = x;
                        }

                        for(int x=b_x+1;x<N-1;x++) {
                            if(board[x][b_y] == '#' || board[x][b_y] == 'O' || (x == next_r_x && b_y == next_r_y)) {
                                next_b_x = x-1;
                                if(board[x][b_y] == 'O') {
                                    next_b_x = x;
                                    blue_check = true;
                                }
                                break;
                            }
                            next_b_x = x;
                        }
                    } else {
                        for(int x=b_x+1;x<N-1;x++) {
                            if(board[x][b_y] == '#' || board[x][b_y] == 'O' || (x == next_r_x && b_y == next_r_y)) {
                                next_b_x = x-1;
                                if(board[x][b_y] == 'O') {
                                    next_b_x = x;
                                    blue_check = true;
                                }
                                break;
                            }
                            next_b_x = x;
                        }
                        for(int x=r_x+1;x<N-1;x++) {
                            if(board[x][r_y] == '#' || board[x][r_y] == 'O' || (x == next_b_x && r_y == next_b_y)) {
                                next_r_x = x-1;
                                if(board[x][r_y] == 'O') {
                                    next_r_x = x;
                                    red_check = true;
                                }
                                break;
                            }
                            next_r_x = x;
                        }
                    }

                    if(red_check && !blue_check) {
                        solve(depth + 1, 1,red-1, blue, next_r_x, next_r_y, next_b_x, next_b_y);
                    } else if(!red_check && !blue_check){
                        if(!(r_x == next_r_x && r_y == next_r_y && b_x == next_b_x && b_y == next_b_y)) {
                            solve(depth + 1, 1, red, blue, next_r_x, next_r_y, next_b_x, next_b_y);
                        }
                    }

                    break;
                case 2:
                    //i = 2, 오른쪽으로 기울이기
                    if(next_r_x == next_b_x && next_r_y < next_b_y) {
                        select_direction = false;
                    }

                    if(select_direction) {
                        for(int y=r_y+1;y<M-1;y++) {
                            if(board[r_x][y] == '#' || board[r_x][y] == 'O' || (r_x == next_b_x && y == next_b_y)) {
                                next_r_y = y-1;
                                if(board[r_x][y] == 'O') {
                                    next_r_y = y;
                                    red_check = true;
                                }
                                break;
                            }
                            next_r_y = y;
                        }

                        for(int y=b_y+1;y<M-1;y++) {
                            if(board[b_x][y] == '#' || board[b_x][y] == 'O' || (b_x == next_r_x && y == next_r_y)) {
                                next_b_y = y-1;
                                if(board[b_x][y] == 'O') {
                                    next_b_y = y;
                                    blue_check = true;
                                }
                                break;
                            }

                            next_b_y = y;

                        }
                    } else {
                        for(int y=b_y+1;y<M-1;y++) {
                            if(board[b_x][y] == '#' || board[b_x][y] == 'O' || (b_x == next_r_x && y == next_r_y)) {
                                next_b_y = y-1;
                                if(board[b_x][y] == 'O') {
                                    next_b_y = y;
                                    blue_check = true;
                                }
                                break;
                            }

                            next_b_y = y;

                        }

                        for(int y=r_y+1;y<M-1;y++) {
                            if(board[r_x][y] == '#' || board[r_x][y] == 'O' || (r_x == next_b_x && y == next_b_y)) {
                                next_r_y = y-1;
                                if(board[r_x][y] == 'O') {
                                    next_r_y = y;
                                    red_check = true;
                                }
                                break;
                            }

                            next_r_y = y;

                        }
                    }

                    if(red_check && !blue_check) {
                        solve(depth + 1, 2,red-1, blue, next_r_x, next_r_y, next_b_x, next_b_y);
                    } else if(!red_check && !blue_check){
                        if(!(r_x == next_r_x && r_y == next_r_y && b_x == next_b_x && b_y == next_b_y)) {
                            solve(depth + 1, 2,red, blue, next_r_x, next_r_y, next_b_x, next_b_y);
                        }
                    }

                    break;
                case 3:
                    //i = 3, 위쪽으로 기울이기
                    if(next_r_y == next_b_y && next_r_x > next_b_x) {
                        select_direction = false;
                    }

                    if(select_direction) {
                        for(int x=r_x-1;x>0;x--) {
                            if(board[x][r_y] == '#' || board[x][r_y] == 'O' || (x == next_b_x && r_y == next_b_y)) {
                                next_r_x = x+1;
                                if(board[x][r_y] == 'O') {
                                    next_r_x = x;
                                    red_check = true;
                                }
                                break;
                            }

                            next_r_x = x;

                        }

                        for(int x=b_x-1;x>0;x--) {
                            if(board[x][b_y] == '#' || board[x][b_y] == 'O' || (x == next_r_x && b_y == next_r_y)) {
                                next_b_x = x+1;
                                if(board[x][b_y] == 'O') {
                                    next_b_x = x;
                                    blue_check = true;
                                }
                                break;
                            }

                            next_b_x = x;

                        }
                    } else {
                        for(int x=b_x-1;x>0;x--) {
                            if(board[x][b_y] == '#' || board[x][b_y] == 'O' || (x == next_r_x && b_y == next_r_y)) {
                                next_b_x = x+1;
                                if(board[x][b_y] == 'O') {
                                    next_b_x = x;
                                    blue_check = true;
                                }
                                break;
                            }

                            next_b_x = x;

                        }

                        for(int x=r_x-1;x>0;x--) {
                            if(board[x][r_y] == '#' || board[x][r_y] == 'O' || (x == next_b_x && r_y == next_b_y)) {
                                next_r_x = x+1;
                                if(board[x][r_y] == 'O') {
                                    next_r_x = x;
                                    red_check = true;
                                }
                                break;
                            }

                            next_r_x = x;

                        }
                    }

                    if(red_check && !blue_check) {
                        solve(depth + 1, 3,red-1, blue, next_r_x, next_r_y, next_b_x, next_b_y);
                    } else if(!red_check && !blue_check){
                        if(!(r_x == next_r_x && r_y == next_r_y && b_x == next_b_x && b_y == next_b_y)) {
                            solve(depth + 1, 3,red, blue, next_r_x, next_r_y, next_b_x, next_b_y);
                        }
                    }

                    break;
            }
        }
    }

    static void board_setting() throws IOException {
        String[] input = br.readLine().split(" ");
        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        board = new char[N][M];

        for(int i=0;i<N;i++) {
            String line = br.readLine();
            for(int j=0;j<M;j++) {
                char c = line.charAt(j);
                if('R' == c) {
                    RED_X = i;
                    RED_Y = j;
                } else if('B' == c) {
                    BLUE_X = i;
                    BLUE_Y = j;
                }
                board[i][j] = c;
            }
        }
    }
}

//기존 베이스 코드 - 틀린 코드
/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ13460 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,RED_X,RED_Y,BLUE_X,BLUE_Y,minCnt = Integer.MAX_VALUE;
    static char[][] board;
    static boolean[][] red_visited;
    static boolean[][] blue_visited;

    public static void main(String[] args) throws IOException {
        board_setting();

        solve(0,1,1,RED_X,RED_Y,BLUE_X,BLUE_Y,red_visited, blue_visited);

        System.out.println(minCnt == Integer.MAX_VALUE ? -1 : minCnt);
    }

    static void solve(int depth, int red, int blue, int r_x, int r_y, int b_x, int b_y, boolean[][] r_v, boolean[][] b_v) {
        if(depth > 10) {
            return;
        }

        if(red == 0 && blue != 0) {
            minCnt = minCnt > depth ? depth : minCnt;
            return;
        }

        for(int i=0;i<4;i++) {
            boolean red_check = false;
            boolean blue_check = false;
            int next_r_x = r_x;
            int next_r_y = r_y;
            int next_b_x = b_x;
            int next_b_y = b_y;
            boolean select_direction = true;        //R,B 중 우선 이동할 구슬 방향 선택, R이동 = default

            switch (i) {
                case 0:
                    //i = 0, 왼쪽으로 기울이기
                    if(next_r_x == next_b_x && next_r_y > next_b_y) {
                        select_direction = false;
                    }

                    if(select_direction) {
                        for(int y=r_y-1;y>0;y--) {
                            if(board[r_x][y] == '#' || board[r_x][y] == 'O' || (r_x == next_b_x && y == next_b_y)) {
                                next_r_y = y+1;
                                if(board[r_x][y] == 'O') {
                                    next_r_y = y;
                                    red_check = true;
                                }
                                break;
                            }
                            if(!r_v[r_x][y]) {
                                r_v[r_x][y] = true;
                                next_r_y = y;
                            }
                        }

                        for(int y=b_y-1;y>0;y--) {
                            if(board[b_x][y] == '#' || board[b_x][y] == 'O' || (b_x == next_r_x && y == next_r_y)) {
                                next_b_y = y+1;
                                if(board[b_x][y] == 'O') {
                                    next_b_y = y;
                                    blue_check = true;
                                }
                                break;
                            }
                            if(!b_v[b_x][y]) {
                                b_v[b_x][y] = true;
                                next_b_y = y;
                            }

                        }
                    } else {
                        for(int y=b_y-1;y>0;y--) {
                            if(board[b_x][y] == '#' || board[b_x][y] == 'O' || (b_x == next_r_x && y == next_r_y)) {
                                next_b_y = y+1;
                                if(board[b_x][y] == 'O') {
                                    next_b_y = y;
                                    blue_check = true;
                                }
                                break;
                            }
                            if(!b_v[b_x][y]) {
                                b_v[b_x][y] = true;
                                next_b_y = y;
                            }

                        }

                        for(int y=r_y-1;y>0;y--) {
                            if(board[r_x][y] == '#' || board[r_x][y] == 'O' || (r_x == next_b_x && y == next_b_y)) {
                                next_r_y = y+1;
                                if(board[r_x][y] == 'O') {
                                    next_r_y = y;
                                    red_check = true;
                                }
                                break;
                            }
                            if(!r_v[r_x][y]) {
                                r_v[r_x][y] = true;
                                next_r_y = y;
                            }

                        }
                    }

                    if(red_check && !blue_check) {
                        solve(depth + 1, red-1, blue, next_r_x, next_r_y, next_b_x, next_b_y, r_v, b_v);

                    } else if(!red_check && !blue_check){
                        //if(!(r_x == next_r_x && r_y == next_r_y && b_x == next_b_x && b_y == next_b_y)) {
                        if(!(r_x == next_r_x && r_y == next_r_y)) {
                            solve(depth + 1, red, blue, next_r_x, next_r_y, next_b_x, next_b_y, r_v, b_v);
                        }
                    }

                    for(int j=next_r_y;j<r_y;j++) {
                        r_v[r_x][j] = !r_v[r_x][j];
                    }

                    for(int j=next_b_y;j<b_y;j++) {
                        b_v[b_x][j] = !b_v[b_x][j];
                    }

                    break;
                case 1:
                    //i = 1, 아래쪽으로 기울이기
                    if(next_r_y == next_b_y && next_r_x < next_b_x) {
                        select_direction = false;
                    }

                    if(select_direction) {
                        for(int x=r_x+1;x<N-1;x++) {
                            if(board[x][r_y] == '#' || board[x][r_y] == 'O' || (x == next_b_x && r_y == next_b_y)) {
                                next_r_x = x-1;
                                if(board[x][r_y] == 'O') {
                                    next_r_x = x;
                                    red_check = true;
                                }
                                break;
                            }

                            if(!r_v[x][r_y]) {
                                r_v[x][r_y] = true;
                                next_r_x = x;
                            }

                        }

                        for(int x=b_x+1;x<N-1;x++) {
                            if(board[x][b_y] == '#' || board[x][b_y] == 'O' || (x == next_r_x && b_y == next_r_y)) {
                                next_b_x = x-1;
                                if(board[x][b_y] == 'O') {
                                    next_b_x = x;
                                    blue_check = true;
                                }
                                break;
                            }

                            if(!b_v[x][b_y]) {
                                b_v[x][b_y] = true;
                                next_b_x = x;
                            }

                        }
                    } else {
                        for(int x=b_x+1;x<N-1;x++) {
                            if(board[x][b_y] == '#' || board[x][b_y] == 'O' || (x == next_r_x && b_y == next_r_y)) {
                                next_b_x = x-1;
                                if(board[x][b_y] == 'O') {
                                    next_b_x = x;
                                    blue_check = true;
                                }
                                break;
                            }

                            if(!b_v[x][b_y]) {
                                b_v[x][b_y] = true;
                                next_b_x = x;
                            }

                        }

                        for(int x=r_x+1;x<N-1;x++) {
                            if(board[x][r_y] == '#' || board[x][r_y] == 'O' || (x == next_b_x && r_y == next_b_y)) {
                                next_r_x = x-1;
                                if(board[x][r_y] == 'O') {
                                    next_r_x = x;
                                    red_check = true;
                                }
                                break;
                            }

                            if(!r_v[x][r_y]) {
                                r_v[x][r_y] = true;
                                next_r_x = x;
                            }

                        }
                    }

                    if(red_check && !blue_check) {
                        solve(depth + 1, red-1, blue, next_r_x, next_r_y, next_b_x, next_b_y, r_v, b_v);
                    } else if(!red_check && !blue_check){
                        //if(!(r_x == next_r_x && r_y == next_r_y && b_x == next_b_x && b_y == next_b_y)) {
                        if(!(r_x == next_r_x && r_y == next_r_y)) {
                            solve(depth + 1, red, blue, next_r_x, next_r_y, next_b_x, next_b_y, r_v, b_v);
                        }
                    }

                    for(int j=next_r_x;j>r_x;j--) {
                        r_v[j][r_y] = !r_v[j][r_y];
                    }

                    for(int j=next_b_x;j>b_x;j--) {
                        b_v[j][b_y] = !b_v[j][b_y];
                    }

                    break;
                case 2:
                    //i = 2, 오른쪽으로 기울이기
                    if(next_r_x == next_b_x && next_r_y < next_b_y) {
                        select_direction = false;
                    }

                    if(select_direction) {
                        for(int y=r_y+1;y<M-1;y++) {
                            if(board[r_x][y] == '#' || board[r_x][y] == 'O' || (r_x == next_b_x && y == next_b_y)) {
                                next_r_y = y-1;
                                if(board[r_x][y] == 'O') {
                                    next_r_y = y;
                                    red_check = true;
                                }
                                break;
                            }

                            if(!r_v[r_x][y]) {
                                r_v[r_x][y] = true;
                                next_r_y = y;
                            }

                        }

                        for(int y=b_y+1;y<M-1;y++) {
                            if(board[b_x][y] == '#' || board[b_x][y] == 'O' || (b_x == next_r_x && y == next_r_y)) {
                                next_b_y = y-1;
                                if(board[b_x][y] == 'O') {
                                    next_b_y = y;
                                    blue_check = true;
                                }
                                break;
                            }

                            if(!b_v[b_x][y]) {
                                b_v[b_x][y] = true;
                                next_b_y = y;
                            }

                        }
                    } else {
                        for(int y=b_y+1;y<M-1;y++) {
                            if(board[b_x][y] == '#' || board[b_x][y] == 'O' || (b_x == next_r_x && y == next_r_y)) {
                                next_b_y = y-1;
                                if(board[b_x][y] == 'O') {
                                    next_b_y = y;
                                    blue_check = true;
                                }
                                break;
                            }

                            if(!b_v[b_x][y]) {
                                b_v[b_x][y] = true;
                                next_b_y = y;
                            }

                        }

                        for(int y=r_y+1;y<M-1;y++) {
                            if(board[r_x][y] == '#' || board[r_x][y] == 'O' || (r_x == next_b_x && y == next_b_y)) {
                                next_r_y = y-1;
                                if(board[r_x][y] == 'O') {
                                    next_r_y = y;
                                    red_check = true;
                                }
                                break;
                            }

                            if(!r_v[r_x][y]) {
                                r_v[r_x][y] = true;
                                next_r_y = y;
                            }

                        }
                    }

                    if(red_check && !blue_check) {
                        solve(depth + 1, red-1, blue, next_r_x, next_r_y, next_b_x, next_b_y, r_v, b_v);
                    } else if(!red_check && !blue_check){
                        //if(!(r_x == next_r_x && r_y == next_r_y && b_x == next_b_x && b_y == next_b_y)) {
                        if(!(r_x == next_r_x && r_y == next_r_y)) {
                            solve(depth + 1, red, blue, next_r_x, next_r_y, next_b_x, next_b_y, r_v, b_v);
                        }
                    }

                    for(int j=next_r_y;j>r_y;j--){
                        r_v[r_x][j] = !r_v[r_x][j];
                    }

                    for(int j=next_b_y;j>b_y;j--) {
                        b_v[b_x][j] = !b_v[b_x][j];
                    }
                    break;
                case 3:
                    //i = 3, 위쪽으로 기울이기
                    if(next_r_y == next_b_y && next_r_x > next_b_x) {
                        select_direction = false;
                    }

                    if(select_direction) {
                        for(int x=r_x-1;x>0;x--) {
                            if(board[x][r_y] == '#' || board[x][r_y] == 'O' || (x == next_b_x && r_y == next_b_y)) {
                                next_r_x = x+1;
                                if(board[x][r_y] == 'O') {
                                    next_r_x = x;
                                    red_check = true;
                                }
                                break;
                            }

                            if(!r_v[x][r_y]) {
                                r_v[x][r_y] = true;
                                next_r_x = x;
                            }

                        }

                        for(int x=b_x-1;x>0;x--) {
                            if(board[x][b_y] == '#' || board[x][b_y] == 'O' || (x == next_r_x && b_y == next_r_y)) {
                                next_b_x = x+1;
                                if(board[x][b_y] == 'O') {
                                    next_b_x = x;
                                    blue_check = true;
                                }
                                break;
                            }

                            if(!b_v[x][b_y]) {
                                b_v[x][b_y] = true;
                                next_b_x = x;
                            }

                        }
                    } else {
                        for(int x=b_x-1;x>0;x--) {
                            if(board[x][b_y] == '#' || board[x][b_y] == 'O' || (x == next_r_x && b_y == next_r_y)) {
                                next_b_x = x+1;
                                if(board[x][b_y] == 'O') {
                                    next_b_x = x;
                                    blue_check = true;
                                }
                                break;
                            }

                            if(!b_v[x][b_y]) {
                                b_v[x][b_y] = true;
                                next_b_x = x;
                            }

                        }

                        for(int x=r_x-1;x>0;x--) {
                            if(board[x][r_y] == '#' || board[x][r_y] == 'O' || (x == next_b_x && r_y == next_b_y)) {
                                next_r_x = x+1;
                                if(board[x][r_y] == 'O') {
                                    next_r_x = x;
                                    red_check = true;
                                }
                                break;
                            }

                            if(!r_v[x][r_y]) {
                                r_v[x][r_y] = true;
                                next_r_x = x;
                            }

                        }
                    }

                    if(red_check && !blue_check) {
                        solve(depth + 1, red-1, blue, next_r_x, next_r_y, next_b_x, next_b_y, r_v, b_v);
                    } else if(!red_check && !blue_check){
                        //if(!(r_x == next_r_x && r_y == next_r_y && b_x == next_b_x && b_y == next_b_y)) {
                        if(!(r_x == next_r_x && r_y == next_r_y)) {
                            solve(depth + 1, red, blue, next_r_x, next_r_y, next_b_x, next_b_y, r_v, b_v);
                        }
                    }

                    for(int j=next_r_x;j<r_x;j++) {
                        r_v[j][r_y] = !r_v[j][r_y];
                    }

                    for(int j=next_b_x;j<b_x;j++) {
                        b_v[j][b_y] = !b_v[j][b_y];
                    }
                    break;
            }
        }
    }

    static void board_setting() throws IOException {
        String[] input = br.readLine().split(" ");
        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        board = new char[N][M];
        red_visited = new boolean[N][M];
        blue_visited = new boolean[N][M];

        for(int i=0;i<N;i++) {
            String line = br.readLine();
            for(int j=0;j<M;j++) {
                red_visited[i][j] = false;
                blue_visited[i][j] = false;
                char c = line.charAt(j);
                if('R' == c) {
                    RED_X = i;
                    RED_Y = j;
                    red_visited[i][j] = true;
                } else if('B' == c) {
                    BLUE_X = i;
                    BLUE_Y = j;
                    blue_visited[i][j] = true;
                } else if('#' == c) {
                    red_visited[i][j] = true;
                    blue_visited[i][j] = true;
                }
                board[i][j] = c;
            }
        }

        for(int i=0;i<M;i++) {
            red_visited[0][i] = true;
            red_visited[N-1][i] = true;
            blue_visited[0][i] = true;
            blue_visited[N-1][i] = true;
        }

        for(int j=0;j<N;j++) {
            red_visited[j][0] = true;
            red_visited[j][M-1] = true;
            blue_visited[j][0] = true;
            blue_visited[j][M-1] = true;
        }
    }
}
*/