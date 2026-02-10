package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
거울 설치

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	12871	3563	2420	26.940%
문제
채영이는 거울을 들여다보는 것을 참 좋아한다. 그래서 집 곳곳에 거울을 설치해두고 집 안을 돌아다닐 때마다 거울을 보곤 한다.

채영이는 새 해를 맞이하여 이사를 하게 되었는데, 거울을 좋아하는 그녀의 성격 때문에 새 집에도 거울을 매달만한 위치가 여러 곳 있다. 또한 채영이네 새 집에는 문이 두 개 있는데, 채영이는 거울을 잘 설치하여 장난을 치고 싶어졌다. 즉, 한 쪽 문에서 다른 쪽 문을 볼 수 있도록 거울을 설치하고 싶어졌다.

채영이네 집에 대한 정보가 주어졌을 때, 한 쪽 문에서 다른 쪽 문을 볼 수 있도록 하기 위해 설치해야 하는 거울의 최소 개수를 구하는 프로그램을 작성하시오.

거울을 설치할 때에는 45도 기울어진 대각선 방향으로 설치해야 한다. 또한 모든 거울은 양면 거울이기 때문에 양 쪽 모두에서 반사가 일어날 수 있다. 채영이는 거울을 매우 많이 가지고 있어서 거울이 부족한 경우는 없다고 하자.

거울을 어떻게 설치해도 한 쪽 문에서 다른 쪽 문을 볼 수 없는 경우는 주어지지 않는다.

입력
첫째 줄에 집의 크기 N (2 ≤ N ≤ 50)이 주어진다. 다음 N개의 줄에는 N개의 문자로 집에 대한 정보가 주어진다. ‘#’는 문이 설치된 곳으로 항상 두 곳이며, ‘.’은 아무 것도 없는 것으로 빛은 이 곳을 통과한다. ‘!’은 거울을 설치할 수 있는 위치를 나타내고, ‘*’은 빛이 통과할 수 없는 벽을 나타낸다.

출력
첫째 줄에 설치해야 할 거울의 최소 개수를 출력한다.

예제 입력 1
5
***#*
*.!.*
*!.!*
*.!.*
*#***
예제 출력 1
2
출처
문제의 오타를 찾은 사람: cokcjswo, jinhot
데이터를 추가한 사람: doju, sait2000
빠진 조건을 찾은 사람: victory277
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
최단 경로
데이크스트라
 */
/*
알고리즘 핵심
BFS
1. 한 지점의 문에서 빛이 진행할 때, 거울이 존재하는 지점을 기준으로 빛이 나아가는 방향으로 방문 여부를 검사한다.
2. 거울은 45방향으로 설치할 수 있으므로 0(0), 1(90), 2(180), 3(270)을 방향으로 잡고 빛이 들어오는 방향의 양쪽 방향으로 빛이 진행할 수 있는지 검사한다.
3. 또한 빛이 진행하는 과정에서 거울을 발견할 때, 해당 위치에 거울을 설치할 수도 아닐 수도 있기 때문에 해당 조건을 유의한다.
4. BFS특징으로 처음 도달하는 위치가 최소 경로임을 만족하므로 문에서 다른 문으로 도착하는 경우가 최소 거울을 만족한다.
 */
public class BOJ2151 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    private static class Solve {
        static class Pos {
            int x,y,d,c;

            Pos(int x, int y, int d, int c) {
                this.x = x;
                this.y = y;
                this.d = d;
                this.c = c;
            }
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,ans;
        int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};
        char[][] house;
        Pos door_s,door_e;

        private void solve() throws IOException {
            init_setting();

            bfs();

            System.out.println(ans);
        }

        private void bfs() {
            Queue<Pos> q = new LinkedList<>();
            boolean[][][] visited = new boolean[N][N][4];
            for(int d = 0; d < 4; d++) {
                q.add(new Pos(door_s.x,door_s.y,d,door_s.c));
                visited[door_s.x][door_s.y][d] = true;
            }

            while(!q.isEmpty()) {
                Pos now = q.poll();

                if (now.x == door_e.x && now.y == door_e.y) {
                    ans = now.c;
                    return;
                }

                int nx = now.x;
                int ny = now.y;

                while (true) {
                    nx += direction[now.d][0];
                    ny += direction[now.d][1];

                    if (nx < 0 || nx >= N || ny < 0 || ny >= N || house[nx][ny] == '*') break;
                    if (house[nx][ny] == '!') {
                        if(!visited[nx][ny][(now.d + 1) % 4]) {
                            visited[nx][ny][(now.d + 1) % 4] = true;
                            q.add(new Pos(nx,ny,(now.d + 1) % 4,now.c + 1));
                        }
                        if(!visited[nx][ny][(now.d + 3) % 4]) {
                            visited[nx][ny][(now.d + 3) % 4] = true;
                            q.add(new Pos(nx,ny,(now.d + 3) % 4,now.c + 1));
                        }
                        // break; <- 빛의 진행방향에 존재하는 거울을 설치하지 않는 경우도 있을 수 있다.
                    }
                    if(house[nx][ny] == '#') {
                        if(nx == door_s.x && ny == door_s.y) break;
                        if(nx == door_e.x && ny == door_e.y) q.add(new Pos(nx,ny,-1,now.c));
                    }
                }
            }
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            house = new char[N][N];

            for(int i = 0; i < N; i++) {
                //house[i] = br.readLine().toCharArray();
                String[] input = br.readLine().split("");
                for(int j = 0; j < N; j++) {
                    char ch = input[j].charAt(0);

                    if(ch == '#') {
                        if(door_s == null) door_s = new Pos(i,j,-1,0);
                        else door_e = new Pos(i,j,-1,0);
                    }

                    house[i][j] = ch;
                }
            }

            ans = Integer.MAX_VALUE;
        }
    }
}
