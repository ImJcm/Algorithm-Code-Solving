package AlgorithmData_for_BOJ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

//카라츠바 알고리즘 - 정수 곱셈 알고리즘
public class Karatsuba {
    static long karatsuba(long x, long y) {
        if(x < 10 && y < 10) return x * y;

        int noOneLength = numLength(x);
        int noTwoLength = numLength(y);

        int maxNumLength = Math.max(noOneLength, noTwoLength);

        Integer halfMaxNumLength = (maxNumLength / 2) + (maxNumLength % 2);

        long maxNumLengthTen = (long)Math.pow(10,halfMaxNumLength);

        long a = x / maxNumLengthTen;
        long b = x % maxNumLengthTen;
        long c = y / maxNumLengthTen;
        long d = y % maxNumLengthTen;

        long z0 = karatsuba(a,c);
        long z1 = karatsuba(a+b, c+d);
        long z2 = karatsuba(b,d);

        long ans = (z0 * (long)Math.pow(10,halfMaxNumLength *2) +
                    ((z1 - z0 - z2) * (long)Math.pow(10,halfMaxNumLength) + z2));

        return ans;
    }

    private static int numLength(long n) {
        int noLen = 0;
        while(n > 0) {
            noLen++;
            n /= 10;
        }
        return noLen;
    }
    //karatsuba 알고리즘 - Array로 받는 경우.
    private static void nomerlize(ArrayList<Integer> num) {
        num.add(0);

        for(int i=0;i+1<num.size();++i) {
            if(num.get(i) < 0) {
                int borrow = (Math.abs(num.get(i) + 9) / 10);
                num.set(i+1,num.get(i+1) - borrow);
                num.set(i, num.get(i) + borrow *10);
            }
            else {
                num.set(i+1, num.get(i+1) + num.get(i) / 10);
                num.set(i,num.get(i) % 10);
            }
        }
        while(num.size() > 1 && num.get(num.size()-1) == 0) num.remove(num.size()-1);
    }

    static ArrayList<Integer> multiply(ArrayList<Integer> a, ArrayList<Integer> b) {
        ArrayList<Integer> c = new ArrayList<>(a.size()+b.size()+1);
        for(int i=0;i<a.size();i++) {
            for(int j =0; j < b.size(); j++) {
                c.set(i+j,c.get(i+j) + a.get(i) + b.get(i));
            }
        }
        nomerlize(c);
        return c;
    }

    public static ArrayList<Integer> karatsuba_List(ArrayList<Integer> a, ArrayList<Integer> b) {
        int an = a.size(), bn = b.size();
        if(an < bn) return karatsuba_List(b,a);
        if(an == 0 || bn == 0) return new ArrayList<Integer>();
        if(an <= 50) return multiply(a,b);
        int half = an / 2;

        ArrayList<Integer> a0 = new ArrayList<Integer>(a.subList(0, half));
        ArrayList<Integer> a1 = new ArrayList<Integer>(a.subList(half, a.size()));
        ArrayList<Integer> b0 = new ArrayList<Integer>(b.subList(0, Math.min(b.size(),half)));
        ArrayList<Integer> b1 = new ArrayList<Integer>(b.subList(Math.min(b.size(), half),b.size()));

        ArrayList<Integer> z2 = karatsuba_List(a1,b1);
        ArrayList<Integer> z0 = karatsuba_List(a0,b0);
        addTo(a0,a1,0);
        addTo(b0,b1,0);

        ArrayList<Integer> z1 = karatsuba_List(a0,b0);
        subFrom(z1,z0);
        subFrom(z1,z2);

        ArrayList<Integer> ret = new ArrayList<Integer>(0);
        addTo(ret, z0,0);
        addTo(ret, z1, half);
        addTo(ret, z2, half+half);

        return ret;
    }

    private static void addTo(ArrayList<Integer> a, ArrayList<Integer> b, int k) {
        int[] t_a = a.stream().mapToInt(Integer::intValue).toArray();
        int[] t_b = b.stream().mapToInt(Integer::intValue).toArray();

        long a_n = arrayToint(t_a);
        long b_n = arrayToint(t_b);
        long r = (long)(a_n + b_n * Math.pow(10,k));

        int[] t = intToarray(r);
        a = new ArrayList<Integer>(Arrays.stream(t).boxed().collect(Collectors.toList()));
    }

    private static void subFrom(ArrayList<Integer> a, ArrayList<Integer> b) {
        //a >= b로 가정
        int[] t_a = a.stream().mapToInt(Integer::intValue).toArray();
        int[] t_b = b.stream().mapToInt(Integer::intValue).toArray();

        long a_n = arrayToint(t_a);
        long b_n = arrayToint(t_b);
        long r = (long)(a_n - b_n);

        int[] t = intToarray(r);
        a = new ArrayList<Integer>(Arrays.stream(t).boxed().collect(Collectors.toList()));
    }

    private static long arrayToint(int[] a) {
        StringBuilder sb = new StringBuilder();
        for(int n : a) {
            sb.append(n);
        }
        return (long)Integer.parseInt(sb.toString());
    }

    private static int[] intToarray(long a) {
        String tmps = Long.toString(a);
        int[] tmp = new int[tmps.length()];
        for(int i=0;i<tmps.length();i++) {
            tmp[i] = tmps.charAt(i) - '0';
        }
        return tmp;
    }
}
