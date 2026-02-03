package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
모양 만들기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	5545	2159	1575	36.265%
문제
N×M인 배열에서 모양을 찾으려고 한다. 배열의 각 칸에는 0과 1 중의 하나가 들어있다. 두 칸이 서로 변을 공유할때, 두 칸을 인접하다고 한다.

1이 들어 있는 인접한 칸끼리 연결했을 때, 각각의 연결 요소를 모양이라고 부르자. 모양의 크기는 모양에 포함되어 있는 1의 개수이다.

배열의 칸 하나에 들어있는 수를 변경해서 만들 수 있는 모양의 최대 크기를 구해보자.

입력
첫째 줄에 배열의 크기 N과 M이 주어진다. 둘째 줄부터 N개의 줄에는 배열에 들어있는 수가 주어진다.

출력
첫째 줄에 수 하나를 변경해서 만들 수 있는 모양의 최대 크기를 출력한다.

제한
2 ≤ N, M ≤ 1,000
0과 1의 개수는 하나 이상이다.
예제 입력 1
3 3
0 1 1
0 0 1
0 1 0
예제 출력 1
5
예제 입력 2
5 4
1 1 0 0
1 0 1 0
1 0 1 0
0 1 1 0
1 0 0 1
예제 출력 2
10
예제 입력 3
3 4
0 1 0 1
0 0 0 1
1 1 0 1
예제 출력 3
6
출처
문제를 번역한 사람: baekjoon
어색한 표현을 찾은 사람: jh05013
알고리즘 분류
그래프 이론
그래프 탐색
너비 우선 탐색
집합과 맵
깊이 우선 탐색
 */
public class BOJ16932 {
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
