package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
연구소 3

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.25 초 (하단 참고)	512 MB	46321	12781	7719	25.835%
문제
인체에 치명적인 바이러스를 연구하던 연구소에 승원이가 침입했고, 바이러스를 유출하려고 한다. 바이러스는 활성 상태와 비활성 상태가 있다. 가장 처음에 모든 바이러스는 비활성 상태이고, 활성 상태인 바이러스는 상하좌우로 인접한 모든 빈 칸으로 동시에 복제되며, 1초가 걸린다. 승원이는 연구소의 바이러스 M개를 활성 상태로 변경하려고 한다.

연구소는 크기가 N×N인 정사각형으로 나타낼 수 있으며, 정사각형은 1×1 크기의 정사각형으로 나누어져 있다. 연구소는 빈 칸, 벽, 바이러스로 이루어져 있으며, 벽은 칸 하나를 가득 차지한다. 활성 바이러스가 비활성 바이러스가 있는 칸으로 가면 비활성 바이러스가 활성으로 변한다.

예를 들어, 아래와 같이 연구소가 생긴 경우를 살펴보자. 0은 빈 칸, 1은 벽, 2는 바이러스의 위치이다.

2 0 0 0 1 1 0
0 0 1 0 1 2 0
0 1 1 0 1 0 0
0 1 0 0 0 0 0
0 0 0 2 0 1 1
0 1 0 0 0 0 0
2 1 0 0 0 0 2
M = 3이고, 바이러스를 아래와 같이 활성 상태로 변경한 경우 6초면 모든 칸에 바이러스를 퍼뜨릴 수 있다. 벽은 -, 비활성 바이러스는 *, 활성 바이러스는 0, 빈 칸은 바이러스가 퍼지는 시간으로 표시했다.

* 6 5 4 - - 2
5 6 - 3 - 0 1
4 - - 2 - 1 2
3 - 2 1 2 2 3
2 2 1 0 1 - -
1 - 2 1 2 3 4
0 - 3 2 3 4 *
시간이 최소가 되는 방법은 아래와 같고, 4초만에 모든 칸에 바이러스를 퍼뜨릴 수 있다.

0 1 2 3 - - 2
1 2 - 3 - 0 1
2 - - 2 - 1 2
3 - 2 1 2 2 3
3 2 1 0 1 - -
4 - 2 1 2 3 4
* - 3 2 3 4 *
연구소의 상태가 주어졌을 때, 모든 빈 칸에 바이러스를 퍼뜨리는 최소 시간을 구해보자.

입력
첫째 줄에 연구소의 크기 N(4 ≤ N ≤ 50), 놓을 수 있는 바이러스의 개수 M(1 ≤ M ≤ 10)이 주어진다.

둘째 줄부터 N개의 줄에 연구소의 상태가 주어진다. 0은 빈 칸, 1은 벽, 2는 비활성 바이러스의 위치이다. 2의 개수는 M보다 크거나 같고, 10보다 작거나 같은 자연수이다.

출력
연구소의 모든 빈 칸에 바이러스가 있게 되는 최소 시간을 출력한다. 바이러스를 어떻게 놓아도 모든 빈 칸에 바이러스를 퍼뜨릴 수 없는 경우에는 -1을 출력한다.

예제 입력 1
7 3
2 0 0 0 1 1 0
0 0 1 0 1 2 0
0 1 1 0 1 0 0
0 1 0 0 0 0 0
0 0 0 2 0 1 1
0 1 0 0 0 0 0
2 1 0 0 0 0 2
예제 출력 1
4
예제 입력 2
7 3
2 0 2 0 1 1 0
0 0 1 0 1 2 0
0 1 1 2 1 0 0
2 1 0 0 0 0 2
0 0 0 2 0 1 1
0 1 0 0 0 0 0
2 1 0 0 2 0 2
예제 출력 2
4
예제 입력 3
7 4
2 0 2 0 1 1 0
0 0 1 0 1 2 0
0 1 1 2 1 0 0
2 1 0 0 0 0 2
0 0 0 2 0 1 1
0 1 0 0 0 0 0
2 1 0 0 2 0 2
예제 출력 3
4
예제 입력 4
7 5
2 0 2 0 1 1 0
0 0 1 0 1 2 0
0 1 1 2 1 0 0
2 1 0 0 0 0 2
0 0 0 2 0 1 1
0 1 0 0 0 0 0
2 1 0 0 2 0 2
예제 출력 4
3
예제 입력 5
7 3
2 0 2 0 1 1 0
0 0 1 0 1 0 0
0 1 1 1 1 0 0
2 1 0 0 0 0 2
1 0 0 0 0 1 1
0 1 0 0 0 0 0
2 1 0 0 2 0 2
예제 출력 5
7
예제 입력 6
7 2
2 0 2 0 1 1 0
0 0 1 0 1 0 0
0 1 1 1 1 0 0
2 1 0 0 0 0 2
1 0 0 0 0 1 1
0 1 0 0 0 0 0
2 1 0 0 2 0 2
예제 출력 6
-1
예제 입력 7
5 1
2 2 2 1 1
2 1 1 1 1
2 1 1 1 1
2 1 1 1 1
2 2 2 1 1
예제 출력 7
0
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: djm03178, gktgnjftm, malbong, publu05, vfjdg12
알고리즘 분류
그래프 이론
브루트포스 알고리즘
그래프 탐색
너비 우선 탐색
 */
/*
82%에서 실패한 이유 : 아래 반례와 같이 virus_cnt를 고려하여 최소시간을 결정하는 경우 잘못된 값을 저장하기 떄문이다.
5 2
0 0 0 2 2
1 1 1 1 0
2 1 2 1 0
2 1 1 1 1
2 2 2 2 0
output = -1
Answer = 3

그리고, 바이러스의 남은 개수는 고려하지 않아도 되서 제거하였다.
- Variable -> static int virus_cnt = 0;          :  line
- bfs()
-> int possible_virus_room = virus_cnt - M;      :  line
-> if(labs[nr][nc] == 2) possible_virus_room--;  :  line
-> if(possible_virus_room == 0 && room_cnt == 0) :  line

- init_setting()
-> virus_cnt++;                                  : line

92%에서 실패한 이유 : 아래 반례와 같이 빈 공간이 없는 경우 bfs 내부의 time의 기본값은 Integer.MAXVALUE인데, 어떠한 time의 값의 변화가 없으므로
원래의 정답은 0이지만 최종적으로는 output이 -1이 된다.
4 4
1 1 1 1
1 2 2 1
1 2 2 1
1 1 1 1
output = -1
Answer = 0

따라서, bfs 내부에서 time의 default 값을 room_cnt의 값에 따라서 조정하여 정답 코드를 구성할 수 있었다.
int time = room_cnt == 0 ? 0 : Integer.MAX_VALUE;

빈 공간이 처음부터 없었다면 시간이 걸릴 수 없기 때문이다.
 */
public class BOJ17142 {
    static class BOJ17142_room {
        int r,c;
        int move;

        BOJ17142_room(int r,int c,int move) {
            this.r = r;
            this.c = c;
            this.move = move;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,min_time = Integer.MAX_VALUE,empty_room_cnt = 0;
    static int[][] labs;
    static ArrayList<BOJ17142_room> possible_virus;
    static BOJ17142_room[] virus;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};


    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        comb_active_virus(0,0);
        System.out.println(min_time == Integer.MAX_VALUE ? -1 : min_time);
    }

    static void comb_active_virus(int cur, int idx) {
        if(idx == M) {
            bfs();
            return;
        }

        if(cur == possible_virus.size()) return;

        virus[idx] = possible_virus.get(cur);
        comb_active_virus(cur + 1, idx + 1);
        comb_active_virus(cur + 1, idx);
    }

    static void bfs() {
        int room_cnt = empty_room_cnt;
        int time = room_cnt == 0 ? 0 : Integer.MAX_VALUE;
        Queue<BOJ17142_room> q = new LinkedList<>();
        boolean[][] visited = new boolean[N+1][N+1];

        for(int i=0;i<virus.length;i++) {
            BOJ17142_room cur_virus = virus[i];
            visited[cur_virus.r][cur_virus.c] = true;
            q.offer(new BOJ17142_room(cur_virus.r, cur_virus.c,0));
        }

        while(!q.isEmpty()) {
            BOJ17142_room now = q.poll();

            if(labs[now.r][now.c] == 0) {
                time = now.move;
            }

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 1 || nr > N || nc < 1 || nc > N || visited[nr][nc] || labs[nr][nc] == 1) continue;
                visited[nr][nc] = true;
                if(labs[nr][nc] == 0) room_cnt--;
                q.offer(new BOJ17142_room(nr,nc,now.move + 1));

            }
        }

        if(room_cnt == 0) {
            min_time = Math.min(min_time, time);
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        labs = new int[N+1][N+1];
        possible_virus = new ArrayList<>();
        virus = new BOJ17142_room[M];

        for(int r=1;r<=N;r++) {
            input = br.readLine().split(" ");
            for(int c=1;c<=N;c++) {
                labs[r][c] = Integer.parseInt(input[c-1]);

                if(labs[r][c] == 2) {
                    possible_virus.add(new BOJ17142_room(r,c,0));
                }

                if(labs[r][c] == 0) {
                    empty_room_cnt++;
                }
            }
        }
    }
}
