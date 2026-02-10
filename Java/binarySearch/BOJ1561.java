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
/*
알고리즘 핵심
이분 탐색 (매개변수 이분탐색)
1. 인원 수, 놀이기구 수, 놀이기구의 운행시간이 주어졌을 때, 마지막 인원이 타는 기구의 번호를 구하기 위해서는 모든 인원이 기구에 타야하고 어떤 기구인지는
추후에 결정할 수 있으므로 우선 총 시간에 초점을 두었다.
2. 모든 인원이 탈 수 있는 시간을 이분탐색의 기준으로 두었을 때, 총 걸린 시간을 구할 수 있었다.
3. 모든 인원이 탈 수 있는 총 시간을 구할 때 이분탐색의 과정에서 가장 마지막으로 수행된 로직에서 어떤 놀이기구에 몇명의 인원이 탔는지를 저장하여 N명을 만족하는
경우와 만족하지 못하는 경우의 각 놀이기구의 수용 인원 수의 차이를 비교하여 앞의 놀이기구를 시작으로 각 인원의 차이만큼 인원을 배치할 때 마지막으로 두 경우가
같아지는 경우의 놀이기구가 마지막 인원이 타는 기구이다.

처음 접근 방법은 총 인원이 놀이기구에 탈 수 있는 시간에 초점을 두어서 올바른 접근이였다. 하지만 총 인원이 탈 수 있는 최초의 시간만 고려한 나머지
마지막 인원이 탈 수 있는 기구의 번호를 측정하기에는 어려웠다.
기구의 번호를 알기 위해서 총 걸린 시간과 놀이기구가 처음으로 모두 비워지는 시간을 측정하여 총 걸린 시간에 나눈 나머지에서 M의 기구를 순차적으로 배치하여
정답을 추출하려고 했지만 시간초과가 발생하였다.

추후 방법으로 최초로 모든 인원이 탈 수 있는 시간에 도달하기 직전의 불가능한 경우의 놀이기구에 탄 인원의 배치와 모든 인원이 탈 수 있는 배치를 비교하여
두 경우의 인원 수의 차이만큼 각 놀이기구에 수용할 수 있는 인원의 차이를 채우는 경우 결국 같아지는 경우가 마지막 인원이 타는 놀이기구 위치라는 점을 생각하게 되었다.
 */
public class BOJ1561 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    private static class Solve {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N,M;
        int[] amusement_rides;
        long ans,l,r,diff;
        long[] low,high;

        void solve() throws IOException {
            init_setting();

            binary_search();

            check_ride_number();

            System.out.println(ans);
        }

        private void check_ride_number() {
            diff = N - diff;

            for(int i = 0; i < M; i++) {
                diff -= (high[i] - low[i]);
                if(diff == 0) {
                    ans = i + 1;
                    break;
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
            long cnt = ride_person_cnt_in_time(t);

            if (cnt >= N) {
                place_on_the_ride(high,t);
                return true;
            }
            else {
                diff = cnt;
                place_on_the_ride(low,t);
                return false;
            }
        }

        private long ride_person_cnt_in_time(long t) {
            long cnt = M;

            for(int i = 0; i < M; i++) {
                cnt += (t / amusement_rides[i]);
            }

            return cnt;
        }

        private void place_on_the_ride(long[] a_ride, long t) {
            for(int i = 0; i < M; i++) {
                a_ride[i] = (t / amusement_rides[i]) + 1;
            }
        }



        private void init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            M = Integer.parseInt(input[1]);

            amusement_rides = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            low = new long[M];
            high = new long[M];

            ans = 0;
            l = 0;
            r = 2_000_000_000L * 30;
        }
    }

    public static class TimeOver_Solve {
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

        private void what_is_the_number_of_the_ride_that_the_last_person_rode() {
            int i = 0, n = 0;
            long one_cycle_t = lcms();
            long remain_time = ans % one_cycle_t;
            long remain_person = N % ride_person_cnt_in_time(one_cycle_t);

            for(long l = 0; l <= remain_time; l++) {
                for(i = 0; i < M; i++) {
                    if(l % amusement_rides[i] == 0) {
                        ans = i + 1;
                        n++;
                    }
                    if(n == remain_person) return;
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
            long cnt = ride_person_cnt_in_time(t);

            if(cnt >= N) return true;
            else return false;
        }

        private long ride_person_cnt_in_time(long t) {
            long cnt = M;

            for(int i = 0; i < M; i++) {
                cnt += (t / amusement_rides[i]);
            }

            return cnt;
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

