package Programmers;

import java.util.*;

/*
구명보트
문제 설명
무인도에 갇힌 사람들을 구명보트를 이용하여 구출하려고 합니다. 구명보트는 작아서 한 번에 최대 2명씩 밖에 탈 수 없고, 무게 제한도 있습니다.

예를 들어, 사람들의 몸무게가 [70kg, 50kg, 80kg, 50kg]이고 구명보트의 무게 제한이 100kg이라면 2번째 사람과 4번째 사람은 같이 탈 수 있지만 1번째 사람과 3번째 사람의 무게의 합은 150kg이므로 구명보트의 무게 제한을 초과하여 같이 탈 수 없습니다.

구명보트를 최대한 적게 사용하여 모든 사람을 구출하려고 합니다.

사람들의 몸무게를 담은 배열 people과 구명보트의 무게 제한 limit가 매개변수로 주어질 때, 모든 사람을 구출하기 위해 필요한 구명보트 개수의 최솟값을 return 하도록 solution 함수를 작성해주세요.

제한사항
무인도에 갇힌 사람은 1명 이상 50,000명 이하입니다.
각 사람의 몸무게는 40kg 이상 240kg 이하입니다.
구명보트의 무게 제한은 40kg 이상 240kg 이하입니다.
구명보트의 무게 제한은 항상 사람들의 몸무게 중 최댓값보다 크게 주어지므로 사람들을 구출할 수 없는 경우는 없습니다.
입출력 예
people	limit	return
[70, 50, 80, 50]	100	3
[70, 80, 50]	100	3
 */
/*
    DFS로 풀다가 감이안와서 질문게시판을 통해 풀이팁을 보았다.
    풀이 팁중 투포인터 풀이를 이용하여 최소 보트개수로 도달하는 방법을 이용하려고 한다.

    최대 2명을 탈 수 있으므로, 오름차순 정렬을 해주고, 0에서 부터 시작하는 i, 배열의 뒤 부터 시작하는 j를 인덱스로 하여
    people[i] + people[j]를 합했을 때 무게가 제한과 비교하여 제한보다 크면, 무게가 큰 j 인덱스에 있는 사람을 보트에 태워보낸다.
    무게 제한보다 작다면, 같이 보트에 태워보낸다. 이를 i 와 j가 교차할 때까지 반복하여 보트의 수를 구한다.
    위의 과정을 투포인트 알고리즘과 유사하다.
 */
class Solution {
    public static void main(String[] args) {
        int[] people = new int[]{70, 80, 50};
        System.out.println(solution(people,100));
    }

    public static int solution(int[] people, int limit) {
        int answer = 0;
        Arrays.sort(people);
        answer = Two_Point_solve(people, limit);
        System.out.println(answer);
        return answer;
    }

    static int Two_Point_solve(int[] p, int l) {
        int i=0,j=p.length-1;
        int sum = 0;
        int boat_count = 0;
        while(i<=j) {
            sum = (p[i] + p[j]);
            boat_count++;
            if(i==j) {
                break;
            } else if(sum <= l) {
                i++; j--;
            } else {
                j--;
            }
        }
        return boat_count;
    }
}
/*
//아래 코드는 DFS로 가능한 경우의 수를 모두 검사하고 최소 보트 수를 최종적으로 반환하는데, 시간초과 실패가 발생
//가지치기를 수행해도 경우의 수가 많아서 시간초과가 발생하는 것으로 생각된다.
//보트에 최대 2명이 탈 수 있다는 조건을 나중에서야 봤다. 아래 코드는 가능하다면 2명 이상을 태우기 때문에 잘못된 코드이다.
class Solution {
    public static void main(String[] args) {
        int[] person = new int[]{70,50, 80, 50};
        System.out.println(solution(person,100));
    }

    static int minBoat = Integer.MAX_VALUE;
    public static int solution(int[] people, int limit) {
        int answer = 0;
        boolean[] visited = new boolean[people.length];
        Arrays.stream(people).sorted();
        Arrays.fill(visited,false);

        DFS(0,people,visited,limit, limit,1);

        answer = minBoat;

        return answer;
    }

    static void DFS(int depth, int[] p, boolean[] visited, int rest_size, int limit, int cnt) {
        if(depth == p.length) {
            minBoat = Math.min(minBoat,cnt);
            return;
        }

        if(cnt >= minBoat) return;  // branch cut

        for(int i=0;i<p.length;i++) {
            if(!visited[i]) {
                visited[i] = true;
                if(rest_size >= p[i]) {
                    DFS(depth+1,p,visited,rest_size-p[i],limit,cnt);
                } else {
                    DFS(depth+1,p,visited,limit-p[i],limit,cnt+1);
                }
                visited[i] = false;
            }
        }
    }
}*/
