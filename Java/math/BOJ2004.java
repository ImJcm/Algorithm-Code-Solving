package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
조합 0의 개수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	56065	15944	13145	28.914%
문제
 
$n \choose m$의 끝자리
$0$의 개수를 출력하는 프로그램을 작성하시오.

입력
첫째 줄에 정수
$n$,
$m$ (
$0 \le m \le n \le 2,000,000,000$,
$n \ne 0$)이 들어온다.

출력
첫째 줄에
$n \choose m$의 끝자리
$0$의 개수를 출력한다.

예제 입력 1
25 12
예제 출력 1
2
출처
데이터를 추가한 사람: dcrgkev
알고리즘 분류
수학
정수론
 */
/*
알고리즘 핵심
수학 (정수론 + 조합(Combination))
 */
public class BOJ2004 {
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
