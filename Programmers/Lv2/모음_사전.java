package Programmers;

/*
모음 사전
제출 내역
문제 설명
사전에 알파벳 모음 'A', 'E', 'I', 'O', 'U'만을 사용하여 만들 수 있는, 길이 5 이하의 모든 단어가 수록되어 있습니다. 사전에서 첫 번째 단어는 "A"이고, 그다음은 "AA"이며, 마지막 단어는 "UUUUU"입니다.

단어 하나 word가 매개변수로 주어질 때, 이 단어가 사전에서 몇 번째 단어인지 return 하도록 solution 함수를 완성해주세요.

제한사항
word의 길이는 1 이상 5 이하입니다.
word는 알파벳 대문자 'A', 'E', 'I', 'O', 'U'로만 이루어져 있습니다.
입출력 예
word	result
"AAAAE"	6
"AAAE"	10
"I"	1563
"EIO"	1189
입출력 예 설명
입출력 예 #1

사전에서 첫 번째 단어는 "A"이고, 그다음은 "AA", "AAA", "AAAA", "AAAAA", "AAAAE", ... 와 같습니다. "AAAAE"는 사전에서 6번째 단어입니다.

입출력 예 #2

"AAAE"는 "A", "AA", "AAA", "AAAA", "AAAAA", "AAAAE", "AAAAI", "AAAAO", "AAAAU"의 다음인 10번째 단어입니다.

입출력 예 #3

"I"는 1563번째 단어입니다.

입출력 예 #4

"EIO"는 1189번째 단어입니다.
 */
/*
알고리즘 핵심
구현
1. 다음과 같은 규칙이 존재한다.
A = 1, AA = 2, AAA = 3, AAAA = 4, AAAAA = 5, AAAAE = 6
AAAE = 10
AAE = 34
...
Ax -> Ex로 넘어갈 때 필요한 변경횟수는 AA,AE,AI,AO,AU = 5 + 1
Axx -> Exx로 넘어갈 때 필요한 변경횟수는 AA,AAA,AAE,AAI,AAO,AAU,AE,AEA,AEE,AEI,AEO,AEU, ... = 25 + 5 + 1
Axxx -> Exxx 또한, 이전 변경횟수와 연관되므로, (25 x 5) + 30 + 1
Axxxx -> Exxxx, (125 x 5) + 155 + 1
이때, 마지막 + 1의 경우 해당 자릿수에서 다음 문자로 넘어가기 위한 횟수로 각 자리수마다 적용해야 한다.
2. 위 규칙을 적용하여, 각 자리에서 변경이 필요한 문자의 차이만큼 (X - A) x {(5^n) + 이전 문자 변경횟수} + 1을 누적한다.
 */
public class 모음_사전 {
    public static void main() {
        String[] words = {
                "AAAAE", "AAAE", "I", "EIO"
        };
        Solve task = new Solve();
        task.solution(words[0]);
    }

    private static class Solve {
        private final int max_l = 5;
        private int ans,L,l;

        public int solution(String word) {
            init_setting(word);

            ans = dictionary(word,L,l);

            return ans;
        }

        private int dictionary(String word, int l, int l1) {

        }

        private void init_setting(String word) {
            L = word.length();
            l = max_l - L;

            ans = 0;
        }
    }
}
