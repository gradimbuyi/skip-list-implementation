package Examples;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.Random;
import java.util.Iterator;
import java.util.Collection;

/* This class implements the skip list data structured using Java's sorted set interface. The list accepts
 * generic types as parameters by comparing each element and storing them in ascending order. Elements are
 * instances of the wrapper class SkipListSetItem, which contains the location of its previous, next, below,
 * and above element. Each element also contain the value of the specified type.
 *
 * Author: Gradi Tshielekeja Mbuyi */
public class SkipListSet <T extends  Comparable<T>> implements SortedSet<T> {
    private SkipListSetItem <T> head;
    private SkipListSetItem <T> bottomHead;
    private SkipListSetItem <T> bottomTail;
    private Integer height;
    private Integer size;
    private final Random random;

    /* Main constructor - accepts no parameters */
    public SkipListSet() {
        head = new SkipListSetItem<>();
        random = new Random(1);
        size = 0;
        head.level = 1;
        height = 1;
        bottomHead = head;
    }

    /* Secondary constructor - accepts a collection as a parameter */
    public SkipListSet(Collection<? extends T> collection) {
        head = new SkipListSetItem<>();
        random = new Random(1);
        size = 0;
        head.level = 1;
        height = 1;
        bottomHead = head;
        addAll(collection);
    }

    /* Private item wrapper class */
    private static class SkipListSetItem <T extends Comparable<T>> {
        private SkipListSetItem<T> next;
        private SkipListSetItem<T> above;
        private SkipListSetItem<T> below;
        private SkipListSetItem<T> previous;
        private T value;
        private Integer level;

        /* Main constructor - called when skip list set is empty */
        public SkipListSetItem() {
            value = null;
            next = null;
            previous = null;
            above = null;
            below = null;
            level = null;
        }

        /* Secondary constructor - called when wanting to add a new value */
        public SkipListSetItem(T value) {
            this.value = value;
            next = null;
            previous = null;
            above = null;
            below = null;
            level = 1;
        }

        /* Comparator - compares given value to the currently accessed node */
        public int compareTo(T value) {
            return this.value.compareTo(value);
        }
    }

    /* Private iterator class */
    private static class SkipListSetIterator <T extends Comparable<T>> implements Iterator<T> {
        private final SkipListSet<T> skipListSet;
        private SkipListSetItem<T> current;

        /* Default constructor - accepts a skip list set*/
        public SkipListSetIterator(SkipListSet<T> skipListSet) {
            this.skipListSet = skipListSet;
            this.current = skipListSet.bottomHead;
        }

        /* checks to see if list has a next element */
        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        /* returns next element in list */
        @Override
        public T next() {
            current = current.next;
            return current.value;
        }

        /* removes current element from the list */
        @Override
        public void remove() {
            skipListSet.remove(current.value);
        }
    }

    /* returns a new instance of the SkipListSetIterator class */
    @Override
    public Iterator<T> iterator() {
        return new SkipListSetIterator<>(this);
    }

    /* returns the first value in a given skip list */
    @Override
    public T first() {
        return bottomHead.value;
    }

    /* returns the last value in a given skip list */
    @Override
    public T last() {
        return bottomTail.value;
    }

    /* returns the size (cardinality) of a given skip list */
    @Override
    public int size() {
        return size;
    }

    /* returns true if a given* skip list is empty, otherwise returns false */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /* NO IMPLEMENTATION OF METHOD - THROWS a UnsupportedOperationException */
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    /* NO IMPLEMENTATION OF METHOD - THROWS a UnsupportedOperationException */
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    /* NO IMPLEMENTATION OF METHOD - THROWS a UnsupportedOperationException */
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    /* NO IMPLEMENTATION OF METHOD - RETURNS null */
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /* returns true if a given skip list contains the given object, otherwise returns false */
    @Override
    public boolean contains(Object object) {
        SkipListSetItem<T> current = search((T) object);
        return current.compareTo((T) object) == 0;
    }

    /* returns true if a given skip list contain all values in a given collection, otherwise returns false */
    @Override
    public boolean containsAll(Collection<?> collection) {
        for(Object item : collection) {
            if(!contains(item)) return false;
        }
        return true;
    }

    /* iterates through a given collection, adding each element to a skip list */
    @Override
    public boolean addAll(Collection<? extends T> collection) {
        for(T item : collection) {
            add(item);
        }
        return true;
    }

    /* iterates through a given collection, removing similar elements from a skip list */
    @Override
    public boolean removeAll(Collection<?> collection) {
        for(Object item : collection) {
            remove(item);
        }
        return true;
    }

    /* iterates through a given collection, removing elements that are not present in the collection from a skip list */
    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    /* creates and returns an object array containing every element from a given skip list */
    @Override
    public Object[] toArray() {
        Object[] object = new Object[size];
        SkipListSetItem<T> current = bottomHead;

        /* iterates through a given skip list, copying each element */
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

    /* clears a given skip list */
    @Override
    public void clear() {
        head = new SkipListSetItem<>();
        size = 0;
        head.level = 1;
        height = 1;
        bottomHead = head;
        System.gc();
    }

    /* given a value of type T, search() returns the closest element to the given value */
    private SkipListSetItem<T> search(T value) {
        SkipListSetItem<T> current = head;

        while(current.below != null) {
            current = current.below;
            while(current.next != null && current.next.compareTo(value) <= 0) {
                current = current.next;
            }
        }

        return current;
    }

    /* during the add operation, changeHead() updates the value of the first element in a given skip list */
    private void changeHead(T headValue, int numLevelToAdd) {
        T newValue = head.value;
        SkipListSetItem<T> currentHead = bottomHead;
        SkipListSetItem<T> newNode;
        SkipListSetItem<T> bellow = null;

        /* the new head cannot exceed the height of the old head */
        if(numLevelToAdd == height) {
            numLevelToAdd--;
        }

        /* updates value of our current head and new node */
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

    /* adds a new skip list item above the current given newNode (which is an instances of the item wrapper class) */
    private void addNewLevel(SkipListSetItem<T> newNode, int numLevelToAdd) {
        SkipListSetItem<T> previous = newNode.previous;

        /* returns before adding a new level*/
        if(numLevelToAdd == 0) {
            return;
        }

        /* for numLevelToAdd times, the method addNewLevel adds a new skip list item on top of the current
         * newNode */
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

    /* This method adds specified element in a skip list, depending on whether the element is already contained in
     * the list. This method does not allow duplicates. Before performing the add operation, add() calls search() and
     * checks to see if the element is within the list. */
    @Override
    public boolean add(T value) {
        SkipListSetItem<T> newNode = null;
        SkipListSetItem<T> current = search(value);

        int numLevelToAdd = 0;

        /* randomly choose the highest level where our a new element will reside */
        while(random.nextBoolean()) {
            numLevelToAdd++;
        }

        /* adds very first node */
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

        /* the given value is already in the list */
        if(current.compareTo(value) == 0) {
            return false;

            /* adds value before the current head */
        } else if(current.compareTo(head.value) == 0 && current.compareTo(value) > 0) {
            changeHead(value, numLevelToAdd);

            /* adds value after the current node found during the search() operation */
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

    private void removeLevels(SkipListSetItem<T> current, int removeBy) {
        for(int i = 0; i < removeBy; i++) {
            current.above = null;
            current.previous.next = current.next;

            if(current.next != null) {
                current.next.previous = current.previous;
            }

            current = current.below;
        }
    }

    private SkipListSetItem<T> deleteSingleNode(SkipListSetItem<T> current) {
        SkipListSetItem<T> next = current.next;

        if(current.next != null) current.next.previous = current.previous;
        if(current.below != null) current.below.above = null;
        if(current.previous != null) current.previous.next = current.next;

        current = null;

        return next;
    }

    public void reBalance() {
        int levelValue, nodePosition = 0;
        int maxLevel = (int) (Math.log(size) / Math.log(2));

        SkipListSetItem<T> current = bottomHead;
        SkipListSetItem<T> currentTop;

        if(height > maxLevel) {
            nodePosition++;
            currentTop = head;

            for(int i = 0; i < height - maxLevel; i++) {
                SkipListSetItem<T> next = currentTop.next;
                while(next != null) {
                    next = deleteSingleNode(next);
                }
                currentTop = currentTop.below;
                currentTop.above = null;
            }
            head = currentTop;
            current = current.next;
        }

        while(current != null) {
            nodePosition++;

            levelValue = 0;
            currentTop = current;

            while(currentTop.above != null) {
                currentTop = currentTop.above;
            }

            if(currentTop.level < levelValue) {
                addNewLevel(current, levelValue);
            } else if(currentTop.level > levelValue) {
                removeLevels(currentTop, levelValue);
            }

            current = current.next;
        }
    }
}