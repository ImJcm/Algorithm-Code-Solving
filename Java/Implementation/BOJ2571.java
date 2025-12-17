package Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
색종이 - 3

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	2737	1391	1046	53.123%
문제
가로, 세로의 크기가 각각 100인 정사각형 모양의 흰색 도화지가 있다. 이 도화지 위에 가로, 세로의 크기가 각각 10인 정사각형 모양의 검은색 색종이를 색종이의 변과 도화지의 변이 평행하도록 붙인다. 이러한 방식으로 색종이를 한 장 또는 여러 장 붙인 후 도화지에서 검은색 직사각형을 잘라내려고 한다. 직사각형 또한 그 변이 도화지의 변과 평행하도록 잘라내어야 한다.

예를 들어 흰색 도화지 위에 세 장의 검은색 색종이를 <그림 1>과 같은 모양으로 붙였다. <그림 1>에 표시된 대로 검은색 직사각형을 잘라내면 그 넓이는 22×5=110이 된다.



<그림 1>



<그림 2>

반면 <그림 2>에 표시된 대로 검은색 직사각형을 잘라내면 그 넓이는 8×15=120이 된다.

검은색 색종이의 수와 각 색종이를 붙인 위치가 주어질 때 잘라낼 수 있는 검은색 직사각형의 최대 넓이를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 색종이의 수가 주어진다. 이어 둘째 줄부터 한 줄에 하나씩 색종이를 붙인 위치가 주어진다. 색종이를 붙인 위치는 두 개의 자연수로 주어지는데 첫 번째 자연수는 색종이의 왼쪽 변과 도화지의 왼쪽 변 사이의 거리이고, 두 번째 자연수는 색종이의 아래쪽 변과 도화지의 아래쪽 변 사이의 거리이다. 색종이의 수는 100이하이며, 색종이가 도화지 밖으로 나가는 경우는 없다.

출력
첫째 줄에 잘라낼 수 있는 검은색 직사각형의 최대 넓이를 출력한다.

예제 입력 1
3
3 7
15 7
5 2
예제 출력 1
120
출처
Olympiad > 한국정보올림피아드 > 한국정보올림피아드시․도지역본선 > 지역본선 2007 > 고등부 2번

알고리즘 분류
구현
누적 합
 */
/*
알고리즘 핵심
구현 + 누적합
문제를 읽고 접근방법을 힌트에서 주어진 누적합을 사용하는 방법으로 생각해보려 했다.
누적합의 기준을 입력으로 주어지는 검은색 색종이의 처음 위치를 기준으로 우상방향으로 넓이를 누적합으로 나타내려고 생각했지만
해당 방법으로 누적합 표현시 색종이가 겹치는 부분에서 어떠한 값을 표현해야할지 오류가 생기는 문제가 있으므로 해당 방법은 옳지 않았다.

도저히 누적합의 기준을 무엇으로 잡야야 풀리는지 생각이 나지않아서 정답 코드를 참고하였다. - https://zoosso.tistory.com/155
1. 검은색 색종이를 덮은 부분을 1로 표시한다.
2. 누적합의 기준을 해당 위치에서 사각형을 만들 수 있는 최대 높이를 나타낸다.
-> paper[i][j] = paper[i - 1][j] + 1 => paper[i][j] += paper[i - 1][j]
3. x 좌표를 기준으로 1에서 100까지 반복문 수행하고, y 좌표를 기준으로 1에서 100까지 반복문을 수행하고 (x,y)의 좌표를 잡는다.
4. (x,y)좌표를 기준으로 ny를 y~100까지 반복하여 해당 위치에서 가능한 직사각형의 최대 높이의 값으로 만들어지는 직사각형의 넓이를 area에 저장한다.
5. area를 최대 넓이를 나타내는 ans에 업데이트한다.

height를 기준으로 누적합을 시행 시, 한 좌표에서 만들 수 있는 직사각형의 최대로 가능한 높이를 알 수 있기 때문에
width를 우측으로 늘려가면서 가능한 최대 높이와 width를 곱하여 직사각형의 넓이를 구할 수 있다.
 */
public class BOJ2571 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,area,ans;
    static int[][] paper;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        //print_paper();
        prefix_sum_height();

        cal_rectangle_area();

        System.out.println(ans);
    }

    private static void cal_rectangle_area() {
        int area = 0;
        for(int x = 1; x < 101; x++) {
            for(int y = 1; y < 101; y++) {
                int height = 100;
                for(int ny = y; ny < 101; ny++) {
                    height = Math.min(height, paper[x][ny]);

                    if(height == 0) break;

                    area = Math.max(area, height * (ny - y + 1));
                    ans = Math.max(ans, area);
                }
            }
        }
    }

    private static void prefix_sum_height() {
        for(int i = 1; i < 101; i++) {
            for(int j = 1; j < 101; j++) {
                if(paper[i][j] == 0) continue;
                paper[i][j] += paper[i - 1][j];
            }
        }
    }

    private static void print_paper() {
        for(int j = 0; j <= 100; j++) {
            for(int k = 0; k <= 100; k++) {
                System.out.print(paper[j][k] + "");
            }
            System.out.println();
        }
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        ans = 0;

        paper = new int[101][101];

        for(int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");

            int x = Integer.parseInt(input[0]);
            int y = Integer.parseInt(input[1]);

            for(int j = 0; j < 10; j++) {
                for(int k = 0; k < 10; k++) {
                    paper[x + j][y + k] = 1;
                }
            }
        }
    }
}
