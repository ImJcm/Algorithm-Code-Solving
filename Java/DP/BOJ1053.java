/*
팰린드롬 공장

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	1118	381	313	35.568%
문제
팰린드롬이란, 앞에서부터 읽었을 때와, 뒤에서부터 읽었을 때가 같은 문자열이다.

모든 문자열이 팰린드롬이 아니기 때문에 다음과 같은 4가지 연산으로 보통 문자열을 팰린드롬으로 만든다.

문자열의 어떤 위치에 어떤 문자를 삽입 (시작과 끝도 가능)
어떤 위치에 있는 문자를 삭제
어떤 위치에 있는 문자를 교환
서로 다른 문자를 교환
1, 2, 3번 연산은 마음껏 사용할 수 있지만, 마지막 연산은 많아야 한 번 사용할 수 있다.

문자열이 주어졌을 때, 팰린드롬으로 만들기 위해 필요한 연산의 최솟값을 출력하는 프로그램을 작성하시오.

입력
첫째 줄에 문자열이 주어진다. 영어 소문자로만 이루어져 있고, 길이는 최대 50이다.

출력
첫째 줄에 문제의 정답을 출력한다.

예제 입력 1
babacvabba
예제 출력 1
2
예제 입력 2
abba
예제 출력 2
0
예제 입력 3
dabba
예제 출력 3
1
예제 입력 4
abc
예제 출력 4
1
예제 입력 5
acxcba
예제 출력 5
1
예제 입력 6
abcacbd
예제 출력 6
1
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;

/*
    A1. dfs = 메모리 초과
    A2. dfs = 시간초과
    A3. dp
    참고 : https://coder-in-war.tistory.com/entry/BOJ-JAVA1053-%ED%8C%B0%EB%A6%B0%EB%93%9C%EB%A1%AC-%EA%B3%B5%EC%9E%A5
    ★참고 : https://bongssi.tistory.com/entry/%ED%8C%B0%EB%A6%B0%EB%93%9C%EB%A1%AC-%EA%B3%B5%EC%9E%A5
 */
/*
    어렵다....
    접근방법
    i와 j 사이의 문자열이 팰린드롬인가를 확인하기 위해 [i-1][j-1]의 문자열에서 3가지의 연산과정에서
    연산 횟수를 증가시켜주는 방식으로 dp를 적용하여 [0][length-1]의 값을 출력하면 된다

    이때, 3가지 연산의 점화식 적용은 아래와 같다
    그리고 4번 연산과정은 한번만 적용하기 때문에, dfs_dp함수를 돌리기 전에
    Swap을 적용한 dfs_dp + 적용안한 dfs_dp를 수행해주면 된다.
    dp[i][j] = min(dp[i+1][j] + 1, dp[i][j-1] + 1, x, y)
    x : (i,j의 위치의 값이 같을 때, dp[i][j] = dp[i+1][j-1])
    y : (i,j의 위치의 값이 다를 때, dp[i][j] = min([i][j], [i+1][j-1]+1)
 */
public class BOJ1053 {
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        ArrayList<Character> a = new ArrayList<>();

        String s = br.readLine();
        for(int i=0;i<s.length();i++) {
            a.add(s.charAt(i));
        }

        min = dfs_dp(a);
        for(int i=0;i<s.length()-1;i++) {
            for(int j=i+1;j<s.length();j++) {
                if(a.get(i).equals(a.get(j))) continue;
                min = Math.min(dfs_dp(swap(i,j,a)) + 1,min);
            }
        }
        System.out.println(min);
    }

    static int dfs_dp(ArrayList<Character> a) {
        int[][] dp = new int[a.size()][a.size()];
        for(int i=0;i<a.size();i++) {
            dp[i][i] = 0;
            if(i != a.size() - 1) {
                dp[i][i+1] = a.get(i).equals(a.get(i+1)) ? 0 : 1;
            }
        }

        for(int i=2;i<a.size();i++) {
            for(int j=0;j<a.size()-i;j++) {
                dp[j][j+i] = Math.min(dp[j+1][j+i] + 1, dp[j][j+i-1] + 1);
                if(a.get(j+i).equals(a.get(j))) {
                    dp[j][j+i] = Math.min(dp[j+1][j+i-1],dp[j][j+i]);
                } else {
                    dp[j][j+i] = Math.min(dp[j+1][j+i-1] + 1,dp[j][j+i]);
                }
            }
        }
        return dp[0][a.size()-1];
    }

    static ArrayList<Character> swap(int i,int j, ArrayList<Character> a) {
        ArrayList<Character> tmp = (ArrayList<Character>) a.clone();
        Character t = tmp.get(i);
        tmp.set(i,tmp.get(j));
        tmp.set(j,t);

        return tmp;
    }
}
/*
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;

    //A1. dfs = 메모리 초과
    //A2. dfs = 시간 초과
public class BOJ1053 {
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        ArrayList<Character> a = new ArrayList<>();

        String s = br.readLine();
        for(int i=0;i<s.length();i++) {
            a.add(s.charAt(i));
        }

        dfs(a,0,0);
        for(int i=0;i<s.length()-1;i++) {
            for(int j=i+1;j<s.length();j++) {
                if(a.get(i).equals(a.get(j))) continue;
                dfs(swap(i,j,a),0,1);
            }
        }
        System.out.println(s.length() == 0 ? 0 : min);
    }

    static void dfs(ArrayList<Character> a, int s, int cnt) {
//        #A2
//        if(s == a.size()/2 || min < cnt) {
        if(s == a.size()/2) {
            for(int i=0;i<a.size()/2;i++) {
                if(!a.get(i).equals(a.get(a.size()-i-1))) {
                    return;
                }
            }
            min = Math.min(cnt,min);
        }

        ArrayList<Character> tmp = (ArrayList<Character>) a.clone();
        for(int i=s;i<a.size()/2;i++) {
            if(a.get(i).equals(a.get(a.size()-i-1))) {
                dfs(tmp,i+1,cnt);
                continue;
            }
//            #A2
//            dfs(tmp,i,cnt+1);

            tmp.add(i,a.get(a.size()-i-1));
            dfs(tmp,i,cnt+1);
            tmp.remove(i);

            tmp.set(i,a.get(a.size()-i-1));
            dfs(tmp,i,cnt+1);
//            #A1
            tmp.remove(i);
            dfs(tmp,i,cnt+1);
        }
    }

    static ArrayList<Character> swap(int i,int j, ArrayList<Character> a) {
        ArrayList<Character> tmp = (ArrayList<Character>) a.clone();
        Character t = tmp.get(i);
        tmp.set(i,tmp.get(j));
        tmp.set(j,t);

        return tmp;
    }
}
 */