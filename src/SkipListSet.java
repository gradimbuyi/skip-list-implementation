import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;
import java.util.Comparator;

/**
 * This class implements the skip list data structured using Java's sorted set interface. The list accepts
 * generic types as parameters, comparing the elements, and storing them in ascending order. Elements are
 * instances of the wrapper class SkipListSetItem, which contains the location of the previous, next, below,
 * and above elements. Each element also contain the value of the specified type.
 *
 * @author Gradi Tshielekeja Mbuyi
 * @version 1.0
 * @since July 27, 2023
 * @param <T> accepts generics as parameters.
 */
public class SkipListSet <T extends  Comparable<T>> implements SortedSet<T> {
    private SkipListSetItem <T> head;
    private SkipListSetItem <T> bottomHead;
    private SkipListSetItem <T> bottomTail;
    private Integer height;
    private Integer size;
    private final Random random;

    /**
     * Default constructor to initialize a skip list. This constructor does not accept any parameters, however as we
     * initialize the list, an instance of the SkipListSetItem class is created and stored in the head variable.
     * The constructor also create an instance for the random variable.
     */
    public SkipListSet() {
        head = new SkipListSetItem<>();
        random = new Random();

        size = 0;
        head.level = 1;
        height = 1;
        bottomHead = head;
    }

    /**
     *
     *
     * @param object element whose presence in this set is to be tested
     * @return Returns true if element is present in the list, otherwise returns false.
     */
    @Override
    public boolean contains(Object object) {
        SkipListSetItem<T> current = search((T) object);
        return current.compareTo((T) object) == 0;
    }

    /**
     *
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return new SkipListSetIterator<>(this);
    }

    /**
     *
     * @return
     */
    @Override
    public Object[] toArray() {
        return new Object[size];
    }

    /**
     *
     * @param a the array into which the elements of this set are to be
     *        stored, if it is big enough; otherwise, a new array of the same
     *        runtime type is allocated for this purpose.
     * @return
     * @param <T1>
     */
    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    /**
     *
     */
    public void print() {
        SkipListSetItem<T> current = head;
        System.out.println("\nSize: " + size);
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

    /**
     *
     * @param value
     * @return
     */
    private SkipListSetItem<T> search(T value) {
        SkipListSetItem<T> current = head;

        while(current.below != null) {
            current = current.below;
            while(current.next != null && current.next.compareTo(value) <= 0) current = current.next;
        }

        return current;
    }

    /**
     *
     * @param currentHead
     * @param newNode
     * @param numLevelToAdd
     */
    private void changeHead(SkipListSetItem<T> currentHead, SkipListSetItem<T> newNode, int numLevelToAdd) {
        bottomHead = newNode;
        newNode.next = currentHead;
        currentHead.previous = newNode;

        while(currentHead.above != null) {
            currentHead = currentHead.above;

            if(numLevelToAdd != 0)  {
                newNode.above = new SkipListSetItem<>(newNode.value);
                newNode.above.next = currentHead;
                newNode.above.below = newNode;
                currentHead.previous = newNode.above;

                newNode = newNode.above;
                numLevelToAdd--;

            } else {
                currentHead.value = newNode.value;
            }
        }
    }

    /**
     *
     * @param newNode
     * @param numLevelToAdd
     */
    private void addNewLevel(SkipListSetItem<T> newNode, int numLevelToAdd) {
        SkipListSetItem<T> previous = newNode.previous;

        if(numLevelToAdd == 0) return;

        for(int i = 0; i < numLevelToAdd; i++) {
            newNode.above = new SkipListSetItem<>(newNode.value);
            newNode.above.level = newNode.level + 1;
            newNode.above.below = newNode;

            if(height == newNode.level + 1) {
                head.above = new SkipListSetItem<>(head.value);
                head.above.below = head;
                head.above.level = head.level + 1;
                head = head.above;
                height++;
            }

            while(previous.above == null) {
                previous = previous.previous;
            }

            newNode.above.previous = previous.above;
            newNode.above.next = previous.above.next;

            if(previous.above.next != null) {
                previous.above.next.previous = newNode.above;
            }

            previous.above.next = newNode.above;
            newNode = newNode.above;
            previous = previous.above;
        }
    }

    /**
     * Adds specified element in a skip list, depending on whether the element is already contained in the list.
     * This method does not allow duplicates. Before performing the add operation, add() calls search() and
     * checks to see if the element is within the list.
     *
     * @param value element whose presence in this collection is to be ensured.
     * @return Returns true if value is successfully added to the skip list, and false if value is already in the list.
     */
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
            changeHead(bottomHead, newNode, numLevelToAdd);

        } else {
            newNode = new SkipListSetItem<>(value);
            newNode.previous = current;
            newNode.next = current.next;

            if(current.next != null) current.next.previous = newNode;
            else bottomTail = newNode;

            current.next = newNode;
            addNewLevel(newNode, numLevelToAdd);
        }

        size++;
        return true;
    }

    /**
     *
     * @param value object to be removed from this set, if present.
     * @return Returns true if element is successfully removed, otherwise returns false.
     */
    @Override
    public boolean remove(Object value) {
        SkipListSetItem<T> current = search((T) value);

        if(current.compareTo((T) value) != 0) return false;

        else if(current.compareTo(head.value) == 0) {
            SkipListSetItem<T> next = current.next;

            while(current != null) {
                current.value = next.value;
                if(current.next != null && current.next.compareTo(next.value) == 0) {
                    current.next = current.next.next;
                    current.next.previous = current;
                }
                current = current.above;
            }
        }

        else while(current != null) {
            current.previous.next = current.next;
            if(current.next != null) current.next.previous = current.previous;
            current = current.above;
        }

        size--;
        return true;
    }

    /**
     *
     * @param collection collection to be checked for containment in this set.
     * @return Returns true if all elements in collection is contained in the skip list.
     */
    @Override
    public boolean containsAll(Collection<?> collection) {
        for(Object item : collection) {
            if(!contains(item)) return false;
        }
        return true;
    }

    /**
     *
     * @param collection collection containing elements to be added to this collection
     * @return
     */
    @Override
    public boolean addAll(Collection<? extends T> collection) {
        for(T item : collection) add(item);
        return true;
    }

    /**
     *
     * @param collection collection containing elements to be retained in this set
     * @return
     */
    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    /**
     *
     * @param collection collection containing elements to be removed from this set
     * @return
     */
    @Override
    public boolean removeAll(Collection<?> collection) {
        for(Object item : collection) remove(item);
        return true;
    }

    /**
     *
     * @return Returns null
     */
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     *
     * @param fromElement low endpoint (inclusive) of the returned set
     * @param toElement high endpoint (exclusive) of the returned set
     * @return
     */
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param toElement high endpoint (exclusive) of the returned set
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param fromElement low endpoint (inclusive) of the returned set
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     */
    @Override
    public void clear() {
        this.head = null; System.gc();
    }

    /**
     *
     * @return
     */
    public SkipListSetItem<T> getHead() {
        return bottomHead;
    }

    /**
     *
     * @return
     */
    @Override
    public T first() {
        return bottomHead.value;
    }

    /**
     *
     * @return
     */
    @Override
    public T last() {
        return bottomTail.value;
    }

    /**
     *
     * @return
     */
    @Override
    public int size() {
        return size;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     *
     */
    public void reBalance() {
        ;
    }
}