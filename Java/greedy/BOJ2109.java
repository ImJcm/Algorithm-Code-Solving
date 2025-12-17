package greedy;

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
/*
알고리즘 핵심
그리디 알고리즘 + 우선순위 큐
1. 입력으로 주어지는 강의의 비용과 기간을 저장하여 우선순위 큐를 사용하여 강의 비용을 기준으로 내림차순 정렬 상태를 정의한다.
2. 강의 비용이 높은 순으로 강의를 배치하여 해당 강의의 기간에 맞추어 해당 강의의 최종 기간부터 1일차 까지 가능한 시간대에 배치한다.
3. 강의 비용을 높은 순으로 내림차순 정렬하였기 때문에 앞선 강의를 먼저 가능한 시간대를 찾아 배치함으로써 최대 비용을 갖는다.
 */
public class BOJ2109 {
    static class BOJ2109_lecture implements Comparable<BOJ2109_lecture> {
        int p,d;

        public BOJ2109_lecture(int p, int d) {
            this.p = p;
            this.d = d;
        }

        /*
            강의 비용으로 내림차순 정렬
         */
        @Override
        public int compareTo(BOJ2109_lecture l) {
            return l.p - this.p;
        }

        /*
            기한을 오름차순으로 정렬하고, 같은 경우 강의 비용이 높은 순으로 내림차순 정렬
         */
        /*@Override
        public int compareTo(BOJ2109_lecture l) {
            if(this.d < l.d) return -1;
            else if(this.d > l.d) return 1;
            else return l.p - this.p;
        }*/

    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static ArrayList<BOJ2109_lecture> lectures;
    final static int MAX_DAY = 10_001;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        PriorityQueue<BOJ2109_lecture> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[MAX_DAY];

        pq.addAll(lectures);

        while(!pq.isEmpty()) {
            BOJ2109_lecture now = pq.poll();

            for(int d = now.d; d > 0; d--) {
                if(visited[d]) continue;
                visited[d] = true;
                ans += now.p;
                break;
            }
        }

        System.out.println(ans);
    }

    /*
        틀린 코드 : 올바른 로직이 아님 (기한 - 오름차순 정렬 + 비용 - 내림차순 정렬)
        반례
        4
        2 1, 3 1, 4 2, 5 2
        result : 8, answer : 9
     */
    private static void wrong_solve() {
        PriorityQueue<BOJ2109_lecture> pq = new PriorityQueue<>();
        int day = 1;

        pq.addAll(lectures);

        while(!pq.isEmpty()) {
            BOJ2109_lecture now = pq.poll();

            if(now.d >= day) {
                ans += now.p;
                day = now.d + 1;
            }
        }

        System.out.println(ans);
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        lectures = new ArrayList<>();

        for(int i = 0; i < N; i++) {
            String[] info = br.readLine().split(" ");

            int p = Integer.parseInt(info[0]);
            int d = Integer.parseInt(info[1]);

            lectures.add(new BOJ2109_lecture(p,d));
        }

        ans = 0;
    }
}
