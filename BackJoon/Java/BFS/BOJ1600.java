package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/*
말이 되고픈 원숭이

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	61772	14597	9210	20.961%
문제
동물원에서 막 탈출한 원숭이 한 마리가 세상구경을 하고 있다. 그 녀석은 말(Horse)이 되기를 간절히 원했다. 그래서 그는 말의 움직임을 유심히 살펴보고 그대로 따라 하기로 하였다. 말은 말이다. 말은 격자판에서 체스의 나이트와 같은 이동방식을 가진다. 다음 그림에 말의 이동방법이 나타나있다. x표시한 곳으로 말이 갈 수 있다는 뜻이다. 참고로 말은 장애물을 뛰어넘을 수 있다.

 	x	 	x
x	 	 	 	x
 	 	말
x	 	 	 	x
 	x	 	x
근데 원숭이는 한 가지 착각하고 있는 것이 있다. 말은 저렇게 움직일 수 있지만 원숭이는 능력이 부족해서 총 K번만 위와 같이 움직일 수 있고, 그 외에는 그냥 인접한 칸으로만 움직일 수 있다. 대각선 방향은 인접한 칸에 포함되지 않는다.

이제 원숭이는 머나먼 여행길을 떠난다. 격자판의 맨 왼쪽 위에서 시작해서 맨 오른쪽 아래까지 가야한다. 인접한 네 방향으로 한 번 움직이는 것, 말의 움직임으로 한 번 움직이는 것, 모두 한 번의 동작으로 친다. 격자판이 주어졌을 때, 원숭이가 최소한의 동작으로 시작지점에서 도착지점까지 갈 수 있는 방법을 알아내는 프로그램을 작성하시오.

입력
첫째 줄에 정수 K가 주어진다. 둘째 줄에 격자판의 가로길이 W, 세로길이 H가 주어진다. 그 다음 H줄에 걸쳐 W개의 숫자가 주어지는데, 0은 아무것도 없는 평지, 1은 장애물을 뜻한다. 장애물이 있는 곳으로는 이동할 수 없다. 시작점과 도착점은 항상 평지이다. W와 H는 1이상 200이하의 자연수이고, K는 0이상 30이하의 정수이다.

출력
첫째 줄에 원숭이의 동작수의 최솟값을 출력한다. 시작점에서 도착점까지 갈 수 없는 경우엔 -1을 출력한다.

예제 입력 1
1
4 4
0 0 0 0
1 0 0 0
0 0 1 0
0 1 0 0
예제 출력 1
4
예제 입력 2
2
5 2
0 0 1 1 0
0 0 1 1 0
예제 출력 2
-1
출처
문제의 오타를 찾은 사람: kdr06006
문제를 만든 사람: ntopia
잘못된 데이터를 찾은 사람: tncks0121
데이터를 추가한 사람: wider93, wksms21, yunsubaek
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
처음 k번의 말(night)의 이동을 어떻게 처리할 수 있을까 고민하다가 말의 이동이 가능할 때 말의 이동을 수행하고 추가로 말의 이동을 하지 않을 때의
이동을 추가로 수행하여 말의 이동 여부를 visited를 3차원으로 생성하여 [x][y][말의 이동횟수]로 방문여부를 체크해주면 되지 않을까 생각하여
아래와 같은 코드를 구상하였다.

처음 아이디어를 떠올렸을 때 말의 이동과 일반이동으로 너무많은 이동 데이터가 queue에 삽입되어 메모리 초과가 발생하지 않을까 생각했었다.
하지만, visited로 말의 이동 횟수에 따른 이동을 방문 여부를 체크함으로써 말의 이동으로 인한 이동과 일반 이동을 동시에 체크할 수 있었다.
 */
public class BOJ1600 {
    static class BOJ1600_board implements Comparable<BOJ1600_board> {
        int x,y;
        int move;
        int k;

        BOJ1600_board(int x,int y,int move, int k) {
            this.x = x;
            this.y = y;
            this.move = move;
            this.k = k;
        }

        @Override
        public int compareTo(BOJ1600_board o) {
            return this.move - o.move;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int W,H,K;
    static int[][] map;
    static boolean[][][] visited;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};
    static int[][] night_direction = {{-1,-2},{-2,-1},{-2,1},{-1,2},{1,2},{2,1},{2,-1},{1,-2}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        //System.out.println(bfs_queue());
        System.out.println(bfs_priorityQueue());
    }

    /*
        우선순위를 사용하여 최소의 이동 횟수를 가진 이동을 앞선 순위로 bfs를 수행할 수 있도록 하여 더욱 빠른 시간을 기대하였지만, 아니였다.
        개인적인 생각으로 이유를 생각해보면 메모리 사용을 줄이는 것으로 봐서 기대한 효과는 보지만 최소 값을 가지는 이동을 우선순위를 앞에 배치하는
        것이 아닌 K가 높은 것을 우선순위가 높게 하면 더욱 최소한의 이동을 보장할 것이라고 생각한다?
        우선 순위 결정을 k가 높은 순으로 내림차순하여 정렬을 진행하도록 하였지만 결과는 틀렸다고 나온다.
        그 이유는 k가 높은 순으로 이동하는 것은 최소 이동을 보장하지 못한다는 것이라고 생각한다. 따라서, 일반 ㅃueue를 사용하였을 때보다
        시간이 늘어난 이유는 많은 이동 정보 값이 PriorityQueue에 삽입되고 이를 정렬하는 과정에서 시간이 늘어난 것으로 본다.

        @Override
        public int compareTo(BOJ1600_board o) {
            //return this.move - o.move;
            return o.k - this.k;
        }

        메모리 : 65772KB , 시간 736ms
     */
    static int bfs_priorityQueue() {
        PriorityQueue<BOJ1600_board> q = new PriorityQueue<>();

        q.offer(new BOJ1600_board(1,1,0,0));
        visited[1][1][0] = true;

        while(!q.isEmpty()) {
            BOJ1600_board now = q.poll();

            if(now.x == H && now.y == W) {
                return now.move;
            }

            if(now.k + 1 <= K) {
                for(int[] d : night_direction) {
                    int nx = now.x + d[0];
                    int ny = now.y + d[1];

                    if(nx < 1 || nx > H || ny < 1 || ny > W || visited[nx][ny][now.k + 1] || map[nx][ny] == 1) continue;
                    visited[nx][ny][now.k + 1] = true;
                    q.offer(new BOJ1600_board(nx,ny,now.move + 1, now.k + 1));
                }
            }

            for(int[] d : direction) {
                int nx = now.x + d[0];
                int ny = now.y + d[1];

                if(nx < 1 || nx > H || ny < 1 || ny > W || visited[nx][ny][now.k] || map[nx][ny] == 1) continue;
                visited[nx][ny][now.k] = true;
                q.offer(new BOJ1600_board(nx,ny,now.move + 1, now.k));
            }
        }
        return -1;
    }

    /*
        메모리 : 93104KB , 시간 : 600ms
     */
    static int bfs_queue() {
        Queue<BOJ1600_board> q = new LinkedList<>();

        q.offer(new BOJ1600_board(1,1,0,0));
        visited[1][1][0] = true;

        while(!q.isEmpty()) {
            BOJ1600_board now = q.poll();

            if(now.x == H && now.y == W) {
                return now.move;
            }

            if(now.k + 1 <= K) {
                for(int[] d : night_direction) {
                    int nx = now.x + d[0];
                    int ny = now.y + d[1];

                    if(nx < 1 || nx > H || ny < 1 || ny > W || visited[nx][ny][now.k + 1] || map[nx][ny] == 1) continue;
                    visited[nx][ny][now.k + 1] = true;
                    q.offer(new BOJ1600_board(nx,ny,now.move + 1, now.k + 1));
                }
            }

            for(int[] d : direction) {
                int nx = now.x + d[0];
                int ny = now.y + d[1];

                if(nx < 1 || nx > H || ny < 1 || ny > W || visited[nx][ny][now.k] || map[nx][ny] == 1) continue;
                visited[nx][ny][now.k] = true;
                q.offer(new BOJ1600_board(nx,ny,now.move + 1, now.k));
            }
        }
        return -1;
    }

    static void init_setting() throws IOException {
        String[] input;
        K = Integer.parseInt(br.readLine());
        input = br.readLine().split(" ");
        W = Integer.parseInt(input[0]);
        H = Integer.parseInt(input[1]);

        map = new int[H+1][W+1];
        visited = new boolean[H+1][W+1][K+1];

        for(int i=1;i<=H;i++) {
            input = br.readLine().split(" ");
            for(int j=1;j<=W;j++) {
                int w = Integer.parseInt(input[j-1]);

                map[i][j] = w;
                for(int k=0;k<=K;k++) {
                    visited[i][j][k] = false;
                }
                if(w == 1) {
                    for(int k=0;k<=K;k++) {
                        visited[i][j][k] = true;
                    }
                }
            }
        }
    }
}
