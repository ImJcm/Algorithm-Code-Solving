'''
문제
지민이는 자신의 저택에서 MN개의 단위 정사각형으로 나누어져 있는 M*N 크기의 보드를 찾았다. 어떤 정사각형은 검은색으로 칠해져 있고, 나머지는 흰색으로 칠해져 있다. 지민이는 이 보드를 잘라서 8*8 크기의 체스판으로 만들려고 한다.

체스판은 검은색과 흰색이 번갈아서 칠해져 있어야 한다. 구체적으로, 각 칸이 검은색과 흰색 중 하나로 색칠되어 있고, 변을 공유하는 두 개의 사각형은 다른 색으로 칠해져 있어야 한다. 따라서 이 정의를 따르면 체스판을 색칠하는 경우는 두 가지뿐이다. 하나는 맨 왼쪽 위 칸이 흰색인 경우, 하나는 검은색인 경우이다.

보드가 체스판처럼 칠해져 있다는 보장이 없어서, 지민이는 8*8 크기의 체스판으로 잘라낸 후에 몇 개의 정사각형을 다시 칠해야겠다고 생각했다. 당연히 8*8 크기는 아무데서나 골라도 된다. 지민이가 다시 칠해야 하는 정사각형의 최소 개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N과 M이 주어진다. N과 M은 8보다 크거나 같고, 50보다 작거나 같은 자연수이다. 둘째 줄부터 N개의 줄에는 보드의 각 행의 상태가 주어진다. B는 검은색이며, W는 흰색이다.

출력
첫째 줄에 지민이가 다시 칠해야 하는 정사각형 개수의 최솟값을 출력한다.

예제 입력 1         예제 입력 2
8 8                 10 13
WBWBWBWB            BBBBBBBBWBWBW
BWBWBWBW            BBBBBBBBBWBWB
WBWBWBWB            BBBBBBBBWBWBW
BWBBBWBW            BBBBBBBBBWBWB
WBWBWBWB            BBBBBBBBWBWBW
BWBWBWBW            BBBBBBBBBWBWB
WBWBWBWB            BBBBBBBBWBWBW
BWBWBWBW            BBBBBBBBBWBWB
예제출력1            WWWWWWWWWWBWB
1                   WWWWWWWWWWBWB
                    예제 출력 2
                    12
'''
#처음에는 kmp 알고리즘으로 풀 생각이였지만 해당 알고리즘이 패턴이 일치하는 것을 찾는 것이 아닌
#패턴에 맞지않는 최소개수를 찾는 것이므로 부적합하였다. 그래서 bruteForce로 진행
import sys
r,c = map(int, input().split())
chess_f_b = ['B','W','B','W','B','W','B','W']
chess_f_w = ['W','B','W','B','W','B','W','B']
count = float('inf')

chess_lt = [list(input()) for _ in range(r)]

def recursive_check(chess_list,r,c,n,count,check_list):
    if n == 8:      #n을 통해 row값을 증가시켜나간다.
        return count

    for i in range(8):      #8열을 check리스트와 비교하여 바꿔야할 문자를 count
        if chess_list[r+n][c+i] != check_list[i]:
            count += 1
    if check_list[0] == 'B':        #해당 재귀에서 check의 첫문자가 B라면,
        return recursive_check(chess_list,r,c,n+1,count,chess_f_w) #다음 재귀는 W
    else:
        return recursive_check(chess_list,r,c,n+1,count,chess_f_b) #다음 재귀는 B

for row in range(r-7):  #8*8만 비교하므로 입력 row에서 -7을 뺀 지점까지만 for
    for col in range(c-7):
        #0~row(r-7)까지 + 0~col(c-7)까지 [row][col]좌표를 시작지점으로 수정해야할
        #정사각형의 개수를 계산하는 알고리즘
        countW = recursive_check(chess_lt,row,col,0,0,chess_f_w)
        countB = recursive_check(chess_lt,row,col,0,0,chess_f_b)

        if countW < countB and count > countW:
            count = countW
        elif countB <= countW and count > countB:
            count = countB

print(count)


