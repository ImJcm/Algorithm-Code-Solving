package math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/*
GCD 합 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	39739	16009	13166	43.009%
문제
양의 정수 n개가 주어졌을 때, 가능한 모든 쌍의 GCD의 합을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 테스트 케이스의 개수 t (1 ≤ t ≤ 100)이 주어진다. 각 테스트 케이스는 한 줄로 이루어져 있다. 각 테스트 케이스는 수의 개수 n (1 < n ≤ 100)가 주어지고, 다음에는 n개의 수가 주어진다. 입력으로 주어지는 수는 1,000,000을 넘지 않는다.

출력
각 테스트 케이스마다 가능한 모든 쌍의 GCD의 합을 출력한다.

예제 입력 1
3
4 10 20 30 40
3 7 5 12
3 125 15 25
예제 출력 1
70
3
35
출처
ICPC > Regionals > Asia Pacific > Thailand > 2013 ACM-ICPC Asia Phuket Regional Programming Contest 연습 세션 PE번

어색한 표현을 찾은 사람: alkyne
문제를 번역한 사람: baekjoon
데이터를 추가한 사람: mythofys, topology
잘못된 데이터를 찾은 사람: tncks0121
알고리즘 분류
수학
정수론
유클리드 호제법
 */
/*
알고리즘 핵심
수학 (정수론 + 유클리드 호제법)
1. 입력으로 주어진 숫자들을 모두 쌍을 이루어 유클리드 호제법을 적용하여 gcd를 ans에 누적하여 더한다.
2. ans의 값은 int를 벗어나는 경우가 존재할 수 있기때문에 int -> long을 적용한다.
 */
public class BOJ9613 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T;
    static ArrayList<Integer>[] Ns;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static int Euclidean(int a, int b) {
        if(b == 0) {
            return a;
        }
        return Euclidean(b, a % b);
    }

    private static void solve() {
        for(ArrayList<Integer> ns : Ns) {
            long ans = 0;

            if(ns.size() == 1) ans = ns.get(0);

            for(int l = 0; l < ns.size() - 1; l++) {
                for(int r = l + 1; r < ns.size(); r++) {
                    ans += Euclidean(ns.get(l),ns.get(r));
                }
            }

            System.out.println(ans);
        }
    }

    private static void init_setting() throws IOException {
        T = Integer.parseInt(br.readLine());

        Ns = new ArrayList[T];

        for(int i = 0; i < T; i++) {
            String[] input = br.readLine().split(" ");
            Ns[i] = new ArrayList<>();

            for(int j = 1; j < input.length; j++) {
                Ns[i].add(Integer.parseInt(input[j]));
            }

            // [Compile Error] JDK15 - unsafe operation -> .toList() 16 버전이상부터는 정상 작동
            /*Ns[i].addAll(Arrays.stream(br.readLine().split(" "))
                    .map(Integer::valueOf)
                    .toList());*/
        }
    }
}
