package AlgorithmData_for_BOJ;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/* *** 지속적으로 유용하거나 알아야할 것들이 있다면 최신화 *** */

public class IO {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st; //- st[0],st[1], ... , ~
    static ArrayList<Integer> arl;
    static int[] arr;
    static int[][] arr2;
    static int[][] arr3;
    static char[][] arr4;
    final static int n = 3, m = 4;

    public static void main(String[] args) throws IOException {
        //int[]
        //1.
        arr = Arrays.asList(br.readLine().split(""))
                .stream().mapToInt(Integer::parseInt)
                .toArray();
        //2.
        arr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        //int[][] - 012345 띄어쓰기x
        for(int i=0;i<n;i++) {
            String input = br.readLine();
            for(int j=0;j<m;j++) {
                arr2[i][j] = input.charAt(j) - '0';
            }
        }

        //int[][] - 0 1 2 3 4 5 띄어쓰기o
        //1.
        for(int i=0;i<n;i++) {
            String[] input = br.readLine().split(" ");
            for(int j=0;j<m;j++) {
                arr2[i][j] = Integer.parseInt(input[j]);
            }
        }

        //2.
        // N M
        /* 0 ... M
           | ... |
           N ... ┘
         */
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //StringTokenizer st = new StringTokenizer(br.readline()," ");
        int[][] origin;
        st = new StringTokenizer(br.readLine()," ");
        int N = Integer.parseInt(st.nextToken());
        long M = Long.parseLong(st.nextToken());
        origin = new int[N][(int)M];
        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine()," ");
            for(int j=0;j<M;j++) {
                origin[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //int[][] - Arrays.asList~
        for(int i=0;i<n;i++) {
            arr3[i] = Arrays.asList(br.readLine().split(""))
                    .stream()
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        //ArrayList<Integer> - Arrays.stream()~
        arl = (ArrayList<Integer>)Arrays
                .stream(br.readLine().split(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        for(int i : arr) {
            System.out.print(i +", ");
            //sb.append(i + ", ");
        }

        /*
        3
        CCP
        CCP
        PPC
         */
        for(int i=0;i<N;i++) {
            arr4[i] = br.readLine().toCharArray();
        }
        //System.out.println(sb);
        System.out.println("----------------------------------------");

        for(Integer i : arl) {
            System.out.print(i.intValue());
        }
    }
}