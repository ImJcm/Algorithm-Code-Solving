package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
늑대와 양 스페셜 저지
시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	8708	4262	3257	48.743%
문제
크기가 R×C인 목장이 있고, 목장은 1×1 크기의 칸으로 나누어져 있다. 각각의 칸에는 비어있거나, 양 또는 늑대가 있다. 양은 이동하지 않고 위치를 지키고 있고, 늑대는 인접한 칸을 자유롭게 이동할 수 있다. 두 칸이 인접하다는 것은 두 칸이 변을 공유하는 경우이다.

목장에 울타리를 설치해 늑대가 양이 있는 칸으로 갈 수 없게 하려고 한다. 늑대는 울타리가 있는 칸으로는 이동할 수 없다. 울타리를 설치해보자.

입력
첫째 줄에 목장의 크기 R, C가 주어진다.

둘째 줄부터 R개의 줄에 목장의 상태가 주어진다. '.'는 빈 칸, 'S'는 양, 'W'는 늑대이다.

출력
늑대가 양이 있는 칸으로 갈 수 없게 할 수 있다면 첫째 줄에 1을 출력하고, 둘째 줄부터 R개의 줄에 목장의 상태를 출력한다. 울타리는 'D'로 출력한다. 울타리를 어떻게 설치해도 늑대가 양이 있는 칸으로 갈 수 있다면 첫째 줄에 0을 출력한다.

제한
1 ≤ R, C ≤ 500
예제 입력 1
6 6
..S...
..S.W.
.S....
..W...
...W..
......
예제 출력 1
1
..SD..
..SDW.
.SD...
.DW...
DD.W..
......
예제 입력 2
1 2
SW
예제 출력 2
0
예제 입력 3
5 5
.S...
...S.
S....
...S.
.S...
예제 출력 3
1
.S...
...S.
S.D..
...S.
.S...
노트
이 문제는 설치해야 하는 울타리의 최소 개수를 구하는 문제가 아니다.

출처
문제를 번역한 사람: baekjoon
 */
/*
알고리즘 핵심
BFS
1. 입력으로 주어지는 목장의 상태에서 빈공간을 울타리로 모두 채운다. (BFS 수행)
2. 이때, 양과 늑대의 접촉여부를 확인하기 위해 목장의 모든 각각의 양을 기준으로 상하좌우에 늑대가 존재하는지 검사한다.
3. 2번에서 늑대가 존재하면 울타리를 설치해도 양과 늑대의 접촉이 불가피하므로 0을 출력하고, 그렇지 않으면 1을 출력하고 목장의 상태를 출력한다.

힌트에서 주어진 문장처럼 울타리를 어떻게 설치하는지는 상관이 없으므로 모든 공간에 울타리를 설치하고 상태를 확인하는 것이 이 문제의 핵심이다.
 */
public class BOJ16956 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    private static class Solve {
        static class Pos {
            int r,c;

            Pos(int r, int c) {
                this.r = r;
                this.c = c;
            }
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int R,C,sheep_cnt, wolf_cnt;
        char[][] ranch;
        int[][] direction = new int[][] {{1,0},{-1,0},{0,1},{0,-1}};
        ArrayList<Pos> es;   // empty space = '.'

        private void solve() throws IOException {
            init_setting();

            bfs();

            check();
        }

        private void check() {
            for(int i = 0; i < R; i++) {
                for(int j = 0; j < C; j++) {
                    if(ranch[i][j] == 'S' && !near_check(i,j)) {
                        System.out.println(0);
                        return;
                    }
                }
            }

            System.out.println(1);
            print_ranch();
        }

        private void print_ranch() {
            for(int i = 0; i < R; i++) {
                System.out.println(ranch[i]);
            }
        }

        private boolean near_check(int r, int c) {
            for(int[] d : direction) {
                int nr = r + d[0];
                int nc = c + d[1];

                if(nr < 0 || nr >= R || nc < 0 || nc >= C || ranch[nr][nc] == 'D') continue;
                if(ranch[nr][nc] == 'W') return false;
            }

            return true;
        }

        private void bfs() {
            Queue<Pos> q = new LinkedList<>(es);
            boolean[][] visited = new boolean[R][C];

            while(!q.isEmpty()) {
                Pos now = q.poll();

                if(visited[now.r][now.c]) continue;
                visited[now.r][now.c] = true;
                ranch[now.r][now.c] = 'D';

                for(int[] d : direction) {
                    int nr = now.r + d[0];
                    int nc = now.c + d[1];

                    if(nr < 0 || nr >= R || nc < 0 || nc >= C || visited[nr][nc]) continue;
                    if(ranch[nr][nc] == 'S' || ranch[nr][nc] == 'W') continue;

                    q.add(new Pos(nr,nc));
                }
            }
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            R = Integer.parseInt(input[0]);
            C = Integer.parseInt(input[1]);

            sheep_cnt = 0;
            wolf_cnt = 0;

            ranch = new char[R][C];
            es = new ArrayList<>();

            for(int i = 0; i < R; i++) {
                input = br.readLine().split("");
                for(int j = 0; j < C; j++) {
                    char ch = input[j].charAt(0);
                    ranch[i][j] = ch;

                    if(ch == 'S') sheep_cnt++;
                    if(ch == 'W') wolf_cnt++;
                    if(ch == '.') es.add(new Pos(i,j));
                }
            }
        }
    }
}
