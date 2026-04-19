package Programmers;

/*
JadenCase란 모든 단어의 첫 문자가 대문자이고, 그 외의 알파벳은 소문자인 문자열입니다. 단, 첫 문자가 알파벳이 아닐 때에는 이어지는 알파벳은 소문자로 쓰면 됩니다. (첫 번째 입출력 예 참고)
문자열 s가 주어졌을 때, s를 JadenCase로 바꾼 문자열을 리턴하는 함수, solution을 완성해주세요.

제한 조건
s는 길이 1 이상 200 이하인 문자열입니다.
s는 알파벳과 숫자, 공백문자(" ")로 이루어져 있습니다.
숫자는 단어의 첫 문자로만 나옵니다.
숫자로만 이루어진 단어는 없습니다.
공백문자가 연속해서 나올 수 있습니다.
입출력 예
s	                    return
"3people unFollowed me"	"3people Unfollowed Me"
"for the last week"	"For The Last Week"
 */
/*
해당 코드는 몇몇개의 테스트코드만 성공하고 나머지 테스트코드는 실패 또는 런타임 에러가 발생
따라서, 코드자체를 변경할 필요가 있음을 판단했다.

문자 하나하나를 기준으로 공백문자일 경우, 다음 문자를 대문자로 바꿔서 문자열을 완성하는 형식으로 코드 구현
 */
class Solution {
    public String solution(String s) {
        String answer = "";

        boolean chk = true; //공백문자 + 다음문자열 대문자만들 수 있는 조건
        for(String str : s.toLowerCase().split("")) {
            answer += (chk ? str.toUpperCase() : str);
            chk = (str.equals(" ") ? true : false);
        }
        return answer;
    }
}
/*
public class Solution {
    public String solution(String s) {
        String answer = "";
        String[] sentence = s.split(" ");   //공백으로 문자열 분리

        for(String str : sentence) {
            char c = str.charAt(0);   //각 문자열의 첫 문자
            //첫 문자를 제외한 나머지 문자열 소문자로 만들기
            String tmp_s = (str.substring(1).toLowerCase());

            if('a' <= c && c <= 'z') {  //첫 문자가 소문자인지 확인
                //첫 문자를 대문자로 변경 후, string 타입으로 캐스팅
                //대문자인 첫 문자와 소문자로 변경된 나머지 문자열과 공백을 합쳐서 answer에 추가
                answer += (Character.toString((char)(c - 32)) + tmp_s + " ");
            } else {
                //첫 문자가 소문자 알파벳이 아닌 경우, 기존 문자열을 answer에 추가
                answer += (str + " ");
            }
        }

        //문자열을 만드는 과정에서 마지막 문자에 공백이 추가되므로, 반환 시에는 마지막 문자인 공백을
        //제거해야함.
        return answer.substring(0,answer.length() - 1);
    }
}
*/
