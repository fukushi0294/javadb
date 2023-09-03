package core.javadb.io;

import core.javadb.io.page.Page;
import core.javadb.io.page.Tuple;

import java.util.List;
import java.util.function.Predicate;

public interface PageIO {
    Page insert(Tuple tuple);
    List<Tuple> search(Predicate<Tuple> condition);
    Page delete(Tuple tuple, Predicate<Tuple> condition);
    Page update(Tuple tuple, Predicate<Tuple> condition);
}
