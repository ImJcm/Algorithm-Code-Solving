package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
사분면

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	3869	1301	933	32.407%
문제
하나의 좌표평면은 다음과 같이 네 개의 사분면으로 나뉜다.



그러면, 각각의 사분면을 다시 사분면으로 나누어 번호를 붙여 보면 어떨까? 예를 들어 1번 사분면의 1번 사분면은 11번 사분면, 3번 사분면의 2번 사분면은 32번 사분면이라고 하면 좋지 않을까? 물론 한 번 더 나눠 볼 수도 있겠다. 3번 사분면의 4번 사분면의 1번 사분면은 341번 사분면이다.



사분면의 번호 길이가 길어짐에 따라 각각의 사분면의 크기는 급격히 작아지며, 그 개수는 기하급수적으로 증가한다.

사분면에 번호를 붙이는 이러한 규칙을 상정하고서, 어떤 사분면 조각을 이동시켰을 때, 그 조각이 위치하게 되는 사분면의 번호가 궁금하다. 예를 들어, 341번 사분면을 오른쪽으로 두 번, 위쪽으로 한 번 이동시키면 424번 사분면에 온다.



하나의 사분면 조각을 이동시켰을 때, 그 조각이 도착한 사분면의 번호를 알아내는 프로그램을 작성하라.

입력
첫 줄에 이동시키려는 사분면 조각 번호의 자릿수를 나타내는 정수 d와, 그 사분면 조각의 번호가 주어진다. (1 ≤ d ≤ 50) 둘째 줄에는 이동의 내용을 나타내는 두 정수가 x, y가 주어진다.
(|x|, |y| ≤ 2^50) 오른쪽으로 이동한 경우 x가 양수, 왼쪽으로 이동한 경우 음수이며, 그 절댓값은 오른쪽 왼쪽 방향 이동 횟수를 나타낸다. 위쪽으로 이동한 경우 y가 양수, 아래쪽으로 이동한 경우 음수이며, 역시 그 절댓값은 아래위 방향 이동 횟수를 나타낸다.

출력
첫 줄에 도착한 사분면의 번호를 출력한다. 만약, 존재하지 않는 사분면인 경우에는 -1을 출력한다.

예제 입력 1
3 341
2 1
예제 출력 1
424
출처
문제를 만든 사람: author10
데이터를 추가한 사람: rkckskdk
알고리즘 분류
수학
분할 정복
재귀
 */
/*
알고리즘 핵심
분할 정복 + 수학
1. d의 값에 따라 사분면의 위치를 표현할 수 있는 배열의 값이 최대 2^50이므로 입력으로 주어지는 시작 사분면을 배열의 인덱스로 나타내는 것은 불가능하다.
2. 입력으로 주어지는 x,y 값을 2진법으로 표현할 수 있고, 해당 진법의 값이 사분면의 위치를 변경한다.
3. x,y의 부호와 값으로 사분면의 이동을 수행하고, 사분면의 이동이 불가능한 조건 검사한다.
4. x의 이동을 우선 수행하고 불가능한 경우 종료하고, 이동이 가능한 경우 다음 y의 이동을 수행한다.
5. 각 x,y의 2진법으로 나타낸 값에서 1인 경우, 각 사분면의 방향을 고려하여 값을 변경하며 값에 따라 다음 사분면의 이동에 영향을 준다.
6. 안쪽 사분면의 값부터 시작하여 바깥쪽 사분면의 이동까지 수행하여 x 이후, y 이동을 수행한 결과를 ans에 저장한다.
이때, x,y이동 과정에서 불가능한 조건을 각각 검사한다.
 */
public class BOJ1891 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int d;
    static long x,y;
    static String n;
    static boolean possible;
    static int[] x_digit,y_digit;
    static StringBuilder ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    /*
        1차 실패 : ArrayIndexOutOfBound
        ㄴ 원인 : moveX(), moveY()에서 가장 왼쪽의 사분면이 변경되는 시점에서 Digits[-1] += 1을 설정하기 때문이다.

        변경 : 0번째 사분면이 이동되는 로직에서 불가능한 조건 검사를 while 내부로 이동하여 ch 값이 변경될 때마다 검사할 수 있도록하였다.
     */
    private static void solve() {
        boolean l_or_r = x < 0;
        boolean u_or_b = y > 0;

        long abs_x = Math.abs(x);
        long abs_y = Math.abs(y);

        move_number_of_digit(x_digit,abs_x);
        move_number_of_digit(y_digit,abs_y);

        if(!possible) ans = new StringBuilder("-1");
        else move_x(x_digit, l_or_r);

        if(possible) {
            n = ans.toString();
            ans = new StringBuilder();

            move_y(y_digit,u_or_b);
        }

        System.out.println(ans);
    }

    private static void move_x(int[] xDigit, boolean lOrR) {
        if(lOrR) {
            for(int i = 0; i < xDigit.length && possible; i++) {
                char ch = n.charAt(d - i - 1);

                // 1차 실패 원인 : 이곳에서 Digit[0]의 사분면이 변경되는 검사조건 적용

                while(xDigit[d - i - 1]-- > 0) {
                    if((ch == '3' || ch == '2') && d - i - 1 == 0) {
                        possible = false;
                        break;
                    }

                    switch (ch) {
                        case '1':
                            ch = '2';
                            break;
                        case '2':
                            ch = '1';
                            xDigit[d - i - 2] += 1;
                            break;
                        case '3':
                            ch = '4';
                            xDigit[d - i - 2] += 1;
                            break;
                        case '4':
                            ch = '3';
                            break;
                    }
                }

                if(!possible) ans = new StringBuilder("-1");
                else ans.insert(0,ch);
            }
        } else {
            for(int i = 0; i < xDigit.length && possible; i++) {
                char ch = n.charAt(d - i - 1);

                while(xDigit[d - i - 1]-- > 0) {
                    if((ch == '4' || ch == '1') && d - i - 1 == 0) {
                        possible = false;
                        break;
                    }

                    switch (ch) {
                        case '1':
                            ch = '2';
                            xDigit[d - i - 2] += 1;
                            break;
                        case '2':
                            ch = '1';
                            break;
                        case '3':
                            ch = '4';
                            break;
                        case '4':
                            ch = '3';
                            xDigit[d - i - 2] += 1;
                            break;
                    }
                }

                if(!possible) ans = new StringBuilder("-1");
                else ans.insert(0,ch);
            }
        }
    }

    private static void move_y(int[] yDigit, boolean uOrB) {
        if(uOrB) {
            for(int i = 0; i < yDigit.length && possible; i++) {
                char ch = n.charAt(d - i - 1);

                while(yDigit[d - i - 1]-- > 0) {
                    if((ch == '1' || ch == '2') && d - i - 1 == 0) {
                        possible = false;
                        break;
                    }

                    switch (ch) {
                        case '1':
                            ch = '4';
                            yDigit[d - i - 2] += 1;
                            break;
                        case '2':
                            ch = '3';
                            yDigit[d - i - 2] += 1;
                            break;
                        case '3':
                            ch = '2';
                            break;
                        case '4':
                            ch = '1';
                            break;
                    }
                }

                if(!possible) ans = new StringBuilder("-1");
                else ans.insert(0,ch);
            }
        } else {
            for(int i = 0; i < yDigit.length && possible; i++) {
                char ch = n.charAt(d - i - 1);

                while(yDigit[d - i - 1]-- > 0) {
                    if((ch == '4' || ch == '3') && d - i - 1 == 0) {
                        possible = false;
                        break;
                    }

                    switch (ch) {
                        case '1':
                            ch = '4';
                            break;
                        case '2':
                            ch = '3';
                            break;
                        case '3':
                            ch = '2';
                            yDigit[d - i - 2] += 1;
                            break;
                        case '4':
                            ch = '1';
                            yDigit[d - i - 2] += 1;
                            break;
                    }
                }

                if(!possible) ans = new StringBuilder("-1");
                else ans.insert(0,ch);
            }
        }
    }

    private static void move_number_of_digit(int[] digits, long n) {
        int limit = 0;

        while(++limit <= d) {
            digits[d - limit] = (int) (n % 2);
            n /= 2;
        }

        if(n != 0) possible = false;
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        d = Integer.parseInt(input[0]);
        n = input[1];

        input = br.readLine().split(" ");

        x = Long.parseLong(input[0]);
        y = Long.parseLong(input[1]);

        ans = new StringBuilder();

        possible = true;

        x_digit = new int[d];
        y_digit = new int[d];
    }
}
