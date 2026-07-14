package Lv2;

import java.util.Arrays;

/*
쿼드압축 후 개수 세기
제출 내역
문제 설명
0과 1로 이루어진 2^n x 2^n 크기의 2차원 정수 배열 arr이 있습니다. 당신은 이 arr을 쿼드 트리와 같은 방식으로 압축하고자 합니다. 구체적인 방식은 다음과 같습니다.

당신이 압축하고자 하는 특정 영역을 S라고 정의합니다.
만약 S 내부에 있는 모든 수가 같은 값이라면, S를 해당 수 하나로 압축시킵니다.
그렇지 않다면, S를 정확히 4개의 균일한 정사각형 영역(입출력 예를 참고해주시기 바랍니다.)으로 쪼갠 뒤, 각 정사각형 영역에 대해 같은 방식의 압축을 시도합니다.
arr이 매개변수로 주어집니다. 위와 같은 방식으로 arr을 압축했을 때, 배열에 최종적으로 남는 0의 개수와 1의 개수를 배열에 담아서 return 하도록 solution 함수를 완성해주세요.

제한사항
arr의 행의 개수는 1 이상 1024 이하이며, 2의 거듭 제곱수 형태를 하고 있습니다. 즉, arr의 행의 개수는 1, 2, 4, 8, ..., 1024 중 하나입니다.
arr의 각 행의 길이는 arr의 행의 개수와 같습니다. 즉, arr은 정사각형 배열입니다.
arr의 각 행에 있는 모든 값은 0 또는 1 입니다.
입출력 예
arr	result
[[1,1,0,0},{1,0,0,0},{1,0,0,1},{1,1,1,1]]	{4,9}
[[1,1,1,1,1,1,1,1},{0,1,1,1,1,1,1,1},{0,0,0,0,1,1,1,1},{0,1,0,0,1,1,1,1},{0,0,0,0,0,0,1,1},{0,0,0,0,0,0,0,1},{0,0,0,0,1,0,0,1},{0,0,0,0,1,1,1,1]]	{10,15}
입출력 예 설명
입출력 예 #1

다음 그림은 주어진 arr을 압축하는 과정을 나타낸 것입니다.
ex1.png
최종 압축 결과에 0이 4개, 1이 9개 있으므로, {4,9}를 return 해야 합니다.
입출력 예 #2

다음 그림은 주어진 arr을 압축하는 과정을 나타낸 것입니다.
ex2.png
최종 압축 결과에 0이 10개, 1이 15개 있으므로, {10,15}를 return 해야 합니다.
 */
/*
알고리즘 핵심
백트랙킹
1. 입력으로 주어진 arr의 길이(l)만큼 같은 숫자로 이루어저 있는지 확인한다.
1-a. 모두 같은 숫자라면, 해당하는 숫자의 카운트를 증가시킨다.
1-b. 같은 숫자가 아니라면, l/2만큼의 차이를 둔 4개의 구간으로 분할한다.
2. 1-b의 경우, 4개의 구간에 1번 과정을 반복하여 수행한다.
 */
public class 쿼드압축_후_개수_세기 {
    static void main() {
        int[][] arr = new int[][] {
                {1,1,1,1,1,1,1,1},{0,1,1,1,1,1,1,1},{0,0,0,0,1,1,1,1},{0,1,0,0,1,1,1,1},
                {0,0,0,0,0,0,1,1},{0,0,0,0,0,0,0,1},{0,0,0,0,1,0,0,1},{0,0,0,0,1,1,1,1}
        };

        Solve task = new Solve();
        System.out.println(Arrays.toString(task.solution(arr)));
    }

    private static class Solve {
        private int[] ans;

        public int[] solution(int[][] arr) {
            init_setting();

            quad_compression(arr, 0, 0, arr.length);

            return ans;
        }

        private void quad_compression(int[][] arr, int r, int c, int l) {
            if(!quad_check(arr,r,c,l)) {
                int n = l / 2;
                quad_compression(arr,r, c, n);
                quad_compression(arr,r + n, c, n);
                quad_compression(arr, r, c + n, n);
                quad_compression(arr, r + n, c + n, n);
                return;
            }

            ans[arr[r][c]] += 1;
        }

        private boolean quad_check(int[][] arr, int r, int c, int l) {
            boolean flag = true;
            int target_num = arr[r][c];

            for(int i = 0; i < l && flag; i++) {
                for(int j = 0; j < l && flag; j++) {
                    if(arr[r + i][c + j] != target_num) flag = false;
                }
            }
            return flag;
        }

        private void init_setting() {
            ans = new int[2];
        }
    }
}
