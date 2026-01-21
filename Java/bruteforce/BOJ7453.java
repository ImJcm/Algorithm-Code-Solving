package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
합이 0인 네 정수 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
12 초 (추가 시간 없음)	1024 MB	56003	15671	8634	23.257%
문제
정수로 이루어진 크기가 같은 배열 A, B, C, D가 있다.

A[a], B[b], C[c], D[d]의 합이 0인 (a, b, c, d) 쌍의 개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 배열의 크기 n (1 ≤ n ≤ 4000)이 주어진다. 다음 n개 줄에는 A, B, C, D에 포함되는 정수가 공백으로 구분되어져서 주어진다. 배열에 들어있는 정수의 절댓값은 최대 228이다.

출력
합이 0이 되는 쌍의 개수를 출력한다.

예제 입력 1
6
-45 22 42 -16
-41 -27 56 30
-36 53 -37 77
-36 30 -75 -46
26 -38 -10 62
-32 -54 -6 45
예제 출력 1
5
출처
ICPC > Regionals > Europe > Southwestern European Regional Contest > SWERC 2005 E번

문제를 번역한 사람: baekjoon
데이터를 추가한 사람: doju, naong606
어색한 표현을 찾은 사람: 79brue
알고리즘 분류
정렬
이분 탐색
두 포인터
중간에서 만나기
 */
public class BOJ7453 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N;
        long ans;
        int[] A,B,C,D,AB,CD;

        private void solve() throws IOException {
            init_setting();

            two_point_check();

            System.out.println(ans);

            // 검증 필요
        }

        private void two_point_check() {
            int ab_idx = 0,cd_idx = N * N - 1;

            while(true) {
                if(ab_idx >= N * N || cd_idx < 0) break;
                int ret = AB[ab_idx] + CD[cd_idx];

                if(ret == 0) {
                    int ab = 0;
                    int cd = 0;

                    while(true) {
                        if(ab_idx + ab < N * N && AB[ab_idx] == AB[ab_idx + ab]) {
                            ab++;
                        } else {
                            ab_idx += ab;
                            break;
                        }
                    }

                    while(true) {
                        if (cd_idx - cd >= 0 && CD[cd_idx] == CD[cd_idx - cd]) {
                            cd--;
                        } else {
                            cd_idx -= cd;
                            break;
                        }
                    }
                    ans += ((long) ab * cd);
                } else if(ret < 0) {
                    ab_idx++;
                } else {
                    cd_idx--;
                }
            }
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            A = new int[N];
            B = new int[N];
            C = new int[N];
            D = new int[N];

            AB = new int[N * N];
            CD = new int[N * N];

            for(int i = 0; i < N; i++) {
                String[] input = br.readLine().split(" ");

                A[i] = Integer.parseInt(input[0]);
                B[i] = Integer.parseInt(input[1]);
                C[i] = Integer.parseInt(input[2]);
                D[i] = Integer.parseInt(input[3]);
            }

            //Arrays.sort(A);
            //Arrays.sort(B);
            //Arrays.sort(C);
            //Arrays.sort(D);

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    AB[i * N + j] = A[i] + B[j];
                    CD[i * N + j] = C[i] + D[j];
                }
            }

            Arrays.sort(AB);
            Arrays.sort(CD);

            ans = 0;
        }
    }
}
