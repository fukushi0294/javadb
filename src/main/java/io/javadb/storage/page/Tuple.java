package io.javadb.storage.page;

import io.javadb.storage.page.Serializable;

import java.util.List;

public class Tuple implements Serializable {
    Integer length;
    String id;
    // TODO: acc concurrency control
    List<Byte> data;
}
