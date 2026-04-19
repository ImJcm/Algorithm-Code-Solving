package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
최대 힙

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초 (추가 시간 없음) (하단 참고)	256 MB	105412	52908	42152	51.454%
문제
널리 잘 알려진 자료구조 중 최대 힙이 있다. 최대 힙을 이용하여 다음과 같은 연산을 지원하는 프로그램을 작성하시오.

배열에 자연수 x를 넣는다.
배열에서 가장 큰 값을 출력하고, 그 값을 배열에서 제거한다.
프로그램은 처음에 비어있는 배열에서 시작하게 된다.

입력
첫째 줄에 연산의 개수 N(1 ≤ N ≤ 100,000)이 주어진다. 다음 N개의 줄에는 연산에 대한 정보를 나타내는 정수 x가 주어진다. 만약 x가 자연수라면 배열에 x라는 값을 넣는(추가하는) 연산이고, x가 0이라면 배열에서 가장 큰 값을 출력하고 그 값을 배열에서 제거하는 경우이다. 입력되는 자연수는 231보다 작다.

출력
입력에서 0이 주어진 횟수만큼 답을 출력한다. 만약 배열이 비어 있는 경우인데 가장 큰 값을 출력하라고 한 경우에는 0을 출력하면 된다.

예제 입력 1
13
0
1
2
0
0
3
2
1
0
0
0
0
0
예제 출력 1
0
2
1
3
2
1
0
0
출처
문제를 만든 사람: baekjoon
문제의 오타를 찾은 사람: dantebald
데이터를 추가한 사람: djm03178, spotky1004
비슷한 문제
1927번. 최소 힙
11286번. 절댓값 힙
알고리즘 분류
자료 구조
우선순위 큐
시간 제한
Java 8: 2 초
Java 8 (OpenJDK): 2 초
Java 11: 2 초
Kotlin (JVM): 2 초
 */
/*
알고리즘 핵심
자료구조 (PriorityQueue)
1. 우선순위 큐를 사용하며, 내림차순을 기준으로 설정한다.
2. 0 이외의 값은 추가하고, 0이 아닌 경우 출력하거나 큐가 비어있으면 0을 출력한다.
 */
public class BOJ11279 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    private static class Solve {
        private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        private int N;
        private PriorityQueue<Integer> pq;
        private StringBuilder ans;

        void solve() throws IOException {
            init_setting();

            program();

            System.out.println(ans);
        }

        private void  program() throws IOException {
            while(N-- > 0) {
                int op = Integer.parseInt(br.readLine());
                switch (op) {
                    case 0:
                        ans.append(pq.isEmpty() ? 0 : pq.poll()).append("\n");
                        break;
                    default:
                        pq.add(op);
                        break;
                }
            }
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            /*pq = new  PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });*/

            pq = new PriorityQueue<>(Comparator.reverseOrder());

            ans = new StringBuilder();
        }
    }
}
