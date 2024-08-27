package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
텔레포트

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	2334	964	656	38.725%
문제
2차원 평면 위에 N개의 도시가 있다. 일부 도시는 특별한 도시이다. (r1, c1)에 있는 도시에서 (r2, c2)에 있는 도시로 가는 이동 시간은 |r1 - r2| + |c1 - c2|와 같다. 만약, 두 도시가 특별한 도시라면, 텔레포트를 이용해서 이동할 수도 있다. 텔레포트에 걸리는 시간은 T이다.

두 도시의 쌍 M개가 주어졌을 때, 최소 이동 시간을 구해보자.

입력
첫째 줄에 도시의 수 N, 텔레포트하는데 걸리는 시간 T가 주어진다.

둘째 줄부터 N개의 줄에 도시의 정보를 의미하는 세 정수 s, x, y가 1번 도시부터 N번 도시까지 순서대로 주어진다. s가 1인 경우에는 특별한 도시라는 의미이고, 0인 경우는 특별한 도시가 아니라는 의미이다. (x, y)는 도시의 좌표이다.

다음 줄에는 M이 주어지고, 다음 M개의 줄에는 두 도시 A와 B가 주어진다.

출력
총 M개의 줄에 걸쳐서 A에서 B에 가는 최소 이동 시간을 출력한다.

제한
2 ≤ N ≤ 1,000
1 ≤ T ≤ 2,000
1 ≤ M ≤ 1,000
0 ≤ x, y ≤ 1,000
A ≠ B
두 도시의 좌표가 같은 경우는 없다.
예제 입력 1
6 3
0 1 2
0 5 1
1 3 3
1 1 5
0 3 5
1 7 5
5
1 2
1 5
1 6
3 4
4 2
예제 출력 1
5
5
6
3
7
예제 입력 2
6 2
1 1 1
1 1 2
1 1 3
1 2 1
1 2 2
1 2 3
6
1 2
2 3
3 4
4 5
5 6
6 1
예제 출력 2
1
1
2
1
1
2
출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: njw1204
알고리즘 분류
그래프 이론
브루트포스 알고리즘
기하학
최단 경로
플로이드–워셜
 */
/*
알고리즘 핵심
0. 최단 경로 알고리즘 - 플루이드-워셜 알고리즘 이용

플루이드-워셜 알고리즘
1. 모든 지점에서 모든 지점으로의 최단 경로가 필요한 경우 사용
2. 동적계획법을 사용하기 위해 2차원 배열의 지점-지점의 최단 경로 비용을 저장할 배열 사용
3. A-B 사이의 최단 경로는 A-B or A-K-B 중 최소 비용을 선택
3-1. A-K-B는 A에서 B로 가는데 K를 거쳐가는 것을 의미
4. K는 모든 지점을 의미
5. 모든 지점 K, 모든 지점 A, 모든 지점 B 각각 탐색하므로 O(N^3)
 */
public class BOJ16958 {
    static class BOJ16958_city {
        int s,x,y;

        BOJ16958_city(int s, int x, int y) {
            this.s = s;
            this.x = x;
            this.y = y;
        }
    }

    static class BOJ16958_order {
        int a,b;

        BOJ16958_order(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,T,M;
    static int[][] Floyd_Warshall;
    static BOJ16958_city[] cities;
    static BOJ16958_order[] orders;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int x = 1; x <= N; x++) {
            for(int y = 1; y <= N; y++) {
                if(x == y) continue;
                int teleport = Integer.MAX_VALUE;
                int diff_abs = Integer.MAX_VALUE;
                if(cities[x].s == 1 && cities[y].s == 1) {
                    teleport = T;
                }
                diff_abs = Math.abs(cities[x].x - cities[y].x) + Math.abs(cities[x].y - cities[y].y);

                Floyd_Warshall[x][y] = Math.min(diff_abs,teleport);
            }
        }

        for(int n = 1; n <= N; n++) {
            for(int a = 1; a <= N; a++) {
                for(int b = 1; b <= N; b++) {
                    Floyd_Warshall[a][b] = Math.min(Floyd_Warshall[a][b], Floyd_Warshall[a][n] + Floyd_Warshall[n][b]);
                }
            }
        }

        for(BOJ16958_order o : orders) {
            sb.append(Floyd_Warshall[o.a][o.b]).append("\n");
        }

        System.out.println(sb.toString());
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        T = Integer.parseInt(input[1]);

        sb = new StringBuilder();

        Floyd_Warshall = new int[N+1][N+1];
        cities = new BOJ16958_city[N+1];

        for(int i = 1; i <= N; i++) {
            input = br.readLine().split(" ");
            int s = Integer.parseInt(input[0]);
            int x = Integer.parseInt(input[1]);
            int y = Integer.parseInt(input[2]);
            cities[i] = new BOJ16958_city(s,x,y);
        }

        M = Integer.parseInt(br.readLine());

        orders = new BOJ16958_order[M];

        for(int i = 0; i < M; i++) {
            input = br.readLine().split(" ");

            int a = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);

            orders[i] = new BOJ16958_order(a,b);
        }
    }
}
