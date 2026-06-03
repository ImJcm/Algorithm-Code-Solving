package Lv2;

import java.util.ArrayList;

/*
스킬트리
제출 내역
문제 설명
선행 스킬이란 어떤 스킬을 배우기 전에 먼저 배워야 하는 스킬을 뜻합니다.

예를 들어 선행 스킬 순서가 스파크 → 라이트닝 볼트 → 썬더일때, 썬더를 배우려면 먼저 라이트닝 볼트를 배워야 하고, 라이트닝 볼트를 배우려면 먼저 스파크를 배워야 합니다.

위 순서에 없는 다른 스킬(힐링 등)은 순서에 상관없이 배울 수 있습니다. 따라서 스파크 → 힐링 → 라이트닝 볼트 → 썬더와 같은 스킬트리는 가능하지만, 썬더 → 스파크나 라이트닝 볼트 → 스파크 → 힐링 → 썬더와 같은 스킬트리는 불가능합니다.

선행 스킬 순서 skill과 유저들이 만든 스킬트리1를 담은 배열 skill_trees가 매개변수로 주어질 때, 가능한 스킬트리 개수를 return 하는 solution 함수를 작성해주세요.

제한 조건
스킬은 알파벳 대문자로 표기하며, 모든 문자열은 알파벳 대문자로만 이루어져 있습니다.
스킬 순서와 스킬트리는 문자열로 표기합니다.
예를 들어, C → B → D 라면 "CBD"로 표기합니다
선행 스킬 순서 skill의 길이는 1 이상 26 이하이며, 스킬은 중복해 주어지지 않습니다.
skill_trees는 길이 1 이상 20 이하인 배열입니다.
skill_trees의 원소는 스킬을 나타내는 문자열입니다.
skill_trees의 원소는 길이가 2 이상 26 이하인 문자열이며, 스킬이 중복해 주어지지 않습니다.
입출력 예
skill	skill_trees	return
"CBD"	["BACDE", "CBADF", "AECB", "BDA"]	2
입출력 예 설명
"BACDE": B 스킬을 배우기 전에 C 스킬을 먼저 배워야 합니다. 불가능한 스킬트립니다.
"CBADF": 가능한 스킬트리입니다.
"AECB": 가능한 스킬트리입니다.
"BDA": B 스킬을 배우기 전에 C 스킬을 먼저 배워야 합니다. 불가능한 스킬트리입니다.
스킬 트리: 유저가 스킬을 배울 순서 ↩
 */
/*
알고리즘 핵심
구현
1. skill_tree의 순서를 선행 skill의 순서와 맞는지 검사한다.

처음 시도로는 선행 스킬의 문자를 순차적으로 위치를 찾아 현재 스킬이 선행 스킬보다 인덱스의 값이 높은지를 검사하는 방식을
생각하였다.
해당 방법으로는 앞선 선행 스킬의 위치와 현재 스킬의 위치를 비교하기 때문에 선행 스킬의 위치를 보존해야하고, 검사하는 조건이 복잡했다.

그래서, 좀더 생각해보면 스킬은 선행 조건이 존재하므로 순서를 갖는다는 점을 통해서 스킬 트리의 순차적인 문자들을 선행 스킬에서 위치를
찾고 해당 스킬을 배운다는 별도의 공간을 두어 선행 조건을 만족하는지 비교하는 것이 좋다고 생각했다.
스킬 트리의 문자들을 선행 스킬 순서에서 위치를 찾고, 해당 위치의 값이 현재 배운 스킬 수량과 비교하여 검증하면 코드가 간결해진다.
(만약, 스킬을 중복하여 배울 수 있다고 한다면, 배우는 수량이 늘어날 수 있으므로 ArrayList -> HashSet을 사용하여 중복방지)
 */
public class 스킬트리 {
    static void main() {
        String skill = "CBD";
        String[] skill_trees = new String[] {
                "BACDE", "CBADF", "AECB", "BDA","CBAFE","C"
        };

        Solve task = new Solve();
        System.out.println(task.solution(skill, skill_trees));
    }

    private static class Solve {
        private int ans;

        public int solution(String skill, String[] skill_trees) {
            init_setting(skill, skill_trees);

            verify_skill_tree(skill,skill_trees);
            verify_skill_tree_2(skill,skill_trees);

            return ans;
        }

        private void verify_skill_tree(String skill, String[] skill_trees) {
            for(String st : skill_trees) {
                int idx = 0;
                int order = 0, n_order = 0;
                boolean flag = false; // 스킬트리가 끊어진 시점에서 선행 스킬이 요구되는 스킬을 배우려는 경우 차단

                while(true) {
                    if(idx == skill.length()) {
                        ans++;
                        break;
                    }
                    char ch = skill.charAt(idx++);
                    n_order = st.indexOf(ch);

                    if(n_order == -1 && order != -1) flag = true;
                    else if((n_order != -1 && flag) || n_order < order) break;
                    order = n_order;
                }
            }
        }

        private void verify_skill_tree_2(String skill, String[] skill_trees) {
            ArrayList<Character> arr;

            for(String st : skill_trees) {
                arr = new ArrayList<>();
                boolean avail = true;

                for(char ch : st.toCharArray()) {
                    if(!avail) break;
                    int idx = skill.indexOf(ch);

                    if(idx == -1) continue;
                    if(arr.size() == idx) {
                        arr.add(ch);
                    } else avail = false;
                }

                if(avail) ans++;
            }
        }

        private void init_setting(String skill, String[] skill_trees) {
            ans = 0;
        }
    }
}
