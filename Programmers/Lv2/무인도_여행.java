package Lv2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
무인도 여행
제출 내역
문제 설명
메리는 여름을 맞아 무인도로 여행을 가기 위해 지도를 보고 있습니다. 지도에는 바다와 무인도들에 대한 정보가 표시돼 있습니다. 지도는 1 x 1크기의 사각형들로 이루어진 직사각형 격자 형태이며, 격자의 각 칸에는 'X' 또는 1에서 9 사이의 자연수가 적혀있습니다. 지도의 'X'는 바다를 나타내며, 숫자는 무인도를 나타냅니다. 이때, 상, 하, 좌, 우로 연결되는 땅들은 하나의 무인도를 이룹니다. 지도의 각 칸에 적힌 숫자는 식량을 나타내는데, 상, 하, 좌, 우로 연결되는 칸에 적힌 숫자를 모두 합한 값은 해당 무인도에서 최대 며칠동안 머물 수 있는지를 나타냅니다. 어떤 섬으로 놀러 갈지 못 정한 메리는 우선 각 섬에서 최대 며칠씩 머물 수 있는지 알아본 후 놀러갈 섬을 결정하려 합니다.

지도를 나타내는 문자열 배열 maps가 매개변수로 주어질 때, 각 섬에서 최대 며칠씩 머무를 수 있는지 배열에 오름차순으로 담아 return 하는 solution 함수를 완성해주세요. 만약 지낼 수 있는 무인도가 없다면 -1을 배열에 담아 return 해주세요.

제한사항
3 ≤ maps의 길이 ≤ 100
3 ≤ maps[i]의 길이 ≤ 100
maps[i]는 'X' 또는 1 과 9 사이의 자연수로 이루어진 문자열입니다.
지도는 직사각형 형태입니다.
입출력 예
maps	result
["X591X","X1X5X","X231X", "1XXX1"]	[1, 1, 27]
["XXX","XXX","XXX"]	[-1]
입출력 예 설명
입출력 예 #1

위 문자열은 다음과 같은 지도를 나타냅니다.

image1

연결된 땅들의 값을 합치면 다음과 같으며

image2

이를 오름차순으로 정렬하면 [1, 1, 27]이 됩니다.

입출력 예 #2

위 문자열은 다음과 같은 지도를 나타냅니다.

image3

섬이 존재하지 않기 때문에 -1을 배열에 담아 반환합니다.
 */
/*
알고리즘 핵심
BFS
1. [r][c]인 2차원 배열로 섬을 구성하고, 모든 섬을 하나씩 출발지로 설정하여 BFS를 수행한다.
2. BFS수행 시, 방문한 섬의 방문처리는 이후 과정에서도 사용하고 섬마다 머무를 수 있는 기간을 누적하여, 도달할 수 있는 모든 섬의 기간을 저장한다.
3. 저장한 기간들을 오름차순 정렬하여 ans에 저장한다.
 */
public class 무인도_여행 {
    static void main() {
        String[] maps = new String[] {
                "X591X","X1X5X","X231X", "1XXX1"
        };

        Solve task = new Solve();
        System.out.println(Arrays.toString(task.solution(maps)));
    }

    private static class Solve {
        private class Island {
            int r,c;

            public Island(int r, int c) {
                this.r = r;
                this.c = c;
            }
        }
        private ArrayList<Integer> ans;
        private int[][] direction = {
                {-1,0},{1,0},{0,-1},{0,1}
        };
        private char[][] maps_2_dimension;
        private boolean[][] visited;


        public int[] solution(String[] maps) {
            init_setting(maps);

            int id = 0;

            for(int i = 0; i < maps.length; i++) {
                for(int j = 0; j < maps[i].length(); j++) {
                    if(visited[i][j]) continue;
                    travel_uninhabited_island(new Island(i,j), maps_2_dimension,visited, id++);
                }
            }

            if(ans.isEmpty()) {
                return new int[] {-1};
            } else {
                return ans.stream()
                    .sorted()
                    .mapToInt(i->i)
                    .toArray();
            }
        }

        private void travel_uninhabited_island(Island st, char[][] maps, boolean[][] v, int id) {
            Queue<Island> q = new LinkedList<>();
            q.add(st);
            v[st.r][st.c] = true;

            int cnt = 0;

            while(!q.isEmpty()) {
                Island curr = q.poll();
                cnt += maps[curr.r][curr.c] - '0';

                for(int[] d : direction) {
                    int nr = curr.r + d[0];
                    int nc = curr.c + d[1];

                    if(nr < 0 || nr >= maps.length || nc < 0 || nc >= maps[0].length || visited[nr][nc]) continue;

                    visited[nr][nc] = true;
                    q.add(new Island(nr, nc));
                }
            }

            if(cnt != 0) ans.add(cnt);
        }

        private void init_setting(String[] maps) {
            ans = new ArrayList<>();

            maps_2_dimension = new char[maps.length][];
            visited = new boolean[maps.length][];

            for(int i = 0; i < maps.length; i++) {
                String[] sp = maps[i].split("");

                maps_2_dimension[i] = new char[sp.length];
                visited[i] = new boolean[sp.length];

                for(int j = 0; j < sp.length; j++) {
                    maps_2_dimension[i][j] = sp[j].charAt(0);
                    if(maps_2_dimension[i][j] == 'X') visited[i][j] = true;
                }
            }
        }
    }
}
