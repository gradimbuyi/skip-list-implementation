/**
 * @author Gradi Tshielekeja Mbuyi
 * @version 1.0
 * @since July 27, 2023
 * @param <T>
 */
public class SkipListSetItem <T extends Comparable<T>> {
    private SkipListSetItem<T> next;
    private SkipListSetItem<T> above;
    private SkipListSetItem<T> below;
    private SkipListSetItem<T> previous;
    private T value;
    private Integer level;

    /**
     *
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
     *
     * @param value
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
     *
     * @param value
     * @return
     */
    public int compareTo(T value) {
        return this.value.compareTo(value);
    }

    public SkipListSetItem<T> getNext() {
        return next;
    }

    public void setNext(SkipListSetItem<T> next) {
        this.next = next;
    }

    public SkipListSetItem<T> getAbove() {
        return above;
    }

    public void setAbove(SkipListSetItem<T> above) {
        this.above = above;
    }

    public SkipListSetItem<T> getBelow() {
        return below;
    }

    public void setBelow(SkipListSetItem<T> below) {
        this.below = below;
    }

    public SkipListSetItem<T> getPrevious() {
        return previous;
    }

    public void setPrevious(SkipListSetItem<T> previous) {
        this.previous = previous;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }


}