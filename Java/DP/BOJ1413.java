package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
박스 안의 열쇠

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	1429	645	515	46.606%
문제
1부터 N까지 번호가 매겨진 박스와 1부터 N까지 번호가 매겨진 열쇠가 있다. i번째 키는 i번째 박스를 열 수 있다.

다못이는 각각의 박스에 정확하게 하나의 열쇠를 무작위로 넣는다. 각각의 열쇠가 박스에 들어갈 확률은 모두 같다고 가정한다. 그러고 나서 박스를 모두 잠근다. 다못이에게는 M개의 폭탄이 있다. 폭탄은 잠겨져 있는 박스를 파괴하는 역할을 한다. 이때 박스 안에 있는 열쇠는 부서지지 않는다. 다못이는 모든 열쇠를 얻고 싶다. 그래서 다음 전략을 사용하기로 했다.

우선 잠겨져 있는 박스 하나를 선택해서 폭탄으로 파괴하고 열쇠를 얻는다. 각각의 잠겨져 있는 박스가 선택될 확률은 모두 같다. 그 열쇠로 열 수 있는 박스가 있으면 열고, 그 박스 속의 열쇠로 열 수 있는 박스가 있으면 또 열고, 이를 열 박스가 더 이상 없을 때까지 반복한다. 그러고 나서 폭탄이 남았으면 그 폭탄을 이용해서 이 전략을 반복한다.

다못이가 모든 열쇠를 얻을 확률을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 박스와 열쇠의 개수 N과 폭탄의 개수 M이 공백을 사이에 두고 주어진다. N은 20보다 작거나 같은 자연수이고, M은 N보다 작거나 같은 자연수이다.

출력
다못이가 모든 열쇠를 얻을 확률을 A/B 형태로 출력한다. A와 B는 최대공약수가 1인 자연수이다.

예제 입력 1
2 1
예제 출력 1
1/2
예제 입력 2
2 2
예제 출력 2
1/1
예제 입력 3
3 1
예제 출력 3
1/3
예제 입력 4
3 2
예제 출력 4
5/6
예제 입력 5
4 2
예제 출력 5
17/24
힌트
예제 1의 경우 1번 박스에 열쇠2가 들어있으면, 모든 키를 얻을 수 있다.

출처
문제의 오타를 찾은 사람: adfsfsf, YunGoon
문제를 번역한 사람: baekjoon
문제를 다시 작성한 사람: jh05013
알고리즘 분류
수학
다이나믹 프로그래밍
조합론
 */
public class BOJ1413 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;


    public static void main(String[] args) throws IOException {
        init_setting();
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);


    }
}
