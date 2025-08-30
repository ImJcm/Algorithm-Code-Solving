package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
롤러코스터 스페셜 저지다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	9640	2697	1965	28.880%
문제
상근이는 우리나라에서 가장 유명한 놀이 공원을 운영하고 있다. 이 놀이 공원은 야외에 있고, 다양한 롤러코스터가 많이 있다.

어느 날 벤치에 앉아있던 상근이는 커다란 황금을 발견한 기분이 들었다. 자신의 눈 앞에 보이는 이 부지를 구매해서 롤러코스터를 만든다면, 세상에서 가장 재미있는 롤러코스터를 만들 수 있다고 생각했다.

이 부지는 직사각형 모양이고, 상근이는 R행 C열의 표 모양으로 나누었다. 롤러코스터는 가장 왼쪽 위 칸에서 시작할 것이고, 가장 오른쪽 아래 칸에서 도착할 것이다. 롤러코스터는 현재 있는 칸과 위, 아래, 왼쪽, 오른쪽으로 인접한 칸으로 이동할 수 있다. 각 칸은 한 번 방문할 수 있고, 방문하지 않은 칸이 있어도 된다.

각 칸에는 그 칸을 지나갈 때, 탑승자가 얻을 수 있는 기쁨을 나타낸 숫자가 적혀있다. 롤러코스터를 탄 사람이 얻을 수 있는 기쁨은 지나간 칸의 기쁨의 합이다. 가장 큰 기쁨을 주는 롤러코스터는 어떻게 움직여야 하는지를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 R과 C가 주어진다. (2 ≤ R, C ≤ 1000) 둘째 줄부터 R개 줄에는 각 칸을 지나갈 때 얻을 수 있는 기쁨이 주어진다. 이 값은 1000보다 작은 양의 정수이다.

출력
첫째 줄에 가장 가장 큰 기쁨을 주는 롤러코스터는 가장 왼쪽 위 칸부터 가장 오른쪽 아래 칸으로 어떻게 움직이면 되는지를 출력한다. 위는 U, 오른쪽은 R, 왼쪽은 L, 아래는 D로 출력한다. 정답은 여러 가지 일 수도 있다.

예제 입력 1
3 3
5 1 3
2 4 8
1 1 2
예제 출력 1
RRDLLDRR
예제 입력 2
2 2
2 1
3 4
예제 출력 2
DR
출처
Contest > Croatian Open Competition in Informatics > COCI 2010/2011 > Contest #2 5번

문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: kjp4155
알고리즘 분류
구현
그리디 알고리즘
해 구성하기
홀짝성
 */
public class BOJ2873 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int R,C;
    static int[][] gladness;
    static StringBuilder ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void move_roller_coaster(int type) {
        boolean direction_change = false;
        boolean row_is_even = (R % 2 == 0);

        switch (type) {
            case 0: {
                for(int r = 0; r < R; r++) {
                    for(int c = 0; c < C - 1; c++) {
                        if(direction_change) ans.append("L");
                        else ans.append("R");
                    }
                    direction_change = !direction_change;
                    if(r < R - 1) ans.append("D");
                }
                break;
            }
            case 1: {
                if(row_is_even) {
                    for(int c = 0; c < C; c++) {
                        for(int r = 0; r < R - 1; r++) {
                            if(direction_change) ans.append("U");
                            else ans.append("D");
                        }
                        direction_change = !direction_change;
                        if(c < C - 1) ans.append("R");
                    }
                } else {
                    for(int r = 0; r < R; r++) {
                        for(int c = 0; c < C - 1; c++) {
                            if(direction_change) ans.append("L");
                            else ans.append("R");
                        }
                        direction_change = !direction_change;
                        if(r < R - 1) ans.append("D");
                    }
                }
                break;
            }
            case 2: {
                // R,C 모두 짝수인 경우
            }
        }
    }

    private static void solve() {
        if(R % 2 == 1 && C % 2 == 1) {
            move_roller_coaster(0);
        } else if(R % 2 == 1 || C % 2 == 1) {
            move_roller_coaster(1);
        } else {

        }

        System.out.println(ans.toString());
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        R = Integer.parseInt(input[0]);
        C = Integer.parseInt(input[1]);

        gladness = new int[R][C];

        for(int r = 0; r < R; r++) {
            gladness[r] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        ans = new StringBuilder();
    }
}
