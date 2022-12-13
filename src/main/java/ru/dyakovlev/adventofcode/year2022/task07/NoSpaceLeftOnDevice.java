package ru.dyakovlev.adventofcode.year2022.task07;

import ru.dyakovlev.adventofcode.utils.ConsoleLogger;
import ru.dyakovlev.adventofcode.utils.TxtFileUtils;
import ru.dyakovlev.adventofcode.year2022.task07.fs.FSItem;
import ru.dyakovlev.adventofcode.year2022.task07.fs.FSItemType;
import ru.dyakovlev.adventofcode.year2022.task07.fs.FSManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NoSpaceLeftOnDevice {

    private static final ConsoleLogger log = ConsoleLogger.getLogger();

    private static long calcCatalogSize(FSItem item) {
        if (item.getType() == FSItemType.FILE) {
            return item.getSize();
        }
        long size = 0;
        for (FSItem child : item.getChildren()) {
            size += (item.getType() == FSItemType.FILE ? item.getSize() : calcCatalogSize(child));
        }
        return size;
    }

    private static void test(String filePath) {
        try {

            log.info("Read file    : '%s'", filePath);
            List<String> lines = TxtFileUtils.readAllLinesFromResourceFile(filePath);
            log.info("Total lines  : %d\n", lines.size());

            FSManager fsManager = new FSManager();
            List<FSItem> dirs = new ArrayList<>();

            for (String line : lines) {
                String[] tokens = line.split(" ");

                switch (tokens[0]) {
                    case "$" :
                            if (Objects.equals(tokens[1], "cd")) {
                                if (Objects.equals(tokens[2], "..")) {
                                    fsManager.goBackToDir();
                                } else {
                                    fsManager.goToDir(tokens[2]);
                                }
                            }
                        break;
                    case "dir":
                            dirs.add(fsManager.createDir(tokens[1]));
                        break;
                    default:
                        fsManager.createFile(tokens[1], Long.parseLong(tokens[0]));
                }
            }

            log.info(fsManager.toString());
            log.info("");

            long limit = 100000;
            long sum = 0;

            for (FSItem dir : dirs) {
                long dirSize = calcCatalogSize(dir);
                String found = "";
                if (dirSize < limit) {
                    sum += dirSize;
                    found = "(found)";
                }
                log.info("%s : %d %s", dir.getName(), dirSize, found);
            }
            log.info("Sum of founds catalogs with limit %d is %d\n", limit, sum);

            long maxSize = 70_000_000L;
            long minNeeded = 30_000_000L;
            long totalSize = calcCatalogSize(fsManager.getRootDir());
            long unusedSize = maxSize - totalSize;
            long minToFree = minNeeded - unusedSize;

            log.info("max         = %d", maxSize);
            log.info("needed_free = %d", minNeeded);
            log.info("total       = %d", totalSize);
            log.info("unused      = %d", unusedSize);
            log.info("min_to_free = %d", minToFree);

            long foundCatalogForDeleteSize = dirs
                    .stream()
                    .mapToLong(NoSpaceLeftOnDevice::calcCatalogSize)
                    .filter(size -> size >= minToFree)
                    .min()
                    .orElse(0);
            log.info("Found catalog to delete is %d", foundCatalogForDeleteSize);

        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        log.info("------------------------------------------------------------------------------------------");
        log.info("|                  Advent of Code - Task 07 - No Space Left On Device                    |");
        log.info("------------------------------------------------------------------------------------------\n");

        test("data/adventofcode/year2022/task07/2022_07-1-example_data.txt");
        log.info("\n------------------------------------------------------------------------------------------\n");
        test("data/adventofcode/year2022/task07/2022_07-2-real_data.txt");

        log.info("\n------------------------------------------------------------------------------------------");
    }

}
