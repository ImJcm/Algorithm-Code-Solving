package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
배열에서 이동 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	11687	3213	2133	25.429%
문제
n×n짜리의 배열이 하나 있다. 이 배열의 (1, 1)에서 (n, n)까지 이동하려고 한다. 이동할 때는 상, 하, 좌, 우의 네 인접한 칸으로만 이동할 수 있다.

이와 같이 이동하다 보면, 배열에서 몇 개의 수를 거쳐서 이동하게 된다. 이동하기 위해 거쳐 간 수들 중 최댓값과 최솟값의 차이가 가장 작아지는 경우를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 n(2 ≤ n ≤ 100)이 주어진다. 다음 n개의 줄에는 배열이 주어진다. 배열의 각 수는 0보다 크거나 같고, 200보다 작거나 같은 정수이다.

출력
첫째 줄에 (최대 - 최소)가 가장 작아질 때의 그 값을 출력한다.

예제 입력 1
5
1 1 3 6 8
1 2 2 5 5
4 4 0 3 3
8 0 2 3 4
4 3 0 2 1
예제 출력 1
2
출처
University > Tu-Darmstadt Programming Contest > TUD Contest 2006 6번

Olympiad > USA Computing Olympiad > 2002-2003 Season > USACO US Open 2003 Contest > Green 1번

문제를 번역한 사람: author5
데이터를 추가한 사람: kdk8361, yclock
알고리즘 분류
그래프 이론
그래프 탐색
이분 탐색
너비 우선 탐색
 */
public class BOJ1981 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N;
        int[][] arr;

        private void solve() throws IOException {
            init_setting();
        }

        private void init_setting() throws IOException {

        }
    }
}
