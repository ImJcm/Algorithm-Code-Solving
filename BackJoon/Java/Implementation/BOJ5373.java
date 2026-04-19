package Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
큐빙 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	16357	6465	4484	38.565%
문제
루빅스 큐브는 삼차원 퍼즐이다. 보통 루빅스 큐브는 3×3×3개의 작은 정육면체로 이루어져 있다. 퍼즐을 풀려면 각 면에 있는 아홉 개의 작은 정육면체의 색이 동일해야 한다.

큐브는 각 면을 양방향으로 90도 만큼 돌릴 수 있도록 만들어져 있다. 회전이 마친 이후에는, 다른 면을 돌릴 수 있다. 이렇게 큐브의 서로 다른 면을 돌리다 보면, 색을 섞을 수 있다.

이 문제에서는 루빅스 큐브가 모두 풀린 상태에서 시작한다. 윗 면은 흰색, 아랫 면은 노란색, 앞 면은 빨간색, 뒷 면은 오렌지색, 왼쪽 면은 초록색, 오른쪽 면은 파란색이다.

루빅스 큐브를 돌린 방법이 순서대로 주어진다. 이때, 모두 돌린 다음에 가장 윗 면의 색상을 구하는 프로그램을 작성하시오.



위의 그림은 루빅스 큐브를 푼 그림이다. 왼쪽 면은 시계방향으로 조금 돌려져 있는 상태이다.

입력
첫째 줄에 테스트 케이스의 개수가 주어진다. 테스트 케이스는 최대 100개이다. 각 테스트 케이스는 다음과 같이 구성되어져 있다.

첫째 줄에 큐브를 돌린 횟수 n이 주어진다. (1 ≤ n ≤ 1000)
둘째 줄에는 큐브를 돌린 방법이 주어진다. 각 방법은 공백으로 구분되어져 있으며, 첫 번째 문자는 돌린 면이다. U: 윗 면, D: 아랫 면, F: 앞 면, B: 뒷 면, L: 왼쪽 면, R: 오른쪽 면이다. 두 번째 문자는 돌린 방향이다. +인 경우에는 시계 방향 (그 면을 바라봤을 때가 기준), -인 경우에는 반시계 방향이다.
출력
각 테스트 케이스에 대해서 큐브를 모두 돌린 후의 윗 면의 색상을 출력한다. 첫 번째 줄에는 뒷 면과 접하는 칸의 색을 출력하고, 두 번째, 세 번째 줄은 순서대로 출력하면 된다. 흰색은 w, 노란색은 y, 빨간색은 r, 오렌지색은 o, 초록색은 g, 파란색은 b.

예제 입력 1
4
1
L-
2
F+ B+
4
U- D- L+ R+
10
L- U- L+ U- L- U- U- L+ U+ U+
예제 출력 1
rww
rww
rww
bbb
www
ggg
gwg
owr
bwb
gwo
www
rww
출처


ICPC > Regionals > Europe > Northwestern European Regional Contest > Benelux Algorithm Programming Contest > BAPC 2012 Preliminaries C번

문제를 번역한 사람: baekjoon
알고리즘 분류
구현
시뮬레이션
 */
/*
알고리즘 핵심
구현
1. 2차원 배열에 큐브 형태를 나타내어 회전한 형태를 구성한다.
2. 한 면을 회전할 때, 인접한 4개의 면과 맞닿은 곳과 회전의 기준이 되는 면을 회전 대상으로 삼아야한다.
3. 회전하는 로직은 회전 시 2차원 배열의 큐브 평면도의 위치의 값을 서로 스왑하는 형태로 구성하였다.
4. 하나의 테스트케이스가 끝날 때마다 큐브를 처음 상태로 초기화한다.
5. 큐브의 윗면을 출력할 때, 2차원 평면도의 (0,3) ~ (2,5) 3x3 배열의 값을 상하 반전하여 출력한다.
 */
public class BOJ5373 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T;
    static char[][] cube;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() throws IOException{
        while(T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            String[] ops = br.readLine().split(" ");

            for(String op : ops) {
                rotate_cube(op);

                //print_cube_2D();
            }

            print_up_side();

            init_cube();
        }
    }

    private static void init_cube() {
        /*
            w : white, g : green, o : orange, b : blue, y : yellow, r : red
                  w w w                 Up
                  w w w           Left  Back Right
                  w w w                 Bottom
            g g g o o o b b b           Front
            g g g o o o b b b
            g g g o o o b b b
                  y y y
                  y y y
                  y y y
                  r r r
                  r r r
                  r r r
         */
        for(int r = 0; r < 3; r++) {
            for(int c = 0; c < 3; c++) {
                cube[r][c + 3] = 'w';
                cube[r + 3][c] = 'g';
                cube[r + 3][c + 3] = 'o';
                cube[r + 3][c + 6] = 'b';
                cube[r + 6][c + 3] = 'y';
                cube[r + 9][c + 3] = 'r';
            }
        }
    }

    private static void rotate_cube(String op) {
        char tmp;

        switch (op) {
            case "U+":
                rotate_in_side(0,3,'+');

                for(int i = 0; i < 3; i++) {
                    tmp = cube[3][i + 6];
                    cube[3][i + 6] = cube[3][i + 3];
                    cube[3][i + 3] = cube[3][i];
                    cube[3][i] = cube[11][5 - i];
                    cube[11][5 - i] = tmp;
                }
                break;
            case "U-":
                rotate_in_side(0,3,'-');

                for(int i = 0; i < 3; i++) {
                    tmp = cube[3][i];
                    cube[3][i] = cube[3][i + 3];
                    cube[3][i + 3] = cube[3][i + 6];
                    cube[3][i + 6] = cube[11][5 - i];
                    cube[11][5 - i] = tmp;
                }
                break;
            case "D+":
                rotate_in_side(6,3,'+');

                for(int i = 0; i < 3; i++) {
                    tmp = cube[5][i];
                    cube[5][i] = cube[5][i + 3];
                    cube[5][i + 3] = cube[5][i + 6];
                    cube[5][i + 6] = cube[9][5 - i];
                    cube[9][5 - i] = tmp;
                }
                break;
            case "D-":
                rotate_in_side(6,3,'-');

                for(int i = 0; i < 3; i++) {
                    tmp = cube[5][i + 6];
                    cube[5][i + 6] = cube[5][i + 3];
                    cube[5][i + 3] = cube[5][i];
                    cube[5][i] = cube[9][5 - i];
                    cube[9][5 - i] = tmp;
                }
                break;
            case "F+":
                rotate_in_side(9,3,'+');

                for(int i = 0; i < 3; i++) {
                    tmp = cube[0][i + 3];
                    cube[0][i + 3] = cube[5 - i][0];
                    cube[5 - i][0] = cube[8][5 - i];
                    cube[8][5 - i] = cube[i + 3][8];
                    cube[i + 3][8] = tmp;
                }
                break;
            case "F-":
                rotate_in_side(9,3,'-');

                for(int i = 0; i < 3; i++) {
                    tmp = cube[0][i + 3];
                    cube[0][i + 3] = cube[i + 3][8];
                    cube[i + 3][8] = cube[8][5 - i];
                    cube[8][5 - i] = cube[5 - i][0];
                    cube[5 - i][0] = tmp;
                }
                break;
            case "B+":
                rotate_in_side(3,3,'+');

                for(int i = 0; i < 3; i++) {
                    tmp = cube[2][i + 3];
                    cube[2][i + 3] = cube[i + 3][6];
                    cube[i + 3][6] = cube[6][5 - i];
                    cube[6][5 - i] = cube[5 - i][2];
                    cube[5 - i][2] = tmp;
                }
                break;
            case "B-":
                rotate_in_side(3,3,'-');

                for(int i = 0; i < 3; i++) {
                    tmp = cube[2][i + 3];
                    cube[2][i + 3] = cube[5 - i][2];
                    cube[5 - i][2] = cube[6][5 - i];
                    cube[6][5 - i] = cube[i + 3][6];
                    cube[i + 3][6] = tmp;
                }
                break;
            case "L+":
                rotate_in_side(3,0,'+');

                for(int i = 0; i < 3; i++) {
                    tmp = cube[i][3];
                    cube[i][3] = cube[i + 3][3];
                    cube[i + 3][3] = cube[i + 6][3];
                    cube[i + 6][3] = cube[i + 9][3];
                    cube[i + 9][3] = tmp;
                }
                break;
            case "L-":
                rotate_in_side(3,0,'-');

                for(int i = 0; i < 3; i++) {
                    tmp = cube[i + 9][3];
                    cube[i + 9][3] = cube[i + 6][3];
                    cube[i + 6][3] = cube[i + 3][3];
                    cube[i + 3][3] = cube[i][3];
                    cube[i][3] = tmp;
                }
                break;
            case "R+":
                rotate_in_side(3,6,'+');

                for(int i = 0; i < 3; i++) {
                    tmp = cube[i + 9][5];
                    cube[i + 9][5] = cube[i + 6][5];
                    cube[i + 6][5] = cube[i + 3][5];
                    cube[i + 3][5] = cube[i][5];
                    cube[i][5] = tmp;
                }
                break;
            case "R-":
                rotate_in_side(3,6,'-');

                for(int i = 0; i < 3; i++) {
                    tmp = cube[i][5];
                    cube[i][5] = cube[i + 3][5];
                    cube[i + 3][5] = cube[i + 6][5];
                    cube[i + 6][5] = cube[i + 9][5];
                    cube[i + 9][5] = tmp;
                }
                break;
        }

    }

    private static void rotate_in_side(int r, int c, char d) {
        char[] tmp = Arrays.copyOfRange(cube[r],c,c + 3);

        if(d == '+') {
            for(int i = 0; i < 2; i++) {
                cube[r][c + i] = cube[r + i][c + 2];
                cube[r + i][c + 2] = cube[r + 2][c + 2 - i];
                cube[r + 2][c + 2 - i] = cube[r + 2 - i][c];
                cube[r + 2 - i][c] = tmp[i];
            }
        } else {
            for(int i = 0; i < 2; i++) {
                cube[r][c + 2 - i] = cube[r + i][c];
                cube[r + i][c] = cube[r + 2][c + i];
                cube[r + 2][c + i] = cube[r + 2 - i][c + 2];
                cube[r + 2 - i][c + 2] = tmp[2 - i];
            }
        }
    }

    private static void print_cube_2D() {
        for(int r = 0; r < 12; r++) {
            for(int c = 0; c < 9; c++) {
                System.out.print(cube[r][c]);
            }
            System.out.println();
        }
        System.out.println("#---------#");
    }

    private static void print_up_side() {
        for(int r = 0; r < 3; r++) {
            for(int c = 0; c < 3; c++) {
                System.out.print(cube[2 - r][c + 3]);
            }
            System.out.println();
        }
    }

    private static void init_setting() throws IOException {
        T = Integer.parseInt(br.readLine());

        cube = new char[12][9];

        init_cube();
    }
}
