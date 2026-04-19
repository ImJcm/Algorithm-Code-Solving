package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
두 동전

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	9536	4198	2847	42.360%
문제
N×M 크기의 보드와 4개의 버튼으로 이루어진 게임이 있다. 보드는 1×1크기의 정사각형 칸으로 나누어져 있고, 각각의 칸은 비어있거나, 벽이다. 두 개의 빈 칸에는 동전이 하나씩 놓여져 있고, 두 동전의 위치는 다르다.

버튼은 "왼쪽", "오른쪽", "위", "아래"와 같이 4가지가 있다. 버튼을 누르면 두 동전이 버튼에 쓰여 있는 방향으로 동시에 이동하게 된다.

동전이 이동하려는 칸이 벽이면, 동전은 이동하지 않는다.
동전이 이동하려는 방향에 칸이 없으면 동전은 보드 바깥으로 떨어진다.
그 외의 경우에는 이동하려는 방향으로 한 칸 이동한다.이동하려는 칸에 동전이 있는 경우에도 한 칸 이동한다.
두 동전 중 하나만 보드에서 떨어뜨리기 위해 버튼을 최소 몇 번 눌러야하는지 구하는 프로그램을 작성하시오.

입력
첫째 줄에 보드의 세로 크기 N과 가로 크기 M이 주어진다. (1 ≤ N, M ≤ 20)

둘째 줄부터 N개의 줄에는 보드의 상태가 주어진다.

o: 동전
.: 빈 칸
#: 벽
동전의 개수는 항상 2개이다.

출력
첫째 줄에 두 동전 중 하나만 보드에서 떨어뜨리기 위해 눌러야 하는 버튼의 최소 횟수를 출력한다. 만약, 두 동전을 떨어뜨릴 수 없거나, 버튼을 10번보다 많이 눌러야 한다면, -1을 출력한다.

예제 입력 1
1 2
oo
예제 출력 1
1
예제 입력 2
6 2
.#
.#
.#
o#
o#
##
예제 출력 2
4
예제 입력 3
6 2
..
..
..
o#
o#
##
예제 출력 3
3
예제 입력 4
5 3
###
.o.
###
.o.
###
예제 출력 4
-1
예제 입력 5
5 3
###
.o.
#.#
.o.
###
예제 출력 5
3
 */
/*
    Point1,2를 만들고, Point1,2에서 위,아래,왼쪽,오른쪽으로 이동할 때의 좌표들을 한 번의 동작으로 인식하게 끔
    ArrayList를 만들어 저장한다. point1 -> 4개, point2 -> 4개 +2 단위로 끊어보면 2개의 좌표를 4개의 방향으로 이동한
    좌표로 볼 수 있다.
    이를 반복하여 4개로 파생된 좌표들은 이전 부모 좌표의 count 값을 가지고, 이를 다른 4개의 좌표로 파생될 때 +1 씩하여
    연산횟수를 구할 수 있다.
    이때 사용한 알고리즘은 BFS로 Breadth-first-Search로 너비우선탐색이다. 최단거리를 찾는데 사용하는 알고리즘으로 이 경우에
    구슬이 떨어지는 최소의 이동 횟수를 구하기 때문에 사용할 수 있었다.
 */
class BOJ16197_Point {
    int x;
    int y;
    int count;
    BOJ16197_Point(int x,int y, int c) {
        this.x = x;
        this.y = y;
        this.count = c;
    }
}

public class BOJ16197 {
    static boolean possible = false;
    static int N,M,MIN_V = Integer.MAX_VALUE;
    static char[][] board;
    static boolean[][] visited1,visited2;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);

        board = new char[N][M];
        visited1 = new boolean[N][M];
        visited2 = new boolean[N][M];
        int x1=0,y1=0,x2=0,y2=0,count=0;
        
        for(int i=0;i<N;i++) {
            String[] s = br.readLine().split("");
            for(int j=0;j<M;j++) {
                board[i][j] = s[j].charAt(0);
                if(board[i][j] == 'o') {
                    if(count==0) {
                        x1=i;
                        y1=j;
                        count++;
                    } else if(count==1){
                        x2=i;
                        y2=j;
                        count++;
                    }
                }
            }
        }

        BFS(x1,y1,x2,y2);
    }

    static void BFS(int x1,int y1,int x2,int y2) {
        Queue<ArrayList<BOJ16197_Point>> queue = new LinkedList<>();
        queue.add(new ArrayList<BOJ16197_Point>() {{add(new BOJ16197_Point(x1,y1,0)); add(new BOJ16197_Point(x2,y2,0));}});

        while(!queue.isEmpty()) {
            ArrayList<BOJ16197_Point> nextStep;
            ArrayList<BOJ16197_Point> step = queue.poll();

            for(int i=0;i<step.size();i+=2) {
                nextStep = new ArrayList<>();
                BOJ16197_Point p1 = step.get(i);
                BOJ16197_Point p2 = step.get(i+1);

                if(p1.count > 10 || p2.count > 10) {
                    System.out.println("-1");
                    return;
                }

                //둘다 떨어진 경우
                if((p1.x == -1 || p1.y == -1) && (p2.x == -1 || p2.y == -1)) {
                    continue;
                } else {
                    if ((p1.x == -1 || p1.y == -1) || (p2.x == -1 || p2.y == -1)) {
                        //p1 또는 p2 둘중 하나만 떨어진 경우
                        //p1.count == p2.count
                        MIN_V = Math.min(MIN_V,p1.count);
                        System.out.println(p2.count);
                        possible = true;
                        return;
                    }
                }

                //실행시간을 줄이고자 미리 방문여부를 검사했지만 이렇게 할 때, 앞서 다음 point로 넘어가서 방문했다고 체크
                //하게 되어 같은 점에서 파생된 다른 점에서는 방문하지 않았지만 방문했다고 판단하는 문제가 있다.
                //따라서, 해당 코드를 주석처리함
                //queue에 데이터가 쌓이는 것을 대비해서 이동횟수 제한을 준 것으로보인다.
                /*//두 점이 이미 방문한 위치면 다음 위치 시작
                if(visited1[p1.x][p1.y] && visited2[p2.x][p2.y]) {
                    continue;
                }

                visited1[p1.x][p1.y] = visited2[p2.x][p2.y] = true;*/

                //nextStep = new ArrayList<>();
                //오른쪽, 왼쪽, 아래쪽, 위쪽
                for(int d=0;d<4;d++) {
                    switch (d) {
                        case 0:
                            if(p1.y+1 >= M) {
                                nextStep.add(new BOJ16197_Point(p1.x,-1,p1.count+1));
                            } else {
                                if(board[p1.x][p1.y+1] == '#') {
                                    nextStep.add(new BOJ16197_Point(p1.x,p1.y,p1.count+1));
                                } else {
                                    nextStep.add(new BOJ16197_Point(p1.x,p1.y+1,p1.count+1));
                                }
                            }

                            if(p2.y+1 >= M) {
                                nextStep.add(new BOJ16197_Point(p2.x,-1,p2.count+1));
                            } else {
                                if (board[p2.x][p2.y + 1] == '#') {
                                    nextStep.add(new BOJ16197_Point(p2.x, p2.y,p2.count+1));
                                } else {
                                    nextStep.add(new BOJ16197_Point(p2.x, p2.y + 1,p2.count+1));
                                }
                            }
                            break;
                        case 1:
                            if(p1.y-1 < 0) {
                                nextStep.add(new BOJ16197_Point(p1.x,-1,p1.count+1));
                            } else {
                                if(board[p1.x][p1.y-1] == '#') {
                                    nextStep.add(new BOJ16197_Point(p1.x,p1.y,p1.count+1));
                                } else {
                                    nextStep.add(new BOJ16197_Point(p1.x,p1.y-1,p1.count+1));
                                }
                            }

                            if(p2.y-1 < 0) {
                                nextStep.add(new BOJ16197_Point(p2.x,-1,p2.count+1));
                            } else {
                                if (board[p2.x][p2.y-1] == '#') {
                                    nextStep.add(new BOJ16197_Point(p2.x, p2.y,p2.count+1));
                                } else {
                                    nextStep.add(new BOJ16197_Point(p2.x, p2.y-1,p2.count+1));
                                }
                            }
                            break;
                        case 2:
                            if(p1.x+1 >= N) {
                                nextStep.add(new BOJ16197_Point(-1,p1.y,p1.count+1));
                            } else {
                                if(board[p1.x+1][p1.y] == '#') {
                                    nextStep.add(new BOJ16197_Point(p1.x,p1.y,p1.count+1));
                                } else {
                                    nextStep.add(new BOJ16197_Point(p1.x+1,p1.y,p1.count+1));
                                }
                            }

                            if(p2.x+1 >= N) {
                                nextStep.add(new BOJ16197_Point(-1,p2.y,p2.count+1));
                            } else {
                                if (board[p2.x+1][p2.y] == '#') {
                                    nextStep.add(new BOJ16197_Point(p2.x, p2.y,p2.count+1));
                                } else {
                                    nextStep.add(new BOJ16197_Point(p2.x+1, p2.y,p2.count+1));
                                }
                            }
                            break;
                        case 3:
                            if(p1.x-1 < 0) {
                                nextStep.add(new BOJ16197_Point(-1,p1.y,p1.count+1));
                            } else {
                                if(board[p1.x-1][p1.y] == '#') {
                                    nextStep.add(new BOJ16197_Point(p1.x,p1.y,p1.count+1));
                                } else {
                                    nextStep.add(new BOJ16197_Point(p1.x-1,p1.y,p1.count+1));
                                }
                            }

                            if(p2.x-1 < 0) {
                                nextStep.add(new BOJ16197_Point(-1,p2.y,p2.count+1));
                            } else {
                                if (board[p2.x-1][p2.y] == '#') {
                                    nextStep.add(new BOJ16197_Point(p2.x, p2.y,p2.count+1));
                                } else {
                                    nextStep.add(new BOJ16197_Point(p2.x-1, p2.y,p2.count+1));
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
                queue.add(nextStep);

            }
        }

        if(!possible) {
            System.out.println("-1");
        }
    }
}
