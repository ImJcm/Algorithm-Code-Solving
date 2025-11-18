package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
public class BOJ1377 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,ans;
        int[] A;

        private void solve() throws IOException {
            init_setting();
            bubble_sort();
            System.out.println(ans);
        }

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
