package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
성곽 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	11419	5580	3979	47.761%
문제


대략 위의 그림과 같이 생긴 성곽이 있다. 굵은 선은 벽을 나타내고, 점선은 벽이 없어서 지나다닐 수 있는 통로를 나타낸다. 이러한 형태의 성의 지도를 입력받아서 다음을 계산하는 프로그램을 작성하시오.

이 성에 있는 방의 개수
가장 넓은 방의 넓이
하나의 벽을 제거하여 얻을 수 있는 가장 넓은 방의 크기
위의 예에서는 방은 5개고, 가장 큰 방은 9개의 칸으로 이루어져 있으며, 위의 그림에서 화살표가 가리키는 벽을 제거하면 16인 크기의 방을 얻을 수 있다.

성은 M × N(1 ≤ M, N ≤ 50)개의 정사각형 칸으로 이루어진다. 성에는 최소 두 개의 방이 있어서, 항상 하나의 벽을 제거하여 두 방을 합치는 경우가 있다.

입력
첫째 줄에 두 정수 N, M이 주어진다. 다음 M개의 줄에는 N개의 정수로 벽에 대한 정보가 주어진다. 벽에 대한 정보는 한 정수로 주어지는데, 서쪽에 벽이 있을 때는 1을, 북쪽에 벽이 있을 때는 2를, 동쪽에 벽이 있을 때는 4를, 남쪽에 벽이 있을 때는 8을 더한 값이 주어진다. 참고로 이진수의 각 비트를 생각하면 쉽다. 따라서 이 값은 0부터 15까지의 범위 안에 있다.

출력
첫째 줄에 1의 답을, 둘째 줄에 2의 답을, 셋째 줄에 3의 답을 출력한다.

예제 입력 1
7 4
11 6 11 6 3 10 6
7 9 6 13 5 15 5
1 10 12 7 13 7 5
13 11 10 8 10 12 13
예제 출력 1
5
9
16
출처
Olympiad > International Olympiad in Informatics > IOI 1994 > Day 1 2번

데이터를 추가한 사람: akaishuichi, superior95
문제의 오타를 찾은 사람: pichulia
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
비트마스킹
 */
/*
처음 문제를 읽고 접근한 방식은 한번의 bfs로 visited[m][n][wall_crush]의 wall_crush의 0,1로 구분하여 벽을 부수고 최대 넓이를 구할 수 있다고 생각하여 코드를 구현하였지만,
visited[][][wall_crush]로 구분하더라도 bfs 내부 로직에서 같은 그룹에서 벽을 부순 이동이 같은 그룹에서 다른 그룹으로의 이동에 영향을 미치기 때문에 잘못된 생각이였다.

따라서, 벽을 부수지 않는 것으로 그룹별로 bfs를 수행하여 <그룹번호, 방의 개수>를 구하여 문제에서 요구하는 1,2,3을 구하도록 코드를 수정하였다.
 */
public class BOJ2234 {
    static class BOJ2234_room {
        int m,n;
        int group;
        int crush_wall;
        int wall;

        BOJ2234_room(int m,int n, int g, int c_w, int w) {
            this.m = m;
            this.n = n;
            this.group = g;
            this.crush_wall = c_w;
            this.wall = w;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,max_rooms = 0;
    static boolean[][][] visited;
    static BOJ2234_room[][] rooms;

    static Map<Integer,Integer> groups;
    static int[][] direction = {{0,-1,1},{-1,0,2},{0,1,4},{1,0,8}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        // 인접한 방의 그룹 넘버를 지정하고 해당 그룹에 해당하는 방의 개수를 groups에 저장
        int group_number = 1;
        for(int m=1;m<=M;m++) {
            for(int n=1;n<=N;n++) {
                if(visited[m][n][0]) continue;
                groups.put(group_number, bfs(m,n,group_number++,0));
            }
        }

        answer_1();
        answer_2();
        answer_3();


    }

    //성 내부의 방의 개수는 groups의 key의 최대 number를 의미한다.
    static void answer_1() {
        System.out.println(groups.size());
    }

    //가장 넓은 방의 넓이는 groups에 저장한 그룹 별 방의 개수를 저장하였기 때문에 group을 돌면서 가장 넓은 크기를 출력한다.
    static void answer_2() {
        int max_size_group = 0;
        for(Map.Entry e : groups.entrySet()) {
            max_size_group = Math.max((int) e.getValue(), max_size_group);
        }
        System.out.println(max_size_group);
    }

    //각 방을 돌면서 인접한 벽이 존재하면 현재 위치와 다음 벽을 넘은 위치와의 그룹 번호를 비교하여 다르면 두 그룹에 속한 방의 넓이를 더하여
    //최대값을 출력한다.
    static void answer_3() {
        for(int m=1;m<=M;m++) {
            for(int n=1;n<=N;n++) {
                for(int[] d : direction) {
                    int nm = m + d[0];
                    int nn = n + d[1];

                    if(nm < 1 || nm > M || nn < 1 || nn > N) continue;
                    if((rooms[m][n].wall & d[2]) > 0 && rooms[m][n].group != rooms[nm][nn].group) {
                        max_rooms = Math.max(max_rooms, groups.get(rooms[m][n].group) + groups.get(rooms[nm][nn].group));
                    }
                }
            }
        }
        System.out.println(max_rooms);
    }

    static int bfs(int m, int n, int g_n, int c_w) {
        int group_cnts = 0;
        Queue<BOJ2234_room> q = new LinkedList<>();

        q.offer(rooms[m][n]);
        rooms[m][n].group = g_n;
        visited[m][n][c_w] = true;

        while(!q.isEmpty()) {
            BOJ2234_room now = q.poll();
            group_cnts++;

            for(int[] d : direction) {
                int nm = now.m + d[0];
                int nn = now.n + d[1];
                int gn = now.group;
                int cw = now.crush_wall;

                if(nm < 1 || nm > M || nn < 1 || nn > N || visited[nm][nn][cw] || (rooms[now.m][now.n].wall & d[2]) > 0) continue;
                visited[nm][nn][cw] = true;
                rooms[nm][nn].group = gn;
                q.offer(rooms[nm][nn]);
            }
        }
        return group_cnts;
    }
    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");
        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        rooms = new BOJ2234_room[M+1][N+1];
        visited = new boolean[M+1][N+1][2];
        groups = new HashMap<>();

        for(int m=1;m<=M;m++) {
            input = br.readLine().split(" ");
            for(int n=1;n<=N;n++) {
                rooms[m][n] = new BOJ2234_room(m,n,0,0,Integer.parseInt(input[n-1]));
                for(int i=0;i<2;i++) {
                    visited[m][n][i] = false;
                }
            }
        }
    }
}
