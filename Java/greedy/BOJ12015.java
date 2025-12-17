package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/*
가장 긴 증가하는 부분 수열 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	65289	27275	19180	41.969%
문제
수열 A가 주어졌을 때, 가장 긴 증가하는 부분 수열을 구하는 프로그램을 작성하시오.

예를 들어, 수열 A = {10, 20, 10, 30, 20, 50} 인 경우에 가장 긴 증가하는 부분 수열은 A = {10*, 20*, 10, 30*, 20, 50*} 이고, 길이는 4이다.

입력
첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000,000)이 주어진다.

둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (1 ≤ Ai ≤ 1,000,000)

출력
첫째 줄에 수열 A의 가장 긴 증가하는 부분 수열의 길이를 출력한다.

예제 입력 1
6
10 20 10 30 20 50
예제 출력 1
4
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: harinboy, jh05013, surung9898, tony9402, yeo2507
비슷한 문제
11053번. 가장 긴 증가하는 부분 수열
11054번. 가장 긴 바이토닉 부분 수열
11055번. 가장 큰 증가하는 부분 수열
11722번. 가장 긴 감소하는 부분 수열
12738번. 가장 긴 증가하는 부분 수열 3
14002번. 가장 긴 증가하는 부분 수열 4
14003번. 가장 긴 증가하는 부분 수열 5
알고리즘 분류
이분 탐색
가장 긴 증가하는 부분 수열 문제
 */
/*
알고리즘 핵심
LIS + 이분탐색
1. 입력으로 주어지는 수열에서 0 ~ N까지의 값을 별도의 LIS 배열에 넣는 과정을 수행한다.
2. Array[i]의 값이 LIS_list의 마지막 값과 비교하여 큰 경우, 뒤에 삽입하고 작은 경우 해당 값의 위치를 이분 탐색을 이용하여
삽입할 위치를 찾고 해당 값을 LIS_list에 넣는다.
3. 위 과정을 모든 수열의 값을 거친 후, LIS_list에 최장 길이 증가 부분 수열이 완성되고 해당 리스트의 길이를 반환한다.

첫 접근으로 O(N^2)의 시간복잡도를 가지는 로직을 사용하여 구현하였지만 시간초과가 발생하였다.
0 ~ N까지의 값 A[i]를 기준으로 0 ~ i에서 A[i]보다 작은 값의 최장 길이 부부 수열의 값에서 +1을 적용하여 업데이트한다.
작은 값이 없는 경우, 최장 길이 부분 수열의 값은 1로 고정한다.

이 과정을 0 ~ N까지 수행할 경우 O(N^2)이 나오는데 문제에서 주어진 N의 범위인 1,000,000으로는 불가능했다.

그래서 기존의 최장 길이 부분 수열 LIS 알고리즘을 개선하여 O(nlogn)의 시간복잡도를 가지는 방법을 사용해야 했다.
 */
public class BOJ12015 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static int[] A,LIS;
    static ArrayList<Integer> LIS_list;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        for(int k = 0; k < N; k++) {
            if(LIS_list.isEmpty()) LIS_list.add(A[k]);
            else if(LIS_list.get(LIS_list.size() - 1) < A[k]) LIS_list.add(A[k]);
            else {
                int pos = binary_search(A[k]);
                LIS_list.set(pos,A[k]);
            }
        }

        ans = LIS_list.size();

        System.out.println(ans);
    }

    private static int binary_search(int t) {
        int start = 0;
        int end = LIS_list.size();

        while(start < end) {
            int mid = (start + end) / 2;
            if(t > LIS_list.get(mid)) start = mid + 1;
            else end = mid;
        }

        return end;
    }

    /*
        틀린 코드 : 시간 초과 O(N^2) = 1,000,000 * 1,000,000
     */
    private static void wrong_solve() {
        for(int k = 0; k < N; k++) {
            LIS[k] = 1;

            for(int i = 0; i < k; i++) {
                if(A[i] < A[k]) {
                    LIS[k] = Math.max(LIS[k],LIS[i] + 1);
                }
            }

            ans = Math.max(ans, LIS[k]);
        }

        System.out.println(ans);
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        A = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        LIS = new int[N];
        LIS_list = new ArrayList<>();

        ans = 0;
    }
}
