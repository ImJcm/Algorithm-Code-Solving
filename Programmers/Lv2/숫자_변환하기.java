package Programmers;

import java.util.LinkedList;
import java.util.Queue;

/*
숫자 변환하기
제출 내역
문제 설명
자연수 x를 y로 변환하려고 합니다. 사용할 수 있는 연산은 다음과 같습니다.

x에 n을 더합니다
x에 2를 곱합니다.
x에 3을 곱합니다.
자연수 x, y, n이 매개변수로 주어질 때, x를 y로 변환하기 위해 필요한 최소 연산 횟수를 return하도록 solution 함수를 완성해주세요. 이때 x를 y로 만들 수 없다면 -1을 return 해주세요.

제한사항
1 ≤ x ≤ y ≤ 1,000,000
1 ≤ n < y
입출력 예
x	y	n	result
10	40	5	2
10	40	30	1
2	5	4	-1
입출력 예 설명
입출력 예 #1
x에 2를 2번 곱하면 40이 되고 이때가 최소 횟수입니다.

입출력 예 #2
x에 n인 30을 1번 더하면 40이 되고 이때가 최소 횟수입니다.

입출력 예 #3
x를 y로 변환할 수 없기 때문에 -1을 return합니다.
 */
public class 숫자_변환하기 {
    static void main() {
        int[] x = new int[] {10,10,2};
        int[] y = new int[] {40,40,5};
        int[] n = new int[] {5,30,4};

        Solve task = new Solve();
        System.out.println(task.solution(x[0],y[0],n[0]));
    }

    private static class Solve {
        private class operation {
            int num;
            int count;

            public operation(int num, int count) {
                this.num = num;
                this.count = count;
            }
        }
        private int ans;
        private Queue<operation> q;
        private boolean[] visited;

        public int solution(int x,int y,int n) {
            init_setting(x,y,n);

            convert_number(x,y,n);

            return ans;
        }

        private void convert_number(int x,int y,int n) {
            q.add(new operation(x,0));
            visited[x] = true;

            while(!q.isEmpty()) {
                operation op = q.poll();

                if(op.num == y) {
                    ans = op.count;
                    return;
                }

                if(op.num + n > y || op.num * 2 > y) continue;

                if(op.num + n <= 1_000_000 && !visited[op.num + n]) {
                    visited[op.num + n] = true;
                    q.add(new operation(op.num + n,op.count + 1));
                }

                if(op.num * 2 <= 1_000_000 && !visited[op.num * 2]) {
                    visited[op.num * 2] = true;
                    q.add(new operation(op.num * 2,op.count + 1));
                }

                if(op.num * 3 <= 1_000_000 && !visited[op.num * 3]) {
                    visited[op.num * 3] = true;
                    q.add(new operation(op.num * 3,op.count + 1));
                }
            }
        }

        private void init_setting(int x,int y,int n) {
            ans = -1;

            q = new LinkedList<>();

            visited = new boolean[1_000_001];
        }
    }
}
