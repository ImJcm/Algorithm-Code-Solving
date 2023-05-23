package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
배열 복원하기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	3126	1451	1155	44.924%
문제
크기가 H × W인 배열 A와 두 정수 X와 Y가 있을 때, 크기가 (H + X) × (W + Y)인 배열 B는 배열 A와 배열 A를 아래로 X칸, 오른쪽으로 Y칸 이동시킨 배열을 겹쳐 만들 수 있다. 수가 겹쳐지면 수가 합쳐진다.

즉, 배열 B의 (i, j)에 들어있는 값은 아래 3개 중 하나이다.

(i, j)가 두 배열 모두에 포함되지 않으면, Bi,j = 0이다.
(i, j)가 두 배열 모두에 포함되면, Bi,j = Ai,j + Ai-X,j-Y이다.
(i, j)가 두 배열 중 하나에 포함되면, Bi,j = Ai,j 또는 Ai-X,j-Y이다.
배열 B와 정수 X, Y가 주어졌을 때, 배열 A를 구해보자.

입력
첫째 줄에 네 정수 H, W, X, Y가 주어진다. 둘째 줄부터 H + X개의 줄에 배열 B의 원소가 주어진다.

항상 배열 A가 존재하는 경우만 입력으로 주어진다.

출력
총 H개의 줄에 배열 A의 원소를 출력한다.

제한
2 ≤ H, W ≤ 300
1 ≤ X < H
1 ≤ Y < W
0 ≤ Bi,j ≤ 1,000
예제 입력 1
2 4 1 1
1 2 3 4 0
5 7 9 11 4
0 5 6 7 8
예제 출력 1
1 2 3 4
5 6 7 8
예제 입력 2
3 3 2 1
1 2 3 0
4 5 6 0
7 9 11 3
0 4 5 6
0 7 8 9
예제 출력 2
1 2 3
4 5 6
7 8 9
 */
/*
단순히 B배열에서 X,Y값 이전의 index 값을 A에 저장하고, 나머지 값은 X,Y를 시작으로 B배열에서 추출한 배열(AtoB)과 A배열에서 X,Y만큼 뺀 좌표의 값
을 뺀 값을 X,Y를 시작으로 값을 저장하면 원본 A 배열을 만들 수 있다.
 */
public class BOJ16967 {
    static int[][] A;
    static int[][] AtoB;
    static int[][] B;
    static int H,W,X,Y;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        Y = Integer.parseInt(st.nextToken());

        A = new int[H][W];
        AtoB = new int[H][W];
        B = new int[H+X][W+Y];

        for(int i=0;i<H+X;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<W+Y;j++) {
                B[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=X;i<H+X;i++) {
            for(int j=Y;j<W+Y;j++) {
                AtoB[i-X][j-Y] = B[i][j];
            }
        }

        for(int i=0;i<H;i++) {
            for(int j=0;j<W;j++) {
                if(i >= X && j >= Y) {
                    break;
                }
                A[i][j] = B[i][j];
            }
        }

        for(int i=X;i<H;i++) {
            for(int j=Y;j<W;j++) {
                A[i][j] = AtoB[i-X][j-Y] - A[i-X][j-Y];
            }
        }

        for(int i=0;i<H;i++) {
            for(int j=0;j<W;j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
    }
}
