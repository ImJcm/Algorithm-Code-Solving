package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
대회 or 인턴 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	30896	14005	11838	45.461%
문제
백준대학교에서는 대회에 나갈 때 2명의 여학생과 1명의 남학생이 팀을 결성해서 나가는 것이 원칙이다. (왜인지는 총장님께 여쭈어보는 것이 좋겠다.)

백준대학교는 뛰어난 인재들이 많아 올해에도 N명의 여학생과 M명의 남학생이 팀원을 찾고 있다. 대회에 참여하려는 학생들 중 K명은 반드시 인턴쉽 프로그램에 참여해야 한다. 인턴쉽에 참여하는 학생은 대회에 참여하지 못한다.

백준대학교에서는 뛰어난 인재들이 많기 때문에, 많은 팀을 만드는 것이 최선이다.

여러분은 여학생의 수 N, 남학생의 수 M, 인턴쉽에 참여해야하는 인원 K가 주어질 때 만들 수 있는 최대의 팀 수를 구하면 된다.

입력
첫째 줄에 N, M, K가 순서대로 주어진다. (0 ≤ M ≤ 100, 0 ≤ N ≤ 100, 0 ≤ K ≤ M+N),

출력
만들 수 있는 팀의 최대 개수을 출력하면 된다.

예제 입력 1
6 3 2
예제 출력 1
2
예제 입력 2
2 1 1
예제 출력 2
0
예제 입력 3
6 10 3
예제 출력 3
3
출처
Contest > Croatian Open Competition in Informatics > COCI 2010/2011 > Contest #1 1번

문제를 번역한 사람: pl0892029
문제의 오타를 찾은 사람: Rche
알고리즘 분류
수학
구현
사칙연산
 */
/*
알고리즘 핵심
그리디 알고리즘 + 수학
1. 여학생 2명과 남학생 1명이 팀을 이루므로 n/2, m 중 최소값이 이룰 수 있는 팀의 수를 구한다.
2. 팀을 구성한 후, 남은 여학생과 남학생의 수를 갱신한 후, 남은 학생의 수를 구한다.
3. 남은 학생의 수로 인턴쉽 프로그램에 참여한 학생을 뽑기 위해 남은 학생의 수보다 인턴쉽에 참여할 학생이 적은 경우
현재 팀을 줄이고 남은 학생의 수를 3명씩 증가시켜 K보다 많아질 때까지 팀을 줄인다.
4. 위 상황을 만족할 때 팀의 수를 출력한다.
 */
public class BOJ2875 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,K,ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        int T = Math.min(N / 2, M);

        N -= T * 2;
        M -= T;

        int S = N + M;

        while(T > 0 && K > S) {
            T--;
            S += 3;
        }

        ans = K > S ? -1 : T;

        System.out.println(ans);
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        K = Integer.parseInt(input[2]);

        ans = 0;
    }
}
