import java.util.*;

/*
전화번호 목록
제출 내역
문제 설명
전화번호부에 적힌 전화번호 중, 한 번호가 다른 번호의 접두어인 경우가 있는지 확인하려 합니다.
전화번호가 다음과 같을 경우, 구조대 전화번호는 영석이의 전화번호의 접두사입니다.

구조대 : 119
박준영 : 97 674 223
지영석 : 11 9552 4421
전화번호부에 적힌 전화번호를 담은 배열 phone_book 이 solution 함수의 매개변수로 주어질 때, 어떤 번호가 다른 번호의 접두어인 경우가 있으면 false를 그렇지 않으면 true를 return 하도록 solution 함수를 작성해주세요.

제한 사항
phone_book의 길이는 1 이상 1,000,000 이하입니다.
각 전화번호의 길이는 1 이상 20 이하입니다.
같은 전화번호가 중복해서 들어있지 않습니다.
입출력 예제
phone_book	return
["119", "97674223", "1195524421"]	false
["123","456","789"]	true
["12","123","1235","567","88"]	false
입출력 예 설명
입출력 예 #1
앞에서 설명한 예와 같습니다.

입출력 예 #2
한 번호가 다른 번호의 접두사인 경우가 없으므로, 답은 true입니다.

입출력 예 #3
첫 번째 전화번호, “12”가 두 번째 전화번호 “123”의 접두사입니다. 따라서 답은 false입니다.

알림

2021년 3월 4일, 테스트 케이스가 변경되었습니다. 이로 인해 이전에 통과하던 코드가 더 이상 통과하지 않을 수 있습니다.
 */
/*
접두사에 대한 이해가 부족하여 정답 코드를 참고하였다.
1. 접두사 = 문장의 앞부분을 의미
2. Arrays.sort()하는 이유 : String의 정렬 기준은 앞부분부터 유사한 문자열이 나열되도록 정렬하여 효율적인 접두사 체크가 가능하기 위해서이다.
3. 각 전화번호를 선택하여 전화번호의 앞부분부터 접두사를 조합하여 해당하는 접두사가 sets에 존재하는지 검사한다.
예시#1 ["119", "97674223", "1195524421"]에서 119는 1, 11 까지만 sets에서 포함여부를 체크하고, 1195524421에서 1,11,119,....
    119에서 접두사로 체크된다.
 */
class Solution {
    static HashSet<String> sets;
    static boolean solution(String[] phone_book) {
        boolean answer = true;

        init_setting(phone_book);

        for(String phone_number : phone_book) {
            for(int i=0;i<phone_number.length();i++) {
                if(sets.contains(phone_number.substring(0,i))) {
                    answer = false;
                    break;
                }
            }
        }

        return answer;
    }

    static void init_setting(String[] phone_book) {
        Arrays.sort(phone_book);

        sets = new LinkedHashSet<>(Arrays.asList(phone_book));
    }

    public static void main(String[] args) {
        String[] phone_book_ex1 = {"119", "97674223", "1195524421"};
        String[] phone_book_ex2 = {"123","456","789"};
        String[] phone_book_ex3 = {"12","123","1235","567","88"};

        System.out.println(solution(phone_book_ex1));
        //System.out.println(solution(phone_book_ex2));
    }
}

/*
전화번호부에 존재하는 전화번호를 하나의 String으로 만들어 접두어를 고려하지 않는 포함여부를 체크하는 코드 + 효율성 실패
 */
class Solution_singleSentence_WrongAnswer {
    static String combine_phone_numbers;
    static boolean solution(String[] phone_book) {
        boolean answer = true;

        init_setting(phone_book);

        // 접두어가 아닌 포함여부를 체크하여 실패
        for(String phone_number : phone_book) {
            int idx = combine_phone_numbers.indexOf(phone_number);
            if(combine_phone_numbers.indexOf(phone_number,idx + 1) > 0) {
                answer = false;
                break;
            }
        }

        return answer;
    }

    static void init_setting(String[] phone_book) {
        Arrays.asList(phone_book).stream().sorted();
        combine_phone_numbers = "";

        for(String phone_number : phone_book) {
            combine_phone_numbers += (phone_number + " ");
        }
    }

    public static void main(String[] args) {
        String[] phone_book_ex1 = {"119", "97674223", "1195524421"};
        String[] phone_book_ex2 = {"123","456","789"};
        String[] phone_book_ex3 = {"12","123","1235","567","88"};

        System.out.println(solution(phone_book_ex1));
    }
}
