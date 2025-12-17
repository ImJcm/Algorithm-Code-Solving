import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
수들의 합 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초	128 MB	54075	26081	17831	48.343%
문제
N개의 수로 된 수열 A[1], A[2], …, A[N] 이 있다. 이 수열의 i번째 수부터 j번째 수까지의 합 A[i] + A[i+1] + … + A[j-1] + A[j]가 M이 되는 경우의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N(1 ≤ N ≤ 10,000), M(1 ≤ M ≤ 300,000,000)이 주어진다. 다음 줄에는 A[1], A[2], …, A[N]이 공백으로 분리되어 주어진다. 각각의 A[x]는 30,000을 넘지 않는 자연수이다.

출력
첫째 줄에 경우의 수를 출력한다.

예제 입력 1
4 2
1 1 1 1
예제 출력 1
3
예제 입력 2
10 5
1 2 3 4 2 5 3 1 1 2
예제 출력 2
3
출처
데이터를 추가한 사람: isku, vvipconcert
알고리즘 분류
브루트포스 알고리즘
누적 합
두 포인터
 */
/*
내가 알고있는 Two Point 알고리즘은 정렬된 데이터에서만 사용가능한 줄 알고있었지만 잘못된 개념이였다. 정렬되지 않는 데이터 상에서도
부분합을 구하는 문제에서도 투 포인트 알고리즘을 사용할 수 있다는 것을 알게 되었다.

첫번째로 구현한 코드는 브루트포스 방식을 이용한 코드이다.

두번째로 구현한 코드는 투 포인트 알고리즘을 활용한 코드이다.

투 포인트(Two Point) 알고리즘 개념
- 리스트에 순차적으로 접근해야 할 때 "두 개의 점의 위치를 기록하면서 처리"하는 알고리즘
- 정렬되어 있는 두 리스트의 합집합에도 사용될 수 있다.
- 병합정렬(merge sort)의 couquer(정복) 영역의 기초가 되기도 한다.

 */
public class BOJ2003 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,cnt = 0;
    static int[] numbers;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();

        solve_twoPoint();
    }

    //브루트포스 알고리즘 사용 코드
    static void solve() {
        for (int i = 0; i < N; i++) {
            int sum = 0;
            for (int j = i; j < N; j++) {
                if (numbers[j] + sum > M) {
                    break;
                } else if (numbers[j] + sum < M) {
                    sum += numbers[j];
                } else {
                    cnt++;
                    break;
                }
            }
        }
        System.out.println(cnt);
    }

    //투 포인트 알고리즘을 활용한 코드
    static void solve_twoPoint() {
        int sum = 0;
        int cnt2 = 0;
        for (int i = 0,j = 0; i < N && j <= N && i <= j;) {
            if(sum >= M) {
                sum -= numbers[i];
                i++;
            } else {
                if(j == N) break;
                sum += numbers[j];
                j++;
            }
            if(sum == M) {
                cnt2++;
            }
        }
        System.out.println("solve_twoPoint : " + cnt2);
    }

    static void init_setting() throws IOException {
        String[] temp = br.readLine().split(" ");
        N = Integer.parseInt(temp[0]);
        M = Integer.parseInt(temp[1]);

        numbers = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}