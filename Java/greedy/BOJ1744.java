package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
수 묶기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	51465	18443	14618	34.712%
문제
길이가 N인 수열이 주어졌을 때, 그 수열의 합을 구하려고 한다. 하지만, 그냥 그 수열의 합을 모두 더해서 구하는 것이 아니라, 수열의 두 수를 묶으려고 한다. 어떤 수를 묶으려고 할 때, 위치에 상관없이 묶을 수 있다. 하지만, 같은 위치에 있는 수(자기 자신)를 묶는 것은 불가능하다. 그리고 어떤 수를 묶게 되면, 수열의 합을 구할 때 묶은 수는 서로 곱한 후에 더한다.

예를 들면, 어떤 수열이 {0, 1, 2, 4, 3, 5}일 때, 그냥 이 수열의 합을 구하면 0+1+2+4+3+5 = 15이다. 하지만, 2와 3을 묶고, 4와 5를 묶게 되면, 0+1+(2*3)+(4*5) = 27이 되어 최대가 된다.

수열의 모든 수는 단 한번만 묶거나, 아니면 묶지 않아야한다.

수열이 주어졌을 때, 수열의 각 수를 적절히 묶었을 때, 그 합이 최대가 되게 하는 프로그램을 작성하시오.

입력
첫째 줄에 수열의 크기 N이 주어진다. N은 50보다 작은 자연수이다. 둘째 줄부터 N개의 줄에 수열의 각 수가 주어진다. 수열의 수는 -1,000보다 크거나 같고, 1,000보다 작거나 같은 정수이다.

출력
수를 합이 최대가 나오게 묶었을 때 합을 출력한다. 정답은 항상 231보다 작다.

예제 입력 1
4
-1
2
1
3
예제 출력 1
6
예제 입력 2
6
0
1
2
4
3
5
예제 출력 2
27
예제 입력 3
1
-1
예제 출력 3
-1
예제 입력 4
3
-1
0
1
예제 출력 4
1
예제 입력 5
2
1
1
예제 출력 5
2
출처
문제의 오타를 찾은 사람: acka, bupjae, jwvg0425, rainshot, silvercube, tncks0121
문제를 번역한 사람: baekjoon
데이터를 추가한 사람: godnes628
알고리즘 분류
그리디 알고리즘
정렬
많은 조건 분기
 */
/*
알고리즘 핵심
그리디 알고리즘 + 정렬
1. 주어진 수열에서 어떠한 위치의 두 수를 묶어서 최대의 합을 만들기 위해 "위치는 상관없는"이라는 조건에 의해 정렬이 필요하다.
2. 양수로 주어지는 수열의 값과 음수 + 0을 포함한 값을 가지는 배열을 나눈 후, 두 배열에서 각각 내림차순과 오름차순으로 정렬한다.
3. 양수 배열에서 큰 값을 시작으로하여 우선적으로 다음에 오는 수와 곱한 값과 더한 값을 비교하여 큰 경우 두 수를 묶는 상태로 만들고 최종 결과에
더하고 인덱스를 추가로 + 1한다.
작거나 같은 경우 현재 인덱스의 수열의 값을 최종 결과에 더한다.
이 과정을 음수 + 0을 포함한 배열에서도 수행한다.
4. 최종 결과를 ans에 누적하여 갱싱한다.
 */
public class BOJ1744 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static ArrayList<Integer> plus_seq,minus_zero_seq;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        ans += operation(plus_seq);
        ans += operation(minus_zero_seq);

        System.out.println(ans);
    }

    private static int operation(ArrayList<Integer> arr) {
        int r = 0;
        for(int i = 0; i < arr.size(); i++) {
            int n1 = arr.get(i);
            int n2 = (i + 1 >= arr.size() ? Integer.MAX_VALUE : arr.get(i + 1));

            int r1 = n1 * (n2 == Integer.MAX_VALUE ? 1 : n2);
            int r2 = n1 + (n2 == Integer.MAX_VALUE ? 0 : n2);

            if(r1 > r2) {
                r += r1;
                i++;
            } else {
                r += n1;
            }
        }

        return r;
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        plus_seq = new ArrayList<>();
        minus_zero_seq = new ArrayList<>();

        for(int i = 0; i < N; i++) {
            int n = Integer.parseInt(br.readLine());
            if(n > 0) plus_seq.add(n);
            else minus_zero_seq.add(n);
        }

        Collections.sort(plus_seq, Collections.reverseOrder());
        Collections.sort(minus_zero_seq);
    }
}
