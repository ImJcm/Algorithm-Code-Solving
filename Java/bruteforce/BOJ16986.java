package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
인싸들의 가위바위보

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	3047	1434	970	44.721%
문제
혹시 마지막으로 엠티를 간 것이 언제인가? 엠티를 안간지 꽤 오래됐다면 요즘 유행하는 인싸들의 가위바위보를 모를 것이다. 요즘 인싸들은 엠티에서 평범한 가위바위보를 시시하다는 이유로 더 이상 취급하지 않는다. 대신 가위불바위총번개악마용물공기보스펀지늑대나무사람뱀을 한다. 이 게임의 명칭이 다소 긴 관계로 문제 내에서는 전체 명칭을 적는 대신 이 게임의 또 다른 이름인 인싸 가위바위보로 부르겠다. 인싸 가위바위보는 평범한 가위바위보와 같이 각 손동작간의 상성이 정해져있다.



인싸 가위바위보는 평범한 가위바위보보다 흥미진진하고 재밌지만 3명 이상이 경기를 할 때 누가 이기고 누가 졌는지를 빠르게 알기 힘들다는 단점이 있다. 그렇기에 3명 이상의 사람들 사이에서 인싸 가위바위보를 할 때는 모두가 동시에 경기를 진행하는 대신 일대일 경기를 여러 번 진행해 누가 우승했는지 판단한다. 3명이서 인싸 가위바위보를 할 때의 우승자를 정하기 위한 구체적인 방식은 아래와 같다. 편의상 참가자 3명을 A, B, C라고 하자.

A, B, C는 게임 시작 전 우승을 위해 필요한 승수와 경기 진행 순서를 미리 합의한다. 경기 진행 순서가 A, B, C라고 가정하자.
먼저 A와 B가 경기를 진행해 승자를 결정한다. 만약 두 사람이 같은 손동작을 내어 무승부가 발생할 경우 경기 진행 순서상 뒤인 사람이 이긴 것으로 간주한다. 즉 A와 B가 같은 손동작을 내면 B의 승리, A와 C가 같은 손동작을 내면 C의 승리, B와 C가 같은 손동작을 내면 C의 승리이다.
이전 경기의 승자와 이전 경기에 참여하지 않은 사람이 경기를 진행해 승자를 결정한다.
특정 사람이 미리 합의된 승수를 달성할 때 까지 3을 반복한다.
합의된 승수를 최초로 달성한 사람이 우승한다.
밑의 표는 침, 펄, 풍 세 사람이 인싸 가위바위보를 진행하는 예시이다. 우승을 위해 필요한 승수는 3승이고 침, 펄, 풍 순서로 경기를 진행한다.



인싸 가위바위보 결과 풍이 제일 먼저 3승에 도달했으므로 우승자는 풍이다. 1라운드, 3라운드에서 두 사람이 같은 손동작을 냈을 때 경기 진행 순서상 뒤인 사람이 승리하는 것을 확인할 수 있다.

컴퓨터공학과 새내기 지우는 첫 엠티에서 친구 경희, 민호와 인싸 가위바위보를 할 생각에 굉장히 신나있다. 지우는 경희와 민호의 행동 패턴을 빅데이터로 분석해 인싸 가위바위보를 하는 중 경희와 민호의 차례가 왔을 때 이들이 낼 손동작의 순서를 정확히 알고 있다. 그래서 마음만 먹으면 전승 우승이 가능하지만 경기를 흥미진진하게 이끌기 위해 인싸 가위바위보를 할 때 모든 손동작을 다르게 내고 싶어한다. 지우의 즐거운 대학생활을 위해 인싸 가위바위보의 상성표와 경희, 민호가 낼 손동작의 순서가 주어졌을 때 지우가 모든 손동작을 다르게 내어 우승할 수 있는지 판단하는 프로그램을 만들자. 경기 진행 순서는 지우, 경희, 민호 순으로 정해져있다.

입력
첫째 줄에 인싸 가위바위보의 손동작 수를 나타내는 N(1 ≤ N ≤ 9)과 우승을 위해 필요한 승수 K(1 ≤ K ≤ 6)가 한 칸의 빈칸을 사이에 두고 주어진다.

그 다음 N개의 줄에 대해 상성에 대한 정보 Ai,j가 주어진다. i+1번째 줄에는 N개의 정수 Ai,1, Ai,2, Ai,3, ..., Ai,N이 한 칸의 빈칸을 사이에 두고 주어진다. Ai,j가 2일 경우에는 i번 손동작이 j번 손동작을 이긴다는 의미이고, 1일 경우에는 비긴다는 의미이고, 0일 경우에는 진다는 의미이다. Ai,i = 1이고, i ≠ j일 때 Ai,j ≠ 1임이 보장된다. 또한 Ai,j가 2일 경우에는 Aj,i가 0이고, Ai,j가 0일 경우에는 Aj,i가 2임이 보장된다.

그 다음 줄에는 경희가 앞으로 자신이 참여하는 20경기에서 낼 손동작의 순서가 한 칸의 빈칸을 사이에 두고 주어진다. 손동작의 번호는 1 이상 N 이하이다.

그 다음 줄에는 민호가 앞으로 자신이 참여하는 20경기에서 낼 손동작의 순서가 한 칸의 빈칸을 사이에 두고 주어진다. 마찬가지로 손동작의 번호는 1 이상 N 이하이다.

출력
첫째 줄에 지우, 경희, 민호 순으로 경기를 진행할 때 지우가 모든 손동작을 다르게 내어 우승할 수 있으면 1을, 그렇지 않으면 0을 출력한다.

예제 입력 1
3 2
1 0 2
2 1 0
0 2 1
2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3
예제 출력 1
1
예제 입력 2
3 1
1 2 2
0 1 2
0 0 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
3 2 1 3 2 1 1 2 3 3 2 2 3 2 1 3 3 2 1 1
예제 출력 2
0
예제 입력 3
4 5
1 0 2 0
2 1 0 2
0 2 1 2
2 0 0 1
1 3 2 1 3 2 2 2 2 1 3 1 3 2 1 3 2 2 2 2
2 3 3 3 1 1 3 1 3 2 1 3 2 2 2 2 1 3 1 2
예제 출력 3
0
예제 입력 4
9 6
1 2 2 0 0 2 2 0 2
0 1 0 2 0 2 0 2 2
0 2 1 0 0 0 0 0 2
2 0 2 1 0 0 2 2 2
2 2 2 2 1 0 2 2 2
0 0 2 2 2 1 2 2 0
0 2 2 0 0 0 1 2 0
2 0 2 0 0 0 0 1 0
0 0 0 0 0 2 2 2 1
4 8 6 1 2 3 3 7 6 4 4 9 9 3 6 7 6 4 1 1
3 8 9 7 9 8 6 3 8 7 1 6 2 3 6 5 8 5 1 8
예제 출력 4
1
예제 입력 5
4 2
1 0 0 0
2 1 2 0
2 0 1 0
2 2 2 1
2 2 3 1 1 3 3 2 2 1 4 1 1 3 3 1 1 1 1 4
1 4 4 2 1 3 1 2 3 4 2 2 3 4 4 2 4 3 1 3
예제 출력 5
1
예제 입력 6
9 6
1 0 2 2 2 2 2 2 0
2 1 0 2 0 2 0 2 2
0 2 1 2 2 0 2 2 0
0 0 0 1 2 2 2 2 0
0 2 0 0 1 2 2 0 0
0 0 2 0 0 1 0 0 2
0 2 0 0 0 2 1 0 0
0 0 0 0 2 2 2 1 2
2 0 2 2 2 0 2 0 1
6 5 8 9 6 1 8 2 1 7 9 5 1 3 4 9 2 3 1 1
2 2 9 9 4 5 9 7 2 7 7 3 1 7 6 6 5 4 2 6
예제 출력 6
1
예제 입력 7
5 2
1 0 0 0 2
2 1 0 0 2
2 2 1 2 0
2 2 0 1 0
0 0 2 2 1
3 5 1 5 2 2 4 5 4 4 1 5 4 3 2 4 3 4 3 4
3 1 3 4 1 1 1 1 3 1 2 1 1 1 3 3 4 1 1 3
예제 출력 7
0
예제 입력 8
9 5
1 2 2 0 0 2 0 2 2
0 1 0 0 2 2 2 0 0
0 2 1 0 0 0 2 0 0
2 2 2 1 2 2 2 2 2
2 0 2 0 1 2 0 0 2
0 0 2 0 0 1 0 0 0
2 0 0 0 2 2 1 0 0
0 2 2 0 2 2 2 1 0
0 2 2 0 0 2 2 2 1
4 7 4 4 1 8 4 3 5 4 4 9 7 1 9 9 6 9 8 8
1 3 5 5 7 6 1 4 8 8 2 9 9 7 9 1 8 3 9 7
예제 출력 8
0
노트
두 사람이 같은 손동작을 내어 무승부가 발생할 경우 경기 진행 순서상 뒤인 사람이 이긴 것으로 간주함에 다시 한 번 유의한다. 구체적으로, 경기 진행 순서는 지우, 경희, 민호 순으로 고정되어있기 때문에 이전 라운드의 결과와 무관하게 지우와 경희가 같은 손동작을 냈으면 경희의 승리이고, 지우와 민호가 같은 손동작을 냈으면 민호의 승리이고, 경희와 민호가 같은 손동작을 냈으면 민호의 승리이다.

비둘기집의 원리에 의해 3(K-1)+1번의 경기를 치르면 누군가는 K번 이상 승리해 우승자가 결정되기 때문에 경희, 민호 각 사람에 대해 앞으로 20경기에서 낼 손동작의 순서만 알고 있으면 지우가 모든 손동작을 다르게 내어 우승할 수 있는지를 판단할 수 있다.

만약 지우가 가능한 모든 손동작을 한 번씩 다 낸 후에도 아직 우승자가 결정되지 않아 지우가 경기를 더 하게 된다면, 지우는 이전에 냈던 손동작을 다시 내야하므로 답은 0이 된다.

출처
문제를 만든 사람: BaaaaaaaaaaarkingDog
문제를 검수한 사람: portableangel
알고리즘 분류
구현
브루트포스 알고리즘
백트래킹
 */
public class BOJ16986 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,K,ans;
        int[] dashboard,visited;
        int[][] participant,compatibility_chart;

        private void solve() throws IOException {
            init_setting();

            play(0,0,1);

            System.out.println(ans);
        }

        private void play(int set, int winner, int next_matcher) {
             if(set == 3 * (K - 1) + 1) {
                 if(dashboard[0] == K) ans = 1;
                 System.out.println(dashboard[0] + " " + dashboard[1] + " " + dashboard[2]);
                 return;
             }

             int res;
             int nm = (next_matcher + 1) % 3 == winner ? (winner + 1) % 3 : (next_matcher + 1) % 3;

             if(winner == 0 || next_matcher == 0) {
                 for(int i = 0; i < N; i++) {
                     if(visited[i] > 0) continue;

                     res = (winner == 0) ? compatibility_chart[i][participant[next_matcher][set] - 1] :
                             compatibility_chart[participant[winner][set] - 1][i];
                     visited[i] = 1;

                     if(res == 2) {
                         dashboard[winner] += 1;
                         play(set + 1, winner, nm);
                         dashboard[winner] -= 1;
                     } else { // 1 or 0
                         dashboard[next_matcher] += 1;
                         play(set + 1, next_matcher, nm);
                         dashboard[next_matcher] -= 1;
                     }
                     visited[i] = 0;
                 }
             } else {
                 res = compatibility_chart[winner][next_matcher];

                 if(res == 2) {
                     dashboard[winner] += 1;
                     play(set + 1, winner, nm);
                     dashboard[winner] -= 1;
                 } else {
                     dashboard[next_matcher] += 1;
                     play(set + 1, next_matcher, nm);
                     dashboard[next_matcher] -= 1;
                 }
             }
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            K = Integer.parseInt(input[1]);

            compatibility_chart = new int[N][N];

            for(int i = 0; i < N; i++) {
                compatibility_chart[i] = Arrays.stream(br.readLine().split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }

            dashboard = new int[3];
            visited = new int[N];

            participant = new int[3][20];

            for(int i = 1; i < 3; i++) {
                participant[i] = Arrays.stream(br.readLine().split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }

            ans = 0;
        }
    }
}
