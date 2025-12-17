package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
배달

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	3557	1106	852	31.720%
문제
어제 선물을 모두 포장한 민식이는 이제 선물을 배달하려고 한다. 민식이가 선물을 배달할 곳은 이 문제를 읽는 사람들이 앉아 있는 교실이다. 교실은 직사각형모양이고, 모두 같은 크기의 정사각형 블록으로 나누어져 있다.

입력으로 교실의 지도가 주어진다. 각각의 정사각형 블록은 다음과 같이 4가지 종류가 있다.

S: 지금 민식이가 있는 곳이다. 이곳이 민식이가 배달을 시작하는 곳이고 1개만 있다.
C: 민식이가 반드시 선물을 배달해야 하는 곳이다. 이러한 블록은 정확하게 2개 있다.
#: 민식이가 갈 수 없는 곳이다.
.: 민식이가 자유롭게 지나갈 수 있는 곳이다.
민식이가 한 블록 동서남북으로 이동하는데는 1분이 걸린다. 민식이는 네가지 방향 중 하나로 이동할 수 있으며, 교실을 벗어날 수 없다. 민식이가 선물을 배달해야 하는 곳에 들어갈 때, 민식이는 그 곳에 있는 사람 모두에게 선물을 전달해야 한다. 이 상황은 동시에 일어나며, 추가적인 시간이 소요되지 않는다.

민식이는 어느 누구도 자신을 보지 않았으면 하기 때문에, 멈추지 않고 매 시간마다 방향을 바꿔야 한다. 이 말은 같은 방향으로 두 번 연속으로 이동할 수 없다는 말이다. 민식이가 선물을 모두 배달하는데 걸리는 시간의 최솟값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 교실의 세로 크기 N과 가로 크기 M이 주어진다. N과 M은 50보다 작거나 같은 자연수이다. 둘째 줄부터 N개의 줄에 교실의 지도가 주어진다.

출력
첫째 줄에 민식이가 선물을 모두 배달하는데 걸리는 시간의 최솟값을 출력한다. 만약 불가능 할 때는 -1을 출력한다.

예제 입력 1
2 3
SCC
...
예제 출력 1
4
예제 입력 2
1 5
C.C.S
예제 출력 2
-1
예제 입력 3
3 3
#.#
CSC
#.#
예제 출력 3
5
예제 입력 4
10 7
#.#....
##...#.
C#...#.
.....#.
..#....
..#S.#.
.##..#.
###..##
..C.#.#
###.#..
예제 출력 4
24
예제 입력 5
3 36
#.#.#.#.#.#.#.#.#.#.#.#.#.#.#.#.#.#C
.................S..................
C#.#.#.#.#.#.#.#.#.#.#.#.#.#.#.#.#.#
예제 출력 5
155
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
알고리즘 핵심
BFS + 방향 조건
1. 위치를 나타내는 정보 n,m과 이동시간 m, 이전에 사용된 방향 prev_d를 가지는 클래스를 이용하여 bfs를 수행한다.
2. 일반적인 bfs와는 다르게 방향성이 존재하며 S -> C1이 최소 이동시간을 갖는다고해서 S -> C1 -> C2가 최소 이동 시간임을 보장하지 않는다.
따라서, S -> C1 -> C2 / S -> C2 -> C1을 모두 bfs를 돌려 이동시간을 계산해야 한다.
3. S에서 처음 도달하는 C의 prev_d에 따라서 다음 C로의 bfs를 각각 수행해야 한다.
S.//.CC
..//...
인 경우, S -> C1으로 오른쪽 방향으로 8의 시간으로 도착했을 때, 다음 C로 +3을 통해 도착이 가능하지만
S -> C1으로 윗 방향으로 8의 시간으로 도착했을 때, 다음 C로 +1을 통해 도착이 가능하다.

따라서, 첫 C로의 최소시간 이동 경로를 각 도착한 방향에 따라 저장해야 한다.
 */
public class BOJ1175 {
    static class BOJ1175_room {
        int n,m,move,prev_d;

        BOJ1175_room(int n, int m, int move, int prev_d) {
            this.n = n;
            this.m = m;
            this.move = move;
            this.prev_d = prev_d;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,ans;
    static Character[][] classroom;
    static BOJ1175_room s;
    static BOJ1175_room[] cs;
    static int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {

        for(int i = 0; i < 2; i++) {
            BOJ1175_room[] route_1 = bfs(s,cs[i]);
            BOJ1175_room[] route_2;

            for(BOJ1175_room r : route_1) {
                if(r == null) continue;
                route_2 = bfs(r,cs[(i + 1) % 2]);

                for(BOJ1175_room r2 : route_2) {
                    if(r2 == null) continue;

                    ans = Math.min(ans, r2.move);
                }
            }
        }

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    private static BOJ1175_room[] bfs(BOJ1175_room s, BOJ1175_room c) {
        Queue<BOJ1175_room> q = new LinkedList<>();
        boolean[][][] visited = new boolean[N][M][4];

        BOJ1175_room[] route = new BOJ1175_room[4];

        q.add(s);

        while(!q.isEmpty()) {
            BOJ1175_room now = q.poll();

            if(c.n == now.n && c.m == now.m) {
                route[now.prev_d] = route[now.prev_d] == null ? now :
                        route[now.prev_d].move > now.move ? now : route[now.prev_d];
            }

            for(int d = 0; d < directions.length; d++) {
                if(now.prev_d == d) continue;

                int nn = now.n + directions[d][0];
                int nm = now.m + directions[d][1];

                if(nn < 0 || nn >= N || nm < 0 || nm >= M || classroom[nn][nm].equals('#')) continue;
                if(visited[nn][nm][d]) continue;

                visited[nn][nm][d] = true;
                q.add(new BOJ1175_room(nn,nm,now.move + 1,d));
            }
        }

        return route;
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        ans = Integer.MAX_VALUE;

        classroom = new Character[N][M];
        cs = new BOJ1175_room[2];

        int idx = 0;
        for(int i = 0; i < N; i++) {
            classroom[i] = Arrays.stream(br.readLine().split(""))
                    .map(s -> s.charAt(0))
                    .toArray(Character[]::new);

            for(int m = 0; m < M; m++) {
                if(classroom[i][m].equals('S')) {
                    s = new BOJ1175_room(i,m,0,-1);
                }

                if(classroom[i][m].equals('C')) {
                    cs[idx++] = new BOJ1175_room(i,m,0,-1);
                }
            }
        }
    }
}
