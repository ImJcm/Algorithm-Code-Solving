package Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
미세먼지 안녕!

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	40510	22716	15393	55.204%
문제
미세먼지를 제거하기 위해 구사과는 공기청정기를 설치하려고 한다. 공기청정기의 성능을 테스트하기 위해 구사과는 집을 크기가 R×C인 격자판으로 나타냈고, 1×1 크기의 칸으로 나눴다. 구사과는 뛰어난 코딩 실력을 이용해 각 칸 (r, c)에 있는 미세먼지의 양을 실시간으로 모니터링하는 시스템을 개발했다. (r, c)는 r행 c열을 의미한다.



공기청정기는 항상 1번 열에 설치되어 있고, 크기는 두 행을 차지한다. 공기청정기가 설치되어 있지 않은 칸에는 미세먼지가 있고, (r, c)에 있는 미세먼지의 양은 Ar,c이다.

1초 동안 아래 적힌 일이 순서대로 일어난다.

미세먼지가 확산된다. 확산은 미세먼지가 있는 모든 칸에서 동시에 일어난다.
(r, c)에 있는 미세먼지는 인접한 네 방향으로 확산된다.
인접한 방향에 공기청정기가 있거나, 칸이 없으면 그 방향으로는 확산이 일어나지 않는다.
확산되는 양은 Ar,c/5이고 소수점은 버린다. 즉, ⌊Ar,c/5⌋이다.
(r, c)에 남은 미세먼지의 양은 Ar,c - ⌊Ar,c/5⌋×(확산된 방향의 개수) 이다.
공기청정기가 작동한다.
공기청정기에서는 바람이 나온다.
위쪽 공기청정기의 바람은 반시계방향으로 순환하고, 아래쪽 공기청정기의 바람은 시계방향으로 순환한다.
바람이 불면 미세먼지가 바람의 방향대로 모두 한 칸씩 이동한다.
공기청정기에서 부는 바람은 미세먼지가 없는 바람이고, 공기청정기로 들어간 미세먼지는 모두 정화된다.
다음은 확산의 예시이다.



왼쪽과 위쪽에 칸이 없기 때문에, 두 방향으로만 확산이 일어났다.



인접한 네 방향으로 모두 확산이 일어난다.



공기청정기가 있는 칸으로는 확산이 일어나지 않는다.

공기청정기의 바람은 다음과 같은 방향으로 순환한다.



방의 정보가 주어졌을 때, T초가 지난 후 구사과의 방에 남아있는 미세먼지의 양을 구해보자.

입력
첫째 줄에 R, C, T (6 ≤ R, C ≤ 50, 1 ≤ T ≤ 1,000) 가 주어진다.

둘째 줄부터 R개의 줄에 Ar,c (-1 ≤ Ar,c ≤ 1,000)가 주어진다. 공기청정기가 설치된 곳은 Ar,c가 -1이고, 나머지 값은 미세먼지의 양이다. -1은 2번 위아래로 붙어져 있고, 가장 윗 행, 아랫 행과 두 칸이상 떨어져 있다.

출력
첫째 줄에 T초가 지난 후 구사과 방에 남아있는 미세먼지의 양을 출력한다.

예제 입력 1
7 8 1
0 0 0 0 0 0 0 9
0 0 0 0 3 0 0 8
-1 0 5 0 0 0 22 0
-1 8 0 0 0 0 0 0
0 0 0 0 0 10 43 0
0 0 5 0 15 0 0 0
0 0 40 0 0 0 20 0
예제 출력 1
188
미세먼지의 확산이 일어나면 다음과 같은 상태가 된다.
공기청정기가 작동한 이후 상태는 아래와 같다.

https://www.acmicpc.net/problem/17144 - 먼지 확산 및 공기청정기의 작동방식 그림 참고

예제 입력 2
7 8 2
0 0 0 0 0 0 0 9
0 0 0 0 3 0 0 8
-1 0 5 0 0 0 22 0
-1 8 0 0 0 0 0 0
0 0 0 0 0 10 43 0
0 0 5 0 15 0 0 0
0 0 40 0 0 0 20 0
예제 출력 2
188
예제 입력 3
7 8 3
0 0 0 0 0 0 0 9
0 0 0 0 3 0 0 8
-1 0 5 0 0 0 22 0
-1 8 0 0 0 0 0 0
0 0 0 0 0 10 43 0
0 0 5 0 15 0 0 0
0 0 40 0 0 0 20 0
예제 출력 3
186
예제 입력 4
7 8 4
0 0 0 0 0 0 0 9
0 0 0 0 3 0 0 8
-1 0 5 0 0 0 22 0
-1 8 0 0 0 0 0 0
0 0 0 0 0 10 43 0
0 0 5 0 15 0 0 0
0 0 40 0 0 0 20 0
예제 출력 4
178
예제 입력 5
7 8 5
0 0 0 0 0 0 0 9
0 0 0 0 3 0 0 8
-1 0 5 0 0 0 22 0
-1 8 0 0 0 0 0 0
0 0 0 0 0 10 43 0
0 0 5 0 15 0 0 0
0 0 40 0 0 0 20 0
예제 출력 5
172
예제 입력 6
7 8 20
0 0 0 0 0 0 0 9
0 0 0 0 3 0 0 8
-1 0 5 0 0 0 22 0
-1 8 0 0 0 0 0 0
0 0 0 0 0 10 43 0
0 0 5 0 15 0 0 0
0 0 40 0 0 0 20 0
예제 출력 6
71
예제 입력 7
7 8 30
0 0 0 0 0 0 0 9
0 0 0 0 3 0 0 8
-1 0 5 0 0 0 22 0
-1 8 0 0 0 0 0 0
0 0 0 0 0 10 43 0
0 0 5 0 15 0 0 0
0 0 40 0 0 0 20 0
예제 출력 7
52
예제 입력 8
7 8 50
0 0 0 0 0 0 0 9
0 0 0 0 3 0 0 8
-1 0 5 0 0 0 22 0
-1 8 0 0 0 0 0 0
0 0 0 0 0 10 43 0
0 0 5 0 15 0 0 0
0 0 40 0 0 0 20 0
예제 출력 8
46
출처
문제를 만든 사람: baekjoon
문제의 오타를 찾은 사람: dbfldkfdbgml, jh05013, tmdals6718
알고리즘 분류
구현
시뮬레이션
 */
/*
1. 먼지 확산 알고리즘 = spread_dust()
    -> 상하좌우 네 방향으로 먼지 확산하는 알고리즘
2. 공기청정기 알고리즘 = on_air_cleaner();
    -> 위, 아래 공기청정기의 바람 방향에 따라 먼지를 이동시키는 알고리즘
3. 1,2 과정을 T초 만큼 반복 = solve()
 */
public class BOJ17144 {
    static class BOJ17144_room {
        int r,c;
        int dusts;
        int spread_dusts;

        BOJ17144_room(int r, int c, int dusts, int s_dusts) {
            this.r = r;
            this.c = c;
            this.dusts = dusts;
            this.spread_dusts = s_dusts;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int R,C,T;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};
    static BOJ17144_room air_cleaner_top, air_cleaner_bottom;
    static BOJ17144_room[][] rooms;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        while(T-- > 0) {
            // 미세먼지 학산
            spreading_dust();
            on_air_cleaner();
        }

        System.out.println(check_dusts());
    }

    static void spreading_dust() {
        for(int r=1;r<=R;r++) {
            for(int c=1;c<=C;c++) {
                if(rooms[r][c].dusts == 0 || rooms[r][c].dusts == -1) continue;

                int s_d = rooms[r][c].dusts / 5;
                for(int[] d : direction) {
                    int nr = r + d[0];
                    int nc = c + d[1];

                    if(nr < 1 || nr > R || nc < 1 || nc > C) continue;
                    if((nr == air_cleaner_top.r && nc == air_cleaner_top.c) ||
                            (nr == air_cleaner_bottom.r && nc == air_cleaner_bottom.c)) continue;
                    rooms[nr][nc].spread_dusts += s_d;
                    rooms[r][c].dusts -= s_d;
                }
            }
        }

        for(int r=1;r<=R;r++) {
            for(int c=1;c<=C;c++) {
                rooms[r][c].dusts += rooms[r][c].spread_dusts;
                rooms[r][c].spread_dusts = 0;
            }
        }
    }

    static void on_air_cleaner() {
        //top_air_cleaner
        // ↓
        for(int r1=air_cleaner_top.r-1;r1>0;r1--) {
            if(r1 == 1) {
                rooms[r1][air_cleaner_top.c].dusts = rooms[r1][air_cleaner_top.c+1].dusts;
            } else {
                rooms[r1][air_cleaner_top.c].dusts = rooms[r1-1][air_cleaner_top.c].dusts;
            }
        }

        // ←
        for(int c1=air_cleaner_top.c+1;c1<=C;c1++) {
            if(c1 == C) {
                rooms[1][c1].dusts = rooms[2][c1].dusts;
            } else {
                rooms[1][c1].dusts = rooms[1][c1+1].dusts;
            }
        }

        // ↑
        for(int r1=2;r1<=air_cleaner_top.r;r1++) {
            if(r1 == air_cleaner_top.r) {
                rooms[r1][C].dusts = rooms[r1][C-1].dusts;
            } else {
                rooms[r1][C].dusts = rooms[r1+1][C].dusts;
            }
        }

        // →
        for(int c1=C-1;c1>air_cleaner_top.c;c1--) {
            if(c1 == air_cleaner_top.c + 1) {
                rooms[air_cleaner_top.r][c1].dusts = 0;
            } else {
                rooms[air_cleaner_top.r][c1].dusts = rooms[air_cleaner_top.r][c1-1].dusts;
            }
        }

        //bottom_air_cleaner
        // ↑
        for(int r1=air_cleaner_bottom.r+1;r1<=R;r1++) {
            if(r1 == R) {
                rooms[r1][air_cleaner_bottom.c].dusts = rooms[r1][air_cleaner_bottom.c+1].dusts;
            } else {
                rooms[r1][air_cleaner_bottom.c].dusts = rooms[r1+1][air_cleaner_bottom.c].dusts;
            }
        }

        // ←
        for(int c1=air_cleaner_bottom.c+1;c1<=C;c1++) {
            if(c1 == C) {
                rooms[R][c1].dusts = rooms[R-1][c1].dusts;
            } else {
                rooms[R][c1].dusts = rooms[R][c1+1].dusts;
            }
        }

        // ↓
        for(int r1=R-1;r1>=air_cleaner_bottom.r;r1--) {
            if(r1 == air_cleaner_bottom.r) {
                rooms[r1][C].dusts = rooms[r1][C-1].dusts;
            } else {
                rooms[r1][C].dusts = rooms[r1-1][C].dusts;
            }
        }

        // →
        for(int c1=C-1;c1>air_cleaner_bottom.c;c1--) {
            if(c1 == air_cleaner_bottom.c + 1) {
                rooms[air_cleaner_bottom.r][c1].dusts = 0;
            } else {
                rooms[air_cleaner_bottom.r][c1].dusts = rooms[air_cleaner_bottom.r][c1-1].dusts;
            }
        }
    }

    static int check_dusts() {
        int sum = 0;
        for(int r=1;r<=R;r++) {
            for(int c=1;c<=C;c++) {
                if((r == air_cleaner_top.r && c == air_cleaner_top.c) ||
                        (r == air_cleaner_bottom.r && c == air_cleaner_bottom.c)) continue;
                sum += rooms[r][c].dusts;
            }
        }

        return sum;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        R = Integer.parseInt(input[0]);
        C = Integer.parseInt(input[1]);
        T = Integer.parseInt(input[2]);

        rooms = new BOJ17144_room[R+1][C+1];

        boolean air_cleaner = true;

        for(int r=1;r<=R;r++) {
            input = br.readLine().split(" ");
            for(int c=1;c<=C;c++) {
                int data = Integer.parseInt(input[c-1]);
                if(data == -1 && air_cleaner) {
                    air_cleaner_top = new BOJ17144_room(r,c,data,0);
                    air_cleaner_bottom = new BOJ17144_room(r+1,c,data,0);
                    air_cleaner = false;
                }
                rooms[r][c] = new BOJ17144_room(r,c,data,0);
            }
        }
    }
}
