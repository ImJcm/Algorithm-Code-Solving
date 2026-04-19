package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
⚾

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초 (추가 시간 없음) (하단 참고)	512 MB	17538	8368	5395	44.794%
문제
⚾는 9명으로 이루어진 두 팀이 공격과 수비를 번갈아 하는 게임이다. 하나의 이닝은 공격과 수비로 이루어져 있고, 총 N이닝 동안 게임을 진행해야 한다. 한 이닝에 3아웃이 발생하면 이닝이 종료되고, 두 팀이 공격과 수비를 서로 바꾼다.

두 팀은 경기가 시작하기 전까지 타순(타자가 타석에 서는 순서)을 정해야 하고, 경기 중에는 타순을 변경할 수 없다. 9번 타자까지 공을 쳤는데 3아웃이 발생하지 않은 상태면 이닝은 끝나지 않고, 1번 타자가 다시 타석에 선다. 타순은 이닝이 변경되어도 순서를 유지해야 한다. 예를 들어, 2이닝에 6번 타자가 마지막 타자였다면, 3이닝은 7번 타자부터 타석에 선다.

공격은 투수가 던진 공을 타석에 있는 타자가 치는 것이다. 공격 팀의 선수가 1루, 2루, 3루를 거쳐서 홈에 도착하면 1점을 득점한다. 타자가 홈에 도착하지 못하고 1루, 2루, 3루 중 하나에 머물러있을 수 있다. 루에 있는 선수를 주자라고 한다. 이닝이 시작될 때는 주자는 없다.

타자가 공을 쳐서 얻을 수 있는 결과는 안타, 2루타, 3루타, 홈런, 아웃 중 하나이다. 각각이 발생했을 때, 벌어지는 일은 다음과 같다.

안타: 타자와 모든 주자가 한 루씩 진루한다.
2루타: 타자와 모든 주자가 두 루씩 진루한다.
3루타: 타자와 모든 주자가 세 루씩 진루한다.
홈런: 타자와 모든 주자가 홈까지 진루한다.
아웃: 모든 주자는 진루하지 못하고, 공격 팀에 아웃이 하나 증가한다.
한 야구팀의 감독 아인타는 타순을 정하려고 한다. 아인타 팀의 선수는 총 9명이 있고, 1번부터 9번까지 번호가 매겨져 있다. 아인타는 자신이 가장 좋아하는 선수인 1번 선수를 4번 타자로 미리 결정했다. 이제 다른 선수의 타순을 모두 결정해야 한다. 아인타는 각 선수가 각 이닝에서 어떤 결과를 얻는지 미리 알고 있다. 가장 많은 득점을 하는 타순을 찾고, 그 때의 득점을 구해보자.

입력
첫째 줄에 이닝 수 N(2 ≤ N ≤ 50)이 주어진다. 둘째 줄부터 N개의 줄에는 각 선수가 각 이닝에서 얻는 결과가 1번 이닝부터 N번 이닝까지 순서대로 주어진다. 이닝에서 얻는 결과는 9개의 정수가 공백으로 구분되어져 있다. 각 결과가 의미하는 정수는 다음과 같다.

안타: 1
2루타: 2
3루타: 3
홈런: 4
아웃: 0
각 이닝에는 아웃을 기록하는 타자가 적어도 한 명 존재한다.

출력
아인타팀이 얻을 수 있는 최대 점수를 출력한다.

예제 입력 1
2
4 0 0 0 0 0 0 0 0
4 0 0 0 0 0 0 0 0
예제 출력 1
1
예제 입력 2
2
4 0 0 0 1 1 1 0 0
0 0 0 0 0 0 0 0 0
예제 출력 2
4
예제 입력 3
2
0 4 4 4 4 4 4 4 4
0 4 4 4 4 4 4 4 4
예제 출력 3
43
예제 입력 4
2
4 3 2 1 0 4 3 2 1
1 2 3 4 1 2 3 4 0
예제 출력 4
46
예제 입력 5
9
4 4 4 4 4 4 4 4 0
4 4 4 4 4 4 4 4 0
4 4 4 4 4 4 4 4 0
4 4 4 4 4 4 4 4 0
4 4 4 4 4 4 4 4 0
4 4 4 4 4 4 4 4 0
4 4 4 4 4 4 4 4 0
4 4 4 4 4 4 4 4 0
4 4 4 4 4 4 4 4 0
예제 출력 5
216
예제 입력 6
9
1 2 4 3 0 2 1 0 3
1 2 1 2 0 0 0 0 1
3 4 2 3 1 2 3 4 0
0 1 2 3 4 2 1 0 0
0 0 0 0 0 0 1 4 4
0 4 0 4 0 4 0 4 0
0 4 2 2 2 2 2 2 2
1 1 1 1 1 1 1 1 0
0 2 0 3 0 1 0 2 0
예제 출력 6
89
출처
문제를 만든 사람: baekjoon
빠진 조건을 찾은 사람: jh05013
알고리즘 분류
구현
브루트포스 알고리즘
시간 제한
PyPy3: 1.5 초
PyPy2: 1.5 초
 */
/*
처음 문제를 읽고 한 경기에서 이닝 별로 타선을 재배치할 수 있다고 생각해서 각 이닝별로 타선을 재배치하여 최대의 점수를 뽑고 각 이닝 별로 추출한 최대 점수
를 누적시키는 방법으로 코드를 구현하였는데 예제#4번에서 expect:48로 틀린 코드인 것을 알게 되었다.

알고리즘 핵심
1. 첫 타선을 결정하고 난 후, N 이닝까지 타선을 변경할 수 없다. ★
2. 1~N이닝 까지 3아웃이 될 때까지 타자의 순서를 유지해야 한다.
3. 각 이닝 별로 타자의 점수가 달라지므로, 타선의 순서를 기록하는 과정에서 해당 타자의 번호를 저장하도록 한다.
4. 순서가 고정되면, 각 이닝 별로 해당 타자의 점수를 종합하여 해당 배치에서의 최대 점수를 업데이트한다.

결론 : 문제를 잘 이해하는 것이 중요하다.
 */
public class BOJ17281 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, ans;
    static int[][] batting_results;
    static int[] batting_orders;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        batting_order_decision(1);

        System.out.println(ans);
    }

    static void batting_order_decision(int depth) {
        if(depth == 10) {
            int score = play();
            ans = Math.max(ans, score);
            return;
        }

        if(depth == 4) {
            visited[1] = true;
            batting_orders[depth] = 1;
            batting_order_decision(depth + 1);
            visited[1] = false;
        } else {
            for(int i=2;i<10;i++) {
                if(visited[i]) continue;
                visited[i] = true;
                batting_orders[depth] = i;
                batting_order_decision(depth + 1);
                visited[i] = false;
            }
        }
    }

    static int play() {
        int cur_batting_order = 1;
        int score = 0;

        for (int n = 1; n <= N; n++) {
            int out_count = 0;
            int[] base = new int[] {0,0,0};       //1루, 2루, 3루

            while(out_count < 3) {
                int result = batting_results[n][batting_orders[cur_batting_order]];

                if(result == 0) {
                    out_count++;
                } else {
                    for(int i = 2; i >= 0; i--) {
                        if(base[i] == 0) continue;

                        if(i+result >= 3) {
                            score++;
                        } else {
                            base[i+result] = 1;
                        }

                        base[i] = 0;
                    }

                    if(result == 4) {
                        score++;
                    } else {
                        base[result-1] = 1;
                    }
                }

                cur_batting_order = cur_batting_order == 9 ? 1 : cur_batting_order + 1;
            }
        }

        return score;
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        batting_results = new int[N+1][10];
        batting_orders = new int[10];
        visited = new boolean[10];

        ans = 0;

        for(int i=1;i<=N;i++) {
            String[] input = br.readLine().split(" ");

            for(int j=1;j<10;j++) {
                batting_results[i][j] = Integer.parseInt(input[j-1]);
            }
        }
    }
}