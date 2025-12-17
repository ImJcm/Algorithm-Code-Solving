package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

/*
점프 점프

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	21984	8185	6459	36.481%
문제
재환이가 1×N 크기의 미로에 갇혀있다. 미로는 1×1 크기의 칸으로 이루어져 있고, 각 칸에는 정수가 하나 쓰여 있다. i번째 칸에 쓰여 있는 수를 Ai라고 했을 때, 재환이는 Ai이하만큼 오른쪽으로 떨어진 칸으로 한 번에 점프할 수 있다. 예를 들어, 3번째 칸에 쓰여 있는 수가 3이면, 재환이는 4, 5, 6번 칸 중 하나로 점프할 수 있다.

재환이는 지금 미로의 가장 왼쪽 끝에 있고, 가장 오른쪽 끝으로 가려고 한다. 이때, 최소 몇 번 점프를 해야 갈 수 있는지 구하는 프로그램을 작성하시오. 만약, 가장 오른쪽 끝으로 갈 수 없는 경우에는 -1을 출력한다.

입력
첫째 줄에 N(1 ≤ N ≤ 1,000)이 주어진다. 둘째 줄에는 Ai (0 ≤ Ai ≤ 100)가 주어진다.

출력
재환이가 최소 몇 번 점프를 해야 가장 오른쪽 끝 칸으로 갈 수 있는지 출력한다. 만약, 가장 오른쪽 끝으로 갈 수 없는 경우에는 -1을 출력한다.

예제 입력 1
10
1 2 0 1 3 2 1 5 4 2
예제 출력 1
5
출처
문제를 만든 사람: baekjoon
문제의 오타를 찾은 사람: jh05013
알고리즘 분류
다이나믹 프로그래밍
그래프 이론
그래프 탐색
너비 우선 탐색
 */
public class BOJ11060 {
    static class BOJ11060_jump{
        int i;
        int jump;

        BOJ11060_jump(int i, int j) {
            this.i = i;
            this.jump = j;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,min_jump = Integer.MAX_VALUE;
    static int[] miro,dp;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
        solve_2();
    }

    //bottom-up
    //dp[0] = 0을 기준으로 miro[0]에서 점프가 가능한 구간의 최소 점프 횟수를 업데이트한다.
    static void solve() {
        dp[0] = 0;

        for(int i=0;i<N;i++) {
            if(dp[i] == Integer.MAX_VALUE) break;       //다음 공간으로 점프할 상황이 없는 경우
            for(int j=1;j<=miro[i];j++) {
                if(i+j >= N) continue;
                dp[i+j] = Math.min(dp[i+j], dp[i] + 1);
            }
        }

        System.out.println(dp[N-1] == Integer.MAX_VALUE ? -1 : dp[N-1]);
    }


    //bfs
    static void solve_2() {
        bfs(new BOJ11060_jump(0,0));
        System.out.println(min_jump == Integer.MAX_VALUE ? -1 : min_jump);
    }

    //visited를 적용하려면 최소 점프 횟수를 만족해야 하기 때문에 for(int j=1;j<=miro[now.i]가 아닌 for(int j=miro[now.i];j>=1)로
    //설정하여 최소 점프를 만족하는 이동을 우선으로하여 visited가 잘 적용될 수 있도록 한다.
    static void bfs(BOJ11060_jump jump) {
        Queue<BOJ11060_jump> q = new LinkedList<>();
        boolean[] visited = new boolean[N];

        q.offer(jump);
        visited[jump.i] = true;

        while(!q.isEmpty()) {
            BOJ11060_jump now = q.poll();

            if(now.i == N - 1) {
                min_jump = Math.min(now.jump,min_jump);
            }

            for(int j=miro[now.i];j>=1;j--) {
                int next_i = now.i + j;

                if(next_i >= N || visited[next_i]) continue;
                visited[next_i] = true;
                q.offer(new BOJ11060_jump(next_i, now.jump + 1));
            }
        }
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        miro = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        dp = new int[N];
        Arrays.fill(dp,Integer.MAX_VALUE);
    }
}
