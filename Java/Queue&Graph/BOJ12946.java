package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;

/*
육각 보드

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	1764	482	351	26.979%
문제
크기가 N × N인 육각 보드가 주어진다. 아래 그림은 N = 1, 2, 3, 4인 경우의 그림이다.



육각 보드의 일부 칸을 색칠하려고 한다. 두 칸이 변을 공유하는 경우에는 같은 색으로 칠할 수 없다.

어떤 칸을 색칠해야 하는지 주어졌을 때, 필요한 색의 최소 종류를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N이 주어진다. (1 ≤ N ≤ 50)

둘째 줄부터 N개의 줄에는 어떤 칸을 색칠해야 하는지에 대한 정보가 주어진다.

i번째 줄의 j번째 문자는 (i, j)칸의 정보를 나타내고, '-'인 경우는 색칠하지 않는 것이고 'X'면 색칠해야 하는 것이다.

출력
첫째 줄에 필요한 색의 종류의 최솟값을 출력한다.

예제 입력 1
3
---
---
---
예제 출력 1
0
예제 입력 2
4
-X--
---X
----
-X--
예제 출력 2
1
예제 입력 3
4
XXXX
---X
---X
---X
예제 출력 3
2
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
그래프 이론
브루트포스 알고리즘
그래프 탐색
너비 우선 탐색
깊이 우선 탐색
 */
public class BOJ12946 {
    static class BOJ12946_board {
        int color;
        String colored;

        public BOJ12946_board(int color, String colored) {
            this.color = color;
            this.colored = colored;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BOJ12946_board[][] boards;
    static boolean flag = false;
    static boolean[][] visited;
    static int[][] direct = {{-1,0},{0,-1},{-1,1},{0,1},{1,-1},{1,0}};
    static int N, min_color_cnt = 0;

    public static void main(String[] args) throws IOException {
        init_setting_2();
        correct_solve();

        //init_setting_1();
        //drawing_boards();

        System.out.println(min_color_cnt);
    }

    static void correct_solve() {
        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++) {
                if(boards[i][j].colored.equals("X") && boards[i][j].color == -1) {
                    dfs_2(i,j,0);
                }
            }
        }
        System.out.println(min_color_cnt);
    }

    //참고 : https://go-coding.tistory.com/47, https://wookje.dance/2017/03/06/boj-12946-%EC%9C%A1%EA%B0%81-%EB%B3%B4%EB%93%9C/
    //보드는 최대 3개의 색으로 칠할 수 있고, 색상을 0,1인 경우 최소 필요 색상은 2개이고 DFS로 돌면서 0,1 이외의 색이 필요한 경우는 필요한 최소한의 색상은 3이 된다.
    static void dfs_2(int x, int y, int c) {
        boards[x][y].color = c;
        min_color_cnt = Math.max(1, min_color_cnt);

        for(int[] d : direct) {
            int nx = x + d[0];
            int ny = y + d[1];

            if(!(0 <= nx && nx < N && 0 <= ny && ny < N)) {
                continue;
            }
            if(!boards[nx][ny].colored.equals("X")) {
                continue;
            }
            if(boards[nx][ny].color == -1) {
                dfs_2(nx,ny,1-c);
            }
            min_color_cnt = Math.max(2,min_color_cnt);
            if(boards[nx][ny].color == c) {
                min_color_cnt = Math.max(3, min_color_cnt);
            }
        }
    }

    static void init_setting_2() throws IOException {
        N = Integer.parseInt(br.readLine());

        boards = new BOJ12946_board[N][N];

        for(int i=0;i<N;i++) {
            String[] input_line = br.readLine().split("");
            for(int j=0;j<N;j++) {
                boards[i][j] = new BOJ12946_board(-1,input_line[j]);
            }
        }
    }

    //dfs방식 - 초기 작성 코드
    static void drawing_boards() {
        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++) {
                if(boards[i][j].colored.equals("-")) {
                    continue;
                }

                visited[i][j] = true;
                boards[i][j].color = 1;
                dfs_1(i,j,1);
            }
        }
    }

    //10% 까지 정답처리 이후, 시간초과발생
    //시간초과 발생 이유 : X가 칠해진 board를 시작으로 최소 색상을 구한 뒤, 시작위치를 바꾸어 동일한 작업을 수행하기 때문이다.
    //                  즉, visited[i][j] = false; boards[i][j].color = 1; 이 부분에서 중복되고 불필요한 작업이였기 때문이다.
    //이 문제의 중요한 포인트는 사용할 수 있는 색의 최대 갯수는 3개라는 점을 이용하면 기저사례로 칠할 수 있는 색상을 정하는 조건에서 3개 이상이 넘어가면 return을 수행한다.
    //아래 코드는 정답처리 되었지만, 다른 좋은 코드를 참고하여 재작성하는 것이 좋아보인다.
    static void dfs_1(int x, int y, int used_min_color) {
        //다음 보드로 넘어갈 수 있는 6개의 경로를 탐색
        for(int[] d : direct) {
            //탐색 범위를 넘어가거나, 이미 방문한 보드라면 continue
            if((x + d[0] < 0 || x + d[0] >= N || y + d[1] < 0 || y + d[1] >= N) || visited[x + d[0]][y + d[1]]) {
                continue;
            }

            //한 보드를 기준으로 주변의 방문한 보드의 색상을 검사하여 이미 쓰인 색상을 저장
            LinkedHashSet<Integer> impossible_colors = new LinkedHashSet<>();
            for(int[] r : direct) {
                if(!(x + d[0] + r[0] < 0 || x + d[0] + r[0] >= N || y + d[1] + r[1] < 0 || y + d[1] + r[1] >= N)
                        && visited[x + d[0] + r[0]][y + d[1] + r[1]]
                        && !boards[x + d[0] + r[0]][y + d[1] + r[1]].colored.equals("-")) {
                    impossible_colors.add(boards[x + d[0] + r[0]][y + d[1] + r[1]].color);
                }
            }

            //색상을 1번부터 시작하여 이미 쓰인 색상인지 검사하고, 안쓰인 색상이 올 때까지 + 1 한다.
            int draw_color = 1;
            while(impossible_colors.contains(draw_color)) {
                draw_color++;
            }
            //색상이 정해지면 impossible_colors에 포함한다.
            impossible_colors.add(draw_color);

            //impossible_colors의 크기가 3이 넘어가면 = 이미 사용된 색상이 4개 이상인 경우, return <- 기저사례
            if(impossible_colors.size() > 3) {
                return;
            }

            //used_min_color에 사용된 색상의 개수를 저장한다.
            used_min_color = used_min_color <= impossible_colors.size() ? impossible_colors.size() : used_min_color;

            //탐색이 된 보드를 방문여부와 색칠이 가능한 색상으로 업데이트한다.
            visited[x + d[0]][y + d[1]] = true;
            boards[x + d[0]][y + d[1]].color = draw_color;
            dfs_1(x + d[0], y + d[1], used_min_color);
        }

        //dfs 함수가 종료된 후, uses_min_color와 min_color_cnt를 비교하여 큰 값을 업데이트한다. 이때, min_color_cnt는 3값을 넘어갈 수 없다.
        min_color_cnt = used_min_color >= min_color_cnt ? used_min_color : min_color_cnt;
    }

    static void init_setting_1() throws IOException {
        N = Integer.parseInt(br.readLine());

        visited = new boolean[N][N];
        boards = new BOJ12946_board[N][N];

        for(int i=0;i<N;i++) {
            String[] input_line = br.readLine().split("");
            for(int j=0;j<N;j++) {
                if(input_line[j].equals("X")) {
                    visited[i][j] = false;
                } else {
                    visited[i][j] = true;
                }
                boards[i][j] = new BOJ12946_board(1,input_line[j]);
            }
        }
    }
}
