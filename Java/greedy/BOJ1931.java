package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
회의실 배정

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	256557	87156	59983	31.518%
문제
한 개의 회의실이 있는데 이를 사용하고자 하는 N개의 회의에 대하여 회의실 사용표를 만들려고 한다. 각 회의 I에 대해 시작시간과 끝나는 시간이 주어져 있고, 각 회의가 겹치지 않게 하면서 회의실을 사용할 수 있는 회의의 최대 개수를 찾아보자. 단, 회의는 한번 시작하면 중간에 중단될 수 없으며 한 회의가 끝나는 것과 동시에 다음 회의가 시작될 수 있다. 회의의 시작시간과 끝나는 시간이 같을 수도 있다. 이 경우에는 시작하자마자 끝나는 것으로 생각하면 된다.

입력
첫째 줄에 회의의 수 N(1 ≤ N ≤ 100,000)이 주어진다. 둘째 줄부터 N+1 줄까지 각 회의의 정보가 주어지는데 이것은 공백을 사이에 두고 회의의 시작시간과 끝나는 시간이 주어진다. 시작 시간과 끝나는 시간은 231-1보다 작거나 같은 자연수 또는 0이다.

출력
첫째 줄에 최대 사용할 수 있는 회의의 최대 개수를 출력한다.

예제 입력 1
11
1 4
3 5
0 6
5 7
3 8
5 9
6 10
8 11
8 12
2 13
12 14
예제 출력 1
4
힌트
(1,4), (5,7), (8,11), (12,14) 를 이용할 수 있다.

출처
빠진 조건을 찾은 사람: andy627
데이터를 추가한 사람: jws0324, kimchangyoung, rosedskim
알고리즘 분류
그리디 알고리즘
정렬
 */
/*
알고라즘 핵심
그리디 알고리즘 + 정렬
1. 회의가 끝나는 시간을 기준으로 오름차순 정렬을 수행한다. 이때, 같은 시간이라면 회의의 시작시간이 빠른 순으로 오름차순 정렬한다.
2. 정렬이 완료된 배열에서 0번 인덱스를 시작으로 회의를 배정하여 최대 회의 갯수를 도출한다.
*/
public class BOJ1931 {
    static class BOJ1931_meeting implements Comparable<BOJ1931_meeting> {
        int start,end;

        BOJ1931_meeting(int s, int e) {
            this.start = s;
            this.end = e;
        }

        @Override
        public int compareTo(BOJ1931_meeting o) {
            return this.end == o.end ? this.start - o.start : this.end - o.end;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,cur_time,ans;
    static BOJ1931_meeting[] meetings;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        Arrays.sort(meetings);

        cur_time = meetings[0].start;

        for(int i = 0; i < N; i++) {
            if(cur_time <= meetings[i].start && cur_time <= meetings[i].end) {
                ans++;
                cur_time = meetings[i].end;
            }
        }

        System.out.println(ans);
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        meetings = new BOJ1931_meeting[N];

        String[] input;
        for(int i = 0; i < N; i++) {
            input = br.readLine().split(" ");

            meetings[i] = new BOJ1931_meeting(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
        }

        cur_time = 0;
        ans = 0;
    }
}
