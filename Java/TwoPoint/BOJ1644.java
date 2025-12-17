package TwoPoint;/*
소수의 연속합 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	29782	12712	8845	41.582%
문제
하나 이상의 연속된 소수의 합으로 나타낼 수 있는 자연수들이 있다. 몇 가지 자연수의 예를 들어 보면 다음과 같다.

3 : 3 (한 가지)
41 : 2+3+5+7+11+13 = 11+13+17 = 41 (세 가지)
53 : 5+7+11+13+17 = 53 (두 가지)
하지만 연속된 소수의 합으로 나타낼 수 없는 자연수들도 있는데, 20이 그 예이다. 7+13을 계산하면 20이 되기는 하나 7과 13이 연속이 아니기에 적합한 표현이 아니다. 또한 한 소수는 반드시 한 번만 덧셈에 사용될 수 있기 때문에, 3+5+5+7과 같은 표현도 적합하지 않다.

자연수가 주어졌을 때, 이 자연수를 연속된 소수의 합으로 나타낼 수 있는 경우의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 자연수 N이 주어진다. (1 ≤ N ≤ 4,000,000)

출력
첫째 줄에 자연수 N을 연속된 소수의 합으로 나타낼 수 있는 경우의 수를 출력한다.

예제 입력 1
20
예제 출력 1
0
예제 입력 2
3
예제 출력 2
1
예제 입력 3
41
예제 출력 3
3
예제 입력 4
53
예제 출력 4
2
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/*
    1. 에라토스테네스의 체 = 소수 배열 생성
    2. Two - point 구간 합
 */
public class BOJ1644 {
    static int N;
    static boolean[] eratos = new boolean[4000001];
    static ArrayList<Integer> prime = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        Arrays.fill(eratos,false);

        Eratoshenes();

        System.out.println(TwoPoint());
    }

    static void Eratoshenes() {
        for(int i=2;i<=4000000;i++) {
            if(eratos[i]) continue;
            for(int u=2*i;u<=N;u+=i) {
                eratos[u] = true;
            }
        }

        for(int i=2;i<=4000000;i++) {
            if(!eratos[i]) {
                prime.add(i);
            }
        }
    }

    static int TwoPoint() {
        int ldx = 0, rdx = 0 , result = 0;
        int sum = prime.get(ldx);

        //prime ArrayList의 size를 넘어가는 rdx가 있을 수 있으므로, rdx 범위 조건 추가 = IndexOutOfBound 예외처리
        while(ldx <= rdx && rdx < prime.size()) {
            if(sum == N && rdx < prime.size()-1) {
                result++;
                sum = sum - (prime.get(ldx++) - prime.get(++rdx));
                continue;
            }

            if(sum < N && rdx < prime.size()-1) {
                sum += prime.get(++rdx);
            } else {
                sum -= prime.get(ldx++);
            }
        }
        return result;
    }
}
