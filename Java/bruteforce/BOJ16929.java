package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/*
Two Dots

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	10251	4784	3061	43.760%
문제
Two Dots는 Playdots, Inc.에서 만든 게임이다. 게임의 기초 단계는 크기가 N×M인 게임판 위에서 진행된다.



각각의 칸은 색이 칠해진 공이 하나씩 있다. 이 게임의 핵심은 같은 색으로 이루어진 사이클을 찾는 것이다.

다음은 위의 게임판에서 만들 수 있는 사이클의 예시이다.


점 k개 d1, d2, ..., dk로 이루어진 사이클의 정의는 아래와 같다.

모든 k개의 점은 서로 다르다.
k는 4보다 크거나 같다.
모든 점의 색은 같다.
모든 1 ≤ i ≤ k-1에 대해서, di와 di+1은 인접하다. 또, dk와 d1도 인접해야 한다. 두 점이 인접하다는 것은 각각의 점이 들어있는 칸이 변을 공유한다는 의미이다.
게임판의 상태가 주어졌을 때, 사이클이 존재하는지 아닌지 구해보자.

입력
첫째 줄에 게임판의 크기 N, M이 주어진다. 둘째 줄부터 N개의 줄에 게임판의 상태가 주어진다. 게임판은 모두 점으로 가득차 있고, 게임판의 상태는 점의 색을 의미한다. 점의 색은 알파벳 대문자 한 글자이다.

출력
사이클이 존재하는 경우에는 "Yes", 없는 경우에는 "No"를 출력한다.

제한
2 ≤ N, M ≤ 50
예제 입력 1
3 4
AAAA
ABCA
AAAA
예제 출력 1
Yes
예제 입력 2
3 4
AAAA
ABCA
AADA
예제 출력 2
No
예제 입력 3
4 4
YYYR
BYBY
BBBY
BBBY
예제 출력 3
Yes
예제 입력 4
7 6
AAAAAB
ABBBAB
ABAAAB
ABABBB
ABAAAB
ABBBAB
AAAAAB
예제 출력 4
Yes
예제 입력 5
2 13
ABCDEFGHIJKLM
NOPQRSTUVWXYZ
예제 출력 5
No
출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: jh05013
알고리즘 분류
그래프 이론
그래프 탐색
깊이 우선 탐색
 */
/*
    그래프 이론을 이용하여 x,y 좌표로 위치 설정 + 인접 노드를 adj로 설정한다. ( 인접노드 추가 시, color가 같은 노드만 추가한다. )

    사이클 확인 방법
    1. 처음 시작 지점을 기준으로 동일한 색상의 점들을 지나면서 처음 지점으로 돌아올 경우 = 사이클로 판단
    2. 각 지점을 시작으로 사이클 여부를 확인하면서 가장 가까운 사이클이 존재하는지 검사하는 방법

    1번 방법의 알고리즘의 경우 2번 방법에 비해 수행되는 코드가 더 많을 것으로 생각한다.

    아래와 같이 예시가 존재할 때, 방법1의 과정은 A1 형태로 사이클을 수행하지만, 방법2의 과정은 A2와 같이 중간에 사이클이 존재하는 경우를 검사
    한다. 따라서, A1 방법과 비교했을 때 좀더 낫다고 판단했다.
                    A1          A2-1  or A2-2
    AAAA            ***x        ****     *x**
    ABAA            *x**        xx**     *x**
    ACDA            *xx*        xxxx     *xx*
    AAAA            ****        xxxx     ****

 */
class BOJ16929_Point {
    int x;
    int y;
    char color;
    ArrayList<BOJ16929_Point> adj;

    BOJ16929_Point(int x, int y, char c) {
        this.x = x;
        this.y = y;
        this.color = c;
    }
}
public class BOJ16929 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BOJ16929_Point[][] board;
    static boolean[][] visited;
    static int N,M;
    static boolean flag = false, check = false;

    public static void main(String[] args) throws IOException {
        init_setting();

        for(int i=0;i<N && !flag;i++) {
            for(int j=0;j<M && !flag;j++) {
                visited[i][j] = true;
                solve(1,board[i][j],board[i][j]);
                visited[i][j] = false;
            }
        }

        if(flag) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

    }

    static void solve(int depth, BOJ16929_Point pre_point, BOJ16929_Point next_point) {
        if(depth > 4 && check && pre_point != next_point) {
            flag = true;
            return;
        }

        /*
           flag를 통한 가지치기 <- 해당 코드가 없다면 시간초과 발생
           이미 사이클이 존재한 시점부터 flag = true로 설정하여 추가로 사이클 여부를 확인하는 DFS를 가지치기 수행하여 수행시간을
           단축시킨다.
         */
        if(flag) {
            return;
        }

        for(BOJ16929_Point n_p : next_point.adj) {
            if(n_p.equals(pre_point)) {
                continue;
            }
            if(!visited[n_p.x][n_p.y]) {
                visited[n_p.x][n_p.y] = true;
            } else {
                check = true;
            }
            solve(depth + 1, next_point, n_p);
            visited[n_p.x][n_p.y] = false;
            check = false;
        }
    }

    static void init_setting() throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        M = Integer.parseInt(tmp[1]);

        visited = new boolean[N][M];
        board = new BOJ16929_Point[N][M];

        for(int i=0;i<N;i++) {
            String input_line = br.readLine();
            for(int j=0;j<input_line.length();j++) {
                visited[i][j] = false;
                board[i][j] = new BOJ16929_Point(i,j,input_line.charAt(j));
                board[i][j].adj = new ArrayList<>();
            }
        }

        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                if(i + 1 < N && board[i][j].color == board[i + 1][j].color) {
                    board[i][j].adj.add(board[i + 1][j]);
                    board[i + 1][j].adj.add(board[i][j]);
                }

                if(j + 1 < M && board[i][j].color == board[i][j + 1].color) {
                    board[i][j].adj.add(board[i][j+1]);
                    board[i][j+1].adj.add(board[i][j]);
                }
            }
        }
    }
}
