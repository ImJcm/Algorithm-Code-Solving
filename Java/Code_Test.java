import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

class Parent {
    int age;

    Parent() {}

    Parent(int age) {
        this.age = age;
    }

    void printInfo() {
        System.out.println("Parent Call!!!!");
    }
}

class Child extends Parent {
    String name;

    Child() {}

    Child(int age, String name) {
        super(age);
        this.name = name;
    }

    @Override
    void printInfo() {
        System.out.println("Child Call!!!!");
    }
}

class Trie {
    boolean end;
    boolean pass;
    Trie[] child;

    Trie() {
        end = false;
        pass = false;
        child = new Trie[25];
    }

    public boolean insert(String str, int idx) {
        if(end) return false;

        if(idx == str.length()) {
            end = true;
            if(pass) return false;
            else return true;
        } else {
            int next = str.charAt(idx) - 'a';
            if(child[next] == null) {
                child[next] = new Trie();
                pass = true;
            }
            return child[next].insert(str, idx+1);
        }
    }
}

class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}

class Rotate_case {

    public void rearrange_arr() {
        int[] arr = {-1, -1, 6, 1, 9, 3, 2, -1, 4, -1};

        for(int i = 0; i < arr.length; i++) {
            if(arr[i] != -1 && arr[i] != i) {
                int x = arr[i];

                while(arr[x] != -1 && arr[x] != x) {
                    int y = arr[x];
                    arr[x] = x;
                    x = y;
                }

                arr[x] = x;

                if(arr[i] != i) {
                    arr[i] = -1;
                }
            }
        }
    }

    public void sum_subsequence_arr() {
        int[] arr = {1,20,2,10};
        int size = arr.length;

        int arrSum = Arrays.stream(arr).sum();
        int curSum = IntStream.range(0,size)
                .map(i -> i * arr[i])
                .sum();

        int maxSum = curSum;

        /*
            R_i - R_i-1 = arrSum - size * arr[size - i]
            R_i = R_i-1 + arrSum - size * arr[size - i]
            arrSum(init) = R_0
            R_1 = curSum = curSum(R_0) + arrSum - size * arr[size - 1];
            R_2 = curSum(R_1) + arrSum - size * arr[size - 2];
            ...
         */
        for (int j = 1; j < size; j++){
            curSum += arrSum - size * arr[size - j];

            if ( curSum > maxSum )
                maxSum = curSum;
        }

        System.out.println(maxSum);
    }

    public void left_rotate_original(int[] arr, int cnt) {
        for(int c = 0; c < cnt; c++) {
            int temp = arr[0];
            for(int i = 0; i < arr.length - 1; i++) {
                arr[i] = arr[i + 1];
            }
            arr[arr.length - 1] = temp;
        }
    }

    public void left_rotate_gcd(int[] arr, int d, int n) {
        for (int i = 0; i < gcd(d, n); i++) {

            int temp = arr[i];
            int j = i;

            while (true) {
                int k = j + d;
                if (k >= n)
                    k = k - n;

                if (k == i)
                    break;

                arr[j] = arr[k];
                j = k;
            }
            arr[j] = temp;
        }
    }

    public int gcd(int a, int b) {
        if (b == 0) return a;
        else return gcd(b, a % b);
    }

    public void left_rotate_reverse(int[] arr, int d, int n) {
        reverseArr(arr,0,d-1);
        reverseArr(arr,d,n-1);
        reverseArr(arr,0,n-1);
    }

    public void reverseArr(int[] arr, int s, int e) {
        while(s < e) {
            int temp = arr[s];
            arr[s] = arr[e];
            arr[e] = temp;

            s++;
            e--;
        }
    }
}

class Pre_in_post_order {
    public static class Node {
        int data;
        Node left,right;

        Node(int d, Node l, Node r) {
            this.data = d;
            this.left = l;
            this.right = r;
        }
    }

    public void pre_order(Node node) {
        if(node == null) return;

        System.out.print(node.data + "-");
        pre_order(node.left);
        pre_order(node.right);
    }

    public void in_order(Node node) {
        if(node == null) return;

        in_order(node.left);
        System.out.print(node.data + "-");
        in_order(node.right);
    }

    public void post_order(Node node) {
        if(node == null) return;

        post_order(node.left);
        post_order(node.right);
        System.out.print(node.data + "-");
    }

    private void level_order(Node node) {
        // 인덱스 위치로 2^level 만큼 출력하면 된다.
        // 배열 : 인덱스 위치 정보 활용
        // 연결 리스트 : root 부터 연결된 자식을 별도의 저장공간에 저장하여 레벨 노드를 분리한다.
    }
}

class Binary_search_tree_class {
    public static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.setData(data);
            setLeft(null);
            setRight(null);
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }

    public Node root;
    public Binary_search_tree_class() {
        this.root = null;
    }

    //탐색 연산
    public boolean find(int id){
        Node current = root;
        while(current!=null){
            //현재 노드와 찾는 값이 같으면
            if(current.getData()==id){
                return true;
                //찾는 값이 현재 노드보다 작으면
            } else if(current.getData()>id){
                current = current.getLeft();
                //찾는 값이 현재 노드보다 크면
            } else{
                current = current.getRight();
            }
        }
        return false;
    }
    //삭제 연산
    public boolean delete(int id){
        Node parent = root;
        Node current = root;
        boolean isLeftChild = false;
        while(current.getData()!=id){
            parent = current;
            if(current.getData()>id){
                isLeftChild = true;
                current = current.getLeft();
            }else{
                isLeftChild = false;
                current = current.getRight();
            }
            if(current==null){
                return false;
            }
        }
        //Case 1: 자식노드가 없는 경우
        if(current.getLeft()==null && current.getRight()==null){
            if(current==root){
                root = null;
            }
            if(isLeftChild==true){
                parent.setLeft(null);
            }else{
                parent.setRight(null);
            }
        }
        //Case 2 : 하나의 자식을 갖는 경우
        else if(current.getRight()==null){
            if(current==root){
                root = current.getLeft();
            }else if(isLeftChild){
                parent.setLeft(current.getLeft());
            }else{
                parent.setRight(current.getLeft());
            }
        } else if(current.getLeft()==null){
            if(current==root){
                root = current.getRight();
            }else if(isLeftChild){
                parent.setLeft(current.getRight());
            }else{
                parent.setRight(current.getRight());
            }
        }
        //Case 3 : 두개의 자식을 갖는 경우
        else if(current.getLeft()!=null && current.getRight()!=null){
            // 오른쪽 서브트리의 최소값을 찾음
            Node successor = getSuccessor(current);
            if(current==root){
                root = successor;
            }else if(isLeftChild){
                parent.setLeft(successor);
            }else{
                parent.setRight(successor);
            }
            successor.setLeft(current.getLeft());
        }
        return true;
    }

    public Node getSuccessor(Node deleleNode){
        Node successsor =null;
        Node successsorParent =null;
        Node current = deleleNode.getRight();
        while(current!=null){
            successsorParent = successsor;
            successsor = current;
            current = current.getLeft();
        }
        if(successsor!=deleleNode.getRight()){
            successsorParent.setLeft(successsor.getRight());
            successsor.setRight(deleleNode.getRight());
        }
        return successsor;
    }

    //삽입 연산
    public void insert(int id){
        Node newNode = new Node(id);
        if(root==null){
            root = newNode;
            return;
        }
        Node current = root;
        Node parent = null;
        while(true){
            parent = current;
            if(id < current.getData()){
                current = current.getLeft();
                if(current==null){
                    parent.setLeft(newNode);
                    return;
                }
            }else{
                current = current.getRight();
                if(current==null){
                    parent.setRight(newNode);
                    return;
                }
            }
        }
    }

    public void display(Node root){
        if(root!=null){
            display(root.getLeft());
            System.out.print(" " + root.getData());
            display(root.getRight());
        }
    }

    public void deleteAll(Node node) {
        if(node == null) return;

        deleteAll(node.left);
        deleteAll(node.right);

        node.left = null;
        node.right = null;
        if(node == root) root = null;
    }
}

public class Code_Test {
    public static void main_1() {
        Parent p = new Child();

        Child c = new Child();

        //Child cc = (Child) p;

        ((Child) p).name = "name";

        System.out.println(((Child) p).name);
    }
    public static void main_2() {
        Trie trie = new Trie();
        String[] str = {"bear","bell","bid","bull","buy","sell","stock","stop"};

        for(int i = 0; i < str.length; i++) {
            trie.insert(str[i],0);
        }
    }
    public static void main_3() {
        int[] numbers = {5, 3, 8, 1, 2};

        // 내림차순 정렬
        int[] sortedNumbers = Arrays.stream(numbers)   // 배열을 Stream으로 변환
                .boxed()           // int -> Integer 객체로 변환
                //.sorted((a, b) -> b - a)  // 내림차순 정렬
                .sorted(Comparator.comparingInt(Integer::intValue).reversed())
                .mapToInt(Integer::intValue)  // 다시 int로 변환
                .toArray();       // 다시 배열로 변환

        // 기본 타입 배열을 Integer 객체 배열로 변환 후 내림차순 정렬
        Integer[] boxedNumbers = Arrays.stream(numbers)  // 기본 int[]를 스트림으로 변환
                .boxed()           // Integer 객체로 변환
                .sorted(Comparator.reverseOrder())  // 내림차순 정렬
                .toArray(Integer[]::new);  // 다시 Integer 배열로 변환

        // 기본 타입 배열을 Integer 객체 배열로 변환 후 내림차순 정렬
        int[] boxedNumberss = Arrays.stream(numbers)  // 기본 int[]를 스트림으로 변환
                .boxed()           // Integer 객체로 변환
                .sorted(Comparator.reverseOrder())  // 내림차순 정렬
                .mapToInt(Integer::intValue)
                .toArray();  // 다시 Integer 배열로 변환

        System.out.println(Arrays.toString(boxedNumbers));  // [8, 5, 3, 2, 1]

        System.out.println(Arrays.toString(boxedNumbers));  // [8, 5, 3, 2, 1]

        System.out.println(Arrays.toString(sortedNumbers));  // [8, 5, 3, 2, 1]
    }
    public static void main_4() {
        Stack<Integer> stack = new Stack<>();
        Queue<Integer> queue = new LinkedList<>();
        int[] arr = {1,2,3,4,5,6,7};
        int size = arr.length;
        Rotate_case rotateCase = new Rotate_case();

        System.out.println(size);

        rotateCase.sum_subsequence_arr();

        //left_rotate_original(arr, 2);
        rotateCase.left_rotate_gcd(arr,2, size);

        Arrays.stream(arr).forEach(i -> System.out.print(i + " "));

        queue.add(1);
    }
    public static void main_5() {
        Pre_in_post_order pre_in_post_order = new Pre_in_post_order();

        Pre_in_post_order.Node[] nodes = new Pre_in_post_order.Node[11];

        for(int i = 1; i < 11; i++) {
            nodes[i] = new Pre_in_post_order.Node(i,null,null);

            if(i / 2 > 0) {
                if(i % 2 == 0) nodes[i / 2].left = nodes[i];
                else nodes[i / 2].right = nodes[i];
            }
        }

        Pre_in_post_order.Node root = nodes[1];

        pre_in_post_order.pre_order(root);
        System.out.println();

        pre_in_post_order.in_order(root);
        System.out.println();

        pre_in_post_order.post_order(root);
    }
    public static void main_6() {
        Binary_search_tree_class b = new Binary_search_tree_class();
        //트리에 노드를 삽입
        b.insert(3);b.insert(8);
        b.insert(1);b.insert(4);b.insert(6);b.insert(2);b.insert(10);b.insert(9);
        b.insert(20);b.insert(25);b.insert(15);b.insert(16);

        System.out.println("트리삽입 결과 : ");
        b.display(b.root);
        System.out.println("");
        System.out.println("이진트리에서 4를 탐색 : " + b.find(4));
        System.out.println("이진트리에서 2를 삭제 : " + b.delete(2));
        b.display(b.root);
        System.out.println("\n이진트리에서 4를 삭제 : " + b.delete(4));
        b.display(b.root);
        System.out.println("\n이진트리에서 10을 삭제 : " + b.delete(10));
        b.display(b.root);

        b.deleteAll(b.root);
    }
    public static void main_7() {
        double d1 = 3.14;
        double d2 = 1.59;

        System.out.printf("%.1f",d1);
        System.out.printf("%.2f",d2);
    }

    public static void main(String[] args) throws IOException {
        //main_1();
        //main_2();
        //main_3();
        //main_4();
        //main_5();
        //main_6();
    }
}