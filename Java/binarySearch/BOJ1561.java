package binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
놀이 공원 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	14139	4141	2829	27.913%
문제
N명의 아이들이 한 줄로 줄을 서서 놀이공원에서 1인승 놀이기구를 기다리고 있다. 이 놀이공원에는 총 M종류의 1인승 놀이기구가 있으며, 1번부터 M번까지 번호가 매겨져 있다.

모든 놀이기구는 각각 운행 시간이 정해져 있어서, 운행 시간이 지나면 탑승하고 있던 아이는 내리게 된다. 놀이 기구가 비어 있으면 현재 줄에서 가장 앞에 서 있는 아이가 빈 놀이기구에 탑승한다. 만일 여러 개의 놀이기구가 동시에 비어 있으면, 더 작은 번호가 적혀 있는 놀이기구를 먼저 탑승한다고 한다.

놀이기구가 모두 비어 있는 상태에서 첫 번째 아이가 놀이기구에 탑승한다고 할 때, 줄의 마지막 아이가 타게 되는 놀이기구의 번호를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N(1 ≤ N ≤ 2,000,000,000)과 M(1 ≤ M ≤ 10,000)이 빈칸을 사이에 두고 주어진다. 둘째 줄에는 각 놀이기구의 운행 시간을 나타내는 M개의 자연수가 순서대로 주어진다. 운행 시간은 1 이상 30 이하의 자연수이며, 단위는 분이다.

출력
첫째 줄에 마지막 아이가 타게 되는 놀이기구의 번호를 출력한다.

예제 입력 1
3 5
7 8 9 7 8
예제 출력 1
3
예제 입력 2
7 2
3 2
예제 출력 2
2
예제 입력 3
22 5
1 2 3 4 5
예제 출력 3
4
출처
Olympiad > Croatian Highschool Competitions in Informatics > 2003 > Regional Competition - Seniors 3번

문제를 번역한 사람: author5
문제의 오타를 찾은 사람: djm03178, psi0613
잘못된 조건을 찾은 사람: doju
데이터를 추가한 사람: rhdqor213
알고리즘 분류
이분 탐색
매개 변수 탐색
 */
public class BOJ1561 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    public static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,M;
        int[] amusement_rides;
        long ans,l,r;

        private void solve() throws IOException {
            init_setting();

            binary_search();

            what_is_the_number_of_the_ride_that_the_last_person_rode();

            System.out.println(ans);
        }

        /*
            시간 초과 발생 가능
            Ex) Input
            1987654321 2
            15 14
         */
        private void what_is_the_number_of_the_ride_that_the_last_person_rode() {
            long n = 0;

            for(long t = 0; t <= ans; t++) {
                for(int v = 0; v < M; v++) {
                    if(t % amusement_rides[v] == 0) {
                        n++;
                    }
                    if(n == N) {
                        ans = v + 1;
                        return;
                    }
                }
            }
        }

        private void binary_search() {
            if(l > r) return;

            long m = (l + r) / 2;

            if(is_everyone_aboard(m)) {
                r = m - 1;
                ans = m;
            } else l = m + 1;

            binary_search();
        }

        private boolean is_everyone_aboard(long t) {
            long cnt = M;

            for(int i = 0; i < M; i++) {
                cnt += (t / amusement_rides[i]);
            }

            if(cnt >= N) return true;
            else return false;
        }

        private long lcms() {
            if(M == 1) {
                return amusement_rides[0];
            } else {
                long gcd = gcd(amusement_rides[0],amusement_rides[1]);
                long lcm = ((long) amusement_rides[0] * amusement_rides[1]) / gcd;

                for(int i = 2; i < M; i++) {
                    gcd = gcd(lcm, amusement_rides[i]);
                    lcm = (lcm * amusement_rides[i]) / gcd;
                }

                return lcm;
            }
        }

        private long gcd(long l1, long l2) {
            return (l2 > 0) ? gcd(l2, l1 % l2) : l1;
        }

        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            M = Integer.parseInt(input[1]);

            amusement_rides = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            ans = 0;
            l = 0;
            r = 2_000_000_000L * 30;
        }
    }
}

