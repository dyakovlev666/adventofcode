package ru.dyakovlev.adventofcode.year2022.task04;

import ru.dyakovlev.adventofcode.utils.ConsoleLogger;
import ru.dyakovlev.adventofcode.utils.TxtFileUtils;

import java.util.List;

public class CampCleanup {

    private static final ConsoleLogger log = ConsoleLogger.getLogger();

    private static boolean numberInRange(int number, int from, int to) {
        return number >= from && number <= to;
    }

    private static void test(String filePath) {
        try {

            log.info("Read file    : '%s'", filePath);
            List<String> lines = TxtFileUtils.readAllLinesFromResourceFile(filePath);
            log.info("Total lines  : %d", lines.size());

            int count = 0;
            int countOfOverlaps = 0;

            for(String line : lines) {
                String[] pairs = line.split(",");
                String[] range1 = pairs[0].split("-");
                String[] range2 = pairs[1].split("-");

                int min1 = Integer.parseInt(range1[0]);
                int max1 = Integer.parseInt(range1[1]);

                int min2 = Integer.parseInt(range2[0]);
                int max2 = Integer.parseInt(range2[1]);

                if (((min1 >= min2) && (max1 <= max2)) || (min2 >= min1) && (max2 <= max1)) {
                    count++;
                }

               if (
                       (numberInRange(min1, min2, max2) || numberInRange(max1, min2, max2))
                        || (numberInRange(min2, min1, max1) || numberInRange(max2, min1, max1))
               ) {
                   countOfOverlaps++;
               }
            }
            log.info("Result : %d", count);
            log.info("Overlaps result : %d", countOfOverlaps);

        } catch (Exception e) {
            log.error(e);
        }
    }

    public static void main(String[] args) {
        log.info("------------------------------------------------------------------------------------------");
        log.info("|                  Advent of Code - Task 04 - Camp cleanup                               |");
        log.info("------------------------------------------------------------------------------------------\n");

        test("data/adventofcode/year2022/task04/2022_04-1-example_data.txt");
        log.info("\n------------------------------------------------------------------------------------------\n");
        test("data/adventofcode/year2022/task04/2022_04-2-real_data.txt");

        log.info("\n------------------------------------------------------------------------------------------");
    }

}
