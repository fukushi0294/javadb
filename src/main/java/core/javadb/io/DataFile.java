package core.javadb.io;

import com.google.common.primitives.Bytes;
import core.javadb.io.page.Page;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.stream.Collectors;

public class DataFile {
    private int freeSpaceLower;
    private int freeSpaceHigher;
    private static int HEADER_LENGTH = 1024 * 8;
    private DataFileHeader dataFileHeader;

    private final RandomAccessFile randomAccessFile;
    private FileOutputStream fileOutputStream;

    public DataFile(RandomAccessFile fi) throws IOException {
        this.randomAccessFile = fi;
        this.dataFileHeader = extract(fi);
        this.freeSpaceLower = this.dataFileHeader.higher;
        this.freeSpaceHigher = this.dataFileHeader.lower;
        this.fileOutputStream = new FileOutputStream(fi.getFD());
    }

    public DataFileHeader extract(RandomAccessFile fi) throws IOException {
        byte[] bytes = new byte[HEADER_LENGTH];
        fi.read(bytes, 0, HEADER_LENGTH);
        return new DataFileHeader(bytes);
    }

    public void append(List<Page> pages) throws IOException {
        List<Byte> bytes = pages.stream().flatMap(p -> p.toByte().stream()).collect(Collectors.toList());
        this.fileOutputStream.write(Bytes.toArray(bytes), this.freeSpaceLower, bytes.size());
        this.freeSpaceLower += bytes.size();
    }

    static class DataFileHeader {
        int lower;
        int higher;

        public DataFileHeader(byte[] bytes) {
            byte lower = bytes[8];
            byte higher = bytes[16];
            this.lower = Byte.valueOf(lower).intValue();
            this.higher = Byte.valueOf(higher).intValue();
        }
    }
}
