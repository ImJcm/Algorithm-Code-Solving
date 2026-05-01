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

AAAAA = 5
AAAA = 9
AAAE = 10
AAAEA = 11
AAAEE 12
AAAEI 13
AAAEO 14
AAAEU 15
AAAI 16
AAAIA 17
AAAIE 18
AAAII 19
AAAIO 20
AAAIU 21
AAAO 22
AAAOA 23
AAAOE 24
AAAOI 25
AAAOO 26
AAAOU 27
AAAU 28
AAAUA 29
AAAUE 30
AAAUI 31
AAAUO 32
AAAUU 33
AAA 34
AAE 35
AAEAA 36
AAEAE 37
AA =
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
        private int ans;
        public int solution(String word) {
            init_setting(word);

            return ans;
        }

        private void init_setting(String word) {

        }
    }
}
