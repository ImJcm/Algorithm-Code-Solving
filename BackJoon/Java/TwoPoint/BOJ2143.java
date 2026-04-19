package TwoPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
두 배열의 합

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	64 MB	19315	6405	4237	30.639%
문제
한 배열 A[1], A[2], …, A[n]에 대해서, 부 배열은 A[i], A[i+1], …, A[j-1], A[j] (단, 1 ≤ i ≤ j ≤ n)을 말한다. 이러한 부 배열의 합은 A[i]+…+A[j]를 의미한다. 각 원소가 정수인 두 배열 A[1], …, A[n]과 B[1], …, B[m]이 주어졌을 때, A의 부 배열의 합에 B의 부 배열의 합을 더해서 T가 되는 모든 부 배열 쌍의 개수를 구하는 프로그램을 작성하시오.

예를 들어 A = {1, 3, 1, 2}, B = {1, 3, 2}, T=5인 경우, 부 배열 쌍의 개수는 다음의 7가지 경우가 있다.

T(=5) = A[1] + B[1] + B[2]
      = A[1] + A[2] + B[1]
      = A[2] + B[3]
      = A[2] + A[3] + B[1]
      = A[3] + B[1] + B[2]
      = A[3] + A[4] + B[3]
      = A[4] + B[2]
입력
첫째 줄에 T(-1,000,000,000 ≤ T ≤ 1,000,000,000)가 주어진다. 다음 줄에는 n(1 ≤ n ≤ 1,000)이 주어지고, 그 다음 줄에 n개의 정수로 A[1], …, A[n]이 주어진다. 다음 줄에는 m(1 ≤ m ≤ 1,000)이 주어지고, 그 다음 줄에 m개의 정수로 B[1], …, B[m]이 주어진다. 각각의 배열 원소는 절댓값이 1,000,000을 넘지 않는 정수이다.

출력
첫째 줄에 답을 출력한다. 가능한 경우가 한 가지도 없을 경우에는 0을 출력한다.

예제 입력 1
5
4
1 3 1 2
3
1 3 2
예제 출력 1
7
 */
/*
    부분 배열의 가능한 수의 배열을 만들고(O(N^2)), 부분배열 A,B를 Two-Point알고리즘(O(N))을 사용하여 목표값에 해당하는 경우, count를 증가시켜
    답을 도출한다

    문제의 요구 알고리즘 : 이분 탐색 + 누적합

 */
public class BOJ2143 {
    static int N;
    static ArrayList<Integer> aArr, bArr;
    static ArrayList<Long> saArr, sbArr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        int an = Integer.parseInt(br.readLine());
        aArr = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<an;i++) {
            aArr.add(Integer.parseInt(st.nextToken()));
        }

        int bn = Integer.parseInt(br.readLine());
        bArr = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<bn;i++) {
            bArr.add(Integer.parseInt(st.nextToken()));
        }

        //부분 배열에서 가능한 배열 원소들의 합 배열
        saArr = new ArrayList<>();
        for(int i=0;i<an;i++) {
            long sum = 0;
            for(int j=i;j<an;j++) {
                sum += aArr.get(j);
                saArr.add(sum);
            }
        }

        sbArr = new ArrayList<>();
        for(int i=0;i<bn;i++) {
            long sum = 0;
            for(int j=i;j<bn;j++) {
                sum += bArr.get(j);
                sbArr.add(sum);
            }
        }

        //Default Ascending sort
        saArr.sort(null);
        sbArr.sort(null);
        System.out.println(solve());
    }

    //Two-point 알고리즘 사용으로 목표값 가능한 경우의 수 찾기
    static long solve() {
        int pa = 0;
        int pb = sbArr.size()-1;
        //부분 배열의 합 쌍의 개수가 int 범위를 넘어가는 경우의 수가 나올 수 있으므로,
        //long type 선언
        long equalCnt = 0;

        while(pa < saArr.size() && pb >= 0) {
            Long sum = saArr.get(pa) + sbArr.get(pb);

            if(sum == N) {
                long a = saArr.get(pa);
                long b = sbArr.get(pb);
                long cntA = 0, cntB = 0;

                //목표값에 부분배열의 합으로 동일한 경우 count
                while(pa < saArr.size() && saArr.get(pa) == a) {
                    cntA++;
                    pa++;
                }

                while(pb >= 0 && sbArr.get(pb) == b) {
                    cntB++;
                    pb--;
                }

                equalCnt += (cntA * cntB);
            } else if(sum > N) {
                pb--;
            } else if(sum < N) {
                pa++;
            }
        }

        return equalCnt;
    }
}
