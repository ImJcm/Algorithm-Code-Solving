package BackJoon;

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
수학 + 구현

 */
public class BOJ1790 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
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
