package Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
경사로

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	23062	12689	9150	55.922%
문제
크기가 N×N인 지도가 있다. 지도의 각 칸에는 그 곳의 높이가 적혀져 있다.

오늘은 이 지도에서 지나갈 수 있는 길이 몇 개 있는지 알아보려고 한다. 길이란 한 행 또는 한 열 전부를 나타내며, 한쪽 끝에서 다른쪽 끝까지 지나가는 것이다.

다음과 같은 N=6인 경우 지도를 살펴보자.



이때, 길은 총 2N개가 있으며, 아래와 같다.



길을 지나갈 수 있으려면 길에 속한 모든 칸의 높이가 모두 같아야 한다. 또는, 경사로를 놓아서 지나갈 수 있는 길을 만들 수 있다. 경사로는 높이가 항상 1이며, 길이는 L이다. 또, 개수는 매우 많아 부족할 일이 없다. 경사로는 낮은 칸과 높은 칸을 연결하며, 아래와 같은 조건을 만족해야한다.

경사로는 낮은 칸에 놓으며, L개의 연속된 칸에 경사로의 바닥이 모두 접해야 한다.
낮은 칸과 높은 칸의 높이 차이는 1이어야 한다.
경사로를 놓을 낮은 칸의 높이는 모두 같아야 하고, L개의 칸이 연속되어 있어야 한다.
아래와 같은 경우에는 경사로를 놓을 수 없다.

경사로를 놓은 곳에 또 경사로를 놓는 경우
낮은 칸과 높은 칸의 높이 차이가 1이 아닌 경우
낮은 지점의 칸의 높이가 모두 같지 않거나, L개가 연속되지 않은 경우
경사로를 놓다가 범위를 벗어나는 경우
L = 2인 경우에 경사로를 놓을 수 있는 경우를 그림으로 나타내면 아래와 같다.



경사로를 놓을 수 없는 경우는 아래와 같다.



위의 그림의 가장 왼쪽부터 1번, 2번, 3번, 4번 예제라고 했을 때, 1번은 높이 차이가 1이 아니라서, 2번은 경사로를 바닥과 접하게 놓지 않아서, 3번은 겹쳐서 놓아서, 4번은 기울이게 놓아서 불가능한 경우이다.

가장 위에 주어진 그림 예의 경우에 지나갈 수 있는 길은 파란색으로, 지나갈 수 없는 길은 빨간색으로 표시되어 있으며, 아래와 같다. 경사로의 길이 L = 2이다.



지도가 주어졌을 때, 지나갈 수 있는 길의 개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N (2 ≤ N ≤ 100)과 L (1 ≤ L ≤ N)이 주어진다. 둘째 줄부터 N개의 줄에 지도가 주어진다. 각 칸의 높이는 10보다 작거나 같은 자연수이다.

출력
첫째 줄에 지나갈 수 있는 길의 개수를 출력한다.

예제 입력 1
6 2
3 3 3 3 3 3
2 3 3 3 3 3
2 2 2 3 2 3
1 1 1 2 2 2
1 1 1 3 3 1
1 1 2 3 3 2
예제 출력 1
3
예제 입력 2
6 2
3 2 1 1 2 3
3 2 2 1 2 3
3 2 2 2 3 3
3 3 3 3 3 3
3 3 3 3 2 2
3 3 3 3 2 2
예제 출력 2
7
예제 입력 3
6 3
3 2 1 1 2 3
3 2 2 1 2 3
3 2 2 2 3 3
3 3 3 3 3 3
3 3 3 3 2 2
3 3 3 3 2 2
예제 출력 3
3
예제 입력 4
6 1
3 2 1 1 2 3
3 2 2 1 2 3
3 2 2 2 3 3
3 3 3 3 3 3
3 3 3 3 2 2
3 3 3 3 2 2
예제 출력 4
11
 */
public class BOJ14890 {
    static int N,L;
    static int[][] map0,map90;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        map0 = new int[N][N];
        map90 = new int[N][N];
        visited = new boolean[N][N];
        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;j++) {
                map0[i][j] = Integer.parseInt(st.nextToken());
                map90[j][i] = map0[i][j];
                visited[i][j] = false;
            }
        }

        System.out.println(solve(map0) + solve(map90));
        //System.out.println(solve(map90));
        //System.out.println(solve(map0));
    }
    static int solve(int[][] map) {
        int count = 0;
        //→
        for(int r=0;r<N;r++) {
            Arrays.fill(visited[r],false);
            boolean ok = true;
            for(int c=0;c<N-1;c++) {
                if(Math.abs(map[r][c] - map[r][c+1]) > 1) {
                    ok = false;
                    break;
                } else {
                    boolean runway = true;
                    // distance = 1 or -1 or 0
                    if(map[r][c] - map[r][c+1] == 1) {
                        if(c + L < N) {
                            int eq = map[r][c+1];
                            for(int i=0;i<L;i++) {
                                if(eq != map[r][c+1+i] || visited[r][c+1+i] == true) {
                                    runway = false;
                                    break;
                                }
                            }
                            if(runway) {
                                for(int i=c+1;i<c+1+L;i++) {
                                    visited[r][i] = true;
                                }
                                c += L-1;
                            } else {
                                ok = false;
                                break;
                            }
                        } else {
                            ok = false;
                            break;
                        }
                    } else if(map[r][c] - map[r][c+1] == -1){
                        if(c - L + 1 >= 0) {
                            int eq = map[r][c];
                            for(int i=0;i<L;i++) {
                                if(eq != map[r][c-i] || visited[r][c-i] == true) {
                                    runway = false;
                                    break;
                                }
                            }
                            if(runway) {
                                for(int i=c;i>c-L;i--) {
                                    visited[r][i] = true;
                                }
                            } else {
                                ok = false;
                                break;
                            }
                        } else {
                            ok = false;
                            break;
                        }
                    } else {
                        //dist == 0
                    }
                }
            }
            if(ok) {
                count++;
            }
            Arrays.fill(visited[r],false);
        }

        //↓ : 배열 초기화에 추가적인 반복문을 사용하므로, 길을 찾는 알고리즘에 지도 배열을 입력하여 가능한 길을
        //출력하는 형태로 변경하는 것이 효율적을 보인다
        //0도 회전한 지도 배열 + 90도 회전한 지도 배열을 이용하는 것이 효율적으로 보임
        /*
        for(int c=0;c<N;c++) {
            Arrays.fill(visited[c],false);
            boolean ok = true;
            for(int r=0;r<N-1;r++) {
                if(Math.abs(map[r][c] - map[r][c+1]) > 1) {
                    ok = false;
                    break;
                } else {
                    boolean runway = true;
                    // distance = 1 or -1 or 0
                    if(map[r][c] - map[r][c+1] == 1) {
                        if(r + 1 + L < N) {
                            for(int i=r+1;i<r+1+L;i++) {
                                if(map[i][c] - map[i+1][c] != 0 || visited[i][c] == true) {
                                    runway = false;
                                    break;
                                }
                            }
                            if(runway) {
                                for(int i=r+1;i<r+1+L;i++) {
                                    visited[i][c] = true;
                                }
                                r += L;
                            } else {
                                ok = false;
                                break;
                            }
                        } else {
                            ok = false;
                            break;
                        }
                    } else if(map[r][c] - map[r][c+1] == -1){
                        if(r - L + 1 >= 0) {
                            for(int i=r;i>r-L+1;i--) {
                                if(map[i][c] - map[i-1][c] != 0 || visited[i][c] == true || visited[i-1][c] == true) {
                                    runway = false;
                                    break;
                                }
                            }
                            if(runway) {
                                for(int i=r;i<r+1-L;i--) {
                                    visited[i][c] = true;
                                }
                            } else {
                                ok = false;
                                break;
                            }
                        } else {
                            ok = false;
                            break;
                        }
                    } else {
                        //dist == 0
                    }
                }
            }
            if(ok) {
                System.out.println("col : " + c);
                count++;
            }
            Arrays.fill(visited[c],false);
        }
         */

        //System.out.println(count);
        return count;
    }
}
