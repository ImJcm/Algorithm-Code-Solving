import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
용감한 용사 진수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	694	278	235	50.214%
문제
N명의 적 병사가 있다. 적의 각 병사는 힘, 민첩, 지능의 3가지 능력치를 가진다. 용감한 용사 진수도 힘, 민첩, 지능의 3가지 능력치를 가진다.

적의 각 병사에 대해,

적 병사가 가진 힘보다 진수의 힘이 크거나 같고,
적 병사가 가진 민첩보다 진수의 민첩이 크거나 같고,
적 병사가 가진 지능보다 진수의 지능이 크거나 같으면,
진수는 그 적 병사를 이길 수 있다.

용감한 용사 진수에게 스탯 포인트를 주면 똑똑한 진수는 자기가 최대한 많은 적을 이길 수 있도록 스탯 포인트를 스스로 분배한다.

N명의 병사들 스탯이 주어졌을 때, 진수가 적어도 K명의 병사를 이길 수 있게 하는 최소의 스탯 포인트를 구하여라.

입력
첫 번째 줄에는 N명의 병사 수와 용감한 용사 진수가 이겨야 할 K명의 병사 수가 주어진다. (1 ≤ K ≤ N ≤ 100)

두 번째 줄부터 N+1 번째 줄까지 각 줄마다 병사들의 힘, 민첩, 지능을 세 개의 음이 아닌 정수로 주어진다. (0 ≤ 힘, 민첩, 지능 ≤ 1000000)

출력
용감한 용사 진수가 적어도 K명의 병사를 이길 수 있게 하는 최소의 스탯 포인트를 출력하여라.

예제 입력 1
3 3
10 5 5
5 10 5
5 5 10
예제 출력 1
30
예제 입력 2
3 1
234 23 342
35 4634 34
46334 6 789
예제 출력 2
599
예제 입력 3
3 2
30 30 30
10 500 10
50 10 50
예제 출력 3
130
 */
//100 * 100 * 100 * 100 = 10^8로 시간제한 만족
//bruteForce 알고리즘
public class BOJ14718 {
    static int N, K;
    static int[][] enemy;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        K = Integer.parseInt(str[1]);
        enemy = new int[N][3];

        for(int i=0;i<N;i++) {
            str = br.readLine().split(" ");
            enemy[i][0] = Integer.parseInt(str[0]);
            enemy[i][1] = Integer.parseInt(str[1]);
            enemy[i][2] = Integer.parseInt(str[2]);
        }

        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++) {
                for(int k=0;k<N;k++) {
                    int count = 0;

                    for(int n=0;n<N;n++) {
                        if(enemy[i][0] >= enemy[n][0] &&
                        enemy[j][1] >= enemy[n][1] &&
                        enemy[k][2] >= enemy[n][2]) {
                            count++;
                        }
                    }

                    if(count == K) {
                        min = Math.min(min,enemy[i][0] + enemy[j][1] + enemy[k][2]);
                    }
                }
            }
        }

        System.out.println(min);
    }
}
