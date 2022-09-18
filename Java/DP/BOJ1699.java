/*
제곱수의 합

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	46094	18415	13331	39.081%
문제
어떤 자연수 N은 그보다 작거나 같은 제곱수들의 합으로 나타낼 수 있다. 예를 들어 11=32+12+12(3개 항)이다. 이런 표현방법은 여러 가지가 될 수 있는데, 11의 경우 11=22+22+12+12+12(5개 항)도 가능하다. 이 경우, 수학자 숌크라테스는 “11은 3개 항의 제곱수 합으로 표현할 수 있다.”라고 말한다. 또한 11은 그보다 적은 항의 제곱수 합으로 표현할 수 없으므로, 11을 그 합으로써 표현할 수 있는 제곱수 항의 최소 개수는 3이다.

주어진 자연수 N을 이렇게 제곱수들의 합으로 표현할 때에 그 항의 최소개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 자연수 N이 주어진다. (1 ≤ N ≤ 100,000)

출력
주어진 자연수를 제곱수의 합으로 나타낼 때에 그 제곱수 항의 최소 개수를 출력한다.

예제 입력 1
7
예제 출력 1
4
예제 입력 2
1
예제 출력 2
1
예제 입력 3
4
예제 출력 3
1
예제 입력 4
11
예제 출력 4
3
예제 입력 5
13
예제 출력 5
2
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class BOJ1699 {
    static int N;
    static int[] mem;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        mem = new int[N + 1];
        Arrays.fill(mem,0);
        mem[1] = 1;

        //중요! : N보다 작은 수 중 가장 큰 제곱수를 적용한다고 최소 값을 만족하지 않기 때문에,
        //N보다 작은 제곱수 = sqrt_num이라고 가정)
        //sqrt_num ~ Math.sqrt(sqrt_num) 범위 내에서 제곱수를 만족하는 경우를 찾아야 한다.
        for(int i=2;i<=N;i++) {
            int sqrt_num = (int)Math.sqrt(i);
            int tmp = Integer.MAX_VALUE;

            for(int j=sqrt_num;j>=Math.sqrt(sqrt_num);j--) {
                tmp = Math.min(1 + mem[i - (int)Math.pow(j,2)], tmp);
            }
            mem[i] = tmp;
        }

        System.out.println(mem[N]);
    }
}
