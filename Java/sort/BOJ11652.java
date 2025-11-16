package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/*
카드

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	54283	16697	12658	30.436%
문제
준규는 숫자 카드 N장을 가지고 있다. 숫자 카드에는 정수가 하나 적혀있는데, 적혀있는 수는 -262보다 크거나 같고, 262보다 작거나 같다.

준규가 가지고 있는 카드가 주어졌을 때, 가장 많이 가지고 있는 정수를 구하는 프로그램을 작성하시오. 만약, 가장 많이 가지고 있는 정수가 여러 가지라면, 작은 것을 출력한다.

입력
첫째 줄에 준규가 가지고 있는 숫자 카드의 개수 N (1 ≤ N ≤ 100,000)이 주어진다. 둘째 줄부터 N개 줄에는 숫자 카드에 적혀있는 정수가 주어진다.

출력
첫째 줄에 준규가 가장 많이 가지고 있는 정수를 출력한다.

예제 입력 1
5
1
2
1
2
1
예제 출력 1
1
예제 입력 2
6
1
2
1
2
1
2
예제 출력 2
1
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: djm03178, hky
알고리즘 분류
자료 구조
정렬
집합과 맵
해시를 사용한 집합과 맵
 */
/*
알고리즘 핵심
집합과 맵 + 정렬
1. <Long,Integer> 타입을 갖는 Map 자료구조를 사용하여 입력으로 주어지는 카드의 정수를 Key, 카드의 개수를 Value로 저장한다.
2. Value 값을 기준으로 내림차순 정렬하고, 같은 경우 Key값이 작은 순으로 오름차순정렬한다.
3. 정렬이 적용된 자료구조의 맨 앞 요소를 출력한다.

이때, 중요한 점은 정렬 기준을 설정할 때 사칙연산을 통해 정렬 기준을 잡을 경우 연산의 결과가 타입의 범위를 넘어가는 경우가 있으므로
비교연산 또는 Object.compare() or Object.compareTo() 메서드를 활용하는 것을 추천한다.
 */
public class BOJ11652 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N;
        long ans;
        HashMap<Long,Integer> cards;
        ArrayList<Long> keySet;

        public void solve() throws IOException {
            init_setting();

            card_check();

            System.out.println(ans);
        }

        private void card_check() {
            keySet.sort(new Comparator<Long>() {
                @Override
                public int compare(Long o1, Long o2) {
                    int diff = cards.get(o2).compareTo(cards.get(o1));   // reverseOrder
                    int k_diff = Long.compare(o1,o2);   // Order
                    //int wrong_k_diff = o1 - o2 > 0 ? 1 : o1 - o2 == 0 ? 0 : -1; // wrong section : o1 - o2의 연산의 결과가 타입의 범위를 넘어가는 수인 경우 잘못된 값으로 정렬기준이 달라진다.
                    //int k_diff = o1 > o2 ? 1 : o1 == o2 ? 0 : -1; // == Long.compare(o1,o2) or o1.compareTo(o2);

                    if(diff == 0) return k_diff;
                    else return diff;
                }
            });

            //ans = keySet.getFirst();
            ans = keySet.get(0);
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            cards = new HashMap<>();

            for(int i = 0; i < N; i++) {
                long n = Long.parseLong(br.readLine());

                if(!cards.containsKey(n)) cards.put(n,1);
                else cards.replace(n, cards.get(n) + 1);
            }

            ans = 0;

            keySet = new ArrayList<>(cards.keySet());
        }
    }
}
