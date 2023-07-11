public class SkipListSetItem <T extends Comparable<T>> {

    private SkipListSetItem <T> next;
    private SkipListSetItem <T> above;
    private SkipListSetItem <T> below;

    private T value;

    public SkipListSetItem() {
        this.next = null;
        this.above = null;
        this.below = null;
    }

    public SkipListSetItem(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
