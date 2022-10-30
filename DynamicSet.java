public interface DynamicSet {
    int size();

    boolean contains(Integer x);

    boolean add(Integer x);

    boolean remove(Integer x);

    public interface PrintableNode {
        String getValueAsString();

        PrintableNode getLeft();

        PrintableNode getRight();
    }
}
