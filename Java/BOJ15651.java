//import java.util.Scanner;
import java.util.*;

public class BOJ15651 {
    static int m,n;
    static int[] list;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        list = new int[m];

        backtrack_permut(0);
        System.out.println(sb);
    }

    /* backtrack을 이용한 permutation */
    public static void backtrack_permut(int index) {
        /* backtrack 종료조건 */
        if (index == m) {
            for (int i=0; i<m; i++) {
                sb.append(list[i] + " ");
            }
            sb.append("\n");
            return;
        }

        for (int i=0; i<n; i++) {
            list[index] = i+1;
            backtrack_permut(index+1);
        }
    }
}
