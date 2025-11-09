package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/*
가장 가까운 두 점

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	49330	9005	4796	16.896%
문제
2차원 평면상에 n개의 점이 주어졌을 때, 이 점들 중 가장 가까운 두 점을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 자연수 n(2 ≤ n ≤ 100,000)이 주어진다. 다음 n개의 줄에는 차례로 각 점의 x, y좌표가 주어진다. 각각의 좌표는 절댓값이 10,000을 넘지 않는 정수이다. 여러 점이 같은 좌표를 가질 수도 있다.

출력
첫째 줄에 가장 가까운 두 점의 거리의 제곱을 출력한다.

예제 입력 1
4
0 0
10 10
0 10
10 0
예제 출력 1
100
출처
데이터를 추가한 사람: august14, Diuven, dummymon, h0ngjun7, jang010505, Juno, pentagon03, pichulia
어색한 표현을 찾은 사람: jh05013
알고리즘 분류
기하학
스위핑
분할 정복
 */
public class BOJ2261 {
    static class pos implements Comparable<pos> {
        int x,y;

        pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(pos o) {
            int x = this.x - o.x;
            if(x < 0) return -1;
            else if(x == 0) return this.y - o.y;
            else return 1;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static ArrayList<pos> ps;

    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();

    }

    public static class Solve {
        int min_dist;
        pos s,e;

        private void solve() throws IOException {
            init_setting();

            //bruteforce();
            sweeping();

            System.out.println(min_dist);
        }

        /*
            오답
         */
        private void sweeping() {
            Collections.sort(ps);

            choice_min_dist_point(ps.get(0),ps.get(1));

            for(int i = 2; i < N; i++) {
                pos c = ps.get(i);

                choice_min_dist_point(s,c);
                choice_min_dist_point(e,c);
            }
        }

        private void choice_min_dist_point(pos p1, pos p2) {
            int d = pow_distance_twopoint(p1,p2);

            if(d < min_dist) {
                min_dist = d;
                s = p1;
                e = p2;
            }
        }

        /*
            오답 예상 : 10^(5 * 2) = O(N^2)
         */
        private void bruteforce() {
            for(int i = 0; i < N - 1; i++) {
                for(int j = i + 1; j < N; j++) {
                    int dist = pow_distance_twopoint(ps.get(i), ps.get(j));

                    if(dist < min_dist) min_dist = dist;
                }
            }
        }

        private int pow_distance_twopoint(pos p1, pos p2) {
            int dx = Math.abs(p1.x - p2.x);
            int dy = Math.abs(p1.y - p2.y);

            int r = (int) Math.pow(dx,2) + (int) Math.pow(dy,2);

            return r;
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            ps = new ArrayList<>();

            for(int i = 0; i < N; i++) {
                String[] input = br.readLine().split(" ");

                int x = Integer.parseInt(input[0]);
                int y = Integer.parseInt(input[1]);

                ps.add(new pos(x,y));
            }

            min_dist = Integer.MAX_VALUE;
        }
    }

}
