package Lv2;

/*
큰 수 만들기
제출 내역
문제 설명
어떤 숫자에서 k개의 수를 제거했을 때 얻을 수 있는 가장 큰 숫자를 구하려 합니다.

예를 들어, 숫자 1924에서 수 두 개를 제거하면 [19, 12, 14, 92, 94, 24] 를 만들 수 있습니다. 이 중 가장 큰 숫자는 94 입니다.

문자열 형식으로 숫자 number와 제거할 수의 개수 k가 solution 함수의 매개변수로 주어집니다. number에서 k 개의 수를 제거했을 때 만들 수 있는 수 중 가장 큰 숫자를 문자열 형태로 return 하도록 solution 함수를 완성하세요.

제한 조건
number는 2자리 이상, 1,000,000자리 이하인 숫자입니다.
k는 1 이상 number의 자릿수 미만인 자연수입니다.
입출력 예
number	        k	return
"1924"	        2	"94"
"1231234"	    3	"3234"
"4177252841"	4	"775841"
 */
/*
알고리즘 핵심
탐욕(Greedy)
1. k개를 제외하고 순서에 맞게 가장 큰 수를 만드는 것이므로 규칙에 맞는 수를 제거하는 방법의 탐욕법을 사용한다.
2. 앞의 자리부터 다음 수와 비교하여 현재 수가 작다면 해당 수를 제거한다.
3. 2번 과정을 반복하여 k개 만큼 제외하면 큰 수를 만들 수 있다.

이때, String 타입의 변수를 다루게 될 경우, 시간초과 발생 가능성이 있기때문에 StringBuilder를 사용하여 number를 다룬다.
 */
public class 큰_수_만들기 {
    static void main() {
        String number = new String(
                //"4177252841"
                //"1231234"
                //"1924"
                //"9988"
                //"987654321
                //"12121212"
                "10"
        );

        int k = 1;

        Solve task = new Solve();
        System.out.println(task.solution(number, k));
    }

    /*
        Wrong solve : #10 - time out

     */
    private static class Solve {
        private String ans;

        public String solution(String number, int k) {
            init_setting(number);

            make_big_number(number,k);

            return ans;
        }

        private void make_big_number(String number, int k) {
            int remove = 0, j = 0;
            boolean do_remove;
            StringBuilder sb = new StringBuilder(number);

            while(remove < k) {
                do_remove = false;

                for(int i = j; i < sb.length() - 1; i++) {
                    if(sb.charAt(i) < sb.charAt(i + 1)) {
                        sb.replace(i, i + 1, "");
                        remove++;
                        do_remove = true;
                        j = Math.max(0, i - 1);
                        break;
                    }
                }

                if(!do_remove) {
                    sb.replace(number.length() - (k - remove), number.length(), "");
                    break;
                }
            }
            ans = sb.toString();
        }

        /*
            Wrong Solve : TestCase #10 - time out
         */
        private void Timeout_make_big_number(String number, int k) {
            int remove = 0, j = 0;
            boolean do_remove;

            while(remove < k) {
                do_remove = false;

                for(int i = j; i < number.length() - 1; i++) {
                    int res = Character.compare(number.charAt(i), number.charAt(i + 1));
                    if(res < 0) {
                        number = number.substring(0, i) + number.substring(i + 1);
                        remove++;
                        do_remove = true;
                        j = Math.max(0, i - 1);
                        break;
                    }
                }

                if(!do_remove) {
                    number = number.substring(0, number.length() - (k - remove));
                    break;
                }
            }
            ans = number;
        }

        private void init_setting(String number) {
            ans = "";
        }
    }
}
