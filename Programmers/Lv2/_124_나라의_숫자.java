package Lv2;

/*
124 나라의 숫자
제출 내역
문제 설명
124 나라가 있습니다. 124 나라에서는 10진법이 아닌 다음과 같은 자신들만의 규칙으로 수를 표현합니다.

124 나라에는 자연수만 존재합니다.
124 나라에는 모든 수를 표현할 때 1, 2, 4만 사용합니다.
예를 들어서 124 나라에서 사용하는 숫자는 다음과 같이 변환됩니다.

10진법	124 나라	10진법	124 나라
1	1	6	14
2	2	7	21
3	4	8	22
4	11	9	24
5	12	10	41
자연수 n이 매개변수로 주어질 때, n을 124 나라에서 사용하는 숫자로 바꾼 값을 return 하도록 solution 함수를 완성해 주세요.

제한사항
n은 50,000,000이하의 자연수 입니다.
입출력 예
n	result
1	1
2	2
3	4
4	11
※ 공지 - 2022년 9월 5일 제한사항이 수정되었습니다.
 */
/*
알고리즘 핵심
진수변환 + 구현
1. n을 3진수 변환한 문자열에서 (0,1,2)와 (4,1,2)의 규칙성을 찾을 수 있었다.
2. 3진수 변환문자열에서 0인 경우, 4로 변경하고 다음 위치의 값을 -1하여 1,2,4로 문자열을 구성할 수 있다.
ex) 12 = 3진수(110) -> 124 나라 문법 적용 -> 114 -> 104 -> 144 -> 044 -> 44
즉, 뒤의 값에서 시작하여 값을 변환하고, 0인 경우 다음 수에 -1을 적용하여 앞자리까지 수행한다.
이때, 가장 왼쪽자리의 값은 -1을 적용하였을 때, 0 -> 4로 변환하는 과정은 적용하지 않고, -1을 적용한 값만을 적용한다.
 */
public class _124_나라의_숫자 {
    static void main() {
        int n = 2;

        Solve task = new Solve();
        System.out.println(task.solution(n));
    }

    private static class Solve {
        private String ans,trinary_str;

        public String solution(int n) {
            init_setting(n);

            _124_country_digit(trinary_str);

            return ans;
        }

        private void _124_country_digit(String tri_str) {
            StringBuilder sb = new StringBuilder();
            boolean down = false;

            for(int i = tri_str.length() - 1; i >= 0; i--) {
                int num = Integer.parseInt(String.valueOf(tri_str.charAt(i)));
                num = down ? num - 1 : num;
                down = false;

                if(i == 0) {
                    if(num > 0) sb.append(num);
                    break;
                }

                if(num == 0) {
                    sb.append("4");
                    down = true;
                } else if(num < 0) {
                    sb.append("2");
                    down = true;
                } else {
                    sb.append(num);
                }
            }

            ans = sb.reverse().toString();
        }

        private void init_setting(int n) {
            ans = "";

            trinary_str = Integer.toString(n,3);
        }
    }
}
