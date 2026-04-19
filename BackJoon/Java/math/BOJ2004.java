package math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
조합 0의 개수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	56065	15944	13145	28.914%
문제
 
$n \choose m$의 끝자리
$0$의 개수를 출력하는 프로그램을 작성하시오.

입력
첫째 줄에 정수
$n$,
$m$ (
$0 \le m \le n \le 2,000,000,000$,
$n \ne 0$)이 들어온다.

출력
첫째 줄에
$n \choose m$의 끝자리
$0$의 개수를 출력한다.

예제 입력 1
25 12
예제 출력 1
2
출처
데이터를 추가한 사람: dcrgkev
알고리즘 분류
수학
정수론
 */
/*
알고리즘 핵심
수학 (정수론 + 조합(Combination))
- nCr = n! / ((n - r)! * r!)
- nCr = nC(n-r)
2C0 = 1, 2C1 = 2, ...
3C0 = 1, 3C1 = 3, ...
4C0 = 1, 4C1 = 4, 4C2 = 6, ...
5C0 = 1, 5C1 = 5, 5C2 = 10, ...
6C0 = 1, 6C1 = 6, 6C2 = 15, 6C3 = 20, ...
7C0 = 1, 7C1 = 7, 7C2 = 21, 7C3 = 35, ...
8C0 = 1, 8C1 = 8, 8C2 = 28, 8C3 = 56, 8C4 = 70, ...
9C0 = 1, 9C1 = 9, 9C2 = 36, 9C3 = 84, 9C4 = 126, ...
10C0 = 1, 10C1 = 10, 10C2 = 45, 10C3 = 120, 10C4 = 210, 10C5 = 252, ...
11C0 = 1, 11C1 = 11, 11C2 = 55, 11C3 = 165, 11C4 = 330, 11C5 = 462, ...
12C0 = 1, 12C1 = 12, 12C2 = 66, 12C3 = 220, 12C4 = 495, 12C5 = 792, 12C6 = 924

규칙성을 이용하여 찾는 것인줄 알았지만 해당방법으로는 뒷부분의 0의 개수를 찾을 수 없었다.

풀이를 어떻게 해야할지 몰라서 솔루션을 참고하고 풀이의 핵심을 생각해보았다.
https://www.acmicpc.net/board/view/72777

1. nCr은 n! / ((n-r)! * r!)의 값이다.
2. nCr의 값에서 뒷부분의 0인 개수를 찾기 위해서는 10이라는 값을 분자와 분모에서 각각 갯수를 찾아야한다.
-> 뒷부분의 0의 개수는 분자에 10이 얼만큼 곱해져 있는지 여부에 따라 결정되기 때문이다.
3. 10의 값은 2와 5를 곱하여 만들 수 있다.

따라서, 1,2,3에 의해 분자와 분모에서 2,5의 값이 얼마나 곱해져 있는지를 구하고, 각각 두 수의 갯수에서 최소값은 10이 곱해진 수를 의미하게 된다.
-> 10! = 10 x 9 x 8 x 7 x 6 x 5 x 4 x 3 x 2 x 1
-> 2가 곱해진 갯수 - 10(1), 8(3), 6(1), 4(2), 2(1) => 8개
-> 5가 곱해진 갯수 - 10(1), 5(2) => 2개
2 < 8이므로 10이 곱해진 갯수는 2개이고, 10! = 3628800, 10!의 마지막 부분의 0의 갯수는 2개이다.

이를 이용하면, 분자,분모 각각 2,5가 곱해진 갯수를 구한다.
A = 분자의 2의 갯수 - 분모의 2의 갯수
B = 분자의 5의 갯수 - 분모의 5의 갯수
min(A,B) (A,B > 0, else ans = 0)
 */
public class BOJ2004 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,N_2,N_5,M_2,M_5,N_SUB_M_2,N_SUB_M_5;
    static final long MAX_LIMIT = 2_000_000_000;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static int find_multiplied_num(int target, int num) {
        int ans = 0;

        long i = num;

        while(i <= MAX_LIMIT) {
            ans += (int) (target / i);
            i *= num;
        }

        return ans;
    }

    private static void solve() {
        N_2 = find_multiplied_num(N,2);
        N_5 = find_multiplied_num(N,5);

        M_2 = find_multiplied_num(M,2);
        M_5 = find_multiplied_num(M,5);

        N_SUB_M_2 = find_multiplied_num(N - M, 2);
        N_SUB_M_5 = find_multiplied_num(N - M, 5);

        int total_2 = Math.max(0, N_2 - (M_2 + N_SUB_M_2));
        int total_5 = Math.max(0, N_5 - (M_5 + N_SUB_M_5));

        int ans = total_2 > 0 && total_5 > 0 ? Math.min(total_2, total_5) : 0;

        System.out.println(ans);
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        N_2 = 0;
        N_5 = 0;
        M_2 = 0;
        M_5 = 0;
        N_SUB_M_2 = 0;
        N_SUB_M_5 = 0;
    }
}
