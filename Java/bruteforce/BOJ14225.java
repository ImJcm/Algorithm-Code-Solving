package BackJoon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

/*
부분수열의 합

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	10009	4757	3245	43.580%
문제
수열 S가 주어졌을 때, 수열 S의 부분 수열의 합으로 나올 수 없는 가장 작은 자연수를 구하는 프로그램을 작성하시오.

예를 들어, S = [5, 1, 2]인 경우에 1, 2, 3(=1+2), 5, 6(=1+5), 7(=2+5), 8(=1+2+5)을 만들 수 있다. 하지만, 4는 만들 수 없기 때문에 정답은 4이다.

입력
첫째 줄에 수열 S의 크기 N이 주어진다. (1 ≤ N ≤ 20)

둘째 줄에는 수열 S가 주어진다. S를 이루고있는 수는 100,000보다 작거나 같은 자연수이다.

출력
첫째 줄에 수열 S의 부분 수열의 합으로 나올 수 없는 가장 작은 자연수를 출력한다.

예제 입력 1
3
5 1 2
예제 출력 1
4
예제 입력 2
3
2 1 4
예제 출력 2
8
예제 입력 3
4
2 1 2 7
예제 출력 3
6
 */
/*
    부분 수열의 합을 만들기위해 백트랙킹을 사용하여 모든 가능한 부분 수열을 구하였고, 주어지는 원소들로 만들 수 있는 부분수열의 합의 수를 1로
    저장하는 배열을 만든 후, 부분 수열의 합으로 나올 수 없는 수는 1 부터 시작해서 1로 카운트 되지않은 인덱스를 반환하면 나온다.

    모든 부분집합을 구한 뒤 처리하는 과정은 많은 시간소모를 하므로, 중복되는 과정을 없애는 것이 중요하다.
 */
public class BOJ14225 {
    static ArrayList<Integer> subset = new ArrayList<>();
    static int[] arr,stdPoint = new int[2000001];
    static boolean[] visited;
    static int N;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = Integer.parseInt(sc.nextLine());

        visited = new boolean[N];
        Arrays.fill(visited,false);
        arr = new int[N];
        arr = Arrays.stream(sc.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .sorted()
                .toArray();

        search(0,0);

        for(int i=1;i<stdPoint.length;i++) {
            if(stdPoint[i] == 0) {
                System.out.println(i);
                break;
            }
        }
    }
    // 중복과정을 줄이고, DFS로 부분집합에 값을 합해주는 경우와 합하지 않은 경우를 나누어 재귀호출하였다.
    // 이 과정은 부분수열을 구성하는 과정에서 중복되는 부분수열을 만들지 않는다
    // 부분집합 구성 o_x : 2 ^ arr.length : N만큼 = O(2^N)
    static void search(int k,int sum) {
        if(k == N) {
            stdPoint[sum] = 1;
            return;
        } else {
            search(k+1,sum+arr[k]);
            search(k+1,sum);
        }
    }

    // 시간초과 - 조금이라도 시간복잡도를 줄여보았으나, 부분집합 자체 중복되는 과정이 많아서 시간초과문제가 발생하는 것 같다.
    // arr.length만큼 부분집합 원소로 구성하는 여부 N번 반복 ^ (sum+arr[i] : N번 + sum : N번) : O(n^n)
    /*static void search(int k,int sum) {
        if(k == N) {
            stdPoint[sum] = 1;
            return;
        } else {
            for(int i=0;i<arr.length;i++) {
                if(!visited[i]) {
                    visited[i] = true;
                    search(k+1,sum+arr[i]);
                    visited[i] = false;
                    search(k+1,sum);
                }
            }
        }
    }*/
    //시간 초과 - 모든 경우의 부분집합을 구하여 합을 만들고 해당하는 값의 인덱스 위치에 1을 저장하기 때문에 중복되는 과정이 많아져 시간초과가 발생한다.
    /*static void search(int k) {
        if(k == N) {
            int sum = 0;
            for(Integer i : subset) {
                sum += i.intValue();
            }
            stdPoint[sum] = 1;
            return;
        } else {
            for(int i=0;i<arr.length;i++) {
                if(!visited[i]) {
                    subset.add(subset.size(),arr[i]);
                    visited[i] = true;
                    search(k+1);
                    subset.remove(subset.size()-1);
                    visited[i] = false;
                    search(k+1);
                }
            }
        }
    }*/
}
