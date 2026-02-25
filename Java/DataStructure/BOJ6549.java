package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
히스토그램에서 가장 큰 직사각형 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	68668	19892	13262	28.442%
문제
히스토그램은 직사각형 여러 개가 아래쪽으로 정렬되어 있는 도형이다. 각 직사각형은 같은 너비를 가지고 있지만, 높이는 서로 다를 수도 있다. 예를 들어, 왼쪽 그림은 높이가 2, 1, 4, 5, 1, 3, 3이고 너비가 1인 직사각형으로 이루어진 히스토그램이다.



히스토그램에서 가장 넓이가 큰 직사각형을 구하는 프로그램을 작성하시오.

입력
입력은 테스트 케이스 여러 개로 이루어져 있다. 각 테스트 케이스는 한 줄로 이루어져 있고, 직사각형의 수 n이 가장 처음으로 주어진다. (1 ≤ n ≤ 100,000) 그 다음 n개의 정수 h1, ..., hn (0 ≤ hi ≤ 1,000,000,000)가 주어진다. 이 숫자들은 히스토그램에 있는 직사각형의 높이이며, 왼쪽부터 오른쪽까지 순서대로 주어진다. 모든 직사각형의 너비는 1이고, 입력의 마지막 줄에는 0이 하나 주어진다.

출력
각 테스트 케이스에 대해서, 히스토그램에서 가장 넓이가 큰 직사각형의 넓이를 출력한다.

예제 입력 1
7 2 1 4 5 1 3 3
4 1000 1000 1000 1000
0
예제 출력 1
8
4000
출처
Contest > University of Ulm Local Contest > University of Ulm Local Contest 2003 H번

문제를 번역한 사람: baekjoon
데이터를 추가한 사람: flareon078, kth004, Lawali
어색한 표현을 찾은 사람: eric00513
알고리즘 분류
자료 구조
세그먼트 트리
분할 정복
스택
 */
public class BOJ6549 {
    public static void main(String[] args) throws IOException {
        Solve_SegmentTree_helper task = new Solve_SegmentTree_helper();
        task.solve();

        Solve_Stack task2 = new Solve_Stack();
        task2.solve();
    }

    /*
        Stack DataStructure Solve
        앞선 높이를 stack에 담고, 순차적으로 다음 높이를 검사한다.
        이때, stack.top인 높이보다 현재 높이보다 낮다면, 지금까지의 가로와 stack에 저장된 높이만큼 곱하여 넓이를 구하여 업데이트한다.
        stack.top인 높이보다 높다면, stack에 담는다.

        N개의 높이 배열만큼 모두 순환한 후, 높이들의 넓이를 구하여 ans에 업데이트하여 최대 넓이를 구한다.
     */
    private static class Solve_Stack {
        private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        private int N;
        private int[] heights;
        private StringBuilder ans = new StringBuilder();

        void solve() throws IOException {

        }

        private boolean init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            if(input[0].equals("0")) return false;

            N = Integer.parseInt(input[0]);

            heights = Arrays.stream(input,1,input.length)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            return true;
        }
    }

    /*
        질문게시판 코드 참조
     */
    private static class Solve_SegmentTree_helper {
        private class Segment_tree {
            private int[] s_tree;

            public Segment_tree(int[] arr) {
                s_tree = new int[4 * arr.length];
                init(arr,0,arr.length - 1,1);
            }

            // [s,e] 구간의 최소 높이의 인덱스를 저장하는 세그먼트 트리 생성
            private int init(int[] arr, int s, int e, int n) {
                if(s == e) return s_tree[n] = s;
                int m = (s + e) / 2;
                int l = init(arr, s, m, 2 * n);
                int r = init(arr, m + 1, e, 2 * n + 1);
                return s_tree[n] = arr[l] < arr[r] ? l : r;
            }

            // [s,e] 구간 내에서 또다른 부분 구간 [l,r]에서의 최소 높이를 갖는 인덱스를 반환
            int query(int[] arr, int l, int r, int s, int e, int n) {
                if(l > e || r < s) return -1;
                if(l >= s && r <= e) return s_tree[n];
                int m = (l + r) / 2;
                int nl = query(arr, l, m, s, e, n * 2);
                int nr = query(arr, m + 1, r, s, e, n * 2 + 1);
                if(nl == -1) return nr;
                if(nr == - 1) return nl;
                return arr[nl] < arr[nr] ? nl : nr;
            }
        }
        private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        private int N;
        private int[] heights;
        private Segment_tree stree;
        private StringBuilder ans = new StringBuilder();

        void solve() throws IOException {
            while(init_setting()) {
                ans.append(find_largest_rectangle(heights, 0, heights.length - 1)).append("\n");
            }

            System.out.println(ans.toString());
        }

        /*
            특정 구간에서 최소 높이를 찾은 후, 최소 높이 인덱스를 기준으로 구간을 나누어 최대 넓이를 구한 후, 이를 반복하여 최대 넓이 값을 구한다.
         */
        private int find_largest_rectangle(int[] h, int s, int e) {
            int lower_height_idx = stree.query(h,0, h.length - 1, s, e, 1);

            int area = (e - s + 1) * h[lower_height_idx];

            if(s <= lower_height_idx - 1) {
                area = Math.max(area, find_largest_rectangle(h, s, lower_height_idx - 1));
            }

            if(lower_height_idx + 1 <= e) {
                area = Math.max(area, find_largest_rectangle(h, lower_height_idx + 1, e));
            }

            return area;
        }

        private boolean init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            if(input[0].equals("0")) return false;

            N = Integer.parseInt(input[0]);

            heights = Arrays.stream(input,1,input.length)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            stree = new Segment_tree(heights);

            return true;
        }
    }
}
