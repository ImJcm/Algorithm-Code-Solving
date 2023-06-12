package BackJoon;

import java.util.*;
import java.util.stream.Collectors;

/*
스도쿠 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	86572	25157	15842	26.829%
문제
스도쿠는 18세기 스위스 수학자가 만든 '라틴 사각형'이랑 퍼즐에서 유래한 것으로 현재 많은 인기를 누리고 있다. 이 게임은 아래 그림과 같이 가로, 세로 각각 9개씩 총 81개의 작은 칸으로 이루어진 정사각형 판 위에서 이뤄지는데, 게임 시작 전 일부 칸에는 1부터 9까지의 숫자 중 하나가 쓰여 있다.



나머지 빈 칸을 채우는 방식은 다음과 같다.

각각의 가로줄과 세로줄에는 1부터 9까지의 숫자가 한 번씩만 나타나야 한다.
굵은 선으로 구분되어 있는 3x3 정사각형 안에도 1부터 9까지의 숫자가 한 번씩만 나타나야 한다.
위의 예의 경우, 첫째 줄에는 1을 제외한 나머지 2부터 9까지의 숫자들이 이미 나타나 있으므로 첫째 줄 빈칸에는 1이 들어가야 한다.



또한 위쪽 가운데 위치한 3x3 정사각형의 경우에는 3을 제외한 나머지 숫자들이 이미 쓰여있으므로 가운데 빈 칸에는 3이 들어가야 한다.



이와 같이 빈 칸을 차례로 채워 가면 다음과 같은 최종 결과를 얻을 수 있다.



게임 시작 전 스도쿠 판에 쓰여 있는 숫자들의 정보가 주어질 때 모든 빈 칸이 채워진 최종 모습을 출력하는 프로그램을 작성하시오.

입력
아홉 줄에 걸쳐 한 줄에 9개씩 게임 시작 전 스도쿠판 각 줄에 쓰여 있는 숫자가 한 칸씩 띄워서 차례로 주어진다. 스도쿠 판의 빈 칸의 경우에는 0이 주어진다. 스도쿠 판을 규칙대로 채울 수 없는 경우의 입력은 주어지지 않는다.

출력
모든 빈 칸이 채워진 스도쿠 판의 최종 모습을 아홉 줄에 걸쳐 한 줄에 9개씩 한 칸씩 띄워서 출력한다.

스도쿠 판을 채우는 방법이 여럿인 경우는 그 중 하나만을 출력한다.

제한
12095번 문제에 있는 소스로 풀 수 있는 입력만 주어진다.
C++14: 80ms
Java: 292ms
PyPy3: 1172ms
예제 입력 1
0 3 5 4 6 9 2 7 8
7 8 2 1 0 5 6 0 9
0 6 0 2 7 8 1 3 5
3 2 1 0 4 6 8 9 7
8 0 4 9 1 3 5 0 6
5 9 6 8 2 0 4 1 3
9 1 7 6 5 2 0 8 0
6 0 3 7 0 1 9 5 2
2 5 8 3 9 4 7 6 0
예제 출력 1
1 3 5 4 6 9 2 7 8
7 8 2 1 3 5 6 4 9
4 6 9 2 7 8 1 3 5
3 2 1 5 4 6 8 9 7
8 7 4 9 1 3 5 2 6
5 9 6 8 2 7 4 1 3
9 1 7 6 5 2 3 8 4
6 4 3 7 8 1 9 5 2
2 5 8 3 9 4 7 6 1
 */
/*
    처음 문제를 접했을 때, DFS 과정을 1~9 숫자를 넣는 반복문과 다시 return 했을 때, 1~9 중 중복방문 여부 검사 +
    zero_cnt로 depth를 조절하여 board에 값이 0인 좌표 방문여부와 0인 좌표를 모아놓은 리스트의 반복문을 고려하다보니
    0인 값을 가지는 좌표에 extractPossibleNum함수를 통해 들어갈 수 있는 숫자를 모은 리스트를 반환하고 sudoku에서 해당
    리스트를 반복문을 사용하여 하나씩 대입해보는 코드를 작성했다. 하지만 코드를 잘못 작성해서 틀린 후, 다른 사람의 코드를 참고하여
    재작성하였다. 다른 사람의 코드와 비교했을 때 생각한 논리는 맞았지만 이를 구현하는데 방법이 틀렸었다. 그래서 처음 생각한 코드를
    수정하여서 제출 시 정답처리 됐지만 메모리와 시간측면에서는 좋지 못한 코드구현이였다.
    82% 시간초과 발생
    이유 : possibleCheck함수의 경우 List를 생성하고, add하는 과정에서 시간이 걸리기 때문이다.
    따라서, 가능한지 여부를 3개의 모든 조건을 만족시키는 방법이 아니라 하나의 조건이라도 부적합하면 false return하는 형태로 변경
 */
//메모리       시간
//23800 KB    492ms
class BOJ2580_Point {
    int x,y;

    BOJ2580_Point(int x,int y) {
        this.x = x;
        this.y = y;
    }
}
public class BOJ2580 {
    static int[][] board;
    static int Zero_cnt = 0;
    static ArrayList<BOJ2580_Point> zeroPoint = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        /*board = new int[9][];
        for(int i=0;i<9;i++) {
            board[i] = Arrays.asList(sc.nextLine().split(" "))
                    .stream()
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }*/
        board = new int[9][9];
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                board[i][j] = sc.nextInt();
                if(board[i][j] == 0) {
                    Zero_cnt++;
                    zeroPoint.add(new BOJ2580_Point(i,j));
                }
            }
        }

        sudoku(0);

    }

    static void sudoku(int zero_cnt) {
        if(zero_cnt == Zero_cnt) {
            print_sudoku();
            System.exit(0);
        }

        int nx = zeroPoint.get(zero_cnt).x;
        int ny = zeroPoint.get(zero_cnt).y;
        if(board[nx][ny] == 0) {
            for(int i=1;i<=9;i++) {
                //if(PossibleCheck(nx,ny,i)) {
                if(possibility(nx,ny,i)) {
                    board[nx][ny] = i;
                    sudoku(zero_cnt+1);
                    board[nx][ny] = 0;
                }
            }
            return;
        }
    }

    //스도쿠 출력
    static void print_sudoku() {
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean possibility(int row, int col, int value) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == value) {
                return false;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (board[i][col] == value) {
                return false;
            }
        }

        int set_row = (row / 3) * 3;
        int set_col = (col / 3) * 3;

        for (int i = set_row; i < set_row + 3; i++) {
            for (int j = set_col; j < set_col + 3; j++) {
                if (board[i][j] == value) {
                    return false;
                }
            }
        }

        return true; // 중복되는 것이 없을 경우 true 반환
    }
    //x,y좌표에 놓을 수 있는 수를 리스트로 반환
    static boolean PossibleCheck(int x,int y, int Num) {
        List<Integer> remain = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        List<Integer> possiblelist_row = new ArrayList<>();
        List<Integer> possiblelist_col = new ArrayList<>();
        List<Integer> possiblelist_box = new ArrayList<>();

        //row
        for(int i=0;i<9;i++) {
            if(board[x][i] != 0) {
                possiblelist_row.add(board[x][i]);
            }
        }

        //col
        for(int i=0;i<9;i++) {
            if(board[i][y] != 0) {
                possiblelist_row.add(board[i][y]);
            }
        }

        //box
        int start_x = 3 * (x / 3);
        int start_y = 3 * (y / 3);
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(board[start_x+i][start_y+j] != 0) {
                    possiblelist_box.add(board[start_x+i][start_y+j]);
                }
            }
        }

        remain.removeAll(possiblelist_row);
        remain.removeAll(possiblelist_col);
        remain.removeAll(possiblelist_box);

        if(remain.contains(Num)) {
            return true;
        }
        return false;
    }
}
//처음 작성한 코드는 논리가 잘못되었다. 우선 방문여부를 검사하는 것과 zeroPoint 리스트의 반복을 할 필요가 없었고,
//출력 시 column 검사하는 조건이 빠졌었다. 이를 수정했을 때, 통과를 하였다.
//배열과 조건 검사를 최적화한 코드와 비교했을 때 메모리의 사용과 시간측면에서 큰 차이가 났다.
//메모리         시간
//309400 KB	    1436 ms
//수정코드
/*
static int[][] board;
    static int Zero_cnt = 0;
    static ArrayList<Point> zeroPoint = new ArrayList<>();
    static boolean[] visited;

    public static void main(String[] args) {
        //long beforeTime = System.currentTimeMillis();
        Scanner sc = new Scanner(System.in);

        board = new int[9][9];
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                board[i][j] = sc.nextInt();
                if(board[i][j] == 0) {
                    Zero_cnt++;
                    zeroPoint.add(new Point(i,j));
                }
            }
        }
        visited = new boolean[Zero_cnt];

        sudoku(0);

        //long afterTime = System.currentTimeMillis();
        //long secDiffTime = (afterTime - beforeTime);
        //System.out.println("시간차이(m) : "+secDiffTime);
    }

    static void sudoku(int zero_cnt) {
        if(zero_cnt == Zero_cnt) {
            if(checkRow() && checkBox() && checkCol()) {
                print_sudoku();
                System.exit(0);
            }
            return;
        }

        int nx = zeroPoint.get(zero_cnt).x;
        int ny = zeroPoint.get(zero_cnt).y;
        List<Integer> possiblelist = extractPossibleNum(nx,ny);
        //boolean[] in_visited = new boolean[possiblelist.size()];

        for(int j=0;j<possiblelist.size();j++) {
            board[nx][ny] = possiblelist.get(j);
            sudoku(zero_cnt+1);
            board[nx][ny] = 0;
            //in_visited[j] = false;
        }
        //visited[i] = false;
    }

    //스도쿠 출력
    static void print_sudoku() {
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    //1x9 row을 검사
    static boolean checkRow() {
        //boolean[] chk = new boolean[9];
        Set<Integer> chk;

        for(int i=0;i<9;i++) {
            chk = new HashSet<>(Arrays.stream(board[i])
                    .boxed()
                    .collect(Collectors.toList()));
            if(chk.size() != 9) {
                return false;
            }
        }
        return true;
    }

    static boolean checkCol() {
        Set<Integer> chk = new HashSet<>();


        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                chk.add(board[i][j]);
            }
            if(chk.size() != 9) {
                return false;
            }
        }
        return true;
    }

    //3x3 box를 검사
    static boolean checkBox() {
        Set<Integer> chk;

        for(int i=0;i<9;i+=3) {
            for(int j=0;j<9;j+=3) {
                chk = new HashSet<Integer>();
                for(int k=0;k<9;k++) {
                    chk.add(board[i+(k/3)][j+(k%3)]);
                }
                if(chk.size() != 9) {
                    return false;
                }
            }
        }
        return true;
    }

    //x,y좌표에 놓을 수 있는 수를 리스트로 반환
    static List<Integer> extractPossibleNum(int x,int y) {
        List<Integer> remain = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        List<Integer> possiblelist_row = new ArrayList<>();
        List<Integer> possiblelist_col = new ArrayList<>();
        List<Integer> possiblelist_box = new ArrayList<>();

        for(int i=0;i<9;i++) {
            if(board[x][i] != 0) {
                possiblelist_row.add(board[x][i]);
            }
        }

        for(int i=0;i<9;i++) {
            if(board[i][y] != 0) {
                possiblelist_row.add(board[i][y]);
            }
        }

        int start_x = 3 * (x / 3);
        int start_y = 3 * (y / 3);
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(board[start_x+i][start_y+j] != 0) {
                    possiblelist_box.add(board[start_x+i][start_y+j]);
                }
            }
        }

        remain.removeAll(possiblelist_row);
        remain.removeAll(possiblelist_col);
        remain.removeAll(possiblelist_box);

        return remain;
    }
 */

//수정 전 코드
/*
import java.util.*;
import java.util.stream.Collectors;

class BOJ2580_Point {
    int x,y;

    BOJ2580_Point(int x,int y) {
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static int[][] board;
    static int Zero_cnt = 0;
    static ArrayList<BOJ2580_Point> zeroPoint = new ArrayList<>();
    static boolean[] visited;

    public static void main(String[] args) {
        //long beforeTime = System.currentTimeMillis();
        Scanner sc = new Scanner(System.in);

        board = new int[9][9];
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                board[i][j] = sc.nextInt();
                if(board[i][j] == 0) {
                    Zero_cnt++;
                    zeroPoint.add(new BOJ2580_Point(i,j));
                }
            }
        }
        visited = new boolean[Zero_cnt];

        sudoku(0);

        //long afterTime = System.currentTimeMillis();
        //long secDiffTime = (afterTime - beforeTime);
        //System.out.println("시간차이(m) : "+secDiffTime);
    }

    static void sudoku(int zero_cnt) {
        if(zero_cnt == Zero_cnt) {
            if(checkRow() && checkBox()) {
                print_sudoku();
            }
            return;
        }

        for(int i=0;i<zeroPoint.size();i++) {
            if(!visited[i]) {
                visited[i] = true;
                int nx = zeroPoint.get(i).x;
                int ny = zeroPoint.get(i).y;
                List<Integer> possiblelist = extractPossibleNum(nx,ny);
                boolean[] in_visited = new boolean[possiblelist.size()];

                for(int j=0;j<possiblelist.size();j++) {
                    if(!in_visited[j]) {
                        in_visited[j] = true;
                        board[nx][ny] = possiblelist.get(j);
                        sudoku(zero_cnt+1);
                        board[nx][ny] = 0;
                    }
                    //in_visited[j] = false;
                }
                //visited[i] = false;
            }
        }
    }

    //스도쿠 출력
    static void print_sudoku() {
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    //1x9 row을 검사
    static boolean checkRow() {
        //boolean[] chk = new boolean[9];
        Set<Integer> chk;

        for(int i=0;i<9;i++) {
            chk = new HashSet<>(Arrays.stream(board[i])
                    .boxed()
                    .collect(Collectors.toList()));
            if(chk.size() != 9) {
                return false;
            }
        }
        return true;
    }

    //3x3 box를 검사
    static boolean checkBox() {
        Set<Integer> chk;

        for(int i=0;i<9;i+=3) {
            for(int j=0;j<9;j+=3) {
                chk = new HashSet<Integer>();
                for(int k=0;k<9;k++) {
                    chk.add(board[i+(k/3)][j+(k%3)]);
                }
                if(chk.size() != 9) {
                    return false;
                }
            }
        }
        return true;
    }

    //x,y좌표에 놓을 수 있는 수를 리스트로 반환
    static List<Integer> extractPossibleNum(int x,int y) {
        List<Integer> remain = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        List<Integer> possiblelist_row = new ArrayList<>();
        List<Integer> possiblelist_col = new ArrayList<>();
        List<Integer> possiblelist_box = new ArrayList<>();

        for(int i=0;i<9;i++) {
            if(board[x][i] != 0) {
                possiblelist_row.add(board[x][i]);
            }
        }

        for(int i=0;i<9;i++) {
            if(board[i][y] != 0) {
                possiblelist_row.add(board[i][y]);
            }
        }

        int start_x = 3 * (x / 3);
        int start_y = 3 * (y / 3);
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(board[start_x+i][start_y+j] != 0) {
                    possiblelist_box.add(board[start_x+i][start_y+j]);
                }
            }
        }

        remain.removeAll(possiblelist_row);
        remain.removeAll(possiblelist_col);
        remain.removeAll(possiblelist_box);

        return remain;
    }
}
 */