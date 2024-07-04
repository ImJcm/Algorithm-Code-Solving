package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
숫자 재배치

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	4601	2194	1642	45.713%
문제
두 정수 A와 B가 있을 때, A에 포함된 숫자의 순서를 섞어서 새로운 수 C를 만들려고 한다. 즉, C는 A의 순열 중 하나가 되어야 한다.

가능한 C 중에서 B보다 작으면서, 가장 큰 값을 구해보자. C는 0으로 시작하면 안 된다.

입력
첫째 줄에 두 정수 A와 B가 주어진다.

출력
B보다 작은 C중에서 가장 큰 값을 출력한다. 그러한 C가 없는 경우에는 -1을 출력한다.

제한
1 ≤ A, B < 109
예제 입력 1
1234 3456
예제 출력 1
3421
예제 입력 2
1000 5
예제 출력 2
-1
예제 입력 3
789 123
예제 출력 3
-1
출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: jh05013
알고리즘 분류
수학
브루트포스 알고리즘
조합론
백트래킹
 */
/*
dfs로 A의 각 자리수에 해당하는 숫자들로 가능한 모든 숫자 조합을 만든 후, 기저사례에서 B보다 작은 값만 C에 최대값으로 업데이트한다.
 */
public class BOJ16943 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int A,B,A_size,C;
    static char[] A_char;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        dfs(0, "");

        System.out.println(C);
    }

    static void dfs(int depth, String s) {
        if(depth == A_size) {
            int c = Integer.parseInt(s);

            if(c < B) {
                C = Math.max(C, c);
            }
            return;
        }

        for(int i=0;i<A_size;i++) {
            if(depth == 0 && A_char[i] == '0') continue;
            if(visited[i]) continue;
            visited[i] = true;
            dfs(depth + 1, s + String.valueOf(A_char[i]));
            visited[i] = false;
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        A_size = input[0].length();

        A = Integer.parseInt(input[0]);
        B = Integer.parseInt(input[1]);
        C = -1;

        A_char = new char[A_size];
        visited = new boolean[A_size];

        for(int i=0;i<A_size;i++) {
            A_char[i] = input[0].charAt(i);
        }

        Arrays.fill(visited, false);
    }
}
