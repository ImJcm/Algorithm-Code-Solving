/*
부분수열의 합

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	55749	25668	16597	44.308%
문제
N개의 정수로 이루어진 수열이 있을 때, 크기가 양수인 부분수열 중에서 그 수열의 원소를 다 더한 값이 S가 되는 경우의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 정수의 개수를 나타내는 N과 정수 S가 주어진다. (1 ≤ N ≤ 20, |S| ≤ 1,000,000) 둘째 줄에 N개의 정수가 빈 칸을 사이에 두고 주어진다. 주어지는 정수의 절댓값은 100,000을 넘지 않는다.

출력
첫째 줄에 합이 S가 되는 부분수열의 개수를 출력한다.

예제 입력 1
5 0
-7 -3 -2 5 8
예제 출력 1
1
 */
/*
    참고 :
    https://blog.naver.com/jackandjills/222353332551 - 비트연산을 통한 배열의 부분집합 구하기
    https://dev-nomad.com/38 - 비트연산
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ1182 {
    static int N, S;
    static int[] boxs;
    static int pre_result, answer=0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        boxs = new int[N];
        boxs = Arrays.asList(br.readLine().split(" "))
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        for(int i=0;i<(1<<N);i++) {
            pre_result = 0;
            // > 0 을 통해 공집합 배열 제외
            if(Integer.bitCount(i) > 0) {
                for (int j = 0; j < N; j++) {
                    if ((i & (1 << j)) != 0) {
                        pre_result += boxs[j];
                    }
                }
                if(pre_result == S) {
                    answer += 1;
                }
            }
        }
        System.out.println(answer);
    }
}
