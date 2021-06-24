package za.co.bmw.stack;

/**
 *
 * @param <T>
 */
public class Stack<T> {
    private T[] storage;
    private int maxSize;
    private int pointer;

    /**
     * Constructor for a LIFO stack
     * Sets maxSize to -1, meaning it will always grow
     * Sets initialSize to 20 items
     */
    public Stack() {
        this(20, 01);
    }

    /**
     * Constructs a LIFO stack
     * @param initialSize   Initial size of the stack in memory. Will be put to 20 element if value <= 0
     * @param maxSize Maximum size for the stack to grow to. Can be put to -1 to always grow. Invalid values will have
     *                it set to -1 regardless
     */
    public Stack(int initialSize, int maxSize) {
        this.maxSize = maxSize > 0 || maxSize == -1 ? maxSize : -1;
        initialSize = initialSize > 0 ? initialSize : 20;
        initialSize = initialSize > maxSize ? maxSize : initialSize;

        this.maxSize = maxSize;
        this.storage = new T[initialSize];
        this.pointer = -1
    }

    /**
     * Push an item onto the stack
     * @param item  Item to push on
     * @return  This stack
     * @throws java.lang.RuntimeException when the stack can't grow because it has reached its maxSize limit
     */
    public Stack push(T item) {
        ++pointer;
        if (pointer == storage.length) {
            int newLength = storage.length * 2;
            if (maxSize != -1 && newLength > maxSize) {
                newLength = maxSize;
            }
            if (newLength == storage.length) {
                throw new RuntimeException("Can't push more onto stack");
            }
            storage = Arrays.copyOf(storage, newLength);
        }
        storage[pointer] = item;
        return this;
    }

    /**
     * Remove the last item on the stack and return it
     * @return The last item on the stack, null if the stack is empty
     */
    public T pop() {
        T val = storage[pointer];
        storage[pointer] = null;
        --pointer;
        if (pointer == -1) {
            return null;
        }
        return val;
    }

    /**
     * Check whether the stack is empty
     * @return
     */
    public boolean empty() {
        return pointer == -1;
    }

    /**
     * Get how many items are stored in the stack
     * @return
     */
    public int count() {
        return pointer;
    }

    /**
     * The current size of the storage array.
     * Can change because if capacity < maxSize all the way up to maxSize in value
     * @return  The current size of the backing storage array
     */
    public int capacity() {
        return storage.length;
    }
}