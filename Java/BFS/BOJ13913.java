package BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
숨바꼭질 4 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	28322	9597	6739	31.957%
문제
수빈이는 동생과 숨바꼭질을 하고 있다. 수빈이는 현재 점 N(0 ≤ N ≤ 100,000)에 있고, 동생은 점 K(0 ≤ K ≤ 100,000)에 있다. 수빈이는 걷거나 순간이동을 할 수 있다. 만약, 수빈이의 위치가 X일 때 걷는다면 1초 후에 X-1 또는 X+1로 이동하게 된다. 순간이동을 하는 경우에는 1초 후에 2*X의 위치로 이동하게 된다.

수빈이와 동생의 위치가 주어졌을 때, 수빈이가 동생을 찾을 수 있는 가장 빠른 시간이 몇 초 후인지 구하는 프로그램을 작성하시오.

입력
첫 번째 줄에 수빈이가 있는 위치 N과 동생이 있는 위치 K가 주어진다. N과 K는 정수이다.

출력
첫째 줄에 수빈이가 동생을 찾는 가장 빠른 시간을 출력한다.

둘째 줄에 어떻게 이동해야 하는지 공백으로 구분해 출력한다.

예제 입력 1
5 17
예제 출력 1
4
5 10 9 18 17
예제 입력 2
5 17
예제 출력 2
4
5 4 8 16 17
 */
/*
  알고리즘 핵심 : 그래프 이론 + BFS
 */
public class BOJ13913 {
    static int N,K;
    static int[] map = new int[100001];
    static int[] path = new int[100001];
    static boolean[] visited = new boolean[100001];
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        K = Integer.parseInt(str[1]);

        Arrays.fill(map,0);
        Arrays.fill(path,-1);
        Arrays.fill(visited,false);

        BFS(N,K);
    }
    /*
        임의의 String에 경로를 추가하고 누적한 값을 마지막에 StringBuilder에 append하는 경우는
        Backjoon 제출 조건에 의해 시간초과가 발생하였다.

        Stack을 사용하여 경로를 넣고, sb에 pop을 통해 추가하여 출력하는 경우는 성공하였다.

        String에 한번에 모든 값을 누적하고, sb에 append하는 방법의 경우
        누적하는 과정과 많은 양의 data를 append하는 과정에서 시간초과가 발생한 것으로 생각하고있다.

     */
    static void BFS(int n, int k) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(n);
        visited[n] = true;

        while(!q.isEmpty()) {
            int pos = q.poll();
            visited[pos] = true;

            if(pos == k) {
                System.out.println(map[k]); //걸린 시간 출력
                //sb.append(map[k] + "\n");
                //String tmp_str = Integer.toString(k);
                int count = map[k];

                Stack<Integer> stack = new Stack<>();
                while(count-- > 0) {
                    stack.add(pos);
                    pos = path[pos];
                    //timeout
                    //tmp_str = path[pos] + " " + tmp_str;
                    //pos = path[pos];
                }
                stack.add(N);

                while(!stack.isEmpty()) {
                    sb.append(stack.pop() + " ");
                }
                System.out.println(sb);
                //sb.append(tmp_str);
                return;
            }

            if(pos < 0 || pos > 100000) continue;

            //part-3 위치에 따라 출력되는 이동 경로가 달라짐 : 5 4 8 16 17
            if(2*pos <= 100000 && !visited[2*pos]) {
                q.offer(2*pos);
                visited[2*pos] = true;
                map[2*pos] = map[pos] + 1;
                path[2*pos] = pos;
            }

            if(pos + 1 <= 100000 && !visited[pos+1]) {
                q.offer(pos+1);
                visited[pos+1] = true;
                map[pos+1] = map[pos] + 1;
                path[pos+1] = pos;
            }

            //part-3 : 5 10 9 18 17
            if(pos - 1 >= 0 && !visited[pos-1]) {
                q.offer(pos-1);
                visited[pos-1] = true;
                map[pos-1] = map[pos] + 1;
                path[pos-1] = pos;
            }


        }
    }
}
