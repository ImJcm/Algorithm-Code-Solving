package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
색종이 붙이기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	27738	10973	6233	36.079%
문제
<그림 1>과 같이 정사각형 모양을 한 다섯 종류의 색종이가 있다. 색종이의 크기는 1×1, 2×2, 3×3, 4×4, 5×5로 총 다섯 종류가 있으며, 각 종류의 색종이는 5개씩 가지고 있다.



<그림 1>

색종이를 크기가 10×10인 종이 위에 붙이려고 한다. 종이는 1×1 크기의 칸으로 나누어져 있으며, 각각의 칸에는 0 또는 1이 적혀 있다. 1이 적힌 칸은 모두 색종이로 덮여져야 한다. 색종이를 붙일 때는 종이의 경계 밖으로 나가서는 안되고, 겹쳐도 안 된다. 또, 칸의 경계와 일치하게 붙여야 한다. 0이 적힌 칸에는 색종이가 있으면 안 된다.

종이가 주어졌을 때, 1이 적힌 모든 칸을 붙이는데 필요한 색종이의 최소 개수를 구해보자.

입력
총 10개의 줄에 종이의 각 칸에 적힌 수가 주어진다.

출력
모든 1을 덮는데 필요한 색종이의 최소 개수를 출력한다. 1을 모두 덮는 것이 불가능한 경우에는 -1을 출력한다.

예제 입력 1
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
예제 출력 1
0
예제 입력 2
0 0 0 0 0 0 0 0 0 0
0 1 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 1 0 0 0 0 0
0 0 0 0 0 1 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
예제 출력 2
4
예제 입력 3
0 0 0 0 0 0 0 0 0 0
0 1 1 0 0 0 0 0 0 0
0 0 1 0 0 0 0 0 0 0
0 0 0 0 1 1 0 0 0 0
0 0 0 0 1 1 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
예제 출력 3
5
예제 입력 4
0 0 0 0 0 0 0 0 0 0
0 1 1 0 0 0 0 0 0 0
0 0 1 0 0 0 0 0 0 0
0 0 0 0 1 1 0 0 0 0
0 0 0 0 0 1 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
예제 출력 4
-1
예제 입력 5
0 0 0 0 0 0 0 0 0 0
0 1 1 0 0 0 0 0 0 0
0 1 1 1 0 0 0 0 0 0
0 0 1 1 1 1 1 0 0 0
0 0 0 1 1 1 1 0 0 0
0 0 0 0 1 1 1 0 0 0
0 0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
예제 출력 5
7
예제 입력 6
1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1
예제 출력 6
4
예제 입력 7
0 0 0 0 0 0 0 0 0 0
0 1 1 1 1 1 0 0 0 0
0 1 1 1 1 1 0 0 0 0
0 0 1 1 1 1 0 0 0 0
0 0 1 1 1 1 0 0 0 0
0 1 1 1 1 1 1 1 1 1
0 1 1 1 0 1 1 1 1 1
0 1 1 1 0 1 1 1 1 1
0 0 0 0 0 1 1 1 1 1
0 0 0 0 0 1 1 1 1 1
예제 출력 7
6
예제 입력 8
0 0 0 0 0 0 0 0 0 0
1 1 1 1 1 0 0 0 0 0
1 1 1 1 1 0 1 1 1 1
1 1 1 1 1 0 1 1 1 1
1 1 1 1 1 0 1 1 1 1
1 1 1 1 1 0 1 1 1 1
0 0 0 0 0 0 0 0 0 0
0 1 1 1 0 1 1 0 0 0
0 1 1 1 0 1 1 0 0 0
0 1 1 1 0 0 0 0 0 1
예제 출력 8
5
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: doju
알고리즘 분류
브루트포스 알고리즘
백트래킹
 */
/*
알고리즘 핵심
1. dfs로 1인 paper에서 색종이를 붙이는 작업 수행
2. 색종이의 넓이가 큰 순서부터 색종이를 붙이는 작업 수행
3. dfs 기저사례로 붙인 색종이의 넓이와 남은 1 공간의 크키가 같은 경우, 사용한 최소 색종이의 개수를 업데이트
4. 최초로 사용된 색종이의 개수가 업데이트 되어있다면, 최소 갯수보다 많이 사용되는 dfs의 경우, 가지치기
5. 181~189의 코드는 5가지의 색종이를 모두 붙일 수 있는지 없는지 검사를 한다. 이때, 해당 조건에 만족하지 못하는 경우는
나머지 색종이를 붙여도 불가능한 경우이므로 이전에 붙인 색종이가 잘못된 경우임을 의미하므로 해당 반복문이 종료되면 190줄의 return를 수행
이후, 1인 공간에서의 색종이를 붙이는 조건 검사를 할 필요가 없기 때문이다.
5번 알고리즘 핵심이 중요 - https://www.acmicpc.net/board/view/101322
 */
public class BOJ17136 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[][] paper;
    static int[] remain_colored_paper;
    static int ans,one_spaces;
    static boolean[][] already_attach;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        dfs(0, 0,0, 0);

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    static void dfs(int depth, int nr, int nc, int use_paper_cnt) {
        if(depth == one_spaces) {
           if(check_isFull_attach()) {
               ans = Math.min(ans, use_paper_cnt);
           }
           return;
        }

        if(ans <= use_paper_cnt) return;

        for(int r = nr; r < 10; r++) {
            for(int c = (r == nr ? nc : 0); c < 10; c++) {
                if(paper[r][c] == 0) continue;

                for(int i = 4; i >= 0; i--) {
                    if(check_attach_paper(r,c,i)) {
                        attach_paper(r,c,i);
                        int next_r = c + 1 == 10 ? r + 1 : r;
                        int next_c = c + 1 == 10 ? 0 : c + 1;
                        dfs(depth + (int) Math.pow(i+1,2), next_r, next_c,use_paper_cnt + 1);
                        detach_paper(r,c,i);
                    }
                }
                return;
            }
        }
    }

    static boolean check_isFull_attach() {
        for(int i = 0; i < 10 ; i++) {
            for(int j = 0; j < 10; j++) {
                if(paper[i][j] == 1) return false;
            }
        }
        return true;
    }

    static boolean check_attach_paper(int r, int c, int i) {
        if(remain_colored_paper[i] == 0) return false;
        if(r + i >= 10 || c + i >= 10) return false;
        for(int i1 = 0; i1 <= i; i1++) {
            for(int i2 = 0; i2 <= i; i2++) {
                if(paper[r + i1][c + i2] == 0) return false;
            }
        }
        return true;
    }

    static void attach_paper(int r, int c, int i) {
        remain_colored_paper[i] -= 1;
        for(int i1 = 0; i1 <= i; i1++) {
            for(int i2 = 0; i2 <= i; i2++) {
                paper[r + i1][c + i2] = 0;
            }
        }
    }

    static void detach_paper(int r, int c, int i) {
        remain_colored_paper[i] += 1;
        for(int i1 = 0; i1 <= i; i1++) {
            for(int i2 = 0; i2 <= i; i2++) {
                paper[r + i1][c + i2] = 1;
            }
        }
    }

    static void print() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                System.out.print(paper[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-------------------");
    }

    public static void init_setting() throws IOException{
        ans = Integer.MAX_VALUE;
        one_spaces = 0;

        remain_colored_paper = new int[] {5, 5, 5, 5, 5};
        paper = new int[10][10];
        already_attach = new boolean[10][10];

        for(int i = 0; i < 10; i++) {
            String[] input = br.readLine().split(" ");
            for(int j = 0; j < 10; j++) {
                paper[i][j] = Integer.parseInt(input[j]);
                if(paper[i][j] == 0) {
                    already_attach[i][j] = true;
                } else {
                    one_spaces++;
                }
            }
            /*paper[i] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();*/
        }

        ans = one_spaces == 0 ? 0 : ans;
    }

}
