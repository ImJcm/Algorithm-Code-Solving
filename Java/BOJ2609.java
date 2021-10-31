/*
문제
두 개의 자연수를 입력받아 최대 공약수와 최소 공배수를 출력하는 프로그램을 작성하시오.

입력
첫째 줄에는 두 개의 자연수가 주어진다. 이 둘은 10,000이하의 자연수이며 사이에 한 칸의 공백이 주어진다.

출력
첫째 줄에는 입력으로 주어진 두 수의 최대공약수를, 둘째 줄에는 입력으로 주어진 두 수의 최소 공배수를 출력한다.

예제 입력 1     예제 출력 1
24 18          6    72
 */
import java.util.Scanner;

public class BOJ2609 {
    static int n1, n2;
    static int GCD = 1;     //최대공약수
    static int LCM = 1;     //최소공배수

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n1 = sc.nextInt();
        n2 = sc.nextInt();

        cal(n1,n2);
        System.out.println(GCD);
        System.out.println(LCM);
    }

    public static void cal(int n1,int n2) {
        boolean pass = false;
        while(true) {
            if (pass) {
                LCM = n1 * n2 * GCD;
                break;
            }
            //for (int j = 2; j <= ((int) Math.min(n1, n2) / 2 < 2 ? 2 : (int)Math.min(n1, n2) / 2+1); j++) {
            //반례 : 101 202로 인해서 틀린코드이다. 공약수를 검색하기위해 n1,n2중 최소값에서 2를 나눈 값까지만 해도된다고 생각했지만,
            //해당 반례로 for문의 범위를 최소값+1까지 해야된다.
            for (int j = 2; j <= (int)Math.min(n1, n2)+1; j++) {
                pass = true;
                if (n1 % j ==0 && n2 % j == 0) {
                    GCD *= j;
                    n1 = n1 / j;
                    n2 = n2 / j;
                    pass = false;
                    break;
                }
            }

        }
    }
}
