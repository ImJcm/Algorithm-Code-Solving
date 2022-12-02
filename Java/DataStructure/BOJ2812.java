import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

/*
크게 만들기 다국어
 
시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	22218	5737	4126	25.846%
문제
N자리 숫자가 주어졌을 때, 여기서 숫자 K개를 지워서 얻을 수 있는 가장 큰 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N과 K가 주어진다. (1 ≤ K < N ≤ 500,000)

둘째 줄에 N자리 숫자가 주어진다. 이 수는 0으로 시작하지 않는다.

출력
입력으로 주어진 숫자에서 K개를 지웠을 때 얻을 수 있는 가장 큰 수를 출력한다.

예제 입력 1 
4 2
1924
예제 출력 1 
94
예제 입력 2 
7 3
1231234
예제 출력 2 
3234
예제 입력 3 
10 4
4177252841
예제 출력 3 
775841
 */
/*
    핵심알고리즘 : 자료구조, 그리디 알고리즘, 스택

    앞의 자리 수의 값부터 스택에 넣으면서, stack.peek값이 현재 인덱스 값과 비교하여 더 큰 값이면 교체하고 삭제한 원소 개수를 업데이트한다
    작은 값이면 stack에 add한다. 이를 반복하고 최종적으로 삭제한 원소 개수가 입력으로 지정한 삭제개수보다 못 미치는 경우 k만큼만 출력
 */
public class BOJ2812 {
    static int N,K,Size,Count=0;

    static Stack<String> stack;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        Size = N-K;

        String[] num = br.readLine().split("");
        stack = new Stack<>();
        for(int i=0;i<N;i++) {
            //삭제하는 갯수를 Count < K에서 넘기는 경우는 조건문에서 제외시켜 삭제한 갯수를 넘기는 경우는 없다
            while(!stack.isEmpty() && Count < K && Integer.parseInt(stack.peek()) < Integer.parseInt(num[i])) {
                stack.pop();
                Count++;
            }
            stack.push(num[i]);
        }

        StringBuilder sb = new StringBuilder();

        //삭제하는 갯수가 K개 만큼 도달하지 못하는 경우, 추가로 stack에서 K개 만큼 출력한다.
        for(int i=0;i<Size;i++) {
            sb.append(stack.get(i));
        }
        System.out.println(sb.toString());
    }
}
