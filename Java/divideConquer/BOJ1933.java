package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
스카이라인

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	4750	1535	960	32.686%
문제
N개의 직사각형 모양의 건물들이 주어졌을 때, 스카이라인을 구해내는 프로그램을 작성하시오. 스카이라인은 건물 전체의 윤곽을 의미한다. 즉, 각각의 건물을 직사각형으로 표현했을 때, 그러한 직사각형들의 합집합을 구하는 문제이다.



예를 들어 직사각형 모양의 건물들이 위와 같이 주어졌다고 하자. 각각의 건물은 왼쪽 x좌표와 오른쪽 x좌표, 그리고 높이로 나타난다. 모든 건물들은 편의상 같은 높이의 지면(땅) 위에 있다고 가정하자. 위의 예에서 스카이라인을 구하면 아래와 같다.



입력
첫째 줄에 건물의 개수 N(1 ≤ N ≤ 100,000)이 주어진다. 다음 N개의 줄에는 N개의 건물에 대한 정보가 주어진다. 건물에 대한 정보는 세 정수 L, H, R로 나타나는데, 각각 건물의 왼쪽 x좌표, 높이, 오른쪽 x좌표를 의미한다. (1 ≤ L < R ≤ 1,000,000,000, 1 ≤ H ≤ 1,000,000,000)

출력
첫째 줄에 스카이라인을 출력한다. 출력을 할 때에는 높이가 변하는 지점에 대해서, 그 지점의 x좌표와 그 지점에서의 높이를 출력한다.

예제 입력 1
8
1 11 5
2 6 7
3 13 9
12 7 16
14 3 25
19 18 22
23 13 29
24 4 28
예제 출력 1
1 11 3 13 9 0 12 7 16 3 19 18 22 3 23 13 29 0
출처
문제의 오타를 찾은 사람: wider93
데이터를 추가한 사람: cinador, djm03178, gaelim
빠진 조건을 찾은 사람: jh05013
알고리즘 분류
자료 구조
집합과 맵
스위핑
트리를 사용한 집합과 맵
우선순위 큐
 */
public class BOJ1933 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {

    }

    private static void init_setting() throws IOException {

    }
}
