/*
IQ Test

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	9681	1728	1352	18.760%
문제
IQ Test의 문제 중에는 공통된 패턴을 찾는 문제가 있다. 수열이 주어졌을 때, 다음 수를 찾는 문제이다.

예를 들어, 1, 2, 3, 4, 5가 주어졌다. 다음 수는 무엇인가? 당연히 답은 6이다. 약간 더 어려운 문제를 보면, 3, 6, 12, 24, 48이 주어졌을 때, 다음 수는 무엇인가? 역시 답은 96이다.

이제 제일 어려운 문제를 보자.

1, 4, 13, 40이 주어졌을 때, 다음 수는 무엇일까? 답은 121이다. 그 이유는 항상 다음 수는 앞 수*3+1이기 때문이다.

은진이는 위의 3문제를 모두 풀지 못했으므로, 자동으로 풀어주는 프로그램을 작성하기로 했다. 항상 모든 답은 구하는 규칙은 앞 수*a + b이다. 그리고, a와 b는 정수이다.

수 N개가 주어졌을 때, 규칙에 맞는 다음 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N이 주어진다. N은 50보다 작거나 같은 자연수이다. 둘째 줄에는 N개의 수가 주어진다. 이 수는 모두 절댓값이 100보다 작거나 같은 정수이다.

출력
다음 수를 출력한다. 만약 다음 수가 여러 개일 경우에는 A를 출력하고, 다음 수를 구할 수 없는 경우에는 B를 출력한다.

예제 입력 1
4
1 4 13 40
예제 출력 1
121
예제 입력 2
5
1 2 3 4 5
예제 출력 2
6
예제 입력 3
5
3 6 12 24 48
예제 출력 3
96
예제 입력 4
1
0
예제 출력 4
A
예제 입력 5
2
-1 2
예제 출력 5
A
예제 입력 6
2
57 57
예제 출력 6
57
예제 입력 7
4
16 -8 4 -2
예제 출력 7
B
예제 입력 8
5
6 5 4 3 1
예제 출력 8
B
예제 입력 9
4
-12 12 -36 60
예제 출력 9
-132
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ1111 {
    static int N;
    static int[] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        arr = new int[N];

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        if (N == 1) {
            System.out.println("A");
        } else if(N == 2) {
            if(arr[0] == arr[1]) System.out.println(arr[0]);
            else System.out.println("A");
        } else {
            int a,b;
            if(arr[0] - arr[1] == 0) {
                a = 0;
            } else {
                a = (arr[1] - arr[2]) / (arr[0] - arr[1]);
            }
            b = arr[1] - arr[0] * a;

            for(int i=0;i<N-1;i++) {
                if(arr[i+1] != arr[i] * a + b) {
                    System.out.println("B");
                    return;
                }
            }
            System.out.println(arr[arr.length-1] * a + b);

            //초기 코드
//            boolean flag_check = true;
//            HashMap<Integer,Integer> map = new HashMap<>();
//            for(int i=1;i<N-1;i++) {
//                flag_check = true;
//                if(arr[i-1] - arr[i] == 0) {
//                    a = 0;
//                } else {
//                    a = (arr[i] - arr[i+1])/(arr[i-1] - arr[i]);
//                }
//                b = arr[i] - (a * arr[i-1]);
//                for(int j=0;j<N-1;j++) {
//                    if(((a != 0 && Math.abs(a) < 1) || (b != 0 && Math.abs(b) < 1))
//                            || (arr[j+1] != (arr[j] * a + b))) {
//                        flag_check = false;
//                        break;
//                    }
//                }
//                if(flag_check) {
//                    map.put(a,b);
//                }
//            }
//
//            List<Integer> keylist = new ArrayList<>(map.keySet());
//            int x = 0,pre_x = Integer.MAX_VALUE;
//            if(map.size() > 0) {
//                for(int s=0;s<map.size();s++) {
//                    a = keylist.get(s);
//                    b = map.get(a);
//                    if(s > 0) {
//                        if(pre_x != Integer.MAX_VALUE && pre_x != x) {
//                            System.out.println("B");
//                            return;
//                        }
//                        pre_x = x;
//                    }
//                    x = arr[arr.length-1] * a + b;
//                }
//                System.out.println(x);
//            } else {
//                System.out.println("B");
//            }
        }
    }
}
