package io.javadb.storage.page;

import java.util.List;

public class Tuple {
    Integer length;
    String id;
    // TODO: acc concurrency control
    List<Byte> data;
}
