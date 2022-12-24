import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
배열 돌리기 3

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	512 MB	6656	3386	2465	50.183%
문제
크기가 N×M인 배열이 있을 때, 배열에 연산을 R번 적용하려고 한다. 연산은 총 6가지가 있다.

1번 연산은 배열을 상하 반전시키는 연산이다.

1 6 2 9 8 4 → 4 2 9 3 1 8
7 2 6 9 8 2 → 9 2 3 6 1 5
1 8 3 4 2 9 → 7 4 6 2 3 1
7 4 6 2 3 1 → 1 8 3 4 2 9
9 2 3 6 1 5 → 7 2 6 9 8 2
4 2 9 3 1 8 → 1 6 2 9 8 4
   <배열>       <연산 결과>
2번 연산은 배열을 좌우 반전시키는 연산이다.

1 6 2 9 8 4 → 4 8 9 2 6 1
7 2 6 9 8 2 → 2 8 9 6 2 7
1 8 3 4 2 9 → 9 2 4 3 8 1
7 4 6 2 3 1 → 1 3 2 6 4 7
9 2 3 6 1 5 → 5 1 6 3 2 9
4 2 9 3 1 8 → 8 1 3 9 2 4
   <배열>       <연산 결과>
3번 연산은 오른쪽으로 90도 회전시키는 연산이다.

1 6 2 9 8 4 → 4 9 7 1 7 1
7 2 6 9 8 2 → 2 2 4 8 2 6
1 8 3 4 2 9 → 9 3 6 3 6 2
7 4 6 2 3 1 → 3 6 2 4 9 9
9 2 3 6 1 5 → 1 1 3 2 8 8
4 2 9 3 1 8 → 8 5 1 9 2 4
   <배열>       <연산 결과>
4번 연산은 왼쪽으로 90도 회전시키는 연산이다.

1 6 2 9 8 4 → 4 2 9 1 5 8
7 2 6 9 8 2 → 8 8 2 3 1 1
1 8 3 4 2 9 → 9 9 4 2 6 3
7 4 6 2 3 1 → 2 6 3 6 3 9
9 2 3 6 1 5 → 6 2 8 4 2 2
4 2 9 3 1 8 → 1 7 1 7 9 4
   <배열>       <연산 결과>
5, 6번 연산을 수행하려면 배열을 크기가 N/2×M/2인 4개의 부분 배열로 나눠야 한다. 아래 그림은 크기가 6×8인 배열을 4개의 그룹으로 나눈 것이고, 1부터 4까지의 수로 나타냈다.

1 1 1 1 2 2 2 2
1 1 1 1 2 2 2 2
1 1 1 1 2 2 2 2
4 4 4 4 3 3 3 3
4 4 4 4 3 3 3 3
4 4 4 4 3 3 3 3
5번 연산은 1번 그룹의 부분 배열을 2번 그룹 위치로, 2번을 3번으로, 3번을 4번으로, 4번을 1번으로 이동시키는 연산이다.

3 2 6 3 1 2 9 7 → 2 1 3 8 3 2 6 3
9 7 8 2 1 4 5 3 → 1 3 2 8 9 7 8 2
5 9 2 1 9 6 1 8 → 4 5 1 9 5 9 2 1
2 1 3 8 6 3 9 2 → 6 3 9 2 1 2 9 7
1 3 2 8 7 9 2 1 → 7 9 2 1 1 4 5 3
4 5 1 9 8 2 1 3 → 8 2 1 3 9 6 1 8
     <배열>            <연산 결과>
6번 연산은 1번 그룹의 부분 배열을 4번 그룹 위치로, 4번을 3번으로, 3번을 2번으로, 2번을 1번으로 이동시키는 연산이다.

3 2 6 3 1 2 9 7 → 1 2 9 7 6 3 9 2
9 7 8 2 1 4 5 3 → 1 4 5 3 7 9 2 1
5 9 2 1 9 6 1 8 → 9 6 1 8 8 2 1 3
2 1 3 8 6 3 9 2 → 3 2 6 3 2 1 3 8
1 3 2 8 7 9 2 1 → 9 7 8 2 1 3 2 8
4 5 1 9 8 2 1 3 → 5 9 2 1 4 5 1 9
     <배열>            <연산 결과>
입력
첫째 줄에 배열의 크기 N, M과 수행해야 하는 연산의 수 R이 주어진다.

둘째 줄부터 N개의 줄에 배열 A의 원소 Aij가 주어진다.

마지막 줄에는 수행해야 하는 연산이 주어진다. 연산은 공백으로 구분되어져 있고, 문제에서 설명한 연산 번호이며, 순서대로 적용시켜야 한다.

출력
입력으로 주어진 배열에 R개의 연산을 순서대로 수행한 결과를 출력한다.

제한
2 ≤ N, M ≤ 100
1 ≤ R ≤ 1,000
N, M은 짝수
1 ≤ Aij ≤ 108
예제 입력 1
6 8 1
3 2 6 3 1 2 9 7
9 7 8 2 1 4 5 3
5 9 2 1 9 6 1 8
2 1 3 8 6 3 9 2
1 3 2 8 7 9 2 1
4 5 1 9 8 2 1 3
1
예제 출력 1
4 5 1 9 8 2 1 3
1 3 2 8 7 9 2 1
2 1 3 8 6 3 9 2
5 9 2 1 9 6 1 8
9 7 8 2 1 4 5 3
3 2 6 3 1 2 9 7
예제 입력 2
6 8 1
3 2 6 3 1 2 9 7
9 7 8 2 1 4 5 3
5 9 2 1 9 6 1 8
2 1 3 8 6 3 9 2
1 3 2 8 7 9 2 1
4 5 1 9 8 2 1 3
2
예제 출력 2
7 9 2 1 3 6 2 3
3 5 4 1 2 8 7 9
8 1 6 9 1 2 9 5
2 9 3 6 8 3 1 2
1 2 9 7 8 2 3 1
3 1 2 8 9 1 5 4
예제 입력 3
6 8 1
3 2 6 3 1 2 9 7
9 7 8 2 1 4 5 3
5 9 2 1 9 6 1 8
2 1 3 8 6 3 9 2
1 3 2 8 7 9 2 1
4 5 1 9 8 2 1 3
3
예제 출력 3
4 1 2 5 9 3
5 3 1 9 7 2
1 2 3 2 8 6
9 8 8 1 2 3
8 7 6 9 1 1
2 9 3 6 4 2
1 2 9 1 5 9
3 1 2 8 3 7
예제 입력 4
6 8 1
3 2 6 3 1 2 9 7
9 7 8 2 1 4 5 3
5 9 2 1 9 6 1 8
2 1 3 8 6 3 9 2
1 3 2 8 7 9 2 1
4 5 1 9 8 2 1 3
4
예제 출력 4
7 3 8 2 1 3
9 5 1 9 2 1
2 4 6 3 9 2
1 1 9 6 7 8
3 2 1 8 8 9
6 8 2 3 2 1
2 7 9 1 3 5
3 9 5 2 1 4
예제 입력 5
6 8 1
3 2 6 3 1 2 9 7
9 7 8 2 1 4 5 3
5 9 2 1 9 6 1 8
2 1 3 8 6 3 9 2
1 3 2 8 7 9 2 1
4 5 1 9 8 2 1 3
5
예제 출력 5
2 1 3 8 3 2 6 3
1 3 2 8 9 7 8 2
4 5 1 9 5 9 2 1
6 3 9 2 1 2 9 7
7 9 2 1 1 4 5 3
8 2 1 3 9 6 1 8
예제 입력 6
6 8 1
3 2 6 3 1 2 9 7
9 7 8 2 1 4 5 3
5 9 2 1 9 6 1 8
2 1 3 8 6 3 9 2
1 3 2 8 7 9 2 1
4 5 1 9 8 2 1 3
6
예제 출력 6
1 2 9 7 6 3 9 2
1 4 5 3 7 9 2 1
9 6 1 8 8 2 1 3
3 2 6 3 2 1 3 8
9 7 8 2 1 3 2 8
5 9 2 1 4 5 1 9
예제 입력 7
6 8 6
3 2 6 3 1 2 9 7
9 7 8 2 1 4 5 3
5 9 2 1 9 6 1 8
2 1 3 8 6 3 9 2
1 3 2 8 7 9 2 1
4 5 1 9 8 2 1 3
1 2 3 4 5 6
예제 출력 7
3 1 2 8 9 1 5 4
1 2 9 7 8 2 3 1
2 9 3 6 8 3 1 2
8 1 6 9 1 2 9 5
3 5 4 1 2 8 7 9
7 9 2 1 3 6 2 3
 */
/*
    swap으로 배열에서 인덱스에 위치한 값끼리 swap으로 변경하려 했으나, 90도 회전의 경우 임시배열을
    생성해야 하므로, 연산과정은 생략하고 과정에 다른 결과 값만을 고려하여 출력
 */
public class BOJ16935 {
    static int N,M,R;
    static int[] Type;
    static int[][] ar;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        ar = new int[N][M];

        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;j++) {
                ar[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Type = new int[R];
        Type = Arrays.asList(br.readLine().split(" "))
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        int[][] tmpArr = ar;
        for(int i=0;i<R;i++) {
            tmpArr = Apply(Type[i],tmpArr);
        }

        for(int i=0;i<tmpArr.length;i++) {
            for(int j=0;j<tmpArr[i].length;j++) {
                System.out.print(tmpArr[i][j] + " ");
            }
            System.out.println();
        }
    }

    static int[][] Apply(int t,int[][] arr) {
        int[][] newArr;
        if(t == 3 || t == 4) {
            newArr = new int[arr[0].length][arr.length];
        } else {
            newArr = new int[arr.length][arr[0].length];
        }

        switch (t) {
            //1,2 방식 + swap
            case 1:
                //1 - 상하 반전
                for (int i = 0; i < (arr.length) / 2; i++) {
                    for (int j = 0; j < arr[i].length; j++) {
                        swap(arr, i, j, (arr.length - 1) - i, j);
                    }
                }
                return arr;
            case 2:
                //2 - 좌우 반전
                for(int i=0;i<arr.length;i++) {
                    for(int j=0;j<(arr[i].length)/2;j++) {
                        swap(arr,i,j,i,(arr[i].length-1)-j);
                    }
                }
                return arr;
            case 3:
                //3 - 오른쪽으로 90도 회전
                //90도 회전의 경우, 배열의 size가 달라질 수 있기 떄문에, swap 대신 기존에 사용하던 회전 적용 후 출력 형식을
                //이용하여 새로운 배열에 저장 후, 저장한 배열을 return하는 형태로 알고리즘 구현
                for(int i=0;i<arr[0].length;i++) {
                    for (int j = 0; j < arr.length; j++) {
                        newArr[i][j] = arr[(arr.length - 1) - j][i];
                    }
                }
                return newArr;
            case 4:
                //4 - 왼쪽으로 90도 회전
                for(int i=0;i<arr[0].length;i++) {
                    for(int j=0;j<arr.length;j++) {
                        newArr[i][j] = arr[j][(arr[0].length-1)-i];
                    }
                }
                return newArr;
            case 5:
                //5,6 방법의 경우, 기존 배열 크기를 사용
                //5 - 4개의 N/2, M/2인 부분그룹에서,
                // ㄴ 1 2  -> 4 1
                // ㄴ 4 3     3 2
                for (int i = 0; i < (arr.length)/2; i++) {
                    for (int j = 0; j < (arr[i].length) / 2; j++) {
                        newArr[i][j] = arr[(arr.length)/2+i][j];
                    }
                    for (int j = (arr[i].length)/2; j < arr[i].length; j++) {
                        newArr[i][j] = arr[i][j - (arr[i].length)/2];
                    }
                }

                for(int i=(arr.length)/2;i<arr.length;i++) {
                    for(int j=0;j<arr[i].length/2;j++) {
                        newArr[i][j] = arr[i][arr[i].length/2 + j];
                    }
                    for(int j=(arr[i].length)/2;j<arr[i].length;j++) {
                        newArr[i][j] = arr[i - (arr.length)/2][j];
                    }
                }
                return newArr;
            case 6:
                //6 - 1 2  -> 2 3
                // ㄴ 4 3     1 4
                for (int i = 0; i < (arr.length)/2; i++) {
                    for (int j = 0; j < (arr[i].length)/2; j++) {
                        newArr[i][j] = arr[i][(arr[i].length)/2 + j];
                    }
                    for (int j = (arr[i].length)/2; j < arr[i].length; j++) {
                        newArr[i][j] = arr[(arr.length)/2 + i][j];
                    }
                }

                for(int i=(arr.length)/2;i<arr.length;i++) {
                    for(int j=0;j<(arr[i].length)/2;j++) {
                        newArr[i][j] = arr[i - (arr.length)/2][j];
                    }
                    for(int j=(arr[i].length)/2;j<arr[i].length;j++) {
                        newArr[i][j] = arr[i][j - (arr[i].length)/2];
                    }
                }
               return newArr;
        }
        //Not affected
        return (arr == null ? newArr : arr);
    }

    static void swap(int[][] a,int fromx,int fromy,int tox, int toy) {
        int temp = a[fromx][fromy];
        a[fromx][fromy] = a[tox][toy];
        a[tox][toy] = temp;
    }
}
