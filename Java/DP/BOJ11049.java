import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
행렬 곱셈 순서

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	23387	10665	7624	44.364%
문제
크기가 N×M인 행렬 A와 M×K인 B를 곱할 때 필요한 곱셈 연산의 수는 총 N×M×K번이다. 행렬 N개를 곱하는데 필요한 곱셈 연산의 수는 행렬을 곱하는 순서에 따라 달라지게 된다.

예를 들어, A의 크기가 5×3이고, B의 크기가 3×2, C의 크기가 2×6인 경우에 행렬의 곱 ABC를 구하는 경우를 생각해보자.

AB를 먼저 곱하고 C를 곱하는 경우 (AB)C에 필요한 곱셈 연산의 수는 5×3×2 + 5×2×6 = 30 + 60 = 90번이다.
BC를 먼저 곱하고 A를 곱하는 경우 A(BC)에 필요한 곱셈 연산의 수는 3×2×6 + 5×3×6 = 36 + 90 = 126번이다.
같은 곱셈이지만, 곱셈을 하는 순서에 따라서 곱셈 연산의 수가 달라진다.

행렬 N개의 크기가 주어졌을 때, 모든 행렬을 곱하는데 필요한 곱셈 연산 횟수의 최솟값을 구하는 프로그램을 작성하시오. 입력으로 주어진 행렬의 순서를 바꾸면 안 된다.

입력
첫째 줄에 행렬의 개수 N(1 ≤ N ≤ 500)이 주어진다.

둘째 줄부터 N개 줄에는 행렬의 크기 r과 c가 주어진다. (1 ≤ r, c ≤ 500)

항상 순서대로 곱셈을 할 수 있는 크기만 입력으로 주어진다.

출력
첫째 줄에 입력으로 주어진 행렬을 곱하는데 필요한 곱셈 연산의 최솟값을 출력한다. 정답은 2^(31)-1 보다 작거나 같은 자연수이다. 또한, 최악의 순서로 연산해도 연산 횟수가 2^(31)-1보다 작거나 같다.

예제 입력 1
3
5 3
3 2
2 6
예제 출력 1
90
 */
public class BOJ11049 {
    static int N;
    static int min = Integer.MAX_VALUE;
    static int[] greedy;
    static int[][] dp;
    static int[] row, column;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        row = new int[N+1];
        column = new int[N+1];

        ArrayList<Integer> matrix = new ArrayList<>();
        for(int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            row[i] = r;
            column[i] = c;

            if(i==N-1) {
                matrix.add(r);
                matrix.add(c);
                continue;
            }
            matrix.add(r);
        }

        //timeout
        //DFS(matrix,0);
        //System.out.println(min);

        //failure
        //greedy = new int[matrix.size()-1];
        //Arrays.fill(greedy,0);
        //Greedy(matrix);

        dp = new int[N+1][N+1];
        System.out.println(DP(0,N-1));
    }

    //timeout
    static void DFS(ArrayList<Integer> a, int c) {
        if(a.size() == 2) {
            min = Math.min(min, c);
            return;
        }

        for(int i=0;i<a.size()-2;i++) {
            int r = a.remove(i+1);
            int mul_cnt = a.get(i) * r * a.get(i+1);
            DFS(a,c+mul_cnt);
            a.add(i+1,r);
        }
    }

    //DP
    /*
        (ABCD)(EFG)가 있을 때,
         x  i    y
        행렬의 곱 횟수는
        행의 개수: a b c d e f g
        열의 개수: b c d e f g h
        곱셈 연산의 수 = a x e x h
     */
    static int DP(int x,int y) {
        if(dp[x][y] > 0) return dp[x][y];

        if(x >= y) {
            return 0;
        }

        int minX = (int)Math.pow(2,31) - 1;
        for(int i=x;i<y;i++) {
            minX = Math.min(minX, row[x]*column[i]*column[y] + DP(x,i) + DP(i+1,y));
        }

        return dp[x][y] = minX;
    }

    //행렬의 곱연산을 최소값만을 찾는 탐욕 탐색은 오답
    static void Greedy(ArrayList<Integer> a) {
        for(int i=1;i<N;i++) {
            int m = Integer.MAX_VALUE;
            int midx = -1;
            for(int j=0;j<a.size()-2;j++) {
                if(m >= greedy[i-1] + a.get(j) * a.get(j+1) * a.get(j+2)) {
                    midx = j+1;
                    m = greedy[i-1] + a.get(j) * a.get(j+1) * a.get(j+2);
                }
            }
            a.remove(midx);
            greedy[i] = m;
        }

        System.out.println(greedy[N-1]);
    }
}
