package BOJ.bruteforce;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

/*
다음 순열

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	21181	8739	6318	41.827%
문제
1부터 N까지의 수로 이루어진 순열이 있다. 이때, 사전순으로 다음에 오는 순열을 구하는 프로그램을 작성하시오.

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
첫째 줄에 입력으로 주어진 순열의 다음에 오는 순열을 출력한다. 만약, 사전순으로 마지막에 오는 순열인 경우에는 -1을 출력한다.

예제 입력 1
4
1 2 3 4
예제 출력 1
1 2 4 3
예제 입력 2
5
5 4 3 2 1
예제 출력 2
-1
 */
/*
 * Permuation 순열문제, 모든 경우의수를 찾아내면서, 입력값과 같은 경우의수를 찾아내면 check하고, 다음에 오는 경우의 수를 출력한다.
 * 예외처리, 입력된 경우의 수 다음의 경우가 없다면 -1
 *
 * Permutation_DFS함수를 사용한 경우, 시간초과가 발생 다른방법을 찾아봐야할 것 같다.
 *
 */
public class BOJ10972 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] box;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        box = new int[N];

        String[] temp = br.readLine().split(" ");
        for(int i=0;i<temp.length;i++) {
            box[i] = Integer.parseInt(temp[i]);
        }

        if(next_permutation()) {
            for(int i=0;i<N;i++) {
                System.out.print(box[i] + " ");
            }
            System.out.println();
        }
    }

    /*
     * 해당 알고리즘은 질문검색을 참고했다.
     * 입력된 수에서 다음으로 나오는 값을 만들기 위해 box[i-1] < box[i]인 i와 box[i-1] < box[j]인 j를 box[i-1], box[j]를 스왑한다.
     * 이후, j=n-1로 설정하고, i와 j 사이의 값들을 서로 스왑하면서 i >= j가 될때까지 진행
     */
    static boolean next_permutation() {
        int i = box.length-1;
        int temp = 0;
        while(i > 0 && box[i-1] >= box[i]) i -= 1;
        if(i<=0) {
            System.out.println("-1");
            return false;
        }
        int j = box.length-1;
        while(box[i-1] >= box[j]) j -= 1;

        temp = box[i-1];
        box[i-1] = box[j];
        box[j] = temp;

        j = box.length-1;
        while(i < j) {
            temp = box[i];
            box[i] = box[j];
            box[j] = temp;
            i+=1;
            j-=1;
        }
        return true;
    }
}
/*
public class BOJ10972 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] box,result;
    static Integer[] box_clone;
    static boolean check = false,breakpoint=false;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        box = new int[N];
        box_clone = new Integer[N];
        result = new int[N];
        visited = new boolean[N];

        String[] temp = br.readLine().split(" ");
        for(int i=0;i<temp.length;i++) {
            box[i] = Integer.parseInt(temp[i]);
            box_clone[i] = Integer.parseInt(temp[i]);
        }

        Arrays.sort(box_clone, Collections.reverseOrder()); //내림차순

        boolean exception = true;
        for(int i=0;i<N && exception;i++) {
            if(box[i] != box_clone[i]) {
                exception = false;
            }
        }
        if(!exception) {
            Permutation_DFS(0);
        } else {
            System.out.println("-1");
        }

    }

    static void Permutation_DFS(int depth) {
        if(depth == N) {
            if(check) {
                for (int i : result) {
                    System.out.print(i + " ");
                }
                System.out.println();
                breakpoint = true;
                return;
            }

            for(int i=0;i<N;i++) {
                if(box[i] != result[i]) {
                    return;
                }
            }
            check = true;
            return;
        }

        for(int i=0;i<N;i++) {
            if(breakpoint) {
                return;
            }
            if(visited[i]) {
                continue;
            }

            result[depth] = box[i];
            visited[i] = true;
            Permutation_DFS(depth+1);
            visited[i] = false;
        }
    }
}
*/