import math

N =int(input())

def prime_check(num):
   sq = int(math.sqrt(num))
   if num == 2:
       return True
   for n in range(2,sq+2):
       if num % n == 0:
           return False
       else:
           continue
   return True


while N > 0:
    num = int(input())
    dN = num//2
    if prime_check(dN):
        print(dN,dN)
    else:
        a_dN,b_dN = dN-1,dN+1
        while a_dN > 0 and b_dN > 0:
            if prime_check(a_dN) and prime_check(b_dN):
                print(a_dN,b_dN)
                break
            else:
                a_dN-=1
                b_dN+=1
    N-=1

