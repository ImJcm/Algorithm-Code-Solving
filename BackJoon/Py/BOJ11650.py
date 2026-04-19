'''
문제
2차원 평면 위의 점 N개가 주어진다. 좌표를 x좌표가 증가하는 순으로, x좌표가 같으면 y좌표가 증가하는 순서로 정렬한 다음 출력하는 프로그램을 작성하시오.

입력
첫째 줄에 점의 개수 N (1 ≤ N ≤ 100,000)이 주어진다. 둘째 줄부터 N개의 줄에는 i번점의 위치 xi와 yi가 주어진다. (-100,000 ≤ xi, yi ≤ 100,000) 좌표는 항상 정수이고, 위치가 같은 두 점은 없다.

출력
첫째 줄부터 N개의 줄에 점을 정렬한 결과를 출력한다.

예제 입력 1
5
3 4
1 1
1 -1
2 2
3 3
예제 출력 1
1 -1
1 1
2 2
3 3
3 4
'''
import sys
N = int(sys.stdin.readline())
lt = []
for i in range(N):
    lt.append(list(map(int,sys.stdin.readline().split())))
lt.sort(key=lambda x:(x[0],x[1]))
for l in lt:
    print("%d %d" %(l[0], l[1]))

#--------------------------------------------------------------
"""
#timeover
N = int(input())
lt = []
#lt = [list(map(int,input().split())) for _ in range(N)]
#lt = [input().split() for _ in range(N)]
for _ in range(N):
    temp_lt = [int(x) for x in input().split()]
    if not lt:
        lt.append(temp_lt)
    else:
        for i, l in enumerate(lt):
            if l[0] > temp_lt[0]:
                lt.insert(i, temp_lt)
                break
            elif l[0] == temp_lt[0]:
                if l[1] > temp_lt[1]:
                    lt.insert(i, temp_lt)
                    break
                else:
                    lt.insert(i+1, temp_lt)
                    break
            else:   #l[0] < temp_lt[0]
                if i == len(lt)-1:
                    lt.append(temp_lt)

#print(lt)
for l in lt:
    print("%d %d" %(l[0], l[1]))
"""