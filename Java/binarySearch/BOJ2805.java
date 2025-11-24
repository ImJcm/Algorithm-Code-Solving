package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
나무 자르기 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	256695	78537	48916	27.257%
문제
상근이는 나무 M미터가 필요하다. 근처에 나무를 구입할 곳이 모두 망해버렸기 때문에, 정부에 벌목 허가를 요청했다. 정부는 상근이네 집 근처의 나무 한 줄에 대한 벌목 허가를 내주었고, 상근이는 새로 구입한 목재절단기를 이용해서 나무를 구할것이다.

목재절단기는 다음과 같이 동작한다. 먼저, 상근이는 절단기에 높이 H를 지정해야 한다. 높이를 지정하면 톱날이 땅으로부터 H미터 위로 올라간다. 그 다음, 한 줄에 연속해있는 나무를 모두 절단해버린다. 따라서, 높이가 H보다 큰 나무는 H 위의 부분이 잘릴 것이고, 낮은 나무는 잘리지 않을 것이다. 예를 들어, 한 줄에 연속해있는 나무의 높이가 20, 15, 10, 17이라고 하자. 상근이가 높이를 15로 지정했다면, 나무를 자른 뒤의 높이는 15, 15, 10, 15가 될 것이고, 상근이는 길이가 5인 나무와 2인 나무를 들고 집에 갈 것이다. (총 7미터를 집에 들고 간다) 절단기에 설정할 수 있는 높이는 양의 정수 또는 0이다.

상근이는 환경에 매우 관심이 많기 때문에, 나무를 필요한 만큼만 집으로 가져가려고 한다. 이때, 적어도 M미터의 나무를 집에 가져가기 위해서 절단기에 설정할 수 있는 높이의 최댓값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 나무의 수 N과 상근이가 집으로 가져가려고 하는 나무의 길이 M이 주어진다. (1 ≤ N ≤ 1,000,000, 1 ≤ M ≤ 2,000,000,000)

둘째 줄에는 나무의 높이가 주어진다. 나무의 높이의 합은 항상 M보다 크거나 같기 때문에, 상근이는 집에 필요한 나무를 항상 가져갈 수 있다. 높이는 1,000,000,000보다 작거나 같은 양의 정수 또는 0이다.

출력
적어도 M미터의 나무를 집에 가져가기 위해서 절단기에 설정할 수 있는 높이의 최댓값을 출력한다.

예제 입력 1
4 7
20 15 10 17
예제 출력 1
15
예제 입력 2
5 20
4 42 40 26 46
예제 출력 2
36
출처
Contest > Croatian Open Competition in Informatics > COCI 2011/2012 > Contest #5 2번

문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: jongseo_park
잘못된 데이터를 찾은 사람: tncks0121
데이터를 추가한 사람: csehydrogen, jh05013, upple1, vyu
빠진 조건을 찾은 사람: rdd6584
알고리즘 분류
이분 탐색
매개 변수 탐색
 */
/*
알고리즘 핵심
이분 탐색
1. 최대 높이를 구하기 위해 최소 높이 0, 입력으로 주어지는 나무의 높이의 최대값을 좌우측으로 기준하여 이분탐색을 진행한다.
2. 이분 탐색으로 중간 높이로 모든 나무를 잘랐을 때 나오는 나무의 길이를 M과 비교한다.
3. M보다 큰 경우, 최대 높이 여부를 검사하고, L_H(최소 높이)를 M_H보다 큰 값으로 설정하여 이분탐색을 반복한다.
M보다 작은 경우, H_H(최대 높이)를 M_H보다 작은 값으로 설정하여 이분 탐색을 진행한다.
4. 위 과정에서 이분 탐색이 L_H가 H_H보다 커지는 경우 종료하고 조건을 만족하는 자를 수 있는 나무의 높이를 출력한다.
 */
public class BOJ2805 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,M;
        long H,L_H,H_H;
        int[] trees;

        private void solve() throws IOException {
            init_setting();

            binary_search();

            System.out.println(H);
        }

        private void binary_search() {
            if(L_H > H_H) return;

            long M_H = (L_H + H_H) / 2;

            if(cut_tree(M_H)) L_H = M_H + 1;
            else H_H = M_H - 1;

            binary_search();
        }

        private boolean cut_tree(long M_H) {
            long t = 0;
            for(int i = 0; i < N; i++) {
                long r = 0;
                if(M_H < trees[i]) r = trees[i] - M_H;

                t += r;
            }

            if(t >= M) {
                H = Math.max(H,M_H);
                return true;
            }
            return false;
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            M = Integer.parseInt(input[1]);

            trees = new int[N];

            trees = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            H = 0;
            L_H = 0;
            H_H = Arrays.stream(trees).max().getAsInt();
        }
    }
}
