/*
포도주 시식

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	106703	36234	26101	32.608%
문제
효주는 포도주 시식회에 갔다. 그 곳에 갔더니, 테이블 위에 다양한 포도주가 들어있는 포도주 잔이 일렬로 놓여 있었다. 효주는 포도주 시식을 하려고 하는데, 여기에는 다음과 같은 두 가지 규칙이 있다.

포도주 잔을 선택하면 그 잔에 들어있는 포도주는 모두 마셔야 하고, 마신 후에는 원래 위치에 다시 놓아야 한다.
연속으로 놓여 있는 3잔을 모두 마실 수는 없다.
효주는 될 수 있는 대로 많은 양의 포도주를 맛보기 위해서 어떤 포도주 잔을 선택해야 할지 고민하고 있다. 1부터 n까지의 번호가 붙어 있는 n개의 포도주 잔이 순서대로 테이블 위에 놓여 있고, 각 포도주 잔에 들어있는 포도주의 양이 주어졌을 때, 효주를 도와 가장 많은 양의 포도주를 마실 수 있도록 하는 프로그램을 작성하시오.

예를 들어 6개의 포도주 잔이 있고, 각각의 잔에 순서대로 6, 10, 13, 9, 8, 1 만큼의 포도주가 들어 있을 때, 첫 번째, 두 번째, 네 번째, 다섯 번째 포도주 잔을 선택하면 총 포도주 양이 33으로 최대로 마실 수 있다.

입력
첫째 줄에 포도주 잔의 개수 n이 주어진다. (1 ≤ n ≤ 10,000) 둘째 줄부터 n+1번째 줄까지 포도주 잔에 들어있는 포도주의 양이 순서대로 주어진다. 포도주의 양은 1,000 이하의 음이 아닌 정수이다.

출력
첫째 줄에 최대로 마실 수 있는 포도주의 양을 출력한다.

예제 입력 1
6
6
10
13
9
8
1
예제 출력 1
33
 */
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ2156 {
    static int N;
    static int[][][][] mem;
    static int[] M; //mol
    static int max = Integer.MIN_VALUE;

    static Integer[] temp_mem;
    static int[] arr;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        M = new int[10000];
        for(int i=0;i<N;i++) {
            M[i] = Integer.parseInt(br.readLine());
        }

        mem = new int[10000+1][2][2][2];
        for(int i=0;i<=N;i++) {
            for(int j=0;j<2;j++) {
                for(int k=0;k<2;k++) {
                    Arrays.fill(mem[i][j][k],0);
                }
            }
        }
        mem[0][0][0][0] = mem[0][1][0][0] = mem[0][0][1][0] = mem[0][1][1][0] = 0;
        mem[0][0][0][1] = mem[0][1][0][1] = mem[0][0][1][1] = M[0];

        dp(N);

        System.out.println(max);

        //질문검색 - 답안
        arr = new int[10000+1];
        temp_mem = new Integer[10000+1];
        for(int i=0;i<N;i++) {
            arr[i+1] = M[i];
        }
        temp_mem[0] = arr[0] = 0;
        temp_mem[1] = arr[1];
        temp_mem[2] = arr[2] + arr[1];

        System.out.println(Math.max(tasting(N-1),tasting(N)));
    }
    static void dp(int n) {
        if(n == 1) {
            max = Math.max(max, M[n-1]);

        }
        for(int i=1;i<=n;i++) {
            mem[i][0][0][0] = Math.max(Math.max(mem[i-1][1][0][0], mem[i-1][0][0][0])
                    ,mem[i][0][0][0]);
            mem[i][0][0][1] = Math.max(Math.max(mem[i-1][0][0][0],mem[i-1][1][0][0]) + M[i]
                    ,mem[i][0][0][1]);

            mem[i][0][1][0] = Math.max(Math.max(mem[i-1][1][0][1], mem[i-1][0][0][1])
                    ,mem[i][0][1][0]);
            mem[i][0][1][1] = Math.max(Math.max(mem[i-1][1][0][1],mem[i-1][0][0][1]) + M[i]
                    ,mem[i][0][1][1]);

            mem[i][1][0][0] = Math.max(Math.max(mem[i-1][0][1][0], mem[i-1][1][1][0])
                    ,mem[i][1][0][0]);
            mem[i][1][0][1] = Math.max(Math.max(mem[i-1][0][1][0],mem[i-1][1][1][0]) + M[i]
                    ,mem[i][1][0][1]);

            mem[i][1][1][0] = Math.max(mem[i-1][0][1][1],mem[i][1][1][0]);

            max = Math.max(mem[i][0][0][0],Math.max(mem[i][0][0][1],
                    Math.max(mem[i][0][1][0],Math.max(mem[i][0][1][1],
                            Math.max(mem[i][1][0][0],Math.max(mem[i][1][0][1],
                                    mem[i][1][1][0]))))));
        }
    }

    /*
        0 : no drink, 1: drink
        tasting(i-3) + arr[i] + arr[i-1] : 0 1 1
        tasting(i-2) + arr[i]            : 0 1
        tasting(i-1)                     : 0
     */
    static int tasting(int i) {
        if(temp_mem[i] == null) {
            temp_mem[i] = Math.max(tasting(i-3) + arr[i] + arr[i-1],
                    Math.max(tasting(i-2) + arr[i],tasting(i-1)));
        }
        return temp_mem[i];
    }

}
