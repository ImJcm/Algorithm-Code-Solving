package bruteforce;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/*
이전 순열(실버3)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	9698	5852	4838	62.799%
문제
1부터 N까지의 수로 이루어진 순열이 있다. 이때, 사전순으로 바로 이전에 오는 순열을 구하는 프로그램을 작성하시오.

사전 순으로 가장 앞서는 순열은 오름차순으로 이루어진 순열이고, 가장 마지막에 오는 순열은 내림차순으로 이루어진 순열이다.

N = 3인 경우에 사전순으로 순열을 나열하면 다음과 같다.

1, 2, 3
1, 3, 2
2, 1, 3
2, 3, 1
3, 1, 2
3, 2, 1
입력
첫째 줄에 N(1 ≤ N ≤ 10,000)이 주어진다. 둘째 줄에 순열이 주어진다.

출력
첫째 줄에 입력으로 주어진 순열의 이전에 오는 순열을 출력한다. 만약, 사전순으로 가장 처음에 오는 순열인 경우에는 -1을 출력한다.

예제 입력 1
4
1 2 3 4
예제 출력 1
-1
예제 입력 2
5
5 4 3 2 1
예제 출력 2
5 4 3 1 2
 */
/*
 * 앞선, BOJ10972의 알고리즘을 이용해서 풀어야겠다.
 * pos와 j를 찾는 조건을 >= -> <=로 변경해서 이전 값을 찾는다.
 *
 */
public class BOJ10973 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] box;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        String[] temp = br.readLine().split(" ");
        box = new int[N];

        for(int i=0;i<N;i++) {
            box[i] = Integer.parseInt(temp[i]);
        }


        if(previous_permutation()) {
            for(int i=0;i<N;i++) {
                System.out.print(box[i] + " ");
            }
            System.out.println();
        }
    }

    static boolean previous_permutation() {
        int pos = box.length-1;
        int temp = 0;

        while(pos > 0 && box[pos-1] <= box[pos]) pos-=1;
        if(pos <= 0) {
            System.out.println("-1");
            return false;
        }
        int j = box.length-1;
        while(box[pos-1] <= box[j]) j-=1;

        temp = box[pos-1];
        box[pos-1] = box[j];
        box[j] = temp;

        j = box.length - 1;
        while(pos < j) {
            temp = box[pos];
            box[pos] = box[j];
            box[j] = temp;
            pos+=1;
            j-=1;
        }
        return true;
    }
}
