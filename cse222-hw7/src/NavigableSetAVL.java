import java.util.Iterator;

public interface NavigableSetAVL<E extends Comparable<? super E>> {

    boolean insert(E item);
    Iterator<E> iterator();
    NavigableSetAVL<E> headSet(E toElement,boolean inclusive);
    NavigableSetAVL<E> tailSet(E toElement,boolean inclusive);

}
