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

 */
public class 단속카메라 {
    static void main() {
        int[][] routes = new int[][] {
                {-20,-15},{-14,-5},{-18,-13},{-5,-3}
        };

        Solve task = new Solve();
        System.out.println(task.solution(routes));
    }

    private static class Solve {
        private class section {
            int section;
            int car_flow;

            public section(int section, int car_flow) {
                this.section = section;
                this.car_flow = car_flow;
            }
        }
        private final int ROUTE = 30000;
        private int ans;
        private int[][] sorted_routes;
        private boolean[] cars;
        private section[] cars_in_section;

        public int solution(int[][] routes) {
            init_setting(routes);

            check_car_flow(sorted_routes, cars_in_section);

            place_camera(sorted_routes, cars_in_section,cars);

            return ans;
        }

        private void place_camera(int[][] sorted_routes, section[] cars_in_section, boolean[] cars) {
            for(section s : cars_in_section) {
                if(s.car_flow == 0) continue;
                int sec = s.section;
                int cnt = 0;

                for(int i = 0; i < sorted_routes.length; i++) {
                    if(sorted_routes[i][0] + ROUTE > sec) break;
                    if(sorted_routes[i][0] + ROUTE <= sec && sec <= sorted_routes[i][1] + ROUTE) {
                        if(cars[i]) break;
                        cars[i] = true;
                        cnt++;
                    }
                }

                if(cnt == s.car_flow) ans++;
            }
        }

        private void check_car_flow(int[][] sorted_routes, section[] cars_in_section) {
            for(int[] r : sorted_routes) {
                int in = r[0] + ROUTE;
                int out = r[1] + ROUTE;

                for(int i = in; i <= out; i++) {
                    cars_in_section[i].car_flow++;
                }
            }

            Arrays.sort(cars_in_section, (s1,s2) -> s2.car_flow - s1.car_flow);
        }

        private void init_setting(int[][] routes) {
            ans = 0;

            cars = new boolean[routes.length];
            cars_in_section = new section[ROUTE * 2 + 1];

            Arrays.setAll(cars_in_section, i -> new section(i,0));

            sorted_routes = routes.clone();

            Arrays.sort(sorted_routes, Comparator.comparingInt(o -> o[0]));
        }
    }
}


