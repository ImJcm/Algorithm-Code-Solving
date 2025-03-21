package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/*
스택 수열

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	184569	74384	51400	38.873%
문제
스택 (stack)은 기본적인 자료구조 중 하나로, 컴퓨터 프로그램을 작성할 때 자주 이용되는 개념이다. 스택은 자료를 넣는 (push) 입구와 자료를 뽑는 (pop) 입구가 같아 제일 나중에 들어간 자료가 제일 먼저 나오는 (LIFO, Last in First out) 특성을 가지고 있다.

1부터 n까지의 수를 스택에 넣었다가 뽑아 늘어놓음으로써, 하나의 수열을 만들 수 있다. 이때, 스택에 push하는 순서는 반드시 오름차순을 지키도록 한다고 하자. 임의의 수열이 주어졌을 때 스택을 이용해 그 수열을 만들 수 있는지 없는지, 있다면 어떤 순서로 push와 pop 연산을 수행해야 하는지를 알아낼 수 있다. 이를 계산하는 프로그램을 작성하라.

입력
첫 줄에 n (1 ≤ n ≤ 100,000)이 주어진다. 둘째 줄부터 n개의 줄에는 수열을 이루는 1이상 n이하의 정수가 하나씩 순서대로 주어진다. 물론 같은 정수가 두 번 나오는 일은 없다.

출력
입력된 수열을 만들기 위해 필요한 연산을 한 줄에 한 개씩 출력한다. push연산은 +로, pop 연산은 -로 표현하도록 한다. 불가능한 경우 NO를 출력한다.

예제 입력 1
8
4
3
6
8
7
5
2
1
예제 출력 1
+
+
+
+
-
-
+
+
-
+
+
-
-
-
-
-
예제 입력 2
5
1
2
5
3
4
예제 출력 2
NO
힌트
1부터 n까지에 수에 대해 차례로 [push, push, push, push, pop, pop, push, push, pop, push, push, pop, pop, pop, pop, pop] 연산을 수행하면 수열 [4, 3, 6, 8, 7, 5, 2, 1]을 얻을 수 있다.

출처
문제를 만든 사람: author5
문제의 오타를 찾은 사람: bgjuw12
데이터를 추가한 사람: djm03178, makusta, scala0114
알고리즘 분류
자료 구조
스택
 */
/*
알고리즘 핵심
자료구조(Stack)
1. 입력으로 주어지는 수열의 값을 sequence 배열에 순차적으로 저장한다.
2. sequence 배열의 값을 순차적으로 조회하기 위한 index = s와 오름차순으로 stack에 값을 넣기 위한 데이터 변수 = d를 외부에 선언한다.
3. 반복문을 돌면서 sequence[s]의 값이 d보다 큰 경우 sequence[s] 값만큼 stack에 값을 push하고, sb에 "+"를 넣는다.
4. 3번 과정이 끝나면 sequence[s]의 값과 stack의 peek() 값을 비교하여 같으면, pop() + "-"를 저장한다.
같지 않으면, sequence[]로 주어진 값을 만들 수 없음을 의미하므로 "NO"를 출력하고 위 과정을 종료한다.
(같지 않을 때, sequence[]로 주어진 값을 만들 수 없는 이유 : 처음으로 오름차순에 의해 순차적으로 1 ~ sequence[s] 까지 stack에 값이
들어온 이후, sequence[s]와 stack의 마지막 값을 비교하는 경우는 확정이다.
이후, sequence[s + 1]의 값만큼 현재 stack에 push할 값과 비교하여 stack에 push할 것인지 stack의 마지막 값과 sequence[s + 1]을 비교할 것인지 결정하게 된다.
이때, d > sequence[s + 1]인 경우 더 이상의 숫자의 추가 없이 sequence의 수열을 만들 수 있는지 없는지 여부를 결정해야 하므로
같다면 stack에 들어온 값과 sequence[s + @]의 값을 비교하여 pop + "-"를 추가할 수 있지만, 같지 않다면 더이상 수열을 이루는 것이 불가능한 것이다.
5. stack이 비어있거나, sequence의 인덱스 값이 N보다 크거나 같은 경우 반복문을 종료한다.
6. flag 값을 통해 push,pop 정보를 담고있는 sb의 값을 출력한다.
 */
public class BOJ1874 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static Stack<Integer> stack;
    static int[] sequence;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        int s = 0,d = 1;
        boolean flag = true;

        while(true) {
            if(!flag || s >= N) break;
            while(d <= sequence[s]) {
                stack.push(d++);
                sb.append("+\n");
            }

            if(!stack.isEmpty() && (int) stack.peek() == sequence[s++]) {
                stack.pop();
                sb.append("-\n");
            } else {
                flag = false;
            }
        }

        if(flag) System.out.println(sb.toString());
        else System.out.println("NO");
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        sb = new StringBuilder();

        sequence = new int[N];

        for(int i = 0; i < N; i++) sequence[i] = Integer.parseInt(br.readLine());

        stack = new Stack<>();
    }
}
