package BFS;

/*
뱀과 사다리 게임

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	38823	14114	10770	33.448%
문제
뱀과 사다리 게임을 즐겨 하는 큐브러버는 어느 날 궁금한 점이 생겼다.

주사위를 조작해 내가 원하는 수가 나오게 만들 수 있다면, 최소 몇 번만에 도착점에 도착할 수 있을까?

게임은 정육면체 주사위를 사용하며, 주사위의 각 면에는 1부터 6까지 수가 하나씩 적혀있다. 게임은 크기가 10×10이고, 총 100개의 칸으로 나누어져 있는 보드판에서 진행된다. 보드판에는 1부터 100까지 수가 하나씩 순서대로 적혀져 있다.

플레이어는 주사위를 굴려 나온 수만큼 이동해야 한다. 예를 들어, 플레이어가 i번 칸에 있고, 주사위를 굴려 나온 수가 4라면, i+4번 칸으로 이동해야 한다. 만약 주사위를 굴린 결과가 100번 칸을 넘어간다면 이동할 수 없다. 도착한 칸이 사다리면, 사다리를 타고 위로 올라간다. 뱀이 있는 칸에 도착하면, 뱀을 따라서 내려가게 된다. 즉, 사다리를 이용해 이동한 칸의 번호는 원래 있던 칸의 번호보다 크고, 뱀을 이용해 이동한 칸의 번호는 원래 있던 칸의 번호보다 작아진다.

게임의 목표는 1번 칸에서 시작해서 100번 칸에 도착하는 것이다.

게임판의 상태가 주어졌을 때, 100번 칸에 도착하기 위해 주사위를 굴려야 하는 횟수의 최솟값을 구해보자.

입력
첫째 줄에 게임판에 있는 사다리의 수 N(1 ≤ N ≤ 15)과 뱀의 수 M(1 ≤ M ≤ 15)이 주어진다.

둘째 줄부터 N개의 줄에는 사다리의 정보를 의미하는 x, y (x < y)가 주어진다. x번 칸에 도착하면, y번 칸으로 이동한다는 의미이다.

다음 M개의 줄에는 뱀의 정보를 의미하는 u, v (u > v)가 주어진다. u번 칸에 도착하면, v번 칸으로 이동한다는 의미이다.

1번 칸과 100번 칸은 뱀과 사다리의 시작 또는 끝이 아니다. 모든 칸은 최대 하나의 사다리 또는 뱀을 가지고 있으며, 동시에 두 가지를 모두 가지고 있는 경우는 없다. 항상 100번 칸에 도착할 수 있는 입력만 주어진다.

출력
100번 칸에 도착하기 위해 주사위를 최소 몇 번 굴려야 하는지 출력한다.

예제 입력 1
3 7
32 62
42 68
12 98
95 13
97 25
93 37
79 27
75 19
49 47
67 17
예제 출력 1
3
5를 굴려 6으로 이동한다.
6을 굴려 12로 이동한다. 이 곳은 98로 이동하는 사다리가 있기 때문에, 98로 이동한다.
2를 굴려 100으로 이동한다.
예제 입력 2
4 9
8 52
6 80
26 42
2 72
51 19
39 11
37 29
81 3
59 5
79 23
53 7
43 33
77 21
예제 출력 2
5
5를 굴려 6으로 이동하고, 사다리를 이용해 80으로 이동한다.
6을 굴려 86으로
6을 또 굴려 92로
6을 또 굴려 98로 이동하고
2를 굴려 100으로 이동한다.
출처
문제를 번역한 사람: baekjoon
빠진 조건을 찾은 사람: jh05013
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
BFS 방식으로 queue에 넣어야할 board는 주사위와 최소 횟수를 고려하면 6,5,...,1 순으로 탐색 board로 결정한다.
visited 배열을 통해 이미 방문한지 여부를 검사한다. 이때 방문여부를 검사하는 것을 통해 이미 최소한의 횟수로 도달한 방법이
있다는 것을 의미하기 때문이다.

위의 방법처럼 visited로 앞서서 먼저 board에 도착하면 최소 다이스를 사용했다는 판단은 잘못되었다.

따라서, visited를 없애고 중복으로 board에 도착했을 때, 사용한 dice의 개수를 board graph 내부에 저장하고 도착하기 전 board의
최소 다이스의 개수 + 1 or 자기 자신의 dice의 개수 중 최소값을 업데이트하고 q가 모두 비워지면 100 번 board의 used_dice_cnt를 출력한다.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class BOJ16928 {
    public static class BOJ16928_board {
        int connect_board;
        int used_dice_cnt;

        BOJ16928_board() {
            this.connect_board = 0;
            this.used_dice_cnt = Integer.MAX_VALUE;
        }

        void setConnect_board(int c_b) {
            this.connect_board = c_b;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BOJ16928_board[] boards;
    static int N,M,cnt = 0;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        bfs(1);

        cnt = boards[100].used_dice_cnt;
        System.out.println(cnt);
    }

    static void bfs(int start) {
        Queue<Integer> q = new LinkedList();

        q.offer(start);
        boards[start].used_dice_cnt = 0;

        while(!q.isEmpty()) {
            int now = q.poll();

            for(int i=6;i>0;i--) {
                if(now + i > 100 || boards[now + i].used_dice_cnt < boards[now].used_dice_cnt + 1) {
                    continue;
                }

                boards[now + i].used_dice_cnt = Math.min(boards[now + i].used_dice_cnt, boards[now].used_dice_cnt + 1);

                int cb = boards[now + i].connect_board;
                if(cb != 0) {
                    q.offer(cb);
                    boards[cb].used_dice_cnt = boards[now + i].used_dice_cnt;
                } else {
                    q.offer(now + i);
                }
            }

        }
    }

    static void init_setting() throws IOException{
        boards = new BOJ16928_board[101];

        for(int i=0;i<101;i++) {
            boards[i] = new BOJ16928_board();
        }

        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        for(int i=0;i<N;i++) {
            input = br.readLine().split(" ");

            boards[Integer.parseInt(input[0])].setConnect_board(Integer.parseInt(input[1]));
        }

        for(int i=0;i<M;i++) {
            input = br.readLine().split(" ");

            boards[Integer.parseInt(input[0])].setConnect_board(Integer.parseInt(input[1]));
        }
    }
}
