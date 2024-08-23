package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
두 배 더하기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	2570	1570	1330	61.037%
문제
모든 값이 0으로 채워져 있는 길이가 N인 배열 A가 있다. 영선이는 다음과 같은 두 연산을 수행할 수 있다.

배열에 있는 값 하나를 1 증가시킨다.
배열에 있는 모든 값을 두 배 시킨다.
배열 B가 주어졌을 때, 배열 A를 B로 만들기 위한 연산의 최소 횟수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 배열의 크기 N이 주어진다. (1 ≤ N ≤ 50)

둘째 줄에는 배열 B에 들어있는 원소가 공백으로 구분해서 주어진다. 배열에 B에 들어있는 값은 0보다 크거나 같고, 1,000보다 작거나 같다.

출력
첫째 줄에 배열 A를 B로 바꾸기 위한 최소 연산 횟수를 출력한다.

예제 입력 1
2
2 1
예제 출력 1
3
예제 입력 2
3
16 16 16
예제 출력 2
7
예제 입력 3
1
100
예제 출력 3
9
예제 입력 4
5
0 0 1 0 1
예제 출력 4
2
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
그리디 알고리즘
 */
/*
알고리즘 핵심
1. 값을 2배하는 것이 한 자리의 수를 +1하는 것보다 최소 연산 횟수에 적합하다.
2. B배열에서 홀수인 자리의 값을 -1 하여 짝수값으로 만든다. 이때 발생한 연산을 누적한다.
3. 모든 B배열의 자리의 값이 짝수가 되면, 2를 나누어 연산 횟수를 1증가하여 누적한다.
4. 모든 B배열의 자리값이 0이 되면, ans의 값이 최소 연산임을 보장하기 때문에 출력한다.
 */
public class BOJ12931 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static int[] A,B;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        while(true) {
            for(int i = 0; i < N; i++) {
                if(B[i] % 2 == 0) continue;
                B[i] -= 1;
                ans++;
            }

            for(int i = 0; i < N; i++) {
                B[i] /= 2;
            }

            if(check_equal_B()) {
                break;
            }

            ans++;
        }

        System.out.println(ans);
    }

    static boolean check_equal_B() {
        for(int i = 0; i < N; i++) {
            if(A[i] != B[i]) {
                return false;
            }
        }
        return true;
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        A = new int[N];

        ans = 0;

        B = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
