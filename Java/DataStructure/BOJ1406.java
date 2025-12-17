package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

/*
에디터 다국어

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.3 초 (하단 참고)	512 MB	141426	40351	27127	27.113%
문제
한 줄로 된 간단한 에디터를 구현하려고 한다. 이 편집기는 영어 소문자만을 기록할 수 있는 편집기로, 최대 600,000글자까지 입력할 수 있다.

이 편집기에는 '커서'라는 것이 있는데, 커서는 문장의 맨 앞(첫 번째 문자의 왼쪽), 문장의 맨 뒤(마지막 문자의 오른쪽), 또는 문장 중간 임의의 곳(모든 연속된 두 문자 사이)에 위치할 수 있다. 즉 길이가 L인 문자열이 현재 편집기에 입력되어 있으면, 커서가 위치할 수 있는 곳은 L+1가지 경우가 있다.

이 편집기가 지원하는 명령어는 다음과 같다.

L	커서를 왼쪽으로 한 칸 옮김 (커서가 문장의 맨 앞이면 무시됨)
D	커서를 오른쪽으로 한 칸 옮김 (커서가 문장의 맨 뒤이면 무시됨)
B	커서 왼쪽에 있는 문자를 삭제함 (커서가 문장의 맨 앞이면 무시됨)
삭제로 인해 커서는 한 칸 왼쪽으로 이동한 것처럼 나타나지만, 실제로 커서의 오른쪽에 있던 문자는 그대로임
P $	$라는 문자를 커서 왼쪽에 추가함
초기에 편집기에 입력되어 있는 문자열이 주어지고, 그 이후 입력한 명령어가 차례로 주어졌을 때, 모든 명령어를 수행하고 난 후 편집기에 입력되어 있는 문자열을 구하는 프로그램을 작성하시오. 단, 명령어가 수행되기 전에 커서는 문장의 맨 뒤에 위치하고 있다고 한다.

입력
첫째 줄에는 초기에 편집기에 입력되어 있는 문자열이 주어진다. 이 문자열은 길이가 N이고, 영어 소문자로만 이루어져 있으며, 길이는 100,000을 넘지 않는다. 둘째 줄에는 입력할 명령어의 개수를 나타내는 정수 M(1 ≤ M ≤ 500,000)이 주어진다. 셋째 줄부터 M개의 줄에 걸쳐 입력할 명령어가 순서대로 주어진다. 명령어는 위의 네 가지 중 하나의 형태로만 주어진다.

출력
첫째 줄에 모든 명령어를 수행하고 난 후 편집기에 입력되어 있는 문자열을 출력한다.

예제 입력 1
abcd
3
P x
L
P y
예제 출력 1
abcdyx
예제 입력 2
abc
9
L
L
L
L
L
P x
L
B
P y
예제 출력 2
yxabc
예제 입력 3
dmih
11
B
B
P x
L
B
B
B
P y
D
D
P z
예제 출력 3
yxz
출처
Olympiad > Croatian Highschool Competitions in Informatics > 2004 > National Competition #1 - Juniors 2번

문제를 번역한 사람: author5
데이터를 추가한 사람: djm03178
메모리 제한을 수정한 사람: djm03178
시간 제한을 수정한 사람: jh05013
어색한 표현을 찾은 사람: naong606
문제의 오타를 찾은 사람: wkd48632
알고리즘 분류
자료 구조
스택
연결 리스트
시간 제한
Java 8: 2 초
Java 8 (OpenJDK): 2 초
Kotlin (JVM): 2 초
C# 6.0 (Mono): 2 초
C# 3.0 (Mono): 2 초
VB.NET 2.0 (Mono): 2 초
VB.NET 4.0 (Mono): 2 초
 */
/*
알고리즘 핵심
자료구조 (Stack)
1. 커서를 기준으로 커서 앞부분을 나타내는 editor_front, 뒷부분을 나타내는 editor_back를 사용한다.
2. L의 경우 editor_front의 가장 뒷문자를 pop하여 editor_back에 push함으로써 커서가 앞으로 움직이는 것으로 나타낼 수 있다.
3. D의 경우 editor_back의 첫문자인 pop하여 나오는 문자를 editor_front에 push함으로써 커서가 뒤로 움직이는 것으로 나타낼 수 있다.
4. B의 경우 커서 기준 왼쪽문자를 지우는 것이므로 editor_front의 뒷문자를 pop을 통해 삭제한다.
5. P의 경우 커서 기준 왼쪽에 문자를 추가하는 것이므로 editor_front에 문자를 push한다.

LinkedList를 사용하였을 때, remove(index), add(index)와 같은 함수는 O(K)이므로 시간초과가 발생한다.
Stack과 LinkedList를 혼용하여 로직을 구성한 경우, 시간초과가 발생하였는데 개인적인 생각으로 원인을 addLast()에서 O(K)가 발생한 것이라고
생각한다.
 */
public class BOJ1406 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    //static LinkedList<String> editor,editor_back;
    static Stack<String> editor_front,editor_back;
    static int M,cursor;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    /*
        시간초과 발생 : 0 %
        원인 : list.remove(index), list.add(index)의 경우 index 만큼 조회하여 찾은 후 remove, add를 수행하기 때문에
        O(N)이 걸리는 것으로 예상한다.
     */
    /*private static void solve_timeOut_0() throws IOException{
        for(int i = 0; i < M; i++) {
            String[] o = br.readLine().split(" ");

            switch (o[0]) {
                case "L":
                    cursor = Math.max(cursor - 1, 0);
                    break;
                case "D":
                    cursor = Math.min(cursor + 1, editor.size() - 1);
                    break;
                case "B":
                    if(cursor > 0) editor.remove(cursor--);
                    break;
                case "P":
                    if(cursor + 1 == editor.size()) editor.addLast(o[1]);
                    else editor.add(cursor + 1,o[1]);
                    cursor++;
                    break;
            }
        }

        for(int i = 1; i < editor.size(); i++) {
            System.out.print(editor.get(i));
        }
    }*/

    /*
        시간초과 : 50 %
     */
    /*private static void solve_timeOut_50() throws IOException{
        for(int i = 0; i < M; i++) {
            String[] o = br.readLine().split(" ");

            switch (o[0]) {
                case "L":
                    if(!editor.isEmpty()) editor_back.addFirst(editor.removeLast());
                    break;
                case "D":
                    if(!editor_back.isEmpty()) editor.addLast(editor_back.removeFirst());
                    break;
                case "B":
                    if(!editor.isEmpty()) editor.removeLast();
                    break;
                case "P":
                    editor.addLast(o[1]);
                    break;
            }
        }

        editor.addAll(editor_back);

        for(String s : editor) {
            System.out.print(s);
        }
    }*/

    /*
        시간초과 : 50 % - Stack + LinkedList
     */
    /*private static void solve_timeOut_50_Stack_LinkedList() throws IOException {
        for (int i = 0; i < M; i++) {
            String[] o = br.readLine().split(" ");

            switch (o[0]) {
                case "L":
                    if (!editor_front.isEmpty()) editor_back.addFirst(editor_front.pop());
                    break;
                case "D":
                    if (!editor_back.isEmpty()) editor_front.add(editor_back.removeFirst());
                    break;
                case "B":
                    if (!editor_front.isEmpty()) editor_front.pop();
                    break;
                case "P":
                    editor_front.add(o[1]);
                    break;
            }
        }

        editor_front.addAll(editor_back);

        for (String s : editor_front) {
            System.out.print(s);
        }
    }*/

    private static void solve() throws IOException {
        for (int i = 0; i < M; i++) {
            String[] o = br.readLine().split(" ");

            switch (o[0]) {
                case "L":
                    if (!editor_front.isEmpty()) editor_back.add(editor_front.pop());
                    break;
                case "D":
                    if (!editor_back.isEmpty()) editor_front.add(editor_back.pop());
                    break;
                case "B":
                    if (!editor_front.isEmpty()) editor_front.pop();
                    break;
                case "P":
                    editor_front.add(o[1]);
                    break;
            }
        }

        for (String s : editor_front) {
            sb.append(s);
        }

        while (!editor_back.isEmpty()) {
            sb.append(editor_back.pop());
        }

        System.out.println(sb.toString());
    }

    private static void init_setting() throws IOException {
        String input = br.readLine();

        //editor = new LinkedList<>();
        //editor_back = new LinkedList<>();

        //if(!input.isEmpty()) editor.addAll(Arrays.asList(input.split("")));

        //editor.addFirst("");

        //cursor = editor.size() - 1;

        editor_front = new Stack<>();
        editor_back = new Stack<>();

        sb = new StringBuilder();

        if(!input.isEmpty()) editor_front.addAll(Arrays.asList(input.split("")));

        M = Integer.parseInt(br.readLine());
    }
}
