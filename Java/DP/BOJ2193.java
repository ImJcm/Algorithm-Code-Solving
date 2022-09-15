/*
이친수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	76493	31842	23947	39.978%
문제
0과 1로만 이루어진 수를 이진수라 한다. 이러한 이진수 중 특별한 성질을 갖는 것들이 있는데, 이들을 이친수(pinary number)라 한다. 이친수는 다음의 성질을 만족한다.

이친수는 0으로 시작하지 않는다.
이친수에서는 1이 두 번 연속으로 나타나지 않는다. 즉, 11을 부분 문자열로 갖지 않는다.
예를 들면 1, 10, 100, 101, 1000, 1001 등이 이친수가 된다. 하지만 0010101이나 101101은 각각 1, 2번 규칙에 위배되므로 이친수가 아니다.

N(1 ≤ N ≤ 90)이 주어졌을 때, N자리 이친수의 개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N이 주어진다.

출력
첫째 줄에 N자리 이친수의 개수를 출력한다.

예제 입력 1
3
예제 출력 1
2
 */
/*
    DP문제의 경우, 경우의 수가 많아지는 경우에는 결과값(res), 메모리제이션(mem)의 type을 저장되는 값이 큰값일 수 있기에
    long type을 사용하는 것이 좋다.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class BOJ2193 {
    static int N;
    static long[][] mem;
    static long[] mem2;
    static long res = 0;
    static long res2 = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        mem = new long[N+1][2];

        for(int i=0;i<=N;i++) {
            Arrays.fill(mem[i],0);
        }

        mem[1][0] = 0;
        mem[1][1] = 1;

        System.out.println(dp(N));

        mem2 = new long[N+1];

        mem2[0] = 0;
        mem2[1] = 1;
        mem2[2] = 1;

        System.out.println(dp2(N));
    }

    static long dp(int n) {
        for(int i=2;i<=n;i++) {
            for(int j=0;j<2;j++) {
                if(j == 0) {
                    mem[i][j] += (mem[i-1][j] + mem[i-1][j+1]);
                } else {
                    mem[i][j] += mem[i-1][j-1];
                }
            }
        }

        res = (mem[n][0] + mem[n][1]);
        return res;
    }

    /*
        어떻게 이런 점화식이 나오는지는 잘 모르겠지만,
        n=1, 2, ... 값을 나열했을 때는 점화식을 구할 수 있을 것같다.
     */
    static long dp2(int n) {
        for(int i=3;i<=n;i++) {
           mem2[i] = mem2[i-1] + mem2[i-2];
        }

        return mem2[n];
    }
}
