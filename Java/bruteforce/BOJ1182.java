package BackJoon;/*
부분수열의 합

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	256 MB	55749	25668	16597	44.308%
문제
N개의 정수로 이루어진 수열이 있을 때, 크기가 양수인 부분수열 중에서 그 수열의 원소를 다 더한 값이 S가 되는 경우의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 정수의 개수를 나타내는 N과 정수 S가 주어진다. (1 ≤ N ≤ 20, |S| ≤ 1,000,000) 둘째 줄에 N개의 정수가 빈 칸을 사이에 두고 주어진다. 주어지는 정수의 절댓값은 100,000을 넘지 않는다.

출력
첫째 줄에 합이 S가 되는 부분수열의 개수를 출력한다.

예제 입력 1
5 0
-7 -3 -2 5 8
예제 출력 1
1
 */
/*
    참고 :
    비트연산을 통한 배열의 부분집합 구하기 -
    https://velog.io/@94applekoo/%EB%B9%84%ED%8A%B8%EC%97%B0%EC%82%B0%EC%9E%90%EB%A1%9C-%EB%B6%80%EB%B6%84-%EC%A7%91%ED%95%A9%EC%9D%84-%EC%83%9D%EC%84%B1%ED%95%98%EB%8A%94-%EB%B2%95-python
    https://dev-nomad.com/38 - 비트연산

    백트랙킹을 통해 int size 매개변수를 통해 부분수열에 속한 수들의 개수를 나타냄을 통해 모든 부분수열의 합을 나타낼 수 있다.
    참고 : https://jun-codinghistory.tistory.com/220
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class BOJ1182 {
    static int N, S;
    static int[] boxs;
    static int pre_result, answer=0;
    static Map<Integer, Integer> arr;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        boxs = new int[N];
        boxs = Arrays.asList(br.readLine().split(" "))
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        //bitMasking();

        arr = new HashMap<>();
        backTracking(0,0);

        answer = arr.get(S) == null ? 0 : arr.get(S);

        System.out.println(S == 0 ? answer - 1 : answer);
    }

    /*
        부분수열의 합으로 가능한 경우 Map 자료구조로 Key, Value형태로 저장한다.
        백트랙킹을 통해 size 크기만큼 부분수열의 원소의 개수를 만족할 때 sum의 값이 부분수열의 합이기 때문에
        모든 백트랙킹 과정이 끝나면 Map 내부는 모든 부분수열의 합과 가능한 경우의 수를 저장하고 있다.
     */
    static void backTracking(int size, int sum) {
        if(size == N) {
            if(arr.containsKey(sum)) {
                arr.replace(sum, arr.get(sum) + 1);
            } else {
                arr.put(sum, 1);
            }
            return;
        }

        backTracking(size + 1, sum + boxs[size]);
        backTracking(size + 1, sum);
    }

    static void bitMasking() {
        /*
            부분합을 계산하기 위한 S의 집합원소들을 배열 boxs에 저장하여 인덱스를 비트로 인식한다
            N번의 부분집합 생성과 부분집합 초기 원소결정 : for(int i=0;i<(1<<N);i++)
            공집합 배열 제외 : if(Integer.bitCount(i) > 0)
            반복문을 통해 원소의 인덱스 설정 : for (int j = 0; j < N; j++)
            부분집합i와 속한 원소 확인 과정 : if ((i & (1 << j)) != 0)
            ㄴ 확인 결과 (i & (1 << j)) 값이 0이면, 이미 부분집합 i에 속한 원소이고,
            ㄴ 0이 아니면, 부분집합에 추가한다.
            추가된 부분집합의 원소들의 합을 pre_result에 저장하고, 최종적으로 부분집합의 합이 S와 같으면
            answer의 값을 증가시켜, 결과를 도출하는 원리이다.
         */
        for(int i=0;i<(1<<N);i++) {
            pre_result = 0;
            // > 0 을 통해 공집합 배열 제외
            if(Integer.bitCount(i) > 0) {
                for (int j = 0; j < N; j++) {
                    if ((i & (1 << j)) != 0) {
                        pre_result += boxs[j];
                    }
                }
                if(pre_result == S) {
                    answer += 1;
                }
            }
        }
    }
}
