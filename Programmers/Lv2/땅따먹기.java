package Lv2;

import java.util.Arrays;

/*
땅따먹기
제출 내역
문제 설명
땅따먹기 게임을 하려고 합니다. 땅따먹기 게임의 땅(land)은 총 N행 4열로 이루어져 있고, 모든 칸에는 점수가 쓰여 있습니다. 1행부터 땅을 밟으며 한 행씩 내려올 때, 각 행의 4칸 중 한 칸만 밟으면서 내려와야 합니다. 단, 땅따먹기 게임에는 한 행씩 내려올 때, 같은 열을 연속해서 밟을 수 없는 특수 규칙이 있습니다.

예를 들면,

| 1 | 2 | 3 | 5 |

| 5 | 6 | 7 | 8 |

| 4 | 3 | 2 | 1 |

로 땅이 주어졌다면, 1행에서 네번째 칸 (5)를 밟았으면, 2행의 네번째 칸 (8)은 밟을 수 없습니다.

마지막 행까지 모두 내려왔을 때, 얻을 수 있는 점수의 최대값을 return하는 solution 함수를 완성해 주세요. 위 예의 경우, 1행의 네번째 칸 (5), 2행의 세번째 칸 (7), 3행의 첫번째 칸 (4) 땅을 밟아 16점이 최고점이 되므로 16을 return 하면 됩니다.

제한사항
행의 개수 N : 100,000 이하의 자연수
열의 개수는 4개이고, 땅(land)은 2차원 배열로 주어집니다.
점수 : 100 이하의 자연수
입출력 예
land	answer
[[1,2,3,5],[5,6,7,8],[4,3,2,1]]	16
입출력 예 설명
입출력 예 #1
문제의 예시와 같습니다.
 */
/*
알고리즘 핵심
DP(동적계획법)
1. 출발점을 시작으로 마지막 위치로 최대값을 갖는 경로를 찾는 것이므로 DFS를 기반으로 구성한다.
2. 출발점이 4개이지만, 4개의 출발점에서 각각 중복되는 경로가 존재하여 불필요한 연산을 줄이기위해 DP를 사용한다.
 */
public class 땅따먹기 {
    static void main() {
        int[][] land = new int[][] {
            {1,2,3,5},
            {5,6,7,8},
            {4,3,2,1}
        };

        Solve task = new Solve();
        System.out.println(task.solution(land));
    }

    private static class Solve {
        private int ans;
        private int[][] dp;

        public int solution(int[][] land) {
            init_setting(land);

            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                    if(i == j) continue;
                    ans = Math.max(ans, land[0][i] + move(1, j, land.length, land));
                }
            }

            return ans;
        }

        private int move(int n, int l, int end, int[][] land) {
            if(n == end) return 0;

            if(dp[n][l] != 0) return dp[n][l];

            for(int i = 0; i < 4; i++) {
                if(i == l) continue;

                dp[n][l] = Math.max(dp[n][l], land[n][l] + move(n + 1, i, end, land));
            }

            return dp[n][l];
        }

        private void init_setting(int[][] land) {
            ans = 0;

            dp = new int[land.length][4];
        }
    }
}
