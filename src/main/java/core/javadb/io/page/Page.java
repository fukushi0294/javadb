package core.javadb.io.page;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Page {
    private final String databaseName;
    private final String tableName;
    public CtId ctId;
    public List<Tuple> tuples;
    boolean compressed;
    // read byte and construct page
    // but page contents is still compressed

    public Page(String databaseName, String tableName) {
        this.ctId = new CtId();
        this.databaseName = databaseName;
        this.tableName = tableName;
    }

    public void insert(Tuple tuple) {
        this.tuples.add(tuple);
    }

    public List<Tuple> has(Predicate<Tuple> condition) {
        return tuples.stream().filter(condition).collect(Collectors.toList());
    }

    public String getTableName() {
        return tableName;
    }

    public static byte[] concat(List<Page> pages) {
        throw new RuntimeException();
    }

    public static Page load(String databaseName, String tableName, byte[] bytes) {
        return new Page(databaseName, tableName);
    }

    public List<Byte> toByte() {
        throw new RuntimeException();
    }
}
