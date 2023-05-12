package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
배열 돌리기 6

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	595	351	231	58.779%
문제
크기가 2N×2N인 배열이 있을 때, 배열에 연산을 R번 적용하려고 한다. 연산은 8가지가 있고, 연산에는 단계 ℓ (0 ≤ ℓ < N)이 있다. 단계 ℓ은 배열을 부분 배열로 나눌때 사용하는 값이며, 부분 배열의 크기는 2ℓ×2ℓ가 되어야 한다. 단계는 연산을 수행할때마다 정한다.

다음은 크기가 23×23 배열을 단계 ℓ의 값에 따라 부분 배열로 나눈 것이다. 같은 부분 배열은 같은 색상으로 표시했다.


ℓ = 0	ℓ = 1	ℓ = 2
1번 연산은 각 부분 배열을 상하 반전시키는 연산이다.


배열	ℓ = 1, 1번 연산 적용
2번 연산은 각 부분 배열을 좌우 반전시키는 연산이다.


배열	ℓ = 2, 2번 연산 적용
3번 연산은 각 부분 배열을 오른쪽으로 90도 회전시키는 연산이다.


배열	ℓ = 1, 3번 연산 적용
4번 연산은 각 부분 배열을 왼쪽으로 90도 회전시키는 연산이다.


배열	ℓ = 2, 4번 연산 적용
5, 6, 7, 8번 연산은 부분 배열을 한 칸으로 생각하고 적용시킨다. 즉, 부분 배열의 안에 있는 값은 변하지 않는다.

5번 연산은 배열을 상하 반전시키는 연산이다.


배열	ℓ = 2, 5번 연산 적용
6번 연산은 배열을 좌우 반전시키는 연산이다.


배열	ℓ = 1, 6번 연산 적용
7번 연산은 오른쪽으로 90도 회전시키는 연산이다.


배열	ℓ = 1, 7번 연산 적용
8번 연산은 왼쪽으로 90도 회전시키는 연산이다.


배열	ℓ = 2, 8번 연산 적용
입력
첫째 줄에 N, R이 주어진다. 둘째 줄부터 2N개의 줄에 배열의 원소 A[i][j]가 주어진다. i번째 줄의 j번째 정수는 A[i][j]를 의미한다.

다음 R개의 줄에 배열에 적용시켜야 하는 연산이 한 줄에 하나씩 주어진다. 연산은 두 정수 k, ℓ로 이루어져 있고, k번 연산을 단계 ℓ로 적용한다는 의미이다.

출력
입력으로 주어진 배열에 R개의 연산을 순서대로 수행한 결과를 출력한다.

제한
1 ≤ N ≤ 7
1 ≤ R ≤ 1,000
1 ≤ k ≤ 8
0 ≤ ℓ < N
-999 ≤ A[i][j] ≤ 999
예제 입력 1
3 8
1 2 3 4 5 6 7 8
9 10 11 12 13 14 15 16
17 18 19 20 21 22 23 24
25 26 27 28 29 30 31 32
33 34 35 36 37 38 39 40
41 42 43 44 45 46 47 48
49 50 51 52 53 54 55 56
57 58 59 60 61 62 63 64
1 1
2 2
3 1
4 2
5 2
6 1
7 1
8 2
예제 출력 1
64 63 62 61 60 59 58 57
56 55 54 53 52 51 50 49
48 47 46 45 44 43 42 41
40 39 38 37 36 35 34 33
32 31 30 29 28 27 26 25
24 23 22 21 20 19 18 17
16 15 14 13 12 11 10 9
8 7 6 5 4 3 2 1
예제 입력 2
3 4
1 2 3 4 5 6 7 8
9 10 11 12 13 14 15 16
17 18 19 20 21 22 23 24
25 26 27 28 29 30 31 32
33 34 35 36 37 38 39 40
41 42 43 44 45 46 47 48
49 50 51 52 53 54 55 56
57 58 59 60 61 62 63 64
1 0
2 0
3 0
4 0
예제 출력 2
1 2 3 4 5 6 7 8
9 10 11 12 13 14 15 16
17 18 19 20 21 22 23 24
25 26 27 28 29 30 31 32
33 34 35 36 37 38 39 40
41 42 43 44 45 46 47 48
49 50 51 52 53 54 55 56
57 58 59 60 61 62 63 64
예제 입력 3
3 4
1 2 3 4 5 6 7 8
9 10 11 12 13 14 15 16
17 18 19 20 21 22 23 24
25 26 27 28 29 30 31 32
33 34 35 36 37 38 39 40
41 42 43 44 45 46 47 48
49 50 51 52 53 54 55 56
57 58 59 60 61 62 63 64
5 0
6 0
7 0
8 0
예제 출력 3
64 63 62 61 60 59 58 57
56 55 54 53 52 51 50 49
48 47 46 45 44 43 42 41
40 39 38 37 36 35 34 33
32 31 30 29 28 27 26 25
24 23 22 21 20 19 18 17
16 15 14 13 12 11 10 9
8 7 6 5 4 3 2 1
예제 입력 4
3 8
1 2 3 4 5 6 7 8
9 10 11 12 13 14 15 16
17 18 19 20 21 22 23 24
25 26 27 28 29 30 31 32
33 34 35 36 37 38 39 40
41 42 43 44 45 46 47 48
49 50 51 52 53 54 55 56
57 58 59 60 61 62 63 64
1 2
8 1
7 2
4 0
3 2
5 1
6 1
2 2
예제 출력 4
45 37 47 39 41 33 43 35
46 38 48 40 42 34 44 36
61 53 63 55 57 49 59 51
62 54 64 56 58 50 60 52
13 5 15 7 9 1 11 3
14 6 16 8 10 2 12 4
29 21 31 23 25 17 27 19
30 22 32 24 26 18 28 20
 */
/*
구현하는데 많은 시간이 걸렸다. 1~6까지 스스로 구현했지만, 7,8 동작에서 머리속으로 생각한 로직을 코드상으로 구현하는게 어려워서
다른 사람의 코드를 보고 로직을 변경하였다.
처음 생각한 로직은 7번 동작에서 5번 동작(좌우반전) 수행 후, 2^l 크기만큼 간격을 두고 [i][j] <-> [i+ssize][j+ssize]교환하는 로직을 생각했지만
로직이 틀린것을 알고 테두리의 좌표값별로 테두리 기준 좌표 [i][j] [x][y]의 값들을 in_diff 간격으로 교환이 이루어지도록 하면되는데
이 로직을 구현하기 힘들어서 다른사람의 코드를 보고 전체 회전 -> l 만큼 부분 반대회전하는 로직을 이용하였다.
 */
public class BOJ20327 {
    static int[][] board;
    static int N,R,K,L;
    static void rotation(int o,int l) {
        int msize = (int)Math.pow(2,N);
        int ssize = (int)Math.pow(2,l);

        switch(o) {
            case 1:
                for (int r = 0; r < msize; r += ssize) {
                    for (int c = 0; c < msize; c += ssize) {
                        //start point = s,c
                        for (int sr = 0; sr < ssize / 2; sr++) {
                            //부분집합 내에서 연산위치 sr, sc
                            //rotation 동작하는 대상 상대적 위치 in_diff
                            int in_diff = (ssize - 1) - (2 * sr);
                            for (int sc = 0; sc < ssize; sc++) {
                                int temp = board[r + sr][c + sc];
                                board[r + sr][c + sc] = board[r + sr + in_diff][c + sc];
                                board[r + sr + in_diff][c + sc] = temp;
                            }
                        }
                    }
                }
                break;
            case 2:
                for (int r = 0; r < msize; r += ssize) {
                    for (int c = 0; c < msize; c += ssize) {
                        //start point = s,c
                        for (int sr = 0; sr < ssize; sr++) {
                            //부분집합 내에서 연산위치 sr, sc
                            //rotation 동작하는 대상 상대적 위치 in_diff
                            for (int sc = 0; sc < ssize / 2; sc++) {
                                int in_diff = (ssize - 1) - (2 * sc);
                                int temp = board[r + sr][c + sc];
                                board[r + sr][c + sc] = board[r + sr][c + sc + in_diff];
                                board[r + sr][c + sc + in_diff] = temp;
                            }
                        }
                    }
                }
                break;
            case 3:
                for (int r = 0; r < msize; r += ssize) {
                    for (int c = 0; c < msize; c += ssize) {
                        for (int sr = 0; sr < ssize / 2; sr++) {
                            int in_diff = (ssize) - (2 * sr);
                            //90도 회전 로직
                            int[] tmp = new int[in_diff];
                            for (int i = 0; i < in_diff; i++) {
                                // 상단 배열 저장
                                tmp[i] = board[r + sr][c + sr + i];
                            }
                            //옮겨지는 대상 : 좌 -> 상
                            for (int i = 0; i < in_diff; i++) {
                                board[r + sr][c + sr + in_diff - 1 - i] = board[r + sr + i][c + sr];
                            }
                            //하 -> 좌
                            for (int i = 0; i < in_diff; i++) {
                                board[r + sr + i][c + sr] = board[r + sr + in_diff - 1][c + sr + i];
                            }
                            //우 -> 하
                            for (int i = 0; i < in_diff; i++) {
                                board[r + sr + in_diff - 1][c + sr + i] = board[r + sr + in_diff - 1 - i][c + sr + in_diff - 1];
                            }
                            //상 -> 우
                            for (int i = 0; i < in_diff - 1; i++) {
                                board[r + sr + in_diff - 1 - i][c + sr + in_diff - 1] = tmp[in_diff - 1 - i];
                            }
                        }
                    }
                }
                break;
            case 4:
                for (int r = 0; r < msize; r += ssize) {
                    for (int c = 0; c < msize; c += ssize) {
                        //start point = s,c
                        //회전횟수 = 외곽 태두리 갯수 = sr
                        for (int sr = 0; sr < ssize / 2; sr++) {
                            int in_diff = (ssize) - (2 * sr);
                            //90도 회전 로직
                            int[] tmp = new int[in_diff];
                            for (int i = 0; i < in_diff; i++) {
                                // 상단 배열 저장
                                tmp[i] = board[r + sr][c + sr + i];
                            }
                            //왼쪽 90
                            //옮겨지는 대상 : 우 -> 상
                            for (int i = 0; i < in_diff; i++) {
                                board[r + sr][c + sr + in_diff - 1 - i] = board[r + sr + in_diff - 1 - i][c + sr + in_diff - 1];
                            }
                            //하 -> 우
                            for (int i = 0; i < in_diff; i++) {
                                board[r + sr + i][c + sr + in_diff - 1] = board[r + sr + in_diff - 1][c + sr + in_diff - 1 - i];
                            }
                            //좌 -> 하
                            for (int i = 0; i < in_diff; i++) {
                                board[r + sr + in_diff - 1][c + sr + in_diff - 1 - i] = board[r + sr + in_diff - 1 - i][c + sr];
                            }
                            //상 -> 좌
                            for (int i = 0; i < in_diff; i++) {
                                board[r + sr + in_diff - 1 - i][c + sr] = tmp[i];
                            }
                        }
                    }
                }
                break;
            case 5:
                for (int r = 0; r < msize/2; r += ssize) {
                    //int in_diff = (l == 0 ? (msize - (2 * r)) - 1 : (msize - (2 * (r+l))));
                    int in_diff = (l == 0 ? msize - 2*r - 1 : msize - ssize - 2*r);
                    for (int c = 0; c < msize; c += ssize) {
                        //start point = s,c
                        for (int sr = 0; sr < ssize; sr++) {
                            for (int sc = 0; sc < ssize; sc++) {
                                int temp = board[r + sr][c + sc];
                                board[r + sr][c + sc] = board[r + sr + in_diff][c + sc];
                                board[r + sr + in_diff][c + sc] = temp;
                            }
                        }
                    }
                }
                break;
            case 6:
                for (int r = 0; r < msize; r += ssize) {
                    for (int c = 0; c < msize/2; c += ssize) {
                        int in_diff = (l == 0 ? msize - 2*c - 1 : msize - ssize - 2*c);
                        for (int sr = 0; sr < ssize; sr++) {
                            for (int sc = 0; sc < ssize; sc++) {
                                int temp = board[r + sr][c + sc];
                                board[r + sr][c + sc] = board[r + sr][c + sc + in_diff];
                                board[r + sr][c + sc + in_diff] = temp;
                            }
                        }
                    }
                }
                break;
            case 7:
                //좌우반전 -> ssize만큼 간격으로 [i][j] <-> [i+ssize][j+ssize]를 교환
                //단순히 ssize만 더하면 안될것 같다. 로직오류
                //https://littlesam95.tistory.com/m/entry/%EB%B0%B1%EC%A4%80BOJ-20327-%EB%B0%B0%EC%97%B4-%EB%8F%8C%EB%A6%AC%EA%B8%B0-6Gold-3
                //7,8회전은 - 3,4번은 활용해보는 방법으로 전환
                //rotation 4(전체) -> rotation 3(L)만큼
                //3,4 번 연산의 경우 l=0의 값은 원상태라 전체를 회전하는 방법은 따로 구해야한다.
                for (int sr = 0; sr < msize / 2; sr++) {
                    int in_diff = msize - (2 * sr);
                    //90도 회전 로직
                    int[] tmp = new int[in_diff];
                    for (int i = 0; i < in_diff; i++) {
                        // 상단 배열 저장
                        tmp[i] = board[sr][sr + i];
                    }
                    //옮겨지는 대상 : 좌 -> 상
                    for (int i = 0; i < in_diff; i++) {
                        board[sr][sr + in_diff - 1 - i] = board[sr + i][sr];
                    }
                    //하 -> 좌
                    for (int i = 0; i < in_diff; i++) {
                        board[sr + i][sr] = board[sr + in_diff - 1][sr + i];
                    }
                    //우 -> 하
                    for (int i = 0; i < in_diff; i++) {
                        board[sr + in_diff - 1][sr + i] = board[sr + in_diff - 1 - i][sr + in_diff - 1];
                    }
                    //상 -> 우
                    for (int i = 0; i < in_diff - 1; i++) {
                        board[sr + in_diff - 1 - i][sr + in_diff - 1] = tmp[in_diff - 1 - i];
                    }
                }
                rotation(4,l);
                break;
            case 8:
                for (int sr = 0; sr < msize / 2; sr++) {
                    int in_diff = msize - (2 * sr);
                    //90도 회전 로직
                    int[] tmp = new int[in_diff];
                    for (int i = 0; i < in_diff; i++) {
                        // 상단 배열 저장
                        tmp[i] = board[sr][sr + i];
                    }
                    //왼쪽 90
                    //옮겨지는 대상 : 우 -> 상
                    for (int i = 0; i < in_diff; i++) {
                        board[sr][sr + in_diff - 1 - i] = board[sr + in_diff - 1 - i][sr + in_diff - 1];
                    }
                    //하 -> 우
                    for (int i = 0; i < in_diff; i++) {
                        board[sr + i][sr + in_diff - 1] = board[sr + in_diff - 1][sr + in_diff - 1 - i];
                    }
                    //좌 -> 하
                    for (int i = 0; i < in_diff; i++) {
                        board[sr + in_diff - 1][sr + in_diff - 1 - i] = board[sr + in_diff - 1 - i][sr];
                    }
                    //상 -> 좌
                    for (int i = 0; i < in_diff; i++) {
                        board[sr + in_diff - 1 - i][sr] = tmp[i];
                    }
                }
                rotation(3,l);
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        board = new int[(int)Math.pow(2,N)][(int)Math.pow(2,N)];
        for(int i=0;i<Math.pow(2,N);i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<Math.pow(2,N);j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=0;i<R;i++) {
            st = new StringTokenizer(br.readLine());
            K = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            rotation(K,L);
        }

        for(int i=0;i<Math.pow(2,N);i++) {
            for(int j=0;j<Math.pow(2,N);j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
/*
3 1
1 2 3 4 5 6 7 8
9 10 11 12 13 14 15 16
17 18 19 20 21 22 23 24
25 26 27 28 29 30 31 32
33 34 35 36 37 38 39 40
41 42 43 44 45 46 47 48
49 50 51 52 53 54 55 56
57 58 59 60 61 62 63 64
3 1

4 1
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
2 4 6 8 10 12 14 16 18 20 22 24 26 28 30 32
3 6 9 12 15 18 21 24 27 30 33 36 39 42 45 48
4 8 12 16 20 24 28 32 36 40 44 48 52 56 60 64
5 10 15 20 25 30 35 40 45 50 55 60 65 70 75 80
6 12 18 24 30 36 42 48 54 60 66 72 78 84 90 96
7 14 21 28 35 42 49 56 63 70 77 84 91 98 105 112
8 16 24 32 40 48 56 64 72 80 88 96 104 112 120 128
9 18 27 36 45 54 63 72 81 90 99 108 117 126 135 144
10 20 30 40 50 60 70 80 90 100 110 120 130 140 150 160
11 22 33 44 55 66 77 88 99 110 121 132 143 154 165 176
12 24 36 48 60 72 84 96 108 120 132 144 156 168 180 192
13 26 39 52 65 78 91 104 117 130 143 156 169 182 195 208
14 28 42 56 70 84 98 112 126 140 154 168 182 196 210 224
15 30 45 60 75 90 105 120 135 150 165 180 195 210 225 240
16 32 48 64 80 96 112 128 144 160 176 192 208 224 240 256
5 0
 */