package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
공통 부분 문자열 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	19492	7929	6203	42.246%
문제
두 문자열이 주어졌을 때, 두 문자열에 모두 포함된 가장 긴 공통 부분 문자열을 찾는 프로그램을 작성하시오.

어떤 문자열 s의 부분 문자열 t란, s에 t가 연속으로 나타나는 것을 말한다. 예를 들어, 문자열 ABRACADABRA의 부분 문자열은 ABRA, RAC, D, ACADABRA, ABRACADABRA, 빈 문자열 등이다. 하지만, ABRC, RAA, BA, K는 부분 문자열이 아니다.

두 문자열 ABRACADABRA와 ECADADABRBCRDARA의 공통 부분 문자열은 CA, CADA, ADABR, 빈 문자열 등이 있다. 이 중에서 가장 긴 공통 부분 문자열은 ADABR이며, 길이는 5이다. 또, 두 문자열이 UPWJCIRUCAXIIRGL와 SBQNYBSBZDFNEV인 경우에는 가장 긴 공통 부분 문자열은 빈 문자열이다.

입력
첫째 줄과 둘째 줄에 문자열이 주어진다. 문자열은 대문자로 구성되어 있으며, 길이는 1 이상 4000 이하이다.

출력
첫째 줄에 두 문자열에 모두 포함 된 부분 문자열 중 가장 긴 것의 길이를 출력한다.

예제 입력 1
ABRACADABRA
ECADADABRBCRDARA
예제 출력 1
5
예제 입력 2
UPWJCIRUCAXIIRGL
SBQNYBSBZDFNEV
예제 출력 2
0
출처


Olympiad > Japanese Olympiad in Informatics > JOI 2007/2008 2번

문제를 번역한 사람: baekjoon
데이터를 추가한 사람: eric00513
문제의 오타를 찾은 사람: scka
알고리즘 분류
다이나믹 프로그래밍
문자열
 */
/*
접근방법으로 LCS를 응용할 수 있다고 생각하여 LCS에서 사용한 알고리즘으로 예시#1을 입력으로 하였을 때 결과는 11로 기댓값과는 달랐다.
그 이유는 기존의 LCS 알고리즘은 연속된 부분수열이 아닌 연속되지 않는 증가된 부분 수열을 구하는 것이기 때문이다.

예시#1                결과
ABRACADABRA          5
ECADADABRBCRDARA

예시#1의 예상되는 결과를 2차원 배열로 나타내었을 때 다음과 같다.
row\col 0   1   2   3   4   5   6   7   8   9   10
        A   B   R   A   C   A   D   A   B   R   A
0   E   0   0   0   0   0   0   0   0   0   0   0
1   C   0   0   0   0   1   1   1   1   1   1   1
2   A   1   1   1   1   1   2   2   2   2   2   2
3   D   1   1   1   1   1   2   3   3   3   3   3
4   A   1   1   1   1   1   2   3   4   4   4   4
5   D   1   1   1   1   1   2   3   4   4   4   4
6   A   1   1   1   1   1   2   3   4   4   4   4
7   B   1   2   2   2   2   2   3   4   4   4   4
8   R   1   2   3   3   3   3   3   4   4   5   5
9   B
10  C                   ...
11  R
12  D

2차원 배열로 문자열1과 문자열2를 순차적으로 비교하면서 연속된 부분 수열의 값을 구할 때 두 문자열의 비교 문자가 같은 경우
연속된 부분수열의 길이가 +1 증가된 것을 볼 수 있다. 그래서, (2,5)에서 A로 같은 경우 (1,5)와 (2,4) 중 큰 값 + 1을 하는 것이라고
생각하였지만, (2,7)에서는 위와 같은 규칙이 성립하지 않았다.

즉, 문자가 같은 경우 (x,y) = max((x-1,y), (x,y-1)) + 1, 다른 경우 max((x-1,y), (x,y-1))와 같은 최장 길이 증가 부분수열을 구하는데
사용하는 알고리즘은 사용하지 못한다.

따라서, 기존의 알고리즘의 변경이 필요했다.

위의 기댓값을 통해 점화식 규칙을 찾으려고 했지만 생각해보면 위의 기댓값은 어떠한 연속된 부분수열에서 파생되었는지 알 수 없다.
그 예로, (8,7)에서는 CADA를 최장 길이 연속 부분수열로 알 수 있지만, (8,8)에서 CADA or ADAB 둘 중 어느 것인지 알 수 없고,
(8,9)에서는 ADABR이 최장 길이에 해당하는 부분수열이다.

메모리제이션에 표현할 수 있는 최장 연속 부분수열의 길이를 위와 같이 사용할 경우 어떤 부분수열이 사용되었는지 알 수 없고, (x,y)에서의
값이 이전의 결과 값을 이용할 수 없다고 생각하여 메모리제이션으로 사용할 수 있는 값을 구하는 방식을 달리해야 했다.

연속된 최장 길이 부분수열의 메모리제이션 기댓값 표를 살펴보다가 두 문자열에서 문자가 같은 경우 이전 결과 값에서 증가시키는 조건을
만족해야 하고 연속된 부분수열을 만족하기 위해서 2차원 배열을 순차적으로 (x,y)에서의 각 문자끼리 비교하여 다를 경우 0, 같은 경우
(x,y) = (x-1,y-1) + 1을 만족한다는 것을 볼 수 있었고 다음과 같은 기댓값 표를 만들 수 있었다.

row\col 0   1   2   3   4   5   6   7   8   9   10
        A   B   R   A   C   A   D   A   B   R   A
0   E   0   0   0   0   0   0   0   0   0   0   0
1   C   0   0   0   0   1   0   0   0   0   0   0
2   A   1   0   0   1   0   2   0   1   0   0   1
3   D   0   0   0   0   0   0   3   0   0   0   0
4   A   1   0   0   1   0   1   0   4   0   0   1
5   D   0   0   0   0   0   0   2   0   0   0   0
6   A   1   0   0   1   0   1   0   3   0   0   1
7   B   0   2   0   0   0   0   0   0   4   0   0
8   R   0   0   3   0   0   0   0   0   0   5   0
9   B   0   1   0   0   0   0   0   0   1   0   0
10  C   0   0   0   0   1   0   0   0   0   0   0
11  R   0   0   1   0   0   0   0   0   0   1   0
12  D   0   0   0   0   0   0   1   0   0   0   0

점화식은 다음과 같다.
lcs[x][y] = lcs[x-1][y-1] + 1 (str1[x] = str2[y])
lcs[x][y] = 0                 (str1[x] != str2[y])

Top_down 방식보다 Bottom_up 방식이 메모리와 시간 모두 우수한 것을 볼 수 있다.
 */
public class BOJ5582 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String str1,str2;
    static int max_len = Integer.MIN_VALUE;
    static int[][] lcs;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        //Top_down(str1.length(),str2.length());
        Bottom_up();
        System.out.println(max_len);
    }

    /*
        메모리     |     시간
        79884 KB        776 ms
     */
    static int Top_down(int str1_idx, int str2_idx) {
        if(str1_idx == 0 || str2_idx == 0) {
            return 0;
        }

        if(lcs[str1_idx][str2_idx] == -1) {
            if(str1.charAt(str1_idx-1) == str2.charAt(str2_idx-1)) {
                lcs[str1_idx][str2_idx] = Top_down(str1_idx-1,str2_idx-1) + 1;
            } else {
                lcs[str1_idx][str2_idx] = 0;
            }
            Top_down(str1_idx-1,str2_idx);
            Top_down(str1_idx,str2_idx-1);
        }

        max_len = Math.max(max_len, lcs[str1_idx][str2_idx]);
        return lcs[str1_idx][str2_idx];
    }

    /*
        메모리     |     시간
        77064 KB        284 ms
     */
    static void Bottom_up() {
        for(int i=1;i<=str1.length();i++) {
            for(int j=1;j<=str2.length();j++) {
                if(str1.charAt(i-1) == str2.charAt(j-1)) {
                    lcs[i][j] = lcs[i-1][j-1] + 1;
                } else {
                    lcs[i][j] = 0;
                }
                max_len = Math.max(max_len, lcs[i][j]);
            }
        }
    }

    //Wrong_Answer
    static int Top_down_WA(int str1_idx, int str2_idx) {
        if(str1_idx == 0 || str2_idx == 0) {
            return 0;
        }

        if(lcs[str1_idx][str2_idx] == -1) {
            if(str1.charAt(str1_idx-1) == str2.charAt(str2_idx-1)) {
                lcs[str1_idx][str2_idx] = Math.max(
                        Top_down(str1_idx-1,str2_idx),
                        Top_down(str1_idx,str2_idx-1)
                ) + 1;
            } else {
                lcs[str1_idx][str2_idx] = Math.max(
                        Top_down(str1_idx-1,str2_idx),
                        Top_down(str1_idx,str2_idx-1)
                );
            }
        }
        return lcs[str1_idx][str2_idx];
    }

    static void init_setting() throws IOException {
        str1 = br.readLine();
        str2 = br.readLine();

        lcs = new int[str1.length() + 1][str2.length() + 1];

        for(int i=0;i<=str1.length();i++) {
            for(int j=0;j<=str2.length();j++) {
                //lcs[i][j] = -1;     //Top_down
                lcs[i][j] = 0;      //Bottom_up
            }
        }
    }
}
