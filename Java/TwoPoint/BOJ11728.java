package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
배열 합치기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1.5 초	256 MB	51714	25063	16754	47.002%
문제
정렬되어있는 두 배열 A와 B가 주어진다. 두 배열을 합친 다음 정렬해서 출력하는 프로그램을 작성하시오.

입력
첫째 줄에 배열 A의 크기 N, 배열 B의 크기 M이 주어진다. (1 ≤ N, M ≤ 1,000,000)

둘째 줄에는 배열 A의 내용이, 셋째 줄에는 배열 B의 내용이 주어진다. 배열에 들어있는 수는 절댓값이 109보다 작거나 같은 정수이다.

출력
첫째 줄에 두 배열을 합친 후 정렬한 결과를 출력한다.

예제 입력 1
2 2
3 5
2 9
예제 출력 1
2 3 5 9
예제 입력 2
2 1
4 7
1
예제 출력 2
1 4 7
예제 입력 3
4 3
2 3 5 9
1 4 7
예제 출력 3
1 2 3 4 5 7 9
출처
문제를 만든 사람: baekjoon
알고리즘 분류
정렬
두 포인터
 */
public class BOJ11728 {
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
