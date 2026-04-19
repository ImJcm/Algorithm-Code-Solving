package math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
진법 변환 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (추가 시간 없음)	256 MB	72615	33741	28984	46.140%
문제
10진법 수 N이 주어진다. 이 수를 B진법으로 바꿔 출력하는 프로그램을 작성하시오.

10진법을 넘어가는 진법은 숫자로 표시할 수 없는 자리가 있다. 이런 경우에는 다음과 같이 알파벳 대문자를 사용한다.

A: 10, B: 11, ..., F: 15, ..., Y: 34, Z: 35

입력
첫째 줄에 N과 B가 주어진다. (2 ≤ B ≤ 36) N은 10억보다 작거나 같은 자연수이다.

출력
첫째 줄에 10진법 수 N을 B진법으로 출력한다.

예제 입력 1
60466175 36
예제 출력 1
ZZZZZ
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: du9172
문제의 오타를 찾은 사람: zmtn94
알고리즘 분류
수학
구현
 */
/*
알고리즘 핵심
수학 (진법 변환)
1. 0 ~ 35까지 대응되는 값을 map에 저장한다. (0~9 : 0~9, 10 ~ 35 : A ~ Z)
2. N을 B로 나눈 후 나머지값을 map에 대응되는 값을 ans에 저장한다.
3. ans를 reverse()하여 출력한다.
 */
public class BOJ11005 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,B;
    static Map<Integer, String> map;
    static StringBuilder ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        while(true) {
            ans.append(map.get(N % B));
            N /= B;

            if(N == 0) break;
        }

        System.out.println(ans.reverse().toString());
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        B = Integer.parseInt(input[1]);

        ans = new StringBuilder();

        map = new HashMap<>();

        for(int i = 0; i <= 35; i++) {
            if(i < 10) map.put(i, String.valueOf(i));
            else map.put(i, String.valueOf((char) (55 + i)));
        }
    }
}
