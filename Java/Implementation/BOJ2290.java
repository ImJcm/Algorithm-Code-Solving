package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
LCD Test 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	3029	1511	1165	50.696%
문제
지민이는 새로운 컴퓨터를 샀다. 하지만 새로운 컴퓨터에 사은품으로 온 LC-디스플레이 모니터가 잘 안나오는 것이다. 지민이의 친한 친구인 지환이는 지민이의 새로운 모니터를 위해 테스트 할 수 있는 프로그램을 만들기로 하였다.

입력
첫째 줄에 두 개의 정수 s와 n이 들어온다. (1 ≤ s ≤ 10, 0 ≤ n ≤ 9,999,999,999)이다. n은 LCD 모니터에 나타내야 할 수 이며, s는 크기이다.

출력
길이가 s인 '-'와 '|'를 이용해서 출력해야 한다. 각 숫자는 모두 s+2의 가로와 2s+3의 세로로 이루어 진다. 나머지는 공백으로 채워야 한다. 각 숫자의 사이에는 공백이 한 칸 있어야 한다.

예제 입력 1
2 1234567890
예제 출력 1
      --   --        --   --   --   --   --   --
   |    |    | |  | |    |       | |  | |  | |  |
   |    |    | |  | |    |       | |  | |  | |  |
      --   --   --   --   --        --   --
   | |       |    |    | |  |    | |  |    | |  |
   | |       |    |    | |  |    | |  |    | |  |
      --   --        --   --        --   --   --

예제 입력 2 (Test)
3 1234567890
예제 출력 2
       ---   ---         ---   ---   ---   ---   ---   ---
    |     |     | |   | |     |         | |   | |   | |   |
    |     |     | |   | |     |         | |   | |   | |   |
    |     |     | |   | |     |         | |   | |   | |   |
       ---   ---   ---   ---   ---         ---   ---
    | |         |     |     | |   |     | |   |     | |   |
    | |         |     |     | |   |     | |   |     | |   |
    | |         |     |     | |   |     | |   |     | |   |
       ---   ---         ---   ---         ---   ---   ---
 */
/*
    문제의 출력 설명에서 "길이가 s인 '-', 'ㅣ'를 이용해서 출력한다" 해당 문장이 오류라고 생각한다.
    예제 출력에 "길이가 s"인 문자가 아닌 "길이가 1"이여야 하지않나?

    어떻게 로직을 짜야하는지 도저히 생각이 나지 않아서 https://velog.io/@hyeon930/BOJ-2290-LCD-Test-Java 이쪽 코드를 참고하여
    코드를 보고 이해한 후 코드를 작성하였다.

    구현하는데 필요한 로직은 첫줄부터 시작해서 순서와 숫자별로 필요한 수의 문자와 공백을 StringBuilder를 통해 높이별로 필요한 만큼
    붙여넣어 출력문을 완성하는 것으로 보인다.

    2차원 배열로 문자를 저장하고 출력하는 방식은 좌표를 지정해야 하기때문에 코드의 가시성이 안좋아지는 것 같다.
    그래서, 참고한 링크의 코드처럼 stringbuilder의 append를 통해 col 열을 신경쓰지 않고 출력 문자열을 만드는 것이 더 좋아보인다.

    처음 제출 시, 틀렸다는 문구를 보고 이유를 찾아보다가 처음에는 출력상의 오류라는 질문이 많길래 출력상의 오류인가 생각하고
    입력을 바꿔가면서 출력문을 보는데 문제가 없었다. 몇 분정도의 삽질한 결과 이유는 "7" case에서 윗부분의 left를 true로
    해서 7의 출력양식이 달랐던 것을 알 수 있었다.
 */
public class BOJ2290 {
    static int s,n,height,column=0;
    static String str;
    // 1 <= n <= 10, 0 <= n <= 10^10 - 1
    static char[][] board;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        s = Integer.parseInt(st.nextToken());
        str = st.nextToken();

        board = new char[2*s+3][(s+2)*str.length() + str.length()];

        for(int i=0;i<2*s+3;i++) {
            Arrays.fill(board[i], ' '); // 문자를 표기할 2차원 배열의 초기값을 공백으로 해주어야 null값이 들어가있는 오류 방지
            height = i;
            for(int j=0;j<str.length();j++) {
                //if(column>1) board[height][column-1] = ' ';
                column = (s+3)*j;
                print(str.charAt(j));
            }
        }

        for(char[] a : board) {
            for(int i=0;i<a.length;i++) {
                System.out.print(a[i]);
            }
            System.out.println();
        }
    }

    private static void print(char num) {
        switch (num) {
            case '0':
                if(height == 0) horizon(true);
                else if(height >= 1 && height <= s) vertical(true, true);
                else if(height == s + 1) horizon(false);
                else if(height > s + 1 && height <= s * 2 + 1) vertical(true, true);
                else horizon(true);
                break;
            case '1':
                if(height == 0) horizon(false);
                else if(height >= 1 && height <= s) vertical(false, true);
                else if(height == s + 1) horizon(false);
                else if(height > s + 1 && height <= s * 2 + 1) vertical(false, true);
                else horizon(false);
                break;
            case '2':
                if(height == 0) horizon(true);
                else if(height >= 1 && height <= s) vertical(false, true);
                else if(height == s + 1) horizon(true);
                else if(height > s + 1 && height <= s * 2 + 1) vertical(true, false);
                else horizon(true);
                break;
            case '3':
                if(height == 0) horizon(true);
                else if(height >= 1 && height <= s) vertical(false, true);
                else if(height == s + 1) horizon(true);
                else if(height > s + 1 && height <= s * 2 + 1) vertical(false, true);
                else horizon(true);
                break;
            case '4':
                if(height == 0) horizon(false);
                else if(height >= 1 && height <= s) vertical(true, true);
                else if(height == s + 1) horizon(true);
                else if(height > s + 1 && height <= s * 2 + 1) vertical(false, true);
                else horizon(false);
                break;
            case '5':
                if(height == 0) horizon(true);
                else if(height >= 1 && height <= s) vertical(true, false);
                else if(height == s + 1) horizon(true);
                else if(height > s + 1 && height <= s * 2 + 1) vertical(false, true);
                else horizon(true);
                break;
            case '6':
                if(height == 0) horizon(true);
                else if(height >= 1 && height <= s) vertical(true, false);
                else if(height == s + 1) horizon(true);
                else if(height > s + 1 && height <= s * 2 + 1) vertical(true, true);
                else horizon(true);
                break;
            case '7':
                if(height == 0) horizon(true);
                else if(height >= 1 && height <= s) vertical(false, true);
                else if(height == s + 1) horizon(false);
                else if(height > s + 1 && height <= s * 2 + 1) vertical(false, true);
                else horizon(false);
                break;
            case '8':
                if(height == 0) horizon(true);
                else if(height >= 1 && height <= s) vertical(true, true);
                else if(height == s + 1) horizon(true);
                else if(height > s + 1 && height <= s * 2 + 1) vertical(true, true);
                else horizon(true);
                break;
            case '9':
                if(height == 0) horizon(true);
                else if(height >= 1 && height <= s) vertical(true, true);
                else if(height == s + 1) horizon(true);
                else if(height > s + 1 && height <= s * 2 + 1) vertical(false, true);
                else horizon(true);
                break;
        }
    }

    private static void horizon(boolean line) {
        if(line) {
            board[height][column] = ' ';
            for(int i=1;i<=s;i++) {
                board[height][column + i] = '-';
            }
            board[height][column + s + 1] = ' ';
        } else {
            for(int i=1;i<=s+2;i++) {
                board[height][column + i] = ' ';
            }
        }
    }

    private static void vertical(boolean left, boolean right) {
        if(left) {
            board[height][column] = '|';
        } else {
            board[height][column] = ' ';
        }

        for(int i=1;i<=s;i++) {
            board[height][column+i] = ' ';
        }

        if(right) {
            board[height][column+s+1] = '|';
        } else {
            board[height][column+s+1] = ' ';
        }
    }
}
/*
      --   --        --   --   --   --   --   --
              |    |    | |  | |    |       | |  | |  | |  |
              |    |    | |  | |    |       | |  | |  | |  |
              --   --   --   --   --        --   --
              | |       |    |    | |  |    | |  |    | |  |
              | |       |    |    | |  |    | |  |    | |  |
              --   --        --   --        --   --   --
      --   --        --   --   --   --   --   --
   |    |    | |  | |    |    |  | |  | |  | |  |
   |    |    | |  | |    |    |  | |  | |  | |  |
      --   --   --   --   --        --   --
   | |       |    |    | |  |    | |  |    | |  |
   | |       |    |    | |  |    | |  |    | |  |
      --   --        --   --        --   --   --
 */