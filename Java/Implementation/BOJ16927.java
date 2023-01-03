/*
배열 돌리기 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	4407	1448	1155	34.852%
문제
크기가 N×M인 배열이 있을 때, 배열을 돌려보려고 한다. 배열은 다음과 같이 반시계 방향으로 돌려야 한다.

A[1][1] ← A[1][2] ← A[1][3] ← A[1][4] ← A[1][5]
   ↓                                       ↑
A[2][1]   A[2][2] ← A[2][3] ← A[2][4]   A[2][5]
   ↓         ↓                   ↑         ↑
A[3][1]   A[3][2] → A[3][3] → A[3][4]   A[3][5]
   ↓                                       ↑
A[4][1] → A[4][2] → A[4][3] → A[4][4] → A[4][5]
예를 들어, 아래와 같은 배열을 2번 회전시키면 다음과 같이 변하게 된다.

1 2 3 4       2 3 4 8       3 4 8 6
5 6 7 8       1 7 7 6       2 7 8 2
9 8 7 6   →   5 6 8 2   →   1 7 6 3
5 4 3 2       9 5 4 3       5 9 5 4
 <시작>         <회전1>        <회전2>
배열과 정수 R이 주어졌을 때, 배열을 R번 회전시킨 결과를 구해보자.

입력
첫째 줄에 배열의 크기 N, M과 수행해야 하는 회전의 수 R이 주어진다.

둘째 줄부터 N개의 줄에 배열 A의 원소 Aij가 주어진다.

출력
입력으로 주어진 배열을 R번 회전시킨 결과를 출력한다.

제한
2 ≤ N, M ≤ 300
1 ≤ R ≤ 109
min(N, M) mod 2 = 0
1 ≤ Aij ≤ 108
예제 입력 1
4 4 2
1 2 3 4
5 6 7 8
9 10 11 12
13 14 15 16
예제 출력 1
3 4 8 12
2 11 10 16
1 7 6 15
5 9 13 14
예제 입력 2
5 4 7
1 2 3 4
7 8 9 10
13 14 15 16
19 20 21 22
25 26 27 28
예제 출력 2
28 27 26 25
22 9 15 19
16 8 21 13
10 14 20 7
4 3 2 1
예제 입력 3
2 2 3
1 1
1 1
예제 출력 3
1 1
1 1
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ16927 {
    static int N,M,R;
    static int[][] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        arr = new int[N][M];

        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        solve();

        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    //시간초과가 발생하여 다른 알고리즘이 필요
    //N x M배열의 회전이 일정 횟수 회전과 동일하다는 점을 이용하여 회전 횟수를 제한하여 시간초과를 해결할 수 있을것을 보인다
    //외곽 직사각형부터 안쪽으로의 직사각형들의 반복 주기가 다르기 때문에 반복문 추가
    //외곽 별 직사각형의 반복 회전 주기 : 2*(N+M)-4-8*rot
    //따라서, 직사각형의 반복 주기는 R % 2*(N+M)-4-8*rot 횟수만큼만 회전하면 되므로, 반복문 횟수 조정
    static void solve() {
        //int[][] tmp = new int[301][301];
        int rotateCount = Math.min(N,M) / 2;

        for(int rot=0;rot<rotateCount;rot++) {
            int recursive_rotate = R % (2*(N+M)-4-8*rot);
            for(int r = 0;r<recursive_rotate;r++) {
/*                int x1 = rot, y1 = rot, x2 = N - 1 - rot, y2 = M - 1 - rot;
                //←
                for(int i=y2-1;i>=y1;i--) tmp[x1][i] = arr[x1][i+1];
                //↓
                for(int i=x2;i>x1;i--) tmp[i][y1] = arr[i-1][y1];
                //→
                for(int i=y2;i>y1;i--) tmp[x2][i] = arr[x2][i-1];
                //↑
                for(int i=x2-1;i>=x1;i--) tmp[i][y2] = arr[i+1][y2];*/
                int x1 = rot, y1 = rot, x2 = N - 1 - rot, y2 = M - 1 - rot;
                int tmp = arr[x1][y1];
                //←
                for(int i=y1;i<y2;i++) arr[x1][i] = arr[x1][i+1];

                //↑
                for(int i=x1;i<x2;i++) arr[i][y2] = arr[i+1][y2];

                //→
                for(int i=y2;i>y1;i--) arr[x2][i] = arr[x2][i-1];

                //↓
                for(int i=x2;i>x1+1;i--) arr[i][y1] = arr[i-1][y1];

                arr[x1+1][y1] = tmp;
            }
        }

       /* for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                arr[i][j] = tmp[i][j];
            }
        }*/
    }
}