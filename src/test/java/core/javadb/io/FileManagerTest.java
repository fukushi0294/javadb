package core.javadb.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FileManagerTest {
    private FileManager target;

    @BeforeEach
    void beforeEach() throws IOException {
        target = new FileManager("test");
    }

    @Test
    void createFileTest() throws IOException {
        String tableName = "tbl";
        target.createFile(tableName);
    }
}
