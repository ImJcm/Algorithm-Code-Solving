package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
주사위 윷놀이

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	14633	6503	4030	40.951%
문제
주사위 윷놀이는 다음과 같은 게임판에서 하는 게임이다.



처음에는 시작 칸에 말 4개가 있다.
말은 게임판에 그려진 화살표의 방향대로만 이동할 수 있다. 말이 파란색 칸에서 이동을 시작하면 파란색 화살표를 타야 하고, 이동하는 도중이거나 파란색이 아닌 칸에서 이동을 시작하면 빨간색 화살표를 타야 한다. 말이 도착 칸으로 이동하면 주사위에 나온 수와 관계 없이 이동을 마친다.
게임은 10개의 턴으로 이루어진다. 매 턴마다 1부터 5까지 한 면에 하나씩 적혀있는 5면체 주사위를 굴리고, 도착 칸에 있지 않은 말을 하나 골라 주사위에 나온 수만큼 이동시킨다.
말이 이동을 마치는 칸에 다른 말이 있으면 그 말은 고를 수 없다. 단, 이동을 마치는 칸이 도착 칸이면 고를 수 있다.
말이 이동을 마칠 때마다 칸에 적혀있는 수가 점수에 추가된다.
주사위에서 나올 수 10개를 미리 알고 있을 때, 얻을 수 있는 점수의 최댓값을 구해보자.

입력
첫째 줄에 주사위에서 나올 수 10개가 순서대로 주어진다.

출력
얻을 수 있는 점수의 최댓값을 출력한다.

예제 입력 1
1 2 3 4 1 2 3 4 1 2
예제 출력 1
190
예제 입력 2
1 1 1 1 1 1 1 1 1 1
예제 출력 2
133
예제 입력 3
5 1 2 3 4 5 5 3 2 4
예제 출력 3
214
예제 입력 4
5 5 5 5 5 5 5 5 5 5
예제 출력 4
130
출처
문제를 만든 사람: baekjoon
문제를 다시 작성한 사람: jh05013
알고리즘 분류
구현
브루트포스 알고리즘
시뮬레이션
백트래킹
 */
/*
알고리즘 핵심
1. 문제에서 주어지는 게임 보드판에서 말이 위치할 수 있는 곳마다 점수, 이동방향에 대한 정보가 필요하다.
2. 이동방향을 설정할 때, 각 위치에 대한 정보 설정이 필요하다.
    (위치에 대한 정보 : 배열로 게임판을 구성한 경우 각 위치를 인덱싱하여 매칭)
3. 게임판에서 말이 위치할 수 있는 곳마다 다음 이동방향을 직접 설정한다. (상대적으로 위치에 대한 정보를 결정하기 때문이다)
4. 아래 코드 기준, 파랑 화살표가 존재하는 위치는 5,10,15의 인덱싱에 위치하는 곳으로 해당 위치에서 이동방향의 경우 2가지가 필요하다.
4-1. 5,10,15에서 처음 출발하는 경우, board[pos][2]를 시작하여 파랑색 방향으로 처음 이동 후, 빨강색 방향의 이동방향인 board[pos][1]로 이동
4-2. 그 외의 출발인 경우, board[pos][1]로 주사위 만큼 이동을 수행
 */
public class BOJ17825 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int ans;
    static int[] order, ware;
    static int[][] board;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        dfs(0,0);

        System.out.println(ans);
    }

    static void dfs(int depth, int score) {
        if(depth == 10) {
            ans = Math.max(ans, score);
            return;
        }

        int dice_number = order[depth];

        for(int w=0;w<4;w++) {
            if(ware[w] == 32) continue;

            int cur_pos = ware[w];
            int next_pos = move_ware(w,dice_number);

            if(dup_location(next_pos)) continue;

            ware[w] = next_pos;
            dfs(depth + 1, score + board[ware[w]][0]);
            ware[w] = cur_pos;
        }
    }

    static int move_ware(int w, int d) {
        int next_pos = ware[w];
        // 처음 이동 시, 파랑길 이동 or 빨간길 이동
        if(ware[w] == 5 || ware[w] == 10 || ware[w] == 15) {
            next_pos = board[next_pos][2];
        } else {
            next_pos = board[next_pos][1];
        }

        for(int i=2;i<=d;i++) {
            if(next_pos == 32) break;
            next_pos = board[next_pos][1];
        }

        return next_pos;
    }

    static boolean dup_location(int n) {
        for(int i=0;i<4;i++) {
            if(ware[i] == n && n != 32) return true;
        }
        return false;
    }

    static void init_setting() throws IOException {
        ware = new int[4];
        order = new int[10];
        board = new int[33][3]; // 0 : 점수, 1 : 이동 방향 1, 2 : 이동 방향 2

        ans = Integer.MIN_VALUE;

        String[] input = br.readLine().split(" ");

        ware[0] = ware[1] = ware[2] = ware[3] = 0;

        for(int i=0;i<input.length;i++) {
            order[i] = Integer.parseInt(input[i]);
        }

        /*
            board 순서
            0(출발)     32(도착)
            1          20  19
            2          31    18
            3          30      17
            4                    16
            5 21 22 23 26 27 28 29 15
              6                  14
                7      25      13
                  8    24    12
                    9  10  11
         */

        board[0][0] = board[32][0] = 0;

        // 외곽 점수: 1~20
        for(int i=1;i<21;i++) {
            board[i][0] = 2 * i;
        }

        // 내부 점수: 낮은 숫자 점수의 위치부터 21~31
        int[] inner_score = {13,16,19,22,24,25,26,27,28,30,35};
        for(int i=21;i<32;i++) {
            board[i][0] = inner_score[i-21];
        }

        // 시작 이동 방향 1
        board[0][1] = 1;

        // 외곽 이동 방향 1
        for(int i=1;i<20;i++) {
            board[i][1] = i+1;
        }

        // 5,10,15 이동 방향 2
        board[5][2] = 21;
        board[10][2] = 24;
        board[15][2] = 29;

        // 21,22,23 이동 방향 1
        board[21][1] = 22;
        board[22][1] = 23;
        board[23][1] = 26;

        // 24,25 이동 방향 1
        board[24][1] = 25;
        board[25][1] = 26;

        // 26 이동 방향 1
        board[26][1] = 30;

        // 27,28,29 이동 방향 1
        board[27][1] = 26;
        board[28][1] = 27;
        board[29][1] = 28;

        // 30,31 이동 방향 1
        board[30][1] = 31;
        board[31][1] = 20;

        // 20 이동 방향 1
        board[20][1] = 32;
    }
}
