package SkipList;

import java.util.Iterator;
/**
 * This class is used to aid with skip list iteration. Given a skip list set, an instance of the
 * SkipListSetIterator class stores the location of the set, as well as the location of the current element -
 * which helps in retrieving the next element in the set (for the specific iteration).
 * @author Gradi Tshielekeja Mbuyi
 * @version 1.0
 * @since July 27, 2023
 * @param <T> accepts generics as parameters.
 */
public class SkipListSetIterator <T extends Comparable<T>> implements Iterator<T> {
    private SkipListSet<T> skipListSet;
    private SkipListSetItem<T> current;

    /**
     * The default constructor takes in one parameter, which is the location of the set to be iterated over. The
     * current location is set to the head of the set.
     * @param skipListSet the current skip list set whose element is to be iterated over.
     */
    public SkipListSetIterator(SkipListSet<T> skipListSet) {
        this.skipListSet = skipListSet;
        this.current = skipListSet.getHead();
    }

    /**
     * This method is used to check if it's possible to iterate to the next element - returning true if the iteration
     * has not reached the tail of the set.
     * @return Returns false if the tail of the skip list is reached during the iteration, otherwise returns true.
     */
    @Override
    public boolean hasNext() {
        return current.getNext() != null;
    }

    /**
     * This method is used to retrieve the next element during the iteration process.
     * @return Returns the memory location of the next element in the skip list during the iteration process.
     */
    @Override
    public T next() {
        current = current.getNext();
        return current.getValue();
    }

    /** This method calls the remove method from the skip list set class. */
    @Override
    public void remove() {
        current = current.getNext();
        skipListSet.remove(current.getPrevious().getValue());
    }

}