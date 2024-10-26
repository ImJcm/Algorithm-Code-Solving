package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
숨바꼭질 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	63344	17928	12445	25.766%
문제
수빈이는 동생과 숨바꼭질을 하고 있다. 수빈이는 현재 점 N(0 ≤ N ≤ 100,000)에 있고, 동생은 점 K(0 ≤ K ≤ 100,000)에 있다. 수빈이는 걷거나 순간이동을 할 수 있다. 만약, 수빈이의 위치가 X일 때 걷는다면 1초 후에 X-1 또는 X+1로 이동하게 된다. 순간이동을 하는 경우에는 1초 후에 2*X의 위치로 이동하게 된다.

수빈이와 동생의 위치가 주어졌을 때, 수빈이가 동생을 찾을 수 있는 가장 빠른 시간이 몇 초 후인지 그리고, 가장 빠른 시간으로 찾는 방법이 몇 가지 인지 구하는 프로그램을 작성하시오.

입력
첫 번째 줄에 수빈이가 있는 위치 N과 동생이 있는 위치 K가 주어진다. N과 K는 정수이다.

출력
첫째 줄에 수빈이가 동생을 찾는 가장 빠른 시간을 출력한다.

둘째 줄에는 가장 빠른 시간으로 수빈이가 동생을 찾는 방법의 수를 출력한다.

예제 입력 1
5 17
예제 출력 1
4
2
출처
문제를 만든 사람: baekjoon
비슷한 문제
1697번. 숨바꼭질
13549번. 숨바꼭질 3
13913번. 숨바꼭질 4
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
알고리즘 핵심
BFS
1. 이동한 위치의 좌표, 중복되는 위치의 방문 횟수, 도달 시간 정보를 가지는 class
2. 현재 위치에서 -1, +1, *2를 했을 때 이동할 위치의 방문여부에 따라 로직을 달리한다.
3. 이동할 위치가 이미 방문한 경우, 최초 도달 시간과 이동한 후의 시간을 비교하여 최초 도달 시간보다 큰 경우 continue, 같은 경우 중복 방문 횟수를 업데이트한다.
(이때, 이미 방문한 곳을 재방문하는 이동의 경우에 최초 도달 시간보다 작은 재방문은 없다)
4. 이동할 위치가 방문하지 않은 경우, 해당 위치를 방문 처리하고 중복횟수와 최초 도달 시간을 업데이트하고 queue에 추가한다.
5. K인 동생이 위치한 곳에 처음으로 도달한 경우 최초 도달 시간과 도달하는 방법의 수를 업데이트하고 출력한다.
 */
public class BOJ12851 {
    static class BOJ12851_pos {
        int n,d,t;

        BOJ12851_pos(int n, int d, int t) {
            this.n = n;
            this.d = d;
            this.t = t;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,K,ans_t,ans_d;
    static BOJ12851_pos[] maps;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        bfs(maps[N]);

        System.out.println(ans_t);
        System.out.println(ans_d);
    }

    private static void bfs(BOJ12851_pos s) {
        Queue<BOJ12851_pos> q = new LinkedList<>();
        boolean[] visited = new boolean[100001];

        s.d += 1;
        q.add(s);
        visited[s.n] = true;

        while(!q.isEmpty()) {
            BOJ12851_pos now = q.poll();

            if(now.n == K) {
                ans_t = now.t;
                ans_d = now.d;
                break;
            }

            int nn;
            for(int i = 0; i < 3; i++) {
                nn = now.n;
                switch (i) {
                    case 0:
                        nn -= 1;
                        break;
                    case 1:
                        nn += 1;
                        break;
                    case 2:
                        nn *= 2;
                        break;
                }

                if(nn < 0 || nn > 100000) continue;

                if(visited[nn]) {
                    if(now.t + 1 > maps[nn].t) continue;
                    else if(now.t + 1 == maps[nn].t) maps[nn].d += now.d;
                    /*
                    // 이미 방문할 곳을 재방문 시에는 최초 방문한 시간보다 적은 경우는 없다.
                    else {
                        maps[nn].d = now.d;
                        maps[nn].t = now.t + 1;
                        q.add(maps[nn]);
                    }*/
                } else {
                    visited[nn] = true;
                    maps[nn].d += now.d;
                    maps[nn].t = now.t + 1;
                    q.add(maps[nn]);
                }
            }
        }
    }


    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);

        ans_t = -1;
        ans_d = -1;

        maps = new BOJ12851_pos[100001];

        for(int i = 0; i <= 100000; i++) {
            maps[i] = new BOJ12851_pos(i,0,0);
        }
    }
}
