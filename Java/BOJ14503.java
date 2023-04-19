import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
로봇 청소기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	46077	25076	16811	53.733%
문제
로봇 청소기가 주어졌을 때, 청소하는 영역의 개수를 구하는 프로그램을 작성하시오.

로봇 청소기가 있는 장소는 N×M 크기의 직사각형으로 나타낼 수 있으며, 1×1크기의 정사각형 칸으로 나누어져 있다. 각각의 칸은 벽 또는 빈 칸이다. 청소기는 바라보는 방향이 있으며, 이 방향은 동, 서, 남, 북중 하나이다. 지도의 각 칸은 (r, c)로 나타낼 수 있고, r은 북쪽으로부터 떨어진 칸의 개수, c는 서쪽으로 부터 떨어진 칸의 개수이다.

로봇 청소기는 다음과 같이 작동한다.

현재 위치를 청소한다.
현재 위치에서 현재 방향을 기준으로 왼쪽방향부터 차례대로 탐색을 진행한다.
왼쪽 방향에 아직 청소하지 않은 공간이 존재한다면, 그 방향으로 회전한 다음 한 칸을 전진하고 1번부터 진행한다.
왼쪽 방향에 청소할 공간이 없다면, 그 방향으로 회전하고 2번으로 돌아간다.
네 방향 모두 청소가 이미 되어있거나 벽인 경우에는, 바라보는 방향을 유지한 채로 한 칸 후진을 하고 2번으로 돌아간다.
네 방향 모두 청소가 이미 되어있거나 벽이면서, 뒤쪽 방향이 벽이라 후진도 할 수 없는 경우에는 작동을 멈춘다.
로봇 청소기는 이미 청소되어있는 칸을 또 청소하지 않으며, 벽을 통과할 수 없다.

입력
첫째 줄에 세로 크기 N과 가로 크기 M이 주어진다. (3 ≤ N, M ≤ 50)

둘째 줄에 로봇 청소기가 있는 칸의 좌표 (r, c)와 바라보는 방향 d가 주어진다. d가 0인 경우에는 북쪽을, 1인 경우에는 동쪽을, 2인 경우에는 남쪽을, 3인 경우에는 서쪽을 바라보고 있는 것이다.

셋째 줄부터 N개의 줄에 장소의 상태가 북쪽부터 남쪽 순서대로, 각 줄은 서쪽부터 동쪽 순서대로 주어진다. 빈 칸은 0, 벽은 1로 주어진다. 지도의 첫 행, 마지막 행, 첫 열, 마지막 열에 있는 모든 칸은 벽이다.

로봇 청소기가 있는 칸의 상태는 항상 빈 칸이다.

출력
로봇 청소기가 청소하는 칸의 개수를 출력한다.

예제 입력 1
3 3
1 1 0
1 1 1
1 0 1
1 1 1
예제 출력 1
1
예제 입력 2
11 10
7 4 0
1 1 1 1 1 1 1 1 1 1
1 0 0 0 0 0 0 0 0 1
1 0 0 0 1 1 1 1 0 1
1 0 0 1 1 0 0 0 0 1
1 0 1 1 0 0 0 0 0 1
1 0 0 0 0 0 0 0 0 1
1 0 0 0 0 0 0 1 0 1
1 0 0 0 0 0 1 1 0 1
1 0 0 0 0 0 1 1 0 1
1 0 0 0 0 0 0 0 0 1
1 1 1 1 1 1 1 1 1 1
예제 출력 2
57
 */
/*
    처음 접근하려고 했던 방식은 로봇청소기의 작동방식에서 DFS를 생각했지만, 3,4번 조건에서 바라보는 방향과 반대방향으로 후진한다는 조건을 보고
    이전 작동 함수로 돌아가는 재귀형식의 DFS방식은 옳지 않은 것으로 판단하고, 조건문 + 반복문을 활용하여 알고리즘 구현함
 */
public class BOJ14503 {
    static int N,M,r,c,d,count=0;
    static int[][] map;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M];
        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                visited[i][j] = false;
            }
        }

        solve();

    }

    static void solve() {
        boolean doing = true;
        int doing_stack = 0;
        while(doing) {
            //3,4
            if(doing_stack == 4) {
                if(d==0) {
                    if(r+1 < N) {
                        r = r + 1;
                        doing_stack = 0;
                    } else {
                        doing = false;
                    }
                    continue;
                } else if(d==1) {
                    if(c-1 > 0) {
                        c = c - 1;
                        doing_stack = 0;
                    } else {
                        doing = false;
                    }
                    continue;
                } else if(d==2) {
                    if(r-1 > 0) {
                        r = r - 1;
                        doing_stack = 0;
                    } else {
                        doing = false;
                    }
                    continue;
                } else if(d==3) {
                    if(c+1 < M) {
                        c = c + 1;
                        doing_stack = 0;
                    } else {
                        doing = false;
                    }
                    continue;
                }
            }

            //1
            if(map[r][c] == 0 && !visited[r][c]) {
                count++;
                visited[r][c] = true;
                doing_stack = 0;
            }

            //2
            if(d==0) {
                if(c-1 > 0 && map[r][c-1] == 0 && !visited[r][c-1]) {
                    c = c - 1;
                } else {
                    doing_stack++;
                }
                d = 3;
                continue;
            } else if(d==1) {
                if(r-1 > 0 && map[r-1][c] == 0 && !visited[r-1][c]) {
                    r = r - 1;
                } else {
                    doing_stack++;
                }
                d = 0;
                continue;
            } else if(d==2) {
                if(c+1 < M && map[r][c+1] == 0 && !visited[r][c+1]) {
                    c = c + 1;
                } else {
                    doing_stack++;
                }
                d = 1;
                continue;
            } else if(d==3) {
                if(r+1 < N && map[r+1][c] == 0 && !visited[r+1][c]) {
                    r = r + 1;
                } else {
                    doing_stack++;
                }
                d = 2;
                continue;
            }
        }
        System.out.println(count);
    }
}
