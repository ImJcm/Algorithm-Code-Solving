package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
A와 B

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	23215	11175	9259	47.412%
문제
수빈이는 A와 B로만 이루어진 영어 단어가 존재한다는 사실에 놀랐다. 대표적인 예로 AB (Abdominal의 약자), BAA (양의 울음 소리), AA (용암의 종류), ABBA (스웨덴 팝 그룹)이 있다.

이런 사실에 놀란 수빈이는 간단한 게임을 만들기로 했다. 두 문자열 S와 T가 주어졌을 때, S를 T로 바꾸는 게임이다. 문자열을 바꿀 때는 다음과 같은 두 가지 연산만 가능하다.

문자열의 뒤에 A를 추가한다.
문자열을 뒤집고 뒤에 B를 추가한다.
주어진 조건을 이용해서 S를 T로 만들 수 있는지 없는지 알아내는 프로그램을 작성하시오.

입력
첫째 줄에 S가 둘째 줄에 T가 주어진다. (1 ≤ S의 길이 ≤ 999, 2 ≤ T의 길이 ≤ 1000, S의 길이 < T의 길이)

출력
S를 T로 바꿀 수 있으면 1을 없으면 0을 출력한다.

예제 입력 1
B
ABBA
예제 출력 1
1
예제 입력 2
AB
ABB
예제 출력 2
0
출처
문제의 오타를 찾은 사람: apjw6112
문제를 번역한 사람: baekjoon
알고리즘 분류
구현
그리디 알고리즘
문자열
 */
/*
알고리즘 핵심
그리디 알고리즘 + 문자열
1. 입력으로 주어지는 S에서 T로 전환이 가능한지 여부를 확인하는 것이므로 T에서 S로 변환하는 과정을 검사하여 답을 도출할 수 있다.
2. T에서 끝 문자부터 줄이기 시작하여 끝 문자가 A or B에 따라 로직이 달라진다.
3. A인 경우 다음으로 제거할 문자는 가장 뒷 문자를 줄이는 과정을 진행하고, B인 경우 문자열을 뒤집는 과정은
T 문자열의 앞부분 인덱스를 줄이면 같은 효과를 가지므로 앞 부분의 문자를 줄이는 과정을 수행한다.
(T 문자열을 뒤집는 과정 대신 앞 또는 뒤의 문자를 선택하여 제거하는 형태로 시간 복잡도를 줄일 수 있다.
문자열을 뒤집는 과정 : O(T 문자열의 길이 = N), 뒤집는 과정을 생략 : O(1))
4. A와 B의 문자열의 길이가 같아질 때, 앞 또는 뒤의 문자를 제거하는지 여부에 따라 B의 문자열을 뒤집고 A 문자열과 같은지 비교를 수행한다.
 */
public class BOJ12904 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String A,B;
    static int ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        /*char ch = B.charAt(B.length() - 1);
        B = B.substring(0,B.length() - 1);
        boolean s_or_e = ch != 'A';*/  // start_idx = true, end_idx = false;
        boolean s_or_e = false;

        while(true) {
            if(B.length() == A.length()) {
                StringBuilder sb = new StringBuilder(B);

                if(s_or_e) B = sb.reverse().toString();

                if(A.equals(B)) ans = 1;
                else ans = 0;
                break;
            }

            char ch = s_or_e ? B.charAt(0) : B.charAt(B.length() - 1);
            B = s_or_e ? B.substring(1) : B.substring(0,B.length() - 1);

            s_or_e = (ch == 'B') != s_or_e;
            /*
                ch = B -> s_or_e != s_or_e; (ch가 B면, s_or_e를 반대로 변환하고, ch가 B가 아니면, s_or_e를 그대로둔다.)
                XOR(배타적 논리합) 연산과 같은 효과
                true != true → false
                true != false → true
                false != true → true
                false != false → false
             */
        }

        System.out.println(ans);
    }

    private static void init_setting() throws IOException {
        A = br.readLine();
        B = br.readLine();

        ans = 0;
    }
}
