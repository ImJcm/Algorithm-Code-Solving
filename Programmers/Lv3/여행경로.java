package Lv3;

import java.util.*;

/*
여행경로
제출 내역
문제 설명
주어진 항공권을 모두 이용하여 여행경로를 짜려고 합니다. 항상 "ICN" 공항에서 출발합니다.

항공권 정보가 담긴 2차원 배열 tickets가 매개변수로 주어질 때, 방문하는 공항 경로를 배열에 담아 return 하도록 solution 함수를 작성해주세요.

제한사항
모든 공항은 알파벳 대문자 3글자로 이루어집니다.
주어진 공항 수는 3개 이상 10,000개 이하입니다.
tickets의 각 행 [a, b]는 a 공항에서 b 공항으로 가는 항공권이 있다는 의미입니다.
주어진 항공권은 모두 사용해야 합니다.
만일 가능한 경로가 2개 이상일 경우 알파벳 순서가 앞서는 경로를 return 합니다.
모든 도시를 방문할 수 없는 경우는 주어지지 않습니다.
입출력 예
tickets	return
[["ICN", "JFK"], ["HND", "IAD"], ["JFK", "HND"]]	["ICN", "JFK", "HND", "IAD"]
[["ICN", "SFO"], ["ICN", "ATL"], ["SFO", "ATL"], ["ATL", "ICN"], ["ATL","SFO"]]	["ICN", "ATL", "ICN", "SFO", "ATL", "SFO"]
입출력 예 설명
예제 #1

["ICN", "JFK", "HND", "IAD"] 순으로 방문할 수 있습니다.

예제 #2

["ICN", "SFO", "ATL", "ICN", "ATL", "SFO"] 순으로 방문할 수도 있지만 ["ICN", "ATL", "ICN", "SFO", "ATL", "SFO"] 가 알파벳 순으로 앞섭니다.

문제가 잘 안풀린다면😢
힌트가 필요한가요? [코딩테스트 연습 힌트 모음집]으로 오세요!
- https://school.programmers.co.kr/learn/courses/14743/14743-%EC%BD%94%EB%94%A9%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%97%B0%EC%8A%B5-%ED%9E%8C%ED%8A%B8-%EB%AA%A8%EC%9D%8C%EC%A7%91?itm_content=lesson43164
 */
public class 여행경로 {
    static void main() {
        String[][] tickets = new String[][] {
                //{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL","SFO"}
                {"ICN", "JFK"}, {"ICN", "JFK"}, {"JFK", "HND"}, {"HND", "ICN"}, {"JFK", "ATL"}
        };

        Solve task = new Solve();
        System.out.println(Arrays.toString(task.solution(tickets)));
    }

    /*
        Wrong Solve : timeout TestCase#1
     */
    private static class Solve {
        private class AirPort {
            String name;
            Queue<String> tickets;

            public AirPort(String name) {
                this.name = name;
                tickets = new LinkedList<>();
            }

            public void addTicket(String ticket) {
                this.tickets.add(ticket);
            }
        }
        private final String start_airport = "ICN";
        private String[] ans;
        private int route_cnt;
        private boolean flag;
        private String[][] sorted_tickets;
        private HashMap<String, AirPort> airports;

        public String[] solution(String[][] tickets) {
            init_setting(tickets);

            travel_route(0, route_cnt - 1, start_airport, airports);

            return ans;
        }

        private void travel_route(int i, int route_cnt, String pos, HashMap<String, AirPort> airports) {
            ans[i] = pos;

            if(!flag) return;
            if(i == route_cnt) {
                flag = false;
                return;
            }

            while(!airports.get(pos).tickets.isEmpty() && flag) {
                String ticket = airports.get(pos).tickets.poll();

                travel_route(i + 1, route_cnt, ticket, airports);

                airports.get(pos).addTicket(ticket);
            }
        }

        private void init_setting(String[][] tickets) {
            route_cnt = tickets.length + 1;
            flag = true;
            ans = new String[route_cnt];

            airports = new HashMap<>();

            sorted_tickets = Arrays.stream(tickets)
                    .sorted(Comparator.comparing(f -> f[1]))
                    .toArray(String[][]::new);

            for(String[] ticket : sorted_tickets) {
                String f = ticket[0];
                String t = ticket[1];

                if(!airports.containsKey(f)) {
                    airports.put(f, new AirPort(f));
                }

                if(!airports.containsKey(t)) {
                    airports.put(t, new AirPort(t));
                }

                airports.get(f).addTicket(t);
            }
        }
    }

    /*
        WrongSolve : logic error
        => 단순하게 공항에서 이동가능한 공항으로의 이동을 결정하는 것은 알파벳이 순서상 오름차순으로 결정하는 것과
        모든 티켓을 사용하여 이동이 가능한 경우이어야 한다.
        즉, 알파벳 순서상 빠른 이동만 선택하는 경우, 전체 티켓을 사용할 수 없다.
     */
    private static class WrongSolve {
        private class AirPort implements Comparable<AirPort> {
            String name;
            PriorityQueue<String> tickets;

            public AirPort(String name) {
                this.name = name;
                tickets = new PriorityQueue<>();
            }

            public void addTicket(String ticket) {
                this.tickets.add(ticket);
            }

            @Override
            public int compareTo(AirPort o) {
                return this.name.compareTo(o.name);
            }
        }
        private final String START = "ICN";
        private int ticket_cnt;
        private boolean flag = true;
        private String[] ans;
        private HashMap<String, AirPort> airports;

        public String[] solution(String[][] tickets) {
            init_setting(tickets);

            travel_route(0, ticket_cnt, START, airports);

            return ans;
        }

        private void travel_route(int idx, int end, String start, HashMap<String, AirPort> airports) {
            if(!flag) return;

            ans[idx] = start;

            if(idx == end) {
                flag = false;
                return;
            }



            while(!airports.get(start).tickets.isEmpty()) {
                String dest = airports.get(start).tickets.poll();

                travel_route(idx + 1, end, dest, airports);
            }
        }

        private void init_setting(String[][] tickets) {
            airports = new HashMap<>();

            ticket_cnt = tickets.length;

            for(String[] ticket : tickets) {
                String f = ticket[0];
                String t = ticket[1];

                if(!airports.containsKey(f)) {
                    airports.put(f, new AirPort(f));
                }

                if(!airports.containsKey(t)) {
                    airports.put(t, new AirPort(t));
                }

                airports.get(f).addTicket(t);
            }

            ans = new String[ticket_cnt + 1];
        }
    }
}
