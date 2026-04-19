package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/*
N과 M (12)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	26213	20637	17807	79.379%
문제
N개의 자연수와 자연수 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.

N개의 자연수 중에서 M개를 고른 수열
같은 수를 여러 번 골라도 된다.
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
1 1
1 7
1 9
7 7
7 9
9 9
예제 입력 3
4 4
1 1 2 2
예제 출력 3
1 1 1 1
1 1 1 2
1 1 2 2
1 2 2 2
2 2 2 2
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: chogahui05, thak00
알고리즘 분류
백트래킹
 */
/*
알고리즘 핵심
bruteforce (back-tracking)
1. N개의 자연수 중에서 M개를 고른 수열 - dfs (back-tracking)을 이용한 수열을 만드는 함수
2. 같은 수를 여러 번 골라도 된다. - dfs()의 인자 s = 다음 수열에 넣을 수의 시작 인덱스를 이전 인덱스 위치로 지정
3. 고른 수열은 비내림차순이어야 한다.(+사전순) - 입력으로 주어지는 N개의 자연수를 오름차순 정렬 + 시작 인덱스의 위치를 이전 수의 인덱스로 지정
4. 중복되는 수열 방지 - n번째 사용된 수를 저정하는 visited 배열 + n번째 수가 변경되면 n+1 번째 visited배열 초기화
 */
public class BOJ15666 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static int[] nums;
    static boolean[][] visited;
    static StringBuilder ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        dfs(0,0,new ArrayList<>());

        System.out.println(ans.toString());
    }

    private static void dfs(int n, int s, ArrayList<Integer> arr) {
        if(n == M) {
            ans.append(arr.toString().replaceAll("[,\\[\\]]","")).append("\n");
            return;
        }

        for(int i = s; i < N; i++) {
            if(visited[n][nums[i]]) continue;

            Arrays.fill(visited[n + 1], false);

            visited[n][nums[i]] = true;
            arr.add(nums[i]);
            dfs(n + 1, i, arr);
            arr.remove(n);
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

        ans = new StringBuilder();

        visited = new boolean[9][10001];
    }
}
