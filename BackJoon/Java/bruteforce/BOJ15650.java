package bruteforce;/*
문제
자연수 N과 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.

1부터 N까지 자연수 중에서 중복 없이 M개를 고른 수열
고른 수열은 오름차순이어야 한다.
입력
첫째 줄에 자연수 N과 M이 주어진다. (1 ≤ M ≤ N ≤ 8)

출력
한 줄에 하나씩 문제의 조건을 만족하는 수열을 출력한다. 중복되는 수열을 여러 번 출력하면 안되며, 각 수열은 공백으로 구분해서 출력해야 한다.

수열은 사전 순으로 증가하는 순서로 출력해야 한다.

예제 입력 1
3 1
예제 출력 1
1
2
3
예제 입력 2
4 2
예제 출력 2
1 2
1 3
1 4
2 3
2 4
3 4
예제 입력 3
4 4
예제 출력 3
1 2 3 4
 */
import java.util.*;
/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 */

public class BOJ15650 {
    static int n,m;
    static int[] lt;
    static boolean[] used;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        lt = new int[m];
        used = new boolean[n+1];

        backtrack_combin(0);
        System.out.println(sb);

    }
    /* combination을 구현할 때는 used리스트를 통해 사용된 것을 거르는 로직을 사용 */
    public static void backtrack_combin(int index) {
        if (index == m) {
            for (int i = 0; i < m; i++) {
                sb.append(lt[i] + " ");
            }
            sb.append("\n");
            return;
        }

        for(int i=0;i<n;i++) {
            if (used[i+1]) {
                continue;
            }
            if (index != 0 && lt[index-1] > i+1) {
                continue;
            }
            lt[index] = i+1;
            used[i+1] = true;
            backtrack_combin(index+1);
            used[i+1] = false;
        }
    }
}
/*
참고
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_B_15650_N과M2 {

	static int N, M;
	static int[] numbers;
	static boolean[] selected;
	static StringBuilder answer = new StringBuilder();

	public static void main(String[] args) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		// 1부터 N까지의 자연수 중에서 중복없이 M개의 수를 고르기
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		numbers = new int[M];
		selected = new boolean[N + 1];

		permutation(0);

		System.out.println(answer);
		in.close();
	}

	public static void permutation(int index) {
		if (index == M) {
			for (int i : numbers)
				answer.append(i + " ");
			answer.append('\n');
			return;
		}

		for (int i = 1; i <= N; i++) {
			if (selected[i])
				continue;
			if (index != 0 && numbers[index - 1] > i)
				continue;

			numbers[index] = i;
			selected[i] = true;
			permutation(index + 1);
			selected[i] = false;
		}
	}
}
 */
