package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
국영수

시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
1 초	256 MB	44352	24522	18006	55.309%
문제
도현이네 반 학생 N명의 이름과 국어, 영어, 수학 점수가 주어진다. 이때, 다음과 같은 조건으로 학생의 성적을 정렬하는 프로그램을 작성하시오.

국어 점수가 감소하는 순서로
국어 점수가 같으면 영어 점수가 증가하는 순서로
국어 점수와 영어 점수가 같으면 수학 점수가 감소하는 순서로
모든 점수가 같으면 이름이 사전 순으로 증가하는 순서로 (단, 아스키 코드에서 대문자는 소문자보다 작으므로 사전순으로 앞에 온다.)
입력
첫째 줄에 도현이네 반의 학생의 수 N (1 ≤ N ≤ 100,000)이 주어진다. 둘째 줄부터 한 줄에 하나씩 각 학생의 이름, 국어, 영어, 수학 점수가 공백으로 구분해 주어진다. 점수는 1보다 크거나 같고, 100보다 작거나 같은 자연수이다. 이름은 알파벳 대소문자로 이루어진 문자열이고, 길이는 10자리를 넘지 않는다.

출력
문제에 나와있는 정렬 기준으로 정렬한 후 첫째 줄부터 N개의 줄에 걸쳐 각 학생의 이름을 출력한다.

예제 입력 1
12
Junkyu 50 60 100
Sangkeun 80 60 50
Sunyoung 80 70 100
Soong 50 60 90
Haebin 50 60 100
Kangsoo 60 80 100
Donghyuk 80 60 100
Sei 70 70 70
Wonseob 70 70 90
Sanghyun 70 70 80
nsj 80 80 80
Taewhan 50 60 90
예제 출력 1
Donghyuk
Sangkeun
Sunyoung
nsj
Wonseob
Sanghyun
Sei
Kangsoo
Haebin
Junkyu
Soong
Taewhan
출처
문제를 만든 사람: baekjoon
데이터를 추가한 사람: alssel2525
빠진 조건을 찾은 사람: djm03178
알고리즘 분류
정렬
 */
/*
알고리즘 핵심
정렬
1. 학생 클래스를 만들고 이름,국어,영어,수학 점수를 받는다.
2. 학생 클래스의 정렬 기준을 정하기 위해 Comparable 인터페이스를 구현하여 조건에 맞게 정렬 기준을 설정한다.
3. Collection.sort()를 통해 CompareTo() 메서드를 통해 정렬을 수행한다.
 */
public class BOJ10825 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;

    public static void main(String[] args) throws IOException {
        Solve task = new Solve();
        task.solve();
    }

    private static class Solve {
        private class Student implements Comparable<Student> {
            String name;
            int korean,english,math;

            Student(String name, int kor, int eng, int math) {
                this.name = name;
                this.korean = kor;
                this.english = eng;
                this.math = math;
            }

            @Override
            public int compareTo(Student o) {
                int kor = this.korean - o.korean;
                int eng = this.english - o.english;
                int math = this.math - o.math;
                int name = this.name.compareTo(o.name);

                if(kor == 0) {
                    if(eng == 0) {
                        if(math == 0) return name;
                        else return -(math);
                    } else return eng;
                } else return -(kor);
            }
        }

        private ArrayList<Student> students;
        private StringBuilder ans;

        void solve() throws IOException {
            init_setting();

            sorting();

            print();
        }

        private void sorting() {
            Collections.sort(students);
        }

        private void print() {
            for(Student s : students) {
                ans.append(s.name).append("\n");
            }

            System.out.println(ans.toString());
        }

        private void init_setting() throws IOException {
            N = Integer.parseInt(br.readLine());

            students = new ArrayList<>();

            for(int i = 0; i < N; i++) {
                String[] input = br.readLine().split(" ");

                String name = input[0];
                int kor = Integer.parseInt(input[1]);
                int eng = Integer.parseInt(input[2]);
                int math = Integer.parseInt(input[3]);

                students.add(new Student(name, kor, eng, math));
            }

            ans = new StringBuilder();
        }
    }
}
