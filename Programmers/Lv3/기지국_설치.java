package Lv3;

import java.util.LinkedList;
import java.util.Queue;

/*
기지국 설치
제출 내역
문제 설명
N개의 아파트가 일렬로 쭉 늘어서 있습니다. 이 중에서 일부 아파트 옥상에는 4g 기지국이 설치되어 있습니다. 기술이 발전해 5g 수요가 높아져 4g 기지국을 5g 기지국으로 바꾸려 합니다. 그런데 5g 기지국은 4g 기지국보다 전달 범위가 좁아, 4g 기지국을 5g 기지국으로 바꾸면 어떤 아파트에는 전파가 도달하지 않습니다.

예를 들어 11개의 아파트가 쭉 늘어서 있고, [4, 11] 번째 아파트 옥상에는 4g 기지국이 설치되어 있습니다. 만약 이 4g 기지국이 전파 도달 거리가 1인 5g 기지국으로 바뀔 경우 모든 아파트에 전파를 전달할 수 없습니다. (전파의 도달 거리가 W일 땐, 기지국이 설치된 아파트를 기준으로 전파를 양쪽으로 W만큼 전달할 수 있습니다.)

초기에, 1, 2, 6, 7, 8, 9번째 아파트에는 전파가 전달되지 않습니다.
기지국설치1_pvskxt.png

1, 7, 9번째 아파트 옥상에 기지국을 설치할 경우, 모든 아파트에 전파를 전달할 수 있습니다.
기지국설치2_kml0pb.png

더 많은 아파트 옥상에 기지국을 설치하면 모든 아파트에 전파를 전달할 수 있습니다.
기지국설치3_xhv7r3.png

이때, 우리는 5g 기지국을 최소로 설치하면서 모든 아파트에 전파를 전달하려고 합니다. 위의 예시에선 최소 3개의 아파트 옥상에 기지국을 설치해야 모든 아파트에 전파를 전달할 수 있습니다.

아파트의 개수 N, 현재 기지국이 설치된 아파트의 번호가 담긴 1차원 배열 stations, 전파의 도달 거리 W가 매개변수로 주어질 때, 모든 아파트에 전파를 전달하기 위해 증설해야 할 기지국 개수의 최솟값을 리턴하는 solution 함수를 완성해주세요

제한사항
N: 200,000,000 이하의 자연수
stations의 크기: 10,000 이하의 자연수
stations는 오름차순으로 정렬되어 있고, 배열에 담긴 수는 N보다 같거나 작은 자연수입니다.
W: 10,000 이하의 자연수
입출력 예
N	stations	W	answer
11	[4, 11]	1	3
16	[9]	2	3
입출력 예 설명
입출력 예 #1
문제의 예시와 같습니다

입출력 예 #2

초기에, 1~6, 12~16번째 아파트에는 전파가 전달되지 않습니다.
기지국설치4_nqfrmm.png

3, 6, 14번째 아파트 옥상에 기지국을 설치할 경우 모든 아파트에 전파를 전달할 수 있습니다.
기지국설치5_zh4ebk.png

이미지 - 문제의 링크를 통해 참고.
 */
/*
알고리즘 핵심
수학 + 구현
1. 기존의 설치된 기지국을 기준으로 두개의 기지국의 사이의 전파가 통하지 않는 구간의 수를 구한다.
2. 해당 구간을 2 * W + 1로 나누고, 나머지 값을 올림하여 기지국의 수를 카운팅한다.
 */
public class 기지국_설치 {
    static void main() {
        int N = 16;
        int[] stations = new int[] {
                1,3
        };
        int W = 2;

        Solve task = new Solve();
        System.out.println(task.solution(N, stations, W));
    }

    private static class Solve {
        private int answer;
        private int range;

        public int solution(int N, int[] stations, int W) {
            init_setting(N,W);

            build_stations(N,stations,W);

            return answer;
        }

        private void build_stations(int N, int[] stations, int W) {
            int remain_section = 0;

            remain_section = Math.max(0, stations[0] - W - 1);
            answer += (remain_section / range) + (remain_section % range == 0 ? 0 : 1);

            remain_section = Math.max(0, N - stations[stations.length - 1] - W);
            answer += (remain_section / range) + (remain_section % range == 0 ? 0 : 1);

            for(int i = 1; i < stations.length; i++) {
                remain_section = (stations[i] - W) - (stations[i - 1] + W + 1);
                if(remain_section < 0) continue;
                answer += (remain_section / range) + (remain_section % range == 0 ? 0 : 1);
            }
        }

        private void init_setting(int N, int W) {
            answer = 0;
            range = 2 * W + 1;
        }
    }
}
