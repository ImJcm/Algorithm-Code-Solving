package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
십자가 2개 놓기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	2514	805	607	33.188%
문제
십자가는 가운데에 '*'가 있고, 상하좌우 방향으로 모두 같은 길이의 '*'가 있는 모양이다. 십자가의 크기는 가운데를 중심으로 상하좌우 방향으로 있는 '*'의 개수이다. 십자가의 크기는 0보다 크거나 같아야 한다.

아래 그림은 크기가 0, 1, 2, 3인 십자가이고, 빈 칸은 '.'이다.

                  ...*...
          ..*..   ...*...
    .*.   ..*..   ...*...
*   ***   *****   *******
    .*.   ..*..   ...*...
          ..*..   ...*...
                  ...*...
십자가의 넓이는 포함된 '*'의 개수이다. 크기가 0, 1, 2, 3인 십자가의 넓이는 1, 5, 9, 13이다.

크기가 N×M이고, '.'과 '#'로 이루어진 격자판이 주어진다. 격자판에 두 개의 십자가를 겹치지 않게 놓으려고 한다. 십자가는 '#'가 있는 칸에만 놓을 수 있다. 놓인 십자가 넓이의 곱의 최댓값을 구해보자.

입력
첫째 줄에 격자판의 크기 N, M (2 ≤ N, M ≤ 15)이 주어진다. 둘째 줄부터 N개의 줄에 격자판의 상태가 주어진다. 항상 두 개의 십자가를 놓을 수 있는 경우만 입력으로 주어진다.

출력
첫째 줄에 놓은 십자가 넓이의 곱의 최댓값을 출력한다.

예제 입력 1
5 6
######
#...#.
######
##..#.
######
예제 출력 1
5
아래와 같이 넓이가 1, 5인 십자가를 놓을 수 있다.

######
#...*.
#*#***
##..*.
######
예제 입력 2
6 6
.#..#.
######
.#..#.
######
.#..#.
.#..#.
예제 출력 2
25
넓이가 5인 십자가 두 개를 놓을 수 있다.

.*..#.
***###
.*..*.
###***
.#..*.
.#..#.
출처
문제를 번역한 사람: baekjoon
데이터를 추가한 사람: kir3i, skaduddn
문제의 오타를 찾은 사람: sminhyuck
알고리즘 분류
구현
브루트포스 알고리즘
 */
/*
알고리즘 핵심
1. (1,1)부터 (N,M)까지 bruteforce를 수행하여 각 십자가를 놓을 수 있는 두개의 좌표를 선택
2. 각 좌표에서 가능한 십자가의 형태를 선택한다.
3. 두 십자가의 형태가 선택되면, 두 십자가의 넓이의 곱을 ans에 업데이트한다.

첫 접근으로 틀린 이유로 문제에서 주어지는 반례와 질문 게시판을 통한 모든 반례를 입력으로 주어졌을 때, 모두 정답을 결과로 나타내었는데 3%에서 틀렸다고 하여 어떠한 반례가 있을까 고민하다가 좌표 선택과정이 잘못된 것인가 생각하여
아래와 같이 해결할 수 있었다.

좌표를 선택하는 과정에서 아래와 같은 코드로 현재 선택된 좌표 이후의 좌표를 나머지 십자가의 좌표로 선택하게하여 중복되는 과정없이 하려했으나 틀린코드로 판정되었다.
static void dfs(int R, int C, int depth, int area) {
    ...
    for(int r=R;r<=N;r++) {
        for(int c=C;c<=M;c++) {
            ...
        }
    }
}
즉, 하나의 십자가 좌표가 선택되면 다음 좌표에서부터 십자가가 선택되는 것이 아닌 (1,1)부터 다시 선택하게 설정해야 된다.
그러한 반례가 있는 모양이다.
static void dfs(int depth, int area) {
    ...
    for(int r=1;r<=N;r++) {
        for(int c=1;c<=M;c++) {
            ...
        }
    }
}
 */
public class BOJ17085 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,ans;
    static String[][] board;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        dfs(0, 1);

        System.out.println(ans);
    }

    static void dfs(int depth, int area) {
        if(depth == 2) {
            ans = Math.max(ans, area);
            return;
        }

        for(int r=1;r<=N;r++) {
            for(int c=1;c<=M;c++) {
                if(visited[r][c] || board[r][c].equals(".")) continue;

                for(Integer i : check_possible_cross(r,c)) {
                    visited_cross_activate(i,r,c);
                    dfs(depth+1,area * ((i*4) + 1));
                    visited_cross_deactivate(i,r,c);
                }

            }
        }
    }

    static void visited_cross_activate(int i, int r, int c) {
        while(i >= 0) {
            visited[r-i][c] = visited[r][c-i] = visited[r+i][c] = visited[r][c+i] = true;
            i--;
        }
    }

    static void visited_cross_deactivate(int i, int r, int c) {
        while(i >= 0) {
            visited[r-i][c] = visited[r][c-i] = visited[r+i][c] = visited[r][c+i] = false;
            i--;
        }
    }

    static ArrayList<Integer> check_possible_cross(int r, int c) {
        ArrayList<Integer> possible_cross_arr = new ArrayList<>();

        int i = 0;
        while(true) {
            if(r-i < 1 || c-i < 1 || r+i > N || c+i > M) break;
            if(!(board[r-i][c].equals("#") && board[r][c-i].equals("#") && board[r+i][c].equals("#") && board[r][c+i].equals("#"))) break;
            if(visited[r-i][c] || visited[r][c-i] || visited[r+i][c] || visited[r][c+i]) break;

            possible_cross_arr.add(i++);
        }

        return possible_cross_arr;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        board = new String[N+1][M+1];
        visited = new boolean[N+1][M+1];

        ans = 1;

        for(int n=1;n<=N;n++) {
            input = br.readLine().split("");
            for(int m=1;m<=M;m++) {
                board[n][m] = input[m-1];
            }
        }
    }
}
