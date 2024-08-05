package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
삼각형의 값 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	2230	535	390	23.636%
문제
오른쪽 삼각형은 9개의 단위 삼각형이 총 3줄(N=3)로 이루어져 있다. 단위 삼각형은 N=1인 삼각형이다.

이때, 그림에서 서로 다른 부분 삼각형은 총 13개가 있다. (N=1인 삼각형이 9개, N=2인 삼각형이 3개, N=3인 삼각형이 1개)

N = 1인 경우 부분 삼각형은 1개, 2인 경우에는 5개, 3인 경우는 13개, 4인 경우는 27개가 있다.

이때, 단위 삼각형의 값을 삼각형 내부에 쓰여 있는 숫자의 값이라고 하자. 삼각형의 값은 삼각형 안에 있는 단위 삼각형의 값의 합이다.

오른쪽 그림은 가장 큰 값을 갖는 부분 삼각형이다.

삼각형이 주어졌을 때, 가장 큰 값을 갖는 부분 삼각형을 구하는 프로그램을 작성하시오.

입력
입력은 여러 개의 테스트 케이스로 이루어져 있고, 각 테스트 케이스는 한 줄로 이루어져 있다. 첫 번째 숫자는 줄의 수를 나타내고, 다음 숫자는 단위 삼각형에 적혀있는 값이 위에서 아래, 왼쪽에서 오른쪽 순서대로 주어진다. 마지막 줄에는 0이 주어진다.

줄의 개수는 400을 넘지 않으며, 단위 삼각형에 적혀있는 값의 절댓값은 1000을 넘지 않는다.

출력
각 테스트 케이스에 대해서, 테스트 케이스의 번호와 가장 큰 부분 삼각형의 값을 출력한다.

예제 입력 1
3 6 -24 0 12 -10 12 40 -4 6
4 1 1 -1 1 1 -1 1 -1 1 1 -1 1 -1 1 -1 1
0
예제 출력 1
1. 54
2. 4
출처
ICPC > Regionals > Africa and Arab > Arab Collegiate Programming Contest > 2008 Arab Collegiate Programming Contest D번

문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: sangdo913
알고리즘 분류
구현
브루트포스 알고리즘
누적 합
 */
/*
실패 코드 - 메모리 초과
예상되는 실패 이유 : 각 head에 큰 크기의 n_sum이 저장되어 있어서 메모리 초과가 발생하는 것으로 예상한다.

head에 해당하는 객체의 n_sum만 할당한 후, 메모리 초과는 해결할 수 있었지만, 틀린 코드로 책정되었다.

이유 : 정방향의 삼각형만 고려하여 부분 삼각형의 합만 계산했기 때문이라고 생각한다.
따라서, 역삼각형의 경우도 고려한 로직이 필요하다.

삼각형을 피라미드 형태로 구분하고 N 계층의 삼각형의 합을 Head 삼각형을 기준으로 왼쪽, 오른쪽 node의 N-1의 sum과 중복되는 삼각형을 제거하여 구하는 방법으로 구현하였다
init_setting() : 단위 삼각형마다 왼쪽, 오른쪽 삼각형을 지정한다.
partial_sum_calculate()
정방향 삼각형 : 높이가 2이상 삼각형부터 N까지 삼각형을 각 Head인 N=1인 단위 삼각형을 기준으로 높이가 2~N까지의 삼각형의 합을 구한다.
역방향 삼각형 : N이 4이상인 상태에서 정방향의 N=1인 삼각형을 기준으로 내부의 역삼각형의 합을 구한다.
-> 25% 시간초과 발생

질문게시판의 게시글을 참고하여 로직을 해석한 방법으로 재구현을 결정하였다.

알고리즘 핵심 - https://www.acmicpc.net/board/view/47263
1. 입력으로 주어지는 단위 삼각형의 값을 2차원 배열로 받는다.
2. N=1인 각 단위 삼각형을 기준으로 높이가 N까지의 부분삼각형의 합들을 모두 구한다.
    이 과정에서 정방향, 역방향 삼각형을 모두 구할 수 있다.
3. 부분 삼각형의 합을 구하는 과정에서 N 높이의 부분 삼각형의 합은 N-1 높이의 부분 삼각형의 합 + N-1에서 구한 삼각형의 밑길이 + 2 만큼의 합으로 구할 수 있다.
    (value[1][1] + value[2][3] + value[3][5] => N=3인 부분 삼각형의 합, value[i][j] : i번째 줄에서 j까지의 삼각형의 합을 의미)

개인적으로 생각하는 핵심 코드는 "같은 행에서 단위 삼각형의 값의 합을 누적한 것" 과 "각 단위 삼각형을 기준으로 부분 삼각형을 정방향, 역방향 모두 구하는 것"

정방향 부분 삼각형의 합을 구할 때, N레벨 까지의 삼각형으로 도달하면서 기준이 되는 단위 삼각형의 j값에 아래로 내려갈 때마다 삼각형의 합에 더하는 구간을 늘려주는 것(l로 조정)과
역방향 부분 삼각형의 합을 구할 때, 위로 올라가면서 부분 삼각형의 합에 더하는 구간의 조건 검사(i2 * 2 - 1)와 더하는 구간(k로 조정)을 늘려주는 것이 중요하다고 생각한다.

 */
public class BOJ4902 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[][] value;


    public static void main(String[] args) throws IOException{
        solve();
    }

    static void solve() throws IOException{
        int numbering = 1;
        while(true) {
            String[] input = br.readLine().split(" ");

            int N = Integer.parseInt(input[0]);
            int ans = Integer.MIN_VALUE;

            if(N == 0) {
                break;
            }

            value = new int[N+1][2*(N+1)];

            for(int i = 1, input_idx = 1; i <= N; i++) {
                for(int j = 1; j <= (2 * i) - 1; j++, input_idx++) {
                    int v = Integer.parseInt(input[input_idx]);
                    value[i][j] = value[i][j-1] + v;
                }
            }

            for(int i = 1; i <= N; i++) {
                for(int j = 1; j <= (2 * i) - 1; j++) {
                    int sum = 0;

                    // 정방향의 삼각형
                    if(j % 2 == 1) {
                        for(int k = i, l = 0; k <= N; k++, l++) {
                            sum += (value[k][j + (2 * l)] - value[k][j-1]);
                            ans = Math.max(ans, sum);
                        }
                    }

                    // 역방향의 삼각형
                    if(j % 2 == 0 && j >= 4) {
                        for(int k = j, i2 = i; k - 2 >= 0; k -= 2, i2--) {
                            if((2 * i2) - 1 < j) break;
                            sum += (value[i2][j] - value[i2][k-1]);
                            ans = Math.max(ans, sum);
                        }
                    }
                }
            }
            System.out.println(numbering++ + ". " + ans);
        }
    }
}

class BOJ4902_time_out {
    static class BOJ4902_head {
        int level,value,down_value;
        BOJ4902_head left,right;
        int[] n_sum;

        BOJ4902_head(int v) {
            this.value = v;
            this.down_value = 0;
            this.left = null;
            this.right = null;
            this.level = 0;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static boolean flag = true;
    static int N,ans;
    static BOJ4902_head[] unit_triangle,heads;

    public static void main(String[] args) throws IOException {
        solve();
    }

    static void solve() throws IOException {
        int numbering = 1;
        StringBuilder sb = new StringBuilder();

        while (true) {
            init_setting();

            if (!flag) {
                break;
            }

            partial_sum_calculate();
            sb.append(numbering++).append(". ").append(ans).append("\n");
        }

        System.out.println(sb);
    }

    static void partial_sum_calculate() {
        for(int i = 2; i <= N; i++) {
            for(BOJ4902_head head : heads) {
                // 정방향 삼각형
                if(head.level + i - 1 <= N) {
                    int i_level_partial_triangle_sum = head.value + head.down_value + head.left.n_sum[i-1] + head.right.n_sum[i-1];
                    int dup_partial_triangle_sum = (i > 2) ? head.left.right.n_sum[i-2] : 0;

                    i_level_partial_triangle_sum -= dup_partial_triangle_sum;

                    head.n_sum[i] = i_level_partial_triangle_sum;

                    ans = Math.max(ans, head.n_sum[i]);
                }

                // 역방향 삼각형
                if(head.level + (3 + (2 * (i - 2))) <= N) {
                    BOJ4902_head left_head = head;
                    BOJ4902_head right_head = head;

                    int side = i;
                    while(side-- > 0) {
                        left_head = left_head.left;
                        right_head = right_head.right;
                    }

                    int sum = head.n_sum[2*i] - (head.n_sum[i] + left_head.n_sum[i] + right_head.n_sum[i]);

                    ans = Math.max(ans,sum);
                }
            }
        }
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);

        if(N == 0) {
            flag = false;
            return;
        }

        int unit_triangle_size = 1 + ((N-1) * (6 + (N-2) * 2)) / 2;     // 계차수열의 합, 모든 N=1인 단위 삼각형의 개수
        int head_size = (N * (N+1)) / 2;

        ans = Integer.MIN_VALUE;

        unit_triangle = new BOJ4902_head[unit_triangle_size];
        heads = new BOJ4902_head[head_size];

        for(int i = 0; i < unit_triangle_size; i++) {
            int value = Integer.parseInt(input[i+1]);
            unit_triangle[i] = new BOJ4902_head(value);

            ans = Math.max(ans, unit_triangle[i].value);
        }

        int s_idx = 0;
        int h_idx = 0;

        for(int i = 1; i <= N; i++) {
            boolean isHead = true;
            int level_size = (2 * i) - 1;

            for(int j = 0; j < level_size; j++) {
                unit_triangle[s_idx].level = i;

                if(isHead) {
                    if(i+1 <= N) {
                        unit_triangle[s_idx].left = unit_triangle[s_idx + level_size];
                        unit_triangle[s_idx].down_value = unit_triangle[s_idx + level_size + 1].value;
                        unit_triangle[s_idx].right = unit_triangle[s_idx + level_size + 2];
                    }

                    unit_triangle[s_idx].n_sum = new int[N - i + 2];
                    unit_triangle[s_idx].n_sum[1] = unit_triangle[s_idx].value;

                    heads[h_idx++] = unit_triangle[s_idx];
                }
                s_idx++;
                isHead = !isHead;
            }
        }
    }

}
