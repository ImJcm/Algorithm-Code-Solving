package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

/*
등차수열 변환

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	1969	869	677	43.677%
문제
크기가 N인 수열 A = [A1, A2, ..., AN]이 있을 때, 모든 1 ≤ i < N에 대해서, Ai+1-Ai가 모두 일치하면 등차수열이라고 한다. 예를 들어, [3], [6, 6, 6], [2, 8, 14, 20], [6, 4, 2]는 등차수열이고, [4, 5, 4], [6, 3, 1]은 등차수열이 아니다.

수열 B = [B1, B2, ..., BN]을 등차수열로 변환하려고 한다. 각각의 수에는 연산을 최대 한 번 적용할 수 있다. 연산은 두 가지가 있는데, 1을 더하거나 1을 빼는 것이다. 수열 B를 등차수열로 변환하기 위해 필요한 연산 횟수의 최솟값을 구해보자.

입력
첫째 줄에 수열 B의 크기 N(1 ≤ N ≤ 105)이 주어진다. 둘째 줄에는 B1, B2, ..., BN(1 ≤ Bi ≤ 109)이 주어진다.

출력
수열 B를 등차수열로 변화시키기 위한 연산 횟수의 최솟값을 출력한다. 등차수열로 변환시킬 수 없다면 -1을 출력한다.

예제 입력 1
4
24 21 14 10
예제 출력 1
3
예제 입력 2
2
5 5
예제 출력 2
0
예제 입력 3
3
14 5 1
예제 출력 3
-1
예제 입력 4
5
1 3 6 9 12
예제 출력 4
1
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
수학
브루트포스 알고리즘
 */
/*
첫 접근을 모든 0,-1,1 연산을 적용한 B배열을 만들고 마지막으로 구성된 B배열의 n까지의 원소들을 b_i+1 - b_i의 등차가 모두 같은지
검사하는 로직이였다. 결과는 시간초과발생

따라서, 모든 가능한 구성을 구하는 것은 잘못된 방식이므로 다른 접근방법을 생각했다.

알고리즘 핵심
1. B의 길이가 2이하인 가능한 총 연산횟수는 0 (비교가능한 등차가 하나이므로 무조건 등차수열이다)
2. 3이상인 B배열에서 B[1] - B[0]이 {0,-1,1} 연산을 수행했을 때, 가능한 등차를 구하고 해당 dfs에 사용할 등차로 고정한다.
3. dfs의 시작은 depth = 1부터 시작하여 depth+1에 해당하는 B배열의 값에 {0,-1,1}연산을 수행하여 연산 횟수를 누적한다.
4. 기저사례로 B.length - 1에 도달하면 일정한 등차를 갖는 등차수열임을 만족하는 것이므로 operator_cnt의 최솟값으로 업데이트한다.
 */
public class BOJ17088 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, ans;
    static int[] B;
    static int[] operand = {0,-1,1};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        //dfs_timeOut(0,0);

        if(B.length <= 2) {
            ans = 0;
        } else {
            for(int i=0;i<3;i++) {
                for(int j=0;j<3;j++) {
                    int init_operator_cnt = 0;

                    if(i == 0 && j == 0) {
                        init_operator_cnt = 0;
                    } else if((i == 0 && j != 0) || (i != 0 && j == 0)) {
                        init_operator_cnt = 1;
                    } else if(i != 0 && j != 0) {
                        init_operator_cnt = 2;
                    }

                    int diff = (B[1] + operand[i]) - (B[0] + operand[j]);
                    B[1] += operand[i];
                    dfs(1, diff, init_operator_cnt);
                    B[1] -= operand[i];
                }
            }
        }

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    static void dfs(int depth, int diff, int operator_cnt) {
        if(depth == B.length - 1) {
            ans = Math.min(ans, operator_cnt);
            return;
        }

        for(int i=0;i<3;i++) {
            int n_diff = (B[depth+1] + operand[i]) - B[depth];
            B[depth+1] += operand[i];
            if(n_diff == diff) {
                dfs(depth+1, diff, i == 0 ? operator_cnt : operator_cnt + 1);
            }
            B[depth+1] -= operand[i];
        }
    }

    // 시간초과
    static void dfs_timeOut(int depth, int operator_cnt) {
        if(depth == B.length) {
            boolean check = true;
            for(int i=0;i<B.length - 2;i++) {
                if((B[i+1] - B[i]) != (B[i+2] - B[i+1])) {
                    check = false;
                    break;
                }
            }

            if(check) {
                ans = Math.min(ans, operator_cnt);
            }
            return;
        }

        for(int i=0;i<3;i++) {
            if(i == 0) {
                dfs_timeOut(depth + 1, operator_cnt);
            } else {
                B[depth] += operand[i];
                dfs_timeOut(depth + 1, operator_cnt + 1);
                B[depth] -= operand[i];
            }
        }
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        B = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        ans = Integer.MAX_VALUE;
    }
}
