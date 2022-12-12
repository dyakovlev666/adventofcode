package ru.dyakovlev.adventofcode.year2022.task05;

import ru.dyakovlev.adventofcode.utils.ConsoleLogger;
import ru.dyakovlev.adventofcode.utils.TxtFileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SupplyStacks {

    private static final ConsoleLogger log = ConsoleLogger.getLogger();

    private static List<Stack<Character>> parseStacks(List<String> lines) {
        List<Stack<Character>> stacks = new ArrayList<>();

        int endOfStacksIndex = 0;
        for (String line : lines) {
            if (line == null || line.length() == 0) {
                break;
            }
            log.info("STACKS LINE [%3d] : %s", endOfStacksIndex++, line);
        }

        int stacksCount = lines.get(endOfStacksIndex - 1).replaceAll(" ", "").length();
        log.info("STACKS COUNT : %d", stacksCount);

        for (int i = 0 ; i < stacksCount ; i++) {
            stacks.add(new Stack<>());
        }

        for (int i = endOfStacksIndex - 2 ; i >= 0; i--) {

            String stackLine = lines.get(i);

            for (int j = 0 ; j < stacksCount ; j++) {

                int offset = 1 + 4 * j;
                if (offset < stackLine.length()) {
                    char value = stackLine.charAt(offset);
                    if (value != ' ') {
                        stacks.get(j).push(value);
                    }
                }
            }
        }

        for (int i = 0 ; i < stacks.size(); i++) {
            log.info("STACK [%d] --> %s", (i + 1), stacks.get(i).toString());
        }

        return stacks;
    }

    private static void test(String filePath) {
        test(filePath, false);
    }

    private static void test(String filePath, boolean reverseAlg) {
        try {

            log.info("Read file    : '%s'", filePath);
            List<String> lines = TxtFileUtils.readAllLinesFromResourceFile(filePath);
            log.info("Total lines  : %d", lines.size());

            List<Stack<Character>> stacks = parseStacks(lines);

            for (int moveIndex = stacks.size() + 1 ; moveIndex < lines.size() ; moveIndex++) {

                String movingLine = lines.get(moveIndex);
                if (movingLine.isEmpty()) {
                    continue;
                }
                String[] movingLineParts = movingLine.split(" ");

                int count = Integer.parseInt(movingLineParts[1]);
                int indexFrom = Integer.parseInt(movingLineParts[3]) - 1;
                int indexTo = Integer.parseInt(movingLineParts[5]) - 1;

                /*log.info(
                        "MOVING LINE [%3d] %s : Count = %d, Index from = %d, Index to = %d",
                        moveIndex,
                        movingLine,
                        count,
                        indexFrom,
                        indexTo
                );*/

                if (!reverseAlg) {
                    for (int i = 0; i < count; i++) {
                        stacks.get(indexTo).push(stacks.get(indexFrom).pop());
                    }
                } else {
                    List<Character> items = new ArrayList<>();
                    for (int i = 0 ; i < count ; i++) {
                        items.add(stacks.get(indexFrom).pop());
                    }
                    while (!items.isEmpty()) {
                        stacks.get(indexTo).push(items.remove(items.size() - 1));
                    }
                }

            }

            StringBuilder result = new StringBuilder();
            for (Stack<Character> stack : stacks) {
                result.append(stack.peek());
            }
            log.info("%sRESULT : %s", reverseAlg ? "REVERSE " : "", result);

        } catch (Exception e) {
            log.error(e);
        }
    }

    public static void main(String[] args) {
        log.info("------------------------------------------------------------------------------------------");
        log.info("|                  Advent of Code - Task 05 - Supply Stacks                              |");
        log.info("------------------------------------------------------------------------------------------\n");

        test("data/adventofcode/year2022/task05/2022_05-1-example_data.txt");
        log.info("\n------------------------------------------------------------------------------------------\n");
        test("data/adventofcode/year2022/task05/2022_05-1-example_data.txt", true);
        log.info("\n------------------------------------------------------------------------------------------\n");
        test("data/adventofcode/year2022/task05/2022_05-2-real_data.txt");
        log.info("\n------------------------------------------------------------------------------------------\n");
        test("data/adventofcode/year2022/task05/2022_05-2-real_data.txt", true);

        log.info("\n------------------------------------------------------------------------------------------");
    }

}
