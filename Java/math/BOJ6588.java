package BOJ.math;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.StringBuilder;
import java.util.Arrays;

/*
골드바흐의 추측 다국어 

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	47480	13336	8774	25.660%
문제
1742년, 독일의 아마추어 수학가 크리스티안 골드바흐는 레온하르트 오일러에게 다음과 같은 추측을 제안하는 편지를 보냈다.

4보다 큰 모든 짝수는 두 홀수 소수의 합으로 나타낼 수 있다.
예를 들어 8은 3 + 5로 나타낼 수 있고, 3과 5는 모두 홀수인 소수이다. 또, 20 = 3 + 17 = 7 + 13, 42 = 5 + 37 = 11 + 31 = 13 + 29 = 19 + 23 이다.

이 추측은 아직도 해결되지 않은 문제이다.

백만 이하의 모든 짝수에 대해서, 이 추측을 검증하는 프로그램을 작성하시오.

입력
입력은 하나 또는 그 이상의 테스트 케이스로 이루어져 있다. 테스트 케이스의 개수는 100,000개를 넘지 않는다.

각 테스트 케이스는 짝수 정수 n 하나로 이루어져 있다. (6 ≤ n ≤ 1000000)

입력의 마지막 줄에는 0이 하나 주어진다.

출력
각 테스트 케이스에 대해서, n = a + b 형태로 출력한다. 이때, a와 b는 홀수 소수이다. 숫자와 연산자는 공백 하나로 구분되어져 있다. 만약, n을 만들 수 있는 방법이 여러 가지라면, b-a가 가장 큰 것을 출력한다. 또, 두 홀수 소수의 합으로 n을 나타낼 수 없는 경우에는 "Goldbach's conjecture is wrong."을 출력한다.

예제 입력 1
8
20
42
0
예제 출력 1
8 = 3 + 5
20 = 3 + 17
42 = 5 + 37
 */
// 소수를 활용하는 문제는 보통 소수를 특정 범위까지 구해놓고 사용하는 것이 효율적이다.
// 따라서, 소수를 구하는 알고리즘인 "에라스토테네스 알고리즘"을 이용하여 빠르게 소수를 구한다.
public class BOJ6588 {
    static Boolean[] elasto_primes = new Boolean[1000001];
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static int range = 1000000;
    public static void main(String[] args) throws Exception {
        Arrays.fill(elasto_primes,true);
        elasto_primes[0] = elasto_primes[1] = false;

        // 에라스토텔레스 소수판별 알고리즘 O(N^1/2lgN)?
        for(int i=2;i<=Math.sqrt(range);i++) {
            for(int j=i+i;j<range;j+=i) {
                elasto_primes[j] = false;
            }
        }

        /*
            //또는 BOJ17427(약수의합)에서 사용한 약수들의 합을 구한 알고리즘에서,
            //소수의 특성인 약수로 1과 자기자신의 수를 갖는 특성을 이용하여 i=3부터 순차적으로 f[i], f[n-i]의 소수판별을
            //하여 결과를 도출하면 된다.
            //f[1000001] = 1000000까지의 index에 해당하는 약수들의 합을 저장한 배열 = f
         */
        int n;
        Boolean ck;
        while((n = Integer.parseInt(br.readLine())) != 0) {
            ck = false;
            for(int i=3;i<range/2;i+=2) {
                if(elasto_primes[n-i] && elasto_primes[i]) {
                    sb.append(n + " = ").append(i + " + ").append(n-i).append("\n");
                    ck = true;
                    break;
                }
            }
            if(!ck) {
                sb.append("Goldbach's conjecture is wrong.\n");
            }
        }
        System.out.println(sb);
    }
}
