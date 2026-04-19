package math;

import java.util.Scanner;

public class BOJ4375 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(sc.hasNextInt()) {
            System.out.println(solve(sc.nextInt()));
        }
    }

    public static int solve(int num) {
        int digit = 1;
        int one_num = 1;

        while(true) {
            if(one_num % num == 0) {
                break;
            } else {
                digit++;
                one_num = one_num * 10 + 1;
                one_num %= num;
            }
        }
        return digit;
    }
}
/*
 while((one_num % num) != 0) {
    one_num += (long)Math.pow(10,digit);
    digit++;
 }
 위와 같이 one_num의 크기가 커지게되면 정수자료형 범위를 넘어가게되어 틀린코드가 되므로,
 Modular 연산을 이용하여, one_num의 값을 조절하여 결과를 도출한다.
 Modular 연산 => X mod N = (X mod N) mod N;
 */
