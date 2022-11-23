import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
약수 지우기 게임 1

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	669	403	355	64.899%
문제
A와 B가 약수 지우기 게임을 한다. 약수 지우기 게임은 두 사람이 즐기는 게임이다.

칠판에 1부터 N까지의 자연수가 적혀 있다. 각 사람은 자신의 턴에 칠판에 적힌 자연수 하나를 지우고, 그 자연수의 약수 중 칠판에 남아 있는 수들을 모두 지운다. 예를 들어, 칠판에 2,3,4,5,6이 적혀 있을 때, 6을 지우면, 그 약수인 2와 3 역시 지워야 한다. 자신의 턴에 숫자를 지우지 않을 수는 없다. 마지막 숫자를 지우는 사람이 지게 된다.

A와 B가 최적의 방법으로 게임을 할 때, 이기는 사람을 출력한다. 게임은 A가 먼저 시작한다.

입력
첫째 줄에 N이 주어진다. N은 1,000,000보다 작거나 같은 자연수이다.

출력
첫째 줄에 A가 이기는 경우 A, B가 이기는 경우 B를 출력한다.

예제 입력 1
4
예제 출력 1
A
 */
/*
    알고리즘 : 수학, 애드 혹, 게임 이론
    N = 1) B
    N = 2) A
    N = 3) A
    N = 4) A
    N = 5) A
    N = 6) A
    N = ... ) A?

    n-1개의 수로 게임 시, 마지막 숫자를 B에게 넘겨주면 A의 승리
    - A가 처음 뽑은 자연수의 약수에 1을 포함하여 지우게 되면 최종적으로 B는 마지막 수를 지워야하고, A의 승리

    n-1개의 수로 게임 시, 마지막 순서가 A에게 갈 때
    - 선공으로 시작할 때 1만 지우고 시작하면, 마지막 순서는 B이므로 최종적으로 A의 승리

    따라서, N=1을 제외할 때면, 모두 A의 승리
 */
public class BOJ12107 {
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        System.out.println((N == 1) ? "B" : "A");
    }
}
