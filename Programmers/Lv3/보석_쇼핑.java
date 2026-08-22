package Lv3;

import java.util.Arrays;
import java.util.HashMap;

/*
[카카오 인턴] 보석 쇼핑
제출 내역
문제 설명
[본 문제는 정확성과 효율성 테스트 각각 점수가 있는 문제입니다.]

개발자 출신으로 세계 최고의 갑부가 된 어피치는 스트레스를 받을 때면 이를 풀기 위해 오프라인 매장에 쇼핑을 하러 가곤 합니다.
어피치는 쇼핑을 할 때면 매장 진열대의 특정 범위의 물건들을 모두 싹쓸이 구매하는 습관이 있습니다.
어느 날 스트레스를 풀기 위해 보석 매장에 쇼핑을 하러 간 어피치는 이전처럼 진열대의 특정 범위의 보석을 모두 구매하되 특별히 아래 목적을 달성하고 싶었습니다.
진열된 모든 종류의 보석을 적어도 1개 이상 포함하는 가장 짧은 구간을 찾아서 구매

예를 들어 아래 진열대는 4종류의 보석(RUBY, DIA, EMERALD, SAPPHIRE) 8개가 진열된 예시입니다.

진열대 번호	1	2	3	4	5	6	7	8
보석 이름	DIA	RUBY	RUBY	DIA	DIA	EMERALD	SAPPHIRE	DIA
진열대의 3번부터 7번까지 5개의 보석을 구매하면 모든 종류의 보석을 적어도 하나 이상씩 포함하게 됩니다.

진열대의 3, 4, 6, 7번의 보석만 구매하는 것은 중간에 특정 구간(5번)이 빠지게 되므로 어피치의 쇼핑 습관에 맞지 않습니다.

진열대 번호 순서대로 보석들의 이름이 저장된 배열 gems가 매개변수로 주어집니다. 이때 모든 보석을 하나 이상 포함하는 가장 짧은 구간을 찾아서 return 하도록 solution 함수를 완성해주세요.
가장 짧은 구간의 시작 진열대 번호와 끝 진열대 번호를 차례대로 배열에 담아서 return 하도록 하며, 만약 가장 짧은 구간이 여러 개라면 시작 진열대 번호가 가장 작은 구간을 return 합니다.

[제한사항]
gems 배열의 크기는 1 이상 100,000 이하입니다.
gems 배열의 각 원소는 진열대에 나열된 보석을 나타냅니다.
gems 배열에는 1번 진열대부터 진열대 번호 순서대로 보석이름이 차례대로 저장되어 있습니다.
gems 배열의 각 원소는 길이가 1 이상 10 이하인 알파벳 대문자로만 구성된 문자열입니다.
입출력 예
gems	result
["DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"]	[3, 7]
["AA", "AB", "AC", "AA", "AC"]	[1, 3]
["XYZ", "XYZ", "XYZ"]	[1, 1]
["ZZZ", "YYY", "NNNN", "YYY", "BBB"]	[1, 5]
입출력 예에 대한 설명
입출력 예 #1
문제 예시와 같습니다.

입출력 예 #2
3종류의 보석(AA, AB, AC)을 모두 포함하는 가장 짧은 구간은 [1, 3], [2, 4]가 있습니다.
시작 진열대 번호가 더 작은 [1, 3]을 return 해주어야 합니다.

입출력 예 #3
1종류의 보석(XYZ)을 포함하는 가장 짧은 구간은 [1, 1], [2, 2], [3, 3]이 있습니다.
시작 진열대 번호가 가장 작은 [1, 1]을 return 해주어야 합니다.

입출력 예 #4
4종류의 보석(ZZZ, YYY, NNNN, BBB)을 모두 포함하는 구간은 [1, 5]가 유일합니다.
그러므로 [1, 5]를 return 해주어야 합니다.

※ 공지 - 2020년 7월 21일 테스트케이스가 추가되었습니다.
 */
public class 보석_쇼핑 {
    static void main() {
        String[] gems = new String[] {
                //"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"
                "AA", "AB", "AC", "AA", "AC"
        };

        Solve task = new Solve();
        System.out.println(Arrays.toString(task.solution(gems)));
    }

    private static class Solve {
        private int[] ans;
        private int gems_kind;

        public int[] solution(String[] gems) {
            init_setting(gems);

            shopping_gems(gems, gems_kind);

            return ans;
        }

        private void shopping_gems(String[] gems, int gems_kind) {
            int low = 0, high = 0, mlow = 0, mhigh = Integer.MAX_VALUE;
            HashMap<String, Integer> gems_maps = new HashMap<>();

            while(low <= high && high < gems.length) {
                if(gems_maps.containsKey(gems[high])) {
                    gems_maps.put(gems[high], gems_maps.get(gems[high]) + 1);
                } else {
                    gems_maps.put(gems[high], 1);
                }

                if(gems_maps.size() == gems_kind) {
                    if(high - low < mhigh - mlow || (high - low == mhigh - mlow) && low < mlow) {
                        mlow = low;
                        mhigh = high;
                    }

                    if(gems_maps.get(gems[low]) == 1) {
                        gems_maps.remove(gems[low]);
                    } else {
                        gems_maps.put(gems[low],gems_maps.get(gems[low]) - 1);
                    }

                    low++;
                } else {
                    high++;
                }
            }

            ans[0] = mlow + 1;
            ans[1] = mhigh + 1;
        }

        private void init_setting(String[] gems) {
            ans = new int[2];

            ans[0] = 0;
            ans[1] = Integer.MAX_VALUE;

            gems_kind = Math.toIntExact(Arrays.stream(gems)
                    .distinct().count());
        }
    }
}
