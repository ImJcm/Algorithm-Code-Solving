package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
병든 나이트

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	18063	7963	6768	43.974%
문제
병든 나이트가 N × M 크기 체스판의 가장 왼쪽아래 칸에 위치해 있다. 병든 나이트는 건강한 보통 체스의 나이트와 다르게 4가지로만 움직일 수 있다.

2칸 위로, 1칸 오른쪽
1칸 위로, 2칸 오른쪽
1칸 아래로, 2칸 오른쪽
2칸 아래로, 1칸 오른쪽
병든 나이트는 여행을 시작하려고 하고, 여행을 하면서 방문한 칸의 수를 최대로 하려고 한다. 병든 나이트의 이동 횟수가 4번보다 적지 않다면, 이동 방법을 모두 한 번씩 사용해야 한다. 이동 횟수가 4번보다 적은 경우(방문한 칸이 5개 미만)에는 이동 방법에 대한 제약이 없다.

체스판의 크기가 주어졌을 때, 병든 나이트가 여행에서 방문할 수 있는 칸의 최대 개수를 구해보자.

입력
첫째 줄에 체스판의 세로 길이 N와 가로 길이 M이 주어진다. N과 M은 2,000,000,000보다 작거나 같은 자연수이다.

출력
병든 나이트가 여행에서 방문할 수 있는 칸의 개수중 최댓값을 출력한다.

예제 입력 1
100 50
예제 출력 1
48
예제 입력 2
1 1
예제 출력 2
1
예제 입력 3
17 5
예제 출력 3
4
예제 입력 4
2 4
예제 출력 4
2
예제 입력 5
20 4
예제 출력 5
4
출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: gudori888, han1g, jh05013, luluctrl4, shnoh
알고리즘 분류
구현
그리디 알고리즘
많은 조건 분기
 */
public class BOJ1783 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int n,m,N,M,ans;
    static int[][] move = {{-2,1},{2,1},{-1,2},{1,2}};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        boolean all_use = false;

        if(N > 2 && M > 6) {
            ans += 4;
            m += 6;
            all_use = true;
        }

        if(all_use) {
            ans += (M - 1 - m);
        } else {
            int impossible = 0;
            int i = 0;
            while(true) {
                if(ailing_knight_move(i)) {
                    impossible = 0;
                    i = 0;
                } else {
                    impossible++;
                    i++;
                }

                if(impossible >= 4) break;
            }
        }

        System.out.println(ans);
    }

    /*
        4% 실패 코드 : 시간 초과
        while(true) {...}의 과정을 수행하는 횟수가 많아서 생기는 것이 원인이라고 예상한다.
     */
    private static void wrong_solve() {
        int impossible = 0;
        int i = 0;
        boolean all_use = false;

        if(N > 2 && M > 6) {
            ans += 4;
            m += 6;
            all_use = true;
        }

        while(true) {
            if(ailing_knight_move(i)) {
                impossible = 0;
                i = 0;
            } else {
                impossible++;
                i++;
            }

            if(impossible >= 4 || (!all_use && ans == 4)) break;
        }

        System.out.println(ans);
    }

    private static boolean ailing_knight_move(int t) {
        int nn = n + move[t][0];
        int nm = m + move[t][1];

        boolean pos = range_check(nn, nm);

        if(pos) {
            n = nn;
            m = nm;
            ans++;
        }
        return pos;
    }

    private static boolean range_check(int pn, int pm) {
        if(pn < 0 || pn >= N || pm < 0 || pm >= M) return false;
        else return true;
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        ans = 1;

        n = N - 1;
        m = 0;
    }
}
