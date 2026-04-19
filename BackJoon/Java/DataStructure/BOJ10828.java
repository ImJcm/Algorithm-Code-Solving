package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EmptyStackException;
import java.util.Stack;

/*
스택

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (추가 시간 없음)	256 MB	289078	108252	78440	38.236%
문제
정수를 저장하는 스택을 구현한 다음, 입력으로 주어지는 명령을 처리하는 프로그램을 작성하시오.

명령은 총 다섯 가지이다.

push X: 정수 X를 스택에 넣는 연산이다.
pop: 스택에서 가장 위에 있는 정수를 빼고, 그 수를 출력한다. 만약 스택에 들어있는 정수가 없는 경우에는 -1을 출력한다.
size: 스택에 들어있는 정수의 개수를 출력한다.
empty: 스택이 비어있으면 1, 아니면 0을 출력한다.
top: 스택의 가장 위에 있는 정수를 출력한다. 만약 스택에 들어있는 정수가 없는 경우에는 -1을 출력한다.
입력
첫째 줄에 주어지는 명령의 수 N (1 ≤ N ≤ 10,000)이 주어진다. 둘째 줄부터 N개의 줄에는 명령이 하나씩 주어진다. 주어지는 정수는 1보다 크거나 같고, 100,000보다 작거나 같다. 문제에 나와있지 않은 명령이 주어지는 경우는 없다.

출력
출력해야하는 명령이 주어질 때마다, 한 줄에 하나씩 출력한다.

예제 입력 1
14
push 1
push 2
top
size
empty
pop
pop
pop
size
empty
pop
push 3
empty
top
예제 출력 1
2
2
0
2
1
-1
0
1
-1
0
3
예제 입력 2
7
pop
top
push 123
top
pop
top
pop
예제 출력 2
-1
-1
123
123
-1
-1
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: deunlee, djm03178
알고리즘 분류
구현
자료 구조
스택
 */
/*
알고리즘 핵심
자료구조(스택)
1. java에서 제공하는 util.Stack을 이용하여 각 문제에서 요구하는 push, pop, size, empty, top을 stack의 내장함수로
사용하여 나타낸다.
2. pop, top의 경우 stack이 비어있는 경우 EmptyStackException을 예외처리하여 -1을 출력할 수 있도록한다.
3. 출력하는 대상이 정해지지 않은 경우를 나타내기 위해 num의 초기값을 Integer.MIN_VALUE로 설정하고 값이 업데이트되는 경우 해당 값을 출력한다.
그렇지 않은 경우 출력하지 않는다.
 */
public class BOJ10828 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static Stack<Integer> stack;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() throws IOException{
        for(int op = 0; op < N; op++) {
            Integer num = Integer.MIN_VALUE;
            boolean e = false, flag = false;
            String[] opration = br.readLine().split(" ");

            switch (opration[0]) {
                case "push": {
                    stack.push(Integer.valueOf(opration[1]));
                    break;
                }
                case "pop": {
                    try {
                        num = stack.pop();
                    } catch (EmptyStackException ex) {
                        num = -1;
                    }
                    break;
                }
                case "size": {
                    num = stack.size();
                    break;
                }
                case "empty": {
                    flag = true;
                    e = stack.isEmpty();
                    break;
                }
                case "top": {
                    try {
                        num = stack.peek();
                    } catch (EmptyStackException ex) {
                        num = -1;
                    }
                    break;
                }
            }

            if(num != Integer.MIN_VALUE) System.out.println(num);
            if(flag) {
                if(e) System.out.println(1);
                else System.out.println(0);
            }
        }
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        stack = new Stack<>();
    }
}
