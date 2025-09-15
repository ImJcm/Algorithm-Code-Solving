package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

/*
숫자 카드 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	191491	78274	55684	39.049%
문제
숫자 카드는 정수 하나가 적혀져 있는 카드이다. 상근이는 숫자 카드 N개를 가지고 있다. 정수 M개가 주어졌을 때, 이 수가 적혀있는 숫자 카드를 상근이가 몇 개 가지고 있는지 구하는 프로그램을 작성하시오.

입력
첫째 줄에 상근이가 가지고 있는 숫자 카드의 개수 N(1 ≤ N ≤ 500,000)이 주어진다. 둘째 줄에는 숫자 카드에 적혀있는 정수가 주어진다. 숫자 카드에 적혀있는 수는 -10,000,000보다 크거나 같고, 10,000,000보다 작거나 같다.

셋째 줄에는 M(1 ≤ M ≤ 500,000)이 주어진다. 넷째 줄에는 상근이가 몇 개 가지고 있는 숫자 카드인지 구해야 할 M개의 정수가 주어지며, 이 수는 공백으로 구분되어져 있다. 이 수도 -10,000,000보다 크거나 같고, 10,000,000보다 작거나 같다.

출력
첫째 줄에 입력으로 주어진 M개의 수에 대해서, 각 수가 적힌 숫자 카드를 상근이가 몇 개 가지고 있는지를 공백으로 구분해 출력한다.

예제 입력 1
10
6 3 2 10 10 10 -10 -10 7 3
8
10 9 -5 2 3 4 5 -10
예제 출력 1
3 0 0 1 2 0 0 2
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: bumsoo0515, emppu, jh05013
문제의 오타를 찾은 사람: cko301, mwy3055, wkd48632
알고리즘 분류
자료 구조
정렬
이분 탐색
해시를 사용한 집합과 맵
 */
/*
알고리즘 핵심
자료구조 (HashMap)
1. N개의 입력으로 주어진 숫자를 HashMap에 저장하고, 이미 입력된 숫자의 횟수를 1 증가시킨다.
2. M개의 입력으로 주어진 숫자를 HashMap에 존재하는지 확인하고 해당 개수를 ans에 저장하고, 없다면 0을 저장한다.

이분 탐색의 방법으로 풀이를 작성한다면, 숫자를 저장할 배열과 숫자의 중복횟수를 저장할 HashMap 자료구조가 필요하다.
숫자를 저장한 배열을 오름차순으로 정렬하고 M개의 숫자를 하나씩 이분 탐색으로 찾는다.
해당 숫자를 찾는 경우, Map에서 중복된 갯수를 ans에 저장하고 없는 경우, 0을 저장한다.
 */
public class BOJ10816 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static HashMap<Integer, Integer> map;
    static StringBuilder ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() throws IOException {
        M = Integer.parseInt(br.readLine());

        int[] input = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        for(int i = 0; i < M; i++) {
            if(map.containsKey(input[i])) ans.append(map.get(input[i]));
            else ans.append(0);
            ans.append(" ");
        }

        System.out.println(ans.toString());
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        String[] input = br.readLine().split(" ");

        map = new HashMap<>();

        for(int i = 0; i < N; i++) {
            int num = Integer.parseInt(input[i]);
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        ans = new StringBuilder();
    }
}
