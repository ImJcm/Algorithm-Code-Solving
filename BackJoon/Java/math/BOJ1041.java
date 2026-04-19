package math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
주사위
 
시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	13434	3530	2989	26.769%
문제
    +---+        
    | D |        
+---+---+---+---+
| E | A | B | F |
+---+---+---+---+
    | C |        
    +---+        
주사위는 위와 같이 생겼다. 주사위의 여섯 면에는 수가 쓰여 있다. 위의 전개도를 수가 밖으로 나오게 접는다.

A, B, C, D, E, F에 쓰여 있는 수가 주어진다.

지민이는 현재 동일한 주사위를 N3개 가지고 있다. 이 주사위를 적절히 회전시키고 쌓아서, N×N×N크기의 정육면체를 만들려고 한다. 이 정육면체는 탁자위에 있으므로, 5개의 면만 보인다.

N과 주사위에 쓰여 있는 수가 주어질 때, 보이는 5개의 면에 쓰여 있는 수의 합의 최솟값을 출력하는 프로그램을 작성하시오.

입력
첫째 줄에 N이 주어진다. 둘째 줄에 주사위에 쓰여 있는 수가 주어진다. 위의 그림에서 A, B, C, D, E, F에 쓰여 있는 수가 차례대로 주어진다. N은 1,000,000보다 작거나 같은 자연수이고, 쓰여 있는 수는 50보다 작거나 같은 자연수이다.

출력
첫째 줄에 문제의 정답을 출력한다.

예제 입력 1 
2
1 2 3 4 5 6
예제 출력 1 
36
예제 입력 2 
3
1 2 3 4 5 6
예제 출력 2 
69
예제 입력 3 
1000000
50 50 50 50 50 50
예제 출력 3 
250000000000000
예제 입력 4 
10
1 1 1 1 50 1
예제 출력 4 
500
 */
/*
    핵심 알고리즘 : 수학, 그리디 알고리즘

    N=1) 5개의 최솟값의 합
    N=2) 3면:4, 2면:4, 1면:0
    N=3) 3면:4, 2면:12, 1면:9개
    N=4) 3면:4, 2면:20, 1면:28개
    ...
    N=k) 3면:4, 2면:4+8*(N-2), 1면: (N-2)^2+4*(N-2)(N-1)
    N이 늘어날 수록 일정하게 증가하는 규칙 발견하고 위와 같은 점화식 발견
    큐브의 6개 면의 값을 받으면 3면의 최소값, 2면의 최소값, 1면의 최소값을 따로 저장하고 큐브의 개수만큼 더해서 식에 적용하고 출력하면
    될 것으로 판단
 */
public class BOJ1041 {
    static long N;
    static int[] cube;
    static long[] sides_sum;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Long.parseLong(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        cube = new int[6];
        for(int i=0;i<6;i++) {
            cube[i] = Integer.parseInt(st.nextToken());
        }

        //단순 면의 값으로 오름차순 정렬하는 것이 아닌 면에 고정된 수가 지정되기 때문에, 인접한 면에서의 최소값을 구하여 저장해야한다
//        Arrays.sort(cube);
//        sides_sum = new long[3];
//        long sum = 0;
//        for(int i=0;i<3;i++) {
//            sum += cube[i];
//            sides_sum[i] = sum;
//            System.out.println(sum);
//        }
        //인접한 면들중 면1,2,3개 일때 합계가 최소인 경우를 구하고, side_sum에 저장
        sides_sum = new long[3];
        //마주보는 주사위의 인덱스 합은 5이므로 마주보는 면 중 최소값 고르기
        long minAF = Math.min(cube[0], cube[5]);
        long minBE = Math.min(cube[1], cube[4]);
        long minCD = Math.min(cube[2], cube[3]);
        sides_sum[0] = Math.min(minAF, Math.min(minBE, minCD));
        sides_sum[1] = Math.min(minAF + minBE, Math.min(minAF + minCD, minBE + minCD));
        sides_sum[2] = minAF + minBE + minCD;

        solve();
    }

    static void solve() {
        if(N==1) {
            int sum = 0;
            int max = Integer.MIN_VALUE;
            for(int i=0;i<6;i++) {
                max = Math.max(max,cube[i]);
                sum += cube[i];
            }
            System.out.println(sum - max);
        } else {
//            result = ((N-2)*(N-2) + 4*(N-2)*(N-1)) * sides_sum[0]
//                    + (4 + 8*(N-2)) * sides_sum[1]
//                    + 4 * sides_sum[2];
            //연산과정에 int type이 있으면, 결과 값이 int로 저장되는 것 확인
            //따라서, long type 변수들로 연산을 수행해야 함
            long r1 = (((N-2)*(N-2)) + 4*(N-2)*(N-1)) * sides_sum[0];
            long r2 = (4 + 8*(N-2)) * sides_sum[1];
            long r3 = 4 * sides_sum[2];

            System.out.println(r1+r2+r3);
        }
    }
}
