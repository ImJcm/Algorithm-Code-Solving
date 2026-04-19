package Programmers;
/*
문제 설명
피보나치 수는 F(0) = 0, F(1) = 1일 때, 1 이상의 n에 대하여 F(n) = F(n-1) + F(n-2) 가 적용되는 수 입니다.

예를들어

F(2) = F(0) + F(1) = 0 + 1 = 1
F(3) = F(1) + F(2) = 1 + 1 = 2
F(4) = F(2) + F(3) = 1 + 2 = 3
F(5) = F(3) + F(4) = 2 + 3 = 5
와 같이 이어집니다.

2 이상의 n이 입력되었을 때, n번째 피보나치 수를 1234567으로 나눈 나머지를 리턴하는 함수, solution을 완성해 주세요.

제한 사항
n은 2 이상 100,000 이하인 자연수입니다.
입출력 예
n	return
3	2
5	5
입출력 예 설명
피보나치수는 0번째부터 0, 1, 1, 2, 3, 5, ... 와 같이 이어집니다.

문제가 잘 안풀린다면😢
힌트가 필요한가요? [코딩테스트 연습 힌트 모음집]으로 오세요! → 클릭
 */
/*
    fibo[i] = fibo[i-1] + fibo[i-2]; 이렇게 표현하는 경우, n이 큰 수일 때, int에 저장할 수 있는 값의 범위를 넘어설 수 있기
    때문에 long 타입 또는 문제에서 요구하는 1234567로 나눈 나머지를 피보나치 값에 넣어주는 방법을 이용해야한다.
 */
class Solution {
    public static void main(String[] args) {
        System.out.println(solution(1));
    }

    public static int solution(int n) {
        int answer = 0;

        answer = fibonacci(n);

        return answer;
    }

    public static int fibonacci(int n) {
        int[] fibo = new int[100001];
        fibo[0] = 0;
        fibo[1] = 1;

        for(int i=2;i<=n;i++) {
            //fibo[i] = fibo[i-1] + fibo[i-2];
            fibo[i] = (fibo[i-1] + fibo[i-2]) % 1234567;
        }

        return fibo[n];
    }
}
