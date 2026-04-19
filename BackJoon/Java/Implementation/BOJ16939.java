package Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
2×2×2 큐브

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	1349	576	449	42.519%
문제
오늘은 2×2×2 루빅스 큐브를 풀어보려고 한다. 큐브의 각 면은 양방향으로 90도 돌릴 수 있게 만들어져 있다. 큐브의 각 면에 있는 색상이 모두 같으면 큐브를 풀었다고 한다.

2×2×2 루빅스 큐브가 주어졌을 때, 정확히 한 번 돌려서 큐브를 풀 수 있는지 아닌지 구해보자.

입력
첫째 줄에 2×2×2 루빅스 큐브 각 면의 각 칸 색상이 주어진다. 색상은 1부터 6까지의 자연수로 나타내며, 각 자연수는 총 4번 등장한다. i번째 수가 의미하는 칸은 아래와 같다.


출력
루빅스 큐브를 정확히 한 번 돌려서 풀 수 있으면 1, 없으면 0을 출력한다.

예제 입력 1
2 5 4 6 1 3 6 2 5 5 1 2 3 5 3 1 1 2 4 6 6 4 3 4
예제 출력 1
0


예제 입력 2
5 3 5 3 2 5 2 5 6 2 6 2 4 4 4 4 1 1 1 1 6 3 6 3
예제 출력 2
1
구체적인 문제 그림 예시 : https://www.acmicpc.net/problem/16939 참고
 */
/*
문제에서 요구한 한 번만 돌려서 각 면의 색상이 모두 같은지 검사하는 문제이다.
처음에는 각 면의 4개의 블록을 기준으로 상,하,좌,우 방향으로 돌리는 것으로 생각했지만 큐브를 합치고 보면 6개의 면에서 한 면의 4개의 블록만을
상,하,좌,우로 움직이면 모든 4개를 제외한 20개의 블록을 움직인 결과와 동일한 결과를 만들어 낼 수 있다.
5,6,7,8면을 기준으로 5를 (좌,90)으로 회전한 결과는 같은 위치의 13, 14, 17, 18, 21, 22를 (좌,90)을 움직인 결과와 같다.

+ 추가로 체크해야될 부분은 기존에 5,6,7,8을 밑면으로 상하좌우를 돌려가며 검사했지만, 옆면들도 좌우로 돌려서 검사해봐야 한다.
즉, xyz 관점으로 검사를 수행해야한다.
 */
public class BOJ16939 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] rubiks_cube;
    static boolean check;
    static int[][] xy_directions,z_directions;
    static int[][][] rotation_xy_target, rotation_z_target;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int i=0;i<4 && !check;i++) {
            for(int j=0;j<2 && !check;j++) {
                rotate_xy(i,j);
                colors_check();
                derotate_xy(i,j);

                if(i < 2) {
                    rotate_z(i,j);
                    colors_check();
                    derotate_z(i,j);
                }
            }
        }

        if(check) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }

    static void rotate_xy(int d, int o) {
        // d - 0 : <-, 1 : ->, 2 : ↑, 3: ↓
        int len = rotation_xy_target[d][o].length;
        int prev_color_1 = rubiks_cube[rotation_xy_target[d][o][0]];
        int prev_color_2 = rubiks_cube[rotation_xy_target[d][o][1]];

        int r_d = (d == 0) ? 1 :
                    (d == 1) ? 0 :
                        (d == 2) ? 3:
                            (d == 3) ? 2 : -1;

        for(int side : rotation_xy_target[d][o]) {
            rubiks_cube[side] = rubiks_cube[xy_directions[side][r_d]];
        }

        rubiks_cube[rotation_xy_target[d][o][len-2]] = prev_color_1;
        rubiks_cube[rotation_xy_target[d][o][len-1]] = prev_color_2;
    }

    static void derotate_xy(int d, int o) {
        int r_d = (d == 0) ? 1 :
                (d == 1) ? 0 :
                        (d == 2) ? 3:
                                (d == 3) ? 2 : -1;

        int len = rotation_xy_target[r_d][o].length;
        int prev_color_1 = rubiks_cube[rotation_xy_target[r_d][o][0]];
        int prev_color_2 = rubiks_cube[rotation_xy_target[r_d][o][1]];

        for(int side : rotation_xy_target[r_d][o]) {
            rubiks_cube[side] = rubiks_cube[xy_directions[side][d]];
        }

        rubiks_cube[rotation_xy_target[r_d][o][len-2]] = prev_color_1;
        rubiks_cube[rotation_xy_target[r_d][o][len-1]] = prev_color_2;
    }

    static void rotate_z(int d, int o) {
        int len = rotation_z_target[d][o].length;
        int prev_color_1 = rubiks_cube[rotation_z_target[d][o][0]];
        int prev_color_2 = rubiks_cube[rotation_z_target[d][o][1]];

        int r_d = (d == 0) ? 1 : 0;

        for(int side : rotation_z_target[d][o]) {
            rubiks_cube[side] = rubiks_cube[z_directions[side][r_d]];
        }

        rubiks_cube[rotation_z_target[d][o][len-2]] = prev_color_1;
        rubiks_cube[rotation_z_target[d][o][len-1]] = prev_color_2;
    }

    static void derotate_z(int d, int o) {
        int r_d = (d == 0) ? 1 : 0;

        int len = rotation_z_target[d][o].length;
        int prev_color_1 = rubiks_cube[rotation_z_target[d][o][0]];
        int prev_color_2 = rubiks_cube[rotation_z_target[d][o][1]];

        for(int side : rotation_z_target[r_d][o]) {
            rubiks_cube[side] = rubiks_cube[z_directions[side][d]];
        }

        rubiks_cube[rotation_z_target[r_d][o][len-2]] = prev_color_1;
        rubiks_cube[rotation_z_target[r_d][o][len-1]] = prev_color_2;
    }

    static void colors_check() {
        boolean keep_going = true;
        int c = 0;
        for(int i=1;i<=24 & keep_going;i++) {
            if(i % 4 == 1) {
                c = rubiks_cube[i];
            }

            if(c != rubiks_cube[i]) {
                keep_going = false;
            }
        }

        if(keep_going) {
            check = true;
        }
    }

    static void print() {
        for(int i=1;i<=24;i++) {
            System.out.print(rubiks_cube[i] + " ");
        }
        System.out.println("--------");
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        int size = input.length;
        check = false;

        rubiks_cube = new int[size + 1];

        // col - (0 : <-, 1 : ->, 2 : ↑, 3: ↓)
        // 5,6,7,8 면을 기준
        // 해당방향으로 이동 후 위치
        xy_directions = new int[][]{
                {0, 0, 0, 0},       // 0 <- empty space
                {15, 18, 24, 5},    // 1
                {13, 20, 23, 6},    // 2
                {16, 17, 22, 7},    // 3
                {14, 19, 21, 8},    // 4
                {13, 17, 1, 9},     // 5
                {14, 18, 2, 10},    // 6
                {15, 19, 3, 11},    // 7
                {16, 20, 4, 12},    // 8
                {14, 19, 5, 24},    // 9
                {16, 17, 6, 23},    // 10
                {13, 20, 7, 22},    // 11
                {15, 18, 8, 21},    // 12
                {21, 5, 2, 11},     // 13
                {22, 6, 4, 9},      // 14
                {23, 7, 1, 12},     // 15
                {24, 8, 3, 10},     // 16
                {5, 21, 3, 10},     // 17
                {6, 22, 1, 12},     // 18
                {7, 23, 4, 9},      // 19
                {8, 24, 2, 11},     // 20
                {17, 13, 12, 4},    // 21
                {18, 14, 11, 3},    // 22
                {19, 15, 10, 2},    // 23
                {20, 16, 9, 1}      // 24
        };
        
        

        // col - (0 : <-, 1 : ->, 2 : ↑, 3: ↓)
        // 5,6,7,8 아랫면을 기준 + 1,2,3,4 면을 기준
        // 해당방향으로 이동 후 위치
        z_directions = new int[][]{
                {0, 0, 0, 0},       // 0 <- empty space
                {15, 18},    // 1
                {13, 20},    // 2
                {16, 17},    // 3
                {14, 19},    // 4
                {0, 0},      // 5
                {0, 0},      // 6
                {0, 0},      // 7
                {0, 0},      // 8
                {19, 14},    // 9
                {17, 16},    // 10
                {20, 13},    // 11
                {18, 15},    // 12
                {11, 2},     // 13
                {9, 4},      // 14
                {12, 1},     // 15
                {10, 3},     // 16
                {3, 10},     // 17
                {1, 12},     // 18
                {4, 9},      // 19
                {2, 11},     // 20
                {0, 0},      // 21
                {0, 0},      // 22
                {0, 0},      // 23
                {0, 0},      // 24
        };

        rotation_xy_target = new int[][][] {
            {
                {5,6,17,18,21,22,13,14},
                {7,8,19,20,23,24,15,16}
            }, {
                {5,6,13,14,21,22,17,18},
                {7,8,15,16,23,24,19,20}
            }, {
                {5,7,9,11,24,22,1,3},
                {6,8,10,12,23,21,2,4}
            }, {
                {5,7,1,3,24,22,9,11},
                {6,8,2,4,23,21,10,12}
            }
        };

        rotation_z_target = new int[][][] {
            {
                {1,2,18,20,12,11,15,13},
                {3,4,17,19,10,9,16,14}
            }, {
                {1,2,15,13,12,11,18,20},
                {3,4,16,14,10,9,17,19}
            }
        };



        for(int i=1;i<=size;i++) {
            rubiks_cube[i] = Integer.parseInt(input[i-1]);
        }
    }
}
