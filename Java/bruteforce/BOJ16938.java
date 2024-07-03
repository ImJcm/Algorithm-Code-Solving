package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;

/*
캠프 준비

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	4115	2876	2216	70.060%
문제
알고리즘 캠프를 열려면 많은 준비가 필요하다. 그 중 가장 중요한 것은 문제이다. 오늘은 백준이를 도와 알고리즘 캠프에 사용할 문제를 고르려고 한다.

백준이는 문제를 N개 가지고 있고, 모든 문제의 난이도를 정수로 수치화했다. i번째 문제의 난이도는 Ai이다.

캠프에 사용할 문제는 두 문제 이상이어야 한다. 문제가 너무 어려우면 학생들이 멘붕에 빠지고, 문제가 너무 쉬우면 학생들이 실망에 빠지게 된다. 따라서, 문제 난이도의 합은 L보다 크거나 같고, R보다 작거나 같아야 한다. 또, 다양한 문제를 경험해보기 위해 가장 어려운 문제와 가장 쉬운 문제의 난이도 차이는 X보다 크거나 같아야 한다.

캠프에 사용할 문제를 고르는 방법의 수를 구해보자.

입력
첫째 줄에 N, L, R, X가 주어진다.

둘째 줄에는 문제의 난이도 A1, A2, ..., AN이 주어진다.

출력
캠프에 사용할 문제를 고르는 방법의 수를 출력한다.

제한
1 ≤ N ≤ 15
1 ≤ L ≤ R ≤ 109
1 ≤ X ≤ 106
1 ≤ Ai ≤ 106
예제 입력 1
3 5 6 1
1 2 3
예제 출력 1
2
2번, 3번 문제를 고르는 방법, 모든 문제를 고르는 방법이 가능하다.

예제 입력 2
4 40 50 10
10 20 30 25
예제 출력 2
2
난이도가 10, 30인 문제를 고르거나, 20, 30인 문제를 고르면 된다.

예제 입력 3
5 25 35 10
10 10 20 10 20
예제 출력 3
6
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
브루트포스 알고리즘
비트마스킹
백트래킹
 */
/*
알고리즘 핵심
0. 문제로 주어지는 문제들의 난이도를 오름차순으로 정렬 - 선택
1. dfs로 가능한 모든 경우의 조합을 만들어낸다.
2. 만들어진 조합으로 문제에서 요구하는 조건을 검사

비트마스킹을 이용하는 경우, 2^15승 만큼의 값만큼 각 문제지의 난이도와 2^n 자리수의 값으로 매핑하여 가능한 문제 조합을 만들고 검사한다.
 */
public class BOJ16938 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,L,R,X,ans;
    static int[] A;
    static boolean[] visited;
    static HashSet<String> visited_bitmask;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        create_bitmask(0,0,0);
        //select_section(new ArrayList<>(),0);

        System.out.println(ans);
    }

    static void select_section(ArrayList<Integer> s, int idx) {
        for(int i=idx;i<A.length;i++) {
            int n = A[i];
            s.add(n);
            select_section(s,i+1);
            s.remove(s.size()-1);
        }

        check(s);
    }

    static void check(ArrayList<Integer> s) {
        if(s.size() < 2) return;

        int max = s.stream().max(Integer::compare).orElse(0);
        int min = s.stream().min(Integer::compare).orElse(0);
        int sum = s.stream().mapToInt(i->i).sum();

        if(max - min >= X && L <= sum && sum <= R) {
            ans++;
        }
    }

    static void create_bitmask(int depth, int idx, int b) {
        if(depth == N) {
            String bit = Integer.toBinaryString(b);
            if(bit.length() > N) return;
            if(visited_bitmask.contains(bit)) return;
            visited_bitmask.add(bit);

            if(Integer.bitCount(b) < 2) return;

            double m = Math.sqrt(Integer.highestOneBit(b));
            double mm = Math.sqrt(Integer.lowestOneBit(b));
            int max = A[m > (int) (m) ? (int) (m) + 1 : (int) m];
            int min = A[Integer.lowestOneBit(b) > 1 ? (int) (mm) : 0];

            int sum = 0;

            for(int i=0;i<bit.length();i++) {
                char c = bit.charAt(i);

                if(c == '1') {
                    sum += A[bit.length() - 1 - i];
                }
            }

            if(L <= sum && sum <= R && max - min >= X) {
                ans++;
            }

            return;
        }

        for(int i=idx;i<N;i++) {
            int a = (int) Math.pow(2,idx);
            create_bitmask(depth + 1, i + 1, b + a);
            create_bitmask(depth + 1, i + 1, b);
        }
    }



    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        L = Integer.parseInt(input[1]);
        R = Integer.parseInt(input[2]);
        X = Integer.parseInt(input[3]);
        ans = 0;
        visited = new boolean[N];
        visited_bitmask = new HashSet<>();

        A = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        Arrays.fill(visited, false);

        Arrays.sort(A);
    }
}
