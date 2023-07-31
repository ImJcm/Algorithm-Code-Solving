package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/*
스도미노쿠 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	2318	1078	715	45.833%
문제
스도쿠가 세계적으로 유행이 된 이후에, 비슷한 퍼즐이 매우 많이 나왔다. 게임 매거진 2009년 7월호에는 스도쿠와 도미노를 혼합한 게임인 스도미노쿠가 소개되었다.

이 퍼즐은 스도쿠 규칙을 따른다. 스도쿠는 9×9 크기의 그리드를 1부터 9까지 숫자를 이용해서 채워야 한다. 스도쿠는 다음과 같은 조건을 만족하게 숫자를 채워야 한다.

각 행에는 1부터 9까지 숫자가 하나씩 있어야 한다.
각 열에는 1부터 9까지 숫자가 하나씩 있어야 한다.
3×3크기의 정사각형에는 1부터 9가지 숫자가 하나씩 있어야 한다.
스도미노쿠의 그리드에는 1부터 9까지 숫자가 쓰여져 있고, 나머지 72칸은 도미노 타일 36개로 채워야 한다. 도미노 타일은 1부터 9까지 서로 다른 숫자의 가능한 쌍이 모두 포함되어 있다. (1+2, 1+3, 1+4, 1+5, 1+6, 1+7, 1+8, 1+9, 2+3, 2+4, 2+5, ...) 1+2와 2+1은 같은 타일이기 때문에, 따로 구분하지 않는다. 도미노 타일은 회전 시킬 수 있다. 또, 3×3 크기의 정사각형의 경계에 걸쳐서 놓여질 수도 있다.

왼쪽 그림은 퍼즐의 초기 상태를 나타내고, 오른쪽은 퍼즐을 푼 상태이다.



스도미노쿠의 퍼즐의 초기 상태가 주어졌을 때, 퍼즐을 푸는 프로그램을 작성하시오.

입력
입력은 여러 개의 테스트 케이스로 이루어져 있다. 각 테스트 케이스의 첫째 줄에는 채워져 있는 도미노의 개수 N이 주어진다. (10 ≤ N ≤ 35) 다음 N개 줄에는 도미노 하나를 나타내는 U LU V LV가 주어진다. U는 도미노에 쓰여 있는 한 숫자이고, LU는 길이가 2인 문자열로 U의 위치를 나타낸다. V와 LV는 도미노에 쓰여 있는 다른 숫자를 나타낸다. 도미노의 위치는 문제에 나와있는 그림을 참고한다. 입력으로 주어지는 도미노의 각 숫자 위치는 항상 인접해 있다.

N개의 도미노의 정보가 주어진 다음 줄에는 채워져 있는 숫자의 위치가 1부터 9까지 차례대로 주어진다. 위치는 도미노의 위치를 나타낸 방법과 같은 방법으로 주어진다.

모든 도미노와 숫자가 겹치는 경우는 없다.

입력의 마지막 줄에는 0이 하나 주어진다.

출력
각 퍼즐에 대해서, 스도쿠를 푼 결과를 출력한다. 항상 답이 유일한 경우만 입력으로 주어진다.

예제 입력 1
10
6 B2 1 B3
2 C4 9 C3
6 D3 8 E3
7 E1 4 F1
8 B7 4 B8
3 F5 2 F6
7 F7 6 F8
5 G4 9 G5
7 I8 8 I9
7 C9 2 B9
C5 A3 D9 I4 A9 E5 A2 C6 I1
11
5 I9 2 H9
6 A5 7 A6
4 B8 6 C8
3 B5 8 B4
3 C3 2 D3
9 D2 8 E2
3 G2 5 H2
1 A2 8 A1
1 H8 3 I8
8 I3 7 I4
4 I6 9 I7
I5 E6 D1 F2 B3 G9 H7 C9 E5
0
예제 출력 1
Puzzle 1
872643195
361975842
549218637
126754983
738169254
495832761
284597316
657381429
913426578
Puzzle 2
814267593
965831247
273945168
392176854
586492371
741358629
137529486
459683712
628714935
 */
/*
    boj2580 스도쿠의 변형문제로 도미노 블록을 고려해야한다. 이를 해결하기 위해서 스도쿠 구성을 만들기 위해 백트랙킹을
    사용하여 비어있는 좌표를 별도의 배열에 담고 배열의 크기만큼 현재의 좌표의 가능한 수와 오른쪽 또는 아래쪽으로 가능한 수에
    대한 유효성 검사하여 재귀호출을 통해 가능한 경우의 수를 구한다. 이때, depth의 값이 비어있는 좌표를 담은 배열의 크기와
    같아지면 기저사례로 현재 구성된 board를 출력한다.
    이때 별도의 검사를 하지않는 이유는 비어있는 좌표에서 가능한 수와 도미노 블록을 구성하는 다른 비어있는 좌표에서 가능한 수를
    백트랙킹 할때마다 검사하기 때문에 모든 좌표가 채워졌다는 의미로 depth가 배열의 크기와 같아지면 바로 board를 출력한다.

    하지만, 현재 sudominoku의 경우 재귀호출된 함수들이 하나의 스도쿠가 구성되었을 때 나머지 호출된 함수들의 종료를 하기 위해
    별도의 검사과정을 필요로한다.
    이를 해결할 수 있는 방법으로 sudominoku 함수의 타입을 boolean으로 바꾸고 반환 값에 따라 종료할지 결정하는 형태로 구성
    ex) if(sudominoku(depth + 1, step)) return true; => 최종적으로 하나의 board가 구성되면 true를 반환하고, 이후의
    재귀호출된 함수들은 모두 조건문에 의해 return true를 반환하여 모두 종료된다.

    다른 방법으로는 위와 비슷하지만 반환타입을 변경하는 형태가 아닌 flag라는 static 변수를 만들고 함수의 기저사례보다 먼저
    검사하는 위치로 최초의 board 구성 시, flag의 값을 변경하여 이후 모든 함수에서 해당 flag 값에 의해 return 될 수 있도록
    하는 방법이다.

    하나의 스도미노쿠 로직을 수행 후, static 변수들을 모두 초기화하는 과정을 수행한다.
 */
class BOJ4574_Point {
    int x,y;
    int num;

    BOJ4574_Point(int x,int y, int num) {
        this.x = x;
        this.y = y;
        this.num = num;
    }
}

public class BOJ4574 {
    static boolean flag = false;
    static BOJ4574_Point[][] board = new BOJ4574_Point[9][9];
    static boolean[][] duplicate = new boolean[9][9];
    static ArrayList<BOJ4574_Point> zeroPoint = new ArrayList<>();
    static int[][] direction = {{1,0},{0,1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int step = 0;
        while(true) {
            //입력 데이터 저장
            int N = Integer.parseInt(br.readLine());
            if(N == 0) break;

            flag = false;
            zeroPoint.clear();
            for(int i=0;i<9;i++) {
                Arrays.fill(duplicate[i],false);
                Arrays.fill(board[i],null);
            }

            for(int i=0;i<N;i++) {
                String[] input = br.readLine().split(" ");
                int r1 = input[1].charAt(0) - 'A';
                int c1 = input[1].charAt(1) - '1';
                int r2 = input[3].charAt(0) - 'A';
                int c2 = input[3].charAt(1) - '1';
                board[r1][c1] = new BOJ4574_Point(r1,c1,Integer.parseInt(input[0]));
                board[r2][c2] = new BOJ4574_Point(r2,c2,Integer.parseInt(input[2]));
                duplicate[Integer.parseInt(input[0])-1][Integer.parseInt(input[2])-1] = duplicate[Integer.parseInt(input[2])-1][Integer.parseInt(input[0])-1] = true;
            }

            String[] input = br.readLine().split(" ");
            for(int i=0;i<9;i++) {
                int r = input[i].charAt(0) - 'A';
                int c = input[i].charAt(1) - '1';
                board[r][c] = new BOJ4574_Point(r,c,i+1);;
            }

            //비어있는 좌표 저장
            for(int r=0;r<9;r++) {
                for(int c=0;c<9;c++) {
                    if(board[r][c] == null) {
                        board[r][c] = new BOJ4574_Point(r,c,0);
                        zeroPoint.add(board[r][c]);
                    }
                }
            }

            //스도미노쿠 로직 구현
            sudominoku(0,++step);
        }
    }
    //방법 2 - static 변수로 함수 제어
    static void sudominoku(int depth,int step) {
        if(flag) return;
        if(depth == zeroPoint.size()) {
            printBoard(step);
            flag = true;
            return;
        }

        if(board[zeroPoint.get(depth).x][zeroPoint.get(depth).y].num == 0) {
            int x = zeroPoint.get(depth).x;
            int y = zeroPoint.get(depth).y;
            for (int j = 0; j < 2; j++) {
                int nx = board[x][y].x + direction[j][0];
                int ny = board[x][y].y + direction[j][1];
                if (nx >= 9 || ny >= 9 || board[nx][ny].num != 0) continue;

                for (int i = 1; i <= 9; i++) {
                    for (int k = i + 1; k <= 9; k++) {
                        if (duplicate[i - 1][k - 1] || duplicate[k - 1][i - 1]) continue;
                        duplicate[i - 1][k - 1] = duplicate[k - 1][i - 1] = true;
                        if (possibility(x, y, i) && possibility(nx,ny,k)) {
                            board[x][y] = new BOJ4574_Point(x, y, i);
                            board[nx][ny] = new BOJ4574_Point(nx, ny, k);
                            sudominoku(depth + 1, step);
                            board[nx][ny] = new BOJ4574_Point(nx, ny, 0);
                            board[x][y] = new BOJ4574_Point(x, y, 0);
                        }

                        if (possibility(x, y, k) && possibility(nx,ny,i)) {
                            board[x][y] = new BOJ4574_Point(x, y, k);
                            board[nx][ny] = new BOJ4574_Point(nx, ny, i);
                            sudominoku(depth + 1, step);
                            board[nx][ny] = new BOJ4574_Point(nx, ny, 0);
                            board[x][y] = new BOJ4574_Point(x, y, 0);
                        }
                        duplicate[i - 1][k - 1] = duplicate[k - 1][i - 1] = false;
                    }
                }
            }
        } else {
            sudominoku(depth + 1, step);
        }
    }
    /*
    //최초 board 생성 시, return으로 다른 재귀호출 함수 제어
    static boolean sudominoku(int depth,int step) {
        if(depth == zeroPoint.size()) {
            printBoard(step);
            return true;
        }

        if(board[zeroPoint.get(depth).x][zeroPoint.get(depth).y].num == 0) {
            int x = zeroPoint.get(depth).x;
            int y = zeroPoint.get(depth).y;
            for (int j = 0; j < 2; j++) {
                int nx = board[x][y].x + direction[j][0];
                int ny = board[x][y].y + direction[j][1];
                if (nx >= 9 || ny >= 9 || board[nx][ny].num != 0) continue;

                for (int i = 1; i <= 9; i++) {
                    for (int k = i + 1; k <= 9; k++) {
                        if (duplicate[i - 1][k - 1] || duplicate[k - 1][i - 1]) continue;
                        duplicate[i - 1][k - 1] = duplicate[k - 1][i - 1] = true;
                        if (possibility(x, y, i) && possibility(nx,ny,k)) {
                            board[x][y] = new BOJ4574_Point(x, y, i);
                            board[nx][ny] = new BOJ4574_Point(nx, ny, k);
                            if(sudominoku(depth + 1, step)) return true;
                            board[nx][ny] = new BOJ4574_Point(nx, ny, 0);
                            board[x][y] = new BOJ4574_Point(x, y, 0);
                        }

                        if (possibility(x, y, k) && possibility(nx,ny,i)) {
                            board[x][y] = new BOJ4574_Point(x, y, k);
                            board[nx][ny] = new BOJ4574_Point(nx, ny, i);
                            if(sudominoku(depth + 1, step)) return true;
                            board[nx][ny] = new BOJ4574_Point(nx, ny, 0);
                            board[x][y] = new BOJ4574_Point(x, y, 0);
                        }
                        duplicate[i - 1][k - 1] = duplicate[k - 1][i - 1] = false;
                    }
                }
            }
        } else {
            if(sudominoku(depth + 1, step)) return true;
        }
        return false;
    }*/

    static void printBoard(int step) {
        System.out.println("Puzzle " + step);
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                System.out.print(board[i][j].num);
            }
            System.out.println();
        }
    }

    static boolean possibility(int x, int y, int value) {
        for(int i=0;i<9;i++) {
            if(board[x][i].num == value) return false;
            if(board[i][y].num == value) return false;
        }

        int set_x = (x / 3) * 3;
        int set_y = (y / 3) * 3;

        for (int i = set_x; i < set_x + 3; i++) {
            for (int j = set_y; j < set_y + 3; j++) {
                if (board[i][j].num == value) {
                    return false;
                }
            }
        }

        return true;
    }
}
/*
//최초의 board판이 구성되도 나머지 재귀호출된 함수들이 종료되지 않아 다른 결과 board도 출력될 수 있는 코드
static void sudominoku(int depth,int step) {
        if(depth == zeroPoint.size()) {
            printBoard(step);
            return;
        }

        if(board[zeroPoint.get(depth).x][zeroPoint.get(depth).y].num == 0) {
            int x = zeroPoint.get(depth).x;
            int y = zeroPoint.get(depth).y;
            for (int j = 0; j < 2; j++) {
                int nx = board[x][y].x + direction[j][0];
                int ny = board[x][y].y + direction[j][1];
                if (nx >= 9 || ny >= 9 || board[nx][ny].num != 0) continue;

                for (int i = 1; i <= 9; i++) {
                    for (int k = i + 1; k <= 9; k++) {
                        if (duplicate[i - 1][k - 1] || duplicate[k - 1][i - 1]) continue;
                        duplicate[i - 1][k - 1] = duplicate[k - 1][i - 1] = true;
                        if (possibility(x, y, i) && possibility(nx,ny,k)) {
                            board[x][y] = new BOJ4574_Point(x, y, i);
                            board[nx][ny] = new BOJ4574_Point(nx, ny, k);
                            sudominoku(depth + 1, step);
                            board[nx][ny] = new BOJ4574_Point(nx, ny, 0);
                            board[x][y] = new BOJ4574_Point(x, y, 0);
                        }

                        if (possibility(x, y, k) && possibility(nx,ny,i)) {
                            board[x][y] = new BOJ4574_Point(x, y, k);
                            board[nx][ny] = new BOJ4574_Point(nx, ny, i);
                            sudominoku(depth + 1, step);
                            board[nx][ny] = new BOJ4574_Point(nx, ny, 0);
                            board[x][y] = new BOJ4574_Point(x, y, 0);
                        }
                        duplicate[i - 1][k - 1] = duplicate[k - 1][i - 1] = false;
                    }
                }
            }
        } else {
            sudominoku(depth + 1, step);
        }
    }
 */