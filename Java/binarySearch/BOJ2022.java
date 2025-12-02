package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
사다리 스페셜 저지다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	5863	3344	2197	56.319%
문제
아래의 그림과 같이 높은 빌딩 사이를 따라 좁은 길이 나있다. 두 개의 사다리가 있는데 길이가 x인 사다리는 오른쪽 빌딩의 아래를 받침대로 하여 왼쪽 빌딩에 기대져 있고 길이가 y인 사다리는 왼쪽 빌딩의 아래를 받침대로 하여 오른쪽 빌딩에 기대져 있다. 그리고 두 사다리는 땅에서부터 정확하게 c인 지점에서 서로 교차한다. 그렇다면 두 빌딩은 얼마나 떨어져 있는 걸까?



입력
첫째 줄에 차례대로 x, y, c에 해당하는 양의 실수 세 개가 입력된다. 수는 소수점 여섯째 자리까지 주어질 수 있으며, 3,000,000,000보다 작거나 같다.

출력
두 빌딩사이에 너비가 되는 수치를 출력한다. 절대/상대 오차는 10-3까지 허용한다.

예제 입력 1
30 40 10
예제 출력 1
26.033
예제 입력 2
12.619429 8.163332 3r
예제 출력 2
7.000
예제 입력 3
10 10 3
예제 출력 3
8.000
예제 입력 4
10 10 1
예제 출력 4
9.798
출처
ICPC > Regionals > North America > Rocky Mountain Regional > Alberta Collegiate Programming Contest > ACPC 2000 B번

데이터를 추가한 사람: jh05013
빠진 조건을 찾은 사람: jh05013
알고리즘 분류
수학
기하학
이분 탐색
피타고라스 정리
 */
/*
알고리즘 핵심
이분 탐색 + 피타고라스 정리 + 수학(기하학)
1. X,Y,C가 주어질 때, 두 빌딩 사이의 거리를 구하는 공식을 만든다.
두 빌딩 사이의 너비 : d
각 사다리가 빌딩에 기대어 진 높이는 각각 sqrt(X^2 - d^2), sqrt(Y^2 - d^2)
각 사다리를 2차원에서 직선이라고 보고, c의 위치를 y축 절편이라고 보면 다음과 같은 두개의 직선 방정식을 만들 수 있다.
y1 = -(sqrt(X^2 - d^2) / d)x + c
y2 = (sqrt(Y^2 - d^2) / d)x + c
이 두 방정식에서 x축의 좌표를 구한 후, 두 값의 절댓값을 더하면 d값이 되므로, 다음과 같은 식을 만들 수 있다.
d = cd / (sqrt(X^2 - d^2)) - (-cd / sqrt(Y^2 - d^2))
=> c / sqrt(X^2 - d^2) + c / sqrt(Y^2 - d^2) = 1
2. X,Y보다 작은 값을 r, l = 1로 설정하여 이분 탐색을 진행하고, l,r을 조정하는 과정에서 0.001로 설정한다.
(0.001로 설정한 이유 : 10^3만큼의 오차를 허용하기 때문이다.)
3. 10^3만큼의 오차를 허용하므로, 0.001 보다 크고, 1.999보다 작거나 같은 값이 나오는 경우 d 값에 가능하다.
1보다 작은 값은 -1을 반환하여, m값을 높이고, 1보다 큰 값은 1을 반환하여 m값을 낮춘다.
 */
public class BOJ2022 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        double X,Y,C,ans,l,r;

        private void solve() throws IOException {
            init_setting();

            binary_search();

            System.out.println(ans);
        }

        private void binary_search() {
            if(l > r) return;

            double m = (l + r) / 2;

            int ret = cal_width(m);

            if(ret == -1) l = m + 0.001;
            else r = m - 0.001;

            binary_search();
        }

        private int cal_width(double w) {
            double r = (C / (Math.sqrt(X * X - w * w))) + (C / (Math.sqrt(Y * Y - w * w)));

            if(0.001 <= r && r <= 1.999) ans = Math.round(w * 1000) / 1000.0;

            if(r <= 1) return -1;
            else return 1;
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            X = Double.parseDouble(input[0]);
            Y = Double.parseDouble(input[1]);
            C = Double.parseDouble(input[2]);

            ans = 0;

            l = 1;
            r = Math.min(X,Y);
        }
    }
}
