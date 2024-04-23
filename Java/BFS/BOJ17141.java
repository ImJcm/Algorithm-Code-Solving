package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
연구소 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	10891	4566	3285	44.165%
문제
인체에 치명적인 바이러스를 연구하던 연구소에 승원이가 침입했고, 바이러스를 유출하려고 한다. 승원이는 연구소의 특정 위치에 바이러스 M개를 놓을 것이고, 승원이의 신호와 동시에 바이러스는 퍼지게 된다.

연구소는 크기가 N×N인 정사각형으로 나타낼 수 있으며, 정사각형은 1×1 크기의 정사각형으로 나누어져 있다. 연구소는 빈 칸, 벽으로 이루어져 있으며, 벽은 칸 하나를 가득 차지한다.

일부 빈 칸은 바이러스를 놓을 수 있는 칸이다. 바이러스는 상하좌우로 인접한 모든 빈 칸으로 동시에 복제되며, 1초가 걸린다.

예를 들어, 아래와 같이 연구소가 생긴 경우를 살펴보자. 0은 빈 칸, 1은 벽, 2는 바이러스를 놓을 수 있는 칸이다.

2 0 0 0 1 1 0
0 0 1 0 1 2 0
0 1 1 0 1 0 0
0 1 0 0 0 0 0
0 0 0 2 0 1 1
0 1 0 0 0 0 0
2 1 0 0 0 0 2
M = 3이고, 바이러스를 아래와 같이 놓은 경우 6초면 모든 칸에 바이러스를 퍼뜨릴 수 있다. 벽은 -, 바이러스를 놓은 위치는 0, 빈 칸은 바이러스가 퍼지는 시간으로 표시했다.

6 6 5 4 - - 2
5 6 - 3 - 0 1
4 - - 2 - 1 2
3 - 2 1 2 2 3
2 2 1 0 1 - -
1 - 2 1 2 3 4
0 - 3 2 3 4 5
시간이 최소가 되는 방법은 아래와 같고, 5초만에 모든 칸에 바이러스를 퍼뜨릴 수 있다.

0 1 2 3 - - 2
1 2 - 3 - 0 1
2 - - 2 - 1 2
3 - 2 1 2 2 3
3 2 1 0 1 - -
4 - 2 1 2 3 4
5 - 3 2 3 4 5
연구소의 상태가 주어졌을 때, 모든 빈 칸에 바이러스를 퍼뜨리는 최소 시간을 구해보자.

입력
첫째 줄에 연구소의 크기 N(5 ≤ N ≤ 50), 놓을 수 있는 바이러스의 개수 M(1 ≤ M ≤ 10)이 주어진다.

둘째 줄부터 N개의 줄에 연구소의 상태가 주어진다. 0은 빈 칸, 1은 벽, 2는 바이러스를 놓을 수 있는 칸이다. 2의 개수는 M보다 크거나 같고, 10보다 작거나 같은 자연수이다.

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
5
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
5
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
4
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: gkgg123, heon0128, seico75
알고리즘 분류
그래프 이론
브루트포스 알고리즘
그래프 탐색
너비 우선 탐색
 */
/*
dfs_select_virus_room으로 바이러스가 위치할 수 있는 (r,c) 좌표를 M개 선택하여 bfs를 돌린 후, 걸린 time 중 최소값을 업데이트하는 로직인데
처음 코드는 dfs로 어떠한 바이러스에 배치하는 과정에서 중복되는 위치에서 bfs를 중복수행하여 "시간초과"가 발생하여서 중복되는 위치를
제거하기 위해 아래 코드를 구성하였지만, "메모리 초과"가 발생하였다.
 */
public class BOJ17141 {
    static class BOJ17141_room implements Comparable<BOJ17141_room> {
        int r,c;
        int move;

        BOJ17141_room(int r, int c, int move) {
            this.r = r;
            this.c = c;
            this.move = move;
        }

        @Override
        public int compareTo(BOJ17141_room o) {
            if (this.r < o.r) {
                return -1;
            } else if(this.r > o.r) {
                return 1;
            } else {
                return this.c - o.c;
            }
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,min_move = Integer.MAX_VALUE;
    static int[][] labs,move_state;
    static ArrayList<Boolean> visited;
    static HashSet<String> dfs_visited;
    static ArrayList<BOJ17141_room> possible_virus;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        dfs_select_virus_room(0, new ArrayList<>());
        System.out.println(min_move == Integer.MAX_VALUE ? -1 : min_move);
    }

    static void dfs_select_virus_room(int depth, ArrayList<BOJ17141_room> rooms) {
        if(depth == M) {
            String visit_check = "";
            Collections.sort(rooms);
            for(BOJ17141_room room : rooms) {
                visit_check += (room.r + "" + room.c);
            }

            if(dfs_visited.contains(visit_check)) return;

            int cnt = bfs(rooms);
            dfs_visited.add(visit_check);

            for(int r=1;r<=N;r++) {
                for(int c=1;c<=N;c++) {
                    if(labs[r][c] != 1 && move_state[r][c] == Integer.MAX_VALUE) {
                        return;
                    }
                }
            }
            min_move = Math.min(min_move,cnt);
            return;
        }

        for(int i=0;i<possible_virus.size();i++) {
            if(visited.get(i)) continue;
            visited.set(i,true);
            rooms.add(possible_virus.get(i));
            dfs_select_virus_room(depth + 1, rooms);
            rooms.remove(possible_virus.get(i));
            visited.set(i,false);
        }
    }

    static int bfs(ArrayList<BOJ17141_room> rooms) {
        int max_time = 0;
        Queue<BOJ17141_room> q = new LinkedList<>(rooms);

        for(int i=1;i<=N;i++) {
            Arrays.fill(move_state[i],Integer.MAX_VALUE);
        }

        for(int i=0;i<rooms.size();i++) {
            rooms.get(i).move = 0;
            BOJ17141_room room = rooms.get(i);
            move_state[room.r][room.c] = 0;
        }

        while(!q.isEmpty()) {
            BOJ17141_room now = q.poll();

            max_time = Math.max(now.move, max_time);

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 1 || nr > N || nc < 1 || nc > N || labs[nr][nc] == 1) continue;
                if(now.move + 1 > move_state[nr][nc]) continue;
                move_state[nr][nc] = now.move + 1;
                q.offer(new BOJ17141_room(nr,nc,now.move + 1));
            }
        }
        return max_time;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        labs = new int[N+1][N+1];
        move_state = new int[N+1][N+1];
        visited = new ArrayList<>();
        dfs_visited = new HashSet<>();
        possible_virus = new ArrayList<>();

        for(int r=1;r<=N;r++) {
            input = br.readLine().split(" ");
            for(int c=1;c<=N;c++) {
                int state = Integer.parseInt(input[c-1]);
                labs[r][c] = state;

                if(state == 2) {
                    possible_virus.add(new BOJ17141_room(r,c,0));
                    visited.add(false);
                }
            }
        }
    }
}
