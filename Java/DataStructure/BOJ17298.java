package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

/*
오큰수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	102051	37930	26430	35.178%
문제
크기가 N인 수열 A = A1, A2, ..., AN이 있다. 수열의 각 원소 Ai에 대해서 오큰수 NGE(i)를 구하려고 한다. Ai의 오큰수는 오른쪽에 있으면서 Ai보다 큰 수 중에서 가장 왼쪽에 있는 수를 의미한다. 그러한 수가 없는 경우에 오큰수는 -1이다.

예를 들어, A = [3, 5, 2, 7]인 경우 NGE(1) = 5, NGE(2) = 7, NGE(3) = 7, NGE(4) = -1이다. A = [9, 5, 4, 8]인 경우에는 NGE(1) = -1, NGE(2) = 8, NGE(3) = 8, NGE(4) = -1이다.

입력
첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000,000)이 주어진다. 둘째 줄에 수열 A의 원소 A1, A2, ..., AN (1 ≤ Ai ≤ 1,000,000)이 주어진다.

출력
총 N개의 수 NGE(1), NGE(2), ..., NGE(N)을 공백으로 구분해 출력한다.

예제 입력 1
4
3 5 2 7
예제 출력 1
5 7 7 -1
예제 입력 2
4
9 5 4 8
예제 출력 2
-1 8 8 -1
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: rhdqor213
알고리즘 분류
자료 구조
스택
 */
/*
알고리즘 핵심
자료구조 (Stack)
1. 입력으로 주어지는 값 A에서 뒷부분부터 앞부분까지 순차적으로 오큰수(nge)의 대상으로 정하여 반복한다.
2. A의 마지막 값은 nge 값이 -1이 고정이므로, -1값을 nge에 저장한다.
3. N-2인 A배열의 뒤에서 2번째 값부터 시작하여 0번째 값까지 반복을 진행하고, 현재 인덱스의 값과 인덱스 기준 앞선 값을
target, before_target에 저장한다.
4. stack 변수에 before_target값을 저장한다.
5. target이 before_target과 비교한다.
5-1. target > before_target) target 기준 before_target이 target의 오큰수이므로 nge에 저장한다.
5-2. target <= before_target) 오큰수를 찾기 위해 target기준 우측방향의 값들을 저장한 stack에서
target보다 큰 값을 찾는다.
이때, target보다 작은 값은 pop을 통해 제거하고, 큰 값이 나올때까지 stack 내부를 비운다.
(stack을 비워도 되는 이유 : 현재 기준 target의 오큰수에 해당하지 않는 값들은 target 이후의 값 기준으로 오큰수가 될 수 없기 때문이다.)
stack이 모두 비워지는 경우, nge에 -1을 저장하고, 오큰수를 중간에 찾은 경우 해당 값을 nge에 저장한다.
6. 모든 과정을 마치고 nge의 값을 출력한다.
 */
public class BOJ17298 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] A;
    static Stack<Integer> stack, nge;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        nge.push(-1);

        for(int i = N - 2; i >= 0; i--) {
            int target = A[i];
            int before_target = A[i + 1];

            stack.push(before_target);

            if(target < before_target) nge.push(before_target);
            else {
                int r = -1;
                while(!stack.isEmpty()) {
                    r = stack.peek();
                    if(r > target) break;
                    else {
                        r = -1;
                        stack.pop();
                    }
                }
                nge.push(r);
            }
        }

        while(!nge.isEmpty()) {
            sb.append(nge.pop()).append(" ");
        }

        System.out.println(sb.toString().trim());
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        A = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        stack = new Stack<>();
        nge = new Stack<>();

        sb = new StringBuilder();
    }
}
