package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
캐슬 디펜스

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	35390	13272	8123	33.251%
문제
캐슬 디펜스는 성을 향해 몰려오는 적을 잡는 턴 방식의 게임이다. 게임이 진행되는 곳은 크기가 N×M인 격자판으로 나타낼 수 있다. 격자판은 1×1 크기의 칸으로 나누어져 있고, 각 칸에 포함된 적의 수는 최대 하나이다. 격자판의 N번행의 바로 아래(N+1번 행)의 모든 칸에는 성이 있다.

성을 적에게서 지키기 위해 궁수 3명을 배치하려고 한다. 궁수는 성이 있는 칸에 배치할 수 있고, 하나의 칸에는 최대 1명의 궁수만 있을 수 있다. 각각의 턴마다 궁수는 적 하나를 공격할 수 있고, 모든 궁수는 동시에 공격한다. 궁수가 공격하는 적은 거리가 D이하인 적 중에서 가장 가까운 적이고, 그러한 적이 여럿일 경우에는 가장 왼쪽에 있는 적을 공격한다. 같은 적이 여러 궁수에게 공격당할 수 있다. 공격받은 적은 게임에서 제외된다. 궁수의 공격이 끝나면, 적이 이동한다. 적은 아래로 한 칸 이동하며, 성이 있는 칸으로 이동한 경우에는 게임에서 제외된다. 모든 적이 격자판에서 제외되면 게임이 끝난다.

게임 설명에서 보다시피 궁수를 배치한 이후의 게임 진행은 정해져있다. 따라서, 이 게임은 궁수의 위치가 중요하다. 격자판의 상태가 주어졌을 때, 궁수의 공격으로 제거할 수 있는 적의 최대 수를 계산해보자.

격자판의 두 위치 (r1, c1), (r2, c2)의 거리는 |r1-r2| + |c1-c2|이다.

입력
첫째 줄에 격자판 행의 수 N, 열의 수 M, 궁수의 공격 거리 제한 D가 주어진다. 둘째 줄부터 N개의 줄에는 격자판의 상태가 주어진다. 0은 빈 칸, 1은 적이 있는 칸이다.

출력
첫째 줄에 궁수의 공격으로 제거할 수 있는 적의 최대 수를 출력한다.

제한
3 ≤ N, M ≤ 15
1 ≤ D ≤ 10
예제 입력 1
5 5 1
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
1 1 1 1 1
예제 출력 1
3
예제 입력 2
5 5 1
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
1 1 1 1 1
0 0 0 0 0
예제 출력 2
3
예제 입력 3
5 5 2
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
1 1 1 1 1
0 0 0 0 0
예제 출력 3
5
예제 입력 4
5 5 5
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
예제 출력 4
15
예제 입력 5
6 5 1
1 0 1 0 1
0 1 0 1 0
1 1 0 0 0
0 0 0 1 1
1 1 0 1 1
0 0 1 0 0
예제 출력 5
9
예제 입력 6
6 5 2
1 0 1 0 1
0 1 0 1 0
1 1 0 0 0
0 0 0 1 1
1 1 0 1 1
0 0 1 0 0
예제 출력 6
14
출처
문제를 만든 사람: baekjoon
문제의 오타를 찾은 사람: lja9702, YunGoon
알고리즘 분류
구현
그래프 이론
브루트포스 알고리즘
그래프 탐색
시뮬레이션
너비 우선 탐색
 */
/*
알고리즘 핵심
- choice_defence_situation() : 궁수를 배치할 수 있는 모든 경우의 수만큼 반복하여 N만큼 턴을 진행하면서 궁수로 잡을 수 있는 적의 개수를 업데이트한다.
- start_defence() : 3명의 궁수 중 한명씩 해당 궁수의 위치에서 bfs를 수행하여 공격 범위 내의 모든 적을 탐색한 후, 우선순위 큐를 통해 거리가 가장 가까운 적이고, 이러한 적이 여려명인 경우 가장 왼쪽 적을 반환한다.
- cal_distance() : 궁수의 위치와 잡을 적의 위치의 거리를 계산하여 반환한다.
- next_turn_grid_board() : 현재 적의 상태를 나타내는 grid_board에서 턴을 진행하여 다음 grid_board의 상태로 업데이트한다.
- choice_archer_position() : M개의 행에서 궁수를 배치할 수 있는 모든 경우의 수를 archer_position_list에 업데이트한다.
- copy_grid_board() : 궁수의 위치마다 최대로 잡을 수 있는 적의 개수를 계산하기 위해 처음 상태의 grid_board의 상태를 유지하기 위한 deep copy하여 반환한다.
 */
public class BOJ17135 {
    static class BOJ17135_grid {
        /*
            r : row
            c : col
            u : 0 - empty place, 1 - enemy, 2 - archer
         */
        int r,c,u;

        BOJ17135_grid(int r, int c, int u) {
            this.r = r;
            this.c = c;
            this.u = u;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,D,ans;
    static BOJ17135_grid[][] grid_board;
    static ArrayList<BOJ17135_grid[]> archer_position_list;
    static BOJ17135_grid[] archer_position;
    static int[][] directions = {{0,-1},{-1,0},{0,1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        choice_archer_position();

        choice_defence_situation();

        System.out.println(ans);
    }

    static void choice_defence_situation() {
        for(BOJ17135_grid[] archers : archer_position_list) {
            int attack_target_cnt = 0;
            BOJ17135_grid[][] new_grid_board = copy_grid_board();

            for(int r=0;r<N;r++) {
                ArrayList<BOJ17135_grid> attack_target = new ArrayList<>();

                for(BOJ17135_grid archer : archers) {
                    BOJ17135_grid a_t = start_defence(archer, new_grid_board);
                    if(a_t != null) {
                        attack_target.add(a_t);
                    }
                }

                for(BOJ17135_grid a_t : attack_target) {
                    if(new_grid_board[a_t.r][a_t.c].u == 1) {
                        attack_target_cnt++;
                        new_grid_board[a_t.r][a_t.c].u = 0;
                    }
                }

                next_turn_grid_board(new_grid_board);
            }

            ans = Math.max(ans, attack_target_cnt);
        }
    }

    static BOJ17135_grid start_defence(BOJ17135_grid archer, BOJ17135_grid[][] g_board) {
        Queue<BOJ17135_grid> q = new LinkedList<>();
        PriorityQueue<BOJ17135_grid> pq = new PriorityQueue<>(new Comparator<BOJ17135_grid>() {
            @Override
            public int compare(BOJ17135_grid o1, BOJ17135_grid o2) {
                int o1_d = Math.abs(o1.r - archer.r) + Math.abs(o1.c - archer.c);
                int o2_d = Math.abs(o2.r - archer.r) + Math.abs(o2.c - archer.c);

                if(o1_d < o2_d) {
                    return -1;
                } else if(o1_d == o2_d) {
                    return o1.c - o2.c;
                } else {
                    return 1;
                }
            }
        });
        boolean[][] visited = new boolean[N+1][M];

        q.offer(archer);

        int a_r = archer.r;
        int a_c = archer.c;

        while(!q.isEmpty()) {
            BOJ17135_grid now_attack_position = q.poll();

            for(int[] d : directions) {
                int nr = now_attack_position.r + d[0];
                int nc = now_attack_position.c + d[1];

                if(nr < 1 || nr > N || nc < 0 || nc >= M) continue;
                if(visited[nr][nc]) continue;
                if(cal_distance(a_r, a_c, nr, nc) > D) continue;

                BOJ17135_grid pos = new BOJ17135_grid(nr,nc,2);

                visited[nr][nc] = true;
                q.offer(pos);

                if(g_board[nr][nc].u == 1) {
                    pq.add(pos);
                }
            }
        }

        return pq.poll();
    }

    static int cal_distance(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }

    static void next_turn_grid_board(BOJ17135_grid[][] new_grid_board) {
        for(int r=N;r>0;r--) {
            for(int c=0;c<M;c++) {
                new_grid_board[r][c].u = new_grid_board[r-1][c].u;
            }
        }
    }

    static void choice_archer_position() {
        dfs(0,0);
    }

    static void dfs(int depth, int s_idx) {
        if(depth == 3) {
            archer_position_list.add(Arrays.copyOf(archer_position,3));
            return;
        }

        for(int i=s_idx;i<M;i++) {
            archer_position[depth] = new BOJ17135_grid(N+1,i,2);
            dfs(depth + 1, i + 1);
        }
    }

    static BOJ17135_grid[][] copy_grid_board() {
        BOJ17135_grid[][] new_board = new BOJ17135_grid[N+1][M];

        for(int r=0;r<=N;r++) {
            for(int c=0;c<M;c++) {
                new_board[r][c] = new BOJ17135_grid(r,c,0);
                new_board[r][c].u = grid_board[r][c].u;
            }
        }
        return new_board;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        D = Integer.parseInt(input[2]);

        ans = 0;

        grid_board = new BOJ17135_grid[N+1][M];
        archer_position_list = new ArrayList<>();
        archer_position = new BOJ17135_grid[3];

        for(int c=0;c<M;c++) {
            grid_board[0][c] = new BOJ17135_grid(0,c,0);
        }

        for(int r=1;r<=N;r++) {
            input = br.readLine().split(" ");
            for(int c=0;c<M;c++) {
                int u = Integer.parseInt(input[c]);

                grid_board[r][c] = new BOJ17135_grid(r,c,u);
            }
        }
    }
}
