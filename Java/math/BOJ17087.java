package math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
숨바꼭질 6

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	12560	6332	5085	49.427%
문제
수빈이는 동생 N명과 숨바꼭질을 하고 있다. 수빈이는 현재 점 S에 있고, 동생은 A1, A2, ..., AN에 있다.

수빈이는 걸어서 이동을 할 수 있다. 수빈이의 위치가 X일때 걷는다면 1초 후에 X+D나 X-D로 이동할 수 있다. 수빈이의 위치가 동생이 있는 위치와 같으면, 동생을 찾았다고 한다.

모든 동생을 찾기위해 D의 값을 정하려고 한다. 가능한 D의 최댓값을 구해보자.

입력
첫째 줄에 N(1 ≤ N ≤ 105)과 S(1 ≤ S ≤ 109)가 주어진다. 둘째 줄에 동생의 위치 Ai(1 ≤ Ai ≤ 109)가 주어진다. 동생의 위치는 모두 다르며, 수빈이의 위치와 같지 않다.

출력
가능한 D값의 최댓값을 출력한다.

예제 입력 1
3 3
1 7 11
예제 출력 1
2
예제 입력 2
3 81
33 105 57
예제 출력 2
24
예제 입력 3
1 1
1000000000
예제 출력 3
999999999
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
수학
정수론
유클리드 호제법
 */
/*
알고리즘 핵심
수학 (유클리드 호제법 - GCD)
1. S와 A_i간의 거리 차이를 모두 구한 후, 모든 거리 차이의 값들의 GCD를 구한다.
2. GCD는 D이다.

처음 문제를 읽고 S와 거리가 먼 A_i와의 차이나는 거리 값을 시작으로 가장 가까운 거리까지 D를 정하고 모든 A_i로 도달하는지 브루트포스 방식을 사용하는 것이라고 생각했지만
해당 방법으로 문제를 풀이할 경우 시간초과가 날 것이라고 생각했다.

그리고 S와 A_i만 고려할 것이 아닌 S -> A_i로 이동한 후, A_i위치에서 다른 A_j의 거리 차이도 고려해야 하는 것이다.

따라서, S와 A_i의 거리 차이 그리고 A_i와 A_j의 거리 차이는 D의 배수이어야 한다.
그렇지 않으면, 수빈이는 모든 동생한테 도달할 수 없기 때문에 배수이면서 동시에 최대인 값을 찾아야하므로 S와 A_i의 거리차이 값들의 GCD를 구하는 것이다.
 */
public class BOJ17087 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,S,D;
    static int[] A;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static int Eculidean(int a, int b) {
        if(b == 0) return a;
        return Eculidean(b, a % b);
    }

    private static void solve() {
        D = Math.abs(S - A[0]);

        for(int a : A) {
            D = Eculidean(D,Math.abs(S - a));
        }

        System.out.println(D);
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        S = Integer.parseInt(input[1]);

        A = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        D = 0;
    }
}
