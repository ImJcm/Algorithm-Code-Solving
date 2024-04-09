package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
스타트링크 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	54047	17989	13725	33.569%
문제
강호는 코딩 교육을 하는 스타트업 스타트링크에 지원했다. 오늘은 강호의 면접날이다. 하지만, 늦잠을 잔 강호는 스타트링크가 있는 건물에 늦게 도착하고 말았다.

스타트링크는 총 F층으로 이루어진 고층 건물에 사무실이 있고, 스타트링크가 있는 곳의 위치는 G층이다. 강호가 지금 있는 곳은 S층이고, 이제 엘리베이터를 타고 G층으로 이동하려고 한다.

보통 엘리베이터에는 어떤 층으로 이동할 수 있는 버튼이 있지만, 강호가 탄 엘리베이터는 버튼이 2개밖에 없다. U버튼은 위로 U층을 가는 버튼, D버튼은 아래로 D층을 가는 버튼이다. (만약, U층 위, 또는 D층 아래에 해당하는 층이 없을 때는, 엘리베이터는 움직이지 않는다)

강호가 G층에 도착하려면, 버튼을 적어도 몇 번 눌러야 하는지 구하는 프로그램을 작성하시오. 만약, 엘리베이터를 이용해서 G층에 갈 수 없다면, "use the stairs"를 출력한다.

입력
첫째 줄에 F, S, G, U, D가 주어진다. (1 ≤ S, G ≤ F ≤ 1000000, 0 ≤ U, D ≤ 1000000) 건물은 1층부터 시작하고, 가장 높은 층은 F층이다.

출력
첫째 줄에 강호가 S층에서 G층으로 가기 위해 눌러야 하는 버튼의 수의 최솟값을 출력한다. 만약, 엘리베이터로 이동할 수 없을 때는 "use the stairs"를 출력한다.

예제 입력 1
10 1 10 2 1
예제 출력 1
6
예제 입력 2
100 2 1 1 0
예제 출력 2
use the stairs
출처
ICPC > Regionals > Europe > Northwestern European Regional Contest > Nordic Collegiate Programming Contest > NCPC 2011 D번

문제를 번역한 사람: baekjoon
데이터를 추가한 사람: fkrdnjs23, luniro, sh0705k
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
floor(층) 정보를 갖는 BOJ5014_floor 클래스 객체를 이용하여 G층에 도달할 때 까지의 bfs를 수행한 move_cnt를 업데이트하고
visited을 적용하여 이미 도달한 층 정보를 이용하여 정답을 도출한다.
 */
public class BOJ5014 {
    static class BOJ5014_floor {
        int floor;
        int move_cnt;

        BOJ5014_floor(int f, int m_c) {
            this.floor = f;
            this.move_cnt = m_c;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int F,S,G,U,D;
    static boolean[] visited;
    static BOJ5014_floor[] building;
    static int[] direction = {};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        int result = bfs();
        System.out.println(result == -1 ? "use the stairs" : result);
    }

    static int bfs() {
        Queue<BOJ5014_floor> q = new LinkedList<>();

        q.offer(building[S]);
        visited[S] = true;

        while(!q.isEmpty()) {
            BOJ5014_floor now = q.poll();

            if(now.floor == G) {
                return now.move_cnt;
            }

            for(int move : direction) {
                int n_floor = now.floor + move;

                if(n_floor > F || n_floor < 1 || visited[n_floor]) continue;
                visited[n_floor] = true;
                building[n_floor].move_cnt = now.move_cnt + 1;
                q.offer(building[n_floor]);
            }
        }
        return -1;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        F = Integer.parseInt(input[0]);
        S = Integer.parseInt(input[1]);
        G = Integer.parseInt(input[2]);
        U = Integer.parseInt(input[3]);
        D = Integer.parseInt(input[4]);

        building = new BOJ5014_floor[F+1];
        visited = new boolean[F+1];
        direction = new int[2];

        direction[0] = U;
        direction[1] = -D;

        for(int i=1;i<=F;i++) {
            building[i] = new BOJ5014_floor(i,0);
            visited[i] = false;
        }
    }
}
