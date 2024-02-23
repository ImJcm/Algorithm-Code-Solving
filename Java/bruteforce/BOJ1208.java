package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
부분수열의 합 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	28028	7282	4949	24.791%
문제
N개의 정수로 이루어진 수열이 있을 때, 크기가 양수인 부분수열 중에서 그 수열의 원소를 다 더한 값이 S가 되는 경우의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 정수의 개수를 나타내는 N과 정수 S가 주어진다. (1 ≤ N ≤ 40, |S| ≤ 1,000,000) 둘째 줄에 N개의 정수가 빈 칸을 사이에 두고 주어진다. 주어지는 정수의 절댓값은 100,000을 넘지 않는다.

출력
첫째 줄에 합이 S가 되는 부분수열의 개수를 출력한다.

예제 입력 1
5 0
-7 -3 -2 5 8
예제 출력 1
1
출처
문제를 만든 사람: author5
문제의 오타를 찾은 사람: jh05013
데이터를 추가한 사람: klimmek55, koosaga, rdd6584, yeonunu
알고리즘 분류
이분 탐색
중간에서 만나기
 */
/*
방법1.
BOJ1182의 비트마스크를 통한 모든 부분수열을 구하는 코드를 이용하여 40/2 = 20, 20으로 나눈 각 배열에서 모든 부분수열의 합을
[1<<20] * 2 크키의 배열에 저장하고, 이후에 S를 만족하는 경우의 수를 Arr1에서 S1, Arr2에서 S2, Arr1 + Arr2를 조합해서 S를 만족하는
경우의 수 = S3를 모두 더한 S1 + S2 + S3가 정답인 경우
=> 시간초과 발생

중간에서 만나기 개념 참고 : https://restudycafe.tistory.com/523
개념 참고 : https://hyeo-noo.tistory.com/128, https://loosie.tistory.com/563

방법2.
subArr_down, subArr_up 을 오름차순 정렬시키고, Two-Point 방식으로 경우의 수를 구하는 것도 가능한 것 같다.

 */
public class BOJ1208 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N,S,cnt = 0;
    static int[] arr,subArr_down,subArr_up;

    public static void main(String[] args) throws IOException{
        init_setting();

        solve();
    }

    // 다른 코드를 참고하고 제출하는데 결과가 틀렸다고 나오는데 이유를 처음에 몰랐는데 알고보니 경우의 수를 누적하는 값이 int의 범위를
    // 넘어갈 수 있다는 상황을 고려하지 않았다.
    // 그래서 cnt : int -> long으로 바꾸고 정답처리 되었다.
    static void solve() {
        make_subArr();

        for(int i=0;i<subArr_down.length;) {
            int down_value = subArr_down[i];
            long down_value_cnts = upper_bound(subArr_down,down_value) - lower_bound(subArr_down,down_value);
            long up_value_cnts = upper_bound(subArr_up,S - down_value) - lower_bound(subArr_up,S - down_value);
            cnt += down_value_cnts * up_value_cnts;
            i += down_value_cnts;
        }

        // i = 1부터 시작해야 공백인 부분집합을 제외할 수 있을것이라고 생각했는데 아니였다.
        // i = 0 부터 시작해서 subArr_down 배열의 공집합 부분수열도 포함시켜서 subArr_up과 조합시켜야 한다.
        // S = 0인 경우, subArr_down, subArr_up의 조합에서 공집합끼리의 조합이 경우의 수에 포함되므로, 문제의 조건에 부적합하다
        // 따라서, S = 0 인 경우, cnt - 1 시켜서 출력하고, 그 외의 경우는 cnt 그대로 출력하면 된다.
        /*for(int i=0;i<subArr_down.length;i++) {
            binary_search_recursive(subArr_down[i],0, subArr_up.length-1);
        }*/

        System.out.println(S == 0 ? cnt - 1 : cnt);
    }

    static int upper_bound(int[] arr, int target) {
        int start = 0, end = arr.length;
        while(start < end) {
            int mid = (end + start) / 2;
            if(target >= arr[mid]) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return end;
    }

    static int lower_bound(int[] arr, int target) {
        int start = 0, end = arr.length;
        while(start < end) {
            int mid = (end + start) / 2;
            if(target <= arr[mid]) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return end;
    }

    // 시간초과 발생...
    // 반복문 + 재귀호출로 인한 시간초과가 발생한 것으로 생각한다....
    // 두 부분수열 합 배열들을 오름차순 정렬하고, target을 subArr_down의 원소들로 잡고 중복되는 요소가 있는지 검사한 후,
    // upper_bound, lower_bound를 통해 중복 개수를 구한 후, subArr_down과 subArr_up에서 구한 중복된 개수를 서로 곱하여
    // cnt에 누적하여 합한다.
    static void binary_search_recursive(int target, int start, int end) {
        if(start > end) return;

        int mid = (end + start) / 2;

        if(subArr_up[mid] + target == 0) {
            cnt++;
        }

        binary_search_recursive(target, start, mid - 1);
        binary_search_recursive(target, mid + 1, end);

    }

    static void fault_solve() {
        // 아래 코드의 문제점 : sum + target이 같을 때, lower 위치로 이동하여 end = mid - 1하는 것
        // 만약, upper 위치로 이동하여 start = mid + 1 후, S == sum + target을 만족하는 경우라면?
        // ㄴ 5 0
        //    0 0 0 0 0
        // lower만 고려하여 올바른 답을 구할 수 없다.
        // 따라서, lower + upper 양쪽 모두를 검사해야한다.
        // 아래 코드 처럼 반복문을 늘려도 해결방법은 아니다. -> lower , upper로만 고려하기 때문
        //          root
        //         /*   \*
        //       case1  case2
        //      /*   \  /  \*
        // * 방향으로만 진행하기 때문에 올바르지 않다.
        // 재귀형태로 이분트리 형식의 조건 검사가 필요하다.
        for(int i=1;i<subArr_down.length;i++) {
            int sum = subArr_down[i];

            int start = 0;
            int end = subArr_up.length - 1;
            int mid = 0;
            while(start <= end) {
                mid = (end + start) / 2;
                int target = subArr_up[mid];

                if(S <= sum + target) {
                    if(S == sum + target) {
                        cnt++;
                    }
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
        }

        for(int i=1;i<subArr_down.length;i++) {
            int sum = subArr_down[i];

            int start = 0;
            int end = subArr_up.length - 1;
            int mid = 0;
            while(start <= end) {
                mid = (end + start) / 2;
                int target = subArr_up[mid];

                if(S < sum + target) {
                    end = mid - 1;
                } else {
                    if(S == sum + target) {
                        cnt++;
                    }
                    start = mid + 1;
                }
            }
        }
    }

    //O(2^20 * 2^20) = O(2^40) - 시간초과
    static void all_search() {
        cnt = 0;
        for(int i=0;i<subArr_down.length;i++) {
            int sum = 0;
            int start = 0;
            if(i==0) {
                start = 1;
            }
            for(int j=start;j< subArr_up.length;j++) {
                sum = subArr_down[i] + subArr_up[j];
                if(sum == S) {
                    cnt++;
                }
            }
        }
        System.out.println(cnt);
    }

    static void make_subArr() {
        int size = N / 2;
        for(int i=0;i<(1<<size);i++) {
            if(Integer.bitCount(i) != 0) {
                //subArr_down 0~N/2
                for(int j=0;j<size;j++) {
                    if((i & (1 << j)) != 0) {
                        subArr_down[i] += arr[j];
                    }
                }
            }
        }

        for(int k=0;k<(1<<(N-size));k++) {
            if(Integer.bitCount(k) != 0) {
                //subArr_up N/2 ~ N
                for(int j=0;j<N-size;j++) {
                    if((k & (1 << j)) != 0) {
                        subArr_up[k] += arr[j + (N - size)];
                    }
                }
            }
        }

        Arrays.sort(subArr_up);
        Arrays.sort(subArr_down);
    }

    static void init_setting() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        arr = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int size = N % 2 == 0 ? N : N+1;
        subArr_down = new int[1 << N/2];
        subArr_up = new int[1 << size/2];

        /*int size = N / 2;
        subArr_down = new int[1 << size];
        subArr_up = new int[1 << N-size];*/

        Arrays.fill(subArr_up,0);
        Arrays.fill(subArr_down,0);
    }
}
