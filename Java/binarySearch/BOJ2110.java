package binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
공유기 설치 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	94632	35896	24754	38.300%
문제
도현이의 집 N개가 수직선 위에 있다. 각각의 집의 좌표는 x1, ..., xN이고, 집 여러개가 같은 좌표를 가지는 일은 없다.

도현이는 언제 어디서나 와이파이를 즐기기 위해서 집에 공유기 C개를 설치하려고 한다. 최대한 많은 곳에서 와이파이를 사용하려고 하기 때문에, 한 집에는 공유기를 하나만 설치할 수 있고, 가장 인접한 두 공유기 사이의 거리를 가능한 크게 하여 설치하려고 한다.

C개의 공유기를 N개의 집에 적당히 설치해서, 가장 인접한 두 공유기 사이의 거리를 최대로 하는 프로그램을 작성하시오.

입력
첫째 줄에 집의 개수 N (2 ≤ N ≤ 200,000)과 공유기의 개수 C (2 ≤ C ≤ N)이 하나 이상의 빈 칸을 사이에 두고 주어진다. 둘째 줄부터 N개의 줄에는 집의 좌표를 나타내는 xi (0 ≤ xi ≤ 1,000,000,000)가 한 줄에 하나씩 주어진다.

출력
첫째 줄에 가장 인접한 두 공유기 사이의 최대 거리를 출력한다.

예제 입력 1
5 3
1
2
8
4
9
예제 출력 1
3
힌트
공유기를 1, 4, 8 또는 1, 4, 9에 설치하면 가장 인접한 두 공유기 사이의 거리는 3이고, 이 거리보다 크게 공유기를 3개 설치할 수 없다.

출처
Olympiad > USA Computing Olympiad > 2004-2005 Season > USACO February 2005 Contest > Gold 3번

잘못된 데이터를 찾은 사람: fler9617
데이터를 추가한 사람: ahmg1216, djm03178, hwangyj9
알고리즘 분류
이분 탐색
매개 변수 탐색
 */
/*
알고리즘 핵심
이분 탐색
1. 입력으로 주어지는 집의 좌표를 오름차순 정렬한다.
2. h_0, h_n에 해당하는 집의 좌표의 차이를 최대 공유기 인접 거리라고 초기화한 후, 이분 탐색을 진행하여 최대 인집 거리를 찾는다.
3. h_0, h_n의 중간 길이를 최대 길이라고 가정하고 C개의 공유기가 해당 길이만큼 거리를 두며 설치가 가능한지 여부를 확인한다.
4. C개 이상이 가능하면, 최대 길이를 업데이트하고 왼쪽 길이를 중간값보다 크게 설정하여 최대 길이를 찾는다.
C개 이상이 불가능하면, 오른쪽 길이를 중간값보다 작게 설정하여 최대 길이를 찾는다.
 */
public class BOJ2110 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    private static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,C,ans,l_c,r_c;
        int[] houses;

        void solve() throws IOException {
            init_setting();

            binary_search();

            System.out.println(ans);
        }

        private void binary_search() {
            if(l_c > r_c) return;

            int m_c = (l_c + r_c) / 2;

            if(place_router(m_c)) l_c = m_c + 1;
            else r_c = m_c - 1;

            binary_search();
        }

        private boolean place_router(int m_c) {
            int p_h = houses[0];
            int c = C - 1;

            for(int i = 1; i < N; i++) {
                int c_h = houses[i];
                if(c_h - p_h < m_c) continue;
                else {
                    c--;
                    p_h = c_h;
                    if(c == 0) {
                        ans = Math.max(ans, m_c);
                        return true;
                    }
                }
            }
            return false;
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            C = Integer.parseInt(input[1]);

            houses = new int[N];

            for(int i = 0; i < N; i++) {
                houses[i] = Integer.parseInt(br.readLine());
            }

            houses = Arrays.stream(houses)
                    .sorted()
                    .toArray();

            ans = 0;

            l_c = 0;
            r_c = Arrays.stream(houses).max().getAsInt();
        }
    }
}
