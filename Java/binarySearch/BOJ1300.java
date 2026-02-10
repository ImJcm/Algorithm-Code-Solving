package binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
K번째 수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	55821	21324	15676	38.709%
문제
세준이는 크기가 N×N인 배열 A를 만들었다. 배열에 들어있는 수 A[i][j] = i×j 이다. 이 수를 일차원 배열 B에 넣으면 B의 크기는 N×N이 된다. B를 오름차순 정렬했을 때, B[k]를 구해보자.

배열 A와 B의 인덱스는 1부터 시작한다.

입력
첫째 줄에 배열의 크기 N이 주어진다. N은 105보다 작거나 같은 자연수이다. 둘째 줄에 k가 주어진다. k는 min(109, N2)보다 작거나 같은 자연수이다.

출력
B[k]를 출력한다.

예제 입력 1
3
7
예제 출력 1
6
출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: jarycoco
빠진 조건을 찾은 사람: mystika
알고리즘 분류
이분 탐색
매개 변수 탐색
 */
/*
알고리즘 핵심
이분 탐색(parametric Search,매개변수 탐색)
1. 일반적인 이분 탐색은 정렬된 상태의 데이터에서 순서를 찾는 방법이지만, 해당 문제는 10^5 x 10^5의 데이터를 1차원 배열로 나열하기에는 너무 크다.
2. 특정 값을 정한 뒤 해당하는 값보다 작은 수의 개수를 계산하여 K번째 수를 찾는다.
3. 2번의 특정값보다 작은 수가 K보다 큰 경우, 해당 값은 K번째의 수의 대상으로 가능하므로 업데이트한 후, r을 줄여 다음 대상을 탐색한다.
K보다 작은 경우, 해당 값은 K번째의 수가 될 수 없으므로 l을 높여 다음 대상을 탐색한다.
4. 2,3번 과정을 통해 K번째 수가 될 수 있는 최소 범위를 측정할 수 있고, 해당 값보다 같거나 큰 값중 최소값인 수를 찾으면 된다.
5. 4번의 과정에서 1~N으로 나누고 나머지가 있는 경우 + 1을 하여 만들 수 있는 값인지 확인하여 최소값을 ans에 업데이트한다.
 */
public class BOJ1300 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    private static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,K;
        long l,r,ans;
        //long[][] A; // 10^5 x 10^5의 최대 크기의 배열은 OutOfMemoryError

        void solve() throws IOException {
            init_setting();

            binary_search();

            check_possible(ans);

            System.out.println(ans);
        }

        private void binary_search() {
            if(l > r) return;

            long mid = (l + r) / 2;

            long c = count_small_than_param(mid);

            if(c >= K) {
                r = mid - 1;
                ans = mid;
            } else l = mid + 1;

            binary_search();
        }

        private void check_possible(long a) {
            long res = (long) Math.pow(100000,2);
            for(int i = 1; i <= N; i++) {
                long j = (a / i) + (a % i != 0 ? 1 : 0);
                if(j > N) continue;
                res = Math.min(res, i * j);
            }

            ans = res;
        }

        private long count_small_than_param(long p) {
            long cnt = Math.min(p, N);

            for(int i = 2; i <= N; i++) {
                cnt += (p / i > N ? N : p / i);
            }

            return cnt;
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());
            K = Integer.parseInt(br.readLine());

            l = 1;
            r = (long) Math.pow(N,2);

            ans = 0;
        }
    }
}