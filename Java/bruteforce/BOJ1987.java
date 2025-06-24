package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
알파벳 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	145496	45326	27399	28.655%
문제
세로
$R$칸, 가로
$C$칸으로 된 표 모양의 보드가 있다. 보드의 각 칸에는 대문자 알파벳이 하나씩 적혀 있고, 좌측 상단 칸 (
$1$행
$1$열) 에는 말이 놓여 있다.

말은 상하좌우로 인접한 네 칸 중의 한 칸으로 이동할 수 있는데, 새로 이동한 칸에 적혀 있는 알파벳은 지금까지 지나온 모든 칸에 적혀 있는 알파벳과는 달라야 한다. 즉, 같은 알파벳이 적힌 칸을 두 번 지날 수 없다.

좌측 상단에서 시작해서, 말이 최대한 몇 칸을 지날 수 있는지를 구하는 프로그램을 작성하시오. 말이 지나는 칸은 좌측 상단의 칸도 포함된다.

입력
첫째 줄에
$R$과
$C$가 빈칸을 사이에 두고 주어진다. (
$1 ≤ R,C ≤ 20$) 둘째 줄부터
$R$개의 줄에 걸쳐서 보드에 적혀 있는
$C$개의 대문자 알파벳들이 빈칸 없이 주어진다.

출력
첫째 줄에 말이 지날 수 있는 최대의 칸 수를 출력한다.

예제 입력 1
2 4
CAAB
ADCB
예제 출력 1
3
예제 입력 2
3 6
HFDFFB
AJHGDH
DGAGEH
예제 출력 2
6
예제 입력 3
5 5
IEFCJ
FHFKC
FFALF
HFGCF
HMCHH
예제 출력 3
10
출처
Olympiad > Croatian Highschool Competitions in Informatics > 2002 > Regional Competition - Juniors 3번

데이터를 추가한 사람: august14, doju, jh05013, xotjr5132
알고리즘 분류
그래프 이론
그래프 탐색
깊이 우선 탐색
백트래킹
격자 그래프
 */
/*
알고리즘 핵심
bruteforce (dfs + backTracking)
1. 시작 시점에서 상하좌우로 말의 이동을 수행하고, 말의 초기 이동 횟수를 1로 초기화한다.
2. 깊이 우선 탐색(dfs)를 수행하여 말의 이동 횟수를 누적한다.
3. 말이 이동 시, 해당 위치가 이전에 도달한 알파벳과 동일한 경우, 현재 이동한 횟수를 ans에 최대값을 업데이트한다.
4. 말이 이동할 수 있는 모든 경우의 수를 탐색한다.
 */
public class BOJ1987 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int R,C,ans;
    static char[][] board;
    static int[][] direction = {{-1,0},{1,0},{0,-1},{0,1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        boolean[] visited = new boolean[26];
        visited[board[1][1] - 'A'] = true;
        how_much_can_move(1, 1, 1, visited);

        System.out.println(ans);
    }

    private static void how_much_can_move(int r, int c, int m, boolean[] visited) {
        for(int[] d : direction) {
            int nr = r + d[0];
            int nc = c + d[1];

            if(nr < 1 || nr > R || nc < 1 || nc > C) continue;
            int idx = board[nr][nc] - 'A';

            if(visited[idx]) {
                ans = Math.max(ans, m);
                continue;
            }

            visited[idx] = true;
            how_much_can_move(nr, nc, m + 1, visited);
            visited[idx] = false;
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        R = Integer.parseInt(input[0]);
        C = Integer.parseInt(input[1]);

        board = new char[R + 1][C + 1];

        for(int i = 1; i <= R; i++) {
            input = br.readLine().split("");

            for(int j = 1; j <= C; j++) {
                board[i][j] = input[j - 1].charAt(0);
            }
        }

        ans = 1;
    }
}
