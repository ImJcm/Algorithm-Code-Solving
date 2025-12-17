package greedy;

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
/*
알고리즘 핵심
그리디 알고리즘 + 분기 조건문
1. 조건을 주어진 병든 나이트의 움직일 수 있는 경우의 수 4가지를 보고 N,M의 값에 따라 방문할 수 있는 칸의 개수가 정해진다는 것을 알 수있다.
2. 4가지의 움직이는 경우의 수를 모두 사용하여 움직이는 경우는 N > 2 && M > 6인 경우 모두 사용할 수 있으므로 이후의 움직임에 제약이 없으므로
M값에 따라 1,4번 움직임을 반복하면 한번의 움직임으로 오른쪽 한번의 움직임을 수행하므로 M - 1 - 6의 값으로 방문 칸을 예상할 수 있다.
(M - 1 - 6인 이유는 -> -1 : M의 좌표 상의 위치, -6 : 4가지의 모든 움직임을 수행한 후 위치)
3. N <= 2 || M <= 6의 각 경우에 해당하는 방문할 수 있는 칸의 갯수를 유추할 수 있으므로 많은 조건 분기를 사용하여 방문 칸을 유추한다.

처음 접근으로 4가지의 이동을 할 수 있는지 없는지 여부를 검사하고 남은 좌표상의 방문할 수 있는 칸을 각 이동 경우에 따라 이동을 수행하여
최대 방문 칸을 계산하려고 하였지만 N,M의 값이 크기 때문에 시간초과가 발생하였다.

이후, 굳이 이동하는 과정을 수행할 필요가 없다는 것을 이동하는 방법에서 1,4 / 2,3을 하나로 묶어 보고 최대 방문 칸을 만드려면 결국
1,4번의 과정을 많이 이용하는 방법과 1,4가 불가능할 때 2,3을 사용하는 방법으로 방문할 수 있는 칸을 계산할 수 있다고 생각이 들었다.

그래서, 각 이동을 한번씩 이용할 수 있는 경우와 아닌 경우를 나누고 N,M의 값에 따라 수행할 수 있는 이동 방법을 고려하여 조건 분기를 생성하여
답을 구할 수 있었다.
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
        if(N > 2 && M > 6) {
            ans += (M - 3);     // 1 + 4 + (M - 1 - 7);
        } else {
            if(N > 2) { // M <= 6
                if(M >= 4) ans += 3;
                else if(M > 2) ans += 2;
                else if(M > 1) ans += 1;
            } else if(M > 6) {
                if(N == 2) ans += 3;
            } else {
                // N <= 2 & M <= 6
                if(N == 2) {
                    if(M >= 5) ans += 2;
                    else if(M > 2) ans += 1;
                }
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
