package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/*
N과 M (10)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	19566	15397	12703	79.290%
문제
N개의 자연수와 자연수 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.

N개의 자연수 중에서 M개를 고른 수열
고른 수열은 비내림차순이어야 한다.
길이가 K인 수열 A가 A1 ≤ A2 ≤ ... ≤ AK-1 ≤ AK를 만족하면, 비내림차순이라고 한다.
입력
첫째 줄에 N과 M이 주어진다. (1 ≤ M ≤ N ≤ 8)

둘째 줄에 N개의 수가 주어진다. 입력으로 주어지는 수는 10,000보다 작거나 같은 자연수이다.

출력
한 줄에 하나씩 문제의 조건을 만족하는 수열을 출력한다. 중복되는 수열을 여러 번 출력하면 안되며, 각 수열은 공백으로 구분해서 출력해야 한다.

수열은 사전 순으로 증가하는 순서로 출력해야 한다.

예제 입력 1
3 1
4 4 2
예제 출력 1
2
4
예제 입력 2
4 2
9 7 9 1
예제 출력 2
1 7
1 9
7 9
9 9
예제 입력 3
4 4
1 1 2 2
예제 출력 3
1 1 2 2
출처
문제를 만든 사람: baekjoon
알고리즘 분류
백트래킹
 */
/*
알고리즘 핵심
bruteforce (back-tracking)
1. dfs를 이용하여 M개의 수열을 만드는 과정을 수행한다.
2. 고른 수열은 비내림차순을 만족하기 위해 주어지는 자연수를 오름차순 정렬을 수행한다.
3. 수열의 비내림차순을 만족하기 위해 n자리의 수열의 값은 이전 자리(n-1)자리의 수열 값보다 커야하므로 n자리에 오는 수열의 값의 시작을
이전 자리의 수열의 값 시작 인덱스에서 고를 수 있도록 한다.
4. 수열의 중복을 제거하기 위해 i번째 자연수가 수열에 쓰였는지 여부를 결정하는 visited와 n번째 자리에 이미 배치가 되었는지 여부를 결정하는 visited2 배열을
사용한다.
 */
public class BOJ15664 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static int[] nums;
    static boolean[] visited;
    static boolean[][] visited2;
    static StringBuilder ans;
    final static int MAX_NUM = 10_001;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        dfs(0, 0, new ArrayList<>());

        System.out.println(ans.toString());
    }

    private static void dfs(int n, int s, ArrayList<Integer> arr) {
        if(n == M) {
            String res = arr.toString().replaceAll(",","").replace("[","").replace("]","");
            ans.append(res).append("\n");
            return;
        }

        for(int i = s; i < N; i++) {
            int num = nums[i];
            if(visited[i]) continue;
            if(visited2[n][num]) continue;

            Arrays.fill(visited2[n + 1], false);

            visited2[n][num] = true;
            visited[i] = true;
            arr.add(num);
            dfs(n + 1, i, arr);
            arr.remove(n);
            visited[i] = false;
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        nums = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .sorted()
                .toArray();

        visited = new boolean[N];
        visited2 = new boolean[9][MAX_NUM];

        ans = new StringBuilder();
    }
}
