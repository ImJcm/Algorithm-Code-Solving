import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

/*
드래곤 커브

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	21775	12429	8436	54.883%
문제
드래곤 커브는 다음과 같은 세 가지 속성으로 이루어져 있으며, 이차원 좌표 평면 위에서 정의된다. 좌표 평면의 x축은 → 방향, y축은 ↓ 방향이다.

시작 점
시작 방향
세대
0세대 드래곤 커브는 아래 그림과 같은 길이가 1인 선분이다. 아래 그림은 (0, 0)에서 시작하고, 시작 방향은 오른쪽인 0세대 드래곤 커브이다.



1세대 드래곤 커브는 0세대 드래곤 커브를 끝 점을 기준으로 시계 방향으로 90도 회전시킨 다음 0세대 드래곤 커브의 끝 점에 붙인 것이다. 끝 점이란 시작 점에서 선분을 타고 이동했을 때, 가장 먼 거리에 있는 점을 의미한다.



2세대 드래곤 커브도 1세대를 만든 방법을 이용해서 만들 수 있다. (파란색 선분은 새로 추가된 선분을 나타낸다)



3세대 드래곤 커브도 2세대 드래곤 커브를 이용해 만들 수 있다. 아래 그림은 3세대 드래곤 커브이다.



즉, K(K > 1)세대 드래곤 커브는 K-1세대 드래곤 커브를 끝 점을 기준으로 90도 시계 방향 회전 시킨 다음, 그것을 끝 점에 붙인 것이다.

크기가 100×100인 격자 위에 드래곤 커브가 N개 있다. 이때, 크기가 1×1인 정사각형의 네 꼭짓점이 모두 드래곤 커브의 일부인 정사각형의 개수를 구하는 프로그램을 작성하시오. 격자의 좌표는 (x, y)로 나타내며, 0 ≤ x ≤ 100, 0 ≤ y ≤ 100만 유효한 좌표이다.

입력
첫째 줄에 드래곤 커브의 개수 N(1 ≤ N ≤ 20)이 주어진다. 둘째 줄부터 N개의 줄에는 드래곤 커브의 정보가 주어진다. 드래곤 커브의 정보는 네 정수 x, y, d, g로 이루어져 있다. x와 y는 드래곤 커브의 시작 점, d는 시작 방향, g는 세대이다. (0 ≤ x, y ≤ 100, 0 ≤ d ≤ 3, 0 ≤ g ≤ 10)

입력으로 주어지는 드래곤 커브는 격자 밖으로 벗어나지 않는다. 드래곤 커브는 서로 겹칠 수 있다.

방향은 0, 1, 2, 3 중 하나이고, 다음을 의미한다.

0: x좌표가 증가하는 방향 (→)
1: y좌표가 감소하는 방향 (↑)
2: x좌표가 감소하는 방향 (←)
3: y좌표가 증가하는 방향 (↓)
출력
첫째 줄에 크기가 1×1인 정사각형의 네 꼭짓점이 모두 드래곤 커브의 일부인 것의 개수를 출력한다.

예제 입력 1
3
3 3 0 1
4 2 1 3
4 2 2 1
예제 출력 1
4
예제 입력 2
4
3 3 0 1
4 2 1 3
4 2 2 1
2 7 3 4
예제 출력 2
11
예제 입력 3
10
5 5 0 0
5 6 0 0
5 7 0 0
5 8 0 0
5 9 0 0
6 5 0 0
6 6 0 0
6 7 0 0
6 8 0 0
6 9 0 0
예제 출력 3
8
예제 입력 4
4
50 50 0 10
50 50 1 10
50 50 2 10
50 50 3 10
예제 출력 4
1992
힌트
그림 링크 참조
https://www.acmicpc.net/problem/15685
 */
/*
    그림을 보면서 규칙이 있을까 생각하다가 0~N세대 까지 올라가면서 움직이는 순서를 반대로 진행하고, 기존 방향에서 +1 해주면 다음
    세대의 드래곤커브의 좌표가 생성된다.
    ex) 0 -> 1세대 (예제 그림 기준)
    1세대 드래곤 커브 : (0,0) -> 0방향 -> (1,0) -> 1방향 -> (1,-1)
    방향 순서 : 0 -> 1
    위의 알고리즘 방법을 따르면, 0 1 / 끝점을 시작으로 (1+1=2) (0+1=1)로 좌표를 이으면,
    (1,-1) -> 2방향 -> (0,-1) -> 1방향 -> (0,-2)

    따라서, 드래곤커브를 그리는 순서를 기억하고 있어야한다.
 */
public class BOJ15685 {
    //static int N,x,y,d,g;
    static int[][] board;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());

        board = new int[101][101];  //default value = 0

        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());

            dragon_Curve(x,y,d,g);
        }

        System.out.println(check());
    }

    //0세대 start -> direction -> end ( 1 )칸
    //1세대 0세대 작성하고 끝점을 기준으로 시계방향 90도 돌려서 잇기
    //2세대 0세대 -> 1세대 -> 90도 시계방향 회전 후 잇기
    //3세대 ...
    //...
    //10세대 아래와 같은 방법으로 동일
    static void dragon_Curve(int x,int y,int d,int g) {
        Stack<Integer> order = new Stack<>();
        int sx = x, sy = y;
        board[y][x] = 1;
        //g == 0
        if(d == 0) {
            if(0 <= sx && sx + 1 <= 100 && 0 <= sy && sy <= 100) {
                sx++;
            }
        } else if(d == 1) {
            if(0 <= sx && sx <= 100 && 0 <= sy - 1 && sy <= 100) {
                sy--;
            }
        } else if(d == 2) {
            if(0 <= sx - 1 && sx <= 100 && 0 <= sy && sy <= 100) {
                sx--;
            }
        } else if(d == 3) {
            if(0 <= sx && sx <= 100 && 0 <= sy && sy + 1<= 100) {
                sy++;
            }
        } else {
            //NOP
        }
        board[sy][sx] = 1;
        order.push(d);

        //g >= 1
        for(int i=1;i<=g;i++) {
            int os = order.size();
            for(int j=os-1;j>=0;j--) {
                int nd = (order.get(j) + 1) % 4;
                if(nd == 0) {
                    if(0 <= sx && sx + 1 <= 100 && 0 <= sy && sy <= 100) {
                        sx++;
                    }
                } else if(nd == 1) {
                    if(0 <= sx && sx <= 100 && 0 <= sy - 1 && sy <= 100) {
                        sy--;
                    }
                } else if(nd == 2) {
                    if(0 <= sx - 1 && sx <= 100 && 0 <= sy && sy <= 100) {
                        sx--;
                    }
                } else if(nd == 3) {
                    if(0 <= sx && sx <= 100 && 0 <= sy && sy + 1 <= 100) {
                        sy++;
                    }
                } else {
                    //Nop
                }
                board[sy][sx] = 1;
                order.push(nd);
            }
        }
    }

    static int check() {
        int count = 0;
        for(int y=0;y<=100;y++) {
            for(int x=0;x<=100;x++) {
                if(board[y][x] == 1) {
                    if(y+1 <= 100 && x+1 <= 100 && (board[y+1][x] == 1 && board[y+1][x+1] == 1 && board[y][x+1] == 1)) {
                        count++;
                    }
                }
            }
        }

        return count;
//        for(int i=0;i<=100;i++) {
//            for(int j=0;j<=100;j++) {
//                System.out.print(board[i][j] + " ");
//            }
//            System.out.println();
//        }
    }
}

