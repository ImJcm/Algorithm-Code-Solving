package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

/*
돌 그룹

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	12149	3831	2506	28.660%
문제
오늘 강호는 돌을 이용해 재미있는 게임을 하려고 한다. 먼저, 돌은 세 개의 그룹으로 나누어져 있으며 각각의 그룹에는 돌이 A, B, C개가 있다. 강호는 모든 그룹에 있는 돌의 개수를 같게 만들려고 한다.

강호는 돌을 단계별로 움직이며, 각 단계는 다음과 같이 이루어져 있다.

크기가 같지 않은 두 그룹을 고른다. 그 다음, 돌의 개수가 작은 쪽을 X, 큰 쪽을 Y라고 정한다. 그 다음, X에 있는 돌의 개수를 X+X개로, Y에 있는 돌의 개수를 Y-X개로 만든다.

A, B, C가 주어졌을 때, 강호가 돌을 같은 개수로 만들 수 있으면 1을, 아니면 0을 출력하는 프로그램을 작성하시오.

입력
첫째 줄에 A, B, C가 주어진다. (1 ≤ A, B, C ≤ 500)

출력
돌을 같은 개수로 만들 수 있으면 1을, 아니면 0을 출력한다.

예제 입력 1
10 15 35
예제 출력 1
1
예제 입력 2
1 1 2
예제 출력 2
0
출처
문제를 번역한 사람: baekjoon
데이터를 추가한 사람: dh0450, xingxing2001
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
문제를 보고 BFS에서 queue에 넣을 돌의 개수의 조합과 넣지 말아야할 조건이 무엇인지 생각하다가 (X<Y) 2X, Y-X의 수식에서 2X = Y - X -> 3X = Y과 같이
수학적 규칙이 있나해서 {1,2,3}, {2,3,4}, {3,4,5}, {4,5,6}의 모든 경우의 수를 통해 조건에 만족하는 규칙성이 존재하는지 찾으려고 하였지만
찾지 못했지만, 경우의 수로 나온 조합의 숫자들을 볼 때 동일한 숫자들의 조합이 중복되어 나타나는 것을 알 수 있었다.
그리고, A,B,C로 주어지는 돌의 개수의 제한으로 500개 이하를 통해서 돌의 조합에서 나올 수 있는 최대의 돌의 개수는 1000개 이하인 것으로 유추할 수 있었다.
이를 통해 visited[][][] 3차 배열로 선언하고 조건에 맞는 BFS를 수행하여 나온 돌의 조합에 해당하는 v[A][B][C] = v[B][C][A] = v[C][A][B]
함으로써 queue에 넣을 돌의 조합을 걸러낼 수 있다. 이때, q가 모두 비워질 때 조합으로 만들어지는 경우에서 세 돌의 개수가 같아지는 경우가 없다는 것을
의미하므로 0을 출력한다. 반대로, 돌의 개수가 같아지는 경우가 발생할 경우 1을 나머지 조건 검사는 불필요하므로 queue를 모두 비우고 1을 출력한다.

1. 메모리 초과 발생
원인 : 1. 너무 많은 변수들을 배열에 저장하는 경우, 2. 재귀적 호출로 많은 함수를 호출할 경우

메모리 초과를 해결하는 방법이 생각나질 않아서 정답코드 참고하였다.

해결법
1. A,B,C 돌 개수의 방문여부를 나타내는 배열을 3차배열이 아닌 2차원 배열을 사용한다.
2. 처음 주어지는 돌의 개수의 합이 3으로 나누어떨어지지 않으면 세 돌의 개수는 같아질 수 없다.
3. 총 돌의 개수는 고정이다

이때, visited의 원소 크기를 왜 1500까지 해야하는지 이해를 못했는데
500 500 499일 때, 번갈아가면서 x+x, y-x를 반복하다가 748, 1, 750 -> 1496,1,2까지 될 수 있기 때문에 1500까지 크기로 가져야 한다.
 */
public class BOJ12886 {
    static class BOJ12886_group {
        int a,b,c;

        public BOJ12886_group(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int A_s, B_s, C_s;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        System.out.println(bfs(A_s,B_s,C_s));
    }

    static int bfs(int a, int b, int c) {
        Queue<BOJ12886_group> q = new LinkedList<>();

        q.add(new BOJ12886_group(a,b,c));
        visited[a][b] = true;

        if((a + b + c) % 3 != 0) {
            return 0;
        }

        while(!q.isEmpty()) {
            BOJ12886_group n = q.poll();

            if(n.a == n.b && n.b == n.c) {
                return 1;
            }

            for(int i=0;i<3;i++) {
                switch (i) {
                    case 0:
                        if(n.a != n.b) {
                            int na = n.a > n.b ? n.a - n.b : n.a + n.a;
                            int nb = n.a > n.b ? n.b + n.b : n.b - n.a;

                            if(!visited[na][nb]) {
                                visited[na][nb] = true;
                                q.add(new BOJ12886_group(na,nb,n.c));
                            }
                        }
                        break;
                    case 1:
                        if(n.b != n.c) {
                            int nb = n.b > n.c ? n.b - n.c : n.b + n.b;
                            int nc = n.b > n.c ? n.c + n.c : n.c - n.b;

                            if(!visited[nb][nc]) {
                                visited[nb][nc] = true;
                                q.add(new BOJ12886_group(n.a,nb,nc));
                            }
                        }
                        break;
                    case 2:
                        if(n.a != n.c) {
                            int na = n.a > n.c ? n.a - n.c : n.a + n.a;
                            int nc = n.a > n.c ? n.c + n.c : n.c - n.a;

                            if(!visited[na][nc]) {
                                visited[na][nc] = true;
                                q.add(new BOJ12886_group(na,n.b,nc));
                            }
                        }
                        break;
                }
            }
        }

        return 0;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        A_s = Integer.parseInt(input[0]);
        B_s = Integer.parseInt(input[1]);
        C_s = Integer.parseInt(input[2]);

        visited = new boolean[1501][1501];

        /*for(int i=0;i<1501;i++) {
            for(int j=0;j<1501;j++) {
                visited[i][j] = false;
            }
        }*/
    }

    //메모리 초과 발생 코드
    public static class BOJ12886_failure {
        public static class BOJ12886_group {
            int A_stones;
            int B_stones;
            int C_stones;

            public BOJ12886_group(int a,int b,int c) {
                this.A_stones = a;
                this.B_stones = b;
                this.C_stones = c;
            }

            void setAStones(int s) {
                this.A_stones = s;
            }

            void setBStones(int s) {
                this.B_stones = s;
            }

            void setCStones(int s) {
                this.C_stones = s;
            }
        }

        static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        static int A_stones,B_stones,C_stones;
        static BOJ12886_group init_group;
        //static boolean[][][] visited;
        static boolean[][] A_visited;
        static boolean[][] B_visited;
        static boolean[][] C_visited;

        public static void main(String[] args) throws IOException {
            init_setting();

            solve();
        }

        static void solve() {
            int result = bfs();

            System.out.println(result);
        }

        static int bfs() {
            Queue<BOJ12886_group> q = new LinkedList<>();

            q.offer(init_group);
            /*visited[A_stones][B_stones][C_stones]
                = visited[B_stones][C_stones][A_stones]
                = visited[C_stones][A_stones][B_stones]
                = true;*/
            for(int i=0;i<3;i++) {
                A_visited[i][A_stones]
                        = B_visited[i][B_stones]
                        = C_visited[i][C_stones]
                        = true;
            }

            while(!q.isEmpty()) {
                BOJ12886_group g = q.poll();

                //{A,B}, {A,C}, {B,C} - 3가지의 조건 검사
                for(int i=0;i<3;i++) {
                    BOJ12886_group new_g = new BOJ12886_group(0,0,0);
                    switch (i) {
                        case 0:
                        /*if(g.A_stones < g.B_stones) {
                            new_g.A_stones = (g.A_stones * 2);
                            new_g.B_stones = (g.B_stones - g.A_stones);
                            new_g.C_stones = (g.C_stones);
                        } else if(g.A_stones > g.B_stones){
                            new_g.A_stones = (g.A_stones - g.B_stones);
                            new_g.B_stones = (g.B_stones * 2);
                            new_g.C_stones = (g.C_stones);
                        }*/
                            if(g.A_stones < g.B_stones) {
                                new_g.setAStones(g.A_stones * 2);
                                new_g.setBStones(g.B_stones - g.A_stones);
                                new_g.setCStones(g.C_stones);
                            } else if(g.A_stones > g.B_stones){
                                new_g.setAStones(g.A_stones - g.B_stones);
                                new_g.setBStones(g.B_stones * 2);
                                new_g.setCStones(g.C_stones);
                            }
                            break;
                        case 1:
                        /*if(g.A_stones < g.C_stones) {
                            new_g.A_stones = (g.A_stones * 2);
                            new_g.B_stones = (g.B_stones);
                            new_g.C_stones = (g.C_stones - g.A_stones);
                        } else if(g.A_stones > g.C_stones){
                            new_g.A_stones = (g.A_stones - g.C_stones);
                            new_g.B_stones = (g.B_stones);
                            new_g.C_stones = (g.C_stones * 2);
                        }*/
                            if(g.A_stones < g.C_stones) {
                                new_g.setAStones(g.A_stones * 2);
                                new_g.setBStones(g.B_stones);
                                new_g.setCStones(g.C_stones - g.A_stones);
                            } else if(g.A_stones > g.C_stones){
                                new_g.setAStones(g.A_stones - g.C_stones);
                                new_g.setBStones(g.B_stones);
                                new_g.setCStones(g.C_stones * 2);
                            }
                            break;
                        case 2:
                        /*if(g.B_stones < g.C_stones) {
                            new_g.A_stones = (g.A_stones);
                            new_g.B_stones = (g.B_stones * 2);
                            new_g.C_stones = (g.C_stones - g.B_stones);
                        } else if(g.B_stones > g.C_stones){
                            new_g.A_stones = (g.A_stones);
                            new_g.B_stones = (g.B_stones - g.C_stones);
                            new_g.C_stones = (g.C_stones * 2);
                        }*/
                            if(g.B_stones < g.C_stones) {
                                new_g.setAStones(g.A_stones);
                                new_g.setBStones(g.B_stones * 2);
                                new_g.setCStones(g.C_stones - g.B_stones);
                            } else if(g.B_stones > g.C_stones){
                                new_g.setAStones(g.A_stones);
                                new_g.setBStones(g.B_stones - g.C_stones);
                                new_g.setCStones(g.C_stones * 2);
                            }
                            break;
                    }

                    if(new_g.A_stones == 0 || new_g.B_stones == 0 || new_g.C_stones == 0) {
                        continue;
                    }

                    if(new_g.A_stones == new_g.B_stones && new_g.B_stones == new_g.C_stones) {
                        q.clear();
                        return 1;
                    }

                /*if(visited[new_g.A_stones][new_g.B_stones][new_g.C_stones] ||
                        visited[new_g.B_stones][new_g.C_stones][new_g.A_stones] ||
                        visited[new_g.C_stones][new_g.A_stones][new_g.B_stones]) {
                    continue;
                }*/
                    for(int j=0;j<3;j++) {
                        if(A_visited[j][new_g.A_stones] && B_visited[j][new_g.B_stones] && C_visited[j][new_g.C_stones]) {
                            continue;
                        }

                        A_visited[i][new_g.A_stones] = B_visited[i][new_g.B_stones] = C_visited[i][new_g.C_stones] = true;
                    }

                /*visited[new_g.A_stones][new_g.B_stones][new_g.C_stones]
                        = visited[new_g.B_stones][new_g.C_stones][new_g.A_stones]
                        = visited[new_g.C_stones][new_g.A_stones][new_g.B_stones]
                        = true;*/


                    q.offer(new_g);
                }
            }

            return 0;

        }

        static void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            A_stones = Integer.parseInt(input[0]);
            B_stones = Integer.parseInt(input[1]);
            C_stones = Integer.parseInt(input[2]);

            //visited = new boolean[1001][1001][1001];
            A_visited = new boolean[3][1001];
            B_visited = new boolean[3][1001];
            C_visited = new boolean[3][1001];

        /*IntStream.range(0,1001)
                .forEach(A -> IntStream.range(0,1001)
                        .forEach(B -> IntStream.range(0,1001)
                                .forEach(C -> visited[A][B][C] = false)));*/

        /*for(int i=0;i<1001;i++) {
            for(int j=0;j<1001;j++) {
                for(int k=0;k<1001;k++) {
                    visited[i][j][k] = false;
                }
            }
        }*/

            for(int i=0;i<3;i++) {
                for(int j=0;j<1001;j++) {
                    A_visited[i][j] = B_visited[i][j] = C_visited[i][j] = false;
                }
            }

            init_group = new BOJ12886_group(A_stones,B_stones,C_stones);
        }
    }
}


