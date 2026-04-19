package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
후위 표기식

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	56267	21987	16824	38.530%
문제
수식은 일반적으로 3가지 표기법으로 표현할 수 있다. 연산자가 피연산자 가운데 위치하는 중위 표기법(일반적으로 우리가 쓰는 방법이다), 연산자가 피연산자 앞에 위치하는 전위 표기법(prefix notation), 연산자가 피연산자 뒤에 위치하는 후위 표기법(postfix notation)이 그것이다. 예를 들어 중위 표기법으로 표현된 a+b는 전위 표기법으로는 +ab이고, 후위 표기법으로는 ab+가 된다.

이 문제에서 우리가 다룰 표기법은 후위 표기법이다. 후위 표기법은 위에서 말한 법과 같이 연산자가 피연산자 뒤에 위치하는 방법이다. 이 방법의 장점은 다음과 같다. 우리가 흔히 쓰는 중위 표기식 같은 경우에는 덧셈과 곱셈의 우선순위에 차이가 있어 왼쪽부터 차례로 계산할 수 없지만 후위 표기식을 사용하면 순서를 적절히 조절하여 순서를 정해줄 수 있다. 또한 같은 방법으로 괄호 등도 필요 없게 된다. 예를 들어 a+b*c를 후위 표기식으로 바꾸면 abc*+가 된다.

중위 표기식을 후위 표기식으로 바꾸는 방법을 간단히 설명하면 이렇다. 우선 주어진 중위 표기식을 연산자의 우선순위에 따라 괄호로 묶어준다. 그런 다음에 괄호 안의 연산자를 괄호의 오른쪽으로 옮겨주면 된다.

예를 들어 a+b*c는 (a+(b*c))의 식과 같게 된다. 그 다음에 안에 있는 괄호의 연산자 *를 괄호 밖으로 꺼내게 되면 (a+bc*)가 된다. 마지막으로 또 +를 괄호의 오른쪽으로 고치면 abc*+가 되게 된다.

다른 예를 들어 그림으로 표현하면 A+B*C-D/E를 완전하게 괄호로 묶고 연산자를 이동시킬 장소를 표시하면 다음과 같이 된다.



결과: ABC*+DE/-

이러한 사실을 알고 중위 표기식이 주어졌을 때 후위 표기식으로 고치는 프로그램을 작성하시오

입력
첫째 줄에 중위 표기식이 주어진다. 단 이 수식의 피연산자는 알파벳 대문자로 이루어지며 수식에서 한 번씩만 등장한다. 그리고 -A+B와 같이 -가 가장 앞에 오거나 AB와 같이 *가 생략되는 등의 수식은 주어지지 않는다. 표기식은 알파벳 대문자와 +, -, *, /, (, )로만 이루어져 있으며, 길이는 100을 넘지 않는다.

출력
첫째 줄에 후위 표기식으로 바뀐 식을 출력하시오

예제 입력 1
A*(B+C)
예제 출력 1
ABC+*
예제 입력 2
A+B
예제 출력 2
AB+
예제 입력 3
A+B*C
예제 출력 3
ABC*+
예제 입력 4
A+B*C-D/E
예제 출력 4
ABC*+DE/-
출처
잘못된 데이터를 찾은 사람: djm03178
데이터를 추가한 사람: fccva, gangminson
빠진 조건을 찾은 사람: jung2381187
알고리즘 분류
자료 구조
스택
 */
/*
알고리즘 핵심
자료구조 (Stack)
1. 피연산자의 경우 ans에 바로 저장하고, 연산자의 경우 우선순위를 비교하여 연산자의 순서를 결정하기 위해 Stack을 사용한다.
2. 우선순위의 경우, "+,-" = 1, "*,/" = 2, "(,)" = 3으로 설정한다.
3. 연산자가 "+,-,*,/"인 경우, operator(stack)의 peek의 우선순위 값보다 크고, 비어있지 않으며, "("이 아닌 경우에 operator의 값을 조건에 만족할 때까지 pop한다.
4. 연산자가 "(,)"인 경우, (는 operator에 저장하고, )의 경우 괄호를 닫아야 하기때문에 (가 pop될 때까지 pop한다.
5. 이후, 남은 operator의 연산자를 모두 pop하고 ans를 출력한다.
 */
public class BOJ1918 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String[] secondary_notation;
    static Stack<String> operator;
    static StringBuilder ans;
    static Map<String, Integer> priority;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        for(String sn : secondary_notation) {
            switch (sn) {
                case "+","-","*","/":
                    while(!operator.isEmpty() && priority.get(sn) <= priority.get(operator.peek()) && !operator.peek().equals("(")) {
                        ans.append(operator.pop());
                    }
                    operator.push(sn);
                    break;
                case "(",")":
                    if(sn.equals(")")) {
                        while(!operator.isEmpty()) {
                            String s = operator.pop();
                            if(!s.equals("(")) ans.append(s);
                            else break;
                        }
                    } else {
                        operator.push(sn);
                    }
                    break;
                default:
                    ans.append(sn);
                    break;
            }
        }

        while(!operator.isEmpty()) ans.append(operator.pop());

        System.out.println(ans.toString());
    }

    private static void init_setting() throws IOException {
        secondary_notation = br.readLine().split("");

        operator = new Stack<>();

        priority = new HashMap<>();

        priority.put("+",1);
        priority.put("-",1);
        priority.put("*",2);
        priority.put("/",2);
        priority.put("(",3);
        priority.put(")",3);

        ans = new StringBuilder();
    }
}
