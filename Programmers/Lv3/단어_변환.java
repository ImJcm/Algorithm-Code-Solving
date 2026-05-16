package Lv3;

import java.security.Key;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/*
단어 변환
제출 내역
문제 설명
두 개의 단어 begin, target과 단어의 집합 words가 있습니다. 아래와 같은 규칙을 이용하여 begin에서 target으로 변환하는 가장 짧은 변환 과정을 찾으려고 합니다.

1. 한 번에 한 개의 알파벳만 바꿀 수 있습니다.
2. words에 있는 단어로만 변환할 수 있습니다.
예를 들어 begin이 "hit", target가 "cog", words가 ["hot","dot","dog","lot","log","cog"]라면 "hit" -> "hot" -> "dot" -> "dog" -> "cog"와 같이 4단계를 거쳐 변환할 수 있습니다.

두 개의 단어 begin, target과 단어의 집합 words가 매개변수로 주어질 때, 최소 몇 단계의 과정을 거쳐 begin을 target으로 변환할 수 있는지 return 하도록 solution 함수를 작성해주세요.

제한사항
각 단어는 알파벳 소문자로만 이루어져 있습니다.
각 단어의 길이는 3 이상 10 이하이며 모든 단어의 길이는 같습니다.
words에는 3개 이상 50개 이하의 단어가 있으며 중복되는 단어는 없습니다.
begin과 target은 같지 않습니다.
변환할 수 없는 경우에는 0를 return 합니다.
입출력 예
begin	target	words	return
"hit"	"cog"	["hot", "dot", "dog", "lot", "log", "cog"]	4
"hit"	"cog"	["hot", "dot", "dog", "lot", "log"]	0
입출력 예 설명
예제 #1
문제에 나온 예와 같습니다.

예제 #2
target인 "cog"는 words 안에 없기 때문에 변환할 수 없습니다.
 */
/*
알고리즘 핵심
DFS/BFS
1. begin을 시작으로 words의 문자열로 변경이 가능할 때 target으로 변경이 되어야 하므로 최단경로탐색인 dfs/bfs를 사용한다.
2. 현재 문자열에서 다음 문자열로 이동이 이루어질때 words의 단어에서 변경이 이루어질 수 있는 조건을 만족하고 중복 여부를 검사한다.
 */
public class 단어_변환 {
    public static void main() {
        String begin = "hit";
        String target = "cog";
        String[] words = {
                //"hot", "dot", "dog", "lot", "log", "cog"
                "hot", "dot", "dog", "lot", "log"
        };

        Solve task = new Solve();
        System.out.println(task.solution(begin,target,words));
    }

    private static class Solve {
        private class Node {
            String str;
            int cnt;

            public Node(String str, int cnt) {
                this.str = str;
                this.cnt = cnt;
            }
        }
        private int ans;
        private boolean[] visited;

        public int solution(String begin, String target, String[] words) {
            init_setting(begin, target, words);

            dfs(0,begin,target,words);
            bfs(begin,target,words);

            return ans == Integer.MAX_VALUE ? 0 : ans;
        }

        private void dfs(int n, String cur_str, String target, String[] words) {
            if(cur_str.equals(target)) {
                ans = Math.min(ans,n);
                return;
            }

            for(int i = 0; i < words.length; i++) {
                if(visited[i]) continue;
                if(!changeable(words[i],cur_str)) continue;
                else {
                    visited[i] = true;
                    dfs(n+1,words[i],target,words);
                    visited[i] = false;
                }
            }
        }

        private void bfs(String begin, String target, String[] words) {
            Queue<Node> q = new LinkedList<>();
            q.add(new Node(begin,0));

            while(!q.isEmpty()) {
                Node now = q.poll();

                if(now.str.equals(target)) {
                    ans = Math.min(ans,now.cnt);
                    return;
                }

                for(int i = 0; i < words.length; i++) {
                    if(visited[i]) continue;
                    if(!changeable(words[i],now.str)) continue;
                    else {
                        visited[i] = true;
                        q.add(new Node(words[i],now.cnt+1));
                    }
                }
            }
        }

        private boolean changeable(String s,String cur_str) {
            int diff = 0;

            for(int i = 0; i < s.length(); i++) {
                if(s.charAt(i) != cur_str.charAt(i)) diff++;
            }

            if(diff == 1) return true;
            else return false;
        }

        private void init_setting(String begin, String target, String[] words) {
            ans = Integer.MAX_VALUE;

            visited = new boolean[words.length];
        }
    }
}
