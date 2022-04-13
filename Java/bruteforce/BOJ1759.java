package BOJ.bruteforce;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

/*
암호 만들기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	44537	20990	14546	44.771%
문제
바로 어제 최백준 조교가 방 열쇠를 주머니에 넣은 채 깜빡하고 서울로 가 버리는 황당한 상황에 직면한 조교들은, 702호에 새로운 보안 시스템을 설치하기로 하였다. 이 보안 시스템은 열쇠가 아닌 암호로 동작하게 되어 있는 시스템이다.

암호는 서로 다른 L개의 알파벳 소문자들로 구성되며 최소 한 개의 모음(a, e, i, o, u)과 최소 두 개의 자음으로 구성되어 있다고 알려져 있다. 또한 정렬된 문자열을 선호하는 조교들의 성향으로 미루어 보아 암호를 이루는 알파벳이 암호에서 증가하는 순서로 배열되었을 것이라고 추측된다. 즉, abc는 가능성이 있는 암호이지만 bac는 그렇지 않다.

새 보안 시스템에서 조교들이 암호로 사용했을 법한 문자의 종류는 C가지가 있다고 한다. 이 알파벳을 입수한 민식, 영식 형제는 조교들의 방에 침투하기 위해 암호를 추측해 보려고 한다. C개의 문자들이 모두 주어졌을 때, 가능성 있는 암호들을 모두 구하는 프로그램을 작성하시오.

입력
첫째 줄에 두 정수 L, C가 주어진다. (3 ≤ L ≤ C ≤ 15) 다음 줄에는 C개의 문자들이 공백으로 구분되어 주어진다. 주어지는 문자들은 알파벳 소문자이며, 중복되는 것은 없다.

출력
각 줄에 하나씩, 사전식으로 가능성 있는 암호를 모두 출력한다.

예제 입력 1
4 6
a t c i s w
예제 출력 1
acis
acit
aciw
acst
acsw
actw
aist
aisw
aitw
astw
cist
cisw
citw
istw
 */
/*
 * 입력값을 정렬한 뒤, DFS로 모든 입력값을 만들어내고, 그 중 최소 한 개의 모음(a,e,i,o,u)와 최소 두 개의
 * 자음을 사용하도록 조건을 건다.
 * 조건 : 모음과 자음을 visited를 결정하는 부분에서 개수를 카운터하고 DFS를 종료하는 시점 이후에도 +,-를 통해서
 * 최신화한다.
 * 기저사례에서 depth=L이지만, 모음과 자음의 최소개수가 성립하지않으면 return, 아니면 sb에 append한다.
 */
public class BOJ1759 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static char[] box,input;
    static boolean[] visited;
    static int L,C;
    static String[] temp;
    static StringBuilder sb = new StringBuilder();
    static String moumm = "aeiou";
    static int m_c,j_c;

    public static void main(String[] args) throws IOException {
        temp = br.readLine().split(" ");
        L = Integer.parseInt(temp[0]);
        C = Integer.parseInt(temp[1]);

        box = new char[L];
        input = new char[C];
        visited = new boolean[C];

        temp = br.readLine().split(" ");
        for(int i=0;i<C;i++) {
            input[i] = temp[i].charAt(0);
        }

        Arrays.sort(input);

        DFS(0,0,0,0);

        System.out.println(sb.toString());
    }

    static void DFS(int depth,int start,int moumm_c,int jaumm_c) {
        if(depth == L) {
            if(moumm_c < 1 || jaumm_c < 2) {
                return;
            }
            for(char c : box)
                sb.append(c);
            sb.append("\n");
            return;
        }

        for(int i=start;i<C;i++) {
            if(visited[i]) {
                continue;
            }
            if(depth == 0) {
                m_c = 0;
                j_c = 0;
            }

            box[depth] = input[i];
            if(moumm.contains(String.valueOf(input[i]))) {
                m_c++;
            } else {
                j_c++;
            }
            visited[i] = true;
            DFS(depth+1,i,m_c,j_c);
            if(moumm.contains(String.valueOf(input[i]))) {
                m_c--;
            } else {
                j_c--;
            }
            visited[i] = false;

        }
    }
}
