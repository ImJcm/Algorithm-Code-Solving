package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
움직이는 미로 탈출

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	14005	4620	2906	28.569%
문제
욱제는 학교 숙제로 크기가 8×8인 체스판에서 탈출하는 게임을 만들었다. 체스판의 모든 칸은 빈 칸 또는 벽 중 하나이다. 욱제의 캐릭터는 가장 왼쪽 아랫 칸에 있고, 이 캐릭터는 가장 오른쪽 윗 칸으로 이동해야 한다.

이 게임의 특징은 벽이 움직인다는 점이다. 1초마다 모든 벽이 아래에 있는 행으로 한 칸씩 내려가고, 가장 아래에 있어서 아래에 행이 없다면 벽이 사라지게 된다. 욱제의 캐릭터는 1초에 인접한 한 칸 또는 대각선 방향으로 인접한 한 칸으로 이동하거나, 현재 위치에 서 있을 수 있다. 이동할 때는 빈 칸으로만 이동할 수 있다.

1초 동안 욱제의 캐릭터가 먼저 이동하고, 그 다음 벽이 이동한다. 벽이 캐릭터가 있는 칸으로 이동하면 더 이상 캐릭터는 이동할 수 없다.

욱제의 캐릭터가 가장 오른쪽 윗 칸으로 이동할 수 있는지 없는지 구해보자.

입력
8개 줄에 걸쳐서 체스판의 상태가 주어진다. '.'은 빈 칸, '#'는 벽이다. 가장 왼쪽 아랫칸은 항상 벽이 아니다.

출력
욱제의 캐릭터가 가장 오른쪽 윗 칸에 도착할 수 있으면 1, 없으면 0을 출력한다.

예제 입력 1
........
........
........
........
........
........
........
........
예제 출력 1
1
예제 입력 2
........
........
........
........
........
........
##......
........
예제 출력 2
0
예제 입력 3
........
........
........
........
........
.#......
#.......
.#......
예제 출력 3
0
예제 입력 4
........
........
........
........
........
.#######
#.......
........
예제 출력 4
1
예제 입력 5
........
........
........
........
#.......
.#######
#.......
........
예제 출력 5
0
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
jdk 11 버전으로는 Pair를 javafx.util.Pair 패키지를 통해 지원하지만, BOJ 제출 시, javafx.util을 지원하지 않는 것으로 컴파일에러가 발생한다.
(jdk8로 제출 시, 정답)

Pair를 대체할 수 있는 방법이 필요하다. -> 기존의 board 클래스 내부에 이동한 횟수를 나타낸 move 변수를 생성하여 이용한다.

코드 로직
1. (7,0)을 시작으로 ↑, ↗, →, ↘, ↓, ↙, ←, ↖, 0(제자리)를 포함한 총 9 방향으로 이동이 가능한지 조건을 검사한다.
2. 이때, 검사되는 조건은 아래와 같다.
2-1. 이동하는 위치에 벽이 존재하는지(wr) + 이동한 위치에 벽이 내려오는지를 검사한다(wr_n).
2-2. 이동하는 위치에서 같은 열에 벽이 존재하는지 검사한다.(wc)
3. 2번 조건에 해당하는 경우, flag 값을 통해 queue에 저장할 다음 위치인지 여부를 검사한다.
4. 기저사례로 이동횟수가 7번이 되면, 도착지점까지 갈 수 있다고 판단한다.

중요한 점: 이 문제는 벽이 이동할 수 있는 최대의 턴수는 7번이라고 생각한다. 그 이후에는 맵상에서 모든 벽은 존재하지 않으므로 7번의 이동하는 동안
이동할 수 있는 구역이 있다면 해당 상황에서는 도착지점까지 갈 수 있다고 판단할 수 있다.
 */
public class BOJ16954 {
    static class BOJ16954_board {
        int r,c;
        int move;

        BOJ16954_board(int r,int c, int m) {
            this.r = r;
            this.c = c;
            this.move = m;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BOJ16954_board[][] boards;
    static ArrayList<BOJ16954_board> walls;
    static boolean[][][] visited;
    static int[][] direction = {{0,0},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        System.out.println(bfs());
    }

    static int bfs() {
        Queue<BOJ16954_board> q = new LinkedList<>();

        q.offer(boards[7][0]);
        visited[7][0][0] = true;

        while(!q.isEmpty()) {
            BOJ16954_board now = q.poll();

            if(now.move == 7 || walls.isEmpty()) {
                return 1;
            }

            for(int[] d : direction) {
                boolean flag = true;
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 0 || nr > 7 || nc < 0 || nc > 7) continue;
                if(visited[nr][nc][now.move + 1]) continue;

                for(BOJ16954_board b : walls) {
                    int wr = b.r + now.move;
                    int wr_n = b.r + now.move + 1;
                    int wc = b.c;
                    if (((wr == nr || wr_n == nr) && wc == nc)) {
                        flag = false;
                        break;
                    }
                }

                if(flag) {
                    visited[nr][nc][now.move + 1] = true;
                    q.offer(new BOJ16954_board(nr, nc,now.move + 1));
                }
            }
        }

        return 0;
    }

    static void init_setting() throws IOException {
        boards = new BOJ16954_board[8][8];
        visited = new boolean[8][8][8];
        walls = new ArrayList<>();

        for(int i=0;i<8;i++) {
            String[] input = br.readLine().split("");
            for(int j=0;j<8;j++) {
                if(input[j].equals("#")) {
                    boards[i][j] = new BOJ16954_board(i,j,0);
                    visited[i][j][0] = true;
                    walls.add(boards[i][j]);
                } else {
                    boards[i][j] = new BOJ16954_board(i,j,0);
                    visited[i][j][0] = false;
                }
            }
        }

    }
}
