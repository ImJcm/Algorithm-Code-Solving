/*
정수 삼각형 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	68428	38749	29141	58.880%
문제
        7
      3   8
    8   1   0
  2   7   4   4
4   5   2   6   5
위 그림은 크기가 5인 정수 삼각형의 한 모습이다.

맨 위층 7부터 시작해서 아래에 있는 수 중 하나를 선택하여 아래층으로 내려올 때, 이제까지 선택된 수의 합이 최대가 되는 경로를 구하는 프로그램을 작성하라. 아래층에 있는 수는 현재 층에서 선택된 수의 대각선 왼쪽 또는 대각선 오른쪽에 있는 것 중에서만 선택할 수 있다.

삼각형의 크기는 1 이상 500 이하이다. 삼각형을 이루고 있는 각 수는 모두 정수이며, 범위는 0 이상 9999 이하이다.

입력
첫째 줄에 삼각형의 크기 n(1 ≤ n ≤ 500)이 주어지고, 둘째 줄부터 n+1번째 줄까지 정수 삼각형이 주어진다.

출력
첫째 줄에 합이 최대가 되는 경로에 있는 수의 합을 출력한다.

예제 입력 1
5
7
3 8
8 1 0
2 7 4 4
4 5 2 6 5
예제 출력 1
30
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class BOJ1932 {
    static int N;
    static int[][] arr;
    static int[][] mem;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        arr = new int[N][N];
        mem = new int[N][N];

//        arr = new int[501][501];
//        mem = new int[501][501];

        for(int i=1;i<=N;i++) {
            String[] tmp = br.readLine().split(" ");
            for(int j=0;j<tmp.length;j++) {
                //arr[(i*(i-1))/2 + j] = Integer.parseInt(tmp[j]);
                arr[i-1][j] = Integer.parseInt(tmp[j]);
            }
            Arrays.fill(mem[i-1],0);
        }

        mem[0][0] = arr[0][0];

        dp_bt();

//        int m = 0;
//        for(int i=0;i<N;i++) {
//            m = Math.max(dp_td(N-1,i),m);
//        }
//        System.out.println(m);
    }

    //dp-tomdown
    static int dp_td(int n, int pidx) {
        if(n == 0) {
            return mem[0][0];
        }

        if(mem[n][pidx] != 0) return mem[n][pidx];
        if(pidx==0) mem[n][pidx] = dp_td(n-1,pidx) + arr[n][pidx];
        else if(pidx==(n)) mem[n][pidx] = dp_td(n-1,pidx-1) + arr[n][pidx];
        else mem[n][pidx] = Math.max(dp_td(n-1,pidx), dp_td(n-1,pidx-1)) + arr[n][pidx];

//        for(int i=0;i<n;i++) {
//            if(i==0) {
//                mem[n][i] = dp_td(n-1,i) + arr[n][i];
//            } else if(i==(n-1)) {
//                mem[n][i] = dp_td(n-1,i-1) + arr[n][i];
//            } else {
//                mem[n][i] = Math.max(dp_td(n-1,i), dp_td(n-1,i-1)) + arr[n][i];
//            }
//        }

        return mem[n][pidx];
    }

    //dp-bottomup
    static void dp_bt() {
        for(int i=1;i<N;i++) {
            for(int j=0;j<=i;j++) {
                if(j == 0) {
                    mem[i][j] = mem[i-1][j] + arr[i][j];
                } else if(j==i) {
                    mem[i][j] = mem[i-1][j-1] + arr[i][j];
                } else {
                    mem[i][j] = Math.max(mem[i-1][j-1],mem[i-1][j]) + arr[i][j];
                }
            }
        }
        for(int i=0;i<N;i++) {
            max = Math.max(max,mem[N-1][i]);
        }
        System.out.println(max);
    }
}
