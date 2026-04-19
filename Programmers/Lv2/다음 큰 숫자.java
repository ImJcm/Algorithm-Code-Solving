package Programmers;
/*
문제 설명
자연수 n이 주어졌을 때, n의 다음 큰 숫자는 다음과 같이 정의 합니다.

조건 1. n의 다음 큰 숫자는 n보다 큰 자연수 입니다.
조건 2. n의 다음 큰 숫자와 n은 2진수로 변환했을 때 1의 갯수가 같습니다.
조건 3. n의 다음 큰 숫자는 조건 1, 2를 만족하는 수 중 가장 작은 수 입니다.
예를 들어서 78(1001110)의 다음 큰 숫자는 83(1010011)입니다.

자연수 n이 매개변수로 주어질 때, n의 다음 큰 숫자를 return 하는 solution 함수를 완성해주세요.

제한 사항
n은 1,000,000 이하의 자연수 입니다.
입출력 예
n	result
78	83
15	23
입출력 예 설명
입출력 예#1
문제 예시와 같습니다.
입출력 예#2
15(1111)의 다음 큰 숫자는 23(10111)입니다.
 */
/*
    Integer.bitCount(int) int 정수를 매개변수로 넣었을 때, 1인 bit의 개수를 반환하는 함수이다.
    정수 n을 이진수 String으로 다시, 이진수 String을 십진수 정수형으로 변환하는 과정을 하지않고 binary 1의 bit수를 반환하는
    함수를 이용하여 n+1씩 반복하면서 1 bit의 개수와 같은 n을 반환해준다.
 */
class Solution {
    public static void main(String[] args) {
        System.out.println(solution(15));
    }

    public static int solution(int n) {
        int answer = 0;
        int one_cnt = Integer.bitCount(n);

        while (true) {
            if (one_cnt == Integer.bitCount(++n)) {
                answer = n;
                break;
            }
        }
        return answer;
    }
}
/*
    아래와 같이 코드를 구현한 경우, 기본 테스트 케이스는 통과하지만 효율성 테스트는 통과하지 못하고 시간초과가 발생
    다른 방법을 찾아야했다. 로직자체는 맞지만 for문의 사용이 많아서 인것같다.
 */
/*
class Solution {
    public static void main(String[] args) {
        solution(15);
    }
    public static int solution(int n) {
        int answer = 0;
        String original = toBinary(n);
        int one_cnt = oneCnt_binary(original);

        while(true) {
            if(one_cnt == oneCnt_binary(toBinary(++n))) {
                System.out.println(toDecimal(toBinary(n)));
                break;
            }
        }
        return answer;
    }
    public static int toDecimal(String b) {
        int num = 0;
        for(int i=b.length()-1;i>=0;i--) {
            char c = b.charAt(i);
            num += Math.pow(2,(b.length()-i-1)) * Integer.parseInt(Character.toString(c));
        }
        return num;
    }

    public static String toBinary(int n) {
        String binary = "";
        while(n != 1) {
            binary = Integer.toString(n%2) + binary;
            n /= 2;
        }
        binary = Integer.toString(1) + binary;
        return binary;
    }

    public static int oneCnt_binary(String b) {
        int oneCnt=0;
        for(int i=0;i<b.length();i++) {
            if(b.charAt(i) == '1') {
                oneCnt++;
            }
        }
        return oneCnt;
    }
}*/
