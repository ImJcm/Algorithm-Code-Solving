package Lv2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/*
가장 큰 수
제출 내역
문제 설명
0 또는 양의 정수가 주어졌을 때, 정수를 이어 붙여 만들 수 있는 가장 큰 수를 알아내 주세요.

예를 들어, 주어진 정수가 [6, 10, 2]라면 [6102, 6210, 1062, 1026, 2610, 2106]를 만들 수 있고, 이중 가장 큰 수는 6210입니다.

0 또는 양의 정수가 담긴 배열 numbers가 매개변수로 주어질 때, 순서를 재배치하여 만들 수 있는 가장 큰 수를 문자열로 바꾸어 return 하도록 solution 함수를 작성해주세요.

제한 사항
numbers의 길이는 1 이상 100,000 이하입니다.
numbers의 원소는 0 이상 1,000 이하입니다.
정답이 너무 클 수 있으니 문자열로 바꾸어 return 합니다.
입출력 예
numbers	return
[6, 10, 2]	"6210"
[3, 30, 34, 5, 9]	"9534330"
※ 공지 - 2021년 10월 20일 테스트케이스가 추가되었습니다.
 */
/*
알고리즘 핵심
정렬
1. int[] 타입으로 넘어온 데이터를 가장 큰 수로 만들기 위해 String타입의 정렬 기준 + 내림차순을 적용해야 한다.
2. 내림차순 정렬 과정에서 String타입의 경우, 앞자리가 같은 경우 자릿수가 작은 수가 앞으로 나오기 때문에 추가적인 조건이 필요하다.
2-a. 두 문자열의 앞자리 수가 같은 경우, 두개의 문자열을 번갈아 덧붙힌 두개의 문자열을 비교하여 큰수가 앞으로 나올 수 있도록 한다.
-> s1 = o1 + o2, s2 = o2 + o1) [3,30] => s1 = 330, s2 = 303, 따라서, 더 큰 수 + 내림차순 => s2.compareTo(s1);
 */
public class 가장_큰_수 {
    static void main() {
        int[] numbers = new int[] {
                300, 30, 34, 5, 9
        };

        Solve task = new Solve();
        System.out.println(task.solution(numbers));
    }

    private static class Solve {
        private StringBuilder ans;
        private String[] str_arr;

        public String solution(int[] numbers) {
            init_setting(numbers);

            search_most_number(str_arr);

            return ans.toString();
        }

        private void search_most_number(String[] numbers) {
            Arrays.sort(numbers, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String s1 = o1 + o2;
                    String s2 = o2 + o1;
                    return s2.compareTo(s1);
                }
            });

            for(String s : numbers) {
                ans.append(s);
            }
        }

        private void init_setting(int[] numbers) {
            ans = new StringBuilder();

            str_arr = Arrays.stream(numbers)
                    .mapToObj(Integer::toString)
                    .toArray(String[]::new);
        }
    }
}
