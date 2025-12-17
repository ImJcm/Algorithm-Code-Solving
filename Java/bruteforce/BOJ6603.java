package bruteforce;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
/*
로또 다국어(silver 2)
시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	40373	22579	15465	54.726%
문제
독일 로또는 {1, 2, ..., 49}에서 수 6개를 고른다.

로또 번호를 선택하는데 사용되는 가장 유명한 전략은 49가지 수 중 k(k>6)개의 수를 골라 집합 S를 만든 다음 그 수만 가지고 번호를 선택하는 것이다.

예를 들어, k=8, S={1,2,3,5,8,13,21,34}인 경우 이 집합 S에서 수를 고를 수 있는 경우의 수는 총 28가지이다. ([1,2,3,5,8,13], [1,2,3,5,8,21], [1,2,3,5,8,34], [1,2,3,5,13,21], ..., [3,5,8,13,21,34])

집합 S와 k가 주어졌을 때, 수를 고르는 모든 방법을 구하는 프로그램을 작성하시오.

입력
입력은 여러 개의 테스트 케이스로 이루어져 있다. 각 테스트 케이스는 한 줄로 이루어져 있다. 첫 번째 수는 k (6 < k < 13)이고, 다음 k개 수는 집합 S에 포함되는 수이다. S의 원소는 오름차순으로 주어진다.

입력의 마지막 줄에는 0이 하나 주어진다.

출력
각 테스트 케이스마다 수를 고르는 모든 방법을 출력한다. 이때, 사전 순으로 출력한다.

각 테스트 케이스 사이에는 빈 줄을 하나 출력한다.

예제 입력 1
7 1 2 3 4 5 6 7
8 1 2 3 5 8 13 21 34
0
예제 출력 1
1 2 3 4 5 6
1 2 3 4 5 7
1 2 3 4 6 7
1 2 3 5 6 7
1 2 4 5 6 7
1 3 4 5 6 7
2 3 4 5 6 7

1 2 3 5 8 13
1 2 3 5 8 21
1 2 3 5 8 34
1 2 3 5 13 21
1 2 3 5 13 34
1 2 3 5 21 34
1 2 3 8 13 21
1 2 3 8 13 34
1 2 3 8 21 34
1 2 3 13 21 34
1 2 5 8 13 21
1 2 5 8 13 34
1 2 5 8 21 34
1 2 5 13 21 34
1 2 8 13 21 34
1 3 5 8 13 21
1 3 5 8 13 34
1 3 5 8 21 34
1 3 5 13 21 34
1 3 8 13 21 34
1 5 8 13 21 34
2 3 5 8 13 21
2 3 5 8 13 34
2 3 5 8 21 34
2 3 5 13 21 34
2 3 8 13 21 34
2 5 8 13 21 34
3 5 8 13 21 34
 */

/*
문제를 보고 중복을 허용하지 않는 순열을 출력하면 될 것으로 판단 -> DFS로 경우의 수 저장하고 출력 (정답)

 */
public class BOJ6603 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int k;
    static int[] s,box;
    static boolean[] visited;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        while(true) {
            s = Arrays.asList(br.readLine().split(" "))
                    .stream()
                    .mapToInt(Integer::parseInt)
                    .toArray();
            if(s[0] == 0) break;
            box = new int[6];
            visited = new boolean[s.length];

            for(int i=1;i<=(s[0]-5);i++) {
                visited[i] = true;
                box[0] = s[i];
                LoTTo(1,i);
                visited[i] = false;
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    static void LoTTo(int depth, int startidx) {
        if(depth == 6) {
            for(int i=0;i<6;i++) {
                sb.append(Integer.toString(box[i]) + " ");
            }
            sb.append("\n");
            return;
        }

        for(int i=startidx;i<s.length;i++) {
            if(visited[i]) {
                continue;
            }

            visited[i] = true;
            box[depth] = s[i];
            LoTTo(depth+1, i);
            visited[i] = false;
        }
    }

    //6중 for문으로도 가능해보인다.
    static void six_for_make_permutation() {
        for(int c1=1;c1<=s[0]-5;c1++) {
            for(int c2=c1+1;c2<=s[0]-4;c2++) {
                for(int c3=c2+1;c3<=s[0]-3;c3++) {
                    for(int c4=c3+1;c4<=s[0]-2;c4++) {
                        for(int c5=c4+1;c5<=s[0]-1;c5++) {
                            for(int c6=c5+1;c6<=s[0];c6++) {
                                System.out.println(s[c1] + " " +s[c2] + " " + s[c3] + " " + s[c4] + " " + s[c5] + " " + s[c6] + "\n");
                            }
                        }
                    }
                }
            }
        }
    }
}
