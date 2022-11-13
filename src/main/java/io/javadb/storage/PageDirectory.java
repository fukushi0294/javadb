package io.javadb.storage;

import java.nio.file.Path;
import java.util.*;

/**
 * PageDirectory is special page which track the location of data page in the database file
 * Given page id, return the physical datafile location and offset.
 * Also, records meta-data about available space
 */
public class PageDirectory implements Serializable {
    /**
     * key: pageId
     * value: datafile location and offset
     */
    HashMap<String, String> pageId;
    /**
     * the number of free slots per page
     */
    HashMap<String, Integer> freeSlots;
    /**
     * List of free/ empty Pages
     */
    Stack<String> availablePageIds;

    // for full scan
    public String getDataFile(String tableName) {
        return null;
    }

    public Path getDatafilePath(String pageId) {
        return null;
    }

    public void append(Page page){

    }

    // for index scan
    public Map.Entry<String, Integer> getDataFileAndOffset(String pageId) {
        return null;
    }

    public String getEmpty() {
        if (availablePageIds.isEmpty()) {
            return null;
        }
        return availablePageIds.pop();
    }

    // periodically write to persistent disk
    public void persist() {

    }
}
