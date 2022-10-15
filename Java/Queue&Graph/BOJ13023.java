/*
ABCDE

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	25844	7987	5305	28.727%
문제
BOJ 알고리즘 캠프에는 총 N명이 참가하고 있다. 사람들은 0번부터 N-1번으로 번호가 매겨져 있고, 일부 사람들은 친구이다.

오늘은 다음과 같은 친구 관계를 가진 사람 A, B, C, D, E가 존재하는지 구해보려고 한다.

A는 B와 친구다.
B는 C와 친구다.
C는 D와 친구다.
D는 E와 친구다.
위와 같은 친구 관계가 존재하는지 안하는지 구하는 프로그램을 작성하시오.

입력
첫째 줄에 사람의 수 N (5 ≤ N ≤ 2000)과 친구 관계의 수 M (1 ≤ M ≤ 2000)이 주어진다.

둘째 줄부터 M개의 줄에는 정수 a와 b가 주어지며, a와 b가 친구라는 뜻이다. (0 ≤ a, b ≤ N-1, a ≠ b) 같은 친구 관계가 두 번 이상 주어지는 경우는 없다.

출력
문제의 조건에 맞는 A, B, C, D, E가 존재하면 1을 없으면 0을 출력한다.

예제 입력 1
5 4
0 1
1 2
2 3
3 4
예제 출력 1
1
예제 입력 2
5 5
0 1
1 2
2 3
3 0
1 4
예제 출력 2
1
예제 입력 3
6 5
0 1
0 2
0 3
0 4
0 5
예제 출력 3
0
예제 입력 4
8 8
1 7
3 7
4 7
3 4
4 6
3 5
0 4
2 7
예제 출력 4
1
 */
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class BOJ13023 {
    static int N,M;
    static boolean[] visited;
    static boolean res = false;
    static ArrayList<ArrayList<Integer>> listgraph;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException{
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        M = Integer.parseInt(tmp[1]);

        visited = new boolean[N];
        Arrays.fill(visited,false);

        graphLinkedList(N);

        makegraphEdge();

        for(int i=0;i<N;i++) {
            visited[i] = true;
            dfs(1,i);
            visited[i] = false;
            if(res) {
                break;
            }
        }
        if(res) System.out.println("1");
        else System.out.println("0");
    }

    static void graphLinkedList(int s) {
        listgraph = new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<s;i++) {
            listgraph.add(new ArrayList<Integer>());
        }
    }

    static void makegraphEdge() throws IOException{
        for(int i=0;i<M;i++) {
            String[] tmp = br.readLine().split(" ");
            int v1 = Integer.parseInt(tmp[0]);
            int v2 = Integer.parseInt(tmp[1]);
            listgraph.get(v1).add(v2);
            listgraph.get(v2).add(v1);
        }
    }

    static void dfs(int v,int start) {
        if(v == 5) {
            res = true;
            return;
        }

        for(int i=0;i<listgraph.get(start).size();i++) {
            int idx = listgraph.get(start).get(i);
            if (visited[idx]) continue;
            visited[idx] = true;
            dfs(v+1,idx);
            visited[idx] = false;
        }
    }
}
