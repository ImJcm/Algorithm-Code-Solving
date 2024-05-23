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
BOJ9251 - LCS
 */
public class BOJ9252 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String str1,str2;
    static String[][] lcs_string;

    static int[][] lcs;
    static int[][] lcs_1;
    static int[][] lcs_2;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        String result = Top_down(str1.length()-1, str2.length()-1);

        System.out.println(result.length());
        System.out.println(result);


        Bottom_up();

        System.out.println(lcs_string[str1.length()][str2.length()].length());
        System.out.println(lcs_string[str1.length()][str2.length()]);
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

    static void init_setting() throws IOException {
        str1 = br.readLine();
        str2 = br.readLine();

        lcs_string = new String[str1.length() + 1][str2.length() + 1];
        lcs = new int[str1.length()+1][str2.length()+1];
        lcs_1 = new int[str1.length()+1][str2.length()+1];
        lcs_2 = new int[str1.length()+1][str2.length()+1];

        for(int i=0;i<=str1.length();i++) {
            for(int j=0;j<=str2.length();j++) {
                lcs_string[i][j] = "";
                lcs[i][j] = lcs_1[i][j] = lcs_2[i][j] = 0;
            }
        }
    }
}
