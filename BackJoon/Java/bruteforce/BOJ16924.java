package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
십자가 찾기 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	3323	1318	970	38.784%
문제
십자가는 가운데에 '*'가 있고, 상하좌우 방향으로 모두 같은 길이의 '*'가 있는 모양이다. 십자가의 크기는 가운데를 중심으로 상하좌우 방향으로 있는 '*'의 개수이다. 십자가의 크기는 1보다 크거나 같아야 한다.

아래 그림은 크기가 1, 2, 3인 십자가이고, 빈 칸은 '.'이다.

              ...*...
      ..*..   ...*...
.*.   ..*..   ...*...
***   *****   *******
.*.   ..*..   ...*...
      ..*..   ...*...
              ...*...
크기가 N×M이고, '.'과 '*'로 이루어진 격자판이 주어진다. 이때, 십자가만을 이용해서 격자판과 같은 모양을 만들 수 있는지 구해보자. 십자가는 서로 겹쳐도 된다. 사용할 수 있는 십자가의 개수는 N×M이하이어야 한다. 격자판의 행은 위에서부터 1번, 열은 왼쪽에서부터 1번으로 번호가 매겨져 있다.

입력
첫째 줄에 격자판의 크기 N, M (3 ≤ N, M ≤ 100)이 주어진다. 둘째 줄부터 N개의 줄에 격자판의 상태가 주어진다.

출력
십자가만 이용해서 입력으로 주어진 격자판을 만들 수 없으면 -1을 출력한다.

만들 수 있는 경우에는 필요한 십자가의 수 k(0 ≤ k ≤ N×M)를 출력한다. 다음 k개의 줄에는 그려야 하는 십자가의 정보 x, y, s를 한 줄에 하나씩 출력한다. x는 십자가 중심의 행의 번호, y는 열의 번호, s는 십자가의 크기이다.

가능한 답이 여러가지인 경우에는 아무거나 출력한다.

예제 입력 1
6 8
....*...
...**...
..*****.
...**...
....*...
........
예제 출력 1
3
3 4 1
3 5 2
3 5 1
예제 입력 2
5 5
.*...
****.
.****
..**.
.....
예제 출력 2
3
2 2 1
3 3 1
3 4 1
예제 입력 3
5 5
.*...
***..
.*...
.*...
.....
예제 출력 3
-1
예제 입력 4
3 3
*.*
.*.
*.*
예제 출력 4
-1
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
구현
브루트포스 알고리즘
시뮬레이션
 */
/*
이 문제에서 힘들었던 점은 문제를 이해하는데 어려움이 있었다. 예제#1에서 주어진 입력으로 나온 결과가 한 개의 답이 아닐 수 있다는 점이였다.
예를 들어, 결과가 두개가 나올 수 있는데 둘 모두 정답이다.
예제#1        결과
6 8           3         또는      2
....*...      3 4 1              3 4 1
...**...      3 5 2              3 5 2
..*****.      3 5 1
...**...
....*...
........

그리고, 문제의 정답으로 요구되는 조건으로 정답으로 출력되는 십자가의 좌표와 크기에 해당하는 십자가가 원래 입력으로 주어진 모양으로 이루어 져야한다는 것 같다.
처음 접근했던 방법으로는 아래 코드와 같이 모든 가능한 십자가를 출력 리스트에 넣는 방법으로 접근하였는데 이는 출력을 이루는 십자가의 정보들로 N*M 크키의 십자가 정보가
넘어가는 경우 나머지 정보가 누락되어 원래의 입력으로 주어진 십자가 모양을 이룰 수 없었던 것으로 생각된다.
if(possible_max_size >= 1) {
    remained[i][j] = false;

    for(int c=1;c<=possible_max_size;c++) {
        for(int[] d : direction) {
            int ni = i + (d[0] * c);
            int nj = j + (d[1] * c);

            remained[ni][nj] = false;
        }
        storage.add(new BOJ16924_theCross(i,j,c));
        cnt++;
    }
}

결과적으로, 문제에 적힌 "가능한 답이 여러가지인 경우에는 아무거나 출력한다." 의 의미가 "구성할 수 있는 십자가"가 핵심 이라고 생각하고,
최소한으로 개수로 십자가를 이루어 원래 입력을 구성할 수 있는 것과 중복으로 존재하는 십자가도 존재할 수 있는 경우도 정답이기 때문에 이 점이 중요했다.
 */
public class BOJ16924 {
    static class BOJ16924_theCross {
        int x,y,c;

        BOJ16924_theCross(int x, int y, int c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }

        void print() {
            System.out.println(this.x + " " + this.y + " " + this.c);
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static boolean check;
    static int[][] direction = {{-1,0},{1,0},{0,-1},{0,1}};
    static int[][] boards;
    static boolean[][] remained;
    static ArrayList<BOJ16924_theCross> storage;

    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int i=1;i<=N;i++) {
            for(int j=1;j<=M;j++) {
                if(boards[i][j] == 1) {
                    int possible_max_size = Integer.MAX_VALUE;
                    for(int[] d : direction) {
                        int ni = i;
                        int nj = j;
                        int possible_size = 0;

                        while(true) {
                            ni += d[0];
                            nj += d[1];

                            if(ni < 1 || ni > N || nj < 1 || nj > M) {
                                break;
                            }

                            if (boards[ni][nj] == 1) {
                                possible_size++;
                            } else {
                                break;
                            }
                        }

                        possible_max_size = Math.min(possible_size,possible_max_size);
                    }

                    if(possible_max_size >= 1) {
                        storage.add(new BOJ16924_theCross(i,j,possible_max_size));
                        remained[i][j] = false;

                        for(int c=1;c<=possible_max_size;c++) {
                            for(int[] d : direction) {
                                int ni = i + (d[0] * c);
                                int nj = j + (d[1] * c);

                                remained[ni][nj] = false;
                            }
                        }
                    }
                }
            }
        }

        for(int i=1;i<=N && check;i++) {
            for(int j=1;j<=M && check;j++) {
                if(remained[i][j]) {
                    check = false;
                }
            }
        }

        if(check && !storage.isEmpty()) {
            int k = Math.min(storage.size(), (N * M));
            System.out.println(k);

            for(int i=0;i<k;i++) {
                storage.get(i).print();
            }
        } else {
            System.out.println(-1);
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        boards = new int[N+1][M+1];
        remained = new boolean[N+1][M+1];
        sb = new StringBuilder();
        check = true;
        storage = new ArrayList<>();

        for(int i=0;i<N;i++) {
            input = br.readLine().split("");
            for(int j=0;j<M;j++) {
                String s = input[j];
                if(s.equals(".")) {
                    boards[i+1][j+1] = 0;
                    remained[i+1][j+1] = false;
                } else {
                    boards[i+1][j+1] = 1;
                    remained[i+1][j+1] = true;
                }

            }
        }
    }
}
