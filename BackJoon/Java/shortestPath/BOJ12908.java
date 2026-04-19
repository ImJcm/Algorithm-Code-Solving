package shortestPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
텔레포트 3

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	1080	495	404	46.383%
문제
수빈이는 크기가 무한대인 격자판 위에 살고 있다. 격자판의 각 점은 두 정수의 쌍 (x, y)로 나타낼 수 있다.

제일 처음에 수빈이의 위치는 (xs, ys)이고, 집이 위치한 (xe, ye)로 이동하려고 한다.

수빈이는 두 가지 방법으로 이동할 수 있다. 첫 번째 방법은 점프를 하는 것이다. 예를 들어 (x, y)에 있는 경우에 (x+1, y), (x-1, y), (x, y+1), (x, y-1)로 이동할 수 있다. 점프는 1초가 걸린다.

두 번째 방법은 텔레포트를 사용하는 것이다. 텔레포트를 할 수 있는 방법은 총 세 가지가 있으며, 미리 정해져 있다. 텔레포트는 네 좌표 (x1, y1), (x2, y2)로 나타낼 수 있으며, (x1, y1)에서 (x2, y2)로 또는 (x2, y2)에서 (x1, y1)로 이동할 수 있다는 것이다. 텔레포트는 10초가 걸린다.

수빈이의 위치와 집의 위치가 주어졌을 때, 집에 가는 가장 빠른 시간을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 xs와 ys가, 둘째 줄에 xe, ye가 주어진다. (0 ≤ xs, ys, xe, ye ≤ 1,000,000,000)

셋째 줄부터 세 개의 줄에는 텔레포트의 정보 x1, y1, x2, y2가 주어진다. (0 ≤ x1, y1, x2, y2 ≤ 1,000,000,000)

입력으로 주어지는 모든 좌표 8개는 서로 다르다.

출력
수빈이가 집에 가는 가장 빠른 시간을 출력한다.

예제 입력 1
3 3
4 5
1000 1001 1000 1002
1000 1003 1000 1004
1000 1005 1000 1006
예제 출력 1
3
예제 입력 2
0 0
20 20
1 1 18 20
1000 1003 1000 1004
1000 1005 1000 1006
예제 출력 2
14
예제 입력 3
0 0
20 20
1000 1003 1000 1004
18 20 1 1
1000 1005 1000 1006
예제 출력 3
14
예제 입력 4
10 10
10000 20000
1000 1003 1000 1004
3 3 10004 20002
1000 1005 1000 1006
예제 출력 4
30
예제 입력 5
3 7
10000 30000
3 10 5200 4900
12212 8699 9999 30011
12200 8701 5203 4845
예제 출력 5
117
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
그래프 이론
브루트포스 알고리즘
최단 경로
플로이드–워셜
 */
/*
알고리즘 핵심
1. N = 10^9으로 모든 좌표에서 다른 모든 좌표의 최단 거리를 구하는 것은 불가능 - N이 매우 크기 때문에 (시간 복잡도 = O(N^3))
2. 점프의 행위는 한칸씩 이동하여 시간을 측정하는 것으로 볼 수 있지만 결국은 abs(x1-x2) + abs(y1-y2)와 같다.
3. 출발지, 도착지, 텔레포트가 가능한 지점 6개인 8개의 지점간에 플로이드 워샬을 이용하여 최단 시간을 측정한다.
4. 최소 측정 시간은 N = 10^9이므로 int의 범위를 넘어갈 수 있으므로 long 타입 사용
 */
public class BOJ12908 {
    static class BOJ12908_pos {
        int x,y;
        BOJ12908_pos teleport;

        BOJ12908_pos(int x, int y) {
            this.x = x;
            this.y = y;
            this.teleport = null;
        }

        void setTeleport(BOJ12908_pos t) {
            this.teleport = t;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static long ans;
    static BOJ12908_pos s,e;
    static BOJ12908_pos[] teleports;
    static long[][] Floyd_Warshall;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int a = 0; a < 8; a++) {
            for(int b = 0; b < 8; b++) {
                Floyd_Warshall[a][b] = Floyd_Warshall[b][a] = diff_abs(teleports[a], teleports[b]);
            }
        }

        for(int k = 0; k < 8; k++) {
            for(int a = 0; a < 8; a++) {
                for(int b = 0; b < 8; b++) {
                    if(a == b || a == k || b == k) continue;
                    Floyd_Warshall[a][b] = Math.min(Floyd_Warshall[a][b],
                            Floyd_Warshall[a][k] + Floyd_Warshall[k][b]);
                }
            }
        }

        System.out.println(Floyd_Warshall[6][7]);
    }

    static long diff_abs(BOJ12908_pos a, BOJ12908_pos b) {
        int able_teleport = a.teleport == b || b.teleport == a ? 10 : Integer.MAX_VALUE;
        return Math.min(Math.abs(a.x - b.x) + Math.abs(a.y - b.y), able_teleport);
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        s = new BOJ12908_pos(Integer.parseInt(input[0]), Integer.parseInt(input[1]));

        input = br.readLine().split(" ");

        e = new BOJ12908_pos(Integer.parseInt(input[0]), Integer.parseInt(input[1]));

        ans = Integer.MAX_VALUE;

        teleports = new BOJ12908_pos[8];
        Floyd_Warshall = new long[8][8];

        for(int i = 0; i < 3; i++) {
            input = br.readLine().split(" ");

            for(int j = 0; j < 2; j++) {
                int x = Integer.parseInt(input[2 * j]);
                int y = Integer.parseInt(input[2 * j + 1]);
                teleports[2 * i + j] = new BOJ12908_pos(x,y);
            }
            teleports[2 * i].setTeleport(teleports[2 * i + 1]);
            teleports[2 * i + 1].setTeleport(teleports[2 * i]);
        }

        teleports[6] = s;
        teleports[7] = e;
    }
}
