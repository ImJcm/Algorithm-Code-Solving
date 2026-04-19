package divideConquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
트리의 순회

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
5 초	128 MB	39235	14322	10038	33.466%
문제
n개의 정점을 갖는 이진 트리의 정점에 1부터 n까지의 번호가 중복 없이 매겨져 있다. 이와 같은 이진 트리의 인오더와 포스트오더가 주어졌을 때, 프리오더를 구하는 프로그램을 작성하시오.
입력
첫째 줄에 n(1 ≤ n ≤ 100,000)이 주어진다. 다음 줄에는 인오더를 나타내는 n개의 자연수가 주어지고, 그 다음 줄에는 같은 식으로 포스트오더가 주어진다.

출력
첫째 줄에 프리오더를 출력한다.

예제 입력 1
3
1 2 3
1 3 2
예제 출력 1
2 1 3
출처
잘못된 데이터를 찾은 사람: tncks0121
알고리즘 분류
트리
분할 정복
재귀
 */
/*
알고리즘 핵심
분할 정복 + 트리
1. 중위 순회(in-order)를 통해 root 기준으로 좌/우측 자식 서브트리의 범위를 특정할 수 있고, 후위 순회(post-order)를 통해 root node를 특정할 수 있다.
2. 다음과 같은 규칙을 따른다.
2-1. 지정된 범위에서 후위 순회의 마지막 값은 항상 root 노드이다.
2-2. 루트 노드의 값을 중위 순회에서 위치를 찾는다.
2-3. root index 기준으로 중위 순회 배열에서 좌측 서브 트리, 우측 서브 트리를 나눈다.
2-4. 좌측, 우측 서브 트리를 나누는 과정을 후위 순회에도 적용하기 위해 좌측 서브 트리는 (l2, r2 - (r1 - root_index + 1)), 우측 서브 트리는 (root_index - l1, r2 - 1)이다.
(r1 - root_index : root index를 기준으로 좌측 서브 트리의 노드 개수, -1 : 왼쪽 서브 트리에서 root node를 나타내기 위해 post_order의 index를 맞추기 위함) (0 ~ N - 1 : index range)
(후위 순위 배열에서 root node의 위치를 기준으로 l1 ~ r1 사이에서 좌측, 우측 서브 트리의 개수가 정해지므로 root_index - l1으로 우측 서브 트리의 시작 지점을 맞춘다.)
3. 좌측 서브 트리 : (l1, root_index - 1, l2, r2 - (root_index - r1 + 1)), 우측 서브 트리 : (root_index + 1, r1, root_index - l1, r2 - 1)
(우측 서브 트리의 r2 => r2 - 1인 이유 : l2,r2의 범위의 후위 순회 배열에서 마지막 값이 root 노드이기 때문이다.)\

문제를 풀면서 중위, 후위 순위를 사용하여 전위를 특정하는 방법을 TC를 순차적으로 만들어 가면서 규칙을 찾아보려고 했다.
각종 TC를 보고 후위 순회의 마지막 값은 root 노드를 나타내는 것을 알 수 있었고, 해당 값이 중위 순회 배열에서 위치를 기점으로 좌측과 우측의 서브 트리를 나뉜다는 것을
알 수 있었다.

-> 아래 예시는 좌측, 우측 서브 트리의 시뮬레이션을 통해 좌표값의 규칙을 찾을 수 있었다.
12
8 4 2 5 9 1 6 10 3 11 7 12
8 4 9 5 2 10 6 11 12 7 3 1

-> 아래 예시는 중위 순회 배열에서 root의 index를 찾기위해 범위를 지정해준다. 이때, 이전의 값은 l2,r2를 지정했지만, 중위 순회 배열에서 찾아야 하므로, l1,r1으로 변경
19
8 4 2 14 9 18 15 5 10 1 16 11 6 12 3 17 19 13 7
8 4 14 18 15 9 10 5 2 16 11 12 6 19 17 13 7 3 1
 */
public class BOJ2263 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] pre_order,in_order,post_order;
    static boolean[] visited;
    static StringBuilder ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        divide_left_right_tree(0,in_order.length - 1, 0,in_order.length - 1);

        System.out.println(ans.toString());
    }

    private static void divide_left_right_tree(int l1, int r1, int l2, int r2) {
        if(l1 > r1 || l2 < 0) return;
        int root_data = post_order[r2];
        int root_idx = find_root_idx(root_data,l1,r1);

        ans.append(root_data).append(" ");

        divide_left_right_tree(l1,root_idx - 1, l2, r2 - (r1 - root_idx + 1));
        divide_left_right_tree(root_idx + 1, r1, root_idx - l1, r2 - 1);
    }

    private static int find_root_idx(int rootData, int l, int r) {
        int idx;
        for(idx = l; idx <= r; idx++) {
            if(rootData == in_order[idx]) break;
        }
        return idx;
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        pre_order = new int[N];

        in_order = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        post_order = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        visited = new boolean[N + 1];

        ans = new StringBuilder();
    }
}
