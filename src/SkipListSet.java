import java.util.Comparator;
import java.util.SortedSet;
import java.util.Random;
import java.util.Iterator;
import java.util.Collection;
/**
 * This class implements the skip list data structured using Java's sorted set interface. The list accepts
 * generic types as parameters by comparing each element and storing them in ascending order. Elements are
 * instances of the wrapper class SkipListSetItem, which contains the location of its previous, next, below,
 * and above element. Each element also contain the value of the specified type.
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
        random = new Random(1);

        size = 0;
        head.level = 1;
        height = 1;
        bottomHead = head;
    }

    /**
     * This method creates and returns an instance of the SkipListSetIterator class. Through the SkipListSetIterator
     * object, we can check to see if a list has a next value. If a value is present, we can retrieve and remove it
     * from the skip list.
     *
     * @return Returns a new instance of the SkipListSetIterator class.
     */
    @Override
    public Iterator<T> iterator() {
        return new SkipListSetIterator<>(this);
    }

    /**
     * This method returns the value of the head element within a skip list.
     * @return Returns value of the first element within a skip list.
     */
    @Override
    public T first() {
        return bottomHead.value;
    }

    /**
     * This method returns the value of the tail element within a skip list.
     * @return Returns value of the last element within a skip list.
     */
    @Override
    public T last() {
        return bottomTail.value;
    }

    /**
     * This method is used by the class SkipLIstSetIterator to retrieve the head element of a skip list.
     * @return Returns first element in the skip list.
     */
    public SkipListSetItem<T> getHead() {
        return bottomHead;
    }


    /**
     *
     * @return Returns the cardinality of a skip list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * This method is unsupported per Project instructions
     * @param fromElement low endpoint (inclusive) of the returned set
     * @param toElement high endpoint (exclusive) of the returned set
     * @return Fails to return
     * @throws UnsupportedOperationException as contracted
     */
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is unsupported per Project instructions
     * @param toElement high endpoint (exclusive) of the returned set
     * @return Fails to return
     * @throws UnsupportedOperationException as contracted
     */
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is unsupported per Project instructions
     * @param fromElement low endpoint (inclusive) of the returned set
     * @return Fails to return
     * @throws UnsupportedOperationException as contracted
     */
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is used to clear the skip list. Each value is reset to its original state.
     */
    @Override
    public void clear() {
        head = new SkipListSetItem<>();
        size = 0;
        head.level = 1;
        height = 1;
        bottomHead = head;

        System.gc();
    }

    /**
     * This method perform search operations. Given a specified value, search() attempts to find the closet value
     * less than or equal to the given object. It's important to note that this is an internal method, meaning
     * that it isn't accessible beyond the scope of the SkipListSet class.
     *
     * @param value element whose presence in this set is to be tested.
     * @return Returns the location of the closest element to the given value.
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
     * This method checks to see if a given object or value is contained within a skip list. It calls the search()
     * method, returning true if the value is present in the list, or false if the value isn't present in the list.
     *
     * @param object element whose presence in this set is to be tested.
     * @return Returns true if element is present in the set, otherwise returns false.
     */
    @Override
    public boolean contains(Object object) {
        SkipListSetItem<T> current = search((T) object);
        return current.compareTo((T) object) == 0;
    }

    /**
     * This method checks to see if the elements of a given collection are contained within a skip list. It calls the
     * contains() method n times, where n represents the number of value within the given collection, returning false
     * if a specific element isn't present in the list, or otherwise returning true. The performance of this operation
     * isn't ideal given the fact that collections are not always sorted.
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
     * This method is used to add elements of a given collections to a skip list, utilizing the add() method to perform
     * this operation.
     *
     * @param collection collection containing elements to be added to this set.
     * @return Returns true once each element is added to the list.
     */
    @Override
    public boolean addAll(Collection<? extends T> collection) {
        for(T item : collection) add(item);
        return true;
    }

    /**
     * This method is used to remove elements of a given collections from a skip list, utilizing the remove() method
     * to perform this operation.
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
     * This method allocates space for an array with a size of SkipListSet.size. As we iterate over the entire
     * skip list, each element is stored within the array. The array is then returned to the user.
     * @return Returns an array containing every element within a skip list.
     */
    @Override
    public Object[] toArray() {
        Object[] object = new Object[size];
        SkipListSetItem<T> current = bottomHead;

        for(int i = 0; i < size; i++) {
            object[i] = current.value;
            current = current.next;
        }

        return object;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    /**
     * This internal method is used to change the head element of a skip list. In the case where a given value is
     * less than the head, changeHead() is called and through it, the head of the skip list is updated.
     * @param headValue value to be added before the head.
     * @param numLevelToAdd numbers of times the current head will be present in the list.
     */
    private void changeHead(T headValue, int numLevelToAdd) {
       SkipListSetItem<T> currentHead = bottomHead;
       SkipListSetItem<T> newNode;
       SkipListSetItem<T> bellow = null;

       T newValue = head.value;
       if(numLevelToAdd == height) numLevelToAdd--;

       /* Updates value of our current head and new node */
       while(currentHead != null) {
           if(numLevelToAdd != 0) {
               newNode = new SkipListSetItem<>(newValue);
               newNode.level = currentHead.level;
               newNode.below = bellow;
               newNode.previous = currentHead;
               newNode.next = currentHead.next;

               if(currentHead.next != null) {
                   currentHead.next.previous = newNode;
               }

               currentHead.next = newNode;
               bellow = newNode;
               newNode = newNode.above;
               numLevelToAdd--;
           }

           currentHead.value = headValue;
           currentHead = currentHead.above;
       }
    }

    /**
     * This internal method is used to add the new element numLevelToAdd times in a skip list.
     * @param newNode element to be added in the skip list.
     * @param numLevelToAdd numbers of times the new node will be present in the list.
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
     * This method adds specified element in a skip list, depending on whether the element is already contained in
     * the list. This method does not allow duplicates. Before performing the add operation, add() calls search() and
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

        while(random.nextBoolean()) {
            numLevelToAdd++;
        }

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
            changeHead(value, numLevelToAdd);

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

        if(current.compareTo((T) value) != 0) {
            return false;

        } else if(current.compareTo(head.value) == 0) {
            SkipListSetItem<T> next = current.next;

            while(current != null) {
                current.value = next.value;

                if(current.next != null && current.next.compareTo(next.value) == 0) {
                    current.next = current.next.next;
                    current.next.previous = current;
                }

                current = current.above;
            }

        } else while(current != null) {
            current.previous.next = current.next;

            if(current.next != null) {
                current.next.previous = current.previous;
            }

            current = current.above;
        }

        size--;
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
     * @return Returns null
     */
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }


    /**
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public void reBalance() {
        ;
    }
}