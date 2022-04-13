package BOJ;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/*
스타트와 링크

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	57520	29023	16968	47.142%
문제
오늘은 스타트링크에 다니는 사람들이 모여서 축구를 해보려고 한다. 축구는 평일 오후에 하고 의무 참석도 아니다. 축구를 하기 위해 모인 사람은 총 N명이고 신기하게도 N은 짝수이다. 이제 N/2명으로 이루어진 스타트 팀과 링크 팀으로 사람들을 나눠야 한다.

BOJ를 운영하는 회사 답게 사람에게 번호를 1부터 N까지로 배정했고, 아래와 같은 능력치를 조사했다. 능력치 Sij는 i번 사람과 j번 사람이 같은 팀에 속했을 때, 팀에 더해지는 능력치이다. 팀의 능력치는 팀에 속한 모든 쌍의 능력치 Sij의 합이다. Sij는 Sji와 다를 수도 있으며, i번 사람과 j번 사람이 같은 팀에 속했을 때, 팀에 더해지는 능력치는 Sij와 Sji이다.

N=4이고, S가 아래와 같은 경우를 살펴보자.

i\j	1	2	3	4
1	 	1	2	3
2	4	 	5	6
3	7	1	 	2
4	3	4	5
예를 들어, 1, 2번이 스타트 팀, 3, 4번이 링크 팀에 속한 경우에 두 팀의 능력치는 아래와 같다.

스타트 팀: S12 + S21 = 1 + 4 = 5
링크 팀: S34 + S43 = 2 + 5 = 7
1, 3번이 스타트 팀, 2, 4번이 링크 팀에 속하면, 두 팀의 능력치는 아래와 같다.

스타트 팀: S13 + S31 = 2 + 7 = 9
링크 팀: S24 + S42 = 6 + 4 = 10
축구를 재미있게 하기 위해서 스타트 팀의 능력치와 링크 팀의 능력치의 차이를 최소로 하려고 한다. 위의 예제와 같은 경우에는 1, 4번이 스타트 팀, 2, 3번 팀이 링크 팀에 속하면 스타트 팀의 능력치는 6, 링크 팀의 능력치는 6이 되어서 차이가 0이 되고 이 값이 최소이다.

입력
첫째 줄에 N(4 ≤ N ≤ 20, N은 짝수)이 주어진다. 둘째 줄부터 N개의 줄에 S가 주어진다. 각 줄은 N개의 수로 이루어져 있고, i번 줄의 j번째 수는 Sij 이다. Sii는 항상 0이고, 나머지 Sij는 1보다 크거나 같고, 100보다 작거나 같은 정수이다.

출력
첫째 줄에 스타트 팀과 링크 팀의 능력치의 차이의 최솟값을 출력한다.

예제 입력 1
4
0 1 2 3
4 0 5 6
7 1 0 2
3 4 5 0
예제 출력 1
0
예제 입력 2
6
0 1 2 3 4 5
1 0 2 3 4 5
1 2 0 3 4 5
1 2 3 0 4 5
1 2 3 4 0 5
1 2 3 4 5 0
예제 출력 2
2
예제 입력 3
8
0 5 4 5 4 5 4 5
4 0 5 1 2 3 4 5
9 8 0 1 2 3 1 2
9 9 9 0 9 9 9 9
1 1 1 1 0 1 1 1
8 7 6 5 4 0 3 2
9 1 9 1 9 1 0 9
6 5 4 3 2 1 9 0
 */
/*
 * (팀조합 알고리즘) * (팀별 능력치 계산 알고리즘)
 * 팀조합 알고리즘은 백트랙킹으로 구성하고, 팀별능력치 계산은 단순 반복문으로 구성시키려고 한다.
 * 처음 제출에서는 모든 경우의 수를 고려하여 계산했기 때문에 "시간초과"가 발생하였다.
 * 팀을 구성하는 과정에서 순열이 아닌 조합을 통해 중복되는 팀 구성을 제외하여 해결
 * balanceCheck()와 power를 나누지 않고 balanceCheck()하나로 구성하는 것이 가독성이 더 좋은것 같다.
 * 그런데 결과로 보면, 메모리는 적게쓰지만, 시간이 조금 증가했고, power를 쓴 코드의 경우, 메모리는 크지만
 * 속도가 조금더 빠른 것을 볼 수 있다.
 */
public class BOJ14889 {
    static int N;
    static int[][] S;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static boolean[] team;
    static int min_result = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        S = new int[N][N];
        team = new boolean[N];

        for(int i=0;i<N;i++) {
            String[] temp = br.readLine().split(" ");
            for(int j=0;j<N;j++) {
                S[i][j] = Integer.parseInt(temp[j]);
            }
        }

        balanceCheck(0,0);

        System.out.println(min_result);
    }
    static void balanceCheck(int depth,int start) {
        if(depth == N/2) {
            int t1_s = 0;
            int t2_s = 0;
            for(int i=0;i<N;i++) {
                for(int j=i+1;j<N;j++) {
                    if(team[i] && team[j]) {
                        t1_s += S[i][j] + S[j][i];
                    } else if(!team[i] && !team[j]) {
                        t2_s += S[i][j] + S[j][i];
                    }
                }
            }
            min_result = Math.min(min_result,Math.abs(t1_s-t2_s));
            return;
        }

        for(int i=start;i<N;i++) {
            if(team[i]) {
                continue;
            }
            team[i] = true;
            balanceCheck(depth+1,i);
            team[i] = false;
        }
    }
}
/*
public class BOJ14889 {
    static int N;
    static int[][] S;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static boolean[] team;
    static int min_result = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        S = new int[N][N];
        team = new boolean[N];

        for(int i=0;i<N;i++) {
            String[] temp = br.readLine().split(" ");
            for(int j=0;j<N;j++) {
                S[i][j] = Integer.parseInt(temp[j]);
            }
        }

        balanceCheck(0,0);

        System.out.println(min_result);
    }
    static void balanceCheck(int depth,int start) {
        if(depth == N/2) {
            power(team);
            return;
        }

        for(int i=start;i<N;i++) {
            if(team[i]) {
                continue;
            }

            team[i] = true;
            balanceCheck(depth+1,i);
            team[i] = false;
        }
    }

    static void power(boolean[] a) {
        int s1=0,s2=0;
        int[] team1,team2;
        team1 = new int[N/2];
        team2 = new int[N/2];

        for(int i=0;i<N;i++) {
            if(a[i]) {
                team1[s1] = i;
                s1++;
            } else {
                team2[s2] = i;
                s2++;
            }
        }

        s1=0;
        //team1의 S
        for(int i=0;i<N/2;i++) {
            for(int j=0;j<N/2;j++) {
                if(i==j) {
                    continue;
                }
                s1 += S[team1[i]][team1[j]];
            }
        }
        s2=0;
        //team2의 S
        for(int i=0;i<N/2;i++) {
            for(int j=0;j<N/2;j++) {
                if(i==j) {
                    continue;
                }
                s2 += S[team2[i]][team2[j]];
            }
        }

        min_result = Math.min(min_result,Math.abs(s1-s2));
    }

}
*/
