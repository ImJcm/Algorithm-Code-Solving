package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
Z

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (추가 시간 없음)	512 MB	101928	44180	33077	43.229%
문제
한수는 크기가 2N × 2N인 2차원 배열을 Z모양으로 탐색하려고 한다. 예를 들어, 2×2배열을 왼쪽 위칸, 오른쪽 위칸, 왼쪽 아래칸, 오른쪽 아래칸 순서대로 방문하면 Z모양이다.



N > 1인 경우, 배열을 크기가 2N-1 × 2N-1로 4등분 한 후에 재귀적으로 순서대로 방문한다.

다음 예는 22 × 22 크기의 배열을 방문한 순서이다.



N이 주어졌을 때, r행 c열을 몇 번째로 방문하는지 출력하는 프로그램을 작성하시오.

다음은 N=3일 때의 예이다.



입력
첫째 줄에 정수 N, r, c가 주어진다.

출력
r행 c열을 몇 번째로 방문했는지 출력한다.

제한
1 ≤ N ≤ 15
0 ≤ r, c < 2N
예제 입력 1
2 3 1
예제 출력 1
11
예제 입력 2
3 7 7
예제 출력 2
63
예제 입력 3
1 0 0
예제 출력 3
0
예제 입력 4
4 7 7
예제 출력 4
63
예제 입력 5
10 511 511
예제 출력 5
262143
예제 입력 6
10 512 512
예제 출력 6
786432
출처
문제를 번역한 사람: baekjoon
잘못된 조건을 찾은 사람: hmw9309
알고리즘 분류
분할 정복
재귀
 */
/*
알고리즘 핵심
분할정복 + 재귀
1. n 차수와 2^n x 2^n 사각형의 끝 지점 (r,c)를 분할 정복의 매개변수로 사용하여 사각형을 나눈다.
2. (n,r,c)에서 n이 1보다 큰 경우, 2^(n-1) x 2^(n-1) 크기의 사각형으로 4등분한다.
이때, 각 사각형의 (r,c)는 끝 지점을 나타내야 하므로, (n,r,c) -> (n-1,r-w,c-w),(n-1,r-w,c),(n-1,r,c-w),(n-1,r,c) (w = 2^(n-1))
3. n이 클수록 많은 수의 로직이 수행되므로, 가지치기를 설정하여 불필요한 수행을 제거한다.
(가지치기 :
    1. (r,c)가 (R,C)를 만족하는 경우, boolean branch = true 설정하여 이후 재귀 호출을 제거한다.
    2. (R,C)가 (r,c)보다 큰 경우, 해당 사각형은 수행하지 않고 해당 사각형에 포함된 1x1 크기의 칸의 개수를 누적한다.)
4. (r,c)가 (R,C)에 도달하는 경우 ans에 저장하고 branch = true 설정하고, 불필요한 재귀 호출을 제거한다.
 */
public class BOJ1074 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,R,C,ans,cnt,size;
    static boolean branch;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        divide_arr(N,size,size);

        System.out.println(ans);
    }

    // n,r,c를 매개변수를 이용하여 2^(n-1) x 2^(n-1) 사각형의 범위를 특정할 수 있고, R,C의 존재 여부를 가지치기하면 될 것같다.
    private static void divide_arr(int n, int r, int c) {
        if(branch) return;
        if(r < R || c < C) {
            cnt += (int) Math.pow(4,n);
            return;
        }
        if(n == 0) {
            if(r - 1 == R && c - 1 == C) {
                ans = cnt;
                branch = true;
            }
            cnt++;
            return;
        }

        int w = (int) Math.pow(2,n - 1);

        divide_arr(n - 1, r - w, c - w);
        divide_arr(n - 1, r - w, c);
        divide_arr(n - 1, r, c - w);
        divide_arr(n - 1, r, c);
    }

    /*
        틀린 코드 : 시간초과 발생
        가지치기를 설정하여도 시간초과가 발생하였다.
     */
    private static void timeOut_divide_arr(int n, int r, int c) {
        if(branch) return;
        if(n == 0) {
            if(r - 1 == R && c - 1 == C) {
                ans = cnt;
                branch = true;
            }
            cnt++;
            return;
        }

        int w = (int) Math.pow(2,n - 1);

        divide_arr(n - 1, r - w, c - w);
        divide_arr(n - 1, r - w, c);
        divide_arr(n - 1, r, c - w);
        divide_arr(n - 1, r, c);
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        R = Integer.parseInt(input[1]);
        C = Integer.parseInt(input[2]);

        size = (int) Math.pow(2,N);

        branch = false;

        ans = 0;
        cnt = 0;
    }
}
