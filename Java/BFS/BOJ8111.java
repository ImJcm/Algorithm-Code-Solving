package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
0과 1 스페셜 저지다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	2693	1517	1109	57.551%
문제
폴란드 왕자 구사과는 다음과 같은 수를 좋아한다.

0과 1로만 이루어져 있어야 한다.
1이 적어도 하나 있어야 한다.
수의 길이가 100 이하이다.
수가 0으로 시작하지 않는다.
예를 들어, 101은 구사과가 좋아하는 수이다.

자연수 N이 주어졌을 때, N의 배수 중에서 구사과가 좋아하는 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 테스트 케이스의 개수 T(T < 10)가 주어진다.

둘째 줄부터 T개의 줄에는 자연수 N이 한 줄에 하나씩 주어진다. N은 20,000보다 작거나 같은 자연수이다.

출력
각각의 테스트 케이스마다 N의 배수이면서, 구사과가 좋아하는 수를 아무거나 출력한다. 만약, 그러한 수가 없다면 BRAK을 출력한다.

예제 입력 1
6
17
11011
17
999
125
173
예제 출력 1
11101
11011
11101
111111111111111111111111111
1000
1011001101
출처
Olympiad > Polish Olympiad in Informatics > POI 1994/1995 > Stage 1 2번

데이터를 추가한 사람: djs100201
알고리즘 분류
수학
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
문제를 읽고 접근방법에 대해서 생각해봤을 때 다음과 같은 방법이 나왔다.
1. 100자리의 1,0으로 이루어진 수를 모두 구한 후, 입력으로 주어진 N의 배수인 것을 찾는 방법

하지만, 1의 방법으로 시간복잡도를 예상해봤을 때 1,0으로 이루어진 수를 구하는 과정이 최대 2^99승 개의 데이터가 나오기 때문에 문제 조건으로 부적합하다고 생각했다.

따라서, 다른 방법을 생각해야 했다.

문제를 다시 읽어보고 0,1로 이루어진 수를 만들어내야 하기 때문에 N을 주어진 수에서 어떠한 수를 곱하였을 때 뒷자리 수가 0,1을 만족해야하기 때문에 이를 활용하고
어떠한 수를 곱하였는지는 중요하지 않기 때문에 다음과 같은 방법을 생각할 수 있었다.

1. 최소 1개 이상의 1이 존재해야 하기 때문에 queue에 넣을 데이터는 X가 1~9까지 곱한 값에서 뒷자리가 0,1인 값을 선택하여 next_num, cur_num을 구분하여 초기 데이터를 queue에 설정한다.
(n = 17일 때, 17 * (1~9) -> 3일 때, 51 : next_num = 5, cur_num = 1)
2. queue에 들어간 데이터에서 (N * X) + next_num의 값의 뒷자리가 0,1인 값을 선택하여 다음 데이터로 queue에 설정한다.
3. next_num이 0인 경우, 1,2과정을 종료하고 cur_num을 출력한다.

위 과정을 수행했을 때, 문제의 예제 입력을 수행하였을 때, 몇몇의 N은 출력이 되었지만 특정 N을 수행했을 때 무한 로딩에 걸리는 결과를 볼 수 있었다.

그 이유를 생각해보면 queue에 많은 데이터가 쌓여서 생긴 문제라고 예상했고, 이를 해결하기 위해 queue를 디버깅했을 때 원인을 찾을 수 있었다.

같은 수의 next_num의 값을 갖는 데이터가 중복이 많이되었다. 이 중복된 데이터가 필요 없는 이유는 0,1로 이루어진 어떠한 수를 출력하기만 해도 되기때문에
cur_num이 다르더라도 next_num이 중복된 데이터의 필요가 없어도 되는 것이다.

이러한 이유로 추가된 코드는 next_num이 중복되는 것을 방지하기 위해 visited boolean 타입 배열을 추가하여 중복검사를 수행하여 해결할 수 있었다.

알고리즘 핵심
1. 최소 1개 이상의 1이 존재해야 하기 때문에 queue에 넣을 데이터는 X가 1~9까지 곱한 값에서 뒷자리가 0,1인 값을 선택하여 next_num, cur_num을 구분하여 초기 데이터를 queue에 설정한다.
(n = 17일 때, 17 * (1~9) -> 3일 때, 51 : next_num = 5, cur_num = 1)
2. queue에 들어간 데이터에서 (N * X) + next_num의 값의 뒷자리가 0,1인 값을 선택하는데 next_num을 visited에 중복여부를 검사하고 다음 데이터로 queue에 설정한다.
(중복되는 next_num이 존재할 경우 continue, 중복되지 않은 경우 queue에 데이터를 추가한다.)
3. next_num이 0인 경우, 1,2과정을 종료하고 cur_num을 출력한다.
 */
public class BOJ8111 {
    static class BOJ8111_number {
        int next_num;
        String cur_num;

        BOJ8111_number(int next_num, String cur_num) {
            this.next_num = next_num;
            this.cur_num = cur_num;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T,t;
    static int[] N;
    static String[] ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        for(int n : N) {
            bfs(n);
            System.out.println(ans[t-1]);
        }
    }

    private static void bfs(int n) {
        Queue<BOJ8111_number> q = new LinkedList<>();
        boolean[] visited = new boolean[n];

        for(int i = 1; i < 10; i++) {
            if((n * i) % 10 == 0 || (n * i) % 10 == 1) {
                q.add(new BOJ8111_number((n * i) / 10, Integer.toString((n * i) % 10)));
            }
        }

        while(!q.isEmpty()) {
            BOJ8111_number now = q.poll();

            if(now.next_num == 0) {
                ans[t++] = now.cur_num;
                return;
            }

            if(now.cur_num.length() > 100) continue;

            for(int i = 0; i < 10; i++) {
                int r = n * i + now.next_num;

                if(r % 10 == 0 || r % 10 == 1) {
                    if(visited[r / 10]) continue;
                    visited[r / 10] = true;
                    q.add(new BOJ8111_number(r / 10, Integer.toString(r % 10) + now.cur_num));
                }
            }
        }

        ans[t++] = "BRAK";
    }

    private static void init_setting() throws IOException {
        T = Integer.parseInt(br.readLine());
        t = 0;

        N = new int[T];
        ans = new String[T];

        for(int i = 0; i < T; i++) {
            N[i] = Integer.parseInt(br.readLine());
        }
    }
}
