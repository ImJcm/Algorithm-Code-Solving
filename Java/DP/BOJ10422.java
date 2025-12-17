package DP;/*
괄호

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	7145	2154	1577	29.879%
문제
‘(‘, ‘)’ 문자로만 이루어진 문자열을 괄호 문자열이라 한다. 올바른 괄호 문자열이란 다음과 같이 정의된다. ()는 올바른 괄호 문자열이다. S가 올바른 괄호 문자열이라면, (S)도 올바른 괄호 문자열이다. S와 T가 올바른 괄호 문자열이라면, 두 문자열을 이어 붙인 ST도 올바른 괄호 문자열이다. (()())()은 올바른 괄호 문자열이지만 (()은 올바른 괄호 문자열이 아니다. 괄호 문자열이 주어졌을 때 올바른 괄호 문자열인지 확인하는 방법은 여러 가지가 있다.

하지만 우리가 궁금한 것은 길이가 L인 올바른 괄호 문자열의 개수이다. 길이 L이 주어졌을 때 길이가 L인 서로 다른 올바른 괄호 문자열의 개수를 출력하는 프로그램을 만들어 보자.

입력
첫 번째 줄에 테스트케이스의 개수를 나타내는 T (1 ≤ T ≤ 100)가 주어진다. 두 번째 줄부터 각 테스트케이스마다 괄호 문자열의 길이를 나타내는 L이 주어진다. (1 ≤ L ≤ 5000)

출력
각 테스트 케이스에 대해 길이가 L인 올바른 괄호 문자열의 개수를 1,000,000,007로 나눈 나머지를 출력하시오.

예제 입력 1
3
1
2
4
예제 출력 1
0
1
2
 */
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ10422 {
    static int N,T;
    static long[] mem;
    static long mod = 1000000007;
    //catalan
    static long[] cat_mem;

    //dfs
    static int[] visited;
    static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        mem = new long[5000+1];
        Arrays.fill(mem,0);

        mem[0] = 1;
        mem[2] = 1;
        //1 : '(', -1 : ')'
        visited = new int[5000+1];
        cat_mem = new long[5001];
        Arrays.fill(cat_mem,0);
        cat_mem[1] = 0;
        cat_mem[2] = 1;
        cat_mem[4] = 2;

        //Catalan_x(5000);
        Catalan();
        for(int i=0;i<N;i++) {
            count = 0;
            T = Integer.parseInt(br.readLine());

            visited[1] = 1;
            visited[T] = -1;
            if(T % 2 != 0) {
                System.out.println(0);
            } else {
                //System.out.println(Catalan_x(T));
                System.out.println(mem[T]);
                dfs(2,1);
            }
            System.out.println(count % mod);
        }

    }
    /*
        참고 - https://loosie.tistory.com/329
        초기값
        dp[0]  = 1  ☛  "" 빈공간

        dp[2] = 1   ☛  ()

        시뮬레이션
        홀수로는 올바른 괄호를 생성할 수 없다. 2의 배수 단위로 시뮬레이션을 돌려보자.
        dp[4] = 2 ☛ ("")(), (())""  ☛  (dp[0])dp[2] +(dp[2])dp[0]
        dp[6] = 5 ☛ ("")()(), ("")(()), (())(), (()())"", ((()))"" ☛ (dp[0])dp[4] + (dp[2])dp[2] + (dp[4])dp[4]
        dp[8] = 14 ☛ dp[0]*dp[6] + dp[2]*dp[4] + dp[4]*dp[2] + dp[6]dp[0]

        이런식으로 i개일 때, 새로 생성되는 괄호 ()를 제외하고 총 i-2의 괄호 경우를 고려해서 설계해주면 된다.

        + 문자열의 길이가 n으로 주어질 때, 양 끝의 "()"을 제외하고 n-2의 괄호부분을 고려하면 되는데,
        카탈린 수 공식으로 Cn = (C_0 * C_n-2 + C_2 * C n-4 + ... + C_n-2 * C_0)
     */

    static void Catalan() {
        for(int i=2;i<2501;i++) {
            for(int j=0;j<i;j++) {
                mem[i*2] += (mem[j*2]*mem[(i-1-j)*2]);
                mem[i*2] %= mod;
            }
        }
    }
    /*
    //참고 - https://m.blog.naver.com/pyw0564/221523147108
        카탈란 수(Catalan number) : 조합론에서 빈번하게 나타타는 수열을 의미
         = 길이가 n일 때, n/2개의 노드로 이진트리를 만들 수 있는 경우의 수
        Cn = 1/(n+1) * (2n n) = (2n)! / n!(n+1)!
        X와 Y로 이루어진 길이가 2n인 문자열이 있다고 합시다. 맨 앞에서 X와 Y를 세었을 때 X가 Y보다 크거나 같은 문자열을 구하면
        XXXYYY     XYXXYY     XYXYXY     XXYYXY     XXYXYY  총 5가지 모양이 됩니다. 이는 뒤크단어(Dyck word)라고도 합니다.
        M[n] = M[n-2] * ((n*n-1) / ((n/2) * (n/2 + 1)))
     */
    //아래 코드에서 틀리는 이유는 long 타입의 제한범위수를 넘어가는 값이 들어가기 때문일 것이라고 예상한다.
    //다른 방법을 찾아본다.
    static long Catalan_x(int n) {
//        long res = 1;
//        long res_b = 1;
        if(cat_mem[n] != 0) return cat_mem[n];
//        for(int i=n;i>(n/2)+1;i--) {
//            res *= i;
//        }
//
//        for(int i=n/2;i>=1;i--) {
//            res_b *= i;
//        }
//        cat_mem[n] = res / res_b;
        for(int i=4;i<=n;i+=2) {
            cat_mem[i] = (cat_mem[i-2] * (i * (i-1))) / ((i/2) * ((i/2) + 1));
            //cat_mem[i] %= mod;
        }
        return cat_mem[n] % mod;
    }

    //시간초과
    static void dfs(int t,int b) {
        if(t == T) {
            int bal = 0;
            for(int i=1;i<=T;i++) {
                bal += visited[i];
                //if(bal < 0) return;
            }
            if(bal == 0) {
                count++;
                for(int i=1;i<=T;i++) {
//                    if(visited[i] == 1) System.out.print('(');
//                    else System.out.print(')');
                }
//                System.out.println();
            }
            return;
        }

        //branch-cut
        if(b < 0) return;

        visited[t] = 1;
        dfs(t+1,b+1);
        visited[t] = -1;
        dfs(t+1,b-1);
    }
}
/*
1 : 0
2 : 1
4 : 2
6 : 5
8 : 14
10 : 42
12 : 132
14 : 429
16 : 1430
18 : 4862
 */
