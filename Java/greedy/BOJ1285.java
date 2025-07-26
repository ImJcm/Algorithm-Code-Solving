package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
동전 뒤집기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
6 초	128 MB	8928	3800	2435	43.677%
문제
N2개의 동전이 N행 N열을 이루어 탁자 위에 놓여 있다. 그 중 일부는 앞면(H)이 위를 향하도록 놓여 있고, 나머지는 뒷면(T)이 위를 향하도록 놓여 있다. <그림 1>은 N이 3일 때의 예이다.



<그림 1>

이들 N2개의 동전에 대하여 임의의 한 행 또는 한 열에 놓인 N개의 동전을 모두 뒤집는 작업을 수행할 수 있다. 예를 들어 <그림 1>의 상태에서 첫 번째 열에 놓인 동전을 모두 뒤집으면 <그림 2>와 같이 되고, <그림 2>의 상태에서 첫 번째 행에 놓인 동전을 모두 뒤집으면 <그림 3>과 같이 된다.


<그림 2>	<그림 3>
<그림 3>의 상태에서 뒷면이 위를 향하여 놓인 동전의 개수는 두 개이다. <그림 1>의 상태에서 이와 같이 한 행 또는 한 열에 놓인 N개의 동전을 모두 뒤집는 작업을 계속 수행할 때 뒷면이 위를 향하도록 놓인 동전의 개수를 2개보다 작게 만들 수는 없다.

N2개의 동전들의 초기 상태가 주어질 때, 한 행 또는 한 열에 놓인 N개의 동전을 모두 뒤집는 작업들을 수행하여 뒷면이 위를 향하는 동전 개수를 최소로 하려 한다. 이때의 최소 개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 20이하의 자연수 N이 주어진다. 둘째 줄부터 N줄에 걸쳐 N개씩 동전들의 초기 상태가 주어진다. 각 줄에는 한 행에 놓인 N개의 동전의 상태가 왼쪽부터 차례대로 주어지는데, 앞면이 위를 향하도록 놓인 경우 H, 뒷면이 위를 향하도록 놓인 경우 T로 표시되며 이들 사이에 공백은 없다.

출력
첫째 줄에 한 행 또는 한 열에 놓인 N개의 동전을 모두 뒤집는 작업들을 수행하여 뒷면이 위를 향하여 놓일 수 있는 동전의 최소 개수를 출력한다.

예제 입력 1
3
HHT
THH
THT
예제 출력 1
2
알고리즘 분류
그리디 알고리즘
브루트포스 알고리즘
비트마스킹
 */
public class BOJ1285 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static char[][] origin_coins,coins;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {

    }

    /*
        실패 코드 : 메모리 초과, row행을 뒤집은 경우의 수를 만드는 과정에서 2^20이라는 경우의 수가 만들어지기 때문에 발생하는 것
        이라고 생각한다.
     */
    private static void wrong_solve4() {
        for(int i = 0; i < Math.pow(2,N); i++) {
            copy_coins(origin_coins);
            bitmask_row_flip(i);
            greedy_flip_check2();
        }

        System.out.println(ans);
    }

    /*
        실패 코드 : 메모리 초과, 동전 뒤집기 이후, T의 갯수를 검사하는 과정에서 N^2
     */
    private static void wrong_solve3() {
        for(int i = 0; i < Math.pow(2,N); i++) {
            copy_coins(origin_coins);
            bitmask_row_flip(i);
            greedy_flip_check();
            ans = Math.min(ans,check_coins());
        }

        System.out.println(ans);
    }

    private static void greedy_flip_check2() {
        int cnt = 0;
        for(int i = 0; i < N; i++) {
            int cnt_T = can_flip_cur_line2(i,false);
            if(cnt_T > (N / 2)) {
                flip(i,false);
                cnt += (N - cnt_T);
            } else {
                cnt += cnt_T;
            }
        }
        ans = Math.min(ans, cnt);
    }

    private static void greedy_flip_check() {
        for(int i = 0; i < N; i++) {
            if(can_flip_cur_line(i,false)) {
                flip(i,false);
            }
        }
    }

    private static void bitmask_row_flip(int i) {
        int row = 0;
        while(i > 0) {
            if(i % 2 == 1) flip(row, true);
            i /= 2;
            row++;
        }
    }

    /*
        실패 코드 : dfs를 통해 모든 경우의 수를 체크하고 T의 최소 개수를 만들려고 하는 로직
        중복되는 경우와 많은 경우의 수로 시간초과 발생 예상
     */
    private static void wrong_solve2() {
        copy_coins(origin_coins);

        dfs(0);

        System.out.println(ans);
    }

    private static void dfs(int n) {
        if(n >= N * N) {
            ans = Math.min(ans,check_coins());
            return;
        }

        //dfs(n + 1);

        flip(n / N, true);
        dfs(n + 1);

        flip(n % N, false);
        dfs(n + 1);
    }

    /*
        틀린 코드 : 행 -> 열 또는 열 -> 행 순으로 T의 개수가 많은 라인을 뒤집어서 최종적인 T의 개수를 검사하는 로직
        최소 갯수의 T를 만족하지 못했다.
     */
    private static void wrong_solve() {
        copy_coins(origin_coins);
        flip_coin(true);
        flip_coin(false);
        ans = Math.min(ans,check_coins());

        copy_coins(origin_coins);
        flip_coin(false);
        flip_coin(true);
        ans = Math.min(ans,check_coins());

        System.out.println(ans);
    }

    private static int check_coins() {
        int cnt = 0;

        for(int r = 0; r < N; r++) {
            for(int c = 0; c < N; c++) {
                if(coins[r][c] == 'T') cnt++;
            }
        }
        return cnt;
    }

    private static void flip_coin(boolean row_or_col) {
        for(int l = 0; l < N; l++) {
            if(can_flip_cur_line(l,row_or_col)) {
                flip(l,row_or_col);
            }
        }
    }

    private static void flip(int l, boolean row_or_col) {
        if(row_or_col) {
            for(int i = 0; i < N; i++) {
                if(coins[l][i] == 'T') {
                    coins[l][i] = 'H';
                } else {
                    coins[l][i] = 'T';
                }
            }
        } else {
            for(int i = 0; i < N; i++) {
                if(coins[i][l] == 'T') {
                    coins[i][l] = 'H';
                } else {
                    coins[i][l] = 'T';
                }
            }
        }
    }

    private static boolean can_flip_cur_line(int l,boolean row_or_col) {
        int cnt = 0;

        if(row_or_col) {
            for(int i = 0; i < N; i++) {
                if(coins[l][i] == 'T') {
                    cnt++;
                }
            }
        } else {
            for(int i = 0; i < N; i++) {
                if(coins[i][l] == 'T') {
                    cnt++;
                }
            }
        }

        if(cnt > N / 2) return true;
        else return false;
    }

    private static int can_flip_cur_line2(int l,boolean row_or_col) {
        int cnt = 0;

        if(row_or_col) {
            for(int i = 0; i < N; i++) {
                if(coins[l][i] == 'T') {
                    cnt++;
                }
            }
        } else {
            for(int i = 0; i < N; i++) {
                if(coins[i][l] == 'T') {
                    cnt++;
                }
            }
        }

        return cnt;
    }

    private static void copy_coins(char[][] o) {
        for(int r = 0; r < N; r++) {
            coins[r] = Arrays.copyOf(o[r],N);
        }
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        coins = new char[N][N];
        origin_coins = new char[N][N];

        for(int i = 0; i < N; i++) {
            origin_coins[i] = br.readLine().toCharArray();
        }

        ans = Integer.MAX_VALUE;
    }
}
