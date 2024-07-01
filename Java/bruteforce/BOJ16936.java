package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/*
나3곱2 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	4291	1770	1393	41.458%
문제
나3곱2 게임은 정수 하나를 이용한다. 가장 먼저, 정수 x로 시작하고, 연산을 N-1번 적용한다. 적용할 수 있는 연산은 두 가지 있고, 아래와 같다.

나3: x를 3으로 나눈다. x는 3으로 나누어 떨어져야 한다.
곱2: x에 2를 곱한다.
나3곱2 게임을 진행하면서, 만든 수를 모두 기록하면 수열 A를 만들 수 있다. 예를 들어, x = 9, N = 6이고, 적용한 연산이 곱2, 곱2, 나3, 곱2, 나3인 경우에 A = [9, 18, 36, 12, 24, 8] 이다.

수열 A의 순서를 섞은 수열 B가 주어졌을 때, 수열 A를 구해보자.

입력
첫째 줄에 수열의 크기 N(2 ≤ N ≤ 100)이 주어진다. 둘째 줄에는 수열 B가 주어진다. B에 포함된 원소는 1018 보다 작거나 같은 자연수이다.

출력
나3곱2 게임의 결과 수열 A를 출력한다. 항상 정답이 존재하는 경우에만 입력으로 주어지며, 가능한 정답이 여러가지인 경우에는 아무거나 출력한다.

예제 입력 1
6
4 8 6 3 12 9
예제 출력 1
9 3 6 12 4 8
예제 입력 2
4
42 28 84 126
예제 출력 2
126 42 84 28
출처
문제를 번역한 사람: baekjoon
문제의 오타를 찾은 사람: orihehe, rdd6584
알고리즘 분류
수학
정렬
해 구성하기
 */
/*
dfs로 문제에서 주어진 연산 규칙을 수행하였을 때 가능한 경로를 찾아서 해당 경로를 출력하면 될 것이라고 생각했다.
permutation_A,B,C 세 가지를 만든 이유, A는 연산을 통해 가능한 경로를 저장하고, B는 입력으로 주어진 수열B에 해당 + 이미 도달한 숫자에 대해 중복 여부 판별(add,remove)
C는 입력으로 주어진 수열B의 모든 원소를 첫 시작 숫자로 dfs를 수행해야하므로 원본을 저장하여, java.util.ConcurrentModificationException에러 발생 방지한다.
(permutation_B를 연산 과정에서 원소를 지울 경우, for()를 통해 참조할 때 에러가 발생)
 */
public class BOJ16936 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static boolean flag;
    static ArrayList<Long> permutation_A, permutation_B, permutation_C;
    static StringBuilder sb;


    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(long num : permutation_C) {
            add_A_remove_B(num);
            dfs(0,num);
            remove_A_add_B(num);
        }
    }

    static void dfs(int depth, long num) {
        if(flag) {
            return;
        }

        if(depth == N-1) {
            for(long n : permutation_A) {
                sb.append(n).append(" ");
            }

            System.out.println(sb.toString());
            flag = true;
            return;
        }

        int idx;
        long next_num;

        if(num % 3 == 0 && isExist(num / 3)) {
            idx = permutation_B.indexOf(num / 3);
            next_num = permutation_B.get(idx);

            add_A_remove_B(num);
            dfs(depth + 1, next_num);
            remove_A_add_B(num);
        }

        if(isExist(2 * num)) {
            idx = permutation_B.indexOf(2 * num);
            next_num = permutation_B.get(idx);

            add_A_remove_B(num);
            dfs(depth + 1, next_num);
            remove_A_add_B(num);
        }
    }

    static void add_A_remove_B(long num) {
        permutation_A.add(num);
        permutation_B.remove(num);
    }

    static void remove_A_add_B(long num) {
        permutation_A.remove(num);
        permutation_B.add(num);
    }

    static boolean isExist(long num) {
        if(permutation_B.contains(num)) {
            return true;
        }
        return false;
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        permutation_A = new ArrayList<>();
        permutation_B = new ArrayList<>();
        permutation_C = new ArrayList<>();
        sb = new StringBuilder();
        flag = false;

        permutation_B = (ArrayList<Long>) Arrays.stream(br.readLine().split(" "))
                        .mapToLong(Long::parseLong)
                        .boxed()
                        .collect(Collectors.toList());

        permutation_C.addAll(permutation_B);
    }
}
