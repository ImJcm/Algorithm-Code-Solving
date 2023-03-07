import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
톱니바퀴 (2)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	2491	1626	1369	68.313%
문제
총 8개의 톱니를 가지고 있는 톱니바퀴 T개가 아래 그림과 같이 일렬로 놓여져 있다. 또, 톱니는 N극 또는 S극 중 하나를 나타내고 있다. 톱니바퀴에는 번호가 매겨져 있는데, 가장 왼쪽 톱니바퀴가 1번, 그 오른쪽은 2번, ..., 가장 오른쪽 톱니바퀴는 T번이다. 아래 그림은 T가 4인 경우이다.



이때, 톱니바퀴를 총 K번 회전시키려고 한다. 톱니바퀴의 회전은 한 칸을 기준으로 한다. 회전은 시계 방향과 반시계 방향이 있고, 아래 그림과 같이 회전한다.





톱니바퀴를 회전시키려면, 회전시킬 톱니바퀴와 회전시킬 방향을 결정해야 한다. 톱니바퀴가 회전할 때, 서로 맞닿은 극에 따라서 옆에 있는 톱니바퀴를 회전시킬 수도 있고, 회전시키지 않을 수도 있다. 톱니바퀴 A를 회전할 때, 그 옆에 있는 톱니바퀴 B와 서로 맞닿은 톱니의 극이 다르다면, B는 A가 회전한 방향과 반대방향으로 회전하게 된다. 예를 들어, 아래와 같은 경우를 살펴보자.



두 톱니바퀴의 맞닿은 부분은 초록색 점선으로 묶여있는 부분이다. 여기서, 3번 톱니바퀴를 반시계 방향으로 회전했다면, 4번 톱니바퀴는 시계 방향으로 회전하게 된다. 2번 톱니바퀴는 맞닿은 부분이 S극으로 서로 같기 때문에, 회전하지 않게 되고, 1번 톱니바퀴는 2번이 회전하지 않았기 때문에, 회전하지 않게 된다. 따라서, 아래 그림과 같은 모양을 만들게 된다.



위와 같은 상태에서 1번 톱니바퀴를 시계 방향으로 회전시키면, 2번 톱니바퀴가 반시계 방향으로 회전하게 되고, 2번이 회전하기 때문에, 3번도 동시에 시계 방향으로 회전하게 된다. 4번은 3번이 회전하지만, 맞닿은 극이 같기 때문에 회전하지 않는다. 따라서, 아래와 같은 상태가 된다.



톱니바퀴 T개의 초기 상태와 톱니바퀴를 회전시킨 방법이 주어졌을 때, 최종 톱니바퀴의 상태를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 톱니바퀴의 개수 T (1 ≤ T ≤ 1,000)가 주어진다.

둘째 줄부터 T개의 줄에 톱니바퀴의 상태가 가장 왼쪽 톱니바퀴부터 순서대로 주어진다. 상태는 8개의 정수로 이루어져 있고, 12시방향부터 시계방향 순서대로 주어진다. N극은 0, S극은 1로 나타나있다.

다음 줄에는 회전 횟수 K(1 ≤ K ≤ 1,000)가 주어진다. 다음 K개 줄에는 회전시킨 방법이 순서대로 주어진다. 각 방법은 두 개의 정수로 이루어져 있고, 첫 번째 정수는 회전시킨 톱니바퀴의 번호, 두 번째 정수는 방향이다. 방향이 1인 경우는 시계 방향이고, -1인 경우는 반시계 방향이다.

출력
총 K번 회전시킨 이후에 12시방향이 S극인 톱니바퀴의 개수를 출력한다.

예제 입력 1
4
10101111
01111101
11001110
00000010
2
3 -1
1 1
예제 출력 1
3
예제 입력 2
4
11111111
11111111
11111111
11111111
3
1 1
2 1
3 1
예제 출력 2
4
예제 입력 3
4
10001011
10000011
01011011
00111101
5
1 1
2 1
3 1
4 1
1 -1
예제 출력 3
2
예제 입력 4
4
10010011
01010011
11100011
01010101
8
1 1
2 1
3 1
4 1
1 -1
2 -1
3 -1
4 -1
예제 출력 4
2
예제 입력 5
5
10010011
01010011
11100011
01010101
01010011
10
1 1
2 1
3 1
4 1
1 -1
2 -1
3 -1
4 -1
5 1
5 -1
예제 출력 5
5
 */
/*
    기어의 상태(S,N), 기어마다 맞닿는 상태, 회전하는 상태를 저장하는 배열을 이용하여 알고리즘 구현
    알고리즘의 핵심 : 하나의 톱니바퀴가 회전할 때 이후에 수행될 톱니바퀴가 이전 상태의 톱니바퀴가 회전된 상태를 요구하지
    않으므로, 한 톱니바퀴가 회전할 때 이후에 수행될 톱니바퀴 회전 상태를 회전이 가능할 때까지 저장하고 더이상 회전이
    불가능할 때 저장된 회전 동작을 수행하고 이후 입력된 회전 동작을 반복한다.
 */
public class BOJ15662 {
    static int T,K;
    static int[][] Gear, rotate, side;
    static ArrayList<int[]> step = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        T = Integer.parseInt(br.readLine());

        Gear = new int[T+1][8];
        side = new int[T+1][2];
        for(int i=1;i<=T;i++) {
            String[] g = br.readLine().split("");
            for(int j=0;j<8;j++) {
                Gear[i][j] = Integer.parseInt(g[j]);
            }
            side[i][0] = Gear[i][6];
            side[i][1] = Gear[i][2];
        }
        K = Integer.parseInt(br.readLine());

        rotate = new int[K][2];
        for(int i=0;i<K;i++) {
            String[] r = br.readLine().split(" ");
            rotate[i][0] = Integer.parseInt(r[0]);
            rotate[i][1] = Integer.parseInt(r[1]);
        }

        solve();
    }

    static void solve() {
        for(int i=0;i<K;i++) {
            //System.out.println("Step : " + (i+1));
            int start = rotate[i][0];
            int doing = rotate[i][1];
            step.add(new int[]{start,doing});

            //left
            int left = start;
            int right = start;
            int tmp_do = doing;
            while(left > 1) {
                tmp_do = -tmp_do;
                if(side[left][0] != side[left-1][1]) {
                    step.add(new int[]{left-1,tmp_do});
                    left--;
                } else {
                    break;
                }
            }

            tmp_do = doing;
            while(right < T) {
                tmp_do = -tmp_do;
                if(side[right][1] != side[right+1][0]) {
                    step.add(new int[]{right+1,tmp_do});
                    right++;
                } else {
                    break;
                }
            }

            for(int[] g : step) {
                //System.out.println(g[0] + " : " + g[1]);
                gear_rotate(g[0],g[1]);
            }
            step.clear();
        }

        //End_print
        int count = 0;
        for(int i=1;i<=T;i++) {
            if(Gear[i][0] == 1) {
                count++;
            }
        }
        System.out.println(count);
    }

    static void gear_rotate(int g,int d) {
        if(d == 1) {
            int tmp = Gear[g][7];
            for(int i=7;i>0;i--) {
                Gear[g][i] = Gear[g][i-1];
            }
            Gear[g][0] = tmp;

            side[g][0] = Gear[g][6];
            side[g][1] = Gear[g][2];
        } else {
            // -1
            int tmp = Gear[g][0];
            for(int i=0;i<7;i++) {
                Gear[g][i] = Gear[g][i+1];
            }
            Gear[g][7] = tmp;

            side[g][0] = Gear[g][6];
            side[g][1] = Gear[g][2];
        }
    }
}
