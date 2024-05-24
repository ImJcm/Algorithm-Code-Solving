package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
LCS 2 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.1 초 (하단 참고)	256 MB	42665	15727	12158	37.953%
문제
LCS(Longest Common Subsequence, 최장 공통 부분 수열)문제는 두 수열이 주어졌을 때, 모두의 부분 수열이 되는 수열 중 가장 긴 것을 찾는 문제이다.

예를 들어, ACAYKP와 CAPCAK의 LCS는 ACAK가 된다.

입력
첫째 줄과 둘째 줄에 두 문자열이 주어진다. 문자열은 알파벳 대문자로만 이루어져 있으며, 최대 1000글자로 이루어져 있다.

출력
첫째 줄에 입력으로 주어진 두 문자열의 LCS의 길이를, 둘째 줄에 LCS를 출력한다.

LCS가 여러 가지인 경우에는 아무거나 출력하고, LCS의 길이가 0인 경우에는 둘째 줄을 출력하지 않는다.

예제 입력 1
ACAYKP
CAPCAK
예제 출력 1
4
ACAK
출처
데이터를 추가한 사람: eric00513
알고리즘 분류
다이나믹 프로그래밍
시간 제한
Java 8: 0.4 초
Python 3: 2 초
PyPy3: 2 초
Java 8 (OpenJDK): 0.4 초
Java 11: 0.4 초
Kotlin (JVM): 0.4 초
Java 15: 0.4 초
 */
/*
BOJ9251 - LCS를 응용하는 것은 알고있었으나, 처음 접근 방법으로 최장 공통 부분 수열의 길이를 메모리제이션의 값으로 사용한 것이 아닌
문자열 저장하여 추가하는 형태로 접근하였다. 하지만, 해당 방법은 메모리 초과 및 시간 초과를 유발하기 때문에 잘못된 방식이였다.

그래서, lcs의 메모리제이션의 값을 만들어 가는 과정에서 현재 위치에서 다음 위치로의 경로를 기억하고 있기 때문에 최장 공통 부분 수열을 구성하는
문자열을 알 수 있다고 생각하였다.

그래서, 각 메모리제이션의 값 위치에서 해당 위치에 도달하기 이전의 메모리제이션의 값에 해당하는 row, col 값을 저장하는 배열을 만들고
역추적하기 위해 사용하였다.
 */
public class BOJ9252 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb;
    static String str1,str2;
    static String[][] lcs_string;
    static int[][] lcs, lcs_1,lcs_2;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        /*String result = Top_down(str1.length()-1, str2.length()-1);

        System.out.println(result.length());
        System.out.println(result);


        Bottom_up();

        System.out.println(lcs_string[str1.length()][str2.length()].length());
        System.out.println(lcs_string[str1.length()][str2.length()]);*/

        pos_bottom_up();
        reverse_lcs_route();

        System.out.println(lcs[str1.length()][str2.length()]);
        System.out.println(sb.toString());
    }

    // 시간 초과 - 재귀
    static String Top_down(int idx1, int idx2) {
        if(idx1 < 0 || idx2 < 0) {
            return "";
        }

        if(lcs_string[idx1][idx2].isBlank()) {
            if(str1.charAt(idx1) == str2.charAt(idx2)) {
                lcs_string[idx1][idx2] += Top_down(idx1-1,idx2-1) + str1.charAt(idx1);
            } else {
                String s1 = Top_down(idx1-1,idx2);
                String s2 = Top_down(idx1,idx2-1);

                lcs_string[idx1][idx2] = s1.length() > s2.length() ? s1 : s2;
            }
        }
        return lcs_string[idx1][idx2];
    }

    // 메모리 초과 - String memorization에 문자열을 추가하는 방식
    static void Bottom_up() {
        for(int i=1;i<=str1.length();i++) {
            for(int j=1;j<=str2.length();j++) {
                if(str1.charAt(i-1) == str2.charAt(j-1)) {
                    lcs_string[i][j] = lcs_string[i-1][j-1] + str1.charAt(i-1);
                } else {
                    lcs_string[i][j] = lcs_string[i-1][j].length() > lcs_string[i][j-1].length() ? lcs_string[i-1][j] : lcs_string[i][j-1];
                }
            }
        }
    }

    // LCS의 최장 공통 부분 수열의 길이를 저장하는 lcs을 만드는 메서드
    static void pos_bottom_up() {
        for(int i=1;i<=str1.length();i++) {
            for(int j=1;j<=str2.length();j++) {
                if(str1.charAt(i-1) == str2.charAt(j-1)) {
                    lcs[i][j] = lcs[i-1][j-1] + 1;
                    lcs_1[i][j] = i-1;
                    lcs_2[i][j] = j-1;
                } else {
                    if(lcs[i-1][j] >= lcs[i][j-1]) {
                        lcs[i][j] = lcs[i-1][j];
                        lcs_1[i][j] = i-1;
                        lcs_2[i][j] = j;
                    } else {
                        lcs[i][j] = lcs[i][j-1];
                        lcs_1[i][j] = i;
                        lcs_2[i][j] = j-1;
                    }
                }
            }
        }
    }

    /*
        lcs_1, lcs_2를 각 문자열에서 최장 공통 부분 수열의 값을 만들기 위해 이어진 경로를 추적하기 위해 역추적하는 메서드
        원리:
            lcs를 구하는 과정에서 어떤 lcs 최장 길이에서 파생되었는지 이전 위치의 index값을 row, col을 저장하기 위해 2차원 배열
            lcs_1, lcs_2를 만들어 각 배열에 row, col을 저장한다.

        역추적 원리:
            1. 최장 길이인 현재 위치에서 이전에 거쳐온 위치의 row,col을 저장한 값으로 현재의 lcs값과 이전 lcs값을 비교하여 같은 경우 공통되는 문자열이
               아니고, 현재 lcs 값보다 이전 lcs 값이 작다면 공통 부분 수열로 사용된 문자이므로 해당 문자를 추가한다.

            2. 이전 위치의 str1의 idx = lcs_1에서 cur_str1_idx, cur_str2_idx에 해당하는 값을 저장하고, 이전 위치의 str2의 idx = lcs_2에서
               cur_str1_idx, cur_str2_idx에 해당하는 값으로 업데이트한다.

            3. 1,2번을 반복하면서 cur_lcs의 값이 0이면 종료한다.
            lcs_1,lcs_2의 이전 최장 길이를 보장하는 위치로 이동하고, 해당 위치에서의 lcs 값을

                ex)
                input example : ACAYKP / CAPCAK
                최장 길이 = 4인 루트를 추적하기 위해, lcs_1과 lcs_2에서 [str1.length()][str2.length()]에 해당하는
                역추적 경로의 위치를 row,col 값으로 추적하면서 lcs 값이 줄어드는 시점에
                lcs                     lcs_1                   lcs_2
                [0, 0, 0, 0, 0, 0, 0]   [0, 0, 0, 0, 0, 0, 0]   [0, 0, 0, 0, 0, 0, 0]
                [0, 0, 1, 1, 1, 1, 1]   [0, 0, 0, 1, 1, 0, 1]   [0, 1, 1, 2, 3, 4, 5]
                [0, 1, 1, 1, 2, 2, 2]   [0, 1, 1, 1, 1, 2, 2]   [0, 0, 2, 3, 3, 4, 5]
                [0, 1, 2, 2, 2, 3, 3]   [0, 2, 2, 3, 2, 2, 3]   [0, 1, 1, 2, 4, 4, 5]
                [0, 1, 2, 2, 2, 3, 3]   [0, 3, 3, 3, 3, 3, 3]   [0, 1, 2, 3, 4, 5, 6]
                [0, 1, 2, 2, 2, 3, 4]   [0, 4, 4, 4, 4, 4, 4]   [0, 1, 2, 3, 4, 5, 5]
                [0, 1, 2, 3, 3, 3, 4]   [0, 5, 5, 5, 6, 5, 5]   [0, 1, 2, 2, 3, 5, 6]
     */
    static void reverse_lcs_route() {
        int cur_str1_idx = str1.length();
        int cur_str2_idx = str2.length();
        int next_str1_idx;
        int next_str2_idx;

        while(true) {
            next_str1_idx = lcs_1[cur_str1_idx][cur_str2_idx];
            next_str2_idx = lcs_2[cur_str1_idx][cur_str2_idx];

            int cur_lcs_value = lcs[cur_str1_idx][cur_str2_idx];
            int next_lcs_value = lcs[next_str1_idx][next_str2_idx];

            if(cur_lcs_value == 0) {
                break;
            }

            if(next_lcs_value < cur_lcs_value) {
                sb.append(str1.charAt(cur_str1_idx-1));
            }

            cur_str1_idx = next_str1_idx;
            cur_str2_idx = next_str2_idx;
        }

        sb.reverse();
    }

    static void init_setting() throws IOException {
        str1 = br.readLine();
        str2 = br.readLine();

        lcs_string = new String[str1.length() + 1][str2.length() + 1];
        lcs = new int[str1.length()+1][str2.length()+1];
        lcs_1 = new int[str1.length()+1][str2.length()+1];
        lcs_2 = new int[str1.length()+1][str2.length()+1];
        sb = new StringBuilder();

        for(int i=0;i<=str1.length();i++) {
            for(int j=0;j<=str2.length();j++) {
                lcs_string[i][j] = "";
                lcs[i][j] = lcs_1[i][j] = lcs_2[i][j] = 0;
            }
        }
    }
}
