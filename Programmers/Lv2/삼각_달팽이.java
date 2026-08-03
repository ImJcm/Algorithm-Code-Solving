package Lv2;

import java.util.Arrays;

/*
삼각 달팽이
제출 내역
문제 설명
정수 n이 매개변수로 주어집니다. 다음 그림과 같이 밑변의 길이와 높이가 n인 삼각형에서 맨 위 꼭짓점부터 반시계 방향으로 달팽이 채우기를 진행한 후, 첫 행부터 마지막 행까지 모두 순서대로 합친 새로운 배열을 return 하도록 solution 함수를 완성해주세요.

examples.png

제한사항
n은 1 이상 1,000 이하입니다.
입출력 예
n	result
4	[1,2,9,3,10,8,4,5,6,7]
5	[1,2,12,3,13,11,4,14,15,10,5,6,7,8,9]
6	[1,2,15,3,16,14,4,17,21,13,5,18,19,20,12,6,7,8,9,10,11]
입출력 예 설명
입출력 예 #1

문제 예시와 같습니다.
입출력 예 #2

문제 예시와 같습니다.
입출력 예 #3

문제 예시와 같습니다.
 */
/*

 */
public class 삼각_달팽이 {
    static void main() {
        int[] n = new int[] {
                1,2,4,5,6,7,8,9
        };

        Solve task = new Solve();
        System.out.println(Arrays.toString(task.solution(n[7])));
    }

    private static class Another_Solve {
        private int[] ans;
        private int[][] snail;
        private int[][] d = new int[][] {
                {1,0}, {0,1}, {-1,-1}
        };

        public int[] solution(int n) {
            init_setting(n);

            make_triangle_snail(n);

            return ans;
        }

        private void make_triangle_snail(int n) {
            if(n == 1) ans = new int[] {1};
            else {
                int x = 0, y = 0, dir = 0, num = 1;

                while(snail[x][y] == 0) {
                    snail[x][y] = num++;

                    int nx = x + d[dir][0];
                    int ny = y + d[dir][1];

                    if(nx < 0 || nx > n || ny < 0 || ny > n || snail[nx][ny] != 0) {
                        dir = (dir + 1) % 3;
                        x += d[dir][0];
                        y += d[dir][1];
                    } else {
                        x += nx;
                        y += ny;
                    }
                }

                ans = Arrays.stream(snail)              // int[][] -> Stream<int[]>
                        .flatMapToInt(Arrays::stream)   // int[] -> IntStream 평탄화
                        .filter(i -> i != 0)        // filter : element중 0이 아닌 것만 고르기
                        .toArray();                     // Array -> return int[]
            }
        }

        private void init_setting(int n) {
            ans = new int[n * (n + 1) / 2];
            snail = new int[n][n];
        }
    }


    private static class Solve {
        private int L;
        private int[] ans;
        private int[] snail;

        public int[] solution(int n) {
            init_setting(n);

            make_triangle_snail(n);

            return ans;
        }

        private void make_triangle_snail(int n) {
            int num = 1, w = 0, c = 1;
            int l_idx, m_idx, r_idx = 0;

            while (true) {
                if(num > L) break;
                // left side
                l_idx = c == 1 ? 0 : (r_idx + w + 1);
                w = c;
                l_idx += w;
                while (snail[l_idx] == 0) {
                    snail[l_idx] = num++;
                    l_idx += (w++ + c - 1);
                }

                // bottom
                m_idx = l_idx - (w + c - 1) + 2;
                while (snail[m_idx] == 0) {
                    snail[m_idx++] = num++;
                }

                // right side
                r_idx = (m_idx - 1) - (n - c + 1);
                w = n - 1;
                while (snail[r_idx] == 0) {
                    snail[r_idx] = num++;
                    r_idx -= (w-- - c + 1);
                }
                c++;
            }

            ans = Arrays.stream(snail, 1, snail.length - 1)
                    .toArray();
        }

        private void init_setting(int n) {
            L = permutaion_sum(n);
            ans = new int[L];
            snail = new int[L + 2];

            snail[0] = snail[L + 1] = -1;
        }

        private int permutaion_sum(int n) {
            return n * (n + 1) / 2;
        }

        private void testPrint(int n) {
            int l = 1;
            for(int i = 1; i <= n; i++) {
                System.out.printf(" ".repeat((n - i)));
                for(int j = 0; j < i; j++) {
                    if(snail[l] > 9) System.out.printf("%d ",snail[l++]);
                    else System.out.printf("%d  ", snail[l++]);
                }
                System.out.println();
            }
        }
    }
    // 1 2  3 4  5  6 7  8  9 10 11 12 13 14 15
    // 1 2 12 3 13 11 4 14 15 10  5  6  7  8  9
    //      o    o  o    o  o  o

    // 1 2  3 4  5  6 7  8  9 10 11 12 13 14 15 16 17 18 19 20 21
    // 1 2 15 3 16 14 4 17 21 13  5 18 19 20 12  6  7  8  9 10 11
    //      o    o  o    o  o  o     o  o  o
}
