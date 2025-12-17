package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

/*
NMK 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	6035	2091	1590	35.787%
문제
1부터 N까지의 수를 한 번씩 이용해서 가장 긴 증가하는 부분 수열의 길이가 M이고, 가장 긴 감소하는 부분 수열의 길이가 K인 수열을 출력한다.

입력
첫째 줄에 세 정수 N, M, K가 주어진다.

출력
첫째 줄에 문제의 조건을 만족하는 수열을 출력한다. 만약, 조건을 만족하는 수열이 없다면 -1을 출력한다.

제한
1 ≤ N ≤ 500
1 ≤ M, K ≤ N
예제 입력 1
4 2 2
예제 출력 1
2 1 4 3
예제 입력 2
4 4 1
예제 출력 2
1 2 3 4
예제 입력 3
4 3 2
예제 출력 3
1 4 2 3
예제 입력 4
4 4 2
예제 출력 4
-1
예제 입력 5
13 5 4
예제 출력 5
1 3 2 13 10 11 12 6 8 9 4 5 7
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
그리디 알고리즘
해 구성하기
 */
/*
알고리즘 핵심
그리디 알고리즘 + 정렬
1. 1 ~ N 까지의 수를 갖는 배열을 오름차순 상태로 만들고 저장한다. (1~N까지 배열을 선언 및 초기화 과정에서 오름차순 수행된다)
2. N,M,K의 값에 따라 조건을 만족하는 부분 배열이 존재하는지 여부를 검사한다.
(2-1. N < M * K
최장 길이 감소 부분 배열을 만족하려면 K개 보다 많은 수를 갖는 묶음이 없어야 하고, 조건을 만족하는 부분 수열은
묶음이 M개 이고, 모든 묵음의 수의 개수가 K개 이면 전체 수열의 길이는 M * K이며 이때가 최대이다.
따라서, 전체 수열의 길이가 M * K의 값이 N보다 크다면 문제의 조건을 만족할 수 없다.
2-2. M + K - 1 > N
M = 1일 때는 가장 긴 증가 부분 수열의 길이가 1이므로 내림차순 정렬된 상태의 부분 배열이다.
이때, 조건을 만족하는 부분 수열은 K = N 이므로, M = 1, K = N이다.
마찬가지로 K = 1일 때는 가장 긴 감소 부분 수열의 길이가 1이므로 전체 수가 오름차순으로 정렬의 부분 배열이다.
즉, M + K 가 N + 1일 때가 최소이며, M + K > N + 1인 경우 조건을 만족시킬 수 없다.)
3. 최소 한개 이상의 묶음은 K개의 수를 갖어야 하며, M개의 묶음을 만들어야 한다.
4. 만들어진 각 묶음을 내림차순 정렬을 수행한다.
4번 과정에 의해 M개의 최장 길이 증가 부분 배열을 만족하며, K개의 수를 갖는 묶음에 의해 최장 길이 감소 부분 배열을 만족한다.)

문제를 읽고 조건을 만족하는 부분 수열을 만들기 위해 M or K중 큰 값을 찾고, 큰 값에 해당하는 부분 수열을 우선적으로 만들고
나머지 부부 수열을 만드는 방법이라고 생각했다.
M값이 큰 경우, N - M + 1 값을 시작으로 N - M + 1 ~ N 값을 이은 부부 수열을 증가 부분 수열을 구성하고, 나머지는 감소 부분 수열을
K - 1로 구성하여 조건을 만족시키는 부분 수열을 만들 수 있다고 생각하였지만 로직을 구현하는데 논리적인 허점이 있어서 불가능했다.
(N,M,K를 임의의 데이터로 시뮬레이션을 돌려보아도 조건을 만족하는 부분 수열을 만들 수가 없었다. => N = 7, M = 2, K = 2
6 7 이후 5자리에 K = 1인 감소 부분 수열을 만들 수가 없었다. 즉, M = 2인 증가 부분 수열을 우선적으로 만드는 것 부터 잘못된 로직이다.)
그래서 더이상 해답을 찾을 수 없다고 판단하여 해결 방법을 보고 코드를 작성하였다.
- https://lotuslee.tistory.com/74 <- 참고
 */
public class BOJ1201 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,K;
    static int[] ans,bundles;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        if(!condition_satisfaction_check()) ans = null;
        else {
            at_most_K_in_M_bundle();
            reverse_sort_each_bundle();
        }

        if(ans == null) {
            System.out.println("-1");
        } else {
            Arrays.stream(ans)
                    .forEach(i -> System.out.print(i + " " ));
        }
    }

    private static boolean condition_satisfaction_check() {
        if(N > M * K || M + K - 1 > N) return false;
        else return true;
    }

    private static void reverse_sort_each_bundle() {
        Integer[] integer_arr = Arrays.stream(ans).boxed().toArray(Integer[]::new);

        for(int i = 0; i < bundles.length; i++) {
            int s_i = bundles[i];
            int e_i = i + 1 == bundles.length ? N : bundles[i + 1];

            Arrays.sort(integer_arr, s_i, e_i, Comparator.reverseOrder());
        }

        ans = Arrays.stream(integer_arr)
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private static void at_most_K_in_M_bundle() {
        bundles = new int[M];
        int s_idx = 0;
        int i = 0;
        int n = N;

        bundles[i++] = s_idx;
        s_idx += K;
        n -= K;

        if(M - 1 > 0) {
            int c = n % (M - 1);    // 한 묶음에 들어갈 최소 개수보다 더 들어가야할 묶음의 수
            int l = n / (M - 1);    // 한 묶음에 들어갈 수의 최소 개수
            int r = (M - 1) - c;

            while(c-- > 0) {
                bundles[i++] = s_idx;
                s_idx += l + 1;
            }

            while(r-- > 0) {
                bundles[i++] = s_idx;
                s_idx += l;
            }
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        K = Integer.parseInt(input[2]);

        ans = IntStream.range(1,N + 1)
                .toArray();
    }
}
