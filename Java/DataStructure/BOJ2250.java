package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
트리의 높이와 너비

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	23883	7148	4939	28.600%
문제
이진트리를 다음의 규칙에 따라 행과 열에 번호가 붙어있는 격자 모양의 틀 속에 그리려고 한다. 이때 다음의 규칙에 따라 그리려고 한다.

이진트리에서 같은 레벨(level)에 있는 노드는 같은 행에 위치한다.
한 열에는 한 노드만 존재한다.
임의의 노드의 왼쪽 부트리(left subtree)에 있는 노드들은 해당 노드보다 왼쪽의 열에 위치하고, 오른쪽 부트리(right subtree)에 있는 노드들은 해당 노드보다 오른쪽의 열에 위치한다.
노드가 배치된 가장 왼쪽 열과 오른쪽 열 사이엔 아무 노드도 없이 비어있는 열은 없다.
이와 같은 규칙에 따라 이진트리를 그릴 때 각 레벨의 너비는 그 레벨에 할당된 노드 중 가장 오른쪽에 위치한 노드의 열 번호에서 가장 왼쪽에 위치한 노드의 열 번호를 뺀 값 더하기 1로 정의한다. 트리의 레벨은 가장 위쪽에 있는 루트 노드가 1이고 아래로 1씩 증가한다.

아래 그림은 어떤 이진트리를 위의 규칙에 따라 그려 본 것이다. 첫 번째 레벨의 너비는 1, 두 번째 레벨의 너비는 13, 3번째, 4번째 레벨의 너비는 각각 18이고, 5번째 레벨의 너비는 13이며, 그리고 6번째 레벨의 너비는 12이다.



우리는 주어진 이진트리를 위의 규칙에 따라 그릴 때에 너비가 가장 넓은 레벨과 그 레벨의 너비를 계산하려고 한다. 위의 그림의 예에서 너비가 가장 넓은 레벨은 3번째와 4번째로 그 너비는 18이다. 너비가 가장 넓은 레벨이 두 개 이상 있을 때는 번호가 작은 레벨을 답으로 한다. 그러므로 이 예에 대한 답은 레벨은 3이고, 너비는 18이다.

임의의 이진트리가 입력으로 주어질 때 너비가 가장 넓은 레벨과 그 레벨의 너비를 출력하는 프로그램을 작성하시오

입력
첫째 줄에 노드의 개수를 나타내는 정수 N(1 ≤ N ≤ 10,000)이 주어진다. 다음 N개의 줄에는 각 줄마다 노드 번호와 해당 노드의 왼쪽 자식 노드와 오른쪽 자식 노드의 번호가 순서대로 주어진다. 노드들의 번호는 1부터 N까지이며, 자식이 없는 경우에는 자식 노드의 번호에 -1이 주어진다.

출력
첫째 줄에 너비가 가장 넓은 레벨과 그 레벨의 너비를 순서대로 출력한다. 너비가 가장 넓은 레벨이 두 개 이상 있을 때에는 번호가 작은 레벨을 출력한다.

예제 입력 1
19
1 2 3
2 4 5
3 6 7
4 8 -1
5 9 10
6 11 12
7 13 -1
8 -1 -1
9 14 15
10 -1 -1
11 16 -1
12 -1 -1
13 17 -1
14 -1 -1
15 18 -1
16 -1 -1
17 -1 19
18 -1 -1
19 -1 -1
예제 출력 1
3 18
힌트
실제 기출문제의 문제 제목은 "이진트리의 너비" 이다.

출처
Olympiad > 한국정보올림피아드 > KOI 2003 > 고등부 1번

문제의 오타를 찾은 사람: insu_nym
데이터를 추가한 사람: pknu1535
알고리즘 분류
그래프 이론
그래프 탐색
트리
깊이 우선 탐색
 */
/*
알고리즘 핵심
이진트리 + dfs
1. 부모 노드, 좌측 자식 노드, 우측 자식 노드, 노드 식별 번호, 좌측 자식 노드의 총 개수, 우측 자식 노드의 총 개수 정보를 갖는 구조체를 사용한다.
2. 입력으로 주어진 부모,자식 노드 관계를 설정한다.
3. 입력으로 주어진 노드에서 1번 노드는 root 노드가 아니므로, 부모 노드가 없는 노드를 root 노드로 설정한다. - find_root_node()
4. root 노드를 시작으로 각 노드를 기준으로 좌측 자식 노드, 우측 자식 노드의 개수를 dfs 탐색으로 찾고 node.left_child_cnt, node.right_child_cnt값을 업데이트한다. - dfs_left_right_child_node_cnt_and_level_node(root)
5. 구성된 이진 트리에서 최대 레벨 값을 dfs를 통해 찾고, 해당 값으로 레벨 별 최대, 최소 열을 저장하는 배열을 만든다. - find_max_level_and_level_max_min_arr_setting()
6. root 노드를 시작으로 자식 노드까지 dfs로 탐색하면서 각 노드들의 col 위치를 설정한다. - dfs_column_node(root,true);
이때, 설정하는 방법은 부모노드의 col 위치를 기준으로 왼쪽 자식 노드이면, parent's col - 현재 노드의 우측 자식 노드의 개수 - 1로 설정할 수 있다.
오른쪽 자식 노드이면, parent's col + 현재 노드의 좌측 자식 노드의 개수 + 1로 설정할 수 있다.
7. root 노드를 시작으로 dfs로 자식 노드를 탐색하면서 level 별 노드의 현재 col 값을 최소, 최대 값을 업데이트한다. - dfs_same_level_node_width(root);
8. 레벨 별 최소, 최대 열을 저장한 배열에서 두 값의 차이가 최대인 레벨과 너비의 차이를 ans, level_ans에 업데이트하고 출력한다. - cal_level_width()

이 문제에서 중요한 부분은 문제의 답을 찾기 위해 각 과정에서 dfs가 사용된다는 점과 입력으로 주어진 예시에서 1이 항상 root 노드가 아닌 점을 알아야 한다.
 */
public class BOJ2250 {
    static class BOJ2250_node {
        int idx;
        BOJ2250_node parent, left_child, right_child;
        int left_child_cnt, right_child_cnt;

        BOJ2250_node(int idx,BOJ2250_node p, BOJ2250_node lc, BOJ2250_node rc, int lcc, int rcc) {
            this.idx = idx;
            this.parent = p;
            this.left_child = lc;
            this.right_child = rc;
            this.left_child_cnt = lcc;
            this.right_child_cnt = rcc;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans,ans_level,max_level;
    static BOJ2250_node[] nodes;
    static int[] level_nodes,col_nodes;
    static int[][] level_max_min_col_node;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        BOJ2250_node root = find_root_node();

        dfs_left_right_child_node_cnt_and_level_node(root);

        find_max_level_and_level_max_min_arr_setting();

        dfs_column_node(root,true);

        dfs_same_level_node_width(root);

        cal_level_width();

        System.out.println(ans_level + " " + ans);
    }

    private static BOJ2250_node find_root_node() {
        BOJ2250_node root = null;

        for(int i = 1; i <= N; i++) {
            if(nodes[i].parent == null) root = nodes[i];
        }

        return root;
    }

    private static void cal_level_width() {
        ans = 0;
        for(int i = 1; i <= max_level; i++) {
            if(ans < level_max_min_col_node[i][0] - level_max_min_col_node[i][1] + 1) {
                ans = level_max_min_col_node[i][0] - level_max_min_col_node[i][1] + 1;
                ans_level = i;
            }
        }
    }

    private static void dfs_same_level_node_width(BOJ2250_node node) {
        if(node == null) return;

        int level = level_nodes[node.idx];
        int col = col_nodes[node.idx];

        level_max_min_col_node[level][0] = Math.max(level_max_min_col_node[level][0], col);
        level_max_min_col_node[level][1] = Math.min(level_max_min_col_node[level][1], col);

        dfs_same_level_node_width(node.left_child);
        dfs_same_level_node_width(node.right_child);
    }

    private static void dfs_column_node(BOJ2250_node node, boolean isRight_subtree) {
        if(node == null) return;

        int col = node.parent == null ? 0 : col_nodes[node.parent.idx];

        if(isRight_subtree) {
            col_nodes[node.idx] = col + node.left_child_cnt + 1;
        } else {
            col_nodes[node.idx] = col - node.right_child_cnt - 1;
        }

        dfs_column_node(node.left_child, false);
        dfs_column_node(node.right_child, true);
    }

    private static void dfs_left_right_child_node_cnt_and_level_node(BOJ2250_node node) {
        if(node == null) return;

        int parent_idx = node.parent == null ? 0 : node.parent.idx;
        level_nodes[node.idx] = level_nodes[parent_idx] + 1;

        dfs_left_right_child_node_cnt_and_level_node(node.left_child);
        dfs_left_right_child_node_cnt_and_level_node(node.right_child);

        int lc = node.left_child == null ? 0 : node.left_child.left_child_cnt + node.left_child.right_child_cnt + 1;
        int rc = node.right_child == null ? 0 : node.right_child.left_child_cnt + node.right_child.right_child_cnt + 1;;

        node.left_child_cnt = lc;
        node.right_child_cnt = rc;
    }

    private static void find_max_level_and_level_max_min_arr_setting() {
        max_level = Arrays.stream(level_nodes).max().getAsInt();

        level_max_min_col_node = new int[max_level + 1][2];

        for(int[] n : level_max_min_col_node) {
            n[1] = Integer.MAX_VALUE;
        }
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        nodes = new BOJ2250_node[N + 1];
        level_nodes = new int[N + 1];
        col_nodes = new int[N + 1];

        for(int i = 1; i <= N; i++) {
            nodes[i] = new BOJ2250_node(i,null,null,null,0,0);
        }

        for(int i = 1; i <= N; i++) {
            String[] connect_info = br.readLine().split(" ");

            int root_node = Integer.parseInt(connect_info[0]);
            int left_node = Integer.parseInt(connect_info[1]);
            int right_node = Integer.parseInt(connect_info[2]);

            if(left_node != -1) {
                nodes[root_node].left_child = nodes[left_node];
                nodes[left_node].parent = nodes[root_node];
            }
            if(right_node != -1) {
                nodes[root_node].right_child = nodes[right_node];
                nodes[right_node].parent = nodes[root_node];
            }
        }
    }
}
