package io.javadb.storage;

import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public class Page implements Serializable {
    List<Tuple> tuples;
    boolean compressed;
    // read byte and construct page
    // but page contents is still compressed
    static Page create(byte[] bytes) {
        throw new RuntimeException();
    }
}
