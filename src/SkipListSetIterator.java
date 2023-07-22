import java.util.Iterator;

public class SkipListSetIterator <T extends Comparable<T>> implements Iterator<T> {
    public SkipListSet<T> skipListSet;
    public SkipListSetItem<T> current;
    public Integer height;

    public SkipListSetIterator(SkipListSet<T> skipListSet) {
        this.skipListSet = skipListSet;
        this.current = skipListSet.getHead();
        this.height = 1;
    }

    @Override
    public boolean hasNext() {
        return current.next != null;
    }

    @Override
    public T next() {
        current = current.next;
        return current.value;
    }

    @Override
    public void remove() {
        skipListSet.remove(current.value);
    }

}