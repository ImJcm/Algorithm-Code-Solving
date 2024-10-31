package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
게리맨더링 2

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	13441	8081	5172	58.132%
문제
재현시의 시장 구재현은 지난 몇 년간 게리맨더링을 통해서 자신의 당에게 유리하게 선거구를 획정했다. 견제할 권력이 없어진 구재현은 권력을 매우 부당하게 행사했고, 심지어는 시의 이름도 재현시로 변경했다. 이번 선거에서는 최대한 공평하게 선거구를 획정하려고 한다.

재현시는 크기가 N×N인 격자로 나타낼 수 있다. 격자의 각 칸은 구역을 의미하고, r행 c열에 있는 구역은 (r, c)로 나타낼 수 있다. 구역을 다섯 개의 선거구로 나눠야 하고, 각 구역은 다섯 선거구 중 하나에 포함되어야 한다. 선거구는 구역을 적어도 하나 포함해야 하고, 한 선거구에 포함되어 있는 구역은 모두 연결되어 있어야 한다. 구역 A에서 인접한 구역을 통해서 구역 B로 갈 수 있을 때, 두 구역은 연결되어 있다고 한다. 중간에 통하는 인접한 구역은 0개 이상이어야 하고, 모두 같은 선거구에 포함된 구역이어야 한다.

선거구를 나누는 방법은 다음과 같다.

1. 기준점 (x, y)와 경계의 길이 d1, d2를 정한다. (d1, d2 ≥ 1, 1 ≤ x < x+d1+d2 ≤ N, 1 ≤ y-d1 < y < y+d2 ≤ N)
2. 다음 칸은 경계선이다.
(x, y), (x+1, y-1), ..., (x+d1, y-d1)
(x, y), (x+1, y+1), ..., (x+d2, y+d2)
(x+d1, y-d1), (x+d1+1, y-d1+1), ... (x+d1+d2, y-d1+d2)
(x+d2, y+d2), (x+d2+1, y+d2-1), ..., (x+d2+d1, y+d2-d1)
3. 경계선과 경계선의 안에 포함되어있는 곳은 5번 선거구이다.
4. 5번 선거구에 포함되지 않은 구역 (r, c)의 선거구 번호는 다음 기준을 따른다.
    - 1번 선거구: 1 ≤ r < x+d1, 1 ≤ c ≤ y
    - 2번 선거구: 1 ≤ r ≤ x+d2, y < c ≤ N
    - 3번 선거구: x+d1 ≤ r ≤ N, 1 ≤ c < y-d1+d2
    - 4번 선거구: x+d2 < r ≤ N, y-d1+d2 ≤ c ≤ N
아래는 크기가 7×7인 재현시를 다섯 개의 선거구로 나눈 방법의 예시이다.


x = 2, y = 4, d1 = 2, d2 = 2	x = 2, y = 5, d1 = 3, d2 = 2	x = 4, y = 3, d1 = 1, d2 = 1
구역 (r, c)의 인구는 A[r][c]이고, 선거구의 인구는 선거구에 포함된 구역의 인구를 모두 합한 값이다. 선거구를 나누는 방법 중에서, 인구가 가장 많은 선거구와 가장 적은 선거구의 인구 차이의 최솟값을 구해보자.

입력
첫째 줄에 재현시의 크기 N이 주어진다.

둘째 줄부터 N개의 줄에 N개의 정수가 주어진다. r행 c열의 정수는 A[r][c]를 의미한다.

출력
첫째 줄에 인구가 가장 많은 선거구와 가장 적은 선거구의 인구 차이의 최솟값을 출력한다.

제한
5 ≤ N ≤ 20
1 ≤ A[r][c] ≤ 100
예제 입력 1
6
1 2 3 4 1 6
7 8 9 1 4 2
2 3 4 1 1 3
6 6 6 6 9 4
9 1 9 1 9 5
1 1 1 1 9 9
예제 출력 1
18


예제 입력 2
6
5 5 5 5 5 5
5 5 5 5 5 5
5 5 5 5 5 5
5 5 5 5 5 5
5 5 5 5 5 5
5 5 5 5 5 5
예제 출력 2
20
예제 입력 3
8
1 2 3 4 5 6 7 8
2 3 4 5 6 7 8 9
3 4 5 6 7 8 9 1
4 5 6 7 8 9 1 2
5 6 7 8 9 1 2 3
6 7 8 9 1 2 3 4
7 8 9 1 2 3 4 5
8 9 1 2 3 4 5 6
예제 출력 3
23


출처
문제를 만든 사람: baekjoon
알고리즘 분류
구현
브루트포스 알고리즘
시뮬레이션
 */
/*
알고리즘 핵심
1. divide_electoral_district_method() : 구역을 나눌 수 있는 모든 경우의 좌표를 구한다.
2. divide_section_number(s) : 나눠진 모든 구역을 1,2,3,4,5의 구역으로 구분하여 반환한다.
3. calculate_differences_population(s) : 1,2,3,4,5로 나누어진 구역에 해당하는 인구수를 종합하여 구역에서 최대 인구수와 최소 인구수의 차이가 최소값에 해당하는 값을 ans에 업데이트한다.

처음 문제를 접근할 때, 5의 경계를 만들지 않고 나머지 구역을 구분지을 수 있는 방법이 있을까? 고민하였지만 처음부터 5의 경계를 구분짓지 않고 나머지 구역들을 나누려면 (r,c)의 모든 좌표마다
해당 좌표가 경계에 속하는 좌표인지를 조건을 검사해야하는 과정이 필요하다고 생각했다. 그래서 이 방법은 복잡도가 크다고 생각했다.

따라서, 처음부터 5의 경계를 구분하는 값으로 배열을 만들고, 나머지 구역은 조건에 맞게 구역을 나눌 수 있었다.
(해당 좌표가 5인지 아닌지 여부를 검사하고 5인 값이 나오면, 그 이후 좌표는 다음 5의 좌표가 나올 때까지 5의 구역임을 확정지을 수 있고, 5가 나오지 않은 좌표는 x좌표, y좌표에 따라 각 구역을
나타내면 된다.)

+ 1,2,3,4,5 구역의 넘버링을 하는 과정에서 예외가 발생하였다.
경계선을 구분하여 구역을 나누지 않을 경우, 1-4, 2-3의 구역을 정하는 과정에서 각 조건을 만족하는 r,c가 동시에 존재하기 때문에 경계 측정이 잘못된 결과를 나타낸다.
반례
N = 6, x = 1, y = 2, d1 = 1, d2 = 4
[1, 5, 2, 2, 2, 2]
[5, 5, 5, 2, 2, 2]
[3, 5, 5, 5, 2, 2]
[3, 3, 5, 5, 5, 2]
[3, 3, '2', 5, 5, 5]
[3, 3, 3, 3, 5, 4]
(5,3) 위치에 3의 넘버링이 되어야 하는데 2번 넘버링 조건과 3번 넘버링 조건이 동시에 만족하는 경우가 발생하여 2번 넘버링 수행 후 continue로 3번 넘버링이 불가능하다.

따라서, 넘버링하는 과정에서 경계선 조건을 추가가 필요하다.

이때, 경계선의 조건으로 5번 경계선의 기준이 r,c의 좌표값을 더하거나 뺀 값이 일정한 값을 유지하는 규칙을 발견하여 이를 이용하였다.
각 좌표의 r,c를 더한 값이 / 방향으로 일정한 값을 유지하기 때문에 x,y의 좌표값을 기준으로 더한 값보다 작으면 1번 구역으로 측정하고, 4번 경계선으로 x + y + d2 값보다 큰 경우 4번 구역으로 측정한다.
2,3번 구역의 경우 경계선이 r,c의 덧셈으로 일정한 값이 유지되지 않았고 뺄셈으로 일정한 값이 유지된다는 점을 이용하여 x - y 값보다 작은 경우 2번 구역으로 측정하고, (x + d1) - (y - d1) 값보다 큰 경우 3번 구역으로 측정하였다.
 */
public class BOJ17779 {
    static class BOJ17779_divide_electoral_district_standard {
        int x,y,d1,d2;

        BOJ17779_divide_electoral_district_standard(int x, int y, int d1, int d2) {
            this.x = x;
            this.y = y;
            this.d1 = d1;
            this.d2 = d2;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N,ans;
    static int[][] A;
    static ArrayList<BOJ17779_divide_electoral_district_standard> methods;

    public static void main(String[] args) throws IOException {
        init_setting();

        solve();
    }

    static void solve() {
        divide_electoral_district_method();

        for(BOJ17779_divide_electoral_district_standard s : methods) {
            calculate_differences_population(s);
        }

        System.out.println(ans);
    }

    static void calculate_differences_population(BOJ17779_divide_electoral_district_standard s) {
        int electoral_district_1 = 0;
        int electoral_district_2 = 0;
        int electoral_district_3 = 0;
        int electoral_district_4 = 0;
        int electoral_district_5 = 0;

        int[][] sections = divide_section_number(s);

        System.out.println("break");

        for(int r=1;r<=N;r++) {
            for(int c=1;c<=N;c++) {
                switch (sections[r][c]) {
                    case 1:
                        electoral_district_1 += A[r][c];
                        break;
                    case 2:
                        electoral_district_2 += A[r][c];
                        break;
                    case 3:
                        electoral_district_3 += A[r][c];
                        break;
                    case 4:
                        electoral_district_4 += A[r][c];
                        break;
                    case 5:
                        electoral_district_5 += A[r][c];
                        break;
                }
            }
        }

        int max_p = Math.max(electoral_district_1,
                Math.max(electoral_district_2,
                        Math.max(electoral_district_3,
                                Math.max(electoral_district_4, electoral_district_5))));

        int min_p = Math.min(electoral_district_1,
                Math.min(electoral_district_2,
                        Math.min(electoral_district_3,
                                Math.min(electoral_district_4, electoral_district_5))));

        ans = Math.min(ans, max_p - min_p);
    }

    static int[][] divide_section_number(BOJ17779_divide_electoral_district_standard s) {
        int[][] ns = new int[N+1][N+1];
        /*int boundary_1 = s.x + s.y;
        int boundary_2 = s.x - s.y;
        int boundary_3 = (s.x + s.d1) - (s.y - s.d1);
        int boundary_4 = s.x + s.y + s.d2;*/


        // 경계 표현
        // ↗, ↙
        for(int d=0;d<=s.d1;d++) {
            ns[s.x+d][s.y-d] = ns[s.x+s.d1+s.d2-d][s.y-s.d1+s.d2+d] = 5;
        }

        // ↖, ↘
        for(int d=0;d<=s.d2;d++) {
            ns[s.x+d][s.y+d] = ns[s.x+s.d1+s.d2-d][s.y-s.d1+s.d2-d] = 5;
        }

        for(int r=1;r<=N;r++) {
            boolean five_section = false;

            for(int c=1;c<=N;c++) {
                if(ns[r][c] != 5) {
                    if(five_section) {
                        ns[r][c] = 5;
                        continue;
                    }

                    if(r < s.x+s.d1 && c <= s.y) {
                        ns[r][c] = 1;
                        continue;
                    }

                    if(r <= s.x+s.d2 && c > s.y) {
                        ns[r][c] = 2;
                        continue;
                    }

                    if(r >= s.x+s.d1 && c < s.y-s.d1+s.d2) {
                        ns[r][c] = 3;
                        continue;
                    }
                    if(r > s.x+s.d2 && c >= s.y-s.d1+s.d2) {
                        ns[r][c] = 4;
                        continue;
                    }

                    /*if(r < s.x+s.d1 && c <= s.y && r + c < boundary_1) {
                        ns[r][c] = 1;
                        continue;
                    }

                    if(r <= s.x+s.d2 && c > s.y && r - c < boundary_2) {
                        ns[r][c] = 2;
                        continue;
                    }

                    if(r >= s.x+s.d1 && c < s.y-s.d1+s.d2 && r - c > boundary_3) {
                        ns[r][c] = 3;
                        continue;
                    }
                    if(r > s.x+s.d2 && c >= s.y-s.d1+s.d2 && r + c > boundary_4) {
                        ns[r][c] = 4;
                        continue;
                    }*/
                } else {
                    if(r == s.x || r == s.x+s.d1+s.d2) continue;
                    five_section = !five_section;
                }
            }
        }

        return ns;
    }

    static void divide_electoral_district_method() {
        for(int x=1;x<=N;x++) {
            for(int y=1;y<=N;y++) {
                for(int d1=1;x+d1<=N;d1++) {
                    for(int d2=1;y+d2<=N;d2++) {
                        if(x+d1+d2<=N && y-d1>=1) {
                            methods.add(new BOJ17779_divide_electoral_district_standard(x,y,d1,d2));
                        }
                    }
                }
            }
        }
    }

    static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        A = new int[N+1][N+1];

        methods = new ArrayList<>();

        ans = Integer.MAX_VALUE;

        for(int r=1;r<=N;r++) {
            String[] input = br.readLine().split(" ");
            for(int c=1;c<=N;c++) {
                A[r][c] = Integer.parseInt(input[c-1]);
            }
        }
    }
}
