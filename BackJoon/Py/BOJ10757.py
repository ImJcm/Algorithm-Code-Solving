'''
문제
두 정수 A와 B를 입력받은 다음, A+B를 출력하는 프로그램을 작성하시오.

입력
첫째 줄에 A와 B가 주어진다. (0 < A,B < 1010000)

출력
첫째 줄에 A+B를 출력한다.

예제 입력 1
9223372036854775807 9223372036854775808
예제 출력 1
18446744073709551615
'''
"""
import sys
A,B = map(int, sys.stdin.readline().split())

print(A+B)
"""
#Py과 같이 값이 큰 정수에 한에서 연산법칙에 의해 만들어진 값을 수용할 type이 없을 경우,
import sys
A,B = map(list, sys.stdin.readline().split())

result = []
temp = 0
carry_bit = 0
if len(A) >= len(B):
    for a in reversed(A):
        temp = int(a) + int(B.pop() if B else 0) + (1 if carry_bit == 1 else 0)
        carry_bit = 0
        if temp >= 10:
            carry_bit = 1
        result.insert(0,temp if carry_bit == 0 else temp-10)
else:
    for b in reversed(B):
        temp = int(b) + int(A.pop() if A else 0) + (1 if carry_bit == 1 else 0)
        carry_bit = 0
        if temp >= 10:
            carry_bit = 1
        result.insert(0,temp if carry_bit == 0 else temp-10)

if carry_bit == 1:
    result.insert(0,1)
for n in result:
    print(n, end="")
