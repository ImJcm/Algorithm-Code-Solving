package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
퍼즐

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	32 MB (하단 참고)	18582	8188	5144	42.569%
문제
3×3 표에 다음과 같이 수가 채워져 있다. 오른쪽 아래 가장 끝 칸은 비어 있는 칸이다.

1	2	3
4	5	6
7	8
어떤 수와 인접해 있는 네 개의 칸 중에 하나가 비어 있으면, 수를 그 칸으로 이동시킬 수가 있다. 물론 표 바깥으로 나가는 경우는 불가능하다. 우리의 목표는 초기 상태가 주어졌을 때, 최소의 이동으로 위와 같은 정리된 상태를 만드는 것이다. 다음의 예를 보자.

1	 	3
4	2	5
7	8	6
1	2	3
4	 	5
7	8	6
1	2	3
4	5
7	8	6
1	2	3
4	5	6
7	8
가장 윗 상태에서 세 번의 이동을 통해 정리된 상태를 만들 수 있다. 이와 같이 최소 이동 횟수를 구하는 프로그램을 작성하시오.

입력
세 줄에 걸쳐서 표에 채워져 있는 아홉 개의 수가 주어진다. 한 줄에 세 개의 수가 주어지며, 빈 칸은 0으로 나타낸다.

출력
첫째 줄에 최소의 이동 횟수를 출력한다. 이동이 불가능한 경우 -1을 출력한다.

예제 입력 1
1 0 3
4 2 5
7 8 6
예제 출력 1
3
예제 입력 2
3 6 0
8 1 2
7 4 5
예제 출력 2
-1
출처
문제를 번역한 사람: baekjoon
잘못된 데이터를 찾은 사람: djm03178
데이터를 추가한 사람: hello70825
메모리 제한을 수정한 사람: portableangel
알고리즘 분류
자료 구조
그래프 이론
그래프 탐색
너비 우선 탐색
해시를 사용한 집합과 맵
메모리 제한
Java 8: 256 MB
Java 8 (OpenJDK): 256 MB
Java 11: 256 MB
Kotlin (JVM): 256 MB
 */
/*
첫 접근으로 1차원 배열로 이력으로 주어진 숫자들의 형태를 나타내어 -3, -1, +1, +3으로 상하좌우의 이동 형태를 나타내려 했지만 해당 방법은
잘못된 방법이였다.

따라서, +1, -1을 적용할 때 좌우의 이동이 층이동이 적용될 수 있는 부분을 고려하는 조건을 추가하여 해결하였다.
+ JDK 8,11의 경우 메모리를 256MB 제한임을 이용하여 문제 제출 시 JDK 11로 변경 후 제출하여 해결할 수 있었다.

추가로, JDK 15에서 주어지는 조건으로 메모리 32MB를 만족시키는 방법은 JDK 15에서 제공하는 자료구조를 이용하는 방법이라고 생각이 들지만 모르겠다...
=> 다른 정답 코드를 참고했을 때 Set을 통해 이미 방문한 퍼즐 형태를 검사하는 조건을 Map을 사용하여 방문여부와 이동한 횟수를 함께 조건을 검사할 수 있는 점을
이용하면 JDK 15로 조건을 만족하는 정답 코드를 만들 수 있는 것이라고 생각한다.
(Map.get()으로 존재 여부를 확인하고, Key는 퍼즐의 형태를 1차원 형태의 정수값으로 Value를 이동 횟수를 저장하여 bfs 사용한다.)
+ 구조체를 사용하는 경우는 0을 사용하여 Key로 저장하여 zero index로 위치를 특정이 가능하지만 구조체를 사용하지 않는 경우 0을 9로 변경하여
0의 위치를 9로 특정할 수 있어야 한다.) - https://codeung.tistory.com/215 참고


알고리즘 핵심
BFS
1. puzzle 정보를 정수형태로 저장하기 위한 1차원 배열과 0인 위치와 움직인 횟수를 저장한다.
2. 첫 퍼즐 형태를 Queue에 저장하고 bfs를 수행한다.
3. 0인 위치를 0~8의 값으로 저장하기 때문에 상하좌우로 스왑할 위치를 계산한다.
(이때, 0과 바꿀 위치를 -3,-1,+1,+3으로 상하좌우를 선택한다. + 좌우를 결정할 때 좌표가 유효한지 검사를 필요로 한다.)
4. puzzle의 1차원 배열을 정수형태로 변형한 후 HashSet에 존재 여부를 검사하여 중복된 puzzle를 제외하여 puzzle 형태를 정수로 저장한다.
5. 존재하지 않는 정수값을 queue에 class 형태로 저장하고 위 과정을 반복한다.
6. 123456780을 만족하는 puzzle 형태가 존재하면 움직인 횟수를 ans에 업데이트하고 bfs를 종료한다.
7. 123456780의 puzzle을 만들 수 없는 경우는 더이상의 puzzle을 만들 수 없는 queue가 존재하지 않아 bfs를 종료하는 경우이므로 -1을 출력한다.
 */
public class BOJ1525 {
    static class BOJ1525_puzzle {
        int[] nums;
        int zero;
        int move_cnt;

        BOJ1525_puzzle(int[] nums, int zero_p, int move_cnt) {
            this.nums = nums;
            this.zero = zero_p;
            this.move_cnt = move_cnt;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int ans, zero_p;
    static int[] init_puzzle;
    static int[] directions = {-3, -1, 1, 3};
    static HashSet<Integer> visited;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        play_puzzle();

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    private static void play_puzzle() {
        Queue<BOJ1525_puzzle> q = new LinkedList<>();

        q.add(new BOJ1525_puzzle(init_puzzle, zero_p,0));
        visited.add(separate_num(init_puzzle));

        while(!q.isEmpty()) {
            BOJ1525_puzzle now = q.poll();

            if(check_order_puzzle(now.nums)) {
                ans = now.move_cnt;
                break;
            }

            for(int d : directions) {
                int next_p = now.zero + d;

                if(next_p < 0 || next_p > 8) continue;
                if(Math.abs(d) == 1 && next_p / 3 != now.zero / 3) continue;

                int[] new_num = swap(now.nums, now.zero, next_p);
                if(!visited.contains(separate_num(new_num))) {
                    q.add(new BOJ1525_puzzle(new_num,next_p,now.move_cnt + 1));
                    visited.add(separate_num(new_num));
                }
            }
        }
    }

    private static boolean check_order_puzzle(int[] num) {
        boolean check = true;
        for(int i = 0; i < 8; i++) {
            if(!(num[i] == (i + 1))) {
                return !check;
            }
        }
        return check;
    }

    private static int[] swap(int[] num, int lidx, int ridx) {
        int[] new_num = Arrays.copyOf(num,num.length);

        int temp = new_num[lidx];
        new_num[lidx] = new_num[ridx];
        new_num[ridx] = temp;

        return new_num;
    }

    private static int separate_num(int[] nums) {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < 9; i++) {
            str.append(nums[i]);
        }
        return Integer.parseInt(str.toString());
    }

    private static void init_setting() throws IOException {
        init_puzzle = new int[9];

        visited = new HashSet<>();

        for(int i = 0; i < 3; i++) {
            String[] input = br.readLine().split(" ");
            for(int j = 0; j < 3; j++) {
                init_puzzle[i * 3 + j] = Integer.parseInt(input[j]);

                if(init_puzzle[i * 3 + j] == 0) {
                    zero_p = i * 3 + j;
                }
            }
        }

        ans = Integer.MAX_VALUE;
    }
}
