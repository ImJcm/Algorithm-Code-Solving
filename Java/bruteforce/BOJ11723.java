package BOJ.bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
집합(실버 5)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1.5 초	4 MB (하단 참고)	53378	16451	11616	29.324%
문제
비어있는 공집합 S가 주어졌을 때, 아래 연산을 수행하는 프로그램을 작성하시오.

add x: S에 x를 추가한다. (1 ≤ x ≤ 20) S에 x가 이미 있는 경우에는 연산을 무시한다.
remove x: S에서 x를 제거한다. (1 ≤ x ≤ 20) S에 x가 없는 경우에는 연산을 무시한다.
check x: S에 x가 있으면 1을, 없으면 0을 출력한다. (1 ≤ x ≤ 20)
toggle x: S에 x가 있으면 x를 제거하고, 없으면 x를 추가한다. (1 ≤ x ≤ 20)
all: S를 {1, 2, ..., 20} 으로 바꾼다.
empty: S를 공집합으로 바꾼다.
입력
첫째 줄에 수행해야 하는 연산의 수 M (1 ≤ M ≤ 3,000,000)이 주어진다.

둘째 줄부터 M개의 줄에 수행해야 하는 연산이 한 줄에 하나씩 주어진다.

출력
check 연산이 주어질때마다, 결과를 출력한다.

예제 입력 1
26
add 1
add 2
check 1
check 2
check 3
remove 2
check 1
check 2
toggle 3
check 1
check 2
check 3
check 4
all
check 10
check 20
toggle 10
remove 20
check 10
check 20
empty
check 1
toggle 1
check 1
toggle 1
check 1
 */
public class BOJ11723 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static int[] box = new int[21];
    static int M,idx;

    public static void main(String[] args) throws IOException {
        M = Integer.parseInt(br.readLine());
        Arrays.fill(box,0);

        calculate(M);

        System.out.println(sb.toString());
    }

    static void calculate(int m) throws IOException {
        for(int i=0;i<m;i++) {
            String[] temp = br.readLine().split(" ");

            switch (temp[0]) {
                case "add":
                    idx = Integer.parseInt(temp[1]);
                    box[idx] = 1;
                    break;
                case "remove":
                    idx = Integer.parseInt(temp[1]);
                    box[idx] = 0;
                    break;
                case "check":
                    idx = Integer.parseInt(temp[1]);
                    if(box[idx] == 1) {
                        sb.append("1\n");
                    } else {
                        sb.append("0\n");
                    }
                    break;
                case "toggle":
                    idx = Integer.parseInt(temp[1]);
                    if(box[idx] == 1) {
                        box[idx] = 0;
                    } else {
                        box[idx] = 1;
                    }
                    break;
                case "all":
                    Arrays.fill(box,1);
                    break;
                case "empty":
                    Arrays.fill(box,0);
                    break;
            }
        }
    }
}
