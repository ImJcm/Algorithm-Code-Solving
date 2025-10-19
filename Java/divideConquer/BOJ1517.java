package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

/*
버블 소트

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	39727	11626	8028	31.434%
문제
N개의 수로 이루어진 수열 A[1], A[2], …, A[N]이 있다. 이 수열에 대해서 버블 소트를 수행할 때, Swap이 총 몇 번 발생하는지 알아내는 프로그램을 작성하시오.

버블 소트는 서로 인접해 있는 두 수를 바꿔가며 정렬하는 방법이다. 예를 들어 수열이 3 2 1 이었다고 하자. 이 경우에는 인접해 있는 3, 2가 바뀌어야 하므로 2 3 1 이 된다. 다음으로는 3, 1이 바뀌어야 하므로 2 1 3 이 된다. 다음에는 2, 1이 바뀌어야 하므로 1 2 3 이 된다. 그러면 더 이상 바꿔야 할 경우가 없으므로 정렬이 완료된다.

입력
첫째 줄에 N(1 ≤ N ≤ 500,000)이 주어진다. 다음 줄에는 N개의 정수로 A[1], A[2], …, A[N]이 주어진다. 각각의 A[i]는 0 ≤ |A[i]| ≤ 1,000,000,000의 범위에 들어있다.

출력
첫째 줄에 Swap 횟수를 출력한다

예제 입력 1
3
3 2 1
예제 출력 1
3
출처
데이터를 추가한 사람: akaishuichi, hororo12345, lambda, osier_star
알고리즘 분류
자료 구조
정렬
세그먼트 트리
분할 정복
 */
/*
알고리즘 핵심
분할 정복 (합병 정렬 or 세그먼트 트리)
I. 합병 정렬
1. 입력으로 주어지는 배열을 반으로 나누어(divide) 각 서브트리의 값을 비교하여 오름차순 정렬된 상태로 merge(합병)한다.
2. 이때, merge하는 과정에서 우측 서브트리의 값이 좌측 서브트리의 값보다 작은 경우, Swap을 의미한다.
따라서, 좌측 서브트리의 남은 요소의 개수만큼 ans에 누적하여 더한다.

ex) 4 2 5 3 1
divide (4,2,5) (3,1)
divide (4,2) (5) (3,1)
divide (4) (2) (5) (3) (1)
merge  (2,4) (5) (1,3)      -> 2가 4보다 작으므로 1+swap / 1이 3보다 작으므로 1+swap
merge  (2,4,5) (1,3)
merge  (1,2,3,4,5)          -> 1이 2,4,5보다 작으므로 3+swap / 3이 4,5보다 작으므로 2+swap
따라서, swap 횟수는 7번 이다.

II. 세그먼트 트리
세그먼트 트리는 배열이 주어졌을 때, 특정 구간의 합, 최댓값, 최소값 등을 구할 때 사용하는 트리이다.
세그먼트 트리의 구간합을 이용하여 i번째 값이 현재 위치에서 자신보다 앞의 구간([i+1 ~ N-1])에 작은 수의 갯수로 swap 횟수를 카운트할 수 있는 점을 이용한 방식이다.
1. 입력으로 주어진 A배열과 A를 오름차순 정렬한 배열 sorted를 준비한다.
2. sorted의 0~N-1의 범위에서 0을 시작으로 하여 A[i]값이 A의 배열에서 위치(idx)에서 [idx+1 ~ N-1] 구간에서 자신보다 작은 수 만큼 swap이 이루어지므로
[idx + 1 ~ N - 1] 구간의 값을 +1 카운팅한다.
3. swap 갯수를 카운팅 한 후, 세그먼트 트리를 업데이트한다.

ex) 예시는 그림으로 보는 것이 이해하기 수월하다.
https://loosie.tistory.com/328#%EC%84%B8%EA%B7%B8%EB%A8%BC%ED%8A%B8_%ED%8A%B8%EB%A6%AC_

문제의 처음 접근 방법을 분할 정복을 이용하며 버블 정렬을 통한 swap 갯수를 확인할 수 있도록 A[i]를 기준으로 우측과 좌측의 값을 비교하여
스왑하는 형태로 구성하였다.
하지만, 시간초과가 발생하였고, 힌트를 참고하여 풀어보려고 하였으나 세그먼트 트리의 개념을 보아도 도저히 접근 방법을 몰랐다.
그래서 정답코드와 풀이 접근 방법을 읽어보고 합병 정렬과 세그먼트 트리를 이용한 방법이 있다는 것을 알게되었다.
세그먼트 트리 - https://loosie.tistory.com/328#%EC%84%B8%EA%B7%B8%EB%A8%BC%ED%8A%B8_%ED%8A%B8%EB%A6%AC_
합병 정렬 - https://gmlwjd9405.github.io/2018/05/08/algorithm-merge-sort.html
 */
public class BOJ1517 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] A;
    static int[] sorted;  // merge sort
    static HashMap<Integer,Integer> pos;
    static long ans;

    public static void main(String[] args) throws IOException {
        /*
        // bubble sort - divideConquer 방식
            bubble_sort_divide_conquer task = new bubble_sort_divide_conquer();
            task.solve();
         */

        /*
        // 합병 정렬(merge sort) 방식
            merge_sort_solve task = new merge_sort_solve();
            task.solve();
         */

        // segment tree 방식



    }

    public static class merge_sort_solve {
        private void solve() throws IOException {
            init_setting();

            merge_sort(A,0,N - 1);

            System.out.println(ans);
        }

        private void merge_sort(int[] a, int l, int r) {
            int m;

            if(l < r) {
                m = (l + r) / 2;
                merge_sort(a,l,m);            // divide
                merge_sort(a,m + 1,r);      // divide
                merge(a,l,m,r);               // combine (conquer)
            }
        }

        private void merge(int[] a, int l, int m, int r) {
            int i,j,k,o,p;
            i = l;
            j = m + 1;
            k = l;
            p = m + 1;

        /*
            - while (i <= m || j <= r)
                -> 두 부분 배열 중 하나라도 남아 있으면 계속 반복
            - if (j > r || (i <= m && a[i] <= a[j]))
                -> 오른쪽 배열이 다 소진되었거나, 왼쪽 값이 더 작을 때 왼쪽 값을 복사
                -> ||,&& 논리연산자의 단락 평가로 j가 right를 넘어가는 경우, 오른쪽 조건을 검사하지 않기 때문에
                    ArrayIndexOutOfBoundException이 발생하지 않는다.
            - else
                -> 그 외에는 오른쪽 값을 복사
         */
            while (i <= m || j <= r) {
                if (j > r || (i <= m && a[i] <= a[j])) {
                    sorted[k++] = a[i++];
                } else {
                    sorted[k++] = a[j++];
                    ans += (p - i);      // 이 부분이 Bubble Sort 시, Swap 해당
                    // 오른쪽 서브배열에서 왼쪽 서브배열의 남은 값만큼 이동하므로 (m + 1 - i) 만큼 swap 개수를 의미
                }
            }

            for(o = l; o <= r; o++) {
                a[o] = sorted[o];
            }
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());
            A = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            ans = 0;

            sorted = new int[N];
        }
    }

    /*
        3% 틀린코드 : 시간 초과
     */
    public static class bubble_sort_divide_conquer {
        private void solve() throws IOException {
            init_setting();

            for(int i = 0; i < N; i++) {
                bubble_sort_dc(i);
            }

            System.out.println(ans);
        }


        private void bubble_sort_dc(int i) {
            if(i + 1 < N && A[i] > A[i + 1]) {
                ans += 1;
                swap(i,i + 1);
                if(i + 1 == N - 1) bubble_sort_dc(i);
                bubble_sort_dc(i + 1);
            }

            if(i - 1 >= 0 && A[i - 1] > A[i]) {
                ans += 1;
                swap(i - 1,i);
                if(i - 1 == 0) bubble_sort_dc(i);
                bubble_sort_dc(i - 1);
            }
        }

        private void swap(int i, int i1) {
            int temp = A[i];

            A[i] = A[i1];
            A[i1] = temp;
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());
            A = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            ans = 0;

            sorted = new int[N];
        }
    }
}
