package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
겉넓이 구하기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	1755	1109	906	65.180%
문제
크기가 N×M인 종이가 있고, 종이는 1×1크기의 칸으로 나누어져 있다. 이 종이의 각 칸 위에 1×1×1 크기의 정육면체를 놓아 3차원 도형을 만들었다.

종이의 각 칸에 놓인 정육면체의 개수가 주어졌을 때, 이 도형의 겉넓이를 구하는 프로그램을 작성하시오.



위의 그림은 3×3 크기의 종이 위에 정육면체를 놓은 것이고, 겉넓이는 60이다.

입력
첫째 줄에 종이의 크기 N, M이 주어진다. 둘째 줄부터 N개의 줄에는 종이의 각 칸에 놓인 정육면체의 수가 주어진다.

출력
첫째 줄에 도형의 겉넓이를 출력한다.

제한
1 ≤ N, M ≤ 100
1 ≤ 종이의 한 칸에 놓인 정육면체의 수 ≤ 100
예제 입력 1
1 1
1
예제 출력 1
6
예제 입력 2
3 3
1 3 4
2 2 3
1 2 4
예제 출력 2
60
 */
/*
    알고리즘 풀이 :
    정육면체 기본 겉넓이 6을 기준으로 인접한 육면체가 있는지 확인하여 해당 육면체의 겉넓이값을 계산
    이 과정을 각 정육면체 별로 수행하여 총 겉넓이 값을 반환한다.
 */
public class BOJ16931 {
    static int N,M; 
    static int[][][] paper = new int[101][101][101];
    static int[][] paper_height = new int[101][101];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        //input data
        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;j++) {
                int z = Integer.parseInt(st.nextToken());
                paper_height[i][j] = z;
                for(int k=0;k<z;k++) {
                    paper[i][j][k] = 1;
                }
            }
        }

        System.out.println(check());
    }

    static int check() {
        int total_area = 0;
        int default_area =0;

        //1,2,3,4,5,6 방향
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                for(int k=0;k<paper_height[i][j];k++) {
                    default_area = 6;
                    // 한 정육면체를 기준으로 주변 6방향의 인접한 면체가 있는지 확인 후, 있으면 default_area - 1씩 진행
                    //1방향
                    if(i-1>=0 && paper[i-1][j][k] == 1) default_area--;
                    //2방향
                    if (j-1>=0 && paper[i][j-1][k] == 1) default_area--;
                    //3방향
                    if (k-1>=0 && paper[i][j][k-1] == 1) default_area--;
                    //4방향
                    if (i+1<N && paper[i+1][j][k] == 1) default_area--;
                    //5방향
                    if (j+1<M && paper[i][j+1][k] == 1) default_area--;
                    //6방향
                    if (k+1<paper_height[i][j] && paper[i][j][k+1] == 1) default_area--;
                    // logic end
                    total_area += default_area;
                }

            }
        }
        return total_area;
    }
}
