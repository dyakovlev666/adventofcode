package ru.dyakovlev.adventofcode.year2022.task02;

import ru.dyakovlev.adventofcode.utils.ConsoleLogger;
import ru.dyakovlev.adventofcode.utils.TxtFileUtils;

import java.util.List;

public class RockPaperScissors {

    private static final ConsoleLogger log = ConsoleLogger.getLogger();

    private enum Element {

        ROCK(1),
        PAPER(2),
        SCISSORS(3);

        private final int score;

        Element(final int score) {
            this.score = score;
        }

        int getScore() {
            return score;
        }

        static Element fromChar(char ch) {
            switch (ch) {
                case 'A' :
                case 'X' :
                    return ROCK;
                case 'B' :
                case 'Y' :
                    return PAPER;
                default:
                    return SCISSORS;
            }
        }

    }

    enum RoundResult {

        LOSS(0),
        DRAW(3),
        WON(6);

        private final int score;

        RoundResult(final int score) {
            this.score = score;
        }

        int getScore() {
            return score;
        }

    }

    private static RoundResult fight(Element opponent, Element me) {
        if (opponent == me) {
            return RoundResult.DRAW;
        }
        if (
                (opponent == Element.ROCK && me == Element.PAPER)
                        || (opponent == Element.PAPER && me == Element.SCISSORS)
                        || (opponent == Element.SCISSORS && me == Element.ROCK)
        ) {
            return RoundResult.WON;
        }
        return RoundResult.LOSS;
    }

    private static Element applyStrategy(Element opponent, Element me) {
        switch (me) {
            case ROCK :
                if (opponent == Element.SCISSORS) return Element.PAPER;
                if (opponent == Element.ROCK) return Element.SCISSORS;
                return me;
            case PAPER:
                return opponent;
            case SCISSORS:
                if (opponent == Element.SCISSORS) return Element.ROCK;
                if (opponent == Element.ROCK) return Element.PAPER;
                return me;
            default:
                return me;
        }
    }

    private static void test(String filePath) {
        try {
            log.info("Read file    : '%s'", filePath);
            List<String> lines = TxtFileUtils.readAllLinesFromResourceFile(filePath);
            log.info("Total lines  : %d", lines.size());

            int score;
            int totalScore = 0;

            int scoreWithStrategy;
            int totalScoreWithStrategy = 0;

            for (int round = 1 ; round <= lines.size() ; round ++) {
                String line = lines.get(round - 1);

                Element opponent = Element.fromChar(line.charAt(0));
                Element me = Element.fromChar(line.charAt(2));
                RoundResult roundResult = fight(opponent, me);

                score = me.getScore() + roundResult.getScore();
                totalScore += score;

                Element meCheater = applyStrategy(opponent, me);
                RoundResult cheatRoundResult = fight(opponent, meCheater);

                scoreWithStrategy = meCheater.getScore() + cheatRoundResult.getScore();
                totalScoreWithStrategy += scoreWithStrategy;

                log.info(
                        "Round : %4d >> %s >> %8s %8s (%8s) >> %4s (%4s) >> %d (%d) >> %6d (%6d)",
                        round,
                        line,
                        opponent,
                        me,
                        meCheater,
                        roundResult,
                        cheatRoundResult,
                        score,
                        scoreWithStrategy,
                        totalScore,
                        totalScoreWithStrategy
                );
            }

            log.info("Total score : %d", totalScore);
            log.info("Total score with strategy : %d", totalScoreWithStrategy);

        } catch (Exception e) {
            log.error(e);
        }
    }

    public static void main(String[] args) {
        log.info("------------------------------------------------------------------------------------------");
        log.info("|                  Advent of Code - Task 02 - Rock Paper Scissors                        |");
        log.info("------------------------------------------------------------------------------------------\n");

        test("data/adventofcode/year2022/task02/2022_02-1-example-data.txt");
        log.info("\n------------------------------------------------------------------------------------------\n");
        test("data/adventofcode/year2022/task02/2022_02-1-real-data.txt");

        log.info("\n------------------------------------------------------------------------------------------");
    }

}
