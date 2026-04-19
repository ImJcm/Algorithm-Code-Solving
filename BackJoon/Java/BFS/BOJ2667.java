package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
단지번호붙이기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	132143	57188	36102	41.086%
문제
<그림 1>과 같이 정사각형 모양의 지도가 있다. 1은 집이 있는 곳을, 0은 집이 없는 곳을 나타낸다. 철수는 이 지도를 가지고 연결된 집의 모임인 단지를 정의하고, 단지에 번호를 붙이려 한다. 여기서 연결되었다는 것은 어떤 집이 좌우, 혹은 아래위로 다른 집이 있는 경우를 말한다. 대각선상에 집이 있는 경우는 연결된 것이 아니다. <그림 2>는 <그림 1>을 단지별로 번호를 붙인 것이다. 지도를 입력하여 단지수를 출력하고, 각 단지에 속하는 집의 수를 오름차순으로 정렬하여 출력하는 프로그램을 작성하시오.



입력
첫 번째 줄에는 지도의 크기 N(정사각형이므로 가로와 세로의 크기는 같으며 5≤N≤25)이 입력되고, 그 다음 N줄에는 각각 N개의 자료(0혹은 1)가 입력된다.

출력
첫 번째 줄에는 총 단지수를 출력하시오. 그리고 각 단지내 집의 수를 오름차순으로 정렬하여 한 줄에 하나씩 출력하시오.

예제 입력 1
7
0110100
0110101
1110101
0000111
0100000
0111110
0111000
예제 출력 1
3
7
8
9
 */
public class BOJ2667 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int N;
    static int[][] node;
    static boolean[][] visited;
    static boolean checkhouses = false;
    static int[][] houses;
    static int count = 0;
    static int house_kind = 0;
    static int[][] d = {{-1,0},{0,-1},{1,0},{0,1}};

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        node = new int[N+1][N+1];
        visited = new boolean[N+1][N+1];
        houses = new int[(N+1)*(N+1)][1];
        for(int i=1;i<=N;i++) {
            String[] str = br.readLine().split("");
            //node[i][0] = node[0][i] = node[i][N+1] = node[N+1][i] = 0;
            //visited[i][0] = visited[0][i] = visited[i][N+1] = visited[N+1][i] = false;
            for(int j=1;j<=N;j++) {
                node[i][j] = Integer.parseInt(str[j-1]);
                visited[i][j] = false;
                houses[i*j][0] = 0;
            }
        }

        for(int i=1;i<=N;i++) {
            for(int j=1;j<=N;j++) {
                if(node[i][j] == 0 || visited[i][j]) continue;
                visited[i][j] = true;
                count = 1;

                //dfs(i,j);
                bfs(i,j);

                house_kind += 1;
                houses[count][0] += 1;
                count = 0;
            }
        }

        System.out.println(house_kind);
        for(int i=1;i<houses.length;i++) {
            if(houses[i][0] == 0) continue;
            for(int j=houses[i][0];j>0;j--) {
                System.out.println(i);
            }
        }
    }

    static void dfs(int x,int y) {

        for(int i=0;i<4;i++) {
            int nx = x + d[i][0];
            int ny = y + d[i][1];
            if(nx < 1 || ny < 1 || nx > N || ny > N) continue;
            if(visited[nx][ny] || node[nx][ny] == 0) continue;
            visited[nx][ny] = true;
            count++;
            dfs(nx,ny);
        }
    }

    static void bfs(int x,int y) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(x*(N+1) + y);

        while(!q.isEmpty()) {
            int n = q.poll();

            for(int i=0;i<4;i++) {
                int nx = n / (N+1) + d[i][0];
                int ny = n % (N+1) + d[i][1];
                if(nx < 1 || ny < 1 || nx > N || ny > N) continue;
                if(visited[nx][ny] || node[nx][ny] == 0) continue;
                visited[nx][ny] = true;
                q.offer(nx * (N+1) + ny);
                count++;
            }
        }

    }
}
