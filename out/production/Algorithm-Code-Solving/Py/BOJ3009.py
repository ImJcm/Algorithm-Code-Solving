'''
세 점이 주어졌을 때, 축에 평행한 직사각형을 만들기 위해서 필요한 네 번째 점을 찾는 프로그램을 작성하시오.

Input
세 점의 좌표가 한 줄에 하나씩 주어진다. 좌표는 1보다 크거나 같고, 1000보다 작거나 같은 정수이다.

Output
직사각형의 네 번째 점의 좌표를 출력한다.

ex)
30 20
10 10
10 20

output
30 10
'''

#xor 연산은 교환법칙이 성립하여 나머지 점을 구한다.
#x=y=a=b = 0
#
#for i in range(3):
#    a,b = map(int,input().split())
#    x^=a
#    y^=b
#print(x,y)
x_dic = dict()
y_dic = dict()

for i in range(3):
    x,y = map(int,input().split())
    if not x in x_dic:
        x_dic[x]=1
    else:
        x_dic[x]+=1
    if not y in y_dic:
        y_dic[y]=1
    else:
        y_dic[y]+=1

print([key for key,value in x_dic.items() if value == 1][0],[key for key,value in y_dic.items() if value == 1][0])
#for key,value in x_dic.items():
#    if value == 1:
#        print(key," ")
#for key,value in y_dic.items():
#    if value == 1:
#        print(key)

