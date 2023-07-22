public class SkipListSetItem <T extends Comparable<T>> {
    public SkipListSetItem<T> next;
    public SkipListSetItem<T> above;
    public SkipListSetItem<T> below;
    public SkipListSetItem<T> previous;

    public T value;
    public Integer level;

    public SkipListSetItem() {
        this.next = null;
        this.previous = null;
        this.above = null;
        this.below = null;
    }

    public SkipListSetItem(T value) {
        this.value = value;

        this.next = null;
        this.previous = null;
        this.above = null;
        this.below = null;
        this.level = 1;
    }

    public int compareTo(T value) {
        return this.value.compareTo(value);
    }
}