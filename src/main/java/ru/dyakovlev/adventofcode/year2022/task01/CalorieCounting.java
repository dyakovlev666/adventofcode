package ru.dyakovlev.adventofcode.year2022.task01;

import ru.dyakovlev.adventofcode.utils.ConsoleLogger;
import ru.dyakovlev.adventofcode.utils.TxtFileUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CalorieCounting {

    private static final ConsoleLogger log = ConsoleLogger.getLogger();

    private static List<List<Integer>> convertLinesToArrayOfIntArrays(List<String> lines) {
        List<List<Integer>> arrayOfIntArrays = new ArrayList<>();
        List<Integer> intArray = new ArrayList<>();

        for (String line : lines) {
            if (line != null && line.length() != 0) {
                intArray.add(Integer.parseInt(line));
            } else {
                arrayOfIntArrays.add(intArray);
                intArray = new ArrayList<>();
            }
        }

        if (arrayOfIntArrays.contains(intArray)) {
            arrayOfIntArrays.add(intArray);
        }

        return arrayOfIntArrays;
    }

    private static void test(String filePath) {
        try {

            log.info("Read file    : '%s'", filePath);
            List<String> lines = TxtFileUtils.readAllLinesFromResourceFile(filePath);
            log.info("Total lines  : %d", lines.size());

            List<List<Integer>> elfCaloriesList = convertLinesToArrayOfIntArrays(lines);
            log.info("Total elf    : %d", elfCaloriesList.size());

            List<Integer> elfSumCaloriesList = new ArrayList<>();

            for (List<Integer> calories : elfCaloriesList) {
                elfSumCaloriesList.add(calories.stream().mapToInt(Integer::intValue).sum());
            }
            elfSumCaloriesList.sort(Comparator.reverseOrder());

            log.info("Elf with max calories : %d", elfSumCaloriesList.get(0));

            log.info(
                    "Sum of the first 3 elf with max calories : %d",
                    elfSumCaloriesList.get(0) + elfSumCaloriesList.get(1) + elfSumCaloriesList.get(2)
            );

        } catch (Exception e) {
            log.error(e);
        }
    }

    public static void main(String[] args) {
        log.info("------------------------------------------------------------------------------------------");
        log.info("|                  Advent of Code - Task 01 - Calorie Counting                           |");
        log.info("------------------------------------------------------------------------------------------\n");

        test("data/adventofcode/year2022/task01/2022_01-1-example_data.txt");
        log.info("\n------------------------------------------------------------------------------------------\n");
        test("data/adventofcode/year2022/task01/2022_01-2-real_data.txt");

        log.info("\n------------------------------------------------------------------------------------------");
    }

}
