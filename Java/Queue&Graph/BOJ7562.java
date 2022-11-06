import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
나이트의 이동 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	42716	21625	16158	49.555%
문제
체스판 위에 한 나이트가 놓여져 있다. 나이트가 한 번에 이동할 수 있는 칸은 아래 그림에 나와있다. 나이트가 이동하려고 하는 칸이 주어진다. 나이트는 몇 번 움직이면 이 칸으로 이동할 수 있을까?



입력
입력의 첫째 줄에는 테스트 케이스의 개수가 주어진다.

각 테스트 케이스는 세 줄로 이루어져 있다. 첫째 줄에는 체스판의 한 변의 길이 l(4 ≤ l ≤ 300)이 주어진다. 체스판의 크기는 l × l이다. 체스판의 각 칸은 두 수의 쌍 {0, ..., l-1} × {0, ..., l-1}로 나타낼 수 있다. 둘째 줄과 셋째 줄에는 나이트가 현재 있는 칸, 나이트가 이동하려고 하는 칸이 주어진다.

출력
각 테스트 케이스마다 나이트가 최소 몇 번만에 이동할 수 있는지 출력한다.

예제 입력 1
3
8
0 0
7 0
100
0 0
30 50
10
1 1
1 1
예제 출력 1
5
28
0
 */
public class BOJ7562 {
    static int N, I;
    static int x,y, dx,dy;
    static int move_count = 0;
    static int[][] board;
    static int[][] d = {
            //clock direction
            {-1,-2},    //11
            {-2,-1},    //10
            {-1,2},     //7
            {-2,1},     //8
            {1,-2},     //1
            {2,-1},     //2
            {2,1},      //4
            {1,2}       //5
    };

    public static class point {
        int x,y;

        point(int x,int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(br.readLine());

        while(N-- > 0) {
            I = Integer.parseInt(br.readLine());
            board = new int[I][I];
            for(int i=0;i<I;i++) {
                Arrays.fill(board[i],0);
            }
            String[] str = br.readLine().split(" ");
            x = Integer.parseInt(str[0]);
            y = Integer.parseInt(str[1]);

            str = br.readLine().split(" ");
            dx = Integer.parseInt(str[0]);
            dy = Integer.parseInt(str[1]);

            sb.append(Integer.toString(BFS(new point(x,y),new point(dx,dy))) + "\n");
            //System.out.println(BFS(new point(x,y),new point(dx,dy)));
        }
        System.out.println(sb);
    }

    static int BFS(point s, point e) {
        //s = start, e = end
        //bfs 구현
        //board 시작 지점의 값을 기준으로 한번씩 이동이 가능할 때
        //이전 칸의 값 += 1을 하면서 Math.min을 적용하여 최소 이동만을
        //저장하여 dx,dy의 최소 이동 수를 sb에 추가하고, sout의 연산시간을
        //줄이고자 한번에 출력한다.
        //위의 연상 과정 처럼 목적지로 갈 수 있는 최소 경로 값을 찾는 문제라고 생각하여
        //나이트 체스가 이미 도달했던 (x,y)에 다시 오더라도 누적된 움직임보다
        //작으면 최소값으로 갱신해주는 문제인 줄 알았으나, 나이트가 이미 도착했던 좌표에 다시 온다면
        //bfs를 수행하는 시점에서 같은 좌표에 오는 경우는 이미 최소 경로보다 높은 경로 값을 가지기 때문에
        //고려하지 않고 중복되는 좌표인 경우를 제외한다.
        Queue<point> q = new LinkedList<>();
        q.offer(s);

        while(!q.isEmpty()) {
            point n = q.poll();
            
            //목표지점 도착 시, 바로 board[e.x][e.y] 출력
            if(n.x == e.x && n.y == e.y) {
                return board[e.x][e.y];
            }
            for(int i=0;i<d.length;i++) {
                int nx = n.x + d[i][0];
                int ny = n.y + d[i][1];

                if(nx < 0 || ny < 0 || nx >= I || ny >= I) continue;
                if(board[nx][ny] != 0) continue;
                //fault think
                //if(board[nx][ny] != 0 && board[nx][ny] < board[n.x][n.y] + 1) continue;

                q.offer(new point(nx,ny));
                board[nx][ny] = board[n.x][n.y] + 1;
                //fault think
                //board[nx][ny] = Math.min(board[nx][ny], board[n.x][n.y] + 1);
            }
        }
        return 0;
    }
}
