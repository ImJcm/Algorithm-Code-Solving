package DataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.RecursiveAction;

/*
집합의 표현 스페셜 저지

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	133088	44873	27416	29.750%
문제
초기에
$n+1$개의 집합
$\{0\}, \{1\}, \{2\}, \dots , \{n\}$이 있다. 여기에 합집합 연산과, 두 원소가 같은 집합에 포함되어 있는지를 확인하는 연산을 수행하려고 한다.

집합을 표현하는 프로그램을 작성하시오.

입력
첫째 줄에
$n$,
$m$이 주어진다.
$m$은 입력으로 주어지는 연산의 개수이다. 다음
$m$개의 줄에는 각각의 연산이 주어진다. 합집합은
$0$
$a$
$b$의 형태로 입력이 주어진다. 이는
$a$가 포함되어 있는 집합과,
$b$가 포함되어 있는 집합을 합친다는 의미이다. 두 원소가 같은 집합에 포함되어 있는지를 확인하는 연산은
$1$
$a$
$b$의 형태로 입력이 주어진다. 이는
$a$와
$b$가 같은 집합에 포함되어 있는지를 확인하는 연산이다.

출력
1로 시작하는 입력에 대해서
$a$와
$b$가 같은 집합에 포함되어 있으면 "YES" 또는 "yes"를, 그렇지 않다면 "NO" 또는 "no"를 한 줄에 하나씩 출력한다.

제한
 
$1 ≤ n ≤ 1\,000\,000$ 
 
$1 ≤ m ≤ 100\,000$ 
 
$0 ≤ a, b ≤ n$ 
 
$a$,
$b$는 정수
 
$a$와
$b$는 같을 수도 있다.
예제 입력 1
7 8
0 1 3
1 1 7
0 7 6
1 7 1
0 3 7
0 4 2
0 1 1
1 1 1
예제 출력 1
NO
NO
YES
출처
문제를 번역한 사람: author5
잘못된 데이터를 찾은 사람: Apple_Cplus
데이터를 추가한 사람: njw1204
알고리즘 분류
자료 구조
분리 집합
 */
public class BOJ1717 {
    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    private static class Solve {
        private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        void solve() throws IOException {
            init_setting();
        }

        private void init_setting() throws IOException {

        }
    }
}
