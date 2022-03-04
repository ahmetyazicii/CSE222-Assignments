import java.util.Iterator;

public interface NavigableSetSL<E extends Comparable<? super E>> {

    boolean insert(E item);
    boolean delete(E target);
    Iterator<E> descendingIterator();

}