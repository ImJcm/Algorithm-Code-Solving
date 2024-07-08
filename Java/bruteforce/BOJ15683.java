package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
감시

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	52975	25504	15586	44.984%
문제
스타트링크의 사무실은 1×1크기의 정사각형으로 나누어져 있는 N×M 크기의 직사각형으로 나타낼 수 있다. 사무실에는 총 K개의 CCTV가 설치되어져 있는데, CCTV는 5가지 종류가 있다. 각 CCTV가 감시할 수 있는 방법은 다음과 같다.


1번	2번	3번	4번	5번
1번 CCTV는 한 쪽 방향만 감시할 수 있다. 2번과 3번은 두 방향을 감시할 수 있는데, 2번은 감시하는 방향이 서로 반대방향이어야 하고, 3번은 직각 방향이어야 한다. 4번은 세 방향, 5번은 네 방향을 감시할 수 있다.

CCTV는 감시할 수 있는 방향에 있는 칸 전체를 감시할 수 있다. 사무실에는 벽이 있는데, CCTV는 벽을 통과할 수 없다. CCTV가 감시할 수 없는 영역은 사각지대라고 한다.

CCTV는 회전시킬 수 있는데, 회전은 항상 90도 방향으로 해야 하며, 감시하려고 하는 방향이 가로 또는 세로 방향이어야 한다.

0 0 0 0 0 0
0 0 0 0 0 0
0 0 1 0 6 0
0 0 0 0 0 0
지도에서 0은 빈 칸, 6은 벽, 1~5는 CCTV의 번호이다. 위의 예시에서 1번의 방향에 따라 감시할 수 있는 영역을 '#'로 나타내면 아래와 같다.

0 0 0 0 0 0
0 0 0 0 0 0
0 0 1 # 6 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
# # 1 0 6 0
0 0 0 0 0 0
0 0 # 0 0 0
0 0 # 0 0 0
0 0 1 0 6 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 1 0 6 0
0 0 # 0 0 0
→	←	↑	↓
CCTV는 벽을 통과할 수 없기 때문에, 1번이 → 방향을 감시하고 있을 때는 6의 오른쪽에 있는 칸을 감시할 수 없다.

0 0 0 0 0 0
0 2 0 0 0 0
0 0 0 0 6 0
0 6 0 0 2 0
0 0 0 0 0 0
0 0 0 0 0 5
위의 예시에서 감시할 수 있는 방향을 알아보면 아래와 같다.

0 0 0 0 0 #
# 2 # # # #
0 0 0 0 6 #
0 6 # # 2 #
0 0 0 0 0 #
# # # # # 5
0 0 0 0 0 #
# 2 # # # #
0 0 0 0 6 #
0 6 0 0 2 #
0 0 0 0 # #
# # # # # 5
0 # 0 0 0 #
0 2 0 0 0 #
0 # 0 0 6 #
0 6 # # 2 #
0 0 0 0 0 #
# # # # # 5
0 # 0 0 0 #
0 2 0 0 0 #
0 # 0 0 6 #
0 6 0 0 2 #
0 0 0 0 # #
# # # # # 5
왼쪽 상단 2: ↔, 오른쪽 하단 2: ↔	왼쪽 상단 2: ↔, 오른쪽 하단 2: ↕	왼쪽 상단 2: ↕, 오른쪽 하단 2: ↔	왼쪽 상단 2: ↕, 오른쪽 하단 2: ↕
CCTV는 CCTV를 통과할 수 있다. 아래 예시를 보자.

0 0 2 0 3
0 6 0 0 0
0 0 6 6 0
0 0 0 0 0
위와 같은 경우에 2의 방향이 ↕ 3의 방향이 ←와 ↓인 경우 감시받는 영역은 다음과 같다.

# # 2 # 3
0 6 # 0 #
0 0 6 6 #
0 0 0 0 #
사무실의 크기와 상태, 그리고 CCTV의 정보가 주어졌을 때, CCTV의 방향을 적절히 정해서, 사각 지대의 최소 크기를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 사무실의 세로 크기 N과 가로 크기 M이 주어진다. (1 ≤ N, M ≤ 8)

둘째 줄부터 N개의 줄에는 사무실 각 칸의 정보가 주어진다. 0은 빈 칸, 6은 벽, 1~5는 CCTV를 나타내고, 문제에서 설명한 CCTV의 종류이다.

CCTV의 최대 개수는 8개를 넘지 않는다.

출력
첫째 줄에 사각 지대의 최소 크기를 출력한다.

예제 입력 1
4 6
0 0 0 0 0 0
0 0 0 0 0 0
0 0 1 0 6 0
0 0 0 0 0 0
예제 출력 1
20
예제 입력 2
6 6
0 0 0 0 0 0
0 2 0 0 0 0
0 0 0 0 6 0
0 6 0 0 2 0
0 0 0 0 0 0
0 0 0 0 0 5
예제 출력 2
15
예제 입력 3
6 6
1 0 0 0 0 0
0 1 0 0 0 0
0 0 1 0 0 0
0 0 0 1 0 0
0 0 0 0 1 0
0 0 0 0 0 1
예제 출력 3
6
예제 입력 4
6 6
1 0 0 0 0 0
0 1 0 0 0 0
0 0 1 5 0 0
0 0 5 1 0 0
0 0 0 0 1 0
0 0 0 0 0 1
예제 출력 4
2
예제 입력 5
1 7
0 1 2 3 4 5 6
예제 출력 5
0
예제 입력 6
3 7
4 0 0 0 0 0 0
0 0 0 2 0 0 0
0 0 0 0 0 0 4
예제 출력 6
0
출처
문제를 만든 사람: baekjoon
알고리즘 분류
구현
브루트포스 알고리즘
시뮬레이션
백트래킹
그림은 문제 링크 참고 - https://www.acmicpc.net/problem/15683
 */
/*
알고리즘 핵심
1. 입력을 주어지는 cctv는 모두 사용되고, 방향을 바꾸어 사각지대를 찾는 것이기 때문에 cctv마다 방향을 바꾸는 형태로 dfs 재귀 호출한다.
2. dfs의 기저사례는 모든 cctv의 방향이 지정되었을 때이므로, 사용된 cctv 개수가 기저사례이다.
3. 각 cctv 종류마다 가능한 방향이 다르므로 가능한 방향을 구성하는 것
4. 방향이 정해진 cctv에서 보여지는 공간을 체크하는 과정에서 isFront 값 여부에 따라 정방향, 반대방향을 체크할 수 있도록 한다.
5. cctv가 보여지는 방향으로 공간을 이동하면서 중간에 벽이 존재하거나 사무실 공간을 넘어가는 경우 탐색을 멈춘다.
 */
public class BOJ15683 {
    static class BOJ15683_cctv {
        int n,m;        // 위치
        int k;          // cctv 종류
        int d;          // 현재 cctv 방향
        ArrayList<Integer> ds;   // 가능한 cctv 방향

        BOJ15683_cctv(int n, int m, int k) {
            this.n = n;
            this.m = m;
            this.k = k;
            this.d = 0;     // default 방향
            this.ds = new ArrayList<>();

            direction(k);
        }

        void direction(int k) {
            switch (k) {
                case 0:
                    // 빈공간
                    break;
                case 1,3,4:
                    for(int i=0;i<4;i++) {
                        this.ds.add(i);
                    }
                    break;
                case 2:
                    for(int i=0;i<2;i++) {
                        this.ds.add(i);
                    }
                    break;
                case 5:
                    this.ds.add(0);
                    break;
            }
        }


    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,cctv_cnt,blind_spots;
    static char[][] office;
    static BOJ15683_cctv[] cctvs;
    static int[][] directions = {{-1,0},{0,1},{1,0},{0,-1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        dfs(0);

        System.out.println(blind_spots);
    }

    static void dfs(int depth) {
        if(depth == cctv_cnt) {
            check_blind_spot();
            return;
        }

        for(int i=0;i<cctvs[depth].ds.size();i++) {
            cctvs[depth].d = cctvs[depth].ds.get(i);
            dfs(depth + 1);
        }
    }

    static void check_blind_spot() {
        int blinds = 0;

        char[][] new_office = copy_office(office);
        for(int i=0;i<cctv_cnt;i++) {
            BOJ15683_cctv cctv = cctvs[i];

            int n = cctv.n;
            int m = cctv.m;

            int d_0 = cctv.d;
            int d_90_r = cctv.d + 1 > 3 ? 0 : cctv.d + 1;   // d를 기준으로 90도 시계방향 회전한 방향

            switch (cctv.k) {
                case 1:
                    on_cctv_view(new_office, n, m, d_0, true);
                    break;
                case 2:
                    on_cctv_view(new_office, n, m, d_0, true);
                    on_cctv_view(new_office, n, m, d_0, false);
                    break;
                case 3:

                    on_cctv_view(new_office, n, m, d_0, true);
                    on_cctv_view(new_office, n, m, d_90_r, true);
                    break;
                case 4:
                    on_cctv_view(new_office, n, m, d_0, true);
                    on_cctv_view(new_office, n, m, d_90_r, true);
                    on_cctv_view(new_office, n, m, d_90_r, false);
                    break;
                case 5:
                    on_cctv_view(new_office, n, m, d_0, true);
                    on_cctv_view(new_office, n, m, d_0, false);
                    on_cctv_view(new_office, n, m, d_90_r, true);
                    on_cctv_view(new_office, n, m, d_90_r, false);
                    break;
            }
        }

        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                if(new_office[i][j] == '0') {
                    blinds++;
                }
            }
        }

        blind_spots = Math.min(blind_spots, blinds);
    }

    static void on_cctv_view(char[][] office, int n, int m, int d, boolean isfront) {
        if(isfront) {
            while(true) {
                n += directions[d][0];
                m += directions[d][1];

                if(n < 0 || n >= N || m < 0 || m >= M) break;
                if(office[n][m] == '6') break;
                if(office[n][m] == '0') {
                    office[n][m] = '#';
                }
            }
        } else {
            while(true) {
                n -= directions[d][0];
                m -= directions[d][1];

                if(n < 0 || n >= N || m < 0 || m >= M) break;
                if(office[n][m] == '6') break;
                if(office[n][m] == '0') {
                    office[n][m] = '#';
                }
            }
        }

    }

    static char[][] copy_office(char[][] office) {
        char[][] new_arr = new char[office.length][office[0].length];

        for(int i=0;i<office.length;i++) {
            for(int j=0;j<office[0].length;j++) {
                new_arr[i][j] = office[i][j];
            }
        }
        return new_arr;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        office = new char[N][M];
        cctvs = new BOJ15683_cctv[8];

        cctv_cnt = 0;
        blind_spots = Integer.MAX_VALUE;

        for (int i=0;i<N;i++) {
            input = br.readLine().split(" ");
            for(int j=0;j<M;j++) {
                office[i][j] = input[j].charAt(0);

                if('1' <= office[i][j] && office[i][j] <= '5') {
                    cctvs[cctv_cnt++] = new BOJ15683_cctv(i,j,Character.getNumericValue(office[i][j]));
                }
            }
        }
    }
}
