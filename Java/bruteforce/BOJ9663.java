/*
문제
N-Queen 문제는 크기가 N × N인 체스판 위에 퀸 N개를 서로 공격할 수 없게 놓는 문제이다.

N이 주어졌을 때, 퀸을 놓는 방법의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N이 주어진다. (1 ≤ N < 15)

출력
첫째 줄에 퀸 N개를 서로 공격할 수 없게 놓는 경우의 수를 출력한다.

예제 입력 1
8
예제 출력 1
92
 */
import java.util.*;

public class BOJ9663 {
    static int N;
    static int[] column;
    static int ans=0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        column = new int[N];

        clearcolumn();
        nQueen(0);
        System.out.println(ans);
    }

    public static void nQueen(int row) {
        //종료 조건
        //모든 row를 지정했다면, return
        if (row == N) {
            ans++;
            return;
        }
        for (int col = 0; col < N; col++) {
            if (isSafePlace(row,col)) {
                column[row] = col;
                nQueen(row + 1);
                column[row] = -1;
            }
        }
    }

    //Queen을 놓으려는 (row,col)의 지점이 아무런 공격을 받지 않는지 여부를 확인
    public static boolean isSafePlace(int row, int col) {
        //row = 0인 첫 행의 위치는 확정적 true, 첫 Queen의 위치를 결정하고, 이후 queen의 위치를 결정한다.
        if(row < 1) {
            return true;
        }

        for(int r=0; r < row; r++) {
            /* column[r] == r행의 col값과 매개변수 col과 비교하여 같은 열인지 확인,
             * row - r == Math.abs(col - column[r])의 값을 통해, 같은 대각선 상에 있는지 확인,
             * 대각선인지 확인하는 로직은 row의 차이값과 col의 차이값이 같은지를 확인하여 대각선 여부를 판단하는 것
             */
            if(column[r] == col || row - r == Math.abs(col - column[r])) {
                return false;
            }
        }
        return true;
    }

    /* 초기 column[]의 값을 -1로 초기화하는 이유는, column의 배열의 크기를 0~N-1로만 지정했기 때문에,
     * 선언 당시, 초기 값은 0으로 되어있어, isSafePlace 과정에서 오류가 날 수 있어, 아직 row에 Queen이 지정되지 않는 것을
     * -1 값으로 지정
     */
    public static void clearcolumn() {
        for(int r=0;r<N;r++) {
            column[r] = -1;
        }
    }
}
