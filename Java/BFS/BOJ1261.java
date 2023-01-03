import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
알고스팟

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초 (추가 시간 없음)	128 MB	31503	13416	9106	41.748%
문제
알고스팟 운영진이 모두 미로에 갇혔다. 미로는 N*M 크기이며, 총 1*1크기의 방으로 이루어져 있다. 미로는 빈 방 또는 벽으로 이루어져 있고, 빈 방은 자유롭게 다닐 수 있지만, 벽은 부수지 않으면 이동할 수 없다.

알고스팟 운영진은 여러명이지만, 항상 모두 같은 방에 있어야 한다. 즉, 여러 명이 다른 방에 있을 수는 없다. 어떤 방에서 이동할 수 있는 방은 상하좌우로 인접한 빈 방이다. 즉, 현재 운영진이 (x, y)에 있을 때, 이동할 수 있는 방은 (x+1, y), (x, y+1), (x-1, y), (x, y-1) 이다. 단, 미로의 밖으로 이동 할 수는 없다.

벽은 평소에는 이동할 수 없지만, 알고스팟의 무기 AOJ를 이용해 벽을 부수어 버릴 수 있다. 벽을 부수면, 빈 방과 동일한 방으로 변한다.

만약 이 문제가 알고스팟에 있다면, 운영진들은 궁극의 무기 sudo를 이용해 벽을 한 번에 다 없애버릴 수 있지만, 안타깝게도 이 문제는 Baekjoon Online Judge에 수록되어 있기 때문에, sudo를 사용할 수 없다.

현재 (1, 1)에 있는 알고스팟 운영진이 (N, M)으로 이동하려면 벽을 최소 몇 개 부수어야 하는지 구하는 프로그램을 작성하시오.

입력
첫째 줄에 미로의 크기를 나타내는 가로 크기 M, 세로 크기 N (1 ≤ N, M ≤ 100)이 주어진다. 다음 N개의 줄에는 미로의 상태를 나타내는 숫자 0과 1이 주어진다. 0은 빈 방을 의미하고, 1은 벽을 의미한다.

(1, 1)과 (N, M)은 항상 뚫려있다.

출력
첫째 줄에 알고스팟 운영진이 (N, M)으로 이동하기 위해 벽을 최소 몇 개 부수어야 하는지 출력한다.

예제 입력 1
3 3
011
111
110
예제 출력 1
3
예제 입력 2
4 2
0001
1000
예제 출력 2
0
예제 입력 3
6 6
001111
010000
001111
110001
011010
100010
예제 출력 3
2
 */
public class BOJ1261 {
    static int N,M;     // 1<= N,M <= 100
    static int[][] map;
    static int[][] crash_wall;
    static boolean[][] visited;
    static int[][] d = {{-1,0},{1,0},{0,-1},{0,1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[M+1][N+1];
        crash_wall = new int[M+1][N+1];
        visited = new boolean[M+1][N+1];

        for(int i=1;i<=M;i++) {
            String[] str = br.readLine().split("");
            Arrays.fill(crash_wall[i],0);
            Arrays.fill(visited[i],false);
            for(int j=1;j<=N;j++) {
                map[i][j] = Integer.parseInt(str[j-1]);
            }
        }

        one_zero_BFS();
    }

    static void one_zero_BFS() {
        Deque<Path> dq = new LinkedList<>();
        dq.addFirst(new Path(1,1));
        visited[1][1] = true;

        while(!dq.isEmpty()) {
            Path now = dq.pollFirst();  //앞의 object부터 꺼내오기

            if(now.x == M && now.y == N) {
                System.out.println(crash_wall[M][N]);
                return;
            }

            for(int i=0;i<4;i++) {
                int nx = d[i][0] + now.x;
                int ny = d[i][1] + now.y;

                if(1 <= nx && nx <= M && 1 <= ny && ny <= N && !visited[nx][ny]) {
                    if(map[nx][ny]==0) {
                        dq.addFirst(new Path(nx,ny));
                        crash_wall[nx][ny] = crash_wall[now.x][now.y];
                    } else {
                        // == 1
                        dq.addLast(new Path(nx,ny));
                        crash_wall[nx][ny] = crash_wall[now.x][now.y] + 1;
                    }
                    visited[nx][ny] = true;
                }

            }
        }
    }

    static class Path {
        int x;
        int y;

        Path(int x,int y) {
            this.x = x;
            this.y = y;
        }
    }
}
