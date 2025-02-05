package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
소코반 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	2454	713	521	28.470%
문제
소코반은 1982년에 일본에서 만들어진 게임으로, 일본어로 창고지기라는 뜻이다. 이 게임은 캐릭터를 이용해 창고 안에 있는 박스를 모두 목표점으로 옮기는 게임이다. 목표점의 수와 박스의 수는 같다. 플레이어는 화살표(위, 아래, 왼쪽, 오른쪽)를 이용해 캐릭터를 아래와 같은 규칙으로 조정할 수 있다.

캐릭터에게 지시한 방향이 빈 칸(박스나 벽이 아닌 곳)인 경우에는 그 칸으로 이동한다.
지시한 방향에 박스가 있는 경우에는, 박스를 민다. 이 경우에는 박스가 이동할 칸도 비어있어야 한다.
지시한 방향이 벽인 경우, 또는 박스가 있는데, 박스가 이동할 칸에 다른 박스나 벽이 있는 경우에는 키를 눌러도 캐릭터는 이동하지 않는다.
모든 박스를 목표점으로 이동시킨 경우에 게임은 끝난다. 게임이 끝난 후에 입력하는 키는 모두 무시된다.

준규는 소코반으로 고통받는 친구들을 위해서 소코반의 해를 찾는 프로그램을 작성하려고 한다. 하지만, 소코반의 해를 찾는 문제는 NP-hard와 PSPACE-complete에 속하고, 매우 어려운 문제이다. 따라서, 간단한 소코반 프로그램을 작성해보려고 한다.

사용자가 입력한 키가 순서대로 주어졌을 때, 게임이 어떻게 진행되는지를 구하는 프로그램을 작성하시오.

게임의 상태는 아래와 같은 문자로 나타낼 수 있다.

문자	뜻
.	빈 공간
#	벽
+	비어 있는 목표점
b	박스
B	목표점 위에 있는 박스
w	캐릭터
W	목표점 위에 있는 캐릭터
첫 번째 입력은 문제의 그림과 같다.

입력
입력은 여러 개의 테스트 케이스로 이루어져 있다.

각 테스트 케이스의 첫째 줄에는 행과 열의 수 R, C가 주어진다. (4 ≤ R ≤ 15, 4 ≤ C ≤ 15) 다음 R개 줄에는 현재 게임의 상태가 주어진다. 모든 줄은 C개의 문자로 이루어져 있다. 마지막 줄에는 플레이어가 입력한 키가 순서대로 주어지며 길이는 최대 50이다. 위, 아래, 왼쪽, 오른쪽은 U, D, L, R로 나타낸다.

입력의 마지막 줄에는 0 0이 주어진다.

입력으로 주어지는 모든 데이터는 항상 캐릭터가 한 명이고, 박스의 수와 목표점의 수는 같다. 또, 목표점 위에 올라가 있지 않은 박스는 적어도 한 개 이며, 가장 바깥쪽 칸은 항상 벽이다.

출력
각각의 게임에 대해서, 게임 번호를 출력한 다음에 게임이 끝났으면 complete를, 아니면 incomplete를 출력한다. 그 다음 줄부터 R개 줄에는 게임의 상태를 출력한다.

예제 입력 1
8 9
#########
#...#...#
#..bb.b.#
#...#w#.#
#...#b#.#
#...++++#
#...#..##
#########
ULRURDDDUULLDDD
6 7
#######
#..####
#.+.+.#
#.bb#w#
##....#
#######
DLLUDLULUURDRDDLUDRR
0 0
예제 출력 1
Game 1: incomplete
#########
#...#...#
#..bb...#
#...#.#.#
#...#.#.#
#...+W+B#
#...#b.##
#########
Game 2: complete
#######
#..####
#.B.B.#
#.w.#.#
##....#
#######
출처
ICPC > Regionals > North America > Mid-Central Regional > 2011 Mid-Central Regional Programming Contest G번

문제를 번역한 사람: baekjoon
알고리즘 분류
구현
시뮬레이션
 */
/*
알고리즘 핵심
구현

1. 입력으로 주어지는 플레이어의 움직임(player - String)에 실시간으로 게임판의 상황이 동기화되어야 하기때문에 r,c 좌표에 현재 위치한 곳의 문자를 알기위해 class를 생성하여 사용하였다.
2. 플레이어를 움직이고 난 후 이동한 위치들과 이전 위치의 문자를 동기화하기 위해 현재 문자열을 나타내는 cur_ch와 초기 문자를 나타내기 위해 init_ch를 사용하였다.
3. 초기 플레이어의 위치를 특정하기 위해 w라는 별도의 변수를 선언하고 사용한다.
4. 움직이는 과정을 수행하는 moving함수의 동작 과정은 다음과 같다.
4-1. if, U) w의 위치를 기준으로 상단 한칸의 문자가 b or B인지 여부를 검사한다.
4-1-1. b or B인 경우, 상단 두번째칸의 문자가 빈공간인 경우 b블록을 위로 옮긴다. / 상단 두번째칸의 문자가 +인 경우, b블록을 B블록으로 만들어 옮긴다.
이후, w는 상단 한칸인 기존의 b블록이 있던 곳으로 문자열을 동기화하여 옮긴 상태로 만들고, 기존 w가 위치한 곳은 cur_ch = init_ch로 설정한다.
4-1-2. . 빈공간인 경우, w만 옮기는 과정을 수행하고 기존 w의 위치를 init_ch로 설정한다.
4-1-3. + 목표공간인 경우, w를 대문자 W로 변경한 후 옮기는 과정을 수행하고 기존 w의 위치를 init_ch로 설정한다.

위 4번 과정을 U,D,L,R에 각 상황에 맞게 수행하여 입력으로 주어진 player 문자열에 맞게 수행하다가 모든 + 지점이 B구간으로 지정된다면 이후 행동을 종료한다.

이때, 주의할 점은 W,B의 대문자의 구분을 통해 목표지점에 플레이어 또는 박스가 초기에 설정될 수 있다는 점을 고려해야한다.

모든 박스가 + 지점에 지정되었다는 것을 알기위해 B로 변경이 이루어진 경우, DES의 값을 -1로 설정하고 B -> b로 변경되는 경우 DES 값을 +1로 설정하여 구분한다.

움직임을 마치고 DES의 값이 0이 되는 경우는 모든 박스가 목표지점에 도달한 것으로 인식할 수 있다.
 */
public class BOJ4577 {
    static class BOJ4577_pos {
        int r,c;
        char cur_ch,init_ch;

        BOJ4577_pos(int r, int c, char cur_ch, char init_ch) {
            this.r = r;
            this.c = c;
            this.cur_ch = cur_ch;
            this.init_ch = init_ch;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int R,C,DES,SET = 0;
    static BOJ4577_pos w;
    static BOJ4577_pos[][] game_state;
    static String player;
    static boolean flag = true;

    public static void main(String[] args) throws IOException {
        solve();
    }

    private static void solve() throws IOException{
        while(true) {
            init_setting();
            if(!flag) break;
            playing();
        }
    }


    private static void playing() {
        for(int p = 0; p < player.length() && DES > 0; p++) {
            char move = player.charAt(p);
            moving(move);
        }
        SET++;
        check();
    }

    private static void check() {
        if(DES == 0) System.out.printf("Game %d: complete\n",SET);
        else System.out.printf("Game %d: incomplete\n",SET);

        for(int r = 1; r <= R; r++) {
            for(int c = 1; c <= C; c++) System.out.print(game_state[r][c].cur_ch);
            System.out.println();
        }
    }

    private static void moving(char m) {
        switch (m) {
            case 'U':
                if(game_state[w.r - 1][w.c].cur_ch == 'b') {
                    if(game_state[w.r - 2][w.c].cur_ch == '.') {
                        game_state[w.r - 2][w.c].cur_ch = 'b';
                        game_state[w.r - 1][w.c].cur_ch = 'w';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.r = w.r - 1;
                    } else if(game_state[w.r - 2][w.c].cur_ch == '+') {
                        game_state[w.r - 2][w.c].cur_ch = 'B';
                        game_state[w.r - 1][w.c].cur_ch = 'w';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.r = w.r - 1;
                        DES--;
                    }
                } else if(game_state[w.r - 1][w.c].cur_ch == 'B') {
                    if(game_state[w.r - 2][w.c].cur_ch == '.') {
                        game_state[w.r - 2][w.c].cur_ch = 'b';
                        game_state[w.r - 1][w.c].cur_ch = 'W';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.r = w.r - 1;
                        DES++;
                    } else if(game_state[w.r - 2][w.c].cur_ch == '+') {
                        game_state[w.r - 2][w.c].cur_ch = 'B';
                        game_state[w.r - 1][w.c].cur_ch = 'W';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.r = w.r - 1;
                    }
                } else if(game_state[w.r - 1][w.c].cur_ch == '.') {
                    game_state[w.r - 1][w.c].cur_ch = 'w';
                    game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                    w.r = w.r - 1;
                } else if(game_state[w.r - 1][w.c].cur_ch == '+') {
                    game_state[w.r - 1][w.c].cur_ch = 'W';
                    game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                    w.r = w.r - 1;
                }
                break;
            case 'D':
                if(game_state[w.r + 1][w.c].cur_ch == 'b') {
                    if(game_state[w.r + 2][w.c].cur_ch == '.') {
                        game_state[w.r + 2][w.c].cur_ch = 'b';
                        game_state[w.r + 1][w.c].cur_ch = 'w';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.r = w.r + 1;
                    } else if(game_state[w.r + 2][w.c].cur_ch == '+') {
                        game_state[w.r + 2][w.c].cur_ch = 'B';
                        game_state[w.r + 1][w.c].cur_ch = 'w';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.r = w.r + 1;
                        DES--;
                    }
                } else if(game_state[w.r + 1][w.c].cur_ch == 'B') {
                    if (game_state[w.r + 2][w.c].cur_ch == '.') {
                        game_state[w.r + 2][w.c].cur_ch = 'b';
                        game_state[w.r + 1][w.c].cur_ch = 'W';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.r = w.r + 1;
                        DES++;
                    } else if (game_state[w.r + 2][w.c].cur_ch == '+') {
                        game_state[w.r + 2][w.c].cur_ch = 'B';
                        game_state[w.r + 1][w.c].cur_ch = 'W';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.r = w.r + 1;
                    }
                } else if(game_state[w.r + 1][w.c].cur_ch == '.') {
                    game_state[w.r + 1][w.c].cur_ch = 'w';
                    game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                    w.r = w.r + 1;
                } else if(game_state[w.r + 1][w.c].cur_ch == '+') {
                    game_state[w.r + 1][w.c].cur_ch = 'W';
                    game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                    w.r = w.r + 1;
                }
                break;
            case 'R':
                if(game_state[w.r][w.c + 1].cur_ch == 'b') {
                    if(game_state[w.r][w.c + 2].cur_ch == '.') {
                        game_state[w.r][w.c + 2].cur_ch = 'b';
                        game_state[w.r][w.c + 1].cur_ch = 'w';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.c = w.c + 1;
                    } else if(game_state[w.r][w.c + 2].cur_ch == '+') {
                        game_state[w.r][w.c + 2].cur_ch = 'B';
                        game_state[w.r][w.c + 1].cur_ch = 'w';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.c = w.c + 1;
                        DES--;
                    }
                } else if(game_state[w.r][w.c + 1].cur_ch == 'B') {
                    if(game_state[w.r][w.c + 2].cur_ch == '.') {
                        game_state[w.r][w.c + 2].cur_ch = 'b';
                        game_state[w.r][w.c + 1].cur_ch = 'W';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.c = w.c + 1;
                        DES++;
                    } else if(game_state[w.r][w.c + 2].cur_ch == '+') {
                        game_state[w.r][w.c + 2].cur_ch = 'B';
                        game_state[w.r][w.c + 1].cur_ch = 'W';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.c = w.c + 1;
                    }
                } else if(game_state[w.r][w.c + 1].cur_ch == '.') {
                    game_state[w.r][w.c + 1].cur_ch = 'w';
                    game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                    w.c = w.c + 1;
                } else if(game_state[w.r][w.c + 1].cur_ch == '+') {
                    game_state[w.r][w.c + 1].cur_ch = 'W';
                    game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                    w.c = w.c + 1;
                }
                break;
            case 'L':
                if(game_state[w.r][w.c - 1].cur_ch == 'b') {
                    if(game_state[w.r][w.c - 2].cur_ch == '.') {
                        game_state[w.r][w.c - 2].cur_ch = 'b';
                        game_state[w.r][w.c - 1].cur_ch = 'w';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.c = w.c - 1;
                    } else if(game_state[w.r][w.c - 2].cur_ch == '+') {
                        game_state[w.r][w.c - 2].cur_ch = 'B';
                        game_state[w.r][w.c - 1].cur_ch = 'w';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.c = w.c - 1;
                        DES--;
                    }
                } else if(game_state[w.r][w.c - 1].cur_ch == 'B') {
                    if(game_state[w.r][w.c - 2].cur_ch == '.') {
                        game_state[w.r][w.c - 2].cur_ch = 'b';
                        game_state[w.r][w.c - 1].cur_ch = 'W';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.c = w.c - 1;
                        DES++;
                    } else if(game_state[w.r][w.c - 2].cur_ch == '+') {
                        game_state[w.r][w.c - 2].cur_ch = 'B';
                        game_state[w.r][w.c - 1].cur_ch = 'W';
                        game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                        w.c = w.c - 1;
                    }
                } else if(game_state[w.r][w.c - 1].cur_ch == '.') {
                    game_state[w.r][w.c - 1].cur_ch = 'w';
                    game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                    w.c = w.c - 1;
                } else if(game_state[w.r][w.c - 1].cur_ch == '+') {
                    game_state[w.r][w.c - 1].cur_ch = 'W';
                    game_state[w.r][w.c].cur_ch = game_state[w.r][w.c].init_ch;
                    w.c = w.c - 1;
                }
                break;
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        R = Integer.parseInt(input[0]);
        C = Integer.parseInt(input[1]);

        DES = 0;

        if(R == 0 && C == 0) {
            flag = false;
            return;
        }

        game_state = new BOJ4577_pos[R + 1][C + 1];

        for(int r = 1; r <= R; r++) {
            input = br.readLine().split("");

            for(int c = 1; c <= C; c++) {
                char cur_ch = input[c - 1].charAt(0);
                char init_ch = (cur_ch == 'b' || cur_ch == 'w') ? '.' :
                        (cur_ch == 'B' || cur_ch == 'W' ? '+' : cur_ch);
                game_state[r][c] = new BOJ4577_pos(r, c, cur_ch, init_ch);

                if(cur_ch == 'w' || cur_ch == 'W') w = new BOJ4577_pos(r, c, cur_ch, init_ch);
                if(cur_ch == 'b' || cur_ch == 'B') DES++;
                if(cur_ch == 'B') DES--;
            }
        }
        player = br.readLine();

    }
}


