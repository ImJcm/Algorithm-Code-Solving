package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
세 친구

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	1926	959	689	47.419%
문제
N명의 사람이 있고, 여기서 세 사람 A, B, C를 고르려고 한다. 세 사람은 모두 친구여야 한다.

세 사람을 고르는 방법은 매우 많이 있을 수 있다. 이때, A의 친구 수 + B의 친구 수 + C의 친구 수가 최소가 되어야 한다. 친구 수의 합을 계산할 때, 세 사람은 빼고 계산해야 한다. 즉, A의 친구 수를 계산할 때, B와 C는 빼고 계산해야 하고, B의 친구 수를 계산할 때는 A와 C, C의 친구 수를 계산할 때는 A와 B를 빼고 계산해야 한다.

입력
첫째 줄에 사람의 수 N(3 ≤ N ≤ 4,000), 친구 관계의 수 M(0 ≤ M ≤ 4,000)이 주어진다. 둘째 줄부터 M개의 줄에는 친구 관계를 의미하는 두 정수 A, B가 주어진다. 친구 관계는 A와 B, 그리고 B와 A가 친구라는 것을 의미한다.

사람은 1번부터 N번까지 번호가 매겨져 있다. 같은 친구 관계가 두 번 이상 주어지는 경우는 없다.

출력
첫째 줄에 A의 친구 수 + B의 친구 수 + C의 친구 수의 최솟값을 출력한다. 만약, 문제 조건대로 세 사람을 고를 수 없는 경우에는 -1을 출력한다.

예제 입력 1
5 6
1 2
1 3
2 3
2 4
3 4
4 5
예제 출력 1
2
예제 입력 2
7 4
2 1
3 6
5 1
1 7
예제 출력 2
-1
출처
문제를 번역한 사람: baekjoon
데이터를 추가한 사람: jh05013
알고리즘 분류
그래프 이론
브루트포스 알고리즘
 */
/*
알고리즘 핵심
A. dfs 호출 시, 최초의 친구 A를 지정하여 dfs 내부에서 공통된 친구 관계인 친구를 고르는 코드를 사용할 수 있는 방법
B. 한번의 dfs 호출로 내부에서 A,B,C를 조건에 맞게 고르는 방법

B를 선택하여 코드를 구현하였다.

1. depth = 0인 경우, 1~N까지의 친구를 모두 한번씩 선택한다. (친구 관계가 2보다 작은 친구는 넘긴다)
2. depth = 1,2인 경우, depth-1에 해당하는 친구 관계인 친구들 중에서 친구로 선택한다.
3. depth = 3인 경우(기저사례), 마지막 친구인 C와 A의 관계를 모르는 상태이므로 A와 C가 친구 관계 여부를 검사한다.
4. 3을 만족하면, A,B,C의 친구 관계의 총합 - 6을 ans와 최솟값으로 업데이트한다.
5. ans가 Integer.MAX_VALUE인 경우, A,B,C를 구성하지 못한 경우이므로, -1을 출력 / Integer.MAX_VALUE가 아닌 경우, ans 출력한다.
 */
public class BOJ17089 {
    static class BOJ17089_person {
        int k;
        ArrayList<Integer> relationship;

        BOJ17089_person(int k) {
            this.k = k;
            this.relationship = new ArrayList<>();
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,ans;
    static BOJ17089_person[] persons, friends;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        dfs(0);

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    static void dfs(int depth) {
        if(depth == 3) {
            if(friends[2].relationship.contains(friends[0].k)) {
                int sum = -6;

                for(BOJ17089_person f : friends) {
                    sum += f.relationship.size();
                }

                ans = Math.min(ans,sum);
            }
            return;
        }

        if(depth == 0) {
            for(int i=1;i<=N;i++) {
                if(persons[i].relationship.size() < 2) continue;
                friends[depth] = persons[i];
                dfs(depth+1);
            }
        } else {
            for(Integer f : friends[depth-1].relationship) {
                if(persons[f].relationship.size() < 2) continue;
                friends[depth] = persons[f];
                dfs(depth+1);
            }
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        persons = new BOJ17089_person[N+1];
        friends = new BOJ17089_person[3];

        ans = Integer.MAX_VALUE;

        for(int i=1;i<=N;i++) {
            persons[i] = new BOJ17089_person(i);
        }

        for(int i=0;i<M;i++) {
            input = br.readLine().split(" ");

            int r1 = Integer.parseInt(input[0]);
            int r2 = Integer.parseInt(input[1]);

            persons[r1].relationship.add(r2);
            persons[r2].relationship.add(r1);
        }
    }
}
