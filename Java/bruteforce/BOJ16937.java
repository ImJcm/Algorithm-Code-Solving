package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

/*
두 스티커

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	4729	1822	1359	38.046%
문제
크기가 H×W인 모눈종이와 스티커 N개가 있다. i번째 스티커의 크기는 Ri×Ci이다. 모눈종이는 크기가 1×1인 칸으로 나누어져 있으며, 간격 1을 두고 선이 그어져 있다.

오늘은 모눈종이에 스티커 2개를 붙이려고 한다. 스티커의 변은 격자의 선과 일치하게 붙여야 하고, 두 스티커가 서로 겹치면 안 된다. 단, 스티커가 접하는 것은 가능하다. 스티커를 90도 회전시키는 것은 가능하다. 스티커가 모눈종이를 벗어나는 것은 불가능하다.

두 스티커가 붙여진 넓이의 최댓값을 구해보자.

입력
첫째 줄에 모눈종이의 크기 H, W, 둘째 줄에 스티커의 수 N이 주어진다. 다음 N개의 줄에는 스티커의 크기 Ri, Ci가 주어진다.

출력
첫째 줄에 두 스티커가 붙여진 넓이의 최댓값을 출력한다. 두 스티커를 붙일 수 없는 경우에는 0을 출력한다.

제한
1 ≤ H, W, N ≤ 100
1 ≤ Ri, Ci ≤ 100
예제 입력 1
2 2
2
1 2
2 1
예제 출력 1
4
예제 입력 2
10 9
4
2 3
1 1
5 10
9 11
예제 출력 2
56
예제 입력 3
10 10
3
6 6
7 7
20 5
예제 출력 3
0
출처
문제를 번역한 사람: baekjoon
알고리즘 분류
구현
브루트포스 알고리즘
기하학
많은 조건 분기
 */
/*
알고리즘의 핵심
1. 스터커는 회전할 수 있고 두 개의 스티커는 접할 수 있다.
2. 임의의 두 스티커를 고르고 모형을 만들었을 때, 입력으로 주어진 H와 W보다 작은 높이와 너비를 갖는 모형의 넓이를 구한다.
3. 두 스티커를 선택하여 만들 수 있는 모형의 경우의 수는 총 8가지
4. (스티커1 회전/회전x) * (스티커2 회전/회전x) * (스티커1, 스티커2를 위아래/좌우로 붙이기) = 2 x 2 x 2 = 8가지
 */
public class BOJ16937 {
    static class BOJ16937_sticker {
        int h,w;

        BOJ16937_sticker(int h, int w) {
            this.h = h;
            this.w = w;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,H,W,max_area;
    static ArrayList<BOJ16937_sticker> stickers;


    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        for(int i=0;i<stickers.size()-1;i++) {
            BOJ16937_sticker s1 = stickers.get(i);

            for(int j=i+1;j<stickers.size();j++) {
                BOJ16937_sticker s2 = stickers.get(j);

                for(int k=0;k<8;k++) {
                    int sum_h = 0;
                    int sum_w = 0;
                    int s1_area = s1.h * s1.w;
                    int s2_area = s2.h * s2.w;

                    switch (k) {
                        // (스티커 형태/N도 회전)
                        case 0:
                            /*
                                s1/0
                                s2/0
                             */
                            sum_h = s1.h + s2.h;
                            sum_w = Math.max(s1.w, s2.w);
                            break;
                        case 1:
                            /*
                                s1/0 s2/0
                             */
                            sum_h = Math.max(s1.h, s2.h);
                            sum_w = s1.w + s2.w;
                            break;
                        case 2:
                            /*
                                s1/0
                                s2/90
                             */
                            sum_h = s1.h + s2.w;
                            sum_w = Math.max(s1.w, s2.h);
                            break;
                        case 3:
                            /*
                                s1/0 s2/90
                             */
                            sum_h = Math.max(s1.h, s2.w);
                            sum_w = s1.w + s2.h;
                            break;
                        case 4:
                            /*
                                s1/90
                                s2/0
                             */
                            sum_h = s1.w + s2.h;
                            sum_w = Math.max(s1.h, s2.w);
                            break;
                        case 5:
                            /*
                                s1/90 s2/0
                             */
                            sum_h = Math.max(s1.w, s2.h);
                            sum_w = s1.h + s2.w;
                            break;
                        case 6:
                            /*
                                s1/90
                                s2/90
                             */
                            sum_h = s1.w + s2.w;
                            sum_w = Math.max(s1.h, s2.h);
                            break;
                        case 7:
                            /*
                                s1/90 s2/90
                             */
                            sum_h = Math.max(s1.w, s2.w);
                            sum_w = s1.h + s2.h;
                            break;
                    }

                    if(sum_h > H || sum_w > W) continue;

                    max_area = Math.max(max_area, s1_area + s2_area);
                }
            }
        }

        System.out.println(max_area == Integer.MIN_VALUE ? 0 : max_area);
    }

    static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        H = Integer.parseInt(input[0]);
        W = Integer.parseInt(input[1]);

        N = Integer.parseInt(br.readLine());

        stickers = new ArrayList<>();

        max_area = Integer.MIN_VALUE;

        for(int i=0;i<N;i++) {
            input = br.readLine().split(" ");

            int h = Integer.parseInt(input[0]);
            int w = Integer.parseInt(input[1]);

            stickers.add(new BOJ16937_sticker(h,w));
        }

        stickers.sort(new Comparator<BOJ16937_sticker>() {
            @Override
            public int compare(BOJ16937_sticker o1, BOJ16937_sticker o2) {
                return (o2.h * o2.w) - (o1.h * o1.w);
            }
        });
    }
}
