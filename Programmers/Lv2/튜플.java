package Programmers;

import java.util.*;

/*
	[123]
"{{4,2,3},{3},{2,3,4,1},{2,3}}"	[3, 2, 4, 1]
입출력 예에 대한 설명
입출력 예 #1
문제 예시와 같습니다.

입출력 예 #2
문제 예시와 같습니다.

입출력 예 #3
(111, 20)을 집합 기호를 이용해 표현하면 {{111}, {111,20}}이 되며, 이는 {{20,111},{111}}과 같습니다.

입출력 예 #4
(123)을 집합 기호를 이용해 표현하면 {{123}} 입니다.

입출력 예 #5
(3, 2, 4, 1)을 집합 기호를 이용해 표현하면 {{3},{3,2},{3,2,4},{3,2,4,1}}이 되며, 이는 {{4,2,3},{3},{2,3,4,1},{2,3}}과 같습니다.
 */
/*
입력으로 주어지는 String 데이터에서 "{,}"에 해당하는 문자를 공백으로 치환 후, ","를 기준으로 문자들을 구분하여 숫자형인 문자열을 Key 값으로 갖는
Map을 생성하여 해당하는 Key값의 Value를 중복하는 값만큼 늘려준다.

Map의 KeySet을 기준으로 List를 만들어 Value 값을 기준으로 내림차순 정렬하면 정답
 */
class 튜플 {
    static Map<String, Integer> m = new HashMap<>();
    public static int[] solution(String s) {
        int[] answer = {};
        int start,end;
        boolean flag = false;

        s = s.replaceAll("[{}]","");

        start = 0;
        end = 0;
        for(int i=0;i<s.length();i++) {
            char c = s.charAt(i);
            if(c == ',') {
                flag = true;
                end = i;
            }

            if(i == s.length() - 1) {
                flag = true;
                end = i + 1;
            }

            if(flag) {
                String number = s.substring(start, end);

                if(m.containsKey(number)) {
                    m.replace(number,m.get(number) + 1);
                } else {
                    m.put(number,1);
                }
                start = i + 1;
                flag = false;
            }
        }

        List<String> entries = new ArrayList<>(m.keySet());

        Collections.sort(entries, Collections.reverseOrder());

        Collections.sort(entries, (v1, v2) -> m.get(v2).compareTo(m.get(v1)));

        answer = entries.stream()
                .mapToInt(Integer::valueOf)
                .toArray();

        return answer;
    }


    public static void main(String[] args) {
        String s = "{{2},{2,1},{2,1,3},{2,1,3,4}}";

        solution(s);
    }
}
