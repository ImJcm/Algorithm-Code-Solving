import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
제곱 ㄴㄴ 수
 
시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	47978	8793	6078	20.742%
문제
어떤 정수 X가 1보다 큰 제곱수로 나누어 떨어지지 않을 때, 그 수를 제곱ㄴㄴ수라고 한다. 제곱수는 정수의 제곱이다. min과 max가 주어지면, min보다 크거나 같고, max보다 작거나 같은 제곱ㄴㄴ수가 몇 개 있는지 출력한다.

입력
첫째 줄에 두 정수 min과 max가 주어진다.

출력
첫째 줄에 min보다 크거나 같고, max보다 작거나 같은 제곱ㄴㄴ수의 개수를 출력한다.

제한
1 ≤ min ≤ 1,000,000,000,000
min ≤ max ≤ min + 1,000,000
예제 입력 1 
1 10
예제 출력 1 
7
예제 입력 2 
15 15
예제 출력 2 
1
예제 입력 3 
1 1000
예제 출력 3 
608
 */
/*
    핵심 알고리즘 : 수학, 소수판정, 에라토스테네스의 체

    에라토스테네스의 체를 통해 소수 배열을 구하고, 소수배열에서 소수의 제곱을 시작으로 배수를 적용하여 max까지 값을 제곱수로 설정하고
    나머지 수는 제곱 ㄴㄴ 수로 판단한다
    이때, 1 <= min <= 10^12로 크기 때문에 배열을 전부 선언하고 제곱수를 판단하기에는 너무 크기때문에, min <= max 의 최대 차이인 1000000
    을 이용하여 제곱수 배열을 max - min + 1로 제한하고 해당 배열에 제곱수를 판정하는 것이 중요하다고 생각한다

    for (long j = min / square + (min % square != 0 ? 1 : 0); j * square <= max; j++) {
         //ck 배열은 1~1000001크기의 배열이므로, 내부에 (j * square - min)에 제곱수를 true로 설정
         if (!ck[(int) (j * square - min)]) {
             ck[(int) (j * square - min)] = true;
         }
     }
     위의 코드가 중요, j의 초기 값이 min값 이상의 값으로 시작할 수 있는 j값을 설정한다
     이때, min / sqaure 값이 1이하의 소수값을 가질 수 있기 때문에, min % square != 0 ? 1 : 0)으로 최소 + 1로 설정

 */
public class BOJ1016 {
    static long min,max;
    static boolean[] prime;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        min = Long.parseLong(st.nextToken());
        max = Long.parseLong(st.nextToken());

        solve();

    }

    static void solve() {
        prime = new boolean[1000001];
        Arrays.fill(prime,true);

        //max의 sqrt는 최대 1,000,000까지 이므로 해당 범위 내의 소수를 에라토스테네스의 체로 구현
        for (int i = 2; i <= 1000000; i++) {
            if (prime[i]) {
                for (int j = 2; i * j <= 1000000; j++)
                    prime[i * j] = false;
            }
        }

        //min~max까지의 범위 내에서 제곱 ㄴㄴ 수인지 판별하는 ck 배열생성
        boolean[] ck = new boolean[(int) (max - min + 1)];
        for (long i = 2; i * i <= max; i++) {
            if (!prime[(int) i])
                continue;   //소수가 아닌 경우, continue
            //소수의 제곱에 j배 만큼의 수들은 제곱 ㄴㄴ수가 아닌 것으로 판단
            long square = i * i;    //소수의 제곱을 저장


            //예시 100보다 큰 숫자에서 3으로 나누어 떨어지는 가장 가까운 수
            //100 + (3 - 100 % 3) = 102
            //j의 시작 지점은 min에서 가장 가까운 수를 만들기 위해, j * square를 통해 가까운 수를 만들고 max까지 반복문 진행
            for (long j = min / square + (min % square != 0 ? 1 : 0); j * square <= max; j++) {
                //ck 배열은 1~1000001크기의 배열이므로, 내부에 (j * square - min)에 제곱수를 true로 설정
                if (!ck[(int) (j * square - min)]) {
                    ck[(int) (j * square - min)] = true;
                }
            }
        }

        int cnt = 0;
        for (long i = min; i <= max; i++)
            //min ~ max 까지 false인 수를 제곱 ㄴㄴ수이므로 갯수 증가
            if (!ck[(int) (i - min)])
                cnt++;

        System.out.println(cnt);
    }
}

/*
1 10
1,2,3,5,6,7,10
 */
