package Utility;

public class Pair<T, P> {
    private T key;
    private P value;

    /**
     * Constructor for a pair object, that holds two generic values
     *
     * @param key   first value
     * @param value second value
     */
    public Pair(T key, P value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the first value of the Pair
     *
     * @return key
     */
    public T getKey() {
        return key;
    }

    /**
     * Sets the value of the first value of the Pair
     *
     * @param key key
     */
    public void setKey(T key) {
        this.key = key;
    }

    /**
     * Returns the second value of the Pair
     * @return value
     */
    public P getValue() {
        return value;
    }

    /**
     * Sets the value of the second value of the Pair
     *
     * @param value value
     */
    public void setValue(P value) {
        this.value = value;
    }
}
