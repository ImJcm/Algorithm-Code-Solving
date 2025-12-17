package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
게리맨더링

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (추가 시간 없음)	512 MB	29110	12774	8061	40.475%
문제
백준시의 시장 최백준은 지난 몇 년간 게리맨더링을 통해서 자신의 당에게 유리하게 선거구를 획정했다. 견제할 권력이 없어진 최백준은 권력을 매우 부당하게 행사했고, 심지어는 시의 이름도 백준시로 변경했다. 이번 선거에서는 최대한 공평하게 선거구를 획정하려고 한다.

백준시는 N개의 구역으로 나누어져 있고, 구역은 1번부터 N번까지 번호가 매겨져 있다. 구역을 두 개의 선거구로 나눠야 하고, 각 구역은 두 선거구 중 하나에 포함되어야 한다. 선거구는 구역을 적어도 하나 포함해야 하고, 한 선거구에 포함되어 있는 구역은 모두 연결되어 있어야 한다. 구역 A에서 인접한 구역을 통해서 구역 B로 갈 수 있을 때, 두 구역은 연결되어 있다고 한다. 중간에 통하는 인접한 구역은 0개 이상이어야 하고, 모두 같은 선거구에 포함된 구역이어야 한다.

아래 그림은 6개의 구역이 있는 것이고, 인접한 구역은 선으로 연결되어 있다.



아래는 백준시를 두 선거구로 나눈 4가지 방법이며, 가능한 방법과 불가능한 방법에 대한 예시이다.


가능한 방법

[1, 3, 4]와 [2, 5, 6]으로 나누어져 있다.

가능한 방법

[1, 2, 3, 4, 6]과 [5]로 나누어져 있다.

불가능한 방법

[1, 2, 3, 4]와 [5, 6]으로 나누어져 있는데, 5와 6이 연결되어 있지 않다.

불가능한 방법

각 선거구는 적어도 하나의 구역을 포함해야 한다.

공평하게 선거구를 나누기 위해 두 선거구에 포함된 인구의 차이를 최소로 하려고 한다. 백준시의 정보가 주어졌을 때, 인구 차이의 최솟값을 구해보자.

입력
첫째 줄에 구역의 개수 N이 주어진다. 둘째 줄에 구역의 인구가 1번 구역부터 N번 구역까지 순서대로 주어진다. 인구는 공백으로 구분되어져 있다.

셋째 줄부터 N개의 줄에 각 구역과 인접한 구역의 정보가 주어진다. 각 정보의 첫 번째 정수는 그 구역과 인접한 구역의 수이고, 이후 인접한 구역의 번호가 주어진다. 모든 값은 정수로 구분되어져 있다.

구역 A가 구역 B와 인접하면 구역 B도 구역 A와 인접하다. 인접한 구역이 없을 수도 있다.

출력
첫째 줄에 백준시를 두 선거구로 나누었을 때, 두 선거구의 인구 차이의 최솟값을 출력한다. 두 선거구로 나눌 수 없는 경우에는 -1을 출력한다.

제한
2 ≤ N ≤ 10
1 ≤ 구역의 인구 수 ≤ 100
예제 입력 1
6
5 2 3 4 1 2
2 2 4
4 1 3 6 5
2 4 2
2 1 3
1 2
1 2
예제 출력 1
1
선거구를 [1, 4], [2, 3, 5, 6]으로 나누면 각 선거구의 인구는 9, 8이 된다. 인구 차이는 1이고, 이 값보다 더 작은 값으로 선거구를 나눌 수는 없다.

예제 입력 2
6
1 1 1 1 1 1
2 2 4
4 1 3 6 5
2 4 2
2 1 3
1 2
1 2
예제 출력 2
0
선거구를 [1, 3, 4], [2, 5, 6]으로 나누면 인구 차이가 0이다.

예제 입력 3
6
10 20 10 20 30 40
0
0
0
0
0
0
예제 출력 3
-1
두 선거구로 나눌 수 있는 방법이 없다.

예제 입력 4
6
2 3 4 5 6 7
2 2 3
2 1 3
2 1 2
2 5 6
2 4 6
2 4 5
예제 출력 4
9
노트
게리맨더링은 특정 후보자나 정당에 유리하게 선거구를 획정하는 것을 의미한다.

출처
문제를 만든 사람: baekjoon
알고리즘 분류
수학
그래프 이론
브루트포스 알고리즘
그래프 탐색
너비 우선 탐색
조합론
깊이 우선 탐색
 */
/*
알고리즘 핵심
1. 조합론을 이용하여 두 선거구를 나눈다.
(이때, 각 선거구를 나눈 경우의 수에서 첫번째와 마지막 경우의 수는 한쪽 선거구의 지역을 선택하지 않거나 모두 선택하는 경우이므로 제외한다.)
2. 두 선거구를 나눈 후, 각 선거구가 인접한지 확인하기 위해 각 선거구에서 조합을 이루는 지역에 한에서 BFS로 탐색한다.
3. 인접한 지역임을 보장하고 두 선거구가 만들어지면 두 선거구의 인구수를 계산한 후 최솟값을 업데이트한다.
 */
public class BOJ17471 {
    static class BOJ17471_section {
        int n;
        ArrayList<Integer> adj;

        BOJ17471_section(int n) {
            this.n = n;
            this.adj = new ArrayList<>();
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static int[] population;
    static BOJ17471_section[] sections;
    static ArrayList<boolean[]> divide_sections_case;
    static ArrayList<boolean[]> valid_divide_sections_case;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        divide_section();
        validate_section();
        calculate_diff_population();

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    static void calculate_diff_population() {
        for(boolean[] s : valid_divide_sections_case) {
            int s_1 = 0;
            int s_2 = 0;

            for(int i = 1; i < s.length; i++) {
                if(s[i]) {
                    s_1 += population[i];
                } else {
                    s_2 += population[i];
                }
            }

            int diff = Math.abs(s_1 - s_2);

            ans = Math.min(ans, diff);
        }
    }

    static void validate_section() {
        divide_sections_case.remove(0);                 // 모든 선거구를 포함한 구역
        divide_sections_case.remove(divide_sections_case.size() - 1); // 모든 선거구를 포함하지 않은 구역

        for(boolean[] s : divide_sections_case) {
            ArrayList<Integer> a1 = new ArrayList<>();
            ArrayList<Integer> a2 = new ArrayList<>();

            for(int i = 1; i < s.length; i++) {
                if(s[i]) {
                    a1.add(i);
                } else {
                    a2.add(i);
                }
            }

            boolean check_1 = true;
            boolean check_2 = true;

            check_1 = bfs(a1);
            check_2 = bfs(a2);

            if(check_1 && check_2) {
                valid_divide_sections_case.add(s);
            }
        }
    }

    static boolean bfs(ArrayList<Integer> a) {
        Queue<BOJ17471_section> q = new LinkedList<>();
        boolean[] visited = new boolean[N+1];

        int c = 0;
        int i = a.get(0);

        q.offer(sections[i]);
        visited[i] = true;

        while(!q.isEmpty()) {
            BOJ17471_section now = q.poll();
            c++;

            for(int n : now.adj) {
                if(a.contains(n) && !visited[n]) {
                    visited[n] = true;
                    q.offer(sections[n]);
                }
            }
        }

        if(c == a.size()) {
            return true;
        } else {
            return false;
        }
    }

    static void divide_section() {
        boolean[] selected_section = new boolean[N+1];
        combination(1, selected_section);
    }

    static void combination(int d, boolean[] s_s) {
        if(d == N + 1) {
            divide_sections_case.add(Arrays.copyOf(s_s,N+1));
            return;
        }

        s_s[d] = true;
        combination(d + 1, s_s);
        s_s[d] = false;
        combination(d + 1, s_s);
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        ans = Integer.MAX_VALUE;

        population = new int[N+1];
        sections = new BOJ17471_section[N+1];
        divide_sections_case = new ArrayList<>();
        valid_divide_sections_case = new ArrayList<>();

        String[] input = br.readLine().split(" ");
        for(int i = 1; i <= N; i++) {
            population[i] = Integer.parseInt(input[i-1]);
            sections[i] = new BOJ17471_section(i);
        }

        for(int i = 1; i <= N; i++) {
            input = br.readLine().split(" ");

            for(int j = 1; j <= Integer.parseInt(input[0]); j++) {
                sections[i].adj.add(Integer.parseInt(input[j]));
            }
        }
    }
}
