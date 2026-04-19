package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
오등큰수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	21650	10171	7904	46.098%
문제
크기가 N인 수열 A = A1, A2, ..., AN이 있다. 수열의 각 원소 Ai에 대해서 오등큰수 NGF(i)를 구하려고 한다.

Ai가 수열 A에서 등장한 횟수를 F(Ai)라고 했을 때, Ai의 오등큰수는 오른쪽에 있으면서 수열 A에서 등장한 횟수가 F(Ai)보다 큰 수 중에서 가장 왼쪽에 있는 수를 의미한다. 그러한 수가 없는 경우에 오등큰수는 -1이다.

예를 들어, A = [1, 1, 2, 3, 4, 2, 1]인 경우 F(1) = 3, F(2) = 2, F(3) = 1, F(4) = 1이다. A1의 오른쪽에 있으면서 등장한 횟수가 3보다 큰 수는 없기 때문에, NGF(1) = -1이다. A3의 경우에는 A7이 오른쪽에 있으면서 F(A3=2) < F(A7=1) 이기 때문에, NGF(3) = 1이다. NGF(4) = 2, NGF(5) = 2, NGF(6) = 1 이다.

입력
첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000,000)이 주어진다. 둘째에 수열 A의 원소 A1, A2, ..., AN (1 ≤ Ai ≤ 1,000,000)이 주어진다.

출력
총 N개의 수 NGF(1), NGF(2), ..., NGF(N)을 공백으로 구분해 출력한다.

예제 입력 1
7
1 1 2 3 4 2 1
예제 출력 1
-1 -1 1 2 2 1 -1
출처
문제를 만든 사람: baekjoon
알고리즘 분류
자료 구조
스택
 */
/*
알고리즘 핵심
자료구조 (Stack + Map)
1. Map<A,B> 자료구조를 사용하여 A의 값이 나온 횟수를 B의 값으로 저장하고, stack으로 A값을 우측부터 가장 가까운 순으로 저장할 수 있도록 한다.
2. 우측부터 좌측으로 현재의 인덱스를 기준으로 map(A[i])의 값이 stack의 peek()값과 비교하여 모든 stack이 빌때까지 수행한다.
비교하는 과정은 map(A[i]) >= map(stack.peek())일 때, 오큰수(NGF)에 해당하는 값이 아니고, i - 1을 기준으로 해당 과정을 수행해도 map(A[i])보다 작은 값은
오큰수가 될 수 없으므로 pop으로 stack에서 제거한다.
이후, stack이 비어있을 경우, NGF[i] = -1
stack이 비어있지 않을 경우, 해당하는 stack.peek()값이 오큰수에 해당하므로 NGF[i] = stack.peek()값을 저장한다.
3. NGF를 출력한다.

문제의 첫 접근을 정답코드와 비슷하게 작성하였으나 시간초과가 발생하였다.
그 이유를 개인적으로 생각해보면 불필요한 메모리의 할당(배열)으로 인한 시간초과가 발생한 것으로 생각된다.

가지치기 + Stack -> Deque를 사용하여 시간초과를 해결해보려고 해도 해결되지 않아 정답코드를 참고하였다.
https://velog.io/@mings/BOJGold4-17299.-%EC%98%A4%EB%93%B1%ED%81%B0%EC%88%98JAVA
 */
public class BOJ17299 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] A,F,NGF;
    static Stack<Integer> stack;
    static Map<Integer, Integer> map;
    static StringBuilder sb;

    static final int MAX_SIZE = 1_000_001;

    public static void main(String[] args) throws IOException {
        init_setting2();

        solve();
    }

    private static void solve() {
        for(int i = A.length - 1; i >= 0; i--) {
            while(!stack.isEmpty() && map.get(stack.peek()) <= map.get(A[i])) stack.pop();

            // 시간초과 - sb.insert > NGF[] + for(N)
            //if(stack.isEmpty()) sb.insert(0,-1 + " ");
            //else sb.insert(0, stack.peek() + " ");

            if(stack.isEmpty()) NGF[i] = -1;
            else NGF[i] = stack.peek();

            stack.push(A[i]);
        }

        for(int n : NGF) {
            sb.append(n).append(" ");
        }

        System.out.println(sb.toString());
    }

    private static void solve_timeout4() {
        int m_limit = F[A[N - 1]];
        for(int i = A.length - 1; i >= 0; i--) {
            int target = A[i];

            if(m_limit <= F[target]) {
                stack.clear();
                m_limit = F[target];
            }
            while(!stack.isEmpty() && F[stack.peek()] <= F[target]) stack.pop();

            //if(stack.isEmpty()) NGF[i] = -1;
            //else NGF[i] = stack.peek();

            if(stack.isEmpty()) sb.insert(0,-1 + " ");
            else sb.insert(0, stack.peek() + " ");

            stack.push(target);
        }

        System.out.println(sb.toString());

        /*for(int i = 0; i < N; i++) {
            System.out.print(NGF[i] + " ");
        }*/
    }

    private static void solve_timeout3() {
        for(int i = A.length - 1; i >= 0; i--) {
            int target = A[i];

            while(!stack.isEmpty() && F[stack.peek()] <= F[target]) stack.pop();

            if(stack.isEmpty()) NGF[i] = -1;
            else NGF[i] = stack.peek();

            stack.push(target);
        }

        for(int i = 0; i < N; i++) {
            System.out.print(NGF[i] + " ");
        }
    }

    /*
        failure 1% : time out
     */
    private static void solve_timeout2() {
        int m = 0;

        for(int i = A.length - 1; i >= 0; i--) {
            int target = A[i];
            int r = -1;

            if(m <= F[target]) {
                stack.clear();
                m = F[target];
            }

            while(!stack.isEmpty()) {
                int top = stack.peek();

                if(F[top] > F[target]) {
                    r = top;
                    break;
                } else {
                    stack.pop();
                }
            }

            stack.push(target);
            sb.insert(0,r + " ");
        }

        System.out.println(sb.toString().trim());
    }

    /*
        failure 1% : time out
     */
    private static void solve_timeout() {
        for(int i = A.length - 1; i >= 0; i--) {
            int target = A[i];
            int r = -1;

            while(!stack.isEmpty()) {
                int top = stack.peek();

                if(F[top] > F[target]) {
                    r = top;
                    break;
                } else {
                    stack.pop();
                }
            }

            stack.push(target);
            sb.insert(0,r + " ");
        }

        System.out.println(sb.toString().trim());
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        String[] input = br.readLine().split(" ");

        A = new int[N];
        F = new int[MAX_SIZE];
        NGF = new int[N];

        NGF[N - 1] = -1;

        for(int i = 0; i < N; i++) {
            int n = Integer.parseInt(input[i]);
            A[i] = n;
            F[n]++;
        }

        stack = new Stack<>();

        sb = new StringBuilder();
    }

    private static void init_setting2() throws IOException {
        N = Integer.parseInt(br.readLine());

        String[] input = br.readLine().split(" ");

        A = new int[N];
        NGF = new int[N];
        map = new HashMap<>();

        for(int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(input[i]);
            map.put(A[i],map.getOrDefault(A[i],0) + 1);
        }

        stack = new Stack<>();

        sb = new StringBuilder();
    }
}
