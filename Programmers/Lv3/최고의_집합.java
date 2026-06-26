package Lv3;

import java.util.Arrays;

/*
최고의 집합
제출 내역
문제 설명
자연수 n 개로 이루어진 중복 집합(multi set, 편의상 이후에는 "집합"으로 통칭) 중에 다음 두 조건을 만족하는 집합을 최고의 집합이라고 합니다.

각 원소의 합이 S가 되는 수의 집합
위 조건을 만족하면서 각 원소의 곱 이 최대가 되는 집합
예를 들어서 자연수 2개로 이루어진 집합 중 합이 9가 되는 집합은 다음과 같이 4개가 있습니다.
{ 1, 8 }, { 2, 7 }, { 3, 6 }, { 4, 5 }
그중 각 원소의 곱이 최대인 { 4, 5 }가 최고의 집합입니다.

집합의 원소의 개수 n과 모든 원소들의 합 s가 매개변수로 주어질 때, 최고의 집합을 return 하는 solution 함수를 완성해주세요.

제한사항
최고의 집합은 오름차순으로 정렬된 1차원 배열(list, vector) 로 return 해주세요.
만약 최고의 집합이 존재하지 않는 경우에 크기가 1인 1차원 배열(list, vector) 에 -1 을 채워서 return 해주세요.
자연수의 개수 n은 1 이상 10,000 이하의 자연수입니다.
모든 원소들의 합 s는 1 이상, 100,000,000 이하의 자연수입니다.
입출력 예
n	s	result
2	9	[4, 5]
2	1	[-1]
2	8	[4, 4]
입출력 예 설명
입출력 예#1
문제의 예시와 같습니다.

입출력 예#2
자연수 2개를 가지고는 합이 1인 집합을 만들 수 없습니다. 따라서 -1이 들어있는 배열을 반환합니다.

입출력 예#3
자연수 2개로 이루어진 집합 중 원소의 합이 8인 집합은 다음과 같습니다.

{ 1, 7 }, { 2, 6 }, { 3, 5 }, { 4, 4 }

그중 각 원소의 곱이 최대인 { 4, 4 }가 최고의 집합입니다.
 */
public class 최고의_집합 {
    static void main() {
        int n = 2;
        int s = 9;

        Solve task = new Solve();
        System.out.println(Arrays.toString(task.solution(n,s)));
    }

    private static class Solve {
        private long result;
        private int N;
        private int[] ans;

        public int[] solution(int n, int s) {
            init_setting(n);

            make_set(n,s,0,1);

            return ans;
        }

        private void make_set(int n, int s, int p, int ps) {
            if(n == N) {

            }
        }

        private void init_setting(int n) {
            result = 0;
            ans = new int[n];

            N = n;
        }
    }
}
