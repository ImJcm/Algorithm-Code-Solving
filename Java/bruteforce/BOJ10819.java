package BOJ.bruteforce;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
차이를 최대로(silver 2)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	19978	12790	9876	64.536%
문제
N개의 정수로 이루어진 배열 A가 주어진다. 이때, 배열에 들어있는 정수의 순서를 적절히 바꿔서 다음 식의 최댓값을 구하는 프로그램을 작성하시오.

|A[0] - A[1]| + |A[1] - A[2]| + ... + |A[N-2] - A[N-1]|

입력
첫째 줄에 N (3 ≤ N ≤ 8)이 주어진다. 둘째 줄에는 배열 A에 들어있는 정수가 주어진다. 배열에 들어있는 정수는 -100보다 크거나 같고, 100보다 작거나 같다.

출력
첫째 줄에 배열에 들어있는 수의 순서를 적절히 바꿔서 얻을 수 있는 식의 최댓값을 출력한다.

예제 입력 1
6
20 1 15 8 4 10
예제 출력 1
62

 */
/*
문제를 보고 계산과정을 줄일 수 있는 방법이 생각이 나질 않아 DFS를 사용하여 모든 경우의수를 구한뒤,
|A[0] - A[1]| + |A[1] - A[2]| + ... + |A[N-2] - A[N-1]|가 최댓값인 경우, max_result에 업데이트하는 식으로 알고리즘 구현
 */
public class BOJ10819 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] A,input;
    static int N,max_result = Integer.MIN_VALUE;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        A = new int[N];
        input = new int[N];
        visited = new boolean[N];
        input = Arrays.asList(br.readLine().split(" "))
                .stream().mapToInt(Integer::parseInt)
                .toArray();

        max_the_difference(0);

        System.out.println(max_result);
    }

    static void max_the_difference(int depth) {
        if(depth == N) {
            int sum = 0;
            for(int i=0;i<N-1;i++) {
                sum += Math.abs(A[i] - A[i+1]);
            }
            max_result = Math.max(sum,max_result);
        }

        for(int i=0;i<N;i++) {
            if(visited[i]) {
                continue;
            }
            A[depth] = input[i];
            visited[i] = true;
            max_the_difference(depth+1);
            visited[i] = false;
        }

    }
}
