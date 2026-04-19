package Programmers;

import java.util.*;

/*
멀리 뛰기
문제 설명
효진이는 멀리 뛰기를 연습하고 있습니다. 효진이는 한번에 1칸, 또는 2칸을 뛸 수 있습니다. 칸이 총 4개 있을 때, 효진이는
(1칸, 1칸, 1칸, 1칸)
(1칸, 2칸, 1칸)
(1칸, 1칸, 2칸)
(2칸, 1칸, 1칸)
(2칸, 2칸)
의 5가지 방법으로 맨 끝 칸에 도달할 수 있습니다. 멀리뛰기에 사용될 칸의 수 n이 주어질 때, 효진이가 끝에 도달하는 방법이 몇 가지인지 알아내, 여기에 1234567를 나눈 나머지를 리턴하는 함수, solution을 완성하세요. 예를 들어 4가 입력된다면, 5를 return하면 됩니다.

제한 사항
n은 1 이상, 2000 이하인 정수입니다.
입출력 예
n	result
4	5
3	3
입출력 예 설명
입출력 예 #1
위에서 설명한 내용과 같습니다.

입출력 예 #2
(2칸, 1칸)
(1칸, 2칸)
(1칸, 1칸, 1칸)
총 3가지 방법으로 멀리 뛸 수 있습니다.
 */
/*
    1,2로 n을 만들 수 있는 방법 n-1을 만들 수 있는 경우의 수에 1을 더해주면 n이고, n-2을 만들 수 있는 경우의 수에 2를 더하면,
    n을 만들 수 있으므로, 아래와 같은 점화식을 만들 수 있다.
    n을 만들 수 있는 모든 경우의 수는 count[n] += (count[n-1] + count[n-2])의 형태로 n-1 or n-2 가 0이 될때까지
    재귀호출한다.
    n = 4 -> count[4] = count[3] + count[2]
                      = (count[2] + count[1]) + (count[1] + count[0])
                      = ((count[1] + count[0]) + (count[0]) + (count[0] + count[0])
                      = 5 * count[0]
    count[0] = 1;
    count[1] = 1;
    count[2] = 1 + 1, 2 = count[1] + count[0] = count[0] + count[0]
    count[3] = 1 + 1 + 1, 1 + 2, 2 + 1 = count[2] + count[1] = 2 + 1 = 3
    n = 3을 구성하는 경우의 수가 2를 구성하는 경우의 수 (1+1,2) + 1하는 경우와 1를 구성하는 수 (1) + 2
 */
class Solution {
    public static void main(String[] args) {;
        int n = 3;
        System.out.println(solution(n));
    }

    public static long solution(int n) {
        long answer = 0;
        int[] jumps = {1,2};
        int[] count = new int[2001];

        count[0] = 1;
        for(int i=1;i<=n;i++) {
            for(int jump : jumps) {
                if(i-jump >= 0) {
                    count[i] += count[i-jump];
                }
                count[i] %= 1234567;
            }
        }

        answer = count[n];
        return answer;
    }
}

