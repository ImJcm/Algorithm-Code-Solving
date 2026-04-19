package Programmers;

import java.util.*;

/*
N개의 최소공배수
문제 설명
두 수의 최소공배수(Least Common Multiple)란 입력된 두 수의 배수 중 공통이 되는 가장 작은 숫자를 의미합니다. 예를 들어 2와 7의 최소공배수는 14가 됩니다. 정의를 확장해서, n개의 수의 최소공배수는 n 개의 수들의 배수 중 공통이 되는 가장 작은 숫자가 됩니다. n개의 숫자를 담은 배열 arr이 입력되었을 때 이 수들의 최소공배수를 반환하는 함수, solution을 완성해 주세요.

제한 사항
arr은 길이 1이상, 15이하인 배열입니다.
arr의 원소는 100 이하인 자연수입니다.
입출력 예
arr	result
[2,6,8,14]	168
[1,2,3]	6
 */
/*
    문제를 보고 최소공배수를 구하는 과정을 arr의 모든 수가 공통된 최대공약수로 나누어 질때만을 고려하는 줄알고 아래와 같이 코드를
    구현하였다. 그런데 2개 이상의 수에서 최소공배수를 구하는 경우는 두 수를 기준으로 최대공약수가 존재하면 나누어 떨어지는 수가
    아닌 경우는 그대로 값이 내려오고 나누어떨어지는 수들만 나눈 값을 내려주고 이 과정을 각각의 수들이 서로소일 때까지 반복하는 것이였다.
    https://mathbang.net/204#gsc.tab=0 - 최소공배수 개념 참고
 */
class Solution {
    public static void main(String[] args) {
        int[] arr = {14,2,7};
        System.out.println(solution(arr));
    }

    public static long solution(int[] arr) {
        long answer = 1;
        Arrays.sort(arr);
        int lastNum = arr[arr.length-1];

        //arr[]의 마지막 값으로 조건을 설정할 경우, i값에 나누어질 때 조건이 변경될 수 있기 때문에, 고정값으로 정해야 했다.
        //for(int i=2;i<=arr[arr.length-1];i++) {
        for(int i=2;i<=lastNum;i++) {
            int cnt = 0;
            for(int j=0;j<arr.length;j++) {
                if(arr[j] % i == 0) {
                    arr[j] /= i;
                    cnt++;
                }
            }
            if(cnt > 0) {
                //i로 나뉜 수가 존재할 경우, i로 한번더 나눌 수 있도록 i--;
                answer *= i--;
            }
        }

        return answer;
    }
    /*
        잘못알고있던 개념으로 푼 코드
     */
    /*public static int solution(int[] arr) {
        int answer = 1;

        Arrays.sort(arr);
        boolean chk = true;

        while(chk) {
            chk = false;
            for(int i=2;i<=arr[0];i++) {
                int j;
                for(j=0;j<arr.length;j++) {
                    if(arr[j] % i != 0) {
                        break;
                    }
                }
                if(j == arr.length) {
                    for(j=0;j<arr.length;j++) {
                        arr[j] /= i;
                    }
                    answer *= i;
                    i=2;
                }
            }
        }

        for(int i=0;i<arr.length;i++) {
            answer *= arr[i];
        }

        return answer;
    }*/
}

