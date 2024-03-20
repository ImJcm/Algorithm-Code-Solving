'''
문제
자연수 N과 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.

1부터 N까지 자연수 중에서 중복 없이 M개를 고른 수열
고른 수열은 오름차순이어야 한다.
입력
첫째 줄에 자연수 N과 M이 주어진다. (1 ≤ M ≤ N ≤ 8)

출력
한 줄에 하나씩 문제의 조건을 만족하는 수열을 출력한다. 중복되는 수열을 여러 번 출력하면 안되며, 각 수열은 공백으로 구분해서 출력해야 한다.

수열은 사전 순으로 증가하는 순서로 출력해야 한다.

예제 입력 1
3 1
예제 출력 1
1
2
3
예제 입력 2
4 2
예제 출력 2
1 2
1 3
1 4
2 3
2 4
3 4
예제 입력 3
4 4
예제 출력 3
1 2 3 4
'''
"""
import sys
from itertools import combinations

N,M = map(int,sys.stdin.readline().split())
lt = [int(x+1) for x in range(N)]
answer = list(combinations(lt, M))

for l in answer:
    for s in range(M):
        print(l[s], end=" ")
    print()
"""
import sys
N,M = map(int,sys.stdin.readline().split())

lt = [int(x+1) for x in range(N)]

def make_N_M_combination(lt, size):
    lt = sorted(lt)
    used = [0 for _ in range(len(lt))]

    def generate(chosen):
        if len(chosen) == size:
            for l in chosen:
                print(l, end=" ")
            print()
            return

        start = lt.index(chosen[-1]) + 1 if chosen else 0
        #chosen에서 마지막으로 선택된 숫자 이후의 값을 조합하기 위해, start로 시작위치 조정
        #ex) 첫번째 값으로 3이 선택됬다면, 이전에 [1,3] == [3,1]이므로, start가 2(3 index) + 1 = 3부터 시작한다.
        #ex+) 3,4를 조합한 값이 출력된다. 4로 시작하는 경우, for nxt in range(4,4)이기 때문에, 종료
        for nxt in range(start, len(lt)):
            if not used[nxt] and (nxt == 0 or lt[nxt-1] != lt[nxt] or used[nxt-1]):
                chosen.append(lt[nxt])
                used[nxt] = 1
                generate(chosen)
                chosen.pop()
                used[nxt] = 0
    generate([])

if __name__ == "__main__":
    make_N_M_combination(lt,M)

""" 
다음과 같은 조건을 만족하면 순열에서 중복을 피할 수 있다. = 조합 만족
i 가 0일 때:
    i 가 0이면 배열의 첫 원소이기 때문에 바로 쓰면 된다.
arr[i-1] != arr[i] 일 때:
    지금은 arr 이 정렬되어 있다. 이때 i 번째 원소가 i-1 번째와 다르면 그냥 ‘B’, ‘C’ 처럼 서로 다른 원소이기 때문에 바로 쓴다.
used[i-1] 일 때:
    가령 i 번째 원소가 두 번째 ‘A’이면, 중복을 피하기 위해 첫 번째 ‘A’가 사용된 상태여야만 쓸 수 있다. 그래서 i-1 번째 원소가 쓰인 상태인지 확인한다.
"""