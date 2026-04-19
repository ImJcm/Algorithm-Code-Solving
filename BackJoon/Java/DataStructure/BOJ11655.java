package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
ROT13

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	31867	19011	15567	60.223%
문제
ROT13은 카이사르 암호의 일종으로 영어 알파벳을 13글자씩 밀어서 만든다.

예를 들어, "Baekjoon Online Judge"를 ROT13으로 암호화하면 "Onrxwbba Bayvar Whqtr"가 된다. ROT13으로 암호화한 내용을 원래 내용으로 바꾸려면 암호화한 문자열을 다시 ROT13하면 된다. 앞에서 암호화한 문자열 "Onrxwbba Bayvar Whqtr"에 다시 ROT13을 적용하면 "Baekjoon Online Judge"가 된다.

ROT13은 알파벳 대문자와 소문자에만 적용할 수 있다. 알파벳이 아닌 글자는 원래 글자 그대로 남아 있어야 한다. 예를 들어, "One is 1"을 ROT13으로 암호화하면 "Bar vf 1"이 된다.

문자열이 주어졌을 때, "ROT13"으로 암호화한 다음 출력하는 프로그램을 작성하시오.

입력
첫째 줄에 알파벳 대문자, 소문자, 공백, 숫자로만 이루어진 문자열 S가 주어진다. S의 길이는 100을 넘지 않는다.

출력
첫째 줄에 S를 ROT13으로 암호화한 내용을 출력한다.

예제 입력 1
Baekjoon Online Judge
예제 출력 1
Onrxwbba Bayvar Whqtr
예제 입력 2
One is 1
예제 출력 2
Bar vf 1
출처
문제를 만든 사람: baekjoon
알고리즘 분류
구현
문자열
 */
/*
알고리즘 핵심
자료구조 (문자열)
1. 입력으로 주어진 문자열을 각 문자로 구분하기 위해 String.toCharArray()로 분리한다.
2. 공백, 숫자 문자는 ROT13에 그대로 저장하고, 알파벳 소문자, 대문자는 해당 문자에서 13을 더했을 때 각 문자 기준 'z' or 'Z'
를 넘어가는 경우, 13을 뺀 문자를 ROT13을 저장한다.
넘어가지 않는 경우, 13을 더한 문자를 ROT13에 저장한다.
 */
public class BOJ11655 {
    // alphabet = a b c d e f g h i j k l m n o p q r s t u v w x y z
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String S;
    static StringBuilder ROT13;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        for(Character ch : S.toCharArray()) {
            switch (ch) {
                case ' ','1','2','3','4','5','6','7','8','9','0':
                    ROT13.append(ch);
                    break;
                default: {
                    Character ch2 = null;
                    if('a' <= ch && ch <= 'z') {
                        if((char) (ch + 13) > 'z') {
                            ch2 = (char) (ch - 13);
                        } else {
                            ch2 = (char) (ch + 13);
                        }
                    } else if('A' <= ch && ch <= 'Z') {
                        if((char) (ch + 13) > 'Z') {
                            ch2 = (char) (ch - 13);
                        } else {
                            ch2 = (char) (ch + 13);
                        }
                    }

                    ROT13.append(ch2);
                }
            }
        }

        System.out.println(ROT13.toString());
    }

    private static void init_setting() throws IOException {
        S = br.readLine();

        ROT13 = new StringBuilder();
    }
}
