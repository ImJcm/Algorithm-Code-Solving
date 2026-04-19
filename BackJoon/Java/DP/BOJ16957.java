package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
체스판 위의 공

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	2058	776	563	37.235%
문제
크기가 R×C인 체스판이 있고, 체스판의 각 칸에는 정수가 하나씩 적혀있다. 체스판에 적혀있는 정수는 모두 서로 다르다.

체스판의 각 칸 위에 공을 하나씩 놓는다. 이제 공은 다음과 같은 규칙에 의해서 자동으로 움직인다.

인접한 8방향 (가로, 세로, 대각선)에 적힌 모든 정수가 현재 칸에 적힌 수보다 크면 이동을 멈춘다.
그 외의 경우에는 가장 작은 정수가 있는 칸으로 공이 이동한다.
공의 크기는 매우 작아서, 체스판의 한 칸 위에 여러 개의 공이 있을 수 있다. 체스판의 상태가 주어진다. 공이 더 이상 움직이지 않을 때, 각 칸에 공이 몇 개 있는지 구해보자.

입력
첫째 줄에 체스판의 크기 R, C가 주어진다. 둘째 줄부터 R개의 줄에 체스판에 적혀있는 정수가 주어진다.

출력
총 R개의 줄에 걸쳐서 체스판에 적힌 정수를 출력한다.

제한
1 ≤ R, C ≤ 500
0 ≤ 체스판에 적힌 정수 ≤ 300,000
예제 입력 1
3 3
1 3 4
5 6 7
8 9 2
예제 출력 1
6 0 0
0 0 0
0 0 3
예제 입력 2
1 6
10 20 3 4 5 6
예제 출력 2
1 0 5 0 0 0
예제 입력 3
4 4
20 2 13 1
4 11 10 35
3 12 9 7
30 40 50 5
예제 출력 3
0 4 0 4
0 0 0 0
4 0 0 0
0 0 0 4
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
다이나믹 프로그래밍
자료 구조
그래프 이론
그래프 탐색
분리 집합
 */
/*
알고리즘 핵심
1. 각 좌표에서 8방향을 탐색하여 가장 작은 n 값을 갖는 좌표로 최종적으로 움직일 수 없는 좌표까지 도달한다.
2. 도달하는 과정에서 지나온 칸의 최종 좌표를 업데이트한다 - 메모리제이션
3. 이동한 칸의 개수를 ans배열에 업데이트한다.
4. 1,2,3의 과정을 모든 좌표에 수행하고, mem이 null이 아닌 이미 이동에 사용된 칸은 넘어간다.

채점 결과가 메모리는 문제가 없지만, 실행 시간이 1708ms로 시간 제한인 2sec에 아슬아슬하게 통과하여 해당 코드의 좋은 알고리즘이 무엇일까 궁금하여
다른 사람의 정답 코드를 검색하였다. - https://100100e.tistory.com/257

핵심
1. r,c 배열의 모든 칸에서 8방향에서 가장 작은 n을 갖는 좌표를 저장한다. - 메모리제이션
2. r,c 배열의 모든 칸에서 메모리제이션 배열에 저장한 값들로 최종 목적지를 찾는다.
    해당 좌표에서 다음 부모 좌표 정보를 저장하고 있고, 그 부모 좌표는 또 (조)무보 좌표를 가지고 있으므로 최종 목적지까지 도달할 수 있다.
3. r,c의 모든 좌표를 최종 목적지에 해당하는 좌표에서 도달할 수 있는 공의 개수를 업데이트한다.

차이점
- 이동한 좌표들의 최종 목적지의 좌표, 즉 메모리제이션의 값을 최종 목적지로 설정하는 것과 다음 이동한 좌표를 설정한 것이다.
 */
public class BOJ16957 {
    static class BOJ16957_square {
        int r,c,n;

        BOJ16957_square(int r, int c, int n) {
            this.r = r;
            this.c = c;
            this.n = n;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int R,C;
    static BOJ16957_square record;
    static BOJ16957_square[][] mem;
    static BOJ16957_square[][] boards;
    static int[][] ans;
    static int[][] directions = {{-1,-1},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        for(int r = 1; r <= R; r++) {
            for(int c = 1; c <= C; c++) {
                if(mem[r][c] != null) continue;
                record = null;
                searchPath(boards[r][c],1);
            }
        }

        for(int r = 1; r <= R; r++) {
            for(int c = 1; c <= C; c++) {
                System.out.print(ans[r][c] + " ");
            }
            System.out.println();
        }
    }

    private static void searchPath(BOJ16957_square s, int moveCnt) {
        BOJ16957_square next = s;

        for(int[] d : directions) {
            int nr = s.r + d[0];
            int nc = s.c + d[1];

            if(nr < 1 || nc < 1 || nr > R || nc > C) continue;

            next = next.n > boards[nr][nc].n ? boards[nr][nc] : next;
        }

        /*
            메모리제이션 - 해당 좌표가 이미 조건1,2를 만족하는 곳의 좌표를 저장한다.
            mem[r][c]가 null이 아니면, next좌표를 통해 갈 수 있는 최종 목적지를 record에 저장하고 현재 위치의 mem을 mem[next][next]로 저장한다.
            record는 이전에 움직여온 좌표들의 최종 목적지를 저장하기 위한 것이다.
            searchPath()가 리턴된 후, mem[s.r][s.c] = record로 저장한다.

            현재 까지 움직인 칸을 누적한 값 moveCnt를 ans의 최종 좌표에 더하여 업데이트한다.
         */
        if(mem[next.r][next.c] != null) {
            record = mem[next.r][next.c];
            mem[s.r][s.c] = record;
            ans[record.r][record.c] += moveCnt;
            return;
        }

        /*
            if - 다음으로 이동할 좌표가 현재 좌표와 같을 때 = 더 이상 움직일 수 없는 경우
            else - 이동이 가능한 경우 searchPath 호출
         */
        if(next == s) {
            record = s;
            ans[record.r][record.c] += moveCnt;
            mem[record.r][record.c] = s;
        } else {
            searchPath(next, moveCnt + 1);
            mem[s.r][s.c] = record;
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        R = Integer.parseInt(input[0]);
        C = Integer.parseInt(input[1]);

        boards = new BOJ16957_square[R+1][C+1];
        mem = new BOJ16957_square[R+1][C+1];
        ans = new int[R+1][C+1];

        for(int r = 1; r <= R; r++) {
            input = br.readLine().split(" ");
            for(int c = 1; c <= C; c++) {
                boards[r][c] = new BOJ16957_square(r,c,Integer.parseInt(input[c-1]));
            }
        }
    }
}
