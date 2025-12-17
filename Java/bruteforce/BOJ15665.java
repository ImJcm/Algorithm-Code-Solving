package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/*
N과 M (11)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	19524	14753	11924	76.377%
문제
N개의 자연수와 자연수 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.

N개의 자연수 중에서 M개를 고른 수열
같은 수를 여러 번 골라도 된다.
입력
첫째 줄에 N과 M이 주어진다. (1 ≤ M ≤ N ≤ 7)

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
1 1
1 7
1 9
7 1
7 7
7 9
9 1
9 7
9 9
예제 입력 3
4 4
1 1 2 2
예제 출력 3
1 1 1 1
1 1 1 2
1 1 2 1
1 1 2 2
1 2 1 1
1 2 1 2
1 2 2 1
1 2 2 2
2 1 1 1
2 1 1 2
2 1 2 1
2 1 2 2
2 2 1 1
2 2 1 2
2 2 2 1
2 2 2 2
출처
문제를 만든 사람: baekjoon
알고리즘 분류
백트래킹
 */
/*
알고리즘 핵심
bruteforce (back-tracking)
처음 접근으로 dfs()로 아래와 같은 로직으로 수열을 만들 수 있었다.
1. 사전 순으로 출력하기 위해 입력으로 주어진 자연수 배열을 정렬한다.
2. 중복되는 수열을 방지하기 위해 n번째 자리의 num이 사용 여부를 판단하는 visited2배열을 사용한다.
3. n번째 자리에서 수가 변경될 때, 다음으로 올 수 있는 n+1번째 자리의 visited2 배열을 초기화한다.

두번째 접근으로는 조금만 생각해보면 중복되는 수열을 만들지 않기 위해 만든 visited2배열이 단순히 nums의 자연수의 값이 중복되는 것을
지우면 해결되는 것이다.

1 4 9 9 -> 1 4 9로 이루어진 수열을 만드는 것으로 문제의 조건을 모두 만족한다.

dfs_2()를 통해 문제의 조건을 만족한다.
1. nums를 오름차순 정렬과 중복되는 수를 제거한 배열로 만든다.
2. 모든 n번째 자리의 시작되는 수를 nums의 처음부터 시작하는 수열을 만든다.
3. 같은 수를 여러번 골라도 되므로 방문 여부를 확인하는 배열은 필요하지 않다.
 */
public class BOJ15665 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static int[] nums;
    static boolean[][] visited2;
    static StringBuilder ans;
    final static int MAX_N = 8;
    final static int MAX_NUM = 10_001;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        dfs(0,new ArrayList<>());
        dfs_2(0, new ArrayList<>());

        System.out.println(ans.toString());
    }

    private static void dfs_2(int n, ArrayList<Integer> arr) {
        if(n == M) {
            ans.append(arr.toString().replaceAll("[,\\[\\]]","")).append("\n");
            return;
        }

        for(int i = 0; i < nums.length; i++) {
            arr.add(nums[i]);
            dfs_2(n + 1, arr);
            arr.remove(n);
        }
    }

    /*
        nums의 자연수를 중복 제거하지 않는 상태의 dfs
     */
    private static void dfs(int n, ArrayList<Integer> arr) {
        if(n == M) {
            ans.append(arr.toString().replaceAll("[,\\[\\]]","")).append("\n");
            return;
        }

        for(int i = 0; i < N; i++) {
            int num = nums[i];
            if(visited2[n][num]) continue;

            Arrays.fill(visited2[n + 1], false);

            visited2[n][num] = true;
            arr.add(num);
            dfs(n + 1, arr);
            arr.remove(n);
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        nums = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .distinct()
                .sorted()
                .toArray();

        ans = new StringBuilder();

        visited2 = new boolean[MAX_N][MAX_NUM];
    }
}
