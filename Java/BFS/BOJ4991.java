package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
로봇 청소기 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	11318	3853	2532	31.356%
문제
오늘은 직사각형 모양의 방을 로봇 청소기를 이용해 청소하려고 한다. 이 로봇 청소기는 유저가 직접 경로를 설정할 수 있다.

방은 크기가 1×1인 정사각형 칸으로 나누어져 있으며, 로봇 청소기의 크기도 1×1이다. 칸은 깨끗한 칸과 더러운 칸으로 나누어져 있으며, 로봇 청소기는 더러운 칸을 방문해서 깨끗한 칸으로 바꿀 수 있다.

일부 칸에는 가구가 놓여져 있고, 가구의 크기도 1×1이다. 로봇 청소기는 가구가 놓여진 칸으로 이동할 수 없다.

로봇은 한 번 움직일 때, 인접한 칸으로 이동할 수 있다. 또, 로봇은 같은 칸을 여러 번 방문할 수 있다.

방의 정보가 주어졌을 때, 더러운 칸을 모두 깨끗한 칸으로 만드는데 필요한 이동 횟수의 최솟값을 구하는 프로그램을 작성하시오.

입력
입력은 여러 개의 테스트케이스로 이루어져 있다.

각 테스트 케이스의 첫째 줄에는 방의 가로 크기 w와 세로 크기 h가 주어진다. (1 ≤ w, h ≤ 20) 둘째 줄부터 h개의 줄에는 방의 정보가 주어진다. 방의 정보는 4가지 문자로만 이루어져 있으며, 각 문자의 의미는 다음과 같다.

.: 깨끗한 칸
*: 더러운 칸
x: 가구
o: 로봇 청소기의 시작 위치
더러운 칸의 개수는 10개를 넘지 않으며, 로봇 청소기의 개수는 항상 하나이다.

입력의 마지막 줄에는 0이 두 개 주어진다.

출력
각각의 테스트 케이스마다 더러운 칸을 모두 깨끗한 칸으로 바꾸는 이동 횟수의 최솟값을 한 줄에 하나씩 출력한다. 만약, 방문할 수 없는 더러운 칸이 존재하는 경우에는 -1을 출력한다.

예제 입력 1
7 5
.......
.o...*.
.......
.*...*.
.......
15 13
.......x.......
...o...x....*..
.......x.......
.......x.......
.......x.......
...............
xxxxx.....xxxxx
...............
.......x.......
.......x.......
.......x.......
..*....x....*..
.......x.......
10 10
..........
..o.......
..........
..........
..........
.....xxxxx
.....x....
.....x.*..
.....x....
.....x....
0 0
예제 출력 1
8
49
-1
출처
ICPC > Regionals > Asia Pacific > Japan > Japan Domestic Contest > 2005 Japan Domestic Contest F번

알고리즘 분류
그래프 이론
브루트포스 알고리즘
그래프 탐색
너비 우선 탐색
비트마스킹
 */
/*
기존의 코드는 로봇 청소기의 위치에서 가장 가까운 더러운 방을 우선으로 이동하여 거리를 누적하여 더하는 식으로 진행된다. 하지만,
두개의 틀린 코드를 다양한 테스트 코드를 디버깅 해보면서 틀린 이유를 추측해볼 수 있었는데 동일한 최소 거리를 가진 더러운 방이 존재하는 경우
어느 곳을 먼저 가야지 최종적으로 최소 이동 거리를 만족하는지 보장할 수 없다는 것이다.

그래서, 힌트에서 주어진 알고리즘으로 브루트포스 알고리즘이 주어진 것으로 생각된다.
사용할 수 있는 방법으로
1. 로봇의 위치에서 모든 더러운 방까지의 최소 이동 거리를 bfs를 수행하여 저장
2. 각 더러운 방에서 다른 더러운 방까지의 최소 이동 거리를 bfs를 수행하여 저장
3. 모든 더러운 방이 2번 과정을 모두 마치면, dist[20][20][20][20] = dist[current_room.h][current_room.w][next_dirty_room.h][next_dirty_room.w]
이동거리를 저장한 배열을 얻을 수 있다.
4. 처음 로봇 위치에서 1~10번의 더러운 방 까지의 이동 거리 + 각 1~10번 위치에서 자신을 제외한 다른 더러운 방까지의 거리를 누적하여 더한다.
5. 최종적으로 최소 이동한 거리를 답으로 도출하는 것
이 방법으로 bfs + Bruteforce를 만족하는 로직을 수행할 수 있다고 생각한다.

bruteforce를 이용한 dfs를 통해 모든 더러운 방을 방문하여 최소 이동거리를 구할 수 있도록 하였다.

정답 코드를 만들고 다른 정답 코드가 궁금하여 검색했을 때 내가 생각한 것과 일치하여 뿌듯했다.
 */
public class BOJ4991 {
    static class BOJ4991_room {
        int w,h;
        int move;

        BOJ4991_room(int h, int w, int move) {
            this.h = h;
            this.w = w;
            this.move = move;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int W,H,V, min_distance = Integer.MAX_VALUE;
    static boolean flag = true;
    static boolean[][][] visited;
    static boolean[] check_visited;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};
    static BOJ4991_room robot_cleaner;
    static String[][] rooms;
    static ArrayList<BOJ4991_room> dirty_rooms;
    static int[][][][] dist;

    public static void main(String[] args) throws IOException {
        solve();
    }

    static void solve() throws IOException {
        while(true) {
            V = 0;
            init_setting();
            if(!flag) {
                break;
            }
            bfs(robot_cleaner);
            for(BOJ4991_room r : dirty_rooms) {
                bfs(r);
            }
            check_min_dist();
        }
    }

    static void bfs(BOJ4991_room r) {
        Queue<BOJ4991_room> q = new LinkedList<>();

        q.offer(r);
        r.move = 0;
        visited[r.h][r.w][V++] = true;

        while(!q.isEmpty()) {
            BOJ4991_room now = q.poll();

            if(rooms[now.h][now.w].equals("*")) {
                dist[r.h][r.w][now.h][now.w] = now.move;
            }

            for(int[] d : direction) {
                int nh = now.h + d[0];
                int nw = now.w + d[1];

                if(nh < 1 || nh > H || nw < 1 || nw > W || visited[nh][nw][V-1] || rooms[nh][nw].equals("x")) continue;
                visited[nh][nw][V-1] = true;
                q.offer(new BOJ4991_room(nh,nw,now.move + 1));
            }
        }
    }

    static void check_min_dist() {
        min_distance = Integer.MAX_VALUE;
        check_visited = new boolean[dirty_rooms.size()];
        Arrays.fill(check_visited, false);

        for(int i=0;i<dirty_rooms.size();i++) {
            int from_robot_to_dirty_init_dist = dist[robot_cleaner.h][robot_cleaner.w][dirty_rooms.get(i).h][dirty_rooms.get(i).w];
            if(from_robot_to_dirty_init_dist == Integer.MAX_VALUE) {
                min_distance = -1;
                break;
            }
            check_visited[i] = true;
            dfs(1, dirty_rooms.get(i), from_robot_to_dirty_init_dist);
            check_visited[i] = false;
        }
        System.out.println(min_distance);
    }

    static void dfs(int depth, BOJ4991_room room, int distance) {
        if(depth == dirty_rooms.size()) {
            min_distance = Math.min(distance, min_distance);
            return;
        }

        for(int i=0;i<dirty_rooms.size();i++) {
            if(check_visited[i]) continue;
            BOJ4991_room next_room = dirty_rooms.get(i);
            check_visited[i] = true;
            dfs(depth + 1, next_room, distance + dist[room.h][room.w][next_room.h][next_room.w]);
            check_visited[i] = false;

        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");
        W = Integer.parseInt(input[0]);
        H = Integer.parseInt(input[1]);

        if(W == 0 && H == 0) {
            flag = false;
            return;
        }

        rooms = new String[H+1][W+1];
        visited = new boolean[H+1][W+1][11];
        dirty_rooms = new ArrayList<>();
        dist = new int[21][21][21][21];

        for(int a=0;a<21;a++) {
            for(int b=0;b<21;b++) {
                for(int c=0;c<21;c++) {
                    Arrays.fill(dist[a][b][c],Integer.MAX_VALUE);
                }
            }
        }

        for(int h=1;h<=H;h++) {
            input = br.readLine().split("");
            for(int w=1;w<=W;w++) {
                rooms[h][w] = input[w-1];

                if(Objects.equals(rooms[h][w], "o")) {
                    robot_cleaner = new BOJ4991_room(h,w,0);
                }

                if(Objects.equals(rooms[h][w], "*")) {
                    dirty_rooms.add(new BOJ4991_room(h,w,0));
                }

                for(int i=0;i<11;i++) {
                    visited[h][w][i] = false;
                }
            }
        }
    }
}

// 메모리 초과
class BOJ4991_wrongAnswer2 {
    static class BOJ4991_room {
        int w,h;
        int move;
        String state;

        BOJ4991_room(int h, int w, int move, String state) {
            this.h = h;
            this.w = w;
            this.move = move;
            this.state = state;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int W,H,V = 0, min_distance;
    static boolean flag = true, check = true;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};
    static BOJ4991_room robot_cleaner;
    static BOJ4991_room[][] rooms;
    static ArrayList<BOJ4991_room> dirty_rooms;

    public static void main(String[] args) throws IOException {
        solve();
    }

    static void solve() throws IOException {
        while(true) {
            init_setting();
            if(!flag) {
                break;
            }
            while(!dirty_rooms.isEmpty()) {
                V = (V == 0 ? 1 : 0);
                bfs(robot_cleaner,V);
                clean_dirty_room();
            }
            System.out.println(min_distance);
        }

    }

    static void bfs(BOJ4991_room robot,int v) {
        Queue<BOJ4991_room> q = new LinkedList<>();

        for(int h=1;h<=H;h++) {
            for(int w=1;w<=W;w++) {
                rooms[h][w].move = Integer.MAX_VALUE;
            }
        }

        q.offer(robot);
        robot.move = 0;

        while(!q.isEmpty()) {
            BOJ4991_room now = q.poll();

            for(int[] d : direction) {
                int nh = now.h + d[0];
                int nw = now.w + d[1];

                if(nh < 1 || nh > H || nw < 1 || nw > W || rooms[nh][nw].state.equals("x")) continue;
                if(rooms[nh][nw].move < now.move + 1) continue;

                rooms[nh][nw].move = now.move + 1;
                q.offer(rooms[nh][nw]);
            }
        }
    }

    static void clean_dirty_room() {
        check = true;
        BOJ4991_room m = new BOJ4991_room(0,0,Integer.MAX_VALUE,".");
        for(BOJ4991_room r : dirty_rooms) {
            if(r.move == Integer.MAX_VALUE) {
                check = false;
                break;
            }
            if(Objects.equals(r.state, "*") && m.move > r.move) {
                m = r;
            }
        }

        if(dirty_rooms.isEmpty()) {
            m.move = 0;
        }

        if(check) {
            min_distance += m.move;
            robot_cleaner = m;
            m.state = ".";
            dirty_rooms.remove(m);
        } else {
            min_distance = -1;
            dirty_rooms.clear();
        }

    }

    static void init_setting() throws IOException {
        min_distance = 0;
        String[] input = br.readLine().split(" ");
        W = Integer.parseInt(input[0]);
        H = Integer.parseInt(input[1]);

        if(W == 0 && H == 0) {
            flag = false;
            return;
        }

        rooms = new BOJ4991_room[H+1][W+1];
        dirty_rooms = new ArrayList<>();

        for(int h=1;h<=H;h++) {
            input = br.readLine().split("");
            for(int w=1;w<=W;w++) {
                rooms[h][w] = new BOJ4991_room(h,w,Integer.MAX_VALUE,input[w-1]);

                if(Objects.equals(rooms[h][w].state, "o")) {
                    robot_cleaner = rooms[h][w];
                    rooms[h][w].state = ".";
                }

                if(Objects.equals(rooms[h][w].state, "*")) {
                    dirty_rooms.add(rooms[h][w]);
                }
            }
        }
    }
}

class BOJ4991_wrongAnswer{
    static class BOJ4991_room {
        int w,h;
        int move;
        String state;

        BOJ4991_room(int h, int w, int move, String state) {
            this.h = h;
            this.w = w;
            this.move = move;
            this.state = state;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int W,H,V = 0, min_distance;
    static boolean flag = true, check = true;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};
    static int[][] visited;
    static BOJ4991_room robot_cleaner;
    static BOJ4991_room[][] rooms;
    static ArrayList<BOJ4991_room> dirty_rooms;

    public static void main(String[] args) throws IOException {
        solve();
    }

    static void solve() throws IOException {
        while(true) {
            init_setting();
            if(!flag) {
                break;
            }
            while(!dirty_rooms.isEmpty()) {
                V = (V == 0 ? 1 : 0);
                bfs(robot_cleaner,V);
                clean_dirty_room();
            }
            System.out.println(min_distance);
        }

    }

    static void bfs(BOJ4991_room robot,int v) {
        Queue<BOJ4991_room> q = new LinkedList<>();

        q.offer(robot);
        robot.move = 0;
        visited[robot.h][robot.w] = v;

        while(!q.isEmpty()) {
            BOJ4991_room now = q.poll();

            for(int[] d : direction) {
                int nh = now.h + d[0];
                int nw = now.w + d[1];

                if(nh < 1 || nh > H || nw < 1 || nw > W || rooms[nh][nw].state.equals("x") || visited[nh][nw] == v) continue;
                visited[nh][nw] = v;
                rooms[nh][nw].move = now.move + 1;
                q.offer(rooms[nh][nw]);
            }
        }
    }

    static void clean_dirty_room() {
        check = true;
        BOJ4991_room m = new BOJ4991_room(0,0,Integer.MAX_VALUE,".");
        for(BOJ4991_room r : dirty_rooms) {
            if(r.move == Integer.MAX_VALUE) {
                check = false;
                break;
            }
            if(Objects.equals(r.state, "*") && m.move > r.move) {
                m = r;
            }
        }

        if(dirty_rooms.isEmpty()) {
            m.move = 0;
        }

        if(check) {
            min_distance += m.move;
            robot_cleaner = m;
            m.state = ".";
            dirty_rooms.remove(m);
        } else {
            min_distance = -1;
            dirty_rooms.clear();
        }

    }

    static void init_setting() throws IOException {
        min_distance = 0;
        String[] input = br.readLine().split(" ");
        W = Integer.parseInt(input[0]);
        H = Integer.parseInt(input[1]);

        if(W == 0 && H == 0) {
            flag = false;
            return;
        }

        rooms = new BOJ4991_room[H+1][W+1];
        dirty_rooms = new ArrayList<>();
        visited = new int[H+1][W+1];

        for(int h=1;h<=H;h++) {
            input = br.readLine().split("");
            for(int w=1;w<=W;w++) {
                rooms[h][w] = new BOJ4991_room(h,w,Integer.MAX_VALUE,input[w-1]);
                visited[h][w] = V;

                if(Objects.equals(rooms[h][w].state, "o")) {
                    robot_cleaner = rooms[h][w];
                    rooms[h][w].state = ".";
                }

                if(Objects.equals(rooms[h][w].state, "*")) {
                    dirty_rooms.add(rooms[h][w]);
                }
            }
        }
    }
}
