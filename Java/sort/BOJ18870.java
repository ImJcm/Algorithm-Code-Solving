package BOJ.sort;/*
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

import java.util.Arrays;
import java.util.Scanner;

public class BOJ18870 {
    public static void main(String[] args) {
        int N;
        int Ns[];

        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        Ns = new int[N];
        sc.nextLine();  // \n제거

        String s[] = sc.nextLine().split(" ");
        for(int i=0;i < s.length;i++){
            Ns[i] = Integer.parseInt(s[i]);
        }

        /* 계수정렬의 경우, 입력되는 숫자들 간격이 클수록 복잡도가 커진다는 단점이 있어서 사용 불가 */
        /* 일반적인 버블정렬과 같은 시간복잡도가 n^2인 정렬은 제외하고, Quick Sort(오름차순)이후, 중복되는 값은 이전 값과 비교하여,
           같으면, 현재 index에서 -1씩 진행하여, 마지막으로 같은 값이 나올 때까지의 index 값을 출력한다.
         */
        Arrays.sort(Ns);    //Quick sort

        /* 출력 구현부 */

        //System.out.println(Arrays.toString(Ns));

    }
}
