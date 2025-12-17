package Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
모노미노도미노 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초 (추가 시간 없음)	512 MB	8891	3299	2232	35.581%
문제
모노미노도미노는 아래와 같이 생긴 보드에서 진행되는 게임이다. 보드는 빨간색 보드, 파란색 보드, 초록색 보드가 그림과 같이 붙어있는 형태이다. 게임에서 사용하는 좌표 (x, y)에서 x는 행, y는 열을 의미한다. 빨간색, 파란색, 초록색 보드가 사용하는 좌표는 그 색으로 그림에 적혀있다.



<그림 1> 모노미노도미노 게임 보드

이 게임에서 사용하는 블록은 타일 하나 또는 두 개가 가로 또는 세로로 붙어있는 형태이다. 아래와 같이 세 종류가 있으며, 왼쪽부터 순서대로 크기가 1×1, 1×2, 2×1 이다.



<그림 2> 모노미노도미노 게임에서 사용하는 블록

블록을 놓을 위치를 빨간색 보드에서 선택하면, 그 위치부터 초록색 보드로 블록이 이동하고, 파란색 보드로 블록이 이동한다. 블록의 이동은 다른 블록을 만나거나 보드의 경계를 만나기 전까지 계속해서 이동한다. 예를 들어, 크기가 1×1인 블록을 (1, 1)에 놓으면, 보드의 상태는 <그림 3>과 같이 변한다.



<그림 3>

여기서 크기가 1×2인 블록을 (3, 0)과 (3, 1)에 놓으면 <그림 4>와 같이 보드의 상태가 변한다.



<그림 4>

다시 이 상태에서 크기가 2×1인 블록을 (2, 2), (3, 2)와 (2, 3), (3, 3)에 놓으면 <그림 5>와 같이 변한다.



<그림 5>

초록색 보드의 4번 행은 모든 칸이 타일로 가득 차있다. 이렇게 초록색 보드에서 어떤 행이 타일로 가득 차 있다면, 그 행의 타일은 모두 사라진다. 사라진 이후에는 초록색 보드에서 사라진 행의 위에 있는 블록이 사라진 행의 수만큼 아래로 이동한다. 파란색의 경우는 열이 타일로 가득 차 있으면, 그 열의 타일이 모두 사라지며, 사라진 이후에는 파란색 보드에서 사라진 열의 왼쪽에 있는 블록이 사라진 열의 수만큼 오른쪽으로 이동한다. 이렇게 한 행이나 열이 타일로 가득 차서 사라지면 1점을 획득한다. 점수는 사라진 행 또는 열의 수와 같다. 만약, 두 개의 행이 사라지면 2점을 획득하게 되고, 한 행과 한 열이 사라져도 2점을 획득하게 된다. 위의 보드는 아래와 같이 변하고, 1점을 얻는다.



<그림 6>

여기서 크기가 2×1인 블록을 (1, 3), (2, 3)에 놓으면 보드는 <그림 7>과 같이 변한다.



<그림 7>

초록색 보드의 0, 1번 행과 파란색 보드의 0, 1번 열은 그림에는 연한색으로 표현되어 있는 특별한 칸이다. 초록색 보드의 0, 1번 행에 블록이 있으면, 블록이 있는 행의 수만큼 아래 행에 있는 타일이 사라지고, 초록색 보드의 모든 블록이 사라진 행의 수만큼 아래로 이동하고, 파란색 보드의 0, 1번 열에 블록이 있으면, 블록이 있는 열의 수만큼 오른쪽 열에 있는 타일이 사라지고, 파란색 보드의 모든 블록이 사라진 열의 수만큼 이동하게 된다. 위의 그림은 파란색 보드의 1번 열에 블록이 있기 때문에, 5번 열에 있는 블록이 모두 사라지고, 파란색 보드의 모든 블록이 오른쪽으로 한 칸 이동하게 된다. 따라서, 보드는 <그림 8>과 같이 변하게 된다.



<그림 8>

위의 보드에서 1×2인 블록을 (0, 0), (0, 1)에 놓으면 <그림 9>와 같다.



<그림 9>

여기서 크기가 2×1인 블록을 (2, 0), (3, 0)에 놓으면 <그림 10>과 같이 변한다. 파란색 보드는 1번 열에 블록이 생겨서 오른쪽으로 한 칸씩 이동한 상태이다.



<그림 10>

크기가 2×1인 블록을 (1, 2), (2, 2)에 놓으면, <그림 11>과 같이 변한다.



<그림 11>

파란색 보드는 1번 열에 블록이 있기 때문에, 5번 열의 타일이 사라지고 모든 블록이 오른쪽으로 한 칸씩 이동하게 된다. 초록색 보드는 4번 행의 모든 칸에 타일이 있기 때문에, 1점을 얻으면서, 4번 행의 모든 타일이 사라진다.



<그림 12>

<그림 12>는 <그림 11>의 상태에서 파란색 보드는 모든 블록이 오른쪽으로 한 칸 이동했고, 초록색 보드의 4번 행이 모두 사라진 상태이다. 이제, 초록색 보드에서 사라진 행의 위에 있는 블록이 아래로 한 칸씩 내려와야 한다. 이동한 후의 상태는 <그림 13>과 같다.



<그림 13>

행이나 열이 타일로 가득찬 경우와 연한 칸에 블록이 있는 경우가 동시에 발생할 수 있다. 이 경우에는 행이나 열이 타일로 가득 찬 경우가 없을 때까지 점수를 획득하는 과정이 모두 진행된 후, 연한 칸에 블록이 있는 경우를 처리해야 한다.

블록은 보드에 놓인 이후에 다른 블록과 합쳐지지 않는다. 블록을 놓은 위치가 순서대로 주어졌을 때, 얻은 점수와 초록색 보드와 파란색 보드에 타일이 있는 칸의 개수를 모두 구해보자.

입력
첫째 줄에 블록을 놓은 횟수 N(1 ≤ N ≤ 10,000)이 주어진다.

둘째 줄부터 N개의 줄에 블록을 놓은 정보가 한 줄에 하나씩 순서대로 주어지며, t x y와 같은 형태이다.

t = 1: 크기가 1×1인 블록을 (x, y)에 놓은 경우
t = 2: 크기가 1×2인 블록을 (x, y), (x, y+1)에 놓은 경우
t = 3: 크기가 2×1인 블록을 (x, y), (x+1, y)에 놓은 경우
블록이 차지하는 칸이 빨간색 칸의 경계를 넘어가는 경우는 입력으로 주어지지 않는다.

출력
첫째 줄에 블록을 모두 놓았을 때 얻은 점수를 출력한다.

둘째 줄에는 파란색 보드와 초록색 보드에서 타일이 들어있는 칸의 개수를 출력한다.

예제 입력 1
1
1 1 1
예제 출력 1
0
2
<그림 3>과 같다.

예제 입력 2
2
1 1 1
2 3 0
예제 출력 2
0
6
<그림 4>와 같다.

예제 입력 3
4
1 1 1
2 3 0
3 2 2
3 2 3
예제 출력 3
1
10
<그림 6>과 같다.

예제 입력 4
5
1 1 1
2 3 0
3 2 2
3 2 3
3 1 3
예제 출력 4
1
12
<그림 8>과 같다.

예제 입력 5
6
1 1 1
2 3 0
3 2 2
3 2 3
3 1 3
2 0 0
예제 출력 5
1
16
<그림 9>와 같다.

예제 입력 6
7
1 1 1
2 3 0
3 2 2
3 2 3
3 1 3
2 0 0
3 2 0
예제 출력 6
1
18
<그림 10>과 같다.

예제 입력 7
8
1 1 1
2 3 0
3 2 2
3 2 3
3 1 3
2 0 0
3 2 0
3 1 2
예제 출력 7
2
15
<그림 13>과 같다.

출처
문제를 만든 사람: baekjoon
알고리즘 분류
구현
시뮬레이션
 */
/*
문제에서 주어진 그림은 https://www.acmicpc.net/problem/20061 에서 확인

알고리즘에서 요구하는 요구사항을 만족하는 코드를 작성하면 문제없다.
1. solve() : N번 만큼 주어지는 블록의 개수만큼 블록 이동, 점수 측정, 흰 영역 체크를 수행
2. move_block() : 처음 주어지는 블록에 해당하는 블록의 파랑, 초록 영역에 알맞게 배치하는 함수
3. tile_check() : 최종적으로 파랑, 초록 영역에 존재하는 타일의 개수를 측정하는 함수
4. clear_and_move() : 파랑, 초록영역에 따라 x,y가 주어지는 위치에서 해당 라인을 지우고 당겨오는 함수
5. blue_point_check() : 파랑 영역에서 한줄에 해당하는 라인이 있을 경우 점수를 업데이트하는 함수
6. green_point_check() : 5번의 기능을 초록영역에서 수행하는 함수
7. light_blue_check() : 흰색의 파랑영역에 블록이 존재하는 경우를 체크하고, 해당하는 영역만큼 파랑 영역을 업데이트하는 함수
8. light_green_check() : 7번의 기능을 초록영역에서 수행하는 함수
9. possible_row() : 타일 모양에 따라 파랑,초록영역에서 위치할 수 있는 row 값을 체크하는 함수
10. possible_col() : 타일 모양에 따라 파랑, 초록영역에서 위치할 수 있는 col 값을 체크하는 함수

9,10의 동작을 예시로,
2
1 1 1
2 3 0
의 입력이 주어질 경우,

초록영역에서 (1,1)타일과 (3,0),(3,1) 블록이 만나서 두번째 타일의 경우 row가 9가 아닌 8로 결정된다.
내부적으로, (3,0)타일은 row = 9, (3,1)타일은 row = 8을 반환하여 둘 중 작은 값인 8로 결정된다.
 */
public class BOJ20061 {
    static class BOJ20061_block {
        int t,x,y;

        BOJ20061_block(int t, int x, int y) {
            this.t = t;
            this.x = x;
            this.y = y;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, blue_point, green_point, blue_tile, green_tile;
    static ArrayList<BOJ20061_block> blocks;
    static BOJ20061_block[][] boards;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for (BOJ20061_block b : blocks) {
            move_block(b);

            blue_point_check();
            green_point_check();

            light_blue_check();
            light_green_check();
        }

        System.out.println(blue_point + green_point);
        System.out.println(tile_check());
    }

    static void move_block(BOJ20061_block b) {
        int row, col;

        switch (b.t) {
            case 1:
                row = possible_row(b.y);
                col = possible_col(b.x);

                boards[b.x][col] = new BOJ20061_block(b.t,b.x,col);
                boards[row][b.y] = new BOJ20061_block(b.t,row,b.y);
                break;
            case 2:
                row = Math.min(possible_row(b.y),possible_row(b.y+1));
                col = possible_col(b.x);

                boards[b.x][col-1] = new BOJ20061_block(b.t,b.x,col-1);
                boards[b.x][col] = new BOJ20061_block(b.t,b.x,col);

                boards[row][b.y] = new BOJ20061_block(b.t,row,b.y);
                boards[row][b.y+1] = new BOJ20061_block(b.t,row,b.y+1);
                break;
            case 3:
                row = possible_row(b.y);
                col = Math.min(possible_col(b.x),possible_col(b.x+1));

                boards[b.x][col] = new BOJ20061_block(b.t,b.x,col);
                boards[b.x+1][col] = new BOJ20061_block(b.t,b.x+1,col);

                boards[row-1][b.y] = new BOJ20061_block(b.t,row-1,b.y);
                boards[row][b.y] = new BOJ20061_block(b.t,row,b.y);
                break;
        }
    }

    static int tile_check() {
        blue_tile = green_tile = 0;

        for(int y=6;y<10;y++) {
            for(int x=0;x<4;x++) {
                if(boards[x][y] != null) {
                    blue_tile++;
                }

                if(boards[y][x] != null) {
                    green_tile++;
                }
            }
        }

        return blue_tile + green_tile;
    }

    static void clear_and_move(int x, int y, int t) {
        switch (t) {
            case 1:
                //blue
                for(x=0;x<4;x++) {
                    for(int sy=y;sy>4;sy--) {
                        boards[x][sy] = boards[x][sy-1];
                        boards[x][sy-1] = null;
                    }
                }
                break;
            case 2:
                //green
                for(y=0;y<4;y++) {
                    for(int sx=x;sx>4;sx--) {
                        boards[sx][y] = boards[sx-1][y];
                        boards[sx-1][y] = null;
                    }
                }
                break;
        }
    }

    static void light_blue_check() {
        boolean keep_checking = true;
        int isExist = 0;
        for(int y=4;y<6 && keep_checking;y++) {
            for(int x=0;x<4 && keep_checking;x++) {
                if(boards[x][y] != null) {
                    if(y == 4) {
                        isExist = 2;
                    } else {
                        isExist = 1;
                    }
                    keep_checking = false;
                }
            }
        }

        while(isExist-- > 0) {
            clear_and_move(0, 9,1);
        }
    }

    static void light_green_check() {
        boolean keep_checking = true;
        int isExist = 0;
        for(int x=4;x<6 && keep_checking;x++) {
            for(int y=0;y<4 && keep_checking;y++) {
                if(boards[x][y] != null) {
                    if(x == 4) {
                        isExist = 2;
                    } else {
                        isExist = 1;
                    }
                    keep_checking = false;
                }
            }
        }

        while(isExist-- > 0) {
            clear_and_move(9, 0,2);
        }
    }

    static void blue_point_check() {
        for(int y=9;y>5;y--) {
            int isExist = 0;

            for(int x=0;x<4;x++) {
                if(boards[x][y] != null) {
                    isExist++;
                }
            }

            if(isExist == 4) {
                blue_point++;

                clear_and_move(0,y,1);

                y++;
            }
        }
    }

    static void green_point_check() {
        for(int x=9;x>5;x--) {
            int isExist = 0;

            for(int y=0;y<4;y++) {
                if(boards[x][y] != null) {
                    isExist++;
                }
            }

            if(isExist == 4) {
                green_point++;

                clear_and_move(x,0,2);

                x++;
            }
        }
    }

    static int possible_col(int x) {
        int col = Integer.MAX_VALUE;
        int possible_col = 4;

        while (true) {
            if (boards[x][possible_col++] != null) {
                col = Math.min(col, possible_col - 2);
                break;
            }

            if(possible_col == 10) {
                col = 9;
                break;
            }
        }

        return col;
    }

    static int possible_row(int y) {
        int row = Integer.MAX_VALUE;
        int possible_row = 4;

        while(true) {
            if (boards[possible_row++][y] != null) {
                row = Math.min(row, possible_row - 2);
                break;
            }

            if(possible_row == 10) {
                row = 9;
                break;
            }
        }

        return row;
    }


    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        boards = new BOJ20061_block[10][10];
        blocks = new ArrayList<>();
        blue_point = green_point = 0;

        while(N-- > 0) {
            String[] input = br.readLine().split(" ");

            int t = Integer.parseInt(input[0]);
            int x = Integer.parseInt(input[1]);
            int y = Integer.parseInt(input[2]);

            blocks.add(new BOJ20061_block(t,x,y));
        }
    }
}
