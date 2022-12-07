package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.*;

public class Day7 extends Solution {
    private static final Directory ROOT = new Directory("/", null);

    public Day7() {
        super(2022, 7);
    }

    @Override
    protected int partOne(String input) {
        var allDirs = parseInputs(input.split("\n"));

        var size = 0;

        for (var dir : allDirs) {
            var dirSize = dir.getSizeCapped();

            if (dirSize <= 100_000) {
                size += dirSize;
            }
        }

        return size;
    }

    @Override
    protected int partTwo(String input) {
        var allDirs = parseInputs(input.split("\n"));

        var rootSize = ROOT.getSizeUncapped();

        var freeSpace = 70_000_000 - rootSize;
        var missingSpace = 30_000_000 - freeSpace;

        var smallestDirSize = rootSize;

        for (var dir : allDirs) {
            var dirTotalSize = dir.getSizeUncapped();

            if (dirTotalSize > missingSpace && dirTotalSize < smallestDirSize) {
                smallestDirSize = dirTotalSize;
            }
        }

        return smallestDirSize;
    }

    private List<Directory> parseInputs(String[] lines) {
        ROOT.subDirectories.clear();
        ROOT.files.clear();

        var allDirs = new ArrayList<Directory>();
        allDirs.add(ROOT);

        var currentDir = ROOT;

        lines:
        for (var i = 0; i < lines.length; i++) {
            var line = lines[i];

            if (line.startsWith("$")) {     //Command
                if (line.startsWith("cd", 2)) {
                    if (line.startsWith("..", 5)) {
                        currentDir = currentDir.getParent();
                    } else {
                        var searchDir = line.substring(5);

                        if (searchDir.equals(ROOT.getName())) {
                            currentDir = ROOT;
                            continue;
                        }

                        for (var subDir : currentDir.subDirectories) {
                            if (subDir.getName().equals(searchDir)) {
                                currentDir = subDir;
                                continue lines;
                            }
                        }
                        throw new RuntimeException("wrong subdir");
                    }
                } else {    //List command
                    for (var j = i + 1; j < lines.length; j++) {
                        var listLine = lines[j];

                        if (listLine.startsWith("$")) {
                            break;
                        }

                        if (listLine.startsWith("dir")) {
                            var dirName = listLine.substring(4);
                            var newDir = new Directory(dirName, currentDir);

                            currentDir.subDirectories.add(newDir);
                            allDirs.add(newDir);
                        } else {
                            var sizeNameSplit = listLine.split(" ");

                            currentDir.files.putIfAbsent(sizeNameSplit[1], Integer.parseInt(sizeNameSplit[0]));
                        }

                        i++;
                    }
                }
            }
        }

        return allDirs;
    }

    private static class Directory {
        private final String name;
        private final Directory parent;
        public final Set<Directory> subDirectories = new HashSet<>();
        public final Map<String, Integer> files = new HashMap<>();

        public Directory(String name, Directory parent) {
            this.name = name;
            this.parent = Objects.requireNonNullElse(parent, this);
        }

        public String getName() {
            return name;
        }

        public Directory getParent() {
            return parent;
        }

        /**
         *
         * @return Won't continue calculating size, if it already exceeded 100k.
         */
        public int getSizeCapped() {
            var size = 0;

            for (var value : files.values()) {
                size += value;

                if (size > 100_000) {
                    return size;
                }
            }

            for (var subDir : subDirectories) {
                size += subDir.getSizeCapped();

                if (size > 100_000) {
                    return size;
                }
            }

            return size;
        }

        public int getSizeUncapped() {
            var size = 0;

            for (var value : files.values()) {
                size += value;
            }

            for (var subDir : subDirectories) {
                size += subDir.getSizeUncapped();
            }

            return size;
        }
    }
}
