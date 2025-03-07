package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
마법사 상어와 파이어볼

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	25605	10523	6464	37.088%
문제
어른 상어가 마법사가 되었고, 파이어볼을 배웠다.

마법사 상어가 크기가 N×N인 격자에 파이어볼 M개를 발사했다. 가장 처음에 파이어볼은 각자 위치에서 이동을 대기하고 있다. i번 파이어볼의 위치는 (ri, ci), 질량은 mi이고, 방향은 di, 속력은 si이다. 위치 (r, c)는 r행 c열을 의미한다.

격자의 행과 열은 1번부터 N번까지 번호가 매겨져 있고, 1번 행은 N번과 연결되어 있고, 1번 열은 N번 열과 연결되어 있다.

파이어볼의 방향은 어떤 칸과 인접한 8개의 칸의 방향을 의미하며, 정수로는 다음과 같다.

7	0	1
6	 	2
5	4	3
마법사 상어가 모든 파이어볼에게 이동을 명령하면 다음이 일들이 일어난다.

모든 파이어볼이 자신의 방향 di로 속력 si칸 만큼 이동한다.
이동하는 중에는 같은 칸에 여러 개의 파이어볼이 있을 수도 있다.
이동이 모두 끝난 뒤, 2개 이상의 파이어볼이 있는 칸에서는 다음과 같은 일이 일어난다.
같은 칸에 있는 파이어볼은 모두 하나로 합쳐진다.
파이어볼은 4개의 파이어볼로 나누어진다.
나누어진 파이어볼의 질량, 속력, 방향은 다음과 같다.
질량은 ⌊(합쳐진 파이어볼 질량의 합)/5⌋이다.
속력은 ⌊(합쳐진 파이어볼 속력의 합)/(합쳐진 파이어볼의 개수)⌋이다.
합쳐지는 파이어볼의 방향이 모두 홀수이거나 모두 짝수이면, 방향은 0, 2, 4, 6이 되고, 그렇지 않으면 1, 3, 5, 7이 된다.
질량이 0인 파이어볼은 소멸되어 없어진다.
마법사 상어가 이동을 K번 명령한 후, 남아있는 파이어볼 질량의 합을 구해보자.

입력
첫째 줄에 N, M, K가 주어진다.

둘째 줄부터 M개의 줄에 파이어볼의 정보가 한 줄에 하나씩 주어진다. 파이어볼의 정보는 다섯 정수 ri, ci, mi, si, di로 이루어져 있다.

서로 다른 두 파이어볼의 위치가 같은 경우는 입력으로 주어지지 않는다.

출력
마법사 상어가 이동을 K번 명령한 후, 남아있는 파이어볼 질량의 합을 출력한다.

제한
4 ≤ N ≤ 50
0 ≤ M ≤ N2
1 ≤ K ≤ 1,000
1 ≤ ri, ci ≤ N
1 ≤ mi ≤ 1,000
1 ≤ si ≤ 1,000
0 ≤ di ≤ 7
예제 입력 1
4 2 1
1 1 5 2 2
1 4 7 1 6
예제 출력 1
8
예제 입력 2
4 2 2
1 1 5 2 2
1 4 7 1 6
예제 출력 2
8
예제 입력 3
4 2 3
1 1 5 2 2
1 4 7 1 6
예제 출력 3
0
예제 입력 4
7 5 3
1 3 5 2 4
2 3 5 2 6
5 2 9 1 7
6 2 1 3 5
4 4 2 4 2
예제 출력 4
9
출처
문제를 만든 사람: baekjoon
알고리즘 분류
구현
시뮬레이션
 */
/*
알고리즘 핵심
구현
1. 파이어볼을 행(r),열(c),질량(m),속도(s),방향(d) 정보를 갖는 구조체를 사용한다.
2. 한번의 명령에 모든 파이어볼이 이동하고 2개 이상의 파이어볼이 위치한 곳에서 분열과정을 수행한다.
3. 파이어볼이 외곽에서 다음 방향으로 이동 시 외곽을 넘어가는 경우 r or c 중 1보다 작거나 N보다 큰 경우 해당하는 좌표의 값이
반전되어 나타낸다.
예시로 나타내면 N = 5일 때, fireball - 4 4 10 7 1
s = 0       s = 1       s = 2       s = 3       s = 4       s = 5       s = 6       s = 7
0 0 0 0 0   0 0 0 0 0   0 0 0 0 0   0 10 0 0 0  0 0 0 0 0   0 0 0 0 0   0 0 0 0 0   0 0 0 0 0
0 0 0 0 0   0 0 0 0 0   10 0 0 0 0  0 0 0 0 0   0 0 0 0 0   0 0 0 0 0   0 0 0 0 0   10 0 0 0 0
0 0 0 0 0   0 0 0 0 10  0 0 0 0 0   0 0 0 0 0   0 0 0 0 0   0 0 0 0 0   0 0 0 0 10  0 0 0 0 0
0 0 0 10 0  0 0 0 0 0   0 0 0 0 0   0 0 0 0 0   0 0 0 0 0   0 0 0 10 0  0 0 0 0 0   0 0 0 0 0
0 0 0 0 0   0 0 0 0 0   0 0 0 0 0   0 0 0 0 0   0 0 10 0 0  0 0 0 0 0   0 0 0 0 0   0 0 0 0 0

즉, r < 1) r = N, c < 1) c = N, r > N) r = 1, c > N) c = 1이 된다.

처음 문제를 읽고 파이어볼의 이동하는 과정에서 잘못 이해하여 코드의 로직을 잘못 작성하였다.
문제를 잘 이해할 수 있도록 해야겠다.
 */
public class BOJ20056 {
    static class BOJ20056_fireball {
        int r,c,m,s,d;

        BOJ20056_fireball(int r, int c, int m, int s, int d) {
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,K,ans;
    static ArrayList<BOJ20056_fireball>[][] field;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        print();
        while(K-- > 0) {
            move_fireball();
            divide_fireball();
            print();
        }
        cal_mass();
    }

    private static void print() {
        for(int r = 1; r <= N; r++) {
            for(int c = 1; c <= N; c++) {
                int m = 0;
                for(BOJ20056_fireball f : field[r][c]) {
                    m += f.m;
                }
                System.out.print(m + "(" + field[r][c].size() +")" + " ");
            }
            System.out.println();
        }
        System.out.println("--");
    }

    private static void cal_mass() {
        for(int r = 1; r <= N; r++) {
            for(int c = 1; c <= N; c++) {
                for(BOJ20056_fireball f : field[r][c]) ans += f.m;
            }
        }

        System.out.println(ans);
    }

    private static void divide_fireball() {
        for(int r = 1; r <= N; r++) {
            for(int c = 1; c <= N; c++) {
                int size = field[r][c].size();

                if(size > 1) {
                    int nm = 0;
                    int ns = 0;
                    boolean nd_even = false;
                    boolean nd_odd = false;

                    for(BOJ20056_fireball f : field[r][c]) {
                        nm += f.m;
                        ns += f.s;

                        if(f.d % 2 == 0) nd_even = true;
                        else nd_odd = true;
                    }

                    nm /= 5;
                    ns /= size;

                    field[r][c].clear();

                    if(nm == 0) continue;

                    for(int i = 1; i <= 4; i++) {
                        if((nd_even && !nd_odd) || (!nd_even && nd_odd)) {
                            field[r][c].add(new BOJ20056_fireball(r,c,nm,ns,(2 * (i - 1))));
                        } else {
                            field[r][c].add(new BOJ20056_fireball(r,c,nm,ns,(2 * (i - 1) + 1)));
                        }
                    }
                }
            }
        }
    }

    private static void move_fireball() {
        int[][] drc = {
                {-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}
        };

        ArrayList<BOJ20056_fireball>[][] new_field = new_fields();

        for(int r = 1; r <= N; r++) {
            for(int c = 1; c <= N; c++) {
                for(BOJ20056_fireball f : field[r][c]) {
                    int nm = f.m;
                    int ns = f.s;
                    int nd = f.d;
                    int nr = f.r + drc[nd][0] * (ns % N);
                    int nc = f.c + drc[nd][1] * (ns % N);

                    if(nr < 1) nr = N - Math.abs(nr);
                    if(nc < 1) nc = N - Math.abs(nc);
                    if(nr > N) nr %= N;
                    if(nc > N) nc %= N;

                    new_field[nr][nc].add(new BOJ20056_fireball(nr,nc,nm,ns,nd));
                }
            }
        }

        field = new_field;
    }

    /*
        fireball의 움직임을 N = 4, (3,3,m,3,1)의 경우 (3,3) -> (2,4) -> (4,2) -> (3,3)와 같이 처음 위치를 기준으로 해당 방향을 바라보는
        직선 형태의 이동으로 순환되는 것으로 생각하여 코드를 작성하였다.

        즉, N = 4, r = 2, c = 3, m = 1, s = 3, d = 1일 때,
        ///o
        //o/
        /o//
        o///
        o 표시된 곳이 해당하는 파이어볼의 순환되는 이동 경로인 것으로 생가하였다.

        하지만, 문제에서 제시하는 "1행은 N행, 1열은 N열과 연결되었다"의 의미는 r or c의 값이 0 or N + 1 값이 되었을 때, 해당하는 좌표를 0 -> N, N + 1 -> 1
        로 변환되는 것을 의미하였다.
     */
    private static void move_fireball_wron_logic() {
        ArrayList<BOJ20056_fireball>[][] new_field = new_fields();

        for(int r = 1; r <= N; r++) {
            for(int c = 1; c <= N; c++) {
                for(BOJ20056_fireball f : field[r][c]) {
                    int nr = f.r;
                    int nc = f.c;
                    int nm = f.m;
                    int ns = f.s;
                    int nd = f.d;
                    int t = ns % N;
                    int a = nr + nc <= N + 1 ? nr + nc - 1 : 2 * N + 1 - nr - nc;
                    int b = N - Math.abs(nr - nc);

                    switch (f.d) {
                        case 0:
                            nr = nr - t < 1 ? N - t + nr : nr - t;

                            new_field[nr][nc].add(new BOJ20056_fireball(nr,nc,nm,ns,nd));
                            break;
                        case 1:
                            t = ns % a;
                            nr -= t;
                            nc += t;

                            if(nr < 1 || nc > N) {
                                nr += a;
                                nc -= a;
                            }

                            new_field[nr][nc].add(new BOJ20056_fireball(nr,nc,nm,ns,nd));
                            break;
                        case 2:
                            nc = nc + t > N ? nc + t - N : nc + t;

                            new_field[nr][nc].add(new BOJ20056_fireball(nr,nc,nm,ns,nd));
                            break;
                        case 3:
                            t = ns % b;
                            nr += t;
                            nc += t;

                            if(nr > N || nc > N) {
                                nr -= b;
                                nc -= b;
                            }

                            new_field[nr][nc].add(new BOJ20056_fireball(nr,nc,nm,ns,nd));
                            break;
                        case 4:
                            nr = nr + t > N ? nr + t - N : nr + t;

                            new_field[nr][nc].add(new BOJ20056_fireball(nr,nc,nm,ns,nd));
                            break;
                        case 5:
                            t = ns % a;
                            nr += t;
                            nc -= t;

                            if(nr > N || nc < 1) {
                                nr -= a;
                                nc += a;
                            }

                            new_field[nr][nc].add(new BOJ20056_fireball(nr,nc,nm,ns,nd));
                            break;
                        case 6:
                            nc = nc - t < 1 ? N + nc - t : nc - t;

                            new_field[nr][nc].add(new BOJ20056_fireball(nr,nc,nm,ns,nd));
                            break;
                        case 7:
                            t = ns % b;
                            nr -= t;
                            nc -= t;

                            if(nr < 1 || nc < 1) {
                                nr += b;
                                nc += b;
                            }

                            new_field[nr][nc].add(new BOJ20056_fireball(nr,nc,nm,ns,nd));
                            break;
                    }
                }
            }
        }

        field = new_field;
    }

    private static ArrayList[][] new_fields() {
        ArrayList<BOJ20056_fireball>[][] new_field = new ArrayList[N + 1][N + 1];

        for(int r = 1; r <= N; r++) {
            for(int c = 1; c <= N; c++) {
                new_field[r][c] = new ArrayList<>();
            }
        }

        return new_field;
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        K = Integer.parseInt(input[2]);

        ans = 0;

        field = new ArrayList[N + 1][N + 1];

        for(int r = 1; r <= N; r++) {
            for(int c = 1; c <= N; c++) {
                field[r][c] = new ArrayList<>();
            }
        }

        for(int i = 0; i < M; i++) {
            input = br.readLine().split(" ");

            int r = Integer.parseInt(input[0]);
            int c = Integer.parseInt(input[1]);
            int m = Integer.parseInt(input[2]);
            int s = Integer.parseInt(input[3]);
            int d = Integer.parseInt(input[4]);

            field[r][c].add(new BOJ20056_fireball(r,c,m,s,d));
        }
    }
}
