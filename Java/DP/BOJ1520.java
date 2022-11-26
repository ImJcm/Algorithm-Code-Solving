import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
내리막 길

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	62435	17170	12226	27.762%
문제
여행을 떠난 세준이는 지도를 하나 구하였다. 이 지도는 아래 그림과 같이 직사각형 모양이며 여러 칸으로 나뉘어져 있다. 한 칸은 한 지점을 나타내는데 각 칸에는 그 지점의 높이가 쓰여 있으며, 각 지점 사이의 이동은 지도에서 상하좌우 이웃한 곳끼리만 가능하다.



현재 제일 왼쪽 위 칸이 나타내는 지점에 있는 세준이는 제일 오른쪽 아래 칸이 나타내는 지점으로 가려고 한다. 그런데 가능한 힘을 적게 들이고 싶어 항상 높이가 더 낮은 지점으로만 이동하여 목표 지점까지 가고자 한다. 위와 같은 지도에서는 다음과 같은 세 가지 경로가 가능하다.



지도가 주어질 때 이와 같이 제일 왼쪽 위 지점에서 출발하여 제일 오른쪽 아래 지점까지 항상 내리막길로만 이동하는 경로의 개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에는 지도의 세로의 크기 M과 가로의 크기 N이 빈칸을 사이에 두고 주어진다. 이어 다음 M개 줄에 걸쳐 한 줄에 N개씩 위에서부터 차례로 각 지점의 높이가 빈 칸을 사이에 두고 주어진다. M과 N은 각각 500이하의 자연수이고, 각 지점의 높이는 10000이하의 자연수이다.

출력
첫째 줄에 이동 가능한 경로의 수 H를 출력한다. 모든 입력에 대하여 H는 10억 이하의 음이 아닌 정수이다.

예제 입력 1
4 5
50 45 37 32 30
35 50 40 20 25
30 30 25 17 28
27 24 22 15 10
예제 출력 1
3
 */
/*
    DP + Graph + DFS
 */
public class BOJ1520 {
    static int N,M;
    static boolean[][] visited;
    static int[][] map;
    static int[][] route;
    static int[][] d = {{-1,0}, {1,0}, {0,-1}, {0,1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N+1][M+1];
        route = new int[N+1][M+1];
        visited = new boolean[N+1][M+1];

        for(int i=1;i<=N;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=1;j<=M;j++) {
                //DFS, DP_bt사용 시, route의 값들을 0으로 초기화
                //route[i][j] = 0;

                //DP_topdown 사용시, route의 초기 값을 -1로 초기화 = 해당 idx의 값으로 0일 수 있음을 고려
                route[i][j] = -1;
                visited[i][j] = false;
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //visited[1][1] = true;
        //DFS(1,1);
        System.out.println(DP(1,1));
        //System.out.println(dp_bt(N,M));
    }

    //시간초과
    static void DFS(int x,int y) {
        if(x == N && y == M) {
             route[x][y]++;
             return;
        }

        for(int i=0;i<4;i++) {
            int nx = x + d[i][0];
            int ny = y + d[i][1];

            if(nx < 1 || ny < 1 || nx > N || ny > M) continue;
            if(!visited[nx][ny] && map[x][y] > map[nx][ny]) {
                visited[nx][ny] = true;
                DFS(nx,ny);
                visited[nx][ny] = false;
            }
        }
    }

    //Top-down
    static int DP(int x,int y) {
        if(x == N && y == M) {
            return 1;
        }

        if(route[x][y] != -1) return route[x][y];
        //route배열의 초기값 = -1, 이미 업데이트된 값이 있다면 해당 값 반환
        //없다면, route[x][y]값을 0으로 변경 후, 재귀함수 호출
        route[x][y] = 0;

        for(int i=0;i<4;i++) {
            int nx = x + d[i][0];
            int ny = y + d[i][1];

            if(nx < 1 || ny < 1 || nx > N || ny > M) continue;
            if(map[x][y] > map[nx][ny]) {
                route[x][y] += DP(nx,ny);
            }
        }
        return route[x][y];
    }

    static int dp_bt(int x,int y) {
        route[1][1] = 1;
        for(int i=1;i<=x;i++) {
            for(int j=1;j<=y;j++) {
                for(int k=0;k<4;k++) {
                    int nx = i + d[k][0];
                    int ny = j + d[k][1];

                    if(nx < 1 || ny < 1 || nx < N || ny < M) continue;
                    if(map[i][j] > map[nx][ny]) {
                        route[nx][ny] += route[i][j];
                    }
                }
            }
        }
        return route[x][y];
    }
}
