import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
숨바꼭질 3

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	56401	16337	10487	25.388%
문제
수빈이는 동생과 숨바꼭질을 하고 있다. 수빈이는 현재 점 N(0 ≤ N ≤ 100,000)에 있고, 동생은 점 K(0 ≤ K ≤ 100,000)에 있다. 수빈이는 걷거나 순간이동을 할 수 있다. 만약, 수빈이의 위치가 X일 때 걷는다면 1초 후에 X-1 또는 X+1로 이동하게 된다. 순간이동을 하는 경우에는 0초 후에 2*X의 위치로 이동하게 된다.

수빈이와 동생의 위치가 주어졌을 때, 수빈이가 동생을 찾을 수 있는 가장 빠른 시간이 몇 초 후인지 구하는 프로그램을 작성하시오.

입력
첫 번째 줄에 수빈이가 있는 위치 N과 동생이 있는 위치 K가 주어진다. N과 K는 정수이다.

출력
수빈이가 동생을 찾는 가장 빠른 시간을 출력한다.

예제 입력 1
5 17
예제 출력 1
2
 */
public class BOJ13549 {
    static int N,K;
    static boolean[] visited = new boolean[100001];
    static int[] time = new int[100001];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] str = br.readLine().split(" ");

        N = Integer.parseInt(str[0]);
        K = Integer.parseInt(str[1]);
        Arrays.fill(visited,false);
        Arrays.fill(time,0);

        //BFS();

        //System.out.println(time[K]);
        dijkstra();
    }

    //BFS(너비 우선 탐색)
    //O(V^2 + E)
    static void BFS() {
        Queue<Integer> q = new LinkedList<>();  //queue
        q.offer(N);
        visited[N] = true;

        while(!q.isEmpty()) {
            int n = q.poll();

            if(n < 0 || n > 100000) continue;

            if(2*n <= 100000 && !visited[2*n]) {
                visited[2*n] = true;
                time[2*n] = time[n];
                q.offer(2*n);
            }

            if(n-1 >= 0 && !visited[n-1]) {
                visited[n-1] = true;
                time[n-1] = time[n] + 1;
                q.offer(n-1);
            }

            if(n+1 < 100000 && !visited[n+1]) {
                visited[n+1] = true;
                time[n+1] = time[n] + 1;
                q.offer(n+1);
            }
        }
    }

    //O(ElogE) or O(ElogV)
    //대개의 그래프는 V^2 > E 이기때문에, O(ElogV)
    //다익스트라(Dijkstra)
    static class Node implements Comparable<Node> {
        int pos;
        int time;

        Node(int p, int t) {
            this.pos = p;
            this.time = t;
        }

        @Override
        public int compareTo(Node n) {
            return this.time - n.time;
        }
    }
    static void dijkstra() {
        int[] move_time = {0,1,1};

        //걸린 시간에 따른 내림차순으로 정렬해야 함, default : 오름차순
        PriorityQueue<Node> pq = new PriorityQueue<>();  //Priority Queue
        pq.offer(new Node(N,0));

        while(!pq.isEmpty()) {
            Node n = pq.poll();
            visited[n.pos] = true;

            if(n.pos == K) {
                System.out.println(n.time);
                return;
            }

            for(int i=0;i<3;i++) {
                int next_time = n.time + move_time[i];
                int next_pos = 0;
                if(i==0) next_pos = n.pos * 2;
                if(i==1) next_pos = n.pos + 1;
                if(i==2) next_pos = n.pos - 1;
                if(next_pos < 0 || next_pos > 100000 || visited[next_pos]) continue;
                pq.offer(new Node(next_pos,next_time));
            }
        }
    }
    //0-1 너비우선탐색
    static void Zero_one_BFS() {
        Deque<Integer> dq = new LinkedList<>();  //deque
        dq.offer(N);
        visited[N] = true;

        while(!dq.isEmpty()) {
            int n = dq.poll();

            if(n < 0 || n > 100000) continue;

            if(2*n <= 100000 && !visited[2*n]) {
                visited[2*n] = true;
                time[2*n] = time[n];
                dq.addFirst(2*n);
            }

            if(n-1 >= 0 && !visited[n-1]) {
                visited[n-1] = true;
                time[n-1] = time[n] + 1;
                dq.offer(n-1);
            }

            if(n+1 < 100000 && !visited[n+1]) {
                visited[n+1] = true;
                time[n+1] = time[n] + 1;
                dq.offer(n+1);
            }
        }
    }
}
