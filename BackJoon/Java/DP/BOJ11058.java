package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
크리보드

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	7421	3317	2648	43.885%
문제
크리보드는 kriii가 만든 신기한 키보드이다. 크리보드에는 버튼이 4개만 있으며, 하는 역할은 다음과 같다.

화면에 A를 출력한다.
Ctrl-A: 화면을 전체 선택한다
Ctrl-C: 전체 선택한 내용을 버퍼에 복사한다
Ctrl-V: 버퍼가 비어있지 않은 경우에는 화면에 출력된 문자열의 바로 뒤에 버퍼의 내용을 붙여넣는다.
크리보드의 버튼을 총 N번 눌러서 화면에 출력된 A개수를 최대로하는 프로그램을 작성하시오.

입력
첫째 줄에 N(1 ≤ N ≤ 100)이 주어진다.

출력
크리보드의 버튼을 총 N번 눌러서 화면에 출력할 수 있는 A 개수의 최댓값을 출력한다.

예제 입력 1
3
예제 출력 1
3
예제 입력 2
7
예제 출력 2
9
예제 입력 3
11
예제 출력 3
27
힌트
N = 3인 경우에 A, A, A를 눌러 A 3개를 출력할 수 있다.

N = 7인 경우에는 A, A, A, Ctrl-A, Ctrl-C, Ctrl-V, Ctrl-V를 눌러 9개를 출력할 수 있다.

N = 11인 경우에는 A, A, A, Ctrl-A, Ctrl-C, Ctrl-V, Ctrl-V, Ctrl-A, Ctrl-C, Ctrl-V, Ctrl-V 를 눌러 27개를 출력할 수 있다.

출처
문제를 만든 사람: baekjoon
알고리즘 분류
다이나믹 프로그래밍
 */
/*
제공된 TC 외에 다른 TC를 적용하여 점화식을 세울 수 있었다.
처음 세운 점화식은 3번의 기회를 소모하여 (화면 전체 선택) - (복사) - (붙여넣기)를 통해 2배를 적용시킬 수 있었는데 기회 한번을 더 소모하여
3배를 적용 시킬 수 있었고, 기회가 n일 때, n-1배를 적용 시킬 수 있는데,
mem[1] = 1
mem[2] = 2
mem[3] = 3
mem[4] = 4
mem[5] = 5

현재 문자열 A라고 가정할 때,
3번의 기회가 2배이므로 AA, 4번의 기회는 A를 복사하여 AA를 만들고 다시한번 붙여넣기를 적용하여 AAA, 5번의 기회는 A를 복사하여 4번의 붙여넣기를 적용하면
AAAA를 만들 수 있다.
6번의 기회부터는 3번 기회 과정을 2번 적용하면 되므로 A -> AA -> AAAA로 4배가 적용되기 때문에 최대 A의 개수는 6회의 기회와 5회의 기회가 같으므로 5회까지 최대 갯수를 만족한다.
따라서, mem[n] = max(mem[n-3] * 2, mem[n-4] * 3, mem[n-5] * 4, mem[n-1] + 1)
예시, mem[6] = max(mem[5] + 1, mem[3] * 2, mem[2] * 3, mem[1] * 4)
 */
public class BOJ11058 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static long[] memorization;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int i=6;i<=N;i++) {
            memorization[i] = Math.max(
                                Math.max(memorization[i-1] + 1, memorization[i-3] * 2),
                                Math.max(memorization[i-4] * 3, memorization[i-5] * 4));
        }
        System.out.println(memorization[N]);
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        memorization = new long[N+1];

        for(int i=0;i<N+1;i++) {
            if(0 < i && i < 6) {
                memorization[i] = i;
            } else {
                memorization[i] = 0;
            }
        }
    }
}
