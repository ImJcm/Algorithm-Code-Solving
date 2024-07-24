package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
파이프 옮기기 2 성공

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (추가 시간 없음)	512 MB	6276	3562	2916	59.571%
문제
유현이가 새 집으로 이사했다. 새 집의 크기는 N×N의 격자판으로 나타낼 수 있고, 1×1크기의 정사각형 칸으로 나누어져 있다. 각각의 칸은 (r, c)로 나타낼 수 있다. 여기서 r은 행의 번호, c는 열의 번호이고, 행과 열의 번호는 1부터 시작한다. 각각의 칸은 빈 칸이거나 벽이다.

오늘은 집 수리를 위해서 파이프 하나를 옮기려고 한다. 파이프는 아래와 같은 형태이고, 2개의 연속된 칸을 차지하는 크기이다.



파이프는 회전시킬 수 있으며, 아래와 같이 3가지 방향이 가능하다.



파이프는 매우 무겁기 때문에, 유현이는 파이프를 밀어서 이동시키려고 한다. 벽에는 새로운 벽지를 발랐기 때문에, 파이프가 벽을 긁으면 안 된다. 즉, 파이프는 항상 빈 칸만 차지해야 한다.

파이프를 밀 수 있는 방향은 총 3가지가 있으며, →, ↘, ↓ 방향이다. 파이프는 밀면서 회전시킬 수 있다. 회전은 45도만 회전시킬 수 있으며, 미는 방향은 오른쪽, 아래, 또는 오른쪽 아래 대각선 방향이어야 한다.

파이프가 가로로 놓여진 경우에 가능한 이동 방법은 총 2가지, 세로로 놓여진 경우에는 2가지, 대각선 방향으로 놓여진 경우에는 3가지가 있다.

아래 그림은 파이프가 놓여진 방향에 따라서 이동할 수 있는 방법을 모두 나타낸 것이고, 꼭 빈 칸이어야 하는 곳은 색으로 표시되어져 있다.



가로



세로



대각선

가장 처음에 파이프는 (1, 1)와 (1, 2)를 차지하고 있고, 방향은 가로이다. 파이프의 한쪽 끝을 (N, N)로 이동시키는 방법의 개수를 구해보자.

입력
첫째 줄에 집의 크기 N(3 ≤ N ≤ 32)이 주어진다. 둘째 줄부터 N개의 줄에는 집의 상태가 주어진다. 빈 칸은 0, 벽은 1로 주어진다. (1, 1)과 (1, 2)는 항상 빈 칸이다.

출력
첫째 줄에 파이프의 한쪽 끝을 (N, N)으로 이동시키는 방법의 수를 출력한다. 이동시킬 수 없는 경우에는 0을 출력한다.

예제 입력 1
3
0 0 0
0 0 0
0 0 0
예제 출력 1
1
예제 입력 2
4
0 0 0 0
0 0 0 0
0 0 0 0
0 0 0 0
예제 출력 2
3
예제 입력 3
5
0 0 1 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
예제 출력 3
0
예제 입력 4
6
0 0 0 0 0 0
0 1 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
예제 출력 4
13
예제 입력 5
22
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
예제 출력 5
4345413252
출처
문제를 만든 사람: baekjoon
알고리즘 분류
다이나믹 프로그래밍
 */
/*
알고리즘 핵심
1. BOJ17070과 동일
2. 2 <= N <= 32로 늘어난 만큼, 메모리제이션에 저장할 경우의 수가 커질 수 있으므로, int -> long 으로 변경한다.
 */
public class BOJ17069 {
    static class BOJ17069_space {
        int r,c;
        int s;      // 0 : 빈 공간, 1 : 벽, 2 : 파이프
        int d;

        BOJ17069_space(int r, int c, int s) {
            this.r = r;
            this.c = c;
            this.s = s;
            this.d = -1;
        }

        BOJ17069_space(int r,int c, int s, int d) {
            this.r = r;
            this.c = c;
            this.s = s;
            this.d = d;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static long ans;
    static BOJ17069_space[][] house;
    static int[][][] directions = {{{0,1}, {1,1}}, {{1,0}, {1,1}}, {{0,1},{1,0},{1,1}}};  // 0 : 가로, 1 : 세로, 2: 대각선

    static long[][][] mem;


    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        bottom_up_dp();
    }

    // 상향식
    static void bottom_up_dp() {
        mem[1][2][0] = mem[1][2][2] = 1;

        for(int r=1;r<=N;r++) {
            for(int c=3;c<=N;c++) {
                if(house[r][c].s == 1) continue;
                long diagonal = (house[r-1][c].s == 1 || house[r][c-1].s == 1) ? 0 : mem[r-1][c-1][2];
                mem[r][c][0] = mem[r][c-1][0] + diagonal;
                mem[r][c][1] = mem[r-1][c][1] + diagonal;
                mem[r][c][2] = mem[r][c-1][0] + mem[r-1][c][1] + diagonal;
            }
        }

        ans = mem[N][N][2];

        System.out.println(ans);
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        house = new BOJ17069_space[N+1][N+1];
        mem = new long[N+1][N+1][3];

        ans = 0;

        for(int r=1;r<=N;r++) {
            String[] input = br.readLine().split(" ");
            for(int c=1;c<=N;c++) {
                house[r][c] = new BOJ17069_space(r,c,Integer.parseInt(input[c-1]));
            }
        }

        for(int i=0;i<=N;i++) {
            house[i][0] = new BOJ17069_space(i,0,0);
            house[0][i] = new BOJ17069_space(0,i,0);
        }
    }
}
