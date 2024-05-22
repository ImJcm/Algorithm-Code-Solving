package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

/*
LCS

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.1 초 (하단 참고)	256 MB	88517	36647	26828	40.776%
문제
LCS(Longest Common Subsequence, 최장 공통 부분 수열)문제는 두 수열이 주어졌을 때, 모두의 부분 수열이 되는 수열 중 가장 긴 것을 찾는 문제이다.

예를 들어, ACAYKP와 CAPCAK의 LCS는 ACAK가 된다.

입력
첫째 줄과 둘째 줄에 두 문자열이 주어진다. 문자열은 알파벳 대문자로만 이루어져 있으며, 최대 1000글자로 이루어져 있다.

출력
첫째 줄에 입력으로 주어진 두 문자열의 LCS의 길이를 출력한다.

예제 입력 1
ACAYKP
CAPCAK
예제 출력 1
4
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: bang627, beginnertomaster, eric00513, fs_edge, qpwoeiruty
알고리즘 분류
다이나믹 프로그래밍
문자열
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
dp를 활용하는 방법이 생각나지 않아서 처음 접근 방식으로 LCS로 가장 긴 공통 부분 수열을 구해야 하기 때문에 두 문자열 중 짧은 문자열의 길이를 기준으로
순차적으로 -1씩 줄여가며 부분 집합을 구하여 서로 비교하고 같은 부분 집합이 존재하면 그 문자열의 길이가 최장 길이 공통 부분 수열의 값으로 생각할 수 있다.
하지만, 해당 방법으로 수행 시, O(n * n * n)으로 최대 1000 * 1000 * 1000의 연산을 수행해야 하므로 시간 초과 및 메모리 초과 발생할 것으로 생각된다.

그래서, 접근 방법이 떠오르지 않아서 정답코드를 참고하였다.
핵심 접근 방식은 부분수열에서 '순서'가 지켜지기 때문에 각 문자열들의 문자드을 서로 비교하면서 서로 같으면 값을 1씩 증가시키면서 누적합을 구하는 것
    A   C   A   Y   K   P
C   0   1   1   1   1   1
A   1   1   2   2   2   2
P   1   1   2   2   2   3
C   1   2   2   2   2   3
A   1   2   3   3   3   3
K   1   2   3   3   4   4
각 열을 채울 때 같은 원소가 있다면 이전 열 또는 행에 +1 한 값이 LCS 길이가 되는 것이다.
lcs(x,y) = lcs(x-1,y-1) + 1            (x==y)
lcs(x,y) = max(lcs(x-1,y), lcs(x,y-1)) (x!=y)
 */
public class BOJ9251 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String str1,str2;
    static int[][] lcs;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        System.out.println(Top_down(str1.length()-1, str2.length()-1));
        //Bottom_up();
        //System.out.println(lcs[str1.length()-1][str2.length()-1]);
    }

    /*
        문자열 s, 길이 k를 지정하여 부분 수열 집합을 반환
     */
    static void wrong_solve() {
        k_subsequence(0,0,n_str,str1,5,sub_seq_arr,visited);
    }

    static int Top_down(int r, int c) {
        if(r == -1 || c == -1) {
            return 0;
        }

        // 도달하지 않은 최장 공통 부분 수열인 경우
        if(lcs[r][c] == -1) {
            // 두 문자열의 r,c 위치에 해당하는 문자가 같은 경우, lcs 값이 증가하기 때문에, (r-1, c-1)의 lcs 값 + 1
            if(str1.charAt(r) == str2.charAt(c)) {
                lcs[r][c] = Top_down(r-1,c-1) + 1;
            }
            // 두 문자열의 r,c 위치에 해당하는 문자가 다른 경우, lcs 값은 (r-1,c)와 (r,c-1) 중 최장 공통 부분 수열의 값 중 큰값
            else {
                lcs[r][c] = Math.max(Top_down(r-1,c), Top_down(r,c-1));
            }
        }

        return lcs[r][c];
    }

    static void Bottom_up() {
        for(int i=0;i<str1.length();i++) {
            for(int j=0;j<str2.length();j++) {
                if(str1.charAt(i) == str2.charAt(j)) {
                    lcs[i][j] = (i - 1) < 0 || (j - 1) < 0 ? 1 : lcs[i-1][j-1] + 1;
                } else {
                    lcs[i][j] = (i - 1) < 0 || (j - 1) < 0 ? 1 : Math.max(lcs[i-1][j],lcs[i][j-1]);
                }
            }
        }
    }

    static void init_setting() throws IOException {
        str1 = br.readLine();
        str2 = br.readLine();

        lcs = new int[str1.length()][str2.length()];

        for(int r=0;r<str1.length();r++) {
            for(int c=0;c<str2.length();c++) {
                lcs[r][c] = -1;
            }
        }

        visited = new boolean[str1.length()];
    }


    static HashSet<String> sub_seq_arr = new HashSet<>();
    static boolean[] visited;
    static String n_str = "";
    static void k_subsequence(int depth, int start, String cs, String s, int k, HashSet<String> arr, boolean[] v) {
        if(depth == k) {
            arr.add(cs);
            return;
        }

        for(int i=start;i<s.length();i++) {
            if(v[i]) continue;
            cs += s.charAt(i);
            v[i] = true;
            k_subsequence(depth + 1, i + 1, cs, s, k, arr, v);
            cs = cs.substring(0,depth);
            v[i] = false;
        }
    }
}

