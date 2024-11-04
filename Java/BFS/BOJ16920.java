package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
확장 게임

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	8928	2121	1445	23.021%
문제
구사과와 친구들이 확장 게임을 하려고 한다. 이 게임은 크기가 N×M인 격자판 위에서 진행되며, 각 칸은 비어있거나 막혀있다. 각 플레이어는 하나 이상의 성을 가지고 있고, 이 성도 격자판 위에 있다. 한 칸 위에 성이 두 개 이상인 경우는 없다.

게임은 라운드로 이루어져 있고, 각 라운드마다 플레이어는 자기 턴이 돌아올 때마다 성을 확장해야 한다. 제일 먼저 플레이어 1이 확장을 하고, 그 다음 플레이어 2가 확장을 하고, 이런 식으로 라운드가 진행된다.

각 턴이 돌아왔을 때, 플레이어는 자신이 가지고 있는 성을 비어있는 칸으로 확장한다. 플레이어 i는 자신의 성이 있는 곳에서 Si칸 만큼 이동할 수 있는 모든 칸에 성을 동시에 만든다. 위, 왼쪽, 오른쪽, 아래로 인접한 칸으로만 이동할 수 있으며, 벽이나 다른 플레이어의 성이 있는 곳으로는 이동할 수 없다. 성을 다 건설한 이후엔 다음 플레이어가 턴을 갖는다.

모든 플레이어가 더 이상 확장을 할 수 없을 때 게임이 끝난다. 게임판의 초기 상태가 주어졌을 때, 최종 상태를 구해보자.

입력
첫째 줄에 격자판의 크기 N, M과 플레이어의 수 P가 주어진다. 둘째 줄에는 S1, S2, ...SP가 주어진다.

다음 N개의 줄에는 게임판의 상태가 주어진다. '.'는 빈 칸, '#'는 벽, '1', '2', ..., '9'는 각 플레이어의 성이다.

모든 플레이어는 적어도 하나의 성을 가지고 있으며, 게임에 참가하지 않는 플레이어의 성이 있는 경우는 없다.

출력
플레이어 1이 가진 성의 수, 2가 가진 성의 수, ..., P가 가진 성의 수를 공백으로 구분해 출력한다.

제한
1 ≤ N, M ≤ 1,000
1 ≤ P ≤ 9
1 ≤ Si ≤ 109
예제 입력 1
3 3 2
1 1
1..
...
..2
예제 출력 1
6 3
예제 입력 2
3 3 2
1 1
1.1
...
..2
예제 출력 2
7 2
예제 입력 3
4 4 2
1 1
1...
....
....
...2
예제 출력 3
10 6
예제 입력 4
4 4 2
1 1
1..1
....
....
...2
예제 출력 4
11 5
예제 입력 5
4 4 2
2 1
1..1
....
....
...2
예제 출력 5
14 2
예제 입력 6
4 4 2
3 1
1..1
....
....
...2
예제 출력 6
14 2
예제 입력 7
4 4 2
1 1
1..1
#.##
....
...2
예제 출력 7
7 6
예제 입력 8
4 4 2
2 1
1..1
#.##
....
...2
예제 출력 8
10 3
예제 입력 9
3 4 4
1 1 1 1
....
#...
1234
예제 출력 9
1 4 3 3
출처
문제를 번역한 사람: baekjoon
데이터를 추가한 사람: hoxymola
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
알고리즘 핵심
BFS
1. 플레이어 id, n,m 위치 정보, 남은 움직일 수 있는 횟수, 플레이어가 움직일 수 있는 움직이는 횟수의 한계치를 갖는 class
2. 순서대로 플레이어 1~P까지 성 구축을 수행하고 S_i 만큼 성 구축을 마치고 다음 플레이어가 성 구축이 가능해야 한다.
(단일 Deque를 사용하여 순차적으로 S_i만큼의 성 구축을 끝마치지 않았다면 queue의 앞부분으로 넣고 모든 이동이 끝난 경우 queue의 뒷부분에 추가하는 방법을 사용할 수 있지만
해당 방법은 이미 방문한 조건만을 검사하여 해당 위치에서 더 이동이 가능한 방법이 있음에도 올바른 방법을 찾지 못한다. 따라서, queue_main, queue_sub를 두어
플레이어 i가 이동이 가능한 모든 경우를 sub에 두고 이동이 더 이상 불가능한 경우 main에 추가하는 방법으로 상하좌우 한칸씩 이동하는 방법으로 구성하였다.
+ 코드를 구성하고 나서 생각이 드는 방법으로 map 정보에 각 칸마다 성을 구축한 플레이어의 성 정보를 저장과 동시에 각 위치에서 이동이 가능한 횟수를 저장하거나 업데이트하여
queue에 들어간 이동정보를 실시간으로 업데이트하여 Deque의 앞부분 또는 뒷부분에 이동정보를 저장하는 방법이 가능하다고 생각한다.
3. 플레이어가 여려 명인 경우 순차적으로 앞 순번의 플레이어의 모든 성 구축을 마치고 다음 플레이어로 넘어가야 하므로 현재 성 구축을 수행해야하는 플레이어를 구분하기 위해
q_main의 현재 추출할 이동 정보의 id가 이전 정보의 id와 다른 경우를 통해 순차적으로 진행한다.
그리고, 플레이어가 혼자인 경우 현재 이동 정보의 id와 이전 이동 정보의 id가 다른 경우는 없을 수 있으므로 P의 값에 따라 single_target 변수를 사용하여
q_main이 빈 경우 + q_sub가 비어있지 않은 경우 q_sub의 이동 정보를 q_main에 옮길수 있도록한다.
4. 플레이어가 성을 구축하는 경우 ans[player_p]의 성 개수를 업데이트한다.
5. 플레이어가 이동한 위치를 방문여부를 체크하고 이동할 수 있는 횟수가 남은 경우 q_sub에 저장하거나 더 이상 이동횟수가 없는 경우 remain_move를 초기화하여 q_main에 이동정보를 추가한다.
6. 모든 q_main, q_sub가 없는 경우 종료하고 ans[]를 출력하여 플레이어의 각 성의 갯수를 출력한다.

 */
public class BOJ16920 {
    static class BOJ16920_pos {
        int id,n,m,remain_move,possible_move;

        BOJ16920_pos(int id, int n, int m, int r_move, int p_move) {
            this.id = id;
            this.n = n;
            this.m = m;
            this.remain_move = r_move;
            this.possible_move = p_move;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,P;
    static int[] ans,move;
    static ArrayList<BOJ16920_pos> S;
    static char[][] map;
    static int[][] ds = {{-1,0},{1,0},{0,-1},{0,1}};
    static boolean single_target;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        bfs(S);

        for(int c : ans) {
            System.out.print(c + " ");
        }
    }

    private static void bfs(ArrayList<BOJ16920_pos> s) {
        Deque<BOJ16920_pos> q_main = new LinkedList<>();
        Queue<BOJ16920_pos> q_sub = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];
        int id = 1;

        s.sort(new Comparator<BOJ16920_pos>() {
            @Override
            public int compare(BOJ16920_pos o1, BOJ16920_pos o2) {
                return o1.id - o2.id;
            }
        });

        for(BOJ16920_pos p : s) {
            q_main.add(p);
            visited[p.n][p.m] = true;
        }

        while(true) {
            if(q_main.isEmpty() && q_sub.isEmpty()) break;
            if(q_main.isEmpty()) {
                q_main.addAll(q_sub);
                q_sub.clear();
            }

            if((id != q_main.peek().id || single_target) && !q_sub.isEmpty()) {
                for(BOJ16920_pos p : q_sub) {
                    q_main.addFirst(p);
                }
                q_sub.clear();
            }

            BOJ16920_pos now = q_main.poll();

            id = now.id;

            for(int[] d : ds) {
                int nn = now.n + d[0];
                int nm = now.m + d[1];

                if(nn < 0 || nn >= N || nm < 0 || nm >= M) continue;
                if(visited[nn][nm] || map[nn][nm] != '.') continue;

                ans[now.id]++;

                visited[nn][nm] = true;
                if(now.remain_move == 1) {
                    q_main.add(new BOJ16920_pos(now.id,nn,nm,now.possible_move,now.possible_move));
                } else {
                    q_sub.add(new BOJ16920_pos(now.id,nn,nm,now.remain_move - 1, now.possible_move));
                }
            }
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        P = Integer.parseInt(input[2]);

        ans = new int[P];
        move = new int[P];
        S = new ArrayList<>();

        input = br.readLine().split(" ");

        single_target = true;

        if(P != 1) {
            single_target = false;
        }

        for(int i = 0; i < P; i++) {
            int m = Integer.parseInt(input[i]);
            move[i] = m;
        }

        map = new char[N][M];

        for(int i = 0; i < N; i++) {
            input = br.readLine().split("");
            for(int j = 0; j < M; j++) {
                map[i][j] = input[j].charAt(0);

                if(!(map[i][j] == '.' || map[i][j] == '#')) {
                    int id = map[i][j] - '1';
                    ans[id]++;
                    S.add(new BOJ16920_pos(id,i,j,move[id],move[id]));
                }
            }
        }
    }
}
