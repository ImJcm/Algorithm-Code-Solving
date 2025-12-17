import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
/*
알고리즘 핵심
DP

첫 접근으로 bruteforce로 0 부터 N까지 자리에서 ?에 해당하는 값을 '(',')','{','}','[',']'를 넣고 모든 문자가 정해지면
올바른 문자열인지 검사하여 ans를 업데이트하는 형식을 구성하였지만 시간초과와 메모리초과가 발생할 것으로 예상할 수 있었다.

해결 방법을 생각한 것으로 bruteforce의 동작과정에서
( ... ) or { ... } or [ ... ] 일 경우, ...에서 구성할 수 있고,
()... 와 같이 구간이 나누어질 수 있는 점을 통해서 dp의 구성으로 처음지점과 마지막지점으로 2차원 배열을 구성할 수 있다고 생각했지만
2차원 배열로만 dp를 구성할 수 있을까라는 의문이 생겼고, (,),{,},[,]의 구성이 올바른 문자열 구성에 영향을 미치기 때문에
2 + 6차원 까지 생각을 했었다.

하지만 이러한 생각은 틀린 것이였고, 도저히 해결방법이 떠오르지 않아서 정답 코드를 참고하였다. -
https://github.com/puzzlecollector/BOJ/commit/96d2fef74ed436692a47a1341273efa10e28d4c0

정답 코드를 참고하여 동작 과정을 설명하면 다음과 같다.
1. 시작 지점 구간과 끝 지점 구간을 나누어 dp를 구성하는 것으로 dfs_dp를 재귀호출하는 과정에서 올바른 문자열을 구성할 수 있다.
2. 홀수 자릿수의 문자열은 올바른 문자열을 만들 수 없다.
3. 처음 구간 지점과 끝 구간 지점이 같아지면 1을 반환한다.
4. dp[l][r]이 -1(초기값)이 아니면 dp[l][r] 메모리제이션 결과값을 반환한다.
5. 올바른 문자열을 구성하는 과정 (i = l+1부터 r까지 +2씩 진행)
5-1. l 구간에서의 문자가 '?'이고, i 구간에서 문자가 ?이면 (),{},[]씩 3번이 가능하므로 3 * dfs_dp(r + 1,i) * dfs_dp(i + 1,r)
5-2. l 구간에서의 문자가 '(','{','['이고, i 구간에서 문자가 ')','}',']'로 각 쌍을 이루면 dfs_dp(r + 1,i) * dfs_dp(i + 1,r)
5-3. l 구간에서의 문자가 '?'이고 i 구간에서 문자가 ')','}',']'이면 dfs_dp(l + 1,i) * dfs_dp(i + 1,r)
5-4. l 구간에서의 문자가 '(','{','['이고, i 구간에서의 문자가 '?'이면 dfs_dp(l + 1,i) * dfs_dp(i + 1,r)
을 수행한다.

이후, 100000L으로 나눈 나머지값을 저장하고 dp[l][r]로 저장한다.
(5자리 수만 저장하기 위함)

dfs_dp(0,N)의 결과값이 100000L보다 큰 값이라면 5자리만 출력할 수 있도록 limit을 나눈 나머지 값을 출력하고 그렇지 않으면 ans를 출력한다.
 */
public class BOJ3012 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static long ans;
    static long[][] dp;
    static char[] unknown_string;
    static Map<Character,Integer> brackets;
    static final long limit = 100000L;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        // dfs_bruteforce(0, unknown_string);

        if((N & 1) == 1) System.out.println(0);
        else {
            ans = dfs_dp(0,N);

            if(ans >= limit) {
                System.out.printf("%05d\n",ans % limit);
            } else {
                System.out.println(ans);
            }
        }
    }

    private static long dfs_dp(int l, int r) {
        if(l == r) return 1;
        if(dp[l][r] != -1) return dp[l][r];

        long result = 0;

        for(int i = l + 1; i < r; i += 2) {
            if(unknown_string[l] == '?' && unknown_string[i] == '?') {
                result += (3 * dfs_dp(l + 1, i) * dfs_dp(i + 1, r)) % limit;
            } else if(brackets.get(unknown_string[l]) > 0 && brackets.get(unknown_string[l]) == -brackets.get(unknown_string[i])) {
                result += (dfs_dp(l + 1, i) * dfs_dp(i + 1, r)) % limit;
            } else if(unknown_string[l] == '?' && brackets.get(unknown_string[i]) < 0) {
                result += (dfs_dp(l + 1, i) * dfs_dp(i + 1, r)) % limit;
            } else if(brackets.get(unknown_string[l]) > 0 && unknown_string[i] == '?') {
                result += (dfs_dp(l + 1, i) * dfs_dp(i + 1, r)) % limit;
            }
        }

        dp[l][r] = result;

        return dp[l][r];
    }

    /*
    //bruteforce - 시간초과 & 메모리 초과로 실패 예상
    private static void dfs_bruteforce(int p, char[] uc) {
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
    }*/

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        unknown_string = br.readLine().toCharArray();

        dp = new long[N + 1][N + 1];

        for(int i = 0; i < N + 1; i++) {
            Arrays.fill(dp[i], -1);
        }

        brackets = new HashMap<>();

        brackets.put('?', 0);
        brackets.put('(', 1);
        brackets.put('{', 2);
        brackets.put('[', 3);
        brackets.put(')', -1);
        brackets.put('}', -2);
        brackets.put(']', -3);
    }
}
