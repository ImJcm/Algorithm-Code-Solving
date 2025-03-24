package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

/*
요세푸스 문제

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	130284	65706	46016	49.175%
문제
요세푸스 문제는 다음과 같다.

1번부터 N번까지 N명의 사람이 원을 이루면서 앉아있고, 양의 정수 K(≤ N)가 주어진다. 이제 순서대로 K번째 사람을 제거한다. 한 사람이 제거되면 남은 사람들로 이루어진 원을 따라 이 과정을 계속해 나간다. 이 과정은 N명의 사람이 모두 제거될 때까지 계속된다. 원에서 사람들이 제거되는 순서를 (N, K)-요세푸스 순열이라고 한다. 예를 들어 (7, 3)-요세푸스 순열은 <3, 6, 2, 7, 5, 1, 4>이다.

N과 K가 주어지면 (N, K)-요세푸스 순열을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N과 K가 빈 칸을 사이에 두고 순서대로 주어진다. (1 ≤ K ≤ N ≤ 5,000)

출력
예제와 같이 요세푸스 순열을 출력한다.

예제 입력 1
7 3
예제 출력 1
<3, 6, 2, 7, 5, 1, 4>
출처
문제를 만든 사람: author5
알고리즘 분류
구현
자료 구조
큐
 */
/*
알고리즘 핵심
자료구조 (큐 - Deque)
1. 1~N까지 순차적으로 Deque에 First - 1 ~ N - Last 순서로 저장한다.
2. Deque가 비어있을 때까지 반복한다.
3. K-1만큼 반복하여 Deque의 Head를 poll()하고, 해당 값을 뒷부분으로 addLast()로 추가한다.
(addLast를 사용하기 위해 Queue -> Deque를 사용)
4. Deque에 남은 데이터의 수가 1보다 큰경우, "value, "형태로 StringBuilder에 추가하고,
Deque에 남은 데이터의 수가 1인 경우, "value"만 추가한다.
5. 출력 양식에 맞게 < ... >로 출력한다.
 */
public class BOJ1158 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,K;
    static Deque<Integer> Josephus;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        while(!Josephus.isEmpty()) {
            int k = K;
            while(--k > 0) Josephus.addLast(Josephus.poll());
            if(Josephus.size() != 1) sb.append(Josephus.poll()).append(", ");
            else sb.append(Josephus.poll());
        }

        System.out.println("<" + sb.toString() + ">");
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);

        sb = new StringBuilder();

        Josephus = new LinkedList<>();

        for(int i = 1; i <= N; i++) Josephus.addLast(i);
    }
}
