package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
Acka

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	1449	1075	808	75.233%
문제
BOJ 알고리즘 캠프에 강사로 참여하고 있는 dotorya, kesakiyo, hongjun7은 301호에서 도원결의를 맺고 프로젝트 아이돌 그룹 Acka을 결성했다.

Acka의 데뷔 앨범에는 총 S개의 곡이 수록될 예정이다. 각각의 곡은 세 사람중 적어도 한 명이 불러야 한다. 즉, 어떤 곡은 두 사람이 불러도 되고, 세 사람이 모두 함께 불러도 된다.

세 사람이 녹음해야 하는 곡의 수가 주어질 때, 앨범을 만들 수 있는 방법의 수를 구하는 프로그램을 작성하시오.

두 앨범 A와 B가 있을 때, 참여한 사람이 다른 곡이 존재한다면, 두 앨범은 다른 앨범이라고 한다.

입력
첫째 줄에 앨범에 포함된 곡의 개수 S와 dotorya, kesakiyo, hongjun7이 불러야 하는 곡의 수가 주어진다. (1 ≤ S ≤ 50, 1 ≤ dotorya, kesakiyo, hongjun7 ≤ S)

출력
첫째 줄에 앨범을 만들 수 있는 방법의 수를 1,000,000,007로 나눈 나머지를 출력한다.

예제 입력 1
3 1 1 1
예제 출력 1
6
예제 입력 2
3 3 1 1
예제 출력 2
9
예제 입력 3
50 10 10 10
예제 출력 3
0
예제 입력 4
18 12 8 9
예제 출력 4
81451692
예제 입력 5
50 25 25 25
예제 출력 5
198591037
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
다이나믹 프로그래밍
 */
/*
알고리즘 핵심
DP
1. dotorya, kesakiyo, hongjun7 세 명을 한 곡에 구성할 수 있는 경우를 나타내는 singers2 변수 singers2
남은 곡의 수, dotorya, kesakiyo, hongjun7 각 세명이 맡을 수 있는 남은 곡 수를 나타내는 4차원 배열의 DP를 사용한다.
(각 곡마다 어떠한 사람이 배치되었는지에 대한 순서는 중요하지 않기 때문이다 = 남은 곡 수와 배치 가능한 사람의 수만으로 경우의 수를 만들 수 있다)
2. singers2를 순차적으로 한 곡에 배치가능한 경우를 만들고, 다음 조건을 만족하는 경우 dfs_dp를 호출한다.
조건 1. dotorya, kesakiyo, hongjun7을 각각 곡에 배치했을 경우 최종적으로 남은 횟수가 0보다 작지 않아야 한다.
조건 2. 하나의 곡에 사람을 배치한 후, 남은 곡 수보다 각 사람의 배치할 수 있는 횟수가 크지 않아야 한다. = 하나의 곡에 최소 한명 이상은 배치해야 하기 때문이다.
조건 3. 하나의 곡에 사람을 배치한 후, 남은 곡 수보다 세 사람의 배치할 수 있는 횟수의 합이 커야한다. = 하나의 곡에 최소 한명씩 배치해도 배치할 수 없는 곡이 존재하기 때문이다.
3. dp[s][d][k][h]에 가능한 경우의 수를 모두 누적하여 더한 뒤 1,000,000,007을 나눈 나머지 값을 저장한다.
4. s = 0일 때, 기저사례로 d,k,h가 모두 0인 경우 1을 반환하고 그렇지 않은 경우 0을 반환한다.
 */
public class BOJ12996 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int S, dotorya, kesakiyo, hongjun7, ans;
    static int[] singers;
    static int[][] singers2;
    static int[][][][] dp;
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        //dfs(0, new int[S]);

        ans = dfs_dp(S,dotorya,kesakiyo,hongjun7);

        System.out.println(ans);
    }

    private static int dfs_dp(int s, int d, int k, int h) {
        if(s == 0) {
            if(d == 0 && k == 0 && h == 0) return 1;
            else return 0;
        }

        if(dp[s][d][k][h] != 0) return dp[s][d][k][h];

        int r = 0;

        for(int[] singer : singers2) {
            int nd = d - singer[0];
            int nk = k - singer[1];
            int nh = h - singer[2];

            if(nd < 0 || nk < 0 || nh < 0) continue;
            if(s - 1 < nd || s - 1 < nk || s - 1 < nh) continue;
            if(nd + nk + nh < s - 1) continue;

            int result = dfs_dp(s - 1, nd, nk, nh);

            if(result == 0) continue;

            r += result;
            r %= LIMIT;
        }

        dp[s][d][k][h] = r;

        return dp[s][d][k][h];
    }

    /*
        O(7^S)이므로 bruteforce로는 처리가 불가능하다.
     */
    private static void dfs(int song, int[] who_is_singing_this_song) {
        if(song == S) {
            if(dotorya == 0 && kesakiyo == 0 && hongjun7 == 0) {
                for(int w : who_is_singing_this_song) {
                    if(w == 0) return;
                }

                ans++;
            }
            return;
        }

        for(int singer : singers) {
            int d = singer / 100;
            int k = (singer % 100) / 10;
            int h = singer % 10;

            if(dotorya < d || kesakiyo < k || hongjun7 < h) continue;

            dotorya -= d;
            kesakiyo -= k;
            hongjun7 -= h;

            who_is_singing_this_song[song] = singer;

            dfs(song + 1, who_is_singing_this_song);

            who_is_singing_this_song[song] = 0;

            dotorya += d;
            kesakiyo += k;
            hongjun7 += h;
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        S = Integer.parseInt(input[0]);
        dotorya = Integer.parseInt(input[1]);
        kesakiyo = Integer.parseInt(input[2]);
        hongjun7 = Integer.parseInt(input[3]);

        ans = 0;

        singers = new int[] {111, 110, 101, 11, 100, 10, 1};
        // dotorya = 100, , kesakiyo = 10, hongjun7 = 1

        singers2 = new int[][] {{1,1,1}, {1,1,0}, {1,0,1}, {0,1,1}, {1,0,0}, {0,1,0}, {0,0,1}};

        dp = new int[S + 1][dotorya + 1][kesakiyo + 1][hongjun7 + 1];
    }
}
/*
3 1 1 1 = 6
A B C
A C B
B A C
B C A
C A B
C B A

3 3 1 1 = 9
A / AB / AC
AC / AB / A
AB / A / AC
AB / AC / A
A / AC / AB
AC / A / AB
ABC / A / A
A / ABC / A
A / A / ABC
*/