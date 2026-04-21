package Lv3;

/*
정수 삼각형
제출 내역
문제 설명
    7
   3 8
  8 1 0
 2 7 4 4
4 5 2 6 5

위와 같은 삼각형의 꼭대기에서 바닥까지 이어지는 경로 중, 거쳐간 숫자의 합이 가장 큰 경우를 찾아보려고 합니다. 아래 칸으로 이동할 때는 대각선 방향으로 한 칸 오른쪽 또는 왼쪽으로만 이동 가능합니다. 예를 들어 3에서는 그 아래칸의 8 또는 1로만 이동이 가능합니다.

삼각형의 정보가 담긴 배열 triangle이 매개변수로 주어질 때, 거쳐간 숫자의 최댓값을 return 하도록 solution 함수를 완성하세요.

제한사항
삼각형의 높이는 1 이상 500 이하입니다.
삼각형을 이루고 있는 숫자는 0 이상 9,999 이하의 정수입니다.
입출력 예
triangle	result
[[7], [3, 8], [8, 1, 0], [2, 7, 4, 4], [4, 5, 2, 6, 5]]	30
 */
/*
알고리즘 핵심
동적 계획법
1. 최대값을 갖는 경로를 찾으면 되기 때문에 재귀호출을 이용한다.
2. 탐색하는 방법은 입력으로 주어진 2차배열에서 트리의 형태는 [i][j] - [i + 1][j], [i + 1][j + 1]이므로 두 경로로 탐색을 진행한다.
3. 2번 과정에서 [i][j] + max(1번 경로, 2번 경로)인 값으로 dp를 업데이트하여, 중복 과정을 생략하여 최대값을 갖는 경로를 찾는다.
 */
public class 정수_삼각형 {
    public static void main() {
        int[][] input = {
                {7},{3,8},{8,1,0},{2,7,4,4},{4,5,2,6,5}
        };

        Solve task = new Solve();
        System.out.println(task.solution(input));
    }

    private static class Solve {
        private int[][] triangle,dp;
        private int ans;

        public int solution(int[][] triangle) {
            init_setting(triangle);

            ans = check(triangle,0,0);
            check2(triangle);

            return ans;
        }

        // bottom-up
        private int check(int[][] triangle, int h, int i) {
            if(h == triangle.length - 1) return triangle[h][i];
            if(dp[h][i] != -1) return dp[h][i];

            dp[h][i] = triangle[h][i] + Math.max(check(triangle,h + 1, i),check(triangle,h + 1, i + 1));

            return dp[h][i];
        }

        // top-down
        private void check2(int[][] triangle) {
            dp[0][0] = triangle[0][0];

            for(int i = 1; i < triangle.length; i++) {
                for(int j = 0; j < triangle[i].length; j++) {
                    dp[i][j] = triangle[i][j] + (j == 0 ? dp[i - 1][j] :
                            Math.max(dp[i - 1][j - 1], dp[i - 1][j]));
                }
            }

            for(int i = 0; i < triangle.length; i++) {
                ans = Math.max(ans, dp[triangle.length - 1][i]);
            }
        }

        private void init_setting(int[][] triangle) {
            dp = new int[triangle.length][triangle.length];

            for(int i = 0; i < triangle.length; i++) {
                for(int j = 0; j < i + 1; j++) {
                    dp[i][j] = -1;
                }
            }

            ans = 0;
        }
    }
}
