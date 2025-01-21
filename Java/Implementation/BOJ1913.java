package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
달팽이

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	21955	9469	7072	44.987%
문제
홀수인 자연수 N이 주어지면, 다음과 같이 1부터 N2까지의 자연수를 달팽이 모양으로 N×N의 표에 채울 수 있다.

9	2	3
8	1	4
7	6	5
25	10	11	12	13
24	9	2	3	14
23	8	1	4	15
22	7	6	5	16
21	20	19	18	17
N이 주어졌을 때, 이러한 표를 출력하는 프로그램을 작성하시오. 또한 N2 이하의 자연수가 하나 주어졌을 때, 그 좌표도 함께 출력하시오. 예를 들어 N=5인 경우 6의 좌표는 (4,3)이다.

입력
첫째 줄에 홀수인 자연수 N(3 ≤ N ≤ 999)이 주어진다. 둘째 줄에는 위치를 찾고자 하는 N2 이하의 자연수가 하나 주어진다.

출력
N개의 줄에 걸쳐 표를 출력한다. 각 줄에 N개의 자연수를 한 칸씩 띄어서 출력하면 되며, 자릿수를 맞출 필요가 없다. N+1번째 줄에는 입력받은 자연수의 좌표를 나타내는 두 정수를 한 칸 띄어서 출력한다.

예제 입력 1
7
35
예제 출력 1
49 26 27 28 29 30 31
48 25 10 11 12 13 32
47 24 9 2 3 14 33
46 23 8 1 4 15 34
45 22 7 6 5 16 35
44 21 20 19 18 17 36
43 42 41 40 39 38 37
5 7
출처
문제를 만든 사람: author5
데이터를 추가한 사람: inbdni
알고리즘 분류
구현
채점 및 기타 정보
이 문제의 채점 우선 순위는 2이다.
 */
/*
알고리즘 핵심
구현
1. N으로 주어지는 값으로 홀수,짝수인지에 따라 1의 좌표를 구한다.
2. 1을 시작으로 N=2,3,...,N까지 진행하는 과정에서 N이 짝수인 경우 우상단의 값, N이 홀수인 경우 좌하단의 값이 채워진다.
3. N의 값에따라 채워질 수의 개수가 정해지므로 해당 개수만큼 num을 채운다.
4. num이 채워짐에 따라 target의 값과 같은 위치를 ans_x,ans_y에 저장한다.
 */
public class BOJ1913 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,target,ans_x,ans_y;
    static int[][] snails;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        int start_x = N / 2 + 1;
        int start_y = N / 2 + (N % 2 == 1 ? 1 : 0);

        int num = 1;

        check_target(num,start_x,start_y);
        snails[start_x--][start_y] = num++;

        for(int i = 2; i <= N; i++) {
            if(i % 2 == 0) {
                for(int j = 1; j <= i * 2 - 1; j++) {
                    check_target(num,start_x,start_y);
                    if(j < i) {
                        snails[start_x][start_y++] = num++;
                    } else {
                        snails[start_x++][start_y] = num++;
                    }
                }
            } else {
                for(int j = 1; j <= i * 2 - 1; j++) {
                    check_target(num,start_x,start_y);
                    if(j < i) {
                        snails[start_x][start_y--] = num++;
                    } else {
                        snails[start_x--][start_y] = num++;
                    }
                }
            }
        }

        print();
    }

    private static void check_target(int n, int x, int y) {
        if(n == target) {
            ans_x = x;
            ans_y = y;
        }
    }

    private static void print() {
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                System.out.print(snails[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println(ans_x + " " + ans_y);
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());
        target = Integer.parseInt(br.readLine());

        snails = new int[N + 1][N + 1];
    }
}
