package Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
레벨 햄버거

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초	512 MB	2203	1030	827	50.955%
문제
상근날드에서 오랜만에 새로운 햄버거를 출시했다. 바로 레벨-L 버거이다. 레벨-L 버거는 다음과 같이 만든다.

레벨-0 버거는 패티만으로 이루어져 있다.
레벨-L 버거는 햄버거번, 레벨-(L-1) 버거, 패티, 레벨-(L-1)버거, 햄버거번으로 이루어져 있다. (L ≥ 1)
예를 들어, 레벨-1 버거는 'BPPPB', 레벨-2 버거는 'BBPPPBPBPPPBB'와 같이 생겼다. (B는 햄버거번, P는 패티)

상도가 상근날드에 방문해서 레벨-N 버거를 시켰다. 상도가 햄버거의 아래 X장을 먹었을 때, 먹은 패티는 몇 장일까? 한 장은 햄버거번 또는 패티 한 장이다.

입력
첫째 줄에 N과 X가 주어진다.

출력
첫째 줄에 상도가 먹은 패티의 수를 출력한다.

제한
1 ≤ N ≤ 50
1 ≤ X ≤ 레벨-N 버거에 있는 레이어의 수
예제 입력 1
2 7
예제 출력 1
4
예제 입력 2
1 1
예제 출력 2
0
예제 입력 3
50 4321098765432109
예제 출력 3
2160549382716056
출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: jh05013
알고리즘 분류
다이나믹 프로그래밍
분할 정복
재귀
 */
public class BOJ16974 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static long X, eat_patty;
    static String[] Level_N;
    static long[][] BP_count;
    static long[] P_count;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    /*
        1레벨 햄버거를 시작으로 N레벨 햄버거를 만드는 과정은 DP를 이용
        알고리즘
        1. i=2 부터 N까지 burger[i] = B + burger[i-1] + P + burger[i-1] + B라는 규칙을 이용
        2. BP_count에는 해당 버거의 5개의 레이어에 해당하게 구성
        3. P_count 해당 레벨의 햄버거는 모두 먹을 경우 먹은 패티의 총 개수를 저장
     */
    static void solve() {
        for(int i=2;i<=N;i++) {
            BP_count[i][0] = BP_count[i][4] = BP_count[i][2] = 1L;
            BP_count[i][1] = BP_count[i][3] = Arrays.stream(BP_count[i-1]).sum();

            P_count[i] = 2 * P_count[i-1] + 1;
        }

        check(N,X);

        System.out.println(eat_patty);
    }

    /*
        분할-정복개념을 이용한 먹을 수 있는 남은 횟수에서 패티 개수를 추출하는 함수

        알고리즘
        기저사례 : remain_x의 값이 0보다 작은 값일 경우 종료

        위 과정을 분할 정복이라고 생각한 이유가 N=2 X=7 예시의 과정을 보면
        2레벨 햄버거 : B BPPPB P BPPPB B
        과정 : 2레벨 햄버거와 남은 먹을 수 있는 횟수를 비교하여 햄버거 크키가 더 큰 경우, 앞 부분 B를 제거하고 remain_x-- 수행(앞 부분의 B를 제거하는 과정) ->
              n-1인 1레벨 햄버거 = BPPPB와 남은 횟수를 비교 -> 5 < 6 이므로 최대로 먹을 수 있는 햄버거임을 판단
                -> 2레벨 햄버거의 앞에 위치한 1레벨 햄버거를 먹고 남은 횟수 7 - 1 - 5 = 1 업데이트한 후 반복문을 종료한다.
                -> 이후, 남은 먹을 수 있는 횟수가 1보다 큰 경우, 다음으로 위치한 P를 먹고 eat_patty를 +1, remain_x - 1로 업데이트한다.
                -> 남은 먹을 수 있는 횟수가 0 또는 음수인 경우, check를 종료한다.
                -> check(1,1)를 전달하고 위 과정을 반복

        앞부분 부터 순차적으로 자신이 먹을 수 있는 횟수와 최대 크기의 햄버거를 판별하고 해당 햄버거를 먹고 남은 횟수를 구하고,
        처음 먹고난 후 패티가 존재하므로 먹을 수 있는 횟수가 존재하면 먹은 패티 수와 먹을 수 있는 횟수를 업데이트한다.
        동일한 과정을 남은 횟수가 없을 때까지 반복
     */
    static void check(int n, long remain_x) {
        for(n=n;n>=1;n--) {
            long n_burger = (BP_count[n][0] + BP_count[n][1] + BP_count[n][2] + BP_count[n][3] + BP_count[n][4]);
            if(n_burger <= remain_x) {
                remain_x -= n_burger;
                eat_patty += P_count[n];
                break;
            }

            remain_x--;
        }

        if(remain_x >= 1) {
            eat_patty++;
            remain_x -= 1;
        } else {
            return;
        }

        check(n, remain_x);
    }

    /*
        String에 저장할 수 있는 최대 길이 : 2,147,483,647
        따라서, String에 문자를 그대로 저장하는 방식은 x
     */
    static void expect_timeOver_or_memoryDump_solve() {
        long eat_patty = 0;

        for(int i=2;i<=N;i++) {
            Level_N[i] = "B" + Level_N[i-1] + "P" + Level_N[i-1] + "B";
        }

        for(int i=0;i<X;i++) {
            if(Level_N[N].charAt(i) == 'P') {
                eat_patty++;
            }
        }

        System.out.println(eat_patty);
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        X = Long.parseLong(input[1]);

        Level_N = new String[51];
        BP_count = new long[51][5];
        P_count = new long[51];

        eat_patty = 0;
        Level_N[1] = "BPPPB";

        BP_count[1][0] = BP_count[1][4] = 1L;    // B
        BP_count[1][1] = BP_count[1][2] = BP_count[1][3] = 1L; // P

        P_count[0] = 1;
        P_count[1] = 3;
    }
}
