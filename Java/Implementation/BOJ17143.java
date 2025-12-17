package Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/*
낚시왕

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	52292	16026	9341	27.226%
문제
낚시왕이 상어 낚시를 하는 곳은 크기가 R×C인 격자판으로 나타낼 수 있다. 격자판의 각 칸은 (r, c)로 나타낼 수 있다. r은 행, c는 열이고, (R, C)는 아래 그림에서 가장 오른쪽 아래에 있는 칸이다. 칸에는 상어가 최대 한 마리 들어있을 수 있다. 상어는 크기와 속도를 가지고 있다.

낚시왕은 처음에 1번 열의 한 칸 왼쪽에 있다. 다음은 1초 동안 일어나는 일이며, 아래 적힌 순서대로 일어난다. 낚시왕은 가장 오른쪽 열의 오른쪽 칸에 이동하면 이동을 멈춘다.

낚시왕이 오른쪽으로 한 칸 이동한다.
낚시왕이 있는 열에 있는 상어 중에서 땅과 제일 가까운 상어를 잡는다. 상어를 잡으면 격자판에서 잡은 상어가 사라진다.
상어가 이동한다.
상어는 입력으로 주어진 속도로 이동하고, 속도의 단위는 칸/초이다. 상어가 이동하려고 하는 칸이 격자판의 경계를 넘는 경우에는 방향을 반대로 바꿔서 속력을 유지한채로 이동한다.

왼쪽 그림의 상태에서 1초가 지나면 오른쪽 상태가 된다. 상어가 보고 있는 방향이 속도의 방향, 왼쪽 아래에 적힌 정수는 속력이다. 왼쪽 위에 상어를 구분하기 위해 문자를 적었다.

상어가 이동을 마친 후에 한 칸에 상어가 두 마리 이상 있을 수 있다. 이때는 크기가 가장 큰 상어가 나머지 상어를 모두 잡아먹는다.

낚시왕이 상어 낚시를 하는 격자판의 상태가 주어졌을 때, 낚시왕이 잡은 상어 크기의 합을 구해보자.

입력
첫째 줄에 격자판의 크기 R, C와 상어의 수 M이 주어진다. (2 ≤ R, C ≤ 100, 0 ≤ M ≤ R×C)

둘째 줄부터 M개의 줄에 상어의 정보가 주어진다. 상어의 정보는 다섯 정수 r, c, s, d, z (1 ≤ r ≤ R, 1 ≤ c ≤ C, 0 ≤ s ≤ 1000, 1 ≤ d ≤ 4, 1 ≤ z ≤ 10000) 로 이루어져 있다. (r, c)는 상어의 위치, s는 속력, d는 이동 방향, z는 크기이다. d가 1인 경우는 위, 2인 경우는 아래, 3인 경우는 오른쪽, 4인 경우는 왼쪽을 의미한다.

두 상어가 같은 크기를 갖는 경우는 없고, 하나의 칸에 둘 이상의 상어가 있는 경우는 없다.

출력
낚시왕이 잡은 상어 크기의 합을 출력한다.

예제 입력 1
4 6 8
4 1 3 3 8
1 3 5 2 9
2 4 8 4 1
4 5 0 1 4
3 3 1 2 7
1 5 8 4 3
3 6 2 1 2
2 2 2 3 5
예제 출력 1
22
각 칸의 왼쪽 아래에 적힌 수는 속력, 오른쪽 아래는 크기, 왼쪽 위는 상어를 구분하기 위한 문자이다. 오른쪽 위에 ❤️는 낚시왕이 잡은 물고기 표시이다.

초기 상태
1초
2초 (E번 상어는 B번에게 먹혔다)
3초
4초
5초
6초

예제 입력 2
100 100 0
예제 출력 2
0
예제 입력 3
4 5 4
4 1 3 3 8
1 3 5 2 9
2 4 8 4 1
4 5 0 1 4
예제 출력 3
22
예제 입력 4
2 2 4
1 1 1 1 1
2 2 2 2 2
1 2 1 2 3
2 1 2 1 4
예제 출력 4
4
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: djm03178
문제의 오타를 찾은 사람: hellojdh
알고리즘 분류
구현
시뮬레이션
 */
public class BOJ17143 {
    static class BOJ17143_shark {
        /*
            r = 행
            c = 열
            s = 속력
            d = 이동방향
            z = 크기
         */
        int r,c,s,d,z;

        BOJ17143_shark(int r, int c, int s, int d, int z) {
            this.r = r;
            this.c = c;
            this.s = s;
            this.d = d;
            this.z = z;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int R,C,M,move,weight;
    static BOJ17143_shark[][] fishing;
    static ArrayList<BOJ17143_shark> sharks, ready_sharks, eaten_sharks;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        while(++move <= C) {
            catch_shark();
            move_sharks();
            select_bigger_sharks();
        }
        System.out.println(weight);
    }

    static void catch_shark() {
        for(int r=1;r<=R;r++) {
            if(fishing[r][move].z > 0) {
                weight += fishing[r][move].z;

                sharks.remove(fishing[r][move]);
                break;
            }
        }
    }

    /*
        모든 상어들의 이동 좌표를 계산하는 기능
        모든 상어들이 s만큼 이동하게 되면, (R or C) * M * S -> 촤대 100 * (100 * 100) * 1000 만큼 수행해야하기 때문에 시간초과가 발생할 것으로 생각된다.
        그래서, 현재 상어 위치에서 1,2 방향일 때, 2 * (C-1)만큼 / 3,4 방향일 때, 2 * (R-1)만큼 이동 시, 원래 위치로 돌아오는 점을 이용하여 큰 값의 s를
        2 * (C-1) or 2 * (R-1) 만큼 줄여 시간을 단축시키는 방법을 이용
     */
    static void move_sharks() {
        ready_sharks = new ArrayList<>();
        for(BOJ17143_shark s : sharks) {
            int r = s.r;
            int c = s.c;
            int z = s.z;
            int spd = s.s;
            int compression_speed = (s.d == 1 || s.d == 2) ? spd % (2 * (R-1)) : spd % (2 * (C-1));
            int cur_direction = s.d;

            switch (s.d) {
                case 1:
                    if(r == 1) {
                        cur_direction = 2;
                    }
                    break;
                case 2:
                    if(r == R) {
                        cur_direction = 1;
                    }
                    break;
                case 3:
                    if(c == C) {
                        cur_direction = 4;
                    }
                    break;
                case 4:
                    if(c == 1) {
                        cur_direction = 3;
                    }
                    break;
            }

            for(int i=1;i<=compression_speed;i++) {
                switch (cur_direction) {
                    case 1: // ↑
                        r -= 1;

                        if(r == 1) {
                            cur_direction = 2;
                        }
                        break;
                    case 2: // ↓
                        r += 1;

                        if(r == R) {
                            cur_direction = 1;
                        }
                        break;
                    case 3: // →
                        c += 1;

                        if(c == C) {
                            cur_direction = 4;
                        }
                        break;
                    case 4: // ←
                        c -= 1;

                        if(c == 1) {
                            cur_direction = 3;
                        }
                        break;
                }
            }
            ready_sharks.add(new BOJ17143_shark(r,c,spd,cur_direction,z));
        }
        sharks.clear();
    }

    static void select_bigger_sharks() {
        eaten_sharks = new ArrayList<>();
        sharks = ready_sharks;
        for(int r=1;r<=R;r++) {
            for(int c=1;c<=C;c++) {
                fishing[r][c] = new BOJ17143_shark(r,c,0,0,0);
            }
        }

        /*
            무게가 큰 상어가 같은 칸의 모든 상어를 먹는 로직

            if(fishing[s.r][s.c].z < s.z) { ... }만 선언 시, 최초로 상어가 위치하고 다음 상어와 무게를 비교한 상어를 제거하지 못하는 문제가 있다.
            따라서, else { eaten_sharks.add(s) }로 무게가 낮은 상어를 먹는 형태로 제거하는 로직이 필요
         */
        for(BOJ17143_shark s : sharks) {
            if(fishing[s.r][s.c].z < s.z) {
                if(fishing[s.r][s.c].z != 0) {
                    eaten_sharks.add(fishing[s.r][s.c]);
                }
                fishing[s.r][s.c] = s;
            } else {
                eaten_sharks.add(s);
            }
        }

        for(BOJ17143_shark s : eaten_sharks) {
            sharks.remove(s);
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        R = Integer.parseInt(input[0]);
        C = Integer.parseInt(input[1]);
        M = Integer.parseInt(input[2]);

        move = 0;
        weight = 0;

        fishing = new BOJ17143_shark[R+1][C+1];
        sharks = new ArrayList<>();

        for(int r=1;r<=R;r++) {
            for(int c=1;c<=C;c++) {
                fishing[r][c] = new BOJ17143_shark(r,c,0,0,0);
            }
        }

        for(int m=1;m<=M;m++) {
            input = br.readLine().split(" ");
            int r = Integer.parseInt(input[0]);
            int c = Integer.parseInt(input[1]);
            int s = Integer.parseInt(input[2]);
            int d = Integer.parseInt(input[3]);
            int z = Integer.parseInt(input[4]);

            fishing[r][c] = new BOJ17143_shark(r,c,s,d,z);
            sharks.add(fishing[r][c]);
        }
    }
}
