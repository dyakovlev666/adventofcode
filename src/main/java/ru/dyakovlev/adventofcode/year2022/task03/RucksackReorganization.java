package ru.dyakovlev.adventofcode.year2022.task03;

import ru.dyakovlev.adventofcode.utils.ConsoleLogger;
import ru.dyakovlev.adventofcode.utils.TxtFileUtils;

import java.util.Comparator;
import java.util.List;

public class RucksackReorganization {

    private static final ConsoleLogger log = ConsoleLogger.getLogger();

    private static boolean isItemPresent(char item, String rucksack) {
        for (int i = 0 ; i < rucksack.length() ; i++) {
            if (item == rucksack.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    private static void test(String filePath) {
        try {

            log.info("Read file    : '%s'", filePath);
            List<String> lines = TxtFileUtils.readAllLinesFromResourceFile(filePath);
            log.info("Total lines  : %d", lines.size());

            int sum = 0;

            for (String rucksack : lines) {
                int size = rucksack.length();
                int halfSize = size / 2;

                char findItem = 0;
                int priority;


                for (int i = 0 ; i < halfSize ; i++) {
                    char item = rucksack.charAt(i);
                    for (int j = halfSize ; j < size ; j++) {
                        if (item == rucksack.charAt(j)) {
                            findItem = item;
                            break;
                        }
                    }
                }

                priority = (findItem >= 'a') ? findItem - 96 : findItem - 64 + 26;
                sum += priority;

                log.info("%s >> %s (%2d)", rucksack, findItem, priority);
            }
            log.info("Sum of priorities : %d\n", sum);

            //////////////////////////////////////////////////////////////////////////////////////////////////////

            sum = 0;
            int groupSize = 3;
            int groupsCount = lines.size() / groupSize;

            for (int i = 0 ; i < groupsCount ; i++) {
                int offset = i * groupSize;
                List<String> group = lines.subList(offset, offset + groupSize);
                group.sort(Comparator.comparingInt(String::length));

                char findItem = 0;
                int priority;
                String minRucksack = group.get(0);
                int minRucksackSize = minRucksack.length();

                for (int j = 0; j < minRucksackSize ; j++) {
                    char item = minRucksack.charAt(j);
                    if (isItemPresent(item, group.get(1)) && isItemPresent(item, group.get(2))) {
                        findItem = item;
                        break;
                    }
                }

                priority = (findItem >= 'a') ? findItem - 96 : findItem - 64 + 26;
                sum += priority;

                log.info("GROUP %3d >> %s (%2d)", i + 1, findItem, priority);
            }
            log.info("Sum of GROUPS priorities : %d", sum);

        } catch (Exception e) {
            log.error(e);
        }
    }

    public static void main(String[] args) {
        log.info("------------------------------------------------------------------------------------------");
        log.info("|                  Advent of Code - Task 03 - Rucksack Reorganization                    |");
        log.info("------------------------------------------------------------------------------------------\n");

        test("data/adventofcode/year2022/task03/2022_03-1-example_data.txt");
        log.info("\n------------------------------------------------------------------------------------------\n");
        test("data/adventofcode/year2022/task03/2022_03-2-real_data.txt");

        log.info("\n------------------------------------------------------------------------------------------");
    }

}
