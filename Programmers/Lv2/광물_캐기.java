package Lv2;

import java.util.ArrayList;
import java.util.Collections;

/*
광물 캐기
문제 설명
마인은 곡괭이로 광산에서 광석을 캐려고 합니다. 마인은 다이아몬드 곡괭이, 철 곡괭이, 돌 곡괭이를 각각 0개에서 5개까지 가지고 있으며, 곡괭이로 광물을 캘 때는 피로도가 소모됩니다. 각 곡괭이로 광물을 캘 때의 피로도는 아래 표와 같습니다.

image

예를 들어, 철 곡괭이는 다이아몬드를 캘 때 피로도 5가 소모되며, 철과 돌을 캘때는 피로도가 1씩 소모됩니다. 각 곡괭이는 종류에 상관없이 광물 5개를 캔 후에는 더 이상 사용할 수 없습니다.

마인은 다음과 같은 규칙을 지키면서 최소한의 피로도로 광물을 캐려고 합니다.

사용할 수 있는 곡괭이중 아무거나 하나를 선택해 광물을 캡니다.
한 번 사용하기 시작한 곡괭이는 사용할 수 없을 때까지 사용합니다.
광물은 주어진 순서대로만 캘 수 있습니다.
광산에 있는 모든 광물을 캐거나, 더 사용할 곡괭이가 없을 때까지 광물을 캡니다.
즉, 곡괭이를 하나 선택해서 광물 5개를 연속으로 캐고, 다음 곡괭이를 선택해서 광물 5개를 연속으로 캐는 과정을 반복하며, 더 사용할 곡괭이가 없거나 광산에 있는 모든 광물을 캘 때까지 과정을 반복하면 됩니다.

마인이 갖고 있는 곡괭이의 개수를 나타내는 정수 배열 picks와 광물들의 순서를 나타내는 문자열 배열 minerals가 매개변수로 주어질 때, 마인이 작업을 끝내기까지 필요한 최소한의 피로도를 return 하는 solution 함수를 완성해주세요.

제한사항
picks는 [dia, iron, stone]과 같은 구조로 이루어져 있습니다.
0 ≤ dia, iron, stone ≤ 5
dia는 다이아몬드 곡괭이의 수를 의미합니다.
iron은 철 곡괭이의 수를 의미합니다.
stone은 돌 곡괭이의 수를 의미합니다.
곡괭이는 최소 1개 이상 가지고 있습니다.
5 ≤ minerals의 길이 ≤ 50
minerals는 다음 3개의 문자열로 이루어져 있으며 각각의 의미는 다음과 같습니다.
diamond : 다이아몬드
iron : 철
stone : 돌
입출력 예
picks	minerals	result
[1, 3, 2]	["diamond", "diamond", "diamond", "iron", "iron", "diamond", "iron", "stone"]	12
[0, 1, 1]	["diamond", "diamond", "diamond", "diamond", "diamond", "iron", "iron", "iron", "iron", "iron", "diamond"]	50
입출력 예 설명
입출력 예 #1

다이아몬드 곡괭이로 앞에 다섯 광물을 캐고 철 곡괭이로 남은 다이아몬드, 철, 돌을 1개씩 캐면 12(1 + 1 + 1 + 1+ 1 + 5 + 1 + 1)의 피로도로 캘 수 있으며 이때가 최소값입니다.

입출력 예 #2

철 곡괭이로 다이아몬드 5개를 캐고 돌 곡괭이고 철 5개를 캐면 50의 피로도로 캘 수 있으며, 이때가 최소값입니다.
 */
/*
알고리즘 핵심
Greedy or Exhaustive Search
Greedy
1. 광물들을 다이아 : 100, 철 : 10, 돌 : 1만큼 가중치를 두어 5개씩 끊어서 그룹화하여 가중치의 합을 곡갱이의 개수만큼만 저장한다.
2. 가중치를 기준으로 내림차순으로 정렬하고, 가중치가 큰 그룹을 순차적으로 다이아, 철, 돌 곡갱이 순으로 배정하여 최소 피로도을 구한다.

Exhaustive Search
1. 곡갱이를 각각 5개의 광물에 모두 배정하여 가능한 모든 경우를 탐색하여 피로도를 누적하여 기저사례에 도달 시, 누적된 피로도의 최소를 저장한다.
 */
public class 광물_캐기 {
    static void main() {
        int[] picks = new int[] {
                //1, 3, 2
                //0, 1, 1
                1,1,0
        };

        String[] minerals = new String[] {
                //"diamond", "diamond", "diamond", "iron", "iron", "diamond", "iron", "stone"
                //"diamond", "diamond", "diamond", "diamond", "diamond", "iron", "iron", "iron", "iron", "iron", "diamond"
                "iron", "iron", "iron", "iron", "diamond", "diamond", "diamond", "diamond", "diamond", "diamond"
        };

        /*Solve task = new Solve();
        System.out.println(task.solution(picks, minerals));*/

        Solve2 task2 = new Solve2();
        System.out.println(task2.solution(picks, minerals));
    }

    /*
        Solve : Greedy
     */
    private static class Solve {
        private int ans;
        private ArrayList<Integer> fatigue_level;

        public int solution(int[] picks, String[] minerals) {
            init_setting();

            fatigue_handling(picks, minerals);

            pickaxe_distribution(picks);

            return ans;
        }

        private void pickaxe_distribution(int[] picks) {
            for(int i = 0, j = 0; i < fatigue_level.size() || j >= picks.length;) {
                if(picks[j] <= 0) {
                    j++;
                    continue;
                }
                picks[j]--;

                int diamond = fatigue_level.get(i) / 100;
                int iron = fatigue_level.get(i) % 100 / 10;
                int stone = fatigue_level.get(i) % 10;

                int fatigue = 0;

                switch (j) {
                    case 0: // diamond pickaxe
                        fatigue = diamond + iron + stone;
                        break;
                    case 1: // iron pickaxe
                        fatigue = 5 * diamond + iron + stone;
                        break;
                    case 2: // stone pickaxe
                        fatigue = 25 * diamond + 5 * iron + stone;
                        break;
                }

                ans += fatigue;
                i++;
            }
        }

        private void fatigue_handling(int[] picks, String[] minerals) {
            int fatigue = 0;
            for(int i = 0; i < minerals.length; i++) {
                if(i >= (picks[0] + picks[1] + picks[2]) * 5) break;

                fatigue += minerals[i].equals("diamond") ? 100 :
                    minerals[i].equals("iron") ? 10 :
                    minerals[i].equals("stone") ? 1 : 0;

                if((i + 1) % 5 == 0 || (i + 1) == minerals.length) {
                    fatigue_level.add(fatigue);
                    fatigue = 0;
                }
            }

            fatigue_level.sort(Collections.reverseOrder());
        }

        private void init_setting() {
            ans = 0;

            fatigue_level = new ArrayList<>();
        }
    }

    /*
        Solve : Exhaustive Search
     */
    private static class Solve2 {
        private int ans;

        public int solution(int[] picks, String[] minerals) {
            init_setting();

            mining_minerals(0, picks, minerals, 0);

            return ans;
        }

        private void mining_minerals(int i, int[] picks, String[] minerals, int fatigue) {
            if(i >= minerals.length || (picks[0] == 0 && picks[1] == 0 && picks[2] == 0)) {
                ans = Math.min(ans, fatigue);
                return;
            }

            int fs = 0;
            int cnt = 0;

            if(picks[0] > 0) {
                fs = 0;
                cnt = 0;
                while(i + cnt < minerals.length && cnt < 5) {
                    fs += 1;
                    cnt++;
                }

                picks[0]--;
                mining_minerals(i + cnt, picks, minerals, fatigue + fs);
                picks[0]++;
            }

            if(picks[1] > 0) {
                fs = 0;
                cnt = 0;
                while(i + cnt < minerals.length && cnt < 5) {
                    fs += minerals[i + cnt].equals("diamond") ? 5 : 1;
                    cnt++;
                }

                picks[1]--;
                mining_minerals(i + cnt, picks, minerals, fatigue + fs);
                picks[1]++;
            }

            if(picks[2] > 0) {
                fs = 0;
                cnt = 0;
                while(i + cnt < minerals.length && cnt < 5) {
                    fs += minerals[i + cnt].equals("diamond") ? 25 :
                            minerals[i + cnt].equals("iron") ? 5 : 1;
                    cnt++;
                }

                picks[2]--;
                mining_minerals(i + cnt, picks, minerals, fatigue + fs);
                picks[2]++;
            }
        }

        private void init_setting() {
            ans = Integer.MAX_VALUE;
        }
    }
}
