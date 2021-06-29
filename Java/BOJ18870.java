import java.util.Arrays;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
/*
import java.io.BufferedReader;
import java.io.InputStreamReader;
 */
public class BOJ18870 {
    public static void main(String[] args) {
        /* io 기능과 같음
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        String[] input = br.readLine().split(" ");
        int[] nums = new int[N];
        for (int i = 0; i < N; i++)
            nums[i] = Integer.parseInt(input[i]);
         */
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

        /* 아래 코드와 같은 기능
        for(int i=0,j=0;i<sortNs.length;i++) {
            j = i;
            while(j > 0 && sortNs[j] == sortNs[j-1]) {
                j--;
            }
            map.put(sortNs[i],j);
        }
         */
        int idx = 0;
        for (int i : sortNs) {
            if(!map.containsKey(i))
                map.put(i,idx++);
        }

        /*
        StringBuilder sb = new StringBuilder();
        for (int n : nums)
            sb.append(map.get(n)).append(' ');

        System.out.println(sb.toString());
         */
        String st = new String("");
        for(int i : Ns) {
            st += map.get(i);
            st += " ";
        }
        System.out.println(st.toString());

    }
}