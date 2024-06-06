package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/*
이차원 배열과 연산

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (추가 시간 없음)	512 MB	22287	10572	7035	44.570%
문제
크기가 3×3인 배열 A가 있다. 배열의 인덱스는 1부터 시작한다. 1초가 지날때마다 배열에 연산이 적용된다.

R 연산: 배열 A의 모든 행에 대해서 정렬을 수행한다. 행의 개수 ≥ 열의 개수인 경우에 적용된다.
C 연산: 배열 A의 모든 열에 대해서 정렬을 수행한다. 행의 개수 < 열의 개수인 경우에 적용된다.
한 행 또는 열에 있는 수를 정렬하려면, 각각의 수가 몇 번 나왔는지 알아야 한다. 그 다음, 수의 등장 횟수가 커지는 순으로, 그러한 것이 여러가지면 수가 커지는 순으로 정렬한다. 그 다음에는 배열 A에 정렬된 결과를 다시 넣어야 한다. 정렬된 결과를 배열에 넣을 때는, 수와 등장 횟수를 모두 넣으며, 순서는 수가 먼저이다.

예를 들어, [3, 1, 1]에는 3이 1번, 1가 2번 등장한다. 따라서, 정렬된 결과는 [3, 1, 1, 2]가 된다. 다시 이 배열에는 3이 1번, 1이 2번, 2가 1번 등장한다. 다시 정렬하면 [2, 1, 3, 1, 1, 2]가 된다.

정렬된 결과를 배열에 다시 넣으면 행 또는 열의 크기가 달라질 수 있다. R 연산이 적용된 경우에는 가장 큰 행을 기준으로 모든 행의 크기가 변하고, C 연산이 적용된 경우에는 가장 큰 열을 기준으로 모든 열의 크기가 변한다. 행 또는 열의 크기가 커진 곳에는 0이 채워진다. 수를 정렬할 때 0은 무시해야 한다. 예를 들어, [3, 2, 0, 0]을 정렬한 결과는 [3, 2]를 정렬한 결과와 같다.

행 또는 열의 크기가 100을 넘어가는 경우에는 처음 100개를 제외한 나머지는 버린다.

배열 A에 들어있는 수와 r, c, k가 주어졌을 때, A[r][c]에 들어있는 값이 k가 되기 위한 최소 시간을 구해보자.

입력
첫째 줄에 r, c, k가 주어진다. (1 ≤ r, c, k ≤ 100)

둘째 줄부터 3개의 줄에 배열 A에 들어있는 수가 주어진다. 배열 A에 들어있는 수는 100보다 작거나 같은 자연수이다.

출력
A[r][c]에 들어있는 값이 k가 되기 위한 연산의 최소 시간을 출력한다. 100초가 지나도 A[r][c] = k가 되지 않으면 -1을 출력한다.

예제 입력 1
1 2 2
1 2 1
2 1 3
3 3 3
예제 출력 1
0
예제 입력 2
1 2 1
1 2 1
2 1 3
3 3 3
예제 출력 2
1
예제 입력 3
1 2 3
1 2 1
2 1 3
3 3 3
예제 출력 3
2
예제 입력 4
1 2 4
1 2 1
2 1 3
3 3 3
예제 출력 4
52
예제 입력 5
1 2 5
1 2 1
2 1 3
3 3 3
예제 출력 5
-1
예제 입력 6
3 3 3
1 1 1
1 1 1
1 1 1
예제 출력 6
2
힌트
배열 A의 초기값이 아래와 같은 경우를 생각해보자.

1 2 1
2 1 3
3 3 3
가장 처음에는 행의 개수 ≥ 열의 개수 이기 때문에, R 연산이 적용된다. 편의상 정렬 중간 단계는 (수, 수의 등장 횟수)로 표현한다.

1 2 1 → (2, 1), (1, 2)         → 2 1 1 2
2 1 3 → (1, 1), (2, 1), (3, 1) → 1 1 2 1 3 1
3 3 3 → (3, 3)                 → 3 3
크기가 가장 큰 행은 2번 행이고, 나머지 행의 뒤에 0을 붙여야 한다.

2 1 1 2 0 0
1 1 2 1 3 1
3 3 0 0 0 0
다음에 적용되는 연산은 행의 개수 < 열의 개수이기 때문에 C 연산이다.

1 3 1 1 3 1
1 1 1 1 1 1
2 1 2 2 0 0
1 2 1 1 0 0
3 0 0 0 0 0
1 0 0 0 0 0
연산이 적용된 결과는 위와 같다.

출처
문제를 만든 사람: baekjoon
어색한 표현을 찾은 사람: djm03178, jh05013
알고리즘 분류
구현
정렬
시뮬레이션
 */
/*

 */
public class BOJ17140 {
    static class BOJ17140_node {
        int num;

        BOJ17140_node(int n) {
            this.num = n;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int r,c,k;
    static ArrayList<ArrayList<BOJ17140_node>> arr;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        int T = 100;
        int row = 3;
        int col = 3;
        while(--T > 0) {
            if(row >= col) {
                col = R_operation(row, col);
            } else {
                row = C_operation(row, col);
            }

            if(arr.get(r-1).get(c-1).num == k) {
                System.out.println(T);
                break;
            }
        }

        if(T == 0) {
            System.out.println(-1);
        }
    }

    static int R_operation(int row, int col) {
        for(int i=0;i<row;i++) {
            HashMap<Integer,Integer> map = new HashMap<>();

            for(int j=0;j<arr.get(i).size();j++) {
                BOJ17140_node node = arr.get(i).get(j);
                int num = node.num;

                if(map.containsKey(num)) {
                    map.replace(num,map.get(num) + 1);
                } else {
                    map.put(num,1);
                }
            }

            ArrayList<Integer> keySet = new ArrayList<>(map.keySet());

            keySet.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    if(map.get(o1) < map.get(o2)) {
                        return -1;
                    } else if(map.get(o1) == map.get(o2)) {
                        return o1 - o2;
                    } else {
                        return 1;
                    }
                }
            });

            arr.get(i).clear();

            for(Integer k : map.keySet()) {
                arr.get(i).add(new BOJ17140_node(k));
                arr.get(i).add(new BOJ17140_node(map.get(k)));
            }


        }

        return col;
    }

    static int C_operation(int row, int col) {

        return row;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        r = Integer.parseInt(input[0]);
        c = Integer.parseInt(input[1]);
        k = Integer.parseInt(input[2]);

        arr = new ArrayList<>();

        for(int i=0;i<3;i++) {
            arr.add(new ArrayList<>());
            input = br.readLine().split(" ");
            for(int j=0;j<3;j++) {
                arr.get(i).add(new BOJ17140_node(Integer.parseInt(input[j])));
            }
        }
    }
}
