package ru.dyakovlev.adventofcode.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TxtFileUtils {

    public static List<String> readAllLinesFromResourceFile(String filePath) {
        List<String> lines;
        try {
            ClassLoader classLoader = TxtFileUtils.class.getClassLoader();
            URL url = classLoader.getResource(filePath);
            assert url != null;
            File file = new File(url.toURI());

            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

}
