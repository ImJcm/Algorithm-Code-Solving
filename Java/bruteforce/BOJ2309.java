package BOJ.bruteforce;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
일곱 난쟁이 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	73581	30849	22471	43.260%
문제
왕비를 피해 일곱 난쟁이들과 함께 평화롭게 생활하고 있던 백설공주에게 위기가 찾아왔다. 일과를 마치고 돌아온 난쟁이가 일곱 명이 아닌 아홉 명이었던 것이다.

아홉 명의 난쟁이는 모두 자신이 "백설 공주와 일곱 난쟁이"의 주인공이라고 주장했다. 뛰어난 수학적 직관력을 가지고 있던 백설공주는, 다행스럽게도 일곱 난쟁이의 키의 합이 100이 됨을 기억해 냈다.

아홉 난쟁이의 키가 주어졌을 때, 백설공주를 도와 일곱 난쟁이를 찾는 프로그램을 작성하시오.

입력
아홉 개의 줄에 걸쳐 난쟁이들의 키가 주어진다. 주어지는 키는 100을 넘지 않는 자연수이며, 아홉 난쟁이의 키는 모두 다르며, 가능한 정답이 여러 가지인 경우에는 아무거나 출력한다.

출력
일곱 난쟁이의 키를 오름차순으로 출력한다. 일곱 난쟁이를 찾을 수 없는 경우는 없다.

예제 입력 1
20
7
23
19
10
15
25
8
13
예제 출력 1
7
8
10
13
19
20
23
 */
//1.조합을 이용한 풀이
//2.다중 for을 통한 풀이 + 모든 합을 구한 뒤, 2개의 값을 뺐을 때, 100이 만들어지는 경우를 출력
public class BOJ2309 {
    static int[] input = new int[9];
    static int[] output = new int[7];
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static boolean ck = false;

    public static void main(String[] args) throws Exception {
        for(int i=0;i<input.length;i++) {
            input[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(input);
        combination_f(0,0,0);
    }

    private static void combination_f(int idx,int cnt,int sum) {
        if(ck) return;
        if(cnt == 7) {
            if(sum == 100) {
                for(int i : output) {
                    System.out.println(i);
                }
                ck = true;
            }
            return;
        }
        if(idx >= 9) return;
        if(sum > 100) return;

        for(int i=idx;i<9;i++) {
            output[cnt] = input[i];
            combination_f(idx+1,cnt+1,sum+input[i]);
        }
        /* //위와 같은 알고리즘 형태이다.
        output[cnt] = input[idx];
        combination_f(idx+1,cnt+1,sum+input[idx]);
        combination_f(idx+1,cnt,sum);
        */

        /*
        int sum = Arrays.stream(input).mapToInt(Integer::parseInt).sum();
        for(int i=0;i<9;i++) {
            for(int j=i+1;j<9;j++) {
                if(sum - input[i] - input[j] == 100) {
                    for(int k=0;k<9;k++) {
                        if(k == i || k == j) continue;
                        sout(input[k]);
                    }
                }
            }
        }
         */
    }
}
