package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
올바른 괄호 문자열 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	3988	749	515	21.813%
문제
여는 괄호와 닫는 괄호로만 이루어져 있으면서, 다음 규칙을 지키는 문자열을 올바른 괄호 문자열이라고 한다.

빈 문자열은 올바른 괄호 문자열이다.
A가 올바른 괄호 문자열이라면, (A), [A], {A}도 올바른 괄호 문자열이다.
A와 B가 올바른 괄호 문자열이라면, AB도 올바른 괄호 문자열이다.
예를 들어, [({})], [](){}, [{}]()[{}]는 올바른 괄호 문자열이다. 하지만, [({{)[, []({)}, [{}])([{}]는 올바른 괄호 문자열이 아니다.

상근이는 책상 서랍에서 올바른 괄호 문자열을 적은 종이를 찾았다. 이 종이는 오래되었기 때문에, 일부 문자는 알아 볼 수 없었다.

알아볼 수 없는 문자를 적절히 괄호로 바꿔서 만들 수 있는 올바른 괄호 문자열의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 문자열의 길이 N이 주어진다. (2 ≤ N ≤ 200)

둘째 줄에는 문자열이 주어진다. 알아볼 수 없는 문자는 '?'로 표시되어 있다.

출력
첫째 줄에 만들 수 있는 올바른 괄호 문자열의 수를 출력한다. 개수가 다섯 자리를 넘어가는 경우에는 마지막 다섯 자리만 출력한다.

예제 입력 1
10
(?([?)]?}?
예제 출력 1
3
예제 입력 2
6
()()()
예제 출력 2
1
예제 입력 3
16
???[???????]????
예제 출력 3
92202
힌트
예제 1의 경우 다음이 가능하다. ({([()])}), ()([()]{}), ([([])]{})

출처
Contest > Croatian Open Competition in Informatics > COCI 2007/2008 > Contest #1 4번

문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: degurii, jung2381187
데이터를 추가한 사람: goooora
알고리즘 분류
다이나믹 프로그래밍
 */
public class BOJ3012 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static int[][] dp;
    static char[] unknown_string;
    static char[] brackets = {'(', ')', '{', '}', '[', ']'};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        dfs(0, unknown_string);
        // ans = dfs_dp(1,N);

        System.out.println(ans % 100000);
    }

    /*private static int dfs_dp(int l, int r) {
        if(l >= r) return 0;

        if(dp[l][r] != -1) return dp[l][r];
    }*/

    /*
        bruteforce - 시간초과 & 메모리 초과로 실패 예상
     */
    private static void dfs(int p, char[] uc) {
        if(p == N) {
            Stack<Integer> s = new Stack<>();
            boolean flag = true;

            for(int i = 0; i < uc.length && flag; i++) {
                switch (uc[i]) {
                    case '(':
                        s.add(1);
                        break;
                    case '{':
                        s.add(2);
                        break;
                    case '[':
                        s.add(3);
                        break;
                    case ')':
                        if(s.isEmpty()) flag = false;
                        else {
                            if(s.peek() == 1) s.pop();
                            else flag = false;
                        }
                        break;
                    case '}':
                        if(s.isEmpty()) flag = false;
                        else {
                            if(s.peek() == 2) s.pop();
                            else flag = false;
                        }
                        break;
                    case ']':
                        if(s.isEmpty()) flag = false;
                        else {
                            if(s.peek() == 3) s.pop();
                            else flag = false;
                        }
                        break;
                }
            }

            if(flag && s.isEmpty()) {
                //System.out.println(Arrays.toString(uc));
                ans++;
            }
            return ;
        }

        if(uc[p] == '?') {
            for(char b : brackets) {
                uc[p] = b;
                dfs(p + 1, uc);
                uc[p] = '?';
            }
        } else {
            dfs(p + 1, uc);
        }
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        unknown_string = br.readLine().toCharArray();

        dp = new int[N + 1][N + 1];

        for(int i = 0; i < N + 1; i++) {
            Arrays.fill(dp[i], -1);
        }
    }
}
