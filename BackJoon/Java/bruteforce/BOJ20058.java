package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
마법사 상어와 파이어스톰

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	16806	7609	4649	41.524%
문제
마법사 상어는 파이어볼과 토네이도를 조합해 파이어스톰을 시전할 수 있다. 오늘은 파이어스톰을 크기가 2N × 2N인 격자로 나누어진 얼음판에서 연습하려고 한다. 위치 (r, c)는 격자의 r행 c열을 의미하고, A[r][c]는 (r, c)에 있는 얼음의 양을 의미한다. A[r][c]가 0인 경우 얼음이 없는 것이다.

파이어스톰을 시전하려면 시전할 때마다 단계 L을 결정해야 한다. 파이어스톰은 먼저 격자를 2L × 2L 크기의 부분 격자로 나눈다. 그 후, 모든 부분 격자를 시계 방향으로 90도 회전시킨다. 이후 얼음이 있는 칸 3개 또는 그 이상과 인접해있지 않은 칸은 얼음의 양이 1 줄어든다. (r, c)와 인접한 칸은 (r-1, c), (r+1, c), (r, c-1), (r, c+1)이다. 아래 그림의 칸에 적힌 정수는 칸을 구분하기 위해 적은 정수이다.


마법을 시전하기 전	L = 1	L = 2
마법사 상어는 파이어스톰을 총 Q번 시전하려고 한다. 모든 파이어스톰을 시전한 후, 다음 2가지를 구해보자.

남아있는 얼음 A[r][c]의 합
남아있는 얼음 중 가장 큰 덩어리가 차지하는 칸의 개수
얼음이 있는 칸이 얼음이 있는 칸과 인접해 있으면, 두 칸을 연결되어 있다고 한다. 덩어리는 연결된 칸의 집합이다.

입력
첫째 줄에 N과 Q가 주어진다. 둘째 줄부터 2N개의 줄에는 격자의 각 칸에 있는 얼음의 양이 주어진다. r번째 줄에서 c번째 주어지는 정수는 A[r][c] 이다.

마지막 줄에는 마법사 상어가 시전한 단계 L1, L2, ..., LQ가 순서대로 주어진다.

출력
첫째 줄에 남아있는 얼음 A[r][c]의 합을 출력하고, 둘째 줄에 가장 큰 덩어리가 차지하는 칸의 개수를 출력한다. 단, 덩어리가 없으면 0을 출력한다.

제한
2 ≤ N ≤ 6
1 ≤ Q ≤ 1,000
0 ≤ A[r][c] ≤ 100
0 ≤ Li ≤ N
예제 입력 1
3 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1
예제 출력 1
284
64
예제 입력 2
3 2
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2
예제 출력 2
280
64
예제 입력 3
3 5
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 0 3 2
예제 출력 3
268
64
예제 입력 4
3 10
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 0 3 2 1 2 3 2 3
예제 출력 4
248
62
예제 입력 5
3 10
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 1 2 3 1 2 3 1
예제 출력 5
246
60
예제 입력 6
3 10
1 0 3 4 5 6 7 0
8 0 6 5 4 3 2 1
1 2 0 4 5 6 7 0
8 7 6 5 4 3 2 1
1 2 3 4 0 6 7 0
8 7 0 5 4 3 2 1
1 2 3 4 5 6 7 0
0 7 0 5 4 3 2 1
1 2 3 1 2 3 1 2 3 1
예제 출력 6
37
9
출처
문제를 만든 사람: baekjoon
알고리즘 분류
구현
그래프 이론
그래프 탐색
시뮬레이션
너비 우선 탐색
깊이 우선 탐색
 */
/*
알고리즘 핵심
구현 + DFS
1. 얼음을 fire_storm()을 통해 입려긍로 주어진 L값에 맞게 구역을 나눈 후 회전을 수행한다.
2. 회전을 마치고 주변에 얼음 덩어리가 3개 미만인 얼음의 경우 따로 저장하여 해당하는 얼음 덩어리를 -1을 수행한다 = 동시성 확보
3. 2번 과정을 모두 마친 후, 남아있는 얼음 덩어리에서 DFS를 사용하여 한 집합을 구성하는 가장 큰 얼음 덩어리를 구함과 동시에 남아있는 얼음을 모두 구한다.
4. 남아있는 얼음과 가장 큰 집합을 구성하는 얼음의 칸 수를 출력한다. 이때, 남아있는 얼음이 0인 경우 덩어리가 없는 것으로 판단하여 가장 큰 얼음 덩어리의 칸의 개수를 0을 출력한다.

이 문제의 경우 문제를 해석하는데 어려움이 있었다.
 */
public class BOJ20058 {
    static class BOJ20058_ice {
        int r,c;
        int ice;

        BOJ20058_ice(int r, int c, int ice) {
            this.r = r;
            this.c = c;
            this.ice = ice;
        }
    }

    static class BOJ20058_result {
        int ice;
        int space;

        BOJ20058_result(int ice, int space) {
            this.ice = ice;
            this.space = space;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,Q,SIZE,remain_ice,biggest_space;
    static int[] Qs;
    static ArrayList<BOJ20058_ice> wait_melt_ices;
    static BOJ20058_ice[][] ices;
    static int[][] direction = {{-1,0},{1,0},{0,-1},{0,1}};
    static boolean[][] visited, melted_visited;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        //sum_print();
        for(int l : Qs) {
            fire_storm(l);
        }

        //System.out.println("final --------------");
        //print();
        //sum_print();

        for(int r = 1; r <= SIZE; r++) {
            for(int c = 1; c <= SIZE; c++) {
                if(visited[r][c] || ices[r][c].ice == 0) continue;
                visited[r][c] = true;
                BOJ20058_result result = search_ices(ices[r][c]);

                remain_ice += result.ice;

                if(biggest_space < result.space) biggest_space = result.space;
            }
        }

        /*
            출력 기준으로 덩어리가 없으면 0을 출력한다. => 남은 얼음이 없으면 덩어리가 없는 것이므로 0, 그렇지 않으면 biggest_space
         */
        System.out.println(remain_ice);
        System.out.println(biggest_space = remain_ice == 0 ? 0 : biggest_space);
    }

    /*
        얼음이 녹은 후 인접한 얼음을 다시 한번 녹일 수 있는지 확인한 후 녹이는 과정의 로직 = 이 방법도 잘못된 방법
     */
    private static BOJ20058_result search_ices(BOJ20058_ice ice) {
        if(ice.ice == 0) return new BOJ20058_result(0,0);

        int r = ice.ice, s = 1;

        for(int[] d : direction) {
            int nr = ice.r + d[0];
            int nc = ice.c + d[1];

            if(nr < 1 || nr > SIZE || nc < 1 || nc > SIZE) continue;
            if(visited[nr][nc]) continue;

            visited[nr][nc] = true;
            BOJ20058_result result = search_ices(ices[nr][nc]);
            r += result.ice;
            s += result.space;
        }

        return new BOJ20058_result(r,s);
    }

    private static void print() {
        for(int r = 0; r <= SIZE + 1; r++) {
            for(int c = 0; c <= SIZE + 1; c++) {
                System.out.print(ices[r][c].ice + " ");
            }
            System.out.println();
        }
    }

    private static void sum_print() {
        int sum = 0;
        for(int r = 0; r <= SIZE + 1; r++) {
            for(int c = 0; c <= SIZE + 1; c++) {
                sum += ices[r][c].ice;
            }
        }
        System.out.println("total ice : " + sum);
    }

    private static void fire_storm(int l) {
        int round_size = (int) Math.pow(2,l);

        for(int r = 1; r <= SIZE; r += round_size) {
            for(int c = 1; c <= SIZE; c+= round_size) {
                int sr = r;
                int sc = c;

                for(int i = 0; i < round_size / 2; i++) {
                    rotate(sr + i,sc + i,round_size - 2 * i);
                }
            }
        }

        print();
        System.out.println("---- before melting");

        ice_melting();

        print();
        System.out.println("---- after melting");
    }

    private static void wrong_func_2_ice_melting() {
        melted_visited = new boolean[SIZE + 2][SIZE + 2];

        for(int r = 1; r <= SIZE; r++) {
            for(int c = 1; c <= SIZE; c++) {
                if(melted_visited[r][c]) continue;
                melting(ices[r][c]);
            }
        }
    }

    private static void melting(BOJ20058_ice ice) {
        Queue<BOJ20058_ice> q = new LinkedList<>();

        q.add(ice);
        //melted_visited[ice.r][ice.c] = true;

        while(!q.isEmpty()) {
            BOJ20058_ice now = q.poll();

            if(can_melting(ices[now.r][now.c])) {
                ices[now.r][now.c].ice--;
                melted_visited[now.r][now.c] = true;

                for(int[] d : direction) {
                    int nr = now.r + d[0];
                    int nc = now.c + d[1];

                    if(nr < 1 || nr > SIZE || nc < 1 || nc > SIZE) continue;
                    if(melted_visited[nr][nc]) continue;
                    melted_visited[nr][nc] = true;
                    q.add(ices[nr][nc]);
                }
            }


        }
    }

    private static boolean can_melting(BOJ20058_ice ice) {
        int adj_ice = 0;

        if(ices[ice.r][ice.c].ice == 0) return false;

        for(int[] d : direction) {
            if(ices[ice.r + d[0]][ice.c + d[1]].ice > 0) adj_ice++;
        }

        if(adj_ice >= 3) return false;
        return true;
    }

    /*
        연쇄적으로 하나의 얼음이 녹은 후 인접한 얼음이 녹지 않는 문제가 있다. = 잘못된 로직

        수정) 잘못된 로직을 생각했으나 얼음의 녹일 수 있는 여부를 검사한 후 바로 녹이는 것이 아니라 녹일 얼음을 따로 저장한 후
        녹이는 로직으로 하는 것이 옳은 방법이였다.
     */
    private static void ice_melting() {
        for(int r = 1; r <= SIZE; r++) {
            for(int c = 1; c <= SIZE; c++) {
                int nr = r;
                int nc = c;
                int adj_ice = 0;
                
                for(int[] d : direction) {
                    nr = r + d[0];
                    nc = c + d[1];
                    
                    if(ices[nr][nc].ice > 0) adj_ice++;
                }
                
                if(adj_ice < 3) wait_melt_ices.add(ices[r][c]);
            }
        }

        for(BOJ20058_ice ice : wait_melt_ices) {
            ice.ice = Math.max(0, ice.ice - 1);
        }

        wait_melt_ices.clear();
    }

    private static void rotate(int sr, int sc, int l) {
        int[] tmp = new int[l - 1];

        for(int i = 0; i < l - 1; i++) {
            tmp[i] = ices[sr + i][sc].ice;

            ices[sr + i][sc].ice = ices[sr + l - 1][sc + i].ice;
            ices[sr + l - 1][sc + i].ice = ices[sr + l - 1 - i][sc + l - 1].ice;
            ices[sr + l - 1 - i][sc + l - 1].ice = ices[sr][sc + l - 1 - i].ice;
            ices[sr][sc + l - 1 - i].ice = tmp[i];
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        Q = Integer.parseInt(input[1]);

        SIZE = (int) Math.pow(2,N);

        ices = new BOJ20058_ice[SIZE + 2][SIZE + 2];

        for(int r = 0; r <= SIZE + 1; r++) {
            if(!(r == 0 || r == SIZE + 1)) input = br.readLine().split(" ");
            for(int c = 0; c <= SIZE + 1; c++) {
                if(r == 0 || r == SIZE + 1 || c == 0 || c == SIZE + 1) ices[r][c] = new BOJ20058_ice(r,c,0);
                else ices[r][c] = new BOJ20058_ice(r,c,Integer.parseInt(input[c - 1]));
            }
        }
        Qs = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        visited = new boolean[SIZE + 2][SIZE + 2];

        remain_ice = 0;
        biggest_space = 0;

        wait_melt_ices = new ArrayList<>();
    }
}
