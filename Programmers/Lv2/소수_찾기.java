package Lv2;

/*
소수 찾기
제출 내역
문제 설명
한자리 숫자가 적힌 종이 조각이 흩어져있습니다. 흩어진 종이 조각을 붙여 소수를 몇 개 만들 수 있는지 알아내려 합니다.

각 종이 조각에 적힌 숫자가 적힌 문자열 numbers가 주어졌을 때, 종이 조각으로 만들 수 있는 소수가 몇 개인지 return 하도록 solution 함수를 완성해주세요.

제한사항
numbers는 길이 1 이상 7 이하인 문자열입니다.
numbers는 0~9까지 숫자만으로 이루어져 있습니다.
"013"은 0, 1, 3 숫자가 적힌 종이 조각이 흩어져있다는 의미입니다.
입출력 예
numbers	return
"17"	3
"011"	2
입출력 예 설명
예제 #1
[1, 7]으로는 소수 [7, 17, 71]를 만들 수 있습니다.

예제 #2
[0, 1, 1]으로는 소수 [11, 101]를 만들 수 있습니다.

11과 011은 같은 숫자로 취급합니다.
 */
/*
알고리즘 핵심
에라토스테네스의 체 + 완전 탐색
1. 최대 7자리 수로 만들 수 있는 소수를 찾기 때문에 10_000_000이하의 값까지 소수를 에라스토테네스의 체로 구한다.
2. numbers로 구할 수 있는 중복없는 모든 경우의 숫자인 문자열을 소수인지 판별한 후, ans를 업데이트한다.
(numbers에서 해당 값을 넣는 경우와 안넣는 경우 모두 고려하고 중복이 없어야 한다.)
 */
public class 소수_찾기 {
    static void main() {
        String numbers = "143";

        Solve task = new Solve();
        System.out.println(task.solution(numbers));
    }

    private static class Solve {
        private final int NUMBER_LIMIT = 10_000_000;
        private int ans;
        private boolean[] decimal,visited,prime_visited;

        public int solution(String numbers) {
            init_setting(numbers);

            seive_of_eratosthenes();

            make_decimal(0, "", numbers.toCharArray());

            return ans;
        }
        
        private void make_decimal(int l, String num, char[] target) {
            if(l == target.length) {
                if(num.isEmpty()) return;
                int res = Integer.parseInt(num);
                if(!decimal[res] && !prime_visited[res]) {
                    prime_visited[res] = true;
                    ans++;
                }
                return;
            }

            for(int i = 0; i < target.length; i++) {
                make_decimal(l + 1, num, target);
                if(visited[i]) continue;
                visited[i] = true;
                String nnum = num + target[i];
                make_decimal(l + 1, nnum, target);
                visited[i] = false;
            }
        } 

        private void seive_of_eratosthenes() {
            for(int i = 2; i < NUMBER_LIMIT / 2; i++) {
                for(int j = 2; i * j < NUMBER_LIMIT; j++) {
                    decimal[i * j] = true;
                }
            }
        }

        private void init_setting(String numbers) {
            decimal = new boolean[NUMBER_LIMIT];
            visited = new boolean[numbers.length()];
            prime_visited = new boolean[NUMBER_LIMIT];

            decimal[0] = decimal[1] = true;
            prime_visited[0] = prime_visited[1] = true;
            ans = 0;
        }
    }
}
