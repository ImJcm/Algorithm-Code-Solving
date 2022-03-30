package BOJ;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/*
1, 2, 3 더하기 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초 (추가 시간 없음)	512 MB	75915	49414	33077	63.269%
문제
정수 4를 1, 2, 3의 합으로 나타내는 방법은 총 7가지가 있다. 합을 나타낼 때는 수를 1개 이상 사용해야 한다.

1+1+1+1
1+1+2
1+2+1
2+1+1
2+2
1+3
3+1
정수 n이 주어졌을 때, n을 1, 2, 3의 합으로 나타내는 방법의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 테스트 케이스의 개수 T가 주어진다. 각 테스트 케이스는 한 줄로 이루어져 있고, 정수 n이 주어진다. n은 양수이며 11보다 작다.

출력
각 테스트 케이스마다, n을 1, 2, 3의 합으로 나타내는 방법의 수를 출력한다.

예제 입력 1
3
4
7
10
예제 출력 1
7
44
274
 */
//문제를 처음보고 DFS에서 Depth를 정수 n으로 잡고 base-case를 n과 같으면 개수를 출력
public class BOJ9095 {
    static int count;
    static int N;
    static int[] box = {1,2,3};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        N=Integer.parseInt(br.readLine());

        for(int i=0;i<N;i++) {
            int n = Integer.parseInt(br.readLine());
            count = 0;
            DFS(0,n);
            sb.append(count+"\n");
        }

        System.out.println(sb);


    }

    static void DFS(int sum,int depth) {
        if(sum == depth) {
            count++;
            return;
        }

        for(int i=0;i<3;i++) {
            int next = box[i];

            if(next+sum > depth) {
                continue;
            }

            DFS(next+sum,depth);
        }
    }
}
