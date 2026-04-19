package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
K번째 좋은 문자열

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	110	50	36	50.000%
문제
좋은 문자열은 다음과 같이 정의된다.

문자열 "()"는 좋은 문자열이다.
S가 좋은 문자열 이라면, "(SS...S)"도 좋은 문자열이다. 즉, 좋은 문자열 하나를 여러개 연속해서 놓은 다음, 괄호로 감싼 것이 좋은 문자열이다.
위의 두 경우를 제외하면 좋은 문자열은 없다.
문자열 X의 부분 수열은 X에서 0개 이상의 문자를 지워서 얻을 수 있다.

문자열 S가 주어진다. S의 각 문자는 '(' 또는 ')' 이다.

G를 S의 부분 수열 중에서 서로 다른 좋은 문자열인 것의 집합이라고 하자. 즉, 같은 좋은 문자열이 여러 번 나온다고 해도, 집합이기 때문에 G에는 하나만 들어있게 된다. 예를 들어, S = "(()())"인 경우에 G에는 "()", "(())", "(()())"이 들어있게 된다.

K가 주어졌을 때, G에 들어있는 문자열 중에서 사전 순으로 K번째인 것을 찾는 프로그램을 작성하시오. 인덱스는 1부터 시작한다.

입력
첫째 줄에 문자열 S와 정수 K가 주어진다. S의 길이는 150보다 작거나 같은 자연수이고, K는 1보다 크거나 같고, 109보다 작거나 같은 자연수이다.

출력
첫째 줄에 G에 들어있는 문자열 중에서 사전 순으로 K번째 작은 문자열을 출력한다. 만약, 그러한 문자열이 없는 경우에는 -1을 출력한다.

예제 입력 1
()))((()())
3
예제 출력 1
(())
예제 입력 2
))))))))))))((((((((((
1
예제 출력 2
-1
예제 입력 3
(())(()(()))
1
예제 출력 3
(((())))
예제 입력 4
(())))()((())())
8
예제 출력 4
()
예제 입력 5
(
1000000000
예제 출력 5
-1
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
브루트포스 알고리즘
 */
/*
첫 문제 접근 방식으로 가능한 모든 S를 구하는 create_S()를 이용하여 모든 좋은 문자열 S_list를 구한 후,
주어진 S에서 (...) 형태의 문자열을 모두 만들어 낸 후, S_list에서 존재하는지 여부에 G_hash에 저장한 후, List로 만들어
사전 정렬(오름차순)을 적용하여 K번째 문자열을 출력하려고 생각했다.

하지만 결과는 시간초과이다.

시간초과의 원인을 생각해보면 S_list를 만드는 것까지는 문제가 아니라고 생각했고, G_list에서 시간 초과의 원인이라고 생각했다.

문제 해결 방식
만들어낸 모든 좋은 문자열 S_list를 사전적 정렬(오름차순)을 적용하고 한 문자열씩 S 문자열에 좋은 문자열이 존재하는지 하나씩 비교하면서
K번째 까지 도달하는지 여부를 검사하면 되는 방법이다.
 */
public class BOJ14056 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int K;
    static String S;
    static HashSet<String> G_hash;
    static List<String> G_list;
    static List<String> S_list;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        create_S();

        /*
        create_G();

        for(int i = 0; i < S.length(); i++) {
            if(S.charAt(i) == ')') continue;
            search(1, i, "(");
        }

        G_list = new ArrayList<>(G_hash);

        Collections.sort(G_list);

        if(K > G_list.size()) System.out.println("-1");
        else System.out.println(G_list.get(K - 1));
        */

        Collections.sort(S_list);

        search_K();
    }

    private static void search_K() {
        int k = 0;
        String ans = "";
        boolean flag = false;

        for(String s : S_list) {
            int idx = 0;
            for(int i = 0; i < S.length(); i++) {
                if(idx < s.length() && s.charAt(idx) == S.charAt(i)) idx++;
            }

            if(idx == s.length()) {
                k++;

                if(k == K) {
                    flag = true;
                    ans = s;
                    break;
                }
            }
        }

        if(flag) {
            System.out.println(ans);
        } else {
            System.out.println(-1);
        }
    }

    private static void create_G() {
        int sidx = S.indexOf("(");
        int eidx = S.lastIndexOf(")");
        backtracking(sidx,eidx, "");
    }

    private static void backtracking(int sidx, int eidx, String s) {
        if(sidx > eidx) {
            if(S_list.contains(s)) {
                G_hash.add(s);
            }
            return;
        }

        backtracking(sidx + 1, eidx, s + S.charAt(sidx));
        backtracking(sidx + 1, eidx, s);
    }

    private static void create_S() {
        StringBuilder sb;
        Queue<String> q = new LinkedList<>();
        S_list.add("()");
        q.add("()");

        while(!q.isEmpty()) {
            String s = q.poll();

            int len = s.length();
            sb = new StringBuilder();

            for(int i = 1; i * len <= S.length() - 2; i++) {
                sb.append(s);
                S_list.add("(" + sb + ")");
                q.add("(" + sb + ")");
            }
        }
    }

    private static void search(int balance, int sidx, String X) {
        if(balance == 0) {
            if(S_list.contains(X)) {
                G_hash.add(X);
            }
            return;
        }

        for(int i = sidx + 1; i < S.length(); i++) {
            char ch = S.charAt(i);
            int b = ch == '(' ? 1 : -1;

            if(balance + b < 0) continue;
            search(balance + b, i, X + (b == 1 ? "(" : ")"));
        }
    }

    private static void init_setting() throws IOException {
        S = br.readLine();
        K = Integer.parseInt(br.readLine());

        G_hash = new HashSet<>();

        G_list = new ArrayList<>();
        S_list = new ArrayList<>();
    }
}
/*
(())))()((())())
(), (()), (()()), (()()()), (()()()()), ((())), ((()())),(((())))

()))((()())
(), (()), (()()), ((()))
 */
