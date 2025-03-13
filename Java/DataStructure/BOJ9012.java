package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/*
괄호 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	240289	115248	82464	46.635%
문제
괄호 문자열(Parenthesis String, PS)은 두 개의 괄호 기호인 ‘(’ 와 ‘)’ 만으로 구성되어 있는 문자열이다. 그 중에서 괄호의 모양이 바르게 구성된 문자열을 올바른 괄호 문자열(Valid PS, VPS)이라고 부른다. 한 쌍의 괄호 기호로 된 “( )” 문자열은 기본 VPS 이라고 부른다. 만일 x 가 VPS 라면 이것을 하나의 괄호에 넣은 새로운 문자열 “(x)”도 VPS 가 된다. 그리고 두 VPS x 와 y를 접합(concatenation)시킨 새로운 문자열 xy도 VPS 가 된다. 예를 들어 “(())()”와 “((()))” 는 VPS 이지만 “(()(”, “(())()))” , 그리고 “(()” 는 모두 VPS 가 아닌 문자열이다.

여러분은 입력으로 주어진 괄호 문자열이 VPS 인지 아닌지를 판단해서 그 결과를 YES 와 NO 로 나타내어야 한다.

입력
입력 데이터는 표준 입력을 사용한다. 입력은 T개의 테스트 데이터로 주어진다. 입력의 첫 번째 줄에는 입력 데이터의 수를 나타내는 정수 T가 주어진다. 각 테스트 데이터의 첫째 줄에는 괄호 문자열이 한 줄에 주어진다. 하나의 괄호 문자열의 길이는 2 이상 50 이하이다.

출력
출력은 표준 출력을 사용한다. 만일 입력 괄호 문자열이 올바른 괄호 문자열(VPS)이면 “YES”, 아니면 “NO”를 한 줄에 하나씩 차례대로 출력해야 한다.

예제 입력 1
6
(())())
(((()())()
(()())((()))
((()()(()))(((())))()
()()()()(()()())()
(()((())()(
예제 출력 1
NO
NO
YES
NO
YES
NO
예제 입력 2
3
((
))
())(()
예제 출력 2
NO
NO
NO
출처
ICPC > Regionals > Asia Pacific > Korea > Nationwide Internet Competition > Daejeon Nationalwide Internet Competition 2012 G번

데이터를 만든 사람: baekjoon
데이터를 추가한 사람: jh05013
문제의 오타를 찾은 사람: marona
알고리즘 분류
자료 구조
문자열
스택
 */
/*
알고리즘 핵심
자료구조(스택)
1. stack 자료구조를 이용하여 VPS 여부를 확인한다.
2. 입력으로 주어진 문자열을 첫문자부터 끝문자까지 순서대로 문자를 확인하고, 해당 문자가 ")"일 때, stack의 마지막 문자(stack.peek())가 "("인 경우
stack의 마지막 문자를 pop하고 해당 문자를 push하지 않는다.
위의 조건에 만족하지 않는 경우 문자를 push하고 끝문자까지 수행한다.
3. 모든 문자를 수행한 후, stack에 문자가 남아있는 여부를 통해 No, Yes를 출력한다.

 */
public class BOJ9012 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T;
    static String[] str;
    static Stack<String> stack;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        for(String s : str) {
            stack = new Stack<>();

            for(String ss : s.split("")) {
                if(stack.isEmpty()) {
                    stack.push(ss);
                    continue;
                }
                if(ss.equals(")") && stack.peek().equals("(")) stack.pop();
                else stack.push(ss);
            }

            if(stack.isEmpty()) System.out.println("YES");
            else System.out.println("NO");
        }
    }

    private static void init_setting() throws IOException {
        T = Integer.parseInt(br.readLine());

        str = new String[T];

        for(int i = 0; i < T; i++) str[i] = br.readLine();
    }
}
