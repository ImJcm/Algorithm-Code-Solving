package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
돌무더기 게임 1

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	1024 MB	1066	321	234	33.429%
문제
3개의 돌무더기에 돌이 각각 $x, y, z$개 있다. R과 B가 이 돌무더기에서 게임을 한다. 각 플레이어는 다음의 한 가지 시행만을 할 수 있다.

돌이 있는 두 개의 돌무더기를 골라, 돌을 하나씩 가져가고, 나머지 하나의 돌무더기에 돌을 하나 추가한다.
더 이상 시행을 할 수 없는 사람이 이긴다.

R와 B가 최선을 다했을 때 이기는 사람을 출력하여라. 게임은 R이 먼저 시작한다.

입력
첫 번째 줄에 테스트 케이스의 개수 $T$가 주어진다.

다음 $T$줄에 각각 세 개의 정수 $x, y, z$가 주어진다.

출력
각 테스트 케이스마다 이기는 사람을 출력한다.

제한
1 <= T <= 200,000
1 <= x,y,z <= 10^9
예제 입력 1
3
1 1 1
1 3 2
2 3 2
예제 출력 1
B
B
R
 */
/*
    알고리즘 핵심 : 홀수의 개수에 따라 승자가 결정되는 게임이론
 */
public class BOJ24678 {
    static int N;
    static int cnt;
    static int[][] XYZ;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        XYZ = new int[N][3];
        for(int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            cnt = 0;
            for(int j=0;j<3;j++) {
                XYZ[i][j] = Integer.parseInt(st.nextToken());
                if(XYZ[i][j] % 2 == 1) cnt++;
            }
            if(cnt == 3 || cnt == 2) System.out.println("B");
            else if(cnt == 1 || cnt == 0) System.out.println("R");
        }


    }
}
