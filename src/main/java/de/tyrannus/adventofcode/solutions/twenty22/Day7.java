package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day7 extends Solution {
    private static final Directory ROOT = new Directory("/", null);

    public Day7() {
        super(2022, 7);
    }

    @Override
    protected int partOne(String input) {
        var allDirs = parseInputs(input);

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
        var allDirs = parseInputs(input);

        var rootSize = ROOT.getSizeUncapped();

        var freeSpace = 70_000_000 - rootSize;
        var missingSpace = 30_000_000 - freeSpace;

        var smallestDirSize = rootSize;

        for (var dir : allDirs) {
            var dirTotalSize = dir.getSizeUncapped();

            if (dirTotalSize >= missingSpace && dirTotalSize < smallestDirSize) {
                smallestDirSize = dirTotalSize;
            }
        }

        return smallestDirSize;
    }

    private List<Directory> parseInputs(String input) {
        ROOT.subDirectories.clear();
        ROOT.size = 0;

        var lines = input.split("\n");
        var allDirs = new ArrayList<Directory>();
        allDirs.add(ROOT);

        var currentDir = ROOT;

        for (var i = 0; i < lines.length; i++) {
            var line = lines[i];

            if (line.startsWith("$")) {     //Command
                if (line.startsWith("cd", 2)) {
                    var dir = line.substring(5);

                    switch (dir) {
                        case "/" -> currentDir = ROOT;
                        case ".." -> currentDir = currentDir.parent;
                        default -> {
                            for (var subDir : currentDir.subDirectories) {
                                if (subDir.name.equals(dir)) {
                                    currentDir = subDir;
                                    break;
                                }
                            }
                        }
                    }
                } else {    //List command
                    for (var j = i + 1; j < lines.length; j++) {
                        var listLine = lines[j];

                        if (listLine.startsWith("$")) {
                            break;
                        }

                        if (listLine.startsWith("dir")) {
                            var newDir = new Directory(listLine.substring(4), currentDir);

                            currentDir.subDirectories.add(newDir);
                            allDirs.add(newDir);
                        } else {
                            currentDir.size += Integer.parseInt(listLine.substring(0, listLine.indexOf(" ")));
                        }

                        i++;
                    }
                }
            }
        }

        return allDirs;
    }

    private static class Directory {
        public final String name;
        public final Directory parent;
        public final List<Directory> subDirectories = new ArrayList<>();
        public int size;

        public Directory(String name, Directory parent) {
            this.name = name;
            this.parent = parent == null ? this : parent;
        }

        /**
         *
         * @return Won't continue calculating size, if it already exceeded 100k.
         */
        public int getSizeCapped() {
            var size = this.size;

            for (var subDir : subDirectories) {
                size += subDir.getSizeCapped();

                if (size > 100_000) {
                    return size;
                }
            }

            return size;
        }

        public int getSizeUncapped() {
            var size = this.size;

            for (var subDir : subDirectories) {
                size += subDir.getSizeUncapped();
            }

            return size;
        }
    }
}
