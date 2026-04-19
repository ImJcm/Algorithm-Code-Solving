package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
Maaaaaaaaaze

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	6155	3604	2127	55.841%
문제
평화롭게 문제를 경작하며 생활하는 BOJ 마을 사람들은 더 이상 2차원 미로에 흥미를 느끼지 않는다. 2차원 미로는 너무나 쉽게 탈출이 가능하기 때문이다. 미로를 이 세상 그 누구보다 사랑하는 준현이는 이런 상황을 매우 안타깝게 여겨 아주 큰 상금을 걸고 BOJ 마을 사람들의 관심을 확 끌 수 있는 3차원 미로 탈출 대회를 개최하기로 했다.

대회의 규칙은 아래와 같다.

5×5 크기의 판이 5개 주어진다. 이중 일부 칸은 참가자가 들어갈 수 있고 일부 칸은 참가자가 들어갈 수 없다. 그림에서 하얀 칸은 참가자가 들어갈 수 있는 칸을, 검은 칸은 참가자가 들어갈 수 없는 칸을 의미한다.


참가자는 주어진 판들을 시계 방향, 혹은 반시계 방향으로 자유롭게 회전할 수 있다. 그러나 판을 뒤집을 수는 없다.


회전을 완료한 후 참가자는 판 5개를 쌓는다. 판을 쌓는 순서는 참가자가 자유롭게 정할 수 있다. 이렇게 판 5개를 쌓아 만들어진 5×5×5 크기의 큐브가 바로 참가자를 위한 미로이다. 이 때 큐브의 입구는 정육면체에서 참가자가 임의로 선택한 꼭짓점에 위치한 칸이고 출구는 입구와 면을 공유하지 않는 꼭짓점에 위치한 칸이다.


참가자는 현재 위치한 칸에서 면으로 인접한 칸이 참가자가 들어갈 수 있는 칸인 경우 그 칸으로 이동할 수 있다.
참가자 중에서 본인이 설계한 미로를 가장 적은 이동 횟수로 탈출한 사람이 우승한다. 만약 미로의 입구 혹은 출구가 막혀있거나, 입구에서 출구에 도달할 수 있는 방법이 존재하지 않을 경우에는 탈출이 불가능한 것으로 간주한다.
이 대회에서 우승하기 위해서는 미로를 잘 빠져나올 수 있기 위한 담력 증진과 체력 훈련, 그리고 적절한 운이 제일 중요하지만, 가장 적은 이동 횟수로 출구에 도달할 수 있게끔 미로를 만드는 능력 또한 없어서는 안 된다. 주어진 판에서 가장 적은 이동 횟수로 출구에 도달할 수 있게끔 미로를 만들었을 때 몇 번 이동을 해야하는지 구해보자.

입력
첫째 줄부터 25줄에 걸쳐 판이 주어진다. 각 판은 5줄에 걸쳐 주어지며 각 줄에는 5개의 숫자가 빈칸을 사이에 두고 주어진다. 0은 참가자가 들어갈 수 없는 칸, 1은 참가자가 들어갈 수 있는 칸을 의미한다.

출력
첫째 줄에 주어진 판으로 설계된 미로를 탈출하는 가장 적은 이동 횟수를 출력한다. 단, 어떻게 설계하더라도 탈출이 불가능할 경우에는 -1을 출력한다.

예제 입력 1
1 1 1 1 1
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
1 1 1 1 1
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
1 1 1 1 1
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
1 1 1 1 1
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
1 1 1 1 1
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
예제 출력 1
12
예제 입력 2
1 1 1 1 1
1 0 0 0 1
1 0 0 0 1
1 0 0 0 1
1 1 1 1 1
0 0 0 0 0
0 1 1 1 0
0 1 0 1 0
0 1 1 1 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 1 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 1 1 1 0
0 1 0 1 0
0 1 1 1 0
0 0 0 0 0
1 1 1 1 1
1 0 0 0 1
1 0 0 0 1
1 0 0 0 1
1 1 1 1 1
예제 출력 2
-1
예제 입력 3
1 1 1 1 1
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
1 1 1 1 1
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
1 1 1 1 1
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
1 1 1 1 1
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
1 1 1 1 1
예제 출력 3
12
예제 입력 4
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
예제 출력 4
12
예제 입력 5
0 0 0 1 0
0 0 0 0 0
1 0 1 1 1
0 0 0 1 0
0 0 1 0 0
0 1 0 0 0
1 1 0 0 0
1 0 0 1 0
0 1 1 1 0
0 1 0 1 0
0 0 1 0 0
1 0 0 0 0
0 1 0 0 0
0 0 1 0 0
1 1 1 0 0
1 0 0 0 1
1 0 0 0 0
0 0 1 0 1
0 1 1 0 0
0 1 0 0 0
0 0 0 1 0
1 0 0 0 0
0 0 1 0 0
0 1 0 0 1
0 1 0 0 0
예제 출력 5
22
예제 입력 6
0 0 0 0 0
0 0 0 0 0
1 0 0 0 1
0 0 1 0 0
0 0 1 1 1
0 1 0 0 1
0 0 0 0 1
0 0 0 0 0
0 0 0 0 0
0 1 0 0 0
0 1 0 0 1
1 0 0 1 0
0 0 0 1 0
0 1 1 0 0
0 1 0 0 0
1 0 1 0 0
0 0 0 0 0
1 0 0 0 0
0 0 0 1 0
1 0 0 0 0
0 0 0 1 0
0 0 0 0 1
1 1 0 0 0
1 0 0 1 1
1 0 0 0 0
예제 출력 6
-1
예제 입력 7
1 1 0 0 0
0 0 0 0 1
0 0 1 0 0
0 0 0 0 0
0 0 0 0 0
0 0 1 1 1
1 0 0 0 0
0 0 1 0 0
0 0 1 1 1
0 0 1 0 0
0 0 0 0 0
0 0 1 0 1
0 0 0 0 0
0 0 0 1 0
0 0 1 0 1
0 0 1 0 0
1 0 0 0 0
0 0 1 1 0
1 0 1 0 0
0 0 1 0 1
0 0 1 1 0
1 1 0 1 1
0 0 0 0 1
0 1 0 1 0
0 1 0 0 0
예제 출력 7
16
예제 입력 8
0 0 1 0 0
0 0 0 0 0
1 1 0 0 0
0 0 1 0 0
1 1 1 0 0
0 0 0 0 1
1 0 0 0 0
0 1 0 0 1
0 0 0 0 0
0 1 0 1 0
1 0 0 0 1
1 1 1 1 1
1 1 0 0 0
0 0 0 1 0
0 0 0 1 0
0 0 0 1 1
0 0 1 0 0
0 1 1 1 0
1 0 0 0 0
0 1 1 0 1
0 1 0 0 0
0 0 0 1 0
1 0 0 0 0
0 0 0 1 0
0 0 0 1 0
예제 출력 8
18
출처
문제를 만든 사람: BaaaaaaaaaaarkingDog
문제를 검수한 사람: Lawali, portableangel, ryute
알고리즘 분류
구현
그래프 이론
브루트포스 알고리즘
그래프 탐색
너비 우선 탐색
 */
/*
알고리즘 핵심
1. 큐브를 쌓은 결과가 중복없는 상태의 데이터 필요 - dfs
2. 1과정에서 나온 결과인 큐브들을 각 큐브 층마다 회전을 상태의 데이터 필요 - dfs
3. 2과정에서 나온 결과로 [1][1][1] -> [5][5][5]로 갈 수 있는지 경로 탐색 - bfs
(3에서 출발지와 도착지를 확정지을 수 있는 이유 : 문제에서 출발지가 속하는 면과 접하지 않은 면의 꼭짓점을 도착지로 정의하며, 각 큐브의 회전을 통해
최상단의 큐브면에서 나머지 3개의 꼭짓점에서 다른 도착지를 구하는 큐브는 중복되기 때문이다.
즉, 큐브의 회전을 통해 모든 출발과 도착의 꼭짓점의 상태를 [1][1][1] -> [5][5][5]의 출발지와 도착지로 큐브 상태를 정의할 수 있다.)
예시, 회전을 수행하지 않은 큐브에서 출발지 - 도착지의 쌍 ({(1,1,1) - (5,5,5)},{(1,5,1) - (5,1,5)}, {(1,1,5) - (5,5,1)}, {(1,5,5) - (5,1,1)})
{(1,5,1) - (5,1,5)} -> z=1,5, 90도 회전
{(1,1,5) - (5,5,1)} -> z=1,5, 270도 회전
{(1,5,5) - (5,1,1)} -> z=1,5, 180도 회전
을 통해 [1][1][1] -> [5][5][5]의 출발과 도착의 쌍으로 만들 수 있다.

순서 중복없이 큐브를 쌓은 큐브 데이터의 ArrayList + 각 회전을 수행한 큐브 데이터의 ArrayList => 메모리 1056928 KB	시간 2904 ms
순서 중복없이 큐브를 쌓은 큐브 데이터의 ArrayList                                       => 메모리 301424 KB     시간 1304 ms
 */
public class BOJ16985 {
    static class BOJ16985_space {
        int x,y,z;
        int in_able;
        int move;

        BOJ16985_space(int z, int x, int y, int in_able, int move) {
            this.z = z;
            this.x = x;
            this.y = y;
            this.in_able = in_able;
            this.move = move;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int ans;
    static int[][] directions = {{0,-1,0},{0,1,0},{0,0,-1},{0,0,1},{1,0,0},{-1,0,0}};
    static BOJ16985_space[][][] cube;
    static ArrayList<BOJ16985_space[][][]> cubes;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        cube_stacking_rotating_And_search_path();
        //cube_4_edge_maze_escape();

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    /**
     * 중복되지 않은 순서의 큐브를 쌓은 것으로 부터 각 층의 큐브를 90도 회전한 상태를 별도의 ArrayList에 저장하고 bfs를 수행하는 메서드
     * 이 부분에서 별도의 ArrayList로 저장하기 때문에 메모리가 크게 증가하는 원인이다.
     *
     * 따라서, 큐브를 중복없이 쌓은 ArrayList(=cubes)에서 회전을 수행한 후 기저사례 조건 만족 시 bfs를 수행하여 메모리를 줄이고 수행시간도 줄일 수 있었다.
     */
    /*static void cube_4_edge_maze_escape() {
        for(BOJ16985_space[][][] cube : cube_apply_order_rotate) {
            int s = cube[1][1][1].in_able;
            int e = cube[5][5][5].in_able;

            if(s != 1 || e != 1) continue;

            path_search_bfs(cube);
        }
    }*/

    static void path_search_bfs(BOJ16985_space[][][] cube) {
        Queue<BOJ16985_space> q = new LinkedList<>();
        boolean[][][] visited = new boolean[cube.length][cube.length][cube.length];

        q.offer(cube[1][1][1]);
        visited[1][1][1] = true;

        while(!q.isEmpty()) {
            BOJ16985_space now = q.poll();

            if(now.x == 5 && now.y == 5 && now.z == 5) {
                ans = Math.min(ans, now.move);
            }

            for(int[] d : directions) {
                int nz = now.z + d[0];
                int nx = now.x + d[1];
                int ny = now.y + d[2];

                if(nz < 1 || nx < 1 || ny < 1 || nz > 5 || nx > 5 || ny > 5) continue;
                if(visited[nz][nx][ny]) continue;
                if(cube[nz][nx][ny].in_able == 0) continue;

                visited[nz][nx][ny] = true;
                q.offer(new BOJ16985_space(nz,nx,ny,now.in_able,now.move + 1));
            }
        }
    }

    static void cube_stacking_rotating_And_search_path() {
        order_dfs(0, new int[5], new boolean[5]);

        for(BOJ16985_space[][][] cube : cubes) {
            rotate_dfs(0, cube);
        }
    }

    static void rotate_dfs(int depth, BOJ16985_space[][][] cube) {
        if(depth == 5) {
            //cube_apply_order_rotate.add(copy_cube(cube));
            if(cube[1][1][1].in_able == 1 && cube[5][5][5].in_able == 1) {
                path_search_bfs(cube);
            }
            return;
        }

        for(int i = 0; i < 4; i++) {
            rotate_dfs(depth + 1, cube);
            rotate_90_degrees_cube(depth + 1, cube);
        }
    }

    static void order_dfs(int depth, int[] order, boolean[] visited) {
        if(depth == 5) {
            cubes.add(copy_cube(order));
            return;
        }

        for(int i = 0; i < 5; i++) {
            if(visited[i]) continue;
            visited[i] = true;
            order[depth] = i + 1;
            order_dfs(depth + 1, order, visited);
            visited[i] = false;
        }
    }

    static void rotate_90_degrees_cube(int z, BOJ16985_space[][][] cube) {
        for(int c = cube.length / 2, x = 1; c > 1; c--, x++) {
            int l = cube.length - x;
            int[] storage = new int[2 * c - 1];

            for(int i = x; i <= l; i++) {
                storage[i - x] = cube[z][x][i].in_able;
            }

            // 상단
            for(int i = x; i <= l; i++) {
                cube[z][x][cube.length - i].in_able = cube[z][i][x].in_able;
            }

            // 좌측
            for(int i = x; i <= l; i++) {
                cube[z][i][x].in_able = cube[z][cube.length - x][i].in_able;
            }

            // 하단
            for(int i = x; i < l; i++) {
                cube[z][cube.length - x][i].in_able = cube[z][cube.length - i][cube.length - x].in_able;
            }

            // 우측
            for(int i = x; i <= l; i++) {
                cube[z][i][cube.length - x].in_able = storage[i - x];
            }
        }
    }

    /*static BOJ16985_space[][][] copy_cube(BOJ16985_space[][][] cube) {
        BOJ16985_space[][][] renew_cube = new BOJ16985_space[6][6][6];

        for(int z = 1; z <= 5; z++) {
            for(int x = 1; x <= 5; x++) {
                for(int y = 1; y <= 5; y++) {
                    renew_cube[z][x][y] = new BOJ16985_space(z,x,y,cube[z][x][y].in_able,0);
                }
            }
        }
        return renew_cube;
    }*/

    static BOJ16985_space[][][] copy_cube(int[] order) {
        BOJ16985_space[][][] renew_cube = new BOJ16985_space[6][6][6];

        for(int z = 1; z <= 5; z++) {
            int o = order[z-1];
            for(int x = 1; x <= 5; x++) {
                for(int y = 1; y <= 5; y++) {
                    renew_cube[z][x][y] = new BOJ16985_space(z,x,y,cube[o][x][y].in_able,0);
                }
            }
        }
        return renew_cube;
    }
    private static void init_setting() throws IOException {
        cube = new BOJ16985_space[6][6][6];
        cubes = new ArrayList<>();
        //cube_apply_order_rotate = new ArrayList<BOJ16985_space[][][]>();

        ans = Integer.MAX_VALUE;

        for(int z = 1; z <= 5; z++) {
            for(int x = 1; x <= 5; x++) {
                String[] input = br.readLine().split(" ");
                for(int y = 1; y <= 5; y++) {
                    cube[z][x][y] = new BOJ16985_space(z,x,y,Integer.parseInt(input[y-1]),0);
                }
            }
        }
    }

    static void test_rotate_90() {
        //rotate_90_degrees_cube(1, cube);
        for(int i = 1; i <= 5 ; i++) {
            for(int j = 1; j <= 5; j++) {
                System.out.print(cube[1][i][j].in_able + " ");
            }
            System.out.println();
        }
    }

    static void test_cube_shape(BOJ16985_space[][][] cube) {
        for(int k = 1; k <= 5; k++) {
            for(int i = 1; i <= 5 ; i++) {
                for(int j = 1; j <= 5; j++) {
                    System.out.print(cube[k][i][j].in_able + " ");
                }
                System.out.println();
            }
            System.out.println("l -----");
        }
        System.out.println("n -----");
    }
}
