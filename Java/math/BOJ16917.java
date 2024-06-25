package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
양념 반 후라이드 반

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	4704	2667	2226	58.410%
문제
현진 치킨에서 판매하는 치킨은 양념 치킨, 후라이드 치킨, 반반 치킨으로 총 세 종류이다. 반반 치킨은 절반은 양념 치킨, 절반은 후라이드 치킨으로 이루어져있다. 양념 치킨 한 마리의 가격은 A원, 후라이드 치킨 한 마리의 가격은 B원, 반반 치킨 한 마리의 가격은 C원이다.

상도는 오늘 파티를 위해 양념 치킨 최소 X마리, 후라이드 치킨 최소 Y마리를 구매하려고 한다. 반반 치킨을 두 마리 구입해 양념 치킨 하나와 후라이드 치킨 하나를 만드는 방법도 가능하다. 상도가 치킨을 구매하는 금액의 최솟값을 구해보자.

입력
첫째 줄에 다섯 정수 A, B, C, X, Y가 주어진다.

출력
양념 치킨 최소 X마리, 후라이드 치킨 최소 Y마리를 구매하는 비용의 최솟값을 출력한다.

제한
1 ≤ A, B, C ≤ 5,000
1 ≤ X, Y ≤ 100,000
예제 입력 1
1500 2000 1600 3 2
예제 출력 1
7900
반반 치킨 4마리를 구매해서, 양념 치킨 2마리와 후라이드 치킨 2마리를 만들고, 양념 치킨 1마리를 구매하는 것이 최소이다.

예제 입력 2
1500 2000 1900 3 2
예제 출력 2
8500
예제 입력 3
1500 2000 500 90000 100000
예제 출력 3
100000000
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
수학
구현
사칙연산
 */
/*
71, 78번 줄에서 양념 치킨과 후라이드 치킨이 수량이 남은 경우 한 마리의 가격과 반반 치킨 2개의 가격을 비교하여 더 낮은 쪽으로
수량을 올려야 하는 과정에서 if(X > (2*C)) 와 같이 수량과 가격을 비교하는 실수 때문에 실패가 있었다.
if(X > (2*C)) -> if(A > (2*C))
 */
public class BOJ16917 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int A,B,C,X,Y,price;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        int half_chicken = 0;
        int pride_chicken = 0;
        int seasoning_chicken = 0;

        if(A+B >= 2*C) {
            half_chicken = Math.min(X,Y);
            seasoning_chicken = X - half_chicken >= 0 ? X - half_chicken : 0;
            pride_chicken = Y - half_chicken >= 0 ? Y - half_chicken : 0;

            if(seasoning_chicken > 0) {
                if(A > (2*C)) {
                    half_chicken += seasoning_chicken;
                    seasoning_chicken = 0;
                }
            }

            if(pride_chicken > 0) {
                if(B > (2*C)) {
                    half_chicken += pride_chicken;
                    pride_chicken = 0;
                }
            }
        } else {
            seasoning_chicken = X;
            pride_chicken = Y;
        }

        price = A * seasoning_chicken + B * pride_chicken + (2*C) * half_chicken;

        System.out.println(price);
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        A = Integer.parseInt(input[0]);
        B = Integer.parseInt(input[1]);
        C = Integer.parseInt(input[2]);
        X = Integer.parseInt(input[3]);
        Y = Integer.parseInt(input[4]);

        price = 0;
    }
}
