package BackJoon;

/*
부분합 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (하단 참고)	128 MB	92725	25554	18058	25.859%
문제
10,000 이하의 자연수로 이루어진 길이 N짜리 수열이 주어진다. 이 수열에서 연속된 수들의 부분합 중에 그 합이 S 이상이 되는 것 중, 가장 짧은 것의 길이를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N (10 ≤ N < 100,000)과 S (0 < S ≤ 100,000,000)가 주어진다. 둘째 줄에는 수열이 주어진다. 수열의 각 원소는 공백으로 구분되어져 있으며, 10,000이하의 자연수이다.

출력
첫째 줄에 구하고자 하는 최소의 길이를 출력한다. 만일 그러한 합을 만드는 것이 불가능하다면 0을 출력하면 된다.

예제 입력 1
10 15
5 1 3 5 10 7 4 9 2 8
예제 출력 1
2
출처
ICPC > Regionals > Europe > Southeastern European Regional Contest > SEERC 2006 B번

문제를 번역한 사람: author5
시간 제한을 수정한 사람: cheetose
잘못된 조건을 찾은 사람: isku
데이터를 추가한 사람: leeingyun96, ppqhdl2, stresstalmo, wookje, YunGoon
빠진 조건을 찾은 사람: rlarlghks970113
문제의 오타를 찾은 사람: ZZangZZang
알고리즘 분류
누적 합
두 포인터
시간 제한
Java 8: 1 초
Java 8 (OpenJDK): 1 초
Java 11: 1 초
Kotlin (JVM): 1 초
Java 15: 1 초
 */
/*

 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ1806 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,S,minLen = Integer.MAX_VALUE;
    static int[] arr;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        int sum = 0;
        for(int start=0,end=0;end <= N && start <= end;) {
            if(sum < S) {
                if(end == N) break;
                sum += arr[end++];
            } else {
                minLen = minLen > (end - start) ? (end - start) : minLen;
                sum -= arr[start++];
            }
        }
        minLen = minLen == Integer.MAX_VALUE ? 0 : minLen;
        System.out.println(minLen);
    }

    static void init_setting() throws IOException{
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        S = Integer.parseInt(tmp[1]);

        arr = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
