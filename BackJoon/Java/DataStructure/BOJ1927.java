package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
최소 힙

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초 (추가 시간 없음) (하단 참고)	128 MB	118928	58643	46338	50.070%
문제
널리 잘 알려진 자료구조 중 최소 힙이 있다. 최소 힙을 이용하여 다음과 같은 연산을 지원하는 프로그램을 작성하시오.

배열에 자연수 x를 넣는다.
배열에서 가장 작은 값을 출력하고, 그 값을 배열에서 제거한다.
프로그램은 처음에 비어있는 배열에서 시작하게 된다.

입력
첫째 줄에 연산의 개수 N(1 ≤ N ≤ 100,000)이 주어진다. 다음 N개의 줄에는 연산에 대한 정보를 나타내는 정수 x가 주어진다. 만약 x가 자연수라면 배열에 x라는 값을 넣는(추가하는) 연산이고, x가 0이라면 배열에서 가장 작은 값을 출력하고 그 값을 배열에서 제거하는 경우이다. x는 231보다 작은 자연수 또는 0이고, 음의 정수는 입력으로 주어지지 않는다.

출력
입력에서 0이 주어진 횟수만큼 답을 출력한다. 만약 배열이 비어 있는 경우인데 가장 작은 값을 출력하라고 한 경우에는 0을 출력하면 된다.

예제 입력 1
9
0
12345678
1
2
0
0
0
0
32
예제 출력 1
0
1
2
12345678
0
출처
데이터를 추가한 사람: developer_p, djm03178, leethyun
비슷한 문제
11279번. 최대 힙
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
1. 오름차순의 기준으로 정렬하는 우선순위 큐를 사용한다.
 */
public class BOJ1927 {
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

        private void program() throws IOException {
            while(N-- > 0) {
                int op = Integer.parseInt(br.readLine());
                switch (op) {
                    case 0:
                        ans.append(pq.isEmpty() ? "0" : pq.poll()).append("\n");
                        break;
                    default:
                        pq.add(op);
                        break;
                }
            }
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            pq = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            });     // default : Order(오름차순)

            ans = new StringBuilder();
        }
    }
}
