package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/*
N과 M (9)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	54721	27594	21053	49.434%
문제
N개의 자연수와 자연수 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.

N개의 자연수 중에서 M개를 고른 수열
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
7 1
7 9
9 1
9 7
9 9
예제 입력 3
4 4
1 1 1 1
예제 출력 3
1 1 1 1
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: kid7ho
알고리즘 분류
백트래킹
 */
/*
알고리즘 핵심
BruteForce (Back Tracking)
1. 모든 요소 N개에서 M개의 수열을 만드는 백트랙킹을 기본으로 한다.
2. 중복되는 수열을 제거하기 위해 n번째 수가 이미 쓰였다는 것을 알기 위해 visited2[n][num] 배열을 사용한다.
-> n번째의 수열의 값으로 num이 사용되었다면 true
visited2배열을 통해 1 7 9 9와 같은 N개의 자연수에서
1 9(3) -> visited2[1][9] = true -> 1 9(4)는 중복되는 수열로 판단하여 넘긴다.

visited2를 초기화하는 조건은 다음과 같다.
앞선 n번째 자리수에서 수가 바뀐 경우 visited2[n+1]에서 올 수 있는 자연수의 중복 여부 배열을 초기화한다.

N = 1 1 2 2 3 3, M = 3인 경우
1 1 2 -> visited2[2][2] = true
1 1 3
1 2 2 -> 여기서 visited2[2][2]는 첫 수열의 값과는 관련없이 중복되지 않는 수열이다.
앞선 2번째 자리의 값이 변하는 경우, 앞으로 올 수 있는 자연수의 중복 여부는 초기화가 필요하다.
따라서, n번째 수열의 값의 변하는 경우, n+1에서 올 수 있는 수열의 중복 여부를 초기화한다.
 */
public class BOJ15663 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static int[] nums;
    static boolean[] visited;
    static boolean[][] visited2;
    static StringBuilder sb;
    final static int MAX_LENGTH = 10_001;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        dfs(0, new ArrayList<>());

        System.out.println(sb.toString());
    }

    private static void dfs(int n, ArrayList<Integer> arr) {
        if(n == M) {
            sb.append(arr.toString().replaceAll(", "," ").replace("[","").replace("]","")).append("\n");
            return;
        }

        for(int i = 0; i < N; i++) {
            int num = nums[i];
            if(visited[i]) continue;
            if(visited2[n][num]) continue;

            Arrays.fill(visited2[n + 1], false);

            visited[i] = true;
            visited2[n][num] = true;
            arr.add(num);
            dfs(n + 1, arr);
            //arr.removeLast(); // jdk15 - 지원하지 않는 메서드
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
        visited2 = new boolean[9][MAX_LENGTH];

        sb = new StringBuilder();
    }
}
