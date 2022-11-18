/*
감소하는 수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	21269	6356	5026	32.723%
문제
음이 아닌 정수 X의 자릿수가 가장 큰 자릿수부터 작은 자릿수까지 감소한다면, 그 수를 감소하는 수라고 한다. 예를 들어, 321과 950은 감소하는 수지만, 322와 958은 아니다. N번째 감소하는 수를 출력하는 프로그램을 작성하시오. 0은 0번째 감소하는 수이고, 1은 1번째 감소하는 수이다. 만약 N번째 감소하는 수가 없다면 -1을 출력한다.

입력
첫째 줄에 N이 주어진다. N은 1,000,000보다 작거나 같은 자연수 또는 0이다.

출력
첫째 줄에 N번째 감소하는 수를 출력한다.

예제 입력 1
18
예제 출력 1
42
예제 입력 2
0
예제 출력 2
0
예제 입력 3
500000
예제 출력 3
-1
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*
    감소하는 수 중 가장큰 수는 9876543210이고, 이는 길이가 10인 자연수 중 가장큰 수이므로,
    9876543210이 상한인 감소하는 수의 배열을 만들고 N번째의 감소하는 수를 출력하면 된다
 */
public class BOJ1038 {
    static int N;
    static List<Long> list;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        list = new ArrayList<Long>();
        if(N > 1022) {
            System.out.println("-1");
            return;
        }

        for(int i=0;i<10;i++) {
            decrease_Nums(i,1);
            //i값을 가장 처음 수로 시작하여 감소하는 수를 만들기 위해 재귀함수 호출
        }

        Collections.sort(list);
        //System.out.println(list.size());  //list.size() = 1023
        System.out.println(list.get(N));
    }

    static void decrease_Nums(long num, int len) {
        //상한 값 9876543210의 길이 10을 넘어가면 ret
        if(len > 10) return;

        //만들어지는 감소하는 수를 list에 add
        list.add(num);

        //감소하는 수를 만드는 재귀 로직
        for(int i=0;i<10;i++) {
            //일의자리 수가 i보다 작을 경우, 기존의 num에 10을 곱하고, 일의자리에 i를 더하여
            //새로운 감소하는 수와 길이를 1추가하고 재귀함수를 호출한다
            if(num % 10 > i) {
                decrease_Nums(num*10 + i,len+1);
            }
        }
    }
}
