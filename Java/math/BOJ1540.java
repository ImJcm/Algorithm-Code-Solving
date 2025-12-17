package math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
정사각형의 개수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	177	89	72	50.350%
문제
세준이는 2차원 평면에 N개의 점을 찍었다. 그리고 나서 정사각형의 개수를 세려고 한다.

정사각형의 개수란, 세준이가 찍은 서로 다른 N개의 점을 꼭짓점으로 하며, 모든 변은 축에 평행한 서로 다른 정사각형을 모두 센 것이다.

세준이는 정사각형의 개수를 최대로 하려고 한다.

N이 주어졌을 때, 정사각형의 개수의 최댓값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N이 주어진다. 이 값은 0보다 크거나 같고, 1000000보다 작거나 같은 값이다.

출력
첫째 줄에 정사각형의 개수의 최댓값을 출력한다.

예제 입력 1
16
예제 출력 1
14
예제 입력 2
4
예제 출력 2
1
예제 입력 3
5
예제 출력 3
1
예제 입력 4
6
예제 출력 4
2
예제 입력 5
115
예제 출력 5
340
 */
public class BOJ1540 {
    static int N;
    static int[] sqaure_Nums;
    static int startPos, endPos;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        sqaure_Nums = new int[N+1];
        Arrays.fill(sqaure_Nums,0);

        System.out.println(solve());
    }

    /*
        N이 증가할 때, 나올수 있는 정사각형의 개수는 규칙에 의해 증가한다
        N=1,2,3) 0
        N=4) 1
        N=5) 1
        N=6) 2
        N=7) 2
        N=8) 3
        N=9) 5
        N=10) 5
        N=11) 6
        N=12) 8
        N=13) 8
        N=14) 9
        N=15) 11
        N=16) 14
        N=...) ...
        N이 자연수의 제곱 수일 때, 증가하는 수치가 route(N)-1만큼 증가하고, 이후, 0~route(N)-1까지 순차적으로
        증가하는 규칙 발견, 이를 이용하여 코드 작성
     */
    static int solve() {
        //sqaure_Nums[0] = sqaure_Nums[1] = sqaure_Nums[2] = sqaure_Nums[3] = 0;

        for(int i=4;i<=N;i++) {
            if(Math.sqrt(i) - (int)Math.sqrt(i) == 0) {
                endPos = (int)Math.sqrt(i);
                sqaure_Nums[i] = sqaure_Nums[i-1] + (endPos-1);
                startPos = 0;
                continue;
            }

            sqaure_Nums[i] = sqaure_Nums[i-1] + startPos++;

            if(endPos == startPos) {
                startPos = 0;
            }
        }
        return sqaure_Nums[N];
    }

}
