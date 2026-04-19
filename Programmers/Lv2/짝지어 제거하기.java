package Programmers;

import java.util.Stack;

/*
문제 설명
짝지어 제거하기는, 알파벳 소문자로 이루어진 문자열을 가지고 시작합니다. 먼저 문자열에서 같은 알파벳이 2개 붙어 있는 짝을 찾습니다. 그다음, 그 둘을 제거한 뒤, 앞뒤로 문자열을 이어 붙입니다. 이 과정을 반복해서 문자열을 모두 제거한다면 짝지어 제거하기가 종료됩니다. 문자열 S가 주어졌을 때, 짝지어 제거하기를 성공적으로 수행할 수 있는지 반환하는 함수를 완성해 주세요. 성공적으로 수행할 수 있으면 1을, 아닐 경우 0을 리턴해주면 됩니다.

예를 들어, 문자열 S = baabaa 라면

b aa baa → bb aa → aa →

의 순서로 문자열을 모두 제거할 수 있으므로 1을 반환합니다.

제한사항
문자열의 길이 : 1,000,000이하의 자연수
문자열은 모두 소문자로 이루어져 있습니다.
입출력 예
s	result
baabaa	1
cdcd	0
입출력 예 설명
입출력 예 #1
위의 예시와 같습니다.
입출력 예 #2
문자열이 남아있지만 짝지어 제거할 수 있는 문자열이 더 이상 존재하지 않기 때문에 0을 반환합니다.

※ 공지 - 2020년 6월 8일 테스트케이스가 추가되었습니다.
 */
/*
    처음 접근한 방식은 String에서 첫번째 문자부터 시작해서 +1 인덱스의 문자가 같은 경우, replaceAll로 ""으로 치환하고, 다시
    인덱스 0부터 시작하는 방법으로 접근했지만, 시간초과가 발생했다. 아마도, i=0으로 돌아가는 시점에서 O(N^2)이 된 것으로 본다.
    따라서, Stack을 이용해서 문자열 길이만큼 O(N)만큼만 걸리는 방식을 사용하였다.
    Stack을 이용하는 방법은 스택이 비어있다면 i=0부터 시작하는 문자를 push하고, 다음 문자와 stack.peek()을 비교하였을 때 같으면 pop한다.
    이를 반복해서 stack이 비어있다면,1 비어있지 않다면 0을 반환한다.
 */
class Solution {
    public static void main(String[] args) {
        System.out.println(solution("baabaa"));
    }

    public static int solution(String s) {
        int answer = -1;

        Stack<Character> stack = new Stack<>();

        for(int i=0;i<s.length();i++) {
            if(stack.isEmpty()) {
                stack.push(s.charAt(i));
                continue;
            }
            char c = s.charAt(i);
            if(stack.peek() == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        if(stack.isEmpty()) {
            answer = 1;
        } else {
            answer = 0;
        }

        /*//시간초과 - 다시 i부터 시작하면 시간이 오래걸리는 것같다.
        //아무래도 i=0으로 다시 돌아가기 때문에 O(n^2)
        for(int i=0;i<s.length()-1;) {
            String subStr = s.substring(i,i+2);
            if(subStr.charAt(0) == subStr.charAt(1)) {
                s = s.replaceAll(subStr,"");
                i=0;   //다시 앞에서 부터 시작
                continue;
            }
            i++;
        }

        if(s.length() == 0) {
            answer = 1;
        } else {
            answer = 0;
        }*/

        return answer;
    }

}
