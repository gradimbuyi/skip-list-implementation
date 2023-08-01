/**
 * This class provides the SkipList Set with data elements. Each instance of the SkipListSetItem class
 * store the data value of the element, as well as the location of it neighbor.
 * @author Gradi Tshielekeja Mbuyi
 * @version 1.0
 * @since July 27, 2023
 * @param <T> accepts generics as parameters.
 */
public class SkipListSetItem <T extends Comparable<T>> {
    private SkipListSetItem<T> next;
    private SkipListSetItem<T> above;
    private SkipListSetItem<T> below;
    private SkipListSetItem<T> previous;
    private T value;
    private Integer level;

    /**
     * The default constructor takes in no parameters and is called in the beginning of the set's initialization.
     * Given an empty set, SkipListSetItem() is called to initialize the head of the set, where each value
     * and neighbor are set to NULL.
     */
    public SkipListSetItem() {
        this.value = null;
        this.next = null;
        this.previous = null;
        this.above = null;
        this.below = null;
        this.level = null;
    }

    /**
     * The secondary constructor is called during the add operation. Given a value V and a set S,
     * SkipListSetItem(T value) is called to allocate space for a new element. Upon allocating space, value V is
     * added to the given set S.
     * @param value given value whose element is to be added in the collection
     */
    public SkipListSetItem(T value) {
        this.value = value;
        this.next = null;
        this.previous = null;
        this.above = null;
        this.below = null;
        this.level = 1;
    }

    /**
     * This method performs comparison operations. The method is given a value which is then compared to the value
     * of the object calling the method.
     * @param value the given value that will be compared to the object's value.
     * @return Returns 0 if the two value being compared are equal to each other. Returns 1 if the given value is
     * greater than the object's value, otherwise return -1.
     */
    public int compareTo(T value) {
        return this.value.compareTo(value);
    }

    /**
     * Getter method for retrieving the next element of a given skip list item.
     * @return Returns the location of the next element.
     */
    public SkipListSetItem<T> getNext() {
        return next;
    }

    /**
     * Getter method for retrieving the above element of a given skip list item.
     * @return Returns the location of the above element.
     */
    public SkipListSetItem<T> getAbove() {
        return above;
    }

    /**
     * Getter method for retrieving the bellow element of a given skip list item.
     * @return Returns the location of the bellow element.
     */
    public SkipListSetItem<T> getBelow() {
        return below;
    }

    /**
     * Getter method for retrieving the previous element of a given skip list item.
     * @return Returns the location of the previous element.
     */
    public SkipListSetItem<T> getPrevious() {
        return previous;
    }

    /**
     * Getter method for retrieving the value of a given skip list item.
     * @return Returns the value of the current element.
     */
    public T getValue() {
        return value;
    }

    /**
     * Getter method for retrieving the level of a given skip list item.
     * @return Returns the level of the current element.
     */
    public Integer getLevel() {
        return level;
    }

    /** Setter method for setting the next element of a given skip list item. */
    public void setNext(SkipListSetItem<T> next) {
        this.next = next;
    }

    /** Setter method for setting the above element of a given skip list item. */
    public void setAbove(SkipListSetItem<T> above) {
        this.above = above;
    }

    /** Setter method for setting the bellow element of a given skip list item. */
    public void setBelow(SkipListSetItem<T> below) {
        this.below = below;
    }

    /** Setter method for setting the previous element of a given skip list item. */
    public void setPrevious(SkipListSetItem<T> previous) {
        this.previous = previous;
    }

    /** Setter method for setting the value of a given skip list item. */
    public void setValue(T value) {
        this.value = value;
    }

    /** Setter method for setting the level of a given skip list item. */
    public void setLevel(Integer level) {
        this.level = level;
    }


}