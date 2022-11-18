import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*

 */
public class BOJ7570 {
    static int N;
    static int[] kids;
    static int[] locations;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        kids = new int[N+1];
        Arrays.fill(kids,0);

        locations = new int[N+1];
        dp = new int[N+1];
        Arrays.fill(dp,0);
        String[] str = br.readLine().split(" ");

        for(int i=1;i<=N;i++) {
            kids[i] = Integer.parseInt(str[i-1]);
            locations[kids[i]] = i;
        }

        System.out.println(greedy_solve());
        System.out.println(dp_solve());
    }

    static int greedy_solve() {
        /*
            location을 보면서 i인덱스에 해당하는 수가 해당 위치에서 정렬에 필요한 작업이 필요하지 않다고
            판단되는 경우 = 이미 정려된 위치에 존재하는 index(fet_num)의 경우 움직여야하는 값에서 제외
            이를 1~N까지 수행하여 N - fet_num을 반환
            ㄴ 줄 서 있는 어린이 중 한명을 선택하여 제일 앞이나 뒤를 보내기 때문에, 자체적으로 선택되는 어린이는
            정렬된 상태의 값을 우선적으로 보내면 되므로 전체에서 정렬에 필요하지 않은 어린이의 수를 뺀 값
            or 정렬을 위해 선택된 어린이 수를 반환

            핵심 : LIS(longest increasing subsequence) 최장 증가 부분수열
            증가수열에 포함하는 번호는 고정한 채 나머지 번호만 움직이면 되기 때문에, 나머지 번호의 갯수만큼 이동하면
            최소한 움직이는 횟수를 알 수 있다.

            결론적으로, 가장 긴 증가수열을 찾되 연속된 수를 가진 증가수열을 찾는 것

         */
        int cnt = 1;
        int fet_cnt = -1;
        for(int i=1;i<N;i++) {
            if(locations[i] < locations[i+1]) {
                //position을 기준으로 비교를 하기때문에, 순차적인 증가부분수열인지 확인하는 과정이다
                /*
                    ex) i=1) 1의 위치 < 2의 위치, true이면, 연속적 + 증가부분수열임을 만족하므로
                    연속적 + 증가부분수열의 길이(cnt)를 +1 하며 반복문 진행하고, 최대 길이 업데이트

                    연속적 증가부분수열이 깨지면, cnt를 1로 초기화하고, 해당 i값 부터 연속적 + 증가부분수열이 있는지
                    확인하고 더 큰 길이의 증가부분수열의 길이 값을 업데이트

                    7576처럼 어린이의 위치를 앞 or 뒤로만 옮기기가 가능한 경우,
                    location을 통해 연속적 + 증가부분수열의 최대 길이를 구한 뒤
                    정렬하기 위해 움직이는 최소 횟수는 N - (연속적 + 증가부분수열의 최대길이)

                    2631의 경우, 어린의의 정렬 위치를 앞or뒤or선택위치 3가지를 자유롭게 이용할 수 있기 때문에
                    연속된 + 증가부분수열이 아니더라도, 증가부분수열 중 최대 길이를 구한 뒤, (N - 증가부분수열 최대 길이)
                    를 하면 답을 구할 수 있다.
                 */
                cnt += 1;
                if (cnt > fet_cnt) fet_cnt = cnt;
            } else {
                cnt = 1;
            }
        }

        return (N - fet_cnt);
    }

    /*
        dp의 경우, 순차적으로 줄세우기가 이루어지므로, i~N까지 진행하면서, 이미 증가부분수열의 길이가
        존재하면 dp[i] = dp[i-1] + 1로 업데이트하고, 수행 전에 증가부분수열이 없다면, 0에서 + 1한 값이
        들어가고, 순차적으로 증가하면서 증가부분수열을 업데이트하면서 반복문을 진행하므로 아래와 같이
        dp[i] = dp[i-1] + 1로 연속적 + 증가부분수열의 길이를 구할 수 있다
     */
    static int dp_solve() {
        int max = 0;
        //dp[i] = i번호 일때까지 연속된 증가수열의 개수
        for(int i=1;i<=N;i++) {
            dp[kids[i]] = dp[kids[i]-1] + 1;
            max = Math.max(dp[kids[i]],max);
        }

        return (N - max);
    }
}
