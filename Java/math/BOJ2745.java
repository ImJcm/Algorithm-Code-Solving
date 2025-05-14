package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
진법 변환

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	80038	39257	33134	48.601%
문제
B진법 수 N이 주어진다. 이 수를 10진법으로 바꿔 출력하는 프로그램을 작성하시오.

10진법을 넘어가는 진법은 숫자로 표시할 수 없는 자리가 있다. 이런 경우에는 다음과 같이 알파벳 대문자를 사용한다.

A: 10, B: 11, ..., F: 15, ..., Y: 34, Z: 35

입력
첫째 줄에 N과 B가 주어진다. (2 ≤ B ≤ 36)

B진법 수 N을 10진법으로 바꾸면, 항상 10억보다 작거나 같다.

출력
첫째 줄에 B진법 수 N을 10진법으로 출력한다.

예제 입력 1
ZZZZZ 36
예제 출력 1
60466175
출처
문제의 오타를 찾은 사람: bupjae
알고리즘 분류
수학
구현
문자열
 */
/*
알고리즘 핵심
수학 + 문자열 (진법 변환)
1. N의 앞자리부터 B로 주어진 값을 N의 길이에서 -1 만큼 적용하여 제곱한 값을 곱하여 나온 결과 값을 ans에 누적한다.
2. 1번 과정에서 길이가 0이 될 때까지 반복한다. 즉, N = ZZZ, B = 36일 때 ZZZ = (36^2 * 35) + (36^1 * 35) + (36^0 * 35)
(-> 0-9 : 0 ~ 9, A-Z : 10 ~ 35값을 매핑 = map)
 */
public class BOJ2745 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String N;
    static int B,ans;
    static Map<String, Integer> map;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        for(int i = N.length() - 1; i >= 0; i--) {
            ans += (int) Math.pow(B,i) * map.get(String.valueOf(N.charAt(N.length() - i - 1)));
        }

        System.out.println(ans);
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = input[0];
        B = Integer.parseInt(input[1]);

        map = new HashMap<>();
        ans = 0;

        for(int i = 0; i <= 35; i++) {
            if(i < 10) map.put(String.valueOf(i),i);
            else map.put(String.valueOf((char)(55 + i)),i);
        }
    }
}
