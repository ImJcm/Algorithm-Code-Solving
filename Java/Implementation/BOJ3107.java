package Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
IPv6 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	5504	1956	1564	37.106%
문제
IPv6은 길이가 128비트인 차세대 인터넷 프로토콜이다.

IPv6의 주소는 32자리의 16진수를 4자리씩 끊어 나타낸다. 이때, 각 그룹은 콜론 (:)으로 구분해서 나타낸다.

예를 들면, 다음과 같다.

2001:0db8:85a3:0000:0000:8a2e:0370:7334
32자리의 16진수는 사람이 읽고 쓰기에 불편하고, 대부분의 자리가 0이기 때문에 아래와 같이 축약할 수 있다.

각 그룹의 앞자리의 0의 전체 또는 일부를 생략 할 수 있다. 위의 IPv6을 축약하면, 다음과 같다
2001:db8:85a3:0:00:8a2e:370:7334
만약 0으로만 이루어져 있는 그룹이 있을 경우 그 중 한 개 이상 연속된 그룹을 하나 골라 콜론 2개(::)로 바꿀 수 있다.
2001:db8:85a3::8a2e:370:7334
2번째 규칙은 모호함을 방지하기 위해서 오직 한 번만 사용할 수 있다.

올바른 축약형 IPv6주소가 주어졌을 때, 이를 원래 IPv6 (32자리의 16진수)로 복원하는 프로그램을 작성하시오.

입력
첫째 줄에 올바른 IPv6 주소가 주어진다. 이 주소는 최대 39글자이다. 또한, 주소는 숫자 0-9, 알파벳 소문자 a-f, 콜론 :으로만 이루어져 있다.

출력
첫째 줄에, 입력으로 주어진 IPv6의 축약되지 않은 형태를 출력한다.

예제 입력 1
25:09:1985:aa:091:4846:374:bb
예제 출력 1
0025:0009:1985:00aa:0091:4846:0374:00bb
예제 입력 2
::1
예제 출력 2
0000:0000:0000:0000:0000:0000:0000:0001
출처
Olympiad > Croatian Highschool Competitions in Informatics > 2009 > National Competition #1 - Juniors 1번

문제를 번역한 사람: baekjoon
어색한 표현을 찾은 사람: jh05013
데이터를 추가한 사람: riverkeh, ynlee411
알고리즘 분류
구현
문자열
 */
/*
알고리즘 핵심
구현 + 문자열
1. ":" 정규 문자열로 문자열을 나눈 후, 0이 아닌 문자열과 0으로 이루어진 문자열의 개수를 구한다.
2. :로 구분된 문자열 배열에서 각 문자열마다 반복하여 빈문자열 or 공백이 아닌 문자열인지에 따라 로직을 수행한다.
3. 공백문자인 경우, "0000:" 문자열을 zero_zone 수 만큼 stringbuiler에 추가한다.
4. 공백문자가 아닌 경우, 해당 문자열에 앞자리에 0이 들어올 수 있는 만큼 추가하여 stringbuilder에 추가한다.
5. stringbuilder에 추가된 문자열의 길이가 40이 아닌 경우, 뒷부분에 0으로 구성된 문자열이 추가되지 않을 것으로 판단하여 "0000"문자열을
부족한 수 만큼 채운다.
6. 마지막 문자가 ":"이므로 0 ~ length() - 1까지 출력한다.
 */
public class BOJ3107 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String ipv6;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        String[] split_ipv6 = ipv6.split(":");

        int zero_zone = 0;
        int non_zero_zone = 0;

        for(String ip : split_ipv6) if(!ip.isEmpty()) non_zero_zone++;
        zero_zone = 8 - non_zero_zone;

        StringBuilder sb = new StringBuilder();

        boolean zero_flag = false;

        for(String ip : split_ipv6) {
            if(ip.isEmpty()) {
                if(!zero_flag) {
                    sb.append("0000:".repeat(zero_zone));
                }
                zero_flag = true;
            } else {
                int remain_zero_length = 4 - ip.length();

                sb.append("0".repeat(remain_zero_length));
                sb.append(ip);
                sb.append(":");
            }
        }

        if(sb.length() != 40) {
            sb.append("0000:".repeat(zero_zone));
        }

        System.out.println(sb.substring(0,sb.toString().length() - 1));
    }

    private static void init_setting() throws IOException {
        ipv6 = br.readLine();


    }
}
