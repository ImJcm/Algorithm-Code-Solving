package BOJ;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/*
수 이어 쓰기 1 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.15 초 (하단 참고)	128 MB	17047	7832	6524	49.695%
문제
1부터 N까지의 수를 이어서 쓰면 다음과 같이 새로운 하나의 수를 얻을 수 있다.
1234567891011121314151617181920212223...
이렇게 만들어진 새로운 수는 몇 자리 수일까? 이 수의 자릿수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N(1 ≤ N ≤ 100,000,000)이 주어진다.

출력
첫째 줄에 새로운 수의 자릿수를 출력한다.

예제 입력 1
5
예제 출력 1
5
예제 입력 2
15
예제 출력 2
21
예제 입력 3
120
예제 출력 3
252
 */
/*
 * 문제를 보고 1~9 : 1, 10~99 : 2, 100~999 : 3, ...
 * 이라는 규칙성을 보고 자릿수를 파악하고 일정 자리수를 넘어가면
 * 이전 자리수로 만들어지는 자릿수를 result에 누적해서 더한다.
 * 10^i-1이 N보다 작거나 같을 때 까지 반복문을 수행한다. N을 넘어가는 경우,
 * N - 자릿수의 개수가 변하는 시작지점의 수(Math.pow(10,i-1)) + 1와 N의 구간에서 만드는 자릿수인 i와 곱하여
 * result와 누적하여 더하고 출력하면 답이다.
 */
public class BOJ1748 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,i;
        int result = 0;
        N = Integer.parseInt(br.readLine());

        for(i=1;Math.pow(10,i)-1<=N;i++) {
            result += (9 * Math.pow(10,i-1)*i);
        }
        result += ((N-Math.pow(10,i-1)+1)*i);

        System.out.println(result);
    }
}
