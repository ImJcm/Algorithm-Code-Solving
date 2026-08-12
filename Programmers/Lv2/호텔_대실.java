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
/*
알고리즘 핵심
정렬 + 우선순위 큐
1. 방 예약 정보에서 예약 시작시간을 기준으로 오름차순 정렬한다.
2. 정렬된 예약 정보를 순차적으로 조회하고, 방 배정이 가능한 경우 우선순위 큐에 예약 종료시간을 추가한다.
3. 2번 과정에서 순차적으로 탐색한 예약 시작 시간과 큐의 가장 빠른 종료 시간의 방의 퇴실 시간을 비교하여 배정이 가능한 경우
해당 정보를 pop하고, 새로운 방 퇴실 시간을 추가한다.
배정이 불가능한 경우(순차적으로 탐색한 예약 시작 시작이 작은 경우), 새로운 방을 배정한다.

개인적인 생각 : 정렬 + 우선순위 큐
(Greedy?, 정렬을 통한 방 배정 시, 예약들 간의 단편화를 줄이는 것이 가장 적게 방을 사용하는 방법이라고 생각한다.)
 */
public class 호텔_대실 {
    static void main() {
        String[][] book_time = new String[][] {
                //{"15:00", "17:00"}, {"16:40", "18:20"}, {"14:20", "15:20"}, {"14:10", "19:20"}, {"18:20", "21:20"}
                //{"10:20", "12:30"}, {"10:20", "12:30"}, {"10:20", "12:30"}
                //{"09:00", "16:00"}, {"10:00", "11:00"}, {"10:00", "13:00"}, {"15:10", "15:50"}
                //{"09:00", "10:00"}, {"09:00", "11:00"}, {"13:30", "20:00"}, {"10:30", "14:00"}, {"11:30", "13:00"}, {"16:30", "19:30"}
                //{"13:00", "14:01"}, {"14:10", "15:00"}, {"15:10", "16:00"}
                //{"00:00", "00:07"}, {"00:01", "00:08"}, {"00:02", "00:09"}, {"10:26", "10:41"}
                {"00:00", "23:57"}, {"00:01", "23:58"}, {"00:02", "23:59"}, {"10:26", "23:56"}
        };

        Solve task = new Solve();
        System.out.println(task.solution(book_time));
    }

    /*
        Wrong Solve : logic error
        배정을 마친 방들을 우선순위 큐로 가장 빠른 퇴소시간인 방에 배정하거나 새로운 방을 배정하는 방식으로 변경하였지만 틀림

        Correct Solve
        디버그 과정에서 cal_time() 내부에서 10분을 추가한 후, 시간이 변경되지 않는 문제가 발견하여 이를 h,m의 변경 순서를
        변경하여 제출한 결과 전부 맞음.
        결론 : m값을 minute의 값을 더한 후, 분으로 변경한 값이 시 값에 영향이 있어서 오류가 난 것
            + 예약 시작 시간을 기준으로 오름차순 정렬한 후, 이미 배정한 방의 예약 종료 시점과 새로운 예약의 시작 지점을
            비교하는 방법이 맞다. + 기존 배열에 예약 배정을 수행하는 것이 아니라 우선순위 큐를 사용하는 것이 좀더 효율적이다.
     */
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
                String l_t_r = rooms.isEmpty() ? "00:00" : cal_time(rooms.peek(),10);

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

            h += (m + minute) / 60;
            m = (m + minute) % 60;

            return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m);
        }

        private void init_setting(String[][] book_time) {
            ans = 0;

            sorted_book_time = Arrays.stream(book_time)
                    //.sorted(Comparator.comparing(a -> a[0]))
                    .sorted(new Comparator<String[]>() {
                        @Override
                        public int compare(String[] o1, String[] o2) {
                            return o1[0].compareTo(o2[0]);
                        }
                    })
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
