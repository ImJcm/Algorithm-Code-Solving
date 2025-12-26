package binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
K번째 수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	55821	21324	15676	38.709%
문제
세준이는 크기가 N×N인 배열 A를 만들었다. 배열에 들어있는 수 A[i][j] = i×j 이다. 이 수를 일차원 배열 B에 넣으면 B의 크기는 N×N이 된다. B를 오름차순 정렬했을 때, B[k]를 구해보자.

배열 A와 B의 인덱스는 1부터 시작한다.

입력
첫째 줄에 배열의 크기 N이 주어진다. N은 105보다 작거나 같은 자연수이다. 둘째 줄에 k가 주어진다. k는 min(109, N2)보다 작거나 같은 자연수이다.

출력
B[k]를 출력한다.

예제 입력 1
3
7
예제 출력 1
6
출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: jarycoco
빠진 조건을 찾은 사람: mystika
알고리즘 분류
이분 탐색
매개 변수 탐색
 */
public class BOJ1300 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,K;
        //long[][] A; // 10^5 x 10^5의 최대 크기의 배열은 OutOfMemoryError

        private void solve() throws IOException {
            init_setting();
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());
            K = Integer.parseInt(br.readLine());

        }
    }
}
1 2  3  4  5  6  7
2 4  6  8  10 12 14
3 6  9  12 15 18 21
4 8  12 16 20 24 28
5 10 15 20 25 30 35
6 12 18 24 30 36 42
7 14 21 28 35 42 49