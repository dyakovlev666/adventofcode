package ru.dyakovlev.adventofcode.year2022.task06;

import ru.dyakovlev.adventofcode.utils.ConsoleLogger;
import ru.dyakovlev.adventofcode.utils.TxtFileUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TuningTrouble {

    private static final ConsoleLogger log = ConsoleLogger.getLogger();

    private static void findMarker(String line, int bufferSize) {
        log.info("Input message : %s", line);
        log.info("Message length : %d", line.length());
        log.info("Buffer size : %d", bufferSize);

        int findMarker = 0;
        for (int from = 0 ; from < line.length() - bufferSize ; from++) {
            Set<Character> chars = new HashSet<>();

            for (int to = from ; to < from + bufferSize ; to++) {
                chars.add(line.charAt(to));
            }

            if (chars.size() == bufferSize) {
                findMarker = from + bufferSize;
                break;
            }
        }

        log.info("FOUND MARKER : %d\n", findMarker);
    }

    private static void test(String filePath, int bufferSize) {
        try {

            log.info("Read file    : '%s'", filePath);
            List<String> lines = TxtFileUtils.readAllLinesFromResourceFile(filePath);
            log.info("Total lines  : %d\n", lines.size());

            for (String line : lines) {
                findMarker(line, bufferSize);
            }

        } catch (Exception e) {
            log.error(e);
        }
    }

    public static void main(String[] args) {
        log.info("------------------------------------------------------------------------------------------");
        log.info("|                  Advent of Code - Task 06 - TuningTrouble                              |");
        log.info("------------------------------------------------------------------------------------------\n");

        test("data/adventofcode/year2022/task06/2022_06-1-example_data.txt", 4);
        log.info("\n------------------------------------------------------------------------------------------\n");
        test("data/adventofcode/year2022/task06/2022_06-1-example_data.txt", 14);
        log.info("\n------------------------------------------------------------------------------------------\n");
        test("data/adventofcode/year2022/task06/2022_06-2-real_data.txt", 4);
        log.info("\n------------------------------------------------------------------------------------------\n");
        test("data/adventofcode/year2022/task06/2022_06-2-real_data.txt", 14);

        log.info("\n------------------------------------------------------------------------------------------");
    }

}
