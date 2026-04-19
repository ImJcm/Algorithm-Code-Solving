package divideConquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
스카이라인

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	4750	1535	960	32.686%
문제
N개의 직사각형 모양의 건물들이 주어졌을 때, 스카이라인을 구해내는 프로그램을 작성하시오. 스카이라인은 건물 전체의 윤곽을 의미한다. 즉, 각각의 건물을 직사각형으로 표현했을 때, 그러한 직사각형들의 합집합을 구하는 문제이다.



예를 들어 직사각형 모양의 건물들이 위와 같이 주어졌다고 하자. 각각의 건물은 왼쪽 x좌표와 오른쪽 x좌표, 그리고 높이로 나타난다. 모든 건물들은 편의상 같은 높이의 지면(땅) 위에 있다고 가정하자. 위의 예에서 스카이라인을 구하면 아래와 같다.



입력
첫째 줄에 건물의 개수 N(1 ≤ N ≤ 100,000)이 주어진다. 다음 N개의 줄에는 N개의 건물에 대한 정보가 주어진다. 건물에 대한 정보는 세 정수 L, H, R로 나타나는데, 각각 건물의 왼쪽 x좌표, 높이, 오른쪽 x좌표를 의미한다. (1 ≤ L < R ≤ 1,000,000,000, 1 ≤ H ≤ 1,000,000,000)

출력
첫째 줄에 스카이라인을 출력한다. 출력을 할 때에는 높이가 변하는 지점에 대해서, 그 지점의 x좌표와 그 지점에서의 높이를 출력한다.

예제 입력 1
8
1 11 5
2 6 7
3 13 9
12 7 16
14 3 25
19 18 22
23 13 29
24 4 28
예제 출력 1
1 11 3 13 9 0 12 7 16 3 19 18 22 3 23 13 29 0
출처
문제의 오타를 찾은 사람: wider93
데이터를 추가한 사람: cinador, djm03178, gaelim
빠진 조건을 찾은 사람: jh05013
알고리즘 분류
자료 구조
집합과 맵
스위핑
트리를 사용한 집합과 맵
우선순위 큐
 */
/*
알고리즘 핵심
우선순위 큐 or 분할정복 (+ 스카이라인 알고리즘)
i) 우선순위 큐
1. 스카이라인 알고리즘을 적용하여 건물의 위치를 좌상단, 우하단으로 표현할 수 있다.
2. 앞선 건물을 시작으로 높이가 높은 건물을 하나 설정하고 다음 건물과 시작 위치, 높이, 길이를 종합적으로 비교하여 겹치는 부분을 제거하고 남은
부분을 건물로 추가하여 순차적으로 모든 건물을 비교한다.
3. 건물의 겹치는 경우, 이어지는 경우, 겹치지 않는 경우를 모두 고려하여 각 건물들의 높이가 가장 높은 구간을 추출한다.
4. 각 추출한 구간을 통해 구간 간의 종료지점을 추가한다.

ii) 트리맵(TreeMap) + 우선순위 큐
1. x좌표를 기준으로 건물의 순서를 오름차순으로 정렬하여 스카이라인을 우선순위 큐로 구성 + 트리맵은 높이를 기준으로 내림차순으로 정렬한다.
2. 우선순위 큐에 (건물의 시작 지점, 높이), (건물의 끝 지점, -높이)로 구성하여 지점을 기준으로 오름차순 정렬하여 구성한다.
3. 우선순위 큐에서 한 데이터씩 트리맵에 넣는다.
3-1. 뽑은 데이터의 높이가 양수(건물의 시작 지점)이면 트리 맵에 추가/갱신한다. (추가되는 값은 해당하는 높이의 건물의 개수를 의미)
3-2. 뽑은 데이터의 높이가 음수(건물의 끝 지점)이면 트리 맵에 삭제/갱신한다.


처음 접근으로 bruteforce로 입력으로 주어진 건물의 구간을 오름차순으로 정렬하여 각각의 x 구간마다 가장 높은 높이를 저장하여 스카이라인을 구하려고 했지만
입력 요구사항으로 x의 범위가 크기 때문에 메모리 초과가 발생하였다.
이후, 접근하는 방식이 떠오르지 않아 스카이라인 알고리즘에 대해 알아보게 되었고, 각 건물의 위치를 좌상단,우하단의 좌표로 특정한다는 것을 알게되었다.

스카이라인 알고리즘 참고 - https://m.blog.naver.com/pshkhh/221371825241
풀이 참조 - https://velog.io/@shinjy9802/%EB%B0%B1%EC%A4%80-1933%EB%B2%88-%EC%8A%A4%EC%B9%B4%EC%9D%B4%EB%9D%BC%EC%9D%B8-Java
풀이 참조(트리맵) - https://coder-in-war.tistory.com/entry/BOJ-JAVA1933-%EC%8A%A4%EC%B9%B4%EC%9D%B4%EB%9D%BC%EC%9D%B8
 */
public class BOJ1933 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static StringBuilder ans;

    public static void main(String[] args) throws IOException {
        /*
            3% 실패 코드 : 메모리 초과
            원인 : L,R의 범위가 10^9만큼의 배열을 만드는 것부터 메모리 초과가 발생한다.
        */

        /*
        general_solve task2 = new general_solve();
        task2.solve();  
        */

        PriorityQueue_solve task1 = new PriorityQueue_solve();
        task1.solve();

        TreeMap_PriorityQueue task = new TreeMap_PriorityQueue();
        task.solve();
    }

    public static class PriorityQueue_solve {
        public class building implements Comparable<building> {
            int l,h,r;

            building(int l, int h, int r) {
                this.l = l;
                this.h = h;
                this.r = r;
            }

            @Override
            public int compareTo(building b) {
                return this.l - b.l;    // 오름차순
            }
        }

        PriorityQueue<building> pq;
        ArrayList<building> point;

        private void solve() throws IOException {
            init_setting();

            skyline();

            skyline_endpoint();

            System.out.println(ans.toString());
        }

        private void skyline() {
            building cb = pq.poll();    // current_building

            while(!pq.isEmpty()) {
                building nb = pq.poll(); // next_building

                /*
                    if) 건물끼리 겹쳐지는 경우 -> ★(nb.l < cb.r)★
                        if) 높이 : 현재 건물 >= 다음 건물 - 현재 건물이 높거나 같은 경우
                            if) 현재 건물보다 다음 건물의 길이가 큰 경우
                                -> 현재 건물이 높이가 더 높으므로 다음 건물과 겹쳐지는 부분을 제거하고 남은 부분을 재설정한다.
                                -> 다음 건물이 현재 건물과 길이가 같거나 작은 경우는 스카이라인에 포함될 수 없으므로 조건 설정 제외
                        if) 높이 : 현재 건물 < 다음 건물 - 다음 건물이 높은 경우
                            if) 현재 건물이 다음 건물보다 길이가 긴 경우
                                -> 현재 건물에서 다음 건물과 겹친 부부을 제외한 남은 건물을 재설정한다.
                            if) 현재 건물의 시작 위치가 다음 건물의 시작 위치보다 앞선 경우
                                -> 더 이상 현재 건물의 시작지점과 동일한 건물에서 가장 높은 건물은 없음을 만족(건물의 시작지점으로 오름차순 정렬 - 우선순위 큐)하므로
                                   현재 건물과 다음 건물 까지의 겹치지 않는 부분을 스카이라인으로 설정한다.
                    if) 현재 건물과 다음 건물이 이어지는 경우 - (cb.r == nb.l)
                        if) 두 건물의 높이가 같은 경우
                            -> 현재 건물의 길이를 다음 건물의 길이로 맞춘다.
                        if) 두 건물의 높이가 다른 경우
                            -> 현재 건물을 스카이라인으로 설정하고, 다음 건물을 현재 건물로 업데이트한다.
                    if) 현재 건물과 다음 건물이 이어지지 않는 경우 - (cb.r > nb.l)
                        if) 현재 건물을 스카이라인으로 설정하고, 다음 건물을 현재 건물로 업데이트한다.
                 */
                if(nb.l < cb.r) {   // cb.l <= nb.l &&
                    if(cb.h >= nb.h) {
                        if(cb.r < nb.r) {
                            pq.add(new building(cb.r,nb.h,nb.r));
                        }
                    } else {
                        if(cb.r > nb.r) {
                            pq.add(new building(nb.r,cb.h,cb.r));
                        }
                        if(cb.l < nb.l) {
                            point.add(new building(cb.l,cb.h,nb.l));
                        }
                        cb = nb;
                    }
                } else if(cb.r == nb.l) { // && cb.h == nb.h
                    if(cb.h == nb.h) {
                        cb.r = nb.r;
                    } else {
                        point.add(cb);
                        cb = nb;
                    }
                } else {
                    point.add(cb);
                    cb = nb;
                }
            }
            point.add(cb);  // 마지막으로 현재 건물을 스카이라인으로 설정한다.
        }

        /*
            좌상단의 스카이라인을 기준으로 우하단의 스카이라인 지점을 설정해야 한다.
            스카이라인 끝지점과 다음 스카이라인의 시작점의 위치가 다르면 해당 지점은 건물의 끝지점을 나타낸다.
         */
        private void skyline_endpoint() {
            int i;
            for(i = 0; i < point.size(); i++) {
                building b = point.get(i);

                ans.append(b.l).append(" ").append(b.h).append(" ");
                if(i != point.size() - 1) {
                    building b2 = point.get(i + 1);
                    if(b.r != b2.l) ans.append(b.r).append(" ").append(0).append(" ");
                } else {
                    ans.append(b.r).append(" ").append(0).append(" ");
                }
            }
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            pq = new PriorityQueue<>();
            point = new ArrayList<>();
            ans = new StringBuilder();

            for(int i = 0; i < N; i++) {
                String[] input = br.readLine().split(" ");

                int l = Integer.parseInt(input[0]);
                int h = Integer.parseInt(input[1]);
                int r = Integer.parseInt(input[2]);

                pq.add(new building(l,h,r));
            }
        }
    }

    public static class TreeMap_PriorityQueue {
        public class building implements Comparable<building> {
            int x,h;

            building(int x, int h) {
                this.x = x;
                this.h = h;
            }

            @Override
            public int compareTo(building b) {
                int diff = this.x - b.x;

                if(diff > 0) return 1;
                else if(diff < 0) return -1;
                else return -(this.h - b.h);

            }
        }

        TreeMap<Integer, Integer> tm;
        PriorityQueue<building> pq;
        ArrayList<building> anslist;

        private void solve() throws IOException {
            init_setting();

            skyline();

            print_skyline();

            System.out.println(ans.toString());
        }

        /*
            트리맵에 건물의 시작 지점에서의 최대 높이를 저정하고 있어서 각 건물의 시작지점마다 최대 높이를 저정하고,
            끝 지점인 경우 트리맵에서 해당 높이의 노드를 삭제하여 건물의 시작과 끝을 조율하여 스카이라인을 구성한다.
         */
        private void skyline() {
            while(!pq.isEmpty()) {
                building b = pq.poll();

                if(b.h > 0) {
                    tm.put(b.h, tm.getOrDefault(b.h,0) + 1);
                } else {
                    int k = -b.h;
                    int v = tm.get(k);
                    if(v == 1) {
                        tm.remove(k);
                    } else {
                        tm.put(k,v - 1);
                    }
                }

                if(tm.isEmpty()) {
                    anslist.add(new building(b.x,0));
                    continue;
                }

                anslist.add(new building(b.x, tm.firstKey()));
            }
        }

        private void print_skyline() {
            int prev = 0;

            for(int i = 0; i < anslist.size(); i++) {
                if(prev != anslist.get(i).h) {
                    int x = anslist.get(i).x;
                    int h = anslist.get(i).h;

                    ans.append(x).append(" ").append(h).append(" ");
                    prev = h;
                }
            }
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            tm = new TreeMap<>(Comparator.reverseOrder());
            pq = new PriorityQueue<>();

            for(int i = 0; i < N; i++) {
                String[] input = br.readLine().split(" ");

                int l = Integer.parseInt(input[0]);
                int h = Integer.parseInt(input[1]);
                int r = Integer.parseInt(input[2]);

                pq.add(new building(l,h));
                pq.add(new building(r,-h));
            }

            ans = new StringBuilder();
            anslist = new ArrayList<>();
        }
    }

    // wrong solve
    public static class general_solve {
        HashMap<Integer, Integer> building;
        HashMap<Integer, Integer> skyline;

        private void solve() throws IOException {
            init_setting();

            make_skyline();

            System.out.println(ans.toString());
        }

        private void make_skyline() {
            for(Integer key : building.keySet()) {
                int mh = building.get(key);

                skyline.put(key,mh);
            }

            int prev_x = 0;
            int prev_height = 0;

            for(Integer key : skyline.keySet()) {
                int cur_height = skyline.get(key);

                if(prev_height != cur_height) {
                    if(prev_x != 0 && prev_x + 1 != key) ans.append(prev_x).append(" ").append(0).append(" ");
                    ans.append(key).append(" ").append(cur_height).append(" ");
                    prev_height = cur_height;
                }
                prev_x = key;
            }
            ans.append(prev_x).append(" ").append(0);
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            building = new HashMap<>();
            skyline = new HashMap<>();

            String[] input;

            for(int i = 0; i < N; i++) {
                input = br.readLine().split(" ");

                int lx = Integer.parseInt(input[0]);
                int h = Integer.parseInt(input[1]);
                int rx = Integer.parseInt(input[2]);

                for(int l = lx; l <= rx; l++) {
                    if(!building.containsKey(l)) {
                        building.put(l,h);
                    } else {
                        if(building.get(l) < h) {
                            building.remove(l);
                            building.put(l,h);
                        }
                    }
                }
            }
            ans = new StringBuilder();
        }
    }
}
