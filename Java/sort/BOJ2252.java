package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/*
줄 세우기 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	55945	33164	22054	57.210%
문제
N명의 학생들을 키 순서대로 줄을 세우려고 한다. 각 학생의 키를 직접 재서 정렬하면 간단하겠지만, 마땅한 방법이 없어서 두 학생의 키를 비교하는 방법을 사용하기로 하였다. 그나마도 모든 학생들을 다 비교해 본 것이 아니고, 일부 학생들의 키만을 비교해 보았다.

일부 학생들의 키를 비교한 결과가 주어졌을 때, 줄을 세우는 프로그램을 작성하시오.

입력
첫째 줄에 N(1 ≤ N ≤ 32,000), M(1 ≤ M ≤ 100,000)이 주어진다. M은 키를 비교한 회수이다. 다음 M개의 줄에는 키를 비교한 두 학생의 번호 A, B가 주어진다. 이는 학생 A가 학생 B의 앞에 서야 한다는 의미이다.

학생들의 번호는 1번부터 N번이다.

출력
첫째 줄에 학생들을 앞에서부터 줄을 세운 결과를 출력한다. 답이 여러 가지인 경우에는 아무거나 출력한다.

예제 입력 1
3 2
1 3
2 3
예제 출력 1
1 2 3
예제 입력 2
4 2
4 2
3 1
예제 출력 2
4 2 3 1
출처
빠진 조건을 찾은 사람: indioindio
데이터를 추가한 사람: lhs456852, wlstn6278
알고리즘 분류
그래프 이론
위상 정렬
방향 비순환 그래프
 */
/*
알고리즘 핵심
0. 위상 정렬
어떤 학생인지 구분을 위한 v, 인접차수를 나타내는 adj_degree, 인접 간선을 나타내는 adj_student를 갖는 클래스를 이용하여 위상 정렬 수행

1. 인접 차수가 0인 학생부터 Queue에 삽입하고 인접 간선을 나타내는 학생의 인접 차수를 업데이트한다.
2. 키가 작은 학생부터 ans에 저장되기 때문에 출력 시 reverse를 적용하여 키큰 순으로 출력할 수 있도록 한다.
 */
public class BOJ2252 {
    static class BOJ2252_student {
        int v,adj_degree;
        ArrayList<BOJ2252_student> adj_student;

        BOJ2252_student(int v) {
            this.v = v;
            this.adj_degree = 0;
            this.adj_student = new ArrayList<>();
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,M;
    static ArrayList<Integer> ans;
    static BOJ2252_student[] students;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        Queue<BOJ2252_student> q = new LinkedList<>();

        for(int i = 1; i <= N; i++) {
            if(students[i].adj_degree == 0) q.add(students[i]);
        }

        while(!q.isEmpty()) {
            BOJ2252_student now = q.poll();

            ans.add(now.v);

            for(BOJ2252_student s : now.adj_student) {
                s.adj_degree--;
                if(s.adj_degree == 0) q.add(s);
                //now.adj_student.remove(s);
            }
        }

        Collections.reverse(ans);

        for(int v : ans) {
            System.out.print(v + " ");
        }
    }

    private static void init_setting() throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        students = new BOJ2252_student[N+1];
        ans = new ArrayList<>();

        for(int i = 1; i <= N; i++) {
            students[i] = new BOJ2252_student(i);
        }

        for(int i = 1; i <= M; i++) {
            input = br.readLine().split(" ");

            int a = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);

            students[b].adj_student.add(students[a]);
            students[a].adj_degree++;
        }
    }
}
