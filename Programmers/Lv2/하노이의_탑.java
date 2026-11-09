package Lv2;

import java.util.*;

/*
하노이의 탑
제출 내역
문제 설명
하노이 탑(Tower of Hanoi)은 퍼즐의 일종입니다. 세 개의 기둥과 이 기동에 꽂을 수 있는 크기가 다양한 원판들이 있고, 퍼즐을 시작하기 전에는 한 기둥에 원판들이 작은 것이 위에 있도록 순서대로 쌓여 있습니다. 게임의 목적은 다음 두 가지 조건을 만족시키면서, 한 기둥에 꽂힌 원판들을 그 순서 그대로 다른 기둥으로 옮겨서 다시 쌓는 것입니다.

한 번에 하나의 원판만 옮길 수 있습니다.
큰 원판이 작은 원판 위에 있어서는 안됩니다.
하노이 탑의 세 개의 기둥을 왼쪽 부터 1번, 2번, 3번이라고 하겠습니다. 1번에는 n개의 원판이 있고 이 n개의 원판을 3번 원판으로 최소 횟수로 옮기려고 합니다.

1번 기둥에 있는 원판의 개수 n이 매개변수로 주어질 때, n개의 원판을 3번 원판으로 최소로 옮기는 방법을 return하는 solution를 완성해주세요.

제한사항
n은 15이하의 자연수 입니다.
입출력 예
n	result
2	[ [1,2], [1,3], [2,3] ]
입출력 예 설명
입출력 예 #1
다음과 같이 옮길 수 있습니다.

Imgur
Imgur
Imgur
Imgur
 */
public class 하노이의_탑 {
    static void main() {
        int n = 2;

        Solve task = new Solve();
        System.out.println(Arrays.deepToString(task.solution(n)));
    }

    private static class Solve {
        private class Move {
            int state,dir;  // dir : 정방향, 역방향 의미
            ArrayList<int[]> order;

            public Move(int state, ArrayList<int[]> order) {
                this.state = state;
                this.order = new ArrayList<>(order);
            }

            public Move(int state, int dir, ArrayList<int[]> order) {
                this.state = state;
                this.dir = dir;
                this.order = new ArrayList<>(order);
            }

            private void addOrder(int[] order) {
                this.order.add(order);
            }
        }
        private final int T = 3;
        private int N,E;
        private int[][] ans;

        public int[][] solution(int n) {
            init_setting(n);

            hanoi_tower(0,E);

            return ans;
        }

        /*
            #1 : 하노이의 탑 과정에 방향성을 두어 양 끝의 상태에서 중간부분에서 만나는 상태를 비교하여 순서를 종합한다.
            #2 : Wrong Solve : TC#11,12 - timeout, TC#13 : Memory dump
         */
        private void hanoi_tower(int start, int end) {
            Queue<Move> q = new LinkedList<>();
            q.add(new Move(start,1,new ArrayList<>()));       // start = 0 => 모든 원반이 0번째 탑에 있다.
            q.add(new Move(end,-1,new ArrayList<>()));

            int[] visited = new int[1 << (N * 2)];
            visited[start] = 1;
            visited[end] = -1;

            int flag = 0;
            int flag_state = 0;
            Move flag_move = null;

            while(!q.isEmpty()) {
                Move s = q.poll();

                if(flag != 0) {
                    if(s.state == flag_state && s.dir == flag) {
                        ArrayList<int[]> arr;
                        if(flag == 1) {
                            arr = new ArrayList<>(s.order);
                            arr.addAll(flag_move.order.reversed());
                        } else {
                            arr = new ArrayList<>(flag_move.order);
                            arr.addAll(s.order.reversed());
                        }
                        ans = arr.toArray(new int[arr.size()][N]);
                        return;
                    }
                    continue;
                }

                int[] top = new int[] {-1, -1, -1};
                for(int i = N - 1; i >= 0; i--) {
                    top[get_location(s.state,i)] = i;
                }

                for(int i = 0; i < T; i++) {
                    if(top[i] != -1) {
                        for(int j = 0; j < T; j++) {
                            if(i != j && (top[j] == -1 || top[i] < top[j])) {
                                int ns = set_location(s.state, top[i], j);

                                ArrayList<int[]> norder = new ArrayList<>(s.order);
                                if(s.dir == 1) norder.add(new int[] {i + 1, j + 1});
                                else norder.add(new int[] {j + 1, i + 1});

                                if(visited[ns] == 0) {
                                    visited[ns] = s.dir;
                                    q.add(new Move(ns, s.dir, norder));
                                } else if(s.dir != visited[ns]) {
                                    flag = s.dir * -1;
                                    flag_state = ns;
                                    flag_move = new Move(s.state, s.dir, norder);
                                }
                            }
                        }
                    }
                }
            }
        }

        /*
            32bit의 정수에서 각각의 2bit를 원반으로 가정하고, 해당하는 2bit의 값을 하노이 탑의 위치로 설정하여
            방문여부와 원반의 위치를 구할 수 있다. (1번탑 : 00, 2번탑 : 01, 3번탑 : 10)
            즉, n = 3)
            000000 => 1번 원판은 1번 탑, 2번 원판은 1번탑, 3번 원판은 1번탑을 의미
            100100 => 1번 원판은 1번 탑, 2번 원판은 2번탑, 3번 원판은 3번탑을 의미

            WrongSolve : timeout TC#11~13
         */
        private void hanoi_tower_timeout(int start) {
            Queue<Move> q = new LinkedList<>();
            q.add(new Move(start,new ArrayList<>()));       // start = 0 => 모든 원반이 0번째 탑에 있다.

            boolean[] visited = new boolean[1 << (N * 2)];
            visited[0] = true;

            while(!q.isEmpty()) {
                Move s = q.poll();

                if(s.state == E) {
                    ans = s.order.toArray(new int[s.order.size()][N]);
                    return;
                }

                int[] top = new int[] {-1, -1, -1};
                for(int i = N - 1; i >= 0; i--) {
                    top[get_location(s.state,i)] = i;
                }

                for(int i = 0; i < T; i++) {
                    if(top[i] != -1) {
                        for(int j = 0; j < T; j++) {
                            if(i != j && (top[j] == -1 || top[i] < top[j])) {
                                int ns = set_location(s.state, top[i], j);
                                if(!visited[ns]) {
                                    visited[ns] = true;
                                    ArrayList<int[]> norder = new ArrayList<>(s.order);
                                    norder.add(new int[] {i + 1, j + 1});
                                    q.add(new Move(ns, norder));
                                }
                            }
                        }
                    }
                }
            }
        }

        private int set_location(int s, int i, int j) {
            /*
                i번째 원반의 위치를 j번 타워로 변경
                (s & ~(3 << (i * 2))) : 상태 정보에서 i번째 원반의 위치 2비트를 00으로 초기화
                | (j << (i * 2)) : i번째 원반의 위치를 j번째 타워로 변경
             */
            return (s & ~(3 << (i * 2))) | (j << (i * 2));
        }

        private int get_location(int s, int i) {
            return (s >> (i * 2)) & 3;
        }

        private void init_setting(int n) {
            ans = null;

            N = n;
            E = 0;

            for(int i = 0; i < n; i++) {
                E = (E << 2) + 2;
            }
        }
    }
}
