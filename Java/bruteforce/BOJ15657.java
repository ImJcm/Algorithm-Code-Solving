package bruteforce;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

/*
N과 M (8)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	11250	9441	8005	84.121%
문제
N개의 자연수와 자연수 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오. N개의 자연수는 모두 다른 수이다.

N개의 자연수 중에서 M개를 고른 수열
같은 수를 여러 번 골라도 된다.
고른 수열은 비내림차순이어야 한다.
길이가 K인 수열 A가 A1 ≤ A2 ≤ ... ≤ AK-1 ≤ AK를 만족하면, 비내림차순이라고 한다.
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
1 1
1 7
1 8
1 9
7 7
7 8
7 9
8 8
8 9
9 9
예제 입력 3
4 4
1231 1232 1233 1234
예제 출력 3
1231 1231 1231 1231
1231 1231 1231 1232
1231 1231 1231 1233
1231 1231 1231 1234
1231 1231 1232 1232
1231 1231 1232 1233
1231 1231 1232 1234
1231 1231 1233 1233
1231 1231 1233 1234
1231 1231 1234 1234
1231 1232 1232 1232
1231 1232 1232 1233
1231 1232 1232 1234
1231 1232 1233 1233
1231 1232 1233 1234
1231 1232 1234 1234
1231 1233 1233 1233
1231 1233 1233 1234
1231 1233 1234 1234
1231 1234 1234 1234
1232 1232 1232 1232
1232 1232 1232 1233
1232 1232 1232 1234
1232 1232 1233 1233
1232 1232 1233 1234
1232 1232 1234 1234
1232 1233 1233 1233
1232 1233 1233 1234
1232 1233 1234 1234
1232 1234 1234 1234
1233 1233 1233 1233
1233 1233 1233 1234
1233 1233 1234 1234
1233 1234 1234 1234
1234 1234 1234 1234
 */
/*
 * DFS를 쓰는데 조건을 추가해야할 것으로 판단
 * 이전에 box에 추가된 값을 확인하고 크거나 같으면 box[depth]에 input[i]를 넣고
 * 아니면, continue 해서 출력문을 저장한 후, StringBuilder를 통해 출력
 */
public class BOJ15657 {
    static int[] box,input;
    static int N,M;
    static String[] s;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static boolean[] visited;

    public static void main(String[] args) throws IOException{
        s = br.readLine().split(" ");
        N = Integer.parseInt(s[0]);
        M = Integer.parseInt(s[1]);

        input = new int[N];
        box = new int[N];
        visited = new boolean[N];

        s = br.readLine().split(" ");
        for(int i=0;i<N;i++) {
            input[i] = Integer.parseInt(s[i]);
        }

        Arrays.sort(input);

        DFS(0,0);

        System.out.println(sb.toString());
    }

    static void DFS(int depth,int pre_depth) {
        if(depth == M) {
            for(int i=0;i<M;i++) {
                sb.append(box[i] + " ");
            }
            sb.append("\n");
            return;
        }

        for(int i=0;i<N;i++) {
            if(box[pre_depth] <= input[i]) {
                box[depth] = input[i];
            } else {
                continue;
            }
            DFS(depth+1,depth);
        }
    }
}
