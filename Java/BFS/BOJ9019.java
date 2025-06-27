package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
DSLR 스페셜 저지다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
6 초	256 MB	98127	24403	16092	21.187%
문제
네 개의 명령어 D, S, L, R 을 이용하는 간단한 계산기가 있다. 이 계산기에는 레지스터가 하나 있는데, 이 레지스터에는 0 이상 10,000 미만의 십진수를 저장할 수 있다. 각 명령어는 이 레지스터에 저장된 n을 다음과 같이 변환한다. n의 네 자릿수를 d1, d2, d3, d4라고 하자(즉 n = ((d1 × 10 + d2) × 10 + d3) × 10 + d4라고 하자)

D: D 는 n을 두 배로 바꾼다. 결과 값이 9999 보다 큰 경우에는 10000 으로 나눈 나머지를 취한다. 그 결과 값(2n mod 10000)을 레지스터에 저장한다.
S: S 는 n에서 1 을 뺀 결과 n-1을 레지스터에 저장한다. n이 0 이라면 9999 가 대신 레지스터에 저장된다.
L: L 은 n의 각 자릿수를 왼편으로 회전시켜 그 결과를 레지스터에 저장한다. 이 연산이 끝나면 레지스터에 저장된 네 자릿수는 왼편부터 d2, d3, d4, d1이 된다.
R: R 은 n의 각 자릿수를 오른편으로 회전시켜 그 결과를 레지스터에 저장한다. 이 연산이 끝나면 레지스터에 저장된 네 자릿수는 왼편부터 d4, d1, d2, d3이 된다.
위에서 언급한 것처럼, L 과 R 명령어는 십진 자릿수를 가정하고 연산을 수행한다. 예를 들어서 n = 1234 라면 여기에 L 을 적용하면 2341 이 되고 R 을 적용하면 4123 이 된다.

여러분이 작성할 프로그램은 주어진 서로 다른 두 정수 A와 B(A ≠ B)에 대하여 A를 B로 바꾸는 최소한의 명령어를 생성하는 프로그램이다. 예를 들어서 A = 1234, B = 3412 라면 다음과 같이 두 개의 명령어를 적용하면 A를 B로 변환할 수 있다.

1234 →L 2341 →L 3412
1234 →R 4123 →R 3412

따라서 여러분의 프로그램은 이 경우에 LL 이나 RR 을 출력해야 한다.

n의 자릿수로 0 이 포함된 경우에 주의해야 한다. 예를 들어서 1000 에 L 을 적용하면 0001 이 되므로 결과는 1 이 된다. 그러나 R 을 적용하면 0100 이 되므로 결과는 100 이 된다.

입력
프로그램 입력은 T 개의 테스트 케이스로 구성된다. 테스트 케이스 개수 T 는 입력의 첫 줄에 주어진다. 각 테스트 케이스로는 두 개의 정수 A와 B(A ≠ B)가 공백으로 분리되어 차례로 주어지는데 A는 레지스터의 초기 값을 나타내고 B는 최종 값을 나타낸다. A 와 B는 모두 0 이상 10,000 미만이다.

출력
A에서 B로 변환하기 위해 필요한 최소한의 명령어 나열을 출력한다. 가능한 명령어 나열이 여러가지면, 아무거나 출력한다.

예제 입력 1
3
1234 3412
1000 1
1 16
예제 출력 1
LL
L
DDDD
출처
ICPC > Regionals > Asia Pacific > Korea > Nationwide Internet Competition > Daejeon Nationalwide Internet Competition 2011 D번

문제를 번역한 사람: baekjoon
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
역추적
 */
/*
알고리즘 핵심
BFS
1. BFS에 사용하기 위한 구조체로 변환되는 숫자와 현재까지의 명령어를 갖는다.
2. DSLR 과정을 각 숫자마다 수행하여 BFS 탐색을 수행한다. - DSLR_change_format()

처음 프로토타입 코드는 숫자가 1, 99와 같이 4자리 수를 만족하지 않는 경우 LR 과정에서 변환이 용이하기 위해 String 타입으로 사용하였으나,
값을 변환하는 과정 + 이미 만들어진 수를 비교하는 과정에서 String <-> Integer 간의 형변환이 많아지는 코드가 작성이 되었고 이는 시간 초과 결과를 발생시켰다.
Integer 타입으로 단순히 사칙연산을 이용하여 LR 로테이션을 수행할 수 있었기 때문에 해당 방법으로 시간 초과를 해결하였다.
 */
public class BOJ9019 {
    static class BOJ9019_operation_2 {
        int number;
        String command;

        BOJ9019_operation_2(int number, String command) {
            this.number = number;
            this.command = command;
        }
    }

    static class BOJ9019_operation {
        String number;
        String command;

        BOJ9019_operation(String number, String command) {
            this.number = number;
            this.command = command;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T,A,B;
    static boolean[] visited;
    static StringBuilder ans;
    static char[] DSLR = {'D','S','L','R'};

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());
        ans = new StringBuilder();

        while(T-- > 0) {
            init_setting();
            solve();
        }

        System.out.println(ans.toString());
    }

    private static void solve() {
        calculate_DSLR(new BOJ9019_operation_2(A, new String("")));
        //calculate_DSLR_timeOut(new BOJ9019_operation(Integer.toString(A), new String("")));
    }

    /*
        Success Solve : DSLR 변환 값을 String -> Integer만을 사용하여 성공
     */
    private static void calculate_DSLR(BOJ9019_operation_2 op) {
        Queue<BOJ9019_operation_2> q = new LinkedList<>();

        q.add(op);
        visited[op.number] = true;

        while(!q.isEmpty()) {
            BOJ9019_operation_2 now = q.poll();

            if(now.number == B) {
                ans.append(now.command).append("\n");
                return;
            }

            for(int i = 0; i < 4; i++) {
                int n_number = DSLR_change_format(now.number,i);

                if(visited[n_number]) continue;

                visited[n_number] = true;
                q.add(new BOJ9019_operation_2(n_number,now.command + DSLR[i]));
            }
        }
    }

    private static int DSLR_change_format(int original, int type) {
        int result = original;

        switch (type) {
            case 0: // D
                result = (result * 2) % 10000;
                break;
            case 1: // S
                result = result == 0 ? 9999 : result - 1;
                break;
            case 2: // L
                int d1 = result / 1000;
                int d234 = result % 1000;
                result = d234 * 10 + d1;
                break;
            case 3: // R
                int d4 = result % 10;
                int d123 = result / 10;
                result = d4 * 1000 + d123;
                break;
        }
        return result;
    }

    /*
        시간 초과 : String <-> Integer 간의 형변환 과정으로인한 시간 초과가 발생한다고 생각한다.
     */
    private static void calculate_DSLR_timeOut(BOJ9019_operation op) {
        Queue<BOJ9019_operation> q = new LinkedList<>();

        q.add(op);
        visited[Integer.parseInt(op.number)] = true;

        while(!q.isEmpty()) {
            BOJ9019_operation now = q.poll();

            String str_num = now.number;
            int str_to_int_num = Integer.parseInt(str_num);
            String now_command = now.command;

            if(str_to_int_num == B) {
                ans.append(now_command).append("\n");
                return;
            }

            for(int i = 0; i < 4; i++) {
                str_num = now.number.length() != 4 ?
                        "0".repeat(4 - now.number.length()) + now.number : now.number;
                str_to_int_num = Integer.parseInt(str_num);
                now_command = now.command;

                switch (i) {
                    case 0: // D
                        str_to_int_num = str_to_int_num * 2 >= 10000 ?
                                (str_to_int_num * 2) % 10000 : str_to_int_num * 2;
                        now_command += "D";
                        break;
                    case 1: // S
                        str_to_int_num = str_to_int_num - 1 < 0 ?
                                9999 : str_to_int_num - 1;
                        now_command += "S";
                        break;
                    case 2: // L
                        str_num = str_num.substring(1) + str_num.charAt(0);
                        str_to_int_num = Integer.parseInt(str_num);
                        now_command += "L";
                        break;
                    case 3: // R
                        str_num = str_num.charAt(3) + str_num.substring(0,3);
                        str_to_int_num = Integer.parseInt(str_num);
                        now_command += "R";
                        break;
                }

                if(visited[str_to_int_num]) continue;
                str_num = Integer.toString(str_to_int_num);
                q.add(new BOJ9019_operation(str_num,now_command));
            }
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        A = Integer.parseInt(input[0]);
        B = Integer.parseInt(input[1]);

        visited = new boolean[10000];
    }
}
