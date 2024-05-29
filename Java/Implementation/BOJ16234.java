package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
인구 이동

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	71216	30009	17616	38.875%
문제
N×N크기의 땅이 있고, 땅은 1×1개의 칸으로 나누어져 있다. 각각의 땅에는 나라가 하나씩 존재하며, r행 c열에 있는 나라에는 A[r][c]명이 살고 있다. 인접한 나라 사이에는 국경선이 존재한다. 모든 나라는 1×1 크기이기 때문에, 모든 국경선은 정사각형 형태이다.

오늘부터 인구 이동이 시작되는 날이다.

인구 이동은 하루 동안 다음과 같이 진행되고, 더 이상 아래 방법에 의해 인구 이동이 없을 때까지 지속된다.

국경선을 공유하는 두 나라의 인구 차이가 L명 이상, R명 이하라면, 두 나라가 공유하는 국경선을 오늘 하루 동안 연다.
위의 조건에 의해 열어야하는 국경선이 모두 열렸다면, 인구 이동을 시작한다.
국경선이 열려있어 인접한 칸만을 이용해 이동할 수 있으면, 그 나라를 오늘 하루 동안은 연합이라고 한다.
연합을 이루고 있는 각 칸의 인구수는 (연합의 인구수) / (연합을 이루고 있는 칸의 개수)가 된다. 편의상 소수점은 버린다.
연합을 해체하고, 모든 국경선을 닫는다.
각 나라의 인구수가 주어졌을 때, 인구 이동이 며칠 동안 발생하는지 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N, L, R이 주어진다. (1 ≤ N ≤ 50, 1 ≤ L ≤ R ≤ 100)

둘째 줄부터 N개의 줄에 각 나라의 인구수가 주어진다. r행 c열에 주어지는 정수는 A[r][c]의 값이다. (0 ≤ A[r][c] ≤ 100)

인구 이동이 발생하는 일수가 2,000번 보다 작거나 같은 입력만 주어진다.

출력
인구 이동이 며칠 동안 발생하는지 첫째 줄에 출력한다.

예제 입력 1
2 20 50
50 30
20 40
예제 출력 1
1
초기 상태는 아래와 같다.



L = 20, R = 50 이기 때문에, 모든 나라 사이의 국경선이 열린다. (열린 국경선은 점선으로 표시)



연합은 하나 존재하고, 연합의 인구는 (50 + 30 + 20 + 40) 이다. 연합의 크기가 4이기 때문에, 각 칸의 인구수는 140/4 = 35명이 되어야 한다.



예제 입력 2
2 40 50
50 30
20 40
예제 출력 2
0
경계를 공유하는 나라의 인구 차이가 모두 L보다 작아서 인구 이동이 발생하지 않는다.

예제 입력 3
2 20 50
50 30
30 40
예제 출력 3
1
초기 상태는 아래와 같다.



L = 20, R = 50이기 때문에, 아래와 같이 국경선이 열린다.



인구 수는 합쳐져있는 연합의 인구수는 (50+30+30) / 3 = 36 (소수점 버림)이 되어야 한다.



예제 입력 4
3 5 10
10 15 20
20 30 25
40 22 10
예제 출력 4
2
예제 입력 5
4 10 50
10 100 20 90
80 100 60 70
70 20 30 40
50 20 100 10
예제 출력 5
3
출처
문제를 만든 사람: baekjoon
알고리즘 분류
구현
그래프 이론
그래프 탐색
시뮬레이션
너비 우선 탐색
 */
/*
solve() 내부에서 무한 루프를 수행하고 연합을 N*N 2차원 배열로 BFS 알고리즘을 사용하여 연합번호를 지정하고 연합에 해당하는 국가들을 cs 배열에 담아
해당 연합 국가들의 인구 이동을 수행하는 move_population()를 수행한다. 이때,연합을 나누는 번호(union_number)가 모든 국가의 수 + 1에 해당하면
국경선이 열린 경우가 없는 것이므로 기저사례로 days를 출력하고 반복문을 종료한다. 연합을 나누는 번호가 모든 국가의 수 + 1에 해당하지 않는다면
국경선이 열려 있는 경우이므로 days를 증가시키고 위의 과정을 반복한다.
 */
public class BOJ16234 {
    static class BOJ16234_country {
        int r,c;
        int population;

        BOJ16234_country(int r,int c,int p) {
            this.r = r;
            this.c = c;
            this.population = p;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,L,R,days;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};
    static boolean[][] visited;
    static ArrayList<BOJ16234_country> cs;
    static BOJ16234_country[][] A;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        int union_number;

        while(true) {
            union_number = 1;

            for(int i=1;i<=N;i++) {
                Arrays.fill(visited[i],false);
            }

            for(int i=1;i<=N;i++) {
                for(int j=1;j<=N;j++) {
                    if(visited[i][j]) continue;
                    union_number++;
                    cs = new ArrayList<>();
                    union_separation(A[i][j]);
                    move_population();
                }
            }

            if(union_number == ((N*N) + 1)) {
                System.out.println(days);
                break;
            }
            days++;
        }
    }

    static void union_separation(BOJ16234_country c) {
        Queue<BOJ16234_country> q = new LinkedList<>();

        q.offer(c);
        visited[c.r][c.c] = true;

        while(!q.isEmpty()) {
            BOJ16234_country now = q.poll();
            cs.add(now);

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr <= 0 || nr > N || nc <= 0 || nc > N || visited[nr][nc]) continue;

                int population_diff = Math.abs(now.population-A[nr][nc].population);
                if(L <= population_diff && population_diff <= R) {
                    visited[nr][nc] = true;
                    q.offer(A[nr][nc]);
                }
            }
        }
    }

    static void move_population() {
        int p_sum = 0;
        for(BOJ16234_country c : cs) {
            p_sum += c.population;
        }

        for(BOJ16234_country c : cs) {
            A[c.r][c.c].population = p_sum / cs.size();
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        L = Integer.parseInt(input[1]);
        R = Integer.parseInt(input[2]);

        A = new BOJ16234_country[N+1][N+1];
        visited = new boolean[N+1][N+1];

        for(int i=1;i<=N;i++) {
            input = br.readLine().split(" ");
            for(int j=1;j<=N;j++) {
                A[i][j] = new BOJ16234_country(i,j,Integer.parseInt(input[j-1]));
                visited[i][j] = false;
            }
        }

        days = 0;
    }
}
