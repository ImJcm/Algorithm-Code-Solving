package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
새로운 하노이 탑

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
5 초	512 MB	1477	785	572	54.218%
문제
오늘은 새로운 하노이 탑 게임을 해보려고 한다. 이 게임의 규칙은 다음과 같다.

막대는 총 세 가지 종류가 있다. 막대 A, 막대 B, 막대 C
게임이 시작될 때, 각각의 막대에는 0개 또는 그 이상의 원판이 놓여져 있다.
모든 원판의 크기는 같으며, 원판의 종류도 A, B, C로 세 가지가 있다. 원판은 원판 A, 원판 B, 원판 C와 같이 표현한다.
한 번 움직이는 것은 한 막대의 가장 위에 있는 원판을 다른 막대의 가장 위로 옮기는 것이다.
게임의 목표는 막대 A에는 원판 A만, 막대 B는 원판 B만, 막대 C는 원판 C만 놓여져 있어야 한다.
되도록 최소로 움직여야 한다.
막대 A, 막대 B, 막대 C에 놓여져 있는 원판의 상태가 주어졌을 때, 게임의 목표를 달성하는데 필요한 움직임의 최소 횟수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 막대 A에 놓여져 있는 원판의 개수와 막대 A의 상태, 둘째 줄에 막대 B에 놓여져 있는 원판의 개수와 막대 B의 상태, 셋째 줄에 막대 C에 놓여져 있는 원판의 개수와 막대 C의 상태가 주어진다. 막대의 상태는 밑에 있는 원판부터 주어진다.

각 막대의 상태는 A, B, C로만 이루어진 문자열이며, 모든 막대에 놓여져 있는 원판 개수의 합은 1보다 크거나 같고, 10보다 작거나 같다.

출력
게임의 목표를 달성하는데 필요한 움직임의 최소 횟수를 출력한다.

예제 입력 1
1 A
2 AA
2 AA
예제 출력 1
4
예제 입력 2
1 B
1 C
1 A
예제 출력 2
5
예제 입력 3
3 CBA
0
0
예제 출력 3
5
힌트
예제 2의 경우에 다음과 같이 움직이면 된다.
원판 A를 막대 A로
원판 C를 막대 C로
원판 A를 막대 C로
원판 B를 막대 B로
원판 A를 막대 A로

예제 3의 경우에 다음과 같이 움직이면 된다.
원판 A를 막대 C로
원판 B를 막대 B로
원판 A를 막대 B로
원판 C를 막대 C로
원판 A를 막대 A로

출처
문제를 번역한 사람: baekjoon
알고리즘 분류
자료 구조
그래프 이론
그래프 탐색
너비 우선 탐색
해시를 사용한 집합과 맵
 */
/*
문제를 보고 어떤식으로 bfs를 구성해야할까 고민하다가 원판을 구조체로 사용하고 내부 정보로 현재 위치한 막대, 끼워진 순서, 원반 정보(A,B,C)를
가지는 형태로 구성하려다 이 형태로 구성하게 되면 방문여부를 체크하기 어렵다는 생각이 들었다.

그래서 특정 원반을 옮기고 난 후의 상태를 나타낼 수 있는 것을 bfs에 전달하게 되면 이동한 상태의 여부와 방문 여부를 알기 쉽다고 생각하여 옮긴 후의
원반들의 상태들의 모든 정보를 갖는 형태로 구성하도록 하였고, 원반 상태를 변경하고 queue에 삽입하는 과정에서 sarrow copy가 이루어지기 때문에
deep copy하여 넘겨주는 과정을 별도로 만들어 사용할 수 있도록 하였다.
(얕은 복사를 수행하게 되면 BOJ12906_stencil 내부의 주소값을 공유하게 되어 서로 값의 변경이 되는 영향을 미치기 때문이다.)

하지만, 방문여부를 어떻게 검사할 수 있을까 생각하다가 도저히 아이디어가 떠오르지 않아 정답코드를 참고하였다.
https://velog.io/@hyeon930/BOJ-12906-%EC%83%88%EB%A1%9C%EC%9A%B4-%ED%95%98%EB%85%B8%EC%9D%B4-%ED%83%91-Java

구조체를 구성하는 방법으로 구현체 내부에 3개의 ArrayList를 사용하는 것까진 동일하였으나, 방문 여부를 체크하는 것으로
HashSet을 활용한 것을 볼 수 있었다.

현재 원반들의 상태를 A 막대 부터 C 막대 까지 원반들의 순서를 String 형태로 나열하여 방문여부를 체크하였다.

조금만 더 시간을 가지고 아이디어를 떠올렸다면 스스로 해결했을 것이라고 생각한다.
 */
public class BOJ12906 {
    static class BOJ12906_stencil {
        int count;
        ArrayList<String>[] state;

        BOJ12906_stencil(int count) {
            this.count = count;
            this.state = new ArrayList[3];

            for(int i=0;i<3;i++) {
                this.state[i] = new ArrayList<>();
            }
        }

        public String visit_check() {
            StringBuilder sb = new StringBuilder();

            for(String s : this.state[0]) sb.append(s);
            sb.append("/");
            for(String s : this.state[1]) sb.append(s);
            sb.append("/");
            for(String s : this.state[2]) sb.append(s);

            return sb.toString();
        }

    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BOJ12906_stencil hanoi;
    static String end_state;
    static HashSet<String> visited;

    public static void main(String[] args) throws IOException {
        init_setting();
        solve();
    }

    static void solve() {
        bfs();
    }

    static void bfs() {
        Queue<BOJ12906_stencil> q = new LinkedList<>();

        q.offer(copy(hanoi));
        visited.add(hanoi.visit_check());

        while(!q.isEmpty()) {
            BOJ12906_stencil now = q.poll();

            if(now.visit_check().equals(end_state)) {
                System.out.println(now.count);
                return;
            }

            for(int i=0;i<3;i++) {
                if(now.state[i].isEmpty()) continue;
                for(int j=0;j<3;j++) {
                    if(i == j) continue;
                    BOJ12906_stencil next_stencil = copy(now);

                    int idx = next_stencil.state[i].size()-1;

                    next_stencil.state[j].add(next_stencil.state[i].remove(idx));
                    if(visited.contains(next_stencil.visit_check())) continue;
                    next_stencil.count = now.count + 1;
                    visited.add(next_stencil.visit_check());
                    q.offer(next_stencil);
                }
            }
        }

    }

    static BOJ12906_stencil copy(BOJ12906_stencil stencil) {
        BOJ12906_stencil new_stencil = new BOJ12906_stencil(stencil.count);

        for(int i=0;i<3;i++) {
            for(String s : stencil.state[i]) {
                new_stencil.state[i].add(s);
            }
        }
        return new_stencil;
    }

    static void init_setting() throws IOException {
        int a,b,c;
        a = b = c = 0;
        String[] input;
        hanoi = new BOJ12906_stencil(0);
        visited = new HashSet<>();

        for(int i=0;i<3;i++) {
            ArrayList<String> arr = new ArrayList<>();
            input = br.readLine().split(" ");
            String[] stencil = new String[]{""};
            if(!input[0].equals("0")) {
                stencil = input[1].split("");
            }
            for(int j=0;j<Integer.parseInt(input[0]);j++) {
                String s = stencil[j];
                if(s.equals("A")) a++;
                if(s.equals("B")) b++;
                if(s.equals("C")) c++;
                arr.add(s);
            }
            hanoi.state[i] = arr;

        }

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<a;i++) {
            sb.append("A");
        }
        sb.append("/");
        for(int i=0;i<b;i++) {
            sb.append("B");
        }
        sb.append("/");
        for(int i=0;i<c;i++) {
            sb.append("C");
        }
        end_state = sb.toString();
    }
}
