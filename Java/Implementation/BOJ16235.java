package Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

/*
나무 재테크

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.3 초 (하단 참고)	512 MB	63692	15857	8960	22.316%
문제
부동산 투자로 억대의 돈을 번 상도는 최근 N×N 크기의 땅을 구매했다. 상도는 손쉬운 땅 관리를 위해 땅을 1×1 크기의 칸으로 나누어 놓았다. 각각의 칸은 (r, c)로 나타내며, r은 가장 위에서부터 떨어진 칸의 개수, c는 가장 왼쪽으로부터 떨어진 칸의 개수이다. r과 c는 1부터 시작한다.

상도는 전자통신공학과 출신답게 땅의 양분을 조사하는 로봇 S2D2를 만들었다. S2D2는 1×1 크기의 칸에 들어있는 양분을 조사해 상도에게 전송하고, 모든 칸에 대해서 조사를 한다. 가장 처음에 양분은 모든 칸에 5만큼 들어있다.

매일 매일 넓은 땅을 보면서 뿌듯한 하루를 보내고 있던 어느 날 이런 생각이 들었다.

나무 재테크를 하자!

나무 재테크란 작은 묘목을 구매해 어느정도 키운 후 팔아서 수익을 얻는 재테크이다. 상도는 나무 재테크로 더 큰 돈을 벌기 위해 M개의 나무를 구매해 땅에 심었다. 같은 1×1 크기의 칸에 여러 개의 나무가 심어져 있을 수도 있다.

이 나무는 사계절을 보내며, 아래와 같은 과정을 반복한다.

봄에는 나무가 자신의 나이만큼 양분을 먹고, 나이가 1 증가한다. 각각의 나무는 나무가 있는 1×1 크기의 칸에 있는 양분만 먹을 수 있다. 하나의 칸에 여러 개의 나무가 있다면, 나이가 어린 나무부터 양분을 먹는다. 만약, 땅에 양분이 부족해 자신의 나이만큼 양분을 먹을 수 없는 나무는 양분을 먹지 못하고 즉시 죽는다.

여름에는 봄에 죽은 나무가 양분으로 변하게 된다. 각각의 죽은 나무마다 나이를 2로 나눈 값이 나무가 있던 칸에 양분으로 추가된다. 소수점 아래는 버린다.

가을에는 나무가 번식한다. 번식하는 나무는 나이가 5의 배수이어야 하며, 인접한 8개의 칸에 나이가 1인 나무가 생긴다. 어떤 칸 (r, c)와 인접한 칸은 (r-1, c-1), (r-1, c), (r-1, c+1), (r, c-1), (r, c+1), (r+1, c-1), (r+1, c), (r+1, c+1) 이다. 상도의 땅을 벗어나는 칸에는 나무가 생기지 않는다.

겨울에는 S2D2가 땅을 돌아다니면서 땅에 양분을 추가한다. 각 칸에 추가되는 양분의 양은 A[r][c]이고, 입력으로 주어진다.

K년이 지난 후 상도의 땅에 살아있는 나무의 개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N, M, K가 주어진다.

둘째 줄부터 N개의 줄에 A배열의 값이 주어진다. r번째 줄의 c번째 값은 A[r][c]이다.

다음 M개의 줄에는 상도가 심은 나무의 정보를 나타내는 세 정수 x, y, z가 주어진다. 처음 두 개의 정수는 나무의 위치 (x, y)를 의미하고, 마지막 정수는 그 나무의 나이를 의미한다.

출력
첫째 줄에 K년이 지난 후 살아남은 나무의 수를 출력한다.

제한
1 ≤ N ≤ 10
1 ≤ M ≤ N2
1 ≤ K ≤ 1,000
1 ≤ A[r][c] ≤ 100
1 ≤ 입력으로 주어지는 나무의 나이 ≤ 10
입력으로 주어지는 나무의 위치는 모두 서로 다름
예제 입력 1
1 1 1
1
1 1 1
예제 출력 1
1
예제 입력 2
1 1 4
1
1 1 1
예제 출력 2
0
예제 입력 3
5 2 1
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 1 3
3 2 3
예제 출력 3
2
예제 입력 4
5 2 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 1 3
3 2 3
예제 출력 4
15
예제 입력 5
5 2 3
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 1 3
3 2 3
예제 출력 5
13
예제 입력 6
5 2 4
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 1 3
3 2 3
예제 출력 6
13
예제 입력 7
5 2 5
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 1 3
3 2 3
예제 출력 7
13
예제 입력 8
5 2 6
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 1 3
3 2 3
예제 출력 8
85
노트
나무 재테크와 관련된 내용은 이 링크를 참고했다.

출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: hamcom1212, jh05013
알고리즘 분류
구현
자료 구조
시뮬레이션
시간 제한
Python 3: 1.3 초
PyPy3: 1.3 초
Python 2: 1.3 초
PyPy2: 1.3 초
 */
/*
첫 접근으로 봄,여름,가을,겨울에서 요구하는 기능을 구현하였고, 1년이 지날 때마다 trees[r][c]에 존재하는 나무들을 age를 기준으로 정렬시키는 코드로 접근하였다.
결과는 시간초과가 발생하였고, 이를 해결하기 위해 정렬, 각 계절에서 사용되는 2차원 + trees 순차적 조회가 시간초과의 원인이라고 생각하였다.

그래서 정렬을 제거하고 데이터 삽입 시, 정렬되어 저장되는 우선순위 큐를 사용하기로 하였고, 계절 간에 데이터 값의 영향을 미치지 않기 위해 3차원 배열을 사용하여
[r][c][2]에서 [][][0]에서 spring, summer, autumn, winter 중 나무의 age 값에 영향을 미치는 spring, autumn의 기능을 적용시킨 값을 [][][0],[][][1]에
따로 저장하여 alive_trees[][], dead_trees[][]와 같이 추가적인 배열을 생성하는 것을 대신할 수 있었다.

그리고 문제가 요구하는 결과 값을 구하는 과정이 spring, summer, autumn, winter의 순서가 고정일 필요가 없다고 생각하여
r,c를 순차적으로 조회하는 2차원 반복문을 한번만 사용하여 나무들의 age에 영향을 미치는 spring, autumn을 묶고, summer, winter를 묶어서 두 계절들의 순서만을
순차적으로 고정시켰다.

코드를 완성하면서 알게된 점 : priorityQueue의 올바른 사용법은 데이터의 삽입의 순서는 중요하지 않고 출력 시, 우선순위 정렬 기준이 적용되어 출력된 값을 이용하는 것
 */
public class BOJ16235 {
    static class BOJ16235_tree implements Comparable<BOJ16235_tree> {
        int r,c;
        int age;

        BOJ16235_tree(int r,int c,int age) {
            this.r = r;
            this.c = c;
            this.age = age;
        }

        @Override
        public int compareTo(BOJ16235_tree o) {
            return this.age - o.age;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M,K,alive_tree_cnt;
    static int[][] directions = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
    static int[][] nutrients,fields,converted_fields;
    static PriorityQueue<BOJ16235_tree>[][][] alive_trees;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    /*
        priorityQueue에 기존에 정렬되어 있는 데이터가 존재하는 경우, 추가 데이터를 넣을 때 정렬이 적용되지 않아서 WA
        -> 이유 : for(BOJ16235_tree t : alive_trees[r][c][period]) {...} 다음 코드와 같이 우선순위 큐의 출력을 적절히 사용하지 못함

        while(!alive_trees[r][c][period].isEmpty()) {
            BOJ16235_tree t = alive_trees[r][c][period].poll();
            ...
        }
        위와 같이 코드를 변경하여 age가 낮은 나무부터 spring, autumn 로직이 적용될 수 있도록 변경하여 정답
     */
    static void solve() {
        int period = 0, next_period = 1;
        while(K-- > 0) {
            for(int r=1;r<=N;r++) {
                for(int c=1;c<=N;c++) {
                    while(!alive_trees[r][c][period].isEmpty()) {
                        BOJ16235_tree t = alive_trees[r][c][period].poll();
                        //spring
                        if(fields[r][c] - t.age < 0) {
                            converted_fields[r][c] += (t.age / 2);
                        } else {
                            fields[r][c] -= t.age;
                            alive_trees[r][c][next_period].add(new BOJ16235_tree(r,c,t.age + 1));

                            //autumn
                            if((t.age + 1) % 5 == 0) {
                                for(int[] d : directions) {
                                    int nr = r + d[0];
                                    int nc = c + d[1];

                                    if(nr < 1 || nr > N || nc < 1 || nc > N) continue;
                                    alive_trees[nr][nc][next_period].add(new BOJ16235_tree(nr,nc,1));
                                }
                            }
                        }
                    }
                    alive_trees[r][c][period].clear();

                    //summer
                    fields[r][c] += converted_fields[r][c];
                    converted_fields[r][c] = 0;

                    //winter
                    fields[r][c] += nutrients[r][c];
                }
            }
            period = (period == 0) ? 1 : 0;
            next_period = (next_period == 0) ? 1 : 0;
        }

        for(int r=1;r<=N;r++) {
            for(int c=1;c<=N;c++) {
                alive_tree_cnt += alive_trees[r][c][period].size();
            }
        }

        System.out.println(alive_tree_cnt);
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        K = Integer.parseInt(input[2]);

        nutrients = new int[N+1][N+1];
        fields = new int[N+1][N+1];
        converted_fields = new int[N+1][N+1];
        alive_trees = new PriorityQueue[N+1][N+1][2];

        for(int i=1;i<=N;i++) {
            input = br.readLine().split(" ");
            for(int j=1;j<=N;j++) {
                nutrients[i][j] = Integer.parseInt(input[j-1]);
                fields[i][j] = 5;
                converted_fields[i][j] = 0;
                for(int k=0;k<2;k++) {
                    alive_trees[i][j][k] = new PriorityQueue<>();
                }
            }
        }

        for(int i=0;i<M;i++) {
            input = br.readLine().split(" ");
            int r = Integer.parseInt(input[0]);
            int c = Integer.parseInt(input[1]);
            int age = Integer.parseInt(input[2]);
            alive_trees[r][c][0].add(new BOJ16235_tree(r,c,age));
        }

        alive_tree_cnt = 0;
    }
}
