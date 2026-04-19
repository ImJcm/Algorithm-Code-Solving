package Programmers;

import java.util.*;

/*
괄호 회전하기
문제 설명
다음 규칙을 지키는 문자열을 올바른 괄호 문자열이라고 정의합니다.

(), [], {} 는 모두 올바른 괄호 문자열입니다.
만약 A가 올바른 괄호 문자열이라면, (A), [A], {A} 도 올바른 괄호 문자열입니다. 예를 들어, [] 가 올바른 괄호 문자열이므로, ([]) 도 올바른 괄호 문자열입니다.
만약 A, B가 올바른 괄호 문자열이라면, AB 도 올바른 괄호 문자열입니다. 예를 들어, {} 와 ([]) 가 올바른 괄호 문자열이므로, {}([]) 도 올바른 괄호 문자열입니다.
대괄호, 중괄호, 그리고 소괄호로 이루어진 문자열 s가 매개변수로 주어집니다. 이 s를 왼쪽으로 x (0 ≤ x < (s의 길이)) 칸만큼 회전시켰을 때 s가 올바른 괄호 문자열이 되게 하는 x의 개수를 return 하도록 solution 함수를 완성해주세요.

제한사항
s의 길이는 1 이상 1,000 이하입니다.
입출력 예
s	result
"[](){}"	3
"}]()[{"	2
"[)(]"	0
"}}}"	0
입출력 예 설명
입출력 예 #1

다음 표는 "[](){}" 를 회전시킨 모습을 나타낸 것입니다.
x	s를 왼쪽으로 x칸만큼 회전	올바른 괄호 문자열?
0	"[](){}"	O
1	"](){}["	X
2	"(){}[]"	O
3	"){}[]("	X
4	"{}[]()"	O
5	"}[](){"	X
올바른 괄호 문자열이 되는 x가 3개이므로, 3을 return 해야 합니다.
입출력 예 #2

다음 표는 "}]()[{" 를 회전시킨 모습을 나타낸 것입니다.
x	s를 왼쪽으로 x칸만큼 회전	올바른 괄호 문자열?
0	"}]()[{"	X
1	"]()[{}"	X
2	"()[{}]"	O
3	")[{}]("	X
4	"[{}]()"	O
5	"{}]()["	X
올바른 괄호 문자열이 되는 x가 2개이므로, 2를 return 해야 합니다.
입출력 예 #3

s를 어떻게 회전하더라도 올바른 괄호 문자열을 만들 수 없으므로, 0을 return 해야 합니다.
입출력 예 #4

s를 어떻게 회전하더라도 올바른 괄호 문자열을 만들 수 없으므로, 0을 return 해야 합니다.
 */
/*
    queue는 indexOf 기능이 없기때문에 stack을 사용
    입력된 문자열을 stack에 저장 후, 저장된 stack의 요소들을 하나씩 검사하여 조건에 맞게 별도의 Stack에 저장한다
    올바른 괄호문 인지 검사하고 inputStack을 Queue와 같이 맨 앞자리 데이터를 조회하고 데이터를 저장한 값을 Stack.push()로 저장
    맨 앞의 요소를 제거하여 입력된 데이터를 회전하는 형태로 업데이트한다.
 */
class Solution {
    public static void main(String[] args) {;
        String s = "{([";
        System.out.println(solution(s));
    }

    public static int solution(String s) {
        int answer = 0;
        boolean chk_flag = true;
        //Queue<Character> queue = new LinkedList<>();
        Stack<Character> inputStack = new Stack<>();
        for(int i=0;i<s.length();i++) {
            inputStack.add(s.charAt(i));
        }

        for(int i=0;i<inputStack.size();i++) {
            Stack<Character> stack = new Stack<>();
            chk_flag = true;
            for(int j=0;j<inputStack.size();j++) {
                Character c = inputStack.elementAt(j);
                if(c.equals('[') || c.equals('{') || c.equals('(')) {
                    stack.push(c);
                } else {
                    if(!stack.isEmpty()) {
                        if(stack.peek().equals('[') && c.equals(']')) {
                            stack.pop();
                        } else if(stack.peek().equals('{') && c.equals('}')) {
                            stack.pop();
                        } else if(stack.peek().equals('(') && c.equals(')')) {
                            stack.pop();
                        } else {
                            chk_flag = false;
                            break;
                        }
                    } else {
                        if(c.equals(']') || c.equals('}') || c.equals(')')) {
                            chk_flag = false;
                            break;
                        }
                    }
                }
            }

            if(chk_flag && stack.isEmpty()) {
                answer++;
            }

            Character c = inputStack.elementAt(0);
            inputStack.remove(0);
            inputStack.push(c);
        }

        return answer;
    }

}
