package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
문자열 분석

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	36906	15055	12464	41.247%
문제
문자열 N개가 주어진다. 이때, 문자열에 포함되어 있는 소문자, 대문자, 숫자, 공백의 개수를 구하는 프로그램을 작성하시오.

각 문자열은 알파벳 소문자, 대문자, 숫자, 공백으로만 이루어져 있다.

입력
첫째 줄부터 N번째 줄까지 문자열이 주어진다. (1 ≤ N ≤ 100) 문자열의 길이는 100을 넘지 않는다.

출력
첫째 줄부터 N번째 줄까지 각각의 문자열에 대해서 소문자, 대문자, 숫자, 공백의 개수를 공백으로 구분해 출력한다.

예제 입력 1
This is String
SPACE    1    SPACE
 S a M p L e I n P u T
0L1A2S3T4L5I6N7E8
예제 출력 1
10 2 0 2
0 10 1 8
5 6 0 16
0 8 9 0
출처
문제를 만든 사람: baekjoon
알고리즘 분류
구현
문자열
 */
/*
알고리즘 핵심
자료구조 (문자열)
1. 입력으로 주어진 문자열을 split()으로 분리하여 소문자, 대문자, 숫자, 공백문자를 조건에 맞게 ans[0..3]에 각각 저장한다.
2. 각 문자열의 소문자,대문자,숫자,공백 문자의 수를 출력한다.
3. 입력으로 주어진 문자열의 종료 조건은 null일 경우로 정한다.
 */
public class BOJ10820 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));;
    static String[] S;
    static Integer[] ans;
    static boolean flag = true;

    public static void main(String[] args) throws IOException {
        while(true) {
            init_setting();

            if(flag) solve();
            else return;
        }
    }

    private static void solve() {
        for(String s : S) {
            char ch = s.charAt(0);
            if('a' <= ch && ch <= 'z') ans[0]++;
            else if('A' <= ch && ch <= 'Z') ans[1]++;
            else if('0' <= ch && ch <= '9') ans[2]++;
            else ans[3]++;
        }
        System.out.println(ans[0] + " " + ans[1] + " " + ans[2] + " " + ans[3]);
    }

    private static void init_setting() throws IOException {
        String input = br.readLine();

        if(input == null) flag = false;
        else S = input.split("");

        ans = new Integer[] {0,0,0,0};
    }
}
