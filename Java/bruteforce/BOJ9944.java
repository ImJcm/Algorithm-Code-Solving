package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/*
NxM 보드 완주하기 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
3 초 (하단 참고)	256 MB	4564	1582	1003	32.209%
문제
N×M 보드 위에서 할 수 있는 게임이 있다. 보드는 크기가 1×1인 정사각형 칸으로 나누어져 있다. 보드의 각 칸은 빈 칸 또는 장애물이다. 장애물은 아래 그림에선 어두운 사각형으로 표시되어져 있다.

게임을 시작하려면 보드의 빈 칸 위에 공을 하나 놓아야 한다. 아래 그림에서 공은 회색 점으로 표시되어져 있다. 게임은 단계로 이루어져 있고, 각 단계는 아래와 같이 구성되어져 있다.

위, 아래, 오른쪽, 왼쪽 중 방향 하나를 고른 다음, 그 방향으로 공을 계속해서 이동시킨다.
공은 장애물, 보드의 경계, 이미 공이 지나갔던 칸을 만나기 전까지 계속해서 이동한다.
게임은 공이 더 이상 이동할 수 없을 때 끝난다. 이 때, 모든 빈 칸을 공이 방문한 적이 있어야 한다.

아래 그림은 총 10단계 만에 모든 칸을 방문하는 방법이다.



보드의 상태가 주어졌을 때, 모든 칸을 방문하기 위한 이동 횟수의 최솟값을 구하는 프로그램을 작성하시오.

입력
입력은 여러 개의 테스트 케이스로 이루어져 있다.

각 테스트 케이스의 첫째 줄에는 보드의 크기를 나타내는 N과 M이 주어진다. N은 세로 크기, M은 가로 크기이고, 두 값은 30보다 작거나 같은 자연수이다. 둘째 줄부터 N개의 줄에는 보드의 상태가 주어진다. 보드의 상태는 장애물을 나타내는 '*'과 빈 칸을 나타내는 '.'으로 이루어져 있다.

입력으로 주어진 보드가 장애물로만 이루어진 경우는 없다.

출력
각 테스트 케이스마다 보드의 모든 빈 칸을 방문하는 최소 이동 횟수를 출력한다. 출력 형식은 예제를 참고한다.

만약, 모든 빈 칸을 방문할 수 없다면 최소 이동 횟수는 -1이다. 가능한 이동 경로의 수는 1,000,000개를 넘지 않는다.

예제 입력 1
5 5
**...
.....
....*
..*..
.....
3 4
****
*...
*..*
예제 출력 1
Case 1: 10
Case 2: 3
출처
ICPC > Regionals > North America > North Central North America Regional > NCNA 2013 F번

문제의 오타를 찾은 사람: sky1357
빠진 조건을 찾은 사람: jh05013
알고리즘 분류
구현
브루트포스 알고리즘
백트래킹
시간 제한
Python 3: 30 초
PyPy3: 30 초
Python 2: 30 초
PyPy2: 30 초
 */
/*
알고리즘 핵심
bruteforce
1. 공의 이동횟수를 확인하기 위해 처음 시작위치를 보드 위의 가능한 지점 모두 수행해야 한다.
2. 처음 시작 위치를 기준으로 4가지 방향으로 가능한 이동 방향을 수행하고, 불가능한 경우 백트랙킹을 수행한다.
3. 모든 이동 가능한 지점을 도달하는 경우를 체크하기 위해 별도의 매개변수로 남은 블록을 체크하여 기저사례로 사용한다.
4. 모든 블록을 도달한 경우, 최소 이동횟수를 업데이트한다.
 */
public class BOJ9944 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,M,ans,remain_block,num = 1;
        char[][] board;
        boolean[][] visited;
        int[][] direction = {
                {1,0},{-1,0},{0,1},{0,-1}
        };

        private void solve() throws IOException {
            while(true) {
                if(init_setting()) return;

                for(int i = 0; i < N; i++) {
                    for(int j = 0; j < M; j++) {
                        if(Objects.equals(board[i][j],'*')) continue;
                        dfs(i,j,0,remain_block - 1);
                    }
                }

                System.out.printf("Case %d: %d\n",num++,ans == Integer.MAX_VALUE ? -1 : ans);
            }
        }

        private void dfs(int i, int j, int mv, int rb) {
            if(rb == 0) {
                ans = Math.min(ans, mv);
                return;
            }

            if(mv > ans || mv > 1_000_000) return;

            for(int[] d : direction) {
                int ni;
                int nj;
                int w = 1;

                while(true) {
                    ni = i + d[0] * w;
                    nj = j + d[1] * w;

                    if(ni < 0 || ni >= N || nj < 0 || nj >= M || visited[ni][nj]) {
                        w--;
                        break;
                    } else {
                        w++;
                    }
                }

                if(w == 0) continue;

                ni = i + d[0] * w;
                nj = j + d[1] * w;

                for(int k = 0; k <= w; k++) {
                    visited[i + d[0] * k][j + d[1] * k] = true;
                }

                dfs(ni,nj,mv + 1, rb - w);

                for(int k = 0; k <= w; k++) {
                    visited[i + d[0] * k][j + d[1] * k] = false;
                }
            }
        }

        private boolean init_setting() throws IOException {
            String line = br.readLine();

            if(line == null) return true;

            String[] input = line.split(" ");

            N = Integer.parseInt(input[0]);
            M = Integer.parseInt(input[1]);

            board = new char[N][M];
            visited = new boolean[N][M];

            remain_block = 0;

            for(int i = 0; i < N; i++) {
                input = br.readLine().split("");
                for(int j = 0; j < M; j++) {
                    char ch = input[j].charAt(0);
                    if(ch == '.') remain_block++;
                    board[i][j] = ch;
                    visited[i][j] = (ch == '*');
                }
            }
            ans = Integer.MAX_VALUE;

            return false;
        }
    }
}
