package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/*
오아시스 재결합 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	36052	10242	7742	28.440%
문제
오아시스의 재결합 공연에 N명이 한 줄로 서서 기다리고 있다.

이 역사적인 순간을 맞이하기 위해 줄에서 기다리고 있던 백준이는 갑자기 자기가 볼 수 있는 사람의 수가 궁금해졌다.

두 사람 A와 B가 서로 볼 수 있으려면, 두 사람 사이에 A 또는 B보다 키가 큰 사람이 없어야 한다.

줄에 서 있는 사람의 키가 주어졌을 때, 서로 볼 수 있는 쌍의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 줄에서 기다리고 있는 사람의 수 N이 주어진다. (1 ≤ N ≤ 500,000)

둘째 줄부터 N개의 줄에는 각 사람의 키가 나노미터 단위로 주어진다. 모든 사람의 키는 231 나노미터 보다 작다.

사람들이 서 있는 순서대로 입력이 주어진다.

출력
서로 볼 수 있는 쌍의 수를 출력한다.

예제 입력 1
7
2
4
1
2
2
5
1
예제 출력 1
10
출처
Olympiad > Croatian Highschool Competitions in Informatics > 2007 > Croatian Olympiad in Informatics 2007 1번

문제를 번역한 사람: baekjoon
데이터를 추가한 사람: kiwiyou
알고리즘 분류
자료 구조
스택
 */
/*
알고리즘 핵심
Stack
1.

첫 접근 방식으로, 순차적으로 높이를 앞선 stack에 담아두고 쌍의 개수를 만들어가는 형태라고 생각은 했지만 코드로 구현하는 과정에서
어려움이 있었다. 간단하게 코드를 구현하였고 이를 TC와 비교하였지만 맞지 않는 경우가 있어서 틀린 로직이라고 생각하여 오래걸리게 되었다.
그래서 힌트를 참고하여 코드를 구성하려고 한다.
참고 코드 : https://www.acmicpc.net/board/view/143719
 */
public class BOJ3015 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    private static class Solve {
        private class info {
            double h;
            int l_c;

            public info(double h, int l_c) {
                this.h = h;
                this.l_c = l_c;
            }
        }
        private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        private int N;
        private long ans;
        private double[] heights;
        private Stack<info> stack;

        void solve() throws IOException {
            init_setting();

            pair_check();

            System.out.println(ans);
        }

        /*
            stack에 높이를 넣고, top의 높이보다 큰 경우, 앞선 업데이트한 쌍의 개수를 누적하는 형태인 것 같은데 구체적인 로직이 필요
            해당 로직으로 코드를 설계하는 과정에서 맞지 않는 tc 존재하여 다시 생각해야 함
            7 2 4 1 2 2 5 1
            2 - 4
            4 - 1 2 2 5
            1 - 2
            2 - 2 5
            2 - 5
            5 - 1
         */
        private void pair_check() {
            for(int i = 0; i < N; i++) {
                int l_c = 0;
                double h = 0;
                while(!stack.empty()) {
                    if(stack.peek().h > heights[i]) {
                        ans += 1;
                        break;
                    } else if(stack.peek().h < heights[i]) {
                        ans += stack.pop().l_c + 1;
                    } else { // stack.peek().h == heights[i]
                        l_c =  stack.peek().l_c + 1;
                        h = stack.pop().h;

                        ans += l_c;
                        break;
                    }
                }
                if(l_c != 0 && h != 0) stack.push(new info(h, l_c));
                else stack.push(new info(heights[i],0));
            }
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            heights = new double[N];

            for(int i = 0; i < N; i++) {
                heights[i] = Double.parseDouble(br.readLine());
            }

            stack = new Stack<>();

            ans = 0L;
        }
    }
}
