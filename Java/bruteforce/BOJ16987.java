package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
계란으로 계란치기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	11737	6068	4195	50.898%
문제
원래 프로그래머의 기본 소양은 팔굽혀펴기를 단 한 개도 할 수 없는 것이라고 하지만 인범이는 3대 500을 넘기는 몇 안되는 프로그래머 중 한 명이다. 인범이는 BOJ에서 틀린 제출을 할 때마다 턱걸이를 5회 하는 기적의 운동 루틴을 통해 뇌와 근육을 동시에 단련한다. 근육을 단련할 때 식단이 정말로 중요하다는 것을 아는 인범이는 탄수화물이 많은 밥이나 빵 따위의 아침 식사를 대신해 단백질이 많은 계란찜을 해먹는다. 계란찜을 먹기 위해서는 계란을 깨야 하는데, 인범이는 힘이 너무 넘치는 나머지 부엌의 대리석을 이용해 계란을 깨면 늘 껍데기가 산산조각나 뒷처리가 너무 어렵게 되곤 한다. 어떻게 하면 계란을 조심스럽게 깰 수 있을까 고민하던 인범이에게 유현이는 굉장히 좋은 해결책을 알려주었다. 바로 계란으로 계란을 치는 것이다. 계란끼리 부딪쳐보니 껍데기가 아주 예쁘게 갈라지는 것을 발견한 인범이는 앞으로 계란으로 계란을 쳐서 식사 준비를 해야겠다고 생각했다. 유현이는 더 나아가 식사 준비를 할 때에도 두뇌를 단련할 수 있는 좋은 퍼즐을 인범이에게 알려주었다.

문제를 소개하기 전, 계란으로 계란을 치게 될 경우 어떤 일이 벌어지는지를 먼저 이해하고 가자. 각 계란에는 내구도와 무게가 정해져있다. 계란으로 계란을 치게 되면 각 계란의 내구도는 상대 계란의 무게만큼 깎이게 된다. 그리고 내구도가 0 이하가 되는 순간 계란은 깨지게 된다. 예를 들어 계란 1의 내구도가 7, 무게가 5이고 계란 2의 내구도가 3, 무게가 4라고 해보자. 계란 1으로 계란 2를 치게 되면 계란 1의 내구도는 4만큼 감소해 3이 되고 계란 2의 내구도는 5만큼 감소해 -2가 된다. 충돌 결과 계란 1은 아직 깨지지 않았고 계란 2는 깨졌다.

유현이가 인범이에게 알려준 퍼즐은 일렬로 놓여있는 계란에 대해 왼쪽부터 차례로 들어서 한 번씩만 다른 계란을 쳐 최대한 많은 계란을 깨는 문제였다. 구체적으로 계란을 치는 과정을 설명하면 아래와 같다.

가장 왼쪽의 계란을 든다.
손에 들고 있는 계란으로 깨지지 않은 다른 계란 중에서 하나를 친다. 단, 손에 든 계란이 깨졌거나 깨지지 않은 다른 계란이 없으면 치지 않고 넘어간다. 이후 손에 든 계란을 원래 자리에 내려놓고 3번 과정을 진행한다.
가장 최근에 든 계란의 한 칸 오른쪽 계란을 손에 들고 2번 과정을 다시 진행한다. 단, 가장 최근에 든 계란이 가장 오른쪽에 위치한 계란일 경우 계란을 치는 과정을 종료한다.
이 과정을 통해 최대한 많은 계란을 깨는 것이 앞으로 인범이가 매일 아침마다 풀게 될 퍼즐이다. 그리고 유현이는 인범이가 찾은 답이 정답이 맞는지 확인해주려고 한다. 일렬로 놓인 계란들의 내구도와 무게가 차례대로 주어졌을 때 최대 몇 개의 계란을 깰 수 있는지 알아맞춰보자.

입력
첫째 줄에 계란의 수를 나타내는 N(1 ≤ N ≤ 8)가 주어진다. 그 다음 N개의 줄에는 계란의 내구도와 무게에 대한 정보가 주어진다. i+1번째 줄에는 왼쪽에서 i번째에 위치한 계란의 내구도 Si(1 ≤ Si ≤ 300)와 무게 Wi(1 ≤ Wi ≤ 300)가 한 칸의 빈칸을 사이에 두고 주어진다.

출력
첫째 줄에 인범이가 깰 수 있는 계란의 최대 개수를 출력한다.

예제 입력 1
3
8 5
1 100
3 5
예제 출력 1
3
예제 입력 2
3
1 100
8 5
3 5
예제 출력 2
2
예제 입력 3
1
123 45
예제 출력 3
0
예제 입력 4
8
222 117
176 92
287 6
284 81
221 96
263 96
188 40
225 1
예제 출력 4
4
예제 입력 5
6
65 281
272 145
233 175
229 12
99 88
142 159
예제 출력 5
6
예제 입력 6
8
161 36
248 93
233 13
241 122
285 91
260 25
221 14
233 42
예제 출력 6
3
예제 입력 7
3
213 295
153 24
15 233
예제 출력 7
3
예제 입력 8
8
44 11
116 73
121 54
49 232
69 136
159 242
109 172
28 31
예제 출력 8
8
예제 입력 9
6
100 1
100 1
100 1
100 1
100 1
100 1
예제 출력 9
0
출처
문제를 만든 사람: BaaaaaaaaaaarkingDog
문제를 검수한 사람: baekjoon, portableangel, ryute
데이터를 추가한 사람: klas
문제의 오타를 찾은 사람: park780172
알고리즘 분류
브루트포스 알고리즘
백트래킹
 */
/*
알고리즘 핵심
1. depth에 해당하는 순서의 계란은 왼손에 들고, 오른손에는 부딪칠 계란을 고른다.
(이때, 왼손, 오른손의 계란은 꺠지지 않아야 한다.)
2. 왼손에 계란을 든 경우, 오른손에 들 계란이 존재하지 않다면 다음 과정을 수행한다.
3. 왼손에 계란을 든 경우, 오른손에 들 계란이 존재하면 해당 계란과 부딪쳐서 내구도를 업데이트한다.
4. 왼손에 들 계란이 N과 같아지면, 깨진 계란의 수를 최대값으로 업데이트한다.
 */
public class BOJ16987 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, ans;
    static int[] weight, durability;
    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        dfs(0,0);

        System.out.println(ans);
    }

    static void dfs(int depth, int broken_egg) {
        if(depth == N) {
            ans = Math.max(ans, broken_egg);
            return;
        }

        if(durability[depth] <= 0 || broken_egg == N - 1) dfs(depth + 1, broken_egg);
        else {
            for(int i=0;i<N;i++) {
                if(depth == i || durability[i] <= 0) continue;
                durability[i] -= weight[depth];
                durability[depth] -= weight[i];

                int broking_egg = durability[i] <= 0 && durability[depth] <= 0 ? 2 :
                        durability[i] > 0 && durability[depth] > 0 ? 0 : 1;

                dfs(depth + 1, broken_egg + broking_egg);

                durability[i] += weight[depth];
                durability[depth] += weight[i];
            }
        }
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        weight = new int[N];
        durability = new int[N];

        ans = 0;

        for(int i=0;i<N;i++) {
            String[] input = br.readLine().split(" ");

            int d = Integer.parseInt(input[0]);
            int w = Integer.parseInt(input[1]);

            weight[i] = w;
            durability[i] = d;
        }
    }
}
