package BOJ.bruteforce;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
Guess (Gold 3)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128MB	7267	2676	1711	36.006%
문제
정수 시퀀스 a 1 , a 2 , … , an n , 1 ≤ i ≤ j ≤ n에 대해 a i + … + a j > 0; S ij ="-" if a i + … + a j < 0; 그렇지 않으면 S ij = "0"입니다.

예를 들어, (a 1 , a 2 , a 3 , a 4 )=( −1, 5, −4, 2)이면 부호 행렬 S는 4×4 행렬입니다.

 	1	2	3	4
1	-	+	0	+
2	 	+	+	+
3	 	 	-	-
4	 	 	 	+
시퀀스 (−1, 5, −4, 2)가 부호 행렬을 생성한다고 말합니다. 부호 행렬은 정수 시퀀스로 생성할 수 있는 경우 유효합니다.

일련의 정수가 주어지면 부호 행렬을 쉽게 계산할 수 있습니다. 이 문제는 반대 방향에 관한 것입니다. 유효한 부호 행렬이 주어지면 부호 행렬을 생성하는 정수 시퀀스를 찾으십시오. 둘 이상의 서로 다른 정수 시퀀스는 동일한 부호 행렬을 생성할 수 있습니다. 예를 들어, 시퀀스 (−2, 5, −3, 1)은 시퀀스 (−1,5, −4,2)와 동일한 부호 행렬을 생성합니다.

유효한 부호 행렬이 주어졌을 때 부호 행렬을 생성하는 일련의 정수를 찾을 수 있는 프로그램을 작성하십시오. 시퀀스의 모든 정수가 -10과 10 사이에 있다고 가정할 수 있습니다.

입력
첫 번째 줄은 정수 n(1 ≤ n ≤ 10)을 포함합니다. 여기서 n은 정수 시퀀스의 길이입니다. 두 번째 줄에는 처음 n개의 문자가 부호 행렬의 첫 번째 행에 해당하고 다음 n−1개의 문자가 두 번째 행에 해당하는 n(n+1)/2개의 문자가 포함되며 n번째 행의 문자.

출력
부호 행렬을 생성하는 n개의 정수 시퀀스를 포함하는 정확히 한 줄을 출력합니다. 둘 이상의 시퀀스가 ​​부호 행렬을 생성하는 경우 그 중 하나를 출력할 수 있습니다. 시퀀스의 모든 정수는 -10에서 10 사이여야 하며 둘 다 포함해야 합니다.

예제 입력 1
4
-+0++++--+
예제 출력 1
-2 5 -3 1
예제 입력 2
2
+++
예제 출력 2
3 4
예제 입력 3
5
++0+-+-+--+-+--
예제 출력 3
1 2 -3 4 -5
 */
/*
 * 해당코드 결과 : "시간초과"
 * 음.. depth==N일 때 for문을 너무많이 해서 시간초과가 발생한다는 생각이 든다.
 * DFS(depth+1)이전에 그 depth행에서 누적합이 sign_box의 부호에 맞는 숫자를 고르는 과정으로 대채해야할 것 같다.
 *
 * BOJ1248의 질문검색을 통해 해당 문제는 N개의 경우의수를 만들고 조건을 검사하기에는 과정이 많아져서 시간초과가 발생한다는 점에서
 * 과정을 줄일 수 있는 방법을 찾아야한다. 그래서, col을 기준으로 숫자를 guess[depth]에 sequence[]값을 넣어가면서
 * depth의 값만큼 (row : 0 ~ depth) * (col : row value ~ depth)의 해당 row에서의 누적합이 sign_box[row][depth]와 일치하는지
 * 확인하고 row가 depth가 될 때까지 검사해서, 일치하는 횟수가 depth가 되면, 해당하는 숫자가 sign_box의 조건을 만족하는 것이므로
 * 다음 DFS(depth+1)로 넘어간다. 이과정이 모든 N의 수를 뽑아서 조건을 검사하는 방법보다 DFS를 호출하는 과정이 적다 그래서
 * 이 방법은 시간초과가 발생하지 않는 것같다.
 *
 * N=4
 * depth = 0                    depth=1         depth=2         depth=3     depth=4 <- 출력
 * (x는 아직 검사하지 못한 위치)
 * ex) o x x x                  o o x x         o o o x         o o o o
 *       x x x                    o x x           o o x           o o o
 *         x x                      x x             o x             o o
 *           x                        x               x               o
 */
public class BOJ1248 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String signMatrix;
    static char[][] sign_box;
    static int N;
    static int[] sequence = {-10,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,10};
    static int[] guess;
    static boolean check = false;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        guess = new int[N];
        sign_box = new char[N][N];

        signMatrix = br.readLine();
        int temp_index = 0;
        for(int i=0;i<N;i++) {
            for(int j=i;j<N;j++) {
                sign_box[i][j] = signMatrix.charAt(temp_index++);
            }
        }
        DFS(0);

    }

    static void DFS(int depth) {
        int sum;

        if(depth == N) {
            for(int i=0;i<N;i++) {
                System.out.print(guess[i] + " ");
            }
            System.out.println();
            check = true;
            //System.exit(1);

        }

        for(int i=0;i < sequence.length;i++) {
            if(check) {
                return;
            }
            guess[depth] = sequence[i];
            int cnt = 0;

            for(int j=0;j<=depth;j++) {
                sum = 0;
                for(int k=j;k<=depth;k++) {
                    sum += guess[k];
                }
                if(sum > 0 && sign_box[j][depth] == '+') {
                    cnt++;
                }
                if(sum < 0 && sign_box[j][depth] == '-') {
                    cnt++;
                }
                if(sum == 0 && sign_box[j][depth] == '0') {
                    cnt++;
                }
            }
            if(cnt == depth + 1) {
                DFS(depth+1);
            }
        }
    }
}

/*
public class BOJ1248 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String signMatrix;
    static char[][] sign_box;
    static int N;
    static int[] sequence = {-10,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,10};
    static int[] guess;
    static boolean check = false,finish_check=false;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        guess = new int[N];
        sign_box = new char[N][N];

        signMatrix = br.readLine();
        int temp_index = 0;
        for(int i=0;i<N;i++) {
            for(int j=i;j<N;j++) {
                sign_box[i][j] = signMatrix.charAt(temp_index++);
            }
        }
        DFS(0);

    }

    static void DFS(int depth) {
        if(depth == N) {
            check = true;
            for (int i = 0; i < N && check; i++) {
                int total = 0;
                char sign;
                for (int j = i; j < N; j++) {
                    total += guess[j];
                    if(total > 0) sign = '+';
                    else if(total < 0) sign = '-';
                    else sign = '0';

                    if(sign != sign_box[i][j]) {
                        check = false;
                        break;
                    }
                }
            }

            if(check) {
                for(int i=0;i<N;i++) {
                    System.out.print(guess[i] + " ");
                }
                finish_check = true;
            }
            return;
        }

        //charAt에서 계차수열로 증가하는 index가 필요
        int pos = 0,end_pos= sequence.length;
        if (sign_box[depth][depth] == '+') {
            pos = 11;
            end_pos = sequence.length;
        } else if (sign_box[depth][depth] == '-') {
            pos = 0;
            end_pos = 10;
        } else {
            pos = 10;
            end_pos = 11;
        }

        for(int i=pos;i < end_pos;i++) {
            //중복을 허용하기 때문에 boolean visited는 작성하지 않음
            if(finish_check) {
                return;
            }

            guess[depth] = sequence[i];
            DFS(depth+1);
        }
    }
}
*/