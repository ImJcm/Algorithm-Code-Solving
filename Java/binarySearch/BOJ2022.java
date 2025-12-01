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
12.619429 8.163332 3
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
public class BOJ2022 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        private void solve() throws IOException {
            init_setting();
        }

        private void init_setting() throws IOException {

        }
    }
}
