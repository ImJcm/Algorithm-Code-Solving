package BOJ.math;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
//import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

/*
문제
양수 A가 N의 진짜 약수가 되려면, N이 A의 배수이고, A가 1과 N이 아니어야 한다. 어떤 수 N의 진짜 약수가 모두 주어질 때, N을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N의 진짜 약수의 개수가 주어진다. 이 개수는 50보다 작거나 같은 자연수이다. 둘째 줄에는 N의 진짜 약수가 주어진다. 1,000,000보다 작거나 같고, 2보다 크거나 같은 자연수이고, 중복되지 않는다.

출력
첫째 줄에 N을 출력한다. N은 항상 32비트 부호있는 정수로 표현할 수 있다.

예제 입력 1         예제 출력 1
2
4 2                 8

예제 입력 2         예제 출력 2
1
2                   4

예제 입력 3         예제 출력 3
6
3 4 2 12 6 8        24

예제 입력 4
14
14 26456 2 28 13228 3307 7 23149 8 6614 46298 56 4 92596
예제 출력 4
185192
/----------------------------------------------------------------------------/
public class BOJ1037 {
    static Scanner sc = new Scanner(System.in);
    static int ns_div = sc.nextInt();

    public static void main(String[] args) {
        System.out.println(solve(ns_div));
    }

    public static int solve(int ns) {
        int i=0, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int[] divs = new int[ns_div];

        while((ns_div--) > 0) {
            divs[i++] = sc.nextInt();
            if(min > divs[i-1]) min = divs[i-1];
            if(max < divs[i-1]) max = divs[i-1];
        }

        return min * max;
    }
}
 */
/*
    입력받을 시, Scanner보다 더 빠른 BufferedReader를 통해 입력받을 경우.
    입력받는 시간을 비교한 결과 Scanner보다 BufferedReader을 통해 입력받는게 더 빠르기 때문에 앞으로, BufferedReader를 사용하도록
    권장.
 */
public class BOJ1037 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuilder sb = new StringBuilder();
    static int[] num;

    public static void main(String[] args) throws Exception{
        long result = solve();
        System.out.println(result);
    }

    static long solve() throws Exception {
        int n = Integer.parseInt(br.readLine());
        String[] s = br.readLine().split(" ");
        num = new int[n];

        for(int i=0;i<n;i++) {
            num[i] = Integer.parseInt(s[i]);
        }
        Arrays.sort(num);
        return num[0] * num[n-1];
    }


}
