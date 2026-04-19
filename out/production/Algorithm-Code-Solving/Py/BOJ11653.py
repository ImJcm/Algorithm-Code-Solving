'''
문제
정수 N이 주어졌을 때, 소인수분해하는 프로그램을 작성하시오.

입력
첫째 줄에 정수 N (1 ≤ N ≤ 10,000,000)이 주어진다.

출력
N의 소인수분해 결과를 한 줄에 하나씩 오름차순으로 출력한다. N이 1인 경우 아무것도 출력하지 않는다.

예제 입력 1             예제 출력 1
72                      2
                        2
                        2
                        3
                        3
예제 입력 2             예제 출력 2
3                       3
예제 입력 3             예제 출력 3
6                       2
                        3
예제 입력 4             예제 출력 4
2                       2
예제 입력 5             예제 출력 5
9991                    97
                        103
'''
import sys

N = int(sys.stdin.readline())
ans = []

num = 2
while N != 1:
    if N % num == 0:
        ans.append(num)
        N //= num
    else:
        num += 1

for i in ans:
    print(i)
"""
# 채점시간이 느리다..... 아마도, prime_list를 만드는 과정에서 많은 시간이 걸리는 것으로 추정
#prime 리스트를 만들고, 인수를 검색하는 방법
import sys
import math

#N+1개의 list를 만든 뒤, index번호 별로 해당 index의 값이 prime인지 아닌지를 True와 False로
#구분한 뒤, prime에 해당하는 index를 True로 남기고, 마지막에 True인 index만 return
def getPrimaryNum_Gen(N):
    nums = [True] * (N+1)
    for i in range(2, len(nums)//2 + 1):
        if nums[i] == True:
            for j in range(i+i, N, i):
                nums[j] = False
    return [i for i in range(2,N) if nums[i] == True]

if __name__ == "__main__":
    N = int(sys.stdin.readline())
    prime_list = getPrimaryNum_Gen(N+1)

    if N in prime_list:
        print(N)
    else:
        result = []
        for prime in prime_list:
            if N % prime == 0:
                while (N % prime == 0):
                    result.append(prime)
                    N = N // prime

        for num in result:
            print(num)
"""

'''
#시간 초과
import sys
import math
N = int(sys.stdin.readline())

def isPrime(num):
    if num < 2: return False
    if num == 2: return True
    if num % 2 == 0: return False
    i=3
    while (i*i) <= num:
        if num % i == 0: return False
        i += 2
    return True

prime_list = []
if N == 1:
    print()
else:
    #prime
    for i in range(2,N+1):
        if isPrime(int(i)):
            prime_list.append(i)
    for p in prime_list:
        p = int(p)
        if N % p == 0:
            while N % p == 0:
                print(p)
                N = N // p
'''