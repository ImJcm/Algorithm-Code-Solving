package Lv3;

/*
스티커 모으기(2)
제출 내역
문제 설명
N개의 스티커가 원형으로 연결되어 있습니다. 다음 그림은 N = 8인 경우의 예시입니다.
스티커_hb1jty.jpg
원형으로 연결된 스티커에서 몇 장의 스티커를 뜯어내어 뜯어낸 스티커에 적힌 숫자의 합이 최대가 되도록 하고 싶습니다. 단 스티커 한 장을 뜯어내면 양쪽으로 인접해있는 스티커는 찢어져서 사용할 수 없게 됩니다.

예를 들어 위 그림에서 14가 적힌 스티커를 뜯으면 인접해있는 10, 6이 적힌 스티커는 사용할 수 없습니다. 스티커에 적힌 숫자가 배열 형태로 주어질 때, 스티커를 뜯어내어 얻을 수 있는 숫자의 합의 최댓값을 return 하는 solution 함수를 완성해 주세요. 원형의 스티커 모양을 위해 배열의 첫 번째 원소와 마지막 원소가 서로 연결되어 있다고 간주합니다.

제한 사항
sticker는 원형으로 연결된 스티커의 각 칸에 적힌 숫자가 순서대로 들어있는 배열로, 길이(N)는 1 이상 100,000 이하입니다.
sticker의 각 원소는 스티커의 각 칸에 적힌 숫자이며, 각 칸에 적힌 숫자는 1 이상 100 이하의 자연수입니다.
원형의 스티커 모양을 위해 sticker 배열의 첫 번째 원소와 마지막 원소가 서로 연결되어있다고 간주합니다.
입출력 예
sticker	answer
[14, 6, 5, 11, 3, 9, 2, 10]	36
[1, 3, 2, 5, 4]	8
입출력 예 설명
입출력 예 #1
6, 11, 9, 10이 적힌 스티커를 떼어 냈을 때 36으로 최대가 됩니다.

입출력 예 #2
3, 5가 적힌 스티커를 떼어 냈을 때 8로 최대가 됩니다.
 */
/*
알고리즘 핵심
DP
1. 첫번째 스티커를 선택하는지 여부에 따라 이후 행동을 결정할 수 있다.
2. 이전의 스티커에 대한 행동을 선택하고 난 이후 행동은 2가지로 나뉜다.
2-1. i번째 스티커를 땐 경우, i + 2번째 스티커부터 행동을 결정할 수 있다.
2-2. i번째 스티커를 때지 않은 경우, i + 1번째 스티커부터 행동을 결정할 수 있다.
3. i번째 스티커에 대한 행동이후, 다음 스티커 위치에서 동일한 과정을 수행하므로 중복되는 과정을 수행하여 dp를 적용할 수 있다.
4. 따라서, 첫번째 스티커를 땐 경우와 때지 않은 경우 2가지에 대한 dp를 설정하여 남은 스티커에서 최대값을 갖는 경우를 만든다.

Wrong solve : 재귀함수를 통한 dp로 시간초과가 발생할 수 있다.
따라서, 반복문 + dp로 적용하여 시간초과를 해결한다.
 */
public class 스티커_모으기_2 {
    static void main() {
        int[] sticker = new int[] {
                //14, 6, 5, 11, 3, 9, 2, 10
                //1,3,2,5,4
                1,2
        };

        Solve task = new Solve();
        System.out.println(task.solution(sticker));
    }

    private static class Solve {
        private int ans, length;
        private int[][] dp;

        public int solution(int[] sticker) {
            init_setting(sticker);

            removing_sticker(sticker);

            return ans;
        }

        private void removing_sticker(int[] sticker) {
            if(length == 1) {
                ans = sticker[0];
                return;
            }

            for(int i = 2; i < length - 1; i++) {
                dp[0][i] = Math.max(dp[0][i - 2] + sticker[i], dp[0][i - 1]);
            }

            for(int i = 2; i < length; i++) {
                dp[1][i] = Math.max(dp[1][i - 1], dp[1][i - 2] + sticker[i]);
            }

            ans = Math.max(dp[0][length - 2], dp[1][length - 1]);

            return;
        }

        private void init_setting(int[] sticker) {
            ans = 0;
            length = sticker.length;

            dp = new int[2][length + 1];

            dp[0][0] = dp[0][1] = sticker[0];
            dp[1][0] = 0;
            dp[1][1] = sticker[1];
        }
    }

    /*
        Wrong solve : 효율성 테스트 실패
        재귀형태 + DP로 인해 시간초과 발생
     */
    private static class WrongSolve {
        private int ans, length;
        private int[][] dp;

        public int solution(int[] sticker) {
            init_setting(sticker);

            ans = Math.max(removing_sticker(2, 0, sticker) + sticker[0], removing_sticker(1, 1, sticker));
            
            return ans;
        }

        private int removing_sticker(int stickerIndex, int removeFirstSticker, int[] sticker) {
            if(stickerIndex > length - 2 + removeFirstSticker) return 0;

            if(dp[removeFirstSticker][stickerIndex] != 0) return dp[removeFirstSticker][stickerIndex];

            return dp[removeFirstSticker][stickerIndex] = Math.max(removing_sticker(stickerIndex + 2, removeFirstSticker, sticker) + sticker[stickerIndex],
                    removing_sticker(stickerIndex + 1, removeFirstSticker, sticker));
            
        }

        private void init_setting(int[] sticker) {
            ans = 0;
            length = sticker.length;
            
            dp = new int[2][sticker.length];
        }
    }
}
