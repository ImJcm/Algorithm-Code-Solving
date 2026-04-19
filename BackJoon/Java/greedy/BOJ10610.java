package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
30 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	55237	22766	18185	40.681%
문제
어느 날, 미르코는 우연히 길거리에서 양수 N을 보았다. 미르코는 30이란 수를 존경하기 때문에, 그는 길거리에서 찾은 수에 포함된 숫자들을 섞어 30의 배수가 되는 가장 큰 수를 만들고 싶어한다.

미르코를 도와 그가 만들고 싶어하는 수를 계산하는 프로그램을 작성하라.

입력
N을 입력받는다. N는 최대 105개의 숫자로 구성되어 있으며, 0으로 시작하지 않는다.

출력
미르코가 만들고 싶어하는 수가 존재한다면 그 수를 출력하라. 그 수가 존재하지 않는다면, -1을 출력하라.

예제 입력 1
30
예제 출력 1
30
예제 입력 2
102
예제 출력 2
210
예제 입력 3
2931
예제 출력 3
-1
예제 입력 4
80875542
예제 출력 4
88755420
출처
Contest > Croatian Open Competition in Informatics > COCI 2014/2015 > Contest #4 1번

문제를 번역한 사람: aaa
알고리즘 분류
수학
그리디 알고리즘
문자열
정렬
정수론
 */
/*
알고리즘 핵심
그리디 알고리즘
1. 30배수에서 10의 자리수 값은 100,1000,...,의 자리수의 합 % 9의 값에 따라 결정된다.
ex) 0,3,6,9 - 0,3,6,9 / 1,4,7 - 2,5,8 / 2,5,8 - 1,4,7
420 : 4 -> 2, 990 : 9 -> 9, 1680 : 1 + 6 = 7 -> 8, 10020 : 1 -> 2
2. 30배수임을 만족하지 못하는 경우로 입력으로 주어지는 N의 크기가 2보다 작은 1의 자리수 또는 N의 크기가 2보다 크지만 0을 포함하지 못하는 경우는 -1출력한다.
3. N의 크기가 2보다 크고, 0이 1개 이상인 경우 입력으로 주어진 수 중에서 작은 값을 시작으로 해당 값에 대응되는 100,1000,...의 자리수들의 값의 합 % 9의 값을
만족하는지 검사한다.
4. 만족하는 경우, 입력으로 주어진 값으로 30의 배수를 만들 수 있으므로 큰 수를 주어진 개수만큼 숫자를 만들고 10의 자리를 해당하는 값 + "0"을 조합하여 ans에 출력한다.
입력으로 주어진 수를 기준으로 체크했을 때, 만들어지는 경우가 없는 경우 -1을 출력한다.
 */
public class BOJ10610 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String N,ans;
    static int size;
    static int[] nums;
    static Map<Integer, ArrayList<Integer>> standard;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        int sum = sum_num();

        if(size < 2) {
            ans = "-1";
        } else {
            if(nums[0] == 0) {
                ans = "-1";
            } else {
                nums[0]--;
                for(int i = 0; i < 10; i++) {
                    if(nums[i] > 0) {
                        if(standard.get((sum - i) % 9).contains(i)) {
                            make_num(i);
                            break;
                        }
                    }
                }
                ans = ans.isEmpty() ? "-1" : ans;
            }
        }

        System.out.println(ans);
    }

    private static void make_num(int n) {
        for(int i = 9; i >= 0; i--) {
            if(nums[i] > 0) {
                ans += Integer.toString(i).repeat(nums[i]);
            }
        }

        ans += "0";
    }

    private static int sum_num() {
        int result = 0;

        for(int i = 0; i < nums.length; i++) {
            result += (nums[i] * i);
        }

        return result;
    }

    private static void init_setting() throws IOException {
        N = br.readLine();

        size = N.length();

        nums = new int[10];
        standard = new HashMap<>();

        for(int i = 0; i < N.length(); i++) {
            //int num = (int) N.charAt(i) - '0';
            int num = Character.getNumericValue(N.charAt(i));
            nums[num]++;
        }

        for(int i = 0; i < 10; i++) {
            standard.put(i,new ArrayList<>());

            for(int j = 0; j < 10; j += 3) {
                if(j - (i % 3) < 0) continue;
                standard.get(i).add(j - (i % 3));
            }
        }

        ans = "";
    }
}
