package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/*
카드 놓기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	469	118	79	24.611%
문제
영선이는 카드와 그리드를 가지고 놀고 있다. 각각의 카드는 직사각형 모양이며, 색칠되어져 있다. 두 카드가 같은 색을 가지는 경우는 없으며, 크기도 카드마다 다를 수 있다.

영선이는 한 번에 카드 하나씩 그리드 위에 올려놓는다. 카드를 올려놓을 때, 그리드의 변과 평행이 되게 카드를 올려놓아야 한다. 따라서, 직사각형은 그리드의 칸을 덮게 된다. 또, 카드는 겹쳐서 놓을 수 있다. 카드가 놓이면서 어떤 카드를 완전히 가리는 경우는 없다.

카드를 다 놓은 다음, 위에서 바라본 결과가 주어진다. 이때, 카드를 놓은 순서를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 그리드의 크기 N과 M이 주어진다. (1 ≤ N, M ≤ 50)

둘째 줄부터 N개의 줄에 그리드에 놓여진 카드의 색이 주어진다. 카드의 색은 알파벳 소문자('a'-'z'), 대문자('A'-'Z'), 숫자('0'-'9') 중 하나이며, '.'는 빈 칸이다.

출력
첫째 줄에 카드를 놓은 순서를 출력한다. 만약 가능한 순서가 여러 가지라면, 사전순으로 앞서는 것을 출력한다. 만약, 불가능한 경우에는 -1을 출력한다.

예제 입력 1
3 6
..AA..
.CAAC.
.CAAC.
예제 출력 1
CA
예제 입력 2
2 6
..47..
..74..
예제 출력 2
-1
예제 입력 3
5 6
bbb666
.655X5
a65AA5
a65AA5
a65555
예제 출력 3
65AXab
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
자료 구조
그래프 이론
우선순위 큐
위상 정렬
방향 비순환 그래프
 */
/*
알고리즘 핵심
위상정렬 + 우선순위 큐
1. 입력으로 주어지는 카드의 색상 별로 외곽라인의 좌측 상단, 우측 하단의 좌표를 구하고, 각 카드의 색상을 인접 차수의 Key로 등록한다. : graph_setting()
2. 각 카드의 좌측 상단, 우측 하단의 좌표를 통해 해당 색상의 카드의 크기를 측정할 수 있고 이 좌표 내부에서 다른 색상을 발견하는 경우
인접 카드로 등록 후, 인접 차수를 업데이트한다. - topology_sort_setting()
3. 위상 정렬 알고리즘을 이용하여 인접 차수가 0임과 동시에 우선 순위 큐를 활용하여 사전순으로 정렬된 순서로 출력 순서를 결정하여
결과를 출력한다. - topology_sort()
(2번 과정에서 카드의 외곽 좌표 내부에 다른 색상이 아닌 공백 문자가 존재하는 경우 불가능한 카드임을 판단하고 flag를 false로 설정한다)
 */
public class BOJ14263 {
    static class BOj14263_card {
        String color;
        int min_n, min_m, max_n, max_m;
        ArrayList<String> adj_card;

        BOj14263_card(String color) {
            this.color = color;
            this.min_n = Integer.MAX_VALUE;
            this.min_m = Integer.MAX_VALUE;
            this.max_n = Integer.MIN_VALUE;
            this.max_m = Integer.MIN_VALUE;
            this.adj_card = new ArrayList<>();
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static boolean flag;
    static StringBuilder sb;
    static String[][] colors;
    static HashMap<String, BOj14263_card> cards;
    static HashMap<String, Integer> adj_degree;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        graph_setting();

        topology_sort_setting();

        topology_sort();

        System.out.println(sb.toString().length() == cards.size() && flag ? sb.toString() : -1);
    }

    private static void topology_sort() {
        // 사전순 정렬 - ASCII 순
        PriorityQueue<BOj14263_card> pq = new PriorityQueue<>(new Comparator<BOj14263_card>() {
            @Override
            public int compare(BOj14263_card o1, BOj14263_card o2) {
                return o1.color.charAt(0) - o2.color.charAt(0);
            }
        });

        for(String c : adj_degree.keySet()) {
            if(adj_degree.get(c) == 0) pq.add(cards.get(c));
        }

        while(!pq.isEmpty()) {
            BOj14263_card c = pq.poll();

            sb.append(c.color);

            for(String adj : c.adj_card) {
                int d = adj_degree.get(adj);
                adj_degree.replace(adj, d - 1);

                if(d - 1 == 0) {
                    pq.add(cards.get(adj));
                }
            }
        }


    }

    private static void topology_sort_setting() {
        // 카드 내부의 덮혀진 카드의 순서를 설정
        for(String c : cards.keySet()) {
            BOj14263_card card = cards.get(c);

            for(int n = card.min_n; n <= card.max_n; n++) {
                for(int m = card.min_m; m <= card.max_m; m++) {
                    String color = colors[n][m];

                    // 색이 존재하는 카드위에 비공간이 존재하는 경우 - 불가능한 카드임을 식별
                    if(color.equals(".")) {
                        flag = false;
                        continue;
                    }
                    if(card.color.equals(color)) continue;
                    if(card.adj_card.contains(color)) continue;

                    card.adj_card.add(color);
                    adj_degree.replace(color, adj_degree.get(color) + 1);
                }
            }
        }
    }

    private static void graph_setting() {
        // 각 색상의 카드의 외곽 설정 + 인접 차수 초기 설정
        for(int n = 1; n <= N; n++) {
            for (int m = 1; m <= M; m++) {
                if (colors[n][m].equals(".")) continue;
                if (!cards.containsKey(colors[n][m])) {
                    cards.put(colors[n][m], new BOj14263_card(colors[n][m]));
                    adj_degree.put(colors[n][m],0);
                }
                outline_check(cards.get(colors[n][m]), n, m);
            }
        }
    }

    private static void outline_check(BOj14263_card card, int n, int m) {
        card.min_n = Math.min(card.min_n, n);
        card.min_m = Math.min(card.min_m, m);
        card.max_n = Math.max(card.max_n, n);
        card.max_m = Math.max(card.max_m, m);
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        colors = new String[N+1][M+1];
        cards = new HashMap<>();
        adj_degree = new HashMap<>();

        sb = new StringBuilder();
        flag = true;

        for(int n = 1; n <= N; n++) {
            input = br.readLine().split("");
            for(int m = 1; m <= M; m++) {
                colors[n][m] = input[m-1];
            }
        }
    }
}
