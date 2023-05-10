package BackJoon;/*
정육면체 전개도

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	1020	362	256	38.323%
문제
여섯 개의 정사각형 모양의 종이가 있으면, 이를 적절히 이어 붙여서 정육면체의 전개도를 만들 수 있다. 정육면체의 전개도라는 것은, 선을 따라 종이를 적절히 접었을 때 정육면체를 완성할 수 있는 경우를 말한다.

예를 들면 아래의 모양은 정육면체의 전개도가 될 수 있다.



하지만 모든 경우에 정육면체를 만들 수 있는 것은 아니다. 예를 들어 다음과 같은 모양의 전개도는 여섯 개의 정사각형으로 이루어 있기는 하나 정육면체를 만들 수는 없다.



여섯 개의 정사각형으로 이루어진 전개도가 주어졌을 때, 이것이 정육면체의 전개도가 될 수 있는지 없는지를 가려내는 프로그램을 작성하시오.

입력
세 개의 입력 데이터가 주어지며, 각각의 입력 데이터는 여섯 개의 줄로 이루어져 있다. 각 데이터는 여섯 개의 줄에 걸쳐 여섯 개의 숫자가 빈 칸을 사이에 두고 주어진다. 숫자는 0 또는 1로 이루어지며, 36개의 숫자 중 1은 정확히 6개가 있다. 0은 공백을 나타내며 1은 정사각형을 나타낸다. (즉 전체의 그림이 전개도를 나타낸다고 보면 된다.) 정사각형들이 서로 떨어져 있는 경우는 없다.

출력
세 개의 줄에 걸쳐, 입력된 순서대로 전개도가 정육면체의 전개도이면 yes를, 아니면 no를 출력한다.

예제 입력 1
0 0 0 0 0 0
0 0 0 0 0 0
0 0 1 0 0 0
0 1 1 1 1 0
0 0 1 0 0 0
0 0 0 0 0 0
0 1 1 0 0 0
0 1 0 0 0 0
0 1 0 0 0 0
1 1 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 1 1 0
0 0 1 1 0 0
0 0 0 1 1 0
0 0 0 0 0 0
0 0 0 0 0 0
예제 출력 1
yes
yes
no
 */
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

//BFS 탐색하면서, 전개도의 총 가로: 총 세로 길이가 4:3 or 3:4인 경우 yes, else no - 반례존재
//https://goodmilktea.tistory.com/56 - 11개의 전개도에서 최대 가로길이, 세로길이 별로 도형을 구분하고
//4개의 조건으로 가로, 세로 별로 길이 + 도형의 상대적 면 위치를 고려하여 조건검사

/*
가로 최대 길이 : 4 => 도형 6개
가로 최대 길이 : 3 => 도형 4개
가로 최대 길이 : 2 => 도형 1개

세로 ...

Arow, Acol에는 각각 가로, 세로 방향으로 입력으로 주어진 1의 값을 기준으로 놓여진 위치의 열(col)값을 저장한다.
비교자체를 1의 좌표가 같은 열에 있는지를 비교하여 위치를 특정짓는다.

구현 방법 설명 : 처음 문제를 접하고 11개의 고정된 전개도가 있다는 정보는 알 지 못했다. 그래서 문제를 푸는 시간이 많이 지체되어 위의 링크에서
다른 사람의 푼 방식을 보고 코드를 이해하고 다시 작성해보았다.

우선 가로(row)기준으로 높이 별 전개도를 구분하여 전개도를 만족하는지를 찾는방법을 사용하였다. 또한, 세로(col)기준으로 길이 별 전개도를 구분하여
전개도를 만족하는지 판단한 방법을 이용하였다. 즉, 가능한 모든 전개도를 조건으로 만족하는지 여부를 검사한다.
입력된 전개도의 위치를 통해 전개도를 만족하는 조건으로 면끼리 접하였는지를 알기위해 새로운 List에
저장하여 면끼리의 접한지 여부를 알수 있다.

 */
public class BOJ1917 {
    static int[][] board = new int[6][6];
    static boolean[] checks = {false,false,false};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        //3개의 주사위 전개도
        for(int i=0;i<3;i++) {
            boolean check = false;

            ArrayList<Integer>[] Arow = new ArrayList[6];
            ArrayList<Integer>[] Acol = new ArrayList[6];

            //6x6만큼 저장
            for(int r=0;r<6;r++) {
                st = new StringTokenizer(br.readLine());
                Arow[r] = new ArrayList<>();
                Acol[r] = new ArrayList<>();
                for(int c=0;c<6;c++) {
                    board[r][c] = Integer.parseInt(st.nextToken());
                }
            }

            //가로 길이 책정
            int cnt_row = 0;
            for(int r=0;r<6;r++) {
                boolean b_row = false;

                for(int c=0;c<6;c++) {
                    if (board[r][c] == 1) {
                        b_row = true;
                        Arow[cnt_row].add(c);
                    }
                }
                if(b_row) cnt_row++;
            }

            //세로 길이 책정
            int cnt_col = 0;
            for(int r=0;r<6;r++) {
                boolean b_col = false;

                for(int c=0;c<6;c++) {
                    if(board[c][r] == 1) {
                        b_col = true;
                        Acol[cnt_col].add(c);
                    }
                }
                if(b_col) cnt_col++;
            }

            //높이가 3인 전개도
            //회전하였을 때 같은 모양의 전개도 예시는 생략
            if(cnt_row == 3) {
                //1 4 1
                /*
                    0          0         0        0          0         0
                    0 0 0 0    0 0 0 0   0 0 0 0  0 0 0 0  0 0 0 0   0 0 0 0
                    0            0           0          0    0           0
                 */
                if(Arow[0].size() == 1 && Arow[1].size() == 4 && Arow[2].size() == 1) check = true;

                //2 3 1
                /*
                    0 0       0 0       0 0
                      0 0 0     0 0 0     0 0 0
                      0           0           0
                 */
                //길이가 2인 곳이 길이가 3인 중간부분과 모서리에 붙어있어야 한다는 조건이 필요
                if(Arow[1].size() == 3) {
                    if(Arow[0].size() == 2
                            && (Arow[0].get(1) == Arow[1].get(0) || Arow[0].get(0) == Arow[1].get(2))) check = true;
                    else if (Arow[2].size() == 2
                            && (Arow[2].get(1) == Arow[1].get(0) || Arow[2].get(0) == Arow[1].get(2))) check = true;
                }

                // 2 2 2
                /*
                    0 0
                      0 0
                        0 0
                 */
                if(Arow[0].size() == 2 && Arow[1].size() == 2 && Arow[2].size() == 2) {
                    if((Arow[0].get(1) == Arow[1].get(0) && Arow[1].get(1) == Arow[2].get(0))
                            || (Arow[0].get(0) == Arow[1].get(1) && Arow[1].get(0) == Arow[2].get(1)))
                        check = true;
                }
            }

            //높이가 2인 주사위 전개도
            if(cnt_row == 2) {
                //3 3
                /*
                    0 0 0
                        0 0 0
                 */
                if(Arow[1].size() == 3) {
                    if(Arow[0].size() == 3 &&
                            (Arow[0].get(2) == Arow[1].get(0) || Arow[0].get(0) == Arow[1].get(2))) {
                        check = true;
                    }
                }
            }


            //90도 회전되어 있는 전개도 모형 기준 = 가로 기준으로 본 전개도의 90도 회전 버전
            //길이가 3인 전개도
            if(cnt_col == 3) {
                // 1 4 1
                /*
                    0 0    0 0      0 0     0 0 0     0       0
                      0      0        0 0     0     0 0 0   0 0
                      0      0 0      0       0       0       0 0
                      0 0    0        0       0       0       0
                 */
                if(Acol[0].size() == 1 && Acol[1].size() == 4 && Acol[2].size() == 1) check = true;

                //2 3 1
                /*
                    0       0      0
                    0 0 0   0 0    0 0
                      0       0 0    0
                      0       0      0 0
                 */
                if(Acol[1].size() == 3) {
                    if(Acol[0].size() == 2 && (Acol[0].get(1) == Acol[1].get(0) || Acol[0].get(0) == Acol[1].get(2))) check = true;
                    else if (Acol[2].size() == 2 && (Acol[2].get(1) == Acol[1].get(0) || Acol[2].get(0) == Acol[1].get(2))) check = true;
                }

                // 2 2 2
                /*
                    0
                    0 0
                      0 0
                        0
                 */
                if(Acol[0].size() == 2 && Acol[1].size() == 2 && Acol[2].size() == 2) {
                    if((Acol[0].get(1) == Acol[1].get(0) && Acol[1].get(1) == Acol[2].get(0))
                            || (Acol[0].get(0) == Acol[1].get(1) && Acol[1].get(0) == Acol[2].get(1))) {
                        check = true;
                    }
                }
            }

            if(cnt_col == 2) {
                //3 3
                /*
                    0
                    0
                    0 0
                      0
                      0
                 */
                if(Acol[1].size() == 3) {
                    if(Acol[0].size() == 3 &&
                            (Acol[0].get(2) == Acol[1].get(0) || Acol[0].get(0) == Acol[1].get(2))) {
                        check = true;
                    }
                }
            }

            if(check) checks[i] = true;
        }

        for(boolean b : checks) {
            if(b) System.out.println("yes");
            else System.out.println("no");
        }
    }

}
