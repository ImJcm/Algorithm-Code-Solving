package math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
피보나치 수 3

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	24822	8264	6700	39.158%
문제
피보나치 수는 0과 1로 시작한다. 0번째 피보나치 수는 0이고, 1번째 피보나치 수는 1이다. 그 다음 2번째 부터는 바로 앞 두 피보나치 수의 합이 된다.

이를 식으로 써보면 Fn = Fn-1 + Fn-2 (n ≥ 2)가 된다.

n=17일때 까지 피보나치 수를 써보면 다음과 같다.

0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597

n이 주어졌을 때, n번째 피보나치 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 n이 주어진다. n은 1,000,000,000,000,000,000보다 작거나 같은 자연수이다.

출력
첫째 줄에 n번째 피보나치 수를 1,000,000으로 나눈 나머지를 출력한다.

예제 입력 1
1000
예제 출력 1
228875
출처
문제를 만든 사람: baekjoon
비슷한 문제
2747번. 피보나치 수
2748번. 피보나치 수 2
10826번. 피보나치 수 4
10870번. 피보나치 수 5
 */
/*
    핵심 알고리즘 : 수학, 분할 정복을 이용한 거듭제곱

    (f_n+2 f_n+1) = [1 1] ^ n
    (f_n+1 f_n)     [1 0]
    의 점화식을 이용하여 O(logN)으로 답을 도출 가능하다
 */
public class BOJ2749 {
    static long N;
    final static long MOD = 1000000;
    static long[][] matrix = {{1,0},{0,1}};
    static long[][] R = {{1,1},{1,0}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Long.parseLong(br.readLine());

        System.out.println(fibonacci(R, N));
    }
    /*
        알고리즘의 핵심
        행렬의 n제곱을 최적화 하는 방법
        ex)
        3^1000의 연산을 1000이라는 지수를 2진수로 바꾸면,
        1111101000(2) = 1000(10)으로 나타낼 수 있고,
        각 자리에 해당하는 수가 몇개가 필요한지 판단
        해당하는 친구들만 answer에 곱한다.

        코드상에서,
        if(n%2 == 1) {
               matrix = multi(matrix,r);
        }
        위의 코드 부분이 지수에서 1인 부분일 때, 해당 자리의 수를 곱해주는 것

        r = multi(r,r);
        해당 자리 값을 만들기 위한 코드

        예시)
        3^4 = 81
        3 ^ 100 = 3^10 x 3^10 = 3 x 3 x 3 x 3

        다른 방법으로, 피사노 주기를 이용하여 푸는 방법
        피사노 주기를 통해 반복 주기를 찾고 주기동안의 피보나치 수열에서 MOD(=1000000)으로 나눈 나머지 값들을 배열에 저장
        그 후 저장한 배열에서 찾고자 하는 n번째 주기에서 몇번째 숫자인지 찾아내서 해당 배열의 값을 출력하는 방법

        피사노 주기 찾는 함수
        //피사노 주기의 경우, 나누는 값에 따라 주기 값이 달라진다.
        //나누는 값이 10^n일 경우,
        //주기 값은 15 x 10^(n-1)이다.

        MOD = 1000000;  //나누는 수
        int cycle_pisano() {
            int k=0,tmp=MOD;
            while(tmp > 1) {
                tmp /= 10;
                k++;
            }
            return 15 * pow(10,k-1);    //피사노 주기
        }

        피사노 주기 값만큼 피보나치 수열 배열 만들기
        void pisano_fibo() {
            arr[0]=0;
            arr[1]=0;
            int cycle = cycle_pisano();
            for(int i=0;i<cycle;i++) {
                arr[i+2] = arr[i+1] + arr[i]) % MOD;
            }
        }
     */
    static long fibonacci(long[][] r, long n) {
       if (n == 0) {
           return 0;
       }

       while(n > 0) {
           if(n%2 == 1) {
               matrix = multi(matrix,r);
           }
           r = multi(r,r);
           n /= 2;
       }
       return matrix[0][1];
    }

    static long[][] multi(long[][] a, long[][] b) {
        long[][] result = {{0,0},{0,0}};

        for(int i=0;i<2;i++) {
            for(int j=0;j<2;j++) {
                for(int k=0;k<2;k++) {
                    result[i][j] = (result[i][j] + a[i][k] * b[k][j]) % MOD;
                }
            }
        }
        return result;
    }
}

/*
n = 17
0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597
 */

