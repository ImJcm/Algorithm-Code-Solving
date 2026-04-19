package Programmers;
import java.lang.StringBuilder;

/*
문제 설명
문자열 s에는 공백으로 구분된 숫자들이 저장되어 있습니다. str에 나타나는 숫자 중 최소값과 최대값을 찾아 이를 "(최소값) (최대값)"형태의 문자열을 반환하는 함수, solution을 완성하세요.
예를들어 s가 "1 2 3 4"라면 "1 4"를 리턴하고, "-1 -2 -3 -4"라면 "-4 -1"을 리턴하면 됩니다.

제한 조건
s에는 둘 이상의 정수가 공백으로 구분되어 있습니다.
입출력 예
s	return
"1 2 3 4"	"1 4"
"-1 -2 -3 -4"	"-4 -1"
"-1 -1"	"-1 -1"
 */
/*
    Stirng 변수에 concat을 사용하려면, 변수의 값이 null이면 안된다. = NullPointerException
    StringBuilder 함수를 사용해서 append하는 방법도 있음
        java.lang.StringBuilder;
        StringBuilder sb = new StringBuilder();
        sb.append("String");
 */
public class Solution {
    public static String solution(String s) {
        String answer = "";

        String[] tmpString = s.split(" ");
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for(int i=0;i<tmpString.length;i++) {
            int value = Integer.parseInt(tmpString[i]);
            if (max < value) max = value;
            if (min > value) min = value;
        }
        answer += Integer.toString(min);
        answer += " " + Integer.toString(max);
        return answer;
    }

    public static void main(String[] args) {
        String s = solution("1 2 3 4");

        System.out.println(s);
    }
}
