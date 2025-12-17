package DP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
/*
2×n 타일링

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	120178	45678	33637	35.825%
문제
2×n 크기의 직사각형을 1×2, 2×1 타일로 채우는 방법의 수를 구하는 프로그램을 작성하시오.

아래 그림은 2×5 크기의 직사각형을 채운 한 가지 방법의 예이다.



입력
첫째 줄에 n이 주어진다. (1 ≤ n ≤ 1,000)

출력
첫째 줄에 2×n 크기의 직사각형을 채우는 방법의 수를 10,007로 나눈 나머지를 출력한다.

예제 입력 1
2
예제 출력 1
2
예제 입력 2
9
예제 출력 2
55
 */
/*
    A1. dfs(완전탐색) - 시간초과
    A2. DP - top-down -> N=1, out : 1, N=2, out : 2, N=3, out = 3, N=4, out = 5, N=5, out = 8, ... 다음과 같은
    점화식을 알 수 있음. out[N] = out[N-1] + out[N-2]
    ㄴ 처음 시도로 "실패"의 결과가 나왔다, 이유: 결과 값으로 mem[n]의 값이 int의 범위 값을 넘어갈 수 있기 때문에
    (질문 검색을 통해 이유를 알게 되었다. + 결과 값을 나누는 과정이 추가될 때는 알고리즘 결과값이 크게 나와 오버플로우가 나올 수 있다는
    생각을 해야겠다.) - mem[n]을 정할 때마다, 10007로 나누는 과정을 추가 - 성공
    A3. DP - bottom-up -> 성공

    DP - topDown vs Bottomup의 메모리, 속도 차이가 크게 나지않는다.

 */
public class BOJ11726 {
    static int N;
    static int count = 0;
    //static int count2 = 0;
    //static boolean[] visited;
    //static boolean[] visited2;
    static int[] mem;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        mem = new int[1001];
        Arrays.fill(mem, 0);
        mem[1] = 1; mem[2] = 2; mem[3] = 3;

        //System.out.println(topdown(N));
        System.out.println(bottomup(N));

//        dfs
//        visited = new boolean[N * 2];
//        Arrays.fill(visited,false);
//        dfs
//        solve(0,0);
//        System.out.println(count % 10007);

//        visited2 = new boolean[N * 2];
//        Arrays.fill(visited2,false);
//        solve2(0);
//        System.out.println(count2 % 10007);
    }

    static int bottomup(int n) {
        for(int i=4;i<=n;i++) {
            mem[i] = mem[i-1] + mem[i-2];
            mem[i] %= 10007;
        }
        return mem[n];
    }

    static int topdown(int n) {
        if(n <= 3) {
            return n;
        }

        if(mem[n] == 0) {
            mem[n] = topdown(n-1) + topdown(n-2);
            mem[n] %= 10007;
        }

        return mem[n];
    }

    //dfs - 시간초과
    /*static void solve(int n,int s) {
        if(n == N) {
            *//*for(int i=0;i<2*N;i++) {
                if(!visited[i]) {
                    return;
                }
            }*//*
            count += 1;
            return;
        }

        for(int i=s;i<N*2;i++) {
            if(visited[i]) {
                continue;
            }

            visited[i] = true;

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
    }*/

    //시자 index를 지정해주지 않을 경우, 중복되는 경우의 수가 많아지기 때문에 idx를 지정해줘야함.
    /*static void solve2(int n) {
        if(n == N) {
            *//*for(int i=0;i<2*N;i++) {
                if(!visited2[i]) {
                    return;
                }
            }*//*
            count2 += 1;
            return;
        }

        for(int i=0;i<N*2;i++) {
            if(visited2[i]) {
                continue;
            }

            visited2[i] = true;

            if(((i+1) % N) != 0 && !visited2[i+1]) {
                visited2[i+1] = true;
                solve2(n + 1);
                visited2[i+1] = false;
            }

            if(i < N && !visited2[N + i] ) {
                visited2[N + i] = true;
                solve2(n + 1);
                visited2[N + i] = false;
            }

            visited2[i] = false;
        }
    }*/
}
