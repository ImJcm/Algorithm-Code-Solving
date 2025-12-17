package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
점프 게임

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	6154	1997	1496	30.725%
문제
상근이는 오른쪽 그림과 같은 지도에서 진행하는 게임을 만들었다.

지도는 총 2개의 줄로 나누어져 있으며, 각 줄은 N개의 칸으로 나누어져 있다. 칸은 위험한 칸과 안전한 칸으로 나누어져 있고, 안전한 칸은 유저가 이동할 수 있는 칸, 위험한 칸은 이동할 수 없는 칸이다.

가장 처음에 유저는 왼쪽 줄의 1번 칸 위에 서 있으며, 매 초마다 아래 세 가지 행동중 하나를 해야 한다.

한 칸 앞으로 이동한다. 예를 들어, 현재 있는 칸이 i번 칸이면, i+1번 칸으로 이동한다.
한 칸 뒤로 이동한다. 예를 들어, 현재 있는 칸이 i번 칸이면, i-1번 칸으로 이동한다.
반대편 줄로 점프한다. 이때, 원래 있던 칸보다 k칸 앞의 칸으로 이동해야 한다. 예를 들어, 현재 있는 칸이 왼쪽 줄의 i번 칸이면, 오른쪽 줄의 i+k번 칸으로 이동해야 한다.
N번 칸보다 더 큰 칸으로 이동하는 경우에는 게임을 클리어한 것이다.

게임을 재밌게 하기 위해서, 상근이는 1초에 한 칸씩 각 줄의 첫 칸이 사라지는 기능을 만들었다. 즉, 게임을 시작한지 1초가 지나면 1번 칸이 사라지고, 2초가 지나면 2번 칸이 사라진다. 편의상 유저가 먼저 움직이고, 칸이 사라진다고 가정한다. 즉, 이번에 없어질 칸이 3번 칸인데, 상근이가 3번 칸에 있다면, 3번 칸에서 다른 칸으로 이동하고 나서 3번 칸이 없어지는 것이다.

각 칸의 정보가 주어졌을 때, 게임을 클리어 할 수 있는지, 없는지 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N과 k가 주어진다. (1 ≤ N, k ≤ 100,000)

둘째 줄에는 왼쪽 줄의 정보가 주어진다. i번째 문자가 0인 경우에는 위험한 칸이고, 1인 경우에는 안전한 칸이다. 셋째 줄에는 오른쪽 줄의 정보가 주어지고, 각 문자의 의미는 왼쪽 줄의 의미와 동일하다.

왼쪽 줄의 1번 칸은 항상 안전한 칸이다.

출력
게임을 클리어할 수 있으면 1을, 없으면 0을 출력한다.

예제 입력 1
7 3
1110110
1011001
예제 출력 1
1
예제 입력 2
6 2
110101
011001
예제 출력 2
0
출처
데이터를 추가한 사람: djm03178
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
알고리즘 핵심
BFS
1. 어떠한 라인을 이용하는지와 현재 위치, 이동하는데 걸린 시간 정보를 갖는 class
2. 왼쪽 라인 첫 번째 자리에서 출발하여 N보다 큰 위치에 도달하는지 bfs를 사용하여 결과를 도출한다.
- N보다 큰 경우를 검사해야 하므로 line과 방문 여부를 검사하기 위한 visited 변수의 크기를 고려하여 조건을 검사해야한다.
- 이동한 시간을 체크하여 다음으로 이동할 위치가 시간 + 1보다 작거나 같은 경우 해당 위치는 불가능한 것을 조건에 추가해야한다.
 */
public class BOJ15558 {
    static class BOJ15558_pos {
        int line,cur_pos,time;

        BOJ15558_pos(int line, int cur_pos, int time) {
            this.line = line;
            this.cur_pos = cur_pos;
            this.time = time;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,K;
    static int[][] line;
    static boolean flag;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        bfs(new BOJ15558_pos(0,1,0));

        System.out.println(flag ? 1 : 0);
    }

    private static void bfs(BOJ15558_pos s) {
        Queue<BOJ15558_pos> q = new LinkedList<>();
        boolean[][] visited = new boolean[2][N + 1];

        q.add(s);
        visited[s.line][s.cur_pos] = true;

        while(!q.isEmpty()) {
            BOJ15558_pos now = q.poll();

            for(int i = 0; i < 3; i++) {
                int next_pos = now.cur_pos;
                int next_line = now.line;

                switch (i) {
                    case 0:
                        next_pos += 1;
                        break;
                    case 1:
                        next_pos -= 1;
                        break;
                    case 2:
                        next_line = next_line == 0 ? 1 : 0;
                        next_pos += K;
                        break;
                }

                if(next_pos > N) {
                    flag = true;
                    q.clear();
                    break;
                }
                if(next_pos < 1 || line[next_line][next_pos] == 0 || next_pos <= now.time + 1 || visited[next_line][next_pos]) continue;

                visited[next_line][next_pos] = true;
                q.add(new BOJ15558_pos(next_line,next_pos,now.time + 1));
            }
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);

        line = new int[2][N + 1];

        for(int i = 0; i < 2; i++) {
            input = br.readLine().split("");
            for(int j = 1; j <= N; j++) {
                line[i][j] = Integer.parseInt(input[j-1]);
            }
        }
    }
}
