package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
연산자 끼워넣기 (2)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	7790	4017	3306	52.186%
문제
N개의 수로 이루어진 수열 A1, A2, ..., AN이 주어진다. 또, 수와 수 사이에 끼워넣을 수 있는 연산자가 주어진다. 연산자는 덧셈(+), 뺄셈(-), 곱셈(×), 나눗셈(÷)으로만 이루어져 있다. 연산자의 개수는 N-1보다 많을 수도 있다. 모든 수의 사이에는 연산자를 한 개 끼워넣어야 하며, 주어진 연산자를 모두 사용하지 않고 모든 수의 사이에 연산자를 끼워넣을 수도 있다.

우리는 수와 수 사이에 연산자를 하나씩 넣어서, 수식을 하나 만들 수 있다. 이때, 주어진 수의 순서를 바꾸면 안 된다.

예를 들어, 6개의 수로 이루어진 수열이 1, 2, 3, 4, 5, 6이고, 주어진 연산자가 덧셈(+) 3개, 뺄셈(-) 2개, 곱셈(×) 1개, 나눗셈(÷) 1개인 경우에는 총 250가지의 식을 만들 수 있다. 예를 들어, 아래와 같은 식을 만들 수 있다.

1+2+3-4×5÷6
1÷2+3+4-5×6
1+2÷3×4-5+6
1÷2×3-4+5+6
1+2+3+4-5-6
1+2+3-4-5×6
식의 계산은 연산자 우선 순위를 무시하고 앞에서부터 진행해야 한다. 또, 나눗셈은 정수 나눗셈으로 몫만 취한다. 음수를 양수로 나눌 때는 C++14의 기준을 따른다. 즉, 양수로 바꾼 뒤 몫을 취하고, 그 몫을 음수로 바꾼 것과 같다. 이에 따라서, 위의 식 4개의 결과를 계산해보면 아래와 같다.

1+2+3-4×5÷6 = 1
1÷2+3+4-5×6 = 12
1+2÷3×4-5+6 = 5
1÷2×3-4+5+6 = 7
1+2+3+4-5-6 = -1
1+2+3-4-5×6 = -18
N개의 수와 연산자가 주어졌을 때, 만들 수 있는 식의 결과가 최대인 것과 최소인 것을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 수의 개수 N(2 ≤ N ≤ 11)가 주어진다. 둘째 줄에는 A1, A2, ..., AN이 주어진다. (1 ≤ Ai ≤ 100) 셋째 줄에는 합이 N-1보다 크거나 같고, 4N보다 작거나 같은 4개의 정수가 주어지는데, 차례대로 덧셈(+)의 개수, 뺄셈(-)의 개수, 곱셈(×)의 개수, 나눗셈(÷)의 개수이다.

출력
첫째 줄에 만들 수 있는 식의 결과의 최댓값을, 둘째 줄에는 최솟값을 출력한다. 연산자를 어떻게 끼워넣어도 항상 -10억보다 크거나 같고, 10억보다 작거나 같은 결과가 나오는 입력만 주어진다. 또한, 앞에서부터 계산했을 때, 중간에 계산되는 식의 결과도 항상 -10억보다 크거나 같고, 10억보다 작거나 같다.

예제 입력 1
2
5 6
1 1 1 1
예제 출력 1
30
-1
예제 입력 2
3
3 4 5
2 1 2 1
예제 출력 2
60
-5
예제 입력 3
6
1 2 3 4 5 6
3 2 1 1
예제 출력 3
72
-48
 */
/*
    사용되는 연산자의 갯수는 N-1개이고, 주어지는 연산자의 수는 N-1 ~ 4N이기 때문에 경우의 수가 많아졌다.
    백트랙킹을 사용, 기저사례 : 피연산자의 모든 사용이 만족할 때 / 재귀 호출 : 연산자의 사용이 가능할 때 해당 연산자를 사용하여
    다음 피연산자와 연산 적용 후, 재귀호출 / 해당 재귀가 끝난 뒤, 사용한 피연산자 기록을 되돌린다.
 */
public class BOJ15658 {
    static int N,Max_value = Integer.MIN_VALUE, Min_value = Integer.MAX_VALUE;
    static int[] numbers;
    static int[] operate;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        //numbers = new int[N];
        numbers = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        operate = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        calculate(1,numbers[0]);

        System.out.println(Max_value + "\n" + Min_value);
    }

    static void calculate(int d,int result) {
        if(d == N) {
            if(result > Max_value) {
                Max_value = result;
            }
            if(result < Min_value) {
                Min_value = result;
            }
            return;
        }

        for(int i=0;i<4;i++) {
            if(operate[i] > 0) {
                operate[i]--;
                switch (i) {
                    case 0:
                        calculate(d+1, result+numbers[d]);
                        break;
                    case 1:
                        calculate(d+1, result-numbers[d]);
                        break;
                    case 2:
                        calculate(d+1, result*numbers[d]);
                        break;
                    case 3:
                        //음수의 나눗셈의 경우 자바에서는 c++14기준으로 수행하는 것같다.
                        calculate(d+1, result/numbers[d]);
                        /*if(result < 0) {
                            calculate(d+1, -1 * ((-1 * result)/numbers[d]));
                        } else {
                            calculate(d+1, result/numbers[d]);
                        }*/
                        break;
                    default:
                        break;
                }
                operate[i]++;
            }
        }
    }
}
