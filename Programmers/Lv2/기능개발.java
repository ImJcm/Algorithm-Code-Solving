package Programmers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
기능개발
제출 내역
문제 설명
프로그래머스 팀에서는 기능 개선 작업을 수행 중입니다. 각 기능은 진도가 100%일 때 서비스에 반영할 수 있습니다.

또, 각 기능의 개발속도는 모두 다르기 때문에 뒤에 있는 기능이 앞에 있는 기능보다 먼저 개발될 수 있고, 이때 뒤에 있는 기능은 앞에 있는 기능이 배포될 때 함께 배포됩니다.

먼저 배포되어야 하는 순서대로 작업의 진도가 적힌 정수 배열 progresses와 각 작업의 개발 속도가 적힌 정수 배열 speeds가 주어질 때 각 배포마다 몇 개의 기능이 배포되는지를 return 하도록 solution 함수를 완성하세요.

제한 사항
작업의 개수(progresses, speeds배열의 길이)는 100개 이하입니다.
작업 진도는 100 미만의 자연수입니다.
작업 속도는 100 이하의 자연수입니다.
배포는 하루에 한 번만 할 수 있으며, 하루의 끝에 이루어진다고 가정합니다. 예를 들어 진도율이 95%인 작업의 개발 속도가 하루에 4%라면 배포는 2일 뒤에 이루어집니다.
입출력 예
progresses	speeds	return
[93, 30, 55]	[1, 30, 5]	[2, 1]
[95, 90, 99, 99, 80, 99]	[1, 1, 1, 1, 1, 1]	[1, 3, 2]
입출력 예 설명
입출력 예 #1
첫 번째 기능은 93% 완료되어 있고 하루에 1%씩 작업이 가능하므로 7일간 작업 후 배포가 가능합니다.
두 번째 기능은 30%가 완료되어 있고 하루에 30%씩 작업이 가능하므로 3일간 작업 후 배포가 가능합니다. 하지만 이전 첫 번째 기능이 아직 완성된 상태가 아니기 때문에 첫 번째 기능이 배포되는 7일째 배포됩니다.
세 번째 기능은 55%가 완료되어 있고 하루에 5%씩 작업이 가능하므로 9일간 작업 후 배포가 가능합니다.

따라서 7일째에 2개의 기능, 9일째에 1개의 기능이 배포됩니다.

입출력 예 #2
모든 기능이 하루에 1%씩 작업이 가능하므로, 작업이 끝나기까지 남은 일수는 각각 5일, 10일, 1일, 1일, 20일, 1일입니다. 어떤 기능이 먼저 완성되었더라도 앞에 있는 모든 기능이 완성되지 않으면 배포가 불가능합니다.

따라서 5일째에 1개의 기능, 10일째에 3개의 기능, 20일째에 2개의 기능이 배포됩니다.

※ 공지 - 2020년 7월 14일 테스트케이스가 추가되었습니다.
 */
/*
remaining_day에 progresses에 해당하는 업무에 speeds를 적용했을 때 수행완료되기 까지의 걸린 일을 저장한다.
reamining_progress의 길이만큼 반복하는 동안 첫번째 업무의 걸린 날을 시작으로 최대 업무 수행 날을 정하고 이후의 업무가 최대 업무수행 날짜보다
낮은 업무들을 completed_progress를 + 1하여 누적한다.
최대 업무수행 날짜가 업무수행 날짜보다 큰 업무가 오기전 completed_progress를 answer에 저장하고, 다음으로 가능한 최대 업무 수행날짜로 위
과정을 반복하고 배열의 모든 원소를 다 적용할 때까지 적용한다.
 */
class 기능개발 {
    public static int[] solution_for(int[] progresses, int[] speeds) {
        ArrayList<Integer> answer = new ArrayList<>();
        int progressing_day = 0;
        int[] remaining_progress = new int[progresses.length];

        for(int i=0;i < progresses.length;i++) {
            int remaining_day = ((100 - progresses[i]) / speeds[i])
                    + ((100 - progresses[i]) % speeds[i] != 0 ? 1 : 0);

            remaining_progress[i] = remaining_day;
        }

        for(int i=0;i<remaining_progress.length;i++) {
            progressing_day = Math.max(progressing_day, remaining_progress[i]);

            int completed_progress = 1;
            for(int j=i+1;j<remaining_progress.length;j++) {
                if(remaining_progress[j] <= progressing_day) {
                    completed_progress++;
                    i++;
                } else {
                    break;
                }
            }
            answer.add(completed_progress);
        }

        return answer.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int[] solution(int[] progresses, int[] speeds) {
        ArrayList<Integer> answer = new ArrayList<>();
        int progressing_day = 0;
        Queue<Integer> remaining_progress = new LinkedList<>();

        for(int i=0;i < progresses.length;i++) {
            //int remaining_day = ((100 - progresses[i]) / speeds[i])
            //        + ((100 - progresses[i]) % speeds[i] != 0 ? 1 : 0);

            remaining_progress.add((int)Math.ceil((100 - progresses[i]) / speeds[i]));
        }

        int idx = 0;
        while(!remaining_progress.isEmpty()) {
            progressing_day = Math.max(progressing_day, remaining_progress.get(idx));
            boolean check = false;
            int completed_progress = 1;


            remaining_progress.remove(idx);
            while(remaining_progress.get(idx++) <= progressing_day) {
                completed_progress++;
            }

            answer.add(completed_progress);
        }

        return answer.stream().mapToInt(Integer::intValue).toArray();
    }


    public static void main(String[] args) {
        int[] progresses = {95, 90, 99, 99, 80, 99};
        int[] speeds = {1, 1, 1, 1, 1, 1};

        System.out.println(Arrays.toString(solution(progresses, speeds)));
    }
}
