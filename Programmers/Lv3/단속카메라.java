package Lv3;

import java.util.Arrays;
import java.util.Comparator;

/*
단속카메라
제출 내역
문제 설명
고속도로를 이동하는 모든 차량이 고속도로를 이용하면서 단속용 카메라를 한 번은 만나도록 카메라를 설치하려고 합니다.

고속도로를 이동하는 차량의 경로 routes가 매개변수로 주어질 때, 모든 차량이 한 번은 단속용 카메라를 만나도록 하려면 최소 몇 대의 카메라를 설치해야 하는지를 return 하도록 solution 함수를 완성하세요.

제한사항

차량의 대수는 1대 이상 10,000대 이하입니다.
routes에는 차량의 이동 경로가 포함되어 있으며 routes[i][0]에는 i번째 차량이 고속도로에 진입한 지점, routes[i][1]에는 i번째 차량이 고속도로에서 나간 지점이 적혀 있습니다.
차량의 진입/진출 지점에 카메라가 설치되어 있어도 카메라를 만난것으로 간주합니다.
차량의 진입 지점, 진출 지점은 -30,000 이상 30,000 이하입니다.
입출력 예

routes	return
[[-20,-15], [-14,-5], [-18,-13], [-5,-3]]	2
입출력 예 설명

-5 지점에 카메라를 설치하면 두 번째, 네 번째 차량이 카메라를 만납니다.

-15 지점에 카메라를 설치하면 첫 번째, 세 번째 차량이 카메라를 만납니다.
 */
/*
알고리즘 핵심
Greedy
1. 차량의 출구 지점을 기준으로 오름차순 정렬한다.
(출구를 지점으로 카메라를 설치 시, 해당 출자 이전의 출입 또는 출구를 하는 차량은 추가 카메라 설치가 불필요하다.)
2. 가장 왼쪽의 차량의 출구를 지점으로 시작하여 다음 차량의 출입 지점과 비교하여 카메라 설치를 결정한다.
출구 지점보다 출입 지점이 크거나 같은 경우, 다음 차량은 출구 지점의 카메라로 발견이 가능하므로 다음 차량의 출입 지점으로 2번 과정을 수행한다.
출구 지점보다 출입 지점이 작은 경우, 이전 차량의 출구 지점에 카메라를 설치하고 출입 지점의 차량의 출구 지점에 카메라 지점으로 설정한다.

처음 접근으로 입구 지점을 기준으로 오름차순 정렬하고, 지점 별로 존재하는 차량의 수를 저장하는 배열을 만들고 해당 배열의 값을 내림차순 정렬하여
해당 지점에 카메라를 배치하여 해당 지점에 해당하는 차량을 지우는 형태로 구성하였는데 잘못된 방법이였다.
그래서, 접근 방법을 참고하였다.

이 문제의 핵심은 입구 지점이 빠른 순서의 차량의 출구 지점을 기준으로 카메라를 설치하여 다른 차량이 들어간 경우를 빼는 것으로 탐욕 알고리즘 구조이다.

issue : Efficiency#4 - time out
가능성
1. Arrays.sort는 binarySort를 지원하기 때문에 O(NlogN)이지만, 평균과 최악의 경우인 O(N^2)이므로 정렬하는 과정에서 시간 초과가 발생했을 가능성이 높다.
2. 문제에서는 주어지지 않은 조건 : (입구 < 출구) or (입구 > 출구) 두 가지 경우를 고려해야 한다.
-> (입구 > 출구)인 경우를 문제에서는 가정하는 듯하다.

출구를 기준으로 오름차순한 경우, 효율성#4에서 시간초과가 발생하지만, 출구 기준 오름차순 + 같을 경우 입구 기준 오름차순으로 할 시,
통과되는 현상이 발생한다.
출구지점을 오름차순만 적용한 테스트와 출구지점 오름차순 + 출입지점 오름차순인 테스트의 정확성 테스트에 걸린 시간은 약 2ms차이가 나는 것으로 확인했다.

테스트#2
아무리 생각해봐도 출구 지점을 기준으로 오름차순한 테스트에 출입 지점은 아무런 상관이 없는데 테스트에 걸린시간이 차이가 나는 것이
이해가 가지않았다.
그래서 Comparator의 부분을 수정해보는 테스트를 진행하였다.
기존 : Arrays.sort(routes, Comparator.comparingInt(route -> route[1]));
수정 : Arrays.sort(routes, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o1[1] - o2[1];
                    //return o1[1] == o2[1] ? o1[0] - o2[0] : o1[1] - o2[1];
                }
            });
new Comparator로 지정한 결과 이 부분이 원인인 것을 확인하였다.
람다식을 사용하는 경우 내부적으로 처리과정에서 시간을 사용하는 것이다.
 */
public class 단속카메라 {
    static void main() {
        int[][] routes = new int[][] {
                //{-20,-15},{-14,-5},{-18,-13},{-5,-3}
                //{1,5},{8,5},{6,5},{7,5} // > 이런 케이스는 없다고 가정하는 듯?
                //{1,5},{4,5},{2,5},{3,5}
                {0,12},{1,12},{2,12},{3,12},{5,6},{6,12},{10,12},{9,12}
        };

        Solve task = new Solve();
        System.out.println(task.solution(routes));
    }

    private static class Solve {
        private int ans;
        private int[][] sorted_routes;

        public int solution(int[][] routes) {
            init_setting(routes);

            place_camera(sorted_routes);

            return ans;
        }

        private void place_camera(int[][] sortedRoutes) {
            ans = 0;
            int cam_pos = -30001;

            for(int i = 0; i < sortedRoutes.length; i++) {
                int s = sortedRoutes[0][0];
                int e = sortedRoutes[0][1];

                if(s > cam_pos) {
                    ans++;
                    cam_pos = e;
                }
            }
        }

        private void init_setting(int[][] routes) {
            ans = 0;

            sorted_routes = Arrays.copyOf(routes, routes.length);
            Arrays.sort(sorted_routes, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o1[1] - o2[1];
                }
            });
            // 람다식의 경우, 시간소모가 크다.
            //Arrays.sort(sorted_routes, Comparator.comparingInt(route -> route[1])); // binarySort
            //Arrays.sort(sorted_routes, Comparator.comparing(route -> route[1]));
            //Arrays.sort(sorted_routes, (a,b) -> a[1] - b[1]);
        }
    }
}


