package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
행렬

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	27736	12051	9755	43.309%
문제
0과 1로만 이루어진 행렬 A와 행렬 B가 있다. 이때, 행렬 A를 행렬 B로 바꾸는데 필요한 연산의 횟수의 최솟값을 구하는 프로그램을 작성하시오.

행렬을 변환하는 연산은 어떤 3×3크기의 부분 행렬에 있는 모든 원소를 뒤집는 것이다. (0 → 1, 1 → 0)

입력
첫째 줄에 행렬의 크기 N M이 주어진다. N과 M은 50보다 작거나 같은 자연수이다. 둘째 줄부터 N개의 줄에는 행렬 A가 주어지고, 그 다음줄부터 N개의 줄에는 행렬 B가 주어진다.

출력
첫째 줄에 문제의 정답을 출력한다. 만약 A를 B로 바꿀 수 없다면 -1을 출력한다.

예제 입력 1
3 4
0000
0010
0000
1001
1011
1001
예제 출력 1
2
예제 입력 2
3 3
111
111
111
000
000
000
예제 출력 2
1
예제 입력 3
1 1
1
0
예제 출력 3
-1
예제 입력 4
18 3
001
100
100
000
011
010
100
100
010
010
010
110
101
101
000
110
000
110
001
100
011
000
100
010
011
100
101
101
010
001
010
010
111
110
111
001
예제 출력 4
7
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
그리디 알고리즘
 */
/*
알고리즘 핵심
그리디 알고리즘
1. N,M 행렬에서 순차적으로 3x3 뒤집기가 가능한지 검사한다.
2. 뒤집기가 가능한 경우, A 행렬을 뒤집고, 이를 N,M까지 뒤집기가 가능한 인덱스 위치까지 수행한다.
3. 뒤집기가 끝난 후, A와 B 행렬이 같은지 검사한 후, 뒤집기 횟수를 출력하거나, 불가능한 경우 -1을 출력한다.
 */
public class BOJ1080 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static boolean flag;
    static int N,M,ans;
    static int[][] A,B;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {

        if(N < 3 && M < 3) {
            flag = check_is_same_matrix();
        } else {
            for(int n = 0; n < N - 2; n++) {
                for(int m = 0; m < M - 2; m++) {
                    if(A[n][m] == B[n][m]) continue;

                    flip_matrix(n,m);
                    ans++;
                }
            }

            flag = check_is_same_matrix();
        }

        System.out.println(flag ? ans : -1);
    }

    private static void flip_matrix(int n, int m) {
        for(int i = n; i < n + 3; i++) {
            for(int j = m; j < m + 3; j++) {
                A[i][j] = (A[i][j] + 1) % 2;
            }
        }
    }

    private static boolean check_is_same_matrix() {
        for(int n = 0; n < N; n++) {
            for(int m = 0; m < M; m++) {
                if(A[n][m] != B[n][m]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        A = new int[N][M];
        B = new int[N][M];

        int count = 2;

        while(count-- > 0) {
            switch (count) {
                case 0:
                    for(int i = 0; i < N; i++) {
                        B[i] = Arrays.stream(br.readLine().split(""))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                    }
                    break;
                case 1:
                    for(int i = 0; i < N; i++) {
                        A[i] = Arrays.stream(br.readLine().split(""))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                    }
                    break;
            }
        }

        ans = 0;
    }
}
