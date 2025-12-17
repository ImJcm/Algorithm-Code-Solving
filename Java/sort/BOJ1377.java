package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

/*
버블 소트

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	24340	7964	6318	36.043%
문제
버블 소트 알고리즘을 다음과 같이 C++로 작성했다.

bool changed = false;
for (int i=1; i<=N+1; i++) {
    changed = false;
    for (int j=1; j<=N-i; j++) {
        if (A[j] > A[j+1]) {
            changed = true;
            swap(A[j], A[j+1]);
        }
    }
    if (changed == false) {
        cout << i << '\n';
        break;
    }
}
위 소스에서 N은 배열의 크기이고, A는 정렬해야 하는 배열이다. 배열은 A[1]부터 사용한다.

위와 같은 소스를 실행시켰을 때, 어떤 값이 출력되는지 구해보자.

입력
첫째 줄에 N이 주어진다. N은 500,000보다 작거나 같은 자연수이다. 둘째 줄부터 N개의 줄에 A[1]부터 A[N]까지 하나씩 주어진다. A에 들어있는 수는 1,000,000보다 작거나 같은 자연수 또는 0이다.

출력
정답을 출력한다.

예제 입력 1
5
10
1
5
2
3
예제 출력 1
3
예제 입력 2
5
1
3
5
7
9
예제 출력 2
1
출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: skynet
알고리즘 분류
정렬
 */
/*
알고리즘 핵심
정렬
1. 입력으로 주어지는 값을 <입력된 순서, 입력으로 주어지는 값>을 Pair 클래스로 저장한다.
2. Pair 클래스의 배열을 "입력으로 주어진 값"을 기준으로 오름차순 정렬한다.
3. 오름차순 정렬된 배열에서 0~N-1의 인덱스를 시작으로 해당 인덱스의 Pair값의 입력된 순서와 인덱스 값을 비교하여
이동에 필요한 횟수를 측정한다.
(왼쪽에서 오른쪽으로 이동하는 횟수는 한번의 정렬로 원래의 위치를 찾아가지만, 오른쪽에서 왼쪽으로의 이동은 한번의 정렬로
1번의 이동만 가능하기 때문에 버블 정렬로 정렬된 배열을 만드는 횟수는 오른쪽에서 왼쪽으로 이동한 횟수가 가장 많은 경우를 찾는 것이다.)
(중요한 점: Stable_sort vs UnStable_sort = 정렬되는 기준의 값이 같은 경우 순서를 변경하는지 아닌지 결정된다.
이 문제는 정렬되는 기준의 값이 같으면 기존의 순서를 유지하여 불필요한 정렬과정을 제외하는 것이 중요하다. 따라서, Stable_sort
로 정렬하여야 한다.)
힌트 참고 : https://www.acmicpc.net/board/view/121951, https://www.acmicpc.net/board/view/2098
 */
public class BOJ1377 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        class Pair {
            int index,value;

            Pair(int index, int value) {
                this.index = index;
                this.value = value;
            }
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,ans;
        ArrayList<Pair> A;

        private void solve() throws IOException {
            init_setting();
            compare();
            System.out.println(ans + 1);
        }

        private void compare() {
            A.sort(new Comparator<Pair>() {
                @Override
                public int compare(Pair o1, Pair o2) {
                    if(o1.value == o2.value) return o1.index - o2.index;
                    return o1.value - o2.value;
                }
            });

            for(int i = 0; i < N; i++) {
                ans = Math.max(ans, A.get(i).index - i);
            }
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            A = new ArrayList<>();

            for(int i = 0; i < N; i++) {
                A.add(new Pair(i, Integer.parseInt(br.readLine())));
            }
        }
    }

    public static class wrong_Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,ans;
        int[] A;

        private void solve() throws IOException {
            init_setting();
            //bubble_sort();
            //System.out.println(ans);

            //quick_sort(1,N);
            //System.out.println(ans);
        }

        /*
            시간 초과 + 틀린 코드
            퀵정렬의 분할 횟수를 결과 값이라고 생각했지만 시간초과가 발생
            아마도, worst case 에서 막히는 것이라고 생각한다. = avg : O(nlogn), worst : O(n^2)

            이미 정렬되어 있는 배열에 퀵정렬 적용 시, 올바른 정답을 구하지 못한다.
         */
        private void quick_sort(int left, int right) {
            if(left >= right) return;

            int pivot = A[left];
            int low = left + 1;
            int high = right;

            while(true) {
                while(low <= right && A[low] < pivot) low++;
                while(A[high] > pivot) high--;

                if(low >= high) break;
                swap(low,high);
            }

            swap(left,high);
            quick_sort(left,high - 1);
            quick_sort(high + 1,right);
            ans++;
        }

        /*
            시간 초과
         */
        private void bubble_sort() {
            boolean changed = false;

            for(int i = 1; i <= N + 1; i++) {
                changed = false;
                for(int j = 1; j <= N - i; j++) {
                    if(A[j] > A[j + 1]) {
                        changed = true;
                        swap(j,j+1);
                    }
                }
                if(!changed) {
                    ans = i;
                    break;
                }
            }
        }

        private void swap(int i1, int i2) {
            int tmp = A[i1];
            A[i1] = A[i2];
            A[i2] = tmp;
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            A = new int[N + 1];

            for(int i = 1; i <= N; i++) {
                A[i] = Integer.parseInt(br.readLine());
            }

            ans = 0;
        }
    }
}
