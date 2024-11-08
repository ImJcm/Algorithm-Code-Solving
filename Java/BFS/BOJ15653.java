package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
구슬 탈출 4

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	4674	2156	1696	48.975%
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
최소 몇 번 만에 빨간 구슬을 구멍을 통해 빼낼 수 있는지 출력한다. 만약, 어떻게 움직여도 빨간 구슬을 구멍을 통해 빼낼 수 없으면 -1을 출력한다.

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
#.#...##.#
#.#.#.#..#
#...#.O#.#
##########
예제 출력 4
12
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
문제의 오타를 찾은 사람: sky1357, welchsgrape
알고리즘 분류
구현
그래프 이론
그래프 탐색
시뮬레이션
너비 우선 탐색
 */
/*
알고리즘 핵심
BFS + 구현
1. 빨간 구슬 위치, 파란 구슬 위치, 이동 횟수를 저장하는 class
2. 빨간 구슬과 파란 구슬 위치 정보를 저장한 class를 기준으로 방문 여부와 이동 횟수를 포함한 bfs를 수행한다.
(map에 실제로 구슬의 움직임을 동기화하는 것이 아닌 빨간, 파랑 구슬의 위치 정보로만을 사용한다.)
3. 빨간 구슬과 파란 구슬의 위치에 따라 상하좌우로 기울일 때 먼저 움직일 구슬을 선택한 후, 두 구슬이 겹치지 않는 경우, 두 구슬 줃 하나만 들어간 경우(빨강 구슬 or 파란 구슬), 두 구슬 모두 들어간 경우를 검사한다.
4. 구슬이 O 구역에 들어간 경우, 해당 구슬의 위치 정보를 -1로 업데이트하여 O 구역에 들어간 것으로 인식할 수 있도록 한다.
5. 빨간 구슬의 위치정보가 -1이고 -1인 위치 정보가 2개인 경우 최초로 빨강 구슬이 들어간 경우이므로 ans에 움직인 횟수를 업데이트한다.
 */
public class BOJ15653 {
    static class BOJ15653_beads {
        int rn,rm,bn,bm,move;

        BOJ15653_beads(int rn, int rm, int bn, int bm, int move) {
            this.rn = rn;
            this.rm = rm;
            this.bn = bn;
            this.bm = bm;
            this.move = move;
        }

        public String toString() {
            return Integer.toString(this.rn) + " " +
                    Integer.toString(this.rm) + " " +
                    Integer.toString(this.bn) + " " +
                    Integer.toString(this.bm);
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,ans,red_bn,red_bm,blue_bn,blue_bm;;
    static char[][] map;
    static int[][] ds = {{-1,0},{1,0},{0,-1},{0,1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        bfs(new BOJ15653_beads(red_bn, red_bm, blue_bn, blue_bm,0));

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    private static void bfs(BOJ15653_beads b) {
        Queue<BOJ15653_beads> q = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();

        q.add(b);
        visited.add(b.toString());

        while(!q.isEmpty()) {
            BOJ15653_beads now = q.poll();

            if(check_map(now)) {
                ans = now.move;
                break;
            }

            for(int i = 0; i < 4; i++) {
                BOJ15653_beads next = tlit_map(i,now);

                if(visited.contains(next.toString()) || next.bn == -1) continue;
                visited.add(next.toString());
                q.add(next);
            }
        }
    }

    private static boolean check_map(BOJ15653_beads b) {
        String s = b.toString();

        boolean flag = false;
        int minus_one_cnt = 0;

        for(String ss : s.split(" ")) {
            if(ss.equals("-1")) minus_one_cnt++;
        }

        if(minus_one_cnt == 2 && s.indexOf("-1") == 0) {
            return !flag;
        }

        return flag;
    }

    private static BOJ15653_beads tlit_map(int i, BOJ15653_beads b) {
        BOJ15653_beads new_b = new BOJ15653_beads(b.rn, b.rm, b.bn, b.bm, b.move);

        boolean red_bead_is_first = true;

        int nrn = b.rn;
        int nrm = b.rm;
        int nbn = b.bn;
        int nbm = b.bm;

        switch (i) {
            case 0:
                // 왼쪽으로 기울이기
                if(nrm > nbm) red_bead_is_first = false;

                if(red_bead_is_first) {
                    while(true) {
                        if(map[nrn][nrm - 1] == '.') {
                            nrm--;
                            continue;
                        }
                        else if(map[nrn][nrm - 1] == 'O') {
                            nrn = nrm = -1;
                            break;
                        } else if(map[nrn][nrm - 1] == '#') break;
                    }

                    while(true) {
                        if(map[nbn][nbm - 1] == '.') {
                            if(nrn == nbn && nrm == nbm - 1) break;
                            nbm--;
                            continue;
                        } else if(map[nbn][nbm - 1] == 'O') {
                            nbn = nbm = -1;
                            break;
                        } else if(map[nbn][nbm - 1] == '#') break;
                    }
                } else {
                    while(true) {
                        if(map[nbn][nbm - 1] == '.') {
                            nbm--;
                            continue;
                        } else if(map[nbn][nbm - 1] == 'O') {
                            nbn = nbm = -1;
                            break;
                        } else if(map[nbn][nbm - 1] == '#') break;
                    }

                    while(true) {
                        if(map[nrn][nrm - 1] == '.') {
                            if(nrn == nbn && nrm - 1 == nbm) break;
                            nrm--;
                            continue;
                        } else if(map[nrn][nrm - 1] == 'O') {
                            nrn = nrm = -1;
                            break;
                        } else if(map[nrn][nrm - 1] == '#') break;
                    }
                }
                break;
            case 1:
                //위쪽으로 기울이기
                if(nrn > nbn) red_bead_is_first = false;

                if(red_bead_is_first) {
                    while(true) {
                        if(map[nrn - 1][nrm] == '.') {
                            nrn--;
                            continue;
                        }
                        else if(map[nrn - 1][nrm] == 'O') {
                            nrn = nrm = -1;
                            break;
                        } else if(map[nrn - 1][nrm] == '#') break;
                    }

                    while(true) {
                        if(map[nbn - 1][nbm] == '.') {
                            if (nrn == nbn - 1 && nrm == nbm) break;
                            nbn--;
                            continue;
                        } else if(map[nbn - 1][nbm] == 'O') {
                            nbn = nbm = -1;
                            break;
                        } else if(map[nbn - 1][nbm] == '#') break;
                    }
                } else {
                    while(true) {
                        if(map[nbn - 1][nbm] == '.') {
                            nbn--;
                            continue;
                        }
                        else if(map[nbn - 1][nbm] == 'O') {
                            nbn = nbm = -1;
                            break;
                        } else if(map[nbn - 1][nbm] == '#') break;
                    }

                    while(true) {
                        if(map[nrn - 1][nrm] == '.') {
                            if(nrn - 1 == nbn && nrm == nbm) break;
                            nrn--;
                            continue;
                        } else if(map[nrn - 1][nrm] == 'O') {
                            nrn = nrm = -1;
                            break;
                        } else if(map[nrn - 1][nrm] == '#') break;
                    }
                }
                break;
            case 2:
                // 오른쪽으로 기울이기
                if(nrm < nbm) red_bead_is_first = false;

                if(red_bead_is_first) {
                    while(true) {
                        if(map[nrn][nrm + 1] == '.') {
                            nrm++;
                            continue;
                        }
                        else if(map[nrn][nrm + 1] == 'O') {
                            nrn = nrm = -1;
                            break;
                        } else if(map[nrn][nrm + 1] == '#') break;
                    }

                    while(true) {
                        if(map[nbn][nbm + 1] == '.') {
                            if(nrn == nbn && nrm == nbm + 1) break;
                            nbm++;
                            continue;
                        } else if(map[nbn][nbm + 1] == 'O') {
                            nbn = nbm = -1;
                            break;
                        } else if(map[nbn][nbm + 1] == '#') break;
                    }
                } else {
                    while(true) {
                        if(map[nbn][nbm + 1] == '.') {
                            nbm++;
                            continue;
                        }
                        else if(map[nbn][nbm + 1] == 'O') {
                            nbn = nbm = -1;
                            break;
                        } else if(map[nbn][nbm + 1] == '#') break;
                    }

                    while(true) {
                        if(map[nrn][nrm + 1] == '.') {
                            if(nrn == nbn && nrm + 1 == nbm) break;
                            nrm++;
                            continue;
                        } else if(map[nrn][nrm + 1] == 'O') {
                            nrn = nrm = -1;
                            break;
                        } else if(map[nrn][nrm + 1] == '#') break;
                    }
                }
                break;
            case 3:
                // 아래쪽으로 기울이기
                if(nrn < nbn) red_bead_is_first = false;

                if(red_bead_is_first) {
                    while(true) {
                        if(map[nrn + 1][nrm] == '.') {
                            nrn++;
                            continue;
                        } else if(map[nrn + 1][nrm] == 'O') {
                            nrn = nrm = -1;
                            break;
                        } else if(map[nrn + 1][nrm] == '#') break;
                    }

                    while(true) {
                        if(map[nbn + 1][nbm] == '.') {
                            if(nrn == nbn + 1 && nrm == nbm) break;
                            nbn++;
                            continue;
                        } else if(map[nbn + 1][nbm] == 'O') {
                            nbn = nbm = -1;
                            break;
                        } else if(map[nbn + 1][nbm] == '#') break;
                    }
                } else {
                    while(true) {
                        if(map[nbn + 1][nbm] == '.') {
                            nbn++;
                            continue;
                        } else if(map[nbn + 1][nbm] == 'O') {
                            nbn = nbm = -1;
                            break;
                        } else if(map[nbn + 1][nbm] == '#') break;
                    }

                    while(true) {
                        if(map[nrn + 1][nrm] == '.') {
                            if(nrn + 1 == nbn && nrm == nbm) break;
                            nrn++;
                            continue;
                        } else if(map[nrn + 1][nrm] == 'O') {
                            nrn = nrm = -1;
                            break;
                        } else if(map[nrn + 1][nrm] == '#') break;
                    }
                }
                break;
        }

        new_b.rn = nrn;
        new_b.rm = nrm;
        new_b.bn = nbn;
        new_b.bm = nbm;
        new_b.move = b.move + 1;

        return new_b;
    }


    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        ans = Integer.MAX_VALUE;

        map = new char[N + 1][M + 1];

        for(int i = 1; i <= N; i++) {
            input = br.readLine().split("");
            for(int j = 1; j <= M; j++) {
                map[i][j] = input[j-1].charAt(0);

                if(map[i][j] == 'B') {
                    blue_bn = i;
                    blue_bm = j;
                    map[i][j] = '.';
                }

                if(map[i][j] == 'R') {
                    red_bn = i;
                    red_bm = j;
                    map[i][j] = '.';
                }
            }
        }
    }
}
