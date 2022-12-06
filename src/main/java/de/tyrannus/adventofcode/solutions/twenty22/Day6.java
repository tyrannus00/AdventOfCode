package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

import java.util.HashSet;

public class Day6 extends Solution {
    public Day6() {
        super(2022, 6);
    }

    @Override
    protected int partOne(String input) {
        var list = input.chars().mapToObj(c -> (char) c).toList();

        for (var i = 3; i < list.size(); i++) {
            var set = new HashSet<>(list.subList(i - 3, i + 1));

            if (set.size() == 4) {  //Sets can't contain duplicates
                return i + 1;
            }
        }

        return 0;
    }

    @Override
    protected int partTwo(String input) {
        var list = input.chars().mapToObj(c -> (char) c).toList();

        //Starting at 1099 + 10, as 1099 was the first index with at least unique characters in a row, as we calculated in part one.
        for (var i = 1109; i < list.size(); i++) {
            var set = new HashSet<>(list.subList(i - 13, i + 1));

            if (set.size() == 14) {  //Sets can't contain duplicates
                return i + 1;
            }
        }

        return 0;
    }
}
