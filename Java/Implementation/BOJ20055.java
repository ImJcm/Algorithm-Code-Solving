package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
컨베이어 벨트 위의 로봇

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	14193	8282	5499	56.749%
문제
길이가 N인 컨베이어 벨트가 있고, 길이가 2N인 벨트가 이 컨베이어 벨트를 위아래로 감싸며 돌고 있다. 벨트는 길이 1 간격으로 2N개의 칸으로 나뉘어져 있으며, 각 칸에는 아래 그림과 같이 1부터 2N까지의 번호가 매겨져 있다.



벨트가 한 칸 회전하면 1번부터 2N-1번까지의 칸은 다음 번호의 칸이 있는 위치로 이동하고, 2N번 칸은 1번 칸의 위치로 이동한다. i번 칸의 내구도는 Ai이다. 위의 그림에서 1번 칸이 있는 위치를 "올리는 위치", N번 칸이 있는 위치를 "내리는 위치"라고 한다.

컨베이어 벨트에 박스 모양 로봇을 하나씩 올리려고 한다. 로봇은 올리는 위치에만 올릴 수 있다. 언제든지 로봇이 내리는 위치에 도달하면 그 즉시 내린다. 로봇은 컨베이어 벨트 위에서 스스로 이동할 수 있다. 로봇을 올리는 위치에 올리거나 로봇이 어떤 칸으로 이동하면 그 칸의 내구도는 즉시 1만큼 감소한다.

컨베이어 벨트를 이용해 로봇들을 건너편으로 옮기려고 한다. 로봇을 옮기는 과정에서는 아래와 같은 일이 순서대로 일어난다.

벨트가 각 칸 위에 있는 로봇과 함께 한 칸 회전한다.
가장 먼저 벨트에 올라간 로봇부터, 벨트가 회전하는 방향으로 한 칸 이동할 수 있다면 이동한다. 만약 이동할 수 없다면 가만히 있는다.
로봇이 이동하기 위해서는 이동하려는 칸에 로봇이 없으며, 그 칸의 내구도가 1 이상 남아 있어야 한다.
올리는 위치에 있는 칸의 내구도가 0이 아니면 올리는 위치에 로봇을 올린다.
내구도가 0인 칸의 개수가 K개 이상이라면 과정을 종료한다. 그렇지 않다면 1번으로 돌아간다.
종료되었을 때 몇 번째 단계가 진행 중이었는지 구해보자. 가장 처음 수행되는 단계는 1번째 단계이다.

입력
첫째 줄에 N, K가 주어진다. 둘째 줄에는 A1, A2, ..., A2N이 주어진다.

출력
몇 번째 단계가 진행 중일때 종료되었는지 출력한다.

제한
2 ≤ N ≤ 100
1 ≤ K ≤ 2N
1 ≤ Ai ≤ 1,000
예제 입력 1
3 2
1 2 1 2 1 2
예제 출력 1
2
예제 입력 2
3 6
10 10 10 10 10 10
예제 출력 2
31
예제 입력 3
4 5
10 1 10 6 3 4 8 2
예제 출력 3
24
예제 입력 4
5 8
100 99 60 80 30 20 10 89 99 100
예제 출력 4
472
 */
public class BOJ20055 {
    static int N,K,Cnt=1,basePoint=0,liftPos=0;
    static int[][] A;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        //0 : 내구도, 1 : 로봇 여부
        A = new int[2*N][2];

        st = new StringTokenizer(br.readLine());
        for(int i=0;i<2*N;i++) {
            A[i][0] = Integer.parseInt(st.nextToken());
            A[i][1] = 0;
        }

        while(true) {
            //1. 벨트가 각 칸 위에 있는 로봇과 함께 한 칸 회전한다.
            int tmp_0 = A[2*N-1][0];
            int tmp_1 = A[2*N-1][1];
            for(int i=2*N-1;i>0;i--) {
                A[i][0] = A[i-1][0];
                A[i][1] = A[i-1][1];
                //내리는 위치
                if(i == N-1) {
                    A[i][1] = 0;
                }
            }
            A[0][0] = tmp_0;
            A[0][1] = tmp_1;
            //2. 가장 먼저 벨트에 올라간 로봇부터, 벨트가 회전하는 방향으로 한 칸 이동할 수 있다면 이동한다. 만약 이동할 수 없다면 가만히 있는다.
            for(int i=N-2;i>0;i--) {
                if(A[i][1] == 1 && A[i+1][0] > 0 && A[i+1][1] == 0) {
                    A[i+1][0] = A[i+1][0] > 0 ? A[i+1][0] - 1 : 0;
                    A[i+1][1] = 1;
                    if(i+1 == N-1) {
                        A[i+1][1] = 0;
                    }
                    A[i][1] = 0;
                }
            }
            //3. 올리는 위치에 있는 칸의 내구도가 0이 아니면 올리는 위치에 로봇을 올린다.
            /*liftPos = (liftPos == 0 ? 2*N-1 : liftPos-1);
            if(A[liftPos][0] > 0) {
                A[liftPos][0] = (A[liftPos][0] > 0 ? A[liftPos][0] - 1 : 0);
                A[liftPos][1] = 1;
            }*/
            if(A[liftPos][0] > 0) {
                A[liftPos][0] = (A[liftPos][0] > 0 ? A[liftPos][0] - 1 : 0);
                A[liftPos][1] = 1;
            }

            //4. 내구도가 0인 칸의 개수가 K개 이상이라면 과정을 종료한다. 그렇지 않다면 1번으로 돌아간다.
            basePoint = 0;
            for(int i=0;i<2*N;i++) {
                if(A[i][0] == 0) {
                    basePoint++;
                }
            }
            if(basePoint >= K) {
                System.out.println(Cnt);
                break;
            }
            Cnt++;
        }
    }
}
