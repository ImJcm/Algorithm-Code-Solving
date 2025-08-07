package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;

/*
순회강연 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	18783	7689	5558	38.848%
문제
한 저명한 학자에게 n(0 ≤ n ≤ 10,000)개의 대학에서 강연 요청을 해 왔다. 각 대학에서는 d(1 ≤ d ≤ 10,000)일 안에 와서 강연을 해 주면 p(1 ≤ p ≤ 10,000)만큼의 강연료를 지불하겠다고 알려왔다. 각 대학에서 제시하는 d와 p값은 서로 다를 수도 있다. 이 학자는 이를 바탕으로, 가장 많은 돈을 벌 수 있도록 순회강연을 하려 한다. 강연의 특성상, 이 학자는 하루에 최대 한 곳에서만 강연을 할 수 있다.

예를 들어 네 대학에서 제시한 p값이 각각 50, 10, 20, 30이고, d값이 차례로 2, 1, 2, 1 이라고 하자. 이럴 때에는 첫째 날에 4번 대학에서 강연을 하고, 둘째 날에 1번 대학에서 강연을 하면 80만큼의 돈을 벌 수 있다.

입력
첫째 줄에 정수 n이 주어진다. 다음 n개의 줄에는 각 대학에서 제시한 p값과 d값이 주어진다.

출력
첫째 줄에 최대로 벌 수 있는 돈을 출력한다.

예제 입력 1
7
20 1
2 1
10 3
100 2
8 2
5 20
50 10
예제 출력 1
185
출처
ICPC > Regionals > Europe > Southeastern European Regional Contest > SEERC 2003 D번

알고리즘 분류
자료 구조
그리디 알고리즘
정렬
우선순위 큐
 */
public class BOJ2109 {
    static class BOJ2109_lecture implements Comparable<BOJ2109_lecture> {
        int p,d;

        public BOJ2109_lecture(int p, int d) {
            this.p = p;
            this.d = d;
        }

        @Override
        public int compareTo(BOJ2109_lecture l) {
            if(this.d < l.d) return -1;
            else if(this.d > l.d) return 1;
            else return l.p - this.p;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans,dday;
    static ArrayList<BOJ2109_lecture> lectures;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        PriorityQueue<BOJ2109_lecture> pq = new PriorityQueue<>();
        ans = 0;

        pq.addAll(lectures);

        for(int day = 1; day <= dday; day++) {
            int max_pay = 0;

            while(!pq.isEmpty()) {
                BOJ2109_lecture now = pq.peek();

                if(now.d == day) {
                    max_pay = Math.max(max_pay, now.p);
                }
            }
        }
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        dday = 0;
        lectures = new ArrayList<>();

        for(int i = 0; i < N; i++) {
            String[] info = br.readLine().split(" ");

            int p = Integer.parseInt(info[0]);
            int d = Integer.parseInt(info[1]);

            dday = Math.max(d,dday);

            lectures.add(new BOJ2109_lecture(p,d));
        }
    }
}
