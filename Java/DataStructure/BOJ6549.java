package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
히스토그램에서 가장 큰 직사각형 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	68668	19892	13262	28.442%
문제
히스토그램은 직사각형 여러 개가 아래쪽으로 정렬되어 있는 도형이다. 각 직사각형은 같은 너비를 가지고 있지만, 높이는 서로 다를 수도 있다. 예를 들어, 왼쪽 그림은 높이가 2, 1, 4, 5, 1, 3, 3이고 너비가 1인 직사각형으로 이루어진 히스토그램이다.



히스토그램에서 가장 넓이가 큰 직사각형을 구하는 프로그램을 작성하시오.

입력
입력은 테스트 케이스 여러 개로 이루어져 있다. 각 테스트 케이스는 한 줄로 이루어져 있고, 직사각형의 수 n이 가장 처음으로 주어진다. (1 ≤ n ≤ 100,000) 그 다음 n개의 정수 h1, ..., hn (0 ≤ hi ≤ 1,000,000,000)가 주어진다. 이 숫자들은 히스토그램에 있는 직사각형의 높이이며, 왼쪽부터 오른쪽까지 순서대로 주어진다. 모든 직사각형의 너비는 1이고, 입력의 마지막 줄에는 0이 하나 주어진다.

출력
각 테스트 케이스에 대해서, 히스토그램에서 가장 넓이가 큰 직사각형의 넓이를 출력한다.

예제 입력 1
7 2 1 4 5 1 3 3
4 1000 1000 1000 1000
0
예제 출력 1
8
4000
출처
Contest > University of Ulm Local Contest > University of Ulm Local Contest 2003 H번

문제를 번역한 사람: baekjoon
데이터를 추가한 사람: flareon078, kth004, Lawali
어색한 표현을 찾은 사람: eric00513
알고리즘 분류
자료 구조
세그먼트 트리
분할 정복
스택
 */
public class BOJ6549 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    private static class Solve {
        private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        void solve() throws IOException {
            init_setting();

        }

        private void init_setting() throws IOException {

        }
    }
}
