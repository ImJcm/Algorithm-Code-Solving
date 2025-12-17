package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
한윤정이 이탈리아에 가서 아이스크림을 사먹는데 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	11843	4791	3620	40.547%
문제
한윤정과 친구들은 이탈리아로 방학 여행을 갔다. 이탈리아는 덥다. 윤정이와 친구들은 아이스크림을 사먹기로 했다. 아이스크림 가게에는 N종류의 아이스크림이 있다. 모든 아이스크림은 1부터 N까지 번호가 매겨져있다. 어떤 종류의 아이스크림을 함께먹으면, 맛이 아주 형편없어진다. 따라서 윤정이는 이러한 경우를 피하면서 아이스크림을 3가지 선택하려고 한다. 이때, 선택하는 방법이 몇 가지인지 구하려고 한다.

입력
첫째 줄에 정수 N과 M이 주어진다. N은 아이스크림 종류의 수이고, M은 섞어먹으면 안 되는 조합의 개수이다. 아래 M개의 줄에는 섞어먹으면 안 되는 조합의 번호가 주어진다. 같은 조합은 두 번 이상 나오지 않는다. (1 ≤ N ≤ 200, 0 ≤ M ≤ 10,000)

출력
첫째 줄에, 가능한 방법이 총 몇 개 있는지 출력한다.

예제 입력 1
5 3
1 2
3 4
1 3
예제 출력 1
3
힌트
5개의 아이스크림과 3가지 섞어먹으면 안 되는 조합이 있다. 1번은 2번 3번과 섞으면 안되고, 3번은 4번과 섞으면 안 된다. 따라서 (1 4 5), (2 3 5), (2 4 5)와 같이 3가지 방법이 있다.

출처
Olympiad > Baltic Olympiad in Informatics > BOI 2011 2번

문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: eric00513, shiftpsh, uss01
알고리즘 분류
브루트포스 알고리즘
 */
/*
알고리즘 핵심
0. 아이스크림 a와 b는 양방향으로 선택되지 않아야할 조합 정보를 양방향으로 갖는다.
1. 아이스크림 1을 선택 후 아이스크림 2번을 선택하는 과정에서 1과 조합하지 않아야할 조건을 검사한다.
2. 아이스크림 2을 선택 후 아이스크림 3번을 선택하는 과정에서 1과 2 모두 조합하지 않아야할 조건을 검사한다.
 */
public class BOJ2422 {
    static class BOJ2422_iceCream {
        int n;
        ArrayList<Integer> bad_comb;

        BOJ2422_iceCream(int n) {
            this.n = n;
            this.bad_comb = new ArrayList<>();
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,ans;
    static BOJ2422_iceCream[] ice_creams;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int i1=1;i1<=ice_creams.length-3;i1++) {
            for(int i2=i1+1;i2<=ice_creams.length-2;i2++) {
                if(ice_creams[i2].bad_comb.contains(i1)) continue;
                for(int i3=i2+1;i3<=ice_creams.length-1;i3++) {
                    if(ice_creams[i3].bad_comb.contains(i1) || ice_creams[i3].bad_comb.contains(i2)) continue;
                    ans++;
                }
            }
        }

        System.out.println(ans);
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        ice_creams = new BOJ2422_iceCream[N+1];

        ans = 0;

        for(int i=1;i<=N;i++) {
            ice_creams[i] = new BOJ2422_iceCream(i);
        }

        for(int i=0;i<M;i++) {
            input = br.readLine().split(" ");

            int n1 = Integer.parseInt(input[0]);
            int n2 = Integer.parseInt(input[1]);

            ice_creams[n1].bad_comb.add(n2);
            ice_creams[n2].bad_comb.add(n1);
        }
    }
}
