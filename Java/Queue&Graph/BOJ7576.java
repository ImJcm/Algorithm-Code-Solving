import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
토마토

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	136251	50796	32054	35.234%
문제
철수의 토마토 농장에서는 토마토를 보관하는 큰 창고를 가지고 있다. 토마토는 아래의 그림과 같이 격자 모양 상자의 칸에 하나씩 넣어서 창고에 보관한다.

창고에 보관되는 토마토들 중에는 잘 익은 것도 있지만, 아직 익지 않은 토마토들도 있을 수 있다. 보관 후 하루가 지나면, 익은 토마토들의 인접한 곳에 있는 익지 않은 토마토들은 익은 토마토의 영향을 받아 익게 된다. 하나의 토마토의 인접한 곳은 왼쪽, 오른쪽, 앞, 뒤 네 방향에 있는 토마토를 의미한다. 대각선 방향에 있는 토마토들에게는 영향을 주지 못하며, 토마토가 혼자 저절로 익는 경우는 없다고 가정한다. 철수는 창고에 보관된 토마토들이 며칠이 지나면 다 익게 되는지, 그 최소 일수를 알고 싶어 한다.

토마토를 창고에 보관하는 격자모양의 상자들의 크기와 익은 토마토들과 익지 않은 토마토들의 정보가 주어졌을 때, 며칠이 지나면 토마토들이 모두 익는지, 그 최소 일수를 구하는 프로그램을 작성하라. 단, 상자의 일부 칸에는 토마토가 들어있지 않을 수도 있다.

입력
첫 줄에는 상자의 크기를 나타내는 두 정수 M,N이 주어진다. M은 상자의 가로 칸의 수, N은 상자의 세로 칸의 수를 나타낸다. 단, 2 ≤ M,N ≤ 1,000 이다. 둘째 줄부터는 하나의 상자에 저장된 토마토들의 정보가 주어진다. 즉, 둘째 줄부터 N개의 줄에는 상자에 담긴 토마토의 정보가 주어진다. 하나의 줄에는 상자 가로줄에 들어있는 토마토의 상태가 M개의 정수로 주어진다. 정수 1은 익은 토마토, 정수 0은 익지 않은 토마토, 정수 -1은 토마토가 들어있지 않은 칸을 나타낸다.

토마토가 하나 이상 있는 경우만 입력으로 주어진다.

출력
여러분은 토마토가 모두 익을 때까지의 최소 날짜를 출력해야 한다. 만약, 저장될 때부터 모든 토마토가 익어있는 상태이면 0을 출력해야 하고, 토마토가 모두 익지는 못하는 상황이면 -1을 출력해야 한다.

예제 입력 1
6 4
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 1
예제 출력 1
8
예제 입력 2
6 4
0 -1 0 0 0 0
-1 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 1
예제 출력 2
-1
예제 입력 3
6 4
1 -1 0 0 0 0
0 -1 0 0 0 0
0 0 0 0 -1 0
0 0 0 0 -1 1
예제 출력 3
6
예제 입력 4
5 5
-1 1 0 0 0
0 -1 -1 -1 0
0 -1 -1 -1 0
0 -1 -1 -1 0
0 0 0 0 0
예제 출력 4
14
예제 입력 5
2 2
1 -1
-1 1
예제 출력 5
0
 */
public class BOJ7576 {
    static int N,M;
    static int[][] tomato;
    static int[][] d = {{-1,0}, {1,0}, {0,-1},{0,1}};
    static Queue<Integer> ripe_tomatos;
    static boolean[][] visited;
    static int check = 0;
    static int min_day = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        tomato = new int[M][N];
        ripe_tomatos = new LinkedList<>();
        visited = new boolean[M][N];

        for(int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;j++) {
                tomato[i][j] = Integer.parseInt(st.nextToken());
                visited[i][j] = false;
                if(tomato[i][j] == 1) {
                    ripe_tomatos.offer(i*N + j);
                    visited[i][j] = true;
                }
                if(tomato[i][j] == -1) visited[i][j] = true;
            }
        }

        BFS(ripe_tomatos);
        for(int i=0;i<M;i++) {
            for(int j=0;j<N;j++) {
                if(visited[i][j] == false) {
                    check = -1;
                    break;
                }
            }
        }
        System.out.println(check == -1 ? "-1":min_day);
    }

    /*
        성공 - 메모리 : 139480KB / 시간 : 848ms
     */
    static void BFS(Queue<Integer> r_t) {
        Queue<Queue<Integer>> ql = new LinkedList<>();
        ql.offer(r_t);

        while(!ql.isEmpty()) {
            Queue<Integer> q = ql.poll();
            Queue<Integer> tmp_q = new LinkedList<>();

            for(int n : q) {
                for(int i=0;i<4;i++) {
                    int nx = n / N + d[i][0];
                    int ny = n % N + d[i][1];
                    if(nx < 0 || ny < 0 || nx >= M || ny >= N ||
                            visited[nx][ny] || Math.abs(tomato[nx][ny]) == 1) continue;
                    if(tomato[nx][ny] == 0 && !visited[nx][ny]) {
                        tmp_q.offer(nx*N + ny);
                        tomato[nx][ny] = 1;
                        visited[nx][ny] = true;
                    }
                }
            }
            if(tmp_q.isEmpty()) continue;
            min_day++;
            ql.offer(tmp_q);
        }
    }

    /*
        성공 - 메모리 : 112448kb / 시간 : 776ms
     */
    static int BFS_a() {
        Queue<Integer> q = ripe_tomatos;

        while(!q.isEmpty()) {
            int n = q.poll();
            int x = n / N;
            int y = n % N;

            for(int i=0;i<4;i++) {
                int nx = n / N + d[i][0];
                int ny = n % N + d[i][1];
                if(nx < 0 || ny < 0 || nx >= M || ny >= N || Math.abs(tomato[nx][ny]) >= 1) continue;
                if(tomato[nx][ny] == 0) {
                    q.offer(nx*N + ny);
                    tomato[nx][ny] = tomato[x][y] + 1;
                }
            }
        }

        int result = Integer.MIN_VALUE;

        for(int i=0;i<M;i++) {
            for(int j=0;j<N;j++) {
                if(tomato[i][j] == 0) {
                    return -1;
                }
                result = Math.max(result,tomato[i][j]);
            }
        }

        if(result == 1) {
            return 0;
        } else {
            return result - 1;
        }
    }
}
