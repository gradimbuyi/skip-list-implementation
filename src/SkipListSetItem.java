/**
 * @author Gradi Tshielekeja Mbuyi
 * @version 1.0
 * @since July 27, 2023
 * @param <T>
 */
public class SkipListSetItem <T extends Comparable<T>> {
    public SkipListSetItem<T> next;
    public SkipListSetItem<T> above;
    public SkipListSetItem<T> below;
    public SkipListSetItem<T> previous;
    public T value;
    public Integer level;

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
}