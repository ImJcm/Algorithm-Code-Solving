package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
가르침

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	128 MB (하단 참고)	48637	13670	8010	25.728%
문제
남극에 사는 김지민 선생님은 학생들이 되도록이면 많은 단어를 읽을 수 있도록 하려고 한다. 그러나 지구온난화로 인해 얼음이 녹아서 곧 학교가 무너지기 때문에, 김지민은 K개의 글자를 가르칠 시간 밖에 없다. 김지민이 가르치고 난 후에는, 학생들은 그 K개의 글자로만 이루어진 단어만을 읽을 수 있다. 김지민은 어떤 K개의 글자를 가르쳐야 학생들이 읽을 수 있는 단어의 개수가 최대가 되는지 고민에 빠졌다.

남극언어의 모든 단어는 "anta"로 시작되고, "tica"로 끝난다. 남극언어에 단어는 N개 밖에 없다고 가정한다. 학생들이 읽을 수 있는 단어의 최댓값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 단어의 개수 N과 K가 주어진다. N은 50보다 작거나 같은 자연수이고, K는 26보다 작거나 같은 자연수 또는 0이다. 둘째 줄부터 N개의 줄에 남극 언어의 단어가 주어진다. 단어는 영어 소문자로만 이루어져 있고, 길이가 8보다 크거나 같고, 15보다 작거나 같다. 모든 단어는 중복되지 않는다.

출력
첫째 줄에 김지민이 K개의 글자를 가르칠 때, 학생들이 읽을 수 있는 단어 개수의 최댓값을 출력한다.

예제 입력 1
3 6
antarctica
antahellotica
antacartica
예제 출력 1
2
예제 입력 2
2 3
antaxxxxxxxtica
antarctica
예제 출력 2
0
예제 입력 3
9 8
antabtica
antaxtica
antadtica
antaetica
antaftica
antagtica
antahtica
antajtica
antaktica
예제 출력 3
3
 */
/*
아래와 같이 arr에서의 0번 요소부터 추가가능한 글자들을 우선적으로 추가하고 있는 탐욕적 방법입니다. 코드 제출 결과로 "틀렸습니다"라는 결과를
가져와서 다른 로직을 생각해야 했습니다. 코드를 디버깅해보면서 생각해보았을 때, 0번인 요소의 문자들부터 추가하는 것이 아닌 0,1,...,N번 까지의
모든 요소들을 각각 추가해보는 로직이 필요하다고 생각한다.

 */
public class BOJ1062 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] arr;
    static String[] arr2;
    static int N,K,maxCnt = 0;
    static boolean[] visited;
    static List<Integer> readable_words = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        K = Integer.parseInt(tmp[1]);

        arr2 = new String[N];
        visited = new boolean[26];

        readable();

        if(K < 5) {
            System.out.println(0);
        } else if(K == 26) {
            System.out.println(N);
        } else {
            solve(0,0);
            //solve(0,5);
            System.out.println(maxCnt);
        }

    }

    /*
        아이디어 : N개의 문자열은 "anta", "tica"로 시작과 끝을 맺기때문에 'a','c','i','n','t'을 제외한 읽기 가능한 문자열 배열을 만든다.
        읽기 가능한 문자열 배열로 입력받는 K에서 K-5의 수만큼의 가능한 문자들을 뽑아서 모든 경우의 수를 구한 뒤 해당 경우에서 입력받은 문자열
        들을 읽기가 가능한지 비교하면 된다고 한다.

        이를 바탕으로 코드를 구현해보겠다.

        "acitn"을 제외하기 때문에 depth는 k-5가 같아질 때, 기저사례를 만족한다.
        'a'~'z' = 0~25에 해당하는 알파벳들을 반복하여 K-5개의 만들 수 있는 알파벳 조합을 만든다.
        depth에 해당한 알파벳 조합으로 모든 입력된 문자열을 비교하여 읽을 수 있는 최대한의 수를 구한다.

     */
    static void solve(int depth, int start) {
        if(depth == K - 5) {
            result_check();
            return;
        }

        //a~z까지 모든 알파벳으로 읽을 수 있는 조합을 만든다.
        for(int i=start;i<26;i++) {
            if(!visited[i]) {
                visited[i] = true;
                solve(depth + 1, i);
                visited[i] = false;
            }
        }
        //아래와 같이 읽을 수 있는 단어들까지만 조합에 넣게되는 경우, readable_words의 크기가 K-5보다 클 경우, 올바른 결과를 나타내지만,
        //반대로, K-5 값이 readable_words의 크기를 넘어갈 경우 기저사례를 만족하지 못하는 문제 발생
        /*for(int i=start;i<readable_words.size();i++) {
            int word = readable_words.get(i);
            if(!visited[word]) {
                visited[word] = true;
                solve(depth + 1, i);
                visited[word] = false;
            }
        }*/
    }

    static void result_check() {
        int cnt = 0;
        boolean flag;
        for(int i=0;i<N;i++) {
            flag = true;
            for(int j=4;j<arr2[i].length()-4;j++) {
                if(!visited[arr2[i].charAt(j) - 'a']) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                cnt++;
            }
        }
        maxCnt = maxCnt < cnt ? cnt : maxCnt;
        return;
    }

    static void readable() throws IOException {
        for(int i=0;i<26;i++) {
            visited[i] = false;
        }

        visited['a' - 'a'] = true;
        visited['c' - 'a'] = true;
        visited['i' - 'a'] = true;
        visited['n' - 'a'] = true;
        visited['t' - 'a'] = true;

        readable_words.add('a' - 'a');
        readable_words.add('c' - 'a');
        readable_words.add('i' - 'a');
        readable_words.add('n' - 'a');
        readable_words.add('t' - 'a');

        for(int i=0;i<N;i++) {
            String s = br.readLine();
            arr2[i] = s;
            for(Character c : s.substring(4,s.length()-4).toCharArray()) {
                if(!readable_words.contains(c-'a')) {
                    readable_words.add(c-'a');
                }
            }
        }
    }

    /*
        N = 50, K = 15인 예시를 적용 시, 결과출력이 안될정도로 느린 것을 볼 수 있다.
        제출 시, 시간초과 발생하여 가지치기(pruning) 또는 불필요한 중복을 제거하는 방법을 찾아야한다.
        visited 배열을 통해 이미 읽기 가능한 알파벳 단어로 쓰인 문장인지 아닌지 판별하여 가지치기를 수행한다.
        하지만, 5%까지 수행 후, 시간초과 발생
        따라서, 힌트를 참고하여 로직은 비슷하지만 다른 아이디어로 코드를 구현해야할 것 같다.
     */
    static void solve2_main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tmp = br.readLine().split(" ");
        N = Integer.parseInt(tmp[0]);
        K = Integer.parseInt(tmp[1]);

        arr = new int[N];
        visited = new boolean[N];

        for(int i=0;i<N;i++) {
            String str = br.readLine();
            arr[i] = removeDuplicatedCharToBit(str);
            visited[i] = false;
        }

        if(K < 5) {
            System.out.println("0");
        } else if(K == 26) {
            System.out.println(N);
        } else {
            solve(0,K);
            System.out.println(maxCnt);
        }
    }

    static void solve2(int readable_bit, int depth) {
        if(depth == 0) {            //읽기 가능한 문자의 수가 0이 되면, 0~N까지의 입력된 문자열들과 비교하여 읽기 가능한 문자들의 수를 카운트
            int cnt = 0;
            for(int i=0;i<N;i++) {
                if(arr[i] == (readable_bit & arr[i])) {
                    cnt++;
                }
            }
            maxCnt = maxCnt < cnt ? cnt : maxCnt;
            return;
        }

        for(int i=0;i<N;i++) {
            int dup_bit = arr[i] - (readable_bit & arr[i]);     //입력된 문자열에서 읽을 수 있는 문자들을 비교하여 중복되는 문자 bit에 해당하는 정수형 값
            if(dup_bit <= 0) continue;                          //입력된 문자열을 읽을 수 있는 문자들과 비교하여 포함한 문자열이라면 다음 문자열로 이동
            int dup_bit_cnts = Integer.bitCount(dup_bit);       //중복된 문자열의 문자 수
            if(depth - dup_bit_cnts >= 0 && !visited[i]) {      //읽기가 가능한 문자의 남은 수
                visited[i] = true;
                solve(readable_bit | arr[i], depth - dup_bit_cnts); //readable_bit에 읽기 가능한 문자들의 bit를 모두 누적
                visited[i] = false;
            }
        }
    }

    /*
        탐욕적 방법으로 잘못된 접근 방법
     */
    static void solve_greedy() {
        int cnt = 0;
        int possible_bit_cnts = 0;
        int optional_bit = 0;
        for(int i=0;i<N;i++) {
            int base_word_bits = Integer.bitCount(arr[i]);
            if(base_word_bits > K) continue;
            cnt = 1;
            possible_bit_cnts = K - base_word_bits;
            optional_bit = 0;

            for(int j=0;j<N;j++) {
                if(i==j) continue;
                int additional_i_arr = arr[i] | optional_bit;
                int duplicated_bit = additional_i_arr & arr[j];
                int duplicated_bit_cnt = Integer.bitCount(duplicated_bit);

                if(possible_bit_cnts >= 0 &&
                        Integer.bitCount(arr[j]) - duplicated_bit_cnt <= possible_bit_cnts) {
                    optional_bit += (arr[j] - duplicated_bit);
                    possible_bit_cnts -= Integer.bitCount(arr[j] - duplicated_bit);
                    cnt++;
                }
            }
            maxCnt = maxCnt <= cnt ? cnt : maxCnt;
        }

        System.out.println(maxCnt);
    }

    static int removeDuplicatedCharToBit(String s) {
        int num = 0;
        StringBuilder sb = new StringBuilder();

        for(char c : s.toCharArray()) {
            if(sb.indexOf(String.valueOf(c)) == -1) {
                sb.append(c);
                num += 1 << (c - 'a');
            }
        }
        return num;
    }
}
