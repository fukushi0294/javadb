package core.javadb.data;

public interface Tree<T> {
    void add(T value);

    boolean contains(T value);

    void delete(T value);
}
