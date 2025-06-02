import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.LinkedList;
import java.util.Queue;

/*
큐

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
0.5 초 (추가 시간 없음)	256 MB	85222	38261	29902	48.868%
문제
정수를 저장하는 큐를 구현한 다음, 입력으로 주어지는 명령을 처리하는 프로그램을 작성하시오.

명령은 총 여섯 가지이다.

push X: 정수 X를 큐에 넣는 연산이다.
pop: 큐에서 가장 앞에 있는 정수를 빼고, 그 수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.
size: 큐에 들어있는 정수의 개수를 출력한다.
empty: 큐가 비어있으면 1, 아니면 0을 출력한다.
front: 큐의 가장 앞에 있는 정수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.
back: 큐의 가장 뒤에 있는 정수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.
입력
첫째 줄에 주어지는 명령의 수 N (1 ≤ N ≤ 10,000)이 주어진다. 둘째 줄부터 N개의 줄에는 명령이 하나씩 주어진다. 주어지는 정수는 1보다 크거나 같고, 100,000보다 작거나 같다. 문제에 나와있지 않은 명령이 주어지는 경우는 없다.

출력
출력해야하는 명령이 주어질 때마다, 한 줄에 하나씩 출력한다.

예제 입력 1
15
push 1
push 2
front
back
size
empty
pop
pop
pop
size
empty
pop
push 3
empty
front
예제 출력 1
1
2
2
0
1
2
-1
0
1
-1
0
3
 */
public class BOJ10845 {
    static int N,back = -1;
    static String[] instruction;
    static Queue<Integer> queue = new LinkedList<Integer>();
    //static LinkedList<Integer> ll = new LinkedList<Integer>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        instruction = new String[N];

        for(int i=0;i<N;i++) {
            instruction[i] = br.readLine();
        }

        for(int i=0;i<N;i++) {
            String[] tmp = instruction[i].split(" ");
            switch (tmp[0]) {
                case "push":
                    if(queue.size() > 0) back = Integer.parseInt(tmp[1]);
                    queue.add(Integer.parseInt(tmp[1]));
                    break;
                case "pop":
                    if(queue.size() == 0) System.out.println("-1");
                    else System.out.println(queue.poll());
                    break;
                case "size":
                    System.out.println(queue.size());
                    break;
                case "empty":
                    if(queue.size() == 0) System.out.println("1");
                    else System.out.println("0");
                    break;
                case "front":
                    if(queue.size() == 0) System.out.println("-1");
                    else System.out.println(queue.peek());
                    break;
                case "back":
                    if(queue.size() == 0) System.out.println("-1");
                    else if(queue.size() == 1) System.out.println(queue.peek());
                    else System.out.println(back);
                    break;
            }
        }

    }
}
