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
/*
알고리즘 핵심
그리디 알고리즘
1. 입력으로 주어지는 배열을 저장한 후, 바트마스킹을 통해 row를 flip한 상태를 N^2개의 개수만큼 상태를 만들어야 한다.
2. 이때, 이전에 만든 row flip배열에서 다음으로 만들어야 할 배열을 만들기 위해 연산을 최소화할 수 있는 XOR 연산으로 필요한 row만 flip한다.
3. 만들어진 배열에서 col마다 T의 개수가 N / 2보다 큰 경우만 flip하게 되면 T의 최소 개수를 만족하는 greedy한 조건을 만족하므로 T의 개수를 측정한다.
4. 각 배열에서 측정한 T의 개수중 최소값을 저장한다.

초기 접근으로 greedy 조건을 만족하기 위해 row에서 T의 개수가 많은 순으로 뒤집고, 다음으로 col또한 같은 동작을 수행하여, 이 과정을 row <-> col또한 반대로 적용하여
수행하여 최소 T의 개수를 만족할 수 있다고 생각했지만 틀린 접근이였다.

두번째 접근으로 간단하게 bruteforce로 dfs를 적용하여 모든 경우의 수를 검사하여 최소 T의 갯수를 찾으려 했지만 중복되는 경우의 수와 많은 경우의 수로 실패하였다.

세번째 접근으로 질문 게시판을 참고하여 한 행 또는 열을 먼저 flip한 모든 경우의 수를 만들고 greedy하게 남은 행 또는 열을 뒤집는 형태가 되어야한다는 것을 알게되었고,
이를 적용하여 비트마스킹을 적용하여 row를 뒤집는 모든 경우의 수에서 col을 greedy하게 뒤집어서 T의 최소 갯수를 찾으려 했지만 메모리 초과가 발생하였다.

네번째 접근으로 세번째 접근에서 메모리 접근의 원인으로 불필요한 배열의 접근과 변형이 원인이 될 수 있다고 생각하여
해당 col을 flip하여 배열을 변형하는 과정의 불필요함과 2^20에 해당하는 모든 경우에서 coins 배열을 접근하고 초기화하는 과정을 제거하였다.

위의 불필요한 과정을 없애고 flip을 하였다고 가정하였을 때 T의 개수만을 측정하였고, XOR 연산을 이용하여 이전 row flip한 배열에서 다음 row flip할 row를 찾는 과정으로 최적화하였다.
 */
public class BOJ1285 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static char[][] origin_coins,coins;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    /*
        성공 코드 : 0 ~ N^2 - 1까지의 수를 비트마스킹하여 row의 동전을 뒤집은 경우의 수를 만들고, col마다 greedy하게 뒤집은 경우에서
        최소의 T를 가지는 경우를 고려하여 최소 형태를 구한다.

        기존에는 row를 뒤집은 coins[][] 배열을 col에도 동전 뒤집기를 적용하였지만 뒤집는 과정이 필요가 없었다.
        그 이유는 뒤집지 않아도 해당 배열에서 col마다 T의 개수가 N / 2보다 큰 경우 뒤집으면 최소 T의 개수를 만족하기 때문에
        이러한 이유로 greedy한 형태를 갖을 수 있고, 배열을 변형하지 않으면 다음 row를 변경할 때 추가적인 coins 배열을 복사하여 생성하지 않아도된다.
        위의 이유는 비트마스킹 0(0000)에 해당하는 배열에서 비트마스킹 1(0001)로의 변경은 0과 1을 XOR 연산하여 서로 다른 부분만 row flip을 적용하면 된다.
        위 과정으로 초기 상태(origin_coins)에서 연산에 사용되는 배열(coins)에 초기화 작업이 필요가 없어져서 메모리초과와 같은 영향을 줄일 수 있다.
     */
    private static void solve() {
        int now_n = 0;
        copy_coins(origin_coins);

        for(int i = 0; i < Math.pow(2,N); i++) {
            now_n ^= i;
            bitmask_row_flip(now_n);
            greedy_flip_check3();
            now_n = i;
        }

        System.out.println(ans);
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

    private static void greedy_flip_check3() {
        int cnt = 0;
        for(int i = 0; i < N; i++) {
            int cnt_T = can_flip_cur_line2(i,false);
            if(cnt_T > (N / 2)) {
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
