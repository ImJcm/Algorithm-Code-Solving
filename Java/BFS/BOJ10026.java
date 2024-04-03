package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
적록색약 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	66277	38228	28928	56.480%
문제
적록색약은 빨간색과 초록색의 차이를 거의 느끼지 못한다. 따라서, 적록색약인 사람이 보는 그림은 아닌 사람이 보는 그림과는 좀 다를 수 있다.

크기가 N×N인 그리드의 각 칸에 R(빨강), G(초록), B(파랑) 중 하나를 색칠한 그림이 있다. 그림은 몇 개의 구역으로 나뉘어져 있는데, 구역은 같은 색으로 이루어져 있다. 또, 같은 색상이 상하좌우로 인접해 있는 경우에 두 글자는 같은 구역에 속한다. (색상의 차이를 거의 느끼지 못하는 경우도 같은 색상이라 한다)

예를 들어, 그림이 아래와 같은 경우에

RRRBB
GGBBB
BBBRR
BBRRR
RRRRR
적록색약이 아닌 사람이 봤을 때 구역의 수는 총 4개이다. (빨강 2, 파랑 1, 초록 1) 하지만, 적록색약인 사람은 구역을 3개 볼 수 있다. (빨강-초록 2, 파랑 1)

그림이 입력으로 주어졌을 때, 적록색약인 사람이 봤을 때와 아닌 사람이 봤을 때 구역의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N이 주어진다. (1 ≤ N ≤ 100)

둘째 줄부터 N개 줄에는 그림이 주어진다.

출력
적록색약이 아닌 사람이 봤을 때의 구역의 개수와 적록색약인 사람이 봤을 때의 구역의 수를 공백으로 구분해 출력한다.

예제 입력 1
5
RRRBB
GGBBB
BBBRR
BBRRR
RRRRR
예제 출력 1
4 3
출처
Olympiad > USA Computing Olympiad > 2013-2014 Season > USACO March 2014 Contest > Bronze 3번

문제를 번역한 사람: baekjoon
어색한 표현을 찾은 사람: corea
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
깊이 우선 탐색
 */
/*
처음 문제를 읽고 한번의 bfs를 사용하여 적록색맹과 아닌 구역의 개수를 구할 수 있을까 고민하다가 로직이 구현이 안되서 색맹과 아닌 경우를 나누어
두개의 구역을 탐색하여 적녹색맹이 없는 경우에서의 BFS(DFS)와 적녹색맹이 존재하는 경우에서의 rg_BFS(rg_DFS)를 돌려 구역의 개수를 별도로 구하는 방법으로 작성하였다.

다른 정답 코드를 참조해보면, 다른 코드들 조차 색맹 여부에 따라서 bfs 또는 dfs를 사용하여 구역을 나누어 구하였다.

추가로, 조금이라도 괜찮은 코드를 봤는데 해당 코드는 우선 색맹이 없는 경우를 구한 뒤, R 또는 G 중에 하나의 색으로 변경하는 방식을 통해 동일한
BFS 또는 DFS를 사용하여 정답을 구할 수 있었다. 아래 방법이 좀 더 깔끔해 보인다.
해당 코드 - https://velog.io/@dot2__/BOJ-10026%EB%B2%88-%EC%A0%81%EB%A1%9D%EC%83%89%EC%95%BDJava
 */
public class BOJ10026 {
    static class BOJ10026_section {
        int r,c;
        char color;
        int section_number;
        int rg_section_number;

        BOJ10026_section(int r, int c, char color, int s_n, int rg_s_n) {
            this.r = r;
            this.c = c;
            this.color = color;
            this.section_number = s_n;
            this.rg_section_number = rg_s_n;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, section_number = 1,rg_section_number = 1;
    static int[][] direction = {{-1,0}, {0,1}, {1,0}, {0,-1}};
    static boolean[][][] visited;
    static BOJ10026_section[][] sections;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        //적녹색맹 x - bfs
        for(int i=1;i<=N;i++) {
            for(int j=1;j<=N;j++) {
                if(visited[0][i][j]) continue;
                bfs(sections[i][j], section_number++);
            }
        }

        //적녹색맹 o - bfs
        for(int i=1;i<=N;i++) {
            for(int j=1;j<=N;j++) {
                if(visited[1][i][j]) continue;
                rg_bfs(sections[i][j], rg_section_number++);
            }
        }

        System.out.println((section_number - 1) + " " + (rg_section_number - 1));

        //init
        section_number = rg_section_number = 0;
        //적녹색맹 x - dfs
        for(int i=1;i<=N;i++) {
            for(int j=1;j<=N;j++) {
                if(visited[2][i][j]) continue;
                section_number++;
                dfs(sections[i][j]);
            }
        }

        //적녹색맹 o - dfs
        for(int i=1;i<=N;i++) {
            for(int j=1;j<=N;j++) {
                if(visited[3][i][j]) continue;
                rg_section_number++;
                rg_dfs(sections[i][j]);
            }
        }

        System.out.println(section_number + " " + rg_section_number);
    }

    static void bfs(BOJ10026_section s, int s_n) {
        Queue<BOJ10026_section> q = new LinkedList<>();

        q.offer(s);
        s.section_number = s_n;

        visited[0][s.r][s.c] = true;


        while(!q.isEmpty()) {
            BOJ10026_section now = q.poll();

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 1 || nr > N || nc < 1 || nc > N || visited[0][nr][nc]) continue;
                if(now.color == sections[nr][nc].color) {
                    visited[0][nr][nc] = true;
                    sections[nr][nc].section_number = now.section_number;
                    q.offer(sections[nr][nc]);
                }
            }
        }
    }

    static void rg_bfs(BOJ10026_section s, int rg_s_n) {
        Queue<BOJ10026_section> q = new LinkedList<>();

        q.offer(s);
        s.rg_section_number = rg_s_n;

        visited[1][s.r][s.c] = true;


        while(!q.isEmpty()) {
            BOJ10026_section now = q.poll();

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 1 || nr > N || nc < 1 || nc > N || visited[1][nr][nc]) continue;
                if(now.color == sections[nr][nc].color) {
                    visited[1][nr][nc] = true;
                    sections[nr][nc].rg_section_number = now.rg_section_number;
                    q.offer(sections[nr][nc]);
                } else if((sections[nr][nc].color == 'G' && now.color == 'R') || (sections[nr][nc].color == 'R' && now.color == 'G')) {
                    visited[1][nr][nc] = true;
                    sections[nr][nc].rg_section_number = now.rg_section_number;
                    q.offer(sections[nr][nc]);
                }
            }
        }
    }

    static void dfs(BOJ10026_section s) {
        visited[2][s.r][s.c] = true;

        for(int[] d : direction) {
            int nr = s.r + d[0];
            int nc = s.c + d[1];

            if(nr < 1 || nr > N || nc < 1 || nc > N || visited[2][nr][nc]) continue;
            if(sections[nr][nc].color != s.color) continue;
            visited[2][nr][nc] = true;
            dfs(sections[nr][nc]);
        }
    }

    static void rg_dfs(BOJ10026_section s) {
        visited[3][s.r][s.c] = true;

        for(int[] d : direction) {
            int nr = s.r + d[0];
            int nc = s.c + d[1];

            if(nr < 1 || nr > N || nc < 1 || nc > N || visited[3][nr][nc]) continue;
            if(sections[nr][nc].color == s.color || ((sections[nr][nc].color == 'R' && s.color == 'G') || (sections[nr][nc].color == 'G' && s.color == 'R'))) {
                visited[3][nr][nc] = true;
                rg_dfs(sections[nr][nc]);
            }

        }
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        sections = new BOJ10026_section[N+1][N+1];
        visited = new boolean[4][N+1][N+1];

        for(int i=1;i<=N;i++) {
            String[] input = br.readLine().split("");
            for(int j=1;j<=N;j++) {
                sections[i][j] = new BOJ10026_section(i,j,input[j-1].charAt(0),0,0);
                for(int k=0;k<3;k++) {
                    visited[k][i][j] = false;
                }
            }
        }
    }
}
