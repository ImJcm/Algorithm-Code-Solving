package BOJ;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
부등호(실버2)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	16316	8743	5892	51.793%
문제
두 종류의 부등호 기호 ‘<’와 ‘>’가 k개 나열된 순서열 A가 있다. 우리는 이 부등호 기호 앞뒤에 서로 다른 한 자릿수 숫자를 넣어서 모든 부등호 관계를 만족시키려고 한다. 예를 들어, 제시된 부등호 순서열 A가 다음과 같다고 하자.

A ⇒ < < < > < < > < >

부등호 기호 앞뒤에 넣을 수 있는 숫자는 0부터 9까지의 정수이며 선택된 숫자는 모두 달라야 한다. 아래는 부등호 순서열 A를 만족시키는 한 예이다.

3 < 4 < 5 < 6 > 1 < 2 < 8 > 7 < 9 > 0

이 상황에서 부등호 기호를 제거한 뒤, 숫자를 모두 붙이면 하나의 수를 만들 수 있는데 이 수를 주어진 부등호 관계를 만족시키는 정수라고 한다. 그런데 주어진 부등호 관계를 만족하는 정수는 하나 이상 존재한다. 예를 들어 3456128790 뿐만 아니라 5689023174도 아래와 같이 부등호 관계 A를 만족시킨다.

5 < 6 < 8 < 9 > 0 < 2 < 3 > 1 < 7 > 4

여러분은 제시된 k개의 부등호 순서를 만족하는 (k+1)자리의 정수 중에서 최댓값과 최솟값을 찾아야 한다. 앞서 설명한 대로 각 부등호의 앞뒤에 들어가는 숫자는 { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }중에서 선택해야 하며 선택된 숫자는 모두 달라야 한다.

입력
첫 줄에 부등호 문자의 개수를 나타내는 정수 k가 주어진다. 그 다음 줄에는 k개의 부등호 기호가 하나의 공백을 두고 한 줄에 모두 제시된다. k의 범위는 2 ≤ k ≤ 9 이다.

출력
여러분은 제시된 부등호 관계를 만족하는 k+1 자리의 최대, 최소 정수를 첫째 줄과 둘째 줄에 각각 출력해야 한다. 단 아래 예(1)과 같이 첫 자리가 0인 경우도 정수에 포함되어야 한다. 모든 입력에 답은 항상 존재하며 출력 정수는 하나의 문자열이 되도록 해야 한다.

예제 입력 1
2
< >
예제 출력 1
897
021
예제 입력 2
9
> < < < > > > < <
예제 출력 2
9567843012
1023765489
 */
/*
 * 문제를 보고, DFS로 depth=k로 조건을 걸고, depth=k일 때, 최대, 최소값을 정하고, DFS호출 전 부등호 조건에 맞는지 검사하는
 * 방법을 이용하면 될 것 같다고 생각.
 * 출력정수는 int 범위를 넘어갈 수 있기 때문에, long으로 선언
 */
public class BOJ2529 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int k;
    static long max_r=Long.MIN_VALUE,min_r=Long.MAX_VALUE;
    static String max_s,min_s;
    static String s;
    static String[] input;  //부등호
    static int[] number;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        k = Integer.parseInt(br.readLine());

        input = br.readLine().split(" ");
        number = new int[k+1];
        visited = new boolean[10];
        DFS(0);


        System.out.println(max_s);
        System.out.println(min_s);
    }

    static void DFS(int depth) {
        if(depth == k+1) {
            for(int i=0;i<k;i++) {
                if(input[i].equals("<")) {
                    if(number[i] < number[i+1]) {
                        continue;
                    } else {
                        return;
                    }
                } else {    // ">"
                    if(number[i] > number[i+1]) {
                        continue;
                    } else {
                        return;
                    }
                }
            }
            //부등호 조건을 만족하는 경우
            long temp = 0;
            String stemp = "";
            for(int i=k;i>=0;i--) {
                temp += (long)Math.pow(10,i) * number[k-i];
                stemp += number[k-i];
            }
            //max_r = Math.max(max_r,temp);
            //min_r = Math.min(min_r,temp);
            if(max_r < temp) {
                max_r = temp;
                max_s = stemp;
            }

            if(min_r > temp) {
                min_r = temp;
                min_s = stemp;
            }

            return;
        }

        for(int i=0;i<10;i++) {
            if(visited[i]) {
                continue;
            }
            number[depth] = i;
            visited[i] = true;
            DFS(depth+1);
            visited[i] = false;

        }
    }
}
