package AlgorithmData_for_BOJ;

public class Divide_Conquer {
    //1~n까지의 합을 구하는 분할 정복 알고리즘
    static int divide_conquer_recursiveSum(int n) {
        if(n==1) return 1;
        if(n%2 == 1) return divide_conquer_recursiveSum(n-1) + n;
        return 2 * divide_conquer_recursiveSum(n/2) + (n/2) * (n/2);
    }

    //행렬의 거듭제곱
    /*
    import java.io.BufferedReader;
    import java.io.InputStreamReader;
    import java.io.IOException;
    import java.util.StringTokenizer;
     */
    public static int N;
    public static int[][] origin;	// A^1 일 때의 배열(초기 배열)

    static int[][] pow(int[][] A, int exp) {
        if(exp == 1) return A;

        int[][] ret = pow(A,exp/2); //divide

        ret = multiply(ret,ret);    //merge

        if(exp % 2 == 1) {
            ret = multiply(ret,origin);
        }

        return ret;
    }

    //conquer
    static int[][] multiply(int[][] o1, int[][] o2) {
        int[][] ret = new int[N][N];

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                for(int k = 0; k < N; k++) {
                    ret[i][j] += o1[i][k] * o2[k][j];
                }
            }
        }
        return ret;
    }
}
