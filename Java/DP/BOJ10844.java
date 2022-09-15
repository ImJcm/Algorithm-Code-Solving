/*
쉬운 계단 수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	110995	34978	25174	29.668%
문제
45656이란 수를 보자.

이 수는 인접한 모든 자리의 차이가 1이다. 이런 수를 계단 수라고 한다.

N이 주어질 때, 길이가 N인 계단 수가 총 몇 개 있는지 구해보자. 0으로 시작하는 수는 계단수가 아니다.

입력
첫째 줄에 N이 주어진다. N은 1보다 크거나 같고, 100보다 작거나 같은 자연수이다.

출력
첫째 줄에 정답을 1,000,000,000으로 나눈 나머지를 출력한다.

예제 입력 1
1
예제 출력 1
9
예제 입력 2
2
예제 출력 2
17
 */
/*
    A1. dp -

 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class BOJ10844 {
    static int N;
    static long[][] mem;
    static int mod = 1000000000;
    static long res = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        mem = new long[101][10];

        for(int i=0;i<101;i++) {
            Arrays.fill(mem[i],0);
        }

        for(int i=0;i<10;i++) {
            if (i == 0) {
                mem[1][i] = 0;
            } else if(i == 9) {
                mem[1][i] = 1;
            } else {
                mem[1][i] = 1;
            }
        }

        for(int i=1;i<10;i++) {
            //res += (dp(N,i) % mod);
        }

        res = botup_dp(N);
        System.out.println(res);
    }

    //topdown
    static long dp(int n, int pre_idx) {
        if(n == 0) {
            if(pre_idx == 0 || pre_idx == 9) {
                return 1;
            } else {
                //1~8
                return 2;
            }
        }

        if(mem[n][pre_idx] != 0) return mem[n][pre_idx];

        long ret = 0;

        if(0 < pre_idx && pre_idx < 9) {
            ret = (dp(n - 1, pre_idx + 1) + ret);
            ret = (dp(n - 1, pre_idx - 1) + ret);
        } else if(pre_idx == 9) {
            ret = (dp(n - 1, pre_idx - 1) + ret);
        } else if(pre_idx == 0) {
            ret = (dp(n - 1, pre_idx + 1) + ret);
        }

        //ret를 전부 합하고, mem에 저장할 때 % mod를 해주는 것
        mem[n][pre_idx] = (ret % mod);

        return mem[n][pre_idx];
    }

    //bottomup
    static long botup_dp(int n) {
        for(int i=2;i<=n;i++) {
            for(int j=0;j<10;j++) {
                if(j == 0) {
                    mem[i][j] += (mem[i-1][j+1] % mod);
                } else if(j == 9) {
                    mem[i][j] += (mem[i-1][j-1] % mod);
                } else {
                    mem[i][j] += ((mem[i-1][j-1] + mem[i-1][j+1]) % mod);
                }
            }
        }

        //res += mem[N][i]로 전부 합친 후, return에서 mod로 나누어줄 것.
        for(int i=0;i<10;i++) {
            res += mem[N][i];
        }

        return (res % mod);
    }
}

/*
3 - 32
4 - 61
5 - 116
 */