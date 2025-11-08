package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
    static class pos {
        int x,y;

        pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static ArrayList<pos> ps;

    public static void main(String[] args) {

    }

    public class init_solve {

        private void solve() throws IOException {
            init_setting();


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
        }
    }

}
