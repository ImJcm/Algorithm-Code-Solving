package Lv3;

import java.util.Arrays;
import java.util.Comparator;

/*
숫자 게임
제출 내역
문제 설명
xx 회사의 2xN명의 사원들은 N명씩 두 팀으로 나눠 숫자 게임을 하려고 합니다. 두 개의 팀을 각각 A팀과 B팀이라고 하겠습니다. 숫자 게임의 규칙은 다음과 같습니다.

먼저 모든 사원이 무작위로 자연수를 하나씩 부여받습니다.
각 사원은 딱 한 번씩 경기를 합니다.
각 경기당 A팀에서 한 사원이, B팀에서 한 사원이 나와 서로의 수를 공개합니다. 그때 숫자가 큰 쪽이 승리하게 되고, 승리한 사원이 속한 팀은 승점을 1점 얻게 됩니다.
만약 숫자가 같다면 누구도 승점을 얻지 않습니다.
전체 사원들은 우선 무작위로 자연수를 하나씩 부여받았습니다. 그다음 A팀은 빠르게 출전순서를 정했고 자신들의 출전 순서를 B팀에게 공개해버렸습니다. B팀은 그것을 보고 자신들의 최종 승점을 가장 높이는 방법으로 팀원들의 출전 순서를 정했습니다. 이때의 B팀이 얻는 승점을 구해주세요.
A 팀원들이 부여받은 수가 출전 순서대로 나열되어있는 배열 A와 i번째 원소가 B팀의 i번 팀원이 부여받은 수를 의미하는 배열 B가 주어질 때, B 팀원들이 얻을 수 있는 최대 승점을 return 하도록 solution 함수를 완성해주세요.

제한사항
A와 B의 길이는 같습니다.
A와 B의 길이는 1 이상 100,000 이하입니다.
A와 B의 각 원소는 1 이상 1,000,000,000 이하의 자연수입니다.
입출력 예
A	B	result
[5,1,3,7]	[2,2,6,8]	3
[2,2,2,2]	[1,1,1,1]	0
입출력 예 설명
입출력 예 #1
https://grepp-programmers.s3.ap-northeast-2.amazonaws.com/files/production/0de59edf-76e1-4313-984a-4b2bd40911fb/number_game2_yt913p.png
A 팀은 숫자 5를 부여받은 팀원이 첫번째로 출전하고, 이어서 1,3,7을 부여받은 팀원들이 차례대로 출전합니다.
B 팀원들을 4번, 2번, 3번, 1번의 순서대로 출전시킬 경우 팀원들이 부여받은 숫자들은 차례대로 8,2,6,2가 됩니다. 그러면, 첫 번째, 두 번째, 세 번째 경기에서 승리하여 3점을 얻게 되고, 이때가 최대의 승점입니다.

입출력 예 #2
B 팀원들을 어떤 순서로 출전시켜도 B팀의 승점은 0점입니다.
 */
/*
알고리즘 핵심
정렬
1. A의 순서는 정해져있고 B의 값으로 승리하는 경우를 최대한 맞추면 되므로 최적의 선택을 하면 되므로 순서는 상관없다.
2. 이때, A와 B를 내림차순으로 정렬하여 순차적으로 A의 값에 B의 최적의 대상을 맞춘다.
2-1. A의 값이 B의 최대값보다 큰거나 같은 경우, 가장 최소값을 상대로 매칭한다.
2-2. A의 값이 B의 최대값보다 작은 경우, B의 최대값을 상대로 매칭하고 승점을 올린다.

여기서 A,B의 크기가 100,000으로 큰 값이기 때문에 DFS로 최대 승점을 갖는 경로를 찾는 문제는 어렵다고 생각하였고,
A의 순서가 고정되어 있어 그리디한 방법이 적절하다고 생각하여 정렬된 상태로 상대를 매칭하는 형태로 구성하였다.
 */
public class 숫자_게임 {
    static void main() {
        int[] A = new int[] {
                5,1,3,7
        };

        int[] B = new int[] {
                2,2,6,8
        };

        Solve task = new Solve();
        System.out.println(task.solution(A,B));
    }

    private static class Solve {
        private int ans;
        private int[] sorted_A, sorted_B;

        public int solution(int[] A, int[] B) {
            init_setting(A,B);

            number_game(sorted_A,sorted_B);

            return ans;
        }

        private void number_game(int[] A, int[] B) {
            int a_idx = 0;
            int b_idx = 0;
            int b_l_idx = B.length - 1;

            while(true) {
                if(b_idx > b_l_idx) break;
                if(A[a_idx] >= B[b_idx]) b_l_idx--;
                else {
                    ans++;
                    b_idx++;
                }

                a_idx++;
            }
        }

        private void init_setting(int[] A, int[] B) {
            ans = 0;

            sorted_A = Arrays.stream(A)
                    .boxed()
                    .sorted(Comparator.reverseOrder())
                    .mapToInt(Integer::intValue)
                    .toArray();

            sorted_B = Arrays.stream(B)
                    .boxed()
                    .sorted(Comparator.reverseOrder())
                    .mapToInt(Integer::intValue)
                    .toArray();
        }
    }
}
