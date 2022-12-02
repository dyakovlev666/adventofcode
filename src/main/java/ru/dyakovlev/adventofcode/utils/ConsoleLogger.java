package ru.dyakovlev.adventofcode.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConsoleLogger {

    public static ConsoleLogger getLogger() {
        return new ConsoleLogger();
    }

    public void info(String data) {
        System.out.println(data);
    }

    public void info(String format, Object... args) {
        System.out.printf(format + "%n", args);
    }

    public void error(Exception e) {
        System.out.printf("ERROR :\n  CLASS   : %s\n  MESSAGE :%s", e.getClass().getName(), e.getMessage());
    }

}
