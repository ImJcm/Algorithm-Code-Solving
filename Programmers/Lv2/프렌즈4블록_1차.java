package Lv2;

import java.util.ArrayList;
import java.util.Arrays;

/*
[1차] 프렌즈4블록
제출 내역
문제 설명
프렌즈4블록
블라인드 공채를 통과한 신입 사원 라이언은 신규 게임 개발 업무를 맡게 되었다. 이번에 출시할 게임 제목은 "프렌즈4블록".
같은 모양의 카카오프렌즈 블록이 2×2 형태로 4개가 붙어있을 경우 사라지면서 점수를 얻는 게임이다.

board map
만약 판이 위와 같이 주어질 경우, 라이언이 2×2로 배치된 7개 블록과 콘이 2×2로 배치된 4개 블록이 지워진다. 같은 블록은 여러 2×2에 포함될 수 있으며, 지워지는 조건에 만족하는 2×2 모양이 여러 개 있다면 한꺼번에 지워진다.

board map

블록이 지워진 후에 위에 있는 블록이 아래로 떨어져 빈 공간을 채우게 된다.

board map

만약 빈 공간을 채운 후에 다시 2×2 형태로 같은 모양의 블록이 모이면 다시 지워지고 떨어지고를 반복하게 된다.
board map

위 초기 배치를 문자로 표시하면 아래와 같다.

TTTANT
RRFACC
RRRFCC
TRRRAA
TTMMMF
TMMTTJ
각 문자는 라이언(R), 무지(M), 어피치(A), 프로도(F), 네오(N), 튜브(T), 제이지(J), 콘(C)을 의미한다

입력으로 블록의 첫 배치가 주어졌을 때, 지워지는 블록은 모두 몇 개인지 판단하는 프로그램을 제작하라.

입력 형식
입력으로 판의 높이 m, 폭 n과 판의 배치 정보 board가 들어온다.
2 ≦ n, m ≦ 30
board는 길이 n인 문자열 m개의 배열로 주어진다. 블록을 나타내는 문자는 대문자 A에서 Z가 사용된다.
출력 형식
입력으로 주어진 판 정보를 가지고 몇 개의 블록이 지워질지 출력하라.

입출력 예제
m	n	board	answer
4	5	["CCBDE", "AAADE", "AAABF", "CCBBF"]	14
6	6	["TTTANT", "RRFACC", "RRRFCC", "TRRRAA", "TTMMMF", "TMMTTJ"]	15
예제에 대한 설명
입출력 예제 1의 경우, 첫 번째에는 A 블록 6개가 지워지고, 두 번째에는 B 블록 4개와 C 블록 4개가 지워져, 모두 14개의 블록이 지워진다.
입출력 예제 2는 본문 설명에 있는 그림을 옮긴 것이다. 11개와 4개의 블록이 차례로 지워지며, 모두 15개의 블록이 지워진다.
 */
public class 프렌즈4블록_1차 {
    static void main() {
        int m = 6, n = 6;
        String[] board = new String[] {
                //"CCBDE", "AAADE", "AAABF", "CCBBF"
                "TTTANT", "RRFACC", "RRRFCC", "TRRRAA", "TTMMMF", "TMMTTJ"
        };

        Solve task = new Solve();
        System.out.println(task.solution(m,n,board));
    }

    private static class Solve {
        private class Pos {
            int m,n;

            public  Pos(int m, int n) {
                this.m = m;
                this.n = n;
            }
        }
        private int ans;
        private ArrayList<Pos> removed;
        private char[][] boards;
        private boolean[][] visited;
        private int[][] direction = {{0,1},{1,0},{1,1}};

        public int solution(int m, int n, String[] board) {
            change_data_structure(board);

            while(true) {
                init_setting(m,n);

                check_block(0,0, visited, boards);

                if(removed.isEmpty()) break;

                remove_block(removed, boards);

                rebase_block(boards);
            }

            return ans;
        }

        private void rebase_block(char[][] boards) {
            for(int i = 0; i < boards[0].length; i++) {
                for(int j = boards.length - 1; j > 0 ; j--) {
                    if(boards[j][i] == ' ') {
                        int idx = j - 1;
                        while(idx >= 0 && boards[idx][i] == ' ') idx--;
                        if(idx < 0) break;
                        else {
                            boards[j][i] = boards[idx][i];
                            boards[idx][i] = ' ';
                        }
                    }
                }
            }
        }

        private void remove_block(ArrayList<Pos> removed, char[][] boards) {
            for(Pos p : removed) {
                int m = p.m, n = p.n;

                ans += (boards[m][n] == ' ' ? 0 : 1);
                boards[m][n] = ' ';

                for(int[] d : direction) {
                    ans += boards[m + d[0]][n + d[1]] == ' ' ? 0 : 1;
                    boards[m + d[0]][n + d[1]] = ' ';
                }
            }
        }

        private void check_block(int m, int n, boolean[][] visited, char[][] boards) {
            char ch = boards[m][n];
            int cnt = ch == ' ' ? 0 : 1;

            if(visited[m][n]) return;
            visited[m][n] = true;

            if(m + 1 >= boards.length || n + 1 >= boards[0].length) return;

            for(int[] d : direction) {
                cnt += (boards[m + d[0]][n + d[1]] == ch ? 1 : 0);
                check_block(m + d[0], n + d[1], visited, boards);
            }

            if(cnt == 4) removed.add(new Pos(m,n));
        }

        private void init_setting(int m, int n) {
            removed = new ArrayList<>();
            visited = new boolean[m][n];
        }

        private void change_data_structure(String[] board) {
            boards = Arrays.stream(board).map(String::toCharArray).toArray(char[][]::new);
        }
    }
}
