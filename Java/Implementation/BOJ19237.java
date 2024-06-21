package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

/*
어른 상어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	15690	6695	4156	39.985%
문제
청소년 상어는 더욱 자라 어른 상어가 되었다. 상어가 사는 공간에 더 이상 물고기는 오지 않고 다른 상어들만이 남아있다. 상어에는 1 이상 M 이하의 자연수 번호가 붙어 있고, 모든 번호는 서로 다르다. 상어들은 영역을 사수하기 위해 다른 상어들을 쫓아내려고 하는데, 1의 번호를 가진 어른 상어는 가장 강력해서 나머지 모두를 쫓아낼 수 있다.

N×N 크기의 격자 중 M개의 칸에 상어가 한 마리씩 들어 있다. 맨 처음에는 모든 상어가 자신의 위치에 자신의 냄새를 뿌린다. 그 후 1초마다 모든 상어가 동시에 상하좌우로 인접한 칸 중 하나로 이동하고, 자신의 냄새를 그 칸에 뿌린다. 냄새는 상어가 k번 이동하고 나면 사라진다.

각 상어가 이동 방향을 결정할 때는, 먼저 인접한 칸 중 아무 냄새가 없는 칸의 방향으로 잡는다. 그런 칸이 없으면 자신의 냄새가 있는 칸의 방향으로 잡는다. 이때 가능한 칸이 여러 개일 수 있는데, 그 경우에는 특정한 우선순위를 따른다. 우선순위는 상어마다 다를 수 있고, 같은 상어라도 현재 상어가 보고 있는 방향에 따라 또 다를 수 있다. 상어가 맨 처음에 보고 있는 방향은 입력으로 주어지고, 그 후에는 방금 이동한 방향이 보고 있는 방향이 된다.

모든 상어가 이동한 후 한 칸에 여러 마리의 상어가 남아 있으면, 가장 작은 번호를 가진 상어를 제외하고 모두 격자 밖으로 쫓겨난다.



<그림 1>

우선 순위
상어 1	상어 2	상어 3	상어 4
↑	↓ ← ↑ →	↑	↓ → ← ↑	↑	→ ← ↓ ↑	↑	← → ↑ ↓
↓	→ ↑ ↓ ←	↓	↓ ↑ ← →	↓	↑ → ← ↓	↓	← ↓ → ↑
←	← → ↓ ↑	←	← → ↑ ↓	←	↑ ← ↓ →	←	↑ → ↓ ←
→	→ ← ↑ ↓	→	→ ↑ ↓ ←	→	← ↓ ↑ →	→	↑ → ↓ ←
<표 1>

<그림 1>은 맨 처음에 모든 상어가 자신의 냄새를 뿌린 상태를 나타내며, <표 1>에는 각 상어 및 현재 방향에 따른 우선순위가 표시되어 있다. 이 예제에서는 k = 4이다. 왼쪽 하단에 적힌 정수는 냄새를 의미하고, 그 값은 사라지기까지 남은 시간이다. 좌측 상단에 적힌 정수는 상어의 번호 또는 냄새를 뿌린 상어의 번호를 의미한다.



<그림 2>



<그림 3>

<그림 2>는 모든 상어가 한 칸 이동하고 자신의 냄새를 뿌린 상태이고, <그림 3>은 <그림 2>의 상태에서 한 칸 더 이동한 것이다. (2, 4)에는 상어 2과 4가 같이 도달했기 때문에, 상어 4는 격자 밖으로 쫓겨났다.



<그림 4>



<그림 5>

<그림 4>은 격자에 남아있는 모든 상어가 한 칸 이동하고 자신의 냄새를 뿌린 상태, <그림 5>는 <그림 4>에서 한 칸 더 이동한 상태를 나타낸다. 상어 2는 인접한 칸 중에 아무 냄새도 없는 칸이 없으므로 자신의 냄새가 들어있는 (2, 4)으로 이동했다. 상어가 이동한 후에, 맨 처음에 각 상어가 뿌린 냄새는 사라졌다.

이 과정을 반복할 때, 1번 상어만 격자에 남게 되기까지 몇 초가 걸리는지를 구하는 프로그램을 작성하시오.

입력
첫 줄에는 N, M, k가 주어진다. (2 ≤ N ≤ 20, 2 ≤ M ≤ N2, 1 ≤ k ≤ 1,000)

그 다음 줄부터 N개의 줄에 걸쳐 격자의 모습이 주어진다. 0은 빈칸이고, 0이 아닌 수 x는 x번 상어가 들어있는 칸을 의미한다.

그 다음 줄에는 각 상어의 방향이 차례대로 주어진다. 1, 2, 3, 4는 각각 위, 아래, 왼쪽, 오른쪽을 의미한다.

그 다음 줄부터 각 상어의 방향 우선순위가 상어 당 4줄씩 차례대로 주어진다. 각 줄은 4개의 수로 이루어져 있다. 하나의 상어를 나타내는 네 줄 중 첫 번째 줄은 해당 상어가 위를 향할 때의 방향 우선순위, 두 번째 줄은 아래를 향할 때의 우선순위, 세 번째 줄은 왼쪽을 향할 때의 우선순위, 네 번째 줄은 오른쪽을 향할 때의 우선순위이다. 각 우선순위에는 1부터 4까지의 자연수가 한 번씩 나타난다. 가장 먼저 나오는 방향이 최우선이다. 예를 들어, 우선순위가 1 3 2 4라면, 방향의 순서는 위, 왼쪽, 아래, 오른쪽이다.

맨 처음에는 각 상어마다 인접한 빈 칸이 존재한다. 따라서 처음부터 이동을 못 하는 경우는 없다.

출력
1번 상어만 격자에 남게 되기까지 걸리는 시간을 출력한다. 단, 1,000초가 넘어도 다른 상어가 격자에 남아 있으면 -1을 출력한다.

예제 입력 1
5 4 4
0 0 0 0 3
0 2 0 0 0
1 0 0 0 4
0 0 0 0 0
0 0 0 0 0
4 4 3 1
2 3 1 4
4 1 2 3
3 4 2 1
4 3 1 2
2 4 3 1
2 1 3 4
3 4 1 2
4 1 2 3
4 3 2 1
1 4 3 2
1 3 2 4
3 2 1 4
3 4 1 2
3 2 4 1
1 4 2 3
1 4 2 3
예제 출력 1
14
문제에 나온 그림과 같다.

예제 입력 2
4 2 6
1 0 0 0
0 0 0 0
0 0 0 0
0 0 0 2
4 3
1 2 3 4
2 3 4 1
3 4 1 2
4 1 2 3
1 2 3 4
2 3 4 1
3 4 1 2
4 1 2 3
예제 출력 2
26
예제 입력 3
5 4 1
0 0 0 0 3
0 2 0 0 0
1 0 0 0 4
0 0 0 0 0
0 0 0 0 0
4 4 3 1
2 3 1 4
4 1 2 3
3 4 2 1
4 3 1 2
2 4 3 1
2 1 3 4
3 4 1 2
4 1 2 3
4 3 2 1
1 4 3 2
1 3 2 4
3 2 1 4
3 4 1 2
3 2 4 1
1 4 2 3
1 4 2 3
예제 출력 3
-1
예제 입력 4
5 4 10
0 0 0 0 3
0 0 0 0 0
1 2 0 0 0
0 0 0 0 4
0 0 0 0 0
4 4 3 1
2 3 1 4
4 1 2 3
3 4 2 1
4 3 1 2
2 4 3 1
2 1 3 4
3 4 1 2
4 1 2 3
4 3 2 1
1 4 3 2
1 3 2 4
3 2 1 4
3 4 1 2
3 2 4 1
1 4 2 3
1 4 2 3
예제 출력 4
-1
출처
문제를 만든 사람: baekjoon
알고리즘 분류
구현
시뮬레이션

예제의 그림은 https://www.acmicpc.net/problem/19237 에서 참고
 */
public class BOJ19237 {
    static class BOJ19237_shark {
        int r,c,n,d;

        BOJ19237_shark(int r, int c, int n, int d) {
            this.r = r;
            this.c = c;
            this.n = n;
            this.d = d;
        }
    }
    static class BOJ19237_space {
        int n,k;

        BOJ19237_space(int n, int k) {
            this.n = n;
            this.k = k;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,K,T;
    static boolean flag;
    static BOJ19237_space[][] space;
    static ArrayList<BOJ19237_shark> sharks;
    static int[][][] priority_directions;
    static int[][] directions = {{0,0},{-1,0},{1,0},{0,-1},{0,1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        T = 0;

        while(T++ <= 1000 && !flag) {
            move_sharks();
            smell_deduction();
            smelling_sharks();
            is_there_one_shark_check();
        }

        /*while(T <= 1000 && !is_there_one_shark_check()) {
            move_sharks();
            smell_deduction();
            smelling_sharks();
            T++;
        }*/

        /*
            T가 1000초일 때, 상어#1만 남게될 경우, 1000을 출력한 후, T는 1001된다.
            이때, -1 출력 조건으로 (T > 1001)만 조건을 세우면 1000출력과 -1출력이 동시에 이루어져 6% 또는 26%에서 WA처리가 된다.
            따라서,
         */

        if(T > 1000 && !flag) {
            System.out.println(-1);
        }
    }

    static void is_there_one_shark_check() {
        if(sharks.size() == 1) {
            System.out.println(T);
            flag = true;
        }
    }

    /*static boolean is_there_one_shark_check() {
        if(sharks.size() == 1) {
            System.out.println(T);
            return true;
        }
        return false;
    }*/

    static void smell_deduction() {
        for(int r=0;r<N;r++) {
            for(int c=0;c<N;c++) {
                if(space[r][c].n != 0) {
                    space[r][c].k -= 1;

                    if(space[r][c].k == 0) {
                        space[r][c].n = 0;
                        space[r][c].k = 0;
                    }
                }
            }
        }
    }

    /*
        sharks 내부의 shark 객체들이 n을 기준으로 오름차순으로 정렬되어 있어야 같은 장소에 있는 상어들을 방출할 수 있다.
     */
    static void smelling_sharks() {
        ArrayList<BOJ19237_shark> kicked_out_sharks = new ArrayList<>();

        for(BOJ19237_shark s : sharks) {
            int n = s.n;
            int r = s.r;
            int c = s.c;

            /*if(space[r][c].n == 0) {
                space[r][c].k = K;
                space[r][c].n = n;
            } else {
                if(space[r][c].k == K) {
                    if(space[r][c].n < n) {
                        sharks.remove(s);
                    } else {
                        // space[r][c].n >= n의 경우로, sharks의 상어들의 순서는 n이 낮은 순서를 보장하고
                        // 해당 공간이 k가 K로 업데이트된 상태라면 n이 작은 상어가 이미 스멜링을 마친 공간이므로
                        // space[r][c].n이 n보다 큰 경우가 올 수 없다. (상어의 넘버링은 중복이 없으므로)
                    }
                } else {
                    space[r][c].n = n;
                    space[r][c].k = K;
                }
            }*/
            if(space[r][c].k == K && space[r][c].n < n) {
                kicked_out_sharks.add(s);
                continue;
            }

            space[r][c].n = n;
            space[r][c].k = K;
        }

        for(BOJ19237_shark s : kicked_out_sharks) {
            sharks.remove(s);
        }
    }

    static void move_sharks() {
        ArrayList<Integer> empty_space;
        ArrayList<Integer> smelled_space;

        for(BOJ19237_shark s : sharks) {
            empty_space = new ArrayList<>();
            smelled_space = new ArrayList<>();

            for(int d=1;d<=4;d++) {
                int nr = s.r + directions[d][0];
                int nc = s.c + directions[d][1];

                if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;

                if(space[nr][nc].n == 0) {
                    empty_space.add(d);
                } else if(space[nr][nc].n == s.n) {
                    smelled_space.add(d);
                }
            }

            select_next_space(s, empty_space, smelled_space);
        }
    }

    static void select_next_space(BOJ19237_shark s, ArrayList<Integer> empty_space, ArrayList<Integer> smelled_space) {
        int d = 0;
        if(empty_space.size() > 1) {
            for(int p=0;p<4;p++) {
                d = priority_directions[s.n][s.d][p];

                if(empty_space.contains(d)) {
                    break;
                }
            }
        } else if(empty_space.size() == 1){
            d = empty_space.get(0);
        } else {
            if(smelled_space.size() > 1) {
                for(int p=0;p<4;p++) {
                    d = priority_directions[s.n][s.d][p];

                    if(smelled_space.contains(d)) {
                        break;
                    }
                }
            } else if(smelled_space.size() == 1) {
                d = smelled_space.get(0);
            } else {
                // 빈 공간도 없고, 해당 상어의 냄새가 남은 공간이 없는 경우
                // 이런 경우가 있을 수 있나?
            }
        }

        update_position_shark(s, d);
    }

    static void update_position_shark(BOJ19237_shark s, int d) {
        s.r += directions[d][0];
        s.c += directions[d][1];
        s.d = d;
    }

    static void print() {
        System.out.println("# space -n | k-------------------- #");
        for(int r=0;r<N;r++) {
            for(int c=0;c<N;c++) {
                System.out.print(space[r][c].n + " ");
            }

            System.out.print("  |  ");

            for(int c=0;c<N;c++) {
                System.out.print(space[r][c].k + " ");
            }
            System.out.println();
        }
        System.out.println("##### space end -n | k-------------------- #");

        /*for(BOJ19237_shark s : sharks) {
            System.out.println("#shark" + s.n + " | r : " + s.r + " | c : " + s.c + " | d : " + s.d);
        }*/

        System.out.println("# sharks -------------------- #");
        for(int r=0;r<N;r++) {
            for(int c=0;c<N;c++) {
                if(space[r][c].k == K) {
                    System.out.print(space[r][c].n + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
        System.out.println("##### sharks end -------------------- #");
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        K = Integer.parseInt(input[2]);

        flag = false;
        space = new BOJ19237_space[N][N];
        sharks = new ArrayList<>();
        priority_directions = new int[M+1][5][4];

        for(int r=0;r<N;r++) {
            input = br.readLine().split(" ");
            for(int c=0;c<N;c++) {
                int n = Integer.parseInt(input[c]);

                space[r][c] = new BOJ19237_space(n,0);

                if(n != 0) {
                    space[r][c].k = K;
                    sharks.add(new BOJ19237_shark(r,c,n,0));
                }
            }
        }

        // 상어의 순서를 n을 기준으로 오름차순
        sharks.sort(new Comparator<BOJ19237_shark>() {
            @Override
            public int compare(BOJ19237_shark o1, BOJ19237_shark o2) {
                return o1.n - o2.n;
            }
        });

        input = br.readLine().split(" ");
        for(int i=0;i<M;i++) {
            sharks.get(i).d = Integer.parseInt(input[i]);
        }

        for(int m=1;m<=M;m++) {
            for(int d=1;d<=4;d++) {
                input = br.readLine().split(" ");

                // (1,↑), (2,↓), (3,←), (4,→)
                for(int k=0;k<4;k++) {
                    int direction = Integer.parseInt(input[k]);

                    priority_directions[m][d][k] = direction;
                }
            }
        }
    }
}
