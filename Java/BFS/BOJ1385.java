package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
벌집 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	893	256	166	26.391%
문제
지민이는 벌집에 빠졌다. 출구가 어디에 있는지는 아는데, 어떻게 가야 하는지를 모른다.

그곳에 있는 벌들에 의하면 방의 번호는 다음과 같이 붙는다.



지민이는 지금 a번 방에 있다. b번 방이 출구인데 어떻게 이동해야 할까? 집에는 민식이가 기다리고 있기 때문에, 가장 빠르게 탈출하기 위해 최단거리로 움직이고 싶다.

입력
첫째 줄에는 당신이 있는 방의 번호 a와 출구가 있는 방의 번호 b가 주어진다.1 ≤ a, b ≤ 1,000,000)

출력
첫째 줄에 탈출을 위해 최단거리로 지나는 방의 번호를 공백으로 구분해 출력한다.

예제 입력 1
10 15
예제 출력 1
10 3 4 14 15
출처
데이터를 만든 사람: baekjoon
문제를 만든 사람: xhark
알고리즘 분류
구현
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
알고리즘 핵심
BFS
문제를 읽고 접근 방식은 각 벌집의 구역과 맞닿은 구역을 특정할 수 있으면 bfs를 통해 최단경로를 구할 수 있다고 생각하였다.
하지만, 벌집의 구역 간의 인접한 구역을 지정하는 것에서 막히게 되었다.

구역간에 인접한 구역이 무엇인지 특정짓기 위해 1부터 60번대 까지 임시로 그린 그림과 인접한 형태를 표를 통해 규칙성을 찾고자 하였다.
                41
            40      42
        39      22      43
    38      21      23      44
61      20      9       24      45
    37      8       10      25
60      19      2       11      46
    36      7       3       26
59      18      1       12      47
    35      6       4       27
58      17      5       13      48
    34      16      14      28
57      33      15      29      49
    56      32      30      50
        55      31      51
            54      52
                50
이 그림을 통해 외각 라인마다 구역의 개수가 등차수열로 늘어난다는 것을 알 수 있었다.
2~7 : 1계층 - 6개
8~19 : 2계층 - 12개
...
N계층 - 6 * N

총 N계층일 때 구역의 개수 : 1 + {6 * N * (N + 1) / 2}
(등차수열의 합공식)

하지만 위 정보로는 구역 간의 인접한 구역이 무엇인지 특정하기는 어려웠다.

그래서 각 구역마다 인접한 구역의 넘버링을 표시하여 규칙을 찾아보려고 하였다.

M   0   1   2   3   4   5
1   2   3   4   5   6   7
2   9   10  3   1   7   8
3   10  11  12  4   1   2
4   3   12  13  14  5   1
5   1   4   14  15  16  6
6   7   1   5   16  17  18
7   8   2   1   6   18  19
8   21  9   2   7   19  20
9   22  23  10  2   8   21
10  23  24  11  3   2   9
...

위 표를 보았을 때 규칙성을 찾을 수 없어 결국 다른 정답 코드를 참고하여 구역 간에 연관성을 나타내는 배열 정보를 알 수 있었다.

https://velog.io/@ddongh1122/%EB%B0%B1%EC%A4%80-1385%EB%B2%88-%EB%B2%8C%EC%A7%91

코드를 분석하였을 때, 한 구역을 기준으로 6개의 방향으로 인접한 구역을 2차원 배열로 나타낼 수 있다.

(x-1,y-1) (x-1,y)
(x,y-1)   (x,y)   (x,y+1)
          (x+1,y) (x+1,y+1)

위와 같은 형태로 인접한 구역을 나타낼 수 있으며 문제에서 나타낸 구역의 넘버링의 제한이 1_000_000이므로 1_000_000까지 넘버링을 수행할 수 있는
2차원 배열의 크기를 측정하기 위해 외곽 라인 계층이 얼마나 필요한지 계산하여 구성하도록 하였다.

구역을 넘버링하는 과정에서 (0,1)방향으로 구역을 설정하는 과정에서는 K-1개를 설정한다. 그외는 K개를 수행한다.
이 의미는 각 계층마다 각 외곽 방향마다 필요한 구역의 개수를 나타내는데 (0,1)방향으로 구역 넘버링은 하나의 구역의 넘버링 과정이 필요하지 않기 때문이다.
ex)
8(x-2,y-1)  9(x-2,y)
7(x-1,y-1)  2(x-1,y) 10(x-1,y+1)
6(x,y-1)    1(x,y)   3(x,y+1)   11(x,y+2)
            5(x+1,y) 4(x+1,y+1)

1 -> 2이후 (0,1)의 방향으로 3을 추가하면 잘못된 구역 넘버링이다. + 6개의 넘버링 이후 6에서 (-1,0)의 방향으로 K = 2만큼 7,8을 추가 후,
(0,1)방향으로 9를 K-1개인 1개를 추가한다.

좌표상으로 둘러쌓인 형태를 나타내기 위해 (0,1)방향에서 외곽 계층 만큼 구역 넘버링을 추가히지 않고 K-1개만 추가하는 이유이다.

bfs시 각 구역에 최초 도달 시 최단 경로임을 만족하기 때문에 구역에 도달 시 이전 구역의 좌표를 저장하여 목적지 구역에 도달 시 이동했던 최단 경로를
역추적하여 경로를 출력할 수 있도록한다.
(이 부분에서 경로 정보를 별도로 누적하여 저장하는 경우 메모리 초과가 발생할 수 있기 때문이다.)
 */
public class BOJ1385 {
    static class BOJ1385_pos {
        int n,x,y,px,py;

        BOJ1385_pos(int n, int x, int y, int px, int py) {
            this.n = n;
            this.x = x;
            this.y = y;
            this.px = px;
            this.py = py;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int a,b;
    static BOJ1385_pos[][] honeycomb;
    static int[][] ds = {{-1,0},{0,1},{1,1},{1,0},{0,-1},{-1,-1}};
    static BOJ1385_pos start,end,track_route;

    static final int MAX_N = 1_000_000;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        make_honeycomb();
        bfs(start);
        print_route();
    }

    private static void print_route() {
        String s = Integer.toString(track_route.n);

        while(true) {
            int px = track_route.px;
            int py = track_route.py;

            if(px == -1 && py == -1) {
                System.out.println(s);
                return;
            }

            track_route = honeycomb[px][py];

            s = track_route.n + " " + s;
        }
    }

    private static void bfs(BOJ1385_pos s) {
        Queue<BOJ1385_pos> q = new LinkedList<>();
        boolean[][] visited = new boolean[honeycomb.length][honeycomb.length];

        q.add(s);
        visited[s.x][s.y] = true;

        while(!q.isEmpty()) {
            BOJ1385_pos now = q.poll();

            if(now.x == end.x && now.y == end.y) {
                track_route = now;
                return;
            }

            for(int[] d : ds) {
                int nx = now.x + d[0];
                int ny = now.y + d[1];

                if(honeycomb[nx][ny] == null || visited[nx][ny]) continue;

                visited[nx][ny] = true;
                honeycomb[nx][ny].px = now.x;
                honeycomb[nx][ny].py = now.y;
                q.add(honeycomb[nx][ny]);
            }
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        a = Integer.parseInt(input[0]);
        b = Integer.parseInt(input[1]);

        int out_line = (cal_outline() / 100) + 1 ;

        honeycomb = new BOJ1385_pos[out_line * 200][out_line * 200];
    }

    private static void make_honeycomb() {
        int x = honeycomb.length / 2;
        int y = honeycomb.length / 2;
        honeycomb[x][y] = new BOJ1385_pos(1,x,y,-1,-1);

        int l = 1;
        int num = 1;

        start = new BOJ1385_pos(a,x,y,-1,-1);
        end = new BOJ1385_pos(b,x,y,-1,-1);

        while(true) {
            for(int d = 0; d < 6; d++) {
                for(int i = 0; i < (d == 1 ? l - 1 : l); i++) {
                    int nx = x + ds[d][0];
                    int ny = y + ds[d][1];

                    honeycomb[nx][ny] = new BOJ1385_pos(++num,nx,ny,-1,-1);

                    if(num > MAX_N) return;

                    if(num == a) {
                        start.x = nx;
                        start.y = ny;
                    }

                    if(num == b) {
                        end.x = nx;
                        end.y = ny;
                    }

                    x = nx;
                    y = ny;
                }
            }
            l++;
        }
    }

    private static int cal_outline() {
        int sum = 1;
        int out_layer = 1;

        while(true) {
            sum += (6 * out_layer++);

            if(sum > MAX_N) break;
        }

        return out_layer;
    }
}
