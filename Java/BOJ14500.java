package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
테트로미노(Gold5)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	53711	20029	12993	35.458%
문제
폴리오미노란 크기가 1×1인 정사각형을 여러 개 이어서 붙인 도형이며, 다음과 같은 조건을 만족해야 한다.
정사각형은 서로 겹치면 안 된다.
도형은 모두 연결되어 있어야 한다.
정사각형의 변끼리 연결되어 있어야 한다. 즉, 꼭짓점과 꼭짓점만 맞닿아 있으면 안 된다.
정사각형 4개를 이어 붙인 폴리오미노는 테트로미노라고 하며, 다음과 같은 5가지가 있다.

아름이는 크기가 N×M인 종이 위에 테트로미노 하나를 놓으려고 한다. 종이는 1×1 크기의 칸으로 나누어져 있으며, 각각의 칸에는 정수가 하나 쓰여 있다.
테트로미노 하나를 적절히 놓아서 테트로미노가 놓인 칸에 쓰여 있는 수들의 합을 최대로 하는 프로그램을 작성하시오.
테트로미노는 반드시 한 정사각형이 정확히 하나의 칸을 포함하도록 놓아야 하며, 회전이나 대칭을 시켜도 된다.

입력
첫째 줄에 종이의 세로 크기 N과 가로 크기 M이 주어진다. (4 ≤ N, M ≤ 500)
둘째 줄부터 N개의 줄에 종이에 쓰여 있는 수가 주어진다. i번째 줄의 j번째 수는 위에서부터 i번째 칸, 왼쪽에서부터 j번째 칸에 쓰여 있는 수이다. 입력으로 주어지는 수는 1,000을 넘지 않는 자연수이다.

출력
첫째 줄에 테트로미노가 놓인 칸에 쓰인 수들의 합의 최댓값을 출력한다.

예제 입력 1
5 5
1 2 3 4 5
5 4 3 2 1
2 3 4 5 6
6 5 4 3 2
1 2 1 2 1
예제 출력 1
19

예제 입력 2
4 5
1 2 3 4 5
1 2 3 4 5
1 2 3 4 5
1 2 3 4 5
예제 출력 2
20

예제 입력 3
4 10
1 2 1 2 1 2 1 2 1 2
2 1 2 1 2 1 2 1 2 1
1 2 1 2 1 2 1 2 1 2
2 1 2 1 2 1 2 1 2 1
예제 출력 3
7
 */
//처음 문제를 봤을 때, DFS가 떠오르지 않았고,도형마다 회전, 대칭 모양별로 N,M까지 적용시켜 최댓값을 출력하는 식으로 생각했다.
//DFS(Depth-First Search)로 풀수 있다는 것을 알아서 DFS로 구현
public class BOJ14500 {
    static int N,M;
    static int[][] board;
    static int result = Integer.MIN_VALUE;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static boolean[][] visited;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine()," ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[N][M];
        visited = new boolean[N][M];

        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine()," ");
            for(int j=0;j<M;j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                DFS(i,j,0,0);
                Tetromino_Exception(i,j);
            }
        }
        System.out.println(result);
    }

    // "ㅗ" 모양을 제외한 나머지 도형의 회전, 대칭되는 모양이 DFS로 만들어지는 모든 경우의 수가 나온다.
    /*
    X를 시작위치로 하여 dx,dy를 번갈아가며 (-1,0), (0,-1), (1,0), (0,1)으로 4방향을 탐색하고 총 시작지점을 포함하여
    4번하므로 모든 도형을 나타낼 수 있다.
            o
          o o o
        o o o o o
      o o o X o o o
        o o o o o
          o o o
            o
     */
   static void DFS(int x,int y,int depth,int sum) {

        //4개의 정사각형이므로 4번 탐색하면 종료
        if(depth == 4) {
            result = Math.max(result, sum);
            return;
        }

        for(int i=0;i<4;i++) {
            int nextX = x + dx[i];
            int nextY = y + dy[i];

            if(nextX < 0 || nextY < 0 || nextX >= N || nextY >= M) {
                continue;
            }

            if(visited[nextX][nextY]) {
                continue;
            }

            visited[nextX][nextY] = true;
            DFS(nextX,nextY,depth+1,sum+board[nextX][nextY]);
            visited[nextX][nextY] = false;
        }
    }
    //"ㅗ"모양의 경우를 따로 계산해야한다. (DFS로 불가능하므로)
    static void Tetromino_Exception(int x, int y) {
        //"ㅏ"
        if(x+2 < N && y+1 < M) {
            result = Math.max(result, board[x][y]+board[x+1][y]+board[x+2][y]+board[x+1][y+1]);
        }
        //"ㅗ"
        if(x+1 < N && y+2 < M) {
            result = Math.max(result, board[x+1][y]+board[x][y+1]+board[x+1][y+1]+board[x+1][y+2]);
        }
        //"ㅓ"
        if(x+2<N && y+1<M) {
            result = Math.max(result, board[x+1][y]+board[x+1][y+1]+board[x][y+1]+board[x+2][y+1]);
        }
        //"ㅜ"
        if(x+1<N && y+2<M) {
            result = Math.max(result, board[x][y]+board[x+1][y+1]+board[x][y+1]+board[x][y+2]);
        }
    }

}


/*
//하드코딩
public class BOJ14500 {
    static int N,M;
    static int[][] board;
    static int result = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[505][505];

        for(int[] a:board) {
            Arrays.fill(a,0);
        }


        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine()," ");
            for(int j=0;j<M;j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        a();
        b();
        c();
        d();
        e();

        System.out.println(result);
    }

    static void a() {
        // "ㅡ"
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i][j]+board[i][j+1]+board[i][j+2]+board[i][j+3]);
            }
        }

        // "ㅣ"
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i][j]+board[i+1][j]+board[i+2][j]+board[i+3][j]);
            }
        }
    }

    static void b() {
        //  o
        //  o
        //  o o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i][j]+board[i+1][j]+board[i+2][j]+board[i+2][j+1]);
            }
        }

        //   o
        //   o
        // o o
        for(int i=0;i<N;i++) {
            for(int j=0; j<M;j++) {
                result = Math.max(result, board[i][j+1]+board[i+1][j+1]+board[i+2][j]+board[i+2][j+1]);
            }
        }

        // o o o
        // o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i][j]+board[i][j+1]+board[i][j+2]+board[i+1][j]);
            }
        }

        // o
        // o o o
        for(int i=0;i+1<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i][j]+board[i+1][j]+board[i+1][j+1]+board[i+1][j+2]);
            }
        }

        // o o
        //   o
        //   o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i][j]+board[i][j+1]+board[i+1][j+1]+board[i+2][j+1]);
            }
        }

        // o o
        // o
        // o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i][j]+board[i+1][j]+board[i+2][j]+board[i][j+1]);
            }
        }

        // o o o
        //     o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i][j]+board[i][j+1]+board[i][j+2]+board[i+1][j+2]);
            }
        }

        //     o
        // o o o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i+1][j]+board[i+1][j+1]+board[i+1][j+2]+board[i][j+2]);
            }
        }
    }

    static void c() {
        // o o
        // o o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i][j]+board[i][j+1]+board[i+1][j]+board[i+1][j+1]);
            }
        }
    }

    static void d() {
        // o
        // o o
        //   o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i][j]+board[i+1][j]+board[i+1][j+1]+board[i+2][j+1]);
            }
        }

        //   o
        // o o
        // o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i+1][j]+board[i+2][j]+board[i][j+1]+board[i+1][j+1]);
            }
        }

        //   o o
        // o o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i+1][j]+board[i+1][j+1]+board[i][j+1]+board[i][j+2]);
            }
        }

        // o o
        //   o o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i][j]+board[i][j+1]+board[i+1][j+1]+board[i+1][j+2]);
            }
        }
    }

    static void e() {
        //   o
        // o o o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i+1][j]+board[i+1][j+1]+board[i][j+1]+board[i+1][j+2]);
            }
        }

        // o o o
        //   o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i][j]+board[i+1][j+1]+board[i][j+1]+board[i][j+2]);
            }
        }

        // o
        // o o
        // o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i][j]+board[i+1][j]+board[i+1][j+1]+board[i+2][j]);
            }
        }

        //   o
        // o o
        //   o
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                result = Math.max(result, board[i+1][j]+board[i][j+1]+board[i+1][j+1]+board[i+2][j+1]);
            }
        }
    }
}
 */
