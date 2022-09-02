import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.io.IOException;
import java.util.StringTokenizer;
/*
    https://coder-in-war.tistory.com/entry/BOJ-JAVA14391-%EC%A2%85%EC%9D%B4-%EC%A1%B0%EA%B0%81
    ㄴ 참고
 */
/*
종이 조각

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	4830	2653	1955	56.081%
문제
영선이는 숫자가 쓰여 있는 직사각형 종이를 가지고 있다. 종이는 1×1 크기의 정사각형 칸으로 나누어져 있고, 숫자는 각 칸에 하나씩 쓰여 있다. 행은 위에서부터 아래까지 번호가 매겨져 있고, 열은 왼쪽부터 오른쪽까지 번호가 매겨져 있다.

영선이는 직사각형을 겹치지 않는 조각으로 자르려고 한다. 각 조각은 크기가 세로나 가로 크기가 1인 직사각형 모양이다. 길이가 N인 조각은 N자리 수로 나타낼 수 있다. 가로 조각은 왼쪽부터 오른쪽까지 수를 이어 붙인 것이고, 세로 조각은 위에서부터 아래까지 수를 이어붙인 것이다.

아래 그림은 4×4 크기의 종이를 자른 한 가지 방법이다.



각 조각의 합은 493 + 7160 + 23 + 58 + 9 + 45 + 91 = 7879 이다.

종이를 적절히 잘라서 조각의 합을 최대로 하는 프로그램을 작성하시오.

입력
첫째 줄에 종이 조각의 세로 크기 N과 가로 크기 M이 주어진다. (1 ≤ N, M ≤ 4)

둘째 줄부터 종이 조각이 주어진다. 각 칸에 쓰여 있는 숫자는 0부터 9까지 중 하나이다.

출력
영선이가 얻을 수 있는 점수의 최댓값을 출력한다.

예제 입력 1
2 3
123
312
예제 출력 1
435
예제 입력 2
2 2
99
11
예제 출력 2
182
예제 입력 3
4 3
001
010
111
100
예제 출력 3
1131
예제 입력 4
1 1
8
예제 출력 4
8
 */
//완전탐색 - DFS
public class BOJ14391 {
    static int N, M;
    static int[] boxs;
    static int max = 0;
    static boolean[] visited;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        boxs = new int[N * M];
        visited = new boolean[N * M];

        for(int i=0;i<N;i++) {
            String s = br.readLine();
            for(int j=0;j<M;j++) {
                boxs[i * M + j] = s.charAt(j) - '0';
            }
        }

        dfs(0);
        System.out.println(max);
    }

    public static void dfs(int d) {
        //기저사례
        if(d == (N*M)) {
            int sum = 0, tmp = 0;
            //가로
            for(int i=0;i<N;i++) {
                tmp = 0;
                for(int j=0;j<M;j++) {
                    //가로로 이어지는 종이인 경우
                    if(visited[i * M + j]) {
                        tmp *= 10;
                        tmp += boxs[i * M + j];
                    //가로로 이어지지 않는 경우
                    } else {
                        sum += tmp;
                        tmp = 0;
                    }
                }
                sum += tmp;
            }

            //세로
            for(int j=0;j<M;j++) {
                tmp = 0;
                for(int i=0;i<N;i++) {
                    //세로로 이어지는 종이의 경우
                    if(!visited[i * M + j]) {
                        tmp *= 10;
                        tmp += boxs[i * M + j];
                    //세로로 이어지지 않는 경우
                    } else {
                        sum += tmp;
                        tmp = 0;
                    }
                }
                sum += tmp;
            }
            max = Math.max(sum,max);
            return;
        }
        /*
            visit = true이후, dfs를 재귀호출하는 경우, 가로로 동작 수행하고
            visit = false이후, dfs는 세로방향으로 동작을 수행한다.
            ㄴ 중요한 개념인 것 같으니 참고
         */
        visited[d] = true;
        dfs(d+1);
        visited[d] = false;
        dfs(d+1);
    }
}
/*
//비트마스킹
public class BOJ14391 {
    static int N, M;
    static int[][] boxs;
    static int[] ex;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        boxs = new int[N][M];
        ex = new int[(int)Math.pow(2,N*M)/2];

//        for(int i=0;i<N;i++) {
//            int v = Integer.parseInt(br.readLine());
//            for(int j=0;j<M;j++) {
//                boxs[i][j] = (v % 10);
//                v = v / 10;
//            }
//        }
        for(int i=0;i<N;i++) {
            String s = br.readLine();
            for(int j=0;j<M;j++) {
                boxs[i][j] = s.charAt(j) - '0';
            }
        }


        int max = 0;
        int sum = 0;
        for(int i=0;i<(1<<(N*M));i++) {
            sum = 0;
            for(int j=0;j<N;j++) {
                int temp = 0;
                for(int k=0;k<M;k++) {
                    if((i & (1 << j * M + k)) != 0) {
                        temp *= 10;
                        temp += boxs[j][k];
                    } else {
                        sum += temp;
                        temp = 0;
                    }
                }
                sum += temp;
            }

            for(int j=0;j<M;j++) {
                int temp = 0;
                for(int k=0;k<N;k++) {
                    if((~i & (1 << k * M + j)) != 0) {
                        temp *= 10;
                        temp += boxs[k][j];
                    } else {
                        sum += temp;
                        temp = 0;
                    }
                }
                sum += temp;
            }

            if(sum > max) {
                max = sum;
            }
        }
        System.out.println(max);

    }
}
*/
