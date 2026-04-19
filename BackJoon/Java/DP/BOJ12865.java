package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
평범한 배낭

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	133849	50276	32031	35.884%
문제
이 문제는 아주 평범한 배낭에 관한 문제이다.

한 달 후면 국가의 부름을 받게 되는 준서는 여행을 가려고 한다. 세상과의 단절을 슬퍼하며 최대한 즐기기 위한 여행이기 때문에, 가지고 다닐 배낭 또한 최대한 가치 있게 싸려고 한다.

준서가 여행에 필요하다고 생각하는 N개의 물건이 있다. 각 물건은 무게 W와 가치 V를 가지는데, 해당 물건을 배낭에 넣어서 가면 준서가 V만큼 즐길 수 있다. 아직 행군을 해본 적이 없는 준서는 최대 K만큼의 무게만을 넣을 수 있는 배낭만 들고 다닐 수 있다. 준서가 최대한 즐거운 여행을 하기 위해 배낭에 넣을 수 있는 물건들의 가치의 최댓값을 알려주자.

입력
첫 줄에 물품의 수 N(1 ≤ N ≤ 100)과 준서가 버틸 수 있는 무게 K(1 ≤ K ≤ 100,000)가 주어진다. 두 번째 줄부터 N개의 줄에 거쳐 각 물건의 무게 W(1 ≤ W ≤ 100,000)와 해당 물건의 가치 V(0 ≤ V ≤ 1,000)가 주어진다.

입력으로 주어지는 모든 수는 정수이다.

출력
한 줄에 배낭에 넣을 수 있는 물건들의 가치합의 최댓값을 출력한다.

예제 입력 1
4 7
6 13
4 8
3 6
5 12
예제 출력 1
14
출처
문제를 만든 사람: Acka
데이터를 추가한 사람: kpqi5858, leedongbin, riroan, skyoliver
알고리즘 분류
다이나믹 프로그래밍
배낭 문제
 */
/*
무게와 가치를 갖는 knapsack 문제에서 수용 가능한 무게 내에서 최대 가치를 뽑아낼 때, 동적 계획법을 사용하는 방법은 현재 물건을 챙기는 경우와 챙기지 않는 경우
두 가지 방법으로 최대 가치를 갖는 것을 재귀형태로 업데이트하는 방법을 사용하였다.

pack(capacity, item)은 남은 capacity(수용량)에서 item 이후 물건들을 챙겼을 때 얻을 수 있는 최대 가치를 나타낸다.

따라서, pack(capacity, 1) 값이 수용가능한 최대 가치 값을 가진다.
 */
public class BOJ12865 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,K;
    static int[] w,v;
    static int[][] knapsack;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        System.out.println(pack(K,1));
    }

    static int pack(int capacity, int item) {
        if(item == N + 1) return 0;
        if(knapsack[capacity][item] != -1) return knapsack[capacity][item];

        knapsack[capacity][item] = pack(capacity,item + 1); // 가방에 물건을 넣지 않고 다음 물건으로 비교를 넘어가는 경우

        if(capacity - w[item] >= 0) {   // 수용 가능한 무게의 물건을 챙길 수 있는 경우
            knapsack[capacity][item] = Math.max(knapsack[capacity][item], pack(capacity - w[item], item + 1) + v[item]);
        }

        return knapsack[capacity][item];
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);

        w = new int[N+1];
        v = new int[N+1];
        knapsack = new int[K + 1][N+1];

        for(int i=1;i<=N;i++) {
            input = br.readLine().split(" ");

            w[i] = Integer.parseInt(input[0]);
            v[i] = Integer.parseInt(input[1]);
        }

        for(int i=1;i<=K;i++) {
            Arrays.fill(knapsack[i],-1);
        }
    }
}
