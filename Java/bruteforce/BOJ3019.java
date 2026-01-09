package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
테트리스 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	3037	1540	1223	49.514%
문제
테트리스는 C열 필드위에서 플레이하는 유명한 게임이다. 필드의 행의 수는 무한하다. 한 번 움직일 때, 아래와 같은 일곱가지 블록 중 하나를 필드에 떨어뜨릴 수 있다.



블록을 떨어뜨리기 전에, 플레이어는 블록을 90, 180, 270도 회전시키거나 좌우로 움직일 수 있다. 이때, 블록이 필드를 벗어나지 않으면 된다. 블록을 필드의 바닥이나 이미 채워져있는 칸의 위에 놓여지게 된다.

창영이가 하고있는 테트리스는 일반적인 테트리스와 약간 규칙이 다르다. 블록이 떨어졌을 때, 블록과 블록 또는 블록과 바닥 사이에 채워져있지 않은 칸이 생기면 안 된다.

예를 들어, 아래와 같이 각 칸의 높이가 2, 1, 1, 1, 0, 1인 경우를 생각해보자. 블록 5번을 떨어뜨리는 방법의 수는 아래와 같이 다섯가지이다.



테트리스 필드의 각 칸의 높이와 떨어뜨려야 하는 블록의 번호가 주어진다. 이때, 블록을 놓는 서로 다른 방법의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 C와 떨어뜨리는 블록의 번호 P가 주어진다. (1 ≤ C ≤ 100, 1 ≤ P ≤ 7)

둘째 줄에는 각 칸의 높이가 주어진다. 높이는 0보다 크거나 같고, 100보다 작거나 같은 자연수이다.

출력
첫째 줄에 블록을 떨어뜨리는 방법의 수를 출력한다.

예제 입력 1
6 5
2 1 1 1 0 1
예제 출력 1
5
예제 입력 2
5 1
0 0 0 0 0
예제 출력 2
7
예제 입력 3
9 4
4 3 5 4 6 5 7 6 6
예제 출력 3
1
출처
Olympiad > Croatian Highschool Competitions in Informatics > 2007 > Croatian Regional Competition in Informatics 2007 2번

Olympiad > Croatian Highschool Competitions in Informatics > 2007 > Regional Competition - Seniors 3번

문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: doju
알고리즘 분류
구현
브루트포스 알고리즘
 */
public class BOJ3019 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        public class Tetromino {
            private int[][] shape;

            public Tetromino(int[][] s) {
                this.shape = new int[s.length][s[0].length];

                for(int i = 0; i < s.length; i++) {
                    this.shape[i] = Arrays.copyOf(s[i],s[i].length);
                }
            }

            public void rotate() {
                int row = shape.length;
                int col = shape[0].length;
                int[][] rotated = new int[col][row];

                for(int i = 0; i < row; i++) {
                    for(int j = 0; j < col; j++) {
                        rotated[j][row - 1 - i] = shape[i][j];
                    }
                }
                shape = rotated;
            }
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int C,P,ans;
        int[][] field;
        Tetromino I_Block,O_Block,S_Block,Z_Block,T_Block,J_Block,L_Block;

        private void solve() throws IOException {
            init_setting();

            block_setting();

            place_tetris();
        }

        private void place_tetris() {

        }

        private void block_setting() {
            I_Block = new Tetromino(new int[][] {
                    {1,1,1,1}
            });

            O_Block = new Tetromino(new int[][] {
                    {1,1},
                    {1,1}
            });

            S_Block = new Tetromino(new int[][] {
                    {0,1,1},
                    {1,1,0}
            });

            Z_Block = new Tetromino(new int[][] {
                    {1,1,0},
                    {0,1,1}
            });

            T_Block = new Tetromino(new int[][] {
                    {0,1,0},
                    {1,1,1}
            });

            J_Block = new Tetromino(new int[][] {
                    {0,0,1},
                    {1,1,1}
            });

            L_Block = new Tetromino(new int[][] {
                    {1,0,0},
                    {1,1,1}
            });
        }


        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            C = Integer.parseInt(input[0]);
            P = Integer.parseInt(input[1]);

            field = new int[105][101];

            input = br.readLine().split(" ");

            for(int i = 1; i <= C; i++) {
                int row = Integer.parseInt(input[i - 1]);

                for(int j = 0; j < row; j++) {
                    field[104 - j][i] = 1;
                }
            }

            ans = 0;
        }
    }
}
