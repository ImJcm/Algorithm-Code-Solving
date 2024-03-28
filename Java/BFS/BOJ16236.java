package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
아기 상어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	68169	31918	19425	43.390%
문제
N×N 크기의 공간에 물고기 M마리와 아기 상어 1마리가 있다. 공간은 1×1 크기의 정사각형 칸으로 나누어져 있다. 한 칸에는 물고기가 최대 1마리 존재한다.

아기 상어와 물고기는 모두 크기를 가지고 있고, 이 크기는 자연수이다. 가장 처음에 아기 상어의 크기는 2이고, 아기 상어는 1초에 상하좌우로 인접한 한 칸씩 이동한다.

아기 상어는 자신의 크기보다 큰 물고기가 있는 칸은 지나갈 수 없고, 나머지 칸은 모두 지나갈 수 있다. 아기 상어는 자신의 크기보다 작은 물고기만 먹을 수 있다. 따라서, 크기가 같은 물고기는 먹을 수 없지만, 그 물고기가 있는 칸은 지나갈 수 있다.

아기 상어가 어디로 이동할지 결정하는 방법은 아래와 같다.

더 이상 먹을 수 있는 물고기가 공간에 없다면 아기 상어는 엄마 상어에게 도움을 요청한다.
먹을 수 있는 물고기가 1마리라면, 그 물고기를 먹으러 간다.
먹을 수 있는 물고기가 1마리보다 많다면, 거리가 가장 가까운 물고기를 먹으러 간다.
거리는 아기 상어가 있는 칸에서 물고기가 있는 칸으로 이동할 때, 지나야하는 칸의 개수의 최솟값이다.
거리가 가까운 물고기가 많다면, 가장 위에 있는 물고기, 그러한 물고기가 여러마리라면, 가장 왼쪽에 있는 물고기를 먹는다.
아기 상어의 이동은 1초 걸리고, 물고기를 먹는데 걸리는 시간은 없다고 가정한다. 즉, 아기 상어가 먹을 수 있는 물고기가 있는 칸으로 이동했다면, 이동과 동시에 물고기를 먹는다. 물고기를 먹으면, 그 칸은 빈 칸이 된다.

아기 상어는 자신의 크기와 같은 수의 물고기를 먹을 때 마다 크기가 1 증가한다. 예를 들어, 크기가 2인 아기 상어는 물고기를 2마리 먹으면 크기가 3이 된다.

공간의 상태가 주어졌을 때, 아기 상어가 몇 초 동안 엄마 상어에게 도움을 요청하지 않고 물고기를 잡아먹을 수 있는지 구하는 프로그램을 작성하시오.

입력
첫째 줄에 공간의 크기 N(2 ≤ N ≤ 20)이 주어진다.

둘째 줄부터 N개의 줄에 공간의 상태가 주어진다. 공간의 상태는 0, 1, 2, 3, 4, 5, 6, 9로 이루어져 있고, 아래와 같은 의미를 가진다.

0: 빈 칸
1, 2, 3, 4, 5, 6: 칸에 있는 물고기의 크기
9: 아기 상어의 위치
아기 상어는 공간에 한 마리 있다.

출력
첫째 줄에 아기 상어가 엄마 상어에게 도움을 요청하지 않고 물고기를 잡아먹을 수 있는 시간을 출력한다.

예제 입력 1
3
0 0 0
0 0 0
0 9 0
예제 출력 1
0
예제 입력 2
3
0 0 1
0 0 0
0 9 0
예제 출력 2
3
예제 입력 3
4
4 3 2 1
0 0 0 0
0 0 9 0
1 2 3 4
예제 출력 3
14
예제 입력 4
6
5 4 3 2 3 4
4 3 2 3 4 5
3 2 9 5 6 6
2 1 2 3 4 5
3 2 1 6 5 4
6 6 6 6 6 6
예제 출력 4
60
예제 입력 5
6
6 0 6 0 6 1
0 0 0 0 0 2
2 3 4 5 6 6
0 0 0 0 0 2
0 2 0 0 0 0
3 9 3 0 0 1
예제 출력 5
48
예제 입력 6
6
1 1 1 1 1 1
2 2 6 2 2 3
2 2 5 2 2 3
2 2 2 4 6 3
0 0 0 0 0 6
0 0 0 0 0 9
예제 출력 6
39
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: djm03178
알고리즘 분류
구현
그래프 이론
그래프 탐색
시뮬레이션
너비 우선 탐색
 */
/*
1. 처음 위치에서 먹는 것이 가능한 물고기의 위치를 search_list에 우선순위를 적용한 곳으로 이동하고 해당 위치의 물고기를 먹는다.
2. search_list의 다음 위치에 해당하는 물고기를 먹으러 간다.
3. 상어의 몸무게가 증가하면 기존의 search_list를 제거하고 새로운 search_list를 생성한다.

위의 1,2,3 방법으로 정답을 구할 수 있다고 생각했지만 x
이유 : 처음 상어의 위치에서 최소거리의 물고기들의 위치를 구하였지만, 첫번째 위치로 이동하여 물고기를 먹은 후의 해당 위치에서 search_list의 다음에 해당하는
위치는 해당 위치에서 최소 거리를 만족할 수 없기 때문이다.

따라서, 아래와 같이 로직을 변경한다.
1. bfs를 통해 먹을 수 있는 물고기가 있는 위치를 bfs를 통해 구한 후, search_list를 만든다.
2. search_list의 가장 처음 위치에 물고기를 먹은 후, 해당 위치에서 다시 bfs를 수행하여 search_list를 업데이트한다.

 */
public class BOJ16236 {
    static class BOJ16236_space {
        int r,c;
        int weight;
        int move;

        BOJ16236_space(int r,int c, int w, int m) {
            this.r = r;
            this.c = c;
            this.weight = w;
            this.move = m;
        }
    }

    static class BOJ16236_shark {
        int r,c;
        int weight;

        BOJ16236_shark(int r,int c, int w) {
            this.r = r;
            this.c = c;
            this.weight = w;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, eating_time = 0, shark_eating_count, fish_count = 0;
    static int[][] direction = {{-1,0}, {0,1}, {1,0}, {0,-1}};
    static boolean[][][] visited;
    static BOJ16236_space[][] spaces;
    static BOJ16236_shark baby_shark;
    static ArrayList<BOJ16236_space> search_list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        while(true) {
            bfs(baby_shark);

            if(search_list.isEmpty()) {
                System.out.println(eating_time);
                return;
            }

            Collections.sort(search_list, new Comparator<BOJ16236_space>() {
                @Override
                public int compare(BOJ16236_space o1, BOJ16236_space o2) {
                    if(o1.move < o2.move) {
                        return -1;
                    } else if(o1.move > o2.move) {
                        return 1;
                    } else {
                        if(o1.r < o2.r) {
                            return -1;
                        } else if (o1.r > o2.r) {
                            return 1;
                        } else {
                            return o1.c - o2.c;
                        }
                    }
                }
            });

            BOJ16236_space fish = search_list.remove(0);
            fish.weight = 0;
            eating_time += fish.move;
            shark_eating_count++;
            baby_shark = new BOJ16236_shark(fish.r, fish.c, baby_shark.weight);

            if(shark_eating_count == baby_shark.weight) {
                shark_eating_count = 0;
                baby_shark.weight++;
            }

            search_list.clear();

            //search_list를 통해 최소거리의 물고기를 먹은 후, 다음 물고기를 먹는 최소거리를 만족하지 못하기 때문에 잘못된 코드이다.
            /*while(search_list.size() != 0) {
                Collections.sort(search_list, new Comparator<BOJ16236_space>() {
                    @Override
                    public int compare(BOJ16236_space o1, BOJ16236_space o2) {
                        if(o1.move < o2.move) {
                            return -1;
                        } else if(o1.move > o2.move) {
                            return 1;
                        } else {
                            if(o1.r < o2.r) {
                                return -1;
                            } else if (o1.r > o2.r) {
                                return 1;
                            } else {
                                return o1.c - o2.c;
                            }
                        }
                    }
                });

                BOJ16236_space fish = search_list.remove(0);
                fish.weight = 0;
                eating_time += (Math.abs(baby_shark.r - fish.r) + Math.abs(baby_shark.c - fish.c));
                shark_eating_count++;
                baby_shark = new BOJ16236_shark(fish.r, fish.c, baby_shark.weight);

                if(shark_eating_count == baby_shark.weight) {
                    search_list.clear();
                    shark_eating_count = 0;
                    baby_shark.weight++;
                    break;
                }
            }*/
        }
    }
    static void bfs(BOJ16236_shark s) {
        Queue<BOJ16236_space> q = new LinkedList<>();
        fish_count = fish_count == 0 ? 0 : fish_count - 1;

        q.offer(spaces[s.r][s.c]);
        spaces[s.r][s.c].move = 0;
        visited[s.r][s.c][fish_count] = true;

        while(!q.isEmpty()) {
            BOJ16236_space now = q.poll();

            if(now.weight != 0 && now.weight < s.weight) {
                search_list.add(now);
            }

            for(int[] d : direction) {
                int nr = now.r + d[0];
                int nc = now.c + d[1];

                if(nr < 1 || nr > N || nc < 1 || nc > N) continue;
                if(visited[nr][nc][fish_count]) continue;
                if(spaces[nr][nc].weight > s.weight) continue;

                visited[nr][nc][fish_count] = true;
                spaces[nr][nc].move = now.move + 1;
                q.offer(spaces[nr][nc]);
            }
        }
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        spaces = new BOJ16236_space[N+1][N+1];

        for(int i=1;i<=N;i++) {
            String[] input = br.readLine().split(" ");
            for(int j=1;j<=N;j++) {
                spaces[i][j] = new BOJ16236_space(i,j,Integer.parseInt(input[j-1]),0);
                int space = spaces[i][j].weight;

                if(space == 9) {
                    baby_shark = new BOJ16236_shark(i,j,2);
                    spaces[i][j].weight = 0;
                    shark_eating_count = 0;
                } else if(space == 1 || space == 2 || space == 3 || space == 4 || space == 5 || space == 6) {
                    fish_count++;
                }
            }
        }

        visited = new boolean[N+1][N+1][fish_count + 1];
        for(int i=1;i<=N;i++) {
            for(int j=1;j<=N;j++) {
                for(int k=0;k<=fish_count;k++) {
                    visited[i][j][k] = false;
                }
            }
        }

    }
}
