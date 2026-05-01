package Programmers;

/*
방문 길이
문제 설명
게임 캐릭터를 4가지 명령어를 통해 움직이려 합니다. 명령어는 다음과 같습니다.

U: 위쪽으로 한 칸 가기

D: 아래쪽으로 한 칸 가기

R: 오른쪽으로 한 칸 가기

L: 왼쪽으로 한 칸 가기

캐릭터는 좌표평면의 (0, 0) 위치에서 시작합니다. 좌표평면의 경계는 왼쪽 위(-5, 5), 왼쪽 아래(-5, -5), 오른쪽 위(5, 5), 오른쪽 아래(5, -5)로 이루어져 있습니다.

방문길이1_qpp9l3.png

예를 들어, "ULURRDLLU"로 명령했다면

방문길이2_lezmdo.png

1번 명령어부터 7번 명령어까지 다음과 같이 움직입니다.
방문길이3_sootjd.png

8번 명령어부터 9번 명령어까지 다음과 같이 움직입니다.
방문길이4_hlpiej.png

이때, 우리는 게임 캐릭터가 지나간 길 중 캐릭터가 처음 걸어본 길의 길이를 구하려고 합니다. 예를 들어 위의 예시에서 게임 캐릭터가 움직인 길이는 9이지만, 캐릭터가 처음 걸어본 길의 길이는 7이 됩니다. (8, 9번 명령어에서 움직인 길은 2, 3번 명령어에서 이미 거쳐 간 길입니다)

단, 좌표평면의 경계를 넘어가는 명령어는 무시합니다.

예를 들어, "LULLLLLLU"로 명령했다면

방문길이5_nitjwj.png

1번 명령어부터 6번 명령어대로 움직인 후, 7, 8번 명령어는 무시합니다. 다시 9번 명령어대로 움직입니다.
방문길이6_nzhumd.png

이때 캐릭터가 처음 걸어본 길의 길이는 7이 됩니다.

명령어가 매개변수 dirs로 주어질 때, 게임 캐릭터가 처음 걸어본 길의 길이를 구하여 return 하는 solution 함수를 완성해 주세요.

제한사항
dirs는 string형으로 주어지며, 'U', 'D', 'R', 'L' 이외에 문자는 주어지지 않습니다.
dirs의 길이는 500 이하의 자연수입니다.
입출력 예
dirs	answer
"ULURRDLLU"	7
"LULLLLLLU"	7
입출력 예 설명
입출력 예 #1
문제의 예시와 같습니다.

입출력 예 #2
문제의 예시와 같습니다.
 */
/*
알고리즘 핵심
dfs + implementation
1. 하나의 점에서 방문할 수 있는 경우가 4개(위,아래,오른쪽,왼쪽)이므로 방문여부를 [x][y][d] 3차원으로 설정한다.
2. (11,11) 배열을 만들고, dfs로 입력으로 주어진 움직이는 순서대로 진행하여 visited를 통해 방문한 경로를 제외한 횟수를 업데이트한다.
 */
public class 방문_길이 {
    public static void main() {
        String[] dirs = {
                "ULURRDLLU","LULLLLLLU"
        };
        Solve task = new Solve();
        System.out.println(task.solution(dirs[0]));
    }

    private static class Solve {
        private class Pos {
            int x,y;
            boolean[] visited;

            public Pos(int x, int y) {
                this.x = x;
                this.y = y;
                this.visited = new boolean[4];
            }

            public boolean visit(int d) {
                boolean state = this.visited[d];
                if(!state) this.visited[d] = true;
                return state;
            }
        }
        private int ans;
        private int[][] direction;
        private Pos[][] map;

        public int solution(String dir) {
            init_setting(dir);

            ans = move(0,dir,map[5][5]);

            return ans;
        }

        private int move(int l, String d, Pos p) {
            if(l == d.length()) return 0;

            int nx = p.x;
            int ny = p.y;
            int route = 0;

            switch (d.charAt(l)) {
                case 'U':
                    nx += direction[0][0];
                    ny += direction[0][1];

                    if(check(nx,ny)) {
                        if(!p.visit(0) && !map[nx][ny].visit(1)) route += 1;
                    } else {
                        nx = p.x;
                        ny = p.y;
                    }
                    break;
                case 'D':
                    nx += direction[1][0];
                    ny += direction[1][1];

                    if(check(nx,ny)) {
                        if(!p.visit(1) && !map[nx][ny].visit(0)) route += 1;
                    } else {
                        nx = p.x;
                        ny = p.y;
                    }
                    break;
                case 'R':
                    nx += direction[2][0];
                    ny += direction[2][1];

                    if(check(nx,ny)) {
                        if(!p.visit(2) && !map[nx][ny].visit(3)) route += 1;
                    } else {
                        nx = p.x;
                        ny = p.y;
                    }
                    break;
                case 'L':
                    nx += direction[3][0];
                    ny += direction[3][1];

                    if(check(nx,ny)) {
                        if(!p.visit(3) && !map[nx][ny].visit(2)) route += 1;
                    } else {
                        nx = p.x;
                        ny = p.y;
                    }
                    break;
            }

            return route + move(l + 1, d, map[nx][ny]);
        }

        private boolean check(int x, int y) {
            if(x < 0 || x > 10 || y < 0 || y > 10) return false;
            return true;
        }

        private void init_setting(String dir) {
            map = new Pos[11][11];

            for(int i = 0; i < 11; i++) {
                for(int j = 0; j < 11; j++) {
                    map[i][j] = new Pos(i, j);
                }
            }

            direction = new int[][]{ // U, D, R, L
                    {-1, 0}, {1, 0}, {0, 1}, {0, -1}
            };

            ans = 0;
        }
    }
}
