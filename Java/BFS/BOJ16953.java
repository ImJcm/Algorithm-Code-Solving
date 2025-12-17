package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
A → B

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	58106	23936	18971	39.725%
문제
정수 A를 B로 바꾸려고 한다. 가능한 연산은 다음과 같은 두 가지이다.

2를 곱한다.
1을 수의 가장 오른쪽에 추가한다.
A를 B로 바꾸는데 필요한 연산의 최솟값을 구해보자.

입력
첫째 줄에 A, B (1 ≤ A < B ≤ 109)가 주어진다.

출력
A를 B로 바꾸는데 필요한 연산의 최솟값에 1을 더한 값을 출력한다. 만들 수 없는 경우에는 -1을 출력한다.

예제 입력 1
2 162
예제 출력 1
5
2 → 4 → 8 → 81 → 162

예제 입력 2
4 42
예제 출력 2
-1
예제 입력 3
100 40021
예제 출력 3
5
100 → 200 → 2001 → 4002 → 40021

출처
문제를 번역한 사람: baekjoon
알고리즘 분류
그래프 이론
그리디 알고리즘
그래프 탐색
너비 우선 탐색
 */
/*
처음 접근 방법으로 bfs와 그래프 이론을 활용하여 최초 접근 시, 연산 횟수는 최솟값을 만족하기 때문에 처음으로 B가 나오는 연산 횟수를 최솟값으로
확정하고 bfs를 종료시킨다.

그리고, 이미 도달한 숫자를 HashSet에 저장하고 있다.

틀린 이유 : A,B의 범위가 10^9승까지 가능하지만, A = 9 * 10^8일 때, 1번 연산 또는 2번 연산을 할 때, int형 범위를 넘어가기 때문에 StackOverFlow가 발생할 수 있다.

따라서, int -> long 타입으로 수정

알고리즘 핵심
1. bfs, 그래프 객체
2. 최초 접근 시, 최소 연산을 보장
3. 두 연산의 결과물이 겹치는 경우가 없다. - visited가 필요없음
4. int, long 타입의 범위 고려
(cur * 10 + 1이 32bit 내에서 존재할 수 없을 수 있다. - https://www.acmicpc.net/board/view/136033)
 */
public class BOJ16953 {
    static class BOJ16953_number {
        long num;
        int operation_cnt;

        BOJ16953_number(long num, int operation_cnt) {
            this.num = num;
            this.operation_cnt = operation_cnt;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int A,B,ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        bfs();

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans + 1);
    }

    static void bfs() {
        Queue<BOJ16953_number> q = new LinkedList<>();

        q.offer(new BOJ16953_number(A,0));

        while(!q.isEmpty()) {
            BOJ16953_number now = q.poll();

            if(now.num == B) {
                ans = Math.min(ans, now.operation_cnt);
                return;
            }

            if(now.num * 10 + 1 <= B) {
                q.offer(new BOJ16953_number(now.num * 10 + 1, now.operation_cnt + 1));
            }

            if(now.num * 2 <= B) {
                q.offer(new BOJ16953_number(now.num * 2, now.operation_cnt + 1));
            }
        }
    }

    public static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        A = Integer.parseInt(input[0]);
        B = Integer.parseInt(input[1]);

        ans = Integer.MAX_VALUE;
    }
}

class BOJ16953_failure_code {
    static class BOJ16953_number {
        int num, operation_cnt;

        BOJ16953_number(int num, int operation_cnt) {
            this.num = num;
            this.operation_cnt = operation_cnt;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int A,B,ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        bfs();

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans + 1);
    }

    static void bfs() {
        Queue<BOJ16953_number> q = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();

        q.offer(new BOJ16953_number(A,0));
        visited.add(A);

        while(!q.isEmpty()) {
            BOJ16953_number now = q.poll();

            if(now.num == B) {
                ans = Math.min(ans, now.operation_cnt);
                return;
            }

            if(!visited.contains(now.num * 10 + 1) && now.num * 10 + 1 <= B) {
                q.offer(new BOJ16953_number(now.num * 10 + 1, now.operation_cnt + 1));
                visited.add(now.num * 10 + 1);
            }

            if(!visited.contains(now.num * 2) && now.num * 2 <= B) {
                q.offer(new BOJ16953_number(now.num * 2, now.operation_cnt + 1));
                visited.add(now.num * 2);
            }
        }
    }

    public static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        A = Integer.parseInt(input[0]);
        B = Integer.parseInt(input[1]);

        ans = Integer.MAX_VALUE;
    }
}
