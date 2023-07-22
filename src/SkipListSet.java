import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.Random;

public class SkipListSet <T extends  Comparable<T>> implements SortedSet<T> {
    private SkipListSetItem <T> head;
    private SkipListSetItem <T> bottomHead;
    private SkipListSetIterator <T> iterator;

    private Integer size;
    private Random random;

    public SkipListSet() {
        head = new SkipListSetItem<>();
        iterator = new SkipListSetIterator<>(this);
        random = new Random();

        size = 0;
        head.level = 0;
        iterator.height = 1;
        bottomHead = head;
    }

    private SkipListSet(SkipListSet<T> previous) {
        head = new SkipListSetItem<>();
        head.value = previous.head.value;

        iterator = previous.iterator;
        random = previous.random;
        size = previous.size;
        head.level = previous.head.level + 1;
        bottomHead = previous.bottomHead;
        previous.head.above = head;
        head.below = previous.head;

        iterator.height++;
    }

    private SkipListSetItem<T> search(T value) {
        SkipListSetItem<T> current = head;

        while(current.below != null) {
            while(current.next != null && current.compareTo(value) <= 0) current = current.next;
            current = current.below;
        }

        return current;
    }

    @Override
    public boolean contains(Object object) {
        SkipListSetItem<T> current = search((T) object);
        return current.value.compareTo((T) object) == 0;
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

    public void print() {
        SkipListSetItem<T> current = head;

        System.out.println("Size: " + size);

        System.out.println(current.value);

        while(current.next != null){
            current = current.next;
            System.out.print(current.value + " ");

        }
    }

    private SkipListSetItem<T> addNewLevel(SkipListSetItem<T> newNode) {
        SkipListSet<T> newLevel = new SkipListSet<>(this);

        newNode.above = new SkipListSetItem<>(newNode.value);
        newNode.above.below = newNode;

        head.above = new SkipListSetItem<>(head.value);
        head.above.below = head;

        return newNode.above;
    }

    @Override
    public boolean add(T value) {
        SkipListSet<T> newLevel;
        SkipListSetItem<T> newNode;
        SkipListSetItem<T> current = search(value);

        if(isEmpty()) {
            head.value = value;
            size++;

            newLevel = new SkipListSet<>(this);
            head = newLevel.head;

            return true;
        }

        if(current.compareTo(value) == 0) {
            return false;

        } else if(current.compareTo(head.value) == 0 && current.compareTo(value) > 0) {
            newNode = new SkipListSetItem<>(value);
            newNode.next = head;
            head.previous = newNode;
            head = newNode;

        } else if(current.compareTo(value) > 0) {
            newNode = new SkipListSetItem<>(value);
            newNode.next = current;
            newNode.previous = current.previous;
            current.previous.next = newNode;
            current.previous = newNode;

            System.out.println("hi");

        } else if(current.compareTo(value) < 0) {
            newNode = new SkipListSetItem<>(value);
            newNode.previous = current;
            newNode.next = current.next;
            current.next = newNode;

            System.out.println("hola");

        }

        /*
        while(random.nextBoolean()) {
            assert newNode != null;
            newNode = addNewLevel(newNode);
        }

         */
        size++;
        return true;
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
    public boolean addAll(Collection<? extends T> collection) {
        for(T item : collection) add(item);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        for(Object item : collection) remove(item);
        return true;
    }

    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        this.head = null; System.gc();
    }

    public SkipListSetItem<T> getHead() {
        return bottomHead;
    }

    @Override
    public T first() {
        return head.value;
    }

    @Override
    public T last() {
        return head.next.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public void reBalance() {

    }

}
