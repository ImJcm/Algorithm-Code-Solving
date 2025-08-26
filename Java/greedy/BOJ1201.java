package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
public class BOJ1201 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {

    }

    private static void init_setting() throws IOException {

    }
}
