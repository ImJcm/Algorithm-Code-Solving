package Lv3;

import java.util.*;
import java.util.stream.Collectors;

/*
베스트앨범
제출 내역
문제 설명
스트리밍 사이트에서 장르 별로 가장 많이 재생된 노래를 두 개씩 모아 베스트 앨범을 출시하려 합니다. 노래는 고유 번호로 구분하며, 노래를 수록하는 기준은 다음과 같습니다.

속한 노래가 많이 재생된 장르를 먼저 수록합니다.
장르 내에서 많이 재생된 노래를 먼저 수록합니다.
장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록합니다.
노래의 장르를 나타내는 문자열 배열 genres와 노래별 재생 횟수를 나타내는 정수 배열 plays가 주어질 때, 베스트 앨범에 들어갈 노래의 고유 번호를 순서대로 return 하도록 solution 함수를 완성하세요.

제한사항
genres[i]는 고유번호가 i인 노래의 장르입니다.
plays[i]는 고유번호가 i인 노래가 재생된 횟수입니다.
genres와 plays의 길이는 같으며, 이는 1 이상 10,000 이하입니다.
장르 종류는 100개 미만입니다.
장르에 속한 곡이 하나라면, 하나의 곡만 선택합니다.
모든 장르는 재생된 횟수가 다릅니다.
입출력 예
genres	plays	return
["classic", "pop", "classic", "classic", "pop"]	[500, 600, 150, 800, 2500]	[4, 1, 3, 0]
입출력 예 설명
classic 장르는 1,450회 재생되었으며, classic 노래는 다음과 같습니다.

고유 번호 3: 800회 재생
고유 번호 0: 500회 재생
고유 번호 2: 150회 재생
pop 장르는 3,100회 재생되었으며, pop 노래는 다음과 같습니다.

고유 번호 4: 2,500회 재생
고유 번호 1: 600회 재생
따라서 pop 장르의 [4, 1]번 노래를 먼저, classic 장르의 [3, 0]번 노래를 그다음에 수록합니다.

장르 별로 가장 많이 재생된 노래를 최대 두 개까지 모아 베스트 앨범을 출시하므로 2번 노래는 수록되지 않습니다.
※ 공지 - 2019년 2월 28일 테스트케이스가 추가되었습니다.
 */
/*
알고리즘 핵심
Map + PriorityQueue
1. Key : 장르, Value : 곡 정보를 담는 형태의 Map을 이용한다.
2. Value의 곡 정보는 해당 장르에 해당하는 곡을 전체 플레이 횟수와 별도로 (고유 번호, 해당 곡 플레이 횟수)를 우선순위 큐에 class로 저장한다.
3. 1번의 Map 자료를 value의 전체 플레이 횟수를 기준으로 내림차순 정렬한 리스트에서 우선순위 큐에 저장한 고유번호, 곡 플레이 횟수에서
플레이 횟수를 최대 2개까지 ans에 저장한다.
 */
public class 베스트앨범 {
    static void main() {
        String[] genres = new String[] {
                "classic", "pop", "classic", "classic", "pop"
        };

        int[] plays = new int[] {
                500, 600, 150, 800, 2500
        };

        Solve task = new Solve();
        System.out.println(Arrays.toString(task.solution(genres, plays)));
    }

    private static class Solve {
        private class Song {
            private int id;
            private int play;

            public Song(int id, int play) {
                this.id = id;
                this.play = play;
            }
        }
        private class Info {
            private int total_play;
            private PriorityQueue<Song> pq;

            public Info(int total_play) {
                this.total_play = total_play;
                pq = new PriorityQueue<>(new Comparator<Song>() {
                    @Override
                    public int compare(Song s1, Song s2) {
                        return s2.play - s1.play;
                    }
                });
            }

            public void addPlay(int play) {
                this.total_play += play;
            }

            public void addSong(Song song) {
                pq.offer(song);
            }
        }
        private HashMap<String, Info> map;
        private ArrayList<Integer> ans;
        private ArrayList<Map.Entry<String, Info>> sort_list;

        public int[] solution(String[] genres, int[] plays) {
            init_setting(genres, plays);

            best_album(map,ans);

            return ans.stream().mapToInt(Integer::intValue).toArray();
        }

        private void best_album(HashMap<String, Info> map, ArrayList<Integer> ans) {
            sort_list = (ArrayList<Map.Entry<String, Info>>) map.entrySet().stream()
                    .sorted(new Comparator<Map.Entry<String, Info>>() {
                        @Override
                        public int compare(Map.Entry<String, Info> o1, Map.Entry<String, Info> o2) {
                            return o2.getValue().total_play - o1.getValue().total_play;
                        }
                    })
                    .collect(Collectors.toList());

            for(Map.Entry<String, Info> entry : sort_list) {
                //String key = entry.getKey();
                Info info = entry.getValue();

                int limit = Math.min(2, info.pq.size());

                while(limit-- > 0) {
                    ans.add(info.pq.poll().id);
                }
            }
        }

        private void init_setting(String[] genres, int[] plays) {
            map = new HashMap<>();

            for(int i = 0; i < genres.length; i++) {
                String genre =  genres[i];
                int play = plays[i];
                Info info = null;

                if(!map.containsKey(genre)) {
                    info = new Info(play);
                    info.addSong(new Song(i, play));
                    map.put(genre, info);
                } else {
                    info = map.get(genre);
                    info.addPlay(play);
                    info.addSong(new Song(i, play));
                }
            }

            ans = new ArrayList<>();
            sort_list = new ArrayList<>();
        }
    }
}
