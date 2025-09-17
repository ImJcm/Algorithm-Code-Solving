package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;

/*
배열 합치기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1.5 초	256 MB	51714	25063	16754	47.002%
문제
정렬되어있는 두 배열 A와 B가 주어진다. 두 배열을 합친 다음 정렬해서 출력하는 프로그램을 작성하시오.

입력
첫째 줄에 배열 A의 크기 N, 배열 B의 크기 M이 주어진다. (1 ≤ N, M ≤ 1,000,000)

둘째 줄에는 배열 A의 내용이, 셋째 줄에는 배열 B의 내용이 주어진다. 배열에 들어있는 수는 절댓값이 109보다 작거나 같은 정수이다.

출력
첫째 줄에 두 배열을 합친 후 정렬한 결과를 출력한다.

예제 입력 1
2 2
3 5
2 9
예제 출력 1
2 3 5 9
예제 입력 2
2 1
4 7
1
예제 출력 2
1 4 7
예제 입력 3
4 3
2 3 5 9
1 4 7
예제 출력 3
1 2 3 4 5 7 9
출처
문제를 만든 사람: baekjoon
알고리즘 분류
정렬
두 포인터
 */
/*
알고리즘 핵심
투 포인트
1. A,B의 정렬된 배열에서 각각의 A_idx, B_idx를 가지고 A[A_idx], B[B_idx] 값을 비교하여 작은 값을 우선하여 ans에 저장한다.
2. A or B에서 idx가 길이를 넘긴 경우, 나머지 배열에서 값을 ans에 추가한다.
 */
public class BOJ11728 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,A_idx,B_idx;
    static int[] A,B;
    static StringBuilder ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        while(A_idx < A.length && B_idx < B.length) {
            if(A[A_idx] < B[B_idx]) ans.append(A[A_idx++]).append(" ");
            else ans.append(B[B_idx++]).append(" ");
        }

        if(A_idx == A.length) for(int i = B_idx; i < B.length; i++) ans.append(B[i]).append(" ");
        if(B_idx == B.length) for(int i = A_idx; i < A.length; i++) ans.append(A[i]).append(" ");

        System.out.println(ans.toString());
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        A = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        B = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        A_idx = 0;
        B_idx = 0;

        ans = new StringBuilder();
    }
}
