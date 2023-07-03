package BackJoon;/*
타일 채우기 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	40903	14589	11509	35.484%
문제
3×N 크기의 벽을 2×1, 1×2 크기의 타일로 채우는 경우의 수를 구해보자.

입력
첫째 줄에 N(1 ≤ N ≤ 30)이 주어진다.

출력
첫째 줄에 경우의 수를 출력한다.

예제 입력 1
2
예제 출력 1
3
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class BOJ2133 {
    static int N;
    static int[][] T;
    static int[] mem;

    static boolean[][] visited;
    static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        T = new int[3][N];
        visited = new boolean[3][N];
        mem = new int[30+1];


        Arrays.fill(mem,0);

        mem[0] = 1;
        mem[2] = 3;

        for(int i=0;i<3;i++) {
            Arrays.fill(T[i],0);
            Arrays.fill(visited[i],false);
        }

//        dfs(0,0);
//        System.out.println(count);
        System.out.println(dp(N));
    }

    /*
        dp[2] = 3
        ==   ㅣㅣ   ㅡ
        ㅡ    ㅡ   ㅣㅣ
        dp[4] = dp[2] * dp[2] + 4일때 가능한 경우의 수(2)
        ㄴ ㅡ ㅡ   ㅣ==ㅣ
          ㅣ==ㅣ   ㅡ ㅡ
        dp[4] = dp[2] * 3 + 2 = 11
        dp[6] = dp[4] * dp[2] + dp[2] * x
        x에서 dp[4]를 하게되면, 앞의 dp[4] * dp[2]의 경우의 수에서 중복되는 경우가 존재한다
        이때, 중복되는 경우의 수를 제외하는 규칙을 구하기 어려웠다
        그래서, x는 n=4로 만들 수 있고, 겹치지않는 위의 2가지 방법만 적용한다
        dp[6] = dp[4] * dp[2] + dp[2] * 2 + d[0] * 2 = 41
        ㄴ ㅡ ㅡ ㅡ     ㅣ = = ㅣ
          ㅣ = = ㅣ     ㅡ ㅡ ㅡ
        다음과 같이 2가지 방법이 추가되는 것을 통해 2씩 추가되는 것을 유추할 수 있다
        dp[n] = dp[n-2] * dp[2] + dp[n-4] * 2 + ... + dp[0] * 2
        이라는 점화식을 도출할 수 있다

        이전에, dfs를 통해 값을 만들고, 점화식을 예측하는 과정에서
        dp[n] = dp[n-2] * dp[2] + (dp[n-2] - dp[n-4]) (n>=4) 라는 식을 생각해봤다.
        이때, n을 적용했을 때 답은 맞게 나오지만, dp[n-2] - dp[n-4]을 어떻게 검증해야하는지 모르겠다


     */
    static int dp(int n) {
        for(int i=4;i<=n;i+=2) {
            mem[i] = mem[i-2] * mem[2];
            int remainder = 0;
            for(int j=i;j-4>=0;j-=2) {
                remainder += mem[j-4];
            }
            mem[i] += (remainder * 2);
        }
        return mem[n];
    }
    //0 : 1x2, 1 : 2x1
    static void dfs(int n, int s) {
        //N위치까지 진행됬을 때, 비어있는 블록이 있다면, return, 다 채워졌다면 count++
        if(n == (3*N)/2) {
            for(int i=0;i<3;i++) {
                for(int j=0;j<N;j++) {
                    if(!visited[i][j]) return;
                }
            }
            count++;
            return;
        }

        //for(int i=r;i<3;i++) {
            for(int i=s;i<3*N;i++) {
                if(visited[i/N][i%N]) continue;

                // | 2x1 블록 가능 여부 확인
                if((i / N) + 1 < 3 && (!visited[i/N][i%N] && !visited[(i/N)+1][i%N])) {
                    visited[i/N][i%N] = visited[(i/N)+1][i%N] = true;
                    dfs(n+1,i);
                    visited[i/N][i%N] = visited[i/N+1][i%N] = false;
                }

                // -- 1x2 블록 가능 여부확인
                if((i+1) % N != 0 && (!visited[i/N][i%N] && !visited[i/N][(i+1)%N])) {
                    visited[i/N][i%N] = visited[i/N][(i+1)%N] = true;
                    dfs(n+1,i);
                    visited[i/N][i%N] = visited[i/N][(i+1)%N] = false;
                }
            }
        //}

    }
}


