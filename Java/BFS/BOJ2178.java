package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
미로 탐색

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	192 MB	138536	59682	38370	41.790%
문제
N×M크기의 배열로 표현되는 미로가 있다.

1	0	1	1	1	1
1	0	1	0	1	0
1	0	1	0	1	1
1	1	1	0	1	1
미로에서 1은 이동할 수 있는 칸을 나타내고, 0은 이동할 수 없는 칸을 나타낸다. 이러한 미로가 주어졌을 때, (1, 1)에서 출발하여 (N, M)의 위치로 이동할 때 지나야 하는 최소의 칸 수를 구하는 프로그램을 작성하시오. 한 칸에서 다른 칸으로 이동할 때, 서로 인접한 칸으로만 이동할 수 있다.

위의 예에서는 15칸을 지나야 (N, M)의 위치로 이동할 수 있다. 칸을 셀 때에는 시작 위치와 도착 위치도 포함한다.

입력
첫째 줄에 두 정수 N, M(2 ≤ N, M ≤ 100)이 주어진다. 다음 N개의 줄에는 M개의 정수로 미로가 주어진다. 각각의 수들은 붙어서 입력으로 주어진다.

출력
첫째 줄에 지나야 하는 최소의 칸 수를 출력한다. 항상 도착위치로 이동할 수 있는 경우만 입력으로 주어진다.

예제 입력 1
4 6
101111
101010
101011
111011
예제 출력 1
15
예제 입력 2
4 6
110110
110110
111111
111101
예제 출력 2
9
예제 입력 3
2 25
1011101110111011101110111
1110111011101110111011101
예제 출력 3
38
예제 입력 4
7 7
1011111
1110001
1000001
1000001
1000001
1000001
1111111
예제 출력 4
13
 */
public class BOJ2178 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N,M;
    static int[][] maze;
    static int[][] maze_dist;
    static boolean[][] visited;
    static int[][] d = {{-1,0},{1,0},{0,-1},{0,1}};

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        maze = new int[N][M];
        maze_dist = new int[N][M];
        visited = new boolean[N][M];

        for(int i=0;i<N;i++) {
            String[] str = br.readLine().split("");
            for(int j=0;j<M;j++) {
                maze[i][j] = maze_dist[i][j] = Integer.parseInt(str[j]);
                visited[i][j] = false;
            }
        }

        System.out.println(bfs(0,0));
    }

    static int bfs(int x,int y) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(x * M + y);
        visited[x][y] = true;

        while(!q.isEmpty()) {
            int n = q.poll();
            for(int i=0;i<4;i++) {
                int nx = (n / M) + d[i][0];
                int ny = (n % M) + d[i][1];
                if(nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                if(visited[nx][ny] || maze[nx][ny] == 0) continue;
                visited[nx][ny] = true;
                q.offer(nx * M + ny);
                maze_dist[nx][ny] += maze_dist[n / M][n % M];
            }
        }

        return maze_dist[N-1][M-1];
    }
}
