package main.java.de.tyrannus.adventofcode.solutions.TwentyOne;

import main.java.de.tyrannus.adventofcode.Solution;

import java.util.Arrays;

public class DayOne extends Solution {

    public DayOne() {
        super(2021, 1);
    }

    @Override
    protected int partOne(String input) {
        var split = Arrays.stream(input.split("\n")).mapToInt(Integer::parseInt).toArray();

        var increments = 0;
        var lastDepth = split[0];

        for (var i = 1; i < split.length; i++) {
            var depth = split[i];

            if (depth > lastDepth) {
                increments++;
            }

            lastDepth = depth;
        }

        return increments;
    }

    @Override
    protected int partTwo(String input) {
        var split = Arrays.stream(input.split("\n")).mapToInt(Integer::parseInt).toArray();

        var increments = 0;

        for (var i = 3; i < split.length; i++) {
            if (sum(i, split) > sum(i - 1, split)) {
                increments++;
            }
        }

        return increments;
    }

    private int sum(int index, int[] ints) {
        return ints[index] + ints[index - 1] + ints[index - 2];
    }

}
