package greedy;

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
/*
알고리즘 핵심
그리디 알고리즘 + 구현 + 조건
1. R,C의 값에 따라 최대 기쁨을 갖는 경로가 결정된다.
2. R,C가 (홀,홀), (홀,짝), (짝,홀)의 경우 모든 경로를 탐색하여 최대 기쁨을 갖는 이동 경로를 만들 수 있다.
R,C가 (짝,짝)의 경우, (R,C)의 지점 중 R + C가 홀수이면서, 최소 기쁨을 갖는 지점을 제외하면 최대 기쁨을 갖는 이동 경로를 만들 수 있다.
3. (짝,짝)일 때, 홀수인 지점 + 최소 기쁨을 갖는 지점을 기준으로 잡고, 시작 지점과 끝 지점에서 제외하려는 지점까지 스네이크 방식으로 이동경로를 
최소화하고, 제외하려는 지점에서 해당 라인 끝지점까지 모든 경로를 이용하여 도달할 수 있도록 한다.

R, C의 값이 짝수인 경우 해결 방법 도출 과정
짝수의 R,C를 갖는 경우, TC를 두고 시뮬레이션을 통해 규칙성을 발견해야 된다고 생각한다.
(R,C)의 특정 지점을 제외한 최대 기쁨의 경로를 시뮬레이션한 경우, 1개를 제외한 모든 경로가 가능하거나
3개의 경로를 제외한 모든 경로를 가는 경우가 존재했다.
이때, 각 지점마다 이동할 수 있는 횟수를 저장하고 이동 시, 주변의 지점에 이동 횟수를 감소시켜 동기화하는 방법도
생각해봤지만 해당 방법은 결국 DFS를 사용하는 방법이므로 시간초과가 발생할 것이라고 생각한다.
시뮬레이션한 TC를 살펴보면 특정 지점을 제외한 경우 1개를 제외한 모든 경로를 이동할 수 있었고, 특정 지점은 제외한 지점을
포함해서 3개의 지점을 제외한 모든 경로를 이동할 수 있었다.
이때, (R,C) => R + C의 합이 홀수인 지점은 1개만을 제외할 수 있었고, R + C가 짝수인 지점은 3개의 지점을 제외해야 했다.
R + C가 짝수인 지점 1개와 나머지 랜덤한 2개의 지점을 제외하고 모든 경로를 이을 수 있는데 이 경우 2개의 지점을 선택하는 것이 곤란했다.
R + C가 짝수인 지점에서 최소값을 선택한 경우, 2개 외의 지점을 추가로 제외하고 이동하므로 최대 기쁨을 만족할 수 없었고,
최소한의 2개의 지점을 선택한 경우는 R + C가 홀수인 지점 2개를 선택한 경우인데 이때, R + C가 짝수인 지점이 최소이면 나머지 2개의 지점은
최소값보다 더 큰값의 기쁨을 갖는 지점을 선택하게 된다.
R + C가 홀수인 지점에서 최소값을 선택한 경우, 해당 지점을 제외하고 모든 경로를 이동할 수 있다.
따라서, R,C가 짝수일 때, R + C가 홀수인 지점이 최소값인 경우만을 고려하여 한 지점을 제외한 모든 경로를 이동하는 것이 최대 기쁨을 갖는 경로가 된다.
R = C = 4
3 2 1 2
2 2 2 1
2 2 2 2
2 2 2 3
R + C가 홀수이면서 최소 기쁨을 갖는 지점 (0,1)을 기준으로 32 - 2 = 30
R + C가 짝수이면서 최소 기쁨을 갖는 지점 (0,2)을 기준으로 짝수합을 갖는 지점을 추가하여 제외하면 제외해야할 지점이 추가되므로,
최대 기쁨을 갖으려면, (0,3), (3,2)를 제외하고 최대 기쁨은 32 - 5 = 27 이다.
따라서, 최대 기쁨을 갖는 경로는 R + C가 최소값을 갖는 지점 한개를 제외한 모든 경로를 지나가는 것이다.
 */
public class BOJ2873 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int R,C,mr,mc;
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
                for(int r = 0; r < mr - 1; r += 2) {
                    for(int c = 0; c < C - 1; c++) ans.append("R");
                    ans.append("D");
                    for(int c = 0; c < C - 1; c++) ans.append("L");
                    ans.append("D");
                }

                for(int i = 0; i < mc - 1; i += 2) ans.append("DRUR");

                if(mr % 2 == 1) ans.append("RD");
                else ans.append("DR");

                for(int c = 0; c < C - mc - 2; c += 2) ans.append("RURD");

                for(int r = 0; r < R - mr - 2; r += 2) {
                    ans.append("D");
                    for(int c = 0; c < C - 1; c++) ans.append("L");
                    ans.append("D");
                    for(int c = 0; c < C - 1; c++) ans.append("R");
                }
            }
        }
    }

    private static void min_gladness_pos() {
        int min_gladness = 1001;

        for(int r = 0; r < R; r++) {
            for(int c = 0; c < C; c++) {
                if((r + c) % 2 != 1) continue;
                if(gladness[r][c] <= min_gladness) {
                    mr = r;
                    mc = c;
                    min_gladness = gladness[r][c];
                }
            }
        }
    }

    private static void solve() {
        min_gladness_pos();
        if(R % 2 == 1 && C % 2 == 1) {
            move_roller_coaster(0);
        } else if(R % 2 == 1 || C % 2 == 1) {
            move_roller_coaster(1);
        } else {
            move_roller_coaster(2);
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
