package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
레이저 통신 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	19641	3726	2582	23.475%
문제
크기가 1×1인 정사각형으로 나누어진 W×H 크기의 지도가 있다. 지도의 각 칸은 빈 칸이거나 벽이며, 두 칸은 'C'로 표시되어 있는 칸이다.

'C'로 표시되어 있는 두 칸을 레이저로 통신하기 위해서 설치해야 하는 거울 개수의 최솟값을 구하는 프로그램을 작성하시오. 레이저로 통신한다는 것은 두 칸을 레이저로 연결할 수 있음을 의미한다.

레이저는 C에서만 발사할 수 있고, 빈 칸에 거울('/', '\')을 설치해서 방향을 90도 회전시킬 수 있다.

아래 그림은 H = 8, W = 7인 경우이고, 빈 칸은 '.', 벽은 '*'로 나타냈다. 왼쪽은 초기 상태, 오른쪽은 최소 개수의 거울을 사용해서 두 'C'를 연결한 것이다.

7 . . . . . . .         7 . . . . . . .
6 . . . . . . C         6 . . . . . /-C
5 . . . . . . *         5 . . . . . | *
4 * * * * * . *         4 * * * * * | *
3 . . . . * . .         3 . . . . * | .
2 . . . . * . .         2 . . . . * | .
1 . C . . * . .         1 . C . . * | .
0 . . . . . . .         0 . \-------/ .
  0 1 2 3 4 5 6           0 1 2 3 4 5 6
입력
첫째 줄에 W와 H가 주어진다. (1 ≤ W, H ≤ 100)

둘째 줄부터 H개의 줄에 지도가 주어진다. 지도의 각 문자가 의미하는 것은 다음과 같다.

.: 빈 칸
*: 벽
C: 레이저로 연결해야 하는 칸
'C'는 항상 두 개이고, 레이저로 연결할 수 있는 입력만 주어진다.

출력
첫째 줄에 C를 연결하기 위해 설치해야 하는 거울 개수의 최솟값을 출력한다.

예제 입력 1
7 8
.......
......C
......*
*****.*
....*..
....*..
.C..*..
.......
예제 출력 1
3
출처
Olympiad > USA Computing Olympiad > 2008-2009 Season > USACO January 2009 Contest > Silver 3번

데이터를 추가한 사람: djm03178, gaelim
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
데이크스트라
최단 경로
 */
/*
처음 코드를 구현하려고할 때, 레이저의 방향을 생각하지 못하고 한번의 bfs를 수행하여 상하좌우 방향으로 한칸씩 이동하면서 최소 거울
개수를 업데이트하려는 방법을 생각하였다. 하지만 해당 방법으로는 레이저의 방향에 따라 필요 거울 개수를 그대로 or + 1할지 결정하는
로직을 만들어내는게 어려웠다. 그래서 처음부터 4가지 레이저 방향으로 bfs를 수행하도록 하고, 레이저 방향을 기억하고 4가지 상하좌우
방향으로 이동을 결정 시, 레이저의 방향과 동일한 이동방향인 경우 거울의 개수를 그대로 업데이트하고 90도의 방향으로 진행 시, + 1을
적용하고 업데이트한다. 이 과정을 수행하여 now에 해당하는 위치가 도착지점인 경우 필요 거울 개수를 최소값으로 업데이트하도록 하였다.
 */
public class BOJ6087 {
    static class BOJ6087_space {
        int r,c;
        int mirror_cnt;
        char wall;
        int razer_direction;

        BOJ6087_space(int r, int c, int m, char w, int r_d) {
            this.r = r;
            this.c = c;
            this.mirror_cnt = m;
            this.wall = w;
            this.razer_direction = r_d;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int W,H,sr,sc,dr,dc,min_mirror_cnt = Integer.MAX_VALUE;
    static BOJ6087_space[][] maps;
    static int[][] direction = {{-1,0}, {0,1}, {1,0}, {0,-1}};



    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    // 4개의 방향으로 레이저 방향 선택 후, 각 방향별 bfs 수행
    static void solve() {
        for(int i=0;i<4;i++) {
            bfs(i);
        }
        check_connection_space_min_mirrors();
    }

    static void bfs(int r_d) {
        Queue<BOJ6087_space> q = new LinkedList<>();

        maps[sr][sc].razer_direction = r_d;
        maps[sr][sc].mirror_cnt = 0;
        q.offer(maps[sr][sc]);

        while(!q.isEmpty()) {
            BOJ6087_space now = q.poll();

            // 도착 지점인 C에 도달 시, 해당 위치에서의 최소 거울의 개수를 최소값으로 업데이트 후, 다음 queue 진행
            if(now.r == dr && now.c == dc) {
                min_mirror_cnt = Math.min(min_mirror_cnt, now.mirror_cnt);
                continue;
            }

            // 4개의 방향인 상하좌우로 이동 선택
            for(int i=0;i<4;i++) {
                int nr = now.r + direction[i][0];
                int nc = now.c + direction[i][1];

                if(nr < 1 || nr > H || nc < 1 || nc > W) continue;          // 범위를 넘어가는 경우
                if(maps[nr][nc].wall == '*') continue;                      // 해당 위치가 벽인 경우
                if(maps[nr][nc].mirror_cnt < now.mirror_cnt + 1) continue;  // 다음 위치가 현재 위치에서의 최소 거울 개수 + 1보다 작은 경우

                if(now.razer_direction == 0) {
                    /*
                        레이저의 방향이 ↑ 인 경우, 다음 이동할 방향에 따라 다른 로직을 수행한다.
                        ↑ 이동인 경우, 거울의 개수를 now의 최소 거울 개수 그대로 업데이트하고,
                        ←, → 이동인 경우, 거울의 개수를 now의 최소 거울 개수에서 + 1한 값으로 업데이트한다.
                        ↓ 이동인 경우, queue에 다음 이동 정보를 넘기지 않는다.
                     */
                    if(i == 0) {
                        maps[nr][nc].mirror_cnt = Math.min(maps[nr][nc].mirror_cnt,now.mirror_cnt);
                    } else if(i == 1 || i == 3) {
                        maps[nr][nc].mirror_cnt = Math.min(maps[nr][nc].mirror_cnt,now.mirror_cnt + 1);
                    } else {
                        continue;
                    }
                } else if(now.razer_direction == 1) {
                    /*
                        레이저의 방향이 → 인 경우, 다음 이동할 방향에 따라 다른 로직을 수행한다.
                        → 이동인 경우, 거울의 개수를 now의 최소 거울 개수 그대로 업데이트하고,
                        ↑, ↓ 이동인 경우, 거울의 개수를 now의 최소 거울 개수에서 + 1한 값으로 업데이트한다.
                        ← 이동인 경우, queue에 다음 이동 정보를 넘기지 않는다.
                     */
                    if(i == 1) {
                        maps[nr][nc].mirror_cnt = Math.min(maps[nr][nc].mirror_cnt,now.mirror_cnt);
                    } else if(i == 0 || i == 2) {
                        maps[nr][nc].mirror_cnt = Math.min(maps[nr][nc].mirror_cnt,now.mirror_cnt + 1);
                    } else {
                        continue;
                    }
                } else if(now.razer_direction == 2) {
                    /*
                        레이저의 방향이 ↓ 인 경우, 다음 이동할 방향에 따라 다른 로직을 수행한다.
                        ↓ 이동인 경우, 거울의 개수를 now의 최소 거울 개수 그대로 업데이트하고,
                        ←, → 이동인 경우, 거울의 개수를 now의 최소 거울 개수에서 + 1한 값으로 업데이트한다.
                        ↑ 이동인 경우, queue에 다음 이동 정보를 넘기지 않는다.
                     */
                    if(i == 2) {
                        maps[nr][nc].mirror_cnt = Math.min(maps[nr][nc].mirror_cnt,now.mirror_cnt);
                    } else if(i == 1 || i == 3) {
                        maps[nr][nc].mirror_cnt = Math.min(maps[nr][nc].mirror_cnt,now.mirror_cnt + 1);
                    } else {
                        continue;
                    }
                } else if(now.razer_direction == 3) {
                    /*
                        레이저의 방향이 ← 인 경우, 다음 이동할 방향에 따라 다른 로직을 수행한다.
                        ← 이동인 경우, 거울의 개수를 now의 최소 거울 개수 그대로 업데이트하고,
                        ↑, ↓ 이동인 경우, 거울의 개수를 now의 최소 거울 개수에서 + 1한 값으로 업데이트한다.
                        → 이동인 경우, queue에 다음 이동 정보를 넘기지 않는다.
                     */
                    if(i == 3) {
                        maps[nr][nc].mirror_cnt = Math.min(maps[nr][nc].mirror_cnt,now.mirror_cnt);
                    } else if(i == 0 || i == 2) {
                        maps[nr][nc].mirror_cnt = Math.min(maps[nr][nc].mirror_cnt,now.mirror_cnt + 1);
                    } else {
                        continue;
                    }
                }

                // 기존의 maps[nr][nc]에 razer_direction을 설정한 상태로 queue에 전달 시, razer의 방향이 올바르지 않을 수 있으므로
                // 새로운 BOJ6087_space를 생성하여 최소 거울 개수와 razer_direction을 설정하여 queue에 전달한다.
                q.offer(new BOJ6087_space(nr,nc,maps[nr][nc].mirror_cnt,maps[nr][nc].wall, i));
            }
        }
    }

    static void check_connection_space_min_mirrors() {
        System.out.println(min_mirror_cnt);
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        W = Integer.parseInt(input[0]);
        H = Integer.parseInt(input[1]);

        maps = new BOJ6087_space[H+1][W+1];

        boolean start_end_position = true;
        for(int i=1;i<=H;i++) {
            input = br.readLine().split("");
            for(int j=1;j<=W;j++) {
                maps[i][j] = new BOJ6087_space(i,j,Integer.MAX_VALUE,input[j-1].charAt(0), -1);
                if(maps[i][j].wall == 'C') {
                    if(start_end_position) {
                        start_end_position = false;
                        sr = i;
                        sc = j;
                    } else {
                        dr = i;
                        dc = j;
                    }
                }
            }
        }
    }
}
