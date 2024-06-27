package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

/*
로마 숫자 만들기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	6672	3677	2932	53.957%
문제
로마 숫자에서는 수를 나타내기 위해서 I, V, X, L을 사용한다. 각 문자는 1, 5, 10, 50을 의미하고, 이 문제에서 다른 문자는 사용하지 않는다.

하나 또는 그 이상의 문자를 이용해서 수를 나타낼 수 있다. 문자열이 나타내는 값은, 각 문자가 의미하는 수를 모두 합한 값이다. 예를 들어, XXXV는 35, IXI는 12를 의미한다.

실제 로마 숫자에서는 문자의 순서가 중요하지만, 이 문제에서는 순서는 신경쓰지 않는다. 예를 들어, 실제 로마 숫자에서 IX는 9를 의미하지만, 이 문제에서는 11을 의미한다.

로마 숫자를 N개 사용해서 만들 수 있는 서로 다른 수의 개수를 구해보자.

입력
첫째 줄에 사용할 수 있는 문자의 개수 N (1 ≤ N ≤ 20)이 주어진다.

출력
첫째 줄에 로마 숫자 N개를 사용해서 만들 수 있는 서로 다른 수의 개수를 출력한다.

예제 입력 1
1
예제 출력 1
4
I, V, X, L을 만들 수 있다.

예제 입력 2
2
예제 출력 2
10
2, 6, 10, 11, 15, 20, 51, 55, 60, 100을 만들 수 있다.

예제 입력 3
10
예제 출력 3
244
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
수학
구현
브루트포스 알고리즘
조합론
백트래킹
 */
/*
순서는 중요하지 않다 = 어떠한 문자들을 사용한 횟수만 고려하면 된다. 라고 생각했는데, 문자를 구성하는 횟수가 다르더라고 문자들이
이루는 결과값이 같을 수 있다.
예로,n=10, (I,5) + (V,1) + (X,4) = 50, (V,10) = 50와 같이 문자는 다르지만 결과값이 갖으면 서로 다른 수로 보지않는다.

 */
public class BOJ16922 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,cnt;
    static String str;
    static char[] roman_script = {'I','V','X','L'};
    static HashSet<Integer> visited;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        dfs(0,0);

        System.out.println(cnt);
    }

    static void backTracking(int n, int s, int result) {
        if(n == N) {
            if(!visited.contains(result)) {
                visited.add(result);
                cnt++;
            }
            return;
        }

        backTracking(n+1, s, result);
        backTracking(n+1, s+1, result);
    }

    static void dfs(int n, int s) {
        if(n == N) {
            int result = 0;
            for(int i=0;i<str.length();i++) {
                int value = 0;
                char c = str.charAt(i);
                switch (c) {
                    case 'I':
                        value = 1;
                        break;
                    case 'V':
                        value = 5;
                        break;
                    case 'X':
                        value = 10;
                        break;
                    case 'L':
                        value = 50;
                        break;
                }
                result += value;
            }

            if(!visited.contains(result)) {
                visited.add(result);
                cnt++;
            }
            return;
        }

        for(int i=s;i < roman_script.length;i++) {
            str += roman_script[i];
            dfs(n+1, i);
            str = str.substring(0,n);
        }
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        visited = new HashSet<>();

        cnt = 0;

        str = "";
    }
}
