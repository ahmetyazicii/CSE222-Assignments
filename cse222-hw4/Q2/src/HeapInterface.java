public interface HeapInterface<E> {

    boolean insert(E item);
    E remove();
    boolean search(E item);
    void merge(HeapInterface<E> other);
    E removeILargest(int ith);
    void show();
    HeapIterator<E> iterator();
    int size();
    E peek();

}
