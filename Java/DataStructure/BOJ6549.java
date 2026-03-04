package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

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
/*
알고리즘 핵심
Data Structure (Stack, Segment Tree)
- Stack
1. 1~N까지 순차적으로 직사각형의 높이를 탐색한다.
2. 현재 높이와 위치를 stack에 담는다. 이때, 스택이 비어있지 않고 현재 높이가 스택의 데이터의 높이보다 크다면 현재 위치와 높이를 스택에 담는다.
스택이 비어있지 않고 현재 높이가 스택의 데이터의 높이보다 작다면, 스택의 높이와 해당 높이로 가능한 가로길이로 넓이를 구한다.
3. 스택을 통해 현재 높이가 스택의 데이터들 중에서 높이가 더 크다면, 현재 높이로 이전 직사각형들의 넓이에 추가될 수 있으므로 위치를 현재 높이와 함께 저장한다.
즉, 이전 직사각형의 위치(가로 길이)를 업데이트 한다.

- Segment Tree
1. Segment Tree를 이용하여 1~N구간에서의 최소 높이를 갖는 데이터를 만든다.
2. 분할 정복을 통해 구간을 나누어 해당 구간에서의 최소 높이를 구하고 구간의 길이와 최소 높이를 통해 넓이를 구한다.
3. 특정 구간에서 최소 높이를 구한 인덱스를 찾으면, 해당 구간에서의 넓이를 구하고, 최소 높이를 갖는 인덱스를 기준으로 분할하여 다음 넓이를 구한다.
 */
public class BOJ6549 {
    public static void main(String[] args) throws IOException {
        /*Solve_SegmentTree_helper task = new Solve_SegmentTree_helper();
        task.solve();*/

        Solve_Stack task2 = new Solve_Stack();
        task2.solve();
    }

    /*
        Stack DataStructure Solve
        앞선 높이를 stack에 담고, 순차적으로 다음 높이를 검사한다.
        이때, stack.top인 높이보다 현재 높이보다 낮다면, 지금까지의 가로와 stack에 저장된 높이만큼 곱하여 넓이를 구하여 업데이트한다.
        stack.top인 높이보다 높다면, stack에 담는다.

        N개의 높이 배열만큼 모두 순환한 후, 높이들의 넓이를 구하여 ans에 업데이트하여 최대 넓이를 구한다.

        처음 시도 => 틀린 코드 : 8%
        원인 : 논리 오류, 반례 : 9 4 6 8 0 10 9 7 5 3 => answer = 21 / output = 12
        오름차순으로 높아지는 높이의 직사각형의 넓이를 구하는 것은 문제없지만, 내림차순으로 이어지는 높이의 넓이를 구하는
        과정에서 이전 직사각형과의 넓이를 구하지 못하는 문제가 있다.
        해결 시도 방법:
            1. 양 끝을 기준으로 2번의 과정을 반복
                반례 : 3 5 2 5 => answer = 6 / output = 5
            2. 1~N까지 한번의 반복으로 내부에서 이전 높이를 검사하여 넓이를 추가 검사
                - 현재 높이가 이전 높이보다 작다면 이전 높이의 직사각형까지 포함하여 넓이를 계산해야 하므로, 이전 가로 길이 정보를 가지고 있어야 한다.
                따라서, stack에 저장할 정보는 현재 높이가 이전 직사각형의 높이보다 같거나 큰 경우까지의 위치와 현재 높이를 담는다.
                현재 높이가 stack.peek의 높이보다 작다면, 해당 stack 데이터의 가로 위치까지 직사각형 넓이를 계산한다.
                이유 : 이전 직사각형의 높이가 다음 직사각형의 높이보다 크다면, 다음 직시각형의 높이만큼 직시각형의 넓이를 추가할 수 있기 때문이다.
     */
    private static class Solve_Stack {
        private class info {
            int i,h;

            public info(int i, int h) {
                this.i = i;
                this.h = h;
            }
        }
        private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        private int N;
        private int[] heights;
        private Stack<info> stack;
        private StringBuilder ans = new StringBuilder();

        void solve() throws IOException {
            while(init_setting()) {
                long res = 0;
                for(int i = 0; i < N; i++) {
                    int ii = i;
                    while(!stack.isEmpty() && stack.peek().h > heights[i]) {
                        ii = stack.peek().i;
                        res = Math.max(res, ((long) (i - stack.peek().i) * stack.pop().h));
                    }
                    if(!stack.isEmpty() && stack.peek().h == heights[i]) continue;
                    if(heights[i] != 0) stack.push(new info(ii,heights[i]));
                }
                while(!stack.isEmpty()) {
                    res = Math.max(res, ((long) (N - stack.peek().i) * stack.pop().h));
                }

                ans.append(res).append("\n");
            }

            System.out.println(ans.toString());
        }

        private boolean init_setting() throws IOException {
            String[] input = br.readLine().split(" ");

            if(input[0].equals("0")) return false;

            N = Integer.parseInt(input[0]);

            heights = Arrays.stream(input,1,input.length)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            stack = new Stack<>();

            return true;
        }
    }

    /*
        질문게시판 코드 참조
        처음 시도 => 틀린 코드 8%
        원인#1 : 최대 크기의 직사각형 넓이 값이 int의 범위를 넘어갈 수 있다. int -> long 변경
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

            // [s,e] 구간 내에서 s <= l && r <= e이면, stree 값 반환
            // l,r의 값을 s,e의 값 내부로 좁히기 위해 분할하고, 최소 높이의 인덱스를 찾는다.
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
        private long find_largest_rectangle(int[] h, int s, int e) {
            int lower_height_idx = stree.query(h,0, h.length - 1, s, e, 1);

            long area = (long) (e - s + 1) * h[lower_height_idx];

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
