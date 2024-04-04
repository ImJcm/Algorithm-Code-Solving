package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
4연산

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	8837	2815	1984	29.271%
문제
정수 s가 주어진다. 정수 s의 값을 t로 바꾸는 최소 연산 횟수를 구하는 프로그램을 작성하시오.

사용할 수 있는 연산은 아래와 같다.

s = s + s; (출력: +)
s = s - s; (출력: -)
s = s * s; (출력: *)
s = s / s; (출력: /) (s가 0이 아닐때만 사용 가능)
입력
첫째 줄에 s와 t가 주어진다. (1 ≤ s, t ≤ 109)

출력
첫째 줄에 정수 s를 t로 바꾸는 방법을 출력한다. s와 t가 같은 경우에는 0을, 바꿀 수 없는 경우에는 -1을 출력한다. 가능한 방법이 여러 가지라면, 사전 순으로 앞서는 것을 출력한다.

연산의 아스키 코드 순서는 '*', '+', '-', '/' 이다.

예제 입력 1
7 392
예제 출력 1
+*+
예제 입력 2
7 256
예제 출력 2
/+***
예제 입력 3
4 256
예제 출력 3
**
예제 입력 4
7 7
예제 출력 4
0
예제 입력 5
7 9
예제 출력 5
-1
예제 입력 6
10 1
예제 출력 6
/
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
 */
/*
메모리 초과가 발생하였다. 예상해보면 발생 위치는 queue에 과도하게 데이터가 들어가서 생기는 문제같다. 이를 해결하기 위해서는
queue에 들어갈 데이터에 조건을 다음과 같이 추가하면 될 것이라 생각한다.
1. now.number == t와 같을 때, result_ops를 결정하는 조건을 queue 데이터 저장 시 조건으로 사용한다.
2. 1의 방법으로 수행 시, 최소 길이 + 사전 순으로 앞서는 것을 최초 도달 시 만족하기 때문이다.

제출 부터 메모리초과가 발생한 이유 : t의 상한이 10^9이기 때문에 numbers = new BOJ14395_number[t+1];로 초기화하려고 했기때문에
발생한 것

또한, 이미 처음 방문한 시점에서 해당 number에서의 연산은 가장 최소한의 연산 횟수와 연산 순서를 *,+,-,/를 수행하여 우선순위가 높은
연산식을 보장한다.
 */
public class BOJ14395 {
    static class BOJ14395_operation {
        int number;
        String ops = "";

        BOJ14395_operation(int num, String op) {
            this.number = num;
            this.ops += op;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int s,t;
    static String result_ops;
    static HashSet<Integer> visited;
    static BOJ14395_operation[] numbers;
    static char[] operations = {'*','+','-','/'};

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        int result = bfs();
        System.out.println(result == -1 ? -1 : result == 0 ? 0 : result_ops);
    }

    static int bfs() {
        Queue<BOJ14395_operation> q = new LinkedList<>();

        q.offer(new BOJ14395_operation(s,""));
        visited.add(s);

        while(!q.isEmpty()) {
            BOJ14395_operation now = q.poll();

            if(now.number == t) {
                result_ops = now.ops;
                return result_ops.length();
            }

            for(char op : operations) {
                int result = 0;
                if(op == '*') {
                    result = (int) Math.pow(now.number,2);    // now.number * now.number;
                } else if(op == '+') {
                    result = now.number * 2;                  // now.number + now.number;
                } else if(op == '-') {
                    result = 0;                               // now.number - now.number;
                } else if(op == '/' && now.number != 0) {
                    result = 1;                               // now.number / now.number;
                }

                if(result > t || visited.contains(result)) continue;
                visited.add(result);
                q.offer(new BOJ14395_operation(result, now.ops + op));
            }

        }
        return -1;
    }

    static int bfs_memory_over_2() {
        Queue<BOJ14395_operation> q = new LinkedList<>();

        q.offer(new BOJ14395_operation(s,""));

        while(!q.isEmpty()) {
            BOJ14395_operation now = q.poll();

            if(now.number == t) {
                result_ops = now.ops;
                return result_ops.length();
            }

            for(char op : operations) {
                int result = 0;
                if(op == '*') {
                    result = (int) Math.pow(now.number,2);    // now.number * now.number;
                } else if(op == '+') {
                    result = now.number * 2;                  // now.number + now.number;
                } else if(op == '-') {
                    result = 0;                               // now.number - now.number;
                } else if(op == '/' && now.number != 0) {
                    result = 1;                               // now.number / now.number;
                }

                if(result > t || (!numbers[result].ops.isEmpty() && numbers[result].ops.length() < now.ops.length() + 1)) continue;
                /*
                // result에서의 연산식의 길이가 같은 경우 + 이미 queue에 포함된 경우, 우선순위가 높은 연산식으로 교체하고 continue;
                // 결과 : 메모리 초과
                if(numbers[result].ops.length() == now.ops.length() + 1) {
                    if(q.contains(numbers[result])) {
                        numbers[result].ops = numbers[result].ops.compareTo(now.ops) > 0 ? numbers[result].ops : now.ops;
                        continue;
                    }
                }*/
                numbers[result].ops = now.ops + op;
                q.offer(new BOJ14395_operation(result, now.ops + op));
            }

        }
        return -1;
    }

    static int bfs_memory_over() {
        Queue<BOJ14395_operation> q = new LinkedList<>();

        q.offer(new BOJ14395_operation(s,""));

        while(!q.isEmpty()) {
            BOJ14395_operation now = q.poll();

            if(now.number == t) {
                result_ops = result_ops.isEmpty() ? now.ops : now.ops.length() == result_ops.length() ?
                        now.ops.compareTo(result_ops) >= 0 ? now.ops : result_ops
                        : now.ops.length() > result_ops.length() ? result_ops : now.ops;
                return result_ops.length();
            }

            for(char op : operations) {
                int result = 0;
                String cur_ops = now.ops;
                if(op == '*') {
                    result = (int) Math.pow(now.number,2);    // now.number * now.number;
                } else if(op == '+') {
                    result = now.number * 2;                  // now.number + now.number;
                } else if(op == '-') {
                    result = 0;                               // now.number - now.number;
                } else if(op == '/' && now.number != 0) {
                    result = 1;                               // now.number / now.number;
                }

                if(result > t || (!numbers[result].ops.isEmpty() && numbers[result].ops.length() < cur_ops.length() + 1)) continue;
                numbers[result].ops = cur_ops + op;
                q.offer(new BOJ14395_operation(result, cur_ops + op));
            }

        }
        return -1;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        s = Integer.parseInt(input[0]);
        t = Integer.parseInt(input[1]);

        //numbers = new BOJ14395_operation[t+1];
        visited = new HashSet<>();
        result_ops = "";

        /*for(int i=0;i<=t;i++) {
            numbers[i] = new BOJ14395_operation(i,"");
        }*/
    }
}
