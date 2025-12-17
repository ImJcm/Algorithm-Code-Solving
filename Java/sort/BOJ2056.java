package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
작업 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	16349	7657	5640	44.187%
문제
수행해야 할 작업 N개 (3 ≤ N ≤ 10000)가 있다. 각각의 작업마다 걸리는 시간(1 ≤ 시간 ≤ 100)이 정수로 주어진다.

몇몇 작업들 사이에는 선행 관계라는 게 있어서, 어떤 작업을 수행하기 위해 반드시 먼저 완료되어야 할 작업들이 있다. 이 작업들은 번호가 아주 예쁘게 매겨져 있어서, K번 작업에 대해 선행 관계에 있는(즉, K번 작업을 시작하기 전에 반드시 먼저 완료되어야 하는) 작업들의 번호는 모두 1 이상 (K-1) 이하이다. 작업들 중에는, 그것에 대해 선행 관계에 있는 작업이 하나도 없는 작업이 반드시 하나 이상 존재한다. (1번 작업이 항상 그러하다)

모든 작업을 완료하기 위해 필요한 최소 시간을 구하여라. 물론, 서로 선행 관계가 없는 작업들은 동시에 수행 가능하다.

입력
첫째 줄에 N이 주어진다.

두 번째 줄부터 N+1번째 줄까지 N개의 줄이 주어진다. 2번째 줄은 1번 작업, 3번째 줄은 2번 작업, ..., N+1번째 줄은 N번 작업을 각각 나타낸다. 각 줄의 처음에는, 해당 작업에 걸리는 시간이 먼저 주어지고, 그 다음에 그 작업에 대해 선행 관계에 있는 작업들의 개수(0 ≤ 개수 ≤ 100)가 주어진다. 그리고 선행 관계에 있는 작업들의 번호가 주어진다.

출력
첫째 줄에 모든 작업을 완료하기 위한 최소 시간을 출력한다.

예제 입력 1
7
5 0
1 1 1
3 1 2
6 1 1
1 2 2 4
8 2 2 4
4 3 3 5 6
예제 출력 1
23
힌트
1번 작업 : 0~5
2번 작업 : 5~6
3번 작업 : 6~9
4번 작업 : 5~11
5번 작업 : 11~12
6번 작업 : 11~19
7번 작업 : 19~23
출처
Olympiad > USA Computing Olympiad > 2001-2002 Season > USACO February 2002 Contest > Green or Orange ?번

문제의 오타를 찾은 사람: tjrwodnjs999
알고리즘 분류
다이나믹 프로그래밍
그래프 이론
위상 정렬
방향 비순환 그래프
 */
/*
알고리즘 핵심
위상 정렬 + 동적 계획법
1. 위상 정렬을 이용하여 인접 차수가 0인 작업부터 큐에 삽입한다.
(이때, 문제에서 선행 관계를 갖는 작업이 하나도 없는 작업이 한드시 하나 이상 존재한다는 조건을 간과하고 1번 작업만을 고려하여서 첫 시도를 틀렸다.)
2. 각 작업의 선행관계를 맺는 작업의 동작 시간(t)와 이전까지 수행한 작업 시간(et)를 더한 값을 각 작업의 et에 최댓값을 업데이트한다.
(현재 작업의 et는 선행 작업의 et의 최대값을 저장하므로 동적 계획법을 수행한다고 할 수 있다.)
3. 모든 작업이 완료되기까지의 최소 시간은 각 작업이 완료되기까지 걸린 최종 시간을 저장한 et의 최대값임을 알 수 있다.
4. ans 는 각 작업에서의 et의 최대값으로 업데이트한다.
 */
public class BOJ2056 {
    static class BOJ2056_operation {
        int n,t,d,et;
        ArrayList<Integer> adj_op;

        BOJ2056_operation(int n) {
            this.n = n;
            this.t = 0;
            this.d = 0;
            this.et = 0;
            this.adj_op = new ArrayList<>();
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static ArrayList<BOJ2056_operation> operations;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        Queue<BOJ2056_operation> q = new LinkedList<>();

        for(BOJ2056_operation o : operations) {
            if(o.d == 0) q.add(o);
        }

        while(!q.isEmpty()) {
            BOJ2056_operation now = q.poll();

            now.et += now.t;
            ans = Math.max(ans, now.et);

            for(Integer op : now.adj_op) {
                operations.get(op - 1).et = Math.max(operations.get(op - 1).et, now.et);
                operations.get(op - 1).d--;

                if(operations.get(op - 1).d == 0) q.add(operations.get(op - 1));
            }
        }

        System.out.println(ans);
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        operations = new ArrayList<>();

        ans = -1;

        for(int i = 1; i <= N; i++) {
            operations.add(new BOJ2056_operation(i));
        }

        for(int i = 1; i <= N; i++) {
            String[] input = br.readLine().split(" ");

            operations.get(i-1).t = Integer.parseInt(input[0]);

            for(int j = 1; j <= Integer.parseInt(input[1]); j++) {
                operations.get(Integer.parseInt(input[j+1]) - 1).adj_op.add(i);
                operations.get(i-1).d++;
            }
        }
    }
}
