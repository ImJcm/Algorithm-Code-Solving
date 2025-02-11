package BackJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
IP 주소 다국어
시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2 초	128 MB	2535	809	605	32.164%
문제
네트워크에 연결되어 있는 컴퓨터들은 각각 하나의 IP 주소를 갖게 된다. 그리고 이러한 IP 주소를 갖는 컴퓨터들이 여러 개 모여서 하나의 IP 네트워크를 구성하게 된다. IP 네트워크는 ‘네트워크 주소’와 ‘네트워크 마스크’라는 두 개의 정보로 표현된다.

IP 주소는 네 개의 바이트로 구성되어 있으며, 각각을 10진수로 나타내고(앞에 0을 붙이지 않은 형태로) 사이에 점을 찍어 주소를 표현한다. 바이트이기 때문에 각각의 수는 0부터 255까지의 값을 갖게 된다. 네트워크 주소와 네트워크 마스크 역시 같은 형식으로 나타낸다.

IP 네트워크에 대해 올바르게 이해하기 위해서는 위와 같은 주소를 2진수로 이해하면 된다. 즉, 각각의 바이트를 8자리의 이진수로 나타내고, 이를 네 개 붙여놓은(앞에서부터) 32자리의 이진수를 생각해 보자. IP 네트워크에는 기본적으로 2m 개의 컴퓨터(혹은 IP 주소)가 할당될 수 있다. 이 경우의 네트워크 주소는 앞의 32-m 자리가 임의의 수(0 또는 1)로 구성되어 있고, 뒤의 m자리는 0으로 채워지게 된다. 네트워크 마스크는 앞의 32-m 자리가 1로 채워져 있고, 뒤의 m자리는 0으로 채워지게 된다. 이와 같은 IP 네트워크에는 앞의 32-m 자리가 네트워크 주소와 일치하는 모든 IP들이 포함되게 된다.

예를 들어 네트워크 주소가 194.85.160.176이고, 네트워크 마스크가 255.255.255.248인 경우를 생각해 보자. 이 경우, 이 네트워크에는 194.85.160.176부터 194.85.160.183까지의 8개의 IP 주소가 포함된다.

어떤 네트워크에 속해있는 IP 주소들이 주어졌을 때, 네트워크 주소와 네트워크 마스크를 구해내는 프로그램을 작성하시오. 답이 여러 개인 경우에는 가장 크기가 작은(포함되는 IP 주소가 가장 적은, 즉 m이 최소인) 네트워크를 구하도록 한다.

입력
첫째 줄에 정수 n(1 ≤ n ≤ 1,000)이 주어진다. 다음 n개의 줄에는 각 컴퓨터의 IP 주소가 주어진다.

출력
첫째 줄에 네트워크 주소를, 둘째 줄에 네트워크 마스크를 출력한다.

예제 입력 1
3
194.85.160.177
194.85.160.183
194.85.160.178
예제 출력 1
194.85.160.176
255.255.255.248
출처
ICPC > Regionals > Northern Eurasia > Northern Eurasia Finals > NEERC 2005 I번

데이터를 추가한 사람: sait2000
 */
/*
알고리즘 핵심
구현 + 비트마스킹
1. A.B.C.D와 같은 IP를 "."로 구분자를 설정하여 섹션을 나누어 계산할 수 있도록 한다.
2. 섹션을 나눈 정수값을 Integer.toBinaryString()을 통해 2진수 문자열로 나타내고, 앞자리의 0이 생략된 개수만큼 추가한다.
3. 각 8비트로 구성된 2진수 문자열에서 앞서 저장한 공통된 문자열에서 각 비트의 값과 비교하고 공통적으로 갖는 비트 문자열을 업데이트한다.
4. 8비트 문자열에서 공통된 비트를 비교하는 과정에서 왼쪽에서 오른쪽으로 진행하다가 같지 않은 문자가 최초로 발견되면 왼쪽 비트부터 공통된 비트의 개수와 해당 구역의 값이 변경되었다는 상태를 업데이트하고,
이후에 진행되는 비트는 비교 과정이 필요하지 않으므로 0을 추가하여 공통된 비트문자열을 업데이트하여 sub_bit_ip에 업데이트한다.
5. A,B,C,D의 모든 구역이 위 과정을 모두 마친 후, 네트워크 주소와 서브넷 마스크의 출력을 진행한다.
각 A,B,C,D의 구역마다 순서대로 진행하고, subnet[A] ~ [D]에서 true인 경우 주어진 IP의 네트워크의 값은 sub_bit_ip의 비트문자열을 2진수로 변환한 값을 의미하고, 서브넷 마스크는 255를 의미한다.
이때, false인 경우 8 - 해당 구역에서의 공통되는 비트 개수의 값을 2의 제곱값을 256에서 뺀 값을 서브넷 마스크를 의미하고, 네트워크 주소를 sub_bit_ip의 값을 2진수로 변환한 값을 의미한다.
subnet의 값중 최초로 false에 도달한 경우, 이후의 구역에서는 네트워크 대역의 IP 값을 의미하므로 더이상의 서브넷 마스크와 네트워크 주소를 구하여 출력하는 과정이 필요없기 때문에
0 값으로 서브넷 마스크와 네트워크 주소로 설정한다.
 */
public class BOJ2064 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static String[] ips;

    public static void main(String[] args) throws IOException {
        while(Integer.parseInt(br.readLine()) != 0) {
            init_setting();

            solve();
        }
    }

    private static void solve() {
        String[] sub_bit_ip = new String[] {"","","",""};
        boolean[] subnet = new boolean[] {true, true, true, true};
        int[] max_bit_ip = new int[] {8,8,8,8};

        for(String ip : ips) {
            Integer[] num_in_ip = Arrays.stream(ip.split("\\."))
                    .mapToInt(Integer::parseInt)
                    .boxed().toArray(Integer[]::new);

            for (int i = 0; i < 4; i++) {
                String binaryString = Integer.toBinaryString(num_in_ip[i]);

                int first_zero = 8 - binaryString.length();
                binaryString = "0".repeat(first_zero) + binaryString;

                if(sub_bit_ip[i].isEmpty()) sub_bit_ip[i] = binaryString;
                else {
                    String tmp = "";
                    boolean flag = true;
                    for(int j = 0; j < 8; j++) {
                        char ch = binaryString.charAt(j);
                        if(flag) {
                            if(sub_bit_ip[i].charAt(j) == ch) tmp += ch;
                            else {
                                flag = false;
                                subnet[i] = false;
                                max_bit_ip[i] = Math.min(max_bit_ip[i],j);
                                tmp += "0";
                            }
                        } else tmp += "0";
                    }
                    sub_bit_ip[i] = tmp;
                }
            }
        }

        StringBuilder sb_network_address = new StringBuilder();
        StringBuilder sb_subnetmask = new StringBuilder();

        boolean flag = true;

        for(int i = 0; i < 4; i++) {
            if(flag) {
                if(subnet[i]) {
                    sb_subnetmask.append("255");
                    sb_network_address.append(Integer.parseInt(sub_bit_ip[i], 2));
                } else {
                    int tmp = 256 - (int) Math.pow(2,8 - max_bit_ip[i]);
                    sb_subnetmask.append(tmp);
                    sb_network_address.append(Integer.parseInt(sub_bit_ip[i], 2));
                    flag = false;
                }
            } else {
                sb_subnetmask.append("0");
                sb_network_address.append("0");
            }

            if(i != 3) {
                sb_network_address.append(".");
                sb_subnetmask.append(".");
            }
        }

        System.out.println(sb_network_address.toString());
        System.out.println(sb_subnetmask.toString());
    }

    private static void init_setting() throws IOException {
        N = Integer.parseInt(br.readLine());

        ips = new String[N];

        for(int i = 0; i < N; i++) ips[i] = br.readLine();
    }
}
