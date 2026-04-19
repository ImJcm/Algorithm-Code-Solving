package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/*
쇠막대기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	60406	39409	29234	65.352%
문제
여러 개의 쇠막대기를 레이저로 절단하려고 한다. 효율적인 작업을 위해서 쇠막대기를 아래에서 위로 겹쳐 놓고, 레이저를 위에서 수직으로 발사하여 쇠막대기들을 자른다. 쇠막대기와 레이저의 배치는 다음 조건을 만족한다.

쇠막대기는 자신보다 긴 쇠막대기 위에만 놓일 수 있다. - 쇠막대기를 다른 쇠막대기 위에 놓는 경우 완전히 포함되도록 놓되, 끝점은 겹치지 않도록 놓는다.
각 쇠막대기를 자르는 레이저는 적어도 하나 존재한다.
레이저는 어떤 쇠막대기의 양 끝점과도 겹치지 않는다.
아래 그림은 위 조건을 만족하는 예를 보여준다. 수평으로 그려진 굵은 실선은 쇠막대기이고, 점은 레이저의 위치, 수직으로 그려진 점선 화살표는 레이저의 발사 방향이다.



이러한 레이저와 쇠막대기의 배치는 다음과 같이 괄호를 이용하여 왼쪽부터 순서대로 표현할 수 있다.

레이저는 여는 괄호와 닫는 괄호의 인접한 쌍 ‘( ) ’ 으로 표현된다. 또한, 모든 ‘( ) ’는 반드시 레이저를 표현한다.
쇠막대기의 왼쪽 끝은 여는 괄호 ‘ ( ’ 로, 오른쪽 끝은 닫힌 괄호 ‘) ’ 로 표현된다.
위 예의 괄호 표현은 그림 위에 주어져 있다.

쇠막대기는 레이저에 의해 몇 개의 조각으로 잘려지는데, 위 예에서 가장 위에 있는 두 개의 쇠막대기는 각각 3개와 2개의 조각으로 잘려지고, 이와 같은 방식으로 주어진 쇠막대기들은 총 17개의 조각으로 잘려진다.

쇠막대기와 레이저의 배치를 나타내는 괄호 표현이 주어졌을 때, 잘려진 쇠막대기 조각의 총 개수를 구하는 프로그램을 작성하시오.

입력
한 줄에 쇠막대기와 레이저의 배치를 나타내는 괄호 표현이 공백없이 주어진다. 괄호 문자의 개수는 최대 100,000이다.

출력
잘려진 조각의 총 개수를 나타내는 정수를 한 줄에 출력한다.

예제 입력 1
()(((()())(())()))(())
예제 출력 1
17
예제 입력 2
(((()(()()))(())()))(()())
예제 출력 2
24
출처
Olympiad > 한국정보올림피아드 > 한국정보올림피아드시․도지역본선 > 지역본선 2015 > 중등부 2번

Olympiad > 한국정보올림피아드 > 한국정보올림피아드시․도지역본선 > 지역본선 2015 > 초등부 3번

데이터를 만든 사람: baekjoon
데이터를 추가한 사람: djm03178, junqwoopark
알고리즘 분류
자료 구조
스택
 */
/*
알고리즘 핵심
자료구조 (Stack)
1. 철 막대기의 시작부분을 저장하는 stack_iron, 막대마다 적용되는 레이저의 개수를 저장하는 stack_razer의 Stack을 사용한다.
2. 입력으로 주어진 쇠막대기를 앞부분부터 끝부분까지 순차적으로 진행하여 해당 위치의 값에 따라 조건을 수행한다.
3. 현재 위치에서의 쇠막대기 형태값 = iron, 현재 위치에서 다음 쇠막대기 형태값 = next_iron, 레이저의 수 = razer를 저장한다.
4. 현재 위치의 쇠막대기 형태가 "("인 경우, 다음 쇠막대기 형태의 값에 따라 로직을 수행한다.
4-1. "("인 경우, 이전까지 누적된 레이저의 수를 stack_razer에 저장하고, stack_iron에 막대기의 시작부분을 저장한다.
4-2. ")"인 경우, 레이저를 의미하는 것으로 is_razer의 값을 true로 설정한다.
5. 현재 위치에서의 쇠막대기 형태가 ")"인 경우, is_razer의 값에 따라 로직을 수행한다.
5-1. true인 경우, razer를 1을 증가시키고, is_razer를 false로 설정한다.
5-2. false인 경우, 쇠막대기의 끝부분을 의미하는 것으로 해당 막대에 적용되는 razer의 개수 + 1 만큼 ans에 누적하여 더하고, stack_iron에 해당 쇠막대기 앞부분을 제거하고,
stack_razer의 최근 값을 제거(pop)한다.
 */
public class BOJ10799 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int ans;
    static String[] iron_bar;
    static Stack<String> stack_iron;
    static Stack<Integer> stack_razer;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    private static void solve() {
        int razer = 0;
        boolean is_razer = false;

        for(int i = 0; i < iron_bar.length; i++) {
            String iron = iron_bar[i];
            String next_iron = iron_bar[i].equals("(") ? iron_bar[i + 1] : "";
            if(stack_iron.isEmpty()) razer = 0;

            switch (iron) {
                case "(":
                    if(next_iron.equals("(")) {
                        stack_razer.push(razer);
                        stack_iron.push(iron);
                        razer = 0;
                    } else {
                        is_razer = true;
                    }
                    break;
                case ")":
                    if(is_razer) {
                        razer++;
                        is_razer = false;
                    } else {
                        ans += (razer + 1);
                        stack_iron.pop();
                        razer += (stack_razer.pop());
                    }
                    break;
            }
        }

        System.out.println(ans);
    }

    private static void init_setting() throws IOException {
        iron_bar = br.readLine().split("");

        stack_iron = new Stack<>();
        stack_razer = new Stack<>();

        ans = 0;
    }
}
