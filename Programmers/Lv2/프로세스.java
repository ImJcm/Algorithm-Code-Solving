package Programmers;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/*
프로세스
제출 내역
문제 설명
운영체제의 역할 중 하나는 컴퓨터 시스템의 자원을 효율적으로 관리하는 것입니다. 이 문제에서는 운영체제가 다음 규칙에 따라 프로세스를 관리할 경우 특정 프로세스가 몇 번째로 실행되는지 알아내면 됩니다.

1. 실행 대기 큐(Queue)에서 대기중인 프로세스 하나를 꺼냅니다.
2. 큐에 대기중인 프로세스 중 우선순위가 더 높은 프로세스가 있다면 방금 꺼낸 프로세스를 다시 큐에 넣습니다.
3. 만약 그런 프로세스가 없다면 방금 꺼낸 프로세스를 실행합니다.
  3.1 한 번 실행한 프로세스는 다시 큐에 넣지 않고 그대로 종료됩니다.
예를 들어 프로세스 4개 [A, B, C, D]가 순서대로 실행 대기 큐에 들어있고, 우선순위가 [2, 1, 3, 2]라면 [C, D, A, B] 순으로 실행하게 됩니다.

현재 실행 대기 큐(Queue)에 있는 프로세스의 중요도가 순서대로 담긴 배열 priorities와, 몇 번째로 실행되는지 알고싶은 프로세스의 위치를 알려주는 location이 매개변수로 주어질 때, 해당 프로세스가 몇 번째로 실행되는지 return 하도록 solution 함수를 작성해주세요.

제한사항
priorities의 길이는 1 이상 100 이하입니다.
priorities의 원소는 1 이상 9 이하의 정수입니다.
priorities의 원소는 우선순위를 나타내며 숫자가 클 수록 우선순위가 높습니다.
location은 0 이상 (대기 큐에 있는 프로세스 수 - 1) 이하의 값을 가집니다.
priorities의 가장 앞에 있으면 0, 두 번째에 있으면 1 … 과 같이 표현합니다.
입출력 예
priorities	location	return
[2, 1, 3, 2]	2	1
[1, 1, 9, 1, 1, 1]	0	5
입출력 예 설명
예제 #1

문제에 나온 예와 같습니다.

예제 #2

6개의 프로세스 [A, B, C, D, E, F]가 대기 큐에 있고 중요도가 [1, 1, 9, 1, 1, 1] 이므로 [C, D, E, F, A, B] 순으로 실행됩니다. 따라서 A는 5번째로 실행됩니다.

※ 공지 - 2023년 4월 21일 문제 지문이 리뉴얼되었습니다.
 */
/*
문제를 읽고 priorityQueue의 정렬기준을 process class를 기준으로 priority값이 큰값으로 내림차순을 적용하고 priority값이 같으면
position이 큰 순서로 내림차순하는 방법으로 Comparator를 구성하려고 하였지만 PriorityQueue에 데이터의 순서가 생각처럼 나열되지 않아서
priority값을 기준으로 내림차순 정렬한 PriorityQueue인 pq와 priories와 같은 순서를 갖는 Queue인 q를 통해 다음의 로직을 수행한다.
1. q가 비워질 때까지 반복문을 수행한다.
2. 현재 프로세스를 나타내는 now_process에 q.poll()로 설정한다.
3. 수행되야할 프로세스의 우선순위를 나타내는 high_priority_process에 pq.peek()로 설정한다.
4. now_process의 priority 값과 high_priority_process의 priority을 비교한다.
4-1. now_process.priority < high_priority_process.priority이면, q에 now_process를 삽입한다.
4-2. now_process.priority >= high_priority_process.priority이면, 5번 로직을 수행한다.
    (모든 상황이 ==, eqauls만을 고려한다. hig_priority_process의 priority의 값은 priorities의 최대값을 넘어갈 수 없다.)
5. location 값과 now_process.position 값을 비교한다.
5-1. location == now_process.position 이면, 로직을 종료한다.
5-2. location != now_process.position 이면, answer을 1증가시키고 q.remove(now_process), pq.poll();를 수행하여 다음 수행할 프로세스의 정보값을 업데이트한다.
 */
class 프로세스 {
    static class process {
        int priority;
        int position;

        process(int priority, int position) {
            this.priority = priority;
            this.position = position;
        }
    }

    /*static PriorityQueue<process> pq = new PriorityQueue<>((p1,p2) -> {
        if(p1.priority < p2.priority) return 1;
        else if(p1.priority > p2.priority) return -1;
        else {
            if(p1.position < p2.position) return 1;
            else if(p1.position > p2.position) return -1;
            else return 0;
        }
    });*/
    static PriorityQueue<process> pq = new PriorityQueue<>(new Comparator<process>() {
        @Override
        public int compare(process o1, process o2) {
            return o2.priority - o1.priority;
        }
    });
    static Queue<process> q = new LinkedList<>();

    public static int solution(int[] priorities, int location) {
        int answer = 1;

        init_setting(priorities, location);

        while(!q.isEmpty()) {
            process now_process = q.poll();
            process high_priority_process = pq.peek();

            if(now_process.priority < high_priority_process.priority) {
                q.offer(now_process);
            } else {
                if(location == now_process.position) {
                    break;
                }
                q.remove(now_process);
                pq.poll();
                answer++;
            }
        }
        return answer;
    }
    static void init_setting(int[] p, int l) {
        for(int i=0;i<p.length;i++) {
            pq.add(new process(p[i],i));
            q.add(new process(p[i],i));
        }
    }

    public static void main(String[] args) {
        //int [] priorities = {1,1,9,1,1,1};
        //int location = 0;
        int [] priorities = {2, 1, 3, 2};
        int location = 2;

        System.out.println(solution(priorities,location));
    }
}
