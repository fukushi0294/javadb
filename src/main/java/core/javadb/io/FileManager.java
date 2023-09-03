package core.javadb.io;

import core.javadb.data.LRUCache;
import core.javadb.io.page.Page;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * create or fetch table file
 * there is no implementation to issue object id against database name or table name like Postgres
 * One fileManager for One Database and manage table file
 * Page operation is done through FileManager
 */
public class FileManager implements AutoCloseable {
    private static final String BASE_PATH = "/javadb/base";
    private final RandomAccessFilePool filePool;
    private final String databaseName;

    static class RandomAccessFilePool extends LRUCache<String, RandomAccessFile> {
        public RandomAccessFilePool(int capacity) {
            super(capacity);
        }

        @Override
        public RandomAccessFile evictElement() {
            try {
                RandomAccessFile removed = super.evictElement();
                removed.close();
                return removed;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void teardown() {
            try {
                for (var e : this.linkedList) {
                    e.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
    }

    public FileManager(String databaseName) throws IOException {
        this.databaseName = databaseName;
        this.filePool = new RandomAccessFilePool(32);
        Path p = Paths.get(BASE_PATH, databaseName);
        if (!Files.exists(p)) {
            Files.createDirectory(p);
        }
    }

    public synchronized void createFile(String tableName) throws IOException {
        Path p = Paths.get(BASE_PATH, databaseName, tableName);
        RandomAccessFile raf = new RandomAccessFile(Files.createFile(p).toFile(), "rw");
        filePool.put(tableName, raf);
    }

    public RandomAccessFile getFile(Path p) throws IOException {
        Path filename = p.getFileName();
        if (filename == null) {
            throw new FileNotFoundException();
        }
        Optional<RandomAccessFile> optionalRaf = filePool.get(p.toString());
        if (optionalRaf.isPresent()) {
            RandomAccessFile raf = optionalRaf.get();
            if (raf.getFD().valid()) {
                return raf;
            }
        }
        RandomAccessFile raf = new RandomAccessFile(p.toFile(), "rw");
        filePool.put(p.toString(), raf);
        return raf;
    }

    public void persist(List<Page> pages) throws IOException {
        var tableToPages = pages.stream()
                .collect(Collectors.groupingBy(Page::getTableName));

        for (var item : tableToPages.entrySet()) {
            var k = item.getKey();
            var v = item.getValue();
            Path path = Path.of(BASE_PATH + "/" + databaseName + "/" + k);
            byte[] contents = Page.concat(v);

            RandomAccessFile raf;
            if (filePool.get(k).isPresent()) {
                raf = filePool.get(k).get();
            } else {
                raf = new RandomAccessFile(path.toFile(), "rw");
            }
            raf.write(contents);
        }
    }

    @Override
    public void close() throws Exception {
        filePool.teardown();
    }
}
