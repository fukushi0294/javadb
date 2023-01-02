package io.javadb.storage.page;

import java.util.List;

public class Page implements Serializable {
    public CtId ctId;
    List<Tuple> tuples;
    boolean compressed;
    // read byte and construct page
    // but page contents is still compressed

    public Page() {
        this.ctId = new CtId();
    }

    public static Page create(byte[] bytes) {
        return new Page();
    }

    public List<Byte> toByte() {
        throw new RuntimeException();
    }
}
