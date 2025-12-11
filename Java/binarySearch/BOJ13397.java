package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
구간 나누기 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	4938	3113	2353	64.272%
문제
N개의 수로 이루어진 1차원 배열이 있다. 이 배열을 M개 이하의 구간으로 나누어서 구간의 점수의 최댓값을 최소로 하려고 한다. 구간은 다음과 같은 조건을 만족해야 한다.

하나의 구간은 하나 이상의 연속된 수들로 이루어져 있다.
배열의 각 수는 모두 하나의 구간에 포함되어 있어야 한다.
구간의 점수란 구간에 속한 수의 최댓값과 최솟값의 차이이다.

예를 들어, 배열이 [1, 5, 4, 6, 2, 1, 3, 7] 이고, M = 3인 경우가 있다.

이때, [1, 5], [4, 6, 2], [1, 3, 7]로 구간을 나누면 각 구간의 점수는 4, 4, 6점이 된다. 이때, 최댓값은 6점이다.

만약, [1, 5, 4], [6, 2, 1], [3, 7]로 구간을 나누었다면, 각 구간의 점수는 4, 5, 4점이 되고, 이때 최댓값은 5점이 된다.

두 경우 중에서 최댓값이 최소인 것은 5점인 것이고, 5점보다 최댓값을 작게 만드는 방법은 없다.

배열과 M이 주어졌을 때, 구간의 점수의 최댓값의 최솟값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 배열의 크기 N과 M이 주어진다. (1 ≤ N ≤ 5,000, 1 ≤ M ≤ N)

둘째 줄에 배열에 들어있는 수가 순서대로 주어진다. 배열에 들어있는 수는 1보다 크거나 같고, 10,000보다 작거나 같은 자연수이다.

출력
첫째 줄에 구간의 점수의 최댓값의 최솟값을 출력한다.

예제 입력 1
8 3
1 5 4 6 2 1 3 7
예제 출력 1
5
예제 입력 2
4 2
1 1 1 1
예제 출력 2
0
예제 입력 3
7 4
1 2 3 1 2 3 1
예제 출력 3
2
예제 입력 4
5 1
1 100 99 2 3
예제 출력 4
99
예제 입력 5
5 2
1 100 99 2 3
예제 출력 5
98
예제 입력 6
5 3
1 100 99 2 3
예제 출력 6
1
출처
문제를 만든 사람: baekjoon
알고리즘 분류
이분 탐색
매개 변수 탐색
 */
/*
알고리즘 핵심
이분 탐색
1. 입력으로 주어진 N개의 배열에서 최대값과 최소값을 구한 후, 뺀 값을 r로 설정하고 0을 l 값으로 설정한다.
2. l,r 값으로 구간의 최대값과 최소값을 뺀 값을 의미하며 해당 값을 이분 탐색의 대상으로 한다.
3. N개의 배열에서 만들 수 있는 구간의 최대값임과 동시에 최소값을 지정하여 0~N-1까지의 구간에서 해당하는 값보다 작은지 큰지 여부에 따라 이분 탐색 과정을 수행한다.
4. 구간마다 연속된 수의 집합이므로 0부터 i(i = 1 ~ ...)만큼의 끝 구간을 지정하여 해당 구간에서 최대값과 최소값의 차이가 3번에 해당하는 값보다 작다면
i 값을 증가시켜 구간을 확장하고 계속 진행한다.
3번에 해당하는 값보다 크다면, 해당 구간으로는 만들 수 없으므로 구간을 새로 시작한다. (이때, 구간의 수를 증가시킨다.)
5. 모든 과정을 마친 후, 만들어진 구간의 갯수 + 1이 M보다 작거나 같다면, 더 작은 최소값을 만들 수 있는 가능성이 있으므로 r 값을 낮춘다.
그렇지 않다면, 해당 최소값은 만들 수 없으므로 l 값을 증가시킨다.

처음 접근으로 bruteforce를 만들었지만, 시간초과 발생하였다.
이후, 풀이 방법을 도저히 모르겠어서 풀이 힌트를 참고하였다.
중요한 점은 다음과 같이 N개의 배열에서 최대값과 최소값의 차이는 해당 배열에서 만들 수 있는 최대값이므로 최소값과 해당 값을 이분 탐색의 기준인 것이다.
 */
public class BOJ13397 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,M,ans,l,r;
        int[] arr;

        private void solve() throws IOException{
            init_setting();

            binary_search();

            System.out.println(ans);
        }

        private void binary_search() {
            if(l > r) return;

            int m = (l + r) / 2;

            if(check_max_section(m)) {
                r = m - 1;
                ans = m;
            } else l = m + 1;

            binary_search();
        }

        private boolean check_max_section(int m) {
            int section_cnt = 0;
            int i = 1;
            int s = 0;

            while(s + i < N) {
                if(diff_max_min(s, s + i) <= m) {
                    i++;
                    continue;
                } else {
                    s = s + i;
                    section_cnt++;
                    i = 1;
                }
            }

            if(section_cnt + 1 <= M) return true;
            else return false;
        }

        private int diff_max_min(int s, int e) {
            int max_v = 0;
            int min_v = 10001;

            for(int i = s; i <= e; i++) {
                max_v = Math.max(max_v, arr[i]);
                min_v = Math.min(min_v, arr[i]);
            }

            return max_v - min_v;
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            M = Integer.parseInt(input[1]);

            arr = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            ans = 10001;

            l = 0;
            r = Arrays.stream(arr).max().getAsInt() - Arrays.stream(arr).min().getAsInt();
        }
    }

    /*
        시간 초과 : bruteforce 형태의 구간의 최대값 중 최소값을 확인하는 로직이다.
     */
    public static class TimeOut_Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,M,ans;
        int[] arr,m_arr;

        private void solve() throws IOException {
            init_setting();

            for(int i = 1; i <= M; i++) {
                m_arr = new int[M];
                divide_section(0,i,0);
            }

            System.out.println(ans);
        }

        private void divide_section(int sec, int limit, int s) {
            if(sec == limit) {
                if(s == N) ans = Math.min(ans, Arrays.stream(m_arr).max().getAsInt());
                return;
            }

            for(int i = s; i < N; i++) {
                m_arr[sec] = diff_max_min_value(s, i);
                divide_section(sec + 1, limit, i + 1);
            }
        }

        private int diff_max_min_value(int s, int e) {
            int max = 0;
            int min = 10001;

            for(int i = s; i <= e; i++) {
                max = Math.max(max, arr[i]);
                min = Math.min(min, arr[i]);
            }

            return max - min;
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            M = Integer.parseInt(input[1]);

            arr = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            m_arr = new int[M];

            ans = 10001;
        }
    }
}
