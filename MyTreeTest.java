package cse214hw2;

public class MyTreeTest {
    public static void main(String[] args) {
        DynamicIntegerSet set = new DynamicIntegerSet();
        set.add(11);
        set.add(56);
        set.add(423);
        set.add(14);
        set.add(5);
        set.add(5);
        set.printTree(set.root());
    }
}
