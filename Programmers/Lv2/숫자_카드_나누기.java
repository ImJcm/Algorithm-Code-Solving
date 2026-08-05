package Lv2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/*
숫자 카드 나누기
제출 내역
문제 설명
철수와 영희는 선생님으로부터 숫자가 하나씩 적힌 카드들을 절반씩 나눠서 가진 후, 다음 두 조건 중 하나를 만족하는 가장 큰 양의 정수 a의 값을 구하려고 합니다.

철수가 가진 카드들에 적힌 모든 숫자를 나눌 수 있고 영희가 가진 카드들에 적힌 모든 숫자들 중 하나도 나눌 수 없는 양의 정수 a
영희가 가진 카드들에 적힌 모든 숫자를 나눌 수 있고, 철수가 가진 카드들에 적힌 모든 숫자들 중 하나도 나눌 수 없는 양의 정수 a
예를 들어, 카드들에 10, 5, 20, 17이 적혀 있는 경우에 대해 생각해 봅시다. 만약, 철수가 [10, 17]이 적힌 카드를 갖고, 영희가 [5, 20]이 적힌 카드를 갖는다면 두 조건 중 하나를 만족하는 양의 정수 a는 존재하지 않습니다. 하지만, 철수가 [10, 20]이 적힌 카드를 갖고, 영희가 [5, 17]이 적힌 카드를 갖는다면, 철수가 가진 카드들의 숫자는 모두 10으로 나눌 수 있고, 영희가 가진 카드들의 숫자는 모두 10으로 나눌 수 없습니다. 따라서 철수와 영희는 각각 [10, 20]이 적힌 카드, [5, 17]이 적힌 카드로 나눠 가졌다면 조건에 해당하는 양의 정수 a는 10이 됩니다.

철수가 가진 카드에 적힌 숫자들을 나타내는 정수 배열 arrayA와 영희가 가진 카드에 적힌 숫자들을 나타내는 정수 배열 arrayB가 주어졌을 때, 주어진 조건을 만족하는 가장 큰 양의 정수 a를 return하도록 solution 함수를 완성해 주세요. 만약, 조건을 만족하는 a가 없다면, 0을 return 해 주세요.

제한사항
제한사항

1 ≤ arrayA의 길이 = arrayB의 길이 ≤ 500,000
1 ≤ arrayA의 원소, arrayB의 원소 ≤ 100,000,000
arrayA와 arrayB에는 중복된 원소가 있을 수 있습니다.
입출력 예
arrayA	arrayB	result
[10, 17]	[5, 20]	0
[10, 20]	[5, 17]	10
[14, 35, 119]	[18, 30, 102]	7
입출력 예 설명
입출력 예 #1

문제 예시와 같습니다.
입출력 예 #2

문제 예시와 같습니다.
입출력 예 #3

철수가 가진 카드에 적힌 숫자들은 모두 3으로 나눌 수 없고, 영희가 가진 카드에 적힌 숫자는 모두 3으로 나눌 수 있습니다. 따라서 3은 조건에 해당하는 양의 정수입니다. 하지만, 철수가 가진 카드들에 적힌 숫자들은 모두 7로 나눌 수 있고, 영희가 가진 카드들에 적힌 숫자는 모두 7로 나눌 수 없습니다. 따라서 최대값인 7을 return 합니다.
 */
/*
알고리즘 핵심
백트랙킹 + 구현 + (feat. prime factorization:소인수 분해)
1. A,B의 배열에서 문제의 조건을 만족하기 위해서는 한쪽 배열에서의 모든 수가 나누어져야 하므로 하나의 수를 기준으로 잡는다.
2. 1번에서 잡은 기준의 수는 각 배열을 오름차순으로 정렬하여 가장 작은 값을 소인수분해하여 가능한 수를 구한다.
3. 해당 수를 A,B배열에서 조건이 만족되는 수중 큰 값을 ans에 갱신한다.
 */
public class 숫자_카드_나누기 {
    static void main() {
        int[] arrayA = new int[] {
                14,35,119
        };

        int[] arrayB = new int[] {
                18,30,102
                //100000000,100000000,100000000
        };

        Solve task = new Solve();
        System.out.println(task.solution(arrayA, arrayB));
    }

    private static class Solve {
        private int ans;
        private ArrayList<Integer> prime_listA, prime_listB;
        private HashSet<Integer> AA, AB;
        private int[] sorted_arrayA, sorted_arrayB;

        public int solution(int[] arrayA, int[] arrayB) {
            init_setting(arrayA, arrayB);

            prime_factorization(prime_listA, sorted_arrayA[0]);
            prime_factorization(prime_listB, sorted_arrayB[0]);

            make_possible_number(0,1,prime_listA,AA);
            make_possible_number(0,1,prime_listB,AB);

            verification(AA, sorted_arrayA, sorted_arrayB);
            verification(AB, sorted_arrayB, sorted_arrayA);

            return ans;
        }

        private void verification(HashSet<Integer> array, int[] origin, int[] target) {
            boolean flag;

            for(Integer i : array) {
                flag = true;

                for(int j = 0; j < origin.length && flag; j++) {
                    if(origin[j] % i != 0) flag = false;
                    if(target[j] % i == 0) flag = false;
                }

                if(flag) ans = Math.max(ans, i);
            }
        }

        private void make_possible_number(int idx, int r, ArrayList<Integer> list, HashSet<Integer> Alist) {
            if(idx == list.size()) {
                if(r != 1) Alist.add(r);
                return;
            }

            make_possible_number(idx+1, r * list.get(idx), list, Alist);
            make_possible_number(idx+1, r, list, Alist);
        }

        private void prime_factorization(ArrayList<Integer> arr, int n) {
            int num = 2;

            while(n != 1 && num <= n) {
                if(n % num == 0) {
                    arr.add(num);
                    n /= num;
                } else num++;
            }
        }

        private void init_setting(int[] arrayA, int[] arrayB) {
            ans = 0;

            prime_listA = new ArrayList<>();
            prime_listB = new ArrayList<>();

            AA = new HashSet<>();
            AB = new HashSet<>();

            sorted_arrayA = Arrays.stream(arrayA).sorted().toArray();
            sorted_arrayB = Arrays.stream(arrayB).sorted().toArray();
        }
    }
}
