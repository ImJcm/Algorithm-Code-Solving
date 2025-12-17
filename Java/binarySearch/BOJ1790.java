package binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
수 이어 쓰기 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	64 MB	13002	3812	2827	31.485%
문제
1부터 N까지의 수를 이어서 쓰면 다음과 같이 새로운 하나의 수를 얻을 수 있다.

1234567891011121314151617181920212223...

이렇게 만들어진 새로운 수에서, 앞에서 k번째 자리 숫자가 어떤 숫자인지 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N(1 ≤ N ≤ 100,000,000)과, k(1 ≤ k ≤ 1,000,000,000)가 주어진다. N과 k 사이에는 공백이 하나 이상 있다.

출력
첫째 줄에 앞에서 k번째 자리 숫자를 출력한다. 수의 길이가 k보다 작아서 k번째 자리 숫자가 없는 경우는 -1을 출력한다.

예제 입력 1
20 23
예제 출력 1
6
출처
문제를 만든 사람: author5
데이터를 추가한 사람: mym0404, qktlf789456
알고리즘 분류
수학
구현
 */
/*
알고리즘 핵심
수학 + 구현 + 이분탐색
1. N이 주어질 때, 10의 단위 (1,10,100,1000,...)자리 수를 기준으로 각 구간의 숫자길이를 구한다.
2. N까지의 숫자에서 10^n의 구간에 해당하는 숫자들의 길이를 누적하여 구한다.
N까지 이어진 숫자 문자열의 길이보다 K가 작다면 -1을 출력한다.
3. 2번 과정에서 10의 단위 자리의 숫자에서 N 보다 작은 10의 단위의 숫자의 지수를 구한다.
해당 지수의 값이 구해지는 동안 K번째 횟수에 이전 구간의 길이를 빼주어 업데이트한다.
4. 10^지수를 시작으로 이전 구간의 길이를 제거한 k 번째 만큼의 숫자를 구한다.
이때, 지수값만큼 구간의 길이가 결정되므로 k를 지수값으로 나누어 k번째에 해당하는 숫자를 구하고,
해당하는 숫자에서 지점을 찾기 위해 k에서 지수값을 나눈 나머지 값에 해당하는 위치의 숫자를 ans에 저장한다.

이분탐색 방법으로는 해당 자릿수 구간에 속하는 숫자의 개수를 (n - 10^p + 1) 규칙으로 나타내어 전체 자릿수를 구하고
(l,r)의 구간에서 중심값((l + r) / 2)의 자릿수 길이를 구하고 K번째와 비교하여 K번째에 속하는 숫자를 구한다.
해당 숫자까지의 총 길이를 구한 후, K를 뺀 위치의 숫자를 ans에 출력한다.
 */
public class BOJ1790 {
    public static void main(String[] args) throws IOException {
        //Solve_implementation task = new Solve_implementation();
        Solve_binarySearch task = new Solve_binarySearch();
        task.solve();
    }

    public static class Solve_binarySearch {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,K,ans;

        private void solve() throws IOException {
            init_setting();
            binarySearch();
            System.out.println(ans);
        }

        private void binarySearch() {
            String ans_str = "";
            /*
                (n - check + 1)은 해당 자릿수 구간에 속하는 숫자의 개수를 의미
                1) check = 1 => n : 1자리 수는 1~9
                2) check = 10 => n - 9 : 10자리 수는 10~99
                3) check = 100 => n - 99 : 100자리 수는 100~999
                ...
                f(n) = (n - 10^p + 1)
                N=13) 일때
                1. check = 1) 1~9 : 9
                2. check = 10) 10~13 : 4 => 자리 수는 2개이므로 4 x 2
                (=> check = 1 => 1~9 + 10~13의 1의 자리수도 포함해서 본다면, 9 + 4(0,1,2,3)
             */
            int n = 1;
            int nl = 0;
            while(n <= N) {
                nl += (N - n + 1);
                n *= 10;
            }

            if(K > nl) ans = -1;
            else {
                int l = 1;
                int r = N;

                while(l <= r) {
                    int m = (l + r) / 2;
                    nl = 0;
                    n = 1;

                    while(n <= N) {
                        nl += (m - n + 1);
                        n *= 10;
                    }

                    if(nl >= K) {
                        ans = m;
                        r = m - 1;
                    } else {
                        l = m + 1;
                    }
                }

                ans_str = Integer.toString(ans);
                nl = 0;
                n = 1;
                while(n <= N) {
                    nl += (ans - n + 1);
                    n *= 10;
                }

                // K번째 숫자가 포함된 숫자를 찾는 과정에서 ans의 값을 sum >= K에서 설정하기 때문에 K번째 숫자는 ans가 포함된 숫자의 문자열 중 하나이다.
                ans = ans_str.charAt(ans_str.length() - (nl - K) - 1) - '0';
            }

            System.out.println(ans);
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            K = Integer.parseInt(input[1]);

            ans = 0;
        }
    }

    public static class Solve_implementation {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,K,nl,ans;

        private void solve() throws IOException {
            init_setting();
            cal_n_k();
        }

        private void cal_n_k() {
            int p = 1;
            int r = 0;
            int k = K;
            int s = 0;
            nl = 0;

            while((int) Math.pow(10,p) <= N) {
                int w = 9 * (int) Math.pow(10,p-1);
                r += w;
                nl += (p * w);

                if(k >= (p * w)) {
                    k -= (p * w);
                    s = p;
                }
                p++;
            }

            nl += (p * (N - r));

            if(nl < K) ans = -1;
            else {
                int start = (int) Math.pow(10,s);
                int n = k / (s + 1);
                int m = k % (s + 1);

                start += (n + (m == 0 ? -1 : 0));

                String str = Integer.toString(start);

                if(m == 0) {
                    ans = (int) str.charAt(str.length() - 1) - '0';
                } else {
                    ans = (int) str.charAt(m - 1) - '0';
                }
            }

            System.out.println(ans);
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            K = Integer.parseInt(input[1]);
        }
    }
}
