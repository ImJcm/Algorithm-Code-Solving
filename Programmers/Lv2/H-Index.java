package Programmers;

import java.util.Arrays;
import java.util.Collections;

/*
H-Index
제출 내역
문제 설명
H-Index는 과학자의 생산성과 영향력을 나타내는 지표입니다. 어느 과학자의 H-Index를 나타내는 값인 h를 구하려고 합니다. 위키백과1에 따르면, H-Index는 다음과 같이 구합니다.

어떤 과학자가 발표한 논문 n편 중, h번 이상 인용된 논문이 h편 이상이고 나머지 논문이 h번 이하 인용되었다면 h의 최댓값이 이 과학자의 H-Index입니다.

어떤 과학자가 발표한 논문의 인용 횟수를 담은 배열 citations가 매개변수로 주어질 때, 이 과학자의 H-Index를 return 하도록 solution 함수를 작성해주세요.

제한사항
과학자가 발표한 논문의 수는 1편 이상 1,000편 이하입니다.
논문별 인용 횟수는 0회 이상 10,000회 이하입니다.
입출력 예
citations	return
[3, 0, 6, 1, 5]	3
입출력 예 설명
이 과학자가 발표한 논문의 수는 5편이고, 그중 3편의 논문은 3회 이상 인용되었습니다. 그리고 나머지 2편의 논문은 3회 이하 인용되었기 때문에 이 과학자의 H-Index는 3입니다.

문제가 잘 안풀린다면😢
힌트가 필요한가요? [코딩테스트 연습 힌트 모음집]으로 오세요! → 클릭

※ 공지 - 2019년 2월 28일 테스트 케이스가 추가되었습니다.
 */
/*
방법1. 모든 경우의 수를 검사하는 DFS를 수행하여 최대 H-Index를 출력
ㄴ 시간초과 발생할 것으로 예상
방법2. citations를 오름차순 정렬 후, citations.length-2부터 시작하여 citations.length-1의 h값을 비교하여
[l-2] <= [l-1]이면, 인용된 논문으로 판단하고 인덱스 위치로 부터 인용된 논문의 수를 누적할 수 있다.
ㄴ 반복문을 통한 index의 값으로 인용된 논문의 수를 알 수 있고, 이 최대값은 자연스럽게 인용되지 않는 논문이 index이하를 만족한다.
또한, [l-2]에 해당하는 인덱스는 인용되지 않는 논문의 수로 인식할 수 있다.
(오름차순 or 내림차순 적용하여 결과 도출 가능)

https://en.wikipedia.org/wiki/H-index
h-index (f) = max{i ∈ N : f(i) >= i}

 */
class Solution {
    static int h_index = 0;

    public static int solution(int[] citations) {
        int answer = 0;

        Integer[] new_citations = Arrays.stream(citations)
                .boxed()
                .toArray(Integer[]::new);

        Arrays.sort(new_citations, Collections.reverseOrder());

        for(int i=0;i<new_citations.length;i++) {
            if(new_citations[i] >= (i + 1)) {
                h_index = Math.max(h_index, i + 1);
            }
        }

        answer = h_index;

        return answer;
    }

    public static void main(String[] args) {
        int[] input = {3,0,6,1,5};
        System.out.println(solution(input));
    }
}
