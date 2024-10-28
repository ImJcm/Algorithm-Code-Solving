package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
열쇠 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	23352	7004	4828	27.394%
문제
상근이는 1층 빌딩에 침입해 매우 중요한 문서를 훔쳐오려고 한다. 상근이가 가지고 있는 평면도에는 문서의 위치가 모두 나타나 있다. 빌딩의 문은 모두 잠겨있기 때문에, 문을 열려면 열쇠가 필요하다. 상근이는 일부 열쇠를 이미 가지고 있고, 일부 열쇠는 빌딩의 바닥에 놓여져 있다. 상근이는 상하좌우로만 이동할 수 있다.

상근이가 훔칠 수 있는 문서의 최대 개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 테스트 케이스의 개수가 주어진다. 테스트 케이스의 수는 100개를 넘지 않는다.

각 테스트 케이스의 첫째 줄에는 지도의 높이와 너비 h와 w (2 ≤ h, w ≤ 100)가 주어진다. 다음 h개 줄에는 빌딩을 나타내는 w개의 문자가 주어지며, 각 문자는 다음 중 하나이다.

'.'는 빈 공간을 나타낸다.
'*'는 벽을 나타내며, 상근이는 벽을 통과할 수 없다.
'$'는 상근이가 훔쳐야하는 문서이다.
알파벳 대문자는 문을 나타낸다.
알파벳 소문자는 열쇠를 나타내며, 그 문자의 대문자인 모든 문을 열 수 있다.
마지막 줄에는 상근이가 이미 가지고 있는 열쇠가 공백없이 주어진다. 만약, 열쇠를 하나도 가지고 있지 않는 경우에는 "0"이 주어진다.

상근이는 처음에는 빌딩의 밖에 있으며, 빌딩 가장자리의 벽이 아닌 곳을 통해 빌딩 안팎을 드나들 수 있다. 각각의 문에 대해서, 그 문을 열 수 있는 열쇠의 개수는 0개, 1개, 또는 그 이상이고, 각각의 열쇠에 대해서, 그 열쇠로 열 수 있는 문의 개수도 0개, 1개, 또는 그 이상이다. 열쇠는 여러 번 사용할 수 있다.

출력
각 테스트 케이스 마다, 상근이가 훔칠 수 있는 문서의 최대 개수를 출력한다.

예제 입력 1
3
5 17
*****************
.............**$*
*B*A*P*C**X*Y*.X.
*y*x*a*p**$*$**$*
*****************
cz
5 11
*.*********
*...*...*x*
*X*.*.*.*.*
*$*...*...*
***********
0
7 7
*ABCDE*
X.....F
W.$$$.G
V.$$$.H
U.$$$.J
T.....K
*SQPML*
irony
예제 출력 1
3
1
0
출처


ICPC > Regionals > Europe > Northwestern European Regional Contest > Benelux Algorithm Programming Contest > BAPC 2013 Preliminaries K번

문제를 번역한 사람: baekjoon
데이터를 추가한 사람: bamgoesn
문제의 오타를 찾은 사람: goooora, na982, vanila_banana
알고리즘 분류
구현
그래프 이론
그래프 탐색
너비 우선 탐색
 */
public class BOJ9328 {
    static class BOJ9328_pos {
        int h,w;

        BOJ9328_pos(int h, int w) {
            this.h = h;
            this.w = w;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T,H,W,ans;
    static boolean re_flag;
    static HashSet<Character> keys;
    static char[][] map;
    static int[][] ds = {{-1,0},{1,0},{0,-1},{0,1}};

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for(int t = 0; t < T; t++) {
            init_setting();
            solve();
        }
    }

    private static void solve() {
        re_flag = false;
        ans = 0;

        while(!re_flag) {
            bfs(new BOJ9328_pos(0,0));
        }

        System.out.println(ans);
    }

    private static void bfs(BOJ9328_pos s) {
        Queue<BOJ9328_pos> q = new LinkedList<>();
        boolean[][] visited = new boolean[H + 2][W + 2];

        re_flag = true;
        ans = 0;

        q.add(s);
        visited[s.h][s.w] = true;

        while(!q.isEmpty()) {
            BOJ9328_pos now = q.poll();

            if(map[now.h][now.w] == '$') {
                ans++;
            }

            for(int[] d : ds) {
                int nh = now.h + d[0];
                int nw = now.w + d[1];

                if(nh < 0 || nh > H + 1 || nw < 0 || nw > W + 1) continue;
                if(visited_check(nh,nw,visited)) continue;
                if((65 <= map[nh][nw] && map[nh][nw] <= 90) && !keys.contains(Character.toLowerCase(map[nh][nw]))) continue;

                if((97 <= map[nh][nw] && map[nh][nw] <= 122) && !keys.contains(map[nh][nw])) {
                    keys.add(map[nh][nw]);
                    re_flag = false;
                }

                visited[nh][nw] = true;
                q.add(new BOJ9328_pos(nh,nw));
            }
        }
    }

    private static boolean visited_check(int h, int w, boolean[][] v) {
        if(map[h][w] == '*' || v[h][w]) return true;
        else return false;
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        H = Integer.parseInt(input[0]);
        W = Integer.parseInt(input[1]);

        map = new char[H + 2][W + 2];
        keys = new HashSet<>();

        for(int h = 0; h <= H + 1; h++) {
            if(!(h == 0 || h == H + 1)) input = br.readLine().split("");
            for(int w = 0; w <= W + 1; w++) {
                if(h == 0 || h == H + 1 || w == 0 || w == W + 1) {
                    map[h][w] = '.';
                } else {
                    map[h][w] = input[w - 1].charAt(0);
                }
            }
        }

        input = br.readLine().split("");

        for(int i = 0; i < input.length; i++) {
            keys.add(input[i].charAt(0));
        }
    }
}
