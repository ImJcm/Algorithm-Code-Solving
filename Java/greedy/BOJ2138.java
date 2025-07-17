package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
전구와 스위치

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	16877	6468	4974	38.872%
문제
N개의 스위치와 N개의 전구가 있다. 각각의 전구는 켜져 있는 상태와 꺼져 있는 상태 중 하나의 상태를 가진다. i(1 < i < N)번 스위치를 누르면 i-1, i, i+1의 세 개의 전구의 상태가 바뀐다. 즉, 꺼져 있는 전구는 켜지고, 켜져 있는 전구는 꺼지게 된다. 1번 스위치를 눌렀을 경우에는 1, 2번 전구의 상태가 바뀌고, N번 스위치를 눌렀을 경우에는 N-1, N번 전구의 상태가 바뀐다.

N개의 전구들의 현재 상태와 우리가 만들고자 하는 상태가 주어졌을 때, 그 상태를 만들기 위해 스위치를 최소 몇 번 누르면 되는지 알아내는 프로그램을 작성하시오.

입력
첫째 줄에 자연수 N(2 ≤ N ≤ 100,000)이 주어진다. 다음 줄에는 전구들의 현재 상태를 나타내는 숫자 N개가 공백 없이 주어진다. 그 다음 줄에는 우리가 만들고자 하는 전구들의 상태를 나타내는 숫자 N개가 공백 없이 주어진다. 0은 켜져 있는 상태, 1은 꺼져 있는 상태를 의미한다.

출력
첫째 줄에 답을 출력한다. 불가능한 경우에는 -1을 출력한다.

예제 입력 1
3
000
010
예제 출력 1
3
출처
데이터를 추가한 사람: kyt9412
알고리즘 분류
그리디 알고리즘
 */
/*
알고리즘 핵심
그리디 알고리즘
1. 각 스위치는 기준점으로부터 -1,0,1만큼 영향을 미친다.
2. 왼쪽 스위치부터 오른쪽 끝 스위치까지 진행한다.
3. i번 스위치는 i번째 기준으로 i-1의 전구가 원하는 전구의 형태를 만드는데 최종적인 결정을 한다.
4. 1번째 전구를 킨 상태와 키지 않은 상태를 기준으로 1,2,3번 과정을 수행하여 원하는 전구의 형태를 만들고, 마지막에 원하는 전구의 형태가
나오는지 확인하여 스위치의 최소 횟수를 기록한다. 불가능한 경우 -1을 출력한다.

처음 접근한 방식은 그리드 알고리즘의 특성상 특정 기준으로 최선의 선택을 하는 것이므로 i번 스위치를 결정하는데 기준이 있다고 생각하여
해당 스위치를 작동했을 때, 원하는 형태의 전구를 만드는데 가중치가 높은 스위치를 선택하도록 하고 스위치를 키는 과정마다 전구의 형태를 검사하여
최소 횟수를 기록하려고 하였다. 또한, 모든 전구를 한번씩 눌러도 원하는 형태가 되지 않는다면 -1을 출력하도록 하였지만 해당 방법은 가중치가
같은 스위치의 경우 최소와 최적의 선택을 보장하지 못했다.

도저히 접근 방법을 몰라서 풀이 개념을 참고하였다.
- https://www.acmicpc.net/board/view/21452
- https://staticvoidlife.tistory.com/143
 */
public class BOJ2138 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static int[] init_electric_bulb,electric_bulb,desired_state;
    static boolean flag;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        int cnt = 0;

        electric_bulb = Arrays.copyOf(init_electric_bulb,N);

        cnt++;
        turn_on_switch(0);

        for(int i = 1; i < N; i++) {
            if(electric_bulb[i - 1] != desired_state[i - 1]) {
                turn_on_switch(i);
                cnt++;
            }
        }

        ans = is_same_desired_electric_bulb_state() ? cnt : Integer.MAX_VALUE;

        cnt = 0;
        electric_bulb = Arrays.copyOf(init_electric_bulb,N);

        for(int i = 1; i < N; i++) {
            if(electric_bulb[i - 1] != desired_state[i - 1]) {
                turn_on_switch(i);
                cnt++;
            }
        }

        ans = is_same_desired_electric_bulb_state() ? Math.min(ans, cnt) : ans == Integer.MAX_VALUE ? -1 : ans;

        System.out.println(ans);
    }

    /*
        i번 스위치가 원하는 전구의 형태를 만드는데 가중치가 높은 스위치를 선택하여 해당 스위치를 눌러 상태를 변경한다.
        하지만, 해당 방법으로는 가중치가 같은 경우에서 올바른 최소 횟수를 보장하지 못했다.
        4
        0000
        1101
        1번 스위치와 2번 스위치의 순서에 따라 정답 여부가 달라지는데, 2번 스위치를 먼저 키는 경우, 4버 스위치를 눌러 정답이 되지만
        1번 스위치를 먼저 누를 경우, 불가능하다.

        이때, 순서를 결정하는 로직을 결정하지 못했다.
     */
    private static void wrong_solve() {
        while(true) {
            int idx = select_turn_on_switch();

            if(idx == -1) {
                flag = true;
                break;
            }

            turn_on_switch(idx);
            ans++;

            if(is_same_desired_electric_bulb_state()) {
                break;
            }
        }

        System.out.println(flag ? -1 : ans);
    }

    private static boolean is_same_desired_electric_bulb_state() {
        boolean check = true;

        for(int i = 0; i < N; i++) {
            if (electric_bulb[i] != desired_state[i]) {
                check = false;
                break;
            }
        }
        return check;
    }

    private static void turn_on_switch(int idx) {
        for(int i = idx - 1; i <= idx + 1; i++) {
            if(i >= 0 && i < N) {
                electric_bulb[i] = 1 - electric_bulb[i];
            }
        }
        /*if(idx == 0) {
            electric_bulb[idx] = electric_bulb[idx] == 0 ? 1 : 0;
            electric_bulb[idx + 1] = electric_bulb[idx + 1] == 0 ? 1 : 0;
        } else if(idx == N - 1) {
            electric_bulb[idx] = electric_bulb[idx] == 0 ? 1 : 0;
            electric_bulb[idx - 1] = electric_bulb[idx - 1] == 0 ? 1 : 0;
        } else {
            electric_bulb[idx - 1] = electric_bulb[idx - 1] == 0 ? 1 : 0;
            electric_bulb[idx] = electric_bulb[idx] == 0 ? 1 : 0;
            electric_bulb[idx + 1] = electric_bulb[idx + 1] == 0 ? 1 : 0;
        }*/
    }

    private static int select_turn_on_switch() {
        int w = Integer.MIN_VALUE;
        int idx = -1;

        for(int i = 0; i < N; i++) {
            if(visited[i]) continue;
            int ls = i - 1 >= 0 ? electric_bulb[i - 1] == desired_state[i - 1] ? -1 : 1 : 0;
            int ms = electric_bulb[i] == desired_state[i] ? -1 : 2;
            int rs = i + 1 < N ? electric_bulb[i + 1] == desired_state[i + 1] ? -1 : 1 : 0;

            if(w < ls + ms + rs) {
                if(idx != -1) visited[idx] = false;
                w = ls + ms + rs;
                idx = i;
                visited[idx] = true;
            }
        }
        return idx;
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        init_electric_bulb = new int[N];
        electric_bulb = new int[N];
        desired_state = new int[N];

        electric_bulb = Arrays.stream(br.readLine().split(""))
                .mapToInt(Integer::parseInt)
                .toArray();

        desired_state = Arrays.stream(br.readLine().split(""))
                .mapToInt(Integer::parseInt)
                .toArray();

        init_electric_bulb = Arrays.copyOf(electric_bulb,N);

        ans = 0;

        visited = new boolean[N];
    }
}
