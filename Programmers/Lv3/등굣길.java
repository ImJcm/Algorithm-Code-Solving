package Lv3;

/*
등굣길
제출 내역
문제 설명
계속되는 폭우로 일부 지역이 물에 잠겼습니다. 물에 잠기지 않은 지역을 통해 학교를 가려고 합니다. 집에서 학교까지 가는 길은 m x n 크기의 격자모양으로 나타낼 수 있습니다.

아래 그림은 m = 4, n = 3 인 경우입니다.

H 0 0 0
0 0 0 0
0 0 0 S
(0 : road, H : House, S = School)

가장 왼쪽 위, 즉 집이 있는 곳의 좌표는 (1, 1)로 나타내고 가장 오른쪽 아래, 즉 학교가 있는 곳의 좌표는 (m, n)으로 나타냅니다.

격자의 크기 m, n과 물이 잠긴 지역의 좌표를 담은 2차원 배열 puddles이 매개변수로 주어집니다. 오른쪽과 아래쪽으로만 움직여 집에서 학교까지 갈 수 있는 최단경로의 개수를
1,000,000,007로 나눈 나머지를 return 하도록 solution 함수를 작성해주세요.

제한사항
격자의 크기 m, n은 1 이상 100 이하인 자연수입니다.
m과 n이 모두 1인 경우는 입력으로 주어지지 않습니다.
물에 잠긴 지역은 0개 이상 10개 이하입니다.
집과 학교가 물에 잠긴 경우는 입력으로 주어지지 않습니다.
입출력 예
m	n	puddles	return
4	3	[[2, 2]]	4
입출력 예 설명
이미지 링크 : https://asset.programmers.co.kr/files/ybm/32c67958d5/729216f3-f305-4ad1-b3b0-04c2ba0b379a.png
 */
/*
알고리즘 핵심
DP
1. 기본적인 경로 탐색으로 DFS를 사용하여 (0,0) -> (m,n)으로 가는 경로를 탐색한다.
2. 탐색과정에서 (m,n) 도달 시, 해당 경로는 가능한 경로이므로 return 과정에서 1을 반환하여 이동한 경로에 표시할 수 있도록 한다.
3. 이때 (x,y)의 지점에서의 이동할 수 있는 경로의 수는 (x + 1,y) + (x,y + 1)의 두 지점에서의 이동할 수 있는 경로의 수의 합이된다.
4. 이미 도달한 지점에서의 이동할 수 있는 경로의 수가 존재한다면 해당 경로의 수를 반환하여 중복되는 연산을 줄이고, 물웅덩이인 지점의 경우
-1을 설정하여 이동할 수 없는 지점으로 판단하여 경로를 탐색한다.
 */
public class 등굣길 {
    static void main() {
        int m = 4, n = 3;
        int[][] puddles = new int[][] {
                {2,2}
        };

        Solve task = new Solve();
        System.out.println(task.solution(m,n,puddles));
    }

    private static class Solve {
        private final int limit = 1_000_000_007;
        private int ans;
        private int[][] road, direction = {
                {0,1}, {1,0}
        };

        public int solution(int m, int n, int[][] puddles) {
            init_setting(m,n,puddles);

            ans = move(0,0,m - 1,n - 1,road);

            return ans;
        }

        private int move(int i, int j, int m, int n, int[][] r) {
            if(i == m && j == n) return 1;

            if(r[i][j] != 0) return r[i][j];

            for(int[] d : direction) {
                if((i + d[0] > m || j + d[1] > n) || r[i + d[0]][j + d[1]] == -1) continue;
                r[i][j] += move(i+d[0],j+d[1],m,n,r);
                r[i][j] %= limit;
            }

            return r[i][j];
        }


        private void init_setting(int m, int n, int[][] puddles) {
            ans = 0;

            road = new int[m][n];

            for(int[] p : puddles) {
                int x = p[0] - 1;
                int y = p[1] - 1;
                road[x][y] = -1;
            }
        }
    }
}
