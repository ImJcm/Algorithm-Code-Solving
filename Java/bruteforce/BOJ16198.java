package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/*
에너지 모으기

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	512 MB	4866	3625	2898	75.547%
문제
N개의 에너지 구슬이 일렬로 놓여져 있고, 에너지 구슬을 이용해서 에너지를 모으려고 한다.

i번째 에너지 구슬의 무게는 Wi이고, 에너지를 모으는 방법은 다음과 같으며, 반복해서 사용할 수 있다.

에너지 구슬 하나를 고른다. 고른 에너지 구슬의 번호를 x라고 한다. 단, 첫 번째와 마지막 에너지 구슬은 고를 수 없다.
x번째 에너지 구슬을 제거한다.
Wx-1 × Wx+1의 에너지를 모을 수 있다.
N을 1 감소시키고, 에너지 구슬을 1번부터 N번까지로 다시 번호를 매긴다. 번호는 첫 구슬이 1번, 다음 구슬이 2번, ... 과 같이 매겨야 한다.
N과 에너지 구슬의 무게가 주어졌을 때, 모을 수 있는 에너지 양의 최댓값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 에너지 구슬의 개수 N(3 ≤ N ≤ 10)이 주어진다.

둘째 줄에는 에너지 구슬의 무게 W1, W2, ..., WN을 공백으로 구분해 주어진다. (1 ≤ Wi ≤ 1,000)

출력
첫째 줄에 모을 수 있는 에너지의 최댓값을 출력한다.

예제 입력 1
4
1 2 3 4
예제 출력 1
12
예제 입력 2
5
100 2 1 3 100
예제 출력 2
10400
예제 입력 3
7
2 2 7 6 90 5 9
예제 출력 3
1818
예제 입력 4
10
1 1 1 1 1 1 1 1 1 1
예제 출력 4
8
 */
/*
    N의 크기가 작기 때문에 브루트포스, DFS으로 생각한다.
    1~N-1까지 구슬을 선택했을 때의 최대 에너지 값만을 추출하여 누적시키게 되면, 실제로 최대값을 구하지 못한다.
    따라서, 백트랙킹 사용해야한다.
    Bruteforce, DFS의 경우, O(N!) : 8!
 */
public class BOJ16198 {
    static int N,Max_E = 0;
    static ArrayList<Integer> energy;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        energy = new ArrayList<>(
                Arrays.asList(br.readLine().split(" "))
                .stream()
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList())
        );

//        DFS(0);
//        System.out.println(Max_E);


        System.out.println(DFS2(energy));

    }

    //단순 최대 값을 갖는 에너지를 누적한 경우, 최대 에너지 값을 만들지 못함.
    //모든 경우의 수를 검사하려면 다중 반복문을 통해 확인 가능
    /*static void bruteforce() {
        int choiceE = 0;
        int ret_energy = 0;

        while(energy.size() > 2) {
            for(int i=1;i<energy.size()-1;i++) {
                choiceE = i;
                ret_energy = Math.max(ret_energy,energy.get(choiceE-1) * energy.get(choiceE+1));
            }
            Max_E += ret_energy;
            energy.remove(choiceE);
            ret_energy = 0;
        }
    }*/

    /*
        모든 경우의 수를 확인하는 재귀형태의 백트랙킹
     */
    static void DFS(int result) {
        if(energy.size() == 2) {
            Max_E = Math.max(result, Max_E);
            return;
        }

        for(int i=1;i<energy.size()-1;i++) {
            result += energy.get(i-1) * energy.get(i+1);
            int choiceE = energy.get(i);
            energy.remove(i);
            DFS(result);
            energy.add(i,choiceE);
            result -= energy.get(i-1) * energy.get(i+1);
        }
    }

    /*
        https://github.com/suhyunsim/Algorithm_Practice/issues/176
        위의 DFS와 같은 기능을 한다.
     */
    static int DFS2(ArrayList<Integer> arr) {
        if(arr.size() == 2) {
            return 0;
        }
        int ret = 0;
        for(int i=1;i<arr.size()-1;i++) {
            int energy = arr.get(i-1) * arr.get(i+1);
            ArrayList<Integer> newlist = new ArrayList<>(arr);
            newlist.remove(i);
            energy += DFS2(newlist);
            if(ret < energy) {
                ret = energy;
            }
        }
        return ret;
    }

}
