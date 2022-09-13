/*
카드 구매하기 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	7496	5637	4593	75.942%
문제
요즘 민규네 동네에서는 스타트링크에서 만든 PS카드를 모으는 것이 유행이다.

PS카드는 PS(Problem Solving)분야에서 유명한 사람들의 아이디와 얼굴이 적혀있는 카드이다. 각각의 카드에는 등급을 나타내는 색이 칠해져 있고, 다음과 같이 8가지가 있다.

전설카드
레드카드
오렌지카드
퍼플카드
블루카드
청록카드
그린카드
그레이카드
카드는 카드팩의 형태로만 구매할 수 있고, 카드팩의 종류는 카드 1개가 포함된 카드팩, 카드 2개가 포함된 카드팩, ... 카드 N개가 포함된 카드팩과 같이 총 N가지가 존재한다.

민규는 지난주에 너무 많은 돈을 써 버렸다. 그래서 오늘은 돈을 최소로 지불해서 카드 N개를 구매하려고 한다. 카드가 i개 포함된 카드팩의 가격은 Pi원이다.

예를 들어, 카드팩이 총 4가지 종류가 있고, P1 = 1, P2 = 5, P3 = 6, P4 = 7인 경우에 민규가 카드 4개를 갖기 위해 지불해야 하는 금액의 최솟값은 4원이다. 1개 들어있는 카드팩을 4번 사면 된다.

P1 = 5, P2 = 2, P3 = 8, P4 = 10인 경우에는 카드가 2개 들어있는 카드팩을 2번 사면 4원이고, 이 경우가 민규가 지불해야 하는 금액의 최솟값이다.

카드 팩의 가격이 주어졌을 때, N개의 카드를 구매하기 위해 민규가 지불해야 하는 금액의 최솟값을 구하는 프로그램을 작성하시오. N개보다 많은 개수의 카드를 산 다음, 나머지 카드를 버려서 N개를 만드는 것은 불가능하다. 즉, 구매한 카드팩에 포함되어 있는 카드 개수의 합은 N과 같아야 한다.

입력
첫째 줄에 민규가 구매하려고 하는 카드의 개수 N이 주어진다. (1 ≤ N ≤ 1,000)

둘째 줄에는 Pi가 P1부터 PN까지 순서대로 주어진다. (1 ≤ Pi ≤ 10,000)

출력
첫째 줄에 민규가 카드 N개를 갖기 위해 지불해야 하는 금액의 최솟값을 출력한다.

예제 입력 1
4
1 5 6 7
예제 출력 1
4
예제 입력 2
5
10 9 8 7 6
예제 출력 2
6
예제 입력 3
10
1 1 2 3 5 8 13 21 34 55
예제 출력 3
5
예제 입력 4
10
5 10 11 12 13 30 35 40 45 47
예제 출력 4
26
예제 입력 5
4
5 2 8 10
예제 출력 5
4
예제 입력 6
4
3 5 15 16
예제 출력 6
10
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class BOJ16194 {
    static int N;
    static int[] mem;
    static int[] P;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        P = Arrays.asList(br.readLine().split(" "))
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        mem = new int[N+1];
        Arrays.fill(mem, Integer.MAX_VALUE);

        System.out.println(dp(N));
    }

    static int dp(int n) {
        if(n == 0) {
            return 0;
        }

        if(mem[n] == Integer.MAX_VALUE) {
            for(int i=0;i<n;i++) {
                int tmp = dp(n-i-1) + P[i];
                mem[n] = Math.min(mem[n], tmp);
            }
        }

        return mem[n];
    }
}
