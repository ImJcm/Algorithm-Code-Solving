package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/*
가장 가까운 두 점

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	49330	9005	4796	16.896%
문제
2차원 평면상에 n개의 점이 주어졌을 때, 이 점들 중 가장 가까운 두 점을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 자연수 n(2 ≤ n ≤ 100,000)이 주어진다. 다음 n개의 줄에는 차례로 각 점의 x, y좌표가 주어진다. 각각의 좌표는 절댓값이 10,000을 넘지 않는 정수이다. 여러 점이 같은 좌표를 가질 수도 있다.

출력
첫째 줄에 가장 가까운 두 점의 거리의 제곱을 출력한다.

예제 입력 1
4
0 0
10 10
0 10
10 0
예제 출력 1
100
출처
데이터를 추가한 사람: august14, Diuven, dummymon, h0ngjun7, jang010505, Juno, pentagon03, pichulia
어색한 표현을 찾은 사람: jh05013
알고리즘 분류
기하학
스위핑
분할 정복
 */
/*
알고리즘 핵심
분할정복
1. x좌표를 기준으로 오름차순 정렬한 좌표에서 두 구역으로 나누어 길이가 짧은 두 점을 찾는다.
2. 두 점을 찾는 구역으로 두 점이 위치가 다음과 같다.
2-1. 두 점이 모두 왼쪽구역에 있는 경우
2-2. 두 점이 모두 오른쪽구역에 있는 경우
2-3. 각 점이 왼쪽, 오른쪽에 있는 경우
3. 2-1,2-2의 경우, 각 구역을 나누어 두 점중 길이가 짧은 길이를 구하고, 2-3의 경우 (s,e)구간의
짧은 길이보다 작은 경우의 두 점을 별도의 배열에 저장한다. (m(mid)의 중심선을 기준으로 d보다 작은 점)
(이때, 중심선이라고 지칭한 이유는 비교하는 대상의 점과 y좌표의 값이 같다고 가정하고 x축의 차이만을 고려하여 후보를 뽑는 것)
4. 2-3으로 저장한 배열 subps에서 y축을 기준으로 오름차순 정렬하여 d보다 작은 두 점을 찾고 짧은 길이 d로 업데이트한다.
5. 최종적으로 분할정복으로 반환되는 값이 가장 길이가 짧은 두 점 사이의 길이이다.

bruteforce 방식이 불가능한 이유 : N <= 10,000으로 작은 수의 N이라면 가능하지만, 10^5인 경우 O(N^2)인 경우 시간초과 발생할 것으로 예상
sweeping 방식의 경우, 짧은 길이의 두 점과 비교할 점을 포함하여 세개의 점간의 길이만을 고려하여 올바른 길이를 구하는 것이 불가능했다.
이때, 세개의 점이 아닌 이전의 점까지 모두 비교한다면 bruteforce와 방식이 같으므로 올바른 sweeping 방식으로 답을 구하는 방법은 모르겠다.

그래서 분할정복을 사용한 정답코드를 참고하여 구현하였다. - https://steady-coding.tistory.com/215#google_vignette

위의 로직처럼 중요한 점은 두 구역으로 분할하는 과정에서 두 점의 위치이다.
두 점의 위치가 왼쪽에 2개 또는 오른쪽에 2개 또는 왼쪽,오른쪽 각각 1개씩 있는 경우를 모두 고려하여 (s,e) 구간에서의 짧은 두점을 찾아야한다.
 */
public class BOJ2261 {
    static class pos {
        int x,y;

        pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static ArrayList<pos> ps;

    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();

    }

    public static class Solve {
        int min_dist;
        pos s,e;

        private void solve() throws IOException {
            init_setting();

            //bruteforce();
            //sweeping();
            min_dist = divideConquer(0,N -1);

            System.out.println(min_dist);
        }

        /*
            분할 정복 로직
            1. 길이가 짧은 두 점이 모두 왼쪽 구역에 존재하는 경우
            2. 모두 오른쪽에 존재하는 경우
            3. 각각 왼쪽과 오른쪽에 존재하는 경우
            세가지 경우를 고려해야 가장 짧은 두 점을 구할 수 있다.
         */
        private int divideConquer(int s, int e) {
            int l = e - s + 1;  // (s,e) 구간의 점의 개수
            int r = Integer.MAX_VALUE;

            if(l <= 3) {
                for(int i = s; i < e; i++) {
                    for(int j = i + 1; j <= e; j++) {
                        int d = pow_distance_twopoint(ps.get(i),ps.get(j));
                        r = Math.min(r, d);
                    }
                }
                return r;
            }

            int m = (s + e) / 2;
            r = Math.min(divideConquer(s,m),divideConquer(m + 1,e));

            ArrayList<pos> subps = new ArrayList<>();

            for(int i = s; i <= e; i++) {
                int sd = (int) Math.pow(Math.abs(ps.get(m).x - ps.get(i).x),2);

                if(r > sd) subps.add(ps.get(i));
            }

            Collections.sort(subps, (p1,p2) -> p1.y - p2.y);

            for(int i = 0; i < subps.size() - 1; i++) {
                for(int j = i + 1; j < subps.size(); j++) {
                    int sd = (int) Math.pow(Math.abs(subps.get(j).y - subps.get(i).y),2);

                    if(sd < r) r = Math.min(r, pow_distance_twopoint(subps.get(i),subps.get(j)));
                    else break;
                }
            }
            return r;
        }

        /*
            오답 : 틀린 로직 (x축을 기준으로 가까운 점이 최소 거리임을 만족하지 못한다.)
            x좌표를 기준으로 오름차순 정렬하여 왼쪽부터 끝지점까지 세개의 점을 고르고 가장 작은 길이를 갖는 두 점을 s,e로 지정하여
            가장 가까운 두 점의 거리를 측정하는 로직
         */
        private void sweeping() {
            choice_min_dist_point(ps.get(0),ps.get(1));

            for(int i = 2; i < N; i++) {
                pos c = ps.get(i);

                choice_min_dist_point(s,c);
                choice_min_dist_point(e,c);
            }
        }

        private void choice_min_dist_point(pos p1, pos p2) {
            int d = pow_distance_twopoint(p1,p2);

            if(d < min_dist) {
                min_dist = d;
                s = p1;
                e = p2;
            }
        }

        /*
            오답 예상 : 10^(5 * 2) = O(N^2)
         */
        private void bruteforce() {
            for(int i = 0; i < N - 1; i++) {
                for(int j = i + 1; j < N; j++) {
                    int dist = pow_distance_twopoint(ps.get(i), ps.get(j));

                    if(dist < min_dist) min_dist = dist;
                }
            }
        }

        private int pow_distance_twopoint(pos p1, pos p2) {
            int dx = Math.abs(p1.x - p2.x);
            int dy = Math.abs(p1.y - p2.y);

            int r = (int) Math.pow(dx,2) + (int) Math.pow(dy,2);

            return r;
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            ps = new ArrayList<>();

            for(int i = 0; i < N; i++) {
                String[] input = br.readLine().split(" ");

                int x = Integer.parseInt(input[0]);
                int y = Integer.parseInt(input[1]);

                ps.add(new pos(x,y));
            }

            Collections.sort(ps, (p1,p2) -> p1.x - p2.x);

            min_dist = Integer.MAX_VALUE;
        }
    }

}
