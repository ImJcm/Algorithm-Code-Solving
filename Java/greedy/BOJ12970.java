package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
AB 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	3566	1555	1243	45.282%
문제
정수 N과 K가 주어졌을 때, 다음 두 조건을 만족하는 문자열 S를 찾는 프로그램을 작성하시오.

문자열 S의 길이는 N이고, 'A', 'B'로 이루어져 있다.
문자열 S에는 0 ≤ i < j < N 이면서 s[i] == 'A' && s[j] == 'B'를 만족하는 (i, j) 쌍이 K개가 있다.
입력
첫째 줄에 N과 K가 주어진다. (2 ≤ N ≤ 50, 0 ≤ K ≤ N(N-1)/2)

출력
첫째 줄에 문제의 조건을 만족하는 문자열 S를 출력한다. 가능한 S가 여러 가지라면, 아무거나 출력한다. 만약, 그러한 S가 존재하지 않는 경우에는 -1을 출력한다.

예제 입력 1
3 2
예제 출력 1
ABB
예제 입력 2
2 0
예제 출력 2
BA
예제 입력 3
5 8
예제 출력 3
-1
예제 입력 4
10 12
예제 출력 4
BAABBABAAB
출처
문제를 번역한 사람: baekjoon
잘못된 조건을 찾은 사람: tongnamuu
알고리즘 분류
수학
그리디 알고리즘
해 구성하기
 */
/*
알고리즘 핵심
그리드 알고리즘 + back-tracking
1. 조건을 만족하는 문자열 중 한가지를 출력하면 되므로 back-tracking을 사용하여 A,B를 추가하여 문자열을 구성하고 조건을 만족하는 문자열을 찾는다.
2. N 길이의 문자열을 구성할 때, A,B로 만들 수 있는 (A,B) 쌍의 갯수는 (N / 2) * (N - N / 2) 보다 K가 크다면 -1을 출력한다.
K보다 크지 않다면 B를 우선으로 추가하여 B 문자의 사용 개수인 b_used_cnt 인자를 증가시키고, A를 추가했을 때 현재까지 추가된 B 개수만큼
빼서 남은 (A,B)쌍의 갯수를 만족시키는지 확인한다.
3. 현재 B의 갯수가 남은 (A,B) 쌍의 갯수를 넘기지 않는 경우에 A를 추가하고 그 외에는 B를 추가하여 조건을 만족시키는 문자열 S를 만든다.
4. 최종적으로 조건을 만족하는 문자열을 만들면 flag를 통해 이후의 재귀호출을 모두 종료시킨다.
 */
public class BOJ12970 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,K,Limit;
    static boolean flag;
    static StringBuilder sb;
    static String ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    /*
        B 문자를 우선적으로 추가하는 로직을 재귀호출하여 불필요한 과정을 방지하여 시간초과를 해결
     */
    private static void solve() {
        Limit = (N / 2) * (N - N / 2);

        if(K > Limit) ans = "-1";
        else dfs(0, 0, K);

        System.out.println(ans);
    }

    private static void dfs(int n, int b_use_cnt, int k_remain_cnt) {
        if(flag) return;
        if(n == N) {
            if(k_remain_cnt == 0) {
                flag = true;
                ans = sb.toString();
            }
            return;
        }

        sb.insert(0,"B");
        dfs(n + 1, b_use_cnt + 1, k_remain_cnt);
        sb.delete(0,1);

        if(b_use_cnt <= k_remain_cnt) {
            sb.insert(0,"A");
            dfs(n + 1, b_use_cnt, k_remain_cnt - b_use_cnt);
            sb.delete(0,1);
        }
    }

    /*
        틀린 코드 3% : 시간초과
     */
    private static void wrong_solve() {
        Limit = (N / 2) * (N - N / 2);

        if(K > Limit) ans = "-1";
        else wrong_dfs(0, 0, K);

        System.out.println(ans);
    }

    public static void wrong_dfs(int n, int b_use_cnt, int k_remain_cnt) {
        if(flag) return;
        if(n == N) {
            if(k_remain_cnt == 0) {
                flag = true;
                ans = sb.toString();
            }
            return;
        }

        sb.insert(0,"B");
        wrong_dfs(n + 1, b_use_cnt + 1, k_remain_cnt);
        sb.delete(0,1);

        if(b_use_cnt <= k_remain_cnt) {
            sb.insert(0,"A");
            wrong_dfs(n + 1, b_use_cnt, k_remain_cnt - b_use_cnt);
            sb.delete(0,1);
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);

        sb = new StringBuilder();

        flag = false;
    }
}
