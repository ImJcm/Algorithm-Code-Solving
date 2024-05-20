package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
동전 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초 (추가 시간 없음)	128 MB	74634	22997	16372	29.888%
문제
n가지 종류의 동전이 있다. 이 동전들을 적당히 사용해서, 그 가치의 합이 k원이 되도록 하고 싶다. 그러면서 동전의 개수가 최소가 되도록 하려고 한다. 각각의 동전은 몇 개라도 사용할 수 있다.

입력
첫째 줄에 n, k가 주어진다. (1 ≤ n ≤ 100, 1 ≤ k ≤ 10,000) 다음 n개의 줄에는 각각의 동전의 가치가 주어진다. 동전의 가치는 100,000보다 작거나 같은 자연수이다. 가치가 같은 동전이 여러 번 주어질 수도 있다.

출력
첫째 줄에 사용한 동전의 최소 개수를 출력한다. 불가능한 경우에는 -1을 출력한다.

예제 입력 1
3 15
1
5
12
예제 출력 1
3
출처
잘못된 조건을 찾은 사람: apples1309, djm03178
빠진 조건을 찾은 사람: goodcrane3
데이터를 추가한 사람: hayman42, isac322, kory0711, paraworld
알고리즘 분류
다이나믹 프로그래밍
 */
/*
동전의 가치의 총합인 k를 1부터 k+1까지 각 동전을 사용하여 메모리제이션을 적용하는 방법을 사용하였다.
k인 가치를 만들기 위해 k - 사용한 동전 가치를 뺀 최소 동전 개수에서 + 1을 적용하면,
memorization[k] = memorization[k - values[i]] (0 <= i < n)인 점화식을 적용하여 기존의 값과 점화식 값 중 최소값을 적용하여
최소 동전 개수를 만족할 수 있다.
 */
public class BOJ2294 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int n,k;
    static int[] values,memorization;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        memorization[0] = 1;

        for(int i=0;i<n;i++) {
            for(int j=1;j<=k;j++) {
                if(j - values[i] >= 0) {
                    int next_v = memorization[j - values[i]] == Integer.MAX_VALUE ? Integer.MAX_VALUE - 1 : memorization[j - values[i]];
                    memorization[j] = Math.min(next_v + 1, memorization[j]);
                }
            }
        }
        System.out.println(memorization[k] == Integer.MAX_VALUE ? -1 : memorization[k]);
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        n = Integer.parseInt(input[0]);
        k = Integer.parseInt(input[1]);

        values = new int[n];
        memorization = new int[k+1];

        for(int i=0;i<=k;i++) {
            memorization[i] = Integer.MAX_VALUE;
        }

        for(int i=0;i<n;i++) {
            values[i] = Integer.parseInt(br.readLine());
            if(values[i] > k) continue;
            memorization[values[i]] = 1;
        }
    }
}
