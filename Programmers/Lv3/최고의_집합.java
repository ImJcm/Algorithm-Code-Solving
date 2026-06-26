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
/*
알고리즘 핵심
구현
1. 문제에서 주어지는 요구사항으로 s,n이 주어질 때, 최고의 집합은 오름차순으로 정렬된 요소들의 합이 s를 만족하며, 모든 곱이 최대이어야 하므로
각 요소들 간의 차이가 최소이어여 한다.
2. (s / n)의 값이 요소의 최소 요소이고, (s % n)의 값이 각 요소에서 +1할 수 있는 횟수이다.
이유 : n = 2, s = 9) (s / n) = 4, (s % n) = 1 => [4,4] -> [4,5] 오름차순의 조건을 만족해야 하므로, 마지막 요소 증가
n = 3, s = 14) (s / n) = 4, (s % n) = 2 => [4,4,4] -> [4,5,5]
이때, (s % n)의 값을 마지막 요소에 모두 더하는 경우가 크지 않는가?
=> (n = 3, s = 14) [4,5,5] vs [4,4,6] = 100 vs 96
=> (n = 4, s = 19) [4,5,5,5] vs [4,4,4,7] vs [4,4,5,6] = 500 vs 448 vs 480
즉, 요소간의 차이가 적을수록 곱은 크다.
 */
public class 최고의_집합 {
    static void main() {
        int n = 2;
        int s = 8;

        Solve task = new Solve();
        System.out.println(Arrays.toString(task.solution(n,s)));
    }

    private static class Solve {
        private int minimum,up_cnt;
        private int[] ans;

        public int[] solution(int n, int s) {
            init_setting(n,s);

            make_set(n, minimum, up_cnt);

            return ans;
        }

        private void make_set(int n, int mini, int uc) {
            if(mini < 1) ans = new int[] {-1};
            else {
                for(int i = 0; i < n; i++) {
                    if(i >= n - uc) ans[i] = mini + 1;
                    else ans[i] = mini;
                }
            }
        }

        private void init_setting(int n, int s) {
            ans = new int[n];

            minimum = (s / n);
            up_cnt = (s % n);
        }
    }

    /*
        wrong solve : time out
     */
    private static class Wrong_Solve {
        private long result;
        private int N;
        private int[] ans,set;

        public int[] solution(int n, int s) {
            init_setting(n,s);

            make_set(0,s,1,0,set);

            return result == -1 ? new int[] {-1} : ans;
        }

        private void make_set(int n, int s, int p, int ps, int[] set) {
            if(n == N) {
                if(s == ps) {
                    long res = 1;
                    for(int i = 0; i < set.length; i++) {
                        res *= set[i];
                    }

                    if(res > result) {
                        result = res;
                        ans = Arrays.copyOf(set, set.length);
                    }
                }
                return;
            }

            for(int i = p; i <= s - ps; i++) {
                if(i + ps > s) break;

                set[n] = i;
                make_set(n + 1, s, i, i + ps, set);
            }
        }

        private void init_setting(int n, int s) {
            result = -1;
            ans = new int[n];
            set = new int[n];

            N = n;
        }
    }
}
