package BackJoon;/*
연속합 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	16383	4863	3577	29.317%
문제
n개의 정수로 이루어진 임의의 수열이 주어진다. 우리는 이 중 연속된 몇 개의 수를 선택해서 구할 수 있는 합 중 가장 큰 합을 구하려고 한다. 단, 수는 한 개 이상 선택해야 한다. 또, 수열에서 수를 하나 제거할 수 있다. (제거하지 않아도 된다)

예를 들어서 10, -4, 3, 1, 5, 6, -35, 12, 21, -1 이라는 수열이 주어졌다고 하자. 여기서 수를 제거하지 않았을 때의 정답은 12+21인 33이 정답이 된다.

만약, -35를 제거한다면, 수열은 10, -4, 3, 1, 5, 6, 12, 21, -1이 되고, 여기서 정답은 10-4+3+1+5+6+12+21인 54가 된다.

입력
첫째 줄에 정수 n(1 ≤ n ≤ 100,000)이 주어지고 둘째 줄에는 n개의 정수로 이루어진 수열이 주어진다. 수는 -1,000보다 크거나 같고, 1,000보다 작거나 같은 정수이다.

출력
첫째 줄에 답을 출력한다.

예제 입력 1
10
10 -4 3 1 5 6 -35 12 21 -1
예제 출력 1
54
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.io.IOException;

public class BOJ13398 {
    static int N;
    static int[] A;
    static int[][] mem;
    static int[][] m;
    static int[][] mm;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

//        A = Arrays.asList(br.readLine().split(" "))
//                .stream()
//                .mapToInt(Integer::parseInt)
//                .toArray();
//        mem = new int[100001][100001];
        m = new int[100001][2];
        A = new int[100001];
        mm = new int[100001][3];

        String[] s = br.readLine().split(" ");
        for(int i=1;i<=N;i++) {
            A[i-1] = Integer.parseInt(s[i-1]);
            Arrays.fill(m[i],0);
            Arrays.fill(mm[i],0);
//            Arrays.fill(mem[i],0);
//            mem[i-1][0] = A[0];
        }
        mm[0][0] = mm[0][1] = A[0];


        m[0][0] = A[0];
        m[N-1][1] = A[N-1];
        System.out.println(dp(N));
    }
    /*
        풀이 포인트
        이중 for문의 경우, 메모리 + 시간초과의 오류발생할 가능성 높기 때문에
        단일 for문으로 문제를 해결해야한다.
        그래서, 양 끝에서 진행하는 연속된 합의 수를 구하고, 최종적으로 i=0~n-1의 반복문에서
        i를 기준으로 양 옆 (i-1, i+1)의 연속된 합이 큰 값들을 더하면 답이 도출된다
        m[i-1][0] + m[i+1][1]
        m[][0] : i=0~n-1까지 진행하는 연속된 합을 저장
        m[][1] : i=n-1~0까지 진행하는 연속된 합을 저장
        max = Math.max(max, m[i][0]) : 수를 제거하지 않을 떄의 최대 구간 합
        lv, rv에서 i를 기준으로 m[i-1][0], m[i+1][1]로 i가 제거되었을 때의 최대 구간 합
     */
    static int dp(int n) {
        max = A[0];
        if(n==1) {
            return m[0][0];
        }
        for(int i=1;i<n;i++) {
            m[i][0] = Math.max(A[i], m[i - 1][0] + A[i]);
            m[n-i][1] = Math.max(A[n-i], m[n-i+1][1] + A[n-i]);
            max = Math.max(max,m[i][0]);
        }

        int lv, rv;
        for(int i=0;i<n;i++) {
            if(i == 0) {
                lv = 0;
                rv = m[i+1][1];
            } else if(i == (n-1)) {
                lv = m[i-1][0];
                rv = 0;
            } else {
                lv = m[i-1][0];
                rv = m[i+1][1];
            }
            max = Math.max(max,lv + rv);
        }

         /*
            더 간단하게 풀기
            [0] : 자기자신
            [1] : 이전 값([0],[1],[2])중 큰값과 자기자신을 더했을 때
            [2] : 이전 값의 합중 가장 큰 값을 저장한다(자기자신을 뺐을 때의 최대값)
         */
//        int result = A[0];
//        for(int i=1;i<n;i++) {
//            mm[i][0] = A[i];
//            mm[i][1] = Math.max(mm[i-1][0],Math.max(mm[i-1][1],mm[i-1][2])) + A[i];
//            mm[i][2] = Math.max(mm[i-1][0],mm[i-1][2] + mm[i-1][0]);
//            result = Math.max(result, Math.max(mm[i][0],Math.max(mm[i][1],mm[i][2])));
//        }
//        System.out.println(result);

        return max;



        /*
            n^2의 과정은 시간초과 및 메모리오류를 예상한다
         */
//        for(int j=-1;j<n;j++) {
//            if(j >= 0) {
//                A[j] = 0;
//                mem[j][j]  = 0;
//            }
//            for(int i=1;i<n;i++) {
//                mem[j][i] = Math.max(A[i],mem[j][i-1] + A[i]);
//                max = Math.max(max,mem[j][i]);
//            }
//        }
//        for(int i=1;i<n;i++) {
//            mem[j][i] = Math.max(A[i],mem[j][i-1] + A[i]);
//            max = Math.max(max,mem[j][i]);
//        }
//        return max;
    }
}
