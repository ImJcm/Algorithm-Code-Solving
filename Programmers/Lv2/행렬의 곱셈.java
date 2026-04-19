package Programmers;

/*
행렬의 곱셈
제출 내역
문제 설명
2차원 행렬 arr1과 arr2를 입력받아, arr1에 arr2를 곱한 결과를 반환하는 함수, solution을 완성해주세요.

제한 조건
행렬 arr1, arr2의 행과 열의 길이는 2 이상 100 이하입니다.
행렬 arr1, arr2의 원소는 -10 이상 20 이하인 자연수입니다.
곱할 수 있는 배열만 주어집니다.
입출력 예
arr1	                            arr2	                            return
[[1, 4], [3, 2], [4, 1]]	        [[3, 3], [3, 3]]	                [[15, 15], [15, 15], [15, 15]]
[[2, 3, 2], [4, 2, 4], [3, 1, 4]]	[[5, 4, 3], [2, 4, 1], [3, 1, 1]]	[[22, 22, 11], [36, 28, 18], [29, 20, 14]]
 */
/*
행렬 곱 계산 방식 = https://namu.wiki/w/%ED%96%89%EB%A0%AC%EA%B3%B1
A 행렬 : M x R / B 행렬 : R x N
연산 횟수 : M x N x R

 */
class Solution {
    public static int[][] solution(int[][] arr1, int[][] arr2) {
        int[][] answer = new int[arr1.length][arr2[0].length];

        for(int r=0;r<arr1.length;r++) {
            for(int c=0;c<arr2[0].length;c++) {
                for(int i=0;i<arr2.length;i++) {
                    answer[r][c] += arr1[r][i] * arr2[i][c];
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        solution(new int[][]{{1,4}, {3,2}, {4,1}}, new int[][]{{3,3}, {3,3}});
    }
}
