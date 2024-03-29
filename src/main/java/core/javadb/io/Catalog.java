package core.javadb.io;

import java.nio.file.Path;

public interface Catalog {
    Path getFilePath(String tableName);

    String getTableName(String pageId);
}
