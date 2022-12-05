/*
이모티콘

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	20508	7698	5079	33.917%
문제
영선이는 매우 기쁘기 때문에, 효빈이에게 스마일 이모티콘을 S개 보내려고 한다.

영선이는 이미 화면에 이모티콘 1개를 입력했다. 이제, 다음과 같은 3가지 연산만 사용해서 이모티콘을 S개 만들어 보려고 한다.

화면에 있는 이모티콘을 모두 복사해서 클립보드에 저장한다.
클립보드에 있는 모든 이모티콘을 화면에 붙여넣기 한다.
화면에 있는 이모티콘 중 하나를 삭제한다.
모든 연산은 1초가 걸린다. 또, 클립보드에 이모티콘을 복사하면 이전에 클립보드에 있던 내용은 덮어쓰기가 된다. 클립보드가 비어있는 상태에는 붙여넣기를 할 수 없으며, 일부만 클립보드에 복사할 수는 없다. 또한, 클립보드에 있는 이모티콘 중 일부를 삭제할 수 없다. 화면에 이모티콘을 붙여넣기 하면, 클립보드에 있는 이모티콘의 개수가 화면에 추가된다.

영선이가 S개의 이모티콘을 화면에 만드는데 걸리는 시간의 최솟값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 S (2 ≤ S ≤ 1000) 가 주어진다.

출력
첫째 줄에 이모티콘을 S개 만들기 위해 필요한 시간의 최솟값을 출력한다.

예제 입력 1
2
예제 출력 1
2
예제 입력 2
4
예제 출력 2
4
예제 입력 3
6
예제 출력 3
5
예제 입력 4
18
예제 출력 4
8
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
    1,2번 과정을 하나로 합쳐서 동작 2초의 동작으로 생각한다 - > x
    1,2,3 과정을 따로 동작을 생각해야 한다

    참고 : https://velog.io/@yanghl98/%EB/%B0/%B1/%EC/%A4/%80-14226-%EC%9D%B4%EB%AA%A8%ED%8B%B0%EC%BD%98-JAVA
    visited 배열의 size의 경우, 화면의 문자열의 길이 + 클립보드 문자열의 길이 = 1000 + 1000이기 떄문에
    화면상의 문자의 길이와 클립보드 문자의 길이에 따라 방문여부를 달리하고 중복 방문을 방지하기위해
    2차원 배열로 visited 배열을 선언하여 방문여부를 구분하였다
    1. 복사, 2. 삭제, 3. 붙여넣기 동작을 구분하여 각 맞는 조건을 설정하여 BFS 동작

 */
public class BOJ14226 {
    static class Step {
        int screen_num;
        int clipboard_num;
        int time;

        public Step(int sn, int cn, int t) {
            this.screen_num = sn;
            this.clipboard_num = cn;
            this.time = t;
        }
    }
    static int N;
    static boolean[][] visited = new boolean[2001][2001];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        System.out.println(BFS());
    }

    static int BFS() {
        Queue<Step> q = new LinkedList<>();
        q.offer(new Step(1,0,0));

        while(!q.isEmpty()) {
            Step now = q.poll();

            int screen_num = now.screen_num;
            int clipboard_num = now.clipboard_num;
            int time = now.time;

            if(screen_num == N) {
                return time;
            }

            if(screen_num > 0 && screen_num < 2001) {
                //1.복사
                if(!visited[screen_num][screen_num]) {
                    visited[screen_num][screen_num] = true;

                    q.offer(new Step(screen_num,screen_num,time+1));
                }

                //2.삭제
                if(!visited[screen_num-1][clipboard_num]) {
                    visited[screen_num-1][clipboard_num] = true;

                    q.offer(new Step(screen_num-1,clipboard_num,time+1));
                }

                //3.붙여넣기
                if(clipboard_num > 0 && screen_num + clipboard_num < 2001) {
                    if (!visited[screen_num + clipboard_num][clipboard_num]) {
                        visited[screen_num + clipboard_num][clipboard_num] = true;

                        q.offer(new Step(screen_num + clipboard_num, clipboard_num, time + 1));
                    }
                }
            }
        }
        return 0;
    }
}
