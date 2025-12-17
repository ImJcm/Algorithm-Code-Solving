package math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
8진수 2진수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	53282	18360	15367	36.382%
문제
8진수가 주어졌을 때, 2진수로 변환하는 프로그램을 작성하시오.

입력
첫째 줄에 8진수가 주어진다. 주어지는 수의 길이는 333,334을 넘지 않는다.

출력
첫째 줄에 주어진 수를 2진수로 변환하여 출력한다. 수가 0인 경우를 제외하고는 반드시 1로 시작해야 한다.

예제 입력 1
314
예제 출력 1
11001100
출처
문제를 만든 사람: author5
데이터를 추가한 사람: occidere
비슷한 문제
1373번. 2진수 8진수
알고리즘 분류
수학
구현
문자열
 */
/*
알고리즘 핵심
수학 + 문자열 (진수변환)
1. 8진수 숫자를 2진수 문자열로 변환하는 Integer.toBinaryString() 함수를 사용한다.
2. 2진수 숫자로 변환 시, 처음으로 오는 값은 0이 아닌 1이여야 하므로 0에 해당하는 부분을 제거한다.
 */
public class BOJ1212 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String octal_str, binary_str;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
        solve2();
    }

    private static void solve() {
        if (octal_str.equals("0")) {
            sb.append(0);
        } else {
            char ch = octal_str.charAt(0);
            int binary_int = Integer.parseInt(String.valueOf(ch), 8);
            String temp_str = Integer.toBinaryString(binary_int);

            sb.append(temp_str);

            for (int i = 1; i < octal_str.length(); i++) {
                ch = octal_str.charAt(i);

                binary_int = Integer.parseInt(String.valueOf(ch), 8);
                temp_str = Integer.toBinaryString(binary_int);

                sb.append(String.format("%03d", Integer.parseInt(temp_str)));
            }
        }
    }

    private static void solve2() {
        if(octal_str.equals("0")) {
            sb.append(0);
        } else {
            char ch = octal_str.charAt(0);
            int binary_int = Integer.parseInt(String.valueOf(ch),8);
            String tmp = "";
            while(true) {
                tmp = (binary_int % 2) + tmp;

                if(binary_int / 2 == 0) break;
                binary_int /= 2;
            }

            sb.append(tmp);

            for(int i = 1; i < octal_str.length(); i++) {
                tmp = "";
                ch = octal_str.charAt(i);
                binary_int = Integer.parseInt(String.valueOf(ch),8);

                while(true) {
                    tmp = (binary_int % 2) + tmp;

                    if(binary_int / 2 == 0) break;
                    binary_int /= 2;
                }
                sb.append(String.format("%03d",Integer.parseInt(tmp)));
            }
        }

        System.out.println(sb.toString());
    }

    /*
        time over : String += String
     */
    private static void wrong_solve() {
        if(octal_str.equals("0")) {
            binary_str = "0";
        } else {
            char ch = octal_str.charAt(0);
            int binary_int = Integer.parseInt(String.valueOf(ch),8);
            String temp_str = Integer.toBinaryString(binary_int);

            binary_str += temp_str;

            for(int i = 1; i < octal_str.length(); i++) {
                ch = octal_str.charAt(i);

                binary_int = Integer.parseInt(String.valueOf(ch),8);
                temp_str = Integer.toBinaryString(binary_int);

                binary_str += String.format("%03d",Integer.parseInt(temp_str));
            }
        }

        //System.out.println(binary_str);
    }

    private static void init_setting() throws IOException {
        octal_str = br.readLine();

        binary_str = "";

        sb = new StringBuilder();
    }
}
