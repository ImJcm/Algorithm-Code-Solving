package bruteforce;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/*
문제
상근이는 어렸을 적에 "봄보니 (Bomboni)" 게임을 즐겨했다.
가장 처음에 N×N크기에 사탕을 채워 놓는다. 사탕의 색은 모두 같지 않을 수도 있다.
상근이는 사탕의 색이 다른 인접한 두 칸을 고른다. 그 다음 고른 칸에 들어있는 사탕을 서로 교환한다.
이제, 모두 같은 색으로 이루어져 있는 가장 긴 연속 부분(행 또는 열)을 고른 다음 그 사탕을 모두 먹는다.
사탕이 채워진 상태가 주어졌을 때, 상근이가 먹을 수 있는 사탕의 최대 개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 보드의 크기 N이 주어진다. (3 ≤ N ≤ 50)
다음 N개 줄에는 보드에 채워져 있는 사탕의 색상이 주어진다. 빨간색은 C, 파란색은 P, 초록색은 Z, 노란색은 Y로 주어진다.
사탕의 색이 다른 인접한 두 칸이 존재하는 입력만 주어진다.

출력
첫째 줄에 상근이가 먹을 수 있는 사탕의 최대 개수를 출력한다.

예제 입력 1
3
CCP
CCP
PPC
예제 출력 1
3

예제 입력 2
4
PPPP
CYZY
CCPY
PPCC
예제 출력 2
4
 */

public class BOJ3085 {
    static char[][] board;
    static int max_candy = Integer.MIN_VALUE;
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        board = new char[N+1][N+1];

        for(int i=0;i<N;i++) {
            board[i] = br.readLine().toCharArray();
        }

        for(int i=0;i<N;i++) {
            for(int j=0;j<N-1;j++) {
                swap(i,j,i+1,j);
                candy_check();
                swap(i,j,i+1,j);

                swap(i,j,i,j+1);
                candy_check();
                swap(i,j,i,j+1);
            }
        }

        System.out.println(max_candy);
    }

    //swap 이후, 스왑한 라인만 캔디를 검사해도 되는 것 같지만, 스왑했는데 스왑한 라인에서 사탕의 최댓값이
    //안바꾼 라인의 최댓값이 클 경우도 있으므로 전체를 검사하는 방법을 사용
    static void candy_check() {
        for(int i=0;i<N;i++) {
            int count = 1;
            for(int j=0;j<N-1;j++) {
                if(board[i][j+1] == board[i][j]) {
                    count++;
                    max_candy = Math.max(count,max_candy);
                    continue;
                }
                count = 1;
            }
        }

        for(int i=0;i<N;i++) {
            int count = 1;
            for(int j=0;j<N-1;j++) {
                if(board[j+1][i] == board[j][i]) {
                    count++;
                    max_candy = Math.max(count,max_candy);
                    continue;
                }
                count = 1;
            }
        }
    }

    static void swap(int r, int c, int c_r, int c_c) {
        char temp = board[r][c];
        board[r][c] = board[c_r][c_c];
        board[c_r][c_c] = temp;
    }
}

//재귀형식으로 candy_game을 불러오게되는 경우, 시간초과 발생하기 때문에
//for문으로 순차적으로 수행해야 할 것 같다.
/*
public clas
public class BOJ3085 {
    static char[][] board;
    static int max_candy = Integer.MIN_VALUE;
    static int N;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());


        board = new char[N][N];

        for(int i=0;i<N;i++) {
            board[i] = br.readLine().toCharArray();
        }

        candy_game(board,0,0);

        System.out.println(max_candy);
    }

    static void candy_game(char[][] b, int r, int c) {
        if(r == N || c == N) {
            return;
        }

        if(r+1 < N && b[r][c] != b[r+1][c]) {
            swap(b,r,c,r+1,c);
            max_candy = Math.max(max_candy,check(b,r,c));
            swap(b,r,c,r+1,c);
        }

        if (c+1 < N && b[r][c] != b[r][c+1]) {
            swap(b,r,c,r,c+1);
            max_candy = Math.max(max_candy,check(b,r,c));
            swap(b,r,c,r,c+1);
        }

        candy_game(b,r+1,c);
        candy_game(b,r,c+1);
    }

    static int check(char[][] b, int r, int c) {
        int row_count = 1;
        int col_count = 1;

        int r_1;
        int c_1;

        r_1 = r;
        while(r_1-- > 0 && b[r_1][c] == b[r][c]) row_count++;

        r_1 = r;
        while(++r_1 < N && b[r_1][c] == b[r][c]) row_count++;


        c_1 = c;
        while(c_1-- > 0 && b[r][c_1] == b[r][c]) col_count++;

        c_1 = c;
        while(++c_1 < N && b[r][c_1] == b[r][c]) col_count++;

        return Math.max(row_count, col_count);
    }

    static void swap(char[][] b, int r1, int c1, int r2, int c2) {
        char temp;

        temp = b[r1][c1];
        b[r1][c1] = b[r2][c2];
        b[r2][c2] = temp;
    }
}
*/
