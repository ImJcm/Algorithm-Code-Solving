package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
닭싸움 팀 정하기 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	1219	521	388	43.255%
문제
닭싸움은 월드의 전통이다. 이번 캠프에서도 어김없이 닭싸움 대회가 열렸다. 그런데, 닭싸움을 하기 위해서는 반드시 누가 우리 편이고, 누가 우리 편이 아닌지를 알아야 할 것이다. 닭싸움의 팀을 정하는 원칙은, 평소 학생들의 인간관계에 따라 다음과 같이 정리할 수 있다.

내 친구의 친구는 내 친구이다.
내 원수의 원수도 내 친구이다.
이 때 두 학생이 친구이면 같은 팀에 속해있어야 하며, 같은 팀에 속해 있는 사람들끼리는 전부 친구여야 한다.

학생들의 인간관계가 주어지면, 닭싸움을 위한 팀 정하기를 할 때, 최대 얼마나 많은 팀이 만들어질 수 있는지 알아내는 프로그램을 작성하시오.

입력
첫째 줄에 학생의 수 n이 주어진다. 각 학생들은 1부터 n까지 번호가 매겨져 있다. (2 ≤ n ≤ 1000)

둘째 줄에 학생 간의 인간관계 중 알려진 것의 개수 m이 주어진다. (1 ≤ m ≤ 5000)

다음 m개의 줄에는 한 줄에 한 개씩, 학생 간의 인간관계가 F p q 혹은 E p q의 형태로 공백으로 구분되어 주어진다. (1 ≤ p < q ≤ n)

첫 번째 글자가 F인 경우에는 p와 q가 친구인 것이고, E인 경우는 p와 q가 원수인 경우이다.

입력은 모순이 없음이 보장된다. 즉, 두 학생이 동시에 친구이면서 원수인 경우는 없다.

출력
첫째 줄에, 가능한 최대 팀 개수를 출력한다.

예제 입력 1
6
4
E 1 4
F 3 5
F 4 6
E 1 2
예제 출력 1
3
 */
/*
    먼저 원수관계를 배제하고 친구관계로 간선을 연결한 후 원수관계를 추가
    먼저 원수관계와 친구관계를 별도로 생성 후, 원수관계에서 1~N까지의 노드를 시작으로 원한관계를 가지는 노드가 있을 경우
    친구관계에 해당 노드와의 간선을 추가하고, 이 과정이 끝나면 친구관계 간선에서 DFS 완전탐색을 수행하여 그룹 별로 개수를 계산

    graph + DFS
 */
public class BOJ1765 {
    static int N,M;
    static ArrayList<Integer>[] eArr, fArr;
    static boolean[] ev, fv;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        eArr = new ArrayList[N+1];
        fArr = new ArrayList[N+1];

        for(int i=0;i<=N;i++) eArr[i] = new ArrayList<>();

        for(int i=0;i<=N;i++) fArr[i] = new ArrayList<>();

        for(int i=0;i<M;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            char c = st.nextToken().charAt(0);
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());

            if(c == 'E') {
                eArr[p].add(q);
                eArr[q].add(p);
            } else {
                //'F'
                fArr[p].add(q);
                fArr[q].add(p);
            }
        }

        //원수의 원수 관계를 탐색하는 DFS알고리즘
        for(int i=1;i<=N;i++) {
            ev = new boolean[N+1];
            eDFS(i,i,0);
        }

        fv = new boolean[N+1];
        int cnt = 0;
        //친구 관계에서 팀 개수 계산 DFS알고리즘
        for(int i=1;i<=N;i++) {
            if(!fv[i]) {
                cnt++;
                DFS(i);
            }
        }
        System.out.println(cnt);

    }

    static void eDFS(int root, int s, int cnt) {
        //서로 원수의 원수관계일 경우, 친구 관계에 간선을 추가
        if(cnt == 2) {
            fArr[root].add(s);
            return;
        }

        for(Integer next : eArr[s]) {
            if(ev[next]) continue;
            ev[next] = true;
            eDFS(root,next,cnt+1);
        }
    }

    static void DFS(int s) {
        for(Integer next : fArr[s]) {
            if(fv[next]) continue;
            fv[next] = true;
            DFS(next);
        }
    }
}
