package main.java.de.tyrannus.adventofcode.solutions.twenty22;

import main.java.de.tyrannus.adventofcode.solutions.Solution;

public class Day04 extends Solution<Integer> {

    public Day04() {
        super(2022, 4);
    }

    @Override
    public Integer partOne(String input) {
        var pairs = input.split("\n");
        var encapsulatedElfs = 0;

        for (var pair : pairs) {
            var pairSplit = pair.split(",");

            var firstElf = pairSplit[0].split("-");
            var secondElf = pairSplit[1].split("-");

            var firstBoundLow = Integer.parseInt(firstElf[0]);
            var firstBoundHigh = Integer.parseInt(firstElf[1]);

            var secondBoundLow = Integer.parseInt(secondElf[0]);
            var secondBoundHigh = Integer.parseInt(secondElf[1]);

            if (secondBoundLow >= firstBoundLow && secondBoundHigh <= firstBoundHigh || firstBoundLow >= secondBoundLow && firstBoundHigh <= secondBoundHigh) {
                encapsulatedElfs++;
            }
        }

        return encapsulatedElfs;
    }


    @Override
    public Integer partTwo(String input) {
        var pairs = input.split("\n");
        var encapsulatedElfs = 0;

        for (var pair : pairs) {
            var pairSplit = pair.split(",");

            var firstElf = pairSplit[0].split("-");
            var secondElf = pairSplit[1].split("-");

            var firstBoundLow = Integer.parseInt(firstElf[0]);
            var firstBoundHigh = Integer.parseInt(firstElf[1]);

            var secondBoundLow = Integer.parseInt(secondElf[0]);
            var secondBoundHigh = Integer.parseInt(secondElf[1]);

            if (secondBoundLow >= firstBoundLow && secondBoundLow <= firstBoundHigh
                    || secondBoundHigh >= firstBoundLow && secondBoundHigh <= firstBoundHigh
                    || firstBoundLow >= secondBoundLow && firstBoundLow <= secondBoundHigh
                    || firstBoundHigh >= secondBoundLow && firstBoundHigh <= secondBoundHigh
            ) {
                encapsulatedElfs++;
            }
        }

        return encapsulatedElfs;
    }
}
