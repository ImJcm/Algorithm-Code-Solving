package BackJoon;

import java.util.Arrays;
import java.util.Scanner;

/*
연산자 끼워넣기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	85205	44506	28331	49.432%
문제
N개의 수로 이루어진 수열 A1, A2, ..., AN이 주어진다. 또, 수와 수 사이에 끼워넣을 수 있는 N-1개의 연산자가 주어진다. 연산자는 덧셈(+), 뺄셈(-), 곱셈(×), 나눗셈(÷)으로만 이루어져 있다.

우리는 수와 수 사이에 연산자를 하나씩 넣어서, 수식을 하나 만들 수 있다. 이때, 주어진 수의 순서를 바꾸면 안 된다.

예를 들어, 6개의 수로 이루어진 수열이 1, 2, 3, 4, 5, 6이고, 주어진 연산자가 덧셈(+) 2개, 뺄셈(-) 1개, 곱셈(×) 1개, 나눗셈(÷) 1개인 경우에는 총 60가지의 식을 만들 수 있다. 예를 들어, 아래와 같은 식을 만들 수 있다.

1+2+3-4×5÷6
1÷2+3+4-5×6
1+2÷3×4-5+6
1÷2×3-4+5+6
식의 계산은 연산자 우선 순위를 무시하고 앞에서부터 진행해야 한다. 또, 나눗셈은 정수 나눗셈으로 몫만 취한다. 음수를 양수로 나눌 때는 C++14의 기준을 따른다. 즉, 양수로 바꾼 뒤 몫을 취하고, 그 몫을 음수로 바꾼 것과 같다. 이에 따라서, 위의 식 4개의 결과를 계산해보면 아래와 같다.

1+2+3-4×5÷6 = 1
1÷2+3+4-5×6 = 12
1+2÷3×4-5+6 = 5
1÷2×3-4+5+6 = 7
N개의 수와 N-1개의 연산자가 주어졌을 때, 만들 수 있는 식의 결과가 최대인 것과 최소인 것을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 수의 개수 N(2 ≤ N ≤ 11)가 주어진다. 둘째 줄에는 A1, A2, ..., AN이 주어진다. (1 ≤ Ai ≤ 100) 셋째 줄에는 합이 N-1인 4개의 정수가 주어지는데, 차례대로 덧셈(+)의 개수, 뺄셈(-)의 개수, 곱셈(×)의 개수, 나눗셈(÷)의 개수이다.

출력
첫째 줄에 만들 수 있는 식의 결과의 최댓값을, 둘째 줄에는 최솟값을 출력한다. 연산자를 어떻게 끼워넣어도 항상 -10억보다 크거나 같고, 10억보다 작거나 같은 결과가 나오는 입력만 주어진다. 또한, 앞에서부터 계산했을 때, 중간에 계산되는 식의 결과도 항상 -10억보다 크거나 같고, 10억보다 작거나 같다.

예제 입력 1
2
5 6
0 0 1 0
예제 출력 1
30
30
예제 입력 2
3
3 4 5
1 0 1 0
예제 출력 2
35
17
예제 입력 3
6
1 2 3 4 5 6
2 1 1 1
예제 출력 3
54
-24
 */
/*
    1. 연산 우선 순위를 무시한다.
    2. 주어진 숫자의 순서를 바꾸지 않는다
    다음 조건을 만족하기 때문에 연산자의 순서만 바꾸면서 연산결과의 최댓값과 최솟값을 찾는다.
    백트랙킹을 활용하여 연산자를 사용한 횟수가 N-1이 되면 기저사례로 계산된 ret값으로 MAX_V, MIN_V에 조건에 맞게 저장
    백트랙킹 과정은 연산자 수 4개의 종류를 반복하여 입력된 연산자를 사용할 때마다 -1을 적용하여 0이 되지 않을 때까지 사용하면서
    연산 결과에 적용한다. 기저사례에 도달하면 되돌아와 해당 사용된 연산자의 횟수를 +1 해주고 다른 숫자들과의 연산을 진행할 수 있도록
    한다.
 */
public class BOJ14888 {
    static int N,SIZE,MAX_V = Integer.MIN_VALUE, MIN_V = Integer.MAX_VALUE;
    static int[] number, operation;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = Integer.parseInt(sc.nextLine());

        //number = new int[N];
        number = Arrays.stream(sc.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        operation = Arrays.stream(sc.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        SIZE = Arrays.stream(operation).sum();

        calculate(0,number[0]);

        System.out.println(MAX_V);
        System.out.println(MIN_V);
    }

    static void calculate(int op_cnt, int ret) {
        if(op_cnt == N-1) {
            //MIN
            if(ret < MIN_V) {
                MIN_V = ret;
            }

            //MAX
            if(ret > MAX_V) {
                MAX_V = ret;
            }
            return;
        }

        for(int i=0;i<4;i++) {
            if(operation[i] > 0) {
                operation[i]--;
                switch (i) {
                    case 0:
                        //덧셈
                        calculate(op_cnt+1,ret+number[op_cnt+1]);
                        break;
                    case 1:
                        //뺄셈
                        calculate(op_cnt+1,ret-number[op_cnt+1]);
                        break;
                    case 2:
                        calculate(op_cnt+1,ret*number[op_cnt+1]);
                        break;
                        //곱셈
                    case 3:
                        //나눗셈
                        if (ret >= 0) {
                            calculate(op_cnt+1,ret/number[op_cnt+1]);
                        } else {
                            calculate(op_cnt+1,-1*((ret*-1)/number[op_cnt+1]));
                        }
                        break;
                    default:
                        //들어올 수 없는 분기
                        break;
                }
                operation[i]++;
            }
        }
    }
}
