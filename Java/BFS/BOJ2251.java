package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

/*
물통 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	20692	11073	8264	53.953%
문제
각각 부피가 A, B, C(1≤A, B, C≤200) 리터인 세 개의 물통이 있다. 처음에는 앞의 두 물통은 비어 있고, 세 번째 물통은 가득(C 리터) 차 있다. 이제 어떤 물통에 들어있는 물을 다른 물통으로 쏟아 부을 수 있는데, 이때에는 한 물통이 비거나, 다른 한 물통이 가득 찰 때까지 물을 부을 수 있다. 이 과정에서 손실되는 물은 없다고 가정한다.

이와 같은 과정을 거치다보면 세 번째 물통(용량이 C인)에 담겨있는 물의 양이 변할 수도 있다. 첫 번째 물통(용량이 A인)이 비어 있을 때, 세 번째 물통(용량이 C인)에 담겨있을 수 있는 물의 양을 모두 구해내는 프로그램을 작성하시오.

입력
첫째 줄에 세 정수 A, B, C가 주어진다.

출력
첫째 줄에 공백으로 구분하여 답을 출력한다. 각 용량은 오름차순으로 정렬한다.

예제 입력 1
8 9 10
예제 출력 1
1 2 8 9 10
출처
Olympiad > USA Computing Olympiad > 2000-2001 Season > USACO Winter 2001 Contest > Orange 4번

문제의 오타를 찾은 사람: kesakiyo
데이터를 추가한 사람: methylene
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
깊이 우선 탐색
 */
/*
알고리즘 핵심
BFS
1. A,B,C의 물병에 담긴 물의 양을 하나의 데이터로 인식한다.
2. BFS를 통해 A -> B or C / B -> A or C / C -> A or B의 6가지의 물의 이동을 수행한다.
 */
public class BOJ2251 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    private static class Solve {
        private class Bottles {
            int a,b,c;

            Bottles(int a, int b, int c) {
                this.a = a;
                this.b = b;
                this.c = c;
            }
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int A,B,C;
        ArrayList<Integer> ans;

        void solve() throws IOException {
            init_setting();

            bfs();

            System.out.println(Arrays.stream(ans.toArray())
                    .sorted()
                    .distinct()
                    .map(String::valueOf)
                    .collect(Collectors.joining(" ")));
        }

        private void bfs() {
            Queue<Bottles> q = new LinkedList<>();
            boolean[][][] visited = new boolean[A + 1][B + 1][C + 1];

            q.add(new Bottles(0, 0, C));
            visited[0][0][C] = true;

            while(!q.isEmpty()) {
                Bottles now = q.poll();

                if(now.a == 0) ans.add(now.c);

                for(int i = 0; i < 6; i++) {
                    Bottles new_bottles = move_water(i,now);

                    if(visited[new_bottles.a][new_bottles.b][new_bottles.c]) continue;

                    visited[new_bottles.a][new_bottles.b][new_bottles.c] = true;
                    q.add(new Bottles(new_bottles.a, new_bottles.b, new_bottles.c));
                }
            }
        }

        private Bottles move_water(int s, Bottles b) {
            Bottles nb = new Bottles(b.a, b.b, b.c);
            int sum = 0;

            switch (s) {
                case 0: // A -> B
                    sum = b.a + b.b;
                    nb.b = Math.min(sum, B);
                    nb.a = Math.max(sum - nb.b, 0);
                    break;
                case 1: // A -> C
                    sum = b.a + b.c;
                    nb.c = Math.min(sum, C);
                    nb.a = Math.max(sum - nb.c, 0);
                    break;
                case 2: // B -> A
                    sum = b.a + b.b;
                    nb.a = Math.min(sum, A);
                    nb.b = Math.max(sum - nb.a, 0);
                    break;
                case 3: // B -> C
                    sum = b.b + b.c;
                    nb.c = Math.min(sum, C);
                    nb.b = Math.max(sum - nb.c, 0);
                    break;
                case 4: // C -> A
                    sum = b.a + b.c;
                    nb.a = Math.min(sum, A);
                    nb.c = Math.max(sum - nb.a, 0);
                    break;
                case 5: // C -> B
                    sum = b.b + b.c;
                    nb.b = Math.min(sum, B);
                    nb.c = Math.max(sum - nb.b, 0);
                    break;
            }
            return nb;
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            A = Integer.parseInt(input[0]);
            B = Integer.parseInt(input[1]);
            C = Integer.parseInt(input[2]);

            ans = new ArrayList<>();
        }
    }
}
