package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
뮤탈리스크

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	9659	4612	3263	46.929%
문제
수빈이는 강호와 함께 스타크래프트 게임을 하고 있다. 수빈이는 뮤탈리스크 1개가 남아있고, 강호는 SCV N개가 남아있다.

각각의 SCV는 남아있는 체력이 주어져있으며, 뮤탈리스크를 공격할 수는 없다. 즉, 이 게임은 수빈이가 이겼다는 것이다.

뮤탈리스크가 공격을 할 때, 한 번에 세 개의 SCV를 공격할 수 있다.

첫 번째로 공격받는 SCV는 체력 9를 잃는다.
두 번째로 공격받는 SCV는 체력 3을 잃는다.
세 번째로 공격받는 SCV는 체력 1을 잃는다.
SCV의 체력이 0 또는 그 이하가 되어버리면, SCV는 그 즉시 파괴된다. 한 번의 공격에서 같은 SCV를 여러 번 공격할 수는 없다.

남아있는 SCV의 체력이 주어졌을 때, 모든 SCV를 파괴하기 위해 공격해야 하는 횟수의 최솟값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 SCV의 수 N (1 ≤ N ≤ 3)이 주어진다. 둘째 줄에는 SCV N개의 체력이 주어진다. 체력은 60보다 작거나 같은 자연수이다.

출력
첫째 줄에 모든 SCV를 파괴하기 위한 공격 횟수의 최솟값을 출력한다.

예제 입력 1
3
12 10 4
예제 출력 1
2
예제 입력 2
3
54 18 6
예제 출력 2
6
예제 입력 3
1
60
예제 출력 3
7
예제 입력 4
3
1 1 1
예제 출력 4
1
예제 입력 5
2
60 40
예제 출력 5
9
힌트
1, 3, 2 순서대로 공격을 하면, 남은 체력은 (12-9, 10-1, 4-3) = (3, 9, 1)이다. 2, 1, 3 순서대로 공격을 하면, 남은 체력은 (0, 0, 0)이다.

출처
문제를 번역한 사람: baekjoon
알고리즘 분류
다이나믹 프로그래밍
그래프 이론
그래프 탐색
너비 우선 탐색
 */
public class BOJ12869 {
    static class BOJ12869_SCVS {
        int[] scvs;
        int count;

        BOJ12869_SCVS(int[] s, int count) {
            scvs = new int[3];       //default 0
            for(int i=0;i<s.length;i++) {
                scvs[i] = s[i];
            }
            this.count = count;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,min_attack_count = Integer.MAX_VALUE;
    static boolean[][][] visited;
    static int[][][] memorization;
    static BOJ12869_SCVS init;
    static int[] attack_damage = {9,3,1};
    static ArrayList<ArrayList<Integer>> attack_order;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        bfs(init);
        System.out.println(min_attack_count);

        //dp(init);
    }

    static void dp(BOJ12869_SCVS scvs) {
        System.out.println(dfs(scvs));
    }

    static int dfs(BOJ12869_SCVS scvs) {
        if(scvs.scvs[0] <= 0 && scvs.scvs[1] <= 0 && scvs.scvs[2] <= 0) {
            return 0;
        }

        if(memorization[scvs.scvs[0]][scvs.scvs[1]][scvs.scvs[2]] != Integer.MAX_VALUE &&
                memorization[scvs.scvs[0]][scvs.scvs[1]][scvs.scvs[2]] != 0) return memorization[scvs.scvs[0]][scvs.scvs[1]][scvs.scvs[2]];

        for(ArrayList<Integer> order : attack_order) {
            int[] next_scvs = new int[3];

            for(int i=0;i<order.size();i++) {
                next_scvs[i] = Math.max(scvs.scvs[i] - order.get(i),0);
            }

            memorization[scvs.scvs[0]][scvs.scvs[1]][scvs.scvs[2]] = Math.min(
                    memorization[next_scvs[0]][next_scvs[1]][next_scvs[2]] + 1,
                    memorization[scvs.scvs[0]][scvs.scvs[1]][scvs.scvs[2]]
            );
            dfs(new BOJ12869_SCVS(next_scvs,scvs.count + 1));
        }

        return memorization[scvs.scvs[0]][scvs.scvs[1]][scvs.scvs[2]];
    }

    static void bfs(BOJ12869_SCVS scvs) {
        Queue<BOJ12869_SCVS> q = new LinkedList<>();

        q.offer(scvs);
        visited[scvs.scvs[0]][scvs.scvs[1]][scvs.scvs[2]] = true;

        while(!q.isEmpty()) {
            BOJ12869_SCVS now = q.poll();

            if(now.scvs[0] <= 0 && now.scvs[1] <= 0 && now.scvs[2] <= 0) {
                min_attack_count = Math.min(min_attack_count, now.count);
                continue;
            }

            for(ArrayList<Integer> order : attack_order) {
                int[] next_scvs = new int[3];

                for(int i=0;i<order.size();i++) {
                    next_scvs[i] = Math.max(now.scvs[i] - order.get(i),0);
                }

                if(visited[next_scvs[0]][next_scvs[1]][next_scvs[2]]) continue;

                visited[next_scvs[0]][next_scvs[1]][next_scvs[2]] = true;
                q.offer(new BOJ12869_SCVS(next_scvs,now.count + 1));
            }
        }
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        int[] arr = new int[3];
        memorization = new int[61][61][61];
        visited = new boolean[61][61][61];

        String[] input = br.readLine().split(" ");

        int s1 = 0,s2 = 0,s3 = 0;
        for(int i=0;i<input.length;i++) {
            arr[i] = Integer.parseInt(input[i]);

            if(i == 0) {
                s1 = arr[i];
            } else if (i == 1) {
                s2 = arr[i];
            } else {
                s3 = arr[i];
            }
        }

        init = new BOJ12869_SCVS(arr,0);

        for(int i=0;i<61;i++) {
            for(int j=0;j<61;j++) {
                for(int k=0;k<61;k++) {
                    memorization[i][j][k] = Integer.MAX_VALUE;
                    //memorization[i][j][k] = 0;
                    visited[i][j][k] = false;
                }
            }
        }
        //memorization[s1][s2][s3] = 0;

        attack_order_setting();
    }

    static void attack_order_setting() {
        attack_order = new ArrayList<>();

        make_orders(0, new ArrayList<Integer>());
    }

    static void make_orders(int depth, ArrayList<Integer> storage) {
        if(depth == N) {
            attack_order.add(new ArrayList<>(storage));
            return;
        }

        for(int i=0;i<N;i++) {
            int damage = attack_damage[i];

            if(storage.contains(damage)) continue;

            storage.add(damage);
            make_orders(depth + 1, storage);
            storage.remove(depth);
        }
    }
}
