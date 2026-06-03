package Lv2;

import java.util.Arrays;
import java.util.Stack;

/*
주식가격
제출 내역
문제 설명
초 단위로 기록된 주식가격이 담긴 배열 prices가 매개변수로 주어질 때, 가격이 떨어지지 않은 기간은 몇 초인지를 return 하도록 solution 함수를 완성하세요.

제한사항
prices의 각 가격은 1 이상 10,000 이하인 자연수입니다.
prices의 길이는 2 이상 100,000 이하입니다.
입출력 예
prices	return
[1, 2, 3, 2, 3]	[4, 3, 1, 1, 0]
입출력 예 설명
1초 시점의 ₩1은 끝까지 가격이 떨어지지 않았습니다.
2초 시점의 ₩2은 끝까지 가격이 떨어지지 않았습니다.
3초 시점의 ₩3은 1초뒤에 가격이 떨어집니다. 따라서 1초간 가격이 떨어지지 않은 것으로 봅니다.
4초 시점의 ₩2은 1초간 가격이 떨어지지 않았습니다.
5초 시점의 ₩3은 0초간 가격이 떨어지지 않았습니다.
※ 공지 - 2019년 2월 28일 지문이 리뉴얼되었습니다.
 */
/*
알고리즘 핵심
Stack(DataStructure)
1. 초단위의 순차적으로 주식 가격이 주어지므로 해당 주식이 떨어지는 시간을 구하기 위해 현재의 가격과 이전 가격의 비교가 필요하다.
2. 스택을 통해 시간대별 주식의 가격을 저장하다가 현재 가격이 이전 가격과 비교한다.
2-1. 현재 가격이 이전 가격보다 적다면, 이전 시간대의 주식의 가격이 떨어지는 최초의 시기이므로 현재 시간에서 이전 가격대의 시간의 차이를 저장한다.
2-2. 현재 가격이 이전 가격보다 크다면, 이전 시간대의 주식의 가격은 떨어지지 않았으므로 현재 시간대의 주식의 가격을 스택에 저장한다.
3. 2번 과정을 모두 마친후, 떨어지는 시간대를 측정한 후, 남은 시간대의 주식가격은 최종시간과의 차이를 저장하여 모든 시간대를 구한다.
 */
public class 주식가격 {
    public static void main(String[] args) {
        int[] prices = {
                1,2,3,2,3
        };

        Solve task = new Solve();
        System.out.println(Arrays.toString(task.solution(prices)));
    }

    private static class Solve {
        private class StockData {
            int price;
            int time;

            public StockData(int price, int time) {
                this.price = price;
                this.time = time;
            }
        }
        private int[] ans;
        private Stack<StockData> stack;

        public int[] solution(int[] prices) {
            init_setting(prices);

            stock(prices);

            return ans;
        }

        private void stock(int[] prices) {
            stack.push(new StockData(prices[0], 1));

            for(int i = 1; i < prices.length; i++) {

                while(!stack.isEmpty() && stack.peek().price > prices[i]) {
                    StockData sd = stack.pop();

                    ans[sd.time - 1] = i + 1 - sd.time;
                }
                stack.push(new StockData(prices[i], i + 1));
            }

            for(int i = 0; i < prices.length; i++) {
                int et = prices.length;

                while(!stack.isEmpty()) {
                    StockData sd = stack.pop();

                    ans[sd.time - 1] = et - sd.time;
                }
            }
        }

        private void init_setting(int[] prices) {
            ans = new int[prices.length];

            Arrays.fill(ans, -1);

            stack = new Stack<>();
        }
    }
}
