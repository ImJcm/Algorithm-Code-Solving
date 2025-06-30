package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
탈출 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	60275	21781	14950	34.118%
문제
사악한 암흑의 군주 이민혁은 드디어 마법 구슬을 손에 넣었고, 그 능력을 실험해보기 위해 근처의 티떱숲에 홍수를 일으키려고 한다. 이 숲에는 고슴도치가 한 마리 살고 있다. 고슴도치는 제일 친한 친구인 비버의 굴로 가능한 빨리 도망가 홍수를 피하려고 한다.

티떱숲의 지도는 R행 C열로 이루어져 있다. 비어있는 곳은 '.'로 표시되어 있고, 물이 차있는 지역은 '*', 돌은 'X'로 표시되어 있다. 비버의 굴은 'D'로, 고슴도치의 위치는 'S'로 나타내어져 있다.

매 분마다 고슴도치는 현재 있는 칸과 인접한 네 칸 중 하나로 이동할 수 있다. (위, 아래, 오른쪽, 왼쪽) 물도 매 분마다 비어있는 칸으로 확장한다. 물이 있는 칸과 인접해있는 비어있는 칸(적어도 한 변을 공유)은 물이 차게 된다. 물과 고슴도치는 돌을 통과할 수 없다. 또, 고슴도치는 물로 차있는 구역으로 이동할 수 없고, 물도 비버의 소굴로 이동할 수 없다.

티떱숲의 지도가 주어졌을 때, 고슴도치가 안전하게 비버의 굴로 이동하기 위해 필요한 최소 시간을 구하는 프로그램을 작성하시오.

고슴도치는 물이 찰 예정인 칸으로 이동할 수 없다. 즉, 다음 시간에 물이 찰 예정인 칸으로 고슴도치는 이동할 수 없다. 이동할 수 있으면 고슴도치가 물에 빠지기 때문이다.

입력
첫째 줄에 50보다 작거나 같은 자연수 R과 C가 주어진다.

다음 R개 줄에는 티떱숲의 지도가 주어지며, 문제에서 설명한 문자만 주어진다. 'D'와 'S'는 하나씩만 주어진다.

출력
첫째 줄에 고슴도치가 비버의 굴로 이동할 수 있는 가장 빠른 시간을 출력한다. 만약, 안전하게 비버의 굴로 이동할 수 없다면, "KAKTUS"를 출력한다.

예제 입력 1
3 3
D.*
...
.S.
예제 출력 1
3
예제 입력 2
3 3
D.*
...
..S
예제 출력 2
KAKTUS
예제 입력 3
3 6
D...*.
.X.X..
....S.
예제 출력 3
6
예제 입력 4
5 4
.D.*
....
..X.
S.*.
....
예제 출력 4
4
출처
Contest > Croatian Open Competition in Informatics > COCI 2006/2007 > Contest #1 4번

문제를 번역한 사람: baekjoon
데이터를 추가한 사람: cjstjdgur123, djm03178, jn307742
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
격자 그래프
 */
/*
알고리즘 핵심
BFS
1. 고슴도치의 방문여부와 물의 이동의 방문여부를 따로 체크하고, 이동 순서를 물 -> 고슴도치 순으로 결정하여 물이 찰 예정인 칸으로
고슴도치의 이동을 막으면서 이동을 수행한다.
2. 현재 이동 대상을 구분하여 다음 이동 공간의 조건을 달리 체크한다.
공통적으로 다음 이동하는 곳이 돌인 경우, 이동이 불가능하다.
현재 이동 대상이 고슴도치인 경우, 다음 이동하는 공간이 고슴도치가 방문한 곳이거나 물이 찬 곳이라면 이동이 불가능하다.
현재 이동 대상이 물인 경우, 다음 이동하는 공간이 물이 찬 곳이거나 비버의 소굴이라면 이동이 불가능하다.
3. 고슴도치가 비버의 소굴에 도달하면 이동에 걸린 시간을 ans에 업데이트하고, 불가능한 경우, KAKTUS를 업데이트하고 출력한다.
 */
public class BOJ3055 {
    static class BOJ3055_space {
        int r,c,time;
        char status;

        BOJ3055_space(int r, int c, char s, int t) {
            this.r = r;
            this.c = c;
            this.status = s;
            this.time = t;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int R,C,ans;
    static int[][] direction = {{0,1},{0,-1},{1,0},{-1,0}};
    static BOJ3055_space D,S;
    static ArrayList<BOJ3055_space> water;
    static BOJ3055_space[][] tea_tree_forest;
    static final String KAKTUS = "KAKTUS";

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        Hedgehog_migration(S,water);

        System.out.println(ans == Integer.MAX_VALUE ? KAKTUS : ans);
    }

    private static void Hedgehog_migration(BOJ3055_space s, ArrayList<BOJ3055_space> water) {
        Queue<BOJ3055_space> q = new LinkedList<>();
        boolean[][] visited_hedgehog = new boolean[R][C];
        boolean[][] visited_water = new boolean[R][C];

        q.addAll(water);
        for(BOJ3055_space w : water) {
            visited_water[w.r][w.c] = true;
        }

        q.add(s);
        visited_hedgehog[s.r][s.c] = true;


        while(!q.isEmpty()) {
            BOJ3055_space now = q.poll();

            if(tea_tree_forest[now.r][now.c].status == D.status) {
                ans = now.time;
                return;
            }

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
                if(tea_tree_forest[nr][nc].status == 'X') continue;
                if(now.status == 'S' && (visited_hedgehog[nr][nc] || visited_water[nr][nc])) continue;
                if(now.status == '*' && (visited_water[nr][nc] || tea_tree_forest[nr][nc].status == 'D')) continue;

                if(now.status == 'S') {
                    visited_hedgehog[nr][nc] = true;
                    q.add(new BOJ3055_space(nr,nc,now.status,now.time + 1));
                }

                if(now.status == '*') {
                    visited_water[nr][nc] = true;
                    q.add(new BOJ3055_space(nr,nc,now.status, now.time + 1));
                }
            }
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        R = Integer.parseInt(input[0]);
        C = Integer.parseInt(input[1]);

        water = new ArrayList<>();
        tea_tree_forest = new BOJ3055_space[R][C];

        for(int r = 0; r < R; r++) {
            input = br.readLine().split("");
            for(int c = 0; c < C; c++) {
                tea_tree_forest[r][c] = new BOJ3055_space(r,c,input[c].charAt(0),0);

                if(tea_tree_forest[r][c].status == 'D') {
                    D = tea_tree_forest[r][c];
                }

                if(tea_tree_forest[r][c].status == 'S') {
                    S = tea_tree_forest[r][c];
                }

                if(tea_tree_forest[r][c].status == '*') {
                    water.add(tea_tree_forest[r][c]);
                }
            }
        }

        ans = Integer.MAX_VALUE;
    }
}
