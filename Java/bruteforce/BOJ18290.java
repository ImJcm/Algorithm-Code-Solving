package BOJ;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/*
NM과 K (1)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	4135	1215	705	25.881%
문제
크기가 N×M인 격자판의 각 칸에 정수가 하나씩 들어있다. 이 격자판에서 칸 K개를 선택할 것이고, 선택한 칸에 들어있는 수를 모두 더한 값의 최댓값을 구하려고 한다. 단, 선택한 두 칸이 인접하면 안된다. r행 c열에 있는 칸을 (r, c)라고 했을 때, (r-1, c), (r+1, c), (r, c-1), (r, c+1)에 있는 칸이 인접한 칸이다.

입력
첫째 줄에 N, M, K가 주어진다. 둘째 줄부터 N개의 줄에 격자판에 들어있는 수가 주어진다.

출력
선택한 칸에 들어있는 수를 모두 더한 값의 최댓값을 출력한다.

제한
1 ≤ N, M ≤ 10
1 ≤ K ≤ min(4, N×M)
격자판에 들어있는 수는 -10,000보다 크거나 같고, 10,000보다 작거나 같은 정수이다.
항상 K개의 칸을 선택할 수 있는 경우만 입력으로 주어진다.
예제 입력 1
1 1 1
1
예제 출력 1
1
예제 입력 2
2 2 2
1 2
3 4
예제 출력 2
5
예제 입력 3
2 2 2
5 4
4 5
예제 출력 3
10
예제 입력 4
5 5 3
1 9 8 -2 0
-1 9 8 -3 0
-5 1 9 -1 0
0 0 0 9 8
9 9 9 0 0
예제 출력 4
27
 */
/*
 * DFS로 처리하는데 선택된 좌표에서 dx{-1,0,1,0}, dy{0,-1,0,1}의 visited를 check하면서 백트래킹
 * 하면 될 것으로 판단.
 * 제출 번호	아이디  문제	결과	        메모리	시간	    언어            코드 길이
 * 41336453	jcm	 18290	맞았습니다!!	17100	1988	Java 11 / 수정	2030B
 * 시간이 생각보다 많이 나온다.
 */
public class BOJ18290 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[][] board;
    static int[][] visited;
    static int N,M,K;
    static int result = Integer.MIN_VALUE;
    static int[] dx = {-1,0,1,0},dy={0,-1,0,1};

    public static void main(String[] args) throws IOException{
        String[] input = br.readLine().split(" ");
        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        K = Integer.parseInt(input[2]);

        board = new int[N][M];
        visited = new int[N][M];

        for(int i=0;i<N;i++) {
            input = br.readLine().split(" ");
            for(int j=0;j<M;j++) {
                board[i][j] = Integer.parseInt(input[j]);
            }
        }

        /*
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                DFS(i,j,1,0);
            }
        }
         */
        DFS(0,0,0,0);

        System.out.println(result);
    }

    static void DFS(int row,int col, int depth,int sum) {
        if(depth == K) {
            result = Math.max(result, sum);
            return;
        }

        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                if(visited[i][j] > 0) {
                    continue;
                }
                visit(i,j);
                DFS(i,j,depth+1,sum+board[i][j]);
                unvisit(i,j);
            }

        }
    }

    static void visit(int r,int c) {
        for(int k=0;k<4;k++) {
            if(0 <= r+dx[k] && 0 <= c+dy[k] && r+dx[k] < N && c+dy[k] < M) {
                visited[r+dx[k]][c+dy[k]] += 1;
            }
        }
        visited[r][c] += 1;
    }

    static void unvisit(int r,int c) {
        for(int k=0;k<4;k++) {
            if(0 <= r+dx[k] && 0 <= c+dy[k] && r+dx[k] < N && c+dy[k] < M) {
                visited[r+dx[k]][c+dy[k]] -= 1;
            }
        }
        visited[r][c] -= 1;
    }
}
