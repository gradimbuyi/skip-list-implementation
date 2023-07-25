import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.Random;

public class SkipListSet <T extends  Comparable<T>> implements SortedSet<T> {
    private SkipListSetItem <T> head;
    private SkipListSetItem <T> bottomHead;
    private SkipListSetItem <T> bottomTail;
    private Integer height;
    private Integer size;
    private SkipListSetIterator <T> iterator;
    private final Random random;

    public SkipListSet() {
        head = new SkipListSetItem<>();
        iterator = new SkipListSetIterator<>(this);
        random = new Random();

        size = 0;
        head.level = 1;
        height = 1;
        bottomHead = head;
    }

    private SkipListSetItem<T> search(T value) {
        SkipListSetItem<T> current = head;

        while(current.below != null) {
            current = current.below;

            while(current.next != null && current.compareTo(value) < 0) {
                current = current.next;
            }
        }

        return current;
    }

    @Override
    public boolean contains(Object object) {
        SkipListSetItem<T> current = search((T) object);
        return current.compareTo((T) object) == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return iterator;
    }

    @Override
    public Object[] toArray() {
        return new Object[size];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    public void print() {
        SkipListSetItem<T> current = head;
        System.out.println("Size: " + size);
        System.out.println("Height: " + height + "\n");

        while(current != null) {
            System.out.println("Current Height: " + current.level);
            System.out.print(current.value + " ");
            while (current.next != null) {
                current = current.next;
                System.out.print(current.value + " ");
            }
            while(current.previous != null) current = current.previous;
            current = current.below;
            System.out.println("\n");
        }
    }

    private void addNewLevel(SkipListSetItem<T> newNode, SkipListSetItem<T> currentHead, int numLevelToAdd) {
        if(numLevelToAdd == 0) return;

        //System.out.println(newNode.value + " is added " + numLevelToAdd + " times");

        for(int i = 0; i < numLevelToAdd; i++) {

            //System.out.println("Height: " + height + " NewNode Height: " + newNode.level);

            newNode.above = new SkipListSetItem<>(newNode.value);
            newNode.above.level = newNode.level + 1;
            newNode.above.below = newNode;

            if(height < newNode.level) {
                currentHead.above = new SkipListSetItem<>(head.value);
                currentHead.above.level = currentHead.level + 1;
                currentHead.above.below = currentHead;
                currentHead.above.next = newNode.above;
                newNode.above.previous = currentHead.above;

                height++;

            } else if(height > newNode.level) {
                SkipListSetItem<T> previous = newNode.previous;
                while(previous.above == null) previous = previous.previous;
                newNode.above.previous = previous.above;
                newNode.above.next = previous.above.next;
                previous.above.next = newNode.above;

            } else {
                head.above = new SkipListSetItem<>(head.value);
                head.above.below = head;
                head.above.level = head.level + 1;
                head.above.next = newNode.above;
                newNode.above.previous = head.above;
                head = head.above;
                height++;
            }

            currentHead = currentHead.above;
            newNode = newNode.above;
        }

        if(newNode.level == height) {
            head.above = new SkipListSetItem<>(head.value);
            head.above.below = head;
            head.above.level = head.level + 1;
            head = head.above;
            height++;
        }

       // System.out.println();
    }

    @Override
    public boolean add(T value) {
        SkipListSetItem<T> newNode = null;
        SkipListSetItem<T> current = search(value);

        int numLevelToAdd = 0;

        while(random.nextBoolean()) numLevelToAdd++;

        if(isEmpty()) {
            head.value = value;
            head.above = new SkipListSetItem<>(value);
            head.above.below = head;
            head.above.level = head.level + 1;
            head = head.above;

            size++;
            height++;
            return true;
        }

        if(current.compareTo(value) == 0) {
            return false;

        } else if(current.compareTo(head.value) == 0 && current.compareTo(value) > 0) {
            newNode = new SkipListSetItem<>(value);
            newNode.next = head;
            head.previous = newNode;
            head = newNode;

            //addNewLevel(newNode, bottomHead, numLevelToAdd);

        } else if(current.compareTo(value) > 0) {
            newNode = new SkipListSetItem<>(value);
            newNode.next = current;
            newNode.previous = current.previous;
            current.previous.next = newNode;
            current.previous = newNode;
            addNewLevel(newNode, bottomHead, numLevelToAdd);

        } else if(current.compareTo(value) < 0) {
            newNode = new SkipListSetItem<>(value);
            newNode.previous = current;
            current.next = newNode;
            bottomTail = newNode;
            addNewLevel(newNode, bottomHead, numLevelToAdd);
        }

        size++;
        return true;
    }

    @Override
    public boolean remove(Object value) {
        SkipListSetItem<T> current = search((T) value);

        size--;
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for(Object item : collection) {
            if(!contains(item)) return false;
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        for(T item : collection) add(item);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
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
        return bottomHead.value;
    }

    @Override
    public T last() {
        return bottomTail.value;
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
        while(head.below.next == null) {
            head = head.below;
            head.above = null;
            height--;

            System.gc();
        }
    }
}
