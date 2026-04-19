package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/*
당근 훔쳐 먹기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	681	313	239	42.679%
문제
꽉꽉나라의 농부 오리는 아무것도 심어져 있지 않은 텃밭을 하나 가지고 있다. 오리는 그 텃밭에 N종류의 당근을 하나씩 심고 T일 동안 재배할 예정이다.

당근 i(1 ≤ i ≤ N)는 처음에는 wi의 맛을 가지고 있고, 각 당근 i에 사용할 pi만큼 맛을 증가시켜주는 영양제가 당근 종류별로 T개씩 준비되어 있다. 오리는 당근이 본래의 맛보다 훨씬 맛있어지기를 바라기 때문에 pi는 항상 wi이상의 값을 가지도록 준비했다. 잠이 많은 오리는 매일 오전에만 텃밭에 나와 당근들을 관리한다. 오리는 각 당근 i에 대해 당근 i가 자리에 없다면 당근 i를 심고, 그렇지 않다면 당근 i에 영양제를 하나 준다.



꽉꽉나라에 놀러 온 토끼는 오리가 오전에만 당근을 관리한다는 사실을 알고 오리의 텃밭을 찾아가 당근을 훔쳐 먹을 계획을 세웠다. 토끼는 위장이 작아서 하루에 최대 하나의 당근을 먹을 수 있고 당근을 먹지 않을 수도 있다. 또한 당근 하나를 먹기로 마음먹으면 남기지 않고 먹으며, 오리와 마주치지 않기 위해 오후에만 텃밭에 찾아간다. 토끼는 자신이 먹은 당근의 맛의 합을 최대로 하고 싶어 한다.

T일 동안 토끼가 먹을 수 있는 당근의 맛의 합의 최댓값을 구해보자.

입력
첫 번째 줄에 N(1 ≤ N ≤ 200,000)과 T(N ≤ T ≤ 100,000,000)가 공백으로 구분되어 주어진다. 오리는 당근의 맛을 충분히 높이기 위해 항상 N이상인 T일 동안 재배한다.

다음 N개의 줄에 걸쳐서 i+1번째 줄에 당근 i의 wi와 pi가 공백으로 구분되어 주어진다. (1 ≤ i ≤ N, 1 ≤ wi ≤ pi ≤ 100, wi와 pi는 정수)

출력
첫 번째 줄에 토끼가 T일 동안 먹을 수 있는 당근의 맛의 합의 최댓값을 출력한다. 정답은 32비트 정수 변수(int) 범위를 초과할 수 있기 때문에 64비트 정수 변수(C/C++ : long long, JAVA : long)를 사용해야 한다.

예제 입력 1
2 2
3 4
1 2
예제 출력 1
8
1일차 오전에 오리는 당근 1과 2를 심는다. 그날 오후 토끼는 1만큼 맛있는 당근 2를 뽑아먹는다.

2일차 오전에 오리는 당근 2를 심고 당근 1에 영양제를 하나 준다. 그날 오후 토끼는 7만큼 맛있는 당근 1을 뽑아먹는다.

예제 입력 2
3 5
1 3
2 9
3 7
예제 출력 2
69
예제 입력 3
6 10
1 10
2 9
3 8
3 5
6 6
5 6
예제 출력 3
324
 */
/*
    핵심 알고리즘 : 그리디 알고리즘 + 정렬

    최대 당근의 맛의 합은 영양제의 값을 기준으로 오름차순 정렬 뒤, 날이 지나면 지날수록 당근에 영양제를 투여하기 때문에
    가장 마지막 날에 먹는 것이 이득이다.
    따라서, 영양제를 주다가 모든 당근을 먹을 수 있는 최소한의 기간부터 당근을 먹으면 최대 당근의 맛을 구할 수 있다

    %주의사항% : 정답은 32비트 정수 변수(int) 범위를 초과할 수 있기 때문에
                64비트 정수 변수(C/C++ : long long, JAVA : long)를 사용해야 한다.
 */
public class BOJ18234 {
    static int N,T;
    static ArrayList<carrot> arr;
    static class carrot implements Comparable<carrot> {
        long w;
        long p;

        carrot(long w, long p) {
            this.w = w;
            this.p = p;
        }

        @Override
        public int compareTo(carrot c) {
            if(c.p < this.p) {
                return 1;
            } else if(c.p > this.p) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        arr = new ArrayList<>();

        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            long w = Long.parseLong(st.nextToken());
            long p = Long.parseLong(st.nextToken());
            arr.add(new carrot(w,p));
        }
        Collections.sort(arr);

        /*
            동작원리
            ex)
            3 5
            1 3
            2 9
            3 7
            위와 같은 예시가 들어왔을 때,
            arr의 형식은
            arr[0] = carrot(1,3)
            arr[1] = carrot(3,7)
            arr[2] = carrot(2,9)
            N=3, T=5이고, 69라는 최대값을 갖는 과정은 1일차 : 1,2,3의 당근을 심고, 토끼는 당근을 먹지 않는다
            2일차 : 당근 1,2,3에 영양제를 투여, 토끼는 당근을 먹지 않는다
            3일차 부터 당근을 먹기 시작해야 5일차가 되었을 때 영양제를 가장 많이 투여한 당근을 마지막 날에 먹을 수 있기 때문에
            T - N : 부여된 일수 - 당근의 개수의 의미는 기본적으로 모든 당근에 영양제를 투여할 수 있는 일수 이다.
            T - N + i : i를 영양제 값을 기준으로 오름차순 정렬한 arr 배열에 들어있는 carrot으로 0~N-1까지 반복하여
            당근을 섭취하기 전까지 아직 먹을 수 없는 당근에 영양제를 추가로 투여하기 때문에 i를 추가하여 영양제 투여 일수를 정한다.
            i를 0부터 시작함으로써 1일차는 당근을 심는 일수를 대신 할 수 있다.
            ex) 1일차 : 오리 - 당근 심기 / 토끼 - 먹지 않기
            2일차 : 오리 - 당근 영야제 투여 / 토끼 - 먹지 않기
            3일차 : 오리 - 당근 영양제 투여 / 토끼 - 1번 당근 먹기 (1 + 3 + 3) = 7
            4일차 : 오리 - 당근1 심기, 당근2,3 영양제 투여 / 토끼 2번 당근 먹기 (3 + 7 + 7 + 7) = 24
            5일차 : 오리 - 당근 1,3 영양제 투여, 당근 2심기 / 토끼 3번 당근 먹기 (2 + 9 + 9 + 9 + 9) = 38
            sum = 38 + 24 + 7 = 69

         */
        long sum = 0;
        for(int i=0;i<N;i++) {
            sum += (i + T - N) * arr.get(i).p + arr.get(i).w;
        }
        System.out.println(sum);
    }
}
