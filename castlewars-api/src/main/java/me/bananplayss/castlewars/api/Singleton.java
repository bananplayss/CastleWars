package me.bananplayss.castlewars.api;

public final class Singleton<T> {

    private final String name;
    private T value;

    public Singleton(String name) {
        this.name = name;
    }

    public T get() {
        if (value == null) {
            throw new IllegalStateException(name + " not initialized yet!");
        }
        return value;
    }

    public void set(T value) {
        if (this.value != null) {
            throw new IllegalStateException(name + " already initialized!");
        }
        this.value = value;
    }
}
