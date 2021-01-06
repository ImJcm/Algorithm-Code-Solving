import java.util.*;

public class BOJ15649 {
    static int n,m;
    static int[] lt;
    static boolean[] used;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        lt = new int[m];
        used = new boolean[n];
        backtrack_permut(0);
        System.out.println(sb);
    }

    public static void backtrack_permut(int index) {
        if (index == m) {
            for(int l : lt) {
                sb.append(l + " ");
            }
            sb.append("\n");
            return;
        }

        for (int i=0;i<n;i++) {
            if(!used[i]) {
                used[i] = true;
                lt[index] = i+1;
                backtrack_permut(index+1);
                used[i] = false;
            }
        }
    }
}
