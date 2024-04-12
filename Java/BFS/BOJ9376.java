package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import static java.lang.Integer.compare;

/*
탈옥 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	11166	2294	1603	27.055%
문제
상근이는 감옥에서 죄수 두 명을 탈옥시켜야 한다. 이 감옥은 1층짜리 건물이고, 상근이는 방금 평면도를 얻었다.

평면도에는 모든 벽과 문이 나타나있고, 탈옥시켜야 하는 죄수의 위치도 나타나 있다. 감옥은 무인 감옥으로 죄수 두 명이 감옥에 있는 유일한 사람이다.

문은 중앙 제어실에서만 열 수 있다. 상근이는 특별한 기술을 이용해 제어실을 통하지 않고 문을 열려고 한다. 하지만, 문을 열려면 시간이 매우 많이 걸린다. 두 죄수를 탈옥시키기 위해서 열어야 하는 문의 개수를 구하는 프로그램을 작성하시오. 문을 한 번 열면 계속 열린 상태로 있는다.

입력
첫째 줄에 테스트 케이스의 개수가 주어진다. 테스트 케이스의 수는 100개를 넘지 않는다.

첫째 줄에는 평면도의 높이 h와 너비 w가 주어진다. (2 ≤ h, w ≤ 100) 다음 h개 줄에는 감옥의 평면도 정보가 주어지며, 빈 공간은 '.', 지나갈 수 없는 벽은 '*', 문은 '#', 죄수의 위치는 '$'이다.

상근이는 감옥 밖을 자유롭게 이동할 수 있고, 평면도에 표시된 죄수의 수는 항상 두 명이다. 각 죄수와 감옥의 바깥을 연결하는 경로가 항상 존재하는 경우만 입력으로 주어진다.

출력
각 테스트 케이스마다 두 죄수를 탈옥시키기 위해서 열어야 하는 문의 최솟값을 출력한다.

예제 입력 1
3
5 9
****#****
*..#.#..*
****.****
*$#.#.#$*
*********
5 11
*#*********
*$*...*...*
*$*.*.*.*.*
*...*...*.*
*********.*
9 9
*#**#**#*
*#**#**#*
*#**#**#*
*#**.**#*
*#*#.#*#*
*$##*##$*
*#*****#*
*.#.#.#.*
*********
예제 출력 1
4
0
9
출처


ICPC > Regionals > Europe > Northwestern European Regional Contest > Benelux Algorithm Programming Contest > BAPC 2013 J번

문제를 번역한 사람: baekjoon
데이터를 추가한 사람: doju, playsworld16, yukino
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
데이크스트라
최단 경로
0-1 너비 우선 탐색
 */
/*
시행착오
1. 두 죄수를 기점으로 동시에 탈출까지의 최소 비용(문 연 횟수)을 업데이트하여 탈출로에서의 최소에 해당하는 비용을 출력하는 방법으로 구현
-> (하단에 class BOJ9376_wrongAnswer 참고) 최소 비용을 만족하지 못하여 실패
2. 최소 비용을 만족하기 위해 방문여부를 지우고 해당 위치에서의 최소 비용에 해당하는 경로가 도달하는 경우 업데이트하는 방법
-> 방문여부를 체크하지 않을 시, 무한으로 bfs를 수행하게 되며, 각 room의 최소 횟수의 문 연 값이 잘못된 값으로 업데이트가 진행된다.
따라서, 방문여부를 체크하면서 우선순위 큐를 통해 두 죄수의 문 연 횟수가 최소 값을 만족하는 이동을 우선적으로 수행할 수 있도록 변경하였다.
하지만, 위의 방법도 해결방법이 아니였다.

그래서, 풀이 방법을 참고하여 해결하려고 한다. (해결 방법을 도출하는 것이 어렵다고 생각한다..)
1. 죄수 1번의 문을 여는 최소 비용의 경로 구하기
2. 죄수 2번의 문을 여는 최소 비용의 경로 구하기
3. 외부에서 내부로의 문을 여는 최소 비용 경로 구하기

3가지의 bfs를 수행하여 구한 정보는 각 위치에서의 문을 연 값이 두 죄수를 탈출하기 위한 최소 비용에 해당하는 것이다.
이때, 해당하는 위치가 "문"인 경우, 3가지 방법에서 중첩하므로 -2를 적용하고, 외부에서 사용할 경로를 확장 해야한다.
정리하면, 중간에서 만나는 위치를 P로 고정하면, 최소 비용은 두 죄수를 P로 끌고오는 비용 + 외부에서 P로 이동하는 최소비용이 된다.

출처 - https://www.acmicpc.net/board/view/91113 - (풀이 접근법), https://g-egg.tistory.com/85 - (자세한 설명)
 */
public class BOJ9376 {
    static class BOJ9376_room implements Comparable<BOJ9376_room> {
        int h,w;
        int open_door;
        char state;

        BOJ9376_room(int h, int w, int od, char s) {
            this.h = h;
            this.w = w;
            this.open_door = od;
            this.state = s;
        }

        @Override
        public int compareTo(BOJ9376_room room) {
            return compare(this.open_door, room.open_door);
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int t,h,w,min_open_doors;
    static BOJ9376_room[] prisoners;
    static BOJ9376_room[][] rooms;
    static int[][][] visited;
    static StringBuilder sb = new StringBuilder();
    static int[][] direction = {{-1,0}, {0,1}, {1,0}, {0,-1}};


    public static void main(String[] args) throws IOException {
        solve();
    }

    static void solve() throws IOException{
        t = Integer.parseInt(br.readLine());

        for(int i = 0; i < t ;i++) {
            init_setting();

            for(int j=0;j<3;j++) {
                bfs(prisoners[j],j);
            }

            check_min_doors();
        }

        System.out.println(sb.toString());
    }

    static void bfs(BOJ9376_room prisoner, int prisoner_number) {
        PriorityQueue<BOJ9376_room> pq = new PriorityQueue<>();

        pq.offer(prisoner);
        visited[prisoner.h][prisoner.w][prisoner_number] = 0;

        while(!pq.isEmpty()) {
            BOJ9376_room now = pq.poll();

            for(int[] d : direction) {
                int nh = now.h + d[0];
                int nw = now.w + d[1];

                if(nh < 0 || nh > h+1 || nw < 0 || nw > w+1) continue;
                if(visited[nh][nw][prisoner_number] != -1 || rooms[nh][nw].state == '*') continue;
                if(rooms[nh][nw].state == '#') {
                    visited[nh][nw][prisoner_number] = visited[now.h][now.w][prisoner_number] + 1;
                    pq.offer(new BOJ9376_room(nh,nw,now.open_door + 1, rooms[nh][nw].state));
                } else {
                    visited[nh][nw][prisoner_number] = visited[now.h][now.w][prisoner_number];
                    pq.offer(new BOJ9376_room(nh,nw,now.open_door, rooms[nh][nw].state));
                }
            }
        }
    }

    static void check_min_doors() {
        for(int r=1;r<=h;r++) {
            for(int c=1;c<=w;c++) {
                int sum = 0;
                boolean flag = true;

                if(rooms[r][c].state == '*') continue;
                if(rooms[r][c].state == '#') sum -= 2;

                for(int i=0;i<3;i++) {
                    if(visited[r][c][i] == -1) {
                        flag = false;
                        break;
                    }
                    sum += visited[r][c][i];
                }

                if(flag) min_open_doors = Math.min(min_open_doors, sum);
            }
        }
        sb.append(min_open_doors).append("\n");
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");
        min_open_doors = Integer.MAX_VALUE;

        h = Integer.parseInt(input[0]);
        w = Integer.parseInt(input[1]);

        prisoners = new BOJ9376_room[3];
        rooms = new BOJ9376_room[h+2][w+2];
        visited = new int[h+2][w+2][3];

        int prisoner = 0;

        for(int r=0;r<=h+1;r++) {
            for(int c=0;c<=w+1;c++) {
                rooms[r][c] = new BOJ9376_room(r,c,0,'.');
                for(int i=0;i<3;i++) {
                    visited[r][c][i] = -1;
                }
            }
        }

        for(int r=1;r<=h;r++) {
            input = br.readLine().split("");
            for(int c=1;c<=w;c++) {
                char s = input[c-1].charAt(0);

                rooms[r][c].state = s;

                if(s == '$') {
                    prisoners[prisoner++] = rooms[r][c];
                }
            }
        }

        // 외부에서의 경로 탐색 추가
        prisoners[prisoner] = rooms[0][0];
    }
}

class BOJ9376_wrongAnswer2{
    static class BOJ9376_room implements Comparable<BOJ9376_room> {
        int prison;
        int h,w;
        int[] each_prisoner_min_open_door;
        char state;

        BOJ9376_room(int p, int h, int w, int[] mod, char s) {
            this.prison = p;
            this.h = h;
            this.w = w;
            this.state = s;
            this.each_prisoner_min_open_door = mod;
        }

        @Override
        public int compareTo(BOJ9376_room room) {
            if(Arrays.stream(this.each_prisoner_min_open_door).sum() < 0) {
                return 1;
            } else if(Arrays.stream(this.each_prisoner_min_open_door).sum() > 0) {
                if(Arrays.stream(room.each_prisoner_min_open_door).sum() < 0) {
                    return -1;
                } else {
                    if(Arrays.stream(this.each_prisoner_min_open_door).sum() < Arrays.stream(room.each_prisoner_min_open_door).sum()) {
                        return -1;
                    } else if(Arrays.stream(this.each_prisoner_min_open_door).sum() > Arrays.stream(room.each_prisoner_min_open_door).sum()){
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
            return 0;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int t,h,w,min_open_doors;
    static BOJ9376_room[] prisoners;
    static BOJ9376_room[][] rooms;
    static boolean[][][] visited;
    static StringBuilder sb = new StringBuilder();
    static int[][] direction = {{-1,0}, {0,1}, {1,0}, {0,-1}};


    public static void main(String[] args) throws IOException {
        solve();
    }

    static void solve() throws IOException{
        t = Integer.parseInt(br.readLine());

        for(int i = 0; i < t ;i++) {
            init_setting();

            bfs();

            check_min_doors();
        }

        System.out.println(sb.toString());
    }

    static void bfs() {
        PriorityQueue<BOJ9376_room> pq = new PriorityQueue<>();

        for(int i=0;i<prisoners.length;i++) {
            prisoners[i].each_prisoner_min_open_door[i] = 0;
            pq.offer(prisoners[i]);
        }

        while(!pq.isEmpty()) {
            BOJ9376_room now = pq.poll();

            for(int[] d : direction) {
                int other_prisoner = now.prison == 0 ? 1 : 0;
                int nh = now.h + d[0];
                int nw = now.w + d[1];

                if(nh < 1 || nh > h || nw < 1 || nw > w || rooms[nh][nw].state == '*' || visited[nh][nw][now.prison]) continue;
                visited[nh][nw][now.prison] = true;
                if(rooms[nh][nw].state == '.' || rooms[nh][nw].state == '$') {
                    rooms[nh][nw].each_prisoner_min_open_door[now.prison] = Math.min(now.each_prisoner_min_open_door[now.prison], rooms[nh][nw].each_prisoner_min_open_door[now.prison]);
                } else if(rooms[nh][nw].state == '#'){
                    rooms[nh][nw].each_prisoner_min_open_door[now.prison] = Math.min(now.each_prisoner_min_open_door[now.prison] + 1, rooms[nh][nw].each_prisoner_min_open_door[now.prison]);
                    rooms[nh][nw].state = '.';
                }
                pq.offer(new BOJ9376_room(now.prison,nh,nw,rooms[nh][nw].each_prisoner_min_open_door,rooms[nh][nw].state));
            }
        }
    }

    static void check_min_doors() {
        // =
        for(int r=1;r<=h;r+=(h-1)) {
            for(int c=1;c<=w;c++) {
                if(rooms[r][c].each_prisoner_min_open_door[0] == Integer.MAX_VALUE || rooms[r][c].each_prisoner_min_open_door[1] == Integer.MAX_VALUE) continue;
                min_open_doors = Math.min(rooms[r][c].each_prisoner_min_open_door[0] + rooms[r][c].each_prisoner_min_open_door[1], min_open_doors);
            }
        }

        // ||
        for(int c=1;c<=w;c+=(w-1)) {
            for(int r=1;r<=h;r++) {
                if(rooms[r][c].each_prisoner_min_open_door[0] == Integer.MAX_VALUE || rooms[r][c].each_prisoner_min_open_door[1] == Integer.MAX_VALUE) continue;
                min_open_doors = Math.min(rooms[r][c].each_prisoner_min_open_door[0] + rooms[r][c].each_prisoner_min_open_door[1], min_open_doors);
            }
        }
        sb.append(min_open_doors).append("\n");
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");
        min_open_doors = Integer.MAX_VALUE;

        h = Integer.parseInt(input[0]);
        w = Integer.parseInt(input[1]);

        prisoners = new BOJ9376_room[2];
        rooms = new BOJ9376_room[h+1][w+1];
        visited = new boolean[h+1][w+1][2];

        int prisoner = 0;

        for(int r=1;r<=h;r++) {
            input = br.readLine().split("");
            for(int c=1;c<=w;c++) {
                char s = input[c-1].charAt(0);

                int[] open_doors_cnt = new int[2];
                for(int i=0;i<prisoners.length;i++) {
                    open_doors_cnt[i] = Integer.MAX_VALUE;
                }
                rooms[r][c] = new BOJ9376_room(-1,r,c,open_doors_cnt,s);
                visited[r][c][0] = visited[r][c][1] = false;

                if(s == '*') visited[r][c][0] = visited[r][c][1] = true;
                if(s == '$') {
                    rooms[r][c].prison = prisoner;
                    prisoners[prisoner++] = rooms[r][c];
                }
            }
        }
    }
}

/*
실패한 코드 - 반례
1
6 7
****#**
*##..**
*.**.**
*$###**
***$***
*******
//answer = 4
//output = 5
디버깅을 통해 오답이 나온 이유는 방문여부를 통해 최소로 문을 연 횟수의 이동이 막히는 문제가 있었다.
반례에서 (4,2)에서 위쪽 방향으로 우선하여 올라가서 (2,5)를 5번의 이동으로 도착하고, 오른쪽 방향으로 이동 시, 문 연 횟수가 최소를 만족하지만 이미 방문한 위치로 판단하여 최소 문 연 횟수를
업데이트하지 못하는 것이다. 따라서, 방문여부를 저장하는 visited를 제거하여 each_prisoner_min_open_door를 통해 다음 이동이 최소 횟수로 문 연 것을 만족하는 이동만 queue에 넣어 bfs를
진행하도록 해결하면 될 것으로 생각한다.
 */
class BOJ9376_wrongAnswer {
    static class BOJ9376_room {
        int prison;
        int h,w;
        int[] each_prisoner_min_open_door;
        char state;

        BOJ9376_room(int p, int h, int w, int[] mod, char s) {
            this.prison = p;
            this.h = h;
            this.w = w;
            this.state = s;
            this.each_prisoner_min_open_door = mod;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int t,h,w,min_open_doors;
    static BOJ9376_room[] prisoners;
    static BOJ9376_room[][] rooms;
    static boolean[][][] visited;
    static StringBuilder sb = new StringBuilder();
    static int[][] direction = {{-1,0}, {0,1}, {1,0}, {0,-1}};


    public static void main(String[] args) throws IOException {
        solve();
    }

    static void solve() throws IOException{
        t = Integer.parseInt(br.readLine());

        for(int i = 0; i < t ;i++) {
            init_setting();

            bfs();

            check_min_doors();
        }

        System.out.println(sb.toString());
    }

    static void bfs() {
        Queue<BOJ9376_room> q = new LinkedList<>();

        for(int i=0;i<prisoners.length;i++) {
            prisoners[i].each_prisoner_min_open_door[i] = 0;
            q.offer(prisoners[i]);
            visited[prisoners[i].h][prisoners[i].w][i] = true;
        }

        while(!q.isEmpty()) {
            BOJ9376_room now = q.poll();

            for(int[] d : direction) {
                int nh = now.h + d[0];
                int nw = now.w + d[1];

                if(nh < 1 || nh > h || nw < 1 || nw > w || rooms[nh][nw].state == '*' || visited[nh][nw][now.prison]) continue;
                visited[nh][nw][now.prison] = true;
                if(rooms[nh][nw].state == '.' || rooms[nh][nw].state == '$') {
                    rooms[nh][nw].each_prisoner_min_open_door[now.prison] = now.each_prisoner_min_open_door[now.prison];
                } else if(rooms[nh][nw].state == '#'){
                    rooms[nh][nw].each_prisoner_min_open_door[now.prison] = now.each_prisoner_min_open_door[now.prison] + 1;
                    rooms[nh][nw].state = '.';
                }
                q.offer(new BOJ9376_room(now.prison,nh,nw,rooms[nh][nw].each_prisoner_min_open_door,rooms[nh][nw].state));
            }
        }
    }

    static void check_min_doors() {
        // =
        for(int r=1;r<=h;r+=(h-1)) {
            for(int c=1;c<=w;c++) {
                if(rooms[r][c].each_prisoner_min_open_door[0] == Integer.MAX_VALUE || rooms[r][c].each_prisoner_min_open_door[1] == Integer.MAX_VALUE) continue;
                min_open_doors = Math.min(rooms[r][c].each_prisoner_min_open_door[0] + rooms[r][c].each_prisoner_min_open_door[1], min_open_doors);
            }
        }

        // ||
        for(int c=1;c<=w;c+=(w-1)) {
            for(int r=1;r<=h;r++) {
                if(rooms[r][c].each_prisoner_min_open_door[0] == Integer.MAX_VALUE || rooms[r][c].each_prisoner_min_open_door[1] == Integer.MAX_VALUE) continue;
                min_open_doors = Math.min(rooms[r][c].each_prisoner_min_open_door[0] + rooms[r][c].each_prisoner_min_open_door[1], min_open_doors);
            }
        }
        sb.append(min_open_doors).append("\n");
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");
        min_open_doors = Integer.MAX_VALUE;

        h = Integer.parseInt(input[0]);
        w = Integer.parseInt(input[1]);

        prisoners = new BOJ9376_room[2];
        rooms = new BOJ9376_room[h+1][w+1];
        visited = new boolean[h+1][w+1][2];

        int prisoner = 0;

        for(int r=1;r<=h;r++) {
            input = br.readLine().split("");
            for(int c=1;c<=w;c++) {
                char s = input[c-1].charAt(0);

                int[] open_doors_cnt = new int[2];
                for(int i=0;i<prisoners.length;i++) {
                    open_doors_cnt[i] = Integer.MAX_VALUE;
                }
                rooms[r][c] = new BOJ9376_room(-1,r,c,open_doors_cnt,s);
                visited[r][c][0] = visited[r][c][1] = false;

                if(s == '*') visited[r][c][0] = visited[r][c][1] = true;
                if(s == '$') {
                    rooms[r][c].prison = prisoner;
                    prisoners[prisoner++] = rooms[r][c];
                }
            }
        }
    }
}
