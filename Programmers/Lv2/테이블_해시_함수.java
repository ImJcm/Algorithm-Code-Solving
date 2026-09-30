package Lv2;

import java.util.Arrays;
import java.util.Comparator;

/*
테이블 해시 함수
제출 내역
문제 설명
완호가 관리하는 어떤 데이터베이스의 한 테이블은 모두 정수 타입인 컬럼들로 이루어져 있습니다. 테이블은 2차원 행렬로 표현할 수 있으며 열은 컬럼을 나타내고, 행은 튜플을 나타냅니다.
첫 번째 컬럼은 기본키로서 모든 튜플에 대해 그 값이 중복되지 않도록 보장됩니다. 완호는 이 테이블에 대한 해시 함수를 다음과 같이 정의하였습니다.

해시 함수는 col, row_begin, row_end을 입력으로 받습니다.
테이블의 튜플을 col번째 컬럼의 값을 기준으로 오름차순 정렬을 하되, 만약 그 값이 동일하면 기본키인 첫 번째 컬럼의 값을 기준으로 내림차순 정렬합니다.
정렬된 데이터에서 S_i를 i 번째 행의 튜플에 대해 각 컬럼의 값을 i 로 나눈 나머지들의 합으로 정의합니다.
row_begin ≤ i ≤ row_end 인 모든 S_i를 누적하여 bitwise XOR 한 값을 해시 값으로서 반환합니다.
테이블의 데이터 data와 해시 함수에 대한 입력 col, row_begin, row_end이 주어졌을 때 테이블의 해시 값을 return 하도록 solution 함수를 완성해주세요.

제한 사항
1 ≤ data의 길이 ≤ 2,500
1 ≤ data의 원소의 길이 ≤ 500
1 ≤ data[i][j] ≤ 1,000,000
data[i][j]는 i + 1 번째 튜플의 j + 1 번째 컬럼의 값을 의미합니다.
1 ≤ col ≤ data의 원소의 길이
1 ≤ row_begin ≤ row_end ≤ data의 길이
입출력 예
data	col	row_begin	row_end	result
[[2,2,6],[1,5,10],[4,2,9],[3,8,3]]	2	2	3	4
입출력 예 설명
정해진 방법에 따라 튜플을 정렬하면 {4, 2, 9}, {2, 2, 6}, {1, 5, 10}, {3, 8, 3} 이 됩니다.
S_2 = (2 mod 2) + (2 mod 2) + (6 mod 2) = 0 입니다.
S_3 = (1 mod 3) + (5 mod 3) + (10 mod 3) = 4 입니다.
따라서 해시 값은 S_2 XOR S_ 3 = 4 입니다.
※ 공지 - 2025년 3월 14일 테스트케이스가 추가되었습니다.
 */
/*
알고리즘 핵심
구현 + 정렬
1. col열의 값을 기준으로 정렬하고, 같으면 기본키인 0번째 요소로 정렬을 수행한 배열을 만든다.
2. 정렬된 배열을 row_begin, row_end까지 모든 열의 값을 각 인덱스번호로 나눈 나머지를 누적하여 xor한 값을 ans에 갱신한다.
 */
public class 테이블_해시_함수 {
    static void main() {
        int[][] data = new int[][] {
                {2,2,6},{1,5,10},{4,2,9},{3,8,3}
        };
        int col = 2;
        int row_begin = 2;
        int row_end = 3;

        Solve task = new Solve();
        System.out.println(task.solution(data,col,row_begin,row_end));
    }

    private static class Solve {
        private int ans;
        private int[][] sorted_data;

        public int solution(int[][] data, int col, int row_begin, int row_end) {
            init_setting(data, col);

            table_hash_func(sorted_data, row_begin, row_end);

            return ans;
        }

        private void table_hash_func(int[][] sortedData, int rowBegin, int rowEnd) {
            int hash_data = 0;

            for(int r = rowBegin; r <= rowEnd; r++) {
                int mod_sum = 0;
                for(int c = 0; c < sortedData[r - 1].length; c++) {
                    mod_sum += (sortedData[r - 1][c] % r);
                }

                hash_data ^= mod_sum;
            }

            ans = hash_data;
        }

        private void init_setting(int[][] data, int col) {
            ans = 0;

            sorted_data = Arrays.stream(data)
                    .sorted(new Comparator<int[]>() {
                        @Override
                        public int compare(int[] o1, int[] o2) {
                            if(o1[col - 1] - o2[col - 1] == 0) {
                                return o2[0] - o1[0];
                            } else return o1[col - 1] - o2[col - 1];
                        }
                    })
                    .toArray(int[][]::new);

        }
    }
}
