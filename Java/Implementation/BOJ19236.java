package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
청소년 상어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초 (추가 시간 없음)	512 MB	13948	8858	5736	65.509%
문제
아기 상어가 성장해 청소년 상어가 되었다.

4×4크기의 공간이 있고, 크기가 1×1인 정사각형 칸으로 나누어져 있다. 공간의 각 칸은 (x, y)와 같이 표현하며, x는 행의 번호, y는 열의 번호이다. 한 칸에는 물고기가 한 마리 존재한다. 각 물고기는 번호와 방향을 가지고 있다. 번호는 1보다 크거나 같고, 16보다 작거나 같은 자연수이며, 두 물고기가 같은 번호를 갖는 경우는 없다. 방향은 8가지 방향(상하좌우, 대각선) 중 하나이다.

오늘은 청소년 상어가 이 공간에 들어가 물고기를 먹으려고 한다. 청소년 상어는 (0, 0)에 있는 물고기를 먹고, (0, 0)에 들어가게 된다. 상어의 방향은 (0, 0)에 있던 물고기의 방향과 같다. 이후 물고기가 이동한다.

물고기는 번호가 작은 물고기부터 순서대로 이동한다. 물고기는 한 칸을 이동할 수 있고, 이동할 수 있는 칸은 빈 칸과 다른 물고기가 있는 칸, 이동할 수 없는 칸은 상어가 있거나, 공간의 경계를 넘는 칸이다. 각 물고기는 방향이 이동할 수 있는 칸을 향할 때까지 방향을 45도 반시계 회전시킨다. 만약, 이동할 수 있는 칸이 없으면 이동을 하지 않는다. 그 외의 경우에는 그 칸으로 이동을 한다. 물고기가 다른 물고기가 있는 칸으로 이동할 때는 서로의 위치를 바꾸는 방식으로 이동한다.

물고기의 이동이 모두 끝나면 상어가 이동한다. 상어는 방향에 있는 칸으로 이동할 수 있는데, 한 번에 여러 개의 칸을 이동할 수 있다. 상어가 물고기가 있는 칸으로 이동했다면, 그 칸에 있는 물고기를 먹고, 그 물고기의 방향을 가지게 된다. 이동하는 중에 지나가는 칸에 있는 물고기는 먹지 않는다. 물고기가 없는 칸으로는 이동할 수 없다. 상어가 이동할 수 있는 칸이 없으면 공간에서 벗어나 집으로 간다. 상어가 이동한 후에는 다시 물고기가 이동하며, 이후 이 과정이 계속해서 반복된다.



<그림 1>

<그림 1>은 청소년 상어가 공간에 들어가기 전 초기 상태이다. 상어가 공간에 들어가면 (0, 0)에 있는 7번 물고기를 먹고, 상어의 방향은 ↘이 된다. <그림 2>는 상어가 들어간 직후의 상태를 나타낸다.



<그림 2>

이제 물고기가 이동해야 한다. 1번 물고기의 방향은 ↗이다. ↗ 방향에는 칸이 있고, 15번 물고기가 들어있다. 물고기가 있는 칸으로 이동할 때는 그 칸에 있는 물고기와 위치를 서로 바꿔야 한다. 따라서, 1번 물고기가 이동을 마치면 <그림 3>과 같아진다.



<그림 3>

2번 물고기의 방향은 ←인데, 그 방향에는 상어가 있으니 이동할 수 없다. 방향을 45도 반시계 회전을 하면 ↙가 되고, 이 칸에는 3번 물고기가 있다. 물고기가 있는 칸이니 서로 위치를 바꾸고, <그림 4>와 같아지게 된다.



<그림 4>

3번 물고기의 방향은 ↑이고, 존재하지 않는 칸이다. 45도 반시계 회전을 한 방향 ↖도 존재하지 않으니, 다시 회전을 한다. ← 방향에는 상어가 있으니 또 회전을 해야 한다. ↙ 방향에는 2번 물고기가 있으니 서로의 위치를 교환하면 된다. 이런 식으로 모든 물고기가 이동하면 <그림 5>와 같아진다.



<그림 5>

물고기가 모두 이동했으니 이제 상어가 이동할 순서이다. 상어의 방향은 ↘이고, 이동할 수 있는 칸은 12번 물고기가 있는 칸, 15번 물고기가 있는 칸, 8번 물고기가 있는 칸 중에 하나이다. 만약, 8번 물고기가 있는 칸으로 이동하면, <그림 6>과 같아지게 된다.



<그림 6>

상어가 먹을 수 있는 물고기 번호의 합의 최댓값을 구해보자.

입력
첫째 줄부터 4개의 줄에 각 칸의 들어있는 물고기의 정보가 1번 행부터 순서대로 주어진다. 물고기의 정보는 두 정수 ai, bi로 이루어져 있고, ai는 물고기의 번호, bi는 방향을 의미한다. 방향 bi는 8보다 작거나 같은 자연수를 의미하고, 1부터 순서대로 ↑, ↖, ←, ↙, ↓, ↘, →, ↗ 를 의미한다.

출력
상어가 먹을 수 있는 물고기 번호의 합의 최댓값을 출력한다.

예제 입력 1
7 6 2 3 15 6 9 8
3 1 1 8 14 7 10 1
6 1 13 6 4 3 11 4
16 1 8 7 5 2 12 2
예제 출력 1
33
예제 입력 2
16 7 1 4 4 3 12 8
14 7 7 6 3 4 10 2
5 2 15 2 8 3 6 4
11 8 2 4 13 5 9 4
예제 출력 2
43
예제 입력 3
12 6 14 5 4 5 6 7
15 1 11 7 3 7 7 5
10 3 8 3 16 6 1 1
5 8 2 7 13 6 9 2
예제 출력 3
76
예제 입력 4
2 6 10 8 6 7 9 4
1 7 16 6 4 2 5 8
3 7 8 6 7 6 14 8
12 7 15 4 11 3 13 3
예제 출력 4
39
출처
문제를 만든 사람: baekjoon
알고리즘 분류
구현
시뮬레이션
백트래킹
 */
/*
예제의 그림은 https://www.acmicpc.net/problem/19236에서 참고

알고리즘 핵심
물고기가 움직이고, 상어가 움직이는 방향으로 먹을 수 있는 물고기를 체크하고 해당하는 물고기마다 이전 과정을 반복해서 최대로 먹을 수 있는 물고기 점수를 구하기 때문에
이전 정보들(물고기의 상태, 현재까지의 최고 점수, 상어의 위치)이 유지해야 한다.
(자바는 컬렉션 간에 = 로 지정 시, 주소값 복사로 인해 데이터를 공유하여 잘못된 값이 나올 수 있으므로 deep copy하여 새로운 배열을 넘겨주는 것이 중요)

1. (0,0)에서 상어가 시작하므로 max_scroe를 업데이트하고, backtrac함수를 호출 (인자 : 물고기 상태(위치, 번호, 방향)를 저장한 배열, 현재까지의 점수)
2. find_shark() : 현재 물고기가 존재하는 상태에서 상어의 위치를 반환
3. move_fish() : 현재 물고기가 존재하는 상태에서 물고기들이 번호가 낮은 순으로 이동을 진행한 후의 물고기 상태를 저장한 배열 반환
4. possible_move_shark() : 물고기가 모두 움직인 후에 현재 상어가 위치한 곳에서 이동이 가능한 모든 위치를 반환
5. 가능한 위치를 모두 탐색하여 가능한 위치의 물고기 점수를 score에 업데이트하고 2~4 과정을 반복
5-1. 상어가 가능한 위치가 없는 경우 더이상 상어는 물고기를 먹을 수 없으므로 종료하고 현재까지의 점수를 max_score와 큰 값으로 비교하여 업데이트
 */
public class BOJ19236 {
    static class BOJ19236_fish {
        int r,c,n,d;

        BOJ19236_fish(int r, int c, int n, int d) {
            this.r = r;
            this.c = c;
            this.n = n;
            this.d = d;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int max_score = Integer.MIN_VALUE;
    static BOJ19236_fish[][] space;
    static PriorityQueue<BOJ19236_fish> pq;
    static int[][] direction = {{0,0},{-1,0},{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        int init_score = space[0][0].n;
        space[0][0].n = 0;
        backtrack(space, init_score);

        System.out.println(max_score);
    }

    static void backtrack(BOJ19236_fish[][] s, int score) {
        BOJ19236_fish cur_shark = find_shark(s);
        BOJ19236_fish[][] cur_state = move_fish(s);
        BOJ19236_fish[][] next_state;

        ArrayList<BOJ19236_fish> sharks = possible_move_shark(cur_state, cur_shark);

        for(BOJ19236_fish next_shark : sharks) {
            score += next_shark.n;
            next_state = shark_eat_fish(cur_state, cur_shark, next_shark);
            backtrack(next_state, score);
            score -= next_shark.n;
        }

        if(sharks.isEmpty()) {
            max_score = Math.max(max_score, score);
        }
    }

    static BOJ19236_fish find_shark(BOJ19236_fish[][] s) {
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                if(s[i][j] != null && s[i][j].n == 0) {
                    return s[i][j];
                }
            }
        }
        return null;
    }

    static BOJ19236_fish[][] shark_eat_fish(BOJ19236_fish[][] s, BOJ19236_fish cur_shark, BOJ19236_fish next_shark) {
        BOJ19236_fish[][] ns = store_state(s);

        ns[cur_shark.r][cur_shark.c] = null;
        ns[next_shark.r][next_shark.c].n = 0;

        return ns;
    }

    static BOJ19236_fish[][] store_state(BOJ19236_fish[][] s) {
        BOJ19236_fish[][] ns = new BOJ19236_fish[4][4];

        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                BOJ19236_fish f = s[i][j];
                if(f != null) {
                    ns[i][j] = new BOJ19236_fish(f.r,f.c,f.n,f.d);
                }
            }
        }

        return ns;
    }

    static BOJ19236_fish[][] move_fish(BOJ19236_fish[][] s) {
        BOJ19236_fish[][] ns = store_state(s);
        pq.clear();

        for(int r=0;r<4;r++) {
            for(int c=0;c<4;c++) {
                if(ns[r][c] == null) continue;
                pq.add(ns[r][c]);
            }
        }

        while(!pq.isEmpty()) {
            BOJ19236_fish f = pq.poll();

            if(f.n == 0) continue;

            while(true) {
                int nr = f.r + direction[f.d][0];
                int nc = f.c + direction[f.d][1];

                if(nr < 0 || nr > 3 || nc < 0 || nc > 3 || (ns[nr][nc] != null && ns[nr][nc].n == 0)) {
                    f.d = (f.d + 1) == 9 ? 1 : f.d + 1;
                    continue;
                }

                value_swap(ns, f.r, f.c, nr, nc);

                //print(ns);

                break;
            }
        }

        return ns;
    }

    static void print(BOJ19236_fish[][] s) {
        System.out.println("number");
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                if(s[i][j] == null) {
                    System.out.print(-1 + " ");
                } else {
                    System.out.print(s[i][j].n + " ");
                }
            }
            System.out.println();
        }

        System.out.println("direction");
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                if(s[i][j] == null) {
                    System.out.print(-1 + " ");
                } else {
                    System.out.print(s[i][j].n + " ");
                }
            }
            System.out.println();
        }

        System.out.println("----------------------------");
    }

    static void value_swap(BOJ19236_fish[][] s, int pr, int pc, int nr, int nc) {
        BOJ19236_fish prev = s[pr][pc];

        s[pr][pc] = s[nr][nc];
        s[nr][nc] = prev;

        if(s[pr][pc] != null) {
            s[pr][pc].r = pr;
            s[pr][pc].c = pc;
        }

        if(s[nr][nc] != null) {
            s[nr][nc].r = nr;
            s[nr][nc].c = nc;
        }
    }

    static ArrayList<BOJ19236_fish> possible_move_shark(BOJ19236_fish[][] s, BOJ19236_fish shark) {
        ArrayList<BOJ19236_fish> pos = new ArrayList<>();

        int nr = shark.r;
        int nc = shark.c;

        while(true) {
            nr += direction[shark.d][0];
            nc += direction[shark.d][1];

            if(nr < 0 || nr > 3 || nc < 0 || nc > 3) break;
            if(s[nr][nc] == null) continue;

            pos.add(s[nr][nc]);
        }

        return pos;
    }

    static void init_setting() throws IOException {
        space = new BOJ19236_fish[4][4];
        pq = new PriorityQueue<>(new Comparator<BOJ19236_fish>() {
            @Override
            public int compare(BOJ19236_fish o1, BOJ19236_fish o2) {
                return o1.n - o2.n;
            }
        });

        for(int r=0;r<4;r++) {
            String[] input = br.readLine().split(" ");
            for(int c=0;c<4;c++) {
                int n = Integer.parseInt(input[2*c]);
                int d = Integer.parseInt(input[(2*c) + 1]);
                space[r][c] = new BOJ19236_fish(r,c,n,d);
            }
        }
    }
}
