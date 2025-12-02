package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
선분과 점 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	2245	1236	891	55.102%
문제
3차원 좌표 평면 위에 선분 하나와 점 하나가 있다. 선분의 양 끝점은 A(Ax, Ay, Az)와 B(Bx, By, Bz)로 나타낼 수 있다. 점의 좌표는 C(Cx, Cy, Cz) 이다.

선분과 점 사이의 거리의 최솟값을 구하는 프로그램을 작성하시오.

두 점 (x1, y1, z1)과 (x2, y2, z2) 사이의 거리는
\(\sqrt{(x2-x1)^2+(y2-y1)^2+(z2-z1)^2}\) 이다.

입력
첫째 줄에 선분과 점의 좌표 Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz가 주어진다. 좌표는 0보다 크거나 같고, 10,000보다 작거나 같은 정수이다.

출력
첫째 줄에 선분과 점 사이의 거리의 최솟값을 출력한다. 절대/상대 오차는 10-6까지 허용한다.

예제 입력 1
0 0 0 1 1 1 2 2 2
예제 출력 1
1.7320508076
예제 입력 2
0 0 0 10 10 10 5 5 5
예제 출력 2
0.0000000000
예제 입력 3
10 20 30 40 50 60 45 35 25
예제 출력 3
28.2842712475
예제 입력 4
0 0 0 1 1 1 0 0 1
예제 출력 4
0.8164965809
예제 입력 5
0 0 0 1 0 1 0 1 0
예제 출력 5
1.0000000000
예제 입력 6
0 0 0 1 1 1 7 6 5
예제 출력 6
8.7749643874
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: chogahui05
알고리즘 분류
수학
기하학
삼분 탐색
3차원 기하학
 */
public class BOJ11664 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] A,B,C;
        double ans;

        private void solve() throws IOException {
            init_setting();


        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            A = new int[3];
            B = new int[3];
            C = new int[3];

            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    switch (i) {
                        case 0:
                            A[j] = Integer.parseInt(input[j]);
                            break;
                        case 1:
                            B[j] = Integer.parseInt(input[i * 3 + j]);
                            break;
                        case 2:
                            C[j] = Integer.parseInt(input[i * 3 + j]);
                            break;
                    }
                }
            }

            ans = 0;
        }
    }
}
