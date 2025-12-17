package Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
배열 돌리기 1

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	11432	5505	3654	46.852%
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
1 ≤ R ≤ 1,000
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
/*
    외곽 테투리 갯수만큼 반복하는 것 까지는 생각하였고 처음 구상한 코드 방법은 외곽 테두리 마다의
    연산 횟수만큼 다음 수로의 변환을 할 생각이였지만, 내부에 다음 수로 변환하는 과정을 구현하는
    것을 아래 코드와 같이 하는 법을 몰랐다.

    처음 생각한 테두리의 연산 횟수만큼 연산방법은 반복문에서 배열의 인덱스를 특정짓기가 어려워서
    구현이 어려웠다
    ex)
        int r = N, c = M;
        rotateCount = Math.min(N,M) / 2;
        while(rotateCount-- > 0)
            for(int i=0;i<2*(r+c)-4;i++) <- 테두리에서 이뤄지는 연산의 횟수만큼 반복

            r -= 2; c -= 2;

    rotateCount = 0 ~ Math.min(N,M) / 2;
    그래서 테두리를 이루는 (0,0), (1,1), ... 시작지점과 (N-1-rotateCount, M-1-rotateCount) 끝지점
    에서의 각 상,하,좌,우 회전 연산을 반복문을 통해 수행한다

 */
public class BOJ16926 {
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

        for(int i=0;i<R;i++) {
            solve();
        }

        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void solve() {
        int[][] tmp = new int[301][301];
        int rotateCount = Math.min(N,M) / 2;

        //외곽 level의 개수 = 최소 변 길이 / 2
        for(int rot = 0;rot<rotateCount;rot++) {
            //x1,y1 <- 좌상단 좌표, x2,y2 = 우하단 좌표
            int x1 = rot, y1 = rot, x2 = N-1-rot, y2 = M-1-rot;
            //좌측 회전 ↓
            for(int i=x1+1;i<=x2;i++) tmp[i][y1] = arr[i-1][y1];
            //하단 회전 →
            for(int i=y1+1;i<=y2;i++) tmp[x2][i] = arr[x2][i-1];
            //우측 회전 ↑
            for(int i=x2-1;i>=x1;i--) tmp[i][y2] = arr[i+1][y2];
            //상단 회전 ←
            for(int i=y2-1;i>=y1;i--) tmp[x1][i] = arr[x1][i+1];
        }

        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                arr[i][j] = tmp[i][j];
            }
        }
    }
}
