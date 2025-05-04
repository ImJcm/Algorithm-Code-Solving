package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
2진수 8진수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	38372	15381	12724	41.480%
문제
2진수가 주어졌을 때, 8진수로 변환하는 프로그램을 작성하시오.

입력
첫째 줄에 2진수가 주어진다. 주어지는 수의 길이는 1,000,000을 넘지 않는다.

출력
첫째 줄에 주어진 수를 8진수로 변환하여 출력한다.

예제 입력 1
11001100
예제 출력 1
314
출처
문제를 만든 사람: author5
데이터를 추가한 사람: sheepbomb
비슷한 문제
1212번. 8진수 2진수
알고리즘 분류
수학
문자열
 */
/*
알고리즘 핵심
수학 + 문자열 (진수변환)
1. 입력으로 주어진 2진수 문자열의 길이는 최대 1,000,000이므로 int, long타입으로 10진수 변환하는 것은 불가능하다.
2. 입력으로 주어진 2진수 문자열을 길이가 3인 문자열로 나누고 해당 문자열을 10진수로 변환하면 8진수로 변환하는 것과 동일한 로직을 이용한다.
ex) 11001100 (2) -> 11 / 001 / 100 -> (2진수 -> 10진수) -> (11 -> 3, 001 -> 1, 100 -> 4) = 314

long 타입의 경우 8바이트(64비트)로 부호를 사용하는 1비트를 제외하면 63비트까지 수의 표현이 가능하다.
따라서, long이 가질 수 있는 최대 값은 2진수로 11...11(63개)로 구성된 수이다.
하지만, 입력으로 주어진 2진수 문자열의 길이는 최대 1,000,000이므로 10진수로 변환이 불가능하다.
 */
public class BOJ1373 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String Binary_str, Octal_str, Decimal_str;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        int s_idx = Binary_str.length() % 3;

        if(s_idx != 0) sb.append(BinaryTo_Octal(0,s_idx));

        for (int i = s_idx; i < Binary_str.length(); i += 3) sb.append(BinaryTo_Octal(i,i + 3));

        System.out.println(sb.toString());
    }

    private static int BinaryTo_Octal(int s_idx, int e_idx) {
        return Integer.parseInt(Binary_str.substring(s_idx, e_idx),2);
    }

    /*
        입력으로 주어지는 2진수 문자열의 길이가 1,000,000이기 때문에,
        Long타입으로 저장할 수 있는 숫자의 길이는 64비트이므로 63길이가 넘어가는 2진수 문자열의 경우 long타입으로 수를 담을 수 없다.
     */
    private static void wrong_solve() {
        /*
        Decimal_str = Integer.toString(Integer.parseInt(Binary_str,2));

        Octal_str = Integer.toOctalString(Integer.parseInt(Decimal_str));
        */

        Decimal_str = Long.toString(Long.parseLong(Binary_str,2));

        Octal_str = Long.toOctalString(Long.parseLong(Decimal_str));

        System.out.println(Octal_str);
    }

    private static void init_setting() throws IOException {
        Binary_str = br.readLine();

        sb = new StringBuilder();
    }
}
