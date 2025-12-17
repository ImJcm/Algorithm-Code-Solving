package Implementation;

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
문제를 읽고 처음 접근은 단순 문제에서 요구하는 로직 그대로 구현하면 될 것이라고 생각하였다. R연산과 C연산에서 연산을 수행한 후, 배열의 크키가 동적으로 변하기 때문에
정적배열이 아닌 동적배열 ArrayList를 사용하여 필요에 따라 배열을 추가할 수 있도록 하였는데, R연산의 결과에 따라 변하는 col만큼 0을 갖는 객체들을 추가해줄 수 있었지만,
C연산의 경우 [r][c]에 num값을 설정해야 하기 때문에 미리 ArrayList가 행에 추가되어 있어야하고 앞서 배열의 순서에 맞게 0 또는 C연산 결과 값이 들어가 있어야 했다.
이 부분을 해결하기 위해 C연산의 경우 최대로 C연산을 수행하기 전 row의 2배만큼 늘어날 수 있는 점을 이용하여 연산전 2배만큼 늘리고 각 col에 대한 row에 적용될 값 외에 빈 공간에는
0을 추가해줌으로서 해결할 수 있다고 생각하였다. 하지만, C연산 이전 row의 2배된 값이 이미 생성된 arr의 배열의 길이보다 큰 경우 col만큼의 길이를 갖는 ArrayList가 생성되지 못해
IndexOutOfBounds 예외가 발생하였다.
상황 예시) R,C연산으로 arr의 size가 row=30, col=12 만큼 만들어졌다고 가정했을 때, 추가 R,C연산으로 (row=12, col=15)가 되고, 이후, C연산을 수행 시,
최대 24행,15열까지 연산로직을 수행하는데, 12*2 < 30이므로 15열을 갖는 ArrayList를 만들지 못하게 되고 Index=13을 참조하려고 할때 예외가 발생한다.
문제 발생 코드 - WA 참고

※ 잘못된 코드를 완성하고 난 후, 문제에서 만들어지는 배열의 크기가 100 x 100으로 제한을 둔 것을 알게되었다.

이 조건을 적용하면 불필요하게 배열을 생성하고 row, col을 검사하는 등의 로직을 제거할 수 있었다.

문제를 좀 자세히 살펴볼 수 있도록 해야겠다...

정답 코드의 알고리즘
1. R연산, C연산 으로 행,열마다 쓰이는 수, 해당하는 수의 횟수를 Map 자료구조를 만들고 커스텀 정렬을 사용하여 나타난 횟수를 기준으로 오름차순, 횟수가 같은 경우 해당하는 수의 크키만큼 오름차순
을 적용하여 해당 값을 각 기준인 row, col로 부터 0에서 정렬된 Map 값을 arr에 적용한다.
2. 값을 적용하고 남은 공간(100 - 사용된 공간)은 0으로 초기화 적용
3. Map 객체를 만들 때, R연산 또는 C연산에서 최대 길이의 row, col을 업데이트하고 반환하여 다음 연산에서 사용할 수 있도록 한다.
4. 위 과정을 T가 100일 때 까지 수행하거나 [r][c] == k인 경우, 종료한다.
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
        int T = 0;
        int row = 3;
        int col = 3;
        while(T++ <= 100) {
            if(arr.get(r-1).get(c-1).num == k) {
                System.out.println(T - 1);
                break;
            }

            if(row >= col) {
                col = R_operation(row, col);
                //System.out.println("R");
            } else {
                row = C_operation(row, col);
                //System.out.println("C");
            }

            /*for(int i=0;i<row;i++) {
                for(int j=0;j<col;j++) {
                    System.out.print(arr.get(i).get(j).num + ",");
                }
                System.out.println();
            }
            System.out.println("-----------------------");*/
        }

        if(T == 102) {
            System.out.println(-1);
        }
    }

    static int R_operation(int row, int col) {
        int n_col = 0;

        for(int i=0;i<row;i++) {
            HashMap<Integer,Integer> map = new HashMap<>();

            for(int j=0;j<col;j++) {
                BOJ17140_node node = arr.get(i).get(j);
                int num = node.num;

                if(num == 0) continue;

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

            n_col = Math.max(n_col, (2 * map.size()));
            n_col = Math.min(n_col, 100);

            int n = 0;
            for(Integer k : keySet) {
                arr.get(i).get(n++).num = k;
                arr.get(i).get(n++).num = map.get(k);
                if(n == 100) break;
            }

            for(int k=n;k<100;k++) {
                arr.get(i).get(k).num = 0;
            }
        }

        return n_col;
    }

    static int C_operation(int row, int col) {
        int n_row = 0;

        for(int i=0;i<col;i++) {
            HashMap<Integer,Integer> map = new HashMap<>();

            for(int j=0;j<row;j++) {
                BOJ17140_node node = arr.get(j).get(i);
                int num = node.num;

                if(num == 0) continue;
                else node.num = 0;

                if(map.containsKey(num)) {
                    map.replace(num, map.get(num) + 1);
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

            n_row = Math.max(n_row, (2 * map.size()));
            n_row = Math.min(n_row, 100);

            int n = 0;
            for(Integer k : keySet) {
                arr.get(n++).get(i).num = k;
                arr.get(n++).get(i).num = map.get(k);
                if(n == 100) break;
            }

            for(int k=n;k<100;k++) {
                arr.get(k).get(i).num = 0;
            }
        }

        return n_row;
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        r = Integer.parseInt(input[0]);
        c = Integer.parseInt(input[1]);
        k = Integer.parseInt(input[2]);

        arr = new ArrayList<>();

        for(int i=0;i<100;i++) {
            arr.add(new ArrayList<>());
            for(int j=0;j<100;j++) {
                arr.get(i).add(new BOJ17140_node(0));
            }
        }

        for(int i=0;i<3;i++) {
            input = br.readLine().split(" ");
            for(int j=0;j<3;j++) {
                arr.get(i).get(j).num = Integer.parseInt(input[j]);
            }
        }
    }
}

/*
//WA
public class Main {
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
        int T = 0;
        int row = 3;
        int col = 3;
        while(T++ <= 100) {
            if((r-1 < row && c-1 < col)) {
                if(arr.get(r-1).get(c-1).num == k) {
                    System.out.println(T - 1);
                    break;
                }
            }

            if(row >= col) {
                col = R_operation(row, col);
            } else {
                row = C_operation(row, col);
            }
        }

        if(T == 102) {
            System.out.println(-1);
        }
    }

    static int R_operation(int row, int col) {
        int n_col = 0;
        for(int i=0;i<row;i++) {
            HashMap<Integer,Integer> map = new HashMap<>();

            for(int j=0;j<arr.get(i).size();j++) {
                BOJ17140_node node = arr.get(i).get(j);
                int num = node.num;

                if(num == 0) continue;

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

            n_col = Math.max(n_col, (2 * map.size()));
            n_col = Math.min(n_col, 100);

            for(Integer k : keySet) {
                arr.get(i).add(new BOJ17140_node(k));
                arr.get(i).add(new BOJ17140_node(map.get(k)));
                if(arr.get(i).size() == 100) break;
            }
        }

        for(int i=0;i<row;i++) {
            int diff = n_col - arr.get(i).size();

            for(int j=0;j<diff;j++) {
                arr.get(i).add(new BOJ17140_node(0));
            }
        }

        return n_col;
    }

    static int C_operation(int row, int col) {
        int n_row = 0;

        if(arr.size() < 2 * row) {
            int n_r = 2 * row;
            if(n_r >= 100) {
                n_r = 100;
            }
            for(int i=arr.size();i<n_r;i++) {
                arr.add(new ArrayList<>());
                for(int j=0;j<col;j++) {
                    arr.get(i).add(new BOJ17140_node(0));
                }
            }
        }

        for(int i=0;i<col;i++) {
            HashMap<Integer,Integer> map = new HashMap<>();

            for(int j=0;j<row;j++) {
                BOJ17140_node node = arr.get(j).get(i);
                int num = node.num;

                if(num == 0) continue;
                else node.num = 0;

                if(map.containsKey(num)) {
                    map.replace(num, map.get(num) + 1);
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

            n_row = Math.max(n_row, (2 * map.size()));
            n_row = Math.min(n_row, 100);

            int r = 0;
            for(Integer k : keySet) {
                arr.get(r++).get(i).num = k;
                arr.get(r++).get(i).num = map.get(k);
                if(r == 100) break;
            }
        }

        return n_row;
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
 */