package Programmers;
/*
문제 설명
Finn은 요즘 수학공부에 빠져 있습니다. 수학 공부를 하던 Finn은 자연수 n을 연속한 자연수들로 표현 하는 방법이 여러개라는 사실을 알게 되었습니다. 예를들어 15는 다음과 같이 4가지로 표현 할 수 있습니다.

1 + 2 + 3 + 4 + 5 = 15
4 + 5 + 6 = 15
7 + 8 = 15
15 = 15
자연수 n이 매개변수로 주어질 때, 연속된 자연수들로 n을 표현하는 방법의 수를 return하는 solution를 완성해주세요.

제한사항
n은 10,000 이하의 자연수 입니다.
입출력 예
n	result
15	4
입출력 예 설명
입출력 예#1
문제의 예시와 같습니다.
 */
/*
    자연수 n이므로, 1~n 까지의 연속된 합 조합을 구하기 위해서 i:1~n 까지 반복문_1
    i부터 시작하는 연속된 합 조합을 만들기 위해 j:i~n 반복문_2
    합이 n과 같으면 결과 갯수 카운트 + 1, 합이 넘어가면 j 반복문 탈출하고 다음 i 진행한다.
 */
class Solution {
    public static void main(String[] args) {
        System.out.println(solution(15));
    }
    public static int solution(int n) {
        int answer = 0;
        for(int i=1;i<=n;i++) {
            int sum = 0;
            for(int j=i;j<=n;j++) {
                sum += j;
                if(sum == n) {
                    answer++;
                } else if(sum > n) {
                    break;
                }
            }
        }
        return answer;
    }
}