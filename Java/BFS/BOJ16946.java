package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
벽 부수고 이동하기 4

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	22875	6459	4527	25.165%
문제
N×M의 행렬로 표현되는 맵이 있다. 맵에서 0은 이동할 수 있는 곳을 나타내고, 1은 이동할 수 없는 벽이 있는 곳을 나타낸다. 한 칸에서 다른 칸으로 이동하려면, 두 칸이 인접해야 한다. 두 칸이 변을 공유할 때, 인접하다고 한다.

각각의 벽에 대해서 다음을 구해보려고 한다.

벽을 부수고 이동할 수 있는 곳으로 변경한다.
그 위치에서 이동할 수 있는 칸의 개수를 세어본다.
한 칸에서 이동할 수 있는 칸은 상하좌우로 인접한 칸이다.

입력
첫째 줄에 N(1 ≤ N ≤ 1,000), M(1 ≤ M ≤ 1,000)이 주어진다. 다음 N개의 줄에 M개의 숫자로 맵이 주어진다.

출력
맵의 형태로 정답을 출력한다. 원래 빈 칸인 곳은 0을 출력하고, 벽인 곳은 이동할 수 있는 칸의 개수를 10으로 나눈 나머지를 출력한다.

예제 입력 1
3 3
101
010
101
예제 출력 1
303
050
303
예제 입력 2
4 5
11001
00111
01010
10101
예제 출력 2
46003
00732
06040
50403
출처
문제를 번역한 사람: baekjoon
데이터를 추가한 사람: jh05013
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
깊이 우선 탐색
 */
/*
문제를 보고 벽인 부분을 시작으로 이동할 수 있는 칸의 개수를 출력하는 것으로 보인다.
즉,
4 5
11001   ->  s1001  ->  (0,0), (1,0), (1,1), (2,0) = 4개
00111       00111
01010       01010
10101       10101

1. 벽인 부분을 1 -> 0으로 만들고 BFS를 수행하고 BFS함수 호출 횟수로 업데이트하는 방법
-> 벽마다 BFS 수행 + BFS 수행마다 visited 초기화 -> 메모리 초과

2. 벽에서 DFS 수행 - visit을 적용해야하는데 이 방법 또한 시작점을 기준으로하는 visit을 새로 적용해야함 -> 메모리 초과 예상

3. 인접한 map 정보를 가지고 벽인 공간에서 인접한 0인 공간들을 dfs를 통해 HashSet에 누적하여 저장한다. 이때, 이미 방문한 공간이라면
HashSet에서 방문여부를 검사한다. - 시간초과 발생

4. 정답코드 참고 - 방법
A-1. init_setting()
-> zeroArea : 0으로 구성된 구역을 나타내는 2차원 배열, 구역마다 index로 값을 설정한다.
-> zeroArea_size : index로 나뉜 0인 구역들이 갖는 영역의 크기 저장하는 ArrayList
-> zero_section_visit : 0을 탐색하는 과정에서 이미 탐색한 0 구역의 방문 여부를 체크한다.
-> zero_space : 0인 값을 갖는 map 객체를 저장하는 ArrayList
-> one_space : 1인 값을 갖는 map 객체를 저장하는 ArrayList

A-2. 0으로 구성하는 구역을 BFS를 통해 탐색 - zero_space_bfs_search()
zero_space에 담긴 0인 공간의 좌표들에서 0인 공간이 갖는 크기를 설정하기 위해 zero_space_bfs_search를 수행한다.
반복하는 과정에서 zero_section_visit으로 이미 방문한 공간이면 다음 과정을 수행하도록 하고, 방문하지 않은 구역이면 BFS 수행하여
0으로 구성한 공간을 나눌 수 있도록 한다. 또한, BFS 내부 과정 중 queue에 삽입하는 과정에서 count 값을 + 1씩 늘리고 zeroArea[x][y]에
구역 index를 설정한다. 모든 queue가 비워지면 해당 구역의 크기를 count에 해당하는 값으로 zeroArea_size에 설정한다.

A-3. 1으로 구성하는 구역을 BFS를 통해 탐색 - one_space_bfs_search()
1에 해당하는 구역을 0으로 만들고(벽을 부수는 과정) 상하좌우의 0인 공간의 사이즈만큼 누적하여 더하고 10으로 나눈 과정을 수행
이때, 상하좌우에 0으로 구성되는 공간을 찾고 0으로 구성된 크기를 더하여 누적하고 다음 방향으로 동작을 수행할 때, 이미 방문한 공간과
겹치는 경우를 제외하기 위해 visit의 기능을 하는 로직이 필요한데 이 과정을 HashSet으로 구역의 index 값을 넣는 과정을 통해
다음 방향의 0인 공간이 HashSet에 존재하는 index 값인지 검사하여 방문여부를 체크한다.

A-4. 마지막으로 N * M 크키만큼의 result를 출력하면 종료.

A-5. 자바의 경우, 2중 for문으로 출력을 하게되면 O(NM)만큼의 접근이 추가로 수행되므로 시간초과가 발생한다.
따라서, StringBuilder를 이용한다.

출력하는 양이 많은 경우 System.out.println()을 사용하는 것은 문제의 시간조건에 부적합하므로, StringBuilder를 사용하는 것이 중요하다.
- 참고
https://yabmoons.tistory.com/216
https://velog.io/@dl-00-e8/BOJ-16946.-%EB%B2%BD-%EB%B6%80%EC%88%98%EA%B3%A0-%EC%9D%B4%EB%8F%99%ED%95%98%EA%B8%B0-4
https://developer-ellen.tistory.com/197 - 시간초과 발생한 이유
 */
class BOJ16946 {
    static class BOJ16946_map {
        int r,c;
        int wall;

        BOJ16946_map(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static boolean[][] zero_section_visited;
    static BOJ16946_map[][] maps;
    static int[][] zeroArea,result;
    static StringBuilder sb;
    static ArrayList<BOJ16946_map> zero_space;
    static ArrayList<Integer> zeroArea_size;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};


    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        int areaNum = 0;
        for(BOJ16946_map z_m : zero_space) {
            if(zero_section_visited[z_m.r][z_m.c]) continue;
            zero_space_bfs_search(areaNum++,z_m);
        }

        for(int i=1;i<=N;i++) {
            for(int j=1;j<=M;j++) {
                if(maps[i][j].wall == 1) {
                    sb.append(one_space_bfs_search(maps[i][j]));
                } else {
                    sb.append(0);
                }
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    static int one_space_bfs_search(BOJ16946_map o_m) {
        int result = 1;
        HashSet<Integer> one_to_zero_section_visited = new HashSet<>();

        for(int[] d : direction) {
            int nr = o_m.r + d[0];
            int nc = o_m.c + d[1];

            if(nr < 1 || nr > N || nc < 1 || nc > M) continue;
            if(maps[nr][nc].wall == 1) continue;
            if(one_to_zero_section_visited.contains(zeroArea[nr][nc])) continue;

            one_to_zero_section_visited.add(zeroArea[nr][nc]);
            result += zeroArea_size.get(zeroArea[nr][nc]);
        }

        result %= 10;

        return result;
    }

    static void zero_space_bfs_search(int a_n, BOJ16946_map z_m) {
        int cnt = 1;
        Queue<BOJ16946_map> q = new LinkedList<>();

        q.offer(z_m);
        zero_section_visited[z_m.r][z_m.c] = true;
        zeroArea[z_m.r][z_m.c] = a_n;

        while(!q.isEmpty()) {
            BOJ16946_map now = q.poll();

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 1 || nr > N || nc < 1 || nc > M) continue;

                if(maps[nr][nc].wall == 0 && !zero_section_visited[nr][nc]) {
                    cnt++;
                    zero_section_visited[nr][nc] = true;
                    zeroArea[nr][nc] = a_n;
                    q.offer(maps[nr][nc]);
                }
            }
        }
        zeroArea_size.add(cnt);
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        sb = new StringBuilder();
        zeroArea_size = new ArrayList<>();
        zero_space = new ArrayList<>();

        zeroArea = new int[N+1][M+1];
        result = new int[N+1][M+1];
        zero_section_visited = new boolean[N+1][M+1];
        maps = new BOJ16946_map[N+1][M+1];

        for(int i=1;i<=N;i++) {
            input = br.readLine().split("");
            for(int j=1;j<=M;j++) {
                result[i][j] = 0;
                maps[i][j] = new BOJ16946_map(i,j);
                maps[i][j].wall = Integer.parseInt(input[j-1]);

                if(maps[i][j].wall == 0) {
                    zero_space.add(maps[i][j]);
                }
            }
        }
    }
}

//출력과정에서 시간초과 발생
class BOJ16946_success_but_timeOver_print_logic {
    static class BOJ16946_map {
        int r,c;
        int wall;

        BOJ16946_map(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static boolean[][] zero_section_visited;
    static BOJ16946_map[][] maps;
    static int[][] zeroArea,result;
    static ArrayList<Integer> zeroArea_size;
    static ArrayList<BOJ16946_map> zero_space;
    static ArrayList<BOJ16946_map> one_space;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};


    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        int areaNum = 0;
        for(BOJ16946_map z_m : zero_space) {
            if(zero_section_visited[z_m.r][z_m.c]) continue;
            zero_space_bfs_search(areaNum++,z_m);
        }

        for(BOJ16946_map o_m : one_space) {
            one_space_bfs_search(o_m);
        }

        //시간초과 원인
        for(int r=1;r<=N;r++) {
            for(int c=1;c<=M;c++) {
                System.out.print(result[r][c]);
            }
            System.out.println();
        }

    }

    static void one_space_bfs_search(BOJ16946_map o_m) {
        HashSet<Integer> one_to_zero_section_visited = new HashSet<>();

        for(int[] d : direction) {
            int nr = o_m.r + d[0];
            int nc = o_m.c + d[1];

            if(nr < 1 || nr > N || nc < 1 || nc > M) continue;
            if(maps[nr][nc].wall == 1) continue;
            if(one_to_zero_section_visited.contains(zeroArea[nr][nc])) continue;

            one_to_zero_section_visited.add(zeroArea[nr][nc]);
            result[o_m.r][o_m.c] += zeroArea_size.get(zeroArea[nr][nc]);
        }

        result[o_m.r][o_m.c] += 1;
        result[o_m.r][o_m.c] %= 10;
    }

    static void zero_space_bfs_search(int a_n, BOJ16946_map z_m) {
        int cnt = 1;
        Queue<BOJ16946_map> q = new LinkedList<>();

        q.offer(z_m);
        zero_section_visited[z_m.r][z_m.c] = true;
        zeroArea[z_m.r][z_m.c] = a_n;

        while(!q.isEmpty()) {
            BOJ16946_map now = q.poll();

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 1 || nr > N || nc < 1 || nc > M) continue;

                if(maps[nr][nc].wall == 0 && !zero_section_visited[nr][nc]) {
                    cnt++;
                    zero_section_visited[nr][nc] = true;
                    zeroArea[nr][nc] = a_n;
                    q.offer(maps[nr][nc]);
                }
            }
        }
        zeroArea_size.add(cnt);

    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        zero_space = new ArrayList<>();
        one_space = new ArrayList<>();
        zeroArea_size = new ArrayList<>();

        zeroArea = new int[N+1][M+1];
        result = new int[N+1][M+1];
        zero_section_visited = new boolean[N+1][M+1];
        maps = new BOJ16946_map[N+1][M+1];

        for(int i=1;i<=N;i++) {
            input = br.readLine().split("");
            for(int j=1;j<=M;j++) {
                result[i][j] = 0;
                maps[i][j] = new BOJ16946_map(i,j);
                maps[i][j].wall = Integer.parseInt(input[j-1]);

                if(maps[i][j].wall == 0) {
                    zero_space.add(maps[i][j]);
                } else {
                    one_space.add(maps[i][j]);
                }
            }
        }
    }
}

class BOJ16946_dfs {
    static class BOJ16946_map {
        int r,c;
        int wall;
        ArrayList<BOJ16946_map> adj;

        BOJ16946_map(int r, int c) {
            this.r = r;
            this.c = c;
            this.adj = new ArrayList<>();
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static BOJ16946_map[][] maps;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int i=1;i<=N;i++) {
            for(int j=1;j<=M;j++) {
                if(maps[i][j].wall == 1) {
                    HashSet<BOJ16946_map> possible_moving_route = new HashSet<>();
                    possible_moving_route.add(maps[i][j]);

                    dfs(i,j,possible_moving_route);

                    System.out.print(possible_moving_route.size() % 10);
                } else {
                    System.out.print(0);
                }
            }
            System.out.println();
        }
    }

    static void dfs(int r,int c, HashSet<BOJ16946_map> pmr) {
        for(BOJ16946_map m : maps[r][c].adj) {
            if(pmr.contains(m)) continue;
            pmr.add(m);
            dfs(m.r,m.c,pmr);
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        maps = new BOJ16946_map[N+1][M+1];

        for(int i=1;i<=N;i++) {
            input = br.readLine().split("");
            for(int j=1;j<=M;j++) {
                maps[i][j] = new BOJ16946_map(i,j);
                maps[i][j].wall = Integer.parseInt(input[j-1]);
            }
        }

        for(int i=1;i<=N;i++) {
            for(int j=1;j<=M;j++) {
                for(int k=0;k<4;k++) {
                    int nr = i + direction[k][0];
                    int nc = j + direction[k][1];

                    if(nr < 1 || nr > N || nc < 1 || nc > M) continue;
                    if(maps[nr][nc].wall == 0) {
                        maps[i][j].adj.add(maps[nr][nc]);
                    }
                }
            }
        }
    }
}

class BOJ16946_bfs {
    static class BOJ16946_map {
        int r,c;
        int wall;

        BOJ16946_map(int r,int c) {
            this.r = r;
            this.c = c;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static boolean[][][] visited;
    static BOJ16946_map[][] maps;
    static int[][] direction = {{-1,0},{0,1},{1,0},{0,-1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int i=1;i<=N;i++) {
            for(int j=1;j<=M;j++) {
                if(maps[i][j].wall == 1) {
                    System.out.print(bfs(i,j) % 10);
                } else {
                    System.out.print(0);
                }
            }
            System.out.println();
        }
    }

    static int bfs(int r, int c) {
        int cnt = 0;
        int base_start = M * (r-1) + c;
        Queue<BOJ16946_map> q = new LinkedList<>();

        q.offer(maps[r][c]);
        visited[base_start][r][c] = true;

        while(!q.isEmpty()) {
            BOJ16946_map now = q.poll();

            cnt++;

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 1 || nr > N || nc < 1 || nc > M) continue;
                if(visited[base_start][nr][nc] || maps[nr][nc].wall == 1) continue;

                visited[base_start][nr][nc] = true;
                q.offer(maps[nr][nc]);
            }
        }
        return cnt;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        maps = new BOJ16946_map[N+1][M+1];

        for(int i=1;i<=N;i++) {
            input = br.readLine().split("");
            for(int j=1;j<=M;j++) {
                maps[i][j] = new BOJ16946_map(i,j);
                maps[i][j].wall = Integer.parseInt(input[j-1]);
            }
        }

        visited = new boolean[(N * M) + 1][N+1][M+1];

        for(int i=1;i<=(N * M);i++) {
            for(int j=1;j<=N;j++) {
                for(int k=1;k<=M;k++) {
                    visited[i][j][k] = false;
                }
            }
        }
    }
}
