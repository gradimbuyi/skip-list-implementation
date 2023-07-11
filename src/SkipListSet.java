import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

public class SkipListSet <T extends  Comparable<T>> implements SortedSet<T> {

    private SkipListSetItem <T> head;
    private SkipListSetItem <T> tail;
    private SkipListSetIterator <T> iterator;

    private Integer height;
    private Integer size;

    public SkipListSet() {
        head = new SkipListSetItem<>();
        tail = new SkipListSetItem<>();
        iterator = new SkipListSetIterator<>();

        height = 0; size = 0;
    }

    public SkipListSet(SkipListSet <T> skipListSet) {
        head = skipListSet.head;
        tail = skipListSet.tail;
        height = skipListSet.height;
        size = skipListSet.size;
        iterator = skipListSet.iterator;
    }

    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return null;
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return null;
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return null;
    }

    @Override
    public T first() {
        return head.getValue();
    }

    @Override
    public T last() {
        return tail.getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return iterator;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    public void reBalance() {

    }
}
