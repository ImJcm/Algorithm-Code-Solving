package DP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
/*
2×n 타일링 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	52138	31227	24917	59.325%
문제
2×n 직사각형을 1×2, 2×1과 2×2 타일로 채우는 방법의 수를 구하는 프로그램을 작성하시오.

아래 그림은 2×17 직사각형을 채운 한가지 예이다.



입력
첫째 줄에 n이 주어진다. (1 ≤ n ≤ 1,000)

출력
첫째 줄에 2×n 크기의 직사각형을 채우는 방법의 수를 10,007로 나눈 나머지를 출력한다.

예제 입력 1
2
예제 출력 1
3
예제 입력 2
8
예제 출력 2
171
예제 입력 3
12
예제 출력 3
2731
 */
/*
    A1. dfs - 시간초과를 예상
    A2. 규칙성이 존재할 것으로 생각하여, i=1~5까지 수작업으로 결과값을 도출하여, 규칙성 파악
    ㄴ i=1) 1, i=2) 3, i=3) 5, i=4) 11, i=5) 21, ...
        mem[n] = mem[n-1] + mem[n-2] * 2

 */
public class BOJ11727 {
    static int N;
    static int count = 0;
    static boolean[] visited;
    static int[] mem;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        mem = new int[1001];
        Arrays.fill(mem, 0);
        mem[1] = 1; mem[2] = 3; mem[3] = 5;

//        dfs
//        visited = new boolean[N * 2];
//        Arrays.fill(visited,false);
//        solve(0,0);
//        System.out.println(count);

        //DP
        System.out.println(dp(N));

    }

    //dp - bottomup
    static int dp(int n) {
        for(int i=4;i<=n;i++) {
            mem[i] = mem[i-1] + mem[i-2] * 2;
            mem[i] %= 10007;
        }

        return mem[n];
    }

    //dp - topdown
    static int dp_topdown(int n) {
        if(n <= 3) {
            return (n * 2) - 1;
        }

        if(mem[n] == 0) {
            mem[n] = dp_topdown(n-1) + dp_topdown(n-2) * 2;
            mem[n] %= 10007;
        }

        return mem[n];
    }

    //dfs
    static void solve(int n,int s) {
        if(n == N) {
            for(int i=0;i<2*N;i++) {
                if(!visited[i]) {
                    return;
                }
            }
            count += 1;
            count %= 10007;
            return;
        }

        for(int i=s;i<N*2;i++) {
            if(visited[i]) {
                continue;
            }

            visited[i] = true;

            if(((i+1) % N) != 0 && i < N && i + 1 < N && (!visited[i+1]) && !visited[N + i] && !visited[N + i + 1]) {
                visited[i+1] = true;
                visited[N + i] = true;
                visited[N + i + 1] = true;
                solve(n+2,i);
                visited[i+1] = false;
                visited[N + i] = false;
                visited[N + i + 1] = false;
            }

            if(((i+1) % N) != 0 && !visited[i+1]) {
                visited[i+1] = true;
                solve(n + 1,i);
                visited[i+1] = false;
            }

            if(i < N && !visited[N + i] ) {
                visited[N + i] = true;
                solve(n + 1,i);
                visited[N + i] = false;
            }



            visited[i] = false;
        }
    }
}

