package Lv2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
호텔 대실
제출 내역
문제 설명
호텔을 운영 중인 코니는 최소한의 객실만을 사용하여 예약 손님들을 받으려고 합니다. 한 번 사용한 객실은 퇴실 시간을 기준으로 10분간 청소를 하고 다음 손님들이 사용할 수 있습니다.
예약 시각이 문자열 형태로 담긴 2차원 배열 book_time이 매개변수로 주어질 때, 코니에게 필요한 최소 객실의 수를 return 하는 solution 함수를 완성해주세요.

제한사항
1 ≤ book_time의 길이 ≤ 1,000
book_time[i]는 ["HH:MM", "HH:MM"]의 형태로 이루어진 배열입니다
[대실 시작 시각, 대실 종료 시각] 형태입니다.
시각은 HH:MM 형태로 24시간 표기법을 따르며, "00:00" 부터 "23:59" 까지로 주어집니다.
예약 시각이 자정을 넘어가는 경우는 없습니다.
시작 시각은 항상 종료 시각보다 빠릅니다.
입출력 예
book_time	result
[["15:00", "17:00"], ["16:40", "18:20"], ["14:20", "15:20"], ["14:10", "19:20"], ["18:20", "21:20"]]	3
[["09:10", "10:10"], ["10:20", "12:20"]]	1
[["10:20", "12:30"], ["10:20", "12:30"], ["10:20", "12:30"]]	3
입출력 예 설명
입출력 예 #1

example1
위 사진과 같습니다.

입출력 예 #2

첫 번째 손님이 10시 10분에 퇴실 후 10분간 청소한 뒤 두 번째 손님이 10시 20분에 입실하여 사용할 수 있으므로 방은 1개만 필요합니다.

입출력 예 #3

세 손님 모두 동일한 시간대를 예약했기 때문에 3개의 방이 필요합니다.
 */
public class 호텔_대실 {
    static void main() {
        String[][] book_time = new String[][] {
                {"15:00", "17:00"}, {"16:40", "18:20"}, {"14:20", "15:20"}, {"14:10", "19:20"}, {"18:20", "21:20"}
                //{"10:20", "12:30"}, {"10:20", "12:30"}, {"10:20", "12:30"}
        };

        Solve task = new Solve();
        System.out.println(task.solution(book_time));
    }

    private static class Solve {
        private int ans;
        private String[][] sorted_book_time;
        private PriorityQueue<String> rooms;

        public int solution(String[][] book_time) {
            init_setting(book_time);

            assign_room(sorted_book_time, rooms);

            return ans;
        }

        private void assign_room(String[][] sorted_book_time, PriorityQueue<String> rooms) {
            for(int i = 0; i < sorted_book_time.length; i++) {
                String l_t_r = rooms.isEmpty() ? "00:00" : rooms.peek();
                l_t_r = cal_time(l_t_r,10);

                if(sorted_book_time[i][0].compareTo(l_t_r) >= 0) {
                    rooms.poll();
                }
                rooms.add(sorted_book_time[i][1]);
            }

            ans = rooms.size();
        }

        private String cal_time(String s, int minute) {
            String[] split = s.split(":");
            Integer h = Integer.parseInt(split[0]);
            Integer m = Integer.parseInt(split[1]);

            m = (m + minute) % 60;
            h += (m + minute) / 60;

            return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m);
        }

        private void init_setting(String[][] book_time) {
            ans = 0;

            sorted_book_time = Arrays.stream(book_time)
                    .sorted(Comparator.comparing(a -> a[0]))
                    .toArray(String[][]::new);

            rooms = new PriorityQueue<>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
        }
    }

    /*
        Failure Solve : logic error
        TC #2,3,4,6,8,9,10,11,13,14,15,17,18
     */
    private static class WrongSolve {
        private int ans;
        private String[][] sorted_book_time;
        private ArrayList<String> rooms;

        public int solution(String[][] book_time) {
            init_setting(book_time);

            assign_room(sorted_book_time, rooms);

            return ans;
        }

        private void assign_room(String[][] sorted_book_time, ArrayList<String> rooms) {
            for(int i = 1; i < sorted_book_time.length; i++) {
                boolean flag = false;

                for(int j = 0; j < rooms.size(); j++) {
                    if(cal_time(rooms.get(j),10).compareTo(sorted_book_time[i][0]) <= 0) {
                        rooms.set(j,sorted_book_time[i][1]);
                        flag = true;
                        break;
                    }
                }

                if(!flag) rooms.add(sorted_book_time[i][1]);
            }

            ans = rooms.size();
        }

        private String cal_time(String s, int minute) {
            String[] split = s.split(":");
            Integer h = Integer.parseInt(split[0]);
            Integer m = Integer.parseInt(split[1]);

            m = (m + minute) % 60;
            h += (m + minute) / 60;

            return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m);
        }

        private void init_setting(String[][] book_time) {
            sorted_book_time = Arrays.stream(book_time)
                    //.sorted((a,b) -> a[0].compareTo(b[0]))
                    .sorted(Comparator.comparing(a -> a[0]))
                    .toArray(String[][]::new);

            rooms = new ArrayList<>();

            ans = 1;
            rooms.add(sorted_book_time[0][1]);
        }
    }
}
