package DP;/*
RGB거리

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (추가 시간 없음)	128 MB	84429	44225	33096	51.907%
문제
RGB거리에는 집이 N개 있다. 거리는 선분으로 나타낼 수 있고, 1번 집부터 N번 집이 순서대로 있다.

집은 빨강, 초록, 파랑 중 하나의 색으로 칠해야 한다. 각각의 집을 빨강, 초록, 파랑으로 칠하는 비용이 주어졌을 때, 아래 규칙을 만족하면서 모든 집을 칠하는 비용의 최솟값을 구해보자.

1번 집의 색은 2번 집의 색과 같지 않아야 한다.
N번 집의 색은 N-1번 집의 색과 같지 않아야 한다.
i(2 ≤ i ≤ N-1)번 집의 색은 i-1번, i+1번 집의 색과 같지 않아야 한다.
입력
첫째 줄에 집의 수 N(2 ≤ N ≤ 1,000)이 주어진다. 둘째 줄부터 N개의 줄에는 각 집을 빨강, 초록, 파랑으로 칠하는 비용이 1번 집부터 한 줄에 하나씩 주어진다. 집을 칠하는 비용은 1,000보다 작거나 같은 자연수이다.

출력
첫째 줄에 모든 집을 칠하는 비용의 최솟값을 출력한다.

예제 입력 1
3
26 40 83
49 60 57
13 89 99
예제 출력 1
96
예제 입력 2
3
1 100 100
100 1 100
100 100 1
예제 출력 2
3
예제 입력 3
3
1 100 100
100 100 100
1 100 100
예제 출력 3
102
예제 입력 4
6
30 19 5
64 77 64
15 19 97
4 71 57
90 86 84
93 32 91
예제 출력 4
208
예제 입력 5
8
71 39 44
32 83 55
51 37 63
89 29 100
83 58 11
65 13 15
47 25 29
60 66 19
예제 출력 5
253
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.io.IOException;

public class BOJ1149 {
    static int N;
    static int[][] mem;
    static int[][] colors;
    static int min = Integer.MAX_VALUE;
    static int min2 = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        mem = new int[N][3];
        colors = new int[N][3];

        for(int i=0;i<N;i++) {
            colors[i] = Arrays.asList(br.readLine().split(" "))
                    .stream()
                    .mapToInt(Integer::parseInt)
                    .toArray();
            Arrays.fill(mem[i],0);
        }
        mem[0][0] = colors[0][0];
        mem[0][1] = colors[0][1];
        mem[0][2] = colors[0][2];

        dp(N);
        System.out.println(min);

        min2 = Math.min(dp_topdown(N-1,0),Math.min(dp_topdown(N-1,1),dp_topdown(N-1,2)));
        System.out.println(min2);

    }

    static void dp(int n) {
        for(int i=1;i<n;i++) {
            mem[i][0] = Math.min(mem[i-1][1],mem[i-1][2]) + colors[i][0];
            mem[i][1] = Math.min(mem[i-1][0],mem[i-1][2]) + colors[i][1];
            mem[i][2] = Math.min(mem[i-1][0],mem[i-1][1]) + colors[i][2];

            min = Math.min(mem[i][0],Math.min(mem[i][1],mem[i][2]));
        }
    }

    static int dp_topdown(int n,int c) {
        if (n < 0) {
            return 0;
        }

        if(mem[n][c] != 0) return mem[n][c];

        if(c == 0) {
            mem[n][0] = Math.min(dp_topdown(n-1,1),dp_topdown(n-1,2)) + colors[n][0];
        } else if (c == 1) {
            mem[n][1] = Math.min(dp_topdown(n-1,0),dp_topdown(n-1,2)) + colors[n][1];
        } else {
            mem[n][2] = Math.min(dp_topdown(n-1,0),dp_topdown(n-1,1)) + colors[n][2];
        }

        return mem[n][c];
    }
}
