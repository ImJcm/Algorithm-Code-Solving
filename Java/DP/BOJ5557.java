package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
1학년 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	20254	9389	7347	46.415%
문제
상근이가 1학년 때, 덧셈, 뺄셈을 매우 좋아했다. 상근이는 숫자가 줄 지어있는 것을 보기만 하면, 마지막 두 숫자 사이에 '='을 넣고, 나머지 숫자 사이에는 '+' 또는 '-'를 넣어 등식을 만들며 놀고 있다. 예를 들어, "8 3 2 4 8 7 2 4 0 8 8"에서 등식 "8+3-2-4+8-7-2-4-0+8=8"을 만들 수 있다.

상근이는 올바른 등식을 만들려고 한다. 상근이는 아직 학교에서 음수를 배우지 않았고, 20을 넘는 수는 모른다. 따라서, 왼쪽부터 계산할 때, 중간에 나오는 수가 모두 0 이상 20 이하이어야 한다. 예를 들어, "8+3+2-4-8-7+2+4+0+8=8"은 올바른 등식이지만, 8+3+2-4-8-7이 음수이기 때문에, 상근이가 만들 수 없는 등식이다.

숫자가 주어졌을 때, 상근이가 만들 수 있는 올바른 등식의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 숫자의 개수 N이 주어진다. (3 ≤ N ≤ 100) 둘째 줄에는 0 이상 9 이하의 정수 N개가 공백으로 구분해 주어진다.

출력
첫째 줄에 상근이가 만들 수 있는 올바른 등식의 개수를 출력한다. 이 값은 263-1 이하이다.

예제 입력 1
11
8 3 2 4 8 7 2 4 0 8 8
예제 출력 1
10
예제 입력 2
40
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1
예제 출력 2
7069052760
힌트
예제 1의 경우 다음과 같이 10가지 방법이 있다.

8+3-2-4+8-7-2-4-0+8=8
8+3-2-4+8-7-2-4+0+8=8
8+3+2+4-8-7+2-4-0+8=8
8+3+2+4-8-7+2-4+0+8=8
8+3+2-4+8-7+2+4-0-8=8
8+3+2-4+8-7+2+4+0-8=8
8-3+2+4-8+7+2+4-0-8=8
8-3+2+4-8+7+2+4+0-8=8
8-3+2-4+8+7+2-4-0-8=8
8-3+2-4+8+7+2-4+0-8=8
출처


Olympiad > Japanese Olympiad in Informatics > Japanese Olympiad in Informatics Qualification Round > JOI 2010/2011 예선 4번

문제를 번역한 사람: baekjoon
데이터를 추가한 사람: eovh1962
어색한 표현을 찾은 사람: mwy3055
문제의 오타를 찾은 사람: rim
알고리즘 분류
다이나믹 프로그래밍
 */
/*
처음 접근 방법으로 dfs를 이용하여 모든 경우의 수를 검사한 방법으로 코드를 작성하고 이를 응요하여 메모리제이션을 활용할 수 있는 부분을
찾으려고 하였으나 아이디어가 떠오르지 않았다.

그래서, 정답 코드를 참고하였다.
정답 아이디어
0. n * 20 인 2차원 배열을 메모리제이션으로 사용 + 모든 배열의 값을 0으로 저장
1. 첫번째 숫자를 시작으로 첫번째 숫자가 가능한 경우의 수는 1
2. 첫번째 숫자를 기준으로 두번째 숫자와 +,-를 적용하여 경우의 수를 업데이트한다.
3. ...
4. n-2번째 숫자까지 위의 과정을 수행한다.
5. 결과값은 memorization[n-2][numbers[n-1]];

0 [0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
1 [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0]
2 [0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0]
3 [0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0]
4 [0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0]
5 [0, 0, 1, 0, 1, 0, 1, 0, 2, 0, 2, 0, 2, 0, 0, 0, 1, 0, 1, 0, 1]
6 [1, 0, 1, 0, 2, 0, 3, 0, 3, 0, 4, 0, 2, 0, 3, 0, 1, 0, 2, 0, 1]
7 [2, 0, 3, 0, 4, 0, 5, 0, 4, 0, 6, 0, 4, 0, 6, 0, 3, 0, 3, 0, 1]
8 [4, 0, 6, 0, 8, 0, 10, 0, 8, 0, 12, 0, 8, 0, 12, 0, 6, 0, 6, 0, 2]
9 [8, 0, 12, 0, 8, 0, 12, 0, 10, 0, 12, 0, 10, 0, 10, 0, 8, 0, 12, 0, 8]
10 [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
 */
public class BOJ5557 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static long usecase = 0;     //dfs_case variable;
    static int[] numbers;
    static long[][] memorization;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        /*
        String temp = "";
        dfs_case(0,numbers[0],temp);
        System.out.println(usecase);
         */

        dp();
    }

    static void dp() {
        int plus = 0,minus = 0;
        memorization[0][numbers[0]] = 1;

        for(int i=1;i<N-1;i++) {
            for(int j=0;j<=20;j++) {
                if(memorization[i-1][j] != 0) {
                    plus = j + numbers[i];
                    minus = j - numbers[i];
                    if(plus >= 0 && plus <= 20) {
                        memorization[i][plus] += memorization[i-1][j];
                    }

                    if(minus >= 0 && minus <= 20) {
                        memorization[i][minus] += memorization[i-1][j];
                    }
                }
            }
        }

        System.out.println(memorization[N-2][numbers[N-1]]);
    }

    /*
        dfs + 사용한 사칙연산 기호 표시
        O(2^n) -> 최대 2^98
     */
    static void dfs_case(int depth, int sum, String t) {
        if(depth == N-2) {
            if(sum == numbers[N-1]) {
                System.out.println(t);
                usecase++;
            }
            return;
        }

        int next_plus_num = sum + numbers[depth+1];
        int next_minus_num = sum - numbers[depth+1];
        if(next_plus_num <= 20 && next_plus_num >= 0) {
            t += "+";
            dfs_case(depth + 1, next_plus_num,t);
            t = t.substring(0,depth);
        }

        if(next_minus_num <= 20 && next_minus_num >= 0) {
            t += "-";
            dfs_case(depth + 1, next_minus_num,t);
            t = t.substring(0,depth);
        }
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());
        numbers = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        memorization = new long[N][21];
        for(int i=0;i<N;i++) {
            Arrays.fill(memorization[i],0);
        }
    }
}
