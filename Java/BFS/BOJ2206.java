package BFS;

/*
벽 부수고 이동하기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	192 MB	139122	36435	22798	23.310%
문제
N×M의 행렬로 표현되는 맵이 있다. 맵에서 0은 이동할 수 있는 곳을 나타내고, 1은 이동할 수 없는 벽이 있는 곳을 나타낸다. 당신은 (1, 1)에서 (N, M)의 위치까지 이동하려 하는데, 이때 최단 경로로 이동하려 한다. 최단경로는 맵에서 가장 적은 개수의 칸을 지나는 경로를 말하는데, 이때 시작하는 칸과 끝나는 칸도 포함해서 센다.

만약에 이동하는 도중에 한 개의 벽을 부수고 이동하는 것이 좀 더 경로가 짧아진다면, 벽을 한 개 까지 부수고 이동하여도 된다.

한 칸에서 이동할 수 있는 칸은 상하좌우로 인접한 칸이다.

맵이 주어졌을 때, 최단 경로를 구해 내는 프로그램을 작성하시오.

입력
첫째 줄에 N(1 ≤ N ≤ 1,000), M(1 ≤ M ≤ 1,000)이 주어진다. 다음 N개의 줄에 M개의 숫자로 맵이 주어진다. (1, 1)과 (N, M)은 항상 0이라고 가정하자.

출력
첫째 줄에 최단 거리를 출력한다. 불가능할 때는 -1을 출력한다.

예제 입력 1
6 4
0100
1110
1000
0000
0111
0000
예제 출력 1
15
예제 입력 2
4 4
0111
1111
1111
1110
예제 출력 2
-1
출처
데이터를 추가한 사람: djm03178, jh05013, Picasso, sait2000, YunGoon
문제의 오타를 찾은 사람: ntopia
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
벽을 부순 여부를 BOJ2206_map 객체 내부에 저장하고 사용하면 이동할 때의 map 객체의 정보가 달라지는 문제가 발생한다.
즉, queue에 저장된 벽을 부순지 아닌지 상태 값이 유효한 값인지 보장할 수 없기 때문에 새로운 map 객체를 생성하여 이동거리와 벽을 부순지 여부
상태를 보장하는 객체를 queue에 전달하는 것이 중요하다.
또한, 모든 위치에서 벽(1)인 상태를 0으로 만들고 bfs를 돌리는 경우 메모리초과가 발생하고 불필요한 작업이다.
이는, 벽을 부수고 해당 위치에 도달했는지 벽을 부수지 않고 해당 위치에 도달했는지 여부를 3차원 배열로 visited[][][2]로 설정하여 한번의
BFS를 통해 해결이 가능하다.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public class BOJ2206 {
    static class BOJ2206_map {
        int r,c;
        int wall;
        int broken_wall;
        int move;

        BOJ2206_map(int r, int c, int move, int bw) {
            this.r = r;
            this.c = c;
            this.move = move;
            this.broken_wall = bw;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static boolean[][][] visited;
    static int N,M;
    static BOJ2206_map[][] maps;
    static int[][] direction = {{-1,0}, {0,1}, {1,0},{0,-1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        System.out.println(bfs());
    }

    static int bfs() {
        Queue<BOJ2206_map> q = new LinkedList<>();

        visited[1][1][0] = true;
        maps[1][1].move = 1;
        q.add(maps[1][1]);

        while(!q.isEmpty()) {
           BOJ2206_map now = q.poll();

           if(now.r == N && now.c == M) {
               return now.move;
           }

           for(int[] d : direction) {
               int nr = now.r + d[0];
               int nc = now.c + d[1];

               if(nr < 1 || nr > N || nc < 1 || nc > M) continue;

               //다음 이동 시, 이미 벽을 부순 상태라면 -> broken_wall = 1
               if(now.broken_wall == 1) {
                   //벽을 부순 상태에서 nr, nc에 도달한 적이 있거나, 이동한 곳이 벽인 경우
                   if(visited[nr][nc][1] || maps[nr][nc].wall == 1) continue;

                   //벽을 부순 상태 -> visited[][][1] 를 true로 설정
                   visited[nr][nc][1] = true;
                   //now의 이동한 거리 + 1, 벽을 부순 상태 값을 이어서 설정한 객체로 queue에 저장한다.
                   q.add(new BOJ2206_map(nr,nc,now.move + 1,1));
               } else {
                   //벽을 부순 상태가 아닌 경우, 이미 방문한 위치라면 continue;
                   if(visited[nr][nc][0]) continue;

                   //이동한 위치가 벽이면 벽을 부술지 결정한다.
                   int broke_status = maps[nr][nc].wall == 1 ? 1 : 0;
                   visited[nr][nc][broke_status] = true;
                   //벽을 부순 경우와 아닌 경우의 새로운 BOJ2206_map 객체를 생성하여 queue에 저장한다.
                   q.add(new BOJ2206_map(nr,nc,now.move + 1, broke_status));
               }

               /*
               //queue에 map에 저장된 객체를 전달하고 전달한 객체의 이동 거리와 벽을 부순 여부 정보를 저장한 값을 사용하는 경우
               //queue에서 poll된 값이 당시 queue되기 전의 값과 같다는 것을 보장할 수 없다.
               //따라서, 새로운 객체로 이동거리 정보와 벽을 부순상태 이후의 이동 정보인지 아닌지의 정보를 queue에 넣어야한다.
               if(now.broken_wall == 1) {
                   if(visited[nr][nc][1] || maps[nr][nc].wall == 1) continue;

                   visited[nr][nc][1] = true;
                   maps[nr][nc].move = now.move + 1;
                   maps[nr][nc].broken_wall = 1;
                   q.add(maps[nr][nc]);
               } else {
                   if(visited[nr][nc][0]) continue;

                   maps[nr][nc].move = now.move + 1;
                   maps[nr][nc].broken_wall = maps[nr][nc].wall == 1 ? 1 : 0;
                   visited[nr][nc][maps[nr][nc].broken_wall] = true;
                   q.add(maps[nr][nc]);
               }*/
           }
        }

        return -1;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        visited = new boolean[N+1][M+1][2];
        maps = new BOJ2206_map[N+1][M+1];

        IntStream.range(1,N+1)
                .forEach(r -> IntStream.range(1,M+1)
                        .forEach(c -> {
                            maps[r][c] = new BOJ2206_map(r,c,0,0);
                        }));


        IntStream.range(1,N+1)
                .forEach(r -> IntStream.range(1,M+1)
                        .forEach(c -> IntStream.range(0,2)
                                .forEach(i -> {
                                    visited[r][c][i] = false;
                                })));

        IntStream.range(1,N+1)
                .forEach(r -> {
                    try {
                        String[] line = br.readLine().split("");
                        IntStream.range(1,line.length+1)
                                .forEach(c -> {
                                    if(line[c-1].equals("1")) {
                                        maps[r][c].wall = 1;
                                    } else {
                                        maps[r][c].wall = 0;
                                    }
                                });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}


// 메모리 초과 - visited 배열의 크기가 (1000 * 1000) * 1001 * 1001 + bfs의 반복이 메모리초과의 결과가 발생된 것으로 생각하였다.
class BOJ2206_memoryOver {
    static class BOJ2206_map {
        int r,c;
        int min_move;
        int wall;

        BOJ2206_map(int r, int c) {
            this.r = r;
            this.c = c;
            min_move = Integer.MAX_VALUE;
        }

        void setWall(int w) {
            this.wall = w;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static boolean[][][] visited;
    static BOJ2206_map[][] maps;
    static int N,M;
    static int[][] direction = {{-1,0}, {0,1}, {1,0},{0,-1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int r=1;r<=N;r++) {
            for(int c=1;c<=M;c++) {
                int broke_wall = M * (r-1) + (c-1);
                if(maps[r][c].wall == 1) {
                    visited[broke_wall][r][c] = false;
                    maps[r][c].setWall(0);

                    bfs(broke_wall);

                    visited[broke_wall][r][c] = true;
                    maps[r][c].setWall(1);
                } else {
                    bfs(broke_wall);
                }
            }
        }

        System.out.println(maps[N][M].min_move == Integer.MAX_VALUE ? -1 : maps[N][M].min_move);
    }

    static void bfs(int bw) {
        Queue<BOJ2206_map> q = new LinkedList<>();

        q.add(maps[1][1]);
        maps[1][1].min_move = 1;
        visited[bw][1][1] = true;

        while(!q.isEmpty()) {
            BOJ2206_map pos = q.poll();

            for(int[] d : direction) {
                int nr = pos.r + d[0];
                int nc = pos.c + d[1];

                if(nr < 1 || nr > N || nc < 1 || nc > M) continue;
                if(maps[nr][nc].wall == 1 || visited[bw][nr][nc]) continue;

                maps[nr][nc].min_move = pos.min_move + 1;
                visited[bw][nr][nc] = true;
                q.add(maps[nr][nc]);
                /*if(visited[bw][nr][nc]) {
                    if(maps[nr][nc].min_move >= pos.min_move + 1) {
                        maps[nr][nc].min_moxve = Math.min(maps[nr][nc].min_move, pos.min_move + 1);
                        q.add(maps[nr][nc]);
                    }
                } else {
                    visited[bw][nr][nc] = true;
                    maps[nr][nc].min_move = Math.min(maps[nr][nc].min_move, pos.min_move + 1);
                    q.add(maps[nr][nc]);
                }*/

                //메모리 초과 - visited 제거 코드
                /*if(maps[nr][nc].min_move >= pos.min_move + 1) {
                    maps[nr][nc].min_move = Math.min(maps[nr][nc].min_move, pos.min_move + 1);
                    q.add(maps[nr][nc]);
                }*/
            }
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        visited = new boolean[N*M][N+1][M+1];
        maps = new BOJ2206_map[N+1][M+1];

        IntStream.range(1,N+1)
                .forEach(r -> IntStream.range(1,M+1)
                        .forEach(c -> {
                            maps[r][c] = new BOJ2206_map(r,c);
                        }));

        IntStream.range(0,N*M)
                        .forEach(i -> {
                            IntStream.range(1,N+1)
                                    .forEach(r -> IntStream.range(1,M+1)
                                            .forEach(c -> {
                                                visited[i][r][c] = false;
                                            }));
                        });

        IntStream.range(1,N+1)
                .forEach(r -> {
                    try {
                        String[] line = br.readLine().split("");
                        IntStream.range(1,line.length+1)
                                .forEach(c -> {
                                    if(line[c-1].equals("1")) {
                                        maps[r][c].setWall(1);
                                        IntStream.range(0,N*M)
                                                .forEach(i -> visited[i][r][c] = true);
                                    } else {
                                        maps[r][c].setWall(0);
                                    }
                                });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
