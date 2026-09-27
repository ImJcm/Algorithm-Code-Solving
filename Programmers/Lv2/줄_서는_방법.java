package Lv2;

import java.util.Arrays;
import java.util.stream.IntStream;

/*
줄 서는 방법
제출 내역
문제 설명
n명의 사람이 일렬로 줄을 서고 있습니다. n명의 사람들에게는 각각 1번부터 n번까지 번호가 매겨져 있습니다. n명이 사람을 줄을 서는 방법은 여러가지 방법이 있습니다. 예를 들어서 3명의 사람이 있다면 다음과 같이 6개의 방법이 있습니다.

[1, 2, 3]
[1, 3, 2]
[2, 1, 3]
[2, 3, 1]
[3, 1, 2]
[3, 2, 1]
사람의 수 n과, 자연수 k가 주어질 때, 사람을 나열 하는 방법을 사전 순으로 나열 했을 때, k번째 방법을 return하는 solution 함수를 완성해주세요.

제한사항
n은 20이하의 자연수 입니다.
k는 n! 이하의 자연수 입니다.
입출력 예
n	k	result
3	5	[3,1,2]
입출력 예시 설명
입출력 예 #1
문제의 예시와 같습니다.
 */
public class 줄_서는_방법 {
    static void main() {
        int n = 3;
        int k = 5;

        Solve task = new Solve();
        System.out.println(Arrays.toString(task.solution(n,k)));
    }

    /*
        Accuracy_TestCase : 100%
        Efficiency_TestCase : time out
     */
    private static class WrongSolve_Timeout {
        private int[] ans;
        private int[] person,line;
        private long cnt;
        private boolean[] visited;
        private boolean flag;

        public int[] solution(int n, long k) {
            init_setting(n,k);

            waiting_in_line(0,n,k, person);

            return ans;
        }

        private void waiting_in_line(int i, int n, long k, int[] p) {
            if(flag) return;
            if(i == n) {
                cnt++;
                if(cnt == k) {
                    flag = true;
                    ans = line.clone();
                }
                return;
            }

            for(int l = 0; l < n; l++) {
                if(visited[l]) continue;
                visited[l] = true;
                line[i] = p[l];
                waiting_in_line(i + 1, n, k, p);
                visited[l] = false;
            }
        }

        private void init_setting(int n, long k) {
            ans = new int[n];

            person = IntStream.range(1, n + 1).toArray();
            line = new int[n];

            cnt = 0;

            visited = new boolean[n];

            flag = false;
        }
    }
}
