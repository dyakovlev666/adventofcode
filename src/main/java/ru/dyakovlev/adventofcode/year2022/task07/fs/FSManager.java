package ru.dyakovlev.adventofcode.year2022.task07.fs;

import java.util.ArrayList;

public class FSManager {

    private final FSItem rootDir;
    private FSItem currDir;

    public FSManager() {
        rootDir = createRootDir();
        currDir = rootDir;
    }

    private FSItem createRootDir() {
        return FSItem
                .builder()
                .name("/")
                .type(FSItemType.DIRECTORY)
                .children(new ArrayList<>())
                .build();
    }

    public FSItem getRootDir() {
        return rootDir;
    }

    public FSItem getCurrDir() {
        return currDir;
    }

    public void goToDir(String name) {
        for (FSItem findDir : currDir.getChildren()) {
            if (findDir.getType() == FSItemType.DIRECTORY && findDir.getName().equals(name)) {
                currDir = findDir;
                return;
            }
        }
        currDir = rootDir;
    }

    public void goBackToDir() {
        currDir = currDir.getParent();
    }

    public FSItem createDir(String name) {
        FSItem newDir = FSItem
                .builder()
                .type(FSItemType.DIRECTORY)
                .name(name)
                .parent(currDir)
                .children(new ArrayList<>())
                .build();
        currDir.getChildren().add(newDir);
        return newDir;
    }

    public void createFile(String name, Long size) {
        currDir.getChildren().add(
                FSItem
                        .builder()
                        .type(FSItemType.FILE)
                        .name(name)
                        .parent(currDir)
                        .size(size)
                        .build()
        );
    }

    private String toString(String offset, FSItem item) {
        StringBuilder sb = new StringBuilder();
        sb.append(offset.concat("- ").concat(item.toString()));
        if (item.getType() == FSItemType.DIRECTORY) {
            for (FSItem child : item.getChildren()) {
                sb.append("\n");
                sb.append(toString(offset + "  ", child));
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return toString("", rootDir);
    }

}
