package Lv2;

/*
가장 큰 정사각형 찾기
제출 내역
문제 설명
1와 0로 채워진 표(board)가 있습니다. 표 1칸은 1 x 1 의 정사각형으로 이루어져 있습니다. 표에서 1로 이루어진 가장 큰 정사각형을 찾아 넓이를 return 하는 solution 함수를 완성해 주세요. (단, 정사각형이란 축에 평행한 정사각형을 말합니다.)

예를 들어

1	2	3	4
0	1	1	1
1	1	1	1
1	1	1	1
0	0	1	0
가 있다면 가장 큰 정사각형은

1	2	3	4
0	1	1	1
1	1	1	1
1	1	1	1
0	0	1	0
가 되며 넓이는 9가 되므로 9를 반환해 주면 됩니다.

제한사항
표(board)는 2차원 배열로 주어집니다.
표(board)의 행(row)의 크기 : 1,000 이하의 자연수
표(board)의 열(column)의 크기 : 1,000 이하의 자연수
표(board)의 값은 1또는 0으로만 이루어져 있습니다.
입출력 예
board	answer
[[0,1,1,1],[1,1,1,1],[1,1,1,1],[0,0,1,0]]	9
[[0,0,1,1],[1,1,1,1]]	4
입출력 예 설명
입출력 예 #1
위의 예시와 같습니다.

입출력 예 #2

| 0 | 0 | 1 | 1 |
| 1 | 1 | 1 | 1 |
로 가장 큰 정사각형의 넓이는 4가 되므로 4를 return합니다.
 */
/*
알고리즘 핵심
구현

초기 설계 - O(N^3)
1. board에서 1로 표시된 위치를 가로와 세로로 최대 길이를 누적하여 s_board에 업데이트한다.
2. 최대 넓이의 정사각형을 찾기 위해 O(n^2)만큼 반복하여 (r,c) 위치에서 가로와 세로의 최소 길이가 만들 수 있는 정사각형의
최대 넓이이므로 최소 길이를 결정한다.
3. 검사하는 과정은 (r,c) 기준으로 O(n)만큼 가로와 세로가 누적한 길이들이 만족하는지 검사한다.
4. O(N^3)이므로 최대 넓이로 업데이트된 ans에 (r,c) 기준 가능한 최소 길이를 비교하여 가지치기를 수행하여 수행시간을 줄인다.

개선 설계 - O(N^2)
1. (r,c)를 기준으로 (r-1,c), (r,c-1), (r-1,c-1)의 값으로 가능한 정사각형을 결정한다.
(r-1,c), (r,c-1), (r-1,c-1)의 값이 모두 1이면, (r,c)에서는 (r-1,c), (r,c-1), (r-1,c-1)의 최소값 + 1인 변의 정사각형이 가능하다.
(r-1,c), (r,c-1), (r-1,c-1)의 값 중 0이 존재하면, 정사각형이 불가능하므로 (r,c)의 값에따라 0 또는 1로 설정한다.
2. m_board[r][c]값에 제곱을 한 값을 ans에 업데이트하여 최대 넓이의 정사각형을 구한다.
 */
public class 가장_큰_정사각형_찾기 {
    static void main() {
        int[][] board = new int[][] {
                //{0,1,1,1}, {1,1,1,1}, {1,1,1,1}, {0,0,1,0}
                {0,0,1,1},{1,1,1,1}
                //{0,0,0,1,1,1},{0,0,0,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}
                //{1,0},{0,0}
        };

        Solve task = new Solve();
        System.out.println(task.solution(board));
    }

    private static class Solve {
        private class Square {
            int max_width, max_length, max_diagonal;

            public Square(int max_width, int max_length, int max_diagonal) {
                this.max_width = max_width;
                this.max_length = max_length;
                this.max_diagonal = max_diagonal;
            }
        }
        private int ans;
        private Square[][] s_board;
        private int[][] m_board;

        public int solution(int[][] board) {
            /*init_setting(board);
            search_the_largest_square_pruning(s_board);*/

            init_setting_2(board);
            search_the_largest_square(board, m_board);

            return ans;
        }

        private void search_the_largest_square(int[][] board, int[][] m_board) {
            for(int i = 0; i < m_board.length; i++) {
                for(int j = 0; j < m_board[0].length; j++) {
                    if(board[i][j] != 1) continue;

                    ans = Math.max(ans, (int) Math.pow(m_board[i][j],2));
                }
            }
        }

        /*
            Solve : pruning
         */
        private void search_the_largest_square_pruning(Square[][] s_board) {
            for(int i = 0; i < s_board.length; i++) {
                for(int j = 0; j < s_board[0].length; j++) {
                    int m_l = Math.min(s_board[i][j].max_width, s_board[i][j].max_length);
                    boolean flag = true;

                    if(m_l < (int) Math.sqrt(ans)) continue;

                    for(int k = 1; k < m_l && flag; k++) {
                        if(s_board[i - k][j].max_width < m_l) flag = false;
                        if(s_board[i][j - k].max_length < m_l) flag = false;
                    }

                    if(flag) ans = Math.max(ans, (int) Math.pow(m_l, 2));
                }
            }
        }

        /*
            Wrong Solve : logic error
         */
        private void search_the_largest_square_logic_error(Square[][] s_board) {
            for(int i = 0; i < s_board.length; i++) {
                for(int j = 0; j < s_board[0].length; j++) {
                    ans = Math.max(ans, (int) Math.pow(
                            Math.min(s_board[i][j].max_width, s_board[i][j].max_length),2
                    ));
                }
            }
        }

        /*
            Efficiency test : time out
         */
        private void search_the_largest_square_TC_timeout(Square[][] s_board) {
            for(int i = 0; i < s_board.length; i++) {
                for(int j = 0; j < s_board[0].length; j++) {
                    int m_l = Math.min(s_board[i][j].max_width, s_board[i][j].max_length);
                    boolean flag = true;

                    if(m_l < 0) continue;

                    for(int k = 1; k < m_l && flag; k++) {
                        if(s_board[i - k][j].max_width < m_l) flag = false;
                        if(s_board[i][j - k].max_length < m_l) flag = false;
                    }

                    if(flag) ans = Math.max(ans, (int) Math.pow(m_l, 2));
                }
            }
        }

        private void init_setting_2(int[][] board) {
            ans = 0;

            m_board = new int[board.length][board[0].length];

            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board[0].length; j++) {
                    int m_w = j - 1 >= 0 ? m_board[i][j - 1] : 0;
                    int m_l = i - 1 >= 0 ? m_board[i - 1][j] : 0;
                    int m_d = i - 1 >= 0 && j - 1 >= 0 ? m_board[i - 1][j - 1] : 0;
                    int r = 0;

                    if(board[i][j] == 1) {
                        r = Math.min(Math.min(m_w, m_l), m_d) + 1;
                    }

                    m_board[i][j] = r;
                }
            }
        }

        private void init_setting(int[][] board) {
            ans = 0;

            s_board = new Square[board.length][board[0].length];

            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board[0].length; j++) {
                    int m_w = j - 1 >= 0 ? s_board[i][j - 1].max_width : 0;
                    int m_l = i - 1 >= 0 ? s_board[i - 1][j].max_length : 0;
                    int m_d = i - 1 >= 0 && j - 1 >= 0 ? s_board[i - 1][j - 1].max_diagonal : 0;

                    if(board[i][j] == 1) {
                        m_w += 1;
                        m_l += 1;
                        m_d += 1;
                    } else {
                        m_w = 0;
                        m_l = 0;
                        m_d = 0;
                    }

                    s_board[i][j] = new Square(m_w, m_l, m_d);
                }
            }
        }
    }
}
