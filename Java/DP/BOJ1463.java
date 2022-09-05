import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;

/*
1로 만들기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.15 초 (하단 참고)	128 MB	216094	70744	45292	32.198%
문제
정수 X에 사용할 수 있는 연산은 다음과 같이 세 가지 이다.

X가 3으로 나누어 떨어지면, 3으로 나눈다.
X가 2로 나누어 떨어지면, 2로 나눈다.
1을 뺀다.
정수 N이 주어졌을 때, 위와 같은 연산 세 개를 적절히 사용해서 1을 만들려고 한다. 연산을 사용하는 횟수의 최솟값을 출력하시오.

입력
첫째 줄에 1보다 크거나 같고, 10^6보다 작거나 같은 정수 N이 주어진다.

출력
첫째 줄에 연산을 하는 횟수의 최솟값을 출력한다.

예제 입력 1
2
예제 출력 1
1
예제 입력 2
10
예제 출력 2
3
 */
/*
    A1. Time over - 일반적 dfs
    A2. DP 개념에 대해서 찾아본 뒤, Top-down 형태로 코드 제출 - 성공
    A3. Bottom-up 방식도 되지 않을까? 하여 코드 제출 - 성공
    결론: 속도, 메모리 면에서 Bottom > Top이라는 결과를 알 수 있다. 하지만, 이와 같은 결과는 문제마다 더 적합한 방식이 있는 것이므로
    아마도, bottom-up방식의 경우, 최소로 나누어지는 수를 결정하는 과정이 Top-down에 비해 적고, 재귀와 반복문의 성능때문에
    속도 + 메모리가 더 나은 것이라 생각된다.

 */
public class BOJ1463 {
    static int N;
    static int min = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
    //static int count = 0;
    static int[] mem = new int[1000001];
    static int[] mem2 = new int[1000001];

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        Arrays.fill(mem,Integer.MAX_VALUE);
        Arrays.fill(mem2,Integer.MAX_VALUE);

//        dfs(N,count);

//        Top-down
        int min2 = Math.min(topdown(N),mem2[N]);
        System.out.println("top : " + min2);

//        Bottom-up
        mem[1] = 0;
        mem[2] = mem[3] = 1;

        min = bottomup(N);
        System.out.println("bot : " + min);

    }

    //Top-down
    static int topdown(int n) {
        int a,b,c;
        a = b = c = Integer.MAX_VALUE;

        if(n <= 3) {
            if(n == 1) {
                return 0;
            }
            return 1;
        }

        if(mem2[n] == Integer.MAX_VALUE) {
            if(n % 3 == 0) a = topdown(n/3) + 1;
            if(n % 2 == 0) b = topdown(n/2) + 1;
            c = topdown(n-1) + 1;

            mem2[n] = Math.min(a,Math.min(b,c));
        }

        return mem2[n];
    }

    //bottom-up
    static int bottomup(int n) {
        int a,b,c;

        for(int i=4;i<=n;i++) {
            a = b = c = Integer.MAX_VALUE;
            if(i % 3 == 0) a = mem[i/3] + 1;
            if(i % 2 == 0) b = mem[i/2] + 1;
            c = mem[i-1] + 1;

            mem[i] = Math.min(a,Math.min(b,c));
        }

        return mem[n];

    }

    /*
    //dfs
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        //mem = new int[N];

        dfs(N,count);
        System.out.println(min);
    }

    static void dfs(int d, int c) {
        if(d == 1) {
          min = Math.min(min,c);
          return;
        }

        for(int i=3;i>0;i--) {
            if(i==3) {
                if(d % 3 == 0) {
                    dfs(d/3,c+1);
                    //c -= 1;
                }
            }

            if(i==2) {
                if(d % 2 == 0) {
                    dfs(d/2,c+1);
                    //c -= 1;
                }
            }

            if(i==1) {
                dfs(d-1,c+1);
                //c -= 1;
            }

        }
     }
     */
//        if(d % 3 == 0) {
//            dfs(d/3,c+1);
//            c -= 1;
//        }
//        else if(d % 2 == 0) {
//            dfs(d/2,c+1);
//            c -= 1;
//        }
//        else {
//            dfs(d-1,c+1);
//            c -= 1;
//        }


}
