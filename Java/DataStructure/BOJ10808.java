package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
알파벳 개수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	63535	43142	33984	68.125%
문제
알파벳 소문자로만 이루어진 단어 S가 주어진다. 각 알파벳이 단어에 몇 개가 포함되어 있는지 구하는 프로그램을 작성하시오.

입력
첫째 줄에 단어 S가 주어진다. 단어의 길이는 100을 넘지 않으며, 알파벳 소문자로만 이루어져 있다.

출력
단어에 포함되어 있는 a의 개수, b의 개수, …, z의 개수를 공백으로 구분해서 출력한다.

예제 입력 1
baekjoon
예제 출력 1
1 1 0 0 1 0 0 0 0 1 1 0 0 1 2 0 0 0 0 0 0 0 0 0 0 0
출처
문제를 만든 사람: baekjoon
잘못된 데이터를 찾은 사람: djm03178
문제의 오타를 찾은 사람: eric00513
알고리즘 분류
구현
문자열
 */
/*
알고리즘 핵심
자료구조 (문자열 + Map)
1. a-z까지 소문자 알파벳은 Map 자료구조에 0으로 초기화 선언한다.
2. 입력으로 주어진 문자열을 각 문자로 나누어 해당 문자의 등장 횟수를 map에 누적하여 더한다.
3. map을 a-z 순서로 StringBuilder에 저장하고 출력한다.
 */
public class BOJ10808 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String S;
    static Map<String,Integer> map;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        for(String s : S.split("")) {
            map.put(s, map.get(s) + 1);
        }

        char ch = 'a';

        for(int i = 0; i < 26; i++) {
            Integer v = map.get(String.valueOf((char)(ch + i)));
            sb.append(v).append(" ");
        }

        System.out.println(sb.toString());
    }

    private static void init_setting() throws IOException {
        S = br.readLine();

        map = new HashMap<>();

        char ch = 'a';

        for(int i = 0; i < 26; i++) {
            String s = String.valueOf((char)(ch + i));
            map.put(s, 0);
        }

        sb = new StringBuilder();
    }
}
