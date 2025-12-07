package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
기타 레슨

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	48774	17693	12313	34.432%
문제
강토는 자신의 기타 강의 동영상을 블루레이로 만들어 판매하려고 한다. 블루레이에는 총 N개의 강의가 들어가는데, 블루레이를 녹화할 때, 강의의 순서가 바뀌면 안 된다. 순서가 뒤바뀌는 경우에는 강의의 흐름이 끊겨, 학생들이 대혼란에 빠질 수 있기 때문이다. 즉, i번 강의와 j번 강의를 같은 블루레이에 녹화하려면 i와 j 사이의 모든 강의도 같은 블루레이에 녹화해야 한다.

강토는 이 블루레이가 얼마나 팔릴지 아직 알 수 없기 때문에, 블루레이의 개수를 가급적 줄이려고 한다. 오랜 고민 끝에 강토는 M개의 블루레이에 모든 기타 강의 동영상을 녹화하기로 했다. 이때, 블루레이의 크기(녹화 가능한 길이)를 최소로 하려고 한다. 단, M개의 블루레이는 모두 같은 크기이어야 한다.

강토의 각 강의의 길이가 분 단위(자연수)로 주어진다. 이때, 가능한 블루레이의 크기 중 최소를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 강의의 수 N (1 ≤ N ≤ 100,000)과 M (1 ≤ M ≤ N)이 주어진다. 다음 줄에는 강토의 기타 강의의 길이가 강의 순서대로 분 단위로(자연수)로 주어진다. 각 강의의 길이는 10,000분을 넘지 않는다.

출력
첫째 줄에 가능한 블루레이 크기중 최소를 출력한다.

예제 입력 1
9 3
1 2 3 4 5 6 7 8 9
예제 출력 1
17
힌트
강의는 총 9개이고, 블루레이는 총 3개 가지고 있다.

1번 블루레이에 1, 2, 3, 4, 5, 2번 블루레이에 6, 7, 3번 블루레이에 8, 9 를 넣으면 각 블루레이의 크기는 15, 13, 17이 된다. 블루레이의 크기는 모두 같아야 하기 때문에, 블루레이의 크기는 17이 된다. 17보다 더 작은 크기를 가지는 블루레이를 만들 수 없다.

출처
잘못된 데이터를 찾은 사람: tncks0121
데이터를 추가한 사람: muzigae, sksdong1
알고리즘 분류
이분 탐색
매개 변수 탐색
 */
/*
알고리즘 핵심
이분 탐색
1. 블루레이의 용량을 이분 탐색을 통해 정한다.
(초기 블루레이 최소 용량을 1, 최대 용량을 N개의 블루레이 수 * 한 블루레이의 최대 용량 = 1,000,000,000 (10,000 * 100,000)
2. 정해진 블루레이의 용량으로 N개의 음원을 블루레이에 담는 과정에서 l,r의 범위를 정한다.
2-1. 한 음원의 용량이 블루레이 용량보다 큰 경우, 블루레이 용량의 범위를 중간값보다 크게 정한다.
2-2. 0부터 N-1까지 음원의 용량을 누적하여 블루레이 용량보다 커지면, 이전까지의 음원을 블루레이에 담는 것으로 블루레이 카운트를 증가시킨다.
2-3. 0~N까지 모두 진행 후, 남은 음원의 용량이 블루레이 용량보다 큰 경우, 블루레이 용량의 범위를 중간값보다 크게 정한다.
2-4. 2-3에서 남은 음원의 용량이 블루레이 용량보다 작은 경우 블루레이 카운트를 증가시킨다.
3. 블루레이 용량이 M보다 큰 경우, 블루레이 용량의 범위를 중간값보다 크게 정한다.
블루레이 용량이 M보다 작거나 같은 경우, 용량의 최소값을 ans에 업데이트하고, 블루레이 용량의 범위를 중간값보다 작게 정한다.
 */
public class BOJ2343 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,M,ans,l,r;
        int[] lecture;

        private void solve() throws IOException {
            init_setting();

            binary_search();

            System.out.println(ans);
        }

        private void binary_search() {
            if(l > r) return;

            int m = (l + r) / 2;

            if(blu_ray(m)) r = m - 1;
            else l = m + 1;

            binary_search();
        }

        private boolean blu_ray(int m) {
            int size = 0;
            int cnt = 0;

            for(int i = 0; i < N; i++) {
                if(m < lecture[i]) return false;

                size += lecture[i];

                if(i != N - 1 && size + lecture[i + 1] > m) {
                    cnt++;
                    size = 0;
                }
            }

            if(size > m) return false;
            else if(size != 0) cnt++;

            if(cnt > M) return false;
            else {
                ans = Math.min(ans,m);
                return true;
            }
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            M = Integer.parseInt(input[1]);

            lecture = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            l = 1;
            r = 1_000_000_000;

            ans = Integer.MAX_VALUE;
        }
    }
}
