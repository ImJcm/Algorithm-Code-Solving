package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
늑대와 양 스페셜 저지
시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	8708	4262	3257	48.743%
문제
크기가 R×C인 목장이 있고, 목장은 1×1 크기의 칸으로 나누어져 있다. 각각의 칸에는 비어있거나, 양 또는 늑대가 있다. 양은 이동하지 않고 위치를 지키고 있고, 늑대는 인접한 칸을 자유롭게 이동할 수 있다. 두 칸이 인접하다는 것은 두 칸이 변을 공유하는 경우이다.

목장에 울타리를 설치해 늑대가 양이 있는 칸으로 갈 수 없게 하려고 한다. 늑대는 울타리가 있는 칸으로는 이동할 수 없다. 울타리를 설치해보자.

입력
첫째 줄에 목장의 크기 R, C가 주어진다.

둘째 줄부터 R개의 줄에 목장의 상태가 주어진다. '.'는 빈 칸, 'S'는 양, 'W'는 늑대이다.

출력
늑대가 양이 있는 칸으로 갈 수 없게 할 수 있다면 첫째 줄에 1을 출력하고, 둘째 줄부터 R개의 줄에 목장의 상태를 출력한다. 울타리는 'D'로 출력한다. 울타리를 어떻게 설치해도 늑대가 양이 있는 칸으로 갈 수 있다면 첫째 줄에 0을 출력한다.

제한
1 ≤ R, C ≤ 500
예제 입력 1
6 6
..S...
..S.W.
.S....
..W...
...W..
......
예제 출력 1
1
..SD..
..SDW.
.SD...
.DW...
DD.W..
......
예제 입력 2
1 2
SW
예제 출력 2
0
예제 입력 3
5 5
.S...
...S.
S....
...S.
.S...
예제 출력 3
1
.S...
...S.
S.D..
...S.
.S...
노트
이 문제는 설치해야 하는 울타리의 최소 개수를 구하는 문제가 아니다.

출처
문제를 번역한 사람: baekjoon
 */
public class BOJ16956 {
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
