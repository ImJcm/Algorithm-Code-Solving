package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
https://www.acmicpc.net/problem/12100 <- 그림 참고
2048 (Easy)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	87401	25627	15035	26.442%
문제
2048 게임은 4×4 크기의 보드에서 혼자 즐기는 재미있는 게임이다. 이 링크를 누르면 게임을 해볼 수 있다.

이 게임에서 한 번의 이동은 보드 위에 있는 전체 블록을 상하좌우 네 방향 중 하나로 이동시키는 것이다. 이때, 같은 값을 갖는 두 블록이 충돌하면 두 블록은 하나로 합쳐지게 된다. 한 번의 이동에서 이미 합쳐진 블록은 또 다른 블록과 다시 합쳐질 수 없다. (실제 게임에서는 이동을 한 번 할 때마다 블록이 추가되지만, 이 문제에서 블록이 추가되는 경우는 없다)


<그림 1>	<그림 2>	<그림 3>
<그림 1>의 경우에서 위로 블록을 이동시키면 <그림 2>의 상태가 된다. 여기서, 왼쪽으로 블록을 이동시키면 <그림 3>의 상태가 된다.


<그림 4>	<그림 5>	<그림 6>	<그림 7>
<그림 4>의 상태에서 블록을 오른쪽으로 이동시키면 <그림 5>가 되고, 여기서 다시 위로 블록을 이동시키면 <그림 6>이 된다. 여기서 오른쪽으로 블록을 이동시켜 <그림 7>을 만들 수 있다.


<그림 8>	<그림 9>
<그림 8>의 상태에서 왼쪽으로 블록을 옮기면 어떻게 될까? 2가 충돌하기 때문에, 4로 합쳐지게 되고 <그림 9>의 상태가 된다.


<그림 10>	<그림 11>	<그림 12>	<그림 13>
<그림 10>에서 위로 블록을 이동시키면 <그림 11>의 상태가 된다.

<그림 12>의 경우에 위로 블록을 이동시키면 <그림 13>의 상태가 되는데, 그 이유는 한 번의 이동에서 이미 합쳐진 블록은 또 합쳐질 수 없기 때문이다.


<그림 14>	<그림 15>
마지막으로, 똑같은 수가 세 개가 있는 경우에는 이동하려고 하는 쪽의 칸이 먼저 합쳐진다. 예를 들어, 위로 이동시키는 경우에는 위쪽에 있는 블록이 먼저 합쳐지게 된다. <그림 14>의 경우에 위로 이동하면 <그림 15>를 만든다.

이 문제에서 다루는 2048 게임은 보드의 크기가 N×N 이다. 보드의 크기와 보드판의 블록 상태가 주어졌을 때, 최대 5번 이동해서 만들 수 있는 가장 큰 블록의 값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 보드의 크기 N (1 ≤ N ≤ 20)이 주어진다. 둘째 줄부터 N개의 줄에는 게임판의 초기 상태가 주어진다. 0은 빈 칸을 나타내며, 이외의 값은 모두 블록을 나타낸다. 블록에 쓰여 있는 수는 2보다 크거나 같고, 1024보다 작거나 같은 2의 제곱꼴이다. 블록은 적어도 하나 주어진다.

출력
최대 5번 이동시켜서 얻을 수 있는 가장 큰 블록을 출력한다.

예제 입력 1
3
2 2 2
4 4 4
8 8 8
예제 출력 1
16
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: clrmt, noorycode
문제의 오타를 찾은 사람: doju
알고리즘 분류
구현
브루트포스 알고리즘
시뮬레이션
백트래킹
 */
/*
    최대 5번 이동해서 얻을 수 있는 최대 값을 얻는 문제이므로, 백트랙킹, DFS를 이용하여 문제를 해결할 수 있다고 생각한다.
    그리고, 비트마스킹 문제로 분류된 이유가 2의 제곱근 형태로 수가 늘어나기 때문에 데이터 표현하는데 사용이 가능하기 때문이라고 생각한다.

    첫 오답 이유 : 블록을 각 방향으로 이동하여 합치는 부분에서 옆의 블록만(ex. [r][c+1] compare [r][c])을 고려하여 합치기 때문에 멀리 떨어진 동일한 블록을 합치는 상황에서 논리 오류가 발생하였다.
    코드 :
    for(int c=0;c<N-1;c++) {
        if(board[r][c] != 0) {
            if(board[r][c+1] == board[r][c]) {
                board[r][c] += board[r][c+1];
                board[r][c+1] = 0;
            }
        }
    }
    해결방법 :
        1. 두 블럭 사이의 빈 공간(0)을 합치는 코드 앞과 뒤부분에 추가하는 방법
        2. 블럭을 합치는 과정에서 빈 공간도 고려하여 멀리떨어진 동일한 블록이 있는지를 찾는 방법

        2번째 방법을 사용한 코드는 아래와 같다.

    비트마스킹을 사용하지 않고 구현 + DFS를 활용하여 코드를 구현하였다.
 */
public class BOJ12100 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,max_value=0;
    static int[][] init_board;

    public static void main(String[] args) throws IOException {
        board_init_setting();

        solve(0, init_board);

        System.out.println(max_value);
    }

    static void solve(int depth, int[][] board) {
        if(depth == 5) {
            check_board(board);
            return;
        }

        //i=(0-왼쪽, 1-위쪽, 2-오른쪽, 3-아래쪽 , 시계방향으로 설정)
        for(int i=0;i<4;i++) {
            int[][] next_board = board_deepCopy(board);

            solve(depth+1, moving_board(i,next_board));
        }
    }

    static int[][] moving_board(int direction, int[][] board) {
        if(direction==0) {
            for(int r=0;r<N;r++) {
                for(int c=0;c<N-1;c++) {
                    int n = 1;
                    if(board[r][c] != 0) {
                        while(c+n < N-1 && board[r][c+n] == 0) {
                            if(board[r][c] == board[r][c+n]) {
                                break;
                            }
                            n++;
                        }
                        if(board[r][c+n] == board[r][c]) {
                            board[r][c] += board[r][c+n];
                            board[r][c+n] = 0;
                        }
                    }
                }

                for(int c=0;c<N-1;c++) {
                    int n = 1;
                    if(board[r][c] == 0) {
                        while(c+n < N-1 && board[r][c+n] == 0) {
                            n++;
                        }
                        board[r][c] = board[r][c+n];
                        board[r][c+n] = 0;
                    }
                }
            }
        } else if(direction==1) {
            for(int c=0;c<N;c++) {
                for (int r = 0; r < N - 1; r++) {
                    int n = 1;
                    if (board[r][c] != 0) {
                        while(r+n < N-1 && board[r+n][c] == 0) {
                            if(board[r][c] == board[r+n][c]) {
                                break;
                            }
                            n++;
                        }

                        if (board[r + n][c] == board[r][c]) {
                            board[r][c] += board[r + n][c];
                            board[r + n][c] = 0;
                        }
                    }
                }

                for (int r = 0; r < N - 1; r++) {
                    int n = 1;
                    if (board[r][c] == 0) {
                        while(r+n < N-1 && board[r+n][c] == 0) {
                            n++;
                        }
                        board[r][c] = board[r + n][c];
                        board[r + n][c] = 0;
                    }
                }
            }
        } else if(direction==2) {
            for(int r=N-1;r>=0;r--) {
                for(int c=N-1;c>0;c--) {
                    int n = 1;
                    if (board[r][c] != 0) {
                        while(c-n > 0 && board[r][c-n] == 0) {
                            if(board[r][c] == board[r][c-n]) {
                                break;
                            }
                            n++;
                        }

                        if (board[r][c - n] == board[r][c]) {
                            board[r][c] += board[r][c - n];
                            board[r][c - n] = 0;
                        }
                    }
                }

                for(int c=N-1;c>0;c--) {
                    int n = 1;
                    if(board[r][c] == 0) {
                        while(c-n > 0 && board[r][c-n] == 0) {
                            n++;
                        }
                        board[r][c] = board[r][c-n];
                        board[r][c-n] = 0;
                    }
                }
            }
        } else if(direction==3) {
            for(int c=N-1;c>=0;c--) {
                for (int r = N-1; r>0; r--) {
                    int n = 1;
                    if (board[r][c] != 0) {
                        while(r-n > 0 && board[r-n][c] == 0) {
                            if(board[r][c] == board[r-n][c]) {
                                break;
                            }
                            n++;
                        }
                        if (board[r - n][c] == board[r][c]) {
                            board[r][c] += board[r - n][c];
                            board[r - n][c] = 0;
                        }
                    }
                }

                for (int r = N - 1; r > 0; r--) {
                    int n = 1;
                    if (board[r][c] == 0) {
                        while(r-n > 0 && board[r-n][c] == 0) {
                            n++;
                        }
                        board[r][c] = board[r - n][c];
                        board[r - n][c] = 0;
                    }
                }
            }
        }

        return board;
    }

    static void check_board(int[][] board) {
        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++) {
                max_value = max_value < board[i][j] ? board[i][j] : max_value;
            }
        }
    }

    static void board_init_setting() throws IOException{
        N = Integer.parseInt(br.readLine());

        init_board = new int[N][N];

        for(int i=0;i<N;i++) {
            init_board[i] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
    }

    static int[][] board_deepCopy(int[][] board) {
        int[][] new_board = new int[N][N];

        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++) {
                new_board[i][j] = board[i][j];
            }
        }

        return new_board;
    }

}
