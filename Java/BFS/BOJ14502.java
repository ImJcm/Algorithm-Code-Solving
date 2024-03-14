package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
연구소

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	97435	56249	31415	54.951%
문제
인체에 치명적인 바이러스를 연구하던 연구소에서 바이러스가 유출되었다. 다행히 바이러스는 아직 퍼지지 않았고, 바이러스의 확산을 막기 위해서 연구소에 벽을 세우려고 한다.

연구소는 크기가 N×M인 직사각형으로 나타낼 수 있으며, 직사각형은 1×1 크기의 정사각형으로 나누어져 있다. 연구소는 빈 칸, 벽으로 이루어져 있으며, 벽은 칸 하나를 가득 차지한다.

일부 칸은 바이러스가 존재하며, 이 바이러스는 상하좌우로 인접한 빈 칸으로 모두 퍼져나갈 수 있다. 새로 세울 수 있는 벽의 개수는 3개이며, 꼭 3개를 세워야 한다.

예를 들어, 아래와 같이 연구소가 생긴 경우를 살펴보자.

2 0 0 0 1 1 0
0 0 1 0 1 2 0
0 1 1 0 1 0 0
0 1 0 0 0 0 0
0 0 0 0 0 1 1
0 1 0 0 0 0 0
0 1 0 0 0 0 0
이때, 0은 빈 칸, 1은 벽, 2는 바이러스가 있는 곳이다. 아무런 벽을 세우지 않는다면, 바이러스는 모든 빈 칸으로 퍼져나갈 수 있다.

2행 1열, 1행 2열, 4행 6열에 벽을 세운다면 지도의 모양은 아래와 같아지게 된다.

2 1 0 0 1 1 0
1 0 1 0 1 2 0
0 1 1 0 1 0 0
0 1 0 0 0 1 0
0 0 0 0 0 1 1
0 1 0 0 0 0 0
0 1 0 0 0 0 0
바이러스가 퍼진 뒤의 모습은 아래와 같아진다.

2 1 0 0 1 1 2
1 0 1 0 1 2 2
0 1 1 0 1 2 2
0 1 0 0 0 1 2
0 0 0 0 0 1 1
0 1 0 0 0 0 0
0 1 0 0 0 0 0
벽을 3개 세운 뒤, 바이러스가 퍼질 수 없는 곳을 안전 영역이라고 한다. 위의 지도에서 안전 영역의 크기는 27이다.

연구소의 지도가 주어졌을 때 얻을 수 있는 안전 영역 크기의 최댓값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 지도의 세로 크기 N과 가로 크기 M이 주어진다. (3 ≤ N, M ≤ 8)

둘째 줄부터 N개의 줄에 지도의 모양이 주어진다. 0은 빈 칸, 1은 벽, 2는 바이러스가 있는 위치이다. 2의 개수는 2보다 크거나 같고, 10보다 작거나 같은 자연수이다.

빈 칸의 개수는 3개 이상이다.

출력
첫째 줄에 얻을 수 있는 안전 영역의 최대 크기를 출력한다.

예제 입력 1
7 7
2 0 0 0 1 1 0
0 0 1 0 1 2 0
0 1 1 0 1 0 0
0 1 0 0 0 0 0
0 0 0 0 0 1 1
0 1 0 0 0 0 0
0 1 0 0 0 0 0
예제 출력 1
27
예제 입력 2
4 6
0 0 0 0 0 0
1 0 0 0 0 2
1 1 1 0 0 2
0 0 0 0 0 2
예제 출력 2
9
예제 입력 3
8 8
2 0 0 0 0 0 0 2
2 0 0 0 0 0 0 2
2 0 0 0 0 0 0 2
2 0 0 0 0 0 0 2
2 0 0 0 0 0 0 2
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
예제 출력 3
3
출처
문제를 만든 사람: baekjoon
빠진 조건을 찾은 사람: bupjae, dotorya
데이터를 추가한 사람: raboribus
알고리즘 분류
구현
그래프 이론
브루트포스 알고리즘
그래프 탐색
너비 우선 탐색
 */
/*
init_setting()에서 빈 공간(0), 벽(1), 바이러스(2) 정보를 map에 저장한 후, 바이러스가 존재하는 구역은 virus 배열에 저장해 준다.

벽1,벽2,벽3을 브루트포스로 놓을 수 있는 곳을 선정한 후, bfs를 수행하여 안전구역의 개수가 최대를 갖는 것으로 업데이트한다.

bfs마다 3개의 벽을 놓을 수 있는 경우의 map 상황을 알기 위해 visited를 [b1][b2][b3][r][c]의 5차원 배열로 b1,b2,b3에 벽이 놓였을 때
기존에 존재한 벽과 바이러스에 해당하는 위치에 방문 여부와 b1,b2,b3에 방문여부를 체크한다.

이때, 세 개의 벽을 놓을 수 있는 위치에서 bfs를 수행할 여부를 possible 배열에 저장하여 불필요한 연산을 줄이도록 하였다.

세 개의 벽을 생성하는 상태를 표현하는 것은 visited 배열을 통해 설정하였다.

 */
public class BOJ14502 {
    public static class BOJ14502_section {
        int row, col;
        int state;

        public BOJ14502_section(int r, int c, int s) {
            this.row = r;
            this.col = c;
            this.state = s;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static boolean[][][][][] visited;
    static boolean[][][] possible;
    static BOJ14502_section[] virus;
    static BOJ14502_section[][] map;
    static int N,M,max_safe_sections = 0, virus_cnt = 0;
    static int[][] direction = {{-1,0}, {0,1}, {1,0}, {0,-1}};


    public static void main(String[] args) throws IOException {
        init_setting();

        solve();

        safe_section_check();
    }

    /*
    3번 블럭은 2번 블록 이후의 블록부터 시작하고, 2번 블록은 1번 블록 이후의 블록부터 시작하는 형태로 브루트포스를 설정한다.
     */
    static void solve() {
        for(int i=0;i<N*M;i++) {
            for(int j=i+1;j<N*M;j++) {
                for(int k=j+1;k<N*M;k++) {
                    if(possible[i][j][k]) {
                        bfs(i,j,k);
                    }
                }
            }
        }
    }

    static void bfs(int b1, int b2, int b3) {
        Queue<BOJ14502_section> q = new LinkedList<>();

        for(int i=0;i<virus_cnt;i++) {
            q.offer(virus[i]);
        }

        //존재하는 virus의 visited 정보는 init_setting에서 수행함.

        while(!q.isEmpty()) {
            BOJ14502_section now_pos = q.poll();

            for(int[] d : direction) {
                if(now_pos.row + d[0] < 0 || now_pos.row + d[0] >= N || now_pos.col + d[1] < 0 || now_pos.col + d[1] >= M) {
                    continue;
                }
                if(visited[b1][b2][b3][now_pos.row + d[0]][now_pos.col + d[1]]) {
                    continue;
                }

                visited[b1][b2][b3][now_pos.row + d[0]][now_pos.col + d[1]] = true;
                q.offer(map[now_pos.row + d[0]][now_pos.col + d[1]]);
            }
        }
    }

    static void safe_section_check() {
        for(int i=0;i<N*M;i++) {
            for(int j=i+1;j<N*M;j++) {
                for(int k=j+1;k<N*M;k++) {
                    if(possible[i][j][k]) {
                        int cnt = 0;
                        for(int r=0;r<N;r++) {
                            for(int c=0;c<M;c++) {
                                if(!visited[i][j][k][r][c]) {
                                    cnt++;
                                }
                            }
                        }
                        max_safe_sections = Math.max(max_safe_sections, cnt);
                    }
                }
            }
        }

        System.out.println(max_safe_sections);
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        visited = new boolean[N*M][N*M][N*M][N][M];
        possible = new boolean[N*M][N*M][N*M];
        virus = new BOJ14502_section[10];
        map = new BOJ14502_section[N][M];

        for(int i=0;i<N;i++) {
            input = br.readLine().split(" ");
            for(int j=0;j<M;j++) {
                switch (input[j]) {
                    case "0":
                        map[i][j] = new BOJ14502_section(i,j,0);
                        break;
                    case "1":
                        map[i][j] = new BOJ14502_section(i,j,1);
                        break;
                    case "2":
                        map[i][j] = new BOJ14502_section(i,j,2);
                        virus[virus_cnt++] = new BOJ14502_section(i,j,2);
                        break;
                }
            }
        }

        for(int i=0;i<N*M;i++) {
            if(map[i/M][i%M].state == 1 || map[i/M][i%M].state == 2) continue;
            for(int j=i+1;j<N*M;j++) {
                if(map[j/M][j%M].state == 1 || map[j/M][j%M].state == 2) continue;
                for(int k=j+1;k<N*M;k++) {
                    if(map[k/M][k%M].state == 1 || map[k/M][k%M].state == 2) continue;
                    for(int r=0;r<N;r++) {
                        for(int c=0;c<M;c++) {
                            if(map[r][c].state == 1 || map[r][c].state == 2) {
                                visited[i][j][k][r][c] = true;
                            } else {
                                visited[i][j][k][r][c] = false;
                            }
                        }
                    }
                    if(map[i/M][i%M].state == 0 && map[j/M][j%M].state == 0 && map[k/M][k%M].state == 0) {
                        visited[i][j][k][i/M][i%M] = true;
                        visited[i][j][k][j/M][j%M] = true;
                        visited[i][j][k][k/M][k%M] = true;
                        possible[i][j][k] = true;
                    } else {
                        possible[i][j][k] = false;
                    }

                }
            }
        }
    }
}
