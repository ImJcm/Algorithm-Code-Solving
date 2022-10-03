/*
파티 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB	30132	14880	9888	47.502%
문제
N개의 숫자로 구분된 각각의 마을에 한 명의 학생이 살고 있다.

어느 날 이 N명의 학생이 X (1 ≤ X ≤ N)번 마을에 모여서 파티를 벌이기로 했다. 이 마을 사이에는 총 M개의 단방향 도로들이 있고 i번째 길을 지나는데 Ti(1 ≤ Ti ≤ 100)의 시간을 소비한다.

각각의 학생들은 파티에 참석하기 위해 걸어가서 다시 그들의 마을로 돌아와야 한다. 하지만 이 학생들은 워낙 게을러서 최단 시간에 오고 가기를 원한다.

이 도로들은 단방향이기 때문에 아마 그들이 오고 가는 길이 다를지도 모른다. N명의 학생들 중 오고 가는데 가장 많은 시간을 소비하는 학생은 누구일지 구하여라.

입력
첫째 줄에 N(1 ≤ N ≤ 1,000), M(1 ≤ M ≤ 10,000), X가 공백으로 구분되어 입력된다. 두 번째 줄부터 M+1번째 줄까지 i번째 도로의 시작점, 끝점, 그리고 이 도로를 지나는데 필요한 소요시간 Ti가 들어온다. 시작점과 끝점이 같은 도로는 없으며, 시작점과 한 도시 A에서 다른 도시 B로 가는 도로의 개수는 최대 1개이다.

모든 학생들은 집에서 X에 갈수 있고, X에서 집으로 돌아올 수 있는 데이터만 입력으로 주어진다.

출력
첫 번째 줄에 N명의 학생들 중 오고 가는데 가장 오래 걸리는 학생의 소요시간을 출력한다.

예제 입력 1
4 8 2
1 2 4
1 3 2
1 4 7
2 1 1
2 3 5
3 1 2
3 4 4
4 2 3
예제 출력 1
10
 */

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.*;

public class BOJ1238 {
    static PriorityQueue<Pair<Integer,Integer>> pQ = new PriorityQueue<>(new Comparator<Pair<Integer, Integer>>() {
        @Override
        public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
            return (o1.getValue() > o2.getValue() ? -1 : 1);
        }
    });
    static ArrayList<ArrayList<Pair<Integer,Integer>>> adj
            = new ArrayList<ArrayList<Pair<Integer,Integer>>>(1001);
            //= new ArrayList<ArrayList<Pair<Integer,Integer>>>(Collections.nCopies(1001,new ArrayList<>()));
    static ArrayList<Integer> dist;
    static Pair<Integer,Integer> ptmp;
    static int N,M,X;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] tmp = br.readLine().split(" ");

        N = Integer.parseInt(tmp[0]);
        M = Integer.parseInt(tmp[1]);
        X = Integer.parseInt(tmp[2]);

        for(int i=0;i<1001;i++) {
            adj.add(new ArrayList<>());
        }

        for(int i=0;i<M;i++) {
            tmp = br.readLine().split(" ");
            int n = Integer.parseInt(tmp[0]);
            int m = Integer.parseInt(tmp[1]);
            int x = Integer.parseInt(tmp[2]);

            ptmp = new Pair<>(m,x);
            adj.get(n).add(ptmp);
        }

        ArrayList<Integer> X_al = dijkstra(X);
        for(int i=1;i<=N;i++) {
            ArrayList<Integer> S_al = dijkstra(i);
            max = Math.max(X_al.get(i) + S_al.get(X),max);
        }
        System.out.println(max);
    }

    static ArrayList dijkstra(int x) {
        dist = new ArrayList<>(Collections.nCopies(N+1,Integer.MAX_VALUE));
        dist.set(x,0);
        pQ.add(new Pair<>(x,0));

        while(!pQ.isEmpty()) {
            int cost = -pQ.peek().getValue();
            int here = pQ.peek().getKey();
            pQ.poll();
            /*
                here노드에 도달하는데 드는 최소비용 값이 이전노드에서 here노드로 오는 경로의 비용과 비교하여,
                이미 최소비용이 cost보다 적다면, 해당 경로의 정보는 무시한다. (= 중복된 원소를 제거하는 과정)
             */
            if(dist.get(here) < cost) continue;

            for(int i = 0; i < adj.get(here).size(); i++) {
                int there = adj.get(here).get(i).getKey();
                int nextDist = cost + adj.get(here).get(i).getValue();

                if(dist.get(there) > nextDist) {
                    dist.set(there,nextDist);
                    pQ.add(new Pair<>(there,-nextDist));
                }
            }
        }
        return dist;
    }
}
/*
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.StringTokenizer;

class BOJ_test {
    public static class Edge {
        int to;
        int weight;

        public Edge(int to,int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static class Info implements Comparable<Info> {
        int node;
        int weight;

        public Info(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        @Override
        public int compareTo(Info o) {
            //오름차순
            return this.weight - o.weight;
            //내림차순
            //return o.weight - this.weight;
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());

        ArrayList<Edge> adj[] = new ArrayList[N+1];
        for(int i=1;i<=N;i++) {
            adj[i] = new ArrayList<Edge>();
        }

        int max = Integer.MIN_VALUE;
        for(int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            adj[from].add(new Edge(to,weight));
        }

        for(int i=1;i<=N;i++) {
            max = Math.max(max, dijstra(i,X,N,adj) + dijstra(X,i,N,adj));
        }
        System.out.println(max);
    }

    public static int dijstra(int s, int x, int n, ArrayList<Edge>[] a) {
        //s : 출발점, x : 도착점, n : 노드개수
        //a : 특정 노드에서 인접한 노드의 정보
        //dist : 가중치의 최소값을 저장할 배열
        int[] dist = new int[n+1];
        Arrays.fill(dist,Integer.MAX_VALUE);

        PriorityQueue<Info> pq = new PriorityQueue<Info>();
        pq.offer(new Info(s,0));
        dist[s] = 0;

        while(!pq.isEmpty()) {
            Info info = pq.poll();
            if(dist[info.node] < info.weight) continue;
            for(Edge edge : a[info.node]) {
                if(edge.weight + info.weight < dist[edge.to]) {
                    dist[edge.to] = edge.weight + info.weight;
                    pq.offer(new Info(edge.to,dist[edge.to]));
                }
            }
        }
        return dist[x];
    }
}
 */
