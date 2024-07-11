package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
치킨 배달

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	92333	45468	27496	46.109%
문제
크기가 N×N인 도시가 있다. 도시는 1×1크기의 칸으로 나누어져 있다. 도시의 각 칸은 빈 칸, 치킨집, 집 중 하나이다. 도시의 칸은 (r, c)와 같은 형태로 나타내고, r행 c열 또는 위에서부터 r번째 칸, 왼쪽에서부터 c번째 칸을 의미한다. r과 c는 1부터 시작한다.

이 도시에 사는 사람들은 치킨을 매우 좋아한다. 따라서, 사람들은 "치킨 거리"라는 말을 주로 사용한다. 치킨 거리는 집과 가장 가까운 치킨집 사이의 거리이다. 즉, 치킨 거리는 집을 기준으로 정해지며, 각각의 집은 치킨 거리를 가지고 있다. 도시의 치킨 거리는 모든 집의 치킨 거리의 합이다.

임의의 두 칸 (r1, c1)과 (r2, c2) 사이의 거리는 |r1-r2| + |c1-c2|로 구한다.

예를 들어, 아래와 같은 지도를 갖는 도시를 살펴보자.

0 2 0 1 0
1 0 1 0 0
0 0 0 0 0
0 0 0 1 1
0 0 0 1 2
0은 빈 칸, 1은 집, 2는 치킨집이다.

(2, 1)에 있는 집과 (1, 2)에 있는 치킨집과의 거리는 |2-1| + |1-2| = 2, (5, 5)에 있는 치킨집과의 거리는 |2-5| + |1-5| = 7이다. 따라서, (2, 1)에 있는 집의 치킨 거리는 2이다.

(5, 4)에 있는 집과 (1, 2)에 있는 치킨집과의 거리는 |5-1| + |4-2| = 6, (5, 5)에 있는 치킨집과의 거리는 |5-5| + |4-5| = 1이다. 따라서, (5, 4)에 있는 집의 치킨 거리는 1이다.

이 도시에 있는 치킨집은 모두 같은 프랜차이즈이다. 프렌차이즈 본사에서는 수익을 증가시키기 위해 일부 치킨집을 폐업시키려고 한다. 오랜 연구 끝에 이 도시에서 가장 수익을 많이 낼 수 있는  치킨집의 개수는 최대 M개라는 사실을 알아내었다.

도시에 있는 치킨집 중에서 최대 M개를 고르고, 나머지 치킨집은 모두 폐업시켜야 한다. 어떻게 고르면, 도시의 치킨 거리가 가장 작게 될지 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N(2 ≤ N ≤ 50)과 M(1 ≤ M ≤ 13)이 주어진다.

둘째 줄부터 N개의 줄에는 도시의 정보가 주어진다.

도시의 정보는 0, 1, 2로 이루어져 있고, 0은 빈 칸, 1은 집, 2는 치킨집을 의미한다. 집의 개수는 2N개를 넘지 않으며, 적어도 1개는 존재한다. 치킨집의 개수는 M보다 크거나 같고, 13보다 작거나 같다.

출력
첫째 줄에 폐업시키지 않을 치킨집을 최대 M개를 골랐을 때, 도시의 치킨 거리의 최솟값을 출력한다.

예제 입력 1
5 3
0 0 1 0 0
0 0 2 0 1
0 1 2 0 0
0 0 1 0 0
0 0 0 0 2
예제 출력 1
5
예제 입력 2
5 2
0 2 0 1 0
1 0 1 0 0
0 0 0 0 0
2 0 0 1 1
2 2 0 1 2
예제 출력 2
10
예제 입력 3
5 1
1 2 0 0 0
1 2 0 0 0
1 2 0 0 0
1 2 0 0 0
1 2 0 0 0
예제 출력 3
11
예제 입력 4
5 1
1 2 0 2 1
1 2 0 2 1
1 2 0 2 1
1 2 0 2 1
1 2 0 2 1
예제 출력 4
32
출처
문제를 만든 사람: baekjoon
문제의 오타를 찾은 사람: chaeeun218, wlsgussla123
알고리즘 분류
구현
브루트포스 알고리즘
백트래킹
 */
public class BOJ15686 {
    static class BOJ15686_building {
        int r,c;
        boolean close;

        BOJ15686_building(int r, int c) {
            this.r = r;
            this.c = c;
            this.close = true;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,ans;
    static int[][] cities;
    static ArrayList<BOJ15686_building> houses;
    static ArrayList<BOJ15686_building> chickens;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int i=1;i<=M;i++) {
            dfs(0,i);
        }

        System.out.println(ans);
    }

    static void dfs(int depth, int M) {
        if(depth == M) {
            check_chicken_distance();
            return;
        }

        for(int i=depth;i<chickens.size();i++) {
            if(!chickens.get(i).close) continue;
            chickens.get(i).close = false;
            dfs(depth + 1, M);
            chickens.get(i).close = true;
        }
    }

    static void check_chicken_distance() {
        int total_d = 0;
        for(BOJ15686_building h : houses) {
            int d = Integer.MAX_VALUE;
            for(BOJ15686_building c : chickens) {
                if(!c.close) {
                    d = Math.min(d, Math.abs(h.r - c.r) + Math.abs(h.c - c.c));
                }
            }
            total_d += d;
        }

        ans = Math.min(ans, total_d);
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        houses = new ArrayList<>();
        chickens = new ArrayList<>();

        ans = Integer.MAX_VALUE;

        for(int r=0;r<N;r++) {
            input = br.readLine().split(" ");
            for(int c=0;c<N;c++) {
                String s = input[c];

                if(s.equals("1")) {
                    houses.add(new BOJ15686_building(r,c));
                } else if(s.equals("2")) {
                    chickens.add(new BOJ15686_building(r,c));
                }
            }
        }
    }
}
