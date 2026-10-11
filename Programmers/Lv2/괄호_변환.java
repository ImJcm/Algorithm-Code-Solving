package Lv2;

import java.util.Stack;

/*
괄호 변환
제출 내역
문제 설명
카카오에 신입 개발자로 입사한 "콘"은 선배 개발자로부터 개발역량 강화를 위해 다른 개발자가 작성한 소스 코드를 분석하여 문제점을 발견하고 수정하라는 업무 과제를 받았습니다. 소스를 컴파일하여 로그를 보니 대부분 소스 코드 내 작성된 괄호가 개수는 맞지만 짝이 맞지 않은 형태로 작성되어 오류가 나는 것을 알게 되었습니다.
수정해야 할 소스 파일이 너무 많아서 고민하던 "콘"은 소스 코드에 작성된 모든 괄호를 뽑아서 올바른 순서대로 배치된 괄호 문자열을 알려주는 프로그램을 다음과 같이 개발하려고 합니다.

용어의 정의
'(' 와 ')' 로만 이루어진 문자열이 있을 경우, '(' 의 개수와 ')' 의 개수가 같다면 이를 균형잡힌 괄호 문자열이라고 부릅니다.
그리고 여기에 '('와 ')'의 괄호의 짝도 모두 맞을 경우에는 이를 올바른 괄호 문자열이라고 부릅니다.
예를 들어, "(()))("와 같은 문자열은 "균형잡힌 괄호 문자열" 이지만 "올바른 괄호 문자열"은 아닙니다.
반면에 "(())()"와 같은 문자열은 "균형잡힌 괄호 문자열" 이면서 동시에 "올바른 괄호 문자열" 입니다.

'(' 와 ')' 로만 이루어진 문자열 w가 "균형잡힌 괄호 문자열" 이라면 다음과 같은 과정을 통해 "올바른 괄호 문자열"로 변환할 수 있습니다.

1. 입력이 빈 문자열인 경우, 빈 문자열을 반환합니다.
2. 문자열 w를 두 "균형잡힌 괄호 문자열" u, v로 분리합니다. 단, u는 "균형잡힌 괄호 문자열"로 더 이상 분리할 수 없어야 하며, v는 빈 문자열이 될 수 있습니다.
3. 문자열 u가 "올바른 괄호 문자열" 이라면 문자열 v에 대해 1단계부터 다시 수행합니다.
  3-1. 수행한 결과 문자열을 u에 이어 붙인 후 반환합니다.
4. 문자열 u가 "올바른 괄호 문자열"이 아니라면 아래 과정을 수행합니다.
  4-1. 빈 문자열에 첫 번째 문자로 '('를 붙입니다.
  4-2. 문자열 v에 대해 1단계부터 재귀적으로 수행한 결과 문자열을 이어 붙입니다.
  4-3. ')'를 다시 붙입니다.
  4-4. u의 첫 번째와 마지막 문자를 제거하고, 나머지 문자열의 괄호 방향을 뒤집어서 뒤에 붙입니다.
  4-5. 생성된 문자열을 반환합니다.
"균형잡힌 괄호 문자열" p가 매개변수로 주어질 때, 주어진 알고리즘을 수행해 "올바른 괄호 문자열"로 변환한 결과를 return 하도록 solution 함수를 완성해 주세요.

매개변수 설명
p는 '(' 와 ')' 로만 이루어진 문자열이며 길이는 2 이상 1,000 이하인 짝수입니다.
문자열 p를 이루는 '(' 와 ')' 의 개수는 항상 같습니다.
만약 p가 이미 "올바른 괄호 문자열"이라면 그대로 return 하면 됩니다.
입출력 예
p	result
"(()())()"	"(()())()"
")("	"()"
"()))((()"	"()(())()"
입출력 예에 대한 설명
입출력 예 #1
이미 "올바른 괄호 문자열" 입니다.

입출력 예 #2

두 문자열 u, v로 분리합니다.
u = ")("
v = ""
u가 "올바른 괄호 문자열"이 아니므로 다음과 같이 새로운 문자열을 만듭니다.
v에 대해 1단계부터 재귀적으로 수행하면 빈 문자열이 반환됩니다.
u의 앞뒤 문자를 제거하고, 나머지 문자의 괄호 방향을 뒤집으면 ""이 됩니다.
따라서 생성되는 문자열은 "(" + "" + ")" + ""이며, 최종적으로 "()"로 변환됩니다.
입출력 예 #3

두 문자열 u, v로 분리합니다.
u = "()"
v = "))((()"
문자열 u가 "올바른 괄호 문자열"이므로 그대로 두고, v에 대해 재귀적으로 수행합니다.
다시 두 문자열 u, v로 분리합니다.
u = "))(("
v = "()"
u가 "올바른 괄호 문자열"이 아니므로 다음과 같이 새로운 문자열을 만듭니다.
v에 대해 1단계부터 재귀적으로 수행하면 "()"이 반환됩니다.
u의 앞뒤 문자를 제거하고, 나머지 문자의 괄호 방향을 뒤집으면 "()"이 됩니다.
따라서 생성되는 문자열은 "(" + "()" + ")" + "()"이며, 최종적으로 "(())()"를 반환합니다.
처음에 그대로 둔 문자열에 반환된 문자열을 이어 붙이면 "()" + "(())()" = "()(())()"가 됩니다.
 */
/*
알고리즘 핵심
구현 + recursive + stack
1. 입력에서 주어지는 알고리즘을 로직으로 구현하여 문자열을 만들어낸다.
2. 균형잡힌 괄호 문자열은 앞부분에서 시작하여 열림과 닫힘을 부호가 다른 값으로 설정하여 0일 때, 균형잡힌 문자열을 나누는 구간의 인덱스를 구한다.
3. 문자열에서 올바른 괄호 문자열을 판정하기 위해 stack을 통해 열고 닫힘을 스택을 통해 여부를 확인한다.
 */
public class 괄호_변환 {
    static void main() {
        String p = new String(
                //"(()())()"
                "()))((()"
        );

        Solve task = new Solve();
        System.out.println(task.solution(p));
    }

    private static class Solve {
        private String ans;

        public String solution(String p) {
            init_setting();

            ans = parenthesis_conversion(p);

            return ans;
        }

        private String parenthesis_conversion(String p) {
            if(p.isEmpty()) return "";

            String[] balanced_str = split_balanced_string(p);

            if(is_correct_parenthesis_string(balanced_str[0])) {
                return balanced_str[0] + parenthesis_conversion(balanced_str[1]);
            } else {
                 String tmp = "(";
                 tmp += parenthesis_conversion(balanced_str[1]);
                 tmp += ")";

                 String new_bal_str_0 = balanced_str[0].substring(1, balanced_str[0].length() - 1);
                 tmp += reverse_parenthesis(new_bal_str_0);
                 return tmp;
            }
        }

        private String reverse_parenthesis(String p) {
            String new_str = new String();

            for(int i = 0; i < p.length(); i++) {
                if(p.charAt(i) == '(') new_str += ')';
                else new_str += '(';    // ")"-> "("
            }

            return new_str;
        }

        private boolean is_correct_parenthesis_string(String balanced_str) {
            Stack<Character> stack = new Stack<>();

            for(int i = 0; i < balanced_str.length(); i++) {
                char ch = balanced_str.charAt(i);

                if(ch == ')' && !stack.isEmpty() && stack.peek() == '(') stack.pop();
                else stack.push(ch);
            }

            if(stack.isEmpty()) return true;
            else return false;
        }

        private String[] split_balanced_string(String p) {
            String[] bal_str = new String[2];

            int idx = 0;
            int weight = p.charAt(0) == '(' ? 1 : -1;

            for(int i = 1; i < p.length(); i++) {
                char ch = p.charAt(i);

                if(ch == '(') weight += 1;
                else if(ch == ')') weight -= 1;

                if(weight == 0) {
                    idx = i;
                    break;
                }
            }

            bal_str[0] = p.substring(0,idx + 1);
            bal_str[1] = p.substring(idx + 1);

            return bal_str;
        }

        private void init_setting() {
            ans = new String();
        }
    }
}
