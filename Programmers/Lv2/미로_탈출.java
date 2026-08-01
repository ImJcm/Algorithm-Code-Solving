package Lv2;

import java.util.LinkedList;
import java.util.Queue;

/*
미로 탈출
제출 내역
문제 설명
1 x 1 크기의 칸들로 이루어진 직사각형 격자 형태의 미로에서 탈출하려고 합니다. 각 칸은 통로 또는 벽으로 구성되어 있으며, 벽으로 된 칸은 지나갈 수 없고 통로로 된 칸으로만 이동할 수 있습니다. 통로들 중 한 칸에는 미로를 빠져나가는 문이 있는데, 이 문은 레버를 당겨서만 열 수 있습니다. 레버 또한 통로들 중 한 칸에 있습니다. 따라서, 출발 지점에서 먼저 레버가 있는 칸으로 이동하여 레버를 당긴 후 미로를 빠져나가는 문이 있는 칸으로 이동하면 됩니다. 이때 아직 레버를 당기지 않았더라도 출구가 있는 칸을 지나갈 수 있습니다. 미로에서 한 칸을 이동하는데 1초가 걸린다고 할 때, 최대한 빠르게 미로를 빠져나가는데 걸리는 시간을 구하려 합니다.

미로를 나타낸 문자열 배열 maps가 매개변수로 주어질 때, 미로를 탈출하는데 필요한 최소 시간을 return 하는 solution 함수를 완성해주세요. 만약, 탈출할 수 없다면 -1을 return 해주세요.

제한사항
5 ≤ maps의 길이 ≤ 100
5 ≤ maps[i]의 길이 ≤ 100
maps[i]는 다음 5개의 문자들로만 이루어져 있습니다.
S : 시작 지점
E : 출구
L : 레버
O : 통로
X : 벽
시작 지점과 출구, 레버는 항상 다른 곳에 존재하며 한 개씩만 존재합니다.
출구는 레버가 당겨지지 않아도 지나갈 수 있으며, 모든 통로, 출구, 레버, 시작점은 여러 번 지나갈 수 있습니다.
입출력 예
maps	result
["SOOOL","XXXXO","OOOOO","OXXXX","OOOOE"]	16
["LOOXS","OOOOX","OOOOO","OOOOO","EOOOO"]	-1
입출력 예 설명
입출력 예 #1

주어진 문자열은 다음과 같은 미로이며

image1

다음과 같이 이동하면 가장 빠른 시간에 탈출할 수 있습니다.

image2

4번 이동하여 레버를 당기고 출구까지 이동하면 총 16초의 시간이 걸립니다. 따라서 16을 반환합니다.

입출력 예 #2

주어진 문자열은 다음과 같은 미로입니다.

image3

시작 지점에서 이동할 수 있는 공간이 없어서 탈출할 수 없습니다. 따라서 -1을 반환합니다.
 */
/*
알고리즘 핵심
BFS
1. 시작 지점에서 출구 까지의 최소 시간을 만족하려면 BFS를 사용하여 경로의 걸린 시간을 구한다.
2. 시작 지점과 레버까지의 BFS에서 걸린 최소 시간 + 레버 지점에서 출구까지의 BFS에서 걸린 최소 시간을 계산한다.
 */
public class 미로_탈출 {
    static void main() {
        String[] maps = new String[] {
                //"SOOOL","XXXXO","OOOOO","OXXXX","OOOOE"
                "LOOXS","OOOOX","OOOOO","OOOOO","EOOOO"
        };

        Solve task = new Solve();
        System.out.println(task.solution(maps));
    }

    private static class Solve {
        private class Route {
            int r,c,t;

            public Route(int r, int c, int t) {
                this.r = r;
                this.c = c;
                this.t = t;
            }
        }
        private int ans;
        private int[] ts;
        private int[][] direction = new int[][] {
                {0,1},{0,-1},{1,0},{-1,0}
        };
        private Route S,E,L;

        public int solution(String[] maps) {
            init_setting(maps);

            maze_escape(maps,S,L,0);
            maze_escape(maps,L,E,1);

            if(ts[0] == -1 || ts[1] == -1) ans = -1;
            else ans = ts[0] + ts[1];

            return ans;
        }

        private void maze_escape(String[] maps, Route s, Route e, int t) {
            Queue<Route> q = new LinkedList<>();
            q.add(s);
            boolean[][] visited = new boolean[maps.length][maps[0].length()];
            visited[s.r][s.c] = true;

            while(!q.isEmpty()) {
                Route r = q.poll();

                if(r.r == e.r && r.c == e.c) {
                    ts[t] = r.t;
                    return;
                }

                for(int[] d : direction) {
                    int nr = r.r + d[0];
                    int nc = r.c + d[1];

                    if(nr < 0 || nr >= maps.length || nc < 0 || nc >= maps[0].length()) continue;
                    if(maps[nr].charAt(nc) == 'X' || visited[nr][nc]) continue;

                    q.add(new Route(nr,nc,r.t + 1));
                    visited[nr][nc] = true;
                }
            }

        }

        private void init_setting(String[] maps) {
            ans = 0;

            ts = new int[2];
            ts[0] = ts[1] = -1;

            for(int i = 0; i < maps.length; i++) {
                String[] m = maps[i].split("");

                for(int j = 0; j < m.length; j++) {
                    switch (m[j]) {
                        case "S":
                            S = new Route(i,j,0);
                            break;
                        case "E":
                            E = new Route(i,j,0);
                            break;
                        case "L":
                            L = new Route(i,j,0);
                            break;
                    }
                }
            }
        }
    }
}
