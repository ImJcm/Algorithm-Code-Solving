package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
소수 경로 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	15415	9203	6586	60.031%
문제
소수를 유난히도 좋아하는 창영이는 게임 아이디 비밀번호를 4자리 ‘소수’로 정해놓았다. 어느 날 창영이는 친한 친구와 대화를 나누었는데:

“이제 슬슬 비번 바꿀 때도 됐잖아”
“응 지금은 1033으로 해놨는데... 다음 소수를 무엇으로 할지 고민중이야"
“그럼 8179로 해”
“흠... 생각 좀 해볼게. 이 게임은 좀 이상해서 비밀번호를 한 번에 한 자리 밖에 못 바꾼단 말이야. 예를 들어 내가 첫 자리만 바꾸면 8033이 되니까 소수가 아니잖아. 여러 단계를 거쳐야 만들 수 있을 것 같은데... 예를 들면... 1033 1733 3733 3739 3779 8779 8179처럼 말이야.”
“흠...역시 소수에 미쳤군. 그럼 아예 프로그램을 짜지 그래. 네 자리 소수 두 개를 입력받아서 바꾸는데 몇 단계나 필요한지 계산하게 말야.”
“귀찮아”
그렇다. 그래서 여러분이 이 문제를 풀게 되었다. 입력은 항상 네 자리 소수만(1000 이상) 주어진다고 가정하자. 주어진 두 소수 A에서 B로 바꾸는 과정에서도 항상 네 자리 소수임을 유지해야 하고, ‘네 자리 수’라 하였기 때문에 0039 와 같은 1000 미만의 비밀번호는 허용되지 않는다.

입력
첫 줄에 test case의 수 T가 주어진다. 다음 T줄에 걸쳐 각 줄에 1쌍씩 네 자리 소수가 주어진다.

출력
각 test case에 대해 두 소수 사이의 변환에 필요한 최소 회수를 출력한다. 불가능한 경우 Impossible을 출력한다.

예제 입력 1
3
1033 8179
1373 8017
1033 1033
예제 출력 1
6
7
0
출처
ICPC > Regionals > Europe > Northwestern European Regional Contest > NWERC 2006 G번

문제의 오타를 찾은 사람: waylight3
알고리즘 분류
수학
그래프 이론
그래프 탐색
정수론
너비 우선 탐색
소수 판정
에라토스테네스의 체
 */
/*
에라토스테네스의 체를 통해 10000까지의 소수를 판별할 수 있는 배열 Sieve를 만들고, bfs를 통해 자리수마다 숫자를 바꿔서 소수인지와
방문여부를 확인하여 비밀번호 변경 횟수를 업데이트한다.

아래 코드를 완성하기까지 에러사항
1. 비밀번호로 변경할 수 있는 숫자를 1~9까지만 생각해서 0을 고려하지 못했다.
2. 0을 추가하는 코드로 변경 후, 4자릿수를 만족해야 하므로 1000의 자리수에 0이 오는 경우를 제외하였다.
 */
public class BOJ1963 {
    static class BOJ1963_number {
        int num;
        int change_cnt;

        BOJ1963_number(int n, int c_c) {
            this.num = n;
            this.change_cnt = c_c;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T,testcase_number = 1;
    static boolean[][] visited;
    static int[] Sieve;
    static ArrayList<BOJ1963_number> test_case;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        Eratosthnes();
      
        while(!test_case.isEmpty()) {
            BOJ1963_number start = test_case.remove(0);
            BOJ1963_number target = test_case.remove(0);

            int result = bfs(start, target, testcase_number++);
            System.out.println(result == -1 ? "Impossible" : result);
        }
    }

    static int bfs(BOJ1963_number s, BOJ1963_number t, int tc_n) {
        Queue<BOJ1963_number> q = new LinkedList<>();
        int[] n = new int[4];
        int[] r_n = new int[4];

        q.offer(s);
        visited[tc_n][s.num] = true;

        while(!q.isEmpty()) {
            BOJ1963_number now = q.poll();
            int num = now.num;
            
            if(num == t.num) {
                return now.change_cnt;
            }

            n[0] = num % 10;
            n[1] = ((num / 10) % 10) * 10;
            n[2] = ((num / 100) % 10) * 100;
            n[3] = ((num / 1000) % 10) * 1000;
            
            r_n[0] = n[3] + n[2] + n[1];
            r_n[1] = n[3] + n[2] + n[0];
            r_n[2] = n[3] + n[1] + n[0];
            r_n[3] = n[2] + n[1] + n[0];
            
            for(int i=0;i<4;i++) {
                for(int j=0;j<10;j++) {
                    if(i == 3 && j == 0) continue;
                    int change_number = (int) Math.pow(10,i) * j;
                    int r = r_n[i] + change_number;

                    if(visited[tc_n][r] || Sieve[r] == 1) continue;

                    visited[tc_n][r] = true;
                    q.offer(new BOJ1963_number(r,now.change_cnt + 1));
                }
            }
        }
        return -1;
    }

    static void Eratosthnes() {
        for(int i=1;i<10000;i++) {
            Sieve[i] = 0;
        }

        for(int i=2;i<10000;i++) {
            if(Sieve[i] == 1) continue;
            for(int j=2*i;j<10000;j += i) {
                Sieve[j] = 1;
            }
        }
    }

    static void init_setting() throws IOException {
        T = Integer.parseInt(br.readLine());

        visited = new boolean[T+1][10000];
        test_case = new ArrayList<>();
        Sieve = new int[10000];

        for(int i=1;i<=T;i++) {
            String[] input = br.readLine().split(" ");
            test_case.add(new BOJ1963_number(Integer.parseInt(input[0]),0));
            test_case.add(new BOJ1963_number(Integer.parseInt(input[1]),0));
            for(int j=1000;j<10000;j++) {
                visited[i][j] = false;
            }
        }
    }
}
