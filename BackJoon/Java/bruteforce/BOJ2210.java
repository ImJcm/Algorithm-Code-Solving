package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

/*
숫자판 점프 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	9916	7307	5860	74.536%
문제
5×5 크기의 숫자판이 있다. 각각의 칸에는 숫자(digit, 0부터 9까지)가 적혀 있다. 이 숫자판의 임의의 위치에서 시작해서, 인접해 있는 네 방향으로 다섯 번 이동하면서, 각 칸에 적혀있는 숫자를 차례로 붙이면 6자리의 수가 된다. 이동을 할 때에는 한 번 거쳤던 칸을 다시 거쳐도 되며, 0으로 시작하는 000123과 같은 수로 만들 수 있다.

숫자판이 주어졌을 때, 만들 수 있는 서로 다른 여섯 자리의 수들의 개수를 구하는 프로그램을 작성하시오.

입력
다섯 개의 줄에 다섯 개의 정수로 숫자판이 주어진다.

출력
첫째 줄에 만들 수 있는 수들의 개수를 출력한다.

예제 입력 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 2 1
1 1 1 1 1
예제 출력 1
15
힌트
111111, 111112, 111121, 111211, 111212, 112111, 112121, 121111, 121112, 121211, 121212, 211111, 211121, 212111, 212121 이 가능한 경우들이다.

출처
Olympiad > USA Computing Olympiad > 2005-2006 Season > USACO November 2005 Contest > Bronze ?번

알고리즘 분류
그래프 이론
브루트포스 알고리즘
그래프 탐색
깊이 우선 탐색
 */
public class BOJ2210 {
    static class BOJ2210_node {
        int r,c;
        String d;

        BOJ2210_node(int r, int c, String d) {
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BOJ2210_node[][] board;
    static int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
    static int ans;
    static String digits;
    static HashSet<String> visited;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int r=0;r<5;r++) {
            for(int c=0;c<5;c++) {
                digits += board[r][c].d;
                dfs(0, board[r][c]);
                digits = "";
            }
        }


        System.out.println(ans);
    }

    static void dfs(int depth, BOJ2210_node n) {
        if(depth == 5) {
            if(!visited.contains(digits)) {
                visited.add(digits);
                ans++;
            }
            return;
        }

        for(int[] d : directions) {
            int nr = n.r + d[0];
            int nc = n.c + d[1];

            if(nr < 0 || nr >= 5 || nc < 0 || nc >= 5) continue;

            digits = digits.concat(board[nr][nc].d);
            dfs(depth + 1, board[nr][nc]);
            digits = digits.substring(0, digits.length() - 1);
        }
    }

    static void init_setting() throws IOException {
        board = new BOJ2210_node[5][5];
        visited = new HashSet<>();

        for(int i=0;i<5;i++) {
            String[] input = br.readLine().split(" ");
            for(int j=0;j<5;j++) {
                board[i][j] = new BOJ2210_node(i,j,input[j]);
            }
        }

        ans = 0;
        digits = "";
    }
}
