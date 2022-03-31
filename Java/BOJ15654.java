package BOJ;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
N과 M (5) (Silver3)
시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	16603	12146	9706	72.901%
문제
N개의 자연수와 자연수 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오. N개의 자연수는 모두 다른 수이다.

N개의 자연수 중에서 M개를 고른 수열
입력
첫째 줄에 N과 M이 주어진다. (1 ≤ M ≤ N ≤ 8)

둘째 줄에 N개의 수가 주어진다. 입력으로 주어지는 수는 10,000보다 작거나 같은 자연수이다.

출력
한 줄에 하나씩 문제의 조건을 만족하는 수열을 출력한다. 중복되는 수열을 여러 번 출력하면 안되며, 각 수열은 공백으로 구분해서 출력해야 한다.

수열은 사전 순으로 증가하는 순서로 출력해야 한다.

예제 입력 1
3 1
4 5 2
예제 출력 1
2
4
5
예제 입력 2
4 2
9 8 7 1
예제 출력 2
1 7
1 8
1 9
7 1
7 8
7 9
8 1
8 7
8 9
9 1
9 7
9 8
예제 입력 3
4 4
1231 1232 1233 1234
예제 출력 3
1231 1232 1233 1234
1231 1232 1234 1233
1231 1233 1232 1234
1231 1233 1234 1232
1231 1234 1232 1233
1231 1234 1233 1232
1232 1231 1233 1234
1232 1231 1234 1233
1232 1233 1231 1234
1232 1233 1234 1231
1232 1234 1231 1233
1232 1234 1233 1231
1233 1231 1232 1234
1233 1231 1234 1232
1233 1232 1231 1234
1233 1232 1234 1231
1233 1234 1231 1232
1233 1234 1232 1231
1234 1231 1232 1233
1234 1231 1233 1232
1234 1232 1231 1233
1234 1232 1233 1231
1234 1233 1231 1232
1234 1233 1232 1231
 */
//모든 경우의수를 M만큼 찾아야 하므로, dps를 쓰면 될 것 같다
public class BOJ15654 {
    static int[] box;
    static int[] input;
    static int N,M;
    static boolean[] visited;

    public static void main(String[] args) throws IOException, NullPointerException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        box = new int[N];
        input = new int[N];
        visited = new boolean[N];
        st = new StringTokenizer(br.readLine()," ");
        for(int i=0;i<N;i++) {
            input[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(input);
        DFS(0);

    }

    static void DFS(int depth) {
        if(depth == M) {
            for(int i=0;i<M;i++) {
                System.out.print(box[i] + " ");
            }
            System.out.println();
            return;
        }

        for(int i=0;i<N;i++) {
            if(visited[i]) {
                continue;
            }
            visited[i] = true;
            box[depth] = input[i];
            DFS(depth+1);
            visited[i] = false;
        }
    }
}
