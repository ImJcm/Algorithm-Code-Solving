package BOJ.bruteforce;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
모든 순열(silver3)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	20239	12370	9250	61.885%
문제
N이 주어졌을 때, 1부터 N까지의 수로 이루어진 순열을 사전순으로 출력하는 프로그램을 작성하시오.

입력
첫째 줄에 N(1 ≤ N ≤ 8)이 주어진다.

출력
첫째 줄부터 N!개의 줄에 걸쳐서 모든 순열을 사전순으로 출력한다.

예제 입력 1
3
예제 출력 1
1 2 3
1 3 2
2 1 3
2 3 1
3 1 2
3 2 1
 */
/*
 * DFS로 풀면될 것 같은데, BOJ10972의 다음 수열의 알고리즘을 이용해보겠다.
 * nextPermuation으로 현재 배열에서 다음 수열을 box로 업데이트하고 box를 StringBuilder에 저장
 */
public class BOJ10974 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] box;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        box = new int[N];

        for(int i=0;i<N;i++) {
            box[i] = i+1;
            sb.append(box[i] + " ");
        }
        sb.append("\n");

        //N!번 반복하면 된다.
        int count = factorial(N);
        for(int i=0;i<count;i++) {
            nextPermutation();
        }
        System.out.println(sb.toString());
    }

    static void nextPermutation(){
        int pos = box.length - 1;
        int temp;
        while(pos > 0 && box[pos-1] >= box[pos]) pos-=1;
        if(pos <= 0) {
            return;
        }

        int pos2 = box.length - 1;
        while(box[pos-1] >= box[pos2]) pos2-=1;

        temp = box[pos-1];
        box[pos-1] = box[pos2];
        box[pos2] = temp;

        pos2 = box.length - 1;
        while(pos < pos2) {
            temp = box[pos];
            box[pos] = box[pos2];
            box[pos2] = temp;
            pos+=1;
            pos2-=1;
        }
        for(int i=0;i<box.length;i++) {
            sb.append(box[i]+ " ");
        }
        sb.append("\n");
    }

    static int factorial(int n) {
        int f = 1;
        for(int i=n;i>1;i--){
            f *= i;
        }
        return f;
    }
}
