package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
보석 도둑 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	93252	23473	16229	23.270%
문제
세계적인 도둑 상덕이는 보석점을 털기로 결심했다.

상덕이가 털 보석점에는 보석이 총 N개 있다. 각 보석은 무게 Mi와 가격 Vi를 가지고 있다. 상덕이는 가방을 K개 가지고 있고, 각 가방에 담을 수 있는 최대 무게는 Ci이다. 가방에는 최대 한 개의 보석만 넣을 수 있다.

상덕이가 훔칠 수 있는 보석의 최대 가격을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N과 K가 주어진다. (1 ≤ N, K ≤ 300,000)

다음 N개 줄에는 각 보석의 정보 Mi와 Vi가 주어진다. (0 ≤ Mi, Vi ≤ 1,000,000)

다음 K개 줄에는 가방에 담을 수 있는 최대 무게 Ci가 주어진다. (1 ≤ Ci ≤ 100,000,000)

모든 숫자는 양의 정수이다.

출력
첫째 줄에 상덕이가 훔칠 수 있는 보석 가격의 합의 최댓값을 출력한다.

예제 입력 1
2 1
5 10
100 100
11
예제 출력 1
10
예제 입력 2
3 2
1 65
5 23
2 99
10
2
예제 출력 2
164
힌트
두 번째 예제의 경우 첫 번째 보석을 두 번째 가방에, 세 번째 보석을 첫 번째 가방에 넣으면 된다.

출처
Contest > Croatian Open Competition in Informatics > COCI 2013/2014 > Contest #1 4번

문제를 번역한 사람: baekjoon
데이터를 추가한 사람: dbdjfdjqkqh1
잘못된 조건을 찾은 사람: eeight
알고리즘 분류
자료 구조
그리디 알고리즘
정렬
우선순위 큐
 */
/*
알고리즘 핵심
그리디 알고리즘 + 정렬
1. 보석의 무게와 가치 중 무게를 기준으로 오름차순 정렬하고, 가방의 무게를 기준으로 오름차순 정렬한 배열을 만든다.
2. 가방을 기준으로 0~K 까지의 가방의 수용무게에 담을 수 있는 보석을 별도의 리스트에 담는다.
3. 2번 과정에서 얻은 리스트에서 가장 가치가 높은 보석을 가방(C[i])에 담는다.
4. 다음 가방에 담을 수 있는 보석의 리스트는 2번 과정에서 만들어진 리스트에 추가로 보석을 담아 이용한다.
5. 모든 가방에 보석을 담으면 long 타입의 ans를 출력한다.

처음 접근으로 보석의 가치를 기준으로 내림차순 정렬하였고, 가치가 같은 경우 무게가 큰 순으로 정렬을 하였고, 가방을 내림차순으로
정렬하여 가치가 높은 순으로 가방에 담을 수 있도록 하였지만, 해당 과정은 최대 가치를 갖는 경우의 수를 만족하지 못했다.
ex) N = 3 K = 4 | jewel => 1 - 65, 5 - 23, 2 - 99 | C => 10 2 3 5
2의 무게를 갖는 보석을 10 가방에 담고, 1의 무게를 갖는 보석을 5에 담게 되는데
원래의 최대 가치의 보석을 담는 경우의 수는 2의 보석을 10, 1의 보석을 3, 5의 보석을 5에 담는 경우 모든 보석을 담을 수 있다.
따라서, 보석의 가치를 기준으로 정렬하면 특정 가방에서의 최대 가치를 가질 수 있는 보석인지 아닌지 여부를 확인할 수 없다.

두번째 접근으로, 보석의 가치가 높은 순으로 정렬하고, 가방의 무게를 기준으로 오름차순으로 정렬하여 가치가 높은 보석이
무게가 허용하는 가장 최적의 가방을 찾는 bruteforce 형태로 구성하였지만 해당 방법은 시간초과가 발생하였다.
따라서, 가방의 수용할 수 있는 무게의 보석만을 고려한다면 시간을 줄일 수 있다고 생각한다.

세번째 접근으로 무게를 기준으로 오름차순 정렬하고, 가방의 무게를 기준으로 오름차순 정렬하여 가방을 기준으로 특정 가방의 무게에
수용할 수 있는 보석들만 고려하는 리스트를 만들고, 해당 리스트에서 가치가 높은 보석을 담는다면 다음 가방의 무게에서 수용할 수 있는
보석의 리스트는 이전에 만든 리스트에 수용 가능한 보석만 추가하여 재사용할 수 있다.
이를 통해 시간초과와 올바른 로직을 구성할 수 있다.
 */
public class BOJ1202 {
    static class BOJ1202_jewel implements Comparable<BOJ1202_jewel> {
        int M,V;

        BOJ1202_jewel(int m, int v) {
            this.M = m;
            this.V = v;
        }

        /* wrong_solve(), wrong_solve2()에서 사용*/
        /*@Override
        public int compareTo(BOJ1202_jewel o) {
            return this.V - o.V > 0 ? -1 :
                    this.V - o.V < 0 ? 1 :
                            o.M - this.M;
        }*/

        @Override
        public int compareTo(BOJ1202_jewel o) {
            return this.M - o.M;
        }
    }


    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,K;
    static long ans;
    static int[] bags;
    static BOJ1202_jewel[] jewels;
    static PriorityQueue<BOJ1202_jewel> pq;
    static boolean[] visited;
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        Arrays.sort(jewels);
        Arrays.sort(bags);

        for(int i = 0, j = 0; i < K; i++) {
            int c = bags[i];

            while(j < N && jewels[j].M <= c) {
                pq.add(jewels[j]);
                j++;
            }

            ans += pq.isEmpty() ? 0 : pq.poll().V;
        }

        System.out.println(ans);
    }

    /*
        시간초과 틀린 코드 : 300,000 * 300,000의 연산으로 시간초과 발생 예상
     */
    private static void wrong_solve2() {
        Arrays.sort(jewels);
        Arrays.sort(bags);

        int total = 0;

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < K; j++) {
                if(visited[j]) continue;
                if(bags[j] >= jewels[i].M) {
                    visited[j] = true;
                    total += jewels[i].V;
                    break;
                }
            }
        }
        System.out.println(total);
    }

    /*
        3% 오답 코드 : 최대 가치의 보석의 합을 만족하지 못함.
     */
    private static void wrong_solve() {
        Arrays.sort(jewels);
        bags = Arrays.stream(bags).boxed().sorted(Comparator.reverseOrder())
                .mapToInt(Integer::intValue).toArray();

        int total = 0;
        int ci = 0;

        for(int i = 0; i < N; i++) {
            if(ci < K && bags[ci] >= jewels[i].M) {
                total += jewels[i].V;
                ci++;
            }
        }

        System.out.println(total);
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);

        jewels = new BOJ1202_jewel[N];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int m = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            jewels[i] = new BOJ1202_jewel(m,v);
        }

        bags = new int[K];

        for(int i = 0; i < K; i++) {
            bags[i] = Integer.parseInt(br.readLine());
        }

        visited = new boolean[K];

        pq = new PriorityQueue<>(new Comparator<BOJ1202_jewel>() {
            @Override
            public int compare(BOJ1202_jewel o1, BOJ1202_jewel o2) {
                return o2.V - o1.V;
            }
        });

        ans = 0;
    }
}
