'''
문제
한수는 지금 (x, y)에 있다. 직사각형의 왼쪽 아래 꼭짓점은 (0, 0)에 있고, 오른쪽 위 꼭짓점은 (w, h)
에 있다. 직사각형의 경계선까지 가는 거리의 최솟값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 x y w h가 주어진다. w와 h는 1,000보다 작거나 같은 자연수이고, x는 1보다 크거나 같고,
w-1보다 작거나 같은 자연수이고, y는 1보다 크거나 같고, h-1보다 작거나 같은 자연수이다.

ex) 6 2 10 3
output) 1
'''

x,y,w,h = map(int,input().split())
print(min(x,y,w-x,h-y))
'''
if x >= (w-h):
    min_r = (w-h)
else:
    mir_r = w

if y >= (h-y):
    min_c = (h-y)
else:
    min_c = y

if min_r >= min_c:
    print(min_c)
else:
    print(min_r)
'''