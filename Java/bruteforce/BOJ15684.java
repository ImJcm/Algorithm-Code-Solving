package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
사다리 조작

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	72790	19633	9589	22.042%
문제
사다리 게임은 N개의 세로선과 M개의 가로선으로 이루어져 있다. 인접한 세로선 사이에는 가로선을 놓을 수 있는데, 각각의 세로선마다 가로선을 놓을 수 있는 위치의 개수는 H이고, 모든 세로선이 같은 위치를 갖는다. 아래 그림은 N = 5, H = 6 인 경우의 그림이고, 가로선은 없다.



초록선은 세로선을 나타내고, 초록선과 점선이 교차하는 점은 가로선을 놓을 수 있는 점이다. 가로선은 인접한 두 세로선을 연결해야 한다. 단, 두 가로선이 연속하거나 서로 접하면 안 된다. 또, 가로선은 점선 위에 있어야 한다.



위의 그림에는 가로선이 총 5개 있다. 가로선은 위의 그림과 같이 인접한 두 세로선을 연결해야 하고, 가로선을 놓을 수 있는 위치를 연결해야 한다.

사다리 게임은 각각의 세로선마다 게임을 진행하고, 세로선의 가장 위에서부터 아래 방향으로 내려가야 한다. 이때, 가로선을 만나면 가로선을 이용해 옆 세로선으로 이동한 다음, 이동한 세로선에서 아래 방향으로 이동해야 한다.

위의 그림에서 1번은 3번으로, 2번은 2번으로, 3번은 5번으로, 4번은 1번으로, 5번은 4번으로 도착하게 된다. 아래 두 그림은 1번과 2번이 어떻게 이동했는지 나타내는 그림이다.


1번 세로선	2번 세로선
사다리에 가로선을 추가해서, 사다리 게임의 결과를 조작하려고 한다. 이때, i번 세로선의 결과가 i번이 나와야 한다. 그렇게 하기 위해서 추가해야 하는 가로선 개수의 최솟값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 세로선의 개수 N, 가로선의 개수 M, 세로선마다 가로선을 놓을 수 있는 위치의 개수 H가 주어진다. (2 ≤ N ≤ 10, 1 ≤ H ≤ 30, 0 ≤ M ≤ (N-1)×H)

둘째 줄부터 M개의 줄에는 가로선의 정보가 한 줄에 하나씩 주어진다.

가로선의 정보는 두 정수 a과 b로 나타낸다. (1 ≤ a ≤ H, 1 ≤ b ≤ N-1) b번 세로선과 b+1번 세로선을 a번 점선 위치에서 연결했다는 의미이다.

가장 위에 있는 점선의 번호는 1번이고, 아래로 내려갈 때마다 1이 증가한다. 세로선은 가장 왼쪽에 있는 것의 번호가 1번이고, 오른쪽으로 갈 때마다 1이 증가한다.

입력으로 주어지는 가로선이 서로 연속하는 경우는 없다.

출력
i번 세로선의 결과가 i번이 나오도록 사다리 게임을 조작하려면, 추가해야 하는 가로선 개수의 최솟값을 출력한다. 만약, 정답이 3보다 큰 값이면 -1을 출력한다. 또, 불가능한 경우에도 -1을 출력한다.

예제 입력 1
2 0 3
예제 출력 1
0
예제 입력 2
2 1 3
1 1
예제 출력 2
1
예제 입력 3
5 5 6
1 1
3 2
2 3
5 1
5 4
예제 출력 3
3
예제 입력 4
6 5 6
1 1
3 2
1 3
2 5
5 5
예제 출력 4
3
예제 입력 5
5 8 6
1 1
2 2
3 3
4 4
3 1
4 2
5 3
6 4
예제 출력 5
-1
예제 입력 6
5 12 6
1 1
1 3
2 2
2 4
3 1
3 3
4 2
4 4
5 1
5 3
6 2
6 4
예제 출력 6
-1
예제 입력 7
5 6 6
1 1
3 1
5 2
4 3
2 3
1 4
예제 출력 7
2
힌트

예제 3	예제 3 정답

예제 7	예제 7 정답
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: commetgo, line1029, seobing
문제의 오타를 찾은 사람: hyakintoss
빠진 조건을 찾은 사람: ntopia
알고리즘 분류
구현
브루트포스 알고리즘
백트래킹
 */
/*
알고리즘 핵심
1. 추가된 사다리 개수가 0인 경우, 사다리 타기 결과를 체크한다.
2. 추가된 사다리 개수가 1~3인 경우의 모든 경우의 수를 구한다.
3. 2에서 구한 경우의 사다리 타기를 진행하여 사용된 사다리 개수의 최솟값을 업데이트한다.
4. 모든 경우의 수를 체크한 후 ans가 Integer.MAX_VALUE이라면, 3보다 추가되는 사다리를 사용해야 하는 것이므로 , -1을 반환한다.
(사다리를 3개 초과하여 사용하는 경우는 불가능하거나 4개 이상의 사다리를 사용해야 하는 것)
 */
public class BOJ15684 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,H,ans;
    static int[][] leader;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        if(check_leader()) {
            ans = 0;
        } else {
            for(int i=1;i<4 && ans == Integer.MAX_VALUE;i++) {
                dfs(0,i,1,1);
            }
        }

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    static void dfs(int depth, int limit_leader_cnt, int next_N, int next_H) {
        if(depth == limit_leader_cnt) {
            if(check_leader()) {
                ans = Math.min(ans, limit_leader_cnt);
            }
            return;
        }

        for(int i=next_H;i<=H;i++) {
            for(int j=(i == next_H ? next_N : 1);j<N;j++) {
                if(leader[i][j] != 0 || leader[i][j+1] != 0) continue;
                leader[i][j] = 1;
                leader[i][j+1] = 2;
                dfs(depth + 1, limit_leader_cnt, j, i);
                leader[i][j] = leader[i][j+1] = 0;
            }
        }
    }

    static boolean check_leader() {
        boolean check = true;

        for(int i=1;i<=N;i++) {
            int sn = i;
            int sh = 1;

            while(sh <= H) {
                int cur = leader[sh][sn];

                if(cur == 1) {
                    sn += 1;
                } else if(cur == 2) {
                    sn -= 1;
                }

                sh += 1;
            }

            if(sn != i) {
                check = false;
            }
        }

        return check;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        H = Integer.parseInt(input[2]);

        leader = new int[H+1][N+1];

        ans = Integer.MAX_VALUE;

        for(int i=0;i<M;i++) {
            input = br.readLine().split(" ");

            int a = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);

            leader[a][b] = 1;
            leader[a][b+1] = 2;
        }
    }
}
