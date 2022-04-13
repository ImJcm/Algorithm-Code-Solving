package BOJ.bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
리모컨(Gold5)

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	63522	15075	10360	22.471%
문제
수빈이는 TV를 보고 있다. 수빈이는 채널을 돌리려고 했지만, 버튼을 너무 세게 누르는 바람에, 일부 숫자 버튼이 고장났다.
리모컨에는 버튼이 0부터 9까지 숫자, +와 -가 있다. +를 누르면 현재 보고있는 채널에서 +1된 채널로 이동하고,
-를 누르면 -1된 채널로 이동한다. 채널 0에서 -를 누른 경우에는 채널이 변하지 않고, 채널은 무한대 만큼 있다.
수빈이가 지금 이동하려고 하는 채널은 N이다. 어떤 버튼이 고장났는지 주어졌을 때, 채널 N으로 이동하기 위해서 버튼을 최소
몇 번 눌러야하는지 구하는 프로그램을 작성하시오.
수빈이가 지금 보고 있는 채널은 100번이다.

입력
첫째 줄에 수빈이가 이동하려고 하는 채널 N (0 ≤ N ≤ 500,000)이 주어진다.
둘째 줄에는 고장난 버튼의 개수 M (0 ≤ M ≤ 10)이 주어진다. 고장난 버튼이 있는 경우에는 셋째 줄에는 고장난 버튼이 주어지며,
같은 버튼이 여러 번 주어지는 경우는 없다.

출력
첫째 줄에 채널 N으로 이동하기 위해 버튼을 최소 몇 번 눌러야 하는지를 출력한다.

예제 입력 1
5457
3
6 7 8
예제 출력 1
6

예제 입력 2
100
5
0 1 2 3 4
예제 출력 2
0

예제 입력 3
500000
8
0 2 3 4 6 7 8 9
예제 출력 3
11117

예제 입력 4
100
3
1 0 5
예제 출력 4
0

예제 입력 5
14124
0
예제 출력 5
5

예제 입력 6
1
9
1 2 3 4 5 6 7 8 9
예제 출력 6
2

예제 입력 7
80000
2
8 9
예제 출력 7
2228
 */
public class BOJ1107 {
    static int[] remote_button = new int[10];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String N, result = "";
        int M, count = 0;

        N = br.readLine();
        M = Integer.parseInt(br.readLine());

        Arrays.fill(remote_button,1);
        //런타임 에러(NullPointer Error) 방지
        if(M != 0) {
            String[] temp = br.readLine().split(" ");
            for (int i = 0; i < M; i++) {
                remote_button[Integer.parseInt(temp[i])] = 0;
            }
        }

        //N = 100일 경우,
        if (N.equals("100")) {
            System.out.println(0);
            return;
        }

        //초기 count 값은 N - 100으로 지정
        count = Math.abs(Integer.parseInt(N) - 100);
        //모든 숫자를 고려하기 때문에, 10^6까지 반복
        for(int i=0;i<=Math.pow(10,6);i++) {
            result = Integer.toString(i);
            boolean chk = true;

            //고장난 버튼이 result 숫자에 존재하는지 여부 판단
            for(int j=0;j<result.length();j++) {
                if(remote_button[Character.getNumericValue(result.charAt(j))] == 0) {
                    chk = false;
                    break;
                }
            }
            //고장난 버튼이 사용되지 않은 숫자의 경우, 이전 count값과 (버튼사용횟수 + |N - result|)값을 비교하여 작은 값
            //count에 저장
            if(chk) {
                count = Math.min(count, result.length() + Math.abs(Integer.parseInt(N)-Integer.parseInt(result)));
            }
        }

        System.out.println(count);
        /*
         * N의 각자리 수와 가장 가까운 숫자를 조합하여 result를 만든 후 버튼입력 값(count)로 세팅하고, N - result를 더한값과
         * N - 100 중 작은 값을 출력하려고 할 때, 예외가 발생.
         * N = 80000, M=2 (8 9) -> 10005
         * 따라서, 각 자리의 숫자별로 가까운 값을 조합하여 나온 값으로 횟수를 계산하면 안된다.
         * 모든 숫자를 고려해야할 것 같다.
         */
        /*
        for(int i=0;i<N.length();i++) {
            int c = Integer.parseInt(Character.toString(N.charAt(i)));
            int m = Integer.MAX_VALUE;
            int m_v = 0;

            for(int j=0;j<remote_button.length;j++) {
                if(remote_button[j] == 1) {
                     m = Math.min(m,Math.abs(c-j));
                     m_v = (m == Math.abs(c-j)) ? j : m_v;
                }
            }
            result += Integer.toString(m_v);
            count++;
        }
        System.out.println(Math.min(count+(Math.abs(Integer.parseInt(N) - Integer.parseInt(result))),
                Math.abs(Integer.parseInt(N)-100)));
    }
         */
    }
}

