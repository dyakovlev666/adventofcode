package ru.dyakovlev.adventofcode.year2022.task07.fs;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FSItem {

    private FSItemType type;
    private String name;
    private Long size;
    private FSItem parent;
    private List<FSItem> children;

    @Override
    public String toString() {
        return name + ((type == FSItemType.DIRECTORY) ? " (dir)" : " (file, size = " + size + ")");
    }

}
