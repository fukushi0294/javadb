package io.javadb.storage;

import java.util.List;

public class Tuple implements Serializable {
    Integer length;
    String id;
    // TODO: acc concurrency control
    List<Byte> data;
}
