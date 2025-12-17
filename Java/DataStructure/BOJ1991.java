package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
트리 순회

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	71540	46715	35932	67.183%
문제
이진 트리를 입력받아 전위 순회(preorder traversal), 중위 순회(inorder traversal), 후위 순회(postorder traversal)한 결과를 출력하는 프로그램을 작성하시오.



예를 들어 위와 같은 이진 트리가 입력되면,

전위 순회한 결과 : ABDCEFG // (루트) (왼쪽 자식) (오른쪽 자식)
중위 순회한 결과 : DBAECFG // (왼쪽 자식) (루트) (오른쪽 자식)
후위 순회한 결과 : DBEGFCA // (왼쪽 자식) (오른쪽 자식) (루트)
가 된다.

입력
첫째 줄에는 이진 트리의 노드의 개수 N(1 ≤ N ≤ 26)이 주어진다. 둘째 줄부터 N개의 줄에 걸쳐 각 노드와 그의 왼쪽 자식 노드, 오른쪽 자식 노드가 주어진다. 노드의 이름은 A부터 차례대로 알파벳 대문자로 매겨지며, 항상 A가 루트 노드가 된다. 자식 노드가 없는 경우에는 .으로 표현한다.

출력
첫째 줄에 전위 순회, 둘째 줄에 중위 순회, 셋째 줄에 후위 순회한 결과를 출력한다. 각 줄에 N개의 알파벳을 공백 없이 출력하면 된다.

예제 입력 1
7
A B C
B D .
C E F
E . .
F . G
D . .
G . .
예제 출력 1
ABDCEFG
DBAECFG
DBEGFCA
출처
데이터를 추가한 사람: ekffjaos553
알고리즘 분류
트리
집합과 맵
재귀
 */
/*
알고리즘 핵심
자료구조 (tree - preorder, inorder, postorder)
1. N(A,B,...)개의 root, left_child, right_child를 갖는 구조체를 만들고, 입력으로 주어지는 연결 상태를 업데이트한다.
2. 전위 순환(preorder)의 경우, 메인 노드의 root가 먼저 탐색되는 구조이므로 main.root를 저장하고, left, right 순으로 재귀 탐색한다.
중위 순환(inorder)의 경우, 메인 노드의 root가 두번째로 탐색되는 구조이므로 left 탐색을 마친 후, main.root를 저장하고, right 순서로 재귀 탐색한다.
후위 순환(postorder)의 경우, 메인 노드의 root가 세번째로 탐색되는 구조이므로 left 탐색하고, right 탐색을 마친 후, main.root를 저장하고, 재귀 탐색한다.
 */
public class BOJ1991 {
    static class BOJ1991_node {
        char root;
        BOJ1991_node left_child, right_child;

        BOJ1991_node(char root, BOJ1991_node lc, BOJ1991_node rc) {
            this.root = root;
            this.left_child = lc;
            this.right_child = rc;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static String res;
    static BOJ1991_node[] trees;
    static StringBuilder ans;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        BOJ1991_node main_root = trees[0];

        res = new String();

        preorder_traversal(main_root);
        ans.append(res).append("\n");

        res = "";
        inorder_traversal(main_root);
        ans.append(res).append("\n");

        res = "";
        postorder_traversal(main_root);
        ans.append(res).append("\n");

        System.out.println(ans.toString());
    }

    private static void preorder_traversal(BOJ1991_node node) {
        res += node.root;
        if(node.left_child != null) preorder_traversal(node.left_child);
        if(node.right_child != null) preorder_traversal(node.right_child);
    }

    private static void inorder_traversal(BOJ1991_node node) {
        if(node.left_child != null) inorder_traversal(node.left_child);
        res += node.root;
        if(node.right_child != null) inorder_traversal(node.right_child);
    }

    private static void postorder_traversal(BOJ1991_node node) {
        if(node.left_child != null) postorder_traversal(node.left_child);
        if(node.right_child != null) postorder_traversal(node.right_child);
        res += node.root;
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        trees = new BOJ1991_node[N];

        char ch = 'A';

        for(int n = 0; n < N; n++) {
            trees[n] = new BOJ1991_node((char)(ch + n),null,null);
        }

        for(int i = 0; i < N; i++) {
            String[] connect_info = br.readLine().split(" ");

            int idx = connect_info[0].charAt(0) - 'A';

            if(!connect_info[1].equals(".")) {
                int l_idx = connect_info[1].charAt(0) - 'A';
                trees[idx].left_child = trees[l_idx];
            }

            if(!connect_info[2].equals(".")) {
                int r_idx = connect_info[2].charAt(0) - 'A';
                trees[idx].right_child = trees[r_idx];
            }
        }

        ans = new StringBuilder();
    }
}
