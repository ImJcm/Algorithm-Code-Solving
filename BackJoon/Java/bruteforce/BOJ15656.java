package bruteforce;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
N과 M (7)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	10872	8534	6910	79.535%
문제
N개의 자연수와 자연수 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오. N개의 자연수는 모두 다른 수이다.

N개의 자연수 중에서 M개를 고른 수열
같은 수를 여러 번 골라도 된다.
입력
첫째 줄에 N과 M이 주어진다. (1 ≤ M ≤ N ≤ 7)

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
7 1
7 7
7 8
7 9
8 1
8 7
8 8
8 9
9 1
9 7
9 8
9 9
예제 입력 3
3 3
1231 1232 1233
예제 출력 3
1231 1231 1231
1231 1231 1232
1231 1231 1233
1231 1232 1231
1231 1232 1232
1231 1232 1233
1231 1233 1231
1231 1233 1232
1231 1233 1233
1232 1231 1231
1232 1231 1232
1232 1231 1233
1232 1232 1231
1232 1232 1232
1232 1232 1233
1232 1233 1231
1232 1233 1232
1232 1233 1233
1233 1231 1231
1233 1231 1232
1233 1231 1233
1233 1232 1231
1233 1232 1232
1233 1232 1233
1233 1233 1231
1233 1233 1232
1233 1233 1233
 */
/*
 * 중복을 허용하는 출력을 하기 때문에 visited를 검사할 필요가 없어보이는 DFS를 구현하면 될 것으로 판단.
 * 결과 : 시간초과
 * 이유 : depth가 M일 때마다 반복문을 통해 print, println을 사용하여 시간이 초과되는 것 같다.
 * 따라서, StringBuilder로 출력형식을 모은뒤 마지막에 한번에 println하는 것을 추천
*/
public class BOJ15656 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] box,input;
    static int N,M;
    static String[] s;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException{
        s = br.readLine().split(" ");
        N = Integer.parseInt(s[0]);
        M = Integer.parseInt(s[1]);

        box = new int[N];
        input = new int[N];

        s = br.readLine().split(" ");
        for(int i=0;i<N;i++) {
            input[i] = Integer.parseInt(s[i]);
        }

        Arrays.sort(input);

        DFS(0);

        System.out.println(sb.toString());
    }

    static void DFS(int depth) {
        if(depth == M) {
            for(int i=0;i<M;i++) {
                //System.out.print(box[i] + " ");
                sb.append(box[i] + " ");
            }
            sb.append("\n");
            return;
        }

        for(int i=0;i<N;i++) {
            box[depth] = input[i];
            DFS(depth+1);
        }
    }
}
