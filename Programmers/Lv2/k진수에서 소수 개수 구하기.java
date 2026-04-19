package Programmers;

import java.util.Arrays;

/*
k진수에서 소수 개수 구하기
제출 내역
문제 설명
문제 설명
양의 정수 n이 주어집니다. 이 숫자를 k진수로 바꿨을 때, 변환된 수 안에 아래 조건에 맞는 소수(Prime number)가 몇 개인지 알아보려 합니다.

0P0처럼 소수 양쪽에 0이 있는 경우
P0처럼 소수 오른쪽에만 0이 있고 왼쪽에는 아무것도 없는 경우
0P처럼 소수 왼쪽에만 0이 있고 오른쪽에는 아무것도 없는 경우
P처럼 소수 양쪽에 아무것도 없는 경우
단, P는 각 자릿수에 0을 포함하지 않는 소수입니다.
예를 들어, 101은 P가 될 수 없습니다.
예를 들어, 437674을 3진수로 바꾸면 211020101011입니다. 여기서 찾을 수 있는 조건에 맞는 소수는 왼쪽부터 순서대로 211, 2, 11이 있으며, 총 3개입니다. (211, 2, 11을 k진법으로 보았을 때가 아닌, 10진법으로 보았을 때 소수여야 한다는 점에 주의합니다.) 211은 P0 형태에서 찾을 수 있으며, 2는 0P0에서, 11은 0P에서 찾을 수 있습니다.

정수 n과 k가 매개변수로 주어집니다. n을 k진수로 바꿨을 때, 변환된 수 안에서 찾을 수 있는 위 조건에 맞는 소수의 개수를 return 하도록 solution 함수를 완성해 주세요.

제한사항
1 ≤ n ≤ 1,000,000
3 ≤ k ≤ 10
입출력 예
n	k	result
437674	3	3
110011	10	2
 */
/*
0으로 구분한 숫자인 문자열 중 가장 큰 값까지 소수를 에라스토테네스의 체로 배열을 만드는데 배열의 크기가 커서 테스트 코드 #1, #11에서
런타임 에러가 발생한다. 따라서 에라스토테네스의 체로 소수판별을 하는 방법이 아닌 다른 방법을 이용해야 한다.

#1과 #11에서의 실패를 해결하기 위해 int -> long 타입을 매개변수로 전달받아 처리할 수 있도록 변경하였다.
 */
class Solution {
    static int[] sieve;

    public static int solution(int n, int k) {
        int count = 0;

        String[] parr = Long.toString(n,k).split("0");

        for(String p : parr) {
            if(p.isEmpty() || p.isBlank()) continue;

            if(isPrime(Long.parseLong(p))) {
                count++;
            }
        }

        return count;
    }

    //Test case #1, #11 - 실패
    public static int solution_fail(int n, int k) {
        int count = 0;

        //int 범위를 넘어서는 값이 들어올 수 있다. 최대 1,000,000 -> 3bit -> 1212210202001
        String[] parr = Integer.toString(n,k).split("0");

        for(String p : parr) {
            if(p.isEmpty() || p.isBlank()) continue;

            if(isPrime(Integer.parseInt(p))) {
                count++;
            }
        }

        return count;
    }

    static boolean isPrime(int n) {
        if(n == 1) return false;
        if(n == 2) return true;

        for(int i=2;i<=Math.sqrt(n);i++) {
            if(n % i == 0) return false;
        }
        return true;
    }

    static boolean isPrime(long n) {
        if(n == 1) return false;
        if(n == 2) return true;

        for(long i=2;i<=Math.sqrt(n);i++) {
            if(n % i == 0) return false;
        }
        return true;
    }

    public static int solution_eratos(int n, int k) {
        int count = 0, max_range = 0;

        String[] parr = Integer.toString(n,k).split("0");

        for(String p : parr) {
            if(p.isEmpty() || p.isBlank()) continue;
            int r = Integer.parseInt(p);
            max_range = Math.max(r, max_range);
        }

        eratos(max_range);

        for(String p : parr) {
            if(p.isEmpty() || p.isBlank()) continue;

            if(sieve[Integer.parseInt(p)] == 0) {
                count++;
            }
        }

        return count;
    }

    static void eratos(int range) {
        sieve = new int[range + 1];

        Arrays.fill(sieve,0);

        sieve[1] = 1;

        for(int i=2;i<=range;i++) {
            if(sieve[i] != 0) continue;

            for(int j=2*i;j<=range;j += i) {
                sieve[j] = 1;
            }
        }
    }

    public static void main(String[] args) {
        //int n = 437674;
        //int n = 883438;
        int n = 1000000;
        int k = 3;
        System.out.println(Integer.toString(n,k));
        System.out.println(solution(n,k));
    }
}