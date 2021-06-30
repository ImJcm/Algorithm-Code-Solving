/*
문제
수직선 위에 N개의 좌표 X1, X2, ..., XN이 있다. 이 좌표에 좌표 압축을 적용하려고 한다.

Xi를 좌표 압축한 결과 X'i의 값은 Xi > Xj를 만족하는 서로 다른 좌표의 개수와 같아야 한다.

X1, X2, ..., XN에 좌표 압축을 적용한 결과 X'1, X'2, ..., X'N를 출력해보자.

입력
첫째 줄에 N이 주어진다.

둘째 줄에는 공백 한 칸으로 구분된 X1, X2, ..., XN이 주어진다.

출력
첫째 줄에 X'1, X'2, ..., X'N을 공백 한 칸으로 구분해서 출력한다.

제한
1 ≤ N ≤ 1,000,000
-109 ≤ Xi ≤ 109
예제 입력 1
5
2 4 -10 4 -9
예제 출력 1
2 3 0 3 1
예제 입력 2
6
1000 999 1000 999 1000 999
예제 출력 2
1 0 1 0 1 0
 */
//아래 코드의 시간복잡도는 Arrays.sort의 경우 dual pivot quicksort를 사용하여 O(nlogn)이고,
//반복문에서 Map의 get, set, containKey메소드는 O(1)이기 때문에, O(n) 따라서, 전체 함수의 시간복잡도는 O(nlogn)

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BOJ18870 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        String[] input = br.readLine().split(" ");
        int[] nums = new int[N];
        for (int i = 0; i < N; i++)
            nums[i] = Integer.parseInt(input[i]);

        int[] sortNums = nums.clone();
        Arrays.sort(sortNums);

        Map<Integer, Integer> map = new HashMap<>();
        int idx = 0;
        for (int n : sortNums)
            if (!map.containsKey(n))
                map.put(n, idx++);

        StringBuilder sb = new StringBuilder();
        for (int n : nums)
            sb.append(map.get(n)).append(' ');

        System.out.println(sb.toString());
    }
}

/*
//초기 해당 문제를 보고, 계수정렬(Counting Sort)를 생각했었는데, 계수정렬의 경우, 배열의 크기가 크면 효율적이지 못하다는 특징을 생각하여,
//이 문제는 값의 범위가 -10^9 < X < 10^9이고, 충분히 크다고 생각하여 배재하였는데, 이는 Integer.MAX_VALUE(+-2,147,483,647)보다 작은
//값을 확인하여, 이 문제는 계수정렬을 통해 풀어도 된다는 결론을 얻었다.

//또는 이 문제를 Dictionary의 Key:Value를 통해서 풀거나 다양한 풀이가 존재한다.

//아래 코드의 결과는 "시간 초과" io부분 변경이 필요해보인다.
//코드 수정을 위해 블로그 검색함.
import java.util.Arrays;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class BOJ18870 {
    public static void main(String[] args) {
        int N;
        int[] Ns;

        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        Ns = new int[N];
        sc.nextLine();  // \n제거

        String[] s = sc.nextLine().split(" ");
        for(int i=0;i < s.length;i++){
            Ns[i] = Integer.parseInt(s[i]);
        }

        int[] sortNs = Ns.clone();
        Arrays.sort(sortNs);

        Map<Integer,Integer> map = new HashMap<>(); //중복 제거를 위한 HashMap 사용

        for(int i=0,j=0;i<sortNs.length;i++) {
            j = i;
            while(j > 0 && sortNs[j] == sortNs[j-1]) {
                j--;
            }
            map.put(sortNs[i],j);
        }

        String st = new String("");
        for(int i : Ns) {
            st += map.get(i);
            st += " ";
        }
        System.out.println(st.toString());

    }
}
*/