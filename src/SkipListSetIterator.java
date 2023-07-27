import java.util.Iterator;

/**
 * @author Gradi Tshielekeja Mbuyi
 * @version 1.0
 * @since July 27, 2023
 * @param <T>
 */
public class SkipListSetIterator <T extends Comparable<T>> implements Iterator<T> {
    private SkipListSet<T> skipListSet;
    private SkipListSetItem<T> current;

    /**
     *
     * @param skipListSet
     */
    public SkipListSetIterator(SkipListSet<T> skipListSet) {
        this.skipListSet = skipListSet;
        this.current = skipListSet.getHead();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean hasNext() {
        return current.next != null;
    }

    /**
     *
     * @return
     */
    @Override
    public T next() {
        current = current.next;
        return current.value;
    }

    /**
     *
     */
    @Override
    public void remove() {
        skipListSet.remove(current.value);
    }

}