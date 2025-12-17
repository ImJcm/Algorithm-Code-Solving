package bruteforce;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
N과 M (6)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	10742	9124	7392	85.407%
문제
N개의 자연수와 자연수 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오. N개의 자연수는 모두 다른 수이다.

N개의 자연수 중에서 M개를 고른 수열
고른 수열은 오름차순이어야 한다.
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
7 8
7 9
8 9
예제 입력 3
4 4
1231 1232 1233 1234
예제 출력 3
1231 1232 1233 1234
 */
public class BOJ15655 {
    static int[] box;
    static int[] input;
    static int N,M;
    static boolean[] visited;
    static boolean[] static_visited;
    static BufferedReader br;
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        box = new int[N];
        input = new int[N];
        visited = new boolean[N];

        st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;i++) {
            input[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(input);

        Combination(0,0);
    }
    /*
     * Combination : 조합이라고 생각은 했지만 어떻게 구현할지 몰라서 삽질을 많이했다.
     * for문에서 i를 s로 고정시키고, 재귀호출 시, s = i로 하게되면, 이전 index는 거치지않고,
     * 자기자신부터 시작하여 다음 index만 접근하므로, 조합으로 중복을 허용하지않고 출력한다.
     */

    static void Combination(int depth,int s) {
        if(depth == M) {
            for(int i=0;i<M;i++) {
                System.out.print(box[i] + " ");
            }
            System.out.println();
            return;
        }

        for(int i=s;i<N;i++) {
            if(visited[i]) {
               continue;
            }
            box[depth] = input[i];
            visited[i] = true;
            Combination(depth+1,i);
            visited[i] = false;
        }
    }
}
