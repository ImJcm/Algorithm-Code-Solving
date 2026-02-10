package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
모양 만들기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	5545	2159	1575	36.265%
문제
N×M인 배열에서 모양을 찾으려고 한다. 배열의 각 칸에는 0과 1 중의 하나가 들어있다. 두 칸이 서로 변을 공유할때, 두 칸을 인접하다고 한다.

1이 들어 있는 인접한 칸끼리 연결했을 때, 각각의 연결 요소를 모양이라고 부르자. 모양의 크기는 모양에 포함되어 있는 1의 개수이다.

배열의 칸 하나에 들어있는 수를 변경해서 만들 수 있는 모양의 최대 크기를 구해보자.

입력
첫째 줄에 배열의 크기 N과 M이 주어진다. 둘째 줄부터 N개의 줄에는 배열에 들어있는 수가 주어진다.

출력
첫째 줄에 수 하나를 변경해서 만들 수 있는 모양의 최대 크기를 출력한다.

제한
2 ≤ N, M ≤ 1,000
0과 1의 개수는 하나 이상이다.
예제 입력 1
3 3
0 1 1
0 0 1
0 1 0
예제 출력 1
5
예제 입력 2
5 4
1 1 0 0
1 0 1 0
1 0 1 0
0 1 1 0
1 0 0 1
예제 출력 2
10
예제 입력 3
3 4
0 1 0 1
0 0 0 1
1 1 0 1
예제 출력 3
6
출처
문제를 번역한 사람: baekjoon
어색한 표현을 찾은 사람: jh05013
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
집합과 맵
깊이 우선 탐색
 */
/*
알고리즘 핵심
BFS + 집합과 맵
1. 입력으로 주어진 배열에서 독립적인 구역을 나누어 번호를 배정한다.
2. 1번 과정에서 나눈 구역의 크기를 Map에 <구역, 크기>로 저장한다.
3. 구역을 나눈 후, (1,1) -> (N,M)까지 모든 수 중에서 0인 위치에 1로 변경하였을 때 인접한 구역의 크기를 누적하여 더한다.
4. 최대 크기의 구역을 ans에 업데이트한다.
 */
public class BOJ16932 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    private static class Solve {
        private class Pos {
            int x,y;

            Pos(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,M,ans;
        int[][] arr,section_arr,direction = {{1,0},{-1,0},{0,1},{0,-1}};
        HashMap<Integer,Integer> section;

        void solve() throws IOException {
            init_setting();

            divide_section();

            change_shape();

            System.out.println(ans);
        }

        private void change_shape() {
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < M; j++) {
                    if(arr[i][j] == 1) continue;
                    HashSet<Integer> adj = new HashSet<>();
                    int sum = 1;

                    for(int[] d : direction) {
                        int nx = i + d[0];
                        int ny = j + d[1];

                        if(nx < 0 || nx >= N || ny < 0 || ny >= M || section_arr[nx][ny] == 0) continue;

                        int sn = section_arr[nx][ny];

                        if(adj.contains(sn)) continue;
                        else {
                            adj.add(sn);
                            sum += section.get(sn);
                        }
                    }

                    ans = Math.max(ans, sum);
                }
            }
        }

        private void divide_section() {
            boolean[][] visited = new boolean[N][M];
            int sec_num = 1;
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < M; j++) {
                    if(arr[i][j] == 0 || visited[i][j]) continue;
                    bfs(new Pos(i,j),sec_num++,visited);
                }
            }
        }

        private void bfs(Pos pos, int sn, boolean[][] v) {
            Queue<Pos> q = new LinkedList<>();
            q.add(pos);
            v[pos.x][pos.y] = true;
            section_arr[pos.x][pos.y] = sn;
            int cnt = 1;

            while(!q.isEmpty()) {
                Pos now = q.poll();

                for(int[] d : direction) {
                    int nx = now.x + d[0];
                    int ny = now.y + d[1];

                    if(nx < 0 || nx >= N || ny < 0 || ny >= M || arr[nx][ny] == 0 || v[nx][ny]) continue;

                    v[nx][ny] = true;
                    section_arr[nx][ny] = sn;
                    cnt += 1;
                    q.add(new Pos(nx,ny));
                }
            }

            section.put(sn,cnt);
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            M = Integer.parseInt(input[1]);

            arr = new int[N][M];
            section_arr = new int[N][M];

            for(int i = 0; i < N; i++) {
                arr[i] = Arrays.stream(br.readLine().split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }

            section = new HashMap<>();

            ans = 0;
        }
    }
}
