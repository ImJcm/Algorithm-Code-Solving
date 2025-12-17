package Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
원판 돌리기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	21335	8101	5196	34.440%
문제
반지름이 1, 2, ..., N인 원판이 크기가 작아지는 순으로 바닥에 놓여있고, 원판의 중심은 모두 같다. 원판의 반지름이 i이면, 그 원판을 i번째 원판이라고 한다. 각각의 원판에는 M개의 정수가 적혀있고, i번째 원판에 적힌 j번째 수의 위치는 (i, j)로 표현한다. 수의 위치는 다음을 만족한다.

(i, 1)은 (i, 2), (i, M)과 인접하다.
(i, M)은 (i, M-1), (i, 1)과 인접하다.
(i, j)는 (i, j-1), (i, j+1)과 인접하다. (2 ≤ j ≤ M-1)
(1, j)는 (2, j)와 인접하다.
(N, j)는 (N-1, j)와 인접하다.
(i, j)는 (i-1, j), (i+1, j)와 인접하다. (2 ≤ i ≤ N-1)
아래 그림은 N = 3, M = 4인 경우이다.



원판의 회전은 독립적으로 이루어진다. 2번 원판을 회전했을 때, 나머지 원판은 회전하지 않는다. 원판을 회전시킬 때는 수의 위치를 기준으로 하며, 회전시킨 후의 수의 위치는 회전시키기 전과 일치해야 한다.

다음 그림은 원판을 회전시킨 예시이다.


1번 원판을 시계 방향으로 1칸 회전	2, 3번 원판을 반시계 방향으로 3칸 회전	1, 3번 원판을 시계 방향으로 2칸 회전
원판을 아래와 같은 방법으로 총 T번 회전시키려고 한다. 원판의 회전 방법은 미리 정해져 있고, i번째 회전할때 사용하는 변수는 xi, di, ki이다.

번호가 xi의 배수인 원판을 di방향으로 ki칸 회전시킨다. di가 0인 경우는 시계 방향, 1인 경우는 반시계 방향이다.
원판에 수가 남아 있으면, 인접하면서 수가 같은 것을 모두 찾는다.
그러한 수가 있는 경우에는 원판에서 인접하면서 같은 수를 모두 지운다.
없는 경우에는 원판에 적힌 수의 평균을 구하고, 평균보다 큰 수에서 1을 빼고, 작은 수에는 1을 더한다.
원판을 T번 회전시킨 후 원판에 적힌 수의 합을 구해보자.

입력
첫째 줄에 N, M, T이 주어진다.

둘째 줄부터 N개의 줄에 원판에 적힌 수가 주어진다. i번째 줄의 j번째 수는 (i, j)에 적힌 수를 의미한다.

다음 T개의 줄에 xi, di, ki가 주어진다.

출력
원판을 T번 회전시킨 후 원판에 적힌 수의 합을 출력한다.

제한
2 ≤ N, M ≤ 50
1 ≤ T ≤ 50
1 ≤ 원판에 적힌 수 ≤ 1,000
2 ≤ xi ≤ N
0 ≤ di ≤ 1
1 ≤ ki < M
예제 입력 1
4 4 1
1 1 2 3
5 2 4 2
3 1 3 5
2 1 3 2
2 0 1
예제 출력 1
30
원판의 초기 상태는 다음과 같다.




x1 = 2, d1 = 0, k1 = 1

2번, 4번 원판을 시계 방향으로 1칸 돌린 후

인접하면서 수가 같은 것을 모두 지운 후

예제 입력 2
4 4 2
1 1 2 3
5 2 4 2
3 1 3 5
2 1 3 2
2 0 1
3 1 3
예제 출력 2
22
예제 1에서 이어진다.


x2 = 3, d2 = 1, k2 = 3

3번 원판을 반시계 방향으로 3칸 돌린 후

인접하면서 수가 같은 것을 모두 지운 후

예제 입력 3
4 4 3
1 1 2 3
5 2 4 2
3 1 3 5
2 1 3 2
2 0 1
3 1 3
2 0 2
예제 출력 3
22
예제 2에서 이어진다.


x3 = 2, d3 = 0, k3 = 2

2, 4번 원판을 시계 방향으로 2칸 돌린 후

인접하면서 수가 같은 것이 없다.

따라서, 평균 22/6 보다 작은 수는 +1, 큰 수는 -1 했다.

예제 입력 4
4 4 4
1 1 2 3
5 2 4 2
3 1 3 5
2 1 3 2
2 0 1
3 1 3
2 0 2
3 1 1
예제 출력 4
0
예제 3에서 이어진다.

x4 = 3, d4 = 1, k4 = 1

3번 원판을 반시계 방향으로 1칸 돌린 후

인접하면서 수가 같은 것을 모두 지운 후

예제 입력 5
4 6 3
1 2 3 4 5 6
2 3 4 5 6 7
3 4 5 6 7 8
4 5 6 7 8 9
2 1 4
3 0 1
2 1 2
예제 출력 5
26
출처
문제를 만든 사람: baekjoon
문제의 오타를 찾은 사람: jh05013, sodp2
잘못된 조건을 찾은 사람: pichulia
알고리즘 분류
구현
시뮬레이션
 */
/*
처음 접근 방식으로 Deque를 사용하여 시계, 반시계 방향 회전을 쉽게 가능하다고 생각하여 사용하였지만, 인접한 숫자를 확인하는 과정에서
deque 특성상 중간의 인덱스 정보를 알 수 있는 방법이 없어서 ArrayList를 사용하기로 결정하였다. 하지만, ArrayList를 사용하면
앞, 뒤로 데이터를 추가하는데 용이하지 못하므로 Stack, Queue로서 사용이 가능한 LinkedList를 사용하기로 결정하였다.

주의할 점 : 인접한 경우가 없을경우, 전체 숫자들의 합 / 남은 숫자의 개수로 각 숫자들을 비교하여 +1, -1하는 과정에서
double타입으로 타입 캐스팅하여 비교를 진행해야 한다.

알고리즘 과정
0. 원형판에 나타낼 number 클래스와 어떤 회전판을 돌릴지 정보를 저장한 order 클래스 생성
1. LinkedList인 stencil에 입력 데이터 저장, 원형판 돌리는 정보 orders에 저장
2. orders에 저장된 객체 개수만큼 반복문을 수행하여 해당하는 원형판을 회전시킨다.
3. 시계방향 : clockwise_rotate, 반시계방향 : counterclockwise_rotate 수행
4. 각 방향별로 원형판을 돌린 후, 해당하는 위치인 m을 업데이트한다.
4-1. 12방향을 기준으로 시계방향 순으로 0,1,...,M-1 순서를 나타낸다. (LinkedList에서 addFirst(), addLast()와 같은 함수 사용 시, 해당하는 객체의 순서는 변경되지만, 해당 객체 내부의 순서 값인 m이 업데아트가
되지 않으므로 k번 회전한 후, 0~M-1 순으로 number객체의 m값을 인덱스 번호로 업데이트한다.
5. 회전을 마친 후, cleanup_stencil()을 수행 -> 원형판에 사라지지 않은 숫자를 나타내는 여부를 number 객체의 isUsed의 true or false 값으로
인접 숫자가 존재하는지 확인하는 과정을 수행할 객체인지 판단할 수 있다.
5-1. isUsed가 false인 객체는 bfs를 통해 인접한 숫자들을 판별하여 동일한 숫자의 객체를 제거한다. 이때, total_sum을 통해 현재 원형판에 숫자가 남아있는지 여부와 총합의 여부를 파악할 수 있다.
6. 원형판에 숫자가 존재하고 bfs를 모두 거친 후의 total_sum 값과 이전 값이 동일하다면 평균값을 계산한 값을 기준으로 +1, -1을 수행한다.
7. Order의 모든 수행이 종료 시, total_sum을 출력
 */
public class BOJ17822 {
    static class BOJ17822_number {
        int i,m,n;
        boolean isUsed;

        BOJ17822_number(int i,int m, int n, boolean i_U) {
            this.i = i;
            this.m = m;
            this.n = n;
            this.isUsed = i_U;
        }
    }

    static class BOJ17822_order {
        int x,d,k;

        BOJ17822_order(int x, int d, int k) {
            this.x = x;
            this.d = d;
            this.k = k;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,T,total_sum;
    static int[][] direction = {{0,-1},{0,1},{-1,0},{1,0}};
    static LinkedList<BOJ17822_number>[] stencil;
    static ArrayList<BOJ17822_order> orders;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(BOJ17822_order o : orders) {
            for(int i=2;i<=N;i++) {
                if(i % o.x == 0) {
                    rotate_stencil(i,o.d,o.k);
                }
            }
            cleanup_stencil();

        }

        System.out.println(total_sum);
    }

    // 테스트용 출력 함수
    static void print() {
        for(int i=1;i<=N;i++) {
            for(BOJ17822_number n : stencil[i]) {
                if(n.isUsed) {
                    System.out.print("x ");
                } else {
                    System.out.print(n.n + " ");
                }

            }
            System.out.println();
        }
    }

    static void rotate_stencil(int i, int d, int k) {
        switch (d) {
            case 0:
                rotate_clockwise(i,k);
                break;
            case 1:
                rotate_counterclockwise(i,k);
                break;
        }
    }

    static void rotate_clockwise(int i,int k) {
        for(int o=0;o<k;o++) {
            stencil[i].addFirst(stencil[i].removeLast());
        }

        for(int m=0;m<stencil[i].size();m++) {
            stencil[i].get(m).m = m;
        }
    }

    static void rotate_counterclockwise(int i, int k) {
        for(int o=0;o<k;o++) {
            stencil[i].addLast(stencil[i].removeFirst());
        }

        for(int m=0;m<stencil[i].size();m++) {
            stencil[i].get(m).m = m;
        }
    }

    static void cleanup_stencil() {
        int prev_total_sum = total_sum;
        int remain_nums = 0;

        for(int i=1;i<=N;i++) {
            for(BOJ17822_number n : stencil[i]) {
                if(n.isUsed) continue;
                bfs(n);
                remain_nums++;
            }
        }

        if(prev_total_sum == total_sum) {
            no_change_operation(remain_nums);
        }
    }

    static void bfs(BOJ17822_number n) {
        Queue<BOJ17822_number> q = new LinkedList<>();
        boolean has_adj = false;

        q.offer(n);
        n.isUsed = true;
        total_sum -= n.n;

        while(!q.isEmpty()) {
            BOJ17822_number now = q.poll();

            for(int[] d : direction) {
                int ni = now.i + d[0];
                int nm = now.m + d[1];

                nm = (nm < 0 || nm >= M) ? ((nm < 0) ? M-1 : 0) : nm;

                if(ni < 1 || ni > N) continue;

                BOJ17822_number nn = stencil[ni].get(nm);

                if(nn.isUsed) continue;

                if(nn.n == now.n) {
                    q.offer(nn);
                    nn.isUsed = true;
                    total_sum -= nn.n;
                    has_adj = true;
                }
            }
        }

        if (!has_adj) {
            n.isUsed = false;
            total_sum += n.n;
        }

    }

    static void no_change_operation(int r_n) {
        double avg = (double) total_sum / r_n;

        for(int i=1;i<=N;i++) {
            for(int j=0;j<M;j++) {
                BOJ17822_number n = stencil[i].get(j);
                if(n.isUsed) continue;

                if(n.n > avg) {
                    n.n -= 1;
                    total_sum -= 1;
                } else if(n.n < avg){
                    n.n += 1;
                    total_sum += 1;
                }
            }
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        T = Integer.parseInt(input[2]);

        stencil = new LinkedList[N+1];
        orders = new ArrayList<>();
        total_sum = 0;

        stencil[0] = new LinkedList<>();
        for(int i=1;i<=N;i++) {
            stencil[i] = new LinkedList<>();

            input = br.readLine().split(" ");
            for(int j=0;j<M;j++) {
                int num = Integer.parseInt(input[j]);
                stencil[i].add(new BOJ17822_number(i,j,num,false));
                total_sum += num;
            }
        }

        for(int t=0;t<T;t++) {
            input = br.readLine().split(" ");

            int x = Integer.parseInt(input[0]);
            int d = Integer.parseInt(input[1]);
            int k = Integer.parseInt(input[2]);

            orders.add(new BOJ17822_order(x,d,k));
        }

    }
}
